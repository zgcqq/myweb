package cn.chun.projs;

import java.io.InputStream;
import java.util.Properties;

/**
 * @author chun
 * @date 2022/9/15
 */
public class Config {
    public static Properties props = new Properties();

    static {
        InputStream inputStream = Config.class.getClassLoader().getResourceAsStream("app.properties");
        try {
            props.load(inputStream);
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("加载配置失败...");
        }
    }

    public Properties getProps(){
        return Config.props;
    }
}
