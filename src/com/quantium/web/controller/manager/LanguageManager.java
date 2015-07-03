package com.quantium.web.controller.manager;

import com.quantium.web.bean.Url;
import com.quantium.web.bean.core.AjaxResponse;
import com.quantium.web.bean.core._language.Language;
import com.quantium.web.bean.core._language.LanguageKey;
import com.quantium.web.bean.core._language.LanguageValue;
import com.quantium.web.bean.security.UserSecurity;
import com.quantium.web.bean.security.permissions.AccessManager;
import com.quantium.web.bean.view.ClientPage;
import com.quantium.web.bean.view.PageContext;
import com.quantium.web.controller.CONFIG;
import com.quantium.web.errors.controllers.AccessErrors;
import com.quantium.web.errors.controllers.DataModifyErrors;
import com.quantium.web.service.Core;
import com.quantium.web.service.Languages;
import com.quantium.web.service.Util;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Created by FREEMAN on 13.03.15.
 */
@Controller
@RequestMapping("/" + ManagerGenerator.MAIN_PAGE + "/languages")
public class LanguageManager implements CONFIG {

    @Autowired
    private Core core;

    @Autowired
    private Util util;

    @RequestMapping
    public String getLanguageList(HttpServletRequest request, Model model) {
        PageContext PC          = PageContext.newInstance(request, model);
        UserSecurity me         = PC.getUser();
        AccessManager manager;

        if (me == null || (manager = me.getAccessManager()) == null || !manager.hasAny("LANGUAGE"))
            PC.addError(AccessErrors._NAME, AccessErrors.NOT_HAS_PERMISSIONS);

        PC.initPage("Управление языками", null, null);

        if (!PC.hasError()) {
            model.addAttribute("languages", Languages.getLanguages());
            PC.setView("languages", "list");
        } else
            PC.setView("main", "access");

        return PC.render(PageContext.RENDER_BACKEND);
    }

    @RequestMapping(value = "/create")
    public String createLanguage(HttpServletRequest request, Model model) {
        PageContext PC          = PageContext.newInstance(request, model);
        UserSecurity me         = PC.getUser();
        AccessManager manager;

        if (me == null || (manager = me.getAccessManager()) == null || !manager.has("LANGUAGE", "ADD"))
            PC.addError(AccessErrors._NAME, AccessErrors.NOT_HAS_PERMISSIONS);

        PC.initPage("Создание языка", null, null);

        if (!PC.hasError()) {
            model.addAttribute("language", new Language());
            PC.setView("languages", "createEdit");
        } else
            PC.setView("main", "access");

        return PC.render(PageContext.RENDER_BACKEND);
    }

    @RequestMapping(value = "/edit/{languageId}")
    public String editLanguage(HttpServletRequest request, Model model, @PathVariable int languageId) {
        PageContext PC          = PageContext.newInstance(request, model);
        UserSecurity me         = PC.getUser();
        AccessManager manager;

        if (me == null || (manager = me.getAccessManager()) == null || !manager.has("LANGUAGE", "MODIFY"))
            PC.addError(AccessErrors._NAME, AccessErrors.NOT_HAS_PERMISSIONS);

        PC.initPage("Изменение языка", null, null);

        if (!PC.hasError()) {
            model.addAttribute("language", Languages.getLanguage(languageId));
            PC.setView("languages", "createEdit");
        } else
            PC.setView("main", "access");

        return PC.render(PageContext.RENDER_BACKEND);
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public JSONObject saveLanguage(HttpServletRequest request) {
        AjaxResponse R          = new AjaxResponse(request);
        UserSecurity me         = R.getUser();
        AccessManager manager   = null;

        boolean create, update, error = false;
        if (me == null || (manager = me.getAccessManager()) == null)
            error   = true;

        if (!(create = manager.has("LANGUAGE", "ADD")))
            error   = true;

        if (!(update = manager.has("LANGUAGE", "MODIFY")))
            error   = true;

        if (error)
            R.addError(AccessErrors._NAME, AccessErrors.NOT_HAS_PERMISSIONS);

        if (!R.hasError()) {
            String
                    tmp     = request.getParameter("languageId"),
                    name    = request.getParameter("name"),
                    code    = request.getParameter("code");
            int languageId  = tmp != null ? Integer.parseInt(tmp) : 0;
            boolean enabled = Boolean.parseBoolean(request.getParameter("enabled"));
            Language languageById, languageByCode;

            if (code == null || code.isEmpty())
                R.addError(DataModifyErrors._NAME, DataModifyErrors.SECONDARY_KEY_EMPTY);

            languageByCode  = Languages.getLanguage(code);

            if (!R.hasError())
                if (languageId > 0 && update) {
                    languageById    = Languages.getLanguage(languageId);

                    if (languageByCode != null && languageById != languageByCode)
                        R.addError(DataModifyErrors._NAME, DataModifyErrors.SECONDARY_KEY_EXISTS);
                    else
                        core.languageUpdate(languageId, name, code, enabled);
                } else if (create) {
                    if (languageByCode != null)
                        R.addError(DataModifyErrors._NAME, DataModifyErrors.SECONDARY_KEY_EXISTS);
                    else
                        core.languageCreate(name, code, enabled);
                }
        }

        return R.getReturn();
    }

    @RequestMapping(value = "/delete/{languageId}")
    @ResponseBody
    public JSONObject deleteLanguage(HttpServletRequest request, @PathVariable int languageId) {
        AjaxResponse R          = new AjaxResponse(request);
        UserSecurity me         = R.getUser();
        AccessManager manager;

        if (me == null || (manager = me.getAccessManager()) == null || !manager.has("LANGUAGE", "REMOVE"))
            R.addError(AccessErrors._NAME, AccessErrors.NOT_HAS_PERMISSIONS);

        if (!R.hasError())
            core.languageDelete(languageId);

        return R.getReturn();
    }

    //TODO сделать более гибкую систему управления словами
    @RequestMapping(value = "/words/{languageId}")
    public String getWords(HttpServletRequest request, Model model, @PathVariable int languageId) {
        PageContext PC          = new PageContext(request, model);
        UserSecurity me         = PC.getUser();
        AccessManager manager;

        if (me == null || (manager = me.getAccessManager()) == null || !manager.has("LANGUAGES", "UPDATE_VALUES"))
            return "redirect:/" + ManagerGenerator.MAIN_PAGE;

        ClientPage page         = new ClientPage();
        page.setTitle("Управление словами");
        PC
                .setClientPage(page)
                .setUrl(new Url(request));

        if (!PC.hasError()) {
            Language language   = Languages.getLanguage(languageId);

            model.addAttribute("language", language);
            model.addAttribute("keys", Languages.getKeys());
            PC.setView("languages", "words");
        } else
            PC.setView("main", "access");

        return PC.render(PageContext.RENDER_BACKEND);
    }

    //TODO сделать более гибкую систему управления словами
    @RequestMapping(value = "/words", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject updateWords(HttpServletRequest request) {
        AjaxResponse R          = new AjaxResponse(request);
        UserSecurity me         = R.getUser();
        AccessManager manager;

        if (me == null || (manager = me.getAccessManager()) == null || !manager.has("LANGUAGES", "UPDATE_VALUES"))
            R.addError(AccessErrors._NAME, AccessErrors.NOT_HAS_PERMISSIONS);

        if (!R.hasError()) {
            String tmp  = request.getParameter("languageId");
            int languageId  = Integer.parseInt(tmp);
            Language language   = Languages.getLanguage(languageId);
            ArrayList<LanguageKey> keys = Languages.getKeys();
            LanguageValue value;

            for (LanguageKey key : keys) {
                value   = language.getValue(key.getKey());
                value.setValue(request.getParameter(key.getKey()));
                util.updateRow(value);
            }
        }

        return R.getReturn();
    }

}
