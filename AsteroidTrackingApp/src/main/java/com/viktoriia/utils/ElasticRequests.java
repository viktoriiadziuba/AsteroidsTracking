package com.viktoriia.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viktoriia.config.ElasticsearchConfig;
import com.viktoriia.domain.Asteroid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ElasticRequests {

    private static final Logger LOGGER = LogManager.getLogger(ElasticRequests.class);
    private static final String INDEX = "asteroid";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public IndexRequest makeIndexRequest(Asteroid asteroid) {
        return new IndexRequest(INDEX).id(asteroid.getNeoReferenceId()).
                source(objectMapper.convertValue(asteroid, Map.class));
    }

    public void bulkInsert(BulkRequest bulkRequest) {
        try {
            ElasticsearchConfig.client().bulk(bulkRequest, RequestOptions.DEFAULT);
            ElasticsearchConfig.client().close();
        } catch (IOException e) {
            LOGGER.warn(e.getMessage());
        }
    }

    public static Asteroid getById(String id) {
        Asteroid asteroid = null;
        GetRequest getRequest = new GetRequest(INDEX, id);
        try {
            GetResponse getResponse = ElasticsearchConfig.client().get(getRequest, RequestOptions.DEFAULT);
            asteroid = objectMapper.convertValue(getResponse.getSourceAsMap(), Asteroid.class);
        } catch (IOException e) {
            LOGGER.warn(e.getMessage());
        }
        if (asteroid == null) {
            throw new NullPointerException("Asteroid with id: " + id + "is absent");
        }
        return asteroid;
    }

    public static List<Asteroid> getListByDate(LocalDate date) {
        List<Asteroid> asteroidList = new ArrayList<>();
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("closeApproachData.closeApproachDate", date));
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(100);
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse searchResponse = ElasticsearchConfig.client().search(searchRequest, RequestOptions.DEFAULT);
            asteroidList = getSearchResult(searchResponse);
        } catch (IOException e) {
            LOGGER.warn(e.getMessage());
        }
        return asteroidList;
    }

    private static List<Asteroid> getSearchResult(SearchResponse response) {
        SearchHit[] searchHit = response.getHits().getHits();
        List<Asteroid> profileDocuments = new ArrayList<>();

        if (searchHit.length > 0) {
            Arrays.stream(searchHit)
                    .filter(Objects::nonNull)
                    .map(hit -> profileDocuments.add(objectMapper.convertValue(hit.getSourceAsMap(), Asteroid.class)))
                    .collect(Collectors.toList());
        }
        return profileDocuments;
    }
}
