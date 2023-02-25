package com.ryoku.dogservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dog_breeds")
public class DogBreed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;
    //jika parentId tidak null, berarti merupakan subBreed
    private Integer parentId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dogBreed")
    private List<DogBreedImages> dogBreedImages;

}
