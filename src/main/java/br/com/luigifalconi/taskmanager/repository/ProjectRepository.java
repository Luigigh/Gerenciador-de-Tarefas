package br.com.luigifalconi.taskmanager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.luigifalconi.taskmanager.entity.Project;
import br.com.luigifalconi.taskmanager.enums.StatusProject;

public interface ProjectRepository extends JpaRepository<Project, Long>{

    @Query("""
        SELECT p
        FROM Project p
        WHERE p.name = :name
    """)
    Optional<Project> findProjectByName(
            @Param("name") String name
    );

    boolean existsByStatus(StatusProject status);
    
}
