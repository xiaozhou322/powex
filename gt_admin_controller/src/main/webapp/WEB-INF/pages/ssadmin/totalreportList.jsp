<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post"
	action="/buluo718admin/totalreportList.html">
	<input type="hidden" name="status" value="${param.status}"><input
		type="hidden" name="logDate" value="${logDate}" /><input
		type="hidden" name="pageNum" value="${currentPage}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /><input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/totalreportList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>日期： <input type="text" name="logDate" class="date"
						readonly="true" value="${logDate }" />
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
			
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">序号</th>
				<th width="60">日期</th>
				<th width="60">当天总金额</th>
				<th width="60">当天总可用金额</th>
				<th width="60">当天总冻结金额</th>
				<th width="60">累计当天充值金额</th>
				<th width="60">累计当天提现金额</th>
				<th width="60">当天总FC</th>
				<th width="60">当天总可用FC</th>
				<th width="60">当天总冻结FC</th>
				<!-- <th width="60">当天总产生豆总量</th> -->
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${totalreportList}" var="totalreport"
				varStatus="num">
				<tr target="sid_user" rel="${totalreport.fid}">
					<td>${num.index +1}</td>
					<td>${totalreport.fdate}</td>
					<td>${totalreport.ftotalAmt}</td>
					<td>${totalreport.ftotalCanAmt}</td>
					<td>${totalreport.ftotalFrozenAmt}</td>
					<td>${totalreport.ftotalRechargeAmt}</td>
					<td>${totalreport.ftotalWithdrawAmt}</td>
					<td>${totalreport.ftotalDou}</td>
					<td>${totalreport.ftotalCanDou}</td>
					<td>${totalreport.ftotalFrozenDou}</td>
				<%-- 	<td>${totalreport.ftotalCreateDou}</td> --%>
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
