<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%

%>

<!doctype html>
<html>
<head> 
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /><meta name = "viewport" content = "width = device-width, user-scalable = no,initial-scale=0.5,maximum-scale=0.5,minimum-scale=0.5">
<%@include file="../comm/link.inc.jsp" %>

 <title><spring:message code="m.security.setpass" /></title>


</head>
<body>
 <div class="findPwdWarp" >
    <div class="stepsCon">
        <div class="steps2"  id="secondstep">
            <h1><spring:message code="m.security.setpass" /></h1>
            <div class="txt">
                <input type="password" id="reset-newpass" placeholder="<spring:message code="security.setpassword" />" autocomplete="off" class="ip_txt" />
                <input type="password" id="reset-confirmpass" placeholder="<spring:message code="security.repeatpassword" />" autocomplete="off" class="ip_txt" />
            </div>
            <button class="btn mtop" id="btn-email-success"><spring:message code="security.reset" /></button>
        </div>
        <div class="steps3" id="successstep" style="display: none">
            <img src="${oss_url}/static/mobile2018/images/step3.png" alt="" />
            <p><spring:message code="security.con" /></p>
            <a class="button" href="/user/login.html"><spring:message code="m.security.go" /></a>
        </div>
    </div>
</div>


<input type="hidden" id="fid" value="${fuser.fid}"/>
<input type="hidden" id="ev_id" value="0"/>
<input type="hidden" id="newuuid" value="0"/>
<input type="hidden" id="mtype" value="1"/>
<%@include file="../comm/tabbar.jsp"%>
<%@include file="../comm/footer.jsp" %>	
	<script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/mobile2018/js/msg.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/mobile2018/js/reset.js?v=20181126201750"></script>

</body>
</html>
