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
<link href="${oss_url}/static/mobile2018/css/usdtTrade.css?v=2" rel="stylesheet" type="text/css" />

</head>
<body class="Ubody">
<%@include file="../comm/header.jsp" %>
<header class="tradeTop">  
    <i class="back toback2"></i>
    <h2 class="tit">订单记录</h2>
</header>
    <section class="usdtTrade usdtTrade2">
         <div class="recodeInfo">
         <form action="/agent/orderlist.html" method="get" id="search_form">
            <div class="search clear">
                 <input class="text fl" type="text" placeholder="在此输入订单号" name="orderno" id="order_no"/>
                 <input class="sub fr" type="button" value="订单搜索" onclick="if($('#order_no').val()!='')$('#search_form')[0].submit();"/>
             </div>
            </form>
            <div class="ddSteup clear">
                <!-- <em class="fl">订单状态</em> -->
                <span class='dd_list fr selStyle' onclick="showtab('coinWarp')">
                    <c:if test="${status == 0 }">全部</c:if>
                    <c:if test="${status == 1 }">未付款</c:if>
                    <c:if test="${status == 2 }">已付款</c:if>                
                    <c:if test="${status == 3 }">已完成</c:if>                
                    <c:if test="${status == 4 }">已取消</c:if>                
                    <c:if test="${status == 5 }">交易失败</c:if>
                </span>
            </div>

         </div>
         <div id="slidecontentbox">
         <c:forEach items="${fagentoperations}" var="v">   
         <div class="recodeMains">
         <a href="/agent/orderdetail.html?order_id=${v.fid}">
             <div class="table-item table-item2 ">
                 <div class="tabs userFace" style="text-align: left;">
                   订单编号：${v.fid}
                    <div class="clear"></div>
                 </div>
                 <div class="tm">${v.fcreateTime}</div>
             </div>
             <div class="recode_item">
                 <div class="reodeDetails">
                     <p>
                        <span class="fl tt">交易对象: </span><c:if test="${is_agent == 1 }"><span class="fl tts"><%-- ${v.fuser.frealName} --%></span></c:if><c:if test="${is_agent == 0 }"><span class="fl tts">${v.fremittanceType}</span></c:if>  
                         <em class="fr tts">¥ ${v.fprice}<i><img src="${oss_url}/static/mobile/css/agent/images/cny.png" height="12" width="24" alt="" /></i></em>
                     </p>
                     <p>
                        <span class="fl tt">订单金额: </span><span class="fl tts">${v.ftotalcny}</span> 
                         <em class="fr">x${v.famount}<i><img src="${oss_url}/static/mobile/css/agent/images/usdt.png" height="12" width="24" alt="" /></i></em>
                     </p>
                     <p><span class="fl tt">交易类型:</span> <span class="fl tts">${v.ftype_s}</span> <em class="fr frees" style="text-decoration: blink;color: #488bef;">${v.fstatus_s}</em></p>
                     <p><span class="fl tt">备注号: </span><span class="fl tts">${v.fremark}</span></p>
                 </div>             
                
                
             </div>
             </a>
         </div>
     </c:forEach>
         </div>
        

    </section>
</section>
<div class="coinWarp">
    <div class="coinLitBox coinLitBox2">       
     <ol class="clear order_ol">
         <li class="<c:if test="${status == 0 }"></c:if> fl"><a href="/agent/orderlist.html?status=0">全部</a></li>
         <li class="<c:if test="${status == 1 }"></c:if> fl"><a href="/agent/orderlist.html?status=1">未付款</a></li>
         <li class="<c:if test="${status == 2 }"></c:if> fl"><a href="/agent/orderlist.html?status=2">已付款</a></li>
         <li class="<c:if test="${status == 3 }"></c:if> fl"><a href="/agent/orderlist.html?status=3">已完成</a></li>
         <li class="<c:if test="${status == 4 }"></c:if> fl"><a href="/agent/orderlist.html?status=4">已取消</a></li>
         <li class="<c:if test="${status == 5 }"></c:if> fl"><a href="/agent/orderlist.html?status=5">交易失败</a></li>
     </ol>       
    </div>
</div>
<input type="hidden" id="pageCount" value="${totalPage}">
<input type="hidden" id="currentPage" value="${currentPage}">
<div id="slide_loading_btn"  onclick="slideLoadMoreInfo()" style=" <c:if test="${totalPage==1 }">display:none</c:if>"><spring:message code="m.security.moremsg" /></div>
<%@include file="../comm/footer.jsp" %>
<script src="${oss_url}/static/mobile2018/js/main.js?v=20181126201750"></script>
<script type="text/javascript">
    $('.order_ol li a').click(function(event) {
        var txt=$(this).html();
        $('.dd_list').html(txt);
    });

    $('.coinWarp').click(function(event) {
        $(this).css('bottom', '-100%');
    });
</script>

<script type="text/javascript">
$(".backIcon").click(function(){
    window.history.go(-1);
    
});


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

</body>
</html>
