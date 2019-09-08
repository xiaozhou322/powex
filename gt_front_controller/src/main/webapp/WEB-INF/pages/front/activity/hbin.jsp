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
<%@include file="../comm/link.inc.jsp" %>

<link rel="stylesheet" href="${oss_url}/static/front/css/user/reset.css?v=20181126201750" type="text/css"></link>
<link rel="stylesheet" href="${oss_url}/static/front/css/index/main.css?v=20181126201750" type="text/css"></link>
</head>
<body style="background:#1d2c43;">
	
 

<div class="container-full " style="min-height:835px; padding-top:100px;">
		    登录成功，欢迎您：${sessionScope.login_user.fnickName}, ${sessionScope.login_user.floginName}:<br> 
		 <form action ="/activitywalletrecord/save.html" method="post">
		  <input name="mobileno"  type="hidden"  value="${sessionScope.login_user.floginName}"><br>
		  <input name="activityid" type="hidden" value="${activityid}"><br>
		  <input type="submit" value="领取红包"/>
		 </form>
 </div>


	
	
	
<p class="CopyRight" style="margin:0;">CopyRight© 2013-2017 GBCAX.com All Rights Reserved</p>


</body>
</html>
