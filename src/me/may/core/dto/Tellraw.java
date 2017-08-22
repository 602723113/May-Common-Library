package me.may.core.dto;

import com.google.common.collect.Lists;
import me.may.core.minecraft.JsonImpl;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Tellraw - 数据模型
 *
 * @author May_Speed
 */
public class Tellraw implements ConfigurationSerializable {

    private List<JsonImpl> jsonParts = Lists.newArrayList();

    @Override
    public String toString() {
        return "Tellraw [jsonParts=" + jsonParts + "]";
    }

    /**
     * 构造Tellraw
     */
    public Tellraw(String message) {
        jsonParts.add(new JsonImpl(message));
    }

    @SuppressWarnings("unchecked")
    public Tellraw(Map<String, Object> map) {
        this.jsonParts = (List<JsonImpl>) map.get("jsonParts");
    }

    /**
     * 补全命令[点击后将会自动出现在聊天栏]
     *
     * @param command 命令
     * @return {@link Tellraw}
     */
    public Tellraw showCommandInChatBar(String command) {
        return onClick("suggest_command", command);
    }

    /**
     * 命令与提示[更方便]
     *
     * @param command 命令
     * @param hover   提示
     * @return {@link Tellraw}
     */
    public Tellraw excuteCommandAndAddHover(String command, String... hover) {
        return excuteCommand(command).addHover(hover);
    }

    /**
     * 执行命令
     *
     * @param command 命令
     * @return {@link Tellraw}
     */
    public Tellraw excuteCommand(String command) {
        return onClick("run_command", command);
    }

    /**
     * 添加显示操作
     *
     * @param name 悬浮显示
     * @param data 显示内容
     * @return {@link Tellraw}
     */
    private Tellraw onHover(String name, String data) {
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
     * @return {@link Tellraw}
     */
    private Tellraw onClick(String name, String data) {
        JsonImpl latest = latest();
        latest.clickActionName = name;
        latest.clickActionData = data;
        return this;
    }

    /**
     * 修改当前显示文本
     *
     * @param text 文本
     * @return {@link Tellraw}
     */
    public Tellraw setText(String text) {
        latest().text = text;
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
     * 增加悬浮消息
     *
     * @param texts 文本列
     * @return {@link Tellraw}
     */
    public Tellraw addHover(List<String> texts) {
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
     * @return {@link Tellraw}
     */
    public Tellraw addHover(String text) {
        return onHover("show_text", text);
    }

    /**
     * 增加悬浮消息
     *
     * @param texts 文本列
     * @return {@link Tellraw}
     */
    public Tellraw addHover(String... texts) {
        return addHover(Arrays.asList(texts));
    }

    /**
     * 结束上一串消息 开始下一串数据[使用字符串]
     *
     * @param text 新的文本
     * @return {@link Tellraw}
     */
    public Tellraw addAnotherTellraw(String text) {
        return addAnotherTellraw(new JsonImpl(String.format(text)));
    }

    /**
     * 结束上一串消息 开始下一串数据[使用一个JsonImpl的实例]
     *
     * @param part 下一段内容
     * @return {@link Tellraw}
     */
    private Tellraw addAnotherTellraw(JsonImpl part) {
        JsonImpl last = latest();
        if (!last.hasText()) {
            last.text = part.text;
        } else {
            jsonParts.add(part);
        }
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

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("jsonParts", this.jsonParts);
        return map;
    }
}
