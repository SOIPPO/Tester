package org.soippo.entity.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.soippo.entity.Module;

public class ModuleResults {
    @JsonProperty("id")
    private Long moduleId;
    @JsonProperty("totalQuestions")
    private Long totalQuestions;
    @JsonProperty("correctAnswersCount")
    private Long correctAnswersCount;

    public Long getTotalQuestions() {
        return totalQuestions;
    }

    public ModuleResults setTotalQuestions(Long totalQuestions) {
        this.totalQuestions = totalQuestions;
        return this;
    }

    public Long getCorrectAnswersCount() {
        return correctAnswersCount;
    }

    public ModuleResults setCorrectAnswersCount(Long correctAnswersCount) {
        this.correctAnswersCount = correctAnswersCount;
        return this;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public ModuleResults setModuleId(Long moduleId) {
        this.moduleId = moduleId;
        return this;
    }
}
