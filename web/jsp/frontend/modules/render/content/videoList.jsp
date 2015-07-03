<%@ page contentType="text/html;charset=UTF-8" language="java" %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
                <div class="columns columnsFlex">
                    <c:forEach items="${module.getData()}" var="video">
                    <a href="?video=${video.getVideoId()}" ui-route="update" class="element fC5 square videoElement bgSilver imageLoad">
                        <div class="label right bgWhite"><span>NEW</span></div>
                        <img class="videoImage" src="/_images/214x214.jpg" alt=""/>
                        <span class="icon play"><span></span></span>
                        <span class="videoTitle tOH">${video.getTitle()}</span>
                    </a></c:forEach>
                </div>