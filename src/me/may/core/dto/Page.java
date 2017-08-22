package me.may.core.dto;

import me.may.core.minecraft.JsonImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Page - 数据模型
 *
 * @author May_Speed
 */
public class Page {

    private List<JsonImpl> jsonParts = new ArrayList<JsonImpl>();

    /**
     * 构造方法
     *
     * @param text 文本
     */
    public Page(String text) {
        jsonParts.add(new JsonImpl(text));
    }

    /**
     * 修改当前显示文本
     *
     * @param text 文本
     * @return {@link Page}
     */
    public Page setText(String text) {
        latest().text = text;
        return this;
    }

    /**
     * 命令与提示[更方便]
     *
     * @param command 命令
     * @param hover   提示
     * @return {@link Page}
     */
    public Page excuteCommandAndAddHover(String command, String... hover) {
        return excuteCommand(command).addHover(hover);
    }

    /**
     * 执行命令
     *
     * @param command 命令
     * @return {@link Page}
     */
    public Page excuteCommand(String command) {
        return onClick("run_command", command);
    }

    /**
     * 增加悬浮消息
     *
     * @param texts 文本列
     * @return {@link Page}
     */
    public Page addHover(List<String> texts) {
        if (texts.isEmpty()) {
            return this;
        }
        StringBuilder text = new StringBuilder();
        for (String t : texts) {
            text.append(t).append("\n");
        }
        return addHover(text.toString().substring(0, text.length() - 1));
    }

    /**
     * 增加悬浮消息
     *
     * @param text 文本
     * @return {@link Page}
     */
    public Page addHover(String text) {
        return onHover("show_text", text);
    }

    /**
     * 增加悬浮消息
     *
     * @param texts 文本数组
     * @return {@link Page}
     */
    public Page addHover(String... texts) {
        return addHover(Arrays.asList(texts));
    }

    /**
     * 结束上一串消息 开始下一串数据[使用字符串]
     *
     * @param text 新的文本
     * @return {@link Page}
     */
    public Page addAnotherMessage(String text) {
        return addAnotherMessage(new JsonImpl(String.format(text)));
    }

    /**
     * 结束这一页 开始下一页数据[使用一个PageJsonImpl的实例]
     *
     * @param part 下一页内容
     * @return {@link Page}
     */
    private Page addAnotherMessage(JsonImpl part) {
        JsonImpl last = latest();
        if (!last.hasText()) {
            last.text = part.text;
        } else {
            jsonParts.add(part);
        }
        return this;
    }

    /**
     * 获得最后一个操作串
     *
     * @return 最后一个操作的消息串
     */
    private JsonImpl latest() {
        return jsonParts.get(jsonParts.size() - 1);
    }

    /**
     * 添加显示操作
     *
     * @param name 悬浮显示
     * @param data 显示内容
     * @return {@link Page}
     */
    private Page onHover(String name, String data) {
        JsonImpl latest = latest();
        latest.hoverActionName = name;
        latest.hoverActionData = data;
        return this;
    }

    /**
     * 添加点击操作
     *
     * @param name 点击名称
     * @param data 点击操作
     * @return {@link Page}
     */
    private Page onClick(String name, String data) {
        JsonImpl latest = latest();
        latest.clickActionName = name;
        latest.clickActionData = data;
        return this;
    }

    /**
     * 转换成Json串
     *
     * @return Json串
     */
    public String toJsonString() {
        StringBuilder msg = new StringBuilder();
        msg.append("[\"\"");
        for (JsonImpl messagePart : jsonParts) {
            msg.append(",");
            messagePart.writeJson(msg);
        }
        msg.append("]");
        return msg.toString();
    }
}
