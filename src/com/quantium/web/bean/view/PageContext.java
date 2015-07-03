package com.quantium.web.bean.view;

import com.quantium.web.bean.Url;
import com.quantium.web.bean.core.StaticNode;
import com.quantium.web.bean.core._language.Language;
import com.quantium.web.bean.core.Response;
import com.quantium.web.bean.view.clientObjects.VideoServer;
import com.quantium.web.service.ConfigService;
import com.quantium.web.service.Languages;
import com.quantium.web.service.MainServices;
import com.quantium.web.util.Primitives;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Михаил on 22.08.14.
 */
public class PageContext extends Response {
    public static final String RENDER_FRONTEND      = "frontend";
    public static final String RENDER_BACKEND       = "adminlte";

    private static HashMap<Integer, StaticNode> staticNodes = new HashMap<Integer, StaticNode>();

    public static void staticNodeSet(ArrayList<StaticNode> staticNodes) {
        PageContext.staticNodes.clear();

        for (StaticNode staticNode : staticNodes)
            staticNodeAdd(staticNode);
    }
    public static void staticNodeAdd(StaticNode staticNode) {
        PageContext.staticNodes.put(staticNode.getNodeId(), staticNode);
    }
    public static StaticNode staticNodeGet(int id){
        return staticNodes.get(id);
    }
    public static StaticNode staticNodeGet(){
        return Primitives.randomItem(staticNodes);
    }

    private static HashMap<Integer, VideoServer> videoServers = new HashMap<Integer, VideoServer>();

    public static void videoServerSet(ArrayList<VideoServer> videoServers) {
        PageContext.videoServers.clear();

        for (VideoServer videoServer : videoServers)
            videoServerAdd(videoServer);
    }
    public static void videoServerAdd(VideoServer videoServer) {
        PageContext.videoServers.put(videoServer.getServerId(), videoServer);
    }
    public static VideoServer videoServerGet(int videoServerId) {
        return videoServers.get(videoServerId);
    }
    public static VideoServer videoServerGet() {
        return Primitives.randomItem(videoServers);
    }

    public static PageContext newInstance(HttpServletRequest request, Model model) {
        return newInstance(request, null, model, null);
    }
    public static PageContext newInstance(HttpServletRequest request, HttpServletResponse response, Model model) {
        return newInstance(request, response, model, null);
    }
    public static PageContext newInstance(HttpServletRequest request, Model model, Url url) {
        return newInstance(request, null, model, url);
    }
    public static PageContext newInstance(HttpServletRequest request, HttpServletResponse response, Model model, Url url) {
        PageContext PC  = new PageContext(request, response, model);

        PC.setLanguage();

        if (url == null)
            PC.setUrl(new Url(request));
        else
            PC.setUrl(url);

        return PC;
    }

    private Model model                     = null;
    private String module                   = null;
    private String action                   = null;
    private Page page                       = null;
    private ClientPage clientPage           = null;
    private boolean onlyAjax                = false;

    private Language language;

    public PageContext(HttpServletRequest request, Model model) {
        super(request);

        setModel(model);
    }
    public PageContext(HttpServletRequest request, HttpServletResponse response, Model model) {
        super(request, response);

        setModel(model);
    }

    public Model getModel() {
        return model;
    }
    private void setModel(Model model) {
        this.model = model;

        this.model.addAttribute("PC", this);
    }

    public PageContext onlyAjax() {
        this.onlyAjax = true;
        return this;
    }

    public PageContext setView(String module, String action) {
        this.module = module;
        this.action = action;

        return this;
    }

    public String getModule() {
        return module;
    }
    public PageContext setModule(String module) {
        this.module = module;
        return this;
    }

    public String getAction() {
        return action;
    }
    public PageContext setAction(String action) {
        this.action = action;
        return this;
    }

    public Page getPage() {
        return page;
    }
    public PageContext setPage(Page page) {
        this.page   = page;
        return this;
    }

    public ClientPage getClientPage() {
        return clientPage;
    }
    public PageContext setClientPage(ClientPage clientPage) {
        this.clientPage = clientPage;
        return this;
    }

    public PageContext initPage(String title, String description, String keywords) {
        ClientPage page = new ClientPage();

        page
                .setTitle(title)
                .setDescriptions(description)
                .setKeywords(keywords);

        this.page       = new Page();
        this.clientPage = page;

        return this;
    }

    public String getTitle() {
        if (clientPage != null)
            return clientPage.getTitle();

        return null;
    }
    public String getKeywords() {
        if (clientPage != null)
            return clientPage.getKeywords();

        return null;
    }
    public String getDescription() {
        if (clientPage != null)
            return clientPage.getDescriptions();

        return null;
    }

    public boolean hasAjax() {
        return hasAjax(request);
    }

    public String render() {
        return render(RENDER_FRONTEND);
    }
    public String render(String render) {
        if (onlyAjax || hasAjax(request))
            return render + "/ajax";
        else
            return render + "/full";
    }

    public PageContext setLanguage() {
        String code = null;
        Object attr;

        if (request != null) {
            if (!((code = request.getParameter("lang")) != null && Languages.hasLanguage(code)) && !((code = request.getParameter("lang")) != null && Languages.hasLanguage(code))) {
                if (user == null || (code = user.getLanguageCode()) == null || !Languages.hasLanguage(code)) {
                    if (request.getSession() == null || (attr = request.getSession().getAttribute("language")) == null || (code = String.valueOf(attr)).equals(null) || !Languages.hasLanguage(code)) {
                        if (request.getLocale() == null || (code = request.getLocale().getLanguage()) == null || !Languages.hasLanguage(code)) {
                            code = null;
                        } else languageSet(code);
                    }
                }
            } else languageSet(code);
        }

        if (code == null || (language = Languages.getLanguage(code)) == null || !language.isEnabled())
            code = ConfigService.getString("CORE", "defaultLanguage");

        language    = Languages.getLanguage(code);

        if (language == null)
            language = new Language();

        return this;
    }

    public ArrayList<Language> getLanguages() {
        return Languages.getLanguages();
    }
    public Language getLanguage() {
        return language;
    }
    public String getWord(String key) {
        if (language != null)
            return language.get(key);

        return null;
    }

    public Menu getMenu(String name) {
        return MainServices.viewService.getMenu(name, language.getCode());
    }

}
