<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="/buluo718admin/userGradeList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /><input
		type="hidden" name="level1" value="${level1}" /><input
		type="hidden" name="level2" value="${level2}" /><input
		type="hidden" name="level3" value="${level3}" /><input
		type="hidden" name="level4" value="${level4}" /><input
		type="hidden" name="ftype" value="${ftype}" /><input
		type="hidden" name="pageNum" value="${currentPage}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /> <input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/userGradeList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>会员信息：<input type="text" name="keywords"
						value="${keywords}" size="20" />
					自身级别： <select type="combox" name="ftype">
							<c:forEach items="${map}" var="type">
								<c:if test="${type.key == ftype}">
									<option value="${type.key}" selected="true">${type.value}</option>
								</c:if>
								<c:if test="${type.key != ftype}">
									<option value="${type.key}">${type.value}</option>
								</c:if>
							</c:forEach>
					</select>有效用户数：<input type="text" name="level1"
						value="${level1}" size="10" />
					&nbsp;&nbsp;&nbsp;&nbsp;初级玩家数：<input type="text" name="level2"
						value="${level2}" size="10" />
					&nbsp;&nbsp;&nbsp;&nbsp;资深玩家数：<input type="text" name="level3"
						value="${level3}" size="10" />
				    &nbsp;&nbsp;&nbsp;&nbsp;圈子成员数：<input type="text" name="level4"
						value="${level4}" size="10" /></td>			
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
				href="/buluo718admin/userGradeDetail.html?parentId={sid_user}" height="500"
				width="800" target="dialog" rel="subscriptionLogList"><span>查看明细</span>
			</a></li>
			<li class="line">line</li>
			<li><a class="icon" href="/buluo718admin/userGradeDetailExport.html"
				target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span>
			</a>
			</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="40">会员UID</th>
				<th width="40">会员账号</th>
				<th width="60">会员姓名</th>
				<th width="60">会员电话</th>
				<th width="60">自身级别</th>
				<th width="60">推荐有效总人数</th>
				<th width="60">有效用户</th>
				<th width="60">FC初级玩家</th>
				<th width="60">FC资深玩家</th>
				<th width="60">FC圈子成员</th>
				<th width="60">FC圈子领袖</th>
				<th width="60">其他</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${userGradeList}" var="user">
				<c:forEach items="${user }" var="v" varStatus="num">
					<c:if test="${num.index ==0 }">
						<tr target="sid_user" rel="${v}">
					</c:if>
					<td>${v }</td>
				</c:forEach>
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
