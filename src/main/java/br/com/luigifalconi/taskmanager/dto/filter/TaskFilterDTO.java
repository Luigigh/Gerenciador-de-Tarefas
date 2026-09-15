package br.com.luigifalconi.taskmanager.dto.filter;

import br.com.luigifalconi.taskmanager.enums.PriorityTask;
import br.com.luigifalconi.taskmanager.enums.StatusTask;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskFilterDTO {

    /** Busca parcial, case-insensitive, no título da task. */
    private String title;

    private StatusTask status;

    private PriorityTask priority;

    private Long responsibleId;

    private Long projectId;

    /** Retorna tasks com prazo a partir desta data (inclusive). */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dueAfter;

    /** Retorna tasks com prazo até esta data (inclusive). */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dueBefore;

}