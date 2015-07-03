package com.quantium.web.controller.manager;

import com.quantium.web.bean.Url;
import com.quantium.web.bean.security.UserSecurity;
import com.quantium.web.bean.view.Menu;
import com.quantium.web.bean.view.PageContext;
import com.quantium.web.controller.Pool;
import com.quantium.web.util.UserHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Created by Михаил on 20.08.14.
 */
@Controller
public class ManagerGenerator extends Pool {
    public static final String MAIN_PAGE                = "jq-admin";

    @RequestMapping(value = MAIN_PAGE)
    public String generatePage(HttpServletRequest request, Model model){
        UserSecurity me     = UserHelper.getMe();

        if (me == null || me.getAccessManager() == null)
            return "redirect:/";

        PageContext PC = new PageContext(request, model);
        PC.setUser(me);

        PC.initPage("Главная страница", null, null);

        return PC.setView("main", "statistic").render(PC.RENDER_BACKEND);
    }

}
