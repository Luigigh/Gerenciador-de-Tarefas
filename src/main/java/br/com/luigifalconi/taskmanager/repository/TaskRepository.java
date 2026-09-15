package br.com.luigifalconi.taskmanager.repository;

import br.com.luigifalconi.taskmanager.entity.Task;
import br.com.luigifalconi.taskmanager.enums.StatusTask;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface TaskRepository extends
        JpaRepository<Task, Long>,
        JpaSpecificationExecutor<Task> {

    List<Task> findByStatus(StatusTask status);

    @Override
    @EntityGraph(attributePaths = {"project", "responsible"})
    Page<Task> findAll(Specification<Task> spec, Pageable pageable);

}