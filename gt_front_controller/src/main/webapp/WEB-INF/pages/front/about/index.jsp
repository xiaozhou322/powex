<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>

<!doctype html>
<html>
<head> 
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp" %>
<link href="${oss_url}/static/front/css/index/main.css?v=2" rel="stylesheet" type="text/css" />
<link href="${oss_url}/static/front2018/css/index/common.css?v=2" rel="stylesheet" type="text/css" />
<link href="${oss_url}/static/front2018/css/user/common.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${oss_url}/static/front2018/js/index/main.js"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/user/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/user/jquery.SuperSlide.2.1.js"></script>
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
</head>
<body>
<%@include file="../comm/header.jsp" %>
	<section class="l-content" >
    <div class="news_banner">
       <!--  <img src="/static/front2018/images/news_bg.png" width="100%" alt="" />    -->
        
        <div class="news_txt"><h1>${fabout.ftype }</h1></div>


    </div>
    <div class="lw-helpNav" id="lw-helpNav">
        <c:forEach items="${abouts }" var="v" varStatus="vs">
        <a href="javascript:;" class="${fabout.ftype == v.name?'lw-active':''}" >${v.name }</a> 
        </c:forEach>

      <div class="clear"></div>
    </div>

    <div class="lw-helpCons" style="min-height: 600px;">
      <div class="mg">

      <c:forEach items="${abouts }" var="v" varStatus="vs">
            
        <div class="lw-heilpTabs fl" <c:if test="${vs.index ne 0}">style="display:none;"</c:if>>
          <ul>
            <c:forEach items="${v.value }" var="vv" varStatus="vvs">
            <li  class="${fabout.fid==vv.fid?'lw-active':'' }" ><a href="/about/index.html?id=${vv.fid }">${vv.ftitle }</a></li>                  
             </c:forEach>
          </ul>
        </div>
          
             </c:forEach>
      
        <div class="lw-helpRight fl">
            <div class="new_list">
              <h2>${fabout.ftitle}</h2>
             <p>${fabout.fcontent}</p>
            </div>
        </div>
        <div class="clear"></div>
      </div>
    </div>
</section> 
<%@include file="../comm/footer.jsp" %>	
  
  <script type="text/javascript">

  window.onload=function(){
      var starttitle = document.getElementById("lw-helpNav").getElementsByTagName("a");
      var startconten = document.getElementsByClassName('lw-heilpTabs');
      for(var i = 0 ;i < starttitle.length;i++){
        if(starttitle[i].getAttribute("class") == "lw-active"){
          startconten[i].style.display ="block";
        }else{
          startconten[i].style.display = "none";
        }
      }

  }

  var title = document.getElementById("lw-helpNav").getElementsByTagName("a");
  for(var i = 0; i < title.length; i++){
    title[i].onclick = selecc;
  }

  function selecc(){
    var conten = document.getElementsByClassName('lw-heilpTabs');

    for(var i = 0;i <title.length;i++){
      if(title[i] === this){
        var ts = conten[i].getElementsByTagName("a")[0].href;
        window.location.href = ts;

      }
    }
}

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
			})
		})
	</script>
</body>
</html>
