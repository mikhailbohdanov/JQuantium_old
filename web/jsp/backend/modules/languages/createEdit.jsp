<%@ page contentType="text/html;charset=UTF-8" language="java" %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div style="width: 570px">
    <form method="post" action="/${HOME}/languages/save">
        <c:if test="${language.getLanguageId() > 0}"><input type="hidden" name="languageId" value="${language.getLanguageId()}"/></c:if>

        <div class="header mini">
            <span class="headerText">Данные языка</span>
        </div>

        <label class="oH mb10 block">
            <div class="flL w260 h30p mr10 taR">Имя языка</div>
            <div class="flL w300">
                <input type="text" name="name" class="formInput bgWhite" value="${language.getName()}" ui-style/>
            </div>
        </label>

        <label class="oH mb10 block">
            <div class="flL w260 h30p mr10 taR">Код языка</div>
            <div class="flL w300">
                <input type="text" name="code" class="formInput bgWhite" value="${language.getCode()}" ui-style/>
            </div>
        </label>

        <label class="oH mb10 block">
            <div class="flL w260 h30p mr10 taR">Активность языка</div>
            <div class="flL w300">
                <input type="checkbox" name="enabled" value="true"<c:if test="${language.isEnabled()}"> checked="true"</c:if> ui-style/>
            </div>
        </label>

        <input type="submit" class="button full bgGreen hover" value="Сохранить"/>
    </form>
</div>