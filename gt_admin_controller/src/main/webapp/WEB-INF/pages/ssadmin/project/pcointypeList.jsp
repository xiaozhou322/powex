<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="/buluo718admin/pcointypeList.html">
	<input type="hidden" name="shortName" value="${shortName}" /> 
	<input type="hidden" name="pageNum" value="${currentPage}" /> 
	<input type="hidden" name="numPerPage" value="${numPerPage}" /> 
	<input type="hidden" name="orderField" value="${param.orderField}" /> 
	<input type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/pcointypeList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>币种简称：<input type="text" name="shortName" value="${shortName}"
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
					href="/buluo718admin/pcointypeDetail.html?pid={sid_cointype}&type=2"
					target="dialog" rel=auditUser height="700" width="800"><span>查看</span>
				</a>
				</li>
			</shiro:hasPermission>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th align="center" width="60" orderField="name"
					<c:if test='${param.orderField == "name" }'> class="${param.orderDirection}"  </c:if>>名称</th>
				<th align="center" width="60" orderField="shortName"
					<c:if test='${param.orderField == "shortName" }'> class="${param.orderDirection}"  </c:if>>简称</th>
				<th align="center" width="60" orderField="projectId"
					<c:if test='${param.orderField == "projectId" }'> class="${param.orderDirection}"  </c:if>>项目方</th>
				<th align="center" width="60">发行总量</th>
				<th align="center" width="60">发行价格</th>
				<th align="center" width="60">审核状态</th>
				<th align="center" width="80">备注</th>
				<th align="center" width="60" orderField="createTime"
					<c:if test='${param.orderField == "createTime" }'> class="${param.orderDirection}"  </c:if>>创建时间</th>
				<th align="center" width="60" orderField="auditTime"
					<c:if test='${param.orderField == "auditTime" }'> class="${param.orderDirection}"  </c:if>>审核时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pcointypeList}" var="pcointype" varStatus="num">
				<tr target="sid_cointype" rel="${pcointype.id}">
					<td>${pcointype.name}</td>
					<td>${pcointype.shortName}</td>
					<td>${pcointype.projectId.fid}</td>
					<td><ex:DoubleCut value="${pcointype.pushTotal}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><ex:DoubleCut value="${pcointype.pushPrice}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td>
						<c:choose>
							<c:when test="${pcointype.auditStatus == 101}">待审核</c:when>
							<c:when test="${pcointype.auditStatus == 102}">审核通过</c:when>
							<c:when test="${pcointype.auditStatus == 103}">审核不通过</c:when>
						</c:choose>
					</td>
					<td>${pcointype.remark}</td>
					<td>${pcointype.createTime}</td>
					<td>${pcointype.auditTime}</td>
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
