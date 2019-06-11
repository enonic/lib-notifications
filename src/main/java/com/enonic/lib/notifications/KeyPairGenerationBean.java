package com.enonic.lib.notifications;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.interfaces.ECPrivateKey;
import org.bouncycastle.jce.interfaces.ECPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;

import nl.martijndwars.webpush.Utils;

public final class KeyPairGenerationBean
{
    static
    {
        if ( Security.getProvider( BouncyCastleProvider.PROVIDER_NAME ) == null )
        {
            Security.addProvider( new BouncyCastleProvider() );
        }
    }

    public KeyPairMapper generateKeyPair()
        throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException, IOException
    {
        final KeyPair keyPair = generateECKeyPair();

        final byte[] publicKey = Utils.encode( (ECPublicKey) keyPair.getPublic() );
        final byte[] privateKey = Utils.encode( (ECPrivateKey) keyPair.getPrivate() );

        return new KeyPairMapper( privateKey, publicKey );
    }

    private KeyPair generateECKeyPair()
        throws InvalidAlgorithmParameterException, NoSuchProviderException, NoSuchAlgorithmException
    {
        final ECNamedCurveParameterSpec parameterSpec = ECNamedCurveTable.getParameterSpec( "prime256v1" );

        final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance( "ECDH", "BC" );
        keyPairGenerator.initialize( parameterSpec );

        return keyPairGenerator.generateKeyPair();
    }

}
