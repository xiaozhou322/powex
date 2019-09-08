<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>

<table class="table">
	<tr>
		<th><spring:message code="financial.cnyrecharge.orderno" /></th>
		<th><spring:message code="financial.cnyrewithdrawal.remarknum" /></th>
		<th><spring:message code="financial.cnyrecharge.rechargetime" /></th>
		<th><spring:message code="financial.cnyrecharge.rechargemode" /></th>
		<th><spring:message code="financial.cnyrecharge.rechargeamount" /></th>
		<th><spring:message code="financial.cnyrecharge.rechargestatus" /></th>
		<th><spring:message code="financial.cnyrecharge.rechargeoper" /></th>
	</tr>
	<c:forEach items="${list}" var="v">
		<tr>
			<td class="gray">${v.fid }</td>
			<td>${v.fremark }</td>
			<td><fmt:formatDate value="${v.fcreateTime }"
					pattern="yyyy-MM-dd HH:mm:ss" />
			</td>
			<td>${v.fremittanceType }</td>
			<td>${v.famount }</td>
			<td><spring:message code="financial.cnyrecharge.status${v.fstatus }" /></td>
			<td class="opa-link"><c:if
					test="${(v.fstatus==1 || v.fstatus==2)}">
					<a class="rechargecancel opa-link" href="javascript:void(0);"
						data-fid="${v.fid }"><spring:message code="financial.cnyrecharge.cancel" /></a>
					<c:if test="${v.fstatus==1}">
						&nbsp;|&nbsp;&nbsp;<a class="rechargesub opa-link"
							href="javascript:void(0);" data-fid="${v.fid }"><spring:message code="financial.cnyrecharge.submit" /></a>
					</c:if>
				</c:if> <c:if test="${(v.fstatus==3 || v.fstatus==4)}">
				--
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

<input type="hidden" value="${cur_page }" name="currentPage"
	id="currentPage"></input>
<div class="text-right">${pagin }</div>