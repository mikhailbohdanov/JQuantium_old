<%@ page contentType="text/html;charset=UTF-8" language="java" %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<div class="config">{"testVariable":"value"}</div>--%>

<c:forEach items="${modules}" var="module"><c:set var="module" value="${module}" scope="request"/>
<div ui-module="${module.getModuleName()}">
    <jsp:include page="common/contentItems.jsp"/>
</div>
</c:forEach>