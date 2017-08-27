package me.may.core.advancement;

/**
 * 图标边框枚举
 *
 * @author May_Speed
 */
public enum FrameEnum {
    // TASK(默认)
    // GOAL为更圆的边框标题,其用于完整信标进度
    // CHALLENGE,其用于杀死所有种类生物的进度
    TASK(),
    GOAL(),
    CHALLENGE();

    private FrameEnum() {
    }
}
