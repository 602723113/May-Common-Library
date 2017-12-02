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
            System.arraycopy(message, 0, message, 0, 3);
        }
        message = texts.toArray(new String[]{});
    }

    public Sign(String... texts) {
        if (texts.length > 4) {
            System.arraycopy(texts, 0, message, 0, 4);
        }
        message = texts;
    }

    public Sign setTexts(String... texts) {
        if (texts.length > 4) {
            System.arraycopy(texts, 0, message, 0, 4);
        }
        message = texts;
        return this;
    }

    public boolean isEmpty() {
        return message.length == 0;
    }

    public String[] getTexts() {
        return message;
    }
}
