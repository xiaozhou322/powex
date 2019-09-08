<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>

<!doctype html>
<html>
<head> 
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp" %>

<link rel="stylesheet" href="${oss_url}/static/front/css/dowload/downApp.css?v=20181126201750" type="text/css"></link>
<style type="text/css">
#qrcode{
	position: relative;
    width: 200px;
    height: 200px;}
#codeico{
            position: absolute;
		    width: 50px;
		    height: 50px;
		    background: #fff url(/static/front2018/images/saologo.png) no-repeat center;
		    background-size: 50%;
		    left: 40%;
		    margin: 0 auto;
		    text-align: center;
		    margin-left: -5px;
		    top: 36%;
        }
</style>
</head>
<body >
<%@include file="../comm/header.jsp" %>
<section>
<div  class="gray-bg">
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
        	 <li id="qrcode">
        	 	<div id="codeico"></div>
    		 </li>
			<%-- <li>
				<img src="${oss_url}/static/front/images/dowload/downsao.png">
			</li> --%>
		</ul>
	</div>
	<div class="grayButton">
		<div class="downLeft"><img src="${oss_url}/static/front/images/dowload/tuxingdown.png"></div>
		<div class="downRight"><img src="${oss_url}/static/front/images/dowload/phonedown.png"></div>
	</div>
</div>

</section>
	
	

<script type="text/javascript">

$(function () {

	init();
})

function init() {
	createQrCode("table",200, 200, window.location.href);
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
</body>
</html>















