package com.metarash.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDto {
    private String email;
    private int finishedCount;
    private int unfinishedCount;
    private List<TaskDto> finishedTasks;
    private List<TaskDto> unfinishedTasks;
}