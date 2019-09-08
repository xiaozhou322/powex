<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="/buluo718admin/levelSettingList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="pageNum" value="${currentPage}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /><input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/levelSettingList.html" method="post">
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
					href="/buluo718admin/goLevelSettingJSP.html?url=ssadmin//updateLevelSetting&uid={sid_user}"
					height="200" width="900" target="dialog" rel="updateLevelSetting"><span>修改</span>
				</a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">序号</th>
				<th width="60">等级</th>
				<th width="60">积分范围</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${levelSettingList}" var="levelSetting" varStatus="num">
				<tr target="sid_user" rel="${levelSetting.fid}">
					<td>${num.index+1}</td>
					<td>${levelSetting.level}</td>
					<c:if test="${num.index+1 ==6 }">
					<td>${">="}${levelSetting.score}</td>
					</c:if>
					<c:if test="${num.index+1 !=6 }">
					<td>${levelSetting.score}~${levelSetting.score2}</td>
					</c:if>
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
