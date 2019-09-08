<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="/buluo718admin/limittradeList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="pageNum" value="${currentPage}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/limittradeList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>关键词：<input type="text" name="keywords" value="${keywords}"
						size="60" /></td>
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
			<shiro:hasPermission name="buluo718admin/addLimittrade">
				<li><a class="add"
					href="/buluo718admin/goLimittradeJSP.html?url=ssadmin//addLimittrade&type=2"
					height="300" width="800" target="dialog" rel="addLimittrade"><span>新增</span>
				</a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="buluo718admin/deleteLimittrade">
				<li><a class="delete"
					href="/buluo718admin/deleteLimittrade.html?uid={sid_user}"
					target="ajaxTodo" title="确定要删除吗?"><span>删除</span> </a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="buluo718admin/updateLimittrade">
				<li><a class="edit"
					href="/buluo718admin/goLimittradeJSP.html?url=ssadmin//updateLimittrade&uid={sid_user}"
					height="300" width="800" target="dialog" rel="updateLimittrade"><span>修改</span>
				</a>
				</li>
			</shiro:hasPermission>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">序号</th>
				<th width="60">法币名称</th>
				<th width="60">交易币名称</th>
				<th width="60">最低价格</th>
				<th width="60">最高价格</th>
				<th width="60">涨跌比例</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${limittradeList}" var="limittrade" varStatus="num">
				<tr target="sid_user" rel="${limittrade.fid}">
					<td>${num.index +1}</td>
					<td>${limittrade.ftrademapping.fvirtualcointypeByFvirtualcointype1.fname}</td>
					<td>${limittrade.ftrademapping.fvirtualcointypeByFvirtualcointype2.fname}</td>
					<td>${limittrade.fdownprice}</td>
					<td>${limittrade.fupprice}</td>
					<td>${limittrade.fpercent}</td>
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
