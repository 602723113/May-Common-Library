package me.may.core.builder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.may.core.advancement.BackgroundEnum;
import me.may.core.advancement.FrameEnum;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

/**
 * 进度系统 - 建造模式
 * 仅1.12 可用
 *
 * @author May_Speed
 */
public class AdvancementBuilder {

    /**
     * 内部ID
     */
    private NamespacedKey advancementId;
    /**
     * 父级
     */
    private String parent;
    /**
     * 进度标题
     */
    private TextComponent title;
    /**
     * 进度描述
     */
    private TextComponent description;
    /**
     * 进度图标
     */
    private String icon;
    /**
     * 进度图标损坏值
     */
    private int data = 0;
    /**
     * 背景方块材质
     */
    private BackgroundEnum background = BackgroundEnum.STONE;
    /**
     * 图标边框
     */
    private FrameEnum frame;
    /**
     * 触发器
     */
    private CriteriaBuilder criteria;

    // 是否直到完成进度前在进度屏幕隐藏进度.对根进度无效,但会影响其子进度.当父进度值为true后便无法覆盖.默认为true
    private boolean hide = true;
    // 是否在完成此进度后显示提示信息.默认为true
    private boolean toast = true;
    // 是否在完成此进度后广播此信息.默认为false
    private boolean announce = false;

    public AdvancementBuilder setAdvancementId(NamespacedKey advancementId) {
        this.advancementId = advancementId;
        return this;
    }

    public AdvancementBuilder setTitle(TextComponent title) {
        this.title = title;
        return this;
    }

    public AdvancementBuilder setDescription(TextComponent description) {
        this.description = description;
        return this;
    }

    public AdvancementBuilder setTitle(String title) {
        this.title = new TextComponent(title);
        return this;
    }

    public AdvancementBuilder setDescription(String description) {
        this.description = new TextComponent(description);
        return this;
    }

    public AdvancementBuilder setIcon(Material material) {
        StringBuilder builder = new StringBuilder("minecraft:");
        builder.append(material.name().toLowerCase());
        this.icon = builder.toString();
        return this;
    }

    public AdvancementBuilder setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public AdvancementBuilder setData(int data) {
        this.data = data;
        return this;
    }

    public AdvancementBuilder setBackground(BackgroundEnum background) {
        this.background = background;
        return this;
    }

    public AdvancementBuilder setFrame(FrameEnum frame) {
        this.frame = frame;
        return this;
    }

    public AdvancementBuilder setHide(boolean hide) {
        this.hide = hide;
        return this;
    }

    public AdvancementBuilder setToast(boolean toast) {
        this.toast = toast;
        return this;
    }

    public AdvancementBuilder setAnnounce(boolean announce) {
        this.announce = announce;
        return this;
    }

    public AdvancementBuilder setCriteria(CriteriaBuilder criteria) {
        this.criteria = criteria;
        return this;
    }

    public AdvancementBuilder setParent(String parent) {
        this.parent = parent;
        return this;
    }

    public AdvancementBuilder setParent(NamespacedKey key) {
        this.parent = key.toString();
        return this;
    }

    public NamespacedKey getAdvancementId() {
        return advancementId;
    }

    public String getParent() {
        return parent;
    }

    public TextComponent getTitle() {
        return title;
    }

    public TextComponent getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    public int getData() {
        return data;
    }

    public BackgroundEnum getBackground() {
        return background;
    }

    public FrameEnum getFrame() {
        return frame;
    }

    public CriteriaBuilder getCriteria() {
        return criteria;
    }

    public boolean isHide() {
        return hide;
    }

    public boolean isToast() {
        return toast;
    }

    public boolean isAnnounce() {
        return announce;
    }

    private JsonElement textComponentToJson(TextComponent component) {
        return new Gson().fromJson(ComponentSerializer.toString(component), JsonElement.class);
    }

    public String toJsonString() {
        JsonObject root = new JsonObject();

        JsonObject display = new JsonObject();
            JsonObject icon = new JsonObject();
            icon.addProperty("item", this.icon);
            icon.addProperty("data", this.data);
        display.add("icon", icon);
        display.add("title", textComponentToJson(this.title));
        display.addProperty("frame", this.frame.toString().toLowerCase());
        display.addProperty("background", this.background.getName());
        display.add("description", textComponentToJson(this.description));
        display.addProperty("show_toast", this.toast);
        display.addProperty("announce_to_chat", this.announce);
        display.addProperty("hidden", this.hide);

        JsonObject criteria = new JsonObject();
        criteria.add(this.criteria.getName(), this.criteria.toJsonString());
        if (this.parent != null && !this.parent.isEmpty()) {
            display.remove("show_toast");
            display.remove("announce_to_chat");
            display.remove("hidden");
            root.addProperty("parent", getParent());
        }
        root.add("display", display);
        root.add("criteria", criteria);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(root);
    }

    public Advancement build() {
        return Bukkit.getAdvancement(advancementId);
    }

    public AdvancementBuilder add() {
        try {
            Bukkit.getUnsafe().loadAdvancement(advancementId, toJsonString());
            Bukkit.getLogger().info("Successfully registered advancement.");
        } catch (IllegalArgumentException e) {
            Bukkit.getLogger().info("Error registering advancement. It seems to already exist!");
        }
        return this;
    }


    /**
     * 授予
     * @param players
     * @return
     */
    public AdvancementBuilder grant(Player... players) {
        Advancement advancement = build();
        for (Player player : players) {
            if (!player.getAdvancementProgress(advancement).isDone()) {
                Collection<String> remainingCriteria = player.getAdvancementProgress(advancement).getRemainingCriteria();
                Bukkit.getLogger().info(remainingCriteria.toString());
                for (String remainingCriterion : remainingCriteria){
                    player.getAdvancementProgress(build()).awardCriteria(remainingCriterion);
                }
            }
        }
        return this;
    }

    /**
     * 撤销
     * @param players
     * @return
     */
    public AdvancementBuilder revoke(Player... players) {
        Advancement advancement = build();
        for (Player player : players) {
            if (player.getAdvancementProgress(advancement).isDone()) {
                Collection<String> awardedCriteria = player.getAdvancementProgress(advancement).getAwardedCriteria();
                Bukkit.getLogger().info(awardedCriteria.toString());
                for (String awardedCriterion : awardedCriteria) {
                    player.getAdvancementProgress(build()).revokeCriteria(awardedCriterion);
                }
            }
        }
        return this;
    }

    public void saveToWorldData(World world) {
        //File file = new File(world.getWorldFolder(), "data\\advancements\\" + advancementId.getNamespace() + "\\" + advancementId.getKey() + ".json");
        File file = new File(world.getWorldFolder(), "data" + File.separator + "advancements" + File.separator + advancementId.getNamespace() + File.separator + advancementId.getKey() + ".json");
        File parentFile = file.getParentFile();

        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("创建进度json文件时出现了错误");
            }
        }

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(toJsonString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("写入进度json时出现了错误");
        }
    }
}
