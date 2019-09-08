<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post"
	action="/buluo718admin/virtualoperationlogList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="pageNum" value="${currentPage}" /><input
		type="hidden" name="logDate" value="${logDate}" /><input
		type="hidden" name="logDate2" value="${logDate2}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /><input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/virtualoperationlogList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>关键词：<input type="text" name="keywords" value="${keywords}"
						size="60" />[会员信息]</td>
					<td>开始日期： <input type="text" name="logDate" class="date"
						readonly="true" value="${logDate }" />
					</td>
					<td>结束日期： <input type="text" name="logDate2" class="date"
						readonly="true" value="${logDate2 }" />
					</td>	
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
			<shiro:hasPermission name="buluo718admin/addVirtualOperationLog">
				<li><a class="add"
					href="/buluo718admin/goVirtualOperationLogJSP.html?url=ssadmin//addVirtualOperationLog"
					height="280" width="800" target="dialog" rel="addVirtualOperation"><span>新增</span>
				</a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="buluo718admin/deleteVirtualOperationLog.html">
				<li><a class="delete"
					href="/buluo718admin/deleteVirtualOperationLog.html?uid={sid_user}"
					target="ajaxTodo" title="确定要删除吗?"><span>删除</span> </a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="buluo718admin/auditVirtualOperationLog.html">
				<li><a class="edit"
					href="/buluo718admin/auditVirtualOperationLog.html?uid={sid_user}"
					target="ajaxTodo" title="确定要审核吗?"><span>审核</span> </a></li>
				<li><a class="edit"
					href="/buluo718admin/sendVirtualOperationLog.html?uid={sid_user}"
					target="ajaxTodo" title="确定要发放冻结吗?"><span>发放冻结</span> </a></li>	
			</shiro:hasPermission>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">序号</th>
				<th width="60">UID</th>
				<th width="60" orderField="fuser.floginName"
					<c:if test='${param.orderField == "fuser.floginName" }'> class="${param.orderDirection}"  </c:if>>登陆名</th>
				<th width="60" orderField="fuser.fnickName"
					<c:if test='${param.orderField == "fuser.fnickName" }'> class="${param.orderDirection}"  </c:if>>会员昵称</th>
				<th width="60" orderField="fuser.frealName"
					<c:if test='${param.orderField == "fuser.frealName" }'> class="${param.orderDirection}"  </c:if>>会员真实姓名</th>
					
				<th width="60" orderField="fstatus"
					<c:if test='${param.orderField == "fstatus" }'> class="${param.orderDirection}"  </c:if>>状态</th>
				<th width="60" orderField="fvirtualcointype.fname"
					<c:if test='${param.orderField == "fvirtualcointype.fname" }'> class="${param.orderDirection}"  </c:if>>虚拟币名称</th>
				<th width="60" orderField="fqty"
					<c:if test='${param.orderField == "fqty" }'> class="${param.orderDirection}"  </c:if>>数量</th>
				<th width="60" orderField=fcreateTime
					<c:if test='${param.orderField == "fcreateTime" }'> class="${param.orderDirection}"  </c:if>>审核时间</th>
				<th width="60" orderField="fcreator.fname"
					<c:if test='${param.orderField == "fcreator.fname" }'> class="${param.orderDirection}"  </c:if>>审核人</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${virtualoperationlogList}"
				var="virtualoperationlog" varStatus="num">
				<tr target="sid_user" rel="${virtualoperationlog.fid}">
					<td>${num.index +1}</td>
					<td>${virtualoperationlog.fuser.fid}</td>
					<td>${virtualoperationlog.fuser.floginName}</td>
					<td>${virtualoperationlog.fuser.fnickName}</td>
					<td>${virtualoperationlog.fuser.frealName}</td>
					<td>${virtualoperationlog.fstatus_s}</td>
					<td>${virtualoperationlog.fvirtualcointype.fname}</td>
					<td><ex:DoubleCut value="${virtualoperationlog.fqty}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td>${virtualoperationlog.fcreateTime}</td>
					<td>${virtualoperationlog.fcreator.fname}</td>
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
