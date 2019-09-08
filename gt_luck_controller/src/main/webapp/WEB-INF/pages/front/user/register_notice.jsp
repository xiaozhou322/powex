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
    <link rel="stylesheet" href="${oss_url}/static/front/css/user/login.css?v=20181126201750" type="text/css"></link>
    <script type="text/javascript">
        window.onload = function() {
            document.getElementById("allheader").style.backgroundColor="#363636";
        }
    </script>
</head>
<body >


<%@include file="../comm/header.jsp" %>


<img src="../static/front/images/user/regist_notice.png" style="width: 940px;margin-top: 60px;margin-left: 16%;"/>




<%@include file="../comm/footer.jsp" %>

<input id="regType" type="hidden" value="0">
<input id="intro_user" type="hidden" value="${intro }">
<script type="text/javascript" src="${oss_url}/static/front/js/comm/msg.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/user/register.js?v=20181126201750"></script>
</body>
</html>