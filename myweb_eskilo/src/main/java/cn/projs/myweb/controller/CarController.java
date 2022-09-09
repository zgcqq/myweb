package cn.projs.myweb.controller;

import cn.projs.myweb.lib.Utils;
import cn.projs.myweb.pojo.dao.CarRepository;
import cn.projs.myweb.pojo.po.Car;
import cn.projs.myweb.service.CarService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 
 * @date 2022/9/6
 */
@Slf4j
@RestController
public class CarController {

    @Autowired
    CarService carService;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @RequestMapping("/cars/getAll")
    public List<Car> getAll(){
        System.out.println("pre");
        List<Car> result = carService.getAllCars();
        System.out.println("post");
        return result;
    }

    @RequestMapping(value="/cars/add", method = RequestMethod.POST)
    public Car add(@RequestBody JSONObject reqBody) {
        Car car = null;
        try {
            car = new Car();
            car.setId(Utils.getIdByUUID());
            car.setColor(reqBody.getString("color"));
            car.setMake(reqBody.getString("make"));
            car.setPrice(reqBody.getString("price"));
            car.setSold(Utils.getCurrentDate("yyyy-MM-dd"));
            System.out.println("car: " + JSON.toJSONString(car));
            carRepository.save(car);
        } catch (Exception e){
            if(!(e.getMessage()).contains("Created")) {
                e.printStackTrace();
            }
        }
        return car;
    }

    @RequestMapping(value="/cars/paginationAll", method = RequestMethod.GET)
    public JSONObject paginationAll(@RequestParam(value = "page") int page,@RequestParam(value = "pageNum") int pageNum) {
        JSONObject result = new JSONObject();
        try {
            SearchRequest searchRequest = new SearchRequest();
            //索引名称
            searchRequest.indices("cars");
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            //根据ID进行排序
            sourceBuilder.sort("price", SortOrder.DESC);
            //分页
            sourceBuilder.from(pageNum*page);
            sourceBuilder.size(pageNum);
            BoolQueryBuilder query = new BoolQueryBuilder();
            sourceBuilder.query(query);

            QueryBuilder queryBuilder = null;
            //各种查询
            queryBuilder = QueryBuilders.matchAllQuery();
            //queryBuilder = QueryBuilders.termQuery("", "");
            //queryBuilder = QueryBuilders.matchQuery("", "");
            //queryBuilder = QueryBuilders.rangeQuery("");
            //queryBuilder = QueryBuilders.existsQuery("");
            //匹配策略
            query.must(queryBuilder);
            //query.mustNot(queryBuilder);
            //query.should(queryBuilder);
            searchRequest.source(sourceBuilder);
            SearchResponse rp = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            Long totalCount = rp.getHits().getTotalHits().value;
            result.put("total", rp.getHits().getTotalHits());
            if (totalCount > 0) {
                JSONArray recordInfoList = new JSONArray();
                for (SearchHit searchHit : rp.getHits()) {

                    JSONObject record = JSON.parseObject(JSON.toJSONString(searchHit));
                    recordInfoList.add(record);
                }
                result.put("list", recordInfoList);
            }

        } catch (Exception e) {
            log.error("查询失败", e);
        }
        return result;
    }

    @RequestMapping(value="/cars/findAll", method = RequestMethod.GET)
    public List<Car> findAll() {
        List<Car> result = new ArrayList<>();
        // 查询全部，并安装价格降序排序
        Iterable<Car> cars = this.carRepository.findAll(Sort.by("price").descending());
        for (Car car : cars) {
            result.add(car);
        }
        return result;
    }

    @RequestMapping(value="/cars/agg", method = RequestMethod.GET)
    public JSONObject agg(){
        JSONObject result = new JSONObject();
        return result;
    }
}
