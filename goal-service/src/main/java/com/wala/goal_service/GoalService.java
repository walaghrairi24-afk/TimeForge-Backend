package com.wala.goal_service;

import com.wala.goal_service.GoalRequestDTO;
import com.wala.goal_service.GoalResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GoalService {
    GoalResponseDTO createGoal(GoalRequestDTO goalRequestDTO);
    GoalResponseDTO getGoalById(Integer id);
    Page<GoalResponseDTO> getAllGoals(String search, Long ownerId, Pageable pageable);
    GoalResponseDTO updateGoal(Integer id, GoalRequestDTO goalRequestDTO);
    void deleteGoal(Integer id);

    GoalResponseDTO updateProgress(Integer id, Double progress);
    byte[] exportGoalsToPdf(String search, Long ownerId);
    byte[] exportGoalsToExcel(String search, Long ownerId);
}