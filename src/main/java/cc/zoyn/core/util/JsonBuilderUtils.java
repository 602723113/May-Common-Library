package cc.zoyn.core.util;

/**
 * Json构建
 *
 * @author Zoyn
 */
public class JsonBuilderUtils {

    private static final String[] REPLACEMENT_CHARS;
    private StringBuilder json;

    static {
        REPLACEMENT_CHARS = new String[128];
        for (int i = 0; i <= 0x1f; i++) {
            REPLACEMENT_CHARS[i] = String.format("\\u%04x", i);
        }
        REPLACEMENT_CHARS['"'] = "\\\"";
        REPLACEMENT_CHARS['\\'] = "\\\\";
        REPLACEMENT_CHARS['\t'] = "\\t";
        REPLACEMENT_CHARS['\b'] = "\\b";
        REPLACEMENT_CHARS['\n'] = "\\n";
        REPLACEMENT_CHARS['\r'] = "\\r";
        REPLACEMENT_CHARS['\f'] = "\\f";
    }

    public JsonBuilderUtils() {
        json = new StringBuilder();
    }

    public JsonBuilderUtils(String string) {
        this();
        append(string);
    }

    public void append(String value) {
        int last = 0;
        int length = value.length();
        for (int i = 0; i < length; i++) {
            char c = value.charAt(i);
            String replacement;
            if (c < 128) {
                replacement = REPLACEMENT_CHARS[c];
                if (replacement == null) {
                    continue;
                }
            } else if (c == '\u2028') {
                replacement = "\\u2028";
            } else if (c == '\u2029') {
                replacement = "\\u2029";
            } else {
                continue;
            }
            if (last < i) {
                json.append(value, last, i);
            }
            json.append(replacement);
            last = i + 1;
        }
        if (last < length) {
            json.append(value, last, length);
        }
    }

    public void deleteLastChar() {
        json.deleteCharAt(json.length() - 1);
    }

    public boolean isEmpty() {
        return json.length() == 0;
    }

    public int length() {
        return json.length();
    }

    @Override
    public String toString() {
        return json.toString();
    }
}
