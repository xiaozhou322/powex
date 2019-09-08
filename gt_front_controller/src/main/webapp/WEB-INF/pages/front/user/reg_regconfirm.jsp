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
<link rel="stylesheet" href="${oss_url}/static/front2018/css/index/main.css?v=20181126201750" type="text/css"></link>
</head>
<style type="text/css">
	.l-header{background:#363842!important;}
</style>
<body style="background:#fff;">

<%@include file="../comm/header.jsp" %>

	<div class="container-full" style="min-height:810px; padding-top:150px;">
		<div class="container mg text-center validate">
			<div style="text-align:center;">
				<div class="validate-online" style="width:auto;">
					
						<c:if test="${validate == false }">
							<div class="validate-success failure">
								<span class="validate-text failure"><spring:message code="register.actfail" /></div>
							</span>
						</c:if>
						<c:if test="${validate == true }">
						<div class="validate-success">
								<span class="validate-text"><spring:message code="register.accactsuc" /></span>
							</div>
						</c:if>	
					<div>
						<span class="form-group" style="margin-right:5px;">
							<a class="btn btn-danger btn-block" href="/index.html"><spring:message code="nav.top.home" /></a>
						</span>
						<span class="form-group">
							<a class="btn btn-danger btn-block" href="/user/login.html"><spring:message code="nav.top.login" /></a>
						</span>
					</div>
				</div>
			</div>
		</div>
	</div>
	

<%@include file="../comm/footer.jsp" %>	
<script type="text/javascript" src="${oss_url}/static/front2018/js/main/main.js?v=20181126201750"></script>


</body>
</html>
