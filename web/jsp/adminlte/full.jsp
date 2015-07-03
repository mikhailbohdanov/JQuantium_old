<%@ page contentType="text/html;charset=UTF-8" language="java" %><%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %><jsp:include page="../common/config.jsp"/>
<jsp:include page="frames/header.jsp"/>

    <jsp:include page="frames/sidebar.jsp"/>

    <div class="content-wrapper">

        <jsp:include page="modules/${PC.getModule()}/${PC.getAction()}.jsp"/>

    </div>

<jsp:include page="frames/footer.jsp"/>