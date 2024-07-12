package com.example.farmCollector.repository;

import com.example.farmCollector.entity.Crop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CropRepository extends JpaRepository<Crop, Long> {

    @Query("SELECT c FROM Crop c WHERE " +
            "(:farmId IS NULL OR c.field.farm.id = :farmId) AND " +
            "(:seasonId IS NULL OR c.season.id = :seasonId) AND " +
            "(:fieldId IS NULL OR c.field.id = :fieldId)")
    Page<Crop> searchCrops(@Param("farmId") Long farmId,
                           @Param("seasonId") Long seasonId,
                           @Param("fieldId") Long fieldId,
                           Pageable pageable);

    @Query("SELECT COUNT(c) FROM Crop c WHERE " +
            "(:farmId IS NULL OR c.field.farm.id = :farmId) AND " +
            "(:seasonId IS NULL OR c.season.id = :seasonId) AND " +
            "(:fieldId IS NULL OR c.field.id = :fieldId)")
    Page<Integer> countCropsByCriteria(@Param("farmId") Long farmId,
                                   @Param("seasonId") Long seasonId,
                                   @Param("fieldId") Long fieldId,
                                   Pageable pageable);
}
