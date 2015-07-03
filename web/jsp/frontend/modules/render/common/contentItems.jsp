<%@ page contentType="text/html;charset=UTF-8" language="java" %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
                    <c:choose>
                        <c:when test="${module.getType() == \"VIDEOS\"}"><jsp:include page="../content/videoList.jsp"/></c:when>
                        <c:when test="${module.getType() == \"POSTS\"}"><jsp:include page="../content/postList.jsp"/></c:when>
                    </c:choose>