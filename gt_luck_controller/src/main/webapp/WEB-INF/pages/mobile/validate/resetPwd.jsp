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

 <title><spring:message code="m.security.setpass" /></title>
</head>
<body>
<div class="findPwdWarp">
    <div class="stepsCon">

        <div class="steps2" id="secondstep">
            <h1><spring:message code="m.security.setpass" /></h1>
            <div class="txt">
                <input type="password" id="reset-newpass" placeholder="<spring:message code="security.setpassword" />" autocomplete="off" class="ip_txt" />
                <input type="password" id="reset-confirmpass" placeholder="<spring:message code="security.repeatpassword" />" autocomplete="off" class="ip_txt" />
            </div>
            <button id="btn-email-success"><spring:message code="security.reset" /></button>
        </div>
        <div class="steps3" style="display: none" id="successstep">
            <img src="${oss_url}/static/mobile2018/images/step3.png" alt="" />
            <p><spring:message code="security.con" /></p>
            <a class="button" href="/user/login.html"><spring:message code="m.security.go" /></a>
        </div>
    </div>
</div>

<input type="hidden" id="fid" value="${fuser.fid}"/>
<input type="hidden" id="ev_id" value="${emailvalidate.fid }"/>
<input type="hidden" id="newuuid" value="${emailvalidate.fNewUUid }"/>
<input type="hidden" id="mtype" value="0"/>
<%@include file="../comm/footer.jsp" %>	
	<script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/mobile2018/js/msg.js?v=1"></script>
	<script type="text/javascript" src="${oss_url}/static/mobile2018/js/reset.js?v=2"></script>
  <script type="text/javascript">
    function previousPage(){
      window.history.go(-1);
    }
</script>
</body>
</html>
