package com.ryoku.dogservice.web.mapper;

import com.ryoku.dogservice.domain.DogBreed;
import com.ryoku.dogservice.web.model.DogBreedDto;
import org.mapstruct.*;

import java.util.List;

@Mapper
@DecoratedWith(DogBreedMapperDecorator.class)
public interface DogBreedMapper {
    DogBreed toEntity(DogBreedDto dogBreedDto);

    DogBreedDto toDto(DogBreed dogBreed);

    DogBreedDto toCustomDto(DogBreed dogBreed);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DogBreed partialUpdate(DogBreedDto dogBreedDto, @MappingTarget DogBreed dogBreed);
}