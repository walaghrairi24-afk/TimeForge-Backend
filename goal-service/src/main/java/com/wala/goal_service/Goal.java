package com.wala.goal_service;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "goals")
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    private Double progress;  // Pourcentage d'avancement (0-100)

    private String status;    // "Not Started", "In Progress", "Completed", "Cancelled"

    private String priority;  // "Low", "Medium", "High"

    @Column(name = "completion_date")
    private LocalDate completionDate;

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDate.now();
        updatedAt = LocalDate.now();
        if (progress == null) progress = 0.0;
        if (status == null) status = "Not Started";
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDate.now();
        // Mise à jour automatique du statut basé sur la progression
        if (progress >= 100 && !"Completed".equals(status)) {
            status = "Completed";
            completionDate = LocalDate.now();
        } else if (progress > 0 && progress < 100 && "Not Started".equals(status)) {
            status = "In Progress";
        }
    }
}