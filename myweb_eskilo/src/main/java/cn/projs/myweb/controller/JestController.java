package cn.projs.myweb.controller;

import cn.projs.myweb.pojo.po.Car;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @author 
 * @date 2022/9/6
 */
@Slf4j
@RestController
public class JestController {
    @Autowired
    JestClient jestClient;

    /**
     * 检索所有：
     * {
     *     "query":{
     *         "match_all":{}
     *     }
     * }
     *
     * @param reqBody
     * @return
     */
    @RequestMapping(value="/jest/search", method = RequestMethod.POST)
    public JSONObject jestSearch(@RequestBody JSONObject reqBody){
        JSONObject result = new JSONObject();
        try {
            String indexName = reqBody.getString("indexName");
            String searchJson = JSON.toJSONString(reqBody.getJSONObject("searchJson"));
            Search search = new Search.Builder(searchJson).addIndex(indexName).build();
            SearchResult resp = jestClient.execute(search);
            result.put("data", resp.toString());
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", e.getMessage());
        }
        return result;
    }
}
