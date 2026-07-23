package br.com.luigifalconi.taskmanager.mapper;

import org.springframework.stereotype.Component;

import br.com.luigifalconi.taskmanager.dto.request.ProjectRequestDTO;
import br.com.luigifalconi.taskmanager.dto.request.ProjectUpdateDTO;
import br.com.luigifalconi.taskmanager.dto.response.ProjectResponseDTO;
import br.com.luigifalconi.taskmanager.entity.Project;

@Component
public class ProjectMapper {

    public Project toEntity(ProjectRequestDTO requestDTO ){

        Project project = new Project();

            project.setName(requestDTO.getName());
            project.setDescription(requestDTO.getDescription());
            project.setBudget(requestDTO.getBudget());
            project.setStartDate(requestDTO.getStartDate());
            project.setExpectedFinalDate(requestDTO.getExpectedFinalDate());
            project.setEndDate(requestDTO.getEndDate());
            project.setStatus(requestDTO.getStatus());


            return project;
    }

    public ProjectResponseDTO toResponseDTO(Project project){

        ProjectResponseDTO projectResDTO = new ProjectResponseDTO();

        projectResDTO.setIdProject(project.getIdProject());
        projectResDTO.setDescripton(project.getDescription());
        projectResDTO.setBudget(project.getBudget());
        projectResDTO.setCreatedAt(project.getCreatedAt());
        projectResDTO.setStartDate(project.getStartDate());  
        projectResDTO.setExpectedFinalDate(project.getExpectedFinalDate());
        projectResDTO.setEndDate(project.getEndDate());
        projectResDTO.setStatus(project.getStatus());

        return projectResDTO;

    }


    public ProjectUpdateDTO toUpdateDTO(Project project){

        ProjectUpdateDTO projectUpdateDTO = new ProjectUpdateDTO();

        projectUpdateDTO.setIdProject(project.getIdProject());
        projectUpdateDTO.setName(project.getName());
        projectUpdateDTO.setDescription(project.getDescription());
        projectUpdateDTO.setBudget(project.getBudget());
        projectUpdateDTO.setStartDate(project.getStartDate());  
        projectUpdateDTO.setExpectedFinalDate(project.getExpectedFinalDate());
        projectUpdateDTO.setEndDate(project.getEndDate());
        projectUpdateDTO.setStatus(project.getStatus());

        return projectUpdateDTO;

    }
    
}
