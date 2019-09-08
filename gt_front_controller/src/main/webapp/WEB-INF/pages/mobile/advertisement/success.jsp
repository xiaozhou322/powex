<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>

<!doctype html>
<html>
<head>
<%@ include file="../comm/basepath.jsp"%>
<jsp:include page="../comm/link.inc.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${oss_url}/static/front/css/index/main.css" />

<script type="text/javascript">
$(function(){
	setTimeout(function(){
		window.location.href="/advertisement/advertisementList.html"; 
		
	},3000);
})


</script>
<body>
<%@include file="../comm/newotc_header.jsp" %>
<div style="background-color: #333;font-size:20px;  text-align: center;  padding: 50px;">
	${massage }&nbsp;&nbsp;&nbsp;&nbsp;
	<a href="/advertisement/advertisementList.html">返回广告列表</a>
	</div>
	<%@include file="../comm/footer.jsp" %>	

</body>
</html>
