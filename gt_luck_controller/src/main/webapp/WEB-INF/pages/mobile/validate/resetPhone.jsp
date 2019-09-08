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
<meta name = "viewport" content = "width = device-width, user-scalable = no,initial-scale=0.5,maximum-scale=0.5,minimum-scale=0.5">
<%@include file="../comm/link.inc.jsp" %>
    <title><spring:message code="security.resetpassword" /></title>
    <style type="text/css">
      html,body{height:100%;}

</style>
</head>
<body>
<div class="warp regWarp">  
    <div class="regMain">
        <h2 class="tit cblue2">
            <spring:message code="m.security.iphonez" /> <p class='cred'><i class="fl icon_ts sfont50 iconfont icon-weibiaoti2"></i>&nbsp;
            <spring:message code="newmobile.after" /></p>
        </h2>
        <div class="login_inp">
            
            <input class="txt txt1" type="text" name="" id="reset-phone" placeholder="<spring:message code="nav.index.username.info.cn" /> " autocomplete="off" >
        </div>
        <div class="login_inp">
            <span class="yzCode"><img src="/servlet/ValidateImageServlet?r=1473326749976"  class="dynamicCode btn-imgcode"/></span>
            <input class="txt" type="text" name="" id="reset-imgcode" placeholder="<spring:message code="security.verifycode" />" autocomplete="off">
        </div>            
        <div class="login_inp">       
            <input class="txt" type="text" name="" id="reset-msgcode" placeholder="<spring:message code="security.verifycode" />" autocomplete="off" >
            <span class="sendCode btn-sendmsg" id="reset-sendmessage" data-msgtype="9" data-tipsid="reset-errortips"><spring:message code="financial.send" /></span>
        </div>            
        <div class="login_inp">
            <span onclick="showtabs('reset-idcard_box')" id="reset-idcards"><spring:message code="security.idcard" /></span>
        </div>
         <select  id="reset-idcard" class="reg_width select3 select6 sl1" style="display:none;">
                    <option value="1"><spring:message code="security.idcard" /></option>
        </select>    
        <div class="login_inp">
            <input class="txt" type="text" name="" id="reset-idcardno" placeholder="<spring:message code="security.idnumber" />" autocomplete="off" >
        </div>        
        <div class="login_inp">
            <a href="/validate/resetEmail.html" class="cblue2 fr"><spring:message code="m.security.emailz" /></a>
        </div>
        <div class="btns">
            <input id="btn-next" type="submit" value="<spring:message code="security.resetpassword" />" class="btn">
        </div>
        <p class="hasNum">
            <a class="cblue" href="/user/login.html"><spring:message code="newmobile.direct" /> →</a>
        </p>
    </div>
</div>

<div class="slet"></div>

<%@include file="../comm/footer.jsp" %>	
	<script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/mobile2018/js/msg.js?v=2"></script>
	<script type="text/javascript" src="${oss_url}/static/mobile2018/js/reset.js?v=1"></script>
</body>

</html>
