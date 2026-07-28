package br.com.luigifalconi.taskmanager.dto.request;

import java.time.LocalDate;

import br.com.luigifalconi.taskmanager.enums.PriorityTask;
import br.com.luigifalconi.taskmanager.enums.StatusTask;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskRequestDTO {

    private String title;

    private String description;

    private String comment;

    private LocalDate startDate;

    private LocalDate expectedFinalDate;

    private LocalDate endDate;

    private StatusTask status;

    private PriorityTask priority;

    private Long projectId;

    private Long responsibleId;

}