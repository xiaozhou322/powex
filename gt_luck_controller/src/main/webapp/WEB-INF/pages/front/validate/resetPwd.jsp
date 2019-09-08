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
<%@include file="../comm/link.inc.jsp" %>
</head>
<body class="l_body">
 
<div class="formMain">
    <div class="fixed"> 
    <%@include file="../comm/header.jsp" %> 
    </div>
    <section>
        <div class="loginMain repeatPwd mg">
            <div class="loginL" id="secondstep">
                <h2><spring:message code="security.resetpassword" /></h2>
                <div class="txt_tr">
                    <label for=""><input id="reset-newpass" class="tr_inp" type="password" placeholder="<spring:message code="security.setpassword" />" autocomplete="off"/></label>
                </div>
                <div class="txt_tr">
                    <label for=""><input class="tr_inp" type="password" id="reset-confirmpass" placeholder="<spring:message code="security.repeatpassword" />" autocomplete="off"/></label>
                </div>
                  <div class="txt_tr" >
                    <span class="text-danger" id="reset-success-errortips"></span>
                  </div>
                <div class="txtBtn mgt50">
                    <span class="btn" id="btn-email-success"><spring:message code="security.reset" /></span>
                </div>
            </div>
        
            <div class="pwdSuc" style="display:none;" id="successstep">
                <img src="${oss_url}/static/front2018/images/suc.png" alt="" />
                <p><spring:message code="security.con" /></p>
                <div class="txtBtn mgt50">
                        <a href="/user/login.html" class="btn"><spring:message code="nav.top.login" /></a>
                </div>
            </div>

        </div>
        


    </section>
</div>
<p class="CopyRight" style="margin:0;">CopyRight© 2013-2017 GBCAX.com All Rights Reserved</p>

<input type="hidden" id="fid" value="${fuser.fid}"/>
<input type="hidden" id="ev_id" value="${emailvalidate.fid }"/>
<input type="hidden" id="newuuid" value="${emailvalidate.fNewUUid }"/>
<input type="hidden" id="mtype" value="0"/>

<script type="text/javascript" src="${oss_url}/static/front2018/js/comm/msg.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/user/reset.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/bootstrap.js?v=20171025221650.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/layer/layer.js?v=20171025221650.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/comm/util.js?v=20171025221650.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/comm/comm.js?v=20171025221650.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/language/language_<spring:message code="language.title" />.js?v=20171025221650.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/main/main.js?v=4"></script>

</body>
</html>
