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
<link href="${oss_url}/static/front/css/index/main.css?v=2" rel="stylesheet" type="text/css" />
<link href="${oss_url}/static/front2018/css/lucky.css?v=20181126201750" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${oss_url}/static/front2018/js/index/main.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/layer/layer.js?v=20181126201750"></script>
<style type="text/css">
  .lw-helpCons{background:#ffffff;}
  .lw-heilpTabs{background:#f8f8f8;}
  .lw-helpRight{background:#f8f8f8; border:none;}
  .lw-heilpTabs ul li{border: 1px solid #fff;}
  .lw-helpRight h2{color:#333; font-weight:600;}
  .lw-helpNav{height: 80px; line-height:80px; background: #f8f8f8;border-bottom: 1px solid #e3e3e3;}
  .lw-helpNav a{color:#6f6f6f;}
    .lw-helpNav a:hover{background:none; color:#2269d4;}
  .lw-helpNav a.lw-active{background:#fff; color:#6f6f6f; border-bottom: 1px solid #2269d4;}
</style>
</head>
<body style="background: #F9FBFF;">
<%@include file="../comm/header.jsp" %>
<section class="l-content" >
<div class="communityTop">
	<h3>社区专属域名 分享交易收益</h3>  
</div>
<br>
<div class="luckyCenter">
	<!-- 活动规则介绍 -->
	<div class="luckyTit">
		<h3>社群账户权益介绍</h3>
		<span></span>	<!-- 虚线 -->
	</div>
	<div class="communityBox">
		<ul class="communityUl">
			<li>
				<img src="${oss_url}/static/front2018/images/exchange/tuijian2x.png">
				<h3>市场推荐</h3>
				<p>优质市场推荐，分享交易收益。</p>
				<span><img src="${oss_url}/static/front2018/images/exchange/1community.png"></span>
			</li>
			<li>
				<img src="${oss_url}/static/front2018/images/exchange/peizhi.png">
				<h3>域名配置</h3>
				<p>配置专属二级域名单项目专区权益。</p>
				<span><img src="${oss_url}/static/front2018/images/exchange/2community.png"></span>
			</li>
			<li>
				<img src="${oss_url}/static/front2018/images/exchange/gonggao.png">
				<h3>发布公告</h3>
				<p>实时发布公告信息首页专栏展示，发布公告条数不限。</p>
				<span><img src="${oss_url}/static/front2018/images/exchange/3community.png"></span>
			</li>
			<li>
				<img src="${oss_url}/static/front2018/images/exchange/shouyi2x.png">
				<h3>收益管理</h3>
				<p>个人资产收益统一管理，可视化信息清晰呈现。</p>
				<span><img src="${oss_url}/static/front2018/images/exchange/4community.png"></span>
			</li>
		</ul>
		<ul class="powexpro">
			<li>bd@powex.pro</li>
			<li><img src="${oss_url}/static/front2018/images/exchange/saoma2x.png"></li>
			<li>联系我们申请社群账户</li>
		</ul>
	</div>
	
</div>

</section> 
<%@include file="../comm/footer.jsp" %>	

<script type="text/javascript" src="${oss_url}/static/front2018/js/jquery.rotate.min.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/main/scroll.js?v=20181126201750"></script>

</body>
</html>
