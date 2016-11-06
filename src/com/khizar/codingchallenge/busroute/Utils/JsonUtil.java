package com.khizar.codingchallenge.busroute.Utils;

import com.google.gson.Gson;
import spark.ResponseTransformer;

/**
 * Created by khizar on 06.11.16.
 */
public class JsonUtil {

    public static String toJson(Object object) {
        return new Gson().toJson(object);
    }

    public static ResponseTransformer json() {
        return JsonUtil::toJson;
    }

}
