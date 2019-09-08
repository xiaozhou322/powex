<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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

<link rel="stylesheet" href="${oss_url}/static/front/css/user/user.css?v=20181126201750" type="text/css"></link>
<link href="${oss_url}/static/front/css/index/main.css?v=20181126201750" rel="stylesheet" type="text/css" />
<script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
</head>
<body>
	




<%@include file="../comm/header.jsp" %>
<!-- 	<div class="container-full body-main">
	<div class="container displayFlex">
		<%@include file="../comm/left_menu.jsp" %> -->

	<div class="lw-content" style="background:#1d2c43;">
		<div class="lw-finance" style="padding:0;">		
	         <div class="lw-financeLeft fl">
	            <ul>
	                <li class="tabBtn "><a href="/user/security.html?tab=0"><spring:message code="security.security" /> <i></i></a></li>
	                <li class="tabBtn"><a href="/user/security.html?tab=1"><spring:message code="security.bindphone" /><i></i></a></li>
			        <li class="tabBtn"><a href="/user/security.html?tab=2"><spring:message code="security.bindemail" /><i></i></a></li>
			        <li class="tabBtn"><a href="/user/security.html?tab=3"><spring:message code="security.logpas" /><i></i></a></li>
	                <li class="tabBtn"><a href="/user/security.html?tab=4"><spring:message code="security.tradingpwd" /><i></i></a></li>
	                <li class="tabBtn"><a href="/user/realCertification.html"><spring:message code="security.ideaut" /><i></i></a></li>
	                <li class="tabBtn"><a href="/user/security.html?tab=6" ><spring:message code="security.gooaut" /><i></i></a></li>
	                <li class="tabBtn lw-cur"><a href="/question/question.html"><spring:message code="security.workcenter" /><i></i></a></li>
	            </ul>
      		 </div>




<!-- 			<div class="col-xs-10 padding-right-clear">
	<div class="col-xs-12 padding-right-clear padding-left-clear rightarea vip">
	
	<ul class="nav nav-tabs rightarea-tabs">
				<li>
					<a href="/user/level.html">等级积分</a>
				</li>
				<li class="active">
					<a href="javascript:void(0)">积分记录</a>
				</li>
			</ul> -->
						
			<div class="lw-financeRight">
				<h1 class="lw-financeTit">积分等级</h1>
				<div class="lw-coinTitList  vip">			
					<ul>
						<li class="fl"><a href="/user/level.html">等级积分</a></li>
						<li class="fl lw-active"><a href="javascript:void(0)">积分记录</a></li>
                  		<div class="clear"></div>
                    </ul>				

					<div class="col-xs-12 padding-clear padding-top-30">
							<table class="table table-striped">
								<tbody><tr class="bg-gary">
									<td class="col-xs-4">时间</td>
									<td class="col-xs-4">类型</td>
									<td class="col-xs-4">获得积分</td>
								</tr>
								
								<c:forEach items="${fscoreRecords }" var="v">
									<tr>
										<td>${v.fcreatetime }
										</td>
										<td>${v.type_s }</td>
										<td>${v.score }</td>
									</tr>
								</c:forEach>
								
								
									
							</tbody></table>
							
								<div class="text-right">
									${pagin }</div>
					</div>
				</div>
			</div>
		</div>
	</div>

<%@include file="../comm/footer.jsp" %>	

</body>
</html>







