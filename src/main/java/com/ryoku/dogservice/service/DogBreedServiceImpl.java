package com.ryoku.dogservice.service;

import com.ryoku.dogservice.domain.DogBreed;
import com.ryoku.dogservice.repository.DogBreedRepository;
import com.ryoku.dogservice.web.exception.NotFoundException;
import com.ryoku.dogservice.web.mapper.DogBreedMapper;
import com.ryoku.dogservice.web.model.DogBreedDto;
import com.ryoku.dogservice.web.model.RequestDogBreedDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DogBreedServiceImpl implements DogBreedService {

    //auto inject dependency dari lombok @RequiredArgsConstructor
    private final DogBreedRepository dogBreedRepository;

    private final DogBreedMapper dogBreedMapper;

    @Override
    public List<DogBreedDto> fetchAllDogs() {
        List<DogBreed> fetchBreed = dogBreedRepository.findByParentIdIsNull();
        return fetchBreed.stream().map(dogBreedMapper::toCustomDto).collect(Collectors.toList());
    }

    @Override
    public DogBreedDto saveNewDog(RequestDogBreedDto requestDogBreedDto) {

        return dogBreedMapper.toDto(dogBreedRepository.save(dogBreedMapper.toEntity(DogBreedDto.builder()
                .name(requestDogBreedDto.getName()).build())));
    }

    @Override
    public DogBreedDto saveSubBreedDog(RequestDogBreedDto requestDogBreedDto, String breed) {

        Optional<DogBreed> fetchParentDog = dogBreedRepository.findByName(breed);
        DogBreedDto newSubDog = DogBreedDto.builder()
                .name(requestDogBreedDto.getName())
                .build();

        fetchParentDog.ifPresent(dog-> newSubDog.setParentId(dog.getId()));

        return dogBreedMapper.toDto(dogBreedRepository.save(dogBreedMapper.toEntity(newSubDog)));
    }

    @Override
    public DogBreedDto updateDog(Integer id, RequestDogBreedDto requestDogBreedDto) {

        Optional<DogBreed> fetchDogBreed = dogBreedRepository.findById(id);
        if (fetchDogBreed.isPresent()){
            fetchDogBreed.get().setName(requestDogBreedDto.getName());
            return dogBreedMapper.toDto(fetchDogBreed.get());
        }else {
            throw new NotFoundException("dog with id " + id + "not found!");
        }
    }

    @Override
    public void deleteDog(Integer id) {

        Optional<DogBreed> fetchDogBreed = dogBreedRepository.findById(id);
        fetchDogBreed.ifPresentOrElse(dogBreedRepository::delete,
                () -> {
                    throw new NotFoundException("dog with id " + id + "not found!");
                });
    }

    @Override
    public DogBreedDto getDog(String name) {
        return dogBreedMapper.toDto(dogBreedRepository.findByName(name).orElseThrow(NotFoundException::new));
    }
}
