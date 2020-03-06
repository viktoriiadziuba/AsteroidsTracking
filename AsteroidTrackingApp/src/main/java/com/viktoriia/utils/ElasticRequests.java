package com.viktoriia.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viktoriia.config.ElasticsearchConfig;
import com.viktoriia.domain.Asteroid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;

import java.io.IOException;
import java.util.Map;

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
}
