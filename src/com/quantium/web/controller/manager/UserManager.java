package com.quantium.web.controller.manager;

import com.quantium.web.bean.Url;
import com.quantium.web.bean.security.UserSecurity;
import com.quantium.web.bean.security.permissions.AccessManager;
import com.quantium.web.bean.social.UserProfile;
import com.quantium.web.bean.view.PageContext;
import com.quantium.web.bean.view.Pagination;
import com.quantium.web.bean.view.clientObjects.Table;
import com.quantium.web.controller.CONFIG;
import com.quantium.web.service.MainServices;
import com.quantium.web.service.Services;
import com.quantium.web.service.SocialService;
import com.quantium.web.service.authentication.UserService;
import com.quantium.web.util.ObjectUtils;
import com.quantium.web.util.UserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by FREEMAN on 16.02.15.
 */
@Controller
@RequestMapping("/" + ManagerGenerator.MAIN_PAGE + "/users")
public class UserManager implements CONFIG {

    @Qualifier("userService")
    @Autowired
    private UserService userService;

    @RequestMapping
    public String getUserList(HttpServletRequest request, Model model) {
        UserSecurity me     = UserHelper.getMe();

        AccessManager manager = null;
        if (me == null || (manager = me.getAccessManager()) == null || !manager.hasAny("USERS"))
            return "redirect:/" + ManagerGenerator.MAIN_PAGE;

        PageContext PC = new PageContext(request, model);
        PC.setUser(me);

        Url url = new Url(request);
        PC.setUrl(url);

        int count, limit = PAGE_LIMIT, offset;
        String tmp, order;
        boolean desc = LIST_DEFAULT_DESC;

        count   = userService._getUserCount();

        if ((tmp = request.getParameter(PAGE_LIMIT_PARAMETER)) != null)
            limit   = Integer.parseInt(tmp);

        if ((order = request.getParameter(LIST_ORDER_COLUMN)) != null)
            desc    = Boolean.valueOf(request.getParameter("desc"));
        else
            order   = "userId";

        Pagination pagination = new Pagination(url, PAGE_PARAMETER, count, limit);

        model.addAttribute("pagination", pagination);
        model.addAttribute("users", userService._getUserList(order, desc, pagination.getOffset(), pagination.getPerPage()));
        model.addAttribute("table", new Table(url, order, desc));

        return PC.setView("users", "list").render(PageContext.RENDER_BACKEND);
    }

    @RequestMapping(value = "/create")
    public String getCreateForm(HttpServletRequest request, Model model) {
        UserSecurity me     = UserHelper.getMe();

        AccessManager manager = null;
        if (me == null || (manager = me.getAccessManager()) == null || !manager.has("USERS", "CREATE"))
            return "redirect:/" + ManagerGenerator.MAIN_PAGE;

        PageContext PC = new PageContext(request, model);
        PC.setUser(me);

        Url url = new Url(request);
        PC.setUrl(url);

        return PC.setView("users", "createEdit").render(PageContext.RENDER_BACKEND);
    }

    @RequestMapping(value = "/edit")
    public String getEditForm(HttpServletRequest request, Model model) {
        UserSecurity me     = UserHelper.getMe();

        AccessManager manager = null;
        if (me == null || (manager = me.getAccessManager()) == null || (!manager.has("USERS", "UPDATE_PROFILE") && !manager.has("USERS", "UPDATE_SECURITY")))
            return "redirect:/" + ManagerGenerator.MAIN_PAGE;

        PageContext PC  = new PageContext(request, model);
        PC.setUser(me);

        Url url         = new Url(request);
        PC.setUrl(url);



        return PC.setView("users", "createEdit").render(PageContext.RENDER_BACKEND);
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public String saveUser(HttpServletRequest request) {
        String tmp;

        UserSecurity userSecurity;
        UserProfile userProfile;

        if ((tmp = request.getParameter("userId")) != null) {
            userSecurity    = Services.security.getUser(Integer.parseInt(tmp));
        } else {
            userSecurity    = new UserSecurity();
        }

        ObjectUtils.getFromRequest(userSecurity, request);

//        if (userSecurity.getPassword() != null )

        MapSqlParameterSource map = new MapSqlParameterSource();
        String query = null;//TODO SQLUtils.buildUpdate(userSecurity, map);

        return query;
    }

    @RequestMapping(value = "/perms")
    public String getUserPermsForm(HttpServletRequest request, Model model) {

        return null;
    }

}
