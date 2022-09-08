package cn.chun.projs.myweb.controller;

import cn.chun.projs.myweb.pojo.User;
import cn.chun.projs.myweb.service.HbaseService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author test@email

 * @date 2022/8/26
 */
@Slf4j
@RestController
public class HBaseController {
    @Autowired
    HbaseService hbaseService;

    @RequestMapping(value = "/hbase/createTable", method = RequestMethod.POST)
    public JSONObject getOne(@RequestBody JSONObject reqBody){
        JSONObject result = new JSONObject();
        try {
            String tableName = reqBody.getString("table");
            String colFamily = reqBody.getString("colFamily");
            hbaseService.createTable(tableName, colFamily);
            result.put("message", "success");
        } catch (IOException e) {
            e.printStackTrace();
            result.put("message", e.getMessage());
        }
        return result;
    }

    @RequestMapping(value="/hbase/putData", method = RequestMethod.POST)
    public JSONObject putData(@RequestBody JSONObject reqBody){
        JSONObject result = new JSONObject();
        try {
            String tableName = reqBody.getString("table");
            String colFamily = reqBody.getString("colFamily");
            String rowKey = reqBody.getString("rowKey");
            String column = reqBody.getString("column");
            String value = reqBody.getString("value");
            hbaseService.insertOrUpdate(tableName, rowKey, colFamily, column, value);
            result.put("message", "success");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", e.getMessage());
        }
        return result;
    }

    @RequestMapping(value="/hbase/getData", method = RequestMethod.GET)
    public JSONObject getData(@RequestParam(value = "table") String tableName,@RequestParam(value = "rowKey") String rowKey){
        JSONObject result = new JSONObject();
        try {
//            String tableName = reqBody.getString("table");
            String item = hbaseService.scanTable(tableName,rowKey);
            result.put("message", "success");
            result.put("data", item);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", e.getMessage());
        }
        return result;
    }

    @RequestMapping(value="/hbase/getAllData", method = RequestMethod.GET)
    public JSONObject getAllData(@RequestParam(value = "table") String tableName){
        JSONObject result = new JSONObject();
        try {
//            String tableName = reqBody.getString("table");
            JSONArray itemArr = hbaseService.scanAllTable(tableName);
            result.put("message", "success");
            result.put("data", itemArr);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", e.getMessage());
        }
        return result;
    }

}
