package cc.zoyn.core;

import cc.zoyn.core.modules.serverping.ServerPing;
import cc.zoyn.core.modules.serverping.ServerPingReply;
import org.junit.Test;

/**
 * @author Zoyn
 * @since 2017-12-22
 */
public class ServerPingTest {

    @Test
    public void testServerPing() {
        ServerPing serverPing = new ServerPing("play.i5mc.com");
        serverPing.pingServer();
        ServerPingReply reply = serverPing.getReply();
//        System.out.println("Motd: " + reply.getMotd().getText());
//        System.out.println("===========");
//        System.out.println(reply.getOriginalJson());
    }

}
