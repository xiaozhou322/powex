<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<%@include file="../comm/link.inc.jsp" %>
<link href="${oss_url}/static/front/css/user/common.css?v=20181126201750" rel="stylesheet" type="text/css" />
<link href="${oss_url}/static/front/css/user/main.css?v=20181126201750" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${oss_url}/static/front/js/user/jquery-1.11.2.min.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/user/jquery.SuperSlide.2.1.js?v=20181126201750"></script>
</head>
<body>
<%@include file="../comm/header.jsp" %>

	<div class=" lw-content">
		<div class="lw-finance">
<%@include file="../comm/left_menu.jsp" %>

			  <div class="lw-financeRight security-right fr">
             <h1 class="lw-financeTit">Setting Google Authentication</h1>
             <div>
                <p class="google_warn google_warn_trade"><img src="${oss_url}/static/front/images/user/warn.png"> <span style="font-size: 14px;">Your account has not enabled GA</span><br>
                  <font>Google Authentication can ensure the safety better,but there is no effect on trading if closed.</font><font>After enable Google Authentication,please input a one-time password shown on the mobile application.</font><font> Please set as follow.</font> </p>
                <p class="google_list"><span>1</span>Install Google Authenticator</p>
                <p class="google_list_p1">IPhone: Install Google Authenticator<a target="_blank" href="http://itunes.apple.com/cn/app/google-authenticator/id388497605?mt=8"><span>Download</span></a></p>
                <p class="google_list_p1" style=" background:none;">Android: Search "Google authenticator" in Google Play<a target="_blank" href="http://apk.hiapk.com/html/2013/07/1643619.html"><span>Download</span></a></p>
                <p class="google_list"><span>2</span>After installing, please set as follow</p>
                <dl class="google_list_dl1">
                  <dd>Click"Add account(+ in IOS)" in “Google Authenticator”,then "Scan the QRcode” Please scan the QRcode </dd>
                  <dt id="qrcode"><img src="${oss_url}/static/front/images/user/code.png" width="144"></dt>
                  <dd>If you can't scan the QRcode,you can also enter the following key manually to add the account: <span id="unbindgoogle-key" ></span></dd>
                </dl>
                <p class="google_list"><span>3</span>Setting Completed</p>
                <dl class="google_list_dl1">
                  <dd> After setting, it will show a 6-digit on the phone, which changes every 30 seconds. This numbers are your GA code </dd>
                  <dd style="padding-top:12px;"> Don’t delete GA APP, otherwise you will be unable to operate the account </dd>
                  <dd style=" padding-top:12px;">You can save the key: <span id="unbindgoogle-key" ></span> If you accidentally deleted GA APP, it can be restored by manually entering the key </dd>
                  <dd style="padding-top:12px;"> Enter GA code to turn on or turn off this function </dd>
                </dl>
                 <dl class="google_list_dl2">
                  <!-- <input type="hidden" name="secret" value="CK25ZI4SV2FMI7SL"> -->
                  <dd>
                    <input id="unbindgoogle-topcode" type="text" name="ga" placeholder="GA code:" style="margin-top: 9px;">
                  </dd>
                  <dt>Please enter the code shown on GA APP</dt>
                 </dl>
                <p id="unbindgoogle-Btn" class="google_btn">Verify &amp; Enable</p>
            </div>
       </div>
		</div>
	</div>
	

  <input type="hidden" id="isEmptygoogle" value="${isBindGoogle==true?1:0 }" />
  <input id="messageType" type="hidden" value="0" />
<%@include file="../comm/footer.jsp" %>	

	<script type="text/javascript" src="${oss_url}/static/front/js/comm/msg.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/api/api.js?v=20181126201750"></script>
</body>
</html>
