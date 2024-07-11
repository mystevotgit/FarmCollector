package com.example.farmCollector.service;

import com.example.farmCollector.entity.Crop;
import com.example.farmCollector.entity.Farm;
import com.example.farmCollector.repository.FarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FarmReportService {

    @Autowired
    private FarmRepository farmRepository;

    public Map<String, Map<String, Set<Crop>>> getReportForFarm(final Long farmId, final Long seasonId) {
        final Farm farm = farmRepository.findById(farmId).orElseThrow();
        Map<String, Map<String, Set<Crop>>> result = new HashMap<>();

        Map<String, Set<Crop>> data = farm.getFields().stream()
                .flatMap(field -> field.getCrops().stream())
                .filter(crop -> crop.getSeason().getId().equals(seasonId))
                .collect(Collectors.groupingBy(crop -> crop.getSeason().getName(),
                                Collectors.toSet()));
        result.put(farm.getName(), data);
        return result;
    }

    public Map<String, Map<String, Set<Crop>>> getReportForCrops(final Long seasonId) {
        List<Farm> farms = farmRepository.findAll();
        return farms.stream()
                .flatMap(farm -> farm.getFields().stream())
                .flatMap(field -> field.getCrops().stream())
                .filter(crop -> crop.getSeason().getId().equals(seasonId))
                .collect(Collectors.groupingBy(Crop::getType,
                        Collectors.groupingBy(crop -> crop.getSeason().getName(),
                                Collectors.toSet())));
    }
}
