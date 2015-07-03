<%@ page contentType="text/html;charset=UTF-8" language="java" %><%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %><jsp:include page="../common/config.jsp"/>
<jsp:include page="frames/header.jsp"/>

    <div id="mainContent">
        <jsp:include page="frames/sidebar.jsp"/>

        <div id="globalContent" class="bgWhite">
        <jsp:include page="modules/${PC.getModule()}/${PC.getAction()}.jsp"/>
        </div>
    </div>

<jsp:include page="frames/footer.jsp"/>