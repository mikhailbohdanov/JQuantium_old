<%@ page contentType="text/html;charset=UTF-8" language="java" %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <div id="footerBottom" class="bgBlue">
            <div class="content">
                <ul class="columns columnsFlex">
                    <c:forEach items="${PC.getMenu(\"footerBottom\").getItems()}" var="item"><li class="fCA">
                        <a<c:if test="${item.isLink()}"> href="${item.getHref()}" class="link cWhite hWhite<c:if test="${item.isCurrentPage(PC)}"> active</c:if>"</c:if>>${item.getValue()}</a>
                    </li>
                </c:forEach></ul>
            </div>
        </div>