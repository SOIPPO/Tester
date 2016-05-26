package org.soippo.service;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;
import org.soippo.entity.Group;
import org.soippo.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SerializeService {
    @Resource
    private GroupService groupService;

    public String serializeGroup(JsonSerializer<User> userSerializer, JsonSerializer<Group> groupSerializer) {
        return new GsonBuilder()
                .registerTypeAdapter(User.class, userSerializer)
                .registerTypeAdapter(Group.class, groupSerializer)
                .create()
                .toJson(groupService.groupList());
    }
}
