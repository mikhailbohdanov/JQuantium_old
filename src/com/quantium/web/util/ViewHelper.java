package com.quantium.web.util;

import com.quantium.web.bean.Url;
import com.quantium.web.bean.view.PageContext;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by FREEMAN on 26.03.15.
 */
public class ViewHelper {

    public static PageContext initContext(HttpServletRequest request, Model model) {
        return initContext(request, null, model, null);
    }
    public static PageContext initContext(HttpServletRequest request, HttpServletResponse response, Model model) {
        return initContext(request, response, model, null);
    }

    public static PageContext initContext(HttpServletRequest request, Model model, Url url) {
        return initContext(request, null, model, url);
    }
    public static PageContext initContext(HttpServletRequest request, HttpServletResponse response, Model model, Url url) {
        PageContext PC  = new PageContext(request, response, model);

        PC.setLanguage();

        if (url == null)
            PC.setUrl(new Url(request));
        else
            PC.setUrl(url);

        return PC;
    }

}
