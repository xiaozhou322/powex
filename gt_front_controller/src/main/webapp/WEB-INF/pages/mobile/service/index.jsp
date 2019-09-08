<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>


<!doctype html>
<html>
<head>
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name = "viewport" content = "width = device-width, user-scalable = no,initial-scale=0.5,maximum-scale=0.5,minimum-scale=0.5">
<%@include file="../comm/link.inc.jsp"%>

  <style type="text/css">
        @font-face {
           font-family: 'PingFangMedium';
           src: url('/static/mobile2018/fonts/PingFangMedium.ttf'); 
        } 
        .ticketsMain>ul>li{
            height: 0.9rem;
    		line-height: 0.9rem;
        }
        .newsMain{
        	margin-top:0;
            margin-bottom: 0.2rem;
    		box-shadow: 0px 3px 3px rgba(70, 90, 111, 0.14);
        }
        .newsMain ul{border:none;}
  </style>

</head>
<body>
<!-- 新闻公告 -->
	<%@include file="../comm/header.jsp"%>
	<div id="top1" class="news">
		<%-- <header class="tradeTop">  
		    <i class="back toback2"></i>
		    <h2 class="tit"><spring:message code="m.security.news" /></h2>
		</header> --%>
	<section style="box-shadow: 0px 2px 9px 0px rgba(70, 90, 111, 0.14);margin-bottom: 0.32rem;">
	     <div class="ticketsMain newsMain">
	         <ul class="clear">
	            <%--  <li class="active"><a href="javascript:void();"><spring:message code="m.security.news" /></a></li>
	             <li><a href="/about/index.html?id=88"><spring:message code="m.security.help" /></a></li>--%>
			 <c:forEach items="${articletypes }" var="v">
		      <li onclick="newsBtn(${v.fid });" class="newsBtnLi">
		      <%-- <a href="/service/ourService.html?id=${v.fid }&tab=2">${v.fname}</a> --%>
		      ${v.fname}
		      </li>
		     </c:forEach>    
         	</ul>
	     </div>
	     <!-- 下拉选择 -->
	    <%--  <div class="securityMain newsCon helpCon">
         <c:forEach items="${articletypes }" var="v" varStatus="vs" >
				<c:if test="${id==v.fid}"> 
				<span class="selStyle" onclick="showtab('coinWarp');">${v.fname }</span>
				</c:if>
         </c:forEach> 
         </div> --%>
      <!-- 内容区域 -->
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
         
         
	</section>
	</div>	
<!-- 底部显示导航 -->
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
	<script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
	<script type="text/javascript">
		 $(".back toback2").click(function(event) {
        	window.history.go(-1);
    	});
		$(function() {
			initTab()
		})
		function initTab(){
			var url = window.location.search;
			if(url=="?id=5&tab=2"){
				$(".newsBtnLi").eq(3).css({'color': '#506FC8','border-bottom':'4px solid #506FC8','font-size':'0.28rem'});
			 }else if(url=="?id=3&tab=2"){
				 $(".newsBtnLi").eq(1).css({'color': '#506FC8','border-bottom':'4px solid #506FC8','font-size':'0.28rem'});
			 }else if(url=="?id=4&tab=2"){
				 $(".newsBtnLi").eq(2).css({'color': '#506FC8','border-bottom':'4px solid #506FC8','font-size':'0.28rem'});
			 }else{
				 $(".newsBtnLi").eq(0).css({'color': '#506FC8','border-bottom':'4px solid #506FC8','font-size':'0.28rem'});
			 }
		}
		 function newsBtn(fid) {
			 window.location.href='/service/ourService.html?id='+fid+'&tab=2';
		}
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
<script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.qrcode.min.js?v=20181126201750"></script>
</body>
</html>
