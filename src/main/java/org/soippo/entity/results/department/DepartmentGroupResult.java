package org.soippo.entity.results.department;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import org.soippo.entity.UserResults;
import org.soippo.utils.View;

import java.util.HashMap;
import java.util.Map;

public class DepartmentGroupResult {
    @JsonProperty("id")
    @JsonView(View.Simplified.class)
    private Long groupId;
    @JsonProperty("name")
    @JsonView(View.Simplified.class)
    private String groupName;
    @JsonProperty("total")
    @JsonView(View.Simplified.class)
    private Long total = 0L;
    @JsonProperty("correct")
    @JsonView(View.Simplified.class)
    private Long correct = 0L;
    @JsonProperty("questions")
    @JsonView(View.Simplified.class)
    private Map<Long, DepartmentQuestionResult> departmentQuestionResultList = new HashMap<>();

    public Long getGroupId() {
        return groupId;
    }

    public DepartmentGroupResult setGroupId(Long groupId) {
        this.groupId = groupId;
        return this;
    }

    public String getGroupName() {
        return groupName;
    }

    public DepartmentGroupResult setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public Long getTotal() {
        return total;
    }

    public DepartmentGroupResult setTotal(Long total) {
        this.total = total;
        return this;
    }

    public Long getCorrect() {
        return correct;
    }

    public DepartmentGroupResult setCorrect(Long correct) {
        this.correct = correct;
        return this;
    }

    public Map<Long, DepartmentQuestionResult> getDepartmentQuestionResultList() {
        return departmentQuestionResultList;
    }

    public DepartmentGroupResult setDepartmentQuestionResultList(Map<Long, DepartmentQuestionResult> departmentQuestionResultList) {
        this.departmentQuestionResultList = departmentQuestionResultList;
        return this;
    }

    public DepartmentGroupResult addUserResult(UserResults result) {
        departmentQuestionResultList.putIfAbsent(result.getQuestionId(), new DepartmentQuestionResult());
        departmentQuestionResultList.get(result.getQuestionId()).addUserResult(result);
        groupId = result.getUser().getGroupId();
        groupName = result.getUser().getGroupName();
        total++;
        correct += result.getIsCorrect() ? 1 : 0;
        return this;
    }
}
