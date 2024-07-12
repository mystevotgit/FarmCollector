package com.example.farmCollector.controller;

import com.example.farmCollector.entity.Field;
import com.example.farmCollector.entity.Season;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class CropControllerTest extends AbstractControllerTest {

    @Test
    public void testGetAllCrops() throws Exception {

        getMockMvc().perform(get("/api/crops"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].type").value("Corn"))
                .andExpect(jsonPath("$.[0].quantity").value("100.0"))
                .andExpect(jsonPath("$.[0].operation").value("planted"))
                .andExpect(jsonPath("$.[0].field.name").value("East Field"))
                .andExpect(jsonPath("$.[1]").exists())
                .andExpect(jsonPath("$.[2]").exists())
                .andExpect(jsonPath("$.[3]").exists())
                .andExpect(jsonPath("$.[4]").exists())
                .andExpect(jsonPath("$.[5]").exists())
                .andExpect(jsonPath("$.[6]").exists())
                .andExpect(jsonPath("$.[7]").exists())
                .andExpect(jsonPath("$.[10]").doesNotExist());
    }

    @Test
    public void testCreateCrop() throws Exception {
        List<Field> fields = getFieldRepository().findAll();
        List<Season> seasons = getSeasonRepository().findAll();
        String cropJson = """
        {
          "type": "Rice",
          "quantity": 200,
          "operation": "planted",
          "field": {
            "id": %s
          },
          "season": {
            "id": %s
          }
        }
        """;

        final String formatedCrop = String.format(cropJson, fields.get(0).getId(), seasons.get(0).getId());
        getMockMvc().perform(post("/api/crops")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(formatedCrop))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.type").value("Rice"))
                .andExpect(jsonPath("$.[10]").doesNotExist());
    }

}
