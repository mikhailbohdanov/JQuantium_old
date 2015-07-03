package com.quantium.web.controller.outAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by FREEMAN on 05.03.15.
 */
@Controller
@RequestMapping("/api/location")
public class Location {

    //TODO сделать этот функционал

    @RequestMapping("/test")
    @ResponseBody
    public String getTest() {
        return "{\"length\":3,\"items\":[{\"id\":1,\"name\":\"Украина\"},{\"id\":\"Россия\"},\"id\":3,\"name\":\"США\"}]}";
    }

}
