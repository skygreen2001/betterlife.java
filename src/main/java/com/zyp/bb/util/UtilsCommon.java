package com.zyp.bb.util;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

//    public static void main(String[] args){
//        String message ="aaaaa";
//        if (UtilsCommon.isJsonString(message)) {
//            System.out.println("ok");
//        }else{
//            System.out.println("bad");
//        }
//        message ="{'aaaa':'dddd'}";
//        if (UtilsCommon.isJsonString(message)) {
//            System.out.println("ok1");
//        }else{
//            System.out.println("bad1");
//        }
//        message ="{\"access_token\":\"$2a$10$ScndhRMpszcSN90SsA9OFe3W9S1JxLvkPtTr7abXusiykOQ4drnHS\",\"user_id\":1320}";
//        if (UtilsCommon.isJsonString(message)) {
//            System.out.println("ok2");
//        }else{
//            System.out.println("bad2");
//        }
//    }
}
