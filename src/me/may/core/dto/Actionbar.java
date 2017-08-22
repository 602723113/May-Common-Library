package me.may.core.dto;

/**
 * Actionbar - 数据模型
 *
 * @author May_Speed
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
