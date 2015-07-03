<%@ page contentType="text/html;charset=UTF-8" language="java" %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="CP" value="${PC.getClientPage()}" scope="request"/>
<c:forEach items="${CP.getGroups()}" var="group"><c:set var="sidebar" value="${group.getSidebar()}"/>
        <div class="moduleWrapper<c:if test="${sidebar != null}"> withSidebar</c:if>">
            <c:if test="${sidebar != null && sidebar.getType() == \"SIDEBAR_LEFT\"}"><jsp:include page="sidebar.jsp"/></c:if>

            <c:forEach items="${group.getModules()}" var="module"><c:set var="module" value="${module}" scope="request"/>
            <jsp:include page="common/module.jsp"/>
            </c:forEach>

            <c:if test="${sidebar != null && sidebar.getType() == \"SIDEBAR_RIGHT\"}"><jsp:include page="sidebar.jsp"/></c:if>
        </div>
</c:forEach>
