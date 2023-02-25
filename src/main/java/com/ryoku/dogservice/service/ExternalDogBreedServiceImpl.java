package com.ryoku.dogservice.service;

import com.ryoku.dogservice.web.model.ImageResponseDto;
import com.ryoku.dogservice.web.model.ImagesResponseDto;
import com.ryoku.dogservice.web.model.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service//default singleton bean
@RequiredArgsConstructor
@Slf4j
public class ExternalDogBreedServiceImpl implements ExternalDogBreedService {

    private static final String DOG_BASE_URL = "https://dog.ceo/api";
    private static final String ALL_BREED_URL = "https://dog.ceo/api/breeds/list/all";
    private final RestTemplate restTemplate;
    private final RestTemplate restTemplateForSub;
    @Cacheable(cacheNames = "allBreedCache")
    @Override
    public ResponseDto fetchAllBreeds() {

        ResponseDto response = restTemplate.getForObject(ALL_BREED_URL, ResponseDto.class);

        assert response != null;

        if (response.getMessage().containsKey("terrier")){
            modifyResponse("terrier", response.getMessage());
        }
        if (response.getMessage().containsKey("sheepdog")){
            modifyResponse("sheepdog", response.getMessage());
        }

        return response;
    }

    private void modifyResponse(String breed, Map<String, List<String>> subBreed){
        List<String> subBreeds = subBreed.get(breed);
        if (subBreeds!=null){
            subBreeds.forEach(sub ->{
                if (breed.equals("terrier")){
                    subBreed.put(breed + "-" + sub, fetchMultiImageBySubBreed(breed, sub, 4 ).getMessage());

                }else{
                    subBreed.put(breed + "-" + sub, Collections.emptyList());
                }
                subBreed.remove(breed);
            });
        }
    }

    @Cacheable(cacheNames = "subBreedCache")
    @Override
    public ResponseDto fetchAllSubBreeds(String breed) {

        ResponseDto response = restTemplateForSub.getForObject(DOG_BASE_URL+ "/breed/" + breed + "/" + "list", ResponseDto.class);

        assert response != null;
        if (breed.equals("terrier") || breed.equals("sheepdog")){
            modifyResponse(breed, response.getMessage());
        }

        return response;
    }

    @Override
    public ImagesResponseDto fetchMultiImageByBreed(String breed, Integer number) {

        if (breed.equals("shiba") && number%2==0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "number must be odd");
        }
        return restTemplate.getForObject(DOG_BASE_URL + "/breed/" + breed + "/images/random/" + number, ImagesResponseDto.class);
    }

    @Override
    public ImagesResponseDto fetchMultiImageBySubBreed(String breed, String subBreed, Integer number) {
        return restTemplate.getForObject(DOG_BASE_URL + "/breed/" + breed + "/" + subBreed + "/images/random/" + number, ImagesResponseDto.class);
    }

    @Override
    public ImageResponseDto fetchImageByBreed(String breed) {
        return restTemplate.getForObject(DOG_BASE_URL + "/breed/" + breed + "/" + "/images/random" , ImageResponseDto.class);
    }

    @Override
    public ImageResponseDto fetchImageBySubBreed(String breed, String subBreed) {
        return restTemplate.getForObject(DOG_BASE_URL + "/breed/" + breed + "/" + subBreed + "/images/random", ImageResponseDto.class);

    }
}
