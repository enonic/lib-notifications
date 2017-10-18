package com.enonic.lib.notifications;

import java.util.concurrent.CountDownLatch;

import com.enonic.xp.testing.ScriptRunnerSupport;

public class NotificationScriptTest
    extends ScriptRunnerSupport
{
    @Override
    public String getScriptTestFile()
    {
        return "/test/notifications-test.js";
    }

    public CountDownLatch newLatch()
    {
        return new CountDownLatch( 1 );

    }
}
