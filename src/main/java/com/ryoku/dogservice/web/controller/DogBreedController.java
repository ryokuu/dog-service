package com.ryoku.dogservice.web.controller;

import com.ryoku.dogservice.service.DogBreedService;
import com.ryoku.dogservice.web.model.DogBreedDto;
import com.ryoku.dogservice.web.model.RequestDogBreedDto;
import com.ryoku.dogservice.web.model.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //secara default merupakan singleton bean
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class DogBreedController {

    private final DogBreedService dogBreedService;

    @GetMapping("/breed/list/all")
    ResponseEntity<List<DogBreedDto>> fetchAll(){
        return ResponseEntity.ok(dogBreedService.fetchAllDogs());
    }

    @GetMapping("/breed/{breed}/list")
    ResponseEntity<DogBreedDto> fetchSubBreeds(@PathVariable String breed){
        return ResponseEntity.ok(dogBreedService.getDog(breed));
    }

    @PostMapping("/breed")
    ResponseEntity<DogBreedDto> createBreed( @RequestBody @Validated RequestDogBreedDto requestDogBreedDto){
        return new ResponseEntity<>(dogBreedService.saveNewDog(requestDogBreedDto), HttpStatus.CREATED);
    }

    @PostMapping("/breed/{breed}")
    ResponseEntity<DogBreedDto> createSubBreed(@RequestBody @Validated RequestDogBreedDto requestDogBreedDto, @PathVariable String breed){
        return new ResponseEntity<>(dogBreedService.saveSubBreedDog(requestDogBreedDto, breed), HttpStatus.CREATED);
    }

    @PutMapping("/breed/{id}")
    ResponseEntity<DogBreedDto> updateBreed(@PathVariable Integer id, @RequestBody @Validated RequestDogBreedDto requestDogBreedDto){
        return ResponseEntity.ok(dogBreedService.updateDog(id, requestDogBreedDto));
    }

    @DeleteMapping("/breed/{id}")
    ResponseEntity<HttpStatus> deleteBreed(@PathVariable Integer id){
        dogBreedService.deleteDog(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
