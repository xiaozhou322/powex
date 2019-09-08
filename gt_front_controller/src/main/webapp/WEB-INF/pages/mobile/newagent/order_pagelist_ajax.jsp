<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ include file="../comm/include.inc.jsp"%>
<%
	
%>
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
			</em> <em class="time"> <fmt:formatDate value="${vul.createTime }"
					pattern="yyyy-MM-dd HH:mm" />
			</em>
			<c:choose>
				<c:when test="${vul.orderStatus==105 || vul.orderStatus==106}">
					<em class="item_s" style="color: red; padding-left: 0.2rem;">${ vul.orderStatusDesc}</em>
				</c:when>
				<c:when test="${vul.orderStatus==107}">
					<em class="item_s" style="color: #05C78F; padding-left: 0.2rem;">${ vul.orderStatusDesc}</em>
				</c:when>
				<c:otherwise>
					<em class="item_s" style="color: blue; padding-left: 0.2rem;">${ vul.orderStatusDesc}</em>
				</c:otherwise>
			</c:choose>
			<strong class="fr cgreen"> <c:if test="${vul.orderType==1}">
					<span class="cgreen">￥${vul.totalPrice} </span>
				</c:if> <c:if test="${vul.orderType!=1}">
					<span class="cred">￥${vul.totalPrice}</span>
				</c:if> <img src="${oss_url}/static/mobile2018/images/arr.png"
				style="vertical-align: 1px; height: 0.24rem; padding-left: 0.2rem;" />
			</strong>
		</div>



		<div class="itemForm clear">
			<p>
				<span class="item_li"><spring:message code="order.orderid" /></span>
				<span class="item_s">${vul.id}</span>
			</p>
			<p>
				<span class="item_li"><spring:message code="order.type" /></span> <span
					class="item_s">${vul.orderType==1?"买入":"卖出"}</span>
			</p>
			
			<p>
				<span class="item_li">下单人</span> <span class="item_s">${vul.fuser.frealName}</span>
			</p>
			<p>
				<span class="item_li"><spring:message
						code="market.currencytype" /></span> <span class="item_s">${vul.fvirtualcointype.fShortName}</span>
			</p>
			<p>
				<span class="item_li"><spring:message code="order.amount" /></span>
				<span class="item_s"> ${vul.amount}</span>
			</p>
			<p>
				<span class="item_li"><spring:message code="order.price" /></span>
				<span class="item_s">${vul.unitPrice}</span>
			</p>
			<p>
				<span class="item_li"><spring:message code="order.totalprice" /></span>
				<span class="item_s">${vul.totalPrice}</span>
			</p>
			<p>
				<span class="item_li"><spring:message code="order.status" /></span>
				<c:choose>
					<c:when test="${vul.orderStatus==105 || vul.orderStatus==106}">
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
				<span class="item_li"><spring:message code="order.newtime" /></span>
				<span class="item_s" style="width: 8.8%"><fmt:formatDate value="${vul.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
			</p>
			<p>
				<span class="item_li"><spring:message code="备注" /></span> <span
					class="item_s" style="width: 8.8%">${vul.remark}</span>
			</p>
		</div>
	</div>
</c:forEach>
