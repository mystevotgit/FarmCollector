package com.example.farmCollector.controller;

import com.example.farmCollector.entity.Crop;
import com.example.farmCollector.entity.Farm;
import com.example.farmCollector.entity.Field;
import com.example.farmCollector.entity.Season;
import com.example.farmCollector.repository.CropRepository;
import com.example.farmCollector.repository.FarmRepository;
import com.example.farmCollector.repository.FieldRepository;
import com.example.farmCollector.repository.SeasonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FarmRepository farmRepository;

    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private CropRepository cropRepository;

    @Autowired
    private FieldRepository fieldRepository;

    @BeforeEach
    public void setUp() {
        farmRepository.deleteAll();
        seasonRepository.deleteAll();
        fieldRepository.deleteAll();
        cropRepository.deleteAll();
    }

    @Test
    public void testGetReportForFarmBySeason() throws Exception {
        Season season = new Season();
        season.setName("season1");
        season = seasonRepository.save(season);

        Farm farm = new Farm();
        farm.setName("Farm1");
        farm = farmRepository.save(farm);

        Field field = new Field();
        field.setName("Field1");
        field.setFarm(farm);
        field.setPlantingArea(1000);
        field = fieldRepository.save(field);

        Crop crop = new Crop();
        crop.setSeason(season);
        crop.setType("Corn");
        crop.setQuantity(100);
        crop.setField(field);
        cropRepository.save(crop);

        mockMvc.perform(get("/api/reports/farm/" + farm.getId() + "/season/" + season.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.Farm1.season1.[0].quantity").value("100.0"))
                .andExpect(jsonPath("$.Farm1.season1.[1]").doesNotHaveJsonPath());

    }

    @Test
    public void testGetReportForCropsBySeason() throws Exception {
        Season season = new Season();
        season.setName("season1");
        season = seasonRepository.save(season);

        Farm farm = new Farm();
        farm.setName("Farm1");
        farm = farmRepository.save(farm);

        Field field = new Field();
        field.setName("Field1");
        field.setFarm(farm);
        field.setPlantingArea(1000);
        field = fieldRepository.save(field);

        Crop crop = new Crop();
        crop.setSeason(season);
        crop.setType("Corn");
        crop.setQuantity(100);
        crop.setField(field);
        cropRepository.save(crop);

        System.out.println(
        mockMvc.perform(get("/api/reports/crops/season/" + season.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.Corn.season1.[0].quantity").value("100.0"))
                .andExpect(jsonPath("$.Corn.season1.[1]").doesNotHaveJsonPath())
                .andReturn().getResponse().getContentAsString());
    }
}
