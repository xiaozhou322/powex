<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post"
	action="/buluo718admin/autotradeList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="type" value="${ftype}" /><input type="hidden"
		name="pageNum" value="${currentPage}" /> <input type="hidden"
		name="numPerPage" value="${numPerPage}" /> <input type="hidden"
		name="orderField" value="${param.orderField}" /><input type="hidden"
		name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/autotradeList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>
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
			<li><a class="add"
				href="/buluo718admin/goAutotradeJSP.html?url=ssadmin//addAutotrade" height="400"
				width="800" target="dialog" rel="addAutotrade"><span>新增</span> </a></li>
			<li><a class="delete"
				href="/buluo718admin/deleteAutotrade.html?uid={sid_user}" target="ajaxTodo"
				title="确定要删除吗?"><span>删除</span> </a></li>
			<li><a class="edit"
				href="/buluo718admin/goAutotradeJSP.html?url=ssadmin//updateAutotrade&uid={sid_user}"
				height="400" width="800" target="dialog" rel="updateAutotrade"><span>修改</span>
			</a></li>
			<li><a class="delete"
				href="/buluo718admin/doAutotrade.html?uid={sid_user}&type=1" target="ajaxTodo"
				title="确定要禁用吗?"><span>禁用</span> </a></li>
			<li><a class="edit"
				href="/buluo718admin/doAutotrade.html?uid={sid_user}&type=2" target="ajaxTodo"
				title="确定要启用吗?"><span>启用</span> </a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">序号</th>
				<th width="60">UID</th>
				<th width="60">类型</th>
				<th width="60">价格同步类型</th>
				<th width="60">状态</th>
				<th width="60">虚拟币名称</th>
				<th width="60">最小交易数量</th>
				<th width="60">最大交易数量</th>
				<th width="60">最小浮动价</th>
				<th width="60">最大浮动价</th>
				<th width="60">多少分钟一次</th>
				<th width="60">开始前随机停几秒</th>
				<th width="60">创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${autotradeList}" var="autotrade"
				varStatus="num">
				<tr target="sid_user" rel="${autotrade.fid}">
					<td>${num.index +1}</td>
					<td>${autotrade.fuser.fid}</td>
					<td>${autotrade.ftype_s}</td>
					<td>${autotrade.fsynType_s}</td>
					<td>${autotrade.fstatus_s}</td>
					<td>${autotrade.ftrademapping.fid}</td>
					<td><fmt:formatNumber value="${autotrade.fminqty}" pattern="##.######" maxIntegerDigits="10" maxFractionDigits="6"/></td>
					<td><fmt:formatNumber value="${autotrade.fmaxqty}" pattern="##.######" maxIntegerDigits="10" maxFractionDigits="6"/></td>
					<td><fmt:formatNumber value="${autotrade.fminprice}" pattern="##.######" maxIntegerDigits="10" maxFractionDigits="6"/></td>
					<td><fmt:formatNumber value="${autotrade.fmaxprice}" pattern="##.######" maxIntegerDigits="10" maxFractionDigits="6"/></td>
					<td>${autotrade.ftimes}</td>
					<td>${autotrade.fstoptimes}</td>
					<td>${autotrade.fcreatetime}</td>
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
