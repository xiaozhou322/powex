<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="/buluo718admin/assetList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="type" value="${ftype}" /><input type="hidden"
		name="pageNum" value="${currentPage}" /> <input type="hidden"
		name="numPerPage" value="${numPerPage}" /> <input type="hidden"
		name="orderField" value="${param.orderField}" /><input type="hidden"
		name="orderDirection" value="${param.orderDirection}" /><input type="hidden"
		name="address" value="${address}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/assetList.html" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>会员信息：<input type="text" name="keywords"
						value="${keywords}" size="60" /></td>
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
				<th width="60">日期</th>
				<th width="60">会员UID</th>
				<th width="60">会员登陆名</th>
				<th width="60">会员真实姓名</th>
				<th width="60">人民币(￥)</th>
				<th width="60">USDT($)</th>
				<c:forEach var="v" varStatus="vs" items="${fvirtualcointypes }">
					<th width="60">${v.fname }(${v.fSymbol })</th>
				</c:forEach>
				<!-- <th width="60">预估总资产(￥)</th> -->
			</tr>
		</thead>
		<tbody>
		
		<c:forEach items="${assetList }" var="v" varStatus="vs">
				<tr target="sid_user" rel="${v.fid}">
					<td>
							<fmt:formatDate value="${v.flastupdatetime }" pattern="yyyy-MM-dd"/>
					</td>
					<td>
						${v.fuser.fid }
					</td>
					<td>
						${v.fuser.floginName }
					</td>
					<td>
						${v.fuser.frealName }
					</td>
					<c:forEach var="vv" varStatus="vvs" items="${v.list }">
						<td >
                                                                   可用：${vv.value1 }|冻结${vv.value2 }
						</td>
					</c:forEach>
					<%-- <td>
							${v.ftotal }
					</td> --%>
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
