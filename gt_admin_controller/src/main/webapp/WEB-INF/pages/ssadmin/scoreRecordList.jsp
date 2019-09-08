<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="/buluo718admin/scoreRecordList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
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
		action="/buluo718admin/scoreRecordList.html" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>会员信息：<input type="text" name="keywords"
						value="${keywords}" size="60" /></td>
					<td>奖励类型： <select type="combox" name="ftype">
							<c:forEach items="${type}" var="t">
								<c:if test="${t.key == ftype}">
									<option value="${t.key}" selected="true">${t.value}</option>
								</c:if>
								<c:if test="${t.key != ftype}">
									<option value="${t.key}">${t.value}</option>
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
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">序号</th>
				<th width="60" orderField="fuser.floginName"
					<c:if test='${param.orderField == "fuser.floginName" }'> class="${param.orderDirection}"  </c:if>>会员登陆名</th>
				<th width="60" orderField="fuser.fnickName"
					<c:if test='${param.orderField == "fuser.fnickName" }'> class="${param.orderDirection}"  </c:if>>会员昵称</th>
				<th width="60" orderField="fuser.frealName"
					<c:if test='${param.orderField == "fuser.frealName" }'> class="${param.orderDirection}"  </c:if>>会员真实姓名</th>
				<th width="60">数量</th>
				<th width="60">类型</th>
				<th width="60">时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${scoreRecordList}" var="scoreRecord" varStatus="num">
				<tr target="sid_user" rel="${scoreRecord.fid}">
					<td>${num.index +1}</td>
					<td>${scoreRecord.fuser.floginName}</td>
					<td>${scoreRecord.fuser.fnickName}</td>
					<td>${scoreRecord.fuser.frealName}</td>
					<td><fmt:formatNumber value="${scoreRecord.score}" pattern="##.######" maxIntegerDigits="10" maxFractionDigits="6"/></td>
					<td>${scoreRecord.type_s}</td>
					<td>${scoreRecord.fcreatetime}</td>
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
