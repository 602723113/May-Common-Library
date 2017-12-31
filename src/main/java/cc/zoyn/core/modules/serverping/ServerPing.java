package cc.zoyn.core.modules.serverping;

import jdk.nashorn.api.scripting.ScriptObjectMirror;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.script.Invocable;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import static cc.zoyn.core.modules.serverping.ServerPingUtils.*;

@Data
@AllArgsConstructor
public class ServerPing {

    private String hostName;
    private int port = 25565;
    private int timeOut = 2000;
    private String chatSet = "UTF-8";
    private ServerPingReply reply;

    public ServerPing(String hostName) {
        this(hostName, 25565);
    }

    public ServerPing(String hostName, int port) {
        this(hostName, port, 2000);
    }

    public ServerPing(String hostName, int port, int timeOut) {
        this.hostName = hostName;
        this.port = port;
        this.timeOut = timeOut;
    }

    public boolean pingServer() {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(hostName, 25565), 8000);
            final DataInputStream in = new DataInputStream(socket.getInputStream());
            final DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            //> Handshake
            ByteArrayOutputStream handshake_bytes = new ByteArrayOutputStream();
            DataOutputStream handshake = new DataOutputStream(handshake_bytes);

            handshake.writeByte(PACKET_HANDSHAKE);
            writeVarInt(handshake, PROTOCOL_VERSION);
            writeVarInt(handshake, this.hostName.length());

            handshake.writeBytes(hostName);
            handshake.writeShort(getPort());
            writeVarInt(handshake, STATUS_HANDSHAKE);

            writeVarInt(out, handshake_bytes.size());
            out.write(handshake_bytes.toByteArray());

            //> Status request
            out.writeByte(0x01); // Size of packet
            out.writeByte(PACKET_STATUSREQUEST);

            //< Status response
            readVarInt(in); // Size
            int id = readVarInt(in);
            int length = readVarInt(in);
            byte[] data = new byte[length];
            in.readFully(data);
            String json = new String(data, this.chatSet);

            // format data
            ScriptObjectMirror mirror = (ScriptObjectMirror) ((Invocable) ServerPingUtils.getEngine()).invokeFunction("parse", json);
            if (mirror == null) {
                return false;
            }
            ServerPingReply reply = new ServerPingReply();

            ServerPingReply.Version version = new ServerPingReply.Version((String) mirror.get("version_name"), (int) mirror.get("version_protocol"));
            ServerPingReply.Players players = new ServerPingReply.Players((int) mirror.get("players_max"), (int) mirror.get("players_online"));
            ServerPingReply.Motd motd = new ServerPingReply.Motd((String) mirror.get("motd_text"));
            ServerPingReply.Favicon favicon = new ServerPingReply.Favicon((String) mirror.get("favicon_data"));

            reply.setVersion(version);
            reply.setPlayers(players);
            reply.setMotd(motd);
            reply.setFavicon(favicon);
            reply.setOriginalJson(json);

            // Close
            handshake.close();
            handshake_bytes.close();
            out.close();
            in.close();

            setReply(reply);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
