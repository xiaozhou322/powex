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
<body >
<%@include file="../comm/header.jsp" %>

<link href="${oss_url}/static/mobile2018/css/base.css?v=2" rel="stylesheet" type="text/css" />
<link href="${oss_url}/static/mobile2018/css/lucky/reset.css?v=20181126201750" rel="stylesheet" type="text/css" />
<link href="${oss_url}/static/mobile2018/css/lucky/lottrey.css?v=20181126201750" rel="stylesheet" type="text/css" />
<input id="activity_id" type="hidden"  value="${factivityModel.id }"/>
		<header>
			<a href="JavaScript:history.go(-1)"><i class="back" style="margin: 0.2rem;"></i></a>
			<div class="h_title">幸运大转盘 好运要不停</div>
		</header>
		<section>
			<div class="g-content">
				<div class="g-lottery-case">
					<div class="g-left">
						<!--<h2>您已拥有<span class="playnum"></span>次抽奖机会，点击立刻抽奖！~</h2>-->
						<div class="g-lottery-box">
							<div class="g-lottery-img">
								<a class="playbtn" href="javascript:;" title="开始抽奖"></a>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="user_reoord">
				<span><img src="${oss_url}/static/mobile2018/images/exchange/huojiang@2x.png" /></span>
				<ul class="hezi" id="hezi_top" style="margin-top: 0.1rem;color: #FFBE04;">恭喜用户123***0025获得二等奖，奖金0.1ETH</ul>
			</div>
			<div class="g_detail">
				每次抽奖消耗<span>1POW</span>,用户可无限次参与抽奖
			</div>
			<div class="g_details">
				<input type="button" value="规则说明" class="btn_desc" />
			</div>
			
			<div class="a_rule">
				<div class="rule_title">*活动规则*</div>
				<div class="rule_con">
					<div class="cons_first">
						<span>&rarr;</span>
						<span>参与规则</span>
						<span>&larr;</span>
						<p>
							1、用户须完成KYC2认证，获得抽奖资格<br /> 2、活动期间用户每天抽奖次数不限
							<br /> 3、奖金自动存到用户资产钱包<br>4、用户中奖记录可前往会员福利-糖果红包查看
						</p>
					</div>
					<div class="cons_second">
						<span>&rarr;</span>
						<span>抽奖规则</span>
						<span>&larr;</span>
						<p>
							1、每次抽奖需要花费1POW，每天不限抽奖次数<br /> 2、每次抽奖间隔10秒
							<br /> 3、奖金金额为：一等奖1ETH、二等奖0.1ETH、三等奖0.01ETH、四等奖0.001ETH
						</p>
					</div>
					<div class="rule_close"><img src="${oss_url}/static/mobile2018/images/exchange/close.png" /></div>
				</div>
			</div>
			<div class="body_back"></div>
<!-- 认证获取更多-->
<div class="realfixed ngrealModule">
	<ul style="line-height:0.5rem;">
		<h3><img src="${oss_url}/static/mobile2018/images/exchange/close@2x.png" class="rclose"></h3>
		<li>
			<p class="powModules"></p>
			<p>参与平台抽奖</p>
			<a href="" class="googleBtn lotterKYCBtn powModulesBtn" style="margin-top:0.5rem"></a>
		</li>
	</ul>
</div>
<!-- 弹出窗 -->
  <div class="comModule">
  	<ul>
  		<li>
  			<img src="${oss_url}/static/mobile2018/images/exchange/Winning.png" class="Winning">
  		</li>
  		<li>
  			<h3 class="WinningTit">很遗憾，大奖与您擦肩而过请再接再厉</h3>
  		</li>
  		<li>
  			<button class="asdbtn">再抽一次</button>
  		</li>
  	</ul>
  		<em class="close2x"><img src="${oss_url}/static/mobile2018/images/exchange/close2x.png" ></em>
  </div>
  <!-- 蒙层 -->
  <div class="fixedModule ngrealModule"></div>
		</section>
		<%@include file="../comm/tabbar.jsp"%>
<%@include file="../comm/footer.jsp" %>
<script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/jquery.rotate.min.js?v=20181126201750"></script>
<script src="${oss_url}/static/mobile2018/js/fluckydraw/index.js?v=20181126201750"></script>
<script type="text/javascript">
//抽奖活动
$(".rclose").on("click",function(){
	$(".realfixed").hide();
	$(".POWRechargeBox").hide();
	$(".POWModuleS").hide();
	$(".lucky").show();
	$(".fixedModule").hide();
	
})


	$(function(){
		  $(".back").click(function(event) {
		        window.history.go(-1);
		    });
	})
</script>
</body>

</html>