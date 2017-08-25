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

}
