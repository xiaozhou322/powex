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
<!-- Google Tag Manager -->
<script>(function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':
new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0],
j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src=
'https://www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f);
})(window,document,'script','dataLayer','GTM-55N5J7M');</script>
<!-- End Google Tag Manager -->

<!-- Global site tag (gtag.js) - Google Analytics -->
<script async src="https://www.googletagmanager.com/gtag/js?id=UA-127188212-1"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'UA-127188212-1');
</script>

<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp" %>
<style type="text/css">
	   .nc_scale {
      background: #606c98 !important;
      /* 默认背景色 */
     
    }
    
    
</style>
</head>


<body class="l_body login_body">
<!-- Google Tag Manager (noscript) -->
<noscript><iframe src="https://www.googletagmanager.com/ns.html?id=GTM-55N5J7M"
height="0" width="0" style="display:none;visibility:hidden"></iframe></noscript>
<!-- End Google Tag Manager (noscript) -->
    <div class="fixed">
       <%@include file="../comm/header.jsp" %>
    </div>
<div class="formMain">  
    <div class="loginMain clear">
        <div class="loginL fl">
            <h2><spring:message code="new.login.title" /></h2> <br>
            <div class="txt_tr">
                <label for=""><input class="tr_inp" id="login-account" type="text" placeholder="<spring:message code="security.accounttips" />" autocomplete="off" /></label>
            </div>
            <div class="txt_tr">
                <label for=""><input class="tr_inp" id="login-password" type="password" placeholder="<spring:message code="security.logpas" />" autocomplete="off"/></label>
            </div>
            <!-- <div class="txt_tr" id="login-validate"></div> -->
  			<!--No-Captcha渲染的位置，其中 class 中必须包含 nc-container-->
  			<div id="login-validate" class="nc-container"></div> 
            <!-- <p id="notice2" style="color: red; display: none;">请先完成验证</p> -->
  			<input type='hidden' id='sessionid' name='sessionid'/>
		    <input type='hidden' id='sig' name='sig'/>
		    <input type='hidden' id='token' name='token'/>
		    <input type='hidden' id='scene' name='scene'/>
		    <input type='hidden' id='appkey' name='appkey'/>
            <div class="txtBtn mgt50">
                <span id="login-submit" class="btn"><spring:message code="nav.top.login" /></span>
                <a href="/validate/resetPhone.html" class="lightBlue a_hover forgetPwd"><spring:message code="nav.index.forpass" />？</a>
            </div>
        </div>
        <div class="loginR loginR_1 fl">
                <p><spring:message code="new.reg.txt1" />？<br /> <spring:message code="new.reg.txt2" /></p>
                <div class="mgt30"><a href="/user/register.html?phone=cn" class="a_hover lightBlue"><spring:message code="new.freereg.title" /></a></div>
        </div>
	    <div class="abnormalLogin" style="margin: 35px 0 60px 0;display:none;">
		    <h2 style="font-size:30px;" id="h_title3"><spring:message code="new.login.Googlelogin" /></h2>
		   	<h2 style="font-size:30px;" id="h_title1"><spring:message code="new.login.Phonelogin" /></h2>
		    <h2 style="font-size:30px;" id="h_title2"><spring:message code="new.login.Emaillogin" /></h2>
		    <div class="txt_tr text_1"><br><br>
		         <label for=""  class="label"class=""><input class="tr_inp"  id="phoneCode" type="text" placeholder="<spring:message code="security.accounttipsPhone" />" autocomplete="off" /><button class="getCode btn-sendemailcode login-msg" id="login_sendphone" data-msgtype="3" data-tipsid="register-errortips" onclick="way2()"><spring:message code="financial.send" /></button></label>
		    </div>
		    <div class="txt_tr text_2"><br><br>
		          <label for=""  class="label"class=""><input  class="tr_inp" id="emailCode"  type="text" placeholder="<spring:message code="security.accounttipsEmail" />" autocomplete="off" /><button class="getCode btn-sendemailcode login-msg" id="login_sendemail" data-msgtype="3" data-tipsid="register-errortips" onclick="way1()"><spring:message code="financial.send" /></button></label>
		    </div>
		    <div class="txt_tr text_3" ><br><br>
		          <label for=""><input class="tr_inp"  type="text" id="googleCode" placeholder="<spring:message code="security.accounttipsGoogle" />" autocomplete="off" /></label>
		    </div>
		      <div class="txtBtn mgt50">
                <span id="Secondlogin-submit" class="btn"><spring:message code="nav.top.verify" /></span>
	   		 </div>
    </div>
</div>


</div>
<p class="CopyRight" style="margin:0;">CopyRight© 2013-2018 POWEX.PRO All Rights Reserved</p>

<script type="text/javascript" src="${oss_url}/static/front2018/js/user/login.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/comm/msg.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/bootstrap.js?v=20171025221650.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/layer/layer.js?v=20171025221650.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/comm/util.js?v=20171025221650.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/comm/comm.js?v=20171025221650.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/language/language_<spring:message code="language.title" />.js?v=20171025221650.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/main/main.js?v=4"></script>
<!-- 国内使用 -->
<script type="text/javascript" charset="utf-8" src="//g.alicdn.com/sd/ncpc/nc.js?t=2015052012"></script>
<!-- 若您的主要用户来源于海外，请替换使用下面的js资源 -->
<!-- <script type="text/javascript" charset="utf-8" src="//aeis.alicdn.com/sd/ncpc/nc.js?t=2015052012"></script> -->

</body>
</html>
<script type="text/javascript">
	   var appkey = "FFFF0N00000000006DE5";
	   var scene = "nc_login";
       var nc_token = ["FFFF0N00000000006DE5", (new Date()).getTime(), Math.random()].join(':');
       var NC_Opt = 
       {
           renderTo: "#login-validate",
           appkey: appkey,
           scene: scene,
           token: nc_token,
           customWidth: 522,
           height: 50,
           trans:{"key1":"code0"},
           elementID: ["usernameID"],
           is_Opt: 0,
           language: "cn",
           isEnabled: true,
           timeout: 3000,
           times:5,
           apimap: {
               // 'analyze': '//a.com/nocaptcha/analyze.jsonp',
               // 'get_captcha': '//b.com/get_captcha/ver3',
               // 'get_captcha': '//pin3.aliyun.com/get_captcha/ver3'
               // 'get_img': '//c.com/get_img',
               // 'checkcode': '//d.com/captcha/checkcode.jsonp',
               // 'umid_Url': '//e.com/security/umscript/3.2.1/um.js',
               // 'uab_Url': '//aeu.alicdn.com/js/uac/909.js',
               // 'umid_serUrl': 'https://g.com/service/um.json'
           },   
           callback: function (data) { 
               window.console && console.log(nc_token)
               window.console && console.log(data.csessionid)
               window.console && console.log(data.sig)
               
               console.log(nc_token);
        	   console.log(data.csessionid);
        	   console.log(data.sig);
               
               $('#sessionid').val(data.csessionid);
               $('#sig').val(data.sig);
               $('#token').val(nc_token);
               $('#scene').val(scene);
               $('#appkey').val(appkey);
           }
       }
       var nc = new noCaptcha(NC_Opt)
       nc.upLang('cn', {
           _startTEXT: language["aliyun.nc.startTEXT"],
           _yesTEXT: language["aliyun.nc.yesTEXT"],
           _error300: language["aliyun.nc.error300"],
           _errorNetwork: language["aliyun.nc.errorNetwork"],
       })
</script>
