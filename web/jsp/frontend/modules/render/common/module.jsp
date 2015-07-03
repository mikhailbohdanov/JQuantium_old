<%@ page contentType="text/html;charset=UTF-8" language="java" %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <div class="content">
                <c:if test="${module.isHeader()}">
                <div class="header big bgBlue">
                    <span class="headerText ml10">${module.getHeaderTitle()}</span>
                    <c:if test="${module.getHeaderMenuId() > 0}"><jsp:include page="headerMenu.jsp"/></c:if>
                </div>
                </c:if>

                <c:set var="pagination" value="${module.getPagination()}" scope="request"/>
                <div class="elementsList"<c:if test="${pagination != null && pagination.getPageCount() > 1}"> ui-view="${module.getModuleName()}"</c:if>>
                    <jsp:include page="../common/contentItems.jsp"/>
                </div>
                <c:if test="${pagination != null && pagination.getPageCount() > 1}"><jsp:include page="../../../../common/frames/pagination.jsp"/></c:if>
            </div>