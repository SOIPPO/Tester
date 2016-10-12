package org.soippo.entity.results.department;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import org.soippo.entity.UserResults;
import org.soippo.utils.View;

import java.util.HashMap;
import java.util.Map;


public class DepartmentResults {
    @JsonProperty("modules")
    @JsonView(View.Simplified.class)
    private Map<Long, DepartmentModuleResult> modules = new HashMap<>();

    public DepartmentResults addUserResult(UserResults result) {
        Long moduleId = result.getQuestion().getModuleId();
        modules.putIfAbsent(moduleId, new DepartmentModuleResult());

        modules.get(moduleId).addUserResult(result);
        return this;
    }

    public Map<Long, DepartmentModuleResult> getModules() {
        return modules;
    }

    public DepartmentResults setModules(Map<Long, DepartmentModuleResult> modules) {
        this.modules = modules;
        return this;
    }
}
