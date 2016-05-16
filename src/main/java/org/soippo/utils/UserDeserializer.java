package org.soippo.utils;

import com.google.gson.*;
import org.soippo.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.lang.reflect.Type;

public class UserDeserializer implements JsonDeserializer<User> {
    @Override
    public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        User user = new Gson().fromJson(json, User.class);
        String password = json.getAsJsonObject().get("password").getAsString();
        user.setPasswordHash(new BCryptPasswordEncoder().encode(password));
        user.setRole(UserRoles.USER);
        return user;
    }
}
