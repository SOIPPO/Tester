package org.soippo.entity.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.soippo.entity.Group;

import java.util.List;

public class GroupModuleResults {
    @JsonProperty("id")
    private Long groupId;
    @JsonProperty("users")
    private List<UserModuleResults> userModuleResults;

    public Long getGroupId() {
        return groupId;
    }

    public GroupModuleResults setGroupId(Long groupId) {
        this.groupId = groupId;
        return this;
    }

    public List<UserModuleResults> getUserModuleResults() {
        return userModuleResults;
    }

    public GroupModuleResults setUserModuleResults(List<UserModuleResults> userModuleResults) {
        this.userModuleResults = userModuleResults;
        return this;
    }
}
