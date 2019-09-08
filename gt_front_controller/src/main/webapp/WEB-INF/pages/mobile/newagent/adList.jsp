<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>

<!doctype html>
<html>
<head> 
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name = "viewport" content = "width = device-width, initial-scale = 1.0, maximum-scale = 1.0, user-scalable = 0" />
<%@include file="../comm/link.inc.jsp" %>
<link href="${oss_url}/static/mobile2018/css/usdtTrade.css?v=20190301163300100" rel="stylesheet" type="text/css" />

</head>
<body class="Ubody">
<%@include file="../comm/header.jsp" %>
<header class="tradeTop">
	<h2 class="tit">
		<c:if test="${returnUrl != null && returnUrl !=''}">   
           	<a href="${returnUrl}" style="color:red">返回页面</a>
        </c:if>
    </h2>
</header>
<section class="usdtTrade">
    <div class="uTrade_r fr">
        <div class="tables-title clear">
            <span class="tit fl">承兑商</span>
            <span class="tit fl">支付方式</span>
            <span class="tit fl">数量</span>
            <span class="tit fl">价格</span>
        </div>
        <div class="agentCon" id="slidecontentbox">
	        <c:forEach items="${adList}" var="ad">
	            <div class="table-item">
	                <div class="item_con">
	                    <div class="tabs userFace fl">
	                        <span class="fl iconTag">${ad.user.frealName}</span>
	                        <div class="uName fl">
	                            <a href="javascript:;">${ad.user.frealName}</a>
	                             <c:if test="${ad.status == 0 }">
	                            	<img src="${oss_url}/static/mobile/css/agent/images/v.png" alt="" />
	                             </c:if>
	                        </div>
	                        <div class="clear"></div>
	                    </div>
	                    <div class="tabs group-payway fl">
	                    	<c:forEach items="${ad.paytypeList}" var="pay">
	                    	 	<c:if test="${pay.payType == 1 }">
		                        <i><img src="${oss_url}/static/mobile/css/agent/images/card.png" alt="银行卡" /></i>
	                        	</c:if>
		                        <c:if test="${pay.payType == 2 }">
		                        <i><img src="${oss_url}/static/mobile/css/agent/images/wx.png" alt="微信" /></i>
		                        </c:if>
		                        <c:if test="${pay.payType == 3 }">
	                        	<i><img src="${oss_url}/static/mobile/css/agent/images/jfb.png" alt="支付宝" /></i>
		                        </c:if>
	                        </c:forEach>
	                    </div>
	                    <div class="tabs rate fl">
	                    	<em>${ad.repertory_count} </em>${ad.fvirtualcointype.fShortName}
	                    </div>
	                    <div class="tabs rate fl"><em>${ad.price} </em>CNY</div>
	                    
	                    <div class="clear"></div>  
	                </div>  
	
	                <div class="group-btn clear">
	                    <p data-v-ad45b12e="" class="gray fl">
		                    <em>交易限额</em>
		                    ${ad.order_limit_min}-${ad.order_limit_max}CNY
	                    </p> 
	                    <c:if test="${ad.ad_type == 1}">
	                    	<span class="buyBt fr" onclick="placeOrder(${ad.id})">买入</span>
	                    </c:if>
	                    <c:if test="${ad.ad_type == 2}">
	                    	<span class="buyBt fr" onclick="placeOrder(${ad.id})">卖出</span>
	                    </c:if>       
	                </div>
	            </div>
	            </c:forEach>            
        </div>
    </div>
</section>
<form id="orderForm" method="post" action="/otc/placeOrder.html">
	<input type="hidden" id="coinId" name="coinId"  value="${coinId}">
	<input type="hidden" id="orderModelJson" name="orderModelJson"  value='${orderModelJson}'>
	<input type="hidden" id="adId" name="adId"  value="">
</form>	
	
<input type="hidden" id="pageCount" value="${totalPage}">
<input type="hidden" id="currentPage" value="${currentPage}">
<div id="slide_loading_btn"  onclick="slideLoadMoreInfo()" style=" <c:if test="${totalPage==1 }">display:none</c:if>"><spring:message code="m.security.moremsg" /></div>

<%@include file="../comm/footer.jsp" %>	
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/account.usdtrecharge.js?v=14"></script>
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
<script type="text/javascript">

var isClick = false;

function placeOrder(adId)
{
	//防止重复点击下单
	if(isClick){
		util.layerAlert("", "请勿重复下单提交", 2);
		return;
	}
	isClick = true;
	
	var coinId = $("#coinId").val();
	var orderModelJson = $("#orderModelJson").val();
	var param = {'coinId':coinId,'orderModelJson':orderModelJson,'adId':adId}
	
	jQuery.post('/otc/placeOrder.html', param, function (data) {
    	if (data.code == -1) {
			util.layerAlert("", data.msg, 2);
			//下单失败要允许再次点击下单按钮
			isClick = false;
			return;
		} else {
			window.location.href='/otc/orderRedirect.html?organOrderId='+data.organOrderId;
		}
    }, "json");
}

</script>

</body>
</html>
