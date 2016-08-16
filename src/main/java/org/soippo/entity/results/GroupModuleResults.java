package org.soippo.entity.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import org.soippo.utils.View;

import java.util.List;

public class GroupModuleResults {
    @JsonProperty("id")
    @JsonView(View.Simplified.class)
    private Long groupId;
    @JsonProperty("users")
    @JsonView(View.Simplified.class)
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
