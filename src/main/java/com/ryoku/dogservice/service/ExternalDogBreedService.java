package com.ryoku.dogservice.service;

import com.ryoku.dogservice.web.model.ImageResponseDto;
import com.ryoku.dogservice.web.model.ImagesResponseDto;
import com.ryoku.dogservice.web.model.ResponseDto;

public interface ExternalDogBreedService {

    ResponseDto fetchAllBreeds();

    ResponseDto fetchAllSubBreeds(String breed);

    ImagesResponseDto fetchMultiImageByBreed(String breed, Integer number);

    ImagesResponseDto fetchMultiImageBySubBreed(String breed, String subBreed, Integer number);

    ImageResponseDto fetchImageByBreed(String breed);

    ImageResponseDto fetchImageBySubBreed(String breed, String SubBreed);


}
