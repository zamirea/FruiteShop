package com.fruitshop.step_definitions;

import com.fruitshop.utilities.ConfigurationReader;
import static io.restassured.RestAssured.*;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class BaseTest {


    @BeforeAll
    public static void setup(){
       baseURI = "https://api.predic8.de";
       basePath = "/shop";
    }

    @AfterAll
    public static void close(){
        reset();
    }



    }

