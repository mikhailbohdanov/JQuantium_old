package com.quantium.web.bean.core;

import org.json.simple.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by FREEMAN on 25.10.14.
 */
public class AjaxResponse extends Response {

    public AjaxResponse() {
        super();
    }
    public AjaxResponse(HttpServletRequest request) {
        super(request);
    }

    public JSONObject getReturn() {
        JSONObject out = super.getReturn();

        if (out == null)
            out = new JSONObject();
        else if (!out.containsKey("errors"))
            out.put("complete", true);

        return out;
    }

}
