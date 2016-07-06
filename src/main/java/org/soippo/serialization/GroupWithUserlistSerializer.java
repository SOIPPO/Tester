package org.soippo.serialization;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.soippo.entity.Group;
import org.soippo.entity.User;

import java.lang.reflect.Type;
import java.util.List;

public class GroupWithUserlistSerializer implements JsonSerializer<Group>{
    @Override
    public JsonElement serialize(Group src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("id", new JsonPrimitive(src.getId()));
        result.add("name", new JsonPrimitive(src.getName()));
        result.add("users", new GsonBuilder()
            .registerTypeAdapter(User.class, new UserSerializer())
            .create()
            .toJsonTree(src.getUsers(), new TypeToken<List<User>>() {}.getType())
            .getAsJsonArray());
        return result;
    }
}
