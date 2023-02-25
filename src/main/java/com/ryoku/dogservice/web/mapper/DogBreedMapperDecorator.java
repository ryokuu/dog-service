package com.ryoku.dogservice.web.mapper;

import com.ryoku.dogservice.domain.DogBreed;
import com.ryoku.dogservice.repository.DogBreedRepository;
import com.ryoku.dogservice.service.ExternalDogBreedService;
import com.ryoku.dogservice.web.model.DogBreedDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class DogBreedMapperDecorator implements DogBreedMapper{

    //jika terdapat error bean not found, clean and compile maven

    @Autowired
    DogBreedMapper dogBreedMapper;

    @Autowired
    DogBreedRepository dogBreedRepository;

    @Autowired
    ExternalDogBreedService externalDogBreedService;

    @Override
    public DogBreedDto toDto(DogBreed dogBreed) {
        DogBreedDto dogBreedDto = dogBreedMapper.toDto(dogBreed);
        if (dogBreed.getParentId()==null){
            dogBreedDto.setImageUrl(externalDogBreedService.fetchImageByBreed(dogBreed.getName()).getMessage());
        }
        return getDogBreedDto(dogBreed, dogBreedDto);
    }

    @Override
    public DogBreedDto toCustomDto(DogBreed dogBreed) {
        DogBreedDto dogBreedDto = dogBreedMapper.toDto(dogBreed);

        return getDogBreedDto(dogBreed, dogBreedDto);
    }

    private DogBreedDto getDogBreedDto(DogBreed dogBreed, DogBreedDto dogBreedDto) {
        List<DogBreed> subBreed = dogBreedRepository.findByParentId(dogBreed.getId());
        if (!subBreed.isEmpty()){
            dogBreedDto.setSubBreed(subBreed.stream().map(DogBreed::getName).collect(Collectors.toList()));
        }else {
            dogBreedDto.setSubBreed(Collections.emptyList());
        }

        return dogBreedDto;
    }
}
