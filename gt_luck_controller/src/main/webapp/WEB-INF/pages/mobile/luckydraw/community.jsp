<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<!-- <meta name = "viewport" content = "width = device-width, initial-scale = 1.0, maximum-scale = 1.0, user-scalable = 0" /> -->
<meta name = "viewport" content = "width = device-width, user-scalable = no,initial-scale=0.5,maximum-scale=0.5,minimum-scale=0.5">
<%@include file="../comm/link.inc.jsp" %>
</head>
<body>
<%@include file="../comm/header.jsp" %>
<link href="${oss_url}/static/mobile2018/css/base.css?v=2" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${oss_url}/static/mobile2018/css/lucky/community.css?v=20181126201750" type="text/css"/>
<header>
			<div class="h_title">社区专属域名 分享交易收益</div>
		</header>
		<section>
			<div class="sec_title">
				<p>社群账户权益介绍</p>
				<div class="thick_line"></div>
				<div class="fine_line"></div>
			</div>
			<div>
				<div class="sec_con">
					<ul>
						<li><img src="${oss_url}/static/mobile2018/images/exchange/tuijian@2x.png"></li>
						<li>市场推荐 </li>
						<li>优质市场推荐，分享交易收益。</li>
						<li><img src="${oss_url}/static/mobile2018/images/exchange/1@2x.png"></li>
					</ul>
				</div>
				<div class="sec_con">
					<ul>
						<li><img src="${oss_url}/static/mobile2018/images/exchange/yuming@2x.png"></li>
						<li>域名配置</li>
						<li>配置专属二级域名 单项目专区权益。</li>
						<li><img src="${oss_url}/static/mobile2018/images/exchange/2@2x.png"></li>
					</ul>
				</div>
				<div class="sec_con">
					<ul>
						<li><img src="${oss_url}/static/mobile2018/images/exchange/gonggao@2x.png"></li>
						<li>发布公告</li>
						<li>实时发布公告信息首页专栏展示。</li>
						<li><img src="${oss_url}/static/mobile2018/images/exchange/3@2x.png"></li>
					</ul>
				</div>
				<div class="sec_con">
					<ul>
						<li><img src="${oss_url}/static/mobile2018/images/exchange/shouyi@2x.png"></li>
						<li>收益管理</li>
						<li>资产收益统一管理信息清晰呈现。</li>
						<li><img src="${oss_url}/static/mobile2018/images/exchange/4@2x.png"></li>
					</ul>
				</div>
			</div>
			<div class="f_code">
				<div>bd@powex.pro</div>
				<img src="${oss_url}/static/mobile2018/images/exchange/lianxi@2x.png" />
				<p>联系我们成为申请社群账户</p>
			</div>
		</section>
<%@include file="../comm/tabbar.jsp"%>
<%@include file="../comm/footer.jsp" %>
<script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
<script src="${oss_url}/static/mobile2018/js/fluckydraw/index.js?v=20181126201750"></script>
</body>
</html>