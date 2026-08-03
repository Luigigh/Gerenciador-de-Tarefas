package br.com.luigifalconi.taskmanager.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.luigifalconi.taskmanager.dto.request.ProjectRequestDTO;
import br.com.luigifalconi.taskmanager.dto.request.ProjectUpdateDTO;
import br.com.luigifalconi.taskmanager.dto.response.ProjectResponseDTO;
import br.com.luigifalconi.taskmanager.entity.Project;
import br.com.luigifalconi.taskmanager.mapper.ProjectMapper;
import br.com.luigifalconi.taskmanager.service.ProjectService;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    private final ProjectMapper projectMapper;

    public ProjectController(
            ProjectService projectService,
            ProjectMapper projectMapper) {

        this.projectService = projectService;
        this.projectMapper = projectMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN, MANAGER')")
    public ProjectResponseDTO createProject(
            @RequestBody ProjectRequestDTO projectRequestDTO) {

        Project project =
                projectMapper.toEntity(projectRequestDTO);

        Project savedProject =
                projectService.createProject(project);

        return projectMapper.toResponseDTO(savedProject);
    }

    @GetMapping
    public List<ProjectResponseDTO> getAllProjects() {

        List<Project> projects =
                projectService.findAllProjects();

        List<ProjectResponseDTO> response =
                new ArrayList<>();

        for (Project project : projects) {

            response.add(
                    projectMapper.toResponseDTO(project)
            );
        }

        return response;
    }

    @GetMapping("/{id}")
    public ProjectResponseDTO getProjectById(
            @PathVariable Long id) {

        Project project =
                projectService.findProjectById(id);

        return projectMapper.toResponseDTO(project);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN, MANAGER')")
    public ProjectResponseDTO updateProject(
            @PathVariable Long id,
            @RequestBody ProjectUpdateDTO projectUpdateDTO) {

        Project updatedProject =
                projectService.updateProject(
                        id,
                        projectUpdateDTO
                );

        return projectMapper.toResponseDTO(updatedProject);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ProjectResponseDTO deleteProject(
            @PathVariable Long id) {

        Project deletedProject =
                projectService.deleteProject(id);

        return projectMapper.toResponseDTO(deletedProject);
    }
}