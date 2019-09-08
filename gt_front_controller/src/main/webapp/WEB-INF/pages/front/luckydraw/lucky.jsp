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
<div class="luckyTop">
	<h3>注册即赠POW 赢取10000ETH豪华大奖</h3>  
	<a href="/financial/index.html">立即充值</a>
</div>
<ul class="luckyUl">
	<li>
		<span class="chongzhi2x"><img src="${oss_url}/static/front2018/images/exchange/chongzhi2x.png"></span>
		<span class="chongzhix">充值人数</span>
		<span class="chongzhi">123456</span>
		<span style="color: #3B4B7C;">&nbsp;&nbsp;人</span>
	</li>	
	<li>
		<span class="chongzhi2x"><img src="${oss_url}/static/front2018/images/exchange/pow2x.png"></span>
		<span class="chongzhix">当前POW</span>
		<span class="chongzhi">123456</span>
		<span style="color: #3B4B7C;">&nbsp;&nbsp;万</span>
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
				平台所有用户均有机会参与抽取pow万币大奖“10000ETH(个人独享价值1500万人民币）”
				<span>
					*本抽奖活动公开、公正，所有信息上链并对外公布。
				</span>
			</li>
			<li>
				<h2>活动规则如下：</h2>
			</li>
			<li>
				<h2>奖励金额：</h2>
				<label>参与平台抽奖可有机会获得平台超级大奖<span class="luckyEthUltit">10000ETH</span>超级大奖。</label>
			</li>
		</ul>
		<ul class="luckyA2x">
			<li>
				<h3>10000ETH</h3>
				<img src="${oss_url}/static/front2018/images/exchange/luckyA2x.png">
				<p>A</p>
				<p>超级大奖</p>
				<p>(最高奖金获得者)</p>
			</li>
			<li class="luckyjiangtou"><img src="${oss_url}/static/front2018/images/exchange/jiangtou2x.png"></li>
			<li>
				<h3>1000ETH</h3>
				<img src="${oss_url}/static/front2018/images/exchange/luckyB2x.png">
				<p>B</p>
				<p>引荐奖</p>
				<p>(A的推荐人B)</p>
			</li>
			<li class="luckyjiangtou"><img src="${oss_url}/static/front2018/images/exchange/jiangtou2x.png" ></li>
			<li>
				<h3>100ETH</h3>
				<img src="${oss_url}/static/front2018/images/exchange/luckyC2x.png">
				<p>C</p>
				<p>溯源奖</p>
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
			<span>(平台开放后可进行交易)均可获赠POW后参与POW万币活动。</span>
		</h3>
		<ul class="luckyCyUl">
			<li>
				<img src="${oss_url}/static/front2018/images/exchange/zhuce2x.png">
				<p class="luckyblue">注册</p>
				<p>新用户注册并通过KYC1即赠送1POW用户通过KYC2认证可使用POW参与抽奖活动。</p>
			</li>
			<li>
				<img src="${oss_url}/static/front2018/images/exchange/yaoqing2x.png">
				<p class="luckybrown">邀请</p>
				<p>每邀请一个用户并成功通过KYC2认证即赠送1POW，邀请人享有推荐奖励资格。</p>
			</li>
			<li>
				<img src="${oss_url}/static/front2018/images/exchange/chongzhi12x.png">
				<p class="luckyyellow">充值</p>
				<p>首次充币才能享有赠送，充币数量满1ETH或等值其他币种。</p>
			</li>
			<li>
				<img src="${oss_url}/static/front2018/images/exchange/jiaoyi2x.png">
				<p class="luckygray">交易</p>
				<p>当天交易手续费每满1USDT赠送1POW，平台将根据用户当天交易手续费取整赠送POW，不做累计。</p>
			</li>
		</ul>
	</div>
	

	<!-- POW抽奖规则 -->
	<div class="luckyPOW">
		<h3>
			<b>POW抽奖规则：</b>
			<span>平台注册用户在平台开放后完成KYC2认证，使用获赠的POW参与抽奖活动。</span>
		</h3>
		<ul class="luckyPOWUl">
			<li>
				<img src="${oss_url}/static/front2018/images/exchange/KYC2x.png">
				<p>a.KYC2认证</p>
			</li>
			<li><img src="${oss_url}/static/front2018/images/exchange/jiantou2x.png"></li>
			<li>
				<img src="${oss_url}/static/front2018/images/exchange/choujiang2x.png">
				<p>b.参与抽奖</p>
			</li>
			<li><img src="${oss_url}/static/front2018/images/exchange/jiantou2x.png"></li>
			<li>
				<img src="${oss_url}/static/front2018/images/exchange/jiangpiao2x.png">
				<p>c.获得奖票</p>
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
	   <a href="/user/security.html?tab=3">谷歌</a>
	   </c:when>
	   <c:otherwise>
	   
	    <a href="/financial/index.html">充值</a>
	   </c:otherwise>
	   
	   </c:choose>
	   
	   </c:if>         
</div>
<!-- 抽奖活动 -->
<input id="activity_id" type="hidden"  value="1"/>
<div class="lucky">
	<div class="g-lottery-box">
		<span class="luckyClose"><img src="${oss_url}/static/front2018/images/exchange/close0x.png"></span>
		<div class="g-lottery-img">
			<a class="playbtn" href="javascript:;" title="开始抽奖"></a>
		</div>
	</div>
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
  <div class="fixedModule"></div>
</section> 
<%@include file="../comm/footer.jsp" %>	

<script type="text/javascript" src="${oss_url}/static/front2018/js/jquery.rotate.min.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/main/scroll.js?v=20181126201750"></script>
<script type="text/javascript">
$(".luckyClose").click(function(){
	  $(".lucky").hide();
})

</script>
<!-- <script type="text/javascript">
	$(function() {
		/* 大转盘 */
		tplaybtn();
		  $(".asdbtn").on("click",function(){
			   $(".comModule").hide();
			   $(".fixedModule").hide();
		  });	
		  $(".luckyClose").click(function(){
			  $(".lucky").hide();
		  })
	})
	/* 大转盘 */
	function tplaybtn(){
		var $btn = $('.playbtn');
		var playnum = 10; //pow币，由后台传入
		$('.playnum').html(playnum);
		var isture = 0;
		var clickfunc = function() {
			var data = [1, 2, 3, 4, 5, 6];
			//data为随机出来的结果，根据概率后的结果
			data = data[Math.floor(Math.random() * data.length)];
			switch(data) {
				case 1:
					rotateFunc(1, 0, '谢谢参与~再来一次吧~');
					break;
				case 2:
					rotateFunc(2, 60, '恭喜您获得一等奖!');
					break;
				case 3:
					rotateFunc(3, 120, '恭喜您获得二等奖!');
					break;
				case 4:
					rotateFunc(4, 180, '恭喜您获得三等奖!');
					break;
				case 5:
					rotateFunc(5, 240, '谢谢参与~再来一次吧~');
					break;
				case 6:
					rotateFunc(6, 300, '恭喜您获得四等奖!');
					break;
			}
		}
		$btn.click(function() {
			$(".comModule").hide();
			$(".fixedModule").hide();
			if(isture) return; // 如果在执行就退出
			isture = true; // 标志为 在执行
				if(playnum <= 0) { //当抽奖次数为0的时候执行
					alert("没有次数了");
					$('.playnum').html(0);
					isture = false;
				} else { //还有次数就执行
					playnum = playnum - 1; //执行转盘了则次数减1
					if(playnum <= 0) {
						playnum = 0;
					}
					$('.playnum').html(playnum);
					clickfunc();
				}
		});
		var rotateFunc = function(awards, angle, text) {
			isture = true;
			$btn.stopRotate();
			$btn.rotate({
				angle: 0,
				duration: 6000, //旋转时间
				animateTo: angle + 1440, //让它根据得出来的结果加上1440度旋转
				callback: function() {
					isture = false; // 标志为 执行完毕
					//显示弹出窗
					$(".WinningTit").html(text)
			//		$(".asdbtn").css("transform","inherit");
					if(awards==1||awards==5){
						$(".Winning").attr("src","/static/front2018/images/exchange/noWinning.png");
					}else{
						$(".Winning").attr("src","/static/front2018/images/exchange/Winning.png");
					}
					/* 弹出窗 */
					$(".comModule").show();
					$(".fixedModule").show();
				}
			});
		};
	};

</script>
 -->
</body>
</html>
