<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>

<!doctype html>
<html>
<head> 
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name = "viewport" content = "width = device-width, user-scalable = no,initial-scale=0.5,maximum-scale=0.5,minimum-scale=0.5">
<style>
	@charset "utf-8";

.container {
	position: relative;
}
/*header*/
.downTrade_l{
	height: 0.8rem;
    background: #C3C3C3;
    line-height: 0.8rem;
}
.downTrade_l a{
font-size: 0.34rem;
    color: #fff;
    height: 0.3rem;
    font-weight: 900;}
.gray-bg {
    background: #f9fbff url(/static/mobile2018/images/BGDown.png) no-repeat;
    background-size: 100% 100%;
    height: 100%;
    position: relative;
    min-height: 11rem;
}
html,body{
    height: 100%;
    width: 100%;
}
.grayApp{padding-bottom: 3.02rem;}

.grayApp .grayAppUl li{
	text-align: center;
    clear: both;
    overflow: hidden;
}
.grayApp .grayAppUl .downsao a{
    width: 2.7rem;
    height: 0.88rem;
    line-height: 0.88rem;
    border-top: 1px solid #4d6fd0;
    border-radius: 10px;
    color: #fff;
    background: #4d6fd0;
    font-size: 0.26rem;
    text-align: center;
    outline: none;
    display: block;
    margin: 0 auto;
}
.grayApp .grayAppUl .grayAppLi{    
	width: 3.5rem;
    margin: 0 auto;
    margin-top: 0.25rem;
    margin-bottom: 0.51rem;}
.grayApp .grayAppUl .grayAppLi>p:nth-child(2){
    margin: 0 0.65rem;
}
.grayApp .grayAppUl li:nth-child(1){padding-top: 1rem;}
.grayApp .grayAppUl li:nth-child(1) h3{
    font-size: 0.44rem;
    font-family: MicrosoftYaHei;
    height: 0.54rem;
    line-height: 0.54rem;
    margin-bottom: 0.37rem;
    color: #33485B;
    text-align: center;
}
.grayApp .grayAppUl .downtit>img{
   vertical-align: -0.03rem;
    width: 0.25rem;
    height: 0.29rem;
}
.grayApp .grayAppUl .downtit>span{
    font-size: 0.24rem;
    font-family: MicrosoftYaHei;
    font-weight: 400;
    margin-left: 0.14rem;
}
.grayApp .grayAppUl .grayAppLi>p{
    display:inline-block;
}
.grayApp .grayAppUl .grayAppLi>p>span{
    display: block;
    margin-bottom: 0.1rem;
}
.grayApp .grayAppUl .grayAppLi>p>span img{
    width: 0.5rem;
}
.grayApp .grayAppUl .grayAppLi>p>em{
    font-size: 0.18rem;
    font-family: MicrosoftYaHei;
    font-weight: 400;
}
.grayButton{
    background: #131E30;
    width: 100%;
    clear: both;
    height: 2.48rem;
    position: absolute;
    bottom: 0;
    left: 0;
}
.downLeft{
	width: 4rem;
    position: absolute;
    right: 0;
    bottom: 1rem;
}
.downRight{
    width: 1.9rem;
    position: absolute;
    top: -0.86rem;
    left: 0.2rem;
}
#qrcode{position: relative;}
#codeico{
    position: absolute;
    width: 1rem;
    height: 1rem;
    background: #fff url(/static/front2018/images/saologo.png) no-repeat center;
    background-size: 50%;
    left: 47%;
    margin: 0 auto;
    text-align: center;
    margin-left: -0.3rem;
    top: 45%;
}

#weixin-tip{display:none;position:fixed;left:0;top:0;background:rgba(0,0,0,0.8);filter:alpha(opacity=80);width:100%;height:100%;z-index:100;}
#weixin-tip p{text-align:center;margin-top:10%;padding:0 5%;position:relative;}
#weixin-tip .close{color:#fff;padding:5px;font:bold 20px/24px simsun;text-shadow:0 1px 0 #ddd;position:absolute;top:0;left:5%;font-size:0.46rem;}
</style>
<script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
<%@include file="../comm/link.inc.jsp" %>
</head>
<body style="background:#131E30;">
<section class="gray-bg">
<!-- <div class="downTrade_l">
  <a onclick="window.history.go(-1)"><i class="back" style="margin: 0.2rem;"></i></a>
</div> -->
<header class="tradeTop">  
    <i class="back toback2" onclick="window.history.go(-1)"></i>
</header>
<div>
	<div class="grayApp">
		<ul class="grayAppUl">
			<li>
				<h3>下载APP</h3>
			</li>
			<li class="downtit">
				<span>随时随地交易无忧 全新终端平台接入</span>
			</li>
			<li  class="grayAppLi">
				<p>
					<span><img src="${oss_url}/static/front/images/dowload/safedown.png"></span>
					<em>安全</em>
				</p>
				<p>
					<span><img src="${oss_url}/static/front/images/dowload/prossdown.png"></span>
					<em>专业</em>
				</p>
				<p>
					<span><img src="${oss_url}/static/front/images/dowload/fastdown.png"></span>
					<em>高效</em>
				</p>
			</li>
			<li class="downsao">
				<a id="J_weixin" class="android-btn" href="#">
					APP下载
				</a>
			</li>
		</ul>
	</div>
	<%-- <div class="downRight"><img src="${oss_url}/static/front/images/dowload/phonedown.png"></div>
	<div class="downLeft"><img src="${oss_url}/static/front/images/dowload/tuxingdown.png"></div>
	 --%>

</div>
	<div class="grayButton">
		<img src="${oss_url}/static/front/images/dowload/phonedown.png" class="downLeft">
		<img src="${oss_url}/static/front/images/dowload/tuxingdown.png" class="downRight">
	</div>
</section>


		<div id="weixin-tip">
			<p><img src="${oss_url}/static/front/images/dowload/live_weixin.png" alt="微信打开"/><span id="close" title="关闭" class="close">×</span></p>
		</div>
	<script type="text/javascript"	src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
		
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/jquery.qrcode.min.js?v=20181126201750"></script>

<script type="text/javascript">

$(function () {

	init();
})

function init() {
	createQrCode("table",350, 350, "${androidDownloadUrl}");
	var margin = ($("#qrcode").height()-$("#codeico").height())/2;
}

function createQrCode(rendermethod, picwidth, picheight, url) {
    if (navigator.userAgent.indexOf("MSIE") > 0) {
       jQuery('#qrcode').qrcode({
         text : utf16to8(url),
         width : picwidth,
         height : picheight,
         render : rendermethod,
         typeNumber:-1,//计算模式
         correctLevel:2,//二维码纠错级别
         background:"#ffffff",//背景颜色
         foreground:"#000000"  //二维码颜色
       });
     } else {
       jQuery('#qrcode').qrcode({
         text : utf16to8(url),
         width : picwidth,
         height : picheight,
         render : "canvas",
         typeNumber:-1,//计算模式
         correctLevel:2,//二维码纠错级别
         background:"#ffffff",//背景颜色
         foreground:"#000000"  //二维码颜色
       });
     }
   };

function generateQRCode(rendermethod, picwidth, picheight, url) {
    $("#qrcode").qrcode({ 
        render: rendermethod, // 渲染方式有table方式（IE兼容）和canvas方式
        width: picwidth, //宽度 
        height:picheight, //高度 
        text: url, //内容 
    });
}


//中文编码格式转换
function utf16to8(str) {
    var out, i, len, c;
    out = "";
    len = str.length;
    for (i = 0; i < len; i++) {
        c = str.charCodeAt(i);
        if ((c >= 0x0001) && (c <= 0x007F)) {
            out += str.charAt(i);
        } else if (c > 0x07FF) {
            out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));
            out += String.fromCharCode(0x80 | ((c >> 6) & 0x3F));
            out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
        } else {
            out += String.fromCharCode(0xC0 | ((c >> 6) & 0x1F));
            out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
        }
    }
    return out;
}
</script>
<script>
var u = navigator.userAgent;
var is_weixin = (function(){return navigator.userAgent.toLowerCase().indexOf('micromessenger') !== -1})();
var isAndroid = u.toLowerCase().indexOf('android') > -1 || u.toLowerCase().indexOf('linux') > -1; //g
var isIOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
window.onload = function() {
	var winHeight = typeof window.innerHeight != 'undefined' ? window.innerHeight : document.documentElement.clientHeight; //兼容IOS，不需要的可以去掉
	var weixinbtn = document.getElementById('J_weixin');
	var weixintip = document.getElementById('weixin-tip');
	var weixinclose = document.getElementById('close');
	if (is_weixin) {
		weixinbtn.onclick = function(e) {
			weixintip.style.height = winHeight + 'px'; //兼容IOS弹窗整屏
			weixintip.style.display = 'block';
			
			return false;
		}
		weixinclose.onclick = function() {
			weixintip.style.display = 'none';
		}
	}
	if(isAndroid){
		weixinbtn.innerHTML="Android版下载";
		weixinbtn.href="${androidDownloadUrl}";
	}
	
	if(isIOS){
		weixinbtn.innerHTML="IOS版下载";
		weixinbtn.href=window.location.origin+"/static/app/"+document.domain+".mobileconfig";
	}
}
</script>
</body>
</html>















