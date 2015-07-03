<%@ page contentType="text/html;charset=UTF-8" language="java" %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="m10">
    <table class="mb10">
        <thead>
        <tr>
            <td class="fwB oH">#ID</td>
            <td class="oH">Код языка</td>
            <td class="oH">Название</td>
            <td class="oH">Статус</td>
            <td class="options">Операции</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${languages}" var="language"><tr>
            <td class="fwB">#${language.getLanguageId()}</td>
            <td>${language.getCode()}</td>
            <td>${language.getName()}</td>
            <td><c:choose><c:when test="${language.isEnabled()}"><span class="cGreen">Включен</span></c:when><c:otherwise><span class="cRed">Выключен</span></c:otherwise></c:choose></td>
            <td class="options">
                <c:if test="${PC.hasAccess(\"LANGUAGES\", \"UPDATE\")}"><a href="/${HOME}/languages/edit/${language.getLanguageId()}" class="link cGreen hGreen">Изменить</a></c:if>
                <c:if test="${PC.hasAccess(\"LANGUAGES\", \"UPDATE_VALUES\")}"><a href="/${HOME}/languages/words/${language.getLanguageId()}" class="link cBlue hBlue">Управление словами</a></c:if>
                <c:if test="${PC.hasAccess(\"LANGUAGES\", \"DELETE\")}"><a href="/${HOME}/languages/delete/${language.getLanguageId()}" class="link cRed hRed">Удалить</a></c:if>
            </td>
        </tr></c:forEach>
        </tbody>
        <tfoot>
        <tr>
            <td class="fwB oH">#ID</td>
            <td class="oH">Код языка</td>
            <td class="oH">Название</td>
            <td class="oH">Статус</td>
            <td class="options">Операции</td>
        </tr>
        </tfoot>
    </table>
</div>