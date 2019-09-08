<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post"
	action="/buluo718admin/approCoinLogsList.html">
	<input type="hidden" name="fuser" value="${fuser}" />
	<input type="hidden" name="startDate" value="${startDate}" />
	<input type="hidden" name="endDate" value="${endDate}" />
	<input type="hidden" name="pageNum" value="${currentPage}" /> 
	<input type="hidden" name="numPerPage" value="${numPerPage}" /> 
	<input type="hidden" name="orderField" value="${param.orderField}" />
	<input type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/approCoinLogsList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>用户信息：<input type="text" name="fuser" value="${fuser}"
						size="30" /></td>
					<td>创建时间起： <input type="text" name="startDate" class="date"
						readonly="true" value="${startDate }" />
					</td>
					<td>创建时间止： <input type="text" name="endDate" class="date"
						readonly="true" value="${endDate }" />
					</td>
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
		<%-- <ul class="toolBar">
		<shiro:hasPermission name="buluo718admin/activeCoinLogsList.html">
			<li><a class="icon" href="/buluo718admin/activeCoinLogsExport.html"
				target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span>
			</a></li>
		</shiro:hasPermission>	
		</ul> --%>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th align="center" width="20">记录ID</th>
				<th align="center" width="60" orderField="fuser.fid"
					<c:if test='${param.orderField == "fuser.fid" }'> class="${param.orderDirection}"  </c:if>>用户ID</th>
				<th align="center" width="60" orderField="fuser.frealName"
					<c:if test='${param.orderField == "fuser.frealName" }'> class="${param.orderDirection}"  </c:if>>用户姓名</th>
				<th align="center" width="60" orderField="coinType.fShortName"
					<c:if test='${param.orderField == "coinType.fShortName" }'> class="${param.orderDirection}"  </c:if>>拨付币种</th>
				<th align="center" width="60">拨付数量</th>
				<th align="center" width="60" orderField="createTime"
					<c:if test='${param.orderField == "createTime" }'> class="${param.orderDirection}"  </c:if>>创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${approCoinLogsList}" var="approCoinLogs"
				varStatus="num">
				<tr target="sid_logs" rel="${approCoinLogs.id}">
					<td>${approCoinLogs.id}</td>
					<td>${approCoinLogs.fuser.fid}</td>
					<td>${approCoinLogs.fuser.frealName}</td>
					<td>${approCoinLogs.coinType.fShortName}</td>
					<td><fmt:formatNumber value="${approCoinLogs.amount}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td>${approCoinLogs.createTime}</td>
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
