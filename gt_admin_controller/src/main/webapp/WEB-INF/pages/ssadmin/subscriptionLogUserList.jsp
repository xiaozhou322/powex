<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post"
	action="/buluo718admin/subscriptionLogUserList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /><input
		type="hidden" name="pageNum" value="1" /> <input type="hidden"
		name="numPerPage" value="${numPerPage}" /> <input type="hidden"
		name="orderField" value="${param.orderField}" /><input type="hidden"
		name="orderDirection" value="${param.orderDirection}" /><input
		type="hidden" name="type" value="${ftype}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/subscriptionLogUserList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>会员信息：<input type="text" name="keywords" value="${keywords}"
						size="60" /></td>
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
	<div class="panelBar"></div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">UID</th>
				<th width="60" orderField="fuser.floginName"
					<c:if test='${param.orderField == "fuser.floginName" }'> class="${param.orderDirection}"  </c:if>>登陆名</th>
				<th width="60" orderField="fuser.fnickName"
					<c:if test='${param.orderField == "fuser.fnickName" }'> class="${param.orderDirection}"  </c:if>>会员昵称</th>
				<th width="60" orderField="fuser.frealName"
					<c:if test='${param.orderField == "fuser.frealName" }'> class="${param.orderDirection}"  </c:if>>会员真实姓名</th>
				<th width="60">虚拟币类型</th>
				<th width="60" orderField="fcount"
					<c:if test='${param.orderField == "fcount" }'> class="${param.orderDirection}"  </c:if>>众筹数量</th>
				<th width="60" orderField="fprice"
					<c:if test='${param.orderField == "fprice" }'> class="${param.orderDirection}"  </c:if>>众筹价格</th>
				<th width="60" orderField="ftotalCost"
					<c:if test='${param.orderField == "ftotalCost" }'> class="${param.orderDirection}"  </c:if>>总消费金额</th>
				<th width="60" orderField="fcreatetime"
					<c:if test='${param.orderField == "fcreatetime" }'> class="${param.orderDirection}"  </c:if>>创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${subscriptionLogUserList}" var="subscriptionLog"
				varStatus="num">
				<tr target="sid_user" rel="${subscriptionLog.fid}">
					<td>${subscriptionLog.fuser.fid}</td>
					<td>${subscriptionLog.fuser.floginName}</td>
					<td>${subscriptionLog.fuser.fnickName}</td>
					<td>${subscriptionLog.fuser.frealName}</td>
					<td>${subscriptionLog.fsubscription.fvirtualcointype.fname}</td>
					<td>${subscriptionLog.fcount}</td>
					<td>${subscriptionLog.fprice}</td>
					<td>${subscriptionLog.ftotalCost} ${subscriptionLog.fsubscription.symbol }</td>
					<td>${subscriptionLog.fcreatetime}</td>
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
