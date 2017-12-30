package cc.zoyn.core.modules.serverping;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @since 2017-12-22
 */
public final class ServerPingUtils {

    static byte PACKET_HANDSHAKE = 0x00, PACKET_STATUSREQUEST = 0x00;
    static int PROTOCOL_VERSION = 4;
    static int STATUS_HANDSHAKE = 1;
    private static ScriptEngine engine;

    /*
     * MOTD解析脚本
     *
     * @author 喵♂呜
     * @editor: Zoyn
     */
    static {
        ScriptEngineManager manager = new ScriptEngineManager();
        engine = manager.getEngineByName("js");
        try {
            engine.eval("function parse(json) {\n" +
                    "    var color = [];\n" +
                    "    color['black'] = '0';\n" +
                    "    color['dark_blue'] = '1';\n" +
                    "    color['dark_green'] = '2';\n" +
                    "    color['dark_aqua'] = '3';\n" +
                    "    color['dark_red'] = '4';\n" +
                    "    color['dark_purple'] = '5';\n" +
                    "    color['gold'] = '6';\n" +
                    "    color['gray'] = '7';\n" +
                    "    color['dark_gray'] = '8';\n" +
                    "    color['blue'] = '9';\n" +
                    "    color['green'] = 'a';\n" +
                    "    color['aqua'] = 'b';\n" +
                    "    color['red'] = 'c';\n" +
                    "    color['light_purple'] = 'd';\n" +
                    "    color['yellow'] = 'e';\n" +
                    "    color['white'] = 'f';\n" +
                    "    var obj = JSON.parse(json);\n" +
                    "    // noinspection JSUnresolvedVariable\n" +
                    "    var motd = obj.description.text;\n" +
                    "    // noinspection JSUnresolvedVariable\n" +
                    "    if (obj.description.extra) {\n" +
                    "        // noinspection JSUnresolvedVariable\n" +
                    "        obj.description.extra.forEach(function (part) {\n" +
                    "            // noinspection JSUnresolvedVariable\n" +
                    "            if (part.obfuscated) {\n" +
                    "                motd += \"§k\";\n" +
                    "            }\n" +
                    "            if (part.bold) {\n" +
                    "                motd += \"§l\";\n" +
                    "            }\n" +
                    "            // noinspection JSUnresolvedVariable\n" +
                    "            if (part.strikethrough) {\n" +
                    "                motd += \"§m\";\n" +
                    "            }\n" +
                    "            // noinspection JSUnresolvedVariable\n" +
                    "            if (part.underline) {\n" +
                    "                motd += \"§n\";\n" +
                    "            }\n" +
                    "            // noinspection JSUnresolvedVariable\n" +
                    "            if (part.italic) {\n" +
                    "                motd += \"§o\";\n" +
                    "            }\n" +
                    "            if (part.reset) {\n" +
                    "                motd += \"§r\";\n" +
                    "            }\n" +
                    "            if (part.color) {\n" +
                    "                motd += '§' + color[part.color];\n" +
                    "            }\n" +
                    "            motd += part.text;\n" +
                    "        })\n" +
                    "    } else if(obj.description) {\n" +
                    "       motd = obj.description\n" +
                    "    }\n" +
                    "    obj.version_name = obj.version.name;\n" +
                    "    obj.version_protocol = obj.version.protocol;\n" +
                    "    obj.players_max = obj.players.max;\n" +
                    "    obj.players_online = obj.players.online;\n" +
                    "    obj.motd_text = motd;\n" +
                    "    obj.favicon_data = obj.favicon;\n" +
                    "    return obj;\n" +
                    "}");
        } catch (ScriptException e) {
            System.out.println("警告! MOTD 解析脚本初始化失败!");
        }
    }

    public static ScriptEngine getEngine() {
        return engine;
    }

    public static String parseMotd(String json) {
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(json);
        if (element.getAsJsonObject().has("description")) {
            JsonElement description = element.getAsJsonObject().get("description");
            if (description.getAsJsonObject().has("extra")) {
                JsonObject extra = description.getAsJsonObject().get("extra").getAsJsonObject();
                System.out.println(extra);
                System.out.println("*******************");
            }
        }
        return "a";
    }

    /**
     * @author thinkofdeath
     * See: https://gist.github.com/thinkofdeath/e975ddee04e9c87faf22
     */
    public static int readVarInt(DataInputStream in) throws IOException {
        int i = 0;
        int j = 0;
        while (true) {
            int k = in.readByte();
            i |= (k & 0x7F) << j++ * 7;
            if (j > 5)
                throw new RuntimeException("VarInt too big");
            if ((k & 0x80) != 128)
                break;
        }
        return i;
    }

    /**
     * @author thinkofdeath
     * See: https://gist.github.com/thinkofdeath/e975ddee04e9c87faf22
     */
    public static void writeVarInt(DataOutputStream out, int paramInt) throws IOException {
        while (true) {
            if ((paramInt & 0xFFFFFF80) == 0) {
                out.writeByte(paramInt);
                return;
            }
            out.writeByte(paramInt & 0x7F | 0x80);
            paramInt >>>= 7;
        }
    }

}
