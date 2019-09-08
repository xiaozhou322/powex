<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post"
	action="/buluo718admin/transportlogList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="pageNum" value="${currentPage}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /><input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/transportlogList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>会员信息：<input type="text" name="keywords"
						value="${keywords}" size="60" /></td>
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
		<shiro:hasPermission name="buluo718admin/transportlogAudit.html">
			<li><a class="edit"
				href="/buluo718admin/transportlogAudit.html?uid={sid_user}"
				target="ajaxTodo" title="确定要审核吗?"><span>审核</span> </a></li>
		</shiro:hasPermission>
		<shiro:hasPermission name="buluo718admin/transportlogCancel.html">		
			<li><a class="edit"
				href="/buluo718admin/transportlogCancel.html?uid={sid_user}"
				target="ajaxTodo" title="确定要取消吗?"><span>取消</span> </a></li>
		</shiro:hasPermission>		
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">序号</th>
				<th width="60">转账会员登录名</th>
				<th width="100">接收会员ID</th>
				<th width="100">数量</th>
				<th width="100">手续费</th>
				<th width="100">状态</th>
				<th width="100">币类型</th>
				<th width="100">创建时间</th>
			</tr>
		</thead>

		<tbody>
			<c:forEach items="${transportlogList}" var="v" varStatus="num">
				<tr target="sid_user" rel="${v.fid}">
					<td>${num.index +1}</td>
					<td>${v.fuser.floginName}</td>
					<td>${v.faddress}</td>
					<td>${v.famount}</td>
					<td>${v.ffees}</td>
					<td>${v.fstatus_s }</td>
					<td>${v.fvirtualcointype.fname }</td>
					<td>${v.fcreatetime}</td>
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
