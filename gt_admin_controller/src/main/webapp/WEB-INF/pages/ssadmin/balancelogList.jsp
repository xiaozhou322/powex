<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="/buluo718admin/balancelogList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="ftype" value="${ftype}" /><input type="hidden"
		name="pageNum" value="${currentPage}" /> <input type="hidden"
		name="numPerPage" value="${numPerPage}" /> <input type="hidden"
		name="orderField" value="${param.orderField}" /><input type="hidden"
		name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/balancelogList.html" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>会员信息：<input type="text" name="keywords"
						value="${keywords}" size="60" /></td>
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
	<div class="panelBar">
		<ul class="toolBar">

		</ul>
	</div>
	<table class="table" width="120%" layoutH="138">
		<thead>
			<tr>
				<th width="20">序号</th>
				<th width="60" orderField="fuser.floginName"
					<c:if test='${param.orderField == "fuser.floginName" }'> class="${param.orderDirection}"  </c:if>>会员登陆名</th>
				<th width="60" orderField="fuser.fnickName"
					<c:if test='${param.orderField == "fuser.fnickName" }'> class="${param.orderDirection}"  </c:if>>会员昵称</th>
				<th width="60" orderField="fuser.frealName"
					<c:if test='${param.orderField == "fuser.frealName" }'> class="${param.orderDirection}"  </c:if>>会员真实姓名</th>
				<th width="60">状态</th>
				<th width="60">定存类型</th>
				<th width="60">定存利率</th>
				<th width="60">定存天数</th>
				<th width="60">存入币类型</th>
				<th width="60">存入数量</th>
				<th width="60">生效时间</th>
				<th width="60">结束时间</th>
				<th width="60">预计收益数量</th>
				<th width="60">实际收益数量</th>
				<th width="60">创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${balancelogList}" var="balancelog" varStatus="num">
				<tr target="sid_user" rel="${balancelog.fid}">
					<td>${num.index +1}</td>
					<td>${balancelog.fuser.floginName}</td>
					<td>${balancelog.fuser.fnickName}</td>
					<td>${balancelog.fuser.frealName}</td>
					<td>${balancelog.fstatus_s}</td>
					<td>${balancelog.ftype}</td>
					<td><fmt:formatNumber value="${balancelog.frate}" pattern="##.######" maxIntegerDigits="10" maxFractionDigits="6"/></td>
					<td>${balancelog.fday}</td>
					<td>${balancelog.fvirtualcointype.fname}</td>
					<td><fmt:formatNumber value="${balancelog.famount}" pattern="##.######" maxIntegerDigits="10" maxFractionDigits="6"/></td>
					<td>${balancelog.feffecttime}</td>
					<td>${balancelog.fendtime}</td>
					<td><fmt:formatNumber value="${balancelog.fincomeAmount1}" pattern="##.######" maxIntegerDigits="10" maxFractionDigits="6"/></td>
					<td><fmt:formatNumber value="${balancelog.fincomeAmount2}" pattern="##.######" maxIntegerDigits="10" maxFractionDigits="6"/></td>
					<td>${balancelog.fcreatetime}</td>
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
