<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="/buluo718admin/logList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> 
		<input type="hidden" name="ftype" value="${ftype}" /><input
		type="hidden" name="pageNum" value="${currentPage}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /><input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/logList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>UID：<input type="text" name="keywords" value="${keywords}"
						size="30" />
					</td>
					<td>操作类型： <select type="combox" name="ftype">
							<c:forEach items="${logType}" var="type">
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
						</div></li>
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
				<th width="60" orderField="fkey1"
					<c:if test='${param.orderField == "fkey1" }'> class="${param.orderDirection}"  </c:if>>UID</th>
				<th width="60" orderField="fkey2"
					<c:if test='${param.orderField == "fkey2" }'> class="${param.orderDirection}"  </c:if>>登陆名</th>
				<th width="100" orderField="fkey3"
					<c:if test='${param.orderField == "fkey3" }'> class="${param.orderDirection}"  </c:if>>IP地址</th>
				<th width="100" orderField="ftype"
					<c:if test='${param.orderField == "ftype" }'> class="${param.orderDirection}"  </c:if>>操作类型</th>
				<th width="100">操作说明</th>
				<th width="100" orderField="fcreateTime"
					<c:if test='${param.orderField == "fcreateTime" }'> class="${param.orderDirection}"  </c:if>>创建日期</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${logList}" var="log" varStatus="num">
				<tr target="sid_user" rel="${log.fid}">
					<td>${log.fkey1}</td>
					<td>${log.fkey2}</td>
					<td>${log.fkey3}</td>
					<td>${log.ftype_s}</td>
					<td>${log.fkey4}</td>
					<td>${log.fcreateTime}</td>
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
