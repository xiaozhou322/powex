<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<form id="pagerForm" method="post"
	action="/buluo718admin/otcBlackList.html">
	 <input type="hidden" name="pageNum" value="${currentPage}" /> 
	 <input type="hidden" name="numPerPage" value="${numPerPage}" /> 
	 <input type="hidden" name="orderField" value="${param.orderField}" />
	 <input type="hidden" name="orderDirection" value="${param.orderDirection}" />
		
	 <input type="hidden" name="userId" value="${userId}" />
</form>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/otcBlackList.html" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>用户ID：&nbsp;
						<input type="text" name="userId" value="${userId}" size="20" />
					</td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">查询</button>
							</div>
						</div>
					</td>
				</tr>
				
			</table>
		</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<%-- <ul class="toolBar">
			  <shiro:hasPermission name="buluo718admin/entrustList.html">
				<li><a class="edit"
					href="/buluo718admin/orderNewDetail.html?url=ssadmin/order/orderNewDetail&orderId={sid_user}"
					height="500" width="700" target="dialog" rel="orderDetail"><span>订单详情</span>
				</a></li>
			</shiro:hasPermission>
		</ul>
		<ul></ul> --%>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th align="center" orderField="id"
					<c:if test='${param.orderField == "id" }'> class="${param.orderDirection}"  </c:if>>ID</th>
				<th align="center" orderField="userId.fid"
					<c:if test='${param.orderField == "userId.fid" }'> class="${param.orderDirection}"  </c:if>>用户ID</th>
				<th align="center">用户登录名</th>
				<th align="center">用户姓名</th>
				<th align="center" orderField="createTime"
					<c:if test='${param.orderField == "createTime" }'> class="${param.orderDirection}"  </c:if>>创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${otcBlackList}" var="v" varStatus="vs">
				<tr target="sid_blacklistId" rel="${v.id}">
				    <td>${v.id }</td>
				    <td>${v.userId.fid }</td>
				    <td>${v.userId.floginName }</td>
					<td>${v.userId.frealName }</td>
					<td><fmt:formatDate value="${v.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
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
