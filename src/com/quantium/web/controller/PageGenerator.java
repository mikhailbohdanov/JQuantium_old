package com.quantium.web.controller;

import com.quantium.web.bean.Url;
import com.quantium.web.bean.core.Response;
import com.quantium.web.bean.core.Route;
import com.quantium.web.bean.core.StaticNode;
import com.quantium.web.bean.social.UserProfile;
import com.quantium.web.bean.view.*;
import com.quantium.web.bean.view.clientObjects.Video;
import com.quantium.web.controller.social.Profiles;
import com.quantium.web.errors.exceptions.view.PageNotFoundException;
import com.quantium.web.service.*;
import com.quantium.web.util.UserHelper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * Created by Михаил on 20.08.14.
 */
@Controller
@RequestMapping
public class PageGenerator extends Pool {

    @Autowired
    private MainServices mainServices;

    @Autowired
    private Services services;

    @PostConstruct
    @RequestMapping(value = "/_refreshContext")
    @ResponseBody
    public void construct() {


        // - - - Get static servers
//        PageContext.addServers(Services.core.getStaticNodeList(StaticNode.Level.PRODUCTION));
        PageContext.staticNodeSet(Services.core.getStaticNodeList(StaticNode.Level.DEVELOP));
//        PageContext.addServers(Services.core.getStaticNodeList(StaticNode.Level.TEST));

        // - - - Get video servers
        PageContext.videoServerSet(Services.media.videoServerGetList());

        Services.core.init();
        Services.view.init();
    }

    @PreDestroy
    public void destroy() {

    }

    @RequestMapping(value = "/_language/{languageCode}")
    @ResponseBody
    public void changeLanguage(HttpServletRequest request, @PathVariable String languageCode) {
        Response R = new Response(request);
        R.languageSet(languageCode);
    }

    @RequestMapping(value = "/**")
    public String generatePage(HttpServletRequest request, HttpServletResponse response, Model model) {
        PageContext PC = PageContext.newInstance(request, response, model);

        Url url             = PC.getUrl();
        String currentUrl   = url.getPath();
        Page page           = null;

        try {
            page = View.getPage(currentUrl);
        } catch (PageNotFoundException e) {}

        if (page == null) {
            Route route = null;
            try {
                route = Core.findRoute(currentUrl);
            } catch (PageNotFoundException e) {}

            if (route != null)
                try {
                    page = View.getPage(route.getOwnerId());
                } catch (PageNotFoundException e) {}
        }

        if (page == null) {
            String routeUrl = currentUrl.replaceAll("^/", "");
            Route route = Services.core.getRoute(routeUrl);

            if (route != null)
                switch (route.getType()) {
                    case PAGE:
                        try {
                            page = View.getPage(route.getOwnerId());
                        } catch (PageNotFoundException e) {}
                        break;
                    case USER:
                        Profiles profilesController = Pool.get(Profiles.class);

                        UserProfile profile = profilesController.getProfile(PC, route.getOwnerId());
                        PC.initPage(profile.getFullName(), null, null);

                        return PC.setView("profile", "view").render();
                    case GROUP:

                        break;
                }

            //TODO call routes handler and return router data builder
        }

        if (page != null) {
            PC.setPage(page);

            if (checkPageAccess(PC, page)) {
                ClientPage clientPage = getClientPage(PC, page);

                if (clientPage != null)
                    PC.setClientPage(clientPage);
                else {
                    //TODO some trouble
                }
            } else {
                //TODO 403 error
            }
        } else {
            //TODO 404 error
        }

        return PC.setView("render", "page").render();
    }

//    @RequestMapping(value = "/module/")
    public String generateModule(HttpServletRequest request, Model model) {
        PageContext PC = PageContext.newInstance(request, model);

        Url url             = PC.getUrl();
        String currentUrl   = url.getPath();
        Page page           = null;



        return PC.setView("render", "multi").render();
    }

    @RequestMapping(value = "/multi-request", method = RequestMethod.GET)
    public String generateMultiRequest(HttpServletRequest request, Model model) {
        PageContext PC = PageContext.newInstance(request, model);

        Url url             = PC.getUrl();
        String tmp,
                languageCode = PC.getLanguage().getCode();

        int iterator, moduleId;
        JSONArray array     = null;
        JSONObject object   = null;

        // - Page
        if ((tmp = request.getParameter("pageUrl")) != null) {
            //TODO must handle this applet
//            url.deleteSearch("pageUrl");
//            url.setSearch("pageUrl", tmp);
        }

        // - Modules
        if ((tmp = request.getParameter("modules")) != null) {
            ArrayList<Module> modules   = new ArrayList<Module>();
            Module module;

            url.deleteSearch("module");

            for (String _tmp : tmp.split(",")) {
                moduleId    = Integer.parseInt(_tmp);

                module      = Services.view.getModule(moduleId, languageCode);

                if (module != null) {
                    fillModule(module, url, languageCode);
                    modules.add(module);
                }
            }

            model.addAttribute("modules", modules);
        }

        return PC.onlyAjax().setView("render", "multi").render();
    }

    public boolean checkPageAccess(PageContext PC, Page page) {
        boolean access = true;
        HttpServletRequest request = PC.getRequest();

        if (page.getPasswords().size() > 0) {
            access = false;

            if (request.getParameter("_password") != null) {
                String passwordKey, _password = request.getParameter("_password");

                PasswordLoop:
                for (PagePassword password : page.getPasswords()) {
                    passwordKey = password.getPassword();

                    switch (password.getAccess()) {
                        case ALL:
                            if (passwordKey.isEmpty() || passwordKey.equals(_password)) {
                                access = true;
                                break PasswordLoop;
                            }
                            break;
                        case AUTHORITY:
                            if (UserHelper.isAuthorized() && passwordKey.equals(_password)) {
                                access = true;
                                break PasswordLoop;
                            }
                            break;
                        case ONLY:
                            if (UserHelper.isAuthorized() && passwordKey.equals(_password) && password.hasUser(UserHelper.getUserId())) {//TODO сделать возможность допроверки остальных паролей и раздачи пользователям если они привязаны к паролю прав
                                access = true;
                                break PasswordLoop;
                            }
                            break;
                        default:
                            access = true;
                            break PasswordLoop;
                    }
                }
            }
        }

        return access;
    }

    public ClientPage getClientPage(PageContext PC, Page page) {
        ClientPage clientPage = Services.view.getClientPage(page.getPageId(), PC.getLanguage().getCode());

        if (clientPage != null) {
            Group sidebar;

            Url url             = PC.getUrl();
            String languageCode = PC.getLanguage().getCode();

            for (Group group : clientPage.getGroups()) {
                fillGroup(group, url, languageCode);

                if ((sidebar = group.getSidebar()) != null)
                    fillGroup(sidebar, url, languageCode);
            }
        }

        return clientPage;
    }

    private void fillGroup(Group group, Url url, String languageCode) {
        ArrayList<Module> modules = group.getModules();

        if (modules == null || modules.size() == 0)
            return;

        for (Module module : modules)
            fillModule(module, url, languageCode);
    }

    private void fillModule(Module module, Url url, String languageCode) {
        int count, limit, menuId;
        Module.Type type            = module.getType();
        Pagination pagination       = null;

//            if ((menuId = module.getHeaderMenuId()) > 0)
//                module.setHeaderMenu(Services.view.getMenu(menuId, languageCode));

        if (type == Module.Type.LIST || type == Module.Type.VIDEOS || type == Module.Type.POSTS || type == Module.Type.IMAGES) {
            limit       = module.getElementsPerPage();

            switch (type) {
                case LIST:
                    count       = 0;
                    break;
                case VIDEOS:
                    count       = Services.media.videoCountOfModule(module.getModuleId(), languageCode);
                    break;
                case POSTS:
                    count       = 0;
                    break;
                case IMAGES:
                    count       = 0;
                    break;
                default:
                    count       = 0;
            }

            pagination  = new Pagination(url, module.getModuleName(), count, limit);
            module.setPagination(pagination);
        }

        switch (type) {
            case LIST:

                break;
            case PAGES:

                break;
            case VIDEOS:
                ArrayList<Video> videos = Services.media.videoListOfModule(module.getModuleId(), pagination, languageCode);
                module.setData(videos);
                break;
            case POSTS:

                break;
            case IMAGES:

                break;
            case IMAGE:

                break;
            case FORM:

                break;
            default:

                break;
        }
    }
}
