package io.cucumber.petstore.restassuredClient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public enum Status {
    available,
    pending,
    sold
}
