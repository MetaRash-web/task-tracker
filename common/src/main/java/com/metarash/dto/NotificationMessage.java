package com.metarash.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationMessage {
    private Long taskId;
    private String title;
    private String message;
    private String userEmail;
    private LocalDateTime dueDate;
}
