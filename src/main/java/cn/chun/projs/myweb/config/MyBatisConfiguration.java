package cn.chun.projs.myweb.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author test@email

 * @date 2022/8/12
 */
@Configuration
@MapperScan("cn.chun.projs.myweb.mapper")
public class MyBatisConfiguration {
}
