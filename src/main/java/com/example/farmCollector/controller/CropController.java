package com.example.farmCollector.controller;

import com.example.farmCollector.dto.CropDto;
import com.example.farmCollector.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crops")
public class CropController {

    @Autowired
    private ApiService apiService;

    @GetMapping
    public ResponseEntity<List<CropDto>> getAllCrops() {
        return ResponseEntity.ok(apiService.getAllCrops());
    }

    @PostMapping
    public ResponseEntity<CropDto> createCrop(@RequestBody CropDto crop) {
        return ResponseEntity.ok(apiService.createCrop(crop));
    }
}
