package com.quantium.web.controller.social;

import com.quantium.web.controller.Pool;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by FREEMAN on 29.09.14.
 */
@Controller
public class Photos extends Pool {

    @RequestMapping(value = "/album{userId:\\d{1,10}}")
    public String getAlbums(HttpServletRequest request, Model model, @PathVariable int userId){

        ModelAndView mv = new ModelAndView();



        return "";
    }

    @RequestMapping(value = "/album{userId:\\d{1,10}}_{albumId:\\d{1,10}}")
    public String getAlbum(HttpServletRequest request, Model model, @PathVariable int userId, @PathVariable int albumId){

        return "";
    }

    @RequestMapping(value = "/photo{userId:\\d{1,10}}_{photoId:\\d{1,10}}")
    public String getPhoto(HttpServletRequest request, Model model, @PathVariable int userId, @PathVariable int albumId){

        return "";
    }

    @RequestMapping(value = "/createAlbum", method = RequestMethod.GET)
    public String createAlbum(HttpServletRequest request, Model model){

        return "";
    }

    @RequestMapping(value = "/createAlbum", method = RequestMethod.POST)
    public String createAlbum(HttpServletRequest request){

        return "";
    }

    //TODO upload photos
}
