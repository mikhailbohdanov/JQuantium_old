<%@ page contentType="text/html;charset=UTF-8" language="java" %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <div id="globalSidebar">
            <c:if test="${PC.hasAnyAccess(\"USERS\")}"><div class="sidebarMenu bgSilver open">
                <a href="/${HOME}/users" class="mainLink bgBlackT hover button full h40p"><div class="ml10">Управление пользователями</div></a>

                <ul class="links">
                    <li><a class="link bgWhite hover" href="/${HOME}/users">Список пользователей</a></li>
                    <c:if test="${PC.hasAccess(\"USERS\", \"CREATE\")}"><li><a class="link bgWhite hover" href="/${HOME}/users/create">Создать пользователя</a></li></c:if>
                    <c:if test="${PC.hasAccess(\"USERS\", \"MODIFY_PERMISSIONS\")}"><li><a class="link bgWhite hover" href="/${HOME}/users/create">Управление правами доступа</a></li></c:if>

                </ul>
            </div></c:if>

            <c:if test="${PC.hasAnyAccess(\"LANGUAGES\")}"><div class="sidebarMenu bgSilver open">
                <a href="/${HOME}/languages" class="mainLink bgBlackT hover button full h40p"><div class="ml10">Управление языками</div></a>

                <ul class="links">
                    <li><a class="link bgWhite hover" href="/${HOME}/languages">Список языков</a></li>
                    <c:if test="${PC.hasAccess(\"LANGUAGES\", \"CREATE\")}"><li><a class="link bgWhite hover" href="/${HOME}/languages/create">Создать язык</a></li></c:if>

                </ul>
            </div></c:if>

        </div>