<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="/buluo718admin/userLendsList.html">
	<input type="hidden" name="status" value="${param.status}"> <input type="hidden"
		name="keywords" value="${keywords}" /><input type="hidden"
		name="pageNum" value="${currentPage}" /> <input type="hidden"
		name="numPerPage" value="${numPerPage}" /> <input type="hidden"
		name="orderField" value="${param.orderField}" /><input type="hidden"
		name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/userLendsList.html" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>会员信息：<input type="text" name="keywords"
						value="${keywords}" size="60" /> <input type="hidden" name="type"
						value="${type}" /></td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">查询</button>
							</div>
						</div></li>
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
				<th width="60">UID</th>
				<th width="60">登陆名</th>
				<th width="60">会员真实姓名</th>
				<th width="60">人民币或虚拟币</th>
				<th width="60">提交总金额</th>
				<th width="60">成交总金额</th>
				<th width="60">未成交金额</th>
				<th width="60">总贷金额</th>
				<th width="60">已还金额</th>
				<th width="60">未还金额</th>
				<th width="60">总手续费</th>
				<th width="60">已还手续费</th>
				<th width="60">未还手续费</th>
				<th width="60">本人收益手续费</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${userLendsList}" var="userLends"
				varStatus="num">
				<tr target="sid_user">
					<td>${num.index +1}</td>
					<td>${userLends.fuserid}</td>
					<td>${userLends.floginName}</td>
					<td>${userLends.fRealName}</td>
					<td>${userLends.fcnyOrCoin}</td>
					<td><fmt:formatNumber value="${userLends.fAmount}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><fmt:formatNumber value="${userLends.fSuccessAmount}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><fmt:formatNumber value="${userLends.noSuccessAmount}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><fmt:formatNumber value="${userLends.fTotalAmount}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><fmt:formatNumber value="${userLends.fReturnAmount}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><fmt:formatNumber value="${userLends.noReturnAmount}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><fmt:formatNumber value="${userLends.ftotalfees}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><fmt:formatNumber value="${userLends.returnFees}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><fmt:formatNumber value="${userLends.noReturnFees}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><fmt:formatNumber value="${userLends.ownerFees}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
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
