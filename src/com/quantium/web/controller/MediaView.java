package com.quantium.web.controller;

import com.quantium.web.bean.view.PageContext;
import com.quantium.web.service.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by FREEMAN on 24.03.15.
 */
@Controller
public class MediaView extends Pool {

    @Autowired
    Services services;

    @RequestMapping(value = "/video/view/{videoId}")
    public String getVideoView(HttpServletRequest request, Model model, @PathVariable int videoId) {
        PageContext PC  = PageContext.newInstance(request, model);

        String languageCode     = PC.getLanguage().getCode();

        Services.media.videoViewUp(videoId, languageCode);
        model.addAttribute("video", Services.media.videoGet(videoId, languageCode));

        return PC.onlyAjax().setView("video", "view").render();
    }



}
