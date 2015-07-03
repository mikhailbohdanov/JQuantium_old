<%@ page contentType="text/html;charset=UTF-8" language="java" %><%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <div id="headerTop" class="bgWhite">
            <div class="content">
                <c:forEach items="${PC.getLanguages()}" var="language"><c:if test="${language.isEnabled()}">
                <a href="?lang=${language.getCode()}" ui-route="update" class="link cBlue hBlue flL mr10<c:if test="${language == PC.getLanguage()}"> active</c:if>">${language.getName()}</a></c:if></c:forEach>
            </div>
        </div>