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

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AsteroidService {

    private static final Logger LOGGER = LogManager.getLogger(AsteroidService.class);
    private static final Gson gson = new Gson();
    private static final ElasticRequests elasticRequests = new ElasticRequests();

    public void postAllAsteroidsToDB() {
        int lastPage = getNumberOfLastPage();
        for (int i = 0; i <= lastPage; i++) {
            BulkRequest bulkRequest = new BulkRequest();
            try {
                JSONObject page = JsonReaderUtils.readJsonFromUrl("https://api.nasa.gov/neo/rest/v1/neo/browse?api_key=dDtvLh3RDWKEGMekL1bGBaZcDAbgT5Zjfc7aj6gD&page=" + i);
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
            JSONObject firstPage = JsonReaderUtils.readJsonFromUrl("https://api.nasa.gov/neo/rest/v1/neo/browse?api_key=dDtvLh3RDWKEGMekL1bGBaZcDAbgT5Zjfc7aj6gD");
            JSONObject pageableData = firstPage.getJSONObject("page");
            lastPage = pageableData.getInt("total_pages");
        } catch (JSONException | IOException e) {
            LOGGER.warn(e.getMessage());
        }
        return lastPage;
    }

    public static List<Asteroid> getAsteroidListByDate(LocalDate date) {
        List<Asteroid> asteroids = new ArrayList<>();
        try {
            JSONObject jsonPage = JsonReaderUtils.readJsonFromUrl("https://api.nasa.gov/neo/rest/v1/feed?start_date=" + date + "&end_date=" + date + "&api_key=dDtvLh3RDWKEGMekL1bGBaZcDAbgT5Zjfc7aj6gD");
            JSONObject datePage = jsonPage.getJSONObject("near_earth_objects");
            JSONArray dayRec = datePage.getJSONArray(date.toString());
            for (int i = 0; i <= dayRec.length(); i++) {
                Asteroid asteroid = gson.fromJson(dayRec.getJSONObject(i).toString(), Asteroid.class);
                asteroids.add(asteroid);
            }
        } catch (IOException | JSONException e) {
            LOGGER.warn(e.getMessage());
        }
        return asteroids;
    }
}
