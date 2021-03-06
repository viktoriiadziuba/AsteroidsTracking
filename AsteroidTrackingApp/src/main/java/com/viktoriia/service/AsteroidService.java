package com.viktoriia.service;

import com.google.gson.Gson;
import com.viktoriia.domain.Asteroid;
import com.viktoriia.utils.ElasticRequests;
import com.viktoriia.utils.JsonReaderUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Properties;

public class AsteroidService {

    private static final Logger LOGGER = LogManager.getLogger(AsteroidService.class);
    private static final Gson gson = new Gson();
    private static final ElasticRequests elasticRequests = new ElasticRequests();
    private static final String PROP_FILE_NAME = "application.properties";
    private static final String API_URL = "https://api.nasa.gov/neo/rest/v1";
    private final String apiKey = this.getApiKey();

    public String getApiKey() {
        InputStream inputStream;
        String key = "";
        try {
            Properties prop = new Properties();
            inputStream = getClass().getClassLoader().getResourceAsStream(PROP_FILE_NAME);
            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("Property file '" + PROP_FILE_NAME + "' not found in the classpath");
            }
            key = prop.getProperty("api_key");
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
        }
        return key;
    }

    public void postAllAsteroidsToDB() {
        int lastPage = getNumberOfLastPage();
        for (int i = 0; i <= lastPage; i++) {
            BulkRequest bulkRequest = new BulkRequest();
            try {
                JSONObject page = JsonReaderUtils.readJsonFromUrl(API_URL + "/neo/browse?api_key=" + apiKey + "&page=" + i);
                JSONArray list = page.getJSONArray("near_earth_objects");
                for (int j = 0; j < list.length(); j++) {
                    Asteroid asteroid = gson.fromJson(list.getJSONObject(j).toString(), Asteroid.class);

                    IndexRequest indexRequest = elasticRequests.makeIndexRequest(asteroid);
                    bulkRequest.add(indexRequest);
                }
            } catch (IOException | JSONException ex) {
                LOGGER.warn(String.format("Getting data from NASA endpoint stopped on page: %s", i));
                break;
            }
            elasticRequests.bulkInsert(bulkRequest);
        }
    }

    public int getNumberOfLastPage() {
        int lastPage = 0;
        try {
            JSONObject firstPage = JsonReaderUtils.readJsonFromUrl(API_URL + "/neo/browse?api_key=" + apiKey);
            JSONObject pageableData = firstPage.getJSONObject("page");
            lastPage = pageableData.getInt("total_pages");
        } catch (JSONException | IOException e) {
            LOGGER.warn(e.getMessage());
        }
        return lastPage;
    }

    public void getAsteroidListByDate(LocalDate date) {
        BulkRequest bulkRequest = new BulkRequest();
        try {
            JSONObject jsonPage = JsonReaderUtils.readJsonFromUrl(API_URL + "/feed?start_date=" + date + "&end_date=" + date + "&api_key=" + apiKey);
            JSONObject datePage = jsonPage.getJSONObject("near_earth_objects");
            JSONArray dayRec = datePage.getJSONArray(date.toString());
            for (int i = 0; i <= dayRec.length(); i++) {
                Asteroid asteroid = gson.fromJson(dayRec.getJSONObject(i).toString(), Asteroid.class);

                IndexRequest indexRequest = elasticRequests.makeIndexRequest(asteroid);
                bulkRequest.add(indexRequest);
            }
        } catch (IOException | JSONException e) {
            LOGGER.warn(e.getMessage());
        }
        elasticRequests.bulkInsert(bulkRequest);
    }
}
