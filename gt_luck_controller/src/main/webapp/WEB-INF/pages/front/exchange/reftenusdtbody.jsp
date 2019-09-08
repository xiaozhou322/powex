<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>



<table class="table">
	<tr class="tr_1">
		<th><spring:message code="financial.usdtrecharge.orderno" /></th>
		<th><spring:message code="financial.usdtrecharge.remarknum" /></th>
		<th><spring:message code="financial.usdtrecharge.rechargetime" /></th>
		<th><spring:message code="financial.usdtrecharge.rechargemode" /></th>
		<th><spring:message code="financial.usdtrecharge.rechargeamount" /></th>
		<th><spring:message code="financial.usdtrecharge.rechargestatus" /></th>
	</tr>
	<c:forEach items="${list}" var="v">
		<tr class="tr_1">
			<td class="gray">${v.fid }</td>
			<td>${v.fremark }</td>
			<td><fmt:formatDate value="${v.fcreateTime }"
					pattern="yyyy-MM-dd HH:mm:ss" />
			</td>
			<td>${v.fremittanceType }</td>
			<td>￥${v.famount }($<ex:DoubleCut value="${v.famount/6.5 }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>)</td>
			<td><c:if test="${v.fstatus!=1}"><spring:message code="financial.cnyrecharge.status${v.fstatus }" /></c:if>
				<c:if test="${(v.fstatus==1 || v.fstatus==2)}">
				<a class="rechargecancel opa-link" href="javascript:void(0);"
					data-fid="${v.fid }"><spring:message code="financial.cnyrecharge.cancel" /></a>
				<c:if test="${v.fstatus==1}">
					&nbsp;|&nbsp;&nbsp;<a class="rechargesub opa-link"
						href="javascript:void(0);" data-fid="${v.fid }"><spring:message code="financial.cnyrecharge.submit" /></a>
				</c:if>
			</c:if>
			</td>

		</tr>
	</c:forEach>
	<c:if test="${fn:length(list)==0 }">
		<tr>
			<td colspan="6" class="no-data-tips" align="center"><span>
					<spring:message code="financial.cnyrecharge.norecharge" /> </span></td>
		</tr>
	</c:if>
</table>

<input type="hidden" value="${currentPage}" name="currentPage"
	id="currentPage"></input>
<div class="text-center">${pagin }</div>