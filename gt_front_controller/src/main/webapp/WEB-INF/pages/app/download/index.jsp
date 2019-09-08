<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<!doctype html>
<html lang="zh-cn">
<head>
    <meta charset="UTF-8">
    <title>${requestScope.constant['webinfo'].ftitle }</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta name="description" content="${requestScope.constant['webinfo'].fdescription }">
	<meta name="keywords" content="${requestScope.constant['webinfo'].fkeywords }">
	<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0" name="viewport">
	<meta content="yes" name="apple-mobile-web-app-capable">
	<meta content="black" name="apple-mobile-web-app-status-bar-style">
	<meta content="telephone=no" name="format-detection">
  <link href="/static/app/css/index.css" rel="stylesheet" type="text/css" media="screen, projection">
  <link href="/static/app/css/weixinAppDowload.css" rel="stylesheet" type="text/css" media="screen, projection">
  <script type="text/javascript" name="baidu-tc-cerfication" data-appid="6281363" src="/static/app/js/lightapp.js"></script>
</head>
<body>
	<div id="appDownloadBody" style="width: 100%; height: 100%; display: block;">
		<div class="appDowload" style="height: 282.15px;"> 
			<img alt="" style="height: 100%;width: 100%;position: relative;" src="/static/app/images/appDownloadBg.png">
			<div class="titlePart">
				<img class="logo" src="/static/app/images/logo.png" name="mobileImgLogo">
				<p class="title">${requestScope.constant['webName']}交易平台APP</p>
			</div>
			<div class="download">
				<a class="downloadBth" href="#" onClick="javascript:showTipImg()">立刻下载</a>
			</div>
		</div>
		<div class="appDowloadExplain">
			<div style="width: 50%; height: 125.4px;">
				<dl class="dlHeight borderRight borderBottom">
					<dd style="line-height: 62.7px;">
						<img alt="" src="/static/app/images/Realtime.png">
						<p>行情信息实时掌握</p>
					</dd>
				</dl>
			</div>
			<div style="height: 125.4px;">
				<dl class="dlHeight borderBottom">
					<dd style="line-height: 62.7px;">
						<img alt="" src="/static/app/images/Instant.png">
						<p>会员转账方便快捷</p>
					</dd>
				</dl>
			</div>
			<div style="width: 50%; height: 125.4px;">
				<dl class="dlHeight borderRight">
					<dd style="line-height: 62.7px;">
						<img alt="" src="/static/app/images/Trade.png">
						<p>虚拟货币实时交易</p>
					</dd>
				</dl>
			</div>
			<div style="height: 125.4px;">
				<dl class="dlHeight">
					<dd style="line-height: 62.7px;">
						<img alt="" src="/static/app/images/Curve.png">
						<p>K线信息一目了然</p>
					</dd>
				</dl>
			</div>
		</div>
		<div class="sharePop" id="tipImg" style="display: none">
		 	<img onClick="closeTipImg()" alt="" src="/static/app/images/weixinDowloadTips0.png?150109">
		</div>
	</div>
<script type="text/javascript" src="/static/app/js/jquery-1.8.2.js"></script>
<script type="text/javascript">
	function is_weixin() {
		    var ua = navigator.userAgent.toLowerCase();
		    if (ua.match(/MicroMessenger/i) == "micromessenger") {
		        return true;
		    } else {
		        return false;
		    }
		}

	function showTipImg(){
		var ua = navigator.userAgent.toLowerCase();
		    if (is_weixin()) {
		        dialogBoxShadow();
	            document.getElementById("tipImg").style.display = "";
		    } else {
		        window.location.href="${requestScope.constant['android_downurl'] }";
		    }
	    
	}
	function closeTipImg(){
	    var obj = document.getElementById("tipImg");
	    if(null==obj){
	        return;
	    }
	    dialogBoxHidden();
	    document.getElementById("tipImg").style.display = "none";
	}
	
	function dialogBoxHidden(){
	    var d=document,
	        o=d.getElementById("dialogBoxShadow");
	    if(!o) return false;
	    d.body.removeChild(o);
	}
	
	function dialogBoxShadow(f){
	    dialogBoxShadowMove(f,true);
	}
	
	function dialogBoxShadowMove(f,canmove){
	    var d = document,
	        divs=d.createElement("div"),
	        doc = d[d.compatMode == "CSS1Compat"?'documentElement':'body'],
	        h = f?doc.clientHeight:Math.max(doc.clientHeight,doc.scrollHeight);
	    divs.setAttribute("id","dialogBoxShadow");
	    divs.setAttribute("onclick","javascript:closeTipImg();");
	    d.body.appendChild(divs);
	    var o = d.getElementById('dialogBoxShadow');
	    o.style.cssText +="	;position:absolute;top:0;left:0;z-index:100;background:#000;opacity:0.7;filter:Alpha(opacity=20);width:100%;height:"+h+"px";
	}
	$(function () {
		var screenHeight = document.documentElement.clientHeight;
		$(".appDowload").css({
			"height":screenHeight*0.45 + "px",
		});
		$(".appDowloadExplain div").css({
			"height":screenHeight*0.20 + "px",
		});
		$(".appDowloadExplain div dd").css({
			"line-height":screenHeight*0.10 + "px",
		});
		$(".appDowloadExplain div dt").css({
			"line-height":screenHeight*0.10 + "px",
		});
		$("#appDownloadBody").css({
			"display":"block",
		});

	});
</script>

</body>	
	
	
</html>