<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="/buluo718admin/balancetypeList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="pageNum" value="${currentPage}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /><input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/balancetypeList.html" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td></td>
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
			<li><a class="add"
				href="/buluo718admin/goBalancetypeJSP.html?url=ssadmin//addBalancetype"
				height="300" width="900" target="dialog" rel="addBalancetype"><span>新增</span>
			</a></li>
			<li><a class="delete"
				href="/buluo718admin/deleteBalancetype.html?uid={sid_user}"
				target="ajaxTodo" title="确定要删除吗?"><span>删除</span> </a></li>
			<li><a class="edit"
				href="/buluo718admin/goBalancetypeJSP.html?url=ssadmin//updateBalancetype&uid={sid_user}"
				height="300" width="900" target="dialog" rel="updateBalancetype"><span>修改</span>
			</a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">序号</th>
				<th width="60">周期名称</th>
				<th width="60">周期天数</th>
				<th width="60">定存利率</th>
				<th width="60">虚拟币类型</th>
				<!-- <th width="60">收益类型</th> -->
				<th width="60">创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${balancetypeList}" var="balancetype"
				varStatus="num">
				<tr target="sid_user" rel="${balancetype.fid}">
					<td>${num.index +1}</td>
					<td>${balancetype.fname}</td>
					<td>${balancetype.fday}天</td>
					<td><fmt:formatNumber value="${balancetype.frate}" pattern="##.######" maxIntegerDigits="10" maxFractionDigits="6"/></td>
					<td>${balancetype.fvirtualcointype.fname}</td>
					<%-- <td>${balancetype.frecType_s}</td> --%>
					<td>${balancetype.fcreatetime}</td>
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
