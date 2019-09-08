<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
	if (request.getServerName().equals("www.gbcax.com")){basePath="https://www.gbcax.com";}
%>

<!doctype html>
<html>
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name = "viewport" content = "width = device-width, initial-scale = 1.0, maximum-scale = 1.0, user-scalable = 0" />
<%@include file="../comm/link.inc.jsp"%>

  <style type="text/css">
        @font-face {
           font-family: 'PingFangMedium';
           src: url('/static/mobile/fonts/PingFangMedium.ttf'); 
        } 
  </style>

</head>
<body>

	<%@include file="../comm/header.jsp"%>
	<div id="top1" class="news">
	<header class="tradeTop">  
    <i class="back toback2"></i>
    <h2 class="tit"><spring:message code="m.security.news" /></h2>
	</header>
	<section>
	     <div class="ticketsMain newsMain">
	         <ul class="clear">
	             <li class="active"><a href="javascript:void();"><spring:message code="m.security.news" /></a></li>
	             <li><a href="/about/index.html?id=2"><spring:message code="m.security.help" /></a></li>
	         </ul>
	     </div>
	     <div class="securityMain newsCon helpCon">
<!-- 	         <ul>
	         <c:forEach items="${articletypes }" var="v">
	             <li>
	                 <a href="/service/ourService.html?id=${v.fid }&tab=2">
	                     <i></i>
	                     <span>${v.fname }</span>
	                 </a>
	             </li>
	        </c:forEach>             
	           
	         </ul> -->
	         <c:forEach items="${articletypes }" var="v" varStatus="vs" >
					<c:if test="${id==v.fid}"> 
					<span class="selStyle" onclick="showtab('coinWarp');">${v.fname }</span>
					</c:if>
	         </c:forEach> 
         <!--     <select name="" id="newSelect"  class="select selectHelp" onchange="newchange()">
                 <c:forEach items="${articletypes }" var="v">
                    <option value="/service/ourService.html?id=${v.fid }&tab=2" ${id==v.fid?'selected':''}>${v.fname }</option>
                 </c:forEach> 
             </select> -->
	     </div>

	</section>
	</div>	

<div id="top2" class="news" style="display: block">
	<header class="tradeTop" style="display:none;">  
    <i class="back toback2"></i>
    <h2 class="tit">${type }</h2>
</header>
<section>
    <div class="news_list">
    <c:forEach items="${farticles }" var="v">
        <dl>
        	<a href="${v.url }">
            <dt class="fl"><img src="${v.furl }" alt="" /> </dt>
            <dd class="">
                <h3 class="title">${v.ftitle }</h3>
                <span class="time"><fmt:formatDate value="${v.fcreateDate }" pattern="yyyy-MM-dd"/></span>
            </dd>
            </a>
            <div class="clear"></div>
        </dl>        
    </c:forEach>
    </div>
</section>
</div>

<div class="coinWarp" id="coinWarp" onclick="hidetab(this.id);" style="z-index: 10000">
    <div class="coinLitBox coinLitBox2">
        
          <ul>    
          		<c:forEach items="${articletypes }" var="v">
                 <li> <a href="/service/ourService.html?id=${v.fid }&tab=2">${v.fname }</a></li>
                </c:forEach>
          </ul>

        
    </div>
</div>

<%@ include file="../comm/tabbar.jsp"%>
<%@ include file="../comm/footer.jsp"%> 
	<script type="text/javascript">
		 $(".back toback2").click(function(event) {
        	window.history.go(-1);
    	});

         function onchage(){

            var top = window.location.href;
	        var nums = top.indexOf("tab=");
	        var tab = top.substr(nums+4, 3);
	        if(tab == 2){
	          $("#top1").css('display','none');
	          $("#top2").css('display','block');
	        }
        
		 }


        function newchange(){
            var newsTit= $("#newSelect option:selected").val();
            window.location.href = newsTit;
        }



	</script>
	<script type="text/javascript" src="${oss_url}/static/front/js/comm/msg.js?v=20181126201750"></script>
	<script type="text/javascript"
		src="${oss_url}/static/front/js/plugin/jquery.qrcode.min.js?v=20181126201750"></script>
</body>
</html>
