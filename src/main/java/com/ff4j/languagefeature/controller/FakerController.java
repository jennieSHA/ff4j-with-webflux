package com.ff4j.languagefeature.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.ff4j.languagefeature.FeignInterface.FakerApiClient;
import com.ff4j.languagefeature.configuration.FeignConfig;
import com.ff4j.languagefeature.configuration.Ff4jConfig;
import com.ff4j.languagefeature.model.User;
import feign.Feign;
import feign.Target;
import feign.codec.Decoder;
import feign.codec.Encoder;
import lombok.extern.slf4j.Slf4j;
import org.ff4j.FF4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Import;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api")
@Slf4j
@Import(FeignConfig.class)
public class FakerController {


    private final FakerApiClient fakerApiClient;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private FF4j ff4j;

    private String lang;

    public FakerController(Decoder decoder, Encoder encoder) {
        this.fakerApiClient = Feign.builder().encoder(encoder).decoder(decoder).target(Target.EmptyTarget.create(FakerApiClient.class));
    }



    @GetMapping("/allUsers")
    public List<User> getUsers() throws IOException {


        if(ff4j.check(Ff4jConfig.FRENCH_LANG)){
            lang = "https://fakerapi.it/api/v1/persons?_locale=fr_FR";
            log.info("French******");
        }else {
            lang =  "https://fakerapi.it/api/v1/persons?_locale=en_US";
            log.info("ENGLISH******");
        }
        List<User> readValues = null;
        JsonNode result = fakerApiClient.getUsers(URI.create(lang)).get("data");
        ObjectReader reader = mapper.readerFor(new TypeReference<List<User>>() {
        });
        try {
            readValues = reader.readValue(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return readValues;
    }

}
