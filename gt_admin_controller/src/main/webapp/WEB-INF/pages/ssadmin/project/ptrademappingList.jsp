<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="/buluo718admin/ptrademappingList.html">
	<input type="hidden" name="frenchCurrencyId" value="${frenchCurrencyId}" /> 
	<input type="hidden" name="pageNum" value="${currentPage}" /> 
	<input type="hidden" name="numPerPage" value="${numPerPage}" /> 
	<input type="hidden" name="orderField" value="${param.orderField}" /> 
	<input type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/ptrademappingList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>币种简称：<input type="text" name="frenchCurrencyId" value="${frenchCurrencyId}"
						size="60" /></td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">查询</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<shiro:hasPermission name="buluo718admin/auditUser1.html">
				<li><a class="edit"
					href="/buluo718admin/ptrademappingDetail.html?pid={sid_trademapping}&type=2"
					target="dialog" rel=auditUser height="600" width="800"><span>查看</span>
				</a>
				</li>
			</shiro:hasPermission>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th align="center" width="60" orderField="frenchCurrencyType"
					<c:if test='${param.orderField == "frenchCurrencyType" }'> class="${param.orderDirection}"  </c:if>>法币类型</th>
				<th align="center" width="60" orderField="tradeCurrencyType"
					<c:if test='${param.orderField == "tradeCurrencyType" }'> class="${param.orderDirection}"  </c:if>>交易币类型</th>
				<th align="center" width="60" orderField="projectId"
					<c:if test='${param.orderField == "projectId" }'> class="${param.orderDirection}"  </c:if>>项目方</th>
				<th align="center" width="60">交易时间</th>
				<th align="center" width="60">开盘价</th>
				<th align="center" width="60">最小挂单单价</th>
				<th align="center" width="60">最大挂单单价</th>
				<th align="center" width="60">最小挂单金额</th>
				<th align="center" width="60">最大挂单金额</th>
				<th align="center" width="60">最小挂单数量</th>
				<th align="center" width="60">最大挂单数量</th>
				<th align="center" width="60">保证金缴纳状态</th>
				<th align="center" width="60">审核状态</th>
				<th align="center" width="80">备注</th>
				<th align="center" width="60" orderField="createTime"
					<c:if test='${param.orderField == "createTime" }'> class="${param.orderDirection}"  </c:if>>创建时间</th>
				<th align="center" width="60" orderField="updateTime"
					<c:if test='${param.orderField == "updateTime" }'> class="${param.orderDirection}"  </c:if>>修改时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${ptrademappingList}" var="ptrademapping" varStatus="num">
				<tr target="sid_trademapping" rel="${ptrademapping.id}">
					<td>${ptrademapping.frenchCurrencyType.fShortName}</td>
					<td>${ptrademapping.tradeCurrencyType.shortName}</td>
					<td>${ptrademapping.projectId.fid}</td>
					<td>${ptrademapping.tradeTime}</td>
					<td><ex:DoubleCut value="${ptrademapping.openPrice}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="6"/></td>
					<td><ex:DoubleCut value="${ptrademapping.minEntrustPrice}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="6"/></td>
					<td><ex:DoubleCut value="${ptrademapping.maxEntrustPrice}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="6"/></td>
					<td><ex:DoubleCut value="${ptrademapping.minEntrustMoney}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="6"/></td>
					<td><ex:DoubleCut value="${ptrademapping.maxEntrustMoney}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="6"/></td>
					<td><ex:DoubleCut value="${ptrademapping.minEntrustQty}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><ex:DoubleCut value="${ptrademapping.maxEntrustQty}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td>
						<c:choose>
							<c:when test="${ptrademapping.depositStatus == 1}">未缴纳</c:when>
							<c:when test="${ptrademapping.depositStatus == 2}">已缴纳</c:when>
						</c:choose>
					</td>
					<td>
						<c:choose>
							<c:when test="${ptrademapping.auditStatus == 101}">待审核</c:when>
							<c:when test="${ptrademapping.auditStatus == 102}">审核通过</c:when>
							<c:when test="${ptrademapping.auditStatus == 103}">审核不通过</c:when>
						</c:choose>
					</td>
					<td>${ptrademapping.remark}</td>
					<td>${ptrademapping.createTime}</td>
					<td>${ptrademapping.updateTime}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="panelBar">
		<div class="pages">
			<span>总共: ${totalCount}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${totalCount}"
			numPerPage="${numPerPage}" pageNumShown="5"
			currentPage="${currentPage}"></div>
	</div>
</div>
