package com.example.farmCollector.dto;

import com.example.farmCollector.entity.Field;
import com.example.farmCollector.entity.Season;
import com.example.farmCollector.enums.FarmOperation;
import lombok.Data;

@Data
public class CropDto {
    private Long id;
    private String type;
    private double quantity;
    private FarmOperation operation;
    private Field field;
    private Season season;
}
