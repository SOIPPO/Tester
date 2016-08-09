package org.soippo.entity.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import org.soippo.utils.View;

import java.sql.Date;

public class ModuleResults {
    @JsonProperty("id")
    @JsonView(View.Simplified.class)
    private Long moduleId;
    @JsonProperty("totalQuestions")
    @JsonView(View.Simplified.class)
    private Long totalQuestions;
    @JsonProperty("correctAnswersCount")
    @JsonView(View.Simplified.class)
    private Long correctAnswersCount;
    @JsonProperty("date")
    @JsonView(View.Simplified.class)
    private Date date;

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

    public Date getDate() {
        return date;
    }

    public ModuleResults setDate(Date date) {
        this.date = date;
        return this;
    }
}
