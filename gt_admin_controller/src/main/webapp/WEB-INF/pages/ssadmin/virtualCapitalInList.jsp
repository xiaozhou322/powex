<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post"
	action="/buluo718admin/virtualCapitalInList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="regfrom" value="${regfrom}" /><input
		type="hidden" name="ftype" value="${ftype}" /><input type="hidden"
		name="pageNum" value="${currentPage}" /> <input type="hidden"
		name="numPerPage" value="${numPerPage}" /> <input type="hidden"
		name="orderField" value="${param.orderField}" /><input type="hidden"
		name="orderDirection" value="${param.orderDirection}" /><input
		type="hidden" name="logDate2" value="${logDate2}" /><input
		type="hidden" name="logDate1" value="${logDate1}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/virtualCapitalInList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>会员信息：<input type="text" name="keywords" value="${keywords}"
						size="30" /></td>
					<td>虚拟币类型： <select type="combox" name="ftype">
							<c:forEach items="${typeMap}" var="type">
								<c:if test="${type.key == ftype}">
									<option value="${type.key}" selected="true">${type.value}</option>
								</c:if>
								<c:if test="${type.key != ftype}">
									<option value="${type.key}">${type.value}</option>
								</c:if>
							</c:forEach>
					</select>
					</td>
					<td>创建时间起： <input type="text" name="logDate1" class="date"
						readonly="true" value="${logDate1 }" />
					</td>
					<td>创建时间止： <input type="text" name="logDate2" class="date"
						readonly="true" value="${logDate2 }" />
					</td>
					<td>所属项目方ID：<input type="text" name="regfrom" value="${regfrom}"
						size="20" /></td>
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
	<shiro:hasPermission name="buluo718admin/auditVCInlog.html">
			<li><a class="edit"
				href="/buluo718admin/auditVCInlog.html?uid={sid_user}" target="ajaxTodo"
				rel="auditVCInlog" title="确定要审核吗?"><span>审核</span>
			</a>
			</li>
			<li><a class="icon" href="/buluo718admin/vcInExport.html"
					target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span>
				</a></li>
	</shiro:hasPermission>		
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20"  orderField="fid"
					<c:if test='${param.orderField == "fid" }'> class="${param.orderDirection}"  </c:if>>充值ID</th>
				<th width="20">会员UID</th>
				<th width="60" orderField="fuser.floginName"
					<c:if test='${param.orderField == "fuser.floginName" }'> class="${param.orderDirection}"  </c:if>>登陆名</th>
				<th width="60" orderField="fuser.frealName"
					<c:if test='${param.orderField == "fuser.frealName" }'> class="${param.orderDirection}"  </c:if>>会员真实姓名</th>
				<th width="60" orderField="fvirtualcointype.fname"
					<c:if test='${param.orderField == "fvirtualcointype.fname" }'> class="${param.orderDirection}"  </c:if>>虚拟币类型</th>
				<th width="60" orderField="fconfirmations"
					<c:if test='${param.orderField == "fconfirmations" }'> class="${param.orderDirection}"  </c:if>>确认数</th>
				<th width="60" orderField="fstatus"
					<c:if test='${param.orderField == "fstatus" }'> class="${param.orderDirection}"  </c:if>>状态</th>
				<th width="60" orderField="ftype"
					<c:if test='${param.orderField == "ftype" }'> class="${param.orderDirection}"  </c:if>>交易类型</th>
				<th width="60" orderField="famount"
					<c:if test='${param.orderField == "famount" }'> class="${param.orderDirection}"  </c:if>>数量</th>
				<th width="60" orderField="ffees"
					<c:if test='${param.orderField == "ffees" }'> class="${param.orderDirection}"  </c:if>>手续费</th>
				<th width="60">充值地址</th>
				<th width="60" orderField="fcreateTime"
					<c:if test='${param.orderField == "fcreateTime" }'> class="${param.orderDirection}"  </c:if>>创建时间</th>
				<th width="60" orderField="flastUpdateTime"
					<c:if test='${param.orderField == "flastUpdateTime" }'> class="${param.orderDirection}"  </c:if>>最后修改时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${virtualCapitaloperationList}"
				var="virtualCapitaloperation" varStatus="num">
				<tr target="sid_user" rel="${virtualCapitaloperation.fid}">
					<td>${virtualCapitaloperation.fid}</td>
					<td>${virtualCapitaloperation.fuser.fid}</td>
					<td>${virtualCapitaloperation.fuser.floginName}</td>
					<td>${virtualCapitaloperation.fuser.frealName}</td>
					<td>${virtualCapitaloperation.fvirtualcointype.fname}</td>
					<td>${virtualCapitaloperation.fconfirmations}</td>
					<td>${virtualCapitaloperation.fstatus_s}</td>
					<td>${virtualCapitaloperation.ftype_s}</td>
					<td><fmt:formatNumber value="${virtualCapitaloperation.famount}" pattern="##.######" maxIntegerDigits="20" maxFractionDigits="6"/></td>
					<td><fmt:formatNumber value="${virtualCapitaloperation.ffees}" pattern="##.######" maxIntegerDigits="20" maxFractionDigits="6"/></td>
					<td>${virtualCapitaloperation.recharge_virtual_address}</td>
					<td>${virtualCapitaloperation.fcreateTime}</td>
					<td>${virtualCapitaloperation.flastUpdateTime}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="panelBar">
		<div class="pages">
			<span>总共: ${totalCount}条，总数量：${totalAmount}</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${totalCount}"
			numPerPage="${numPerPage}" pageNumShown="5"
			currentPage="${currentPage}"></div>
	</div>
</div>
