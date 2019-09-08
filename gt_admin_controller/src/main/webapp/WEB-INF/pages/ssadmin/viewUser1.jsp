<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="/buluo718admin/userList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /><input
		type="hidden" name="cid" value="${cid}" /> <input type="hidden"
		name="troUid" value="${troUid}" /> <input type="hidden" name="ftype"
		value="${ftype}" /> <input type="hidden" name="pageNum"
		value="${currentPage}" /> <input type="hidden" name="numPerPage"
		value="${numPerPage}" /> <input type="hidden" name="orderField"
		value="${param.orderField}" /> <input type="hidden"
		name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/userList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>会员信息：<input type="text" name="keywords"
						value="${keywords}" size="60" /><input type="hidden" name="cid"
						value="${cid}" />
					</td>
					<td>推荐人UID：<input type="text" name="troUid" value="${troUid}"
						size="30" />
					</td>
					<td>会员状态： <select type="combox" name="ftype">
							<c:forEach items="${typeMap}" var="type">
								<c:if test="${type.key == ftype}">
									<option value="${type.key}" selected="true">${type.value}</option>
								</c:if>
								<c:if test="${type.key != ftype}">
									<option value="${type.key}">${type.value}</option>
								</c:if>
							</c:forEach>
					</select></td>
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
			<shiro:hasPermission name="buluo718admin/userForbbin.html?status=1">
				<li><a class="delete"
					href="/buluo718admin/userForbbin.html?uid={sid_user}&status=1&rel=listUser"
					target="ajaxTodo" title="确定要禁用吗?"><span>禁用</span> </a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="buluo718admin/userForbbin.html?status=2">
				<li><a class="edit"
					href="/buluo718admin/userForbbin.html?uid={sid_user}&status=2&rel=listUser"
					target="ajaxTodo" title="确定要解除禁用吗?"><span>解除禁用</span> </a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="buluo718admin/auditUser.html">
				<li><a class="edit"
					href="/buluo718admin/goUserJSP.html?uid={sid_user}&status=view&url=ssadmin//auditUser"
					target="dialog" rel=auditUser height="400" width="800"><span>查看身份证信息</span>
				</a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="buluo718admin/userForbbin.html?status=3">
				<li><a class="edit"
					href="/buluo718admin/userForbbin.html?uid={sid_user}&status=3&rel=listUser"
					target="ajaxTodo" title="确定要重设登陆密码吗?"><span>重设登陆密码</span> </a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="buluo718admin/userForbbin.html?status=4">
				<li><a class="edit"
					href="/buluo718admin/userForbbin.html?uid={sid_user}&status=4&rel=listUser"
					target="ajaxTodo" title="确定要重设交易密码吗?"><span>重设交易密码</span> </a>
				</li>
			</shiro:hasPermission>
			<li><a class="edit"
				href="/buluo718admin/goUserJSP.html?uid={sid_user}&url=ssadmin//updateIntroCount"
				height="240" width="800" target="dialog" rel="updateIntroCount"><span>修改推荐数</span>
			</a>
			</li>
			<li><a class="edit"
				href="/buluo718admin/cancelGoogleCode.html?uid={sid_user}"
				target="ajaxTodo" title="确定要重设GOOGLE验证吗?"><span>重置GOOGLE验证</span>
			</a>
			</li>
			<li class="line">line</li>
			<li><a class="icon" href="/buluo718admin/userExport.html"
				target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span>
			</a></li>
		</ul>
	</div>
	<table class="table" width="200%" layoutH="138">
		<thead>
			<tr>
				<th width="40">会员UID</th>
				<th width="40">推荐人UID</th>
				<th width="60" orderField="floginName"
					<c:if test='${param.orderField == "floginName" }'> class="${param.orderDirection}"  </c:if>>登陆名</th>
				<th width="60" orderField="fstatus"
					<c:if test='${param.orderField == "fstatus" }'> class="${param.orderDirection}"  </c:if>>会员状态</th>
				<th width="60" orderField="fpostRealValidate"
					<c:if test='${param.orderField == "fpostRealValidate" }'> class="${param.orderDirection}"  </c:if>>证件是否提交</th>
				<th width="60" orderField="fhasRealValidate"
					<c:if test='${param.orderField == "fhasRealValidate" }'> class="${param.orderDirection}"  </c:if>>证件是否已审</th>
				<th width="60">昵称</th>
				<th width="60">真实姓名</th>
				<th width="60" orderField="fscore.flevel"
					<c:if test='${param.orderField == "fscore.flevel" }'> class="${param.orderDirection}"  </c:if>>会员等级</th>
				<th width="60">累计推荐注册数</th>
				<th width="60">是否已手机验证</th>
				<th width="60">电话号码</th>
				<th width="60">邮箱地址</th>
				<th width="60">证件类型</th>
				<th width="60">证件号码</th>
				<th width="60" orderField="fregisterTime"
					<c:if test='${param.orderField == "fregisterTime" }'> class="${param.orderDirection}"  </c:if>>注册时间</th>
				<th width="60" orderField="flastLoginTime"
					<c:if test='${param.orderField == "flastLoginTime" }'> class="${param.orderDirection}"  </c:if>>上次登陆时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${userList}" var="user" varStatus="num">
				<tr target="sid_user" rel="${user.fid}">
					<td>${user.fid}</td>
					<td>${user.fIntroUser_id.fid}</td>
					<td>${user.floginName}</td>
					<td>${user.fstatus_s}</td>
					<td>${user.fpostRealValidate}</td>
					<td>${user.fhasRealValidate}</td>
					<td>${user.fnickName}</td>
					<td>${user.frealName}</td>
					<td>${user.fscore.flevel}</td>
					<td>${user.fInvalidateIntroCount}</td>
					<td>${user.fisTelephoneBind}</td>
					<td>${user.ftelephone}</td>
					<td>${user.femail}</td>
					<td>${user.fidentityType_s}</td>
					<td>${user.fidentityNo}</td>
					<td>${user.fregisterTime}</td>
					<td>${user.flastLoginTime}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
