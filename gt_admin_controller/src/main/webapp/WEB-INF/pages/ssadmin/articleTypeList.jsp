<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="/buluo718admin/articleTypeList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="pageNum" value="${currentPage}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /><input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/articleTypeList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>关键字：<input type="text" name="keywords" value="${keywords}"
						size="60" />[名称、关键词、描述]</td>
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
			<shiro:hasPermission name="buluo718admin/updateArticleType.html">
				<li><a class="edit"
					href="/buluo718admin/goArticleTypeJSP.html?url=ssadmin//updateArticleType&uid={sid_user}"
					height="300" width="800" target="dialog" rel="updateAricle"><span>修改</span>
				</a></li>
				<li><a class="add"
					href="/buluo718admin/goArticleTypeJSP.html?url=ssadmin//addArticleType1"
					height="300" width="800" target="dialog" rel="addArticleType1"><span>新增</span>
				</a>
				</li>
				<li><a class="delete"
					href="/buluo718admin/deleteArticleType.html?uid={sid_user}" target="ajaxTodo"
					title="确定要删除吗?"><span>删除</span> </a>
				</li>
			</shiro:hasPermission>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">序号</th>
				<th width="60" orderField="fname"
					<c:if test='${param.orderField == "fname" }'> class="${param.orderDirection}"  </c:if>>类型名称</th>
				<th width="60" orderField="fkeywords"
					<c:if test='${param.orderField == "fkeywords" }'> class="${param.orderDirection}"  </c:if>>关键词</th>
				<th width="60">描述</th>
				<th width="60" orderField="flastCreateDate"
					<c:if test='${param.orderField == "flastCreateDate" }'> class="${param.orderDirection}"  </c:if>>创建时间</th>
				<th width="60" orderField="flastModifyDate"
					<c:if test='${param.orderField == "flastModifyDate" }'> class="${param.orderDirection}"  </c:if>>修改时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${articleTypeList}" var="articleType"
				varStatus="num">
				<tr target="sid_user" rel="${articleType.fid}">
					<td>${num.index +1}</td>
					<td>${articleType.fname}</td>
					<td>${articleType.fkeywords}</td>
					<td>${articleType.fdescription}</td>
					<td>${articleType.flastCreateDate}</td>
					<td>${articleType.flastModifyDate}</td>
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
