package me.may.core.builder;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import me.may.core.advancement.TriggerEnum;

import java.util.List;

/**
 * 条件触发 - 建造模式
 *
 * @author May_Speed
 */
public class CriteriaBuilder {

    private String name;
    private TriggerEnum type;
    private List<ConditionBuilder> conditionList;

    public CriteriaBuilder(String name, TriggerEnum type) {
        this.name = name;
        this.type = type;
        this.conditionList = Lists.newArrayList();
    }

    public CriteriaBuilder(String name, TriggerEnum type, List<ConditionBuilder> conditionList) {
        this.name = name;
        this.type = type;
        this.conditionList = conditionList;
    }

    public String getName() {
        return name;
    }

    public CriteriaBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public TriggerEnum getType() {
        return type;
    }

    public CriteriaBuilder setType(TriggerEnum type) {
        this.type = type;
        return this;
    }

    public List<ConditionBuilder> getConditionList() {
        return conditionList;
    }

    public CriteriaBuilder addCondition(ConditionBuilder condition) {
        this.conditionList.add(condition);
        return this;
    }

    public CriteriaBuilder removeCondition(ConditionBuilder condition) {
        this.conditionList.remove(condition);
        return this;
    }

    public CriteriaBuilder clearCondition() {
        return this;
    }

    /*
     * toJsonString之后看起来会是这样的
     * {
     *   "trigger": "minecraft:type",
     *   "conditions": {
     *         conditions1...
     *    }
     * }
     */
    public JsonObject toJsonString() {
        JsonObject criteria = new JsonObject();

        criteria.addProperty("trigger", "minecraft:" + type.toString().toLowerCase());
        JsonObject conditions = new JsonObject();

        for (int i = 0; i < conditionList.size(); i++) {
            ConditionBuilder condition = conditionList.get(i);
            conditions.add(condition.getName(), condition.getJsonData());
        }
        criteria.add("conditions", conditions);
        return criteria;
    }
}
