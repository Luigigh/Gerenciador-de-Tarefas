package br.com.luigifalconi.taskmanager.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import br.com.luigifalconi.taskmanager.enums.PriorityTask;
import br.com.luigifalconi.taskmanager.enums.StatusTask;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskResponseDTO {

    private Long idTask;

    private String title;

    private String description;

    private String comment;

    private LocalDateTime createdAt;

    private LocalDate startDate;

    private LocalDate expectedFinalDate;

    private LocalDate endDate;

    private StatusTask status;

    private PriorityTask priority;

    private Long projectId;

    private String projectName;

    private Long responsibleId;

    private String responsibleName;

}