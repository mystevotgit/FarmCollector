package com.example.farmCollector.controller;

import com.example.farmCollector.entity.Crop;
import com.example.farmCollector.repository.CropRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CropControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CropRepository cropRepository;

    @BeforeEach
    public void setUp() {
        cropRepository.deleteAll();
    }

    @Test
    public void testGetAllCrops() throws Exception {
        Crop crop = new Crop();
        crop.setType("corn");
        cropRepository.save(crop);

        mockMvc.perform(get("/api/crops"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].type").value("corn"))
                .andExpect(jsonPath("$.[1]").doesNotHaveJsonPath());
    }

    @Test
    public void testCreateCrop() throws Exception {
        String cropJson = "{\"type\": \"Corn\"}";

        mockMvc.perform(post("/api/crops")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cropJson))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.type").value("Corn"))
                .andExpect(jsonPath("$.[1]").doesNotHaveJsonPath());
    }
}
