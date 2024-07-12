package com.example.farmCollector.controller;

import com.example.farmCollector.entity.Farm;
import com.example.farmCollector.repository.FarmRepository;
import com.example.farmCollector.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/farms")
public class FarmController {
    @Autowired
    private FarmRepository farmRepository;

    @Autowired
    private ApiService apiService;

    @GetMapping
    public ResponseEntity<List<Farm>> getAllFarms() {
        return ResponseEntity.ok(apiService.getAllFarms());
    }

    @PostMapping
    public ResponseEntity<Farm> createFarm(@RequestBody Farm farm) {
        return ResponseEntity.ok(apiService.creatFarm(farm));
    }
}
