package cc.zoyn.core.util.nms.nbt;

import lombok.Getter;

/**
 * @author Zoyn
 * @since 2017-12-02
 */
@Getter
public enum AttributeType {

    ATTACK_DAMAGE("generic.attackDamage"),
    ATTACK_SPEED("generic.attackSpeed"),
    MAX_HEALTH("generic.maxHealth"),
    MOVEMENT_SPEED("generic.movementSpeed"),
    ARMOR("generic.armor"),
    LUCK("generic.luck");

    private String name;

    AttributeType(String name) {
        this.name = name;
    }


}
