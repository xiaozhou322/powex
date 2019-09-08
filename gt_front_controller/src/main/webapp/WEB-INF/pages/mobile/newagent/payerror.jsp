<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>

<!doctype html>
<html>
<head> 
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp" %>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/main.js"></script>

</head>


    <meta charset="utf-8">
    <title>Error_500</title>
 
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
    <h2 class="tit"><c:if test="${returnUrl != null && returnUrl !=''}">   
           		<a href="${returnUrl}" style="color:red">返回页面</a>
            </c:if></h2>
</header>
<section class="section regAgain textC error">
    <div class="error" style="width:100%;heigth:500px;margin-top:120px;margin-bottom:120px;line-height:50px">
    <h1 style="font-size:30px;color:#E93E66;">错误信息提示</h1>
    <h1 style="font-size:24px;margin-top:30px">${errCode}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${errMsg}</h1>
    </div>
    <c:if test="${returnUrl != null && returnUrl !=''}">   
   		<a class="btn returnHome regAgainBtn" href="${returnUrl}">返回页面</a>
    </c:if>
</section>

<%@include file="../comm/footer.jsp" %> 
<script type="text/javascript"	src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
<script type="text/javascript">
    $(".back").click(function(event){
        window.history.go(-1);
    });
</script>
</body>
</html>