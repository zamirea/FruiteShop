package com.fruitshop.step_definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.* ;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class CRUD_StepDefinition {
    String createPath;
    Map<String, Object> newFruit = new LinkedHashMap<>();
    private Response response;

    private static int createdProductId;

    public static int getCreatedProductId(){
        return createdProductId;
    }

    private String updatePath;


    @Given("the vendor exist")
    public void the_vendor_exist() {
        RestAssured.baseURI = "https://api.predic8.de";
        RestAssured.basePath = "/shop";
        createPath = "/vendors/1/products/";


    }

    @When("I send a valid create product payload")
    public void i_send_a_valid_create_product_payload() {
        newFruit.put("name", "banana");
        newFruit.put("price",2);
        newFruit.put("category", "Fruit");

         response =
                 given()
                         .log().uri()
                         .header("Content-Type", ContentType.JSON)
                         .body(newFruit).
                         when()
                         .post(createPath)
                         .then().extract().response();
    }
    @Then("response status code should be {int}")
    public void response_status_code_should_be(int statusCode) {

        assertThat(response.statusCode(), is(statusCode));



    }
    @Then("create product response should be valid")
    public void create_product_response_should_be_valid() {
        assertThat(response.jsonPath().get("name"), is(newFruit.get("name")));
        assertThat(response.jsonPath().get("price"), is(newFruit.get("price")));
       String url = response.jsonPath().get("self_url");
        String[] arr = url.split("/");

        createdProductId = Integer.parseInt(arr[arr.length-1]);
    }


    @Given("the product ID was created")
    public void the_product_ID_was_created() {
        updatePath = "/products/" + getCreatedProductId();
        System.out.println(getCreatedProductId());
    }




    @When("send updated name and the price of the product")
    public void send_updated_name_and_the_price_of_the_product() {
        String patchBody ="{\n" +
                "    \"name\": \"Apple\",\n" +
                "    \"price\": 6\n" +
                "}"
                ;

        response =
                given()
                        .log().uri()
                        .header("Content-Type", ContentType.JSON)
                        .body(patchBody).
                        when()
                        .patch(updatePath)
                        .then().extract().response();


    }
    @Then("updated product response should be valid")
    public void updated_product_response_should_be_valid() {
        assertThat(response.jsonPath().get("name"), is("Apple"));
        assertThat(response.jsonPath().get("price"), is(6));

    }

    @When("Delete the created product")
    public void delete_the_created_product() {
       response= given()
                .log().uri()
                .pathParam("id", getCreatedProductId())
                .when()
                .delete("/products/{id}")
       ;
    }

}
