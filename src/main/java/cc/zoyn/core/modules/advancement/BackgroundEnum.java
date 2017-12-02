package cc.zoyn.core.modules.advancement;

/**
 * 背景可用材质枚举
 *
 * @author Zoyn
 */
public enum BackgroundEnum {
    ADVENTURE("minecraft:textures/gui/advancements/backgrounds/adventure.png"),
    END("minecraft:textures/gui/advancements/backgrounds/end.png"),
    HUSBANDRY("minecraft:textures/gui/advancements/backgrounds/husbandry.png"),
    NETHER("minecraft:textures/gui/advancements/backgrounds/nether.png"),
    STONE("minecraft:textures/gui/advancements/backgrounds/stone.png");

    private String name;

    BackgroundEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
