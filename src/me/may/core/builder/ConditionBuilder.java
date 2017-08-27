package me.may.core.builder;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * 奖励 - 建造模式
 *
 * @author May_Speed
 */
public class ConditionBuilder {

    private String name;
    private JsonElement jsonData;

    public ConditionBuilder(String name) {
        this.name = name;
    }

    public ConditionBuilder(String name, JsonElement jsonData) {
        this.name = name;
        this.jsonData = jsonData;
    }

    public String getName() {
        return name;
    }

    public ConditionBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public JsonElement getJsonData() {
        return jsonData;
    }

    public ConditionBuilder setJsonData(JsonObject jsonData) {
        this.jsonData = jsonData;
        return this;
    }
}
