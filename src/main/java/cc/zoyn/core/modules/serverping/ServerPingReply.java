package cc.zoyn.core.modules.serverping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Zoyn
 * @since 2017-12-22
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ServerPingReply {

    private Version version;
    private Players players;
    private Motd motd;
    private Favicon favicon;
    private String originalJson;

    @AllArgsConstructor
    public static class Version {
        @Getter
        private String name;
        @Getter
        private int protocol;
    }

    @AllArgsConstructor
    public static class Players {
        @Getter
        private int maxPlayer;
        @Getter
        private int onlinePlayer;
    }

    @AllArgsConstructor
    public static class Motd {
        @Getter
        private String text;
    }

    @AllArgsConstructor
    public static class Favicon {
        @Getter
        private String data;
    }

}
