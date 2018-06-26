package cc.zoyn.core.serverping;

import lombok.*;

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

    @ToString
    @AllArgsConstructor
    public static class Version {
        @Getter
        private String name;
        @Getter
        private int protocol;
    }

    @ToString
    @AllArgsConstructor
    public static class Players {
        @Getter
        private int maxPlayer;
        @Getter
        private int onlinePlayer;
    }

    @ToString
    @AllArgsConstructor
    public static class Motd {
        @Getter
        private String text;
    }

    @ToString
    @AllArgsConstructor
    public static class Favicon {
        @Getter
        private String data;
    }

}
