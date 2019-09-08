package com.gt.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * JsonHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/8/25.
 */
public class JsonHelper {

    private JsonHelper() {
    }


    /**
     * Obj to jsonstr.
     *
     * @param obj the obj
     * @return the string
     */
    public static String obj2JsonStr(Object obj) {

        String jsonStr = null;
        if (obj != null) {
            jsonStr = JSON.toJSONString(obj);
        }
        return jsonStr;
    }

    /**
     * Obj 2 json str with date format.
     *
     * @param obj the obj
     * @return the string
     */
    public static String obj2JsonStrWithDateFormat(Object obj) {

        String jsonStr = null;
        if (obj != null) {
            //jsonStr = JSON.toJSONStringWithDateFormat(obj, DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond.getFormat());
        }
        return jsonStr;
    }

    /**
     * Jsonstr to obj.
     *
     * @param <T>     the type parameter
     * @param jsonStr the json str
     * @param clz     the clz
     * @return the t
     */
    public static final <T> T jsonStr2Obj(String jsonStr, Class<T> clz) {

        T model = null;
        if (!StringUtils.isEmpty(jsonStr) && clz != null) {
            model = JSON.parseObject(jsonStr, clz);
        }
        return model;
    }

    /**
     * Json array str 2 list.
     *
     * @param <T>          the type parameter
     * @param jsonArrayStr the json array str
     * @param clazz        the clazz
     * @return the list
     */
    public static final <T> List<T> jsonArrayStr2List(String jsonArrayStr, Class<T> clazz) {

        return JSON.parseArray(jsonArrayStr, clazz);
    }

    /**
     * Json str 2 json obj.
     *
     * @param jsonStr the json str
     * @return the jSON object
     */
    public static final JSONObject jsonStr2JsonObj(String jsonStr) {
        return JSON.parseObject(jsonStr);
    }

    /**
     * Json str 2 json map.
     *
     * @param jsonStr
     * @param clazz
     * @param <T>
     * @return
     */
    public static final <T extends Object> Map<String, T> jsonStr2MapObj(String jsonStr, Class<T> clazz) {

        Map<String, T> tMap = new HashMap<String, T>();

        JSONObject jsonMap = JSONObject.parseObject(jsonStr);

        for (String key : jsonMap.keySet()) {

            T t = (T) jsonMap.parseObject(jsonMap.get(key).toString(), clazz);

            tMap.put(key, t);
        }

        return tMap;
    }
}
