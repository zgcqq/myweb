package cn.chun.projs.myweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @author 
 * @date 2022/8/12
 */
@Service
public class MysqlService {
    @Autowired
    DataSource dataSource;

    public void testConnection(){
        try {
            Connection cnn = dataSource.getConnection();
         } catch (Exception e){

        }
    }
}
