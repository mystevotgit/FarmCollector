package com.example.farmCollector.controller;

import com.example.farmCollector.entity.Field;
import com.example.farmCollector.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fields")
public class FieldController {

    @Autowired
    private ApiService apiService;

    @GetMapping
    public ResponseEntity<List<Field>> getAllFields() {
        return ResponseEntity.ok(apiService.getAllFields());
    }

    @PostMapping
    public ResponseEntity<Field> createField(@RequestBody Field field) {
        return ResponseEntity.ok(apiService.createField(field));
    }
}
