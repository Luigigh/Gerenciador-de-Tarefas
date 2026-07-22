package br.com.luigifalconi.taskmanager.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import br.com.luigifalconi.taskmanager.enums.StatusProject;
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
public class ProjectResponseDTO {

    private Long idProject;

    private String name;

    private String descripton;

    private double Budget;

    private LocalDateTime createdAt;

    private LocalDate startDate;

    private LocalDate expectedFinalDate;

    private LocalDate endDate;

    private StatusProject status;

}