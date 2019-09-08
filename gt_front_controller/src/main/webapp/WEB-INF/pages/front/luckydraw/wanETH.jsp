<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>


<!doctype html>
<html>
<head> 
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp" %>
<link href="${oss_url}/static/front2018/css/lucky.css?v=20181126201750" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${oss_url}/static/front2018/css/jquery.mCustomScrollbar.css?v=20181126201750" type="text/css"></link>

<%-- <script type="text/javascript" src="${oss_url}/static/front2018/js/index/main.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/layer/layer.js?v=20181126201750"></script>
 --%>
<%-- <script type="text/javascript" src="${oss_url}/static/front2018/js/fluckydraw/index.js?v=20181126201750"></script> --%>

<script type="text/javascript" src="${oss_url}/static/front2018/js/fluckydraw/easing.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/jquery.mCustomScrollbar.concat.min.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/main/scroll.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/layer/layer.js?v=20181126100022.js?v=20181126201750"></script>

<script type="text/javascript" src="${oss_url}/static/front2018/js/comm/util.js?v=20181126100022.js?v=20181126201750"></script>

</head>
<body style="background: #F9FBFF;">

<section class="l-content">
	<div class="wanEthBox">
		<ul class="wanEthUl">
			<li>
				<img src="${oss_url}/static/front2018/images/exchange/wanETHfontbg2x.png" />
			</li>
			<li style="margin-top: 66px;">
				<img src="${oss_url}/static/front2018/images/exchange/yuanjiao2x.png" />
			</li>
		</ul>
		<div class="wanEthTime">
			<div class="wanEthTit">
				<ul class="wanEthTit">
					<li>距离POW目标值还有：<span></span></li>
					<li>
					<label class="wanEthTithour">0000000000</label>
					<!-- <label class="wanEthTithour">00</label>
					<label class="wanEthTithour">00</label>
					<label class="wanEthTithour">00</label>
					<label class="wanEthTithour">00</label>
					<label class="wanEthTithour">00</label>
					<label class="wanEthTithour">00</label>
 -->						<!-- <label class="wanEthTitday">00</label>
						<span>天</span>
						<label class="wanEthTithour">00</label>
						<span>时</span>
						<label class="wanEthTitminute">00</label>
						<span>分</span>
						<label class="wanEthTitsecond">00</label>
						<span>秒</span> -->
					</li>
				</ul>
			</div>
			<!-- 活动结束 -->
			<ul class="wanEthIntroduce">
				<h3>
					<span><img src="${oss_url}/static/front2018/images/exchange/biaoti1wanETH.png">&nbsp;&nbsp;摇号规则&nbsp;&nbsp;<img src="${oss_url}/static/front2018/images/exchange/wanETHbiaoti2x.png"></span>
					<a href="/lucky/luckyIndex.html">活动详情>></a>
				</h3>
				<li>1、平台注册用户成功完成KYC2认证，可获得抽取奖票号资格，抽取次数不限</li>
				<li>2、用户可使用获赠或充值购买POW参与万币活动，获得开奖将票号</li>
				<li style="height: 30px;">3、1POW对应一个奖票号，用户可以选择摇奖注数，同时获得多个奖票号</li>
				<h4>*本次活动赠送的POW仅在本次活动有效，未刮奖的POW将在活动结束后自动失效清除。 </h4>
			</ul>
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
				<li>
					<a href="/introl/mydivide.html?type=1">获取更多POW>></a>
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
				<li>
					<img src="${oss_url}/static/front2018/images/exchange/wanETHup2x.png" class="wanETHbtn" />
				</li>
				<li>
					<img src="${oss_url}/static/front2018/images/exchange/wanLeft.png" />
				</li>
			</ul>
			<!-- 奖票信息 -->
			<div class="wanEthUL3Bg">
				<h3>
					<span><img src="${oss_url}/static/front2018/images/exchange/biaoti1wanETH.png">&nbsp;&nbsp;奖票信息&nbsp;&nbsp;<img src="${oss_url}/static/front2018/images/exchange/wanETHbiaoti2x.png"></span>
				</h3>
				<div class="wanEthUL3Prize">
					<ul class="wanEthUL3tit">
						<li>期数</li>
						<li>用户ID</li>
						<li>奖票号</li>
						<li>时间</li>
						<li>状态</li>
					</ul>
					<div class="wanEthUL3Box">
						<ul class="wanEthUL3Td" id="LotteryList">
							
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--弹出窗-->
	<div class="wanEthModule" style="display: none;">
		<span class="wanEthModuleClose">×</span>
		<h3>您好，您本次的奖票号为</h3>
		<ul class="wanscroll">
		</ul>
		<!-- <h4 class='testbox'>可前往<a href="/introl/redPocket.html">糖果红包</a>查看</h4> -->
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

<%-- <%@include file="../comm/footer.jsp" %>	
 --%>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/layer/layer.js?v=20181126100022.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/comm/util.js?v=20181126100022.js?v=20181126201750"></script>
 <script type="text/javascript" src="${oss_url}/static/front2018/js/fluckydraw/waneth.js?v=20181126201750"></script>
</body>
</html>
