<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>
<%

%>



<div class="col-xs-12">
	<div class="panel">
		<div class="panel-heading">
			<span class="text-danger"><spring:message code="entrust.title.entrust" /></span>
			<span class="pull-right">
               <a class="allcancel opa-link" href="javascript:void(0);" data-value="${ftrademapping.fid }"><spring:message code="entrust.entrust.cancelall" /></a>
            </span>
		</div>
		<div  id="fentrustsbody0" class="panel-body">
			<table class="table">
			
			<tr>
				<td><spring:message code="entrust.entrust.date" /></td>
				<td><spring:message code="entrust.entrust.type" /></td>
				<td><spring:message code="entrust.entrust.price" /></td>
				<td><spring:message code="entrust.entrust.qty" /></td>
				<td><spring:message code="entrust.entrust.amount" /></td>
				<td><spring:message code="entrust.deal.qty" /></td>
				<td><spring:message code="entrust.deal.amount" /></td>
				<td><spring:message code="entrust.deal.fee" /></td>
				<td><spring:message code="market.entruststatus" /></td>
				<td class="width-65"><spring:message code="market.entrustaction" /></td>
			</tr>
				<c:if test="${fn:length(fentrusts1)==0 }">
					<tr>
						<td colspan="10" class="no-data-tips">
							<span>
								<spring:message code="json.trade.noord" />
							</span>
						</td>
					</tr>
				</c:if>
					
				<c:forEach items="${fentrusts1 }" var="v" varStatus="vs">
					<tr>
						<td class="gray"><fmt:formatDate value="${v.fcreateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td class="${v.fentrustType==0?'text-success':'text-danger' }"><spring:message code="enum.entrust.${v.fentrustType}" />${v.fisLimit==true?'[市价]':'' }</td>
						<td>${coin1.fSymbol}<ex:DoubleCut value="${v.fprize }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/></td>
						<td>${coin2.fSymbol}<ex:DoubleCut value="${v.fcount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount2 }"/></td>
						<td>${coin1.fSymbol}<ex:DoubleCut value="${v.famount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/></td>
						<td>${coin2.fSymbol}<ex:DoubleCut value="${v.fcount-v.fleftCount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount2 }"/></td>
						<td>${coin1.fSymbol}<ex:DoubleCut value="${v.fsuccessAmount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/></td>
						<c:choose>
						<c:when test="${v.fentrustType==0 }">
						<td>${coin2.fSymbol}<ex:DoubleCut value="${v.ffees-v.fleftfees}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/></td>
						</c:when>
						<c:otherwise>
						<td>${coin1.fSymbol}<ex:DoubleCut value="${v.ffees-v.fleftfees}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/></td>
						</c:otherwise>
						</c:choose>
						<td>
						<spring:message code="enum.entrust.status.${v.fstatus}" />
						</td>
						<td  class="opa-link">
						<c:if test="${v.fstatus==1 || v.fstatus==2}">
						<a href="javascript:void(0);" class="tradecancel opa-link" data-value="${v.fid}"><spring:message code="entrust.entrust.cancel" /></a>
						</c:if>
						<c:if test="${v.fstatus==3}">
						<a href="javascript:void(0);" class="tradelook opa-link" data-value="${v.fid}"><spring:message code="entrust.entrust.view" /></a>
						</c:if>
						</td>
                          </tr>
			</c:forEach>
				
			</table>
		</div>
	</div>
</div>

