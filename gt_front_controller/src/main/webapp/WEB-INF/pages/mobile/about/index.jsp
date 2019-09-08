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
<meta name = "viewport" content = "width = device-width, user-scalable = no,initial-scale=0.5,maximum-scale=0.5,minimum-scale=0.5">
<%@include file="../comm/link.inc.jsp" %>
  <style type="text/css">
        @font-face {
           font-family: 'PingFangMedium';
           src: url('/static/mobile2018/fonts/PingFangMedium.ttf'); 
        } 
        .table{width:100%!important;}
       #lw-helpNav .selecteds{
            color: #506FC8;
		    border-bottom: 4px solid #506FC8;
		    font-size: 0.28rem;
    }
  </style>
<!-- <link href="${oss_url}/static/front/css/about/main.css?v=20181126201750" rel="stylesheet" type="text/css" />
 <link href="${oss_url}/static/front/css/index/main.css?v=20181126201750" rel="stylesheet" type="text/css" />-->
<script type="text/javascript" src="${oss_url}/static/front/js/index/main.js?v=20181126201750"></script>
<!-- <script type="text/javascript" src="${oss_url}/static/front/js/user/jquery-1.11.2.min.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/user/jquery.SuperSlide.2.1.js?v=20181126201750"></script>
 -->
</head>
<body>



<%@include file="../comm/header.jsp" %>
<div id="top1" class="news">
    <header class="tradeTop">
    <a href='/user/security.html'>
    	<i class="toback2"></i>
    </a>
        <h2 class="tit"><spring:message code="m.security.help" /></h2>
    </header>
    <section style="box-shadow: 0px 2px 9px 0px rgba(70, 90, 111, 0.14);margin-bottom: 0.32rem;">
         <div class="ticketsMain newsMain">
             <ul class="clear" id="lw-helpNav">
             	<c:forEach items="${abouts }" var="v" varStatus="vs">
	               <li class="${fabout.ftype == v.name?'selecteds':''}" onclick="">${v.name }</li>
                </c:forEach>
             </ul>
         </div>      
		<div id="top2" class="newsDetail" style="display: block;">
		    <div class="ticketsMain helpContent">
		    	<c:forEach items="${abouts}" var="v" varStatus="vs">
		         <div class="newsHelpMain lw-helpCons" >
		          <div class="lw-heilpTabs" <c:if test="${vs.index ne 0}">style="display:none; width:100%;"</c:if>>
		             <ul>  
		               <c:forEach items="${v.value }" var="vv" varStatus="vvs">               
			                <c:if test="${vv.fid != 2 }">
			                <li><a href="/about/index.html?id=${vv.fid }&tab=3">${vv.ftitle }</a></li>
			               </c:if> 
		               </c:forEach>
		             </ul>
		         </div>
		        </div>
		       </c:forEach>
	      </div>
		</div>
    </section>
</div>


<style type="text/css">
  .infolist{overflow:hidden;}
  .infolist li{display:block; width:100%!important;}
  .infolist li span:nth-child(2){margin-left:1rem!important;}
  .box .ul li{width:100%!important; float:none!important;display:block; }
</style>
<div style="display: none" id="top3">
<%-- <header class="tradeTop" style="background:#fff;">  
    <i class="back toback2"></i>
    <h2 class="tit">${fabout.ftitle}</h2>
</header> --%>
 <header class="tradeTop" style=" background: #fff; margin-bottom: 0.2rem;">  
         <i class="back toback2"></i><!-- <a href='/user/security.html'></a> -->
        <h2 class="tit">${fabout.ftitle}</h2>
    </header>
<section class="newsListDetail">
    <div class="newslistMain">
         <h2>${fabout.ftitle}</h2>
        <p style="font-size: 0.22rem;">${fabout.fcontent}</p>
    </div>
</section>
</div>	
<%-- <%@ include file="../comm/tabbar.jsp"%> --%>
<%@ include file="../comm/footer.jsp"%>	
  <script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
  <script type="text/javascript">


  </script>

	<script type="text/javascript">
		$(function() {
			$('.collapse').on('hide.bs.collapse', function(ele) {
				$("#"+$(ele.currentTarget).data().icon).html("+");
				return true;
			})
			$('.collapse').on('show.bs.collapse', function(ele) {
				$("#"+$(ele.currentTarget).data().icon).html("-");
				return true;
			});
      var top = window.location.href;
        var nums = top.indexOf("tab=");
        var tab = top.substr(nums+4, 3);
        if(tab == 2){
          $("#top3").css('display','none');
          $("#top2").css('display','block');
        }
         if(tab == 3){
          $("#top1,#top2").css('display','none');
          $("#top3").css('display','block');
        }

         var starttitles = document.getElementById("lw-helpNav").getElementsByTagName('li');
         var startcontens = document.getElementsByClassName('lw-heilpTabs');
         for(var i = 0 ;i < starttitles.length;i++){
            if(starttitles[i].getAttribute("class") == "selecteds"){
              startcontens[i].style.display ="block";
          }else{
              startcontens[i].style.display = "none";
          }
        }

		})
	</script>

  <script type="text/javascript">
    $(".backIcon").click(function(event) {
        window.history.go(-1);
    });


      var starttitle = document.getElementById("lw-helpNav").getElementsByTagName('li');
        var startconten = document.getElementsByClassName('lw-heilpTabs');
        for(var i = 0 ;i < starttitle.length;i++){
          starttitle[i].onclick = selecc;
        }
     
  
    function selecc(){
     
    var title = document.getElementById("lw-helpNav").getElementsByTagName('li');

    var conten = document.getElementsByClassName('lw-heilpTabs');
    for(var i = 0;i <title.length;i++){
      if(title[i] === this){
        var ts = conten[i].getElementsByTagName("a")[0].href;
        var ts = ts.replace(/tab=3/, "tab=2");
        window.location.href = ts;

       }
     }
  }
  </script>




</body>
</html>
