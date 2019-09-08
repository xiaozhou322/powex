<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="comm/include.inc.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;if (request.getServerName().equals("www.gbcax.com")){basePath="https://www.gbcax.com";}
%>

<!doctype html>
<html>
<head> 
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="comm/link.inc.jsp" %>
<link href="${oss_url}/static/front/css/index/main.css?v=20181126" rel="stylesheet" type="text/css" />
<link href="${oss_url}/static/front2018/css/lucky.css?v=20181126" rel="stylesheet" type="text/css" />
<link href="${oss_url}/static/front2018/css/jcountdown/jcountdown.css?v=20181126" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${oss_url}/static/front2018/js/index/main.js?v=20181126"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/layer/layer.js?v=20181126"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/fluckydraw/index.js?v=20181126"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/jcountdown/jquery-migrate-1.4.1.min.js?v=20181126"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/jcountdown/jquery.jcountdown.min.js?v=20181126"></script>
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
<%@include file="comm/header.jsp" %>
<section class="l-content" >
<!-- <div class="luckyTop">
	<h3>注册即赠POW 赢取10000ETH豪华大奖</h3>  
	<a href="/financial/index.html">立即充值</a>
	
</div> -->
<div>
<div class="idex-luckyTop">
	<div class="idexLeft"><img src="${oss_url}/static/front2018/images/exchange/idexLeft.png"></div>
	<div class="idexRight"><img src="${oss_url}/static/front2018/images/exchange/idexRight.png"></div>
	<ul class="idexCenter">
		<li><img src="${oss_url}/static/front2018/images/exchange/idexPOWEX1.png"></li>
		<li><img src="${oss_url}/static/front2018/images/exchange/idexPOWEX2.png"></li>
		<li><img src="${oss_url}/static/front2018/images/exchange/idexPOWEX3.png"></li>
	</ul>
	<div>
		<ul class="idexregister">
			<li>
			<c:choose>
		         <c:when test="${sessionScope.login_user == null }">
		         <a href="${oss_url}/user/register.html" class="idexregister-a"><img src="${oss_url}/static/front2018/images/exchange/idexregister.png"></a>
		         </c:when>
		         <c:otherwise>
		         <a href="${oss_url}/financial/index.html" class="idexregister-a"><img src="${oss_url}/static/front2018/images/exchange/idexRecharge.png"></a>
		         </c:otherwise>
	         </c:choose>
			</li>
			<li>
				<img src="${oss_url}/static/front2018/images/exchange/idexpel.png">
			</li>
		</ul>
	</div>
</div>
</div>


<ul class="luckyUl">
	<li>
		<span class="chongzhi2x"><img src="${oss_url}/static/front2018/images/exchange/chongzhi2x.png"></span>
		<span class="chongzhix">注册人数</span>
		<span class="chongzhi" id="userNum">${usertotal}</span>
		<span style="color: #3B4B7C;">&nbsp;&nbsp;人</span>
	</li>	
	<li>
		<span class="chongzhi2x" style="margin-left:80px;"><img src="${oss_url}/static/front2018/images/exchange/pow2x.png"></span>
		<span class="chongzhix">当前POW数</span>
		<span class="chongzhi" id="POWnum"><fmt:parseNumber integerOnly="true" value="${totalfwalle}"/></span>
		<span style="color: #3B4B7C;">&nbsp;&nbsp;</span>
	</li>	
	<li>
		<span class="chongzhi2x"> </span>
		<span class="chongzhix"></span>
		<span style="color: #3B4B7C;">活动开启倒计时&nbsp;</span>
		<div class="countdowntask" style="float:right;margin-top:13px;padding:0px;line-height:0px;"></div>
	</li>
</ul>

<div class="luckyCenter">
	<!-- 活动规则介绍 -->
	<div class="luckyTit">
		<h3>活动规则介绍</h3>
		<span></span>	<!-- 虚线 -->
	</div>
	<div class="luckyETH">
		<ul class="luckyEthUl">
			<li>
				活动期间，平台注册正常用户，通过不同形式获赠POW即有资格参与POWEX.PRO平台开放后首发活动，
				平台所有用户均有机会参与抽取POW万币大奖“10000ETH(个人独享价值1500万人民币）”
				<span>
					*本抽奖活动公平、公正、公开，所有信息上链并对外公布。
				</span>
			</li>
			<li>
				<h2>活动规则如下：</h2>
			</li>
			<li>
				<h2>奖励金额：</h2>
				<label>参与平台抽奖可有机会获得平台超级大奖<span class="luckyEthUltit">10000ETH</span>。</label>
			</li>
		</ul>
		<ul class="luckyA2x">
			<li>
				<h3>10000ETH</h3>
				<img src="${oss_url}/static/front2018/images/exchange/luckyA2x.png">
				<p>A</p>
				<p class="luckyA2p">超级大奖</p>
				<p>(最高奖金获得者)</p>
			</li>
			<li class="luckyjiangtou"><img src="${oss_url}/static/front2018/images/exchange/jiangtou2x.png"></li>
			<li>
				<h3>1000ETH</h3>
				<img src="${oss_url}/static/front2018/images/exchange/luckyB2x.png">
				<p>B</p>
				<p class="luckyA2p">引荐奖</p>
				<p>(A的推荐人B)</p>
			</li>
			<li class="luckyjiangtou"><img src="${oss_url}/static/front2018/images/exchange/jiangtou2x.png" ></li>
			<li>
				<h3>100ETH</h3>
				<img src="${oss_url}/static/front2018/images/exchange/luckyC2x.png">
				<p>C</p>
				<p class="luckyA2p">溯源奖</p>
				<p>(B的推荐人C)</p>
			</li>
		</ul>
	</div>
	<!-- 参与方式 -->
	<div class="luckyCy">
		<h3>
			<b>参与方式：</b>
			<span>用户在POWEX.PRO平台进行</span>
			<label>注册、推荐、充值、交易</label>
			<span>(平台开放后可进行交易)均可获赠POW参与POW万币活动。</span>
		</h3>
		<ul class="luckyCyUl">
			<li>
				<img src="${oss_url}/static/front2018/images/exchange/zhuce2x.png">
				<p class="luckyblue">注册</p>
				<p>新用户注册并通过KYC1即赠送1POW，用户通过KYC2认证可使用POW参与抽奖活动。</p>
			</li>
			<li>
				<img src="${oss_url}/static/front2018/images/exchange/yaoqing2x.png">
				<p class="luckybrown">邀请</p>
				<p>每邀请一个用户并成功通过KYC2认证即赠送1POW，邀请人享有推荐奖励资格。</p>
			</li>
			<li>
				<img src="${oss_url}/static/front2018/images/exchange/chongzhi12x.png">
				<p class="luckyyellow">充值</p>
				<p>首次充币获赠1POW，充币数量满1ETH或等值其他币种。</p>
			</li>
			<li>
				<img src="${oss_url}/static/front2018/images/exchange/jiaoyi2x.png">
				<p class="luckygray">交易</p>
				<p>当天交易手续费每满1USDT赠送1POW，平台根据用户当天交易手续费取整赠送POW，不做累计。</p>
			</li>
		</ul>
	</div>
	

	<!-- POW抽奖规则 -->
	<div class="luckyPOW">
		<h3>
			
			<span><b>POW抽奖规则：</b>平台注册用户在平台开放后完成KYC2认证，使用获赠的POW参与抽奖。每消耗1POW参与一次刮奖，刮奖之后获得参与抽奖的奖票。 </span>
		</h3>
		<ul class="luckyPOWUl">
			<li>
				<span><img src="${oss_url}/static/front2018/images/exchange/KYC2x.png" style="margin-top: 25px;"></span>
				<p>a.KYC2认证</p>
			</li>
			<li><img src="${oss_url}/static/front2018/images/exchange/jiantou2x.png"></li>
			<li>
				<span style="margin-top: 11px;height: 121px;"><img src="${oss_url}/static/front2018/images/exchange/choujiang2x.png"></span>
				<p>b.参与抽奖</p>
			</li>
			<li><img src="${oss_url}/static/front2018/images/exchange/jiantou2x.png"></li>
			<li>
			<span style="margin-top: 21px;height: 112px;"><img src="${oss_url}/static/front2018/images/exchange/jiangpiao2x.png"></span>
				<p>c.获得奖票</p>
			</li>
			<li><img src="${oss_url}/static/front2018/images/exchange/jiantou2x.png"></li>
			<li>
				<span style="margin-top: 12px;height: 120px;"><img src="${oss_url}/static/front2018/images/exchange/jiangbei.png"></span>
				<p>d.开奖</p>
			</li>
		</ul>
	</div>
	
	<!-- POW -->
	<div class="luckyCy">
		<h3>
			<span><b class="luckypow">POW：</b>POW是POWEX.PRO的平台通用奖券，目前可用于平台抽奖活动，后续可用于交易手续费折扣、参与众筹、POW持有者专享活动、币币交易、发展生态链等。</span>
		</h3>
		<ul class="luckyCyUl luckyCyUlPow">
			<li>
				<span>
					<img src="${oss_url}/static/front2018/images/exchange/huodong.png">
				</span>
				<p>参与活动</p>
			</li>
			<li>
				<span><img style="width: 74px;" src="${oss_url}/static/front2018/images/exchange/zekou2x.png"></span>
				<p>手续费折扣</p>
			</li>
			<li>
				<span><img src="${oss_url}/static/front2018/images/exchange/jiaoyi12x.png"></span>
				<p>币币交易</p>
			</li>
			<li>
				<span><img src="${oss_url}/static/front2018/images/exchange/dongtai2x.png"></span>
				<p>发展生态</p>
			</li>
		</ul>
	</div>
</div>
<div style="display: none;">
 <c:if test="${sessionScope.login_user != null }">  	  
	   <c:choose>
	   <c:when test="${!sessionScope.login_user.fhasRealValidate||!sessionScope.login_user.fhasImgValidate}">
	 <a href=" ">KYC1和KYC2</a>
	   </c:when>
	    <c:when test="${!sessionScope.login_user.fgoogleBind}">
	   <a href="${oss_url}/user/security.html?tab=3">谷歌</a>
	   </c:when>
	   <c:otherwise>
	   
	    <a href="${oss_url}/financial/index.html">充值</a>
	   </c:otherwise>
	   
	   </c:choose>
	   
	   </c:if>         
</div>
<!-- 底部宣传 -->
<div class="lotterBoxfoot">
	<div class="lotterFootdiv">
		<h3><span></span>活动原则<span></span></h3>
		<h4>本次抽奖活动公平、公正、公开，所有信息上链并对外公布</h4>
	</div>
	<ul class="lotterFootUl">
		<li>
			<p class="lotterFootp">公平</p>
			<p>数据封存后上链</p>
		</li>
		<li>
			<p class="lotterFootp">公开</p>
			<p>抽奖信息上链并对外公布</p>
		</li>
		<li>
			<p class="lotterFootp">公正</p>
			<p>摇奖算法公开真实随机数</p>
		</li>
	</ul>
	
</div>
<!-- 抽奖活动 -->
<input id="activity_id" type="hidden"  value="1"/>
<div class="lucky">
	<div class="g-lottery-box">
		<span class="luckyClose"><img src="${oss_url}/static/front2018/images/exchange/close0x.png"></span>
		<div class="g-lottery-img">
			<a class="playbtn" href="javascript:;" title="开始抽奖"></a>
		</div>
		<a href="${oss_url}/luckydraw/luckydrawIndex.html?lang=zh_CN" class="playLook">查看活动详情</a>
		
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
<!-- 弹出窗 -->
  <div class="comModule">
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
  		<em class="close2x"><img src="${oss_url}/static/front2018/images/exchange/close2x.png" ></em>
  </div>
  <!-- 蒙层 -->
  <div class="fixedModule ngrealModule"></div>
</section> 
<%@include file="comm/footer.jsp" %>	

<script type="text/javascript" src="${oss_url}/static/front2018/js/jquery.rotate.min.js?v=20181126"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/main/scroll.js?v=20181126"></script>
<script type="text/javascript">
$(".luckyClose").click(function(){
	  $(".lucky").hide();
	 
	
});

$(function() {
	 scroll();	//	初始化抽奖活动
	    config = {
	        timeText:'${awardsStartTime}', //倒计时时间，格式：年/月/日 时:分:秒
	        timeZone:'8' , //时区，GMT号码
	        style: "crystal", //显示的样式，可选值有flip,slide,metal,crystal(翻动、滑动、金属、水晶)
	        color: "black", //显示的颜色，可选值white,black(黑色、白色)
	        width: 300, //倒计时宽度，无限制，默认为0
	        textGroupSpace: 10, //天、时、分、秒之间间距
	        textSpace: 0, //数字之间间距
	        reflection: 0, //是否显示倒影
	        reflectionOpacity: 0, //倒影透明度
	        reflectionBlur: 0, //倒影模糊程度
	        dayTextNumber: 2, //倒计时天数数字个数
	        displayDay: !0, //是否显示天数
	        displayHour: !0, //是否显示小时数
	        displayMinute: !0, //是否显示分钟数
	        displaySecond: !0, //是否显示秒数
	        displayLabel: !0, //是否显示倒计时底部label
	        onFinish: function() {}//完成事件，您可以在时间结束时执行一些脚本，在创建倒计时时只需传递一个函数即可。
	    };
	    $(".countdowntask").jCountdown(config);

	/* 抽奖活动弹出 */

		  $(window).scroll(function(){
			  scroll();
		    });
	});
function scroll(){
	var scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
	  if(scrollTop >= 400){
			$(".lucky").show();
		} else{
			$(".lucky").hide(); // 如果小于等于400 淡出
		}
}
</script>

</body>
</html>
