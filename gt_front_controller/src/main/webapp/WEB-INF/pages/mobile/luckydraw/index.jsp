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
<link rel="stylesheet" href="${oss_url}/static/mobile2018/css/lucky/lucky.css?v=20181126201750" type="text/css"/>
<link rel="stylesheet" href="${oss_url}/static/mobile2018/css/lucky/indexlucky.css?v=20181126201750" type="text/css"/>
  <input id="activity_id" type="hidden"  value="${factivityModel.id }"/>
<header class="lucky_head">
			<a href="JavaScript:void(0)"><i class="back" style="margin: 0.2rem;"></i></a>
			<div class="head_title">注册即赠POW<br />赢取10000ETH豪华大奖</div>
		<div class="head_content">
			
				<ul>
					<li>
						<img src="${oss_url}/static/mobile2018/images/exchange/chongzhi@3x.png" />
						<span>注册人数</span>
					</li>
					<li>
						<span>${usertotal}</span><em>人</em>
					</li>
				</ul>
				<ul>
					<li>|</li>
				</ul>
				<ul>
					<li>
						<img src="${oss_url}/static/mobile2018/images/exchange/pow@3x.png" />
						<span>当前POW</span>
					</li>
					<li>
						<span><fmt:parseNumber integerOnly="true" value="${totalfwalle}"/></span>
					</li>
				</ul>
			
			</div>
		</header>
		
		<section class="s_con">
			
			<div class="act_title">
				<img src="${oss_url}/static/mobile2018/images/exchange/rules@3x.png" />&nbsp;
				<span>活动规则</span>
				<a href="/10000eth/luckdrawIndex.html" class="activte">去参与</a>
			</div>
			<div class="act_details">
				<div class="d_con">
					<span>奖励金额</span><br />
					<span>参与平台抽奖可有机会获得平台超级大奖10000ETH获得者的相应推荐人可获得推荐奖励。</span>
				</div>
				<div class="details_con">
					<ul class="invite_cont2">
						<li><img src="${oss_url}/static/mobile2018/images/exchange/10000ETH@3x.png"></li>
						<li><img alt="" src="${oss_url}/static/mobile2018/images/exchange/A@3x.png"></li>
						<li>A</li>
						<li>超级大奖</li>
						<li>（最高奖金获得者）</li>
					</ul>

					<ul class="invite_cont2">
						<li><img src="${oss_url}/static/mobile2018/images/exchange/1000ETH@3x.png"></li>
						<li><img alt="" src="${oss_url}/static/mobile2018/images/exchange/B@3x.png"></li>
						<li>B</li>
						<li>引荐奖</li>
						<li>（A的推荐人B）</li>
					</ul>

					<ul class="invite_cont2">
						<li><img src="${oss_url}/static/mobile2018/images/exchange/100ETH@3x.png"></li>
						<li><img alt="" src="${oss_url}/static/mobile2018/images/exchange/C@3x.png" width="89px" height="63px"></li>
						<li>C</li>
						<li>溯源奖</li>
						<li>（B的推荐人C）</li>
					</ul>
				</div>
			</div>
			<div class="act_way">
				<div class="w_con">
					<span>参与方式</span><br />
				</div>
				<div class="w_icon click_recom">
					<ul id="click_reg">
						<li><img src="${oss_url}/static/mobile2018/images/exchange/zhuce@3x.png"></li>
						<li>注册</li>
					</ul>
					<ul id="click_recom">
						<li><img src="${oss_url}/static/mobile2018/images/exchange/yaoqing @3x.png"></li>
						<li>推荐</li>
					</ul>
					<ul id="click_pay">
						<li><img src="${oss_url}/static/mobile2018/images/exchange/chongzhi1@3x.png"></li>
						<li>充值</li>
					</ul>
					<ul id="click_deal">
						<li><img src="${oss_url}/static/mobile2018/images/exchange/jiaoyi @3x.png"></li>
						<li>交易</li>
					</ul>
				</div>
				<div class="w_frame w0">
					<img src="${oss_url}/static/mobile2018/images/exchange/close@2x.png" />
					<p>新用户注册并通过KYC1即赠送1POW，用户通过KYC2认证可使用POW参与抽奖活动。</p>
				</div>
				<div class="w_frame w1">
					<img src="${oss_url}/static/mobile2018/images/exchange/close@2x.png" />
					<p>每邀请一个用户并成功通过KYC2认证即赠送1POW，邀请人享有推荐奖励资格。</p>
				</div>
				<div class="w_frame w2">
					<img src="${oss_url}/static/mobile2018/images/exchange/close@2x.png" />
					<p>首次充币获赠1POW，充币数量满1ETH或等值其他币种。</p>
				</div>
				<div class="w_frame w3">
					<img src="${oss_url}/static/mobile2018/images/exchange/close@2x.png" />
					<p>当天交易手续费每满1USDT赠送1POW，平台根据用户当天交易手续费取整赠送POW，不做累计。</p>
				</div>
			</div>
			
			<div class="act_rule">
				<span>POW抽奖规则</span><br />
				<div class="r_t">平台注册用户完成KYC2认证后，使用获赠的POW参与抽奖活动。参与一次刮奖消耗1POW，其中获得参与抽奖的奖票。</div>
				<div class="r_pic">
					<ul>
						<li><img src="${oss_url}/static/mobile2018/images/exchange/KYC2@3x.png"></li>
						<li>KYC2<br>认证</li>
					</ul>
					<ul>
						<li><img src="${oss_url}/static/mobile2018/images/exchange/jiantou@3x.png"></li>
					</ul>
					<ul>
						<li><img src="${oss_url}/static/mobile2018/images/exchange/choujiang@3x.png"></li>
						<li>参与<br>抽奖</li>
					</ul>
					<ul> 
						<li><img src="${oss_url}/static/mobile2018/images/exchange/jiantou@3x.png"/></li>
					</ul>
					<ul>
						<li><img src="${oss_url}/static/mobile2018/images/exchange/jiangpiao@3x.png"/></li>
						<li>获得<br>奖票</li>
					</ul>
					<ul> 
						<li><img src="${oss_url}/static/mobile2018/images/exchange/jiantou@3x.png"/></li>
					</ul>
					<ul>
						<li><img src="${oss_url}/static/mobile2018/images/exchange/jiangbei@2x.png"/></li>
						<li>开奖</li>
					</ul>
				</div><br />
				
			</div>
			<div class="act_notes">
				<span>*POW</span><br />
				<div class="n_t">POW是POWEX.PRO的平台通用奖券，目前可用于平台抽奖活动，后续可用于交易手续费折扣、参与众筹、POW持有者专享活动、币币交易、发展生态链等。</div>
				<div class="n_pic">
					<ul>
						<li><img src="${oss_url}/static/mobile2018/images/exchange/huodong@2x.png"></li>
						<li>参与活动</li>
					</ul>
					<ul>
						<li><img src="${oss_url}/static/mobile2018/images/exchange/zekou@2x.png"></li>
						<li>手续费折扣</li>
					</ul>
					<ul>
						<li><img src="${oss_url}/static/mobile2018/images/exchange/jiaoyi@2x.png"/></li>
						<li>币币交易</li>
					</ul>
					<ul>
						<li><img src="${oss_url}/static/mobile2018/images/exchange/dongtai @2x.png"/></li>
						<li>发展生态</li>
					</ul>
				</div>
			</div>
		
			
 <!-- 抽奖活动 -->
<div style="display: none;">
 <c:if test="${sessionScope.login_user != null }">  	  
	   <c:choose>
	   <c:when test="${!sessionScope.login_user.fhasRealValidate||!sessionScope.login_user.fhasImgValidate}">
	 <a href="/user/realCertification.html">KYC1和KYC2</a>
	   </c:when>
	    <c:when test="${!sessionScope.login_user.fgoogleBind}">
	   <a href="/user/security.html?tab=3">谷歌</a>
	   </c:when>
	   <c:otherwise>
	   
	    <a href="/financial/index.html">充值</a>
	   </c:otherwise>
	   
	   </c:choose>
	   
	   </c:if>         
</div>
<div class="lucky"	style="display:none;">
	<div class="g-lottery-box" style="z-index: 9999;">
		<span class="luckyClose"><img src="${oss_url}/static/mobile2018/images/exchange/close0x.png"></span>
		<div class="g-lottery-img">
			<a class="playbtn" href="javascript:;" title="开始抽奖"></a>
		</div>
		<a href="/luckydraw/luckydrawIndex.html?lang=zh_CN" class="playLook">查看活动详情</a>
		
	</div>
</div>
<!-- 认证获取更多-->
<div class="realfixed ngrealModule">
	<ul style="line-height:0.5rem;">
		<h3><img src="${oss_url}/static/mobile2018/images/exchange/close@2x.png" class="realclose"></h3>
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
  <div class="fixedModule ngrealModule" style="display:none;"></div>
</section>
	<div class="main_foot">
			<div class="m_rule">
				<div class="f_line"></div>
				<div>活动原则</div>
				<div class="f_line"></div>
			</div>
			<div class="r_pictures">
				<ul class="r_picture1">
					<li>公平</li>
					<li>数据封存后上链</li>
				</ul>
				<ul class="r_picture2" style="margin: 0px 0.2rem;">
					<li>公正</li>
					<li>抽奖信息上链并对外公布</li>
				</ul>
				<ul class="r_picture3">
					<li>公开</li>
					<li>摇奖算法公开真实随机数</li>
				</ul>
			</div>
		</div>
	
<%@include file="../comm/tabbar.jsp"%>
<%@include file="../comm/footer.jsp" %>
<script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
<script src="${oss_url}/static/mobile2018/js/fluckydraw/index.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/jquery.rotate.min.js?v=20181126201750"></script>
<script type="text/javascript">
	$(function(){
		 $(".back").click(function(event) {
			 window.history.go(-1);
		});
	})
</body>
</html>
