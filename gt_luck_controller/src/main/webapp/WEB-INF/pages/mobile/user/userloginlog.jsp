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

<%@include file="../comm/link.inc.jsp" %>
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
    <h2 class="tit"><spring:message code="security.loginlog"/> </h2>
</header>
<section>
     <div class="ticketsMain userloginLogMain">

       <div class="choiceMsg" style="padding:0.2rem 0.3rem;">
           <span class="fl style"><spring:message code="m.userloginlog.select" /></span>
            <span style="margin:0.1rem 0 0 0.3rem;" class="fl selStyle" onclick="showtab('coinWarp');"><spring:message code="m.userloginlog.loginlog" /></span>
           <div class="clear"></div>
       </div>   
        
            <select name="" id="select"class="fl select select8" style="display: none;">
               <option value="1"  selected="selected" ><spring:message code="m.userloginlog.loginlog" /></option>
               <option value="2"><spring:message code="m.userloginlog.settinglog" /></option>
           </select>

         <div class="logMain">
             <table id="slidecontentbox">
                 <tr>
                     <th><spring:message code="security.loginlog.logtime" /></th>
                     <th><spring:message code="security.loginlog.logip" /></th>
                 </tr>
                 <c:forEach items="${logs }" var="v">
                 <tr>
                     <td class="td_1">${v.fcreateTime }</td>
                     <td class="td_2">${v.fkey3 }</td>
                 </tr>
              	</c:forEach>           
               
             </table>
         </div>
     </div>
</section>

<div class="coinWarp" id="coinWarp" onclick="hidetab(this.id);">
    <div class="coinLitBox coinLitBox2">
        
          <ul>    
                 <li> <a href="/user/userloginlog.html"><spring:message code="m.userloginlog.loginlog" /></a></li>
                 <li> <a href="/user/userloginlog.html?type=2"><spring:message code="m.userloginlog.settinglog" /></a></li>
              
          </ul>

        
    </div>
</div>


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
	var url = '';
    if(selectId.val()==1){
       url="/user/userloginlog.html";  
    }else if(selectId.val()==2){
       url="/user/userloginlog.html?type=2";  
    }
    $(window).unbind("scroll",slideLoad);
    if(currentPage>0&&currentPage<pageCount)
    {
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

<script type="text/javascript">
    function previousPage(){
      window.history.go(-1);
    }
</script>
<script type="text/javascript">

$("#select").on('change', function(event) {
    var selectId = $('#select option:selected'); 
    if(selectId.val()==1){
       window.location.href="/user/userloginlog.html";  
    }else if(selectId.val()==2){
       window.location.href="/user/userloginlog.html?type=2";  
    }
});

</script>
</body>
</html>