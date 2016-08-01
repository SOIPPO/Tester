package org.soippo.serialization;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.soippo.entity.Group;
import org.soippo.entity.User;
import org.soippo.utils.UserRoles;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.util.Optional;

public class UserDeserializer extends JsonDeserializer<User> {
    @Override
    public User deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);

        return new User()
                .setId(Optional.ofNullable(node.get("id")).map(JsonNode::asLong).orElse(null))
                .setFirstName(node.get("firstName").asText())
                .setMiddleName(node.get("middleName").asText())
                .setLastName(node.get("lastName").asText())
                .setEmail(node.get("email").asText())
                .setPasswordHash(new BCryptPasswordEncoder(11).encode(node.get("password").asText()))
                .setRole(UserRoles.valueOf(Optional.ofNullable(node.get("role")).map(JsonNode::asText).orElse("USER")))
                .setGroup(new ObjectMapper().treeToValue(node.get("group"), Group.class));

    }
}
