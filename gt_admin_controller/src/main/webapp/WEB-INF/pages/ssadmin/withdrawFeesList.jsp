<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post"
	action="/buluo718admin/withdrawFeesList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="pageNum" value="${currentPage}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" />
</form>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/withdrawFeesList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>会员等级：<input type="text" name="keywords"
						value="${keywords}" size="60" class="digits" /></td>
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
			<shiro:hasPermission name="buluo718admin/updateWithdrawFees.html">
				<li><a class="edit"
					href="/buluo718admin/goWithdrawFeesJSP.html?url=ssadmin//updateWithdrawFees&uid={sid_user}"
					height="200" width="800" target="dialog" rel="updateWithdrawFees"><span>修改</span>
				</a>
				</li>
			</shiro:hasPermission>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="75">
		<thead>
			<tr>
				<th width="20">序号</th>
				<th width="60">会员等级</th>
				<th width="60">CNY提现手续费</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${withdrawFeesList}" var="withdrawFees"
				varStatus="num">
				<tr target="sid_user" rel="${withdrawFees.fid}">
					<td>${num.index +1}</td>
					<td>${withdrawFees.flevel}</td>
					<td>${withdrawFees.ffee}</td>
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
