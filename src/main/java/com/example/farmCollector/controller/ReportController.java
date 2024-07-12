package com.example.farmCollector.controller;

import com.example.farmCollector.dto.CropDto;
import com.example.farmCollector.dto.FarmInfoDto;
import com.example.farmCollector.entity.Crop;
import com.example.farmCollector.service.FarmReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    @Autowired
    private FarmReportService farmReportService;

    @GetMapping("/farm/{farmId}/season/{seasonId}")
    public ResponseEntity<Map<String, Map<String, Set<CropDto>>>> getReportForFarm(@PathVariable Long farmId, @PathVariable Long seasonId) {
        return ResponseEntity.ok(farmReportService.getReportForFarm(farmId, seasonId));
    }
    @GetMapping("/farm")
    public ResponseEntity<Map<String, FarmInfoDto>> getReportForFarm() {
        return ResponseEntity.ok(farmReportService.getFarmsReport());
    }

    @GetMapping("/farm/search")
    public ResponseEntity<Map<String, FarmInfoDto>> searchReport() {
        return ResponseEntity.ok(farmReportService.getFarmsReport());
    }

    @GetMapping("/crops/season/{seasonId}")
    public ResponseEntity<Map<String, Map<String, Set<Crop>>>> getReportForCrops(@PathVariable Long seasonId) {
        return ResponseEntity.ok(farmReportService.getReportForCrops(seasonId));
    }

}
