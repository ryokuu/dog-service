package com.ryoku.dogservice.service;

import com.ryoku.dogservice.web.model.DogBreedDto;
import com.ryoku.dogservice.web.model.RequestDogBreedDto;

import java.util.List;

public interface DogBreedService {

    List<DogBreedDto> fetchAllDogs();

    DogBreedDto saveNewDog(RequestDogBreedDto dogBreedDto);

    DogBreedDto saveSubBreedDog(RequestDogBreedDto dogBreedDto, String breed);

    DogBreedDto updateDog(Integer id, RequestDogBreedDto dogBreedDto);

    void deleteDog(Integer id);

    DogBreedDto getDog(String name);

}
