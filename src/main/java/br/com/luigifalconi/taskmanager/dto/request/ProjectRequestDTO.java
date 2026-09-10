package br.com.luigifalconi.taskmanager.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

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
public class ProjectRequestDTO {

    private String name;

    private String description;

    private BigDecimal budget;

    private LocalDate startDate;

    private LocalDate expectedFinalDate;

    private LocalDate endDate;

    private StatusProject status;
    
}
