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

</head>
<body class="l_body">
 <div class="formMain">
    <div class="fixed">
      <%@include file="../comm/header.jsp" %>
    </div>
    <section>
        <div class="loginMain regMain mg clear">
            <div class="loginL fl">
                <h2 class="clear">
                    <span><spring:message code="security.resetpassword" /></span>
                    <a class="otherReg fr" href="validate/resetPhone.html"><spring:message code="security.retrievebyphone" /></a>
                </h2>
<!--                 <div class="btnTab">
                    <a href="validate/resetPhone.html"><spring:message code="security.retrievebyphone" /></a>
                    
                </div> -->
                
                <div class="txt_tr">
                    <label for=""  class="label"><input id="reset-email" class="tr_inp" type="email" placeholder="<spring:message code="security.email" />" autocomplete="off" /></label>
                </div>
               
                <div class="txt_tr">
                    <select class="tr_inp" name="" id="reset-idcard">
                         <option value="1"><spring:message code="security.idcard" /></option>          
                     </select>
                </div>                
                <div class="txt_tr">            
                    <label for=""><input id="reset-idcardno" class="tr_inp" type="text" placeholder="<spring:message code="security.idnumber" />" autocomplete="off" /></label>
                </div>

                <div class="txt_tr">
                    <label for=""  class="label"><input id="reset-imgcode" class="tr_inp" type="text" placeholder="<spring:message code="security.verifycode" />" autocomplete="off"/><img class="dyCode btn-imgcode" src="/servlet/ValidateImageServlet?r=1473326869382"></img></label>
                </div>
                <div class="txt_tr">
                    <span id="reset-errortips" class="cred"></span>
                  </div>
                <div class="txtBtn mgt20">
                    <span class="btn repeatBtn" id="btn-email-next"><spring:message code="security.resetpassword" /></span>
                    
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
<script type="text/javascript" src="${oss_url}/static/front2018/js/user/reset.js?v=2"></script>

<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/bootstrap.js?v=20171025221650.js"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/layer/layer.js?v=20171025221650.js"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/comm/util.js?v=20171025221650.js"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/comm/comm.js?v=20171025221650.js"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/language/language_<spring:message code="language.title" />.js?v=20171025221650.js"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/main/main.js?v=4"></script>

</body>
</html>
