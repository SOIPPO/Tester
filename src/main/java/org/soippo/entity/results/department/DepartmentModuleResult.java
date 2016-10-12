package org.soippo.entity.results.department;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import org.soippo.entity.UserResults;
import org.soippo.entity.results.ModuleResults;
import org.soippo.utils.View;

import java.util.HashMap;
import java.util.Map;

public class DepartmentModuleResult extends ModuleResults {
    @JsonProperty("groups")
    @JsonView(View.Simplified.class)
    private Map<Long, DepartmentGroupResult> groupResultList = new HashMap<>();

    public DepartmentModuleResult addUserResult(UserResults result) {
        Long groupId = result.getUser().getGroupId();
        groupResultList.putIfAbsent(groupId, new DepartmentGroupResult());

        groupResultList.get(groupId).addUserResult(result);
        moduleId = result.getModuleId();
        totalQuestions++;
        correctAnswersCount += result.getIsCorrect() ? 1 : 0;
        date = result.getDate();
        return this;
    }

    public Map<Long, DepartmentGroupResult> getGroupResultList() {
        return groupResultList;
    }

    public DepartmentModuleResult setGroupResultList(Map<Long, DepartmentGroupResult> groupResultList) {
        this.groupResultList = groupResultList;
        return this;
    }
}
