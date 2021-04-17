package com.fruitshop.step_definitions;

import io.cucumber.java.sl.In;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;
import java.util.LinkedHashMap;
import java.util.Map;

public class Fruit_Shop_API_Practice extends BaseTest{

    public int productID;

    @Test
    public void Add_a_product_to_a_vendor(){
        Map<String, Object> newFruit = new LinkedHashMap<>();
        newFruit.put("name", "banana");
        newFruit.put("price", 1.29);
        newFruit.put("category", "Fruit");

     //Post    /vendors/{id}/products/
     String url =
        given()
                .header("Content-Type", ContentType.JSON)
                .pathParam("vendorID", 10)
                .body(newFruit)
                .log().all().
        when()
                .post("/vendors/{vendorID}/products/").
        then()
                .statusCode(201)
                .log().all()
                .body("name",is("banana"))
                .body("price", is(1.29F))
                .extract().jsonPath()
                .get("self_url")


                ;
        String[] arr = url.split("/");

        productID = Integer.parseInt(arr[3]);
        System.out.println("productID = " + productID);

    }

    //PATCH /products/{id}
    @Test
    public void update_a_product(){


        String patchBody ="{\n" +
                "    \"name\": \"Apple\",\n" +
                "    \"price\": 5.99\n" +
                "}"
                ;
     given()
             .contentType(ContentType.JSON)
             .log().all()
             .pathParam("productID", 592)
             .body(patchBody)
             .when()
             .patch("/products/{productID}").then()
             .log().all()
             .statusCode(200)
             .body("name", is("Apple"));

    }
    @Test
    public void get_list_of_product(){

        given()
                .contentType(ContentType.JSON)
                .log().all().when()
                .get("/products/")
                .then()
                .statusCode(200)
                .log().all();

    }

    @Test
    public void add_a_photo_of_a_product(){
      //  PUT /products/{id}/photo



    }
}
