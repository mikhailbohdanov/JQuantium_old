package com.quantium.web.bean.view;

/**
 * Created by Михаил on 20.09.14.
 */
public class Module {
    public static enum Type {
        LIST,
        PAGES,
        VIDEOS,
        POSTS,
        IMAGES,
        IMAGE,
        FORM
    }

    private int moduleId;
    private Type type;
    private boolean header;
    private int headerMenuId;
    private String defaultLink;
    private int elementsPerPage;
    private String template;

    // - Lang data
    private String headerTitle;

    // - Meta data
    private int groupId;
    private int order;

    private Menu headerMenu;
    private Object data;
    private Pagination pagination;

    public int getModuleId() {
        return moduleId;
    }
    public Module setModuleId(int moduleId) {
        this.moduleId = moduleId;
        return this;
    }

    public Type getType() {
        return type;
    }
    public Module setType(Type type) {
        this.type = type;
        return this;
    }
    public Module setType(String type) {
        if (type != null)
            this.type = Type.valueOf(type);

        return this;
    }

    public boolean isHeader() {
        return header;
    }
    public Module setHeader(boolean header) {
        this.header = header;
        return this;
    }

    public int getHeaderMenuId() {
        return headerMenuId;
    }
    public Module setHeaderMenuId(int headerMenuId) {
        this.headerMenuId = headerMenuId;
        return this;
    }

    public String getDefaultLink() {
        return defaultLink;
    }
    public Module setDefaultLink(String defaultLink) {
        this.defaultLink = defaultLink;
        return this;
    }

    public int getElementsPerPage() {
        return elementsPerPage;
    }
    public Module setElementsPerPage(int elementsPerPage) {
        this.elementsPerPage = elementsPerPage;
        return this;
    }

    public String getTemplate() {
        return template;
    }
    public Module setTemplate(String template) {
        this.template = template;
        return this;
    }


    public String getHeaderTitle() {
        return headerTitle;
    }
    public Module setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
        return this;
    }

    public int getGroupId() {
        return groupId;
    }
    public Module setGroupId(int groupId) {
        this.groupId = groupId;
        return this;
    }

    public int getOrder() {
        return order;
    }
    public Module setOrder(int order) {
        this.order = order;
        return this;
    }

    public Menu getHeaderMenu() {
        return headerMenu;
    }
    public Module setHeaderMenu(Menu headerMenu) {
        this.headerMenu = headerMenu;
        return this;
    }

    public Object getData() {
        return data;
    }
    public Module setData(Object data) {
        this.data = data;
        return this;
    }

    public Pagination getPagination() {
        return pagination;
    }
    public Module setPagination(Pagination pagination) {
        this.pagination = pagination;
        return this;
    }

    public String getModuleName() {
        if (moduleId == 0)
            return null;

        return "m_" + moduleId;
    }
}
