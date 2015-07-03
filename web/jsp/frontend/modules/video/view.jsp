<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="header big bgBlue">
    <span class="headerText ml10">${video.getTitle()}</span>
</div>

<div class="content auto">
    <iframe width="800" height="450" src="${video.getPlayerUrl()}" frameborder="0" allowfullscreen></iframe>
</div>
