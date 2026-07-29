package br.com.luigifalconi.taskmanager.service;

import br.com.luigifalconi.taskmanager.dto.request.TaskUpdateDTO;
import br.com.luigifalconi.taskmanager.entity.Project;
import br.com.luigifalconi.taskmanager.entity.Task;
import br.com.luigifalconi.taskmanager.entity.User;
import br.com.luigifalconi.taskmanager.repository.ProjectRepository;
import br.com.luigifalconi.taskmanager.repository.TaskRepository;
import br.com.luigifalconi.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository,
                       ProjectRepository projectRepository,
                       UserRepository userRepository) {

        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public Task createTask(Task task) {

        return taskRepository.save(task);

    }

    public List<Task> findAllTasks() {

        return taskRepository.findAll();

    }

    public Task findTaskById(Long id) {

        return taskRepository.findById(id)

                .orElseThrow(() ->
                        new RuntimeException("Task not found"));

    }

    public Task updateTask(Long id, TaskUpdateDTO dto) {

        Task task = findTaskById(id);

        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setComment(dto.getComment());

        task.setStartDate(dto.getStartDate());
        task.setExpectedFinalDate(dto.getExpectedFinalDate());
        task.setEndDate(dto.getEndDate());

        task.setStatus(dto.getStatus());

        Project project = projectRepository.findById(dto.getProjectId())

                .orElseThrow(() ->
                        new RuntimeException("Project not found"));

        User user = userRepository.findById(dto.getResponsibleId())

                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        task.setProject(project);
        task.setResponsible(user);

        return taskRepository.save(task);

    }

    public Task deleteTask(Long id) {

        Task task = findTaskById(id);

        taskRepository.delete(task);

        return task;

    }

}