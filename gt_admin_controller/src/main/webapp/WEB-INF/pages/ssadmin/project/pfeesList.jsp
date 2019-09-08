<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="/buluo718admin/pfeesList.html">
	<input type="hidden" name="projectId" value="${projectId}" /> 
	<input type="hidden" name="pageNum" value="${currentPage}" /> 
	<input type="hidden" name="numPerPage" value="${numPerPage}" /> 
	<input type="hidden" name="orderField" value="${param.orderField}" /> 
	<input type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/pfeesList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>项目方ID：<input type="text" name="projectId" value="${projectId}"
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
					href="/buluo718admin/pfeesDetail.html?pid={sid_fees}&type=2"
					target="dialog" rel=auditUser height="400" width="800"><span>查看</span>
				</a>
				</li>
			</shiro:hasPermission>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="60" orderField="projectId"
					<c:if test='${param.orderField == "projectId" }'> class="${param.orderDirection}"  </c:if>>项目方</th>
				<th width="60">交易市场</th>
				<th width="60">买入手续费</th>
				<th width="60">卖出手续费</th>
				<th width="60">审核状态</th>
				<th width="60" orderField="createTime"
					<c:if test='${param.orderField == "createTime" }'> class="${param.orderDirection}"  </c:if>>创建时间</th>
				<th width="60" orderField="updateTime"
					<c:if test='${param.orderField == "updateTime" }'> class="${param.orderDirection}"  </c:if>>修改时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pfeesList}" var="pfees" varStatus="num">
				<tr target="sid_fees" rel="${pfees.id}">
					<td>${pfees.projectId.fid}</td>
					<td>${pfees.trademappingStr}</td>
					<td><ex:DoubleCut value="${pfees.buyFee}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="5"/></td>
					<td><ex:DoubleCut value="${pfees.sellFee}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="5"/></td>
					<td>
						<c:choose>
							<c:when test="${pfees.auditStatus == 101}">待审核</c:when>
							<c:when test="${pfees.auditStatus == 102}">审核通过</c:when>
							<c:when test="${pfees.auditStatus == 103}">审核不通过</c:when>
						</c:choose>
					</td>
					<td>${pfees.createTime}</td>
					<td>${pfees.updateTime}</td>
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
