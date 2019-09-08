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
<script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
<%@include file="../comm/link.inc.jsp" %>

<link rel="stylesheet" href="${oss_url}/static/front/css/dowload/index.css?v=20181126201750" type="text/css"></link>
<style type="text/css">
.header {
	position: relative;
}
</style>
</head>
<body class="gray-bg">
	<div class="container-full header dowload">
		<div class="container-full header-nav">
			<div class="container padding-clear">
				<div class="navbar" role="navigation">
					<div class="navbar-header navbar-default">
						<button type="button " class="navbar-toggle" data-toggle="collapse" data-target="#example-navbar-collapse">
							<span class="sr-only"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
						</button>
						<a class="navbar-brand" href="">
							<img alt="${requestScope.constant['webName']}" src="${requestScope.constant['logoImage'] }">
						</a>
					</div>
					<div class="collapse navbar-collapse navbar-right" id="example-navbar-collapse">
						<ul class="nav navbar-nav ">
							<li class="">
									<a href="/">首页
										<div class="relative">
											<span class="line"></span>
										</div>
									</a>
								</li>		
								
								<li class="">
									<a href="/trade/coin.html">交易中心
										<div class="relative">
											<span class="line"></span>
										</div>
									</a>
								</li>
								
								<li class="">
									<a href="/financial/index.html">财务中心
										<div class="relative">
											<span class="line"></span>
										</div>
									</a>
								</li>
								<li class="">
									<a href="/market.html">行情中心
										<div class="relative">
											<span class="line"></span>
										</div>
									</a>
								</li>
								<li class="">
									<a href="/crowd/index.html">众筹中心
										<div class="relative">
											<span class="line"></span>
										</div>
									</a>
								</li>
								<li class="">
									<a href="/user/security.html">个人中心
										<div class="relative">
											<span class="line"></span>
										</div>
									</a>
								</li>
								<li class="">
									<a href="/about/index.html">帮助中心
										<div class="relative">
											<span class="line"></span>
										</div>
									</a>
								</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div class="container-full ">
			<div class="container ">
				<div class="dcenter">
<!-- 					<div class="dcenter-left">
						<img src="https://static.bichuang.com/upload/args/58638f875b6e4453bcefa6db6fdfd598_1481009984.png">
					</div> -->
					<div class="dcenter-right">
						<a target="_blank" href="${requestScope.constant['android_downurl'] }">Android版下载</a>
						<a class="cen" target="_blank" href="${requestScope.constant['ios_downurl'] }">iPhone版下载</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="container-full download-bottom"></div>
</body>
</html>