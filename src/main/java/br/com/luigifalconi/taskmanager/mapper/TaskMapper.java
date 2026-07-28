package br.com.luigifalconi.taskmanager.mapper;

import org.springframework.stereotype.Component;

import br.com.luigifalconi.taskmanager.dto.request.TaskRequestDTO;
import br.com.luigifalconi.taskmanager.dto.response.TaskResponseDTO;
import br.com.luigifalconi.taskmanager.entity.Project;
import br.com.luigifalconi.taskmanager.entity.Task;
import br.com.luigifalconi.taskmanager.entity.User;
import br.com.luigifalconi.taskmanager.repository.ProjectRepository;
import br.com.luigifalconi.taskmanager.repository.UserRepository;

@Component
public class TaskMapper {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public TaskMapper(ProjectRepository projectRepository,
                      UserRepository userRepository) {

        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public Task toEntity(TaskRequestDTO dto) {

        Project project = projectRepository
                .findById(dto.getProjectId())
                .orElseThrow(() ->
                        new RuntimeException("Project not found"));

        User responsible = userRepository
                .findById(dto.getResponsibleId())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return Task.builder()

                .title(dto.getTitle())
                .description(dto.getDescription())
                .comment(dto.getComment())

                .startDate(dto.getStartDate())
                .expectedFinalDate(dto.getExpectedFinalDate())
                .endDate(dto.getEndDate())

                .status(dto.getStatus())
                .priority(dto.getPriority())

                .project(project)
                .responsible(responsible)

                .build();

    }

    public TaskResponseDTO toResponseDTO(Task task) {

        return TaskResponseDTO.builder()

                .idTask(task.getIdTask())

                .title(task.getTitle())
                .description(task.getDescription())
                .comment(task.getComment())

                .createdAt(task.getCreatedAt())

                .startDate(task.getStartDate())
                .expectedFinalDate(task.getExpectedFinalDate())
                .endDate(task.getEndDate())

                .status(task.getStatus())
                .priority(task.getPriority())

                .projectId(task.getProject().getIdProject())
                .projectName(task.getProject().getName())

                .responsibleId(task.getResponsible().getIdUser())
                .responsibleName(
                        task.getResponsible().getFirstName() +
                        " " +
                        task.getResponsible().getLastName())

                .build();

    }

}