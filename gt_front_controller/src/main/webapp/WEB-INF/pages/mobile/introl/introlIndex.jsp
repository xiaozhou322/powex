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
}
.bannarNav_tit{
    color: #FFE2BA;
    font-size: 54px;
    width: 600px;
    text-align: center;
    margin: 0 auto;
    padding-top: 84px;
    line-height: 79px;
    letter-spacing: 2px;
}
.introlCenter{}
.introlCenter ul{
	text-align: center;
    margin-top: 0.4rem;
}
.introlCenter ul li{
    box-shadow: 0px 2px 9px 0px rgba(70, 90, 111, 0.14);
    display: inline-block;
    width: 45%;
    text-align: center;
    margin: 0 auto;
    margin-bottom: 0.37rem;
    padding: 0.3rem 0;
    background: #fff;
}
.introlCenter ul li .introlImg img{height: 2.34rem;}
.introlCenter ul li .introlImgTit img{}
.introlCenter ul li h3{
	font-size: 0.24rem;
    color: #4E6E8A;
    line-height: 0.46rem;
}
.exclusive{margin-bottom: 0.3rem;}
.exclusive .goInvite{
	background: #2269D4;
    width: 3rem;
    height: 0.6rem;
    color: #fff;
    display: block;
    margin: 0 auto;
    font-size: 0.3rem;
    line-height: 0.6rem;
    border-radius: 0.08rem;
    text-align: center;
}
.exclusive .seeInvite{
    text-align: center;
    margin: 0 auto;
    display: block;
    font-size: 0.24rem;
    height: 1rem;
    line-height: 1rem;
}
#qrcode img{width: 50%;}
</style>
</head>
<body>
<input type="hidden" id="type" value="1"/>
<%@include file="../comm/header.jsp" %>
<section style="background:#F9FBFF;">
<!--banner-->
	<div class="bannarNav">
		<a class="back" style=" margin: 0.1rem 0 0 0.2rem; "></a>
		<div class="bannarNav_tit"><spring:message code="user.invite.inviteReg" /></div>
	</div>
	<!-- 内容区域 -->
	<div class="introlCenter">
		<ul>
			<li style="margin-right: 0.15rem;">
				<p class="introlImg"><img src="${oss_url}/static/mobile2018/images/exchange/introl-pic1.png"></p>
				<p class="introlImgTit"><img src="${oss_url}/static/mobile2018/images/exchange/introls-1.png"></p>
				<h3>完成POWEX用户注册<br>并完成KYC2认证</h3>
			</li>
			<li>
				<p class="introlImg"><img src="${oss_url}/static/mobile2018/images/exchange/introl-pic2.png"></p>
				<p class="introlImgTit"><img src="${oss_url}/static/mobile2018/images/exchange/introls-2.png"></p>
				<h3>获得推广链接及<br>专属二维码</h3>
			</li>
			<li style="margin-right: 0.15rem;">
				<p class="introlImg"><img src="${oss_url}/static/mobile2018/images/exchange/introl-pic3.png"></p>
				<p class="introlImgTit"><img src="${oss_url}/static/mobile2018/images/exchange/introls-3.png"></p>
				<h3>邀请好友通过专属链接<br>注册并完成KYC2认证</h3>
			</li>
			<li>
				<p class="introlImg"><img src="${oss_url}/static/mobile2018/images/exchange/introl-pic4.png"></p>
				<p class="introlImgTit"><img src="${oss_url}/static/mobile2018/images/exchange/introls-4.png"></p>
				<h3>获得邀请奖励存<br>入糖果红包</h3>
			</li>
		</ul>
	
	
		<!-- 邀请步骤 -->
		<c:if test="${sessionScope.login_user == null }">  
			<div class="exclusive">
				<%-- <h3 style="color: #b7b7b7;font-size: 20px;"><spring:message code="user.invite.loginSee" /></h3> --%>
				<a href="/user/login.html" class="goInvite"><spring:message code="user.invite.login" /></a>
			</div>
		</c:if>
		<c:if test="${sessionScope.login_user != null }">
			<div class="exclusive">
				<button class="goInvite goInviteBtn">
					<spring:message code="user.invite.goInvite" />
				</button>
				<a href="/introl/mydivide.html?tab=2" class="seeInvite">
					<spring:message code="user.invite.seeInviteDetail" />
				</a>
			</div>
		</c:if>
		
	</div>
	<div class="warpBg shareWarp">
		<div class="yqCode shareWarp2">
	       	<p><spring:message code="m.security.erweim" /></p>
	        <div class="pic"><span id="qrcode"></span></div>
	       <div class="pro_picture">
	           	<div class="invite_content">
		       		<spring:message code="m.invite.con1" />
		       		<br><spring:message code="m.invite.con2" />
	        	</div>
	       </div>
	    </div>
  	</div>
</section>
<p id="link" style="display:none;">${spreadLink}</p>
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
    $(".goInviteBtn").click(function(){
    	$(".warpBg").show();
    	$(".yqCode").show();
    })

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
    	
       //获取网页中的canvas对象
        var mycanvas1=$("#qrcode").find('canvas')[0];
       //将转换后的img标签插入到html中
    	var image = new Image();
    	image.src = mycanvas1.toDataURL("image/png");
        $('#qrcode').html(image); //imgDiv表示你要插入的容器id
//    	$("#qrcodeder").find('canvas').hide() 
 }
	
</script>
</body>
</html>