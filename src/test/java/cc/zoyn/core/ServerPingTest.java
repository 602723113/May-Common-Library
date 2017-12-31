package cc.zoyn.core;

import cc.zoyn.core.modules.serverping.ServerPing;
import cc.zoyn.core.modules.serverping.ServerPingReply;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Zoyn
 * @since 2017-12-22
 */
public class ServerPingTest {

    @Test
    public void testServerPing() {
        ServerPing serverPing = new ServerPing("mc.hypixel.net");
        serverPing.pingServer();
        ServerPingReply reply = serverPing.getReply();
        Assert.assertEquals(47, reply.getVersion().getProtocol());
    }

}
