package com.example.farmCollector.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ReportControllerTest extends AbstractControllerTest {

    @ParameterizedTest
    @CsvSource({
            "/api/reports/search", // By default report is grouped by farm.
            "/api/reports/search?groupBy=farm"
    })
    public void testGetReportGroupedByFarmDefaultSearch(final String url) throws Exception {

        getMockMvc().perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalPages").value("1"))
                .andExpect(jsonPath("$.totalGroups").value("2"))
                .andExpect(jsonPath("$.currentPage").value("0"))
                .andExpect(jsonPath("$.data.['Non Tropic Farm']").exists())
                .andExpect(jsonPath("$.data.['Tropic Farm']").exists())
                .andExpect(jsonPath("$.data.['Savana Farm']").doesNotHaveJsonPath())
                // Verify that planted and harvested exists in json path.
                .andExpect(jsonPath("$.data.['Tropic Farm'].planted.[0].operation").value("planted"))
                .andExpect(jsonPath("$.data.['Tropic Farm'].harvested.[0].operation").value("harvested"));
    }

    @Test
    public void testGetReportGroupedBySeason() throws Exception {

        getMockMvc().perform(get("/api/reports/search?groupBy=season"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalPages").value("1"))
                .andExpect(jsonPath("$.totalGroups").value("3"))
                .andExpect(jsonPath("$.currentPage").value("0"))
                .andExpect(jsonPath("$.data.['Summer 2024'].planted.[0].operation").value("planted"))
                .andExpect(jsonPath("$.data.['Summer 2024'].harvested.[0].operation").value("harvested"))
                .andExpect(jsonPath("$.data.['Non Tropic Farm']").doesNotHaveJsonPath())
                .andExpect(jsonPath("$.data.['Winter 2024'].planted.[0].season.name").value("Winter 2024"))
                .andExpect(jsonPath("$.data.['Spring 2024'].harvested.[0].season.name").value("Spring 2024"));
    }

    @Test
    public void testGetReportGroupedByCropType() throws Exception {

        getMockMvc().perform(get("/api/reports/search?groupBy=crop"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalPages").value("1"))
                .andExpect(jsonPath("$.totalGroups").value("3"))
                .andExpect(jsonPath("$.currentPage").value("0"))
                .andExpect(jsonPath("$.data.Yam.planted.[0].operation").value("planted"))
                .andExpect(jsonPath("$.data.Yam.harvested.[0].operation").value("harvested"))
                .andExpect(jsonPath("$.data.orange").doesNotHaveJsonPath())
                .andExpect(jsonPath("$.data.Corn.planted.[0].type").value("Corn"))
                .andExpect(jsonPath("$.data.Rice.harvested.[0].type").value("Rice"));
    }

    @Test
    public void testGetReportBySizeAndPageGroupedByCropType() throws Exception {

        getMockMvc().perform(get("/api/reports/search?size=5&page=1&groupBy=crop"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalPages").value("2"))
                .andExpect(jsonPath("$.totalGroups").value("3"))
                .andExpect(jsonPath("$.currentPage").value("1"))
                .andExpect(jsonPath("$.data.Yam.planted.[0].operation").doesNotExist())
                .andExpect(jsonPath("$.data.Yam.harvested.[0].operation").value("harvested"))
                .andExpect(jsonPath("$.data.orange").doesNotExist());
    }

    @Test
    public void testGetReportByFarmIdAndSeasonIdAndFieldIdGroupedByCropType() throws Exception {
        final long farmId = getFarmRepository().findAll().get(0).getId();
        final long seasonId = getSeasonRepository().findAll().get(0).getId();
        final long fieldId = getFieldRepository().findAll().get(0).getId();
        String url = "/api/reports/search?farmId=%s&seasonId=%s&fieldId=%s&groupBy=crop";
        final String formatedUrl = String.format(url, farmId, seasonId, fieldId);

        getMockMvc().perform(get(formatedUrl))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalPages").value("1"))
                .andExpect(jsonPath("$.totalGroups").value("2"))
                .andExpect(jsonPath("$.currentPage").value("0"))
                .andExpect(jsonPath("$.data.Corn.planted.[0].operation").value("planted"))
                .andExpect(jsonPath("$.data.Corn.harvested.[0].operation").value("harvested"))
                .andExpect(jsonPath("$.data.Yam").doesNotHaveJsonPath())
                .andExpect(jsonPath("$.data.Corn.planted.[0].type").value("Corn"))
                .andExpect(jsonPath("$.data.Rice.harvested.[0].type").value("Rice"));
    }

}
