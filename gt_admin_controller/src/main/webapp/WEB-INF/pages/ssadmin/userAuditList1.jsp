<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="/buluo718admin/userAuditList1.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="pageNum" value="${currentPage}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /> <input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/userAuditList1.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>会员信息：<input type="text" name="keywords" value="${keywords}"
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
				<li><a class="edit"
					href="/buluo718admin/goUserJSP.html?uid={sid_user}&url=ssadmin//auditUser1"
					target="dialog" rel="auditUser1" height="400" width="800"><span>审核</span>
				</a>
				</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids"
					class="checkboxCtrl">
				</th>
				<th width="60">会员UID</th>
				<th width="100" orderField="floginName"
					<c:if test='${param.orderField == "floginName" }'> class="${param.orderDirection}"  </c:if>>登陆名</th>
				<th width="60" orderField="fstatus"
					<c:if test='${param.orderField == "fstatus" }'> class="${param.orderDirection}"  </c:if>>会员状态</th>
				<th width="60">昵称</th>
				<th width="60">真实姓名</th>
				<th width="60">证件类型</th>
				<th width="80">国家地区</th>
				<th width="60">证件号码</th>
				<th width="60" orderField="fregisterTime"
					<c:if test='${param.orderField == "fregisterTime" }'> class="${param.orderDirection}"  </c:if>>注册时间</th>
				<th width="60" orderField="flastLoginTime"
					<c:if test='${param.orderField == "flastLoginTime" }'> class="${param.orderDirection}"  </c:if>>上次登陆时间</th>
				<th width="60">推荐人ID</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${userList}" var="user" varStatus="num">
				<tr target="sid_user" rel="${user.fid}">
					<td><input name="ids" value="${user.fid}"
						type="checkbox">
					</td>
					<td>${user.fid}</td>
					<td>${user.floginName}</td>
					<td>${user.fstatus_s}</td>
					<td>${user.fnickName}</td>
					<td>${user.frealName}</td>
					<td>${user.fidentityType_s}</td>
					<td>
						<c:choose>
							<c:when test="${user.fareaCode=='86'}">中国大陆</c:when>
							<c:when test="${user.fareaCode=='853'}">中国澳门</c:when>
							<c:when test="${user.fareaCode=='886'}">中国台湾</c:when>
							<c:when test="${user.fareaCode=='852'}">中国香港</c:when>
							<c:when test="${user.fareaCode=='61'}">澳大利亚(Australia)</c:when>
							<c:when test="${user.fareaCode=='49'}">德国(Germany)</c:when>
							<c:when test="${user.fareaCode=='7'}">俄罗斯(Russian Federation)</c:when>
							<c:when test="${user.fareaCode=='33'}">法国(France)</c:when>
							<c:when test="${user.fareaCode=='63'}">菲律宾(Philippines)</c:when>
							<c:when test="${user.fareaCode=='82'}">韩国(Korea)</c:when>
							<c:when test="${user.fareaCode=='1'}">加拿大(Canada)</c:when>
							<c:when test="${user.fareaCode=='52'}">墨西哥(Mexico)</c:when>
							<c:when test="${user.fareaCode=='81'}">日本(Japan)</c:when>
							<c:when test="${user.fareaCode=='66'}">泰国(Thailand)</c:when>
							<c:when test="${user.fareaCode=='65'}">新加坡(Singapore)</c:when>
							<c:when test="${user.fareaCode=='91'}">印度(India)</c:when>
							<c:when test="${user.fareaCode=='44'}">英国(United Kingdom)</c:when>
							<c:otherwise>${user.fareaCode}</c:otherwise>					
						</c:choose>
					</td>
					<td>${user.fidentityNo}</td>
					<td>${user.fregisterTime}</td>
					<td>${user.flastLoginTime}</td>
					<td>${user.fIntroUser_id.fid}</td>
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
