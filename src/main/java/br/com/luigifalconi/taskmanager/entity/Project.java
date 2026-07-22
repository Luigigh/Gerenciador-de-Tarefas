package br.com.luigifalconi.taskmanager.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import br.com.luigifalconi.taskmanager.enums.StatusProject;
import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProject;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private double budget;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDate startDate;

    private LocalDate expectedFinalDate;

    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private StatusProject status;


    
}
