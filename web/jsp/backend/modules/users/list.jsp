<%@ page contentType="text/html;charset=UTF-8" language="java" %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="m10">
    <table class="mb10">
        <thead>
        <tr>
            <td class="checker"><label><input type="checkbox" name=""/></label></td>
            <td class="fwB oH">
                <a href="${table.get("userId")}" class="flL link cSilver hSilver">#ID</a>
                <span class="flL icon<c:if test="${table.getActive() == \"userId\"}"> arrow small white ${table.isActiveDesc() ? "bottom" : "top"}</c:if>"><span></span></span>
            </td>
            <td class="oH">
                <a href="${table.get("lastName")}" class="flL link cSilver hSilver">Фамилия, Имя</a>
                <span class="flL icon<c:if test="${table.getActive() == \"lastName\"}"> arrow small white ${table.isActiveDesc() ? "bottom" : "top"}</c:if>"><span></span></span>
            </td>
            <td class="oH">
                <a href="${table.get("userName")}" class="flL link cSilver hSilver">Имя пользователя</a>
                <span class="flL icon<c:if test="${table.getActive() == \"userName\"}"> arrow small white ${table.isActiveDesc() ? "bottom" : "top"}</c:if>"><span></span></span>
            </td>
            <td class="oH">
                <a href="${table.get("email")}" class="flL link cSilver hSilver">Email</a>
                <span class="flL icon<c:if test="${table.getActive() == \"email\"}"> arrow small white ${table.isActiveDesc() ? "bottom" : "top"}</c:if>"><span></span></span>
            </td>
            <td class="oH">
                <a href="${table.get("phone")}" class="flL link cSilver hSilver">Телефон</a>
                <span class="flL icon<c:if test="${table.getActive() == \"phone\"}"> arrow small white ${table.isActiveDesc() ? "bottom" : "top"}</c:if>"><span></span></span>
            </td>
            <td class="options">Операции</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="user"><tr><c:set var="profile" value="${user.getProfile()}"/>
            <td class="checker"><label><input type="checkbox" name=""/></label></td>
            <td class="fwB">#${user.getUserId()}</td>
            <td>${profile.getLastName()}, ${profile.getFirstName()}</td>
            <td>${user.getUserName()}</td>
            <td>${user.getEmail()}</td>
            <td>${user.getPhone()}</td>
            <td class="options">
                <c:if test="${PC.hasAccess(\"USERS\", \"UPDATE_PROFILE\") || PC.hasAccess(\"USERS\", \"UPDATE_SECURITY\")}"><a href="#" class="link cGreen hGreen">Изменить</a></c:if>
                <c:if test="${PC.hasAccess(\"USERS\", \"MODIFY_PERMISSIONS\")}"><a href="#" class="link cBlue hBlue">Права доступа</a></c:if>
                <c:if test="${PC.hasAccess(\"USERS\", \"DELETE\")}"><a href="#" class="link cRed hRed">Удалить</a></c:if>
            </td>
        </tr></c:forEach>
        </tbody>
        <tfoot>
        <tr>
            <td class="checker"><label><input type="checkbox" name=""/></label></td>
            <td class="fwB oH">
                <a href="${table.get("userId")}" class="flL link cSilver hSilver">#ID</a>
                <span class="flL icon<c:if test="${table.getActive() == \"userId\"}"> arrow small white ${table.isActiveDesc() ? "bottom" : "top"}</c:if>"><span></span></span>
            </td>
            <td class="oH">
                <a href="${table.get("lastName")}" class="flL link cSilver hSilver">Фамилия, Имя</a>
                <span class="flL icon<c:if test="${table.getActive() == \"lastName\"}"> arrow small white ${table.isActiveDesc() ? "bottom" : "top"}</c:if>"><span></span></span>
            </td>
            <td class="oH">
                <a href="${table.get("userName")}" class="flL link cSilver hSilver">Имя пользователя</a>
                <span class="flL icon<c:if test="${table.getActive() == \"userName\"}"> arrow small white ${table.isActiveDesc() ? "bottom" : "top"}</c:if>"><span></span></span>
            </td>
            <td class="oH">
                <a href="${table.get("email")}" class="flL link cSilver hSilver">Email</a>
                <span class="flL icon<c:if test="${table.getActive() == \"email\"}"> arrow small white ${table.isActiveDesc() ? "bottom" : "top"}</c:if>"><span></span></span>
            </td>
            <td class="oH">
                <a href="${table.get("phone")}" class="flL link cSilver hSilver">Телефон</a>
                <span class="flL icon<c:if test="${table.getActive() == \"phone\"}"> arrow small white ${table.isActiveDesc() ? "bottom" : "top"}</c:if>"><span></span></span>
            </td>
            <td class="options">Операции</td>
        </tr>
        </tfoot>
    </table>
    <jsp:include page="../../../common/frames/pagination.jsp"/>
</div>