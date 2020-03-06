package com.viktoriia.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

public class ElasticsearchConfig {

    public static RestHighLevelClient client() {
        return new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));
    }
}
