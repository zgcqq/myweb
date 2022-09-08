package cn.projs.myweb;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author 
 * @date 2022/8/31
 */
//@Slf4j
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class MyWebEskiloApp {

    public static void main(String[] args) {
        try {
            System.out.println("NgSearchApplication starting ...");
//            System.setProperty("spring.devtools.restart.enabled", "false");
            SpringApplication.run(MyWebEskiloApp.class, args);
            System.out.println("NgSearchApplication started.");
        } catch (Throwable e){
            e.printStackTrace();
        }
    }
}
