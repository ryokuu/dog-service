package com.ryoku.dogservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ryoku.dogservice.service.DogBreedService;
import com.ryoku.dogservice.web.model.RequestDogBreedDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DogBreedController.class)
class DogBreedControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    DogBreedService dogBreedService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void fetchAll() throws Exception {
        mockMvc.perform(get("/api/v1/breed/list/all").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void fetchSubBreeds() throws Exception {
        String subBreed = "hound";
        mockMvc.perform(get("/api/v1/breed/"+subBreed+ "+/list").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void createBreed() throws Exception {

        RequestDogBreedDto requestDogBreedDto = RequestDogBreedDto.builder()
                .name("cihuahua")
                .build();
        String requestJson = objectMapper.writeValueAsString(requestDogBreedDto);

        mockMvc.perform(post("/api/v1/breed")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated());

    }

    @Test
    void updateBreed() throws Exception {
        RequestDogBreedDto requestDogBreedDto = RequestDogBreedDto.builder()
                .name("cihuahua")
                .build();
        String requestJson = objectMapper.writeValueAsString(requestDogBreedDto);

        long id = 1000L;
        mockMvc.perform(put("/api/v1/breed"+id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteBreed() throws Exception {
        long id = 1000L;
        mockMvc.perform(delete("/api/v1/breed"+id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}