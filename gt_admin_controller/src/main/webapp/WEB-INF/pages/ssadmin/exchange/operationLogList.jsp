<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<form id="pagerForm" method="post"
	action="/buluo718admin/usdtOperationLogList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="pageNum" value="${currentPage}" /><input
		type="hidden" name="logDate" value="${logDate}" /><input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /><input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/usdtOperationLogList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>会员信息：<input type="text" name="keywords" value="${keywords}"
						size="60" /></td>
					<td>日期： <input type="text" name="logDate" class="date"
						readonly="true" value="${logDate }" />
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
			<shiro:hasPermission name="buluo718admin/usdtAddOperationLog.html">
				<li><a class="add"
					href="/buluo718admin/goOperationLogJSP.html?url=ssadmin/exchange/addOperationLog"
					height="280" width="800" target="dialog" rel="addOperationLog"><span>新增</span>
				</a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="buluo718admin/usdtDeleteOperationLog.html">
				<li><a class="delete"
					href="/buluo718admin/usdtDeleteOperationLog.html?uid={sid_user}"
					target="ajaxTodo" title="确定要删除吗?"><span>删除</span> </a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="buluo718admin/usdtAuditOperationLog.html">
				<li><a class="edit"
					href="/buluo718admin/goOperationLogJSP.html?url=ssadmin/exchange/operationAuditLog&uid={sid_user}"
					height="430" width="800" target="dialog" rel="auditOperationLog"
					title="确定要审核吗?"><span>审核</span> </a>
				</li>
			</shiro:hasPermission>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">会员UID</th>
				<th width="60" orderField="fuser.floginName"
					<c:if test='${param.orderField == "fuser.floginName" }'> class="${param.orderDirection}"  </c:if>>登陆名</th>
				<th width="60" orderField="fuser.fnickName"
					<c:if test='${param.orderField == "fuser.fnickName" }'> class="${param.orderDirection}"  </c:if>>会员昵称</th>
				<th width="60" orderField="fuser.frealName"
					<c:if test='${param.orderField == "fuser.frealName" }'> class="${param.orderDirection}"  </c:if>>会员真实姓名</th>
				<th width="60" orderField="ftype"
					<c:if test='${param.orderField == "ftype" }'> class="${param.orderDirection}"  </c:if>>操作币种</th>
				<th width="60" orderField="fstatus"
					<c:if test='${param.orderField == "fstatus" }'> class="${param.orderDirection}"  </c:if>>状态</th>
				<th width="60" orderField="famount"
					<c:if test='${param.orderField == "famount" }'> class="${param.orderDirection}"  </c:if>>金额</th>
				<th width="60">描述</th>
				<th width="60" orderField="fcreateTime"
					<c:if test='${param.orderField == "fcreateTime" }'> class="${param.orderDirection}"  </c:if>>创建时间</th>
				<th width="60" orderField="flastUpdateTime"
					<c:if test='${param.orderField == "flastUpdateTime" }'> class="${param.orderDirection}"  </c:if>>修改时间</th>
				<th width="60" orderField="fkey1"
					<c:if test='${param.orderField == "fkey1" }'> class="${param.orderDirection}"  </c:if>>审核人</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${operationlogList}" var="operationlog"
				varStatus="num">
				<tr target="sid_user" rel="${operationlog.fid}">
					<td>${operationlog.fuser.fid}</td>
					<td>${operationlog.fuser.floginName}</td>
					<td>${operationlog.fuser.fnickName}</td>
					<td>${operationlog.fuser.frealName}</td>
					<td>${operationlog.ftype_s}</td>
					<td>${operationlog.fstatus_s}</td>
					<td><fmt:formatNumber value="${operationlog.famount}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td>${operationlog.fdescription}</td>
					<td>${operationlog.fcreateTime}</td>
					<td>${operationlog.flastUpdateTime}</td>
					<td>${operationlog.fkey1}</td>
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
