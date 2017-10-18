package com.enonic.lib.notifications;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import org.apache.http.HttpResponse;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jose4j.lang.JoseException;

import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Utils;

public final class PushBean
{
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

    private Consumer<Integer> success;

    private Consumer<Integer> error;

    public int send()
        throws InterruptedException, GeneralSecurityException, JoseException, ExecutionException, IOException
    {
        return doSend();
    }

    public void sendAsync()
    {
        Executors.newSingleThreadExecutor().submit( this::doSendAsync );
    }

    private int doSend()
        throws InterruptedException, GeneralSecurityException, JoseException, ExecutionException, IOException
    {
        final Notification notification = new Notification( endpoint, receiverKey, auth, payload );

        final PushService pushService = new PushService();
        pushService.setPublicKey( Utils.loadPublicKey( publicKey ) );
        pushService.setPrivateKey( Utils.loadPrivateKey( privateKey ) );

        final HttpResponse httpResponse = pushService.send( notification );
        return httpResponse.getStatusLine().getStatusCode();
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
