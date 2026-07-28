package br.com.luigifalconi.taskmanager.dto.request;

import java.time.LocalDate;

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
public class TaskUpdateDTO {

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