package br.com.luigifalconi.taskmanager.repository;

import br.com.luigifalconi.taskmanager.entity.Task;
import br.com.luigifalconi.taskmanager.enums.StatusTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(StatusTask status);

}