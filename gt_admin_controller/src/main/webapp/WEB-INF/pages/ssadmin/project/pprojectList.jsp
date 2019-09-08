<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="/buluo718admin/pprojectList.html">
	<input type="hidden" name="projectId" value="${projectId}" /> 
	<input type="hidden" name="pageNum" value="${currentPage}" /> 
	<input type="hidden" name="numPerPage" value="${numPerPage}" /> 
	<input type="hidden" name="orderField" value="${param.orderField}" /> 
	<input type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/pprojectList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>项目方ID：<input type="text" name="projectId" value="${projectId}"
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
			<shiro:hasPermission name="buluo718admin/auditUser1.html">
				<li><a class="edit"
					href="/buluo718admin/pprojectDetail.html?pid={sid_pproject}"
					target="dialog" rel=auditUser height="400" width="800"><span>修改</span>
				</a>
				</li>
			</shiro:hasPermission>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="60" orderField="projectId"
					<c:if test='${param.orderField == "projectId" }'> class="${param.orderDirection}"  </c:if>>用户id</th>
				<th width="60">名称</th>
				<th width="60">项目方类型</th>
				<th width="60">logo地址</th>
				<th width="60">网站</th>
				<th width="60">项目亮点</th>
				<th width="60">项目介绍</th>
				<th width="60">备注</th>
				<th width="60" orderField="createTime"
					<c:if test='${param.orderField == "createTime" }'> class="${param.orderDirection}"  </c:if>>创建时间</th>
				<th width="60" orderField="updateTime"
					<c:if test='${param.orderField == "updateTime" }'> class="${param.orderDirection}"  </c:if>>修改时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pprojectList}" var="pproject" varStatus="num">
				<tr target="sid_pproject" rel="${pproject.id}">
					<td>${pproject.projectId.fid}</td>
					<td>${pproject.name}</td>
					<td>
						<c:choose>
							<c:when test="${pproject.type == 1}">项目方</c:when>
							<c:when test="${pproject.type == 2}">社群</c:when>
						</c:choose>
					</td>
					<td>${pproject.logoUrl}</td>
					<td>${pproject.website}</td>
					<td>${pproject.advantage}</td>
					<td>${pproject.introduce}</td>
					<td>${pproject.remark}</td>
					<td>${pproject.createTime}</td>
					<td>${pproject.updateTime}</td>
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
