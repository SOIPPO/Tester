package org.soippo.utils;

import com.google.gson.*;
import org.soippo.entity.User;

import java.lang.reflect.Type;

public class UserSerializer implements JsonSerializer<User>{
    @Override
    public JsonElement serialize(User src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("id", new JsonPrimitive(src.getId()));
        result.add("firstName", new JsonPrimitive(src.getFirstName()));
        result.add("lastName", new JsonPrimitive(src.getSecondName()));
        result.add("middleName", new JsonPrimitive(src.getMiddleName()));
        result.add("groupId", new JsonPrimitive(src.getGroup().getId()));
        result.add("groupName", new JsonPrimitive(src.getGroup().getName()));
        result.add("email", new JsonPrimitive(src.getEmail()));
        result.add("password", new JsonPrimitive(src.getPasswordHash()));
        result.add("role", new JsonPrimitive(src.getRole().toString()));
        return result;
    }
}
