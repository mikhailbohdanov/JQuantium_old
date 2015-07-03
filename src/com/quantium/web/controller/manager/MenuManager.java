package com.quantium.web.controller.manager;

import com.quantium.web.bean.security.UserSecurity;
import com.quantium.web.bean.security.permissions.AccessManager;
import com.quantium.web.bean.view.PageContext;
import com.quantium.web.controller.CONFIG;
import com.quantium.web.service.authentication.UserService;
import com.quantium.web.util.UserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by FREEMAN on 13.03.15.
 */
@Controller
@RequestMapping("/" + ManagerGenerator.MAIN_PAGE + "/menu")
public class MenuManager implements CONFIG {

    @RequestMapping
    public String getMenuList(HttpServletRequest request, Model model) {
        UserSecurity me     = UserHelper.getMe();

        AccessManager manager = null;
        if (me == null || (manager = me.getAccessManager()) == null || !manager.hasAny("MENU"))
            return "redirect:/" + ManagerGenerator.MAIN_PAGE;

        PageContext PC = new PageContext(request, model);



        return "";
    }


}
