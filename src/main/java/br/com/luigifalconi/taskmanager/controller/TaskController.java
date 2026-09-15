package br.com.luigifalconi.taskmanager.controller;

import br.com.luigifalconi.taskmanager.dto.filter.TaskFilterDTO;
import br.com.luigifalconi.taskmanager.dto.request.TaskRequestDTO;
import br.com.luigifalconi.taskmanager.dto.request.TaskUpdateDTO;
import br.com.luigifalconi.taskmanager.dto.response.PageResponseDTO;
import br.com.luigifalconi.taskmanager.dto.response.TaskResponseDTO;
import br.com.luigifalconi.taskmanager.entity.Task;
import br.com.luigifalconi.taskmanager.mapper.TaskMapper;
import br.com.luigifalconi.taskmanager.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public TaskController(TaskService taskService,
                          TaskMapper taskMapper) {

        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @Operation(summary = "Create a new task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task created successfully"),
            @ApiResponse(responseCode = "400", description = "Task not created")
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public TaskResponseDTO createTask(
            @RequestBody TaskRequestDTO requestDTO) {

        Task task = taskMapper.toEntity(requestDTO);

        Task savedTask = taskService.createTask(task);

        return taskMapper.toResponseDTO(savedTask);
    }

    @Operation(
            summary = "Get tasks with optional filters and pagination",
            description = """
                    Todos os filtros são opcionais e combináveis. Exemplo:
                    /tasks?status=IN_PROGRESS&priority=HIGH&responsibleId=2\
                    &page=0&size=10&sort=expectedFinalDate,asc
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tasks found successfully")
    })
    @GetMapping
    public PageResponseDTO<TaskResponseDTO> getTasks(
            @ParameterObject TaskFilterDTO filter,
            @ParameterObject Pageable pageable) {

        Page<Task> tasks = taskService.findTasks(filter, pageable);

        return PageResponseDTO.from(
                tasks,
                taskMapper::toResponseDTO
        );
    }

    @Operation(summary = "Get task by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task found"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @GetMapping("/{id}")
    public TaskResponseDTO getTaskById(
            @PathVariable Long id) {

        Task task = taskService.findTaskById(id);

        return taskMapper.toResponseDTO(task);
    }

    @Operation(summary = "Update task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task updated"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'DEVELOPER', 'TESTER')")
    public TaskResponseDTO updateTask(
            @PathVariable Long id,
            @RequestBody TaskUpdateDTO dto) {

        Task task = taskService.updateTask(id, dto);

        return taskMapper.toResponseDTO(task);
    }

    @Operation(summary = "Delete task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task deleted"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public TaskResponseDTO deleteTask(
            @PathVariable Long id) {

        Task task = taskService.deleteTask(id);

        return taskMapper.toResponseDTO(task);
    }

}