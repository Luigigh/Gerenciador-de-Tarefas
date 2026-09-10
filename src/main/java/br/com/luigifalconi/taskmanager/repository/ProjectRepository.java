package br.com.luigifalconi.taskmanager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.luigifalconi.taskmanager.entity.Project;
import br.com.luigifalconi.taskmanager.enums.StatusProject;

public interface ProjectRepository
        extends JpaRepository<Project, Long> {

    Optional<Project> findByName(String name);

    boolean existsByName(String name);

    boolean existsByStatus(StatusProject status);
}