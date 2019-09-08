<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="/buluo718admin/balanceearningList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="pageNum" value="${currentPage}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /><input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/balanceearningList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>会员信息：<input type="text" name="keywords"  value="${keywords}"
						size="60"/>
					</td>
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
				<th width="60">登录名</th>
				<th width="60">昵称</th>
				<th width="60">真实姓名</th>
				<th width="60">收益类型</th>
				<th width="60">币种</th>
				<th width="60">余额宝数量</th>
				<th width="60">收到数量</th>
				<th width="60">状态</th>
				<th width="60">创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${balanceearningList}" var="balanceearningList" varStatus="num">
				<tr target="sid_user" rel="${balanceearningList.fid}">
					<td>${num.index +1}</td>
					<td>${balanceearningList.fuser.floginName}</td>
					<td>${balanceearningList.fuser.fnickName}</td>
					<td>${balanceearningList.fuser.frealName}</td>
					<td>${balanceearningList.ftype_s}</td>
					<td>${balanceearningList.fvirtualcointype.fname}</td>
					<td><fmt:formatNumber value="${balanceearningList.fbalanceqty}" pattern="##.##" maxIntegerDigits="10" maxFractionDigits="6"/></td>
					<td><fmt:formatNumber value="${balanceearningList.freceiveqty}" pattern="##.##" maxIntegerDigits="10" maxFractionDigits="6"/></td>
					<td>${balanceearningList.fstatus_s}</td>
					<td>${balanceearningList.fcreatetime}</td>
					
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
