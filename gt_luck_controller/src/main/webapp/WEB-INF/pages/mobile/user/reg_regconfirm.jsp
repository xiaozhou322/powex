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
<body>

<header class="tradeTop">  
    <i class="back toback2" onclick="previousPage();"></i>
    <h2 class="tit">激活邮箱</h2>
</header>

<%@include file="../comm/header.jsp" %>
<section class="section regAgain">
	<div class="container-full">
		<div class="container text-center validate">
			<div class="col-xs-12 clearfix padding-clear text-center">
				<div class="validate-online" style='text-align:center; padding:0.8rem 0.3rem 0;'>
					
						<c:if test="${validate == false }">
					<!-- 		<span class="validate-success failure">
								<span class="validate-text failure"><spring:message code="register.actfail" /></span>
							</span> -->
							<div class="validate-success failure" style="margin-bottom:0.8rem;">
							    <img src="${oss_url}/static/mobile/images/emailPic.png" width="260" alt="" />
							    <p class="validate-text failure"><spring:message code="register.actfail" /></p>
							</div> 
						</c:if>
						<c:if test="${validate == true }">
							<div class="validate-success"  style="margin-bottom:0.8rem;">
							    <img src="${oss_url}/static/mobile/images/emailSuc.png" width="260" alt="" />
								<p class="validate-text"><spring:message code="register.accactsuc" /></p>
							</div>
						</c:if>	
					
					<div class="form-group textCenter" style="padding:0 0.3rem 0.2rem;">
						<a class="btn regAgainBtn" style="color:#fff;" href="/index.html"><spring:message code="nav.top.home" /></a>
					</div>
					<div class="form-group textCenter"style="padding:0 0.3rem 0.2rem;">
						<a class="btn regAgainBtn" style="color:#fff;" href="/user/login.html"><spring:message code="nav.top.login" /></a>
					</div>
				
<!-- 					<div class="form-group">
						<a class="btn regAgainBtn cblue" href="/user/login.html"><spring:message code="nav.top.login" /></a>
					</div> -->
				</div>
			</div>
		</div>
	</div>
</section>

<%@include file="../comm/footer.jsp" %>	

<script type="text/javascript">
    function previousPage(){
      window.history.go(-1);
    }
</script>


</body>
</html>
