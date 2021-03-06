package io.cucumber.petstore.restassuredClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class PetApiClient {
    static {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    public static Response postPet(PetDto.PetDetails pet) throws JsonProcessingException {
        ObjectMapper jacksonObjectMapper = new ObjectMapper();
        String bodyString = jacksonObjectMapper.writeValueAsString(pet);
        return RestAssured.given()
                .contentType("application/json")
                .body(bodyString)
                .when()
                .post("/pet");
    }

    public static Response getPet(Integer id) {
        return RestAssured.given()
                .contentType("application/json")
                .when()
                .get("/pet/{id}", id);
    }

    public static Response updatePet(PetDto.PetDetails pet) throws JsonProcessingException {
        ObjectMapper jacksonObjectMapper = new ObjectMapper();
        String bodyString = jacksonObjectMapper.writeValueAsString(pet);
        return RestAssured.given()
                .contentType("application/json")
                .body(bodyString)
                .when()
                .put("/pet");
    }

    public static Response deletePet(Integer petId) {
        Response request = RestAssured.given()
                .contentType("application/json")
                .when()
                .delete("/pet/{id}", petId);
        return request;
    }

}