package cc.zoyn.core.dto;

/**
 * Actionbar - 数据模型
 *
 * @author Zoyn
 */
public class Actionbar {

    private String message;

    public Actionbar(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
