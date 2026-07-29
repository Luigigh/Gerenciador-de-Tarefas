package br.com.luigifalconi.taskmanager.controller;

import br.com.luigifalconi.taskmanager.dto.request.TaskRequestDTO;
import br.com.luigifalconi.taskmanager.dto.request.TaskUpdateDTO;
import br.com.luigifalconi.taskmanager.dto.response.TaskResponseDTO;
import br.com.luigifalconi.taskmanager.entity.Task;
import br.com.luigifalconi.taskmanager.mapper.TaskMapper;
import br.com.luigifalconi.taskmanager.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    public TaskResponseDTO createTask(
            @RequestBody TaskRequestDTO requestDTO) {

        Task task = taskMapper.toEntity(requestDTO);

        Task savedTask = taskService.createTask(task);

        return taskMapper.toResponseDTO(savedTask);
    }

    @Operation(summary = "Get all tasks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tasks found successfully")
    })
    @GetMapping
    public List<TaskResponseDTO> getAllTasks() {

        List<Task> tasks = taskService.findAllTasks();

        List<TaskResponseDTO> response = new ArrayList<>();

        for (Task task : tasks) {

            response.add(
                    taskMapper.toResponseDTO(task)
            );

        }

        return response;
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
    public TaskResponseDTO deleteTask(
            @PathVariable Long id) {

        Task task = taskService.deleteTask(id);

        return taskMapper.toResponseDTO(task);
    }

}