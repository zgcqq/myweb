package cn.projs.myweb.pojo.dao;

import cn.projs.myweb.pojo.po.Car;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author 
 * @date 2022/9/7
 */
public interface CarRepository extends ElasticsearchRepository<Car,Long> {
}
