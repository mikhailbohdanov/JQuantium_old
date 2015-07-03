<%@ page contentType="text/html;charset=UTF-8" language="java" %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <div id="headerBottom" class="bgWhite">
            <div class="content">
                <ul class="">
                    <c:forEach items="${PC.getMenu(\"headerBottom\").getItems()}" var="item"><li class="fC dropMenu">
                    <a href="${item.getHref()}" ui-route="replace" ui-class="active" class="menuItem<c:if test="${item.isCurrentPage(PC)}"> active</c:if>">${item.getValue()}</a>
                    <c:if test="${item.getChildren().size() > 0}"><div class="dropList">
                        <c:forEach items="${item.getChildren()}" var="child">
                        <div class="listItem"><a href="${child.getHref()}" ui-route="replace" ui-class="active" class="link cWhite hWhite<c:if test="${child.isCurrentPage(PC)}"> active</c:if>">${child.getValue()}</a></div></c:forEach>
                    </div>
                    </c:if>
                    </li>
                    </c:forEach>
                </ul>
            </div>
        </div>