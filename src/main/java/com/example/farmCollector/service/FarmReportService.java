package com.example.farmCollector.service;

import com.example.farmCollector.dto.CropDto;
import com.example.farmCollector.dto.FarmReportSearchCriteria;
import com.example.farmCollector.entity.Crop;
import com.example.farmCollector.enums.Group;
import com.example.farmCollector.repository.CropRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FarmReportService {

    @Autowired
    private CropRepository cropRepository;
    @Autowired
    private ObjectMapper objectMapper;

    public Map<String, Map<String, List<CropDto>>> searchFarmReports(FarmReportSearchCriteria criteria, Pageable pageable) {
        Page<Crop> cropsPage = cropRepository.searchCrops(criteria.getFarmId(), criteria.getSeasonId(), criteria.getFieldId(), pageable);

        if (Group.crop.name().equalsIgnoreCase(criteria.getGroupBy())) {
            return cropsPage.stream()
                    .map(crop -> objectMapper.convertValue(crop, CropDto.class))
                    .collect(Collectors.groupingBy(CropDto::getType,
                    Collectors.groupingBy(cropDto -> cropDto.getOperation().name(), Collectors.toList())
            ));
        }
        if (Group.season.name().equalsIgnoreCase(criteria.getGroupBy())) {
            return cropsPage.stream()
                    .map(crop -> objectMapper.convertValue(crop, CropDto.class))
                    .collect(Collectors.groupingBy(cropDto ->  cropDto.getSeason().getName(),
                            Collectors.groupingBy(cropDto -> cropDto.getOperation().name(), Collectors.toList())
                    ));
        }

        return cropsPage.stream()
                .map(crop -> objectMapper.convertValue(crop, CropDto.class))
                .collect(Collectors.groupingBy(cropDto -> cropDto.getField().getFarm().getName(),
                Collectors.groupingBy(cropDto -> cropDto.getOperation().name(), Collectors.toList())
        ));
    }

    public int countSearchFarmReports(FarmReportSearchCriteria criteria, Pageable pageable) {
        Page<Integer> page = cropRepository.countCropsByCriteria(criteria.getFarmId(), criteria.getSeasonId(), criteria.getFieldId(), pageable);
        return page.getTotalPages();
    }
}
