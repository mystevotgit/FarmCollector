package com.example.farmCollector.controller;

import com.example.farmCollector.entity.Farm;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class FieldControllerTest extends AbstractControllerTest {

    @Test
    void getAllFields() throws Exception {
        getMockMvc().perform(get("/api/fields"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].name").value("East Field"))
                .andExpect(jsonPath("$.[1].name").exists())
                .andExpect(jsonPath("$.[5]").doesNotExist());
    }

    @Test
    void createField() throws Exception {
        List<Farm> farms = getFarmRepository().findAll();
        String fieldJson = """
                {
                    "name": "North Field",
                    "plantingArea": 1000,
                    "farm": {
                        "id": %s
                    }
                }
                """;

        final String formatedField = String.format(fieldJson, farms.get(0).getId());
        getMockMvc().perform(post("/api/fields")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(formatedField))
                .andExpect(status().isOk());
        assertEquals(3, getFieldRepository().findAll().size());
    }
}