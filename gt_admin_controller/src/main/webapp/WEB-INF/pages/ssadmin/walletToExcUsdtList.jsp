<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post"
	action="/buluo718admin/walletToExcUsdtList.html">
	<input type="hidden" name="userId" value="${userId}" />
	<input type="hidden" name="logDate1" value="${logDate1}" />
	<input type="hidden" name="logDate2" value="${logDate2}" />
	<input type="hidden" name="pageNum" value="${currentPage}" /> 
	<input type="hidden" name="numPerPage" value="${numPerPage}" /> 
	<input type="hidden" name="orderField" value="${param.orderField}" />
	<input type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/walletToExcUsdtList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>用户ID：<input type="text" name="userId" value="${userId}"
						size="10" /></td>
					<td>开始时间起： <input type="text" name="logDate1" class="date"
						readonly="true" value="${logDate1 }" />
					</td>
					<td>开始时间止： <input type="text" name="logDate2" class="date"
						readonly="true" value="${logDate2 }" />
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
		<ul class="toolBar">
		<shiro:hasPermission name="buluo718admin/walletToExcUsdtList.html">
			<li><a class="icon" href="/buluo718admin/drawAccountsStatisticsExport.html?shortName=USDT&type=1"
				target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span>
			</a></li>
		</shiro:hasPermission>	
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th align="center" width="60">用户ID</th>
				<th align="center" width="60">用户姓名</th>
				<th align="center" width="60">划账币种</th>
				<th align="center" width="60">划账类型</th>
				<th align="center" width="60">划账总数</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${walletToExcUsdtList}" var="walletToExchange" varStatus="num">
				<tr target="sid_user" rel="${walletToExchange.userId}">
					<td>${walletToExchange.userId}</td>
					<td>${walletToExchange.userName}</td>
					<td>${walletToExchange.coinShortName}</td>
					<td>
						<c:choose>
							<c:when test="${walletToExchange.type == 1}">钱包——>交易所</c:when>
							<c:when test="${walletToExchange.type == 2}">交易所——>钱包</c:when>
						</c:choose>
					</td>
					<td><fmt:formatNumber value="${walletToExchange.totalAmount}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
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
