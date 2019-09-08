<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="/buluo718admin/findFuserByFentrustPrice.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /><input
		type="hidden" name="logDate" value="${logDate}" /><input
		type="hidden" name="price" value="${price}" /><input
		type="hidden" name="ftype" value="${ftype}" /><input
		type="hidden" name="status" value="${status}" /><input
		type="hidden" name="entype" value="${entype}" /> <input type="hidden"
		name="pageNum" value="${currentPage}" /> <input type="hidden"
		name="numPerPage" value="${numPerPage}" /> <input type="hidden"
		name="orderField" value="${param.orderField}" /><input type="hidden"
		name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/findFuserByFentrustPrice.html" method="post">
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
					<td>日期： <input type="text" name="logDate" class="date"
						readonly="true" value="${logDate }" />
					</td>
					<td>价格：<input type="text" name="price" value="${price}"
						size="20" /></td>
					<td>状态： <select type="combox" name="status">
							<c:forEach items="${statusMap}" var="s">
								<c:if test="${s.key == status}">
									<option value="${s.key}" selected="true">${s.value}</option>
								</c:if>
								<c:if test="${s.key != status}">
									<option value="${s.key}">${s.value}</option>
								</c:if>
							</c:forEach>
					</select>
					</td>
					<td>类型： <select type="combox" name="entype">
							<c:forEach items="${entypeMap}" var="t">
								<c:if test="${t.key == entype}">
									<option value="${t.key}" selected="true">${t.value}</option>
								</c:if>
								<c:if test="${t.key != entype}">
									<option value="${t.key}">${t.value}</option>
								</c:if>
							</c:forEach>
					</select>
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

		</ul>
	</div>
	<table class="table" width="120%" layoutH="138">
		<thead>
			<tr>
				<th width="20">序号</th>
				<th width="60" orderField="fuser.floginName"
					<c:if test='${param.orderField == "fuser.floginName"}'> class="${param.orderDirection}"  </c:if>>登陆名</th>
				<th width="60" orderField="fuser.fnickName"
					<c:if test='${param.orderField == "fuser.fnickName"}'> class="${param.orderDirection}"  </c:if>>会员昵称</th>
				<th width="60" orderField="fuser.frealName"
					<c:if test='${param.orderField == "fuser.frealName"}'> class="${param.orderDirection}"  </c:if>>会员真实姓名</th>
				<th width="60" orderField="fvirtualcointype.fname"
					<c:if test='${param.orderField == "fvirtualcointype.fname"}'> class="${param.orderDirection}"  </c:if>>虚拟币类型</th>
				<th width="60" orderField="fentrustType"
					<c:if test='${param.orderField == "fentrustType"}'> class="${param.orderDirection}"  </c:if>>交易类型</th>
				<th width="60" orderField="fstatus"
					<c:if test='${param.orderField == "fstatus"}'> class="${param.orderDirection}"  </c:if>>状态</th>
				<th width="60" orderField="fprize"
					<c:if test='${param.orderField == "fprize"}'> class="${param.orderDirection}"  </c:if>>单价</th>
				<th width="60" orderField="fcount"
					<c:if test='${param.orderField == "fcount"}'> class="${param.orderDirection}"  </c:if>>数量</th>
				<th width="60" orderField="fleftCount"
					<c:if test='${param.orderField == "fleftCount"}'> class="${param.orderDirection}"  </c:if>>未成交数量</th>
				<th width="60">已成交数量</th>	
				<th width="60" orderField="famount"
					<c:if test='${param.orderField == "famount"}'> class="${param.orderDirection}"  </c:if>>总金额</th>
				<th width="60" orderField="fsuccessAmount"
					<c:if test='${param.orderField == "fsuccessAmount"}'> class="${param.orderDirection}"  </c:if>>成交总金额</th>
				<th width="60" orderField="flastUpdatTime"
					<c:if test='${param.orderField == "flastUpdatTime"}'> class="${param.orderDirection}"  </c:if>>修改时间</th>
				<th width="60" orderField="fcreateTime"
					<c:if test='${param.orderField == "fcreateTime"}'> class="${param.orderDirection}"  </c:if>>创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${entrustList}" var="entrust" varStatus="num">
				<tr target="sid_user" rel="${entrust.fid}">
					<td>${num.index +1}</td>
					<td>${entrust.fuser.floginName}</td>
					<td>${entrust.fuser.fnickName}</td>
					<td>${entrust.fuser.frealName}</td>
					<td>${entrust.fvirtualcointype.fname}</td>
					<td>${entrust.fentrustType_s}</td>
					<td>${entrust.fstatus_s}</td>
					<td>${entrust.fprize}</td>
					<td>${entrust.fcount}</td>
					<td>${entrust.fleftCount}</td>
					<td>${entrust.fcount - entrust.fleftCount}</td>
					<td>${entrust.famount}</td>
					<td>${entrust.fsuccessAmount}</td>
					<td>${entrust.flastUpdatTime}</td>
					<td>${entrust.fcreateTime}</td>
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
