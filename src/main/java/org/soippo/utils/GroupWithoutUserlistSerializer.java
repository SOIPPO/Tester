package org.soippo.utils;

import com.google.gson.*;
import org.soippo.entity.Group;

import java.lang.reflect.Type;

public class GroupWithoutUserlistSerializer implements JsonSerializer<Group> {
    @Override
    public JsonElement serialize(Group src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("id", new JsonPrimitive(src.getId()));
        result.add("name", new JsonPrimitive(src.getName()));
        return result;
    }
}
