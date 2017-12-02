package cc.zoyn.core.modules.advancement;

/**
 * 触发器枚举
 *
 * @author Zoyn
 */
public enum TriggerEnum {
    /**
     * 玩家繁殖两个动物时触发
     */
    BRED_ANIMALS,
    /**
     * 玩家从酿造台中拿出一瓶药水时触发
     */
    BREWED_POTION,
    /**
     * 玩家在两个维度间旅行时触发
     */
    CHANGED_DIMENSION,
    /**
     * 玩家更改信标结构时触发
     */
    CONSTRUCT_BEACON,
    /**
     * 玩家损耗了物品时触发
     */
    CONSUME_ITEM,
    /**
     * 玩家治愈了僵尸村民时触发
     */
    CURED_ZOMBIE_VILLAGER,
    /**
     * 玩家通过附魔台附魔物品时触发(使用铁砧或命令时不触发)
     */
    ENCHANTED_ITEM,
    /**
     * 玩家进入方块时触发。每刻都会检查成功的匹配，仅在进度内被函数奖励剥夺可用条件
     */
    ENTER_BLOCK,
    /**
     * 实体伤害玩家时触发
     */
    ENTITY_HURT_PLAYER,
    /**
     * 实体杀死玩家时触发
     */
    ENTITY_KILLED_PLAYER,
    /**
     * 仅可使用命令触发
     */
    IMPOSSIBLE,
    /**
     * 玩家物品栏变化时触发
     */
    INVENTORY_CHANGED,
    /**
     * 物品栏中任何物品以任何形式损害时触发
     */
    ITEM_DURABILITY_CHANGED,
    /**
     * 玩家获得漂浮状态效果时触发
     */
    LEVITATION,
    /**
     * 每个游戏刻(每秒20次)检查玩家的位置
     */
    LOCATION,
    /**
     * 玩家放置方块时触发
     */
    PLACED_BLOCK,
    /**
     * 玩家伤害实体(包括自己时触发)
     */
    PLAYER_HURT_ENTITY,
    /**
     * 玩家杀死实体时触发
     */
    PLAYER_KILLED_ENTITY,
    /**
     * 玩家解锁配方时触发(例如用知识之书)
     */
    RECIPE_UNLOCKED,
    /**
     * 玩家上床睡觉时触发
     */
    SLEPT_IN_BED,
    /**
     * 玩家召唤了实体时触发。例如铁傀儡(南瓜和铁块)、雪傀儡(南瓜和雪块)、末影龙(末影水晶)和凋灵(灵魂沙和凋灵骷髅头颅)。使用发射器来放置凋灵骷髅头颅或南瓜也能激活此触发器。刷怪蛋、命令和刷怪箱不会激活此触发器
     */
    SUMMONED_ENTITY,
    /**
     * 玩家驯服动物时触发
     */
    TAME_ANIMAL,
    /**
     * 每个游戏刻触发(每秒20次)
     */
    TICK,
    /**
     * 玩家使用末影之眼(在有要塞生成的世界)时触发
     */
    USED_ENDER_EYE,
    /**
     * 玩家与村民交易时触发
     */
    VILLAGER_TRADE
}
