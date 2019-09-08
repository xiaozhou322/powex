<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>

<!doctype html>
<html>
<head> 
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp" %>
<style>
.bannarNav{
    background: url(/static/front2018/images/exchange/introlbanner.png) no-repeat center;
    height: 335px;
    background-size: 100% 100%;
}
.bannarNav_tit{
        color: #FFE2BA;
    font-size: 54px;
    width: 600px;
    text-align: center;
    margin: 0 auto;
    padding-top: 84px;
    line-height: 79px;
    letter-spacing: 4px;
}
.introlCenter{
    margin: 0 auto;
    min-width: 1300px;
}
.invitedA, .exclusive, .introlList{
    clear: both;
    overflow: hidden;
    zoom: 1;
    width: 1300px;
    margin: 0 auto;
    text-align: center;
    background: #fff;
    padding: 50px 0;
    margin-top: 55px;
    box-shadow: 0px 2px 9px 0px rgba(70, 90, 111, 0.14);
}
.invitedA h3, .exclusive h3{
	    color: #33485B;
    font-size: 30px;
    text-align: center;
    margin-bottom: 24px;
}
.introlo1{
    position: absolute;
    top: 385px;
    left: 312px;
}
.introlo2{
    position: absolute;
    top: 70px;
    left: 42px;
}
.introlo3{
    position: absolute;
    top: 137px;
    right: 31px;
}
.invitedAUl{
	clear: both;
    overflow: hidden;
    zoom: 1;
    padding: 0 60px;
    }
.invitedAUl li{display: inline-block;}
.invitedAUl li p{    
    display: inline-block;
    border: 1px dashed rgba(153, 168, 182, 0.3);
    width: 224px;
    vertical-align: 35px;
    margin: 0 22px;}
.invitedBUl{margin-top: 51px;}
.invitedBUl li{    
	display: inline-block;
    width: 261px;
    margin-right: 70px;}
 .invitedAUl li img{    width: 70px; }
 .invitedBUl li img{    width: 50%; }
.invitedBUl li p{
    font-size: 20px;
    font-family: MicrosoftYaHei;
    color: #4E6E8A;
    line-height: 43px;
    margin-top: 20px;
}
.invitedBUl li:last-child {
    margin-right: 0;
}
.exclusive{position: relative;}
#qrcode{
    position: relative;
    width: 260px;
    margin: 0 auto;
    text-align: center;
    margin-top: 10px;
    margin-bottom: 40px;
    height: 260px;}
.qrcodederico{
    position: absolute;
    width: 50px;
    height: 50px;
    background: #fff url(/static/front2018/images/saologo.png) no-repeat center;
    background-size: 70%;
    left: 43%;
    margin: 0 auto;
    top: 43%;
    margin-top: -13px;
}
.exclusiveUl li:nth-child(1) p{
    color: #4E6E8A;
    font-size: 22px;
    font-family: MicrosoftYaHei;
    line-height: 50px;
}
.buyBtn{
    width: 300px;
    height: 59px;
    background: #488bef;
    text-align: center;
    line-height: 59px;
    margin-top: 10px;
    color: #fff;
    font-size: 30px;
    display: inline-block;
    cursor: pointer;
}
.introlList{
padding-bottom: 15px;
}
.introlListA{
    clear: both;
    overflow: hidden;
    margin-bottom: 40px;
}
.introlListB{
	clear: both;
    overflow: hidden;
}
.introlListA li{
    float: left;
    text-align: center;
    font-size: 20px;
    font-family: MicrosoftYaHei;
    width: 325px;
    color: #4E6E8A;
}

.introlListB li span{
    border: 1px solid #FC8B72;
    font-size: 16px;
    font-family: MicrosoftYaHei;
    color: #FC8B72;
    padding: 0 4px;
    margin-left: 20px;
}
.introlListB li em{
    font-size: 20px;
    color: #4E6E8A;
}
.introlListB li label{
color: #FC8B72;
    font-size: 14px;
    font-family: MicrosoftYaHei;
    margin-left: 9px;
}
.introlListB li{
    float: left;
    text-align: center;
    font-size: 30px;
    font-family: MicrosoftYaHei;
    width: 325px;
    color: #33485B;
}
.introldet{
        float: right;
    color: #2269D4;
    font-size: 15px;
    margin-top: 38px;
    padding-bottom: 10px;
    margin-right: 30px;
}
.rewards{
    clear: both;
    overflow: hidden;
    zoom: 1;
    width: 1300px;
    margin: 0 auto;
    margin-top: 60px;
}
.rewards .rewardsTit{
        border-bottom: 1px solid #4e6e8a38;
    height: 50px;
    display: block;
}
.rewards .rewardsUl{    
    font-size: 16px;
    font-family: MicrosoftYaHei;
    color: #4E6E8A;
    margin-bottom: 2em;
    line-height: 40px;
    padding: 20px 16px;}
.rewards .rewardsTit span{vertical-align: -4px;}
.rewards .rewardsTit span>img{width: 22px;}
.rewards .rewardsTit h3{display: inline-block;
    font-size: 20px;
    padding-left: 5px;
    font-family: MicrosoftYaHei;}
</style>
</head>
<body>
<input type="hidden" id="type" value="1"/>
<%@include file="../comm/header.jsp" %>
<section style="background:#F9FBFF;">
<!--banner-->
	<div class="bannarNav">
		<div class="bannarNav_tit"><spring:message code="user.invite.inviteReg" /></div>
	</div>
	<!-- 内容区域 -->
	<div class="introlCenter">
		<!-- 邀请步骤 -->
		<div class="invitedA">
			<h3><spring:message code="user.invite.inviteStep" /></h3>
			<ul class="invitedAUl">
				<li>
				<img src="/static/front2018/images/exchange/introls-1.png">
				<p></p>
				</li>
				<li>
				<img src="/static/front2018/images/exchange/introls-2.png">
				<p></p>
				</li>
				<li>
				<img src="/static/front2018/images/exchange/introls-3.png">
				<p></p>
				</li>
				<li><img src="/static/front2018/images/exchange/introls-4.png"></li>
			</ul>
			<ul class="invitedBUl">
				<li>
					<img src="/static/front2018/images/exchange/introl-pic1.png">
					<p><spring:message code="user.invite.inviteStep1" /></p>
				</li>
				<li>
					<img src="/static/front2018/images/exchange/introl-pic2.png">
					<p><spring:message code="user.invite.inviteStep2" /></p>
				</li>
				<li>
					<img src="/static/front2018/images/exchange/introl-pic3.png">
					<p><spring:message code="user.invite.inviteStep3" /></p>
				</li>
				<li>
					<img src="/static/front2018/images/exchange/introl-pic4.png">
					<p><spring:message code="user.invite.inviteStep4" /></p>
				</li>
			</ul>
		</div>
		<c:if test="${sessionScope.login_user == null }">  
			<div class="exclusive">
				<h3 style="color: #b7b7b7;font-size: 20px;"><spring:message code="user.invite.loginSee" /></h3>
				<a href="/user/login.html" class="buyBtn"><spring:message code="user.invite.login" /></a>
			</div>
		</c:if>
	<c:if test="${sessionScope.login_user != null }">  
		<!-- 我的专属推广链接 -->
		<div class="exclusive">
			<h3><spring:message code="user.invite.myLink" /></h3>
			<p class="introlo1"><img src="/static/front2018/images/exchange/introl03.png"></p>
			<p class="introlo2"><img src="/static/front2018/images/exchange/introlo2.png"></p>
			<p class="introlo3"><img src="/static/front2018/images/exchange/introlo1.png"></p>
			<ul class="exclusiveUl">
				<li>
					<p id="link">${spreadLink}</p>
					<p><spring:message code="user.invite.inviteCode" />&nbsp;&nbsp;${fuserNo}</p>
				</li>
				<li>
					<div id="qrcode">
						<div class="qrcodederico"></div>
					</div>
					<button class="buyBtn linkCopy" data-clipboard-target="#link"><spring:message code="user.invite.goInvite" /></button>
				</li>
			</ul>
		</div>
		<!-- 邀请人数 -->
		<div class="introlList">
			<ul class="introlListA">
				<li><spring:message code="user.invite.inviteNum" /></li>
				<li><spring:message code="user.invite.rewardNum" /></li>
				<li><spring:message code="user.invite.fstInviteNum" /></li>
				<li><spring:message code="user.invite.secInviteNum" /></li>
			</ul>
			<ul class="introlListB">
				<li>${fristtotal+introTotal }&nbsp;<em>人</em></li>
				<li><fmt:formatNumber type="number" value="${introReward }" pattern="0" maxFractionDigits="0"/>&nbsp;&nbsp;<em>${rewardCoin }</em></li>
				<li>${fristtotal }&nbsp;<em>人</em><span><fmt:formatNumber type="number" value="${firsttranslation}" pattern="0.00" maxFractionDigits="2"/>%</span><label><spring:message code="introl.winrat" /></label></li>
				<li>${introTotal }&nbsp;<em>人</em><span><fmt:formatNumber type="number" value="${twotranslation}" pattern="0.00" maxFractionDigits="2"/>%</span><label><spring:message code="introl.winrat" /></label></li>
			</ul>
			<a href="/introl/mydivide.html?type=1" class="introldet"><spring:message code="user.invite.seeInviteDetail" /></a>
		</div>
		</c:if>
		<!-- 邀请奖励规则说明 -->
			<div class="rewards">
				<div class="rewardsTit">
					<span><img src="/static/front2018/images/exchange/introlIcon.png"></span>
					<h3><spring:message code="user.invite.inviteRule" /></h3>
				</div>
				<ul class="rewardsUl">
					<li><spring:message code="user.invite.inviteRuleStep1" /></li>
					<li><spring:message code="user.invite.inviteRuleStep2" /></li>
					<li><spring:message code="user.invite.inviteRuleStep3" /></li>
					<li><spring:message code="user.invite.inviteRuleStep4" /></li>
				</ul>
			</div>
	</div>
	
</section>

<%@include file="../comm/footer.jsp" %>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/clipboard.min.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/jquery.qrcode.min.js?v=20181126201750"></script>
<script type="text/javascript">
// 将【复制】按钮充当复制数据的元素载体
var clip = new Clipboard('.linkCopy');
clip.on('success', function(e) {
		   /* 	$('.userInvite-linktips').show(); */
		   util.showMsg("推广链接已复制到本地，可粘贴发送给好友推广获得推广奖励");
		    e.clearSelection();
		});

$(".close_times").click(function(){
	$(".cli_invite").css("display","none");
})

$(function () {
	var spreadLink = $("#link").html();
    //生成二维码
	createQrCode(spreadLink);

})

function createQrCode(spreadLink) {
    if (navigator.userAgent.indexOf("MSIE") > 0) {
       jQuery('#qrcode').qrcode({
         text : spreadLink,
         width : "260",
         height : "260",
         render : "canvas"
       });
     } else {
       jQuery('#qrcode').qrcode({
         text : spreadLink,
         width : "260",
         height : "260",
         render : "canvas"
       });
     }
 }
	
</script>
</body>
</html>