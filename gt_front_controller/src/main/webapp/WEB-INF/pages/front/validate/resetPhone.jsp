<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%

%>

<!doctype html>
<html>
<head> 
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp" %>

</head>
<body class="l_body">
    <div class="fixed">
      <%@include file="../comm/header.jsp" %> 
    </div>
<div class="formMain">

    <section>
        <div class="loginMain regMain mg clear">
            <div class="loginL fl">
                <h2 class="clear">
                <span><spring:message code="security.resetpassword" /></span>
                <a class="otherReg fr" href="validate/resetEmail.html"><spring:message code="security.retrievebyemail" /></a>
                </h2>
<!--                 <div class="btnTab">
                    <a href="validate/resetPhone.html" class="active"><spring:message code="security.retrievebyphone" /></a>
                    
                </div> -->
                <div class="txt_tr">
                    <select class="tr_inp" name="" id="reset-areaCode">
                         <option value="86"><spring:message code="security.area.chinamainland" /></option>          
                     </select>
                </div>                
                <div class="txt_tr">
                  <span id="reset-phone-areacode" class="btn btn-areacode" style="height:38px; line-height:28px; left:16px; top:1px;display:none;">+86</span>
                  <label for=""  class="label"><input id="reset-phone" class="tr_inp" type="text" placeholder="<spring:message code="nav.index.username.info.cn" />" autocomplete="off"/></label>
                </div>
                             
                <div class="txt_tr">
                 <label for=""  class="label"><input id="reset-imgcode" class="tr_inp" type="text" placeholder="<spring:message code="security.verifycode" />" autocomplete="off"/><img class="dyCode btn-imgcode" src="/servlet/ValidateImageServlet?r=1473326749976" ></img></label>
                </div>
               <div class="txt_tr">
                   <label for="" class="label"><input id="reset-msgcode" class="tr_inp" type="text" placeholder="<spring:message code="security.verifycode" />" autocomplete="off"/><button class="getCode btn-sendmsg" id="reset-sendmessage" data-msgtype="9" data-tipsid="reset-errortips"><spring:message code="financial.send" /></button></label>
                </div>    
                <div class="txt_tr">
                  <select class="tr_inp" name="" id="reset-idcard">
                         <option value="1"><spring:message code="security.idcard" /></option>          
                     </select>
                </div>                
                <div class="txt_tr">
                   <label for=""><input id="reset-idcardno" class="tr_inp" type="text" autocomplete="off" placeholder="<spring:message code="security.idnumber" />" /></label>
                </div>
                <div class="txt_tr">
        					<span id="reset-errortips" class="cred"></span>
        				</div>
                <div class="txtBtn mgt20">
                    <span class="btn btn-danger" id="btn-next"><spring:message code="security.resetpassword" /></span>
                </div>
            </div>
            <div class="loginR fl">
                <p>
                   <spring:message code="new.repeatPwd.remind" />
                </p>
            </div>
        </div>
    </section>
</div>
<p class="CopyRight" style="margin:0;">CopyRight© 2013-2017 GBCAX.com All Rights Reserved</p>

	



	<script type="text/javascript" src="${oss_url}/static/front2018/js/comm/msg.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/front2018/js/user/reset.js?v=20181126201750"></script>

<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/bootstrap.js?v=20171025221650.js"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/layer/layer.js?v=20171025221650.js"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/comm/util.js?v=20171025221650.js"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/comm/comm.js?v=20171025221650.js"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/language/language_<spring:message code="language.title" />.js?v=20171025221650.js"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/main/main.js?v=4"></script>

</body>
</html>
