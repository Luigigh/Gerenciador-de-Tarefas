package br.com.luigifalconi.taskmanager.specification;

import br.com.luigifalconi.taskmanager.dto.filter.TaskFilterDTO;
import br.com.luigifalconi.taskmanager.entity.Task;
import br.com.luigifalconi.taskmanager.enums.PriorityTask;
import br.com.luigifalconi.taskmanager.enums.StatusTask;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class TaskSpecification {

    private TaskSpecification() {
    }

    /*
     * Combina todos os filtros recebidos em uma única Specification.
     */
    public static Specification<Task> withFilters(TaskFilterDTO filter) {

        Specification<Task> spec =
                (root, query, builder) -> builder.conjunction();

        if (filter == null) {
            return spec;
        }

        return spec
                .and(titleContains(filter.getTitle()))
                .and(hasStatus(filter.getStatus()))
                .and(hasPriority(filter.getPriority()))
                .and(hasResponsible(filter.getResponsibleId()))
                .and(belongsToProject(filter.getProjectId()))
                .and(dueAfter(filter.getDueAfter()))
                .and(dueBefore(filter.getDueBefore()));
    }

    public static Specification<Task> titleContains(String title) {

        if (title == null || title.isBlank()) {
            return null;
        }

        String pattern = "%" + title.toLowerCase().trim() + "%";

        return (root, query, builder) -> builder.like(
                builder.lower(root.get("title")),
                pattern
        );
    }

    public static Specification<Task> hasStatus(StatusTask status) {

        if (status == null) {
            return null;
        }

        return (root, query, builder) -> builder.equal(
                root.get("status"),
                status
        );
    }

    public static Specification<Task> hasPriority(PriorityTask priority) {

        if (priority == null) {
            return null;
        }

        return (root, query, builder) -> builder.equal(
                root.get("priority"),
                priority
        );
    }

    public static Specification<Task> hasResponsible(Long responsibleId) {

        if (responsibleId == null) {
            return null;
        }

        return (root, query, builder) -> builder.equal(
                root.get("responsible").get("idUser"),
                responsibleId
        );
    }

    public static Specification<Task> belongsToProject(Long projectId) {

        if (projectId == null) {
            return null;
        }

        return (root, query, builder) -> builder.equal(
                root.get("project").get("idProject"),
                projectId
        );
    }

    public static Specification<Task> dueAfter(LocalDate date) {

        if (date == null) {
            return null;
        }

        return (root, query, builder) ->
                builder.greaterThanOrEqualTo(
                        root.get("expectedFinalDate"),
                        date
                );
    }

    public static Specification<Task> dueBefore(LocalDate date) {

        if (date == null) {
            return null;
        }

        return (root, query, builder) ->
                builder.lessThanOrEqualTo(
                        root.get("expectedFinalDate"),
                        date
                );
    }

}