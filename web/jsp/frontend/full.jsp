<%@ page contentType="text/html;charset=UTF-8" language="java" %><%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:include page="frames/header.jsp"/>

    <div id="mainContent" ui-view="mainContent">
        <jsp:include page="modules/${PC.getModule()}/${PC.getAction()}.jsp"/>
    </div>

<jsp:include page="frames/footer.jsp"/>