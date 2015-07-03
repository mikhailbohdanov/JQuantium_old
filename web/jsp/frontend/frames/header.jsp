<%@ page contentType="text/html;charset=UTF-8" language="java" %><!DOCTYPE html>
<html>
<head>
    <noscript><meta http-equiv="refresh" content="0; URL=/browser"></noscript>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />

    <title id="title">${PC.getClientPage().getTitle()}</title>
    <meta name="description" id="description" content=""/>
    <meta name="keywords" id="keywords" content=""/>

    <link rel="stylesheet" href="http://re.vo.org.ua/css/main.css" type="text/css"/>
    <link rel="stylesheet" href="http://re.vo.org.ua/css/pages.css" type="text/css"/>
    <link rel="stylesheet" href="http://re.vo.org.ua/css/common.css" type="text/css"/>

    <script type="text/javascript" src="http://re.vo.org.ua/js/jquery.js"></script>
    <script type="text/javascript" src="http://re.vo.org.ua/js/jquery.history.js"></script>
    <%--<script type="text/javascript" src="http://re.vo.org.ua/js/jquery.router.js"></script>--%>
    <script type="text/javascript" src="http://re.vo.org.ua/js/main.js"></script>
    <script type="text/javascript" src="http://re.vo.org.ua/js/front.js"></script>

    <link rel="icon" href="http://re.vo.org.ua/images/favicon.ico" type="image/x-icon"/>
</head>
<body>
<div id="layerBG" class="bgBlack hide"></div>
<div id="layerModal" class="hide">
    <div>
        <a href="#" class="layerButton left"><span class="icon hide"><span></span></span></a>
        <a href="#" class="layerButton right"><span class="icon bigCross white"><span></span></span></a>
        <div id="layerWrapper" class="scroll pt20 pb80" style="">
            <div id="layerContent" class="bgWhite" ui-view="modal" ui-view-static></div>
        </div>
    </div>
</div>
<div id="layerLoader" class="hide" ui-view="layerLoader" ui-view-static></div>
<div id="layerMessages" class="hide" ui-view="layerMessages" ui-view-static></div>

<div id="globalWrapper" class="<%--hideFooter--%>" ui-view="globalWrapper" ui-view-static>
    <div id="header">
        <jsp:include page="header/top.jsp"/>
        <jsp:include page="header/middle.jsp"/>
        <jsp:include page="header/bottom.jsp"/>
    </div>