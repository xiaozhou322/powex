<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="/buluo718admin/userLookup.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="pageNum" value="${currentPage}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" />
</form>

<div class="pageHeader">
	<form rel="pagerForm" method="post" action="/buluo718admin/userLookup.html"
		onsubmit="return dwzSearch(this, 'dialog');">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>关键字：<input type="text" name="keywords" value="${keywords}"
						size="60" /> [会员名、昵称、真实姓名、身份证号、登陆名、邮箱地址]</td>
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
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">序号</th>
				<th width="100">用户名</th>
				<th width="60">会员状态</th>
				<th width="60">昵称</th>
				<th width="60">真实姓名</th>
				<th width="60">电话号码</th>
				<th width="60">邮箱地址</th>
				<th width="60">身份证号码</th>
				<th width="60">查找带回</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${userList1}" var="user" varStatus="num">
				<tr>
					<td>${num.index +1}</td>
					<td>${user.floginName}</td>
					<td>${user.fstatus_s}</td>
					<td>${user.fnickName}</td>
					<td>${user.frealName}</td>
					<td>${user.ftelephone}</td>
					<td>${user.femail}</td>
					<td>${user.fidentityNo}</td>
					<td><a class="btnSelect"
						href="javascript:$.bringBack({id:'${user.fid}', floginName:'${user.floginName}'})"
						title="查找带回">选择</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="panelBar">
		<div class="pages">
			<span>总共: ${totalCount}条</span>
		</div>
		<div class="pagination" targetType="dialog" totalCount="${totalCount}"
			numPerPage="${numPerPage}" pageNumShown="5"
			currentPage="${currentPage}"></div>
	</div>
</div>
