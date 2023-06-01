package com.ff4j.languagefeature.FeignInterface;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

import java.net.URI;

@FeignClient(name="faker-api-service")

public interface FakerApiClient {

  //  @RequestMapping(method = RequestMethod.GET)
    @RequestLine(value = "GET")
    @JsonProperty("data")
    JsonNode getUsers(URI uri);

}
