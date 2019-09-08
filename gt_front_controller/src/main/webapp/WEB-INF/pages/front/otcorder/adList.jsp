<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>

<!doctype html>
<html>
<head> 
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp" %>
<link href="${oss_url}/static/front2018/css/usdtTrade.css?v=12" rel="stylesheet" type="text/css" />
</head>
<body class="">
<%@include file="../comm/otc_header.jsp" %>
<section class="uTrade mg clear">
        <div class="uTrade_r">
            <div class="tables-title clear">
                <span class="tit fl">承兑商</span>
                <span class="tit fl"><spring:message code="agent.payment" /></span>
                 <span class="tit fl"><spring:message code="market.amount" /></span>
                <span class="tit fl"><spring:message code="market.price.cny" /></span>
                <span class="tit fl"><spring:message code="agent.amount" /></span>
                <span class="tit fl"><spring:message code="market.entrustaction" /></span>
            </div>
            <div class="agentCon">
               <c:forEach items="${adList}" var="ad">
                <div class="table-item">
                    <div class="tabs userFace fl">
                        <span class="fl iconTag">
                        	<c:if test="${fn:length(ad.user.frealName)>=1}">
                        		${fn:substring(ad.user.frealName,0,1)}
                        	</c:if>
                       </span>
                        <div class="uName fl">
                            <a href="javascript:;">
                            	<c:if test="${fn:length(ad.user.frealName)>0} && ${fn:length(ad.user.frealName)<=2}">
                        		${fn:substring(ad.user.frealName,0,2)}
	                        	</c:if>
	                        	<c:if test="${fn:length(ad.user.frealName)>2}">
	                        		${fn:substring(ad.user.frealName,0,3)}...
	                        	</c:if>
	                        </a>
                            <c:if test="${ad.status == 0 }">
                           		<img src="${oss_url}/static/front2018/images/v.png" alt="" />
                            </c:if>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="tabs group-payway fl">
                       <c:forEach items="${ad.paytypeList}" var="pay">
                    	 	<c:if test="${pay.payType == 1 }">
	                        <i><img src="${oss_url}/static/front2018/images/card.png" alt="银行卡" /></i>
                        	</c:if>
	                        <c:if test="${pay.payType == 2 }">
	                        <i><img src="${oss_url}/static/front2018/images/wx.png" alt="微信" /></i>
	                        </c:if>
	                        <c:if test="${pay.payType == 3 }">
                        	<i><img src="${oss_url}/static/front2018/images/jfb.png" alt="支付宝" /></i>
	                        </c:if>
                       </c:forEach>
                    </div>
                    <div class="tabs rate fl"><em>${ad.repertory_count}</em>${ad.fvirtualcointype.fShortName}</div>
                    <div class="tabs rate fl">
                    <em>${ad.price}</em>CNY<br>                    
                    </div>
					 <div class="tabs rate fl">
                        <em data-v-ad45b12e="" class="gray">${ad.order_limit_min}-${ad.order_limit_max}CNY</em>
                    </div>
                    <div class="tabs fl">
                    <c:if test="${ad.ad_type == 1}">
                    	<span class="buyBt" onclick="placeOrder(${ad.id})"><spring:message code="market.buy" /></span>
                    </c:if>
                    <c:if test="${ad.ad_type == 2}">
                    	<span class="buyBt" onclick="placeOrder(${ad.id})"><spring:message code="market.sell" /></span>
                    </c:if>
                    </div>

                    <div class="clear"></div>
                 </div>                
                 </c:forEach>
            </div>
            <c:if test="${totalPage > 1 }">
        	<div class="page">
				${pagin }
			</div>
			</c:if>
			<input type="hidden" value="${currentPage}" id="currentPage">
        </div>
    </section>

<div>
	<form id="orderForm" method="post" action="/otc/placeOrder.html">
		<input type="hidden" id="coinId" name="coinId"  value="${coinId}">
		<input type="hidden" id="orderModelJson" name="orderModelJson"  value='${orderModelJson}'>
		<input type="hidden" id="adId" name="adId"  value="">
	</form>	
	
</div>

	<div class="goTop">
		<!-- 返回顶部 -->
	</div>

	<%@include file="../comm/otc_footer.jsp"%>
	<script type="text/javascript"
		src="${oss_url}/static/front2018/js/comm/msg.js"></script>
	<script type="text/javascript"
		src="${oss_url}/static/front2018/js/plugin/jquery.qrcode.min.js"></script>
</body>
</html>

<script type="text/javascript">
	/* function placeOrder(adId) {
		$("#adId").val(adId);
		
		$("#orderForm").submit();
	} */
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
