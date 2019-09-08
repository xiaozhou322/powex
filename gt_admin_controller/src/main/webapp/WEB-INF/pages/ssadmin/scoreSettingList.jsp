<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="/buluo718admin/scoreSettingList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="pageNum" value="${currentPage}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /><input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/scoreSettingList.html" method="post">
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
				<li><a class="edit"
					href="/buluo718admin/goScoreSettingJSP.html?url=ssadmin//updateScoreSetting&uid={sid_user}"
					height="200" width="900" target="dialog" rel="updateScoreSetting"><span>修改</span>
				</a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">序号</th>
				<th width="60">操作类型</th>
				<th width="60">奖励积分</th>
				<th width="60">备注</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${scoreSettingList}" var="scoreSetting" varStatus="num">
				<tr target="sid_user" rel="${scoreSetting.fid}">
					<td>${num.index+1}</td>
					<td>${scoreSetting.type_s}</td>
					<td>${scoreSetting.score}</td>
					<td>${scoreSetting.remark}</td>
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
