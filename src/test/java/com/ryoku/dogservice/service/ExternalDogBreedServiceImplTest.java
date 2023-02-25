package com.ryoku.dogservice.service;

import com.ryoku.dogservice.web.model.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
class ExternalDogBreedServiceImplTest {

    private static final String ALL_DOGS_URL = "https://dog.ceo/api/breeds/list/all";

    @Test
    void fetchAll() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseDto response = restTemplate.getForObject(ALL_DOGS_URL, ResponseDto.class);

        assert response != null;
        assertEquals(response.getStatus(), "success");

        if (response.getMessage().containsKey("sheepdog")){
            Map<String, List<String>> dogs = response.getMessage();
            List<String> sheepdogSub = dogs.get("sheepdog");
            sheepdogSub.forEach(sub -> dogs.put("sheepdog" + "-" + sub, new ArrayList<>()));

            dogs.remove("sheepdog");
            assertTrue(dogs.containsKey("sheepdog-english"));
            assertTrue(dogs.containsKey("sheepdog-shetland"));

        }
    }

    @Test
    void fetchMultiImageByBreed() {
    }

    @Test
    void fetchMultiImageBySubBreed() {
    }

    @Test
    void fetchImageByBreed() {
    }

    @Test
    void fetchImageBySubBreed() {
    }
}