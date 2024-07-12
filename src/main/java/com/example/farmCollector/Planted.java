package com.example.farmCollector;

import com.example.farmCollector.entity.Crop;
import com.example.farmCollector.entity.Field;
import lombok.Data;

import java.util.List;

@Data
public class Planted {
    private List<Crop> crops;
}
