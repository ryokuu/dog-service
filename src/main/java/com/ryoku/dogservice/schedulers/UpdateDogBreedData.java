package com.ryoku.dogservice.schedulers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ryoku.dogservice.domain.DogBreed;
import com.ryoku.dogservice.repository.DogBreedRepository;
import com.ryoku.dogservice.service.feign.FetchDogsDataFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component //secara default @component, @service, @controller merupakan singleton (hanya 1 bean instance yang dibuat)
@RequiredArgsConstructor
@Slf4j
public class UpdateDogBreedData {

    //auto inject dari lombok @RequiredArgsConstructor
    private final DogBreedRepository dogBreedRepository;
    private final FetchDogsDataFeignClient fetchDogsDataFeignClient;

    /*run setiap jam untuk mengupdate data dan hanya untuk data initialization
    supaya tidak perlu insert data manual setiap running application (h2)
     */

    @Scheduled(fixedRate = 3600000)
    public void updateDogData() throws JSONException, JsonProcessingException {
        String response = fetchDogsDataFeignClient.fetchAllDogsData().getBody();
        JsonNode jsonNode = new ObjectMapper().readValue(response, JsonNode.class);
        log.info("response ->{}", jsonNode.get("message"));
        JSONObject json = new JSONObject(response);

        Iterator<String> keys = json.getJSONObject("message").keys();
        keys.forEachRemaining(key -> {
            //key disini merupakan nama dogbreed dari hasil response
            List<String> subBreed = Arrays.asList(jsonNode
                    .get("message")
                    .get(key)
                    .toString()
                    .replace("[", "")
                    .replace("]", "")
                    .replace("\"", "")
                    .split(","));
            if (!dogBreedRepository.existsByName(key)){
                // id di set null, karna auto generated dari hibernate
                DogBreed savedDog = dogBreedRepository.save(new DogBreed(null, key, null, null ));
                subBreed.forEach(sub ->{
                    if (!sub.equals("")){
                        dogBreedRepository.save(new DogBreed(null, sub, savedDog.getId(), null));
                    }
                });

            }
        });
    }
}
