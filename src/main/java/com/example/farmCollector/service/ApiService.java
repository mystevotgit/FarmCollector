package com.example.farmCollector.service;

import com.example.farmCollector.dto.CropDto;
import com.example.farmCollector.entity.Crop;
import com.example.farmCollector.entity.Farm;
import com.example.farmCollector.entity.Field;
import com.example.farmCollector.entity.Season;
import com.example.farmCollector.repository.CropRepository;
import com.example.farmCollector.repository.FarmRepository;
import com.example.farmCollector.repository.FieldRepository;
import com.example.farmCollector.repository.SeasonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiService {

    private final CropRepository cropRepository;
    private final FieldRepository fieldRepository;
    private final FarmRepository farmRepository;
    private final SeasonRepository seasonRepository;
    private final ObjectMapper objectMapper;


    public List<CropDto> getAllCrops() {
        return cropRepository.findAll().stream().map(crop -> objectMapper.convertValue(crop, CropDto.class)).toList();
    }

    public CropDto createCrop(final CropDto cropDto) {
        System.out.println();
        final Field field = fieldRepository.findById(cropDto.getField().getId())
                .orElseThrow(() -> new ObjectNotFoundException("field", Field.class));
        final Season season = seasonRepository.findById(cropDto.getSeason().getId())
                .orElseThrow(() -> new ObjectNotFoundException("season", Season.class));
        cropDto.setField(field);
        cropDto.setSeason(season);
        Crop crop = objectMapper.convertValue(cropDto, Crop.class);
        crop = cropRepository.save(crop);
        cropDto.setId(crop.getId());
        return cropDto;
    }

    public List<Farm> getAllFarms() {
        return farmRepository.findAll();
    }

    public Farm creatFarm(Farm farm) {
        return farmRepository.save(farm);
    }

    public List<Field> getAllFields() {
        return fieldRepository.findAll();
    }

    public Field createField(Field field) {
        final Farm farm = farmRepository.findById(field.getFarm().getId())
                .orElseThrow(() -> new ObjectNotFoundException("farm", Farm.class));
        field.setFarm(farm);
        return fieldRepository.save(field);
    }

    public List<Season> getAllSeasons() {
        return seasonRepository.findAll();
    }

    public Season createSeason(Season season) {
        return seasonRepository.save(season);
    }
}
