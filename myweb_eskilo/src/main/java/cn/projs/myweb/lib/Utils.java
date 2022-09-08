package cn.projs.myweb.lib;

import java.text.SimpleDateFormat;
import java.util.UUID;

/**
 * @author 
 * @date 2022/9/7
 */
public class Utils {
    public static String getCurrentDateTime(String patterStr){
        SimpleDateFormat df = new SimpleDateFormat(patterStr);
        return df.format(System.currentTimeMillis());
    }

    public static String getCurrentDate(String patterStr){
        SimpleDateFormat df = new SimpleDateFormat(patterStr);
        return df.format(System.currentTimeMillis());
    }

    public static String getIdByUUID() {
        String uuIdStr = UUID.randomUUID().toString();

        return uuIdStr.replaceAll("-", "");
    }
}
