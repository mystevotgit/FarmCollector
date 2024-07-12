package com.example.farmCollector.controller;

import com.example.farmCollector.entity.Crop;
import com.example.farmCollector.entity.Farm;
import com.example.farmCollector.entity.Field;
import com.example.farmCollector.entity.Season;
import com.example.farmCollector.enums.FarmOperation;
import com.example.farmCollector.repository.CropRepository;
import com.example.farmCollector.repository.FarmRepository;
import com.example.farmCollector.repository.FieldRepository;
import com.example.farmCollector.repository.SeasonRepository;
import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Getter
public abstract class AbstractControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SeasonRepository seasonRepository;
    @Autowired
    private FarmRepository farmRepository;
    @Autowired
    private FieldRepository fieldRepository;
    @Autowired
    private CropRepository cropRepository;

    @BeforeEach
    public void setUp() {
        cropRepository.deleteAll();
        fieldRepository.deleteAll();
        farmRepository.deleteAll();
        seasonRepository.deleteAll();
        seedData();
    }

    public void seedData () {
        Season season = new Season();
        season.setName("Summer 2024");
        season = seasonRepository.save(season);

        Season season2 = new Season();
        season2.setName("Spring 2024");
        season2 = seasonRepository.save(season2);

        Season season3 = new Season();
        season3.setName("Winter 2024");
        season3 = seasonRepository.save(season3);

        Farm farm = new Farm();
        farm.setName("Tropic Farm");
        farm = farmRepository.save(farm);

        Farm farm2 = new Farm();
        farm2.setName("Non Tropic Farm");
        farm2 = farmRepository.save(farm2);

        Field field = new Field();
        field.setName("East Field");
        field.setFarm(farm);
        field.setPlantingArea(1000);
        field = fieldRepository.save(field);

        Field field2 = new Field();
        field2.setName("West Field");
        field2.setFarm(farm2);
        field2.setPlantingArea(1000);
        field2 = fieldRepository.save(field2);

        Crop crop = new Crop();
        crop.setSeason(season);
        crop.setType("Corn");
        crop.setOperation(FarmOperation.planted);
        crop.setQuantity(100);
        crop.setField(field);
        cropRepository.save(crop);

        Crop crop2 = new Crop();
        crop2.setSeason(season2);
        crop2.setType("Yam");
        crop2.setOperation(FarmOperation.planted);
        crop2.setQuantity(100);
        crop2.setField(field2);
        cropRepository.save(crop2);

        Crop crop3 = new Crop();
        crop3.setSeason(season);
        crop3.setType("Rice");
        crop3.setOperation(FarmOperation.planted);
        crop3.setQuantity(100);
        crop3.setField(field);
        cropRepository.save(crop3);

        Crop crop4 = new Crop();
        crop4.setSeason(season3);
        crop4.setType("Corn");
        crop4.setOperation(FarmOperation.planted);
        crop4.setQuantity(99);
        crop4.setField(field);
        cropRepository.save(crop4);

        Crop cropH = new Crop();
        cropH.setSeason(season);
        cropH.setType("Corn");
        cropH.setOperation(FarmOperation.harvested);
        cropH.setQuantity(108);
        cropH.setField(field);
        cropRepository.save(cropH);

        Crop crop2H = new Crop();
        crop2H.setSeason(season2);
        crop2H.setType("Yam");
        crop2H.setOperation(FarmOperation.harvested);
        crop2H.setQuantity(102);
        crop2H.setField(field2);
        cropRepository.save(crop2H);

        Crop crop3H = new Crop();
        crop3H.setSeason(season);
        crop3H.setType("Rice");
        crop3H.setOperation(FarmOperation.harvested);
        crop3H.setQuantity(105);
        crop3H.setField(field);
        cropRepository.save(crop3H);

        Crop crop4H = new Crop();
        crop4H.setSeason(season3);
        crop4H.setType("Corn");
        crop4H.setOperation(FarmOperation.harvested);
        crop4H.setQuantity(89);
        crop4H.setField(field);
        cropRepository.save(crop4H);
    }
}
