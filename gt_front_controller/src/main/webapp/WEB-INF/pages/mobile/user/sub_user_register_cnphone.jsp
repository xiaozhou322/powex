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
<meta name = "viewport" content = "width = device-width, user-scalable = no,initial-scale=0.5,maximum-scale=0.5,minimum-scale=0.5">

<%@include file="../comm/link.inc.jsp" %>


  <title><spring:message code="m.security.reg" /></title>
<style type="text/css">
      html,body{height:100%;}
       #SM_TXT_1{
      	    font-size: 0.24rem;
      }
      
</style>
</head>
<body >
<div class="warp regWarp"> 
<input type="hidden" value="0" id="regType">
  <input type="hidden" value="${intro }" id="register-intro">   
    <div class="regMain">
        <h2 class="tit cblue2">
            <spring:message code="m.security.iphonereg" /> <p class='cred'><i class="fl icon_ts sfont50 iconfont icon-weibiaoti2"></i>&nbsp;<spring:message code="newmobile.after" /></p>
        </h2>
        <div class="login_inp">
            <input class="form-control login-ipt" id="register-areaCode" value="86" type="hidden" /><span id="register-phone-areacode" class="btn btn-areacode register-areacode" style="display: none;">86</span>
            <span class="s_1">+86</span>
            <input class="txt txt1" type="text" name="" id="register-phone" placeholder="<spring:message code="nav.index.username.info.cn" />" autocomplete="off">
        </div>
        <%-- <div class="login_inp">
            <span class="yzCode"><img src="/servlet/ValidateImageServlet?r=<%=new java.util.Date().getTime() %>"  class="dynamicCode  btn-imgcode" /></span>
            <input class="txt" type="text" name="" id="register-imgcode" placeholder="<spring:message code="security.verifycode" />" autocomplete="off">
        </div> --%>            
        <div class="login_inp">
            <span class="sendCode btn-sendmsg register-msg" id="register-sendmessage" data-msgtype="12" data-tipsid="register-errortips" ><spring:message code="financial.send" /></span>
            <input class="txt" type="text" name="" placeholder="<spring:message code="security.verifycode" />" autocomplete="off"  id="register-msgcode">
        </div>            
        <div class="login_inp">
            <input class="txt" type="password" name="" id="register-password" placeholder="<spring:message code="security.setpassword" />" autocomplete="off" >
        </div>            
        <div class="login_inp">
            <input class="txt" type="password" id="register-confirmpassword" name="" placeholder="<spring:message code="security.repeatpassword" />" autocomplete="off" >
        </div> 
        
        <div  class="login_inp" id="register-validate-phone"></div>
        <input type='hidden' id='sessionid' name='sessionid'/>
	    <input type='hidden' id='sig' name='sig'/>
	    <input type='hidden' id='token' name='token'/>
	    <input type='hidden' id='scene' name='scene'/>
	    <input type='hidden' id='appkey' name='appkey'/>  
	             
        <div class="login_inp reg_ts clear">
             <input id="agree" type="checkbox" checked="checked" class="reg_checkbox" /><spring:message code="security.icertify" /><a class="user_xieyi cblue2" href="/about/index.html?id=4"><spring:message code="security.useragree" /></a>
            <a href="/user/register.html?regtype=email" class="fr reg_email cblue2"><spring:message code="m.security.emailreg" /></a>
        </div>
        <div class="btns">
            <input  id="register-submit" type="submit" value="<spring:message code="nav.top.register" />" class="btn">
        </div>
        <p class="hasNum">
            <a class="cblue" href="/user/login.html"><spring:message code="m.security.gotologin" /><spring:message code="nav.top.login" /> →</a>
        </p>
    </div>
</div> 
<%@include file="../comm/tabbar.jsp"%>

<%@include file="../comm/footer.jsp" %>	

	<input id="regType" type="hidden" value="0">
	<input id="intro_user" type="hidden" value="${intro }">
  
	<script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/mobile2018/js/msg.js?v=3"></script>
	<script type="text/javascript" src="${oss_url}/static/mobile2018/js/register.js?v=7"></script>
	<script type="text/javascript" src="${oss_url}/static/mobile2018/js/language/language_<spring:message code="language.title" />.js?v=20171025221650.js"></script>
<!--   <script type="text/javascript" src="${oss_url}/static/front/js/comm/msg.js?v=2"></script>
  <script type="text/javascript" src="${oss_url}/static/front/js/user/register.js?v=4"></script> -->
  <script src="//g.alicdn.com/sd/smartCaptcha/0.0.1/index.js?v=20181126201750"></script>
  <script type="text/javascript">
    function previousPage(){
      window.history.go(-1);
    }
</script>

<script>
	var appkey = "FFFF00000000017F11B9";
	var scene = "ic_register_h5";

    window.NVC_Opt = {
        appkey:appkey,
        scene:scene,
        renderTo:'#register-validate-phone',
        trans: {"key1": "code0", "nvcCode":200},
        elements: [
            '//img.alicdn.com/tfs/TB17cwllsLJ8KJjy0FnXXcFDpXa-50-74.png',
            '//img.alicdn.com/tfs/TB17cwllsLJ8KJjy0FnXXcFDpXa-50-74.png'
        ], 
        bg_back_prepared: '//img.alicdn.com/tps/TB1skE5SFXXXXb3XXXXXXXXXXXX-100-80.png',
        bg_front: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAGQAAABQCAMAAADY1yDdAAAABGdBTUEAALGPC/xhBQAAAAFzUkdCAK7OHOkAAAADUExURefk5w+ruswAAAAfSURBVFjD7cExAQAAAMKg9U9tCU+gAAAAAAAAAIC3AR+QAAFPlUGoAAAAAElFTkSuQmCC',
        obj_ok: '//img.alicdn.com/tfs/TB1rmyTltfJ8KJjy0FeXXXKEXXa-50-74.png',
        bg_back_pass: '//img.alicdn.com/tfs/TB1KDxCSVXXXXasXFXXXXXXXXXX-100-80.png',
        obj_error: '//img.alicdn.com/tfs/TB1q9yTltfJ8KJjy0FeXXXKEXXa-50-74.png',
        bg_back_fail: '//img.alicdn.com/tfs/TB1w2oOSFXXXXb4XpXXXXXXXXXX-100-80.png',
        upLang:{"cn":{
        	_ggk_guide: language["aliyun.nvc.ggk_guide"],
            _ggk_success: language["aliyun.nvc.ggk_success"],
            _ggk_loading: language["aliyun.nvc.ggk_loading"],
            _ggk_fail: language["aliyun.nvc.ggk_fail"],
            _ggk_action_timeout: language["aliyun.nvc.ggk_action_timeout"],
            _ggk_net_err: language["aliyun.nvc.ggk_net_err"],
            _ggk_too_fast: language["aliyun.nvc.ggk_too_fast"]
            }
        }
    }
  </script>

  <script src="//g.alicdn.com/sd/nvc/1.1.112/guide.js?v=20181126201750"></script>
  <script>
    window.onload = function(){
      var ic = new smartCaptcha({
        renderTo: '#register-validate-phone',
        width: 572,
        height: 50,
        default_txt: language["aliyun.ic.default_txt"],
        success_txt: language["aliyun.ic.success_txt"],
        fail_txt: language["aliyun.ic.fail_txt"],
        scaning_txt: language["aliyun.ic.scaning_txt"],
        success: function(data) {
          console.log(NVC_Opt.token)
          console.log(data.sessionId);
          console.log(data.sig);
          
          $('#sessionid').val(data.sessionId);
          $('#sig').val(data.sig);
          $('#token').val(NVC_Opt.token);
          $('#scene').val(scene);
          $('#appkey').val(appkey);
        },
      });
      ic.init();
    }

  </script>
</body>
</html>
