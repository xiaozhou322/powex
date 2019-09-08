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
 <script type="text/javascript" src="${oss_url}/static/front2018/js/fluckydraw/index.js?v=20181126201750"></script> 
 <script type="text/javascript" src="${oss_url}/static/front2018/js/comm/util.js?v=20181126201750"></script> 
<style type="text/css">
  .comModule1 {
    height: 420px;
    width: 470px;
    clear: both;
    overflow: hidden;
    background: url(../images/exchange/tcbj.png) no-repeat center;
    background-size: 100% 100%;
    text-align: center;
    position: fixed;
    top: 8em;
    left: 50%;
    margin-left: -258px;
    z-index: 100;
    display: none;
}

</style>
</head>
<body>
<%@include file="../comm/header.jsp" %>
<section class="l-content" >
<div class="lotteryTop">
	<%-- <span>${factivityModel.name}</span> --%>
	<h3 style="margin-top: 40px;">幸运大转盘 好运要不停</h3>  
</div>
<div class="lotteryCenter">
	<div class="trotting">
	<!-- 走马灯 -->
		<div class="trottingUl">
			<span class="gx2x"><img src="${oss_url}/static/front2018/images/draw/gx@2x.gif"></span>
			<ul class="hezi" id="hezi_top"></ul>
			<a href="/luckydraw/luckydrawIndex.html?lang=zh_CN#miao" class="gd2x">更多<img src="${oss_url}/static/front2018/images/draw/gd2x.png"></a>
		</div>
		<!-- 活动区域 -->
		<div class="lotterBox">
		<!-- 活动规则 -->
			<div class="lotterHD">
				<div class="lotterHTit">*活动规则*</div>
				<div class="lotterDiv">
				   <input id="activity_id" type="hidden"  value="${factivityModel.id }"/>
				<div style="box-shadow: -2px 0px 18px #ececec;clear: both; overflow: hidden;">
					<ul class="lotterHL">
						<h3>
							<span></span>
							<label>参与规则</label>
							<span></span>						
						</h3>
						<li>1、用户必须完成KYC2认证，获得抽奖资格</li>
						<li>2、活动期间每天用户抽奖次数不限</li>
						<li>3、奖金自动存到用户资产钱包</li>
						<li>4、用户中奖记录可前往会员福利—糖果红包查看</li>
						<h4><img src="${oss_url}/static/front2018/images/draw/cy2x.png"></h4>
					</ul>
					<ul class="lotterHR">
						<h3>
							<span></span>
							<label>抽奖规则</label>
							<span></span>						
						</h3>
						<li>1、每次抽奖需要花费1POW，每天不限抽奖次数</li>
						<li>2、每次抽奖间隔10秒</li>
						<li>3、奖励金额为：<b>一等奖</b>1ETH、<b>二等奖</b>0.1ETH、<b>三等奖</b>0.01ETH、<b>四等奖</b>0.001ETH</li>
						<h4><img src="${oss_url}/static/front2018/images/draw/cj2x.png"></h4>
					</ul>
					</div>
				</div>
			</div>
			<!-- 抽奖活动 -->
			<div class="lotterCj">
				<!-- 大转盘 -->
				<div class="lotterDial">
					<div class="g-left">
						
						<div class="g-lottery-box">
							<div class="g-lottery-img">
								<a class="playbtn" href="javascript:;" title="开始抽奖"></a>
							</div>
							
						</div>
					</div>
							<ul class="lotterPOW">
								<li>当前${fvirtualwallet.fvirtualcointype.fShortName}数:<span
									class="playnum"><fmt:parseNumber integerOnly="true"
											value="${fvirtualwallet.ftotal}" /></span></li>
								<li>
								<label onclick="ftradeBtn()">获取更多${fvirtualwallet.fvirtualcointype.fShortName}</label>
								</li>
							</ul>

						</div>	
			</div>
			<!-- END抽奖活动 -->
				<!-- 更多获奖用户 -->
				<div class="luckyBg">
					<div class="lotteryPrize" id="miao">
						<ul class="lotterytit" >
							<li>用户ID</li>
							<li>抽奖时间</li>
							<li>中奖记录</li>
						</ul>
						<div class="lotteryBox">
						<ul class="lotteryTd"  id="lottery_log">

						</ul>
						</div>
					</div>
				</div>
				<!-- END更多获奖用户 -->
		</div>
		<!-- end活动区域 -->
	</div>
</div>
<!-- 认证获取更多-->
<div class="realfixed ngrealModule">
	<ul style="line-height: 30px;">
		<h3><img src="${oss_url}/static/front2018/images/close@2x.png" class="realclose"></h3>
		<li>
			<p class="powModules"></p>
			<p>参与平台抽奖</p>
			<a href="" class="googleBtn lotterKYCBtn powModulesBtn" style="margin-top: 39px;"></a>
		</li>
	</ul>
</div>
<!-- POW获取更多-->
<div class="POWRechargeBox POWModuleS">
	<div class="POWRecharge">
		<h3><img src="${oss_url}/static/front2018/images/close@2x.png" class="realclose"></h3>
		<ul>
			<h3>完成以下任务均有机会获得POW，参与平台抽奖</h3>
			<li class="POWRechargeli">
				<p><span><img src="${oss_url}/static/front2018/images/exchange/renweu2x.png" style="width: 26px;"></span>任务名称</p>
				<p><span><img src="${oss_url}/static/front2018/images/exchange/zhuangtai2x.png" style="width: 29px;"></span>领取状态</p>
			</li>
			<c:if test="${sessionScope.login_user!=null}">
			<li>
				<p>KYC1认证</p>
				<p>
					<c:choose>
					   <c:when test="${!sessionScope.login_user.fhasRealValidate}">
					    <a href="/user/realCertification.html">未领取 </a>
					   </c:when>				
					   <c:otherwise>已领取</c:otherwise>
					</c:choose>
				</p>
			</li>
			<li>
				<p>KYC2认证</p>
				<p>
					<c:choose>
					   <c:when test="${!sessionScope.login_user.fhasImgValidate}">
					     <a href="/user/realCertification.html">未领取</a>
					   </c:when>				
					   <c:otherwise>已领取</c:otherwise>
					</c:choose>
				</p>
			</li>
			<li>
				<p>谷歌认证</p>
				<p>
					<c:choose>
					   <c:when test="${!sessionScope.login_user.fgoogleBind}">
					         <a href="/user/security.html?tab=5">未领取</a>
					   </c:when>				
					   <c:otherwise>已领取</c:otherwise>
					</c:choose>
				</p>
			</li>
			<li>
				<p>交易密码</p>
				<p>
				<c:choose>
				   <c:when test="${ftradePassword==null || empty ftradePassword}">
					<a href="/user/security.html?tab=4">未领取</a>
				   </c:when>				
				   <c:otherwise> 已领取  </c:otherwise>
				</c:choose>
				</p>
			</li>
			<li>
				<p>首次充值</p>
				<p>
					<c:choose>
					   <c:when test="${isFristTrade}">
					<a href="/financial/index.html">   未领取</a>
					   </c:when>				
					   <c:otherwise>
					     已领取
					   </c:otherwise>
					</c:choose>
				</p>
			</li>
			<li>
				<p>邀请好友</p>
				<p><a href="/introl/mydivide.html?type=1">去邀请</a></p>
			</li>
			<!-- <li>
				<p>邀请好友</p>
				<p>领取状态</p>
			</li> -->
			</c:if>
		</ul>
		<div class="POWRechargebottom">
		*仅第一次设置交易密码及第一次充值赠送1POW<br>
		*好友推荐：推荐人完成KYC2均可获赠1POW，不限制次数
		</div>
		<div class="POWRechargehongbao">
			<img src="${oss_url}/static/front2018/images/exchange/hongbao2x.png">
		</div>
		<%-- <li>
			<c:if test="${sessionScope.login_user!=null}">
			<!-- kyc1 -->
				<c:choose>
				   <c:when test="${!sessionScope.login_user.fhasRealValidate}">
				    <a href="/user/realCertification.html"> KYC1未认证 </a>
				   </c:when>				
				   <c:otherwise>
				    KYC1已认证
				   </c:otherwise>
				</c:choose>
				<!-- kyc2 -->
				<c:choose>
				   <c:when test="${!sessionScope.login_user.fhasImgValidate}">
				     <a href="/user/realCertification.html">KYC2未认证</a>
				   </c:when>				
				   <c:otherwise>
				   KYC2已认证
				   </c:otherwise>
				</c:choose>
				<!--谷歌认证 -->
				<c:choose>
				   <c:when test="${!sessionScope.login_user.fgoogleBind}">
				         <a href="/user/security.html?tab=5"> 谷歌  為认证</a>
				   </c:when>				
				   <c:otherwise>
				         谷歌  已认证
				   </c:otherwise>
				</c:choose>
				<!-- 交易密码 -->
				<c:choose>
				   <c:when test="${ftradePassword==null}">
				<a href="/user/security.html?tab=4">   交易密码未设置</a>
				   </c:when>				
				   <c:otherwise>
				   交易密码宜设置
				   </c:otherwise>
				</c:choose>
				<!-- 首次充值 -->
				<c:choose>
				   <c:when test="${isFristTrade}">
				<a href="/financial/index.html">   首次充值未完成</a>
				   </c:when>				
				   <c:otherwise>
				      首次充值已完成
				   </c:otherwise>
				</c:choose>
			</c:if>
	</li>
		 --%>
	</div>
</div>
<!-- 没有POW币弹出窗 -->
  <div class="comModule NotcomModule">
	  	<ul>
	  		<li>
	  			<img src="${oss_url}/static/front2018/images/exchange/noWinning.png" class="Winning">
	  		</li>
	  		<li>
	  			<h3 class="WinningTit">您好，您的POW余额不足，无法继续抽奖</h3>
	  		</li>
	  		<li>
	  			<a href="/financial/index.html" class="asdbtn">前往充值</a>
	  		</li>
	  	</ul>
	  	<em class="close2x"><img src="${oss_url}/static/front2018/images/exchange/close2x.png" ></em>
  </div>
<!-- 弹出窗 -->
  <div class="comModule">
  	<em></em>
  	<ul>
  		<li>
  			<img src="${oss_url}/static/front2018/images/exchange/Winning.png" class="Winning">
  		</li>
  		<li>
  			<h3 class="WinningTit">很遗憾，大奖与您擦肩而过请再接再厉</h3>
  		</li>
  		<li>
  			<button class="asdbtn">再抽一次</button>
  		</li>
  	</ul>
  </div>
  <!-- 蒙层 -->
  <div class="fixedModule ngrealModule POWModuleS"></div>
</section> 
<%@include file="../comm/footer.jsp" %>	
<script type="text/javascript" src="${oss_url}/static/front2018/js/jquery.rotate.min.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/main/scroll.js?v=20181126201750"></script>

</body>
</html>
