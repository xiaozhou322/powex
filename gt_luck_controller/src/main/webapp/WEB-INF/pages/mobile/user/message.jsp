<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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

<style type="text/css">
        @font-face {
           font-family: 'PingFangMedium';
           src: url('/static/mobile/fonts/PingFangMedium.ttf'); 
        } 
</style>
<%@include file="../comm/link.inc.jsp" %>

</head>
<body>
<header class="header backHeader">  
    <i class="backIcon" id="backIcon" onclick="previousPage();"></i>
    <i class="backIcon1" id="backIcon1" onclick="previousPages();" style="display: none"></i>   
    <h2 class="tit"><spring:message code="security.message" /></h2>
</header>
<section id="msgIndex">
     <div class="ticketsMain">
         <ul>
             <li class="${type ==1?'active':'' } taburl"><a href="/user/message.html?type=1" class="${type ==1?'taburl':'' } "><spring:message code="security.message.unread" /></a></li>
             <li class="${type ==2?'active':'' } taburl"><a href="/user/message.html?type=2" class="${type ==1?'taburl':'' } "><spring:message code="security.message.read" /></a></li>
         </ul>
         <div class="messageCon">
             <ul id="slidecontentbox">
              <c:forEach items="${messages }" var="v">
                 <li>
                     <h2 class="Cff9000">${v.ftitle }</h2>
                     <p>${v.fcontent}</p>
                     <div class="lookDetail">
                         <span class="fl">${v.fcreateTime }</span>
                         <a  class="fr Cff9000 showmsg msglook"  data-value="${v.fid}" data-msg="${v.fid}"><spring:message code="security.view" /></a>
                         <div class="clear"></div>
                     </div>               
                 </li>
                </c:forEach>                
             </ul>
         </div>
     </div>
</section>	


<section class="ptop2"  >
<c:forEach items="${messages }" var="v">
    <div class="msgDetail" id="showmsg${v.fid}" style="display: none">
        <div class="msgTit">
            <h1>${v.ftitle }</h1>
            <span>${v.fcreateTime }</span>
        </div>
        <div class="msgText">
            <p>${v.fcontent}</p>
        </div>
    </div>
    </c:forEach> 
     <c:if test="${fn:length(messages)==0 }">
      <div class="textCenter">
          <img src="${oss_url}/static/mobile/images/noMsg.png" width="86" alt="" />
          <p> <spring:message code="security.loginlog.nolog" /> </p>
      </div>
    </c:if> 
</section>

 
<%@include file="../comm/header.jsp" %>


<script type="text/javascript">
    function previousPage(){
      window.history.go(-1);
    }
    function previousPages(){
     document.getElementById('backIcon1').style.display = 'none';
   	 document.getElementById('backIcon').style.display = 'block';
   	 document.getElementById('msgIndex').style.display = 'block';
   	 $(".msgDetail").css('display','none');
    }

   $(".showmsg").click(function(event) {
   	 var fID = $(this).data('msg');
   	 var showmessage = "showmsg" + fID;
   	 $('#'+showmessage).css('display','block');
   	 document.getElementById('msgIndex').style.display = 'none';
   	 document.getElementById('backIcon').style.display = 'none';
   	 document.getElementById('backIcon1').style.display = 'block';
   	 
	});
</script>


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
	var selectId = $('#select option:selected'); 
	
    $(window).unbind("scroll",slideLoad);
    if(currentPage>0&&currentPage<pageCount)
    {
    	var url = $('.taburl').attr('href');
		var index = layer.load(2);
        $(slideBtn).text(language["load.ing.msg"]).show();
    
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
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/msg/message.js?v=20181126201750"></script>

</body>
</html>