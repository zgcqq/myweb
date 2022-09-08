package cn.projs.myweb.service;

import cn.projs.myweb.pojo.dao.CarRepository;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author 
 * @date 2022/9/6
 */

@Service
public class CarService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    public JSONArray getAllCars(){
        JSONArray result = new JSONArray();
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("cars");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = searchResponse.getHits();
            SearchHit[] hitsHits = hits.getHits();
            for (SearchHit hit : hitsHits) {
                String sourceAsString = hit.getSourceAsString();
                result.add(sourceAsString);
            }
            return result;
        } catch (Exception e){
            e.printStackTrace();
            return new JSONArray();
        }
    }
}
