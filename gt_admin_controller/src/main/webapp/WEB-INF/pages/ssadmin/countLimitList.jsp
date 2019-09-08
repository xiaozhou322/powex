<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="/buluo718admin/countLimitList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="pageNum" value="${currentPage}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /><input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/countLimitList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>IP地址：<input type="text" name="keywords"
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
		<shiro:hasPermission name="buluo718admin/deleteCountLimit.html">
				<li><a class="delete"
					href="/buluo718admin/deleteCountLimit.html?uid={sid_user}"
					target="ajaxTodo" title="确定要解除限制吗?"><span>解除限制</span> </a>
				</li>
		</shiro:hasPermission>		
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">序号</th>
				<th width="60" orderField="ftype"
					<c:if test='${param.orderField == "ftype" }'> class="${param.orderDirection}"  </c:if>>限制类型</th>
				<th width="60" orderField="fip"
					<c:if test='${param.orderField == "fip" }'> class="${param.orderDirection}"  </c:if>>IP地址</th>
				<th width="60">次数</th>	
				<th width="60" orderField="fcreateTime"
					<c:if test='${param.orderField == "fcreateTime" }'> class="${param.orderDirection}"  </c:if>>创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${countLimitList}" var="countlimit" varStatus="num">
				<tr target="sid_user" rel="${countlimit.fid}">
					<td>${num.index +1}</td>
					<td>${countlimit.ftype_s}</td>
					<td>${countlimit.fip}</td>
					<td>${countlimit.fcount}</td>
					<td>${countlimit.fcreateTime}</td>
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
