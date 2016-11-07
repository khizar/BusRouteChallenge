package com.khizar.codingchallenge.busroute.Application;

import com.google.gson.Gson;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import spark.Spark;
import spark.utils.IOUtils;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;



/**
 * Created by khizar on 06.11.16.
 */
public class ApplicationTest {

    @BeforeClass
    public static void beforeClass() {
        Application.main(new String []{"src/test/data/test-data.txt"});
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void afterClass() {
        Spark.stop();
    }

    @Test
    public void aDirectRouteShouldBeAvailable() {
        TestResponse res = request("GET", "/api/direct?dep_sid=3&arr_sid=6");
        Map<String, String> json = res.json();
        assertEquals(200, res.status);
        assertEquals("3", json.get("dep_sid"));
        assertEquals("6", json.get("arr_sid"));
        assertEquals(true, json.get("direct_bus_route"));
    }

    @Test
    public void aDirectRouteShouldNotBeAvailable() {
        TestResponse res = request("GET", "/api/direct?dep_sid=245&arr_sid=6");
        Map<String, String> json = res.json();
        assertEquals(200, res.status);
        assertEquals("245", json.get("dep_sid"));
        assertEquals("6", json.get("arr_sid"));
        assertEquals(false, json.get("direct_bus_route"));
    }


    private TestResponse request(String method, String path) {
        try {
            URL url = new URL("http://localhost:8088" + path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setDoOutput(true);
            connection.connect();
            String body = IOUtils.toString(connection.getInputStream());
            return new TestResponse(connection.getResponseCode(), body);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Sending request failed: " + e.getMessage());
            return null;
        }
    }

    private static class TestResponse {

        private final String body;
        private final int status;

        public TestResponse(int status, String body) {
            this.status = status;
            this.body = body;
        }

        @SuppressWarnings("unchecked")
        public Map<String, String> json() {
            return new Gson().fromJson(body, HashMap.class);
        }
    }

}

