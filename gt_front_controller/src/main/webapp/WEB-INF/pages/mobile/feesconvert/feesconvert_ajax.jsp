<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ include file="../comm/include.inc.jsp"%>

<c:forEach var="v" varStatus="vs" items="${list}">
	<div class="table-item">
		<h3 class="feesconTit">
			<span class="fl">
				时间：<fmt:formatDate value="${v.createTime }" pattern="yyyy-MM-dd"/>
			</span>
			<span class="fr">
				订单ID：${v.id }
			</span>
		</h3>
		<ul class="fnickUl">
			<li><em style="color: #ffb266;">${v.bfscAmount}</em>
			BFSC数量
			</li>
			<li><em style="color: #ffb266;">${v.usdtAmount}</em>
			USDT数量
			</li>
			<li><em style="color: #74c40d;">$${v.bfscPrice }</em>
			BFSC价格
			</li>
		</ul>
		<div>
			<p class="fl order_limit">
				状态&nbsp;&nbsp;
				<c:if test="${v.status == 1 }">
					<span style="color: #488bef;">未处理</span>
				</c:if>
				<c:if test="${v.status == 2 }">
					<span style=" color: #666;">已处理</span>
				</c:if>
			</p>
			<p class="fr">
				<c:if test="${v.status == 1 }">
					<span class="buyBt" style=" background: #488bef; color: #fff;" onclick="handleOrder(${v.id })">
						处理订单
					</span>  
				</c:if>
				<c:if test="${v.status == 2 }">
					<span class="buyBt" style=" background: #ccc; color: #fdfdfd; ">
						订单已处理
					</span> 
				</c:if>
			</p>
		</div>
	</div>
</c:forEach>
