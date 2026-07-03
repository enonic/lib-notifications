package com.enonic.lib.notifications;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.martijndwars.webpush.Encoding;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Utils;

public final class PushBean
{
    private static final Logger LOG = LoggerFactory.getLogger( PushBean.class );

    static
    {
        if ( Security.getProvider( BouncyCastleProvider.PROVIDER_NAME ) == null )
        {
            Security.addProvider( new BouncyCastleProvider() );
        }
    }

    private String endpoint;

    private String publicKey;

    private String privateKey;

    private String auth;

    private String receiverKey;

    private String payload;

    private String subject;

    private Consumer<Integer> success;

    private Consumer<Integer> error;

    public int send()
        throws InterruptedException, GeneralSecurityException, JoseException, ExecutionException, IOException
    {
        return doSend();
    }

    public void sendAsync()
    {
        Thread.startVirtualThread( this::doSendAsync );
    }

    private int doSend()
        throws InterruptedException, GeneralSecurityException, JoseException, ExecutionException, IOException
    {
        final Notification notification = new Notification( endpoint, receiverKey, auth, payload );

        final PushService pushService = new PushService();
        pushService.setPublicKey( Utils.loadPublicKey( publicKey ) );
        pushService.setPrivateKey( Utils.loadPrivateKey( privateKey ) );

        if ( subject != null && !subject.isBlank() )
        {
            pushService.setSubject( subject );
        }

        final HttpResponse httpResponse = pushService.send( notification, Encoding.AES128GCM );
        final int status = httpResponse.getStatusLine().getStatusCode();
        if ( ( status < 200 || status >= 300 ) && LOG.isDebugEnabled() )
        {
            LOG.debug( "Web push rejected: status={} endpoint={} body={}", status, endpoint, readBody( httpResponse ) );
        }
        return status;
    }

    private static String readBody( final HttpResponse httpResponse )
    {
        try
        {
            return httpResponse.getEntity() != null ? EntityUtils.toString( httpResponse.getEntity() ) : "";
        }
        catch ( final IOException e )
        {
            return "<unreadable: " + e.getMessage() + ">";
        }
    }

    private void doSendAsync()
    {
        try
        {
            final int status = doSend();
            if ( success != null && ( status >= 200 && status < 300 ) )
            {
                success.accept( status );
            }
            else if ( error != null && ( status < 200 || status >= 300 ) )
            {
                error.accept( status );
            }
        }
        catch ( Throwable t )
        {
            if ( error != null )
            {
                error.accept( 0 );
            }
        }
    }

    public void setEndpoint( final String endpoint )
    {
        this.endpoint = endpoint;
    }

    public void setPublicKey( final String publicKey )
    {
        this.publicKey = publicKey;
    }

    public void setPrivateKey( final String privateKey )
    {
        this.privateKey = privateKey;
    }

    public void setAuth( final String auth )
    {
        this.auth = auth;
    }

    public void setPayload( final String payload )
    {
        this.payload = payload;
    }

    public void setSubject( final String subject )
    {
        this.subject = subject;
    }

    public void setReceiverKey( final String receiverKey )
    {
        this.receiverKey = receiverKey;
    }

    public void setSuccess( final Consumer<Integer> success )
    {
        this.success = success;
    }

    public void setError( final Consumer<Integer> error )
    {
        this.error = error;
    }
}
