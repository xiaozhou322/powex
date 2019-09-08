<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>

<!doctype html>
<html>
<head> 
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp" %>

<link rel="stylesheet" href="${oss_url}/static/front/css/user/reset.css?v=20181126201750" type="text/css"></link>
<link rel="stylesheet" href="${oss_url}/static/front/css/index/main.css?v=20181126201750" type="text/css"></link>
</head>
<body style="background:#1d2c43;">
	
 

<div class="container-full " style="min-height:835px; padding-top:100px;">
 			<c:if test="${code==0000}">
			  ${msg}
			</c:if>
			<c:if test="${code==1122}">
			  ${msg}
			</c:if>
			<c:if test="${code==1133}">
			  ${msg}<br>
			  <a href="/user/register.html?phone=cn">去注册</a>
			</c:if>
		    <a href="/index.html">首页</a>
			
	</div>


	
<p class="CopyRight" style="margin:0;">CopyRight© 2013-2017 GBCAX.com All Rights Reserved</p>


</body>
</html>
