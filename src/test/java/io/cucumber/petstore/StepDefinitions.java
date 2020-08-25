package io.cucumber.petstore;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.petstore.restassuredClient.*;
import io.restassured.response.Response;
import org.junit.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static io.cucumber.petstore.Context.PET_OBJECT;

public class StepDefinitions {

    TestContext testContext;

    public StepDefinitions(TestContext testContext) {
        this.testContext = testContext;
    }

    private PetDto.PetDetails createdPetData;

    private PetDto.PetDetails petTesObject = PetDto.PetDetails.builder()
            .id(Math.abs(new Random().nextInt()))
            .category(new CategoryDto.CategoryDetails(1, "dogs"))
            .name("Vino")
            .photoUrls(new ArrayList<>(
                    Collections.singletonList("")))
            .tags(new ArrayList<>(
                    Collections.singletonList(new TagDto.TagDetails(1, "labrador"))))
            .status(Status.available)
            .build();

    @Given("^I add a pet to store$")
    public void iHaveGivenPet() throws IOException {
        Response response = PetApiClient.postPet(petTesObject);
        setResponseContext(response);
        createdPetData = convertResponseToObject(response);
    }

    @Then("^Verify pet successfully added$")
    public void thePetShouldBeAvailable() throws IOException {
        Response responseContext = getResponseContext();

        Assert.assertEquals(200, responseContext.getStatusCode());
        Response getPetResp = PetApiClient.getPet(createdPetData.getId());
        PetDto.PetDetails petDetails = convertResponseToObject(getPetResp);
        Assert.assertEquals(200, getPetResp.getStatusCode());
        Assert.assertEquals("Vino", petDetails.getName());
    }

    @When("^I change availability status$")
    public void iChangeAvailabilityStatus() throws Throwable {
        petTesObject.setStatus(Status.sold);
        Response response = PetApiClient.updatePet(petTesObject);
        Assert.assertEquals(200, response.getStatusCode());
        setResponseContext(response);
    }

    @Then("^Verify pet successfully updated$")
    public void verifyPetSuccessfullyUpdated() throws Throwable {
        PetDto.PetDetails petDetails = convertResponseToObject(getResponseContext());
        Assert.assertEquals(Status.sold, petDetails.getStatus());
    }

    @When("^I delete pet$")
    public void iDeletePet() {
        Response deletePetResp = PetApiClient.deletePet(createdPetData.getId());
        Assert.assertEquals(200, deletePetResp.getStatusCode());
    }

    @Then("^Verify pet successfully deleted$")
    public void verifyPetSuccessfullyDeleted() {
        Response getPatResp = PetApiClient.getPet(createdPetData.getId());
        Assert.assertEquals(404, getPatResp.getStatusCode());
    }

    private PetDto.PetDetails convertResponseToObject(Response lastResponse) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(lastResponse.getBody().print(), PetDto.PetDetails.class);
    }

    private void setResponseContext(Response lastResponse) {
        testContext.scenarioContext.setContext(PET_OBJECT, lastResponse);
    }

    private Response getResponseContext() {
        return (Response) testContext.getScenarioContext().getContext(PET_OBJECT);
    }
}
