package com.metarash.tasktrackerscheduler.dto;

import com.metarash.tasktrackerscheduler.entity.Task;
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
    private List<Task> finishedTasks;
    private List<Task> unfinishedTasks;
}