package com.example.farmCollector.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class SeasonControllerTest extends AbstractControllerTest {

    @Test
    void getAllSeasons() throws Exception {
        getMockMvc().perform(get("/api/seasons"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].name").value("Summer 2024"))
                .andExpect(jsonPath("$.[1].name").exists())
                .andExpect(jsonPath("$.[5]").doesNotExist());
    }

    @Test
    void createSeason() throws Exception {
        String seasonJson = """
                {
                    "name": "Hamarthan 2024"
                }
                """;

        getMockMvc().perform(post("/api/seasons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(seasonJson))
                .andExpect(status().isOk());
        assertEquals(4, getSeasonRepository().findAll().size());
    }
}