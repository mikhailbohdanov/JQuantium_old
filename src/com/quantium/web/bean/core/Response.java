package com.quantium.web.bean.core;

import com.quantium.web.bean.Url;
import com.quantium.web.bean.security.UserSecurity;
import com.quantium.web.bean.security.permissions.AccessManager;
import com.quantium.web.service.Languages;
import com.quantium.web.service.MainServices;
import com.quantium.web.util.UserHelper;
import org.json.simple.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Author FREEMAN
 * Created 12.11.14
 */
public class Response {
    public static boolean hasAjax(HttpServletRequest request) {
        String param = null;
        boolean ajax = false;

        try {
            if ((param = request.getHeader("X-REQUESTED-WITH")) != null && param.equals("XMLHttpRequest"))
                ajax = true;
        } catch (Exception e){}

        if (!ajax)
            try {
                if ((param = request.getHeader("HTTP-X-REQUESTED-WITH")) != null && param.equals("XMLHttpRequest"))
                    ajax = true;
            } catch (Exception e){}

        if (!ajax || (param = request.getParameter("_ajax")) != null)
            try {
                if (param.equals("true"))
                    ajax = true;
                else if (param.equals("false"))
                    ajax = false;
            } catch (Exception e){}

        return ajax;
    }

    protected HashMap<String, Long> errors      = new HashMap<String, Long>();

    protected HttpServletRequest request        = null;
    protected HttpServletResponse response      = null;
    protected Url url                           = null;

    protected UserSecurity user;
    protected AccessManager manager;

    private long START_MILLIS       = System.currentTimeMillis();
    private long START_NANO         = System.nanoTime();

    protected Response() {
        this.user       = UserHelper.getMe();

        if (this.user != null)
            this.manager    = this.user.getAccessManager();


    }
    public Response(HttpServletRequest request) {
        this();

        this.request    = request;
    }
    public Response(HttpServletRequest request, HttpServletResponse response) {
        this();

        this.request    = request;
        this.response   = response;
    }

    public long getDurationMillis() {
        return System.currentTimeMillis() - START_MILLIS;
    }
    public long getDurationNano() {
        return System.nanoTime() - START_NANO;
    }
    public long getDuration() {
        return Long.parseLong(String.valueOf(getDurationMillis()) + String.valueOf(getDurationNano()));
    }

    public HttpServletRequest getRequest() {
        return request;
    }
    public HttpServletResponse getResponse() {
        return response;
    }

    public boolean languageSet(String code) {
        if (!Languages.hasLanguage(code))
            return false;

        if (request.getSession() != null)
            request.getSession().setAttribute("language", code);

        if (user != null) {
            user.setLanguageCode(code);
            MainServices.securityService.updateSecurityUser(user);
        }

        return true;
    }

    public Response addError(String errorType, long errorCode) {
        if (errors.containsKey(errorType))
            errors.replace(errorType, errors.get(errorType) | errorCode);
        else
            errors.put(errorType, errorCode);

        return this;
    }
    public boolean hasError() {
        if (errors.size() == 0)
            return false;

        for (Map.Entry<String, Long> entry : errors.entrySet())
            if (entry.getValue() > 0)
                return true;

        return false;
    }
    public boolean hasError(String errorType, long errorCode) {
        if (!errors.containsKey(errorType))
            return false;
        else
            return (errors.get(errorType) & errorCode) > 0;
    }
    public boolean hasError(Class className, long errorCode) {
        if (!errors.containsKey(className))
            return false;
        else
            return (errors.get(className) & errorCode) > 0;
    }

    public Url getUrl() {
        return url;
    }
    public Response setUrl(Url url) {
        this.url = url;
        return this;
    }

    public JSONObject getReturn() {
        JSONObject out = new JSONObject();

        if (!hasError())
            return out;

        out.put("errors", this.errors);

        return out;
    }


    public UserSecurity getUser() {
        return user;
    }
    public Response setUser(UserSecurity user) {
        this.user = user;

        if (user != null)
            this.manager = user.getAccessManager();

        return this;
    }

    public boolean hasAccess(String access, String value) {
        if (manager != null)
            return manager.has(access, value);

        return false;
    }
    public boolean hasAny(String... accesses) {
        if (manager == null)
            return false;

        for (String access : accesses)
            if (manager.hasAny(access))
                return true;

        return false;
    }
    public boolean hasAnyAccess(String access) {
        return manager != null && manager.hasAny(access);
    }
    public boolean hasAnyAccess(String access, String... values) {
        return manager != null && manager.hasAny(access, values);
    }
    public boolean hasAllAccess(String access, String... values) {
        return manager != null && manager.hasAll(access, values);
    }

}
