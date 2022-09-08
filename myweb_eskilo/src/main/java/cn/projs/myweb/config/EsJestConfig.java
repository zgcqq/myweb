package cn.projs.myweb.config;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 
 * @date 2022/9/6
 */
@Configuration
public class EsJestConfig {
    @Bean
    public JestClient jestClient(){
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig
                .Builder("http://localhost:9200").connTimeout(15000).readTimeout(30000)//连接es的配置
                .multiThreaded(true).build());

        return factory.getObject();
    }
}
