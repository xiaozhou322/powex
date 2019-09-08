<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>



<!-- <table class="table">
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
</table> -->

	<c:forEach items="${list}" var="v">
			<table style="border:none;">
				<tr class="tr_spectial">
					<td colspan="4" class="td_01 Color333"><spring:message code="financial.usdtrecharge.rechargetime" />：<fmt:formatDate value="${v.fcreateTime }" pattern="yyyy-MM-dd HH:mm:ss"/>   <c:if test="${(v.fstatus==1 || v.fstatus==2)}">
								<span class="fr" style="color:#999;">
                                    <a class="rechargecancel opa-link cblue" href="javascript:void(0);" data-fid="${v.fid }"><spring:message code="financial.cnyrecharge.cancel" /></a>
                                    
    								<c:if test="${v.fstatus==1}">|
    								<a class="rechargesub opa-link cblue" href="javascript:void(0);" data-fid="${v.fid }"><spring:message code="financial.cnyrecharge.submit" /></a>
								</c:if>
								</c:if>
                                </span></td>
				</tr>
				<tr>
					<td class="td_01 Color333">${v.fid }</td>
					<td class="colorRed">￥${v.famount }($<ex:DoubleCut value="${v.famount/6.5 }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>)</td>
					<td class="Color333">${v.fremittanceType }</td>
<!-- 					<td class="td_02"><spring:message code="financial.cnyrecharge.status${v.fstatus }" /></td>
 -->                   
				</tr>

				<tr class="tr_spectial2">
					<td class="td_01"><spring:message code="financial.usdtrecharge.orderno" /></td>
					<td><spring:message code="financial.usdtrecharge.rechargeamount" /></td>
					<td><spring:message code="financial.usdtrecharge.rechargemode" /></td>
<!-- 					<td class="td_02"><spring:message code="financial.usdtrecharge.rechargestatus" /></td>
 -->                    
				</tr>
				<tr>
		            <td class="td_01 Color333"><spring:message code="financial.cnyrecharge.status${v.fstatus }" /></td>
		            <td class=" Color333">${v.fremark }</td>
		            <td></td>
		        </tr>
		        <tr>
		            <td class="td_01"><spring:message code="financial.usdtrecharge.rechargestatus" /></td>
		            <td><spring:message code="financial.usdtrecharge.remarknum" /></td>
		            <td></td>
		        </tr>
			</table>
<!--             <p class="p_01">${v.fremark }</p>
            <p class="p_01 p_02"><spring:message code="financial.usdtrecharge.remarknum" /></p>
 -->
			</c:forEach>

<input type="hidden" value="${currentPage}" name="currentPage"
	id="currentPage"></input>
