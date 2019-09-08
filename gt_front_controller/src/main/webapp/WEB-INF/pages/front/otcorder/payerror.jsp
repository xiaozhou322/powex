<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>

<!doctype html>
<html>
<head> 
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp" %>
</head>
    <meta charset="utf-8">
    <title>Error_500</title>
    <style type="text/css">
    html{height:100%;}
    .l-header{background:#363842; z-index:9999;}
     body {
         width:100%;
         height:100%;
         background:url(static/front2018/images/errorbg.png) repeat-x 0 0;
     }
     .errorPage{
         font-family: Arial,Helvetica,sans-serif;
         font-size: 14px; padding-bottom:84px;
         margin: 50px 0 0 0;
     }
     .img{width:1400px; height:600px;display:block; margin:0 auto 0; opacity:0.8;}
     .p{font-size:18px; color:#36A5F9;padding-top:40px;}
     .returnBtn{width:200px; height:52px; text-align:center; line-height:50px; padding:0; display:inline-block; font-size:20px; border: 2px solid #007ed7; color:#0093dd; border-radius:8px; margin:20px 0 0 0; transition:all 0.3s;}
     .returnBtn:hover,:focus{background:#36A5F9; color:#fff; border-color:#36A5F9;}
    </style>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
</head>
<body>
<%@include file="../comm/otc_header.jsp" %>
 <section class="errorPage">
    <div class="mgs"> 
	    <img class="img" src="/static/front2018/images/otcerror.png" alt="" />
	    <div class="error" style="width:500px;heigth:200px;line-height:50px;position: absolute;left:630px;top:245px;">
	    	<h1 style="font-size:30px;color:#E93E66;">错误信息提示</h1>
	    	<h1 style="font-size:18px;">${errCode}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${errMsg}</h1>
			<p class="p">
				<%-- <a class="returnBtn" href="/"><spring:message code="nav.top.home" /></a> --%>
			</p>  
		 </div> 
    </div>

</section>

<%@include file="../comm/otc_footer.jsp" %> 
</body>
</html>