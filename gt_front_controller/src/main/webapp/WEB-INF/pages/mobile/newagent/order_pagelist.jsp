<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ include file="../comm/include.inc.jsp"%>

<!doctype html>
<html>

<head>
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport"
	content="width = device-width, initial-scale = 1.0, maximum-scale = 1.0, user-scalable = 0" />
<%@include file="../comm/link.inc.jsp"%>
<link href="${oss_url}/static/mobile2018/css/usdtTrade.css?v=20190301163300100" rel="stylesheet" type="text/css" />
<style type="text/css">
.uTrade{
	box-shadow: 0px 2px 9px 0px rgba(70, 90, 111, 0.14);
    margin-bottom: 0.32rem;
    margin-top: 0.2rem;}
.buyTit{
    color: #506FC8;
    font-size: 0.24rem;
}
.buyTit img{
	width: 0.29rem;
    height: 0.27rem;
    vertical-align: -0.05rem;
    }
    .uTrade{
	box-shadow: 0px 2px 9px 0px rgba(70, 90, 111, 0.14);
    margin-bottom: 0.32rem;
    margin-top: 0.2rem;}
    
 .orderType {
	height: 0.82rem;
	line-height: 0.82rem;
	vertical-align: middle;
	border-bottom: 1px solid #ddd;
	overflow: hidden;
	font-size: 0.24rem;
	padding: 0 0.5rem;
	background: #fff;
}

#t_con .itemForm {
	display: none;
	margin-bottom: 0.2rem;
	background: #fff;
}

.itemForm p {
	line-height: 0.72rem;
	color: #8b8b8b;
	vertical-align: middle;
}

.itemForm p:last-child {
	padding-bottom: 0.32rem;
}

.itemForm p .item_li {
	display: inline-block;
	width: 2rem;
	text-indent: 0.5rem;
}

.itemForm p .item_s {
	line-height: 0.72rem;
	color: #8b8b8b;
	vertical-align: middle;
}

.buyBtList {
	display: inline-block;
	padding: 0.1rem 0.2rem;
	background: #2269d4;
	color: #fff;
	line-height: 0.36rem;
	border-radius: 0.2rem;
}
</style>

</style>
</head>

<body>
	<section class="uTrade mg clear">
		<%@include file="../comm/newotc_header.jsp"%>
		<div class="myMsg mg clear">
			<!-- 订单下拉框 -->
			<div class="uTrade_l">
				<div class="securityMain newsCon helpCon">
					<span class="selStyle" onclick="showtab('coinWarp');"> 
						<spring:message code="order.history" />
					</span>
				</div>
			</div>
			<!-- END订单下拉框 -->

			<div class="mg">
				<div class="myRecord_list">
					<div class="record_item">
						<div id="t_con">
						<ul id="slidecontentbox" class=" coin_list ">
							<c:forEach items="${FotcorderList}" var="vul">
								<div class="itemCon">
									<div class="orderType">
										<em> <c:choose>
												<c:when test="${isMerchant}">
													${vul.orderType==1?"出售":"求购 "}
												</c:when>
												<c:otherwise>
													${vul.orderType==1?"买入":"卖出"}
												</c:otherwise>
											</c:choose>
										</em> 
										<em class="time">
											<fmt:formatDate value="${vul.createTime }" pattern="yyyy-MM-dd HH:mm"/>
										</em> 
										<c:choose>
												<c:when
													test="${vul.orderStatus==105 || vul.orderStatus==106}">
													<em class="item_s" style=" color: red;padding-left: 0.2rem;">${ vul.orderStatusDesc}</em>
												</c:when>
												<c:when test="${vul.orderStatus==107}">
													<em class="item_s" style=" color: #05C78F;padding-left: 0.2rem;">${ vul.orderStatusDesc}</em>
												</c:when>
												<c:otherwise>
													<em class="item_s" style=" color: blue;padding-left: 0.2rem;">${ vul.orderStatusDesc}</em>
												</c:otherwise>
											</c:choose>
										<strong class="fr cgreen">
												<c:if test="${vul.orderType==1}">
													<span class="cgreen">￥${vul.totalPrice} </span>
												</c:if>
												<c:if test="${vul.orderType!=1}">
													<span class="cred">￥${vul.totalPrice}</span>
												</c:if>
											<img src="${oss_url}/static/mobile2018/images/arr.png"
											style="vertical-align: 1px; height: 0.24rem; padding-left: 0.2rem;" />
										</strong>
									</div>



									<div class="itemForm clear">
										<p>
											<span class="item_li"><spring:message
													code="order.orderid" /></span> <span class="item_s">${vul.id}</span>
										</p>
										<p>
											<span class="item_li"><spring:message
													code="order.type" /></span> <span class="item_s">${vul.orderType==1?"买入":"卖出"}</span>
										</p>
										
										<p>
											<span class="item_li">下单人</span> <span class="item_s">${vul.fuser.frealName}</span>
										</p>

										<p>
											<span class="item_li"><spring:message
													code="market.currencytype" /></span> <span class="item_s">${vul.fvirtualcointype.fShortName}</span>
										</p>
										<p>
											<span class="item_li"><spring:message
													code="order.amount" /></span> <span class="item_s">
												${vul.amount}</span>
										</p>
										<p>
											<span class="item_li"><spring:message
													code="order.price" /></span> <span class="item_s">${vul.unitPrice}</span>
										</p>
										<p>
											<span class="item_li"><spring:message
													code="order.totalprice" /></span> <span class="item_s">${vul.totalPrice}</span>
										</p>
										<p>
											<span class="item_li"><spring:message
													code="order.status" /></span>
											<c:choose>
												<c:when
													test="${vul.orderStatus==105 || vul.orderStatus==106}">
													<span class="item_s" style="width: 8.8%; color: red;">${ vul.orderStatusDesc}</span>
												</c:when>
												<c:when test="${vul.orderStatus==107}">
													<span class="item_s" style="width: 8.8%; color: #05C78F;">${ vul.orderStatusDesc}</span>
												</c:when>
												<c:otherwise>
													<span class="item_s" style="width: 8.8%; color: blue;">${ vul.orderStatusDesc}</span>
												</c:otherwise>
											</c:choose>
										<p>
										<p>
											<span class="item_li"><spring:message
													code="order.newtime" /></span> <span class="item_s"
												style="width: 8.8%"><fmt:formatDate value="${vul.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
										</p>
										<p>
											<span class="item_li"><spring:message code="备注" /></span> <span
												class="item_s" style="width: 8.8%">${vul.remark}</span>
										</p>
									</div>
								</div>
							</c:forEach>
	</section>

	<div class="coinWarp" id="coinWarp" onclick="hidetab(this.id);"
		style="z-index: 10000">
		<div class="coinLitBox coinLitBox2">
			<ul>
				<li><a href="/order/orderList.html?status=0"> <spring:message
							code="order.unfinish" />
				</a></li>
				<li><a href="/order/orderPageList.html?status=1"> <spring:message
							code="order.history" />
				</a></li>
			</ul>
		</div>
	</div>
<!-- 上滑加载更多  -->
<input type="hidden" id="pageCount" value="${totalPage}">
<input type="hidden" id="currentPage" value="${currentPage}">
<div class="textC" id="slide_loading_btn" style="display:none;" onclick="slideLoadMoreInfo()" style=" <c:if test="${totalPage==1 }">display:none</c:if>"><spring:message code="m.security.moremsg" /></div>

<%@include file="../comm/otc_tabbar.jsp"%>
<script type="text/javascript"	src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
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
	<jsp:include page="../comm/footer.jsp"></jsp:include>
	<script type="text/javascript"
		src="${oss_url}/static/front2018/js/trade/trade.js?v=2"></script>
	<script type="text/javascript">

		$(function() {
			$(".orderType").click(function(event) {
				$(this).siblings(".itemForm").stop().slideToggle(10);
			});
		})
	</script>
</body>

</html>