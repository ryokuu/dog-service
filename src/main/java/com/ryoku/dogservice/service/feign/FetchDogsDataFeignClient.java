package com.ryoku.dogservice.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*
    Merupakan alternatif dari RestTemplate.
    bisa juga digunakan untuk berhubungan dengan service lain
    hanya dengan service name sesuai yang terdaftar pada
    Eureka Discovery Client
 */
@FeignClient(url = "https://dog.ceo", name = "external-dog-service")
public interface FetchDogsDataFeignClient {

    String FETCH_ALL_DOGS_PATH = "/api/breeds/list/all";

    @RequestMapping(method = RequestMethod.GET, value = FETCH_ALL_DOGS_PATH)
    ResponseEntity<String> fetchAllDogsData();


}
