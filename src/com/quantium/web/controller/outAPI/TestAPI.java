package com.quantium.web.controller.outAPI;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by FREEMAN on 22.04.15.
 */
@Controller
@RequestMapping("/api")
public class TestAPI {

    @RequestMapping(value = "/get")
    @ResponseBody
    public JSONObject getData(HttpServletRequest request) {
        JSONObject out = new JSONObject();

        out.put("var1", "value1");
        out.put("var2", "value2");

        return out;
    }


}
