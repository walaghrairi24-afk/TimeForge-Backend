package com.wala.goal_service;

import com.wala.goal_service.GoalRequestDTO;
import com.wala.goal_service.GoalResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class GoalMapper {

    public Goal toEntity(GoalRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        return Goal.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .progress(dto.getProgress() != null ? dto.getProgress() : 0.0)
                .status(dto.getStatus() != null ? dto.getStatus() : "Not Started")
                .priority(dto.getPriority())
                .ownerId(dto.getOwnerId())
                .build();
    }

    public GoalResponseDTO toDTO(Goal entity) {
        if (entity == null) {
            return null;
        }

        return GoalResponseDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .progress(entity.getProgress())
                .status(entity.getStatus())
                .priority(entity.getPriority())
                .completionDate(entity.getCompletionDate())
                .ownerId(entity.getOwnerId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public Goal updateEntityFromDTO(GoalRequestDTO dto, Goal entity) {
        if (dto == null) {
            return entity;
        }

        if (dto.getTitle() != null) {
            entity.setTitle(dto.getTitle());
        }
        if (dto.getDescription() != null) {
            entity.setDescription(dto.getDescription());
        }
        if (dto.getStartDate() != null) {
            entity.setStartDate(dto.getStartDate());
        }
        if (dto.getEndDate() != null) {
            entity.setEndDate(dto.getEndDate());
        }
        if (dto.getProgress() != null) {
            entity.setProgress(dto.getProgress());
        }
        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
        }
        if (dto.getPriority() != null) {
            entity.setPriority(dto.getPriority());
        }
        if (dto.getOwnerId() != null) {
            entity.setOwnerId(dto.getOwnerId());
        }

        return entity;
    }
}