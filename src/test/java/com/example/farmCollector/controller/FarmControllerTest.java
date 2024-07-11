package com.example.farmCollector.controller;

import com.example.farmCollector.entity.Farm;
import com.example.farmCollector.repository.FarmRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FarmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FarmRepository farmRepository;

    @BeforeEach
    public void setUp() {
        farmRepository.deleteAll();
    }

    @Test
    public void testGetAllFarms() throws Exception {
        final Farm farm = new Farm();
        farm.setName("Farm1");
        farmRepository.save(farm);
        mockMvc.perform(get("/api/farms"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].name").value("Farm1"))
                .andExpect(jsonPath("$.[1]").doesNotHaveJsonPath());
    }

    @Test
    public void testCreateFarm() throws Exception {
        String farmJson = "{\"name\": \"MyFarm\"}";

        mockMvc.perform(post("/api/farms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(farmJson))
                .andExpect(status().is2xxSuccessful());
        assertEquals(1, farmRepository.findAll().size());
    }
}
