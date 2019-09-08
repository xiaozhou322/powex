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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name = "viewport" content = "width = device-width, initial-scale = 1.0, maximum-scale = 1.0, user-scalable = 0" />
<%@include file="../comm/link.inc.jsp" %>
    <title><spring:message code="entrust.title.${status eq 1?'deal':'entrust' }" /></title>
      <style type="text/css">
        @font-face {
           font-family: 'PingFangMedium';
           src: url('/static/mobile/fonts/PingFangMedium.ttf'); 
        } 
    </style>

</head>
<body>	
<jsp:include page="../comm/header.jsp"></jsp:include>

<div id="top1" class="entrust_recorde">
<header class="tradeTop">
     <span class="back toback2"></span> 
    <span class="tit"><spring:message code="entrust.title.${status eq 1?'deal':'entrust' }" /></span>
</header>
<div class="clear entrust_btns">
      <a href="/trade/entrust.html?symbol=${ftrademapping.fid }&status=0" class="tit fl   ${status eq 0?'active':'' }">                      
          <spring:message code="entrust.title.entrust" />                   
      </a>               
      <a href="/trade/entrust.html?symbol=${ftrademapping.fid }&status=1" class="fl tit ${status eq 1?'active':'' }">
          <spring:message code="entrust.title.deal" />
      </a>
</div>
<section>
    <div class="coinAllCon">
         <div class="coinAll coin_list">
             <ul>    
             	 <c:forEach var="v" varStatus="vs" items="${ftrademappings }">             
                 <li><a href="/trade/entrust.html?symbol=${v.fid }&status=${status }&tab=2"><i class='listIcon' style="background: url(${v.fvirtualcointypeByFvirtualcointype2.furl }) no-repeat 0 center;background-size:85% auto; "></i> ${v.fvirtualcointypeByFvirtualcointype2.fShortName }/${v.fvirtualcointypeByFvirtualcointype1.fShortName }</a></li>
                 </c:forEach>                                              
             </ul>
         </div>
    </div>
</section>
</div>

<div id="top2" class="entrust_recorde" style="display:none">

<header class="tradeTop">
     <i class="back toback2"></i>  
    <span class="tit"><spring:message code="entrust.title.${status eq 1?'deal':'entrust' }" /></span>
</header>
<section class="coinAllMain entrustRecord">
    <div class="PassetsMain coinAllCon">
         <div class="coinAllentrust_detail">
             <ul id="slidecontentbox" class=" coin_list ">
             <c:forEach items="${fentrusts }" var="v" varStatus="vs">                 
                 <li class="showmsg">
                    <a>
                    	<i style="background: url(${v.ftrademapping.fvirtualcointypeByFvirtualcointype2.furl}) no-repeat 0 center;background-size: 85% auto;top: 0;"></i>
                    	
                        <div class="fl">                        
                           <em><spring:message code="enum.entrust.${v.fentrustType}" />${v.fisLimit==true?'[限价单]':'' }</em>
                           <em class="time"><fmt:formatDate value="${v.fcreateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></em>                        
                        </div>
                        <strong class="fr cgreen">${v.ftrademapping.fvirtualcointypeByFvirtualcointype1.fSymbol}<ex:DoubleCut value="${v.famount}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/></strong>
                        <div class="clear"></div>
                    </a>
                  
                     <div class="entrustCon" style="display: none">
                        <table>
                            <tr>
                                <td><spring:message code="entrust.entrust.date" /></td>
                                <td><fmt:formatDate value="${v.fcreateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            </tr>
                            <tr>
                                <td><spring:message code="entrust.entrust.type" /></td>
                                <td><spring:message code="enum.entrust.${v.fentrustType}" />${v.fisLimit==true?'[限价单]':'' }</td>
                            </tr>        
                            <tr>
                                <td><spring:message code="entrust.entrust.qty" /></td>
                                <td>${ftrademapping.fvirtualcointypeByFvirtualcointype2.fSymbol}<ex:DoubleCut value="${v.fcount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount2 }"/></td>
                            </tr>        
                            <tr>
                                <td><spring:message code="entrust.entrust.price" /></td>
                                <td class="cgreen">${v.ftrademapping.fvirtualcointypeByFvirtualcointype1.fSymbol}<ex:DoubleCut value="${v.fprize }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/></td>
                            </tr>        
                            <tr>
                                <td><spring:message code="entrust.entrust.amount" /></td>
                                <td class="cred">${v.ftrademapping.fvirtualcointypeByFvirtualcointype1.fSymbol}<ex:DoubleCut value="${v.famount}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/></td>
                            </tr>        
                            <tr>
                                <td><spring:message code="entrust.deal.qty" /></td>
                                <td>${ftrademapping.fvirtualcointypeByFvirtualcointype2.fSymbol}<ex:DoubleCut value="${v.fcount-v.fleftCount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount2 }"/></td>
                            </tr>
                               
                            <tr>
                                  
                                <td><spring:message code="entrust.deal.amount" /></td>
                                <td>${v.ftrademapping.fvirtualcointypeByFvirtualcointype1.fSymbol}<ex:DoubleCut value="${v.fsuccessAmount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/></td>
                            </tr>        
                            <tr>
                                <td><spring:message code="entrust.deal.fee" /></td>
                                <c:choose>
                                <c:when test="${v.fentrustType==0 }"> 
                                <td class="cblue">${ftrademapping.fvirtualcointypeByFvirtualcointype2.fSymbol}<ex:DoubleCut value="${v.ffees-v.fleftfees}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/></td>
                                </c:when>
                                <c:otherwise>
                                <td>${v.ftrademapping.fvirtualcointypeByFvirtualcointype1.fSymbol}<ex:DoubleCut value="${v.ffees-v.fleftfees}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/></td>
                                </c:otherwise>
                                </c:choose>
                            </tr>        
                            <tr>
                                <td><spring:message code="entrust.deal.average" /></td>
                                <td>${v.ftrademapping.fvirtualcointypeByFvirtualcointype1.fSymbol}<ex:DoubleCut value="${((v.fcount-v.fleftCount)==0)?0:  v.fsuccessAmount/((v.fcount-v.fleftCount)) }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/></td>
                            </tr>        
                            <tr>
                                <td><spring:message code="market.entruststatus" /></td>
                                <td class="cblue">
                                <spring:message code="enum.entrust.status.${v.fstatus}" />        
                                </td>
                            </tr>
                             <tr>
                                <td><spring:message code="market.entrustaction" /></td>
                                <td class="">
                                    <c:if test="${v.fstatus==1 || v.fstatus==2}">
                                    <a href="javascript:void(0);" class="tradecancel" data-value="${v.fid}" refresh="1"><spring:message code="entrust.entrust.cancel" /></a>
                                    </c:if>
                                   
                                </td>
                            </tr>
                        </table>
                    </div>
                  
                </li>  
            </c:forEach>             
                                             
             </ul>
             <c:if test="${fn:length(fentrusts)==0 }">
               <div class="textCenter noTxt mtop2">
                    <img src="${oss_url}/static/mobile/images/noMsg.png" width="86" alt="" />
                    <p><spring:message code="json.trade.noord" /></p>
               </div>
             </c:if>

         </div>
    </div>
</section>

</div>
<script type="text/javascript">
    $(".showmsg a").click(function(event){
        $(this).siblings(".entrustCon").stop().slideToggle(10);
        $(this).parent("li").css('height', 'auto');
    });
     $(document).ready(function(){
            var top = window.location.href;
            var nums = top.indexOf("tab=");
            var tab = top.substr(nums+4, 3);
            if(tab == 2){
              $("#top1").css('display','none');
              $("#top2").css('display','block');
            }
    });
      
    $(".backIcon").click(function(event) {
            window.history.go(-1);
    });

</script>
 

<!-- 上滑加载更多  -->
<input type="hidden" id="pageCount" value="${totalPage}">
<input type="hidden" id="currentPage" value="${currentPage}">
<div class="textC" id="slide_loading_btn" style="display:none;" onclick="slideLoadMoreInfo()" style=" <c:if test="${totalPage==1 }">display:none</c:if>"><spring:message code="m.security.moremsg" /></div>
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
    	var url = window.location.href;
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
<input type="hidden" id="symbol" value="${ftrademapping.fid }">
<jsp:include page="../comm/footer.jsp"></jsp:include>
<script type="text/javascript" src="${oss_url}/static/mobile/js/trade/trade.js?v=20181126201750"></script>
</body>
</html>
