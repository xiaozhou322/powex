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
<link href="${oss_url}/static/front/css/index/main.css?v=2" rel="stylesheet" type="text/css" />
<link href="${oss_url}/static/front2018/css/lucky.css?v=20181126201750" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${oss_url}/static/front2018/js/index/main.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/layer/layer.js?v=20181126201750"></script>
<style type="text/css">
  .lw-helpCons{background:#ffffff;}
  .lw-heilpTabs{background:#f8f8f8;}
  .lw-helpRight{background:#f8f8f8; border:none;}
  .lw-heilpTabs ul li{border: 1px solid #fff;}
  .lw-helpRight h2{color:#333; font-weight:600;}
  .lw-helpNav{height: 80px; line-height:80px; background: #f8f8f8;border-bottom: 1px solid #e3e3e3;}
  .lw-helpNav a{color:#6f6f6f;}
  .lw-helpNav a:hover{background:none; color:#2269d4;}
  .lw-helpNav a.lw-active{background:#fff; color:#6f6f6f; border-bottom: 1px solid #2269d4;}
</style>

<script type="text/javascript">

$(function() {
	<c:if test="${pageContext.response.locale ne 'zh_CN' }">
	     $(".platTerrace li").find("p").css("height","300px");
	     $(".platUser li").find("p").css("height","300px");
	     
	</c:if>
	
});


</script>
</head>
<body style="background: #F9FBFF;">
<%@include file="../comm/header.jsp" %>
<section class="l-content" >
<div class="platformTop">
	<h3>POWEX.PRO</h3>  
	<h4><span><spring:message code="json.planfrom.banner" /></span></h4>  
	<ul>
		<li>
			<label><img src="${oss_url}/static/front2018/images/exchange/Professional2x.png"></label>
			<span><spring:message code="json.planfrom.banner1" /></span>
		</li>
		<li>
			<label><img src="${oss_url}/static/front2018/images/exchange/Efficientplat2x.png"></label>
			<span><spring:message code="json.planfrom.banner2" /></span>
		</li>
		<li>
			<label><img src="${oss_url}/static/front2018/images/exchange/Friendly2x.png"></label>
			<span><spring:message code="json.planfrom.banner3" /></span>
		</li>
	</ul>
</div>

<div class="platformCen">
	<h3 class="platTit">
		<span><spring:message code="json.planfrom.title1" /></span>
		<p><img src="${oss_url}/static/front2018/images/exchange/biaotiplatform.png"></p>
	</h3>
	<div class="clear">
		<ul class="platTerrace">
			<li>
				<h3><span><spring:message code="json.planfrom.title1.top1" /></span></h3>
				<p>	
					<spring:message code="json.planfrom.title1.top1.content1" /><br>
					<spring:message code="json.planfrom.title1.top1.content2" /> <br>
					<spring:message code="json.planfrom.title1.top1.content3" />
				</p>
				<img src="${oss_url}/static/front2018/images/exchange/pingtai2x.png">
			</li>
			<li>
				<h3><span><spring:message code="json.planfrom.title1.top2" /></span></h3>
				<p>	
					<spring:message code="json.planfrom.title1.top2.content1" /><br>
					<spring:message code="json.planfrom.title1.top2.content2" /><br>
					<spring:message code="json.planfrom.title1.top2.content3" />
				</p>
				<img src="${oss_url}/static/front2018/images/exchange/safeplat2x.png">
			</li>
			<li>
				<h3><span><spring:message code="json.planfrom.title1.top3" /></span></h3>
				<p>	
					<spring:message code="json.planfrom.title1.top3.content1" /><br>
					<spring:message code="json.planfrom.title1.top3.content2" /><br>
					<spring:message code="json.planfrom.title1.top3.content3" />
				</p>
				<img src="${oss_url}/static/front2018/images/exchange/Human2x.png">
			</li>
			<li>
				<h3><span><spring:message code="json.planfrom.title1.top4" /></span></h3>
				<p>	
					<spring:message code="json.planfrom.title1.top4.content1" /><br>
					<spring:message code="json.planfrom.title1.top4.content2" /><br>
					<spring:message code="json.planfrom.title1.top4.content3" />
				</p>
				<img src="${oss_url}/static/front2018/images/exchange/saverplat2x.png">
			</li>
		</ul>
	</div>

	<div class="platUser">
		<h3 class="platTit">
			<span><spring:message code="json.planfrom.title2" /></span>
			<p><img src="${oss_url}/static/front2018/images/exchange/platform2x.png"></p>
		</h3>
		<ul>
			<li>
				<span><img src="${oss_url}/static/front2018/images/exchange/pt_user2x.png"></span>
				<h3><spring:message code="json.planfrom.title2.top1" /></h3>
				<p>
					<spring:message code="json.planfrom.title2.top1.content1" /><br>
					<spring:message code="json.planfrom.title2.top1.content2" /><br>
					<spring:message code="json.planfrom.title2.top1.content3" /><br>
					<spring:message code="json.planfrom.title2.top1.content4" />
					
				</p>
			</li>
			<li>
				<span><img src="${oss_url}/static/front2018/images/exchange/sq_userplat2x.png"></span>
				<h3><spring:message code="json.planfrom.title2.top2" /></h3>
				<p>
				    <spring:message code="json.planfrom.title2.top2.content2" /><br>
					<spring:message code="json.planfrom.title2.top2.content3" /><br>
					<spring:message code="json.planfrom.title2.top2.content1" /><br>					
					<spring:message code="json.planfrom.title2.top2.content4" />
				</p>
			</li>
			<li>
			<span><img src="${oss_url}/static/front2018/images/exchange/xm_userplat2x.png"></span>
				<h3><spring:message code="json.planfrom.title2.top3" /></h3>
				<p>
				    <spring:message code="json.planfrom.title2.top3.content3" /><br>
				    <spring:message code="json.planfrom.title2.top3.content4" /><br>
					<spring:message code="json.planfrom.title2.top3.content1" /><br>
					<spring:message code="json.planfrom.title2.top3.content2" /><br>					
					
				</p>
			</li>
		</ul>
	</div>
	<!-- 平台生态基金 -->
	<div class="platfunds">
		<h3 class="platTit">
			<span><spring:message code="json.planfrom.title3" /></span>
			<p><img src="${oss_url}/static/front2018/images/exchange/platformbiaoti3.png"></p>
		</h3>
		<div class="platDiv">
			<spring:message code="json.planfrom.title3.content" />
		</div>
		<p class="platformplat"><img src="${oss_url}/static/front2018/images/exchange/platformplat2x.png"></p>
	</div>
</div>

</section> 
<%@include file="../comm/footer.jsp" %>	

<script type="text/javascript" src="${oss_url}/static/front2018/js/jquery.rotate.min.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/main/scroll.js?v=20181126201750"></script>

</body>
</html>
