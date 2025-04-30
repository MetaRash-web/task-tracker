package com.metarash.backend.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class TaskFilter {
    private int page = 0;
    private int size = 25;
    private String sort = "createdAt";

    @JsonIgnore
    @Setter(AccessLevel.NONE)
    private String priority;

    private String status;

    public void setPriority(String priority) {
        if (priority != null && !priority.startsWith("u=")) {
            this.priority = priority;
        }
    }
}
