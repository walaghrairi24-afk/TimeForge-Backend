package com.wala.goal_service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoalRepository extends JpaRepository<Goal,Integer> {
    // Recherche simple par titre (insensible à la casse)
    Page<Goal> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    // Recherche par ownerId
    Page<Goal> findByOwnerId(Long ownerId, Pageable pageable);

    // Recherche combinée simple
    @Query("SELECT g FROM Goal g WHERE " +
            "(:search IS NULL OR LOWER(g.title) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(g.description) LIKE LOWER(CONCAT('%', :search, '%'))) AND " +
            "(:ownerId IS NULL OR g.ownerId = :ownerId)")
    Page<Goal> findAllWithSearch(@Param("search") String search,
                                 @Param("ownerId") Long ownerId,
                                 Pageable pageable);

    // Pour l'export - recherche simple
    @Query("SELECT g FROM Goal g WHERE " +
            "(:search IS NULL OR LOWER(g.title) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(g.description) LIKE LOWER(CONCAT('%', :search, '%'))) AND " +
            "(:ownerId IS NULL OR g.ownerId = :ownerId)")
    List<Goal> findForExport(@Param("search") String search,
                             @Param("ownerId") Long ownerId);
}
