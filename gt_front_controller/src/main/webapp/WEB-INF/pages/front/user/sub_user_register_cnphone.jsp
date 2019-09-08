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
<%@include file="../comm/link.inc.jsp" %>
<style type="text/css">
	 .pop_frame {
				background:url(/static/front2018/images/panel@2x.png) no-repeat 100% 100%;
				 width:434px;
				 height:290px;
				 background-size:100%;
				 position:absolute;
				 top:30%;
				 left:40%;
				 text-align:center;
				 z-index:2;
			}
			
			.btn_approve {
				background:url(${oss_url}/static/front2018/images/b2x.png) no-repeat 100% 100%;
				background-size:100%;
				width:234px;
				height:70px;
				margin:auto;
				cursor:pointer;
			}
			
			.frame_close {
				float:right;
				position:relative;
				top:20px;
				right:24px;
				cursor:pointer;
			}
			
			.pop_frame div:nth-child(2) {
				 height:45px;
				 font-size:18px;
				 font-family:Adobe Heiti Std R;
				 font-weight:normal;
				 color:rgba(255, 48, 48, 1);
				 line-height:37px;
				 padding-top:66px;
			}
			
			.pop_frame div:nth-child(3) {
				height:56px;
				font-size:17px;
				font-family:Adobe Heiti Std R;
				font-weight:normal;
				color:#3F3F3F;
				margin-top:40px;
			}
			
			.pop_frame div:nth-child(3)>span {
				color:#4672FF;
			}
			
			.btn_approve>span {
				position:relative;
				top:19px;
				width:97px;
				height:23px;
				font-size:22px;
				font-family:Adobe Heiti Std R;
				font-weight:normal;
				color:rgba(255, 255, 255, 1);
			}
			.pop_body{
			  	 position:fixed;
				 top:0;
				 left:0;
				 right:0;
				 bottom:0;
				 background:#2d2d2d;
				 opacity:0.6;
				 z-index:1;
			}
			#invite_code{margin-right:15px}
			#SM_TXT_1{color:#fff !important;}
</style>
</head>
<body class="l_body login_body">
    <div class="fixed">
      <%@include file="../comm/header.jsp" %>
    </div>
    <div class="formMain">
        <div class="loginMain regMain clear">
            <div class="loginL fl">
                <h2 class="clear">
                    <span><spring:message code="new.reg.title" /></span>
                    <a class="fr otherReg" href="/user/register.html?regtype=email"><spring:message code="security.regbyemail" /></a>
                </h2>
                <!--<div class="btnTab clear">
                     <a href="/user/register.html?phone=cn" class="active"><spring:message code="security.regbyphone" /></a> -->
                    <!-- <a class="fr" href="/user/register.html"><spring:message code="security.regbyemail" /></a> 
                </div>-->
                <div class="txt_tr">
                    <!-- <p class="tit">手机号码</p> -->
                    <input class="form-control login-ipt" id="register-areaCode" value="86" type="hidden" />
                    <span id="register-phone-areacode" class="btn btn-areacode register-areacode" style="display: none">+86</span>
                    <label for=""  class="label"><input id="register-phone" class="tr_inp" type="text" placeholder="<spring:message code="nav.index.username.info.cn" />" autocomplete="off"/></label>
                </div>                
<%--                 <div class="txt_tr">
                    <!-- <p class="tit">验证码</p> -->
                    <label for=""  class="label"><input class="tr_inp" id="register-imgcode" type="text" placeholder="<spring:message code="security.verifycode" />" /><img class="dyCode btn-imgcode" src="/servlet/ValidateImageServlet?r=<%=new java.util.Date().getTime() %>"></img></label>
                </div> --%>
                <div class="txt_tr">
                    <!-- <p class="tit">短信验证码</p> -->
                    <label for="" class="label fl"><input class="tr_inp" id="register-msgcode" type="text" placeholder="<spring:message code="security.verifycode" />" /><button class="getCode btn-sendmsg register-msg" id="register-sendmessage" data-msgtype="12" data-tipsid="register-errortips"><spring:message code="financial.send" /></button></label>
                </div>                
                <div class="txt_tr">
                    <!-- <p class="tit">登录密码</p> -->
                    <label for=""><input id="register-password" class="tr_inp" type="password" placeholder="<spring:message code="security.setpassword" />" /></label>
                </div>                
                <div class="txt_tr">
                    <!-- <p class="tit">确认密码</p> -->
                    <label for=""><input id="register-confirmpassword" class="tr_inp" type="password" placeholder="<spring:message code="security.repeatpassword" />" /></label>
                </div>
                 <div class="txt_tr">
                      <label for=""><input class="tr_inp" id="invite_code" type="text" placeholder="<spring:message code="security.invite.code" />" autocomplete="off"  value="${invite_code}" <c:if test="${intro!=null}"> readonly="readonly" disabled="disabled" </c:if>/><spring:message code="security.choose" /></label>
               </div>
                <div class="txt_tr" id="register-validate-phone" style="margin-bottom: 20px"></div>
	            <!-- <p id="notice2" style="color: red; display: none;">请先完成验证</p> -->
	  			<input type='hidden' id='sessionid' name='sessionid'/>
			    <input type='hidden' id='sig' name='sig'/>
			    <input type='hidden' id='token' name='token'/>
			    <input type='hidden' id='scene' name='scene'/>
			    <input type='hidden' id='appkey' name='appkey'/>
             <div class="tit">
                 <input type="checkbox" id="agree" checked="checked"/> <spring:message code="new.reg.agree" /><a href="/about/index.html?id=4" class="lightBlue">《<spring:message code="security.useragree" />》</a>
             </div>
             <div class="txt_tr ">
             <p id="register-errortips" class="cred mgt15"></p>
           	</div>
             <div class="txtBtn mgt20">
                 <input class="btn reg_createBtn" id="register-submit" value="<spring:message code="nav.top.register" />" type="button">
                 <span class="forgetPwd"><spring:message code="security.already" />？<a href="/user/login.html" class="lightBlue a_hover"><spring:message code="nav.top.login" /></a></span>
             </div>
            </div>
        	<div style="margin: 120px 0 0 28px;float: left;letter-spacing: 1px;line-height: 26px;width: 350px;">
                <p>
                   <spring:message code="new.reg.remind" />
                </p>
            </div> 
        </div>
         
    </div>
			 <div class="pop_frame" hidden="hidden">
				<div class="frame_close"><img src="${oss_url}/static/front2018/images/close@2x.png"></div>
				<div>您好，恭喜您注册成功</div>
				<div>立即前往<span>KYC1认证</span>获赠POW赢取平台大奖</div>
				<div class="btn_approve"><span>立即认证</span></div>
	</div>       
</div>
	<div class="pop_body" hidden="hidden"></div>
<p class="CopyRight" style="margin:0;">CopyRight© 2013-2018 POWEX.PRO All Rights Reserved</p>


  <input type="hidden" value="0" id="regType">
  <input type="hidden" value="86" id="register-phone-areacode">

  <input type="hidden" value="${intro }" id="register-intro"> 
	<%-- <script type="text/javascript" src="${oss_url}/static/front2018/js/comm/msg.js?v=20181126201750"></script> --%>
	<script type="text/javascript" src="${oss_url}/static/front2018/js/user/register.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/comm/msg.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/bootstrap.js?v=20171025221650.js"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/layer/layer.js?v=20171025221650.js"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/comm/util.js?v=20171025221650.js"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/comm/comm.js?v=20171025221650.js"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/language/language_<spring:message code="language.title" />.js?v=20171025221650.js"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/main/main.js?v=4"></script>
<script src="//g.alicdn.com/sd/smartCaptcha/0.0.1/index.js?v=20181126201750"></script>

</body>
</html>
<script type="text/javascript">
var appkey = "FFFF0N00000000006DE5";
var scene = "ic_register";
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
    width: 522,
    height: 32,
    default_txt: language["aliyun.ic.default_txt"],
    success_txt: language["aliyun.ic.success_txt"],
    fail_txt: language["aliyun.ic.fail_txt"],
    scaning_txt: language["aliyun.ic.scaning_txt"],
    success: function(data) {
      console.log("data="+data);
      console.log("token="+NVC_Opt.token);
      console.log("sessionId="+data.sessionId);
      console.log("sig="+data.sig);
      
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

