<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="/buluo718admin/pushList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="keywords1" value="${keywords1}" /><input
		type="hidden" name="ftype" value="${ftype}" /><input type="hidden"
		name="pageNum" value="${currentPage}" /> <input type="hidden"
		name="numPerPage" value="${numPerPage}" /> <input type="hidden"
		name="orderField" value="${param.orderField}" /><input type="hidden"
		name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/pushList.html" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>买方会员信息：<input type="text" name="keywords"
						value="${keywords}" size="60" /></td>
					<td>卖方会员信息：<input type="text" name="keywords1"
						value="${keywords1}" size="60" /></td>	
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
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">序号</th>
				<th width="60">买方UID</th>
				<th width="60">卖方UID</th>
				<th width="60">资产名称</th>
				<th width="60">单价</th>
				<th width="60">数量</th>
				<th width="60">总金额</th>
				<th width="60">手续费率</th>
				<th width="60">手续费金额</th>
				<th width="60">状态</th>
				<th width="60">创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pushList}" var="push" varStatus="num">
				<tr target="sid_user" rel="${push.fid}">
					<td>${num.index +1}</td>
					<td>${push.fuserByFbuyuserid.fid}</td>
					<td>${push.fuserByFsellUserid.fid}</td>
					<td>${push.fvirtualcointype.fname}</td>
					<td>￥<fmt:formatNumber value="${push.fprice}" pattern="##.##" maxIntegerDigits="10" maxFractionDigits="4"/></td>
					<td><fmt:formatNumber value="${push.fqty}" pattern="##.##" maxIntegerDigits="10" maxFractionDigits="4"/></td>
					<td>￥<fmt:formatNumber value="${push.ftotal}" pattern="##.##" maxIntegerDigits="10" maxFractionDigits="4"/></td>
					<td><fmt:formatNumber value="${push.frate}" pattern="##.##" maxIntegerDigits="10" maxFractionDigits="4"/></td>
					<td>￥<fmt:formatNumber value="${push.ffees}" pattern="##.##" maxIntegerDigits="10" maxFractionDigits="4"/></td>
					<td>${push.fstatus_s}</td>
					<td>${push.fcreatetime}</td>
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
