 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;if (request.getServerName().equals("www.gbcax.com")){basePath="https://www.gbcax.com";}
%>

<!doctype html>
<html>
<head> 
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name = "viewport" content = "width = device-width, user-scalable = no,initial-scale=0.5,maximum-scale=0.5,minimum-scale=0.5">


<%@include file="../comm/link.inc.jsp" %>
    <title><spring:message code="nav.top.login" /></title>
    <style type="text/css">
    html,body{height:100%;}
    .login_inps{
    		width: 100%;
		    margin-bottom: 0.4rem;
		    position: relative;
    }
    .login_inps .txt{
    	    width: 100%;
		    line-height: 0.55rem;
		    font-size: 0.28rem;
		    border-bottom: 1.5px solid #89afe6;
		    padding-left: 0.05rem;
    }
     .sendCode{
    	width:43%;
    } 
  
    #nc_1-stage-1 .slider .label{
   		 font-size:0.23rem;
    }
    
    #nc_1-stage-1 .bg-green{
    	font-size:0.23rem;
    }
</style>
</head>


<body>
<%@include file="../comm/header.jsp" %>
<div class="warp">
    <div class="login">
        <h2 class="login_tit"><img class="logo2" src="${oss_url}/static/mobile2018/images/phoneLogo.png" alt="" /></h2>        
        <div class="loginMain">
            <div class="login_inp">
                <i class="iconfont icon-wode cblue2 fl"></i>
                <input class="txt" type="text" name="" id="login-account" placeholder="<spring:message code="security.accounttips" />" autocomplete="off">
            </div>
            <div class="login_inp">
               <i class="iconfont icon-mima cblue2 fl"></i>
                <input class="txt" type="password" name="" id="login-password" placeholder="<spring:message code="security.logpas" />" autocomplete="off">
            </div>
            <!-- <p id="notice2" style="color: red; display: none;">请先完成验证</p> -->
        	<div  class="login_inp" id="login-validate"></div>
    		<input type='hidden' id='sessionid' name='sessionid'/>
		    <input type='hidden' id='sig' name='sig'/>
		    <input type='hidden' id='token' name='token'/>
		    <input type='hidden' id='scene' name='scene'/>
		    <input type='hidden' id='appkey' name='appkey'/>
		    
            <div class="btns">
                <input id="login-submit" type="submit" value="<spring:message code="nav.top.login" />" class="btn">
                <p><a href="user/register.html?phone=cn" class="btn btn1"><spring:message code="nav.top.register" /></a></p>
            </div>
            <p class="forgetPwd">
                <a class="cblue" href="/validate/resetPhone.html"><spring:message code="nav.index.forpass" /><i style="vertical-align:-0.02rem; font-size:0.32rem;" class="iconfont icon-04wenhaofuzhi"></i> </a>
            </p>
        </div>
        <div class="abnormalLogin" style="margin: 35px 0 60px 0;display:none;">
		    <h2 style="font-size:0.3rem;color:#2269d4;" id="h_title3"><spring:message code="new.login.Googlelogin" /></h2>
		   	<h2 style="font-size:0.3rem;color:#2269d4;" id="h_title1"><spring:message code="new.login.Phonelogin" /></h2>
		    <h2 style="font-size:0.3rem;color:#2269d4;" id="h_title2"><spring:message code="new.login.Emaillogin" /></h2><br>
		<div class="txt_tr text_2 login_inps" >
            <span class="sendCode btn-sendemailcode register-msg" id="login_sendemail" data-msgtype="3" data-tipsid="register-errortips" onclick="way1()"><spring:message code="financial.send" /></span>
            <input class="txt" type="text" name="" id="emailCode" placeholder="<spring:message code="security.accounttipsEmail" />" autocomplete="off" >
        </div><br>
        <div class="txt_tr text_1 login_inps" >
            <span class="sendCode btn-sendemailcode register-msg" id="login_sendphone" data-msgtype="3" data-tipsid="register-errortips" onclick="way2()"><spring:message code="financial.send" /></span>
            <input class="txt" type="text" name="" id="phoneCode" placeholder="<spring:message code="security.accounttipsPhone" />" autocomplete="off" >
        </div><br>
		<div class="txt_tr text_3 login_inps">
            <input class="txt" type="text" name="" id="googleCode" placeholder="<spring:message code="security.accounttipsGoogle" />" autocomplete="off">
        </div>
		      <div class="txtBtn mgt50">
                <span id="Secondlogin-submit" class="btn" onclick="ValidateCode()"><spring:message code="nav.top.verify" /></span>
	   		 </div>
    </div>
    </div>
</div>

<%@include file="../comm/footer.jsp" %>     
<script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/login.js?v=2"></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/comm/msg.js?v=20181126201750"></script>
<script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js"></script>
<!-- 国内使用 -->
<script type="text/javascript" charset="utf-8" src="//g.alicdn.com/sd/nch5/index.js?t=2015052012"></script>
<!-- 若您的主要用户来源于海外，请替换使用下面的js资源 -->
<!-- <script type="text/javascript" charset="utf-8" src="//aeis.alicdn.com/sd/nch5/index.js?t=2015052012"></script> -->
</body>

</html>
<script type="text/javascript">
function way1(){
	email.sendcodeTwo(3, "layui-layer", "login_sendemail");
	
}	

function way2(){
	msg.sendMsgCodeTwo(18, "layui-layer", "login_sendphone")
}

function ValidateCode(){
	var code = null;
	if(clicktype==null){
		return;
	}else{
		if(clicktype==0){
			code=$("#phoneCode").val();
		}else if(clicktype==1){
			code=$("#emailCode").val();
		}else{
			code=$("#googleCode").val();
		}
	}
	var param={	
			codeType:clicktype,
			code:code
	}
	$.post("/user/codelogin.html?random=" + Math.round(Math.random() * 100), param, function(data) {
		if (data.code == 0) {
			//提示登录名不存在
			window.location.href = "/index.html";
		} else if(data.code == -1){
			if(clicktype==0){
				layer.msg(data.msg);
			}else if(clicktype==1){
				layer.msg(data.msg);
			}else if(clicktype==2){
				layer.msg(data.msg);
			}else{
				
			}
			
		}else {
			//提示异常
		}
	}, "json");
	
}


var appkey = "FFFF00000000017F11B9";
var scene = "nc_login_h5";
var nc_token = ["FFFF00000000017F11B9", (new Date()).getTime(), Math.random()].join(':');
var nc=NoCaptcha.init({
    renderTo: '#login-validate',
    appkey: appkey, 
    scene: scene,
    token: nc_token,
    trans: {"key1": "code200"},
    elementID: ["usernameID"],
    is_Opt: 0,
    language: "cn",
    timeout: 10000,
    retryTimes: 5,
    errorTimes: 5,
    inline:false,
    apimap: {
        // 'analyze': '//a.com/nocaptcha/analyze.jsonp',
        // 'uab_Url': '//aeu.alicdn.com/js/uac/909.js',
    },
    bannerHidden:false,
    initHidden:false,
    callback: function (data) {
        window.console && console.log(nc_token)
        window.console && console.log(data.csessionid)
        window.console && console.log(data.sig)
        
        $('#sessionid').val(data.csessionid);
        $('#sig').val(data.sig);
        $('#token').val(nc_token);
        $('#scene').val(scene);
        $('#appkey').val(appkey);
    },
    error: function (s) {
    }
});
NoCaptcha.setEnabled(true);
nc.reset();//请务必确保这里调用一次reset()方法

NoCaptcha.upLang('cn', {
    'LOADING':language["aliyun.nc.LOADING"],//加载
    'SLIDER_LABEL': language["aliyun.nc.SLIDER_LABEL"],//等待滑动
    'CHECK_Y':language["aliyun.nc.CHECK_Y"],//通过
    'ERROR_TITLE':language["aliyun.nc.ERROR_TITLE"],//拦截
    'CHECK_N':language["aliyun.nc.CHECK_N"], //准备唤醒二次验证
    'OVERLAY_INFORM':language["aliyun.nc.OVERLAY_INFORM"],//二次验证
    'TIPS_TITLE':language["aliyun.nc.TIPS_TITLE"]//验证码输错时的提示
});
</script>
</script>
