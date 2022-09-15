package cn.chun.projs.myweb;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 
 * @date 2022/8/12
 */
@Slf4j
@SpringBootApplication
@MapperScan("cn.chun.projs.myweb.mapper")
public class MyWebHbaseApp {
    public static void main(String[] args) {
        log.info("NgSearchApplication starting ...");
        SpringApplication.run(MyWebHbaseApp.class, args);
        log.info("NgSearchApplication started.");
    }
}

