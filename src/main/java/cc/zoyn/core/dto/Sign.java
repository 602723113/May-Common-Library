package cc.zoyn.core.dto;

import java.util.List;

/**
 * Sign - 数据传输模型
 *
 * @author Zoyn
 */
public class Sign {

    private String[] message = new String[4];

    public Sign(List<String> texts) {
        if (texts.size() > 4) {
            for (int i = 0; i < 3; i++) {
                String string = message[i];
                message[i] = string;
            }
        }
        message = texts.toArray(new String[]{});
    }

    public Sign(String... texts) {
        if (texts.length > 4) {
            for (int i = 0; i <= 3; i++) {
                String string = texts[i];
                message[i] = string;
            }
        }
        message = texts;
    }

    public Sign setTexts(String... texts) {
        if (texts.length > 4) {
            for (int i = 0; i <= 3; i++) {
                String string = texts[i];
                message[i] = string;
            }
        }
        message = texts;
        return this;
    }

    public boolean isEmpty() {
        if (message.length == 0) {
            return true;
        }
        return false;
    }

    public String[] getTexts() {
        return message;
    }
}
