package com.wala.goal_service;

import com.wala.goal_service.GoalRequestDTO;
import com.wala.goal_service.GoalResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GoalServiceImpl implements GoalService {

    private final GoalRepository goalRepository;
    private final GoalMapper goalMapper;
    private final PdfExportService pdfExportService;
    private final ExcelExportService excelExportService;

    @Override
    public GoalResponseDTO createGoal(GoalRequestDTO goalRequestDTO) {
        Goal goal = goalMapper.toEntity(goalRequestDTO);
        Goal savedGoal = goalRepository.save(goal);
        return goalMapper.toDTO(savedGoal);
    }

    @Override
    public GoalResponseDTO getGoalById(Integer id) {
        Goal goal = goalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Goal not found with id: " + id));
        return goalMapper.toDTO(goal);
    }



    @Override
    public GoalResponseDTO updateGoal(Integer id, GoalRequestDTO goalRequestDTO) {
        Goal existingGoal = goalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Goal not found with id: " + id));

        Goal updatedGoal = goalMapper.updateEntityFromDTO(goalRequestDTO, existingGoal);
        Goal savedGoal = goalRepository.save(updatedGoal);
        return goalMapper.toDTO(savedGoal);
    }

    @Override
    public void deleteGoal(Integer id) {
        if (!goalRepository.existsById(id)) {
            throw new RuntimeException("Goal not found with id: " + id);
        }
        goalRepository.deleteById(id);
    }
    @Override
    public Page<GoalResponseDTO> getAllGoals(String search, Long ownerId, Pageable pageable) {
        Page<Goal> goals = goalRepository.findAllWithSearch(search, ownerId, pageable);
        return goals.map(goalMapper::toDTO);
    }

    @Override
    public GoalResponseDTO updateProgress(Integer id, Double progress) {
        Goal goal = goalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Goal not found with id: " + id));

        goal.setProgress(progress);
        Goal updatedGoal = goalRepository.save(goal);
        return goalMapper.toDTO(updatedGoal);
    }

    @Override
    public byte[] exportGoalsToPdf(String search, Long ownerId) {
        List<Goal> goals = goalRepository.findForExport(search, ownerId);
        List<GoalResponseDTO> goalDTOs = goals.stream()
                .map(goalMapper::toDTO)
                .collect(Collectors.toList());
        return pdfExportService.exportGoalsToPdf(goalDTOs);
    }

    @Override
    public byte[] exportGoalsToExcel(String search, Long ownerId) {
        List<Goal> goals = goalRepository.findForExport(search, ownerId);
        List<GoalResponseDTO> goalDTOs = goals.stream()
                .map(goalMapper::toDTO)
                .collect(Collectors.toList());
        return excelExportService.exportGoalsToExcel(goalDTOs);
    }
}