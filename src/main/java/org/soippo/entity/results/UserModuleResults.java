package org.soippo.entity.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.soippo.entity.User;

import java.util.List;

public class UserModuleResults {
    @JsonProperty("user")
    private User user;
    @JsonProperty("moduleResultsList")
    private List<ModuleResults> moduleResultsList;

    public User getUser() {
        return user;
    }

    public UserModuleResults setUser(User user) {
        this.user = user;
        return this;
    }

    public List<ModuleResults> getModuleResultsList() {
        return moduleResultsList;
    }

    public UserModuleResults setModuleResultsList(List<ModuleResults> moduleResultsList) {
        this.moduleResultsList = moduleResultsList;
        return this;
    }

}
