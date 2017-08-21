package com.zyp.bb.util;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

/**
 * Created by skygreen on 2017/8/21.
 */
public class UtilsCommon {
    public static boolean isJsonString(String jsonStr) {
        Object obj = jsonStr;
        if (StringUtils.isBlank(jsonStr)) {
            return false;
        }
        if (obj instanceof JSONObject) {
            return true;
        } else {
            return false;
        }
    }
}
