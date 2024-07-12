package com.example.farmCollector.dto;

import com.example.farmCollector.entity.Crop;
import lombok.Data;

import java.util.List;

@Data
public class FarmInfoDto {
    private List<CropDto> planted;
    private List<CropDto> harvested;
}
