package br.com.sunnypay.bdd.config;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import jakarta.transaction.Transactional;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

@Transactional
public abstract class BaseStepDefinitions {
    @LocalServerPort
    protected int port;

    protected Response response;

    protected Map<String, String> headers = new HashMap<>();

    protected Map<String, String> queryParams = new HashMap<>();

    protected Object requestBody;

    protected void addHeader(String name, String value) {
        headers.put(name, value);
    }

    protected void setupJsonHeaders() {
        addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        addHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
    }

    protected void addAuthToken(String token) {
        addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }

    protected void addQueryParam(String name, String value) {
        queryParams.put(name, value);
    }

    protected void setRequestBody(Object body) {
        requestBody = body;
    }


    protected String getBaseUrl() {
        return "http://localhost";
    }

    protected void setupRestAssured() {
        RestAssured.baseURI = getBaseUrl();
        RestAssured.port = port;
    }

    protected void clearAll() {
        headers.clear();
        queryParams.clear();
        requestBody = null;
        response = null;
    }

    protected RequestSpecification givenConfig() {
        setupRestAssured();

        RequestSpecification request = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        for (Map.Entry<String, String> header : headers.entrySet()) {
            request.header(header.getKey(), header.getValue());
        }

        for (Map.Entry<String, String> param : queryParams.entrySet()) {
            request.queryParam(param.getKey(), param.getValue());
        }

        if (requestBody != null) {
            request.body(requestBody);
        }

        return request;
    }

    protected Response executeGet(String path) {
        response = givenConfig()
                .when()
                .get(path)
                .then()
                .extract()
                .response();
        return response;
    }

    protected Response executePost(String path) {
        response = givenConfig()
                .when()
                .post(path)
                .then()
                .extract()
                .response();
        return response;
    }
}
