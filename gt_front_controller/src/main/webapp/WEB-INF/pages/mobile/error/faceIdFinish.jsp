<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>
<%

%>

<!doctype html>
<html>
<head> 
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
 <meta name = "viewport" content = "width = device-width, user-scalable = no,initial-scale=0.5,maximum-scale=0.5,minimum-scale=0.5">
<%@include file="../comm/link.inc.jsp" %>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/main.js?v=20181126201750"></script>
<script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
</head>


    <meta charset="utf-8">
    <title></title>
 
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
     <style type="text/css">
        @font-face {
           font-family: 'PingFangMedium';
           src: url('/static/mobile2018/fonts/PingFangMedium.ttf'); 
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
<p>
<%
String errorcode=request.getParameter("status_text");
if(null==errorcode){
	out.println("认证异常，请重新进行认证");
}
if(errorcode.equals("OK")){
	out.println("认证成功，请登陆网站查看认证结果。");
}else if(errorcode.equals("CANCELLED")){
	out.println("认证失败，原因：用户主动取消验证流程");
}else if(errorcode.equals("TIMEOUT")){
	out.println("认证失败，原因：认证操作超时");
}else if(errorcode.equals("IdError")){
	out.println("认证失败，原因：身份证号码不符");
}else if(errorcode.equals("SignError")){
	out.println("认证失败，原因：验签失败，请联系平台");
}else if(errorcode.equals("PROCESSING")){
	out.println("正在进行POWEX.PRO身份验证，请稍后刷新查看认证结果。");
}else if(errorcode.equals("FAILED")){
	out.println("认证失败，原因：验证流程未完成或出现异常");
}else{
	out.println("认证失败，原因：未知状态");
}
%>
</p>
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