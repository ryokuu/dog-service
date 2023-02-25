package com.ryoku.dogservice.repository;

import com.ryoku.dogservice.domain.DogBreed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DogBreedRepository extends JpaRepository<DogBreed, Integer> {

    boolean existsByName(String name);

    Optional<DogBreed> findByName(String name);

    List<DogBreed> findByParentId(Integer id);

    List<DogBreed> findByParentIdIsNull();

}