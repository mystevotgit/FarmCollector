package com.example.farmCollector.controller;

import com.example.farmCollector.dto.CropDto;
import com.example.farmCollector.dto.FarmReportSearchCriteria;
import com.example.farmCollector.service.FarmReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    @Autowired
    private FarmReportService farmReportService;

    @GetMapping("/search")
    public Map<String, Object> searchFarmReports(FarmReportSearchCriteria criteria, Pageable pageable) {
        final int totalPages = farmReportService.countSearchFarmReports(criteria, pageable);
        Map<String, Map<String, List<CropDto>>> groupedResults = farmReportService.searchFarmReports(criteria, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("data", groupedResults);
        response.put("currentPage", pageable.getPageNumber());
        response.put("totalGroups", groupedResults.size());
        response.put("totalPages", totalPages);

        return response;
    }

}
