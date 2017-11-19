package cc.zoyn.core.builder;

import cc.zoyn.core.advancement.FrameEnum;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import cc.zoyn.core.advancement.BackgroundEnum;
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
 * @author Zoyn
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
    private int iconData = 0;
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

    /**
     * 是否直到完成进度前在进度屏幕隐藏进度.对根进度无效,但会影响其子进度.当父进度值为true后便无法覆盖.默认为true
     */
    private boolean hide = true;
    /**
     * 是否在完成此进度后显示提示信息.默认为true
     */
    private boolean toast = true;
    /**
     * 是否在完成此进度后广播此信息.默认为false
     */
    private boolean announce = false;

    /**
     * 设置该进度的内部Id
     *
     * @param advancementId 内部Id对象
     * @return {@link AdvancementBuilder}
     */
    public AdvancementBuilder setAdvancementId(NamespacedKey advancementId) {
        this.advancementId = advancementId;
        return this;
    }

    /**
     * 设置该进度的标题
     *
     * @param title 标题
     * @return {@link AdvancementBuilder}
     */
    public AdvancementBuilder setTitle(TextComponent title) {
        this.title = title;
        return this;
    }

    /**
     * 设置该进度的标题
     *
     * @param title 标题
     * @return {@link AdvancementBuilder}
     */
    public AdvancementBuilder setTitle(String title) {
        this.title = new TextComponent(title);
        return this;
    }

    /**
     * 设置该进度的描述
     *
     * @param description 描述
     * @return {@link AdvancementBuilder}
     */
    public AdvancementBuilder setDescription(TextComponent description) {
        this.description = description;
        return this;
    }

    /**
     * 设置该进度的描述
     *
     * @param description 描述
     * @return {@link AdvancementBuilder}
     */
    public AdvancementBuilder setDescription(String description) {
        this.description = new TextComponent(description);
        return this;
    }

    /**
     * 设置该进度所显示的物品
     *
     * @param icon 物品 如 minecraft:stone
     * @return {@link AdvancementBuilder}
     */
    public AdvancementBuilder setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    /**
     * 设置该进度所显示的物品
     *
     * @param material 物品
     * @return {@link AdvancementBuilder}
     */
    public AdvancementBuilder setIcon(Material material) {
        StringBuilder builder = new StringBuilder("minecraft:");
        builder.append(material.name().toLowerCase());
        this.icon = builder.toString();
        return this;
    }

    /**
     * 设置该进度所显示的物品的子Id
     *
     * @param data 子Id
     * @return {@link AdvancementBuilder}
     */
    public AdvancementBuilder setIconData(int data) {
        this.iconData = data;
        return this;
    }

    /**
     * 设置该进度所显示的背景,如果父级已设定则自动忽略
     *
     * @param background 背景
     * @return {@link AdvancementBuilder}
     */
    public AdvancementBuilder setBackground(BackgroundEnum background) {
        this.background = background;
        return this;
    }

    /**
     * 设置该进度所显示的图标框架
     *
     * @param frame 框架
     * @return {@link AdvancementBuilder}
     */
    public AdvancementBuilder setFrame(FrameEnum frame) {
        this.frame = frame;
        return this;
    }

    /**
     * 设置该进度是否直到完成进度前在进度屏幕隐藏进度.对根进度无效,但会影响其子进度.当父进度值为true后便无法覆盖
     *
     * @param hide {@link Boolean}
     * @return {@link AdvancementBuilder}
     */
    public AdvancementBuilder setHide(boolean hide) {
        this.hide = hide;
        return this;
    }

    /**
     * 设置该进度是否在完成后进行提示
     *
     * @param toast {@link Boolean}
     * @return {@link AdvancementBuilder}
     */
    public AdvancementBuilder setToast(boolean toast) {
        this.toast = toast;
        return this;
    }

    /**
     * 设置该进度是否在完成后进行公告
     *
     * @param announce {@link Boolean}
     * @return {@link AdvancementBuilder}
     */
    public AdvancementBuilder setAnnounce(boolean announce) {
        this.announce = announce;
        return this;
    }

    /**
     * 设置该进度的触发器
     *
     * @param criteria {@link CriteriaBuilder}
     * @return {@link AdvancementBuilder}
     */
    public AdvancementBuilder setCriteria(CriteriaBuilder criteria) {
        this.criteria = criteria;
        return this;
    }

    /**
     * 设置该进度的父级进度
     *
     * @param parent namespacedKeyObj.toString
     * @return {@link AdvancementBuilder}
     */
    public AdvancementBuilder setParent(String parent) {
        this.parent = parent;
        return this;
    }

    /**
     * 设置该进度的父级进度
     *
     * @param key 填入namespacedKeyObj
     * @return {@link AdvancementBuilder}
     */
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

    public int getIconData() {
        return iconData;
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

    /**
     * 将此AdvancementBuilder的数据格式化为Json
     * @return
     */
    public String toJsonString() {
        JsonObject root = new JsonObject();

        JsonObject display = new JsonObject();
        JsonObject icon = new JsonObject();
        icon.addProperty("item", this.icon);
        icon.addProperty("data", this.iconData);
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
     * 将部分玩家的该进度设置为已达成
     *
     * @param players
     * @return {@link AdvancementBuilder}
     */
    public AdvancementBuilder grant(Player... players) {
        Advancement advancement = build();
        for (Player player : players) {
            if (!player.getAdvancementProgress(advancement).isDone()) {
                Collection<String> remainingCriteria = player.getAdvancementProgress(advancement).getRemainingCriteria();
                Bukkit.getLogger().info(remainingCriteria.toString());
                for (String remainingCriterion : remainingCriteria) {
                    player.getAdvancementProgress(build()).awardCriteria(remainingCriterion);
                }
            }
        }
        return this;
    }

    /**
     * 撤销部分玩家的该进度
     *
     * @param players 玩家数组
     * @return {@link AdvancementBuilder}
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
