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
<meta name = "viewport" content = "width = device-width, initial-scale = 1.0, maximum-scale = 1.0, user-scalable = 0" />
<%@include file="../comm/link.inc.jsp" %>
  <style type="text/css">
        @font-face {
           font-family: 'PingFangMedium';
           src: url('/static/mobile/fonts/PingFangMedium.ttf'); 
        } 
        .table{width:100%!important;}
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
        <i class="back toback2"></i>
        <h2 class="tit"><spring:message code="m.security.help" /></h2>
    </header>
    <section>
         <div class="ticketsMain newsMain">
             <ul class="clear">
                 <li><a href="/service/ourService.html?id=1"><spring:message code="m.security.news" /></a></li>
                 <li  class="active"><a href="javascript:void();"><spring:message code="m.security.help" /></a></li>
             </ul>
         </div>
         <div class="securityMain newsCon helpCon">
             <c:forEach items="${abouts }" var="v" varStatus="vs">
                  <c:if test="${fabout.ftype == v.name}">
                  
                    <span class="selStyle" style="width:100%;" onclick="showtab('coinWarp')">${v.name }</span>
                 
                  </c:if> 
             </c:forEach> 
         </div>
          
       <!--    <select name="" id="lw-helpNav" class="select selectHelp" onchange="selecc();">
                    <c:forEach items="${abouts }" var="v" varStatus="vs">
                    <option ${fabout.ftype == v.name?'selected':''} >${v.name }</option>
                      </c:forEach>  
          </select> -->
    </section>
</div>

<div id="top2" class="newsDetail" style="display: block;">
<!-- <header class="tradeTop">  
    <i class="back toback2"></i>
    <span class="tit">${fabout.ftype }</span>
</header> -->
<section>
    <div class="ticketsMain helpContent">
    <c:forEach items="${abouts }" var="v" varStatus="vs">
         <div class="newsHelpMain lw-helpCons" >
          <div class="lw-heilpTabs" <c:if test="${vs.index ne 0}">style="display:none; width:100%;"</c:if>>
             <ul>  
               <c:forEach items="${v.value }" var="vv" varStatus="vvs">               
                 <li><a href="/about/index.html?id=${vv.fid }&tab=3">${vv.ftitle }</a></li>
                </c:forEach>   

             </ul>
           </div>
            </c:forEach>
         </div>
    </div>
</section>
</div>

<div class="coinWarp" id="coinWarp" onclick="hidetab(this.id);" style="z-index: 10000">
    <div class="coinLitBox coinLitBox2">
        
          <ul id="lw-helpNav">    
                <c:forEach items="${abouts }" var="v" varStatus="vs">
                 <li class="${fabout.ftype == v.name?'selecteds':''}" onclick="">${v.name }</li>
                </c:forEach>
          </ul>

        
    </div>
</div>
<style type="text/css">
  .infolist{overflow:hidden;}
  .infolist li{display:block; width:100%!important;}
  .infolist li span:nth-child(2){margin-left:1rem!important;}
  .box .ul li{width:100%!important; float:none!important;display:block; }
</style>
<div style="display: none" id="top3">
<header class="tradeTop" style="background:#fff;">  
    <i class="back toback2"></i>
    <h2 class="tit">${fabout.ftitle}</h2>
</header>
<section class="newsListDetail">
    <div class="newslistMain">
              <h2>${fabout.ftitle}</h2>
             <div>${fabout.fcontent}</div>
    </div>
</section>
</div>	
<%@ include file="../comm/tabbar.jsp"%>
<%@ include file="../comm/footer.jsp"%>	
  
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
