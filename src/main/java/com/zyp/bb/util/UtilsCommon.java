package com.zyp.bb.util;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by skygreen on 2017/8/21.
 */
public class UtilsCommon {
    private static final Logger logger = LoggerFactory.getLogger(UtilsCommon.class);
    public static boolean isJsonString(String jsonStr) {
        if (StringUtils.isBlank(jsonStr)) {
            return false;
        }
        try {
            new JSONObject(jsonStr);
            return true;
        } catch (JSONException e) {
//            e.printStackTrace();
            logger.error("bad json: " + jsonStr);
            return false;

        }
    }

    /**
     * 返回当前时间
     * @return
     */
    public static String now(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String currentTime = df.format(new Date());
        System.out.println(currentTime);// new Date()为获取当前系统时间
        return currentTime;
    }

}
