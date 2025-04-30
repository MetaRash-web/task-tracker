package com.metarash.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TaskEvent {
    TaskEventType eventType;
    Long taskId;
    LocalDateTime dueDate;
}