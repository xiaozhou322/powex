<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;if (request.getServerName().equals("www.gbcax.com")){basePath="https://www.gbcax.com";}
%>

<!doctype html>
<html>
<head> 
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp" %>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/main.js?v=20181126201750"></script>

</head>


    <meta charset="utf-8">
    <title></title>
 
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
     <style type="text/css">
        @font-face {
           font-family: 'PingFangMedium';
           src: url('/static/mobile/fonts/PingFangMedium.ttf'); 
        } 
    .error img{margin:1rem auto;}
    .returnHome{width:90%; height:0.6rem; border-radius:0; line-height:0.6rem; margin:1rem auto 0; padding:0;}
    </style>
</head>
<body>
<%@include file="../comm/header.jsp" %>
 <header class="tradeTop">  
    <i class="back toback2"></i>
</header>
<section class="section regAgain textC error">
    <p>认证完成，请稍后刷新查看认证结果。</p>
    <a class="btn returnHome regAgainBtn" href="${requestScope.constant['fulldomain'] }"><spring:message code="nav.top.home" /></a>
</section>

<%@include file="../comm/footer.jsp" %> 
<script type="text/javascript">
    $(".back").click(function(event){
        window.history.go(-1);
    });
</script>
</body>
</html>