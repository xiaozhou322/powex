<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>
<c:forEach var="v" varStatus="vs" items="${list}">
    <div class="table-item">
        <h3 class="table-item-h3">
			<c:if test="${v.ad_type == 2 }"><span class='colorBlue'><spring:message code="agent.purchase" /></span></c:if>
                  <c:if test="${v.ad_type == 1 }"><span class="redcolor"><spring:message code="market.sell" /></span></c:if>
                  <c:if test="${v.status == 1 }"><span style="color:#6f6f6f;font-size:0.24rem"><spring:message code="ad.shelves.downline" /></span></c:if>
                  <c:if test="${v.status == 0 }"><span style="color:#FF1616;font-size:0.24rem">(<spring:message code="ad.shelves.online" />)</span></c:if>
                  <c:if test="${v.status==0}">
                  <span class="buyBt" onclick="dismount(${v.id})"><spring:message code="ad.shelves.down" /></span>
             </c:if>
		</h3>
        <ul class="fnickUl">
			<li><em>${v.repertory_count }个</em>
				<spring:message code="market.amount" />
			</li>
			<li><em>${v.price }CNY</em>
				<spring:message code="market.price.cny" />
			</li>
			<li class="amountLi" style="text-align: right;">
				<em class="gray">
	                <fmt:formatNumber value="${v.order_limit_min }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="2"/>
	                ~
	                <fmt:formatNumber value="${v.order_limit_max }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="2"/>CNY
	            </em>
				<spring:message code="agent.amount" />
			</li>
		</ul>
   </div>                
</c:forEach>
