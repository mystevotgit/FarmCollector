package com.example.farmCollector.dto;

import lombok.Data;

@Data
public class FarmReportSearchCriteria {
    private Long farmId;
    private Long seasonId;
    private Long fieldId;
    private String groupBy;
}

