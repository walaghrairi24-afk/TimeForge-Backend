package com.wala.goal_service;

import com.wala.goal_service.GoalRequestDTO;
import com.wala.goal_service.GoalResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/goals")
public class GoalController {

    private final GoalService goalService;

    @PostMapping
    public ResponseEntity<GoalResponseDTO> createGoal(@RequestBody GoalRequestDTO goalRequestDTO) {
        GoalResponseDTO createdGoal = goalService.createGoal(goalRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGoal);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GoalResponseDTO> getGoal(@PathVariable Integer id) {
        GoalResponseDTO goal = goalService.getGoalById(id);
        return ResponseEntity.ok(goal);
    }


    @GetMapping
    public ResponseEntity<Page<GoalResponseDTO>> getAllGoals(
            @RequestParam(required = false) String search,      // Paramètre de recherche optionnel
            @RequestParam(required = false) Long ownerId,       // Filtre par ownerId optionnel
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {

        Sort sort = sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        // Utilisation de la même méthode pour tous les cas
        Page<GoalResponseDTO> goals = goalService.getAllGoals(search, ownerId, pageable);
        return ResponseEntity.ok(goals);
    }
    @PutMapping("/{id}")
    public ResponseEntity<GoalResponseDTO> updateGoal(
            @PathVariable Integer id,
            @RequestBody GoalRequestDTO goalRequestDTO) {
        GoalResponseDTO updatedGoal = goalService.updateGoal(id, goalRequestDTO);
        return ResponseEntity.ok(updatedGoal);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGoal(@PathVariable Integer id) {
        goalService.deleteGoal(id);
        return ResponseEntity.noContent().build();
    }



    @PatchMapping("/{id}/progress")
    public ResponseEntity<GoalResponseDTO> updateProgress(
            @PathVariable Integer id,
            @RequestParam Double progress) {
        GoalResponseDTO updatedGoal = goalService.updateProgress(id, progress);
        return ResponseEntity.ok(updatedGoal);
    }

    @GetMapping("/export/pdf")
    public ResponseEntity<byte[]> exportGoalsToPdf(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long ownerId) {

        byte[] pdfContent = goalService.exportGoalsToPdf(search, ownerId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "goals_export.pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
    }

    @GetMapping("/export/excel")
    public ResponseEntity<byte[]> exportGoalsToExcel(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long ownerId) {

        byte[] excelContent = goalService.exportGoalsToExcel(search, ownerId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("filename", "goals_export.xlsx");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(excelContent, headers, HttpStatus.OK);
    }
}