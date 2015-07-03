<%@ page contentType="text/html;charset=UTF-8" language="java" %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><c:if test="${pagination.getPageCount() > 1}">
        <div class="pagination bgSilver bgOff" ui-pagination="${module.getModuleName()}" data-elements-count="${pagination.getCount()}" data-element-per-page="${pagination.getPerPage()}" data-current-page="${pagination.getPage()}" data-page-offset="${pagination.getPageOffset()}">
            <div class="pagesList">
                <a href="${pagination.getUrl(pagination.getLeft())}" class="pageElement bgParent hover left">&lt;</a>
                <c:forEach items="${pagination.getPageList()}" var="page">
                <c:choose><c:when test="${page > 0}"><a href="${pagination.getUrl(page)}" ui-route="update" class="pageElement bgParent hover${pagination.getPage() == page ? " active" : ""}">${page}</a></c:when><c:otherwise><span class="pageElement">...</span></c:otherwise></c:choose></c:forEach>
                <a href="${pagination.getUrl(pagination.getRight())}" class="pageElement bgParent hover right">&gt;</a>
            </div>
        </div></c:if>