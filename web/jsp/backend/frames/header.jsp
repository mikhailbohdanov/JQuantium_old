<%@ page contentType="text/html;charset=UTF-8" language="java" %><!DOCTYPE html>
<html>
<head>
    <noscript><meta http-equiv="refresh" content="0; URL=/browser"></noscript>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />

    <title>${PC.getClientPage().getTitle()}</title>

    <link rel="stylesheet" href="//re.vo.org.ua/css/main.css" type="text/css"/>
    <link rel="stylesheet" href="//re.vo.org.ua/css/common.css" type="text/css"/>
    <link rel="stylesheet" href="//re.vo.org.ua/css/admin.css" type="text/css"/>

    <script type="text/javascript" src="http://re.vo.org.ua/js/jquery.js"></script>
    <script type="text/javascript" src="http://re.vo.org.ua/js/jquery.router.js"></script>
    <script type="text/javascript" src="http://re.vo.org.ua/js/main.js"></script>
    <script type="text/javascript" src="http://re.vo.org.ua/js/front.js"></script>


</head>
<body>
<div id="layerBG" class="hide"></div>
<div id="layerModal" class="hide"></div>
<div id="layerLoader" class="hide"></div>
<div id="layerMessages" class="hide"></div>

<div id="globalWrapper">
    <jsp:include page="header/menu.jsp"/>
    <jsp:include page="header/main.jsp"/>