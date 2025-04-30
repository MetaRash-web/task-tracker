package com.metarash.emailsender.mapper;

import com.metarash.dto.EmailDto;
import com.metarash.dto.ReportDto;
import com.metarash.dto.TaskDto;

public class EmailMapper {

    public static EmailDto mapAllTasks(ReportDto dto) {
        StringBuilder body = new StringBuilder();
        body.append("📝 Все задачи за день\n");
        body.append("Всего выполненных: ").append(dto.getFinishedCount()).append("\n");
        body.append("Всего невыполненных: ").append(dto.getUnfinishedCount()).append("\n\n");

        body.append("✅ Завершённые задачи:\n");
        if (dto.getFinishedTasks() != null && !dto.getFinishedTasks().isEmpty()) {
            for (TaskDto task : dto.getFinishedTasks()) {
                body.append("- ").append(task.getTitle()).append(": ").append(task.getDescription()).append("\n");
            }
        } else {
            body.append("— Нет завершённых задач.\n");
        }

        body.append("\n❌ Незавершённые задачи:\n");
        if (dto.getUnfinishedTasks() != null && !dto.getUnfinishedTasks().isEmpty()) {
            for (TaskDto task : dto.getUnfinishedTasks()) {
                body.append("- ").append(task.getTitle()).append(": ").append(task.getDescription()).append("\n");
            }
        } else {
            body.append("— Все задачи завершены!\n");
        }

        return new EmailDto(
                dto.getEmail(),
                "Все задачи за день",
                body.toString()
        );
    }

    public static EmailDto mapFinishedTasks(ReportDto dto) {
        StringBuilder body = new StringBuilder();
        body.append("✅ Завершённые задачи: ").append(dto.getFinishedCount()).append("\n\n");

        if (dto.getFinishedTasks() != null && !dto.getFinishedTasks().isEmpty()) {
            for (TaskDto task : dto.getFinishedTasks()) {
                body.append("- ").append(task.getTitle()).append(": ").append(task.getDescription()).append("\n");
            }
        } else {
            body.append("— Сегодня нет завершённых задач.\n");
        }

        return new EmailDto(
                dto.getEmail(),
                "Завершённые задачи за день",
                body.toString()
        );
    }

    public static EmailDto mapUnfinishedTasks(ReportDto dto) {
        StringBuilder body = new StringBuilder();
        body.append("❌ Незавершённые задачи: ").append(dto.getUnfinishedCount()).append("\n\n");

        if (dto.getUnfinishedTasks() != null && !dto.getUnfinishedTasks().isEmpty()) {
            for (TaskDto task : dto.getUnfinishedTasks()) {
                body.append("- ").append(task.getTitle()).append(": ").append(task.getDescription()).append("\n");
            }
        } else {
            body.append("— Отлично! Нет невыполненных задач.\n");
        }

        return new EmailDto(
                dto.getEmail(),
                "Незавершённые задачи за день",
                body.toString()
        );
    }

    private EmailMapper() {}
}

