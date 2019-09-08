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
<title><spring:message code="m.question.worklist" /></title>
<style type="text/css">
        @font-face {
           font-family: 'PingFangMedium';
           src: url('/static/mobile/fonts/PingFangMedium.ttf'); 
        } 
    </style>
</head>
<body>
<%@include file="../comm/header.jsp" %>

  <header class="header backHeader">  
    <i class="backIcon" onclick="previousPage();"></i>
    <h2 class="tit"><spring:message code="m.question.worklist" /></h2>
</header>
<section>
     <div class="ticketsMain ticketsListMain" style="padding-bottom:0px" id="slidecontentbox">
         <ul>
             <li><a href="/question/question.html"><spring:message code="security.workcenter" /></a></li>
             <li class="active"><a href="/question/questionlist.html"><spring:message code="m.question.worklist" /></a></li>
         </ul> 
         <c:forEach items="${list }" var="v">        
         <div class="ticketsList">
             
             <table class="tableTit">
                 <tr>
<!--                      <th class="td1">工单类型</th>
 -->                     <th class="td1"><spring:message code="enum.question.${v.ftype }" /><span class="cgreen">&nbsp;&nbsp;(<spring:message code="enum.question.status.${v.fstatus }" />)</span><em style="display:block; font-size:12px;">${v.fcreateTime }</em></th>
                     <th class="td3"><a class="cblue deteleBtn delete" href="javascript:void(0)" data-question="{&quot;questionid&quot;:${v.fid }}"><spring:message code="security.delete" /></a></th>
                 </tr>
             </table>
             <table class="tableDetailTit lookWarp hideBox">
                 <tr>
                     <td class="td1"><spring:message code="security.quesnum" /></td>
                     <td class="td2">${v.fid }</td>
                 </tr>                 
                 <tr>
                     <td class="td1"><spring:message code="security.subtime" /></td>
                     <td class="td2">${v.fcreateTime }</td>
                 </tr>                 
                 <tr>
                     <td class="td1"><spring:message code="security.prodes" /></td>
                     <td class="td2 discription" colspan="2">${v.fdesc }</td>
                 </tr>                 
                 <tr>
                     <td class="td1"><spring:message code="security.prostate" /></td>
                     <td class="td2 cgreen"><spring:message code="enum.question.status.${v.fstatus }" /></td>
                     <td class=" lookMore">
                     <c:if test="${v.fstatus==2 }">
                     <a class="cblue" href="javascript:void(0)" data-question="${v.fid }"><spring:message code="security.view" /></a>
                      </c:if>
                     </td>
                 </tr>
             </table>
      
             <span class="cblue look view" onclick="javascript:this.innerHTML=(this.innerHTML=='<spring:message code="m.question.view" />'?'<spring:message code="m.question.shou" />':'<spring:message code="m.question.view" />')"><spring:message code="m.question.view" /></span>
                
         </div>   
      </c:forEach>

       <c:if test="${fn:length(list)==0 }">
            <p style="text-align:center; font-size:18px; display:none;"><spring:message code="financial.norec" /></p>
       </c:if>
     
     <c:forEach items="${list }" var="v">
      <c:if test="${v.fstatus==2 }">
     <div class="ticketsDetail lookMoreWarp" id="lookMoreWarp${v.fid }" data-show="${v.fid }" tabindex="-1">
         <div class="ticketsDetailCon">
             <h2 class="tcenter"><spring:message code="security.quecontent" /></h2>
             <p>${v.fdesc }</p>
             <h3 class="cblue results"><spring:message code="security.repcontent" /></h3>
             <p>${v.fanswer }</p>
             <span class="close close2"></span>
         </div>
     </div>
    </c:if>       
    </c:forEach>
                     
</section>
<!-- 上滑加载更多  -->
<input type="hidden" id="pageCount" value="${totalPage}">
<input type="hidden" id="currentPage" value="${currentPage}">
<div id="slide_loading_btn"  onclick="slideLoadMoreInfo()" style=" <c:if test="${totalPage==1 }">display:none</c:if>"><spring:message code="m.security.moremsg" /></div>
<script type="text/javascript">
var pageCount = parseInt($('#pageCount').val());
var currentPage = parseInt($('#currentPage').val());
var slideBtn = $('#slide_loading_btn');
$(document).ready(function(){
    $(window).bind("scroll",slideLoad);
    if(currentPage==pageCount&&pageCount>1){  $(slideBtn).text(language["load.no.msg"]).show(); }
});
function slideLoad(){
    var scrollbar_top = document.documentElement.scrollTop  || document.body.scrollTop
    var bottomHeight = 10;
    var screenHeight = document.documentElement.clientHeight || document.body.clientHeight
    var page_bottom_pos = scrollbar_top+screenHeight+bottomHeight
    if (page_bottom_pos >= document.body.scrollHeight){
        slideLoadMoreInfo();
    }

}
function  slideLoadMoreInfo(){
    $(window).unbind("scroll",slideLoad);
    if(currentPage>0&&currentPage<pageCount)
    {
		var index = layer.load(2);
        $(slideBtn).text(language["load.ing.msg"]).show();
        var url = "/question/questionlist.html";
        var object = {};
        object.currentPage = currentPage+1;
        $.get(url,object,function(html){
            if($.trim(html)!='')
            {
            
                $('#slidecontentbox').append(html);
                $('#currentPage').val(currentPage+1);
                $(slideBtn).text(language["load.more.msg"]).hide();
                $(window).bind("scroll",slideLoad);
                currentPage = parseInt($('#currentPage').val());
                if(currentPage==pageCount)
                {
                    $(slideBtn).text(language["load.no.msg"]).show();
                    $(window).unbind("scroll",slideLoad);
                }
            }
            else
            {
                $(slideBtn).text(language["load.no.msg"]).show();
                $(window).unbind("scroll",slideLoad);
            }
            layer.close(index);
        })
    }
    else
    {
        $(slideBtn).text(language["load.no.msg"]).fadeOut(3000);
        $(window).unbind("scroll",slideLoad);
    }
}
</script>

<%@include file="../comm/footer.jsp" %> 
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/question.js?v=5"></script>
<script type="text/javascript">
  $(document).scroll(function() {
         var scrollTop = $(document).scrollTop();  //获取当前滑动位置
         if(scrollTop > 60){                 //滑动到该位置时执行代码
          $(".lw-header").addClass('lw-fixed')
         }else{
          $(".lw-header").removeClass('lw-fixed')
         }
    });

  $(".look").on('click',function(event) {
        var _this=$(this).siblings('.tableDetailTit')
    	$(_this).stop().slideToggle(10 ) 
});

$(".lookMore").click(function(event) {
    var fID = $(this).find('a').data('question');
      var showmessage = "lookMoreWarp" + fID;
      document.getElementById(showmessage).style.display = 'block';
    
});

$(".close2").click(function(event) {
   $(".ticketsDetail").fadeOut(50);
});

</script>
<script type="text/javascript">
    function previousPage(){
      window.history.go(-1);
    }
    

</script>
</body>
</html>
