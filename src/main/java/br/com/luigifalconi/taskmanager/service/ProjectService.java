package br.com.luigifalconi.taskmanager.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.luigifalconi.taskmanager.dto.request.ProjectUpdateDTO;
import br.com.luigifalconi.taskmanager.entity.Project;
import br.com.luigifalconi.taskmanager.repository.ProjectRepository;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project createProject(Project project) {

        if (projectRepository.existsByName(project.getName())) {
            throw new RuntimeException("Project name already exists!");
        }

        return projectRepository.save(project);
    }

    public List<Project> findAllProjects() {

        return projectRepository.findAll();
    }

    public Project findProjectById(Long id) {

        return projectRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Project not found!"));
    }

    public Project updateProject(
            Long id,
            ProjectUpdateDTO projectUpdateDTO) {

        Project project = findProjectById(id);

        project.setName(projectUpdateDTO.getName());
        project.setDescription(projectUpdateDTO.getDescription());
        project.setBudget(projectUpdateDTO.getBudget());
        project.setStartDate(projectUpdateDTO.getStartDate());
        project.setExpectedFinalDate(
                projectUpdateDTO.getExpectedFinalDate()
        );
        project.setEndDate(projectUpdateDTO.getEndDate());
        project.setStatus(projectUpdateDTO.getStatus());

        return projectRepository.save(project);
    }

    public Project deleteProject(Long id) {

        Project project = findProjectById(id);

        projectRepository.delete(project);

        return project;
    }
}