package br.com.luigifalconi.taskmanager.mapper;

import org.springframework.stereotype.Component;

import br.com.luigifalconi.taskmanager.dto.request.ProjectRequestDTO;
import br.com.luigifalconi.taskmanager.dto.response.ProjectResponseDTO;
import br.com.luigifalconi.taskmanager.entity.Project;

@Component
public class ProjectMapper {

    public Project toEntity(ProjectRequestDTO dto) {

        Project project = new Project();

        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        project.setBudget(dto.getBudget());
        project.setStartDate(dto.getStartDate());
        project.setExpectedFinalDate(
                dto.getExpectedFinalDate()
        );
        project.setEndDate(dto.getEndDate());
        project.setStatus(dto.getStatus());

        return project;
    }

    public ProjectResponseDTO toResponseDTO(Project project) {

        return ProjectResponseDTO.builder()
                .idProject(project.getIdProject())
                .name(project.getName())
                .description(project.getDescription())
                .budget(project.getBudget())
                .createdAt(project.getCreatedAt())
                .startDate(project.getStartDate())
                .expectedFinalDate(project.getExpectedFinalDate())
                .endDate(project.getEndDate())
                .status(project.getStatus())
                .build();
    }
}