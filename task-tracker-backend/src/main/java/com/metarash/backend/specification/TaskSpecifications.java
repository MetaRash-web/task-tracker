package com.metarash.backend.specification;

import com.metarash.backend.dto.TaskFilter;
import com.metarash.backend.entity.Task;
import org.springframework.data.jpa.domain.Specification;

public class TaskSpecifications {

    public static Specification<Task> hasUsername(String username) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("user").get("username"), username);
    }

    public static Specification<Task> hasPriority(String priority) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("priority"), priority);
    }

    public static Specification<Task> hasStatus(String status) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<Task> build(TaskFilter filter, String username) {
        Specification<Task> spec = Specification.where(hasUsername(username));

        if (filter.getPriority() != null) {
            spec = spec.and(hasPriority(filter.getPriority()));
        }
        if (filter.getStatus() != null) {
            spec = spec.and(hasStatus(filter.getStatus()));
        }

        return spec;
    }
}
