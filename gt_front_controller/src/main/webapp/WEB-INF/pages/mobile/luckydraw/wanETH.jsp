<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%

%>

<!doctype html>
<html>
<head> 
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<!-- <meta name = "viewport" content = "width = device-width, initial-scale = 1.0, maximum-scale = 1.0, user-scalable = 0" /> -->
<meta name = "viewport" content = "width = device-width, user-scalable = no,initial-scale=0.5,maximum-scale=0.5,minimum-scale=0.5">
<%@include file="../comm/link.inc.jsp" %>
</head>
<body>
<%@include file="../comm/header.jsp" %>
<link href="${oss_url}/static/mobile2018/css/base.css?v=2" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${oss_url}/static/mobile2018/css/lucky/wanETH.css" />
<link rel="stylesheet" href="${oss_url}/static/mobile2018/css/lucky/jquery.mCustomScrollbar.css" />
	<a href="JavaScript:history.go(-1)"><i class="back" style="margin-left: 0.5rem;"></i></a>
	<header></header>
		<section class="draw_lottery">
			<div class="draw_num">
				<img src="${oss_url}/static/mobile2018/images/exchange/jinagpiaohao@3x.png" />
			</div>
			<div class="draw_content">
				<div class="d_title">
					<img src="${oss_url}/static/mobile2018/images/exchange/biaoti1@3x.png" />
					<span>摇号规则</span>
					<img src="${oss_url}/static/mobile2018/images/exchange/biaoti@3x.png" />
				</div>
				<div class="d_cons">
					1、平台注册用户成功完成KYC2认证，可获得抽取奖票号资格，抽取次数不限<br />
					2、用户可使用获赠或充值购买POW参与万币活动，获得开奖将票号<br />
					3、1POW对应一个奖票号，用户可以选择摇奖注数，同时获得多个奖票号
				</div>
				<div class="d_hint">
					*本次活动赠送的POW仅在本次活动有效，
					未刮奖的POW将在活动结束后自动失效清除。 
				</div>
				<!-- 当前POW -->
					<ul class="wanEthUL2">
						<li>摇奖注数
							<select id="numETH">
								<option>1</option>
								<option>10</option>
								<option>50</option>
								<option>100</option>
							</select>
						</li>
						<li>
							当前POW<span class="totalPowNum">10</span>
						</li>
					</ul>
					
					<!-- datawanETH抽奖功能 -->
					<ul class="wanEthUL3">
						<li>
							<div class="wanEthUL3Span">
								<div class="wanEthnum"></div>
								<div class="wanEthnum"></div>
								<div class="wanEthnum"></div>
								<div class="wanEthnum"></div>
								<div class="wanEthnum"></div>
								<div class="wanEthnum"></div>
								<div class="wanEthnum"></div>
								<div class="wanEthnum"></div>
								<div class="wanEthnum"></div>
							</div>
						</li>
						<li class="d_click" align="center">
							<img src="${oss_url}/static/mobile2018/images/exchange/button@3x.png" class="wanETHbtn" style="padding-top: 0px; height: 1.3rem;width: 1.5rem;"/>
						</li>
						
					</ul>
				<div class="d_title">
					<img src="${oss_url}/static/mobile2018/images/exchange/biaoti1@3x.png" />
					<span>奖票信息</span>
					<img src="${oss_url}/static/mobile2018/images/exchange/biaoti@3x.png" />
				</div>
				<div class="wanEthUL3Prize">
							<ul class="wanEthUL3tit">
								<li>用户ID</li>
								<li>奖票号</li>
								<li>时间</li>
							</ul>
							<div class="wanEthUL3Box">
								<ul class="wanEthUL3Td" id="LotteryList">
								</ul>
							</div>
						</div>
					</div>
			<!--弹出窗-->
			<div class="wanEthModule" >
				<span class="wanEthModuleClose">×</span>
				<h3>您好，您本次的奖票号为</h3>
				<ul  class="wanscroll">
				</ul>
				
			</div>
			
				<!-- 认证获取更多-->
<div class="realfixed ngrealModule">
	<ul style="line-height: 30px;">
		<h3><img src="${oss_url}/static/front2018/images/close@2x.png" class="realclose"></h3>
		<li>
			<p class="powModules"></p>
			<p>参与平台摇奖</p>
			<a href="" class="googleBtn lotterKYCBtn powModulesBtn" style="margin-top: 39px;"></a>
		</li>
	</ul>
</div>
  <!-- 蒙层 -->
  <div class="fixedModule ngrealModule"></div>
  <input id="nper_id" type="hidden"/>
  
		</section>

<script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/fluckydraw/scroll.js" ></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/fluckydraw/easing.js" ></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/fluckydraw/jquery.mCustomScrollbar.concat.min.js" ></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/fluckydraw/waneth.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/layer/layer.js?v=20181126100022.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/comm/util.js?v=20181126100022.js?v=20181126201750"></script>

</body>
</html>