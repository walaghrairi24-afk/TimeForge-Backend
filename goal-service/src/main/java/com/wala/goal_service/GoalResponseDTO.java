package com.wala.goal_service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoalResponseDTO {
    private Integer id;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double progress;
    private String status;
    private String priority;
    private LocalDate completionDate;
    private Long ownerId;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}