package com.example.farmCollector.controller;

import com.example.farmCollector.entity.Season;
import com.example.farmCollector.repository.SeasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seasons")
public class SeasonController {
    @Autowired
    private SeasonRepository seasonRepository;

    @GetMapping
    public ResponseEntity<List<Season>> getAllSeasons() {
        return ResponseEntity.ok(seasonRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Season> createSeason(@RequestBody Season season) {
        return ResponseEntity.ok(seasonRepository.save(season));
    }
}
