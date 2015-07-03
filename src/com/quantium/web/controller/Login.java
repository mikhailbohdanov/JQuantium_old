package com.quantium.web.controller;

import com.quantium.web.bean.view.PageContext;
import com.quantium.web.util.UserHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by FREEMAN on 22.10.14.
 */
@Controller
@RequestMapping("/login")
public class Login extends Pool {


    @RequestMapping
    public String getForm(HttpServletRequest request, Model model) {
        if (UserHelper.isAuthorized())
            return "redirect:/" + (PageContext.hasAjax(request) ? "?_ajax=true" : null);

        PageContext PC = new PageContext(request, model);
        PC
                .setLanguage()
                .setView("signin", "form");



        return PC.render();
    }


}
