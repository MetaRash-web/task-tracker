package com.metarash.backend.specification;

import com.metarash.backend.model.dto.TaskFilter;
import com.metarash.backend.model.entity.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

@Slf4j
public class TaskSpecifications {

    public static Specification<Task> hasField(String fieldName, Object value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(fieldName), value);
    }

    public static Specification<Task> hasUsername(String username) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("user").get("username"), username);
    }

    public static Specification<Task> hasPriority(String priority) {
        return hasField("priority", priority);
    }

    public static Specification<Task> hasStatus(String status) {
        return hasField("status", status);
    }

    public static Specification<Task> build(TaskFilter filter, String username) {
        Specification<Task> spec = Specification.where(hasUsername(username));

        Optional.ofNullable(filter.getPriority())
                .ifPresent(priority -> spec.and(hasPriority(priority)));

        Optional.ofNullable(filter.getStatus())
                .ifPresent(status -> spec.and(hasStatus(status)));

        return spec;
    }
}
