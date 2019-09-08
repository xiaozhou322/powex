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
<title><spring:message code="security.qa" /></title>
  <style type="text/css">
        @font-face {
           font-family: 'PingFangMedium';
           src: url('/static/mobile/fonts/PingFangMedium.ttf'); 
        } 
    </style>
<!-- <script type="text/javascript" src="${oss_url}/static/front/js/user/jquery-1.11.2.min.js?v=20181126201750"></script> -->
<!-- <script type="text/javascript" src="${oss_url}/static/front/js/user/jquery.SuperSlide.2.1.js?v=20181126201750"></script>
 --></head>
<body>
<%@include file="../comm/header.jsp" %>

  <header class="header backHeader">  
    <i class="backIcon" onclick="previousPage();"></i>
    <h2 class="tit"><spring:message code="security.workcenter" /></h2>
</header>
<section>
     <div class="ticketsMain">
         <ul>
             <li class="active"><a href="/question/question.html"><spring:message code="security.workcenter" /></a></li>
             <li><a href="/question/questionlist.html"><spring:message code="m.question.worklist" /></a></li>
         </ul>
         <div class="ticketsCon">
             <div class="choiceTit">
                 <span class="fl"><spring:message code="security.quetype" /></span>
                  <c:forEach items="${question_list }" var="v" varStatus="vf">
                        <c:if test="${vf.index eq  0}">
                            <span class="choiceBtn ">
                            <span class="fl selStyle selStyle8" style="margin-left:0.3rem;">${v.value }</span>
                            </span>
                         </c:if>
                  </c:forEach>
                 <div class="clear"></div>
             </div>
            <select name="" id="question-type" class="fr select select5" style="display: none;"> 
                        <option value="1"></option>
            </select>
             <p><spring:message code="security.prodes" /></p>
             <textarea class="textarea" name="" id="question-desc" placeholder="<spring:message code="security.pleenteradescr" />"></textarea>
         </div>
         <input class="btn mtop" type="button" value="<spring:message code="security.subques" />" id="submitQuestion"/>
     </div>
</section>

<div class="coinWarp">
    <div class="coinLitBox coinLitBox2">
        
          <ul>
            <c:forEach items="${question_list }" var="v">          
                 <li data-value="${v.key }" id="key${v.key }" onclick="lwSelect(this.id,'question-type','choiceBtn','coin')">${v.value }</li>
               </c:forEach>
          </ul>

        
    </div>
</div>


<%@include file="../comm/footer.jsp" %> 
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/question.js?v=3"></script>
<script type="text/javascript">
  $(document).scroll(function() {
         var scrollTop = $(document).scrollTop();  //获取当前滑动位置
         if(scrollTop > 60){                 //滑动到该位置时执行代码
          $(".lw-header").addClass('lw-fixed')
         }else{
          $(".lw-header").removeClass('lw-fixed')
         }
    });
    
    $(".choiceBtn").click(function(event) {
        $(".coinWarp").animate({bottom:"0"}, 200)
    });
       $(".coinWarp").click(function(event) {
        $(this).css('bottom', '-100%');
    });

</script>
<script type="text/javascript">
    function previousPage(){
      window.history.go(-1);
    }
</script>
</body>
</html>
