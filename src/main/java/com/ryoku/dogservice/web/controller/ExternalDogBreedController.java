package com.ryoku.dogservice.web.controller;

import com.ryoku.dogservice.service.ExternalDogBreedService;
import com.ryoku.dogservice.web.model.ImagesResponseDto;
import com.ryoku.dogservice.web.model.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //secara default merupakan singleton bean
@RequiredArgsConstructor
@RequestMapping("/api/external")
// merupakan controller untuk hit langsung ke https://dog.ceo/api
public class ExternalDogBreedController {


    private final ExternalDogBreedService externalDogBreedService;

    @GetMapping("/breeds/list/all")
    ResponseEntity<ResponseDto> fetchAllDogs(){
        return ResponseEntity.ok(externalDogBreedService.fetchAllBreeds());
    }

    @GetMapping("/breed/{breed}/list")
    ResponseEntity<ResponseDto> fetchSubBreeds(@PathVariable String breed){
        return ResponseEntity.ok(externalDogBreedService.fetchAllSubBreeds(breed));
    }

    @GetMapping("/breed/{breed}/images/random/{number}")
    ResponseEntity<ImagesResponseDto> fetchBreedsImages(@PathVariable String breed, @PathVariable Integer number){
        return ResponseEntity.ok(externalDogBreedService.fetchMultiImageByBreed(breed, number));
    }

    @GetMapping("/breed/{breed}/{subBreed}/images/random/{number}")
    ResponseEntity<ImagesResponseDto> fetchSubBreedsImages(@PathVariable String breed, @PathVariable Integer number, @PathVariable String subBreed){
        return ResponseEntity.ok(externalDogBreedService.fetchMultiImageBySubBreed(breed, subBreed, number));
    }

}
