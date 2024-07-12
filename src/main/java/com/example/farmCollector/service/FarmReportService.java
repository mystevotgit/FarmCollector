package com.example.farmCollector.service;

import com.example.farmCollector.dto.CropDto;
import com.example.farmCollector.dto.FarmInfoDto;
import com.example.farmCollector.entity.Crop;
import com.example.farmCollector.entity.Farm;
import com.example.farmCollector.enums.FarmOperation;
import com.example.farmCollector.repository.CropRepository;
import com.example.farmCollector.repository.FarmRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FarmReportService {

    @Autowired
    private FarmRepository farmRepository;

    @Autowired
    private CropRepository cropRepository;
    @Autowired
    private ObjectMapper objectMapper;

    public Map<String, Map<String, Set<CropDto>>> getReportForFarm(final Long farmId, final Long seasonId) {
        final Farm farm = farmRepository.findById(farmId).orElseThrow();
        Map<String, Map<String, Set<CropDto>>> result = new HashMap<>();

        Map<String, Set<CropDto>> data = farm.getFields().stream()
                .flatMap(field -> field.getCrops().stream())
                .filter(crop -> crop.getSeason().getId().equals(seasonId))
                .map(crop -> objectMapper.convertValue(crop, CropDto.class))
                .collect(Collectors.groupingBy(crop -> crop.getSeason().getName(),
                                Collectors.toSet()));
        result.put(farm.getName(), data);
        return result;
    }

    public Map<String, FarmInfoDto> getFarmsReport() {
        final List<Crop> crops = cropRepository.findAll();
        Map<String, FarmInfoDto> result = new HashMap<>();
        final Map<String, List<CropDto>> allCrops = groupCropDtos(crops);
        for (String farm : allCrops.keySet()) {
            final FarmInfoDto farmInfoDto = new FarmInfoDto();
            farmInfoDto.setPlanted(allCrops.get(farm).stream()
                    .filter(crop -> FarmOperation.PLANT == crop.getOperation())
                    .toList());
            farmInfoDto.setHarvested(allCrops.get(farm).stream()
                    .filter(crop -> FarmOperation.HARVEST == crop.getOperation())
                    .toList());
            result.put(farm, farmInfoDto);
        }
        return result;
    }

    private Map<String, List<CropDto>> groupCropDtos(final List<Crop> crops) {
        return crops.stream()
                .map(crop -> objectMapper.convertValue(crop, CropDto.class))
                .collect(Collectors.groupingBy(cropDto -> cropDto.getField().getFarm().getName(), Collectors.toList()));
    }

    private List<CropDto> getCropDtos(final Farm farm, final FarmOperation operation) {
        return farm.getFields().stream()
                .flatMap(field -> field.getCrops().stream())
                .filter(crop -> operation == crop.getOperation())
                .map(crop -> objectMapper.convertValue(crop, CropDto.class))
                .toList();
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
