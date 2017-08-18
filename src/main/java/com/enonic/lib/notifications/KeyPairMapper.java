package com.enonic.lib.notifications;

import com.google.common.io.BaseEncoding;
import com.google.common.io.ByteSource;

import com.enonic.xp.script.serializer.MapGenerator;
import com.enonic.xp.script.serializer.MapSerializable;

public final class KeyPairMapper
    implements MapSerializable
{

    private final byte[] privateKey;

    private final byte[] publicKey;

    KeyPairMapper( final byte[] privateKey, final byte[] publicKey )
    {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    @Override
    public void serialize( final MapGenerator gen )
    {
        gen.value( "privateKeyBytes", ByteSource.wrap( privateKey ) );
        gen.value( "publicKeyBytes", ByteSource.wrap( publicKey ) );

        gen.value( "privateKey", BaseEncoding.base64Url().omitPadding().encode( privateKey ) );
        gen.value( "publicKey", BaseEncoding.base64Url().omitPadding().encode( publicKey ) );
    }

}
