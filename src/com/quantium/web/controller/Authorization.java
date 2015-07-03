package com.quantium.web.controller;

import com.quantium.web.bean.core.AjaxResponse;
import com.quantium.web.bean.security.UserSecurity;
import com.quantium.web.errors.controllers.SecurityErrors;
import com.quantium.web.service.Services;
import com.quantium.web.util.UserHelper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by FREEMAN on 22.12.14.
 */
@Controller
@RequestMapping("/security")
public class Authorization extends Pool {

    @Autowired
    private Services services;

    //TODO only post method
    @RequestMapping(value = "/code", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject code(HttpServletRequest request) {


        return null;
    }

    //TODO temporary functional
    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public String authorize(HttpServletRequest request) {
        authorizeAjax(request);
        return "redirect:/";
    }

    @RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
    public String logout(HttpServletRequest request) {
        logoutAjax(request);
        return "redirect:/";
    }

    //TODO only post method
    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST}, headers = {"X-REQUESTED-WITH=XMLHttpRequest", "HTTP-X-REQUESTED-WITH=XMLHttpRequest"})
    @ResponseBody
    public JSONObject authorizeAjax(HttpServletRequest request) {
        AjaxResponse AR = new AjaxResponse();

        if (!UserHelper.isAuthorized()) {
            String login, password;

            if ((login = request.getParameter("login")) == null)
                AR
                        .addError(SecurityErrors._NAME, SecurityErrors.LOGIN_EMPTY)
                        .addError(SecurityErrors._NAME, SecurityErrors.SECURITY_AUTHENTICATE_MISS_PARAMS);

            if ((password = request.getParameter("password")) == null)
                AR
                        .addError(SecurityErrors._NAME, SecurityErrors.PASSWORD_EMPTY)
                        .addError(SecurityErrors._NAME, SecurityErrors.SECURITY_AUTHENTICATE_MISS_PARAMS);
            else
                UserHelper.validatePassword(password, password, AR);

            if (!AR.hasError())
                UserHelper.loginUser(login, password, AR);

            //TODO call hooks after login user (recount invites, messages, other counters...)
        } else
            AR.addError(SecurityErrors._NAME, SecurityErrors.SECURITY_AUTHENTICATED);

        return AR.getReturn();
    }

    //TODO only post method
    @RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST}, headers = {"X-REQUESTED-WITH=XMLHttpRequest", "HTTP-X-REQUESTED-WITH=XMLHttpRequest"})
    @ResponseBody
    public JSONObject logoutAjax(HttpServletRequest request) {
        AjaxResponse AR = new AjaxResponse();

        if (UserHelper.isAuthorized())
            UserHelper.logoutUser(AR);
        else
            AR.addError(SecurityErrors._NAME, SecurityErrors.SECURITY_NOT_AUTHENTICATED);

        return AR.getReturn();
    }

    //TODO only post method
    @RequestMapping(value = "/signup", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public JSONObject signupAjax(HttpServletRequest request) {
        AjaxResponse AR = new AjaxResponse();

        if (!UserHelper.isAuthorized()) {
            UserSecurity user = Services.users.createUser(
                    request.getParameter("userName"),
                    request.getParameter("password"),
                    request.getParameter("passwordConfirm"),
                    request.getParameter("email"),
                    request.getParameter("phone"),
                    request.getParameter("firstName"),
                    request.getParameter("lastName"),
                    false,
                    AR
            );
        } else
            AR.addError(SecurityErrors._NAME, SecurityErrors.SECURITY_AUTHENTICATED);

        return AR.getReturn();
    }

    //TODO only post method
    @RequestMapping(value = "/confirm.{type}", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public void confirmAny(HttpServletRequest request, @PathVariable String type) {

    }



}
