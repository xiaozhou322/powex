<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="/buluo718admin/bannerList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /><input
		type="hidden" name="type" value="${ftype}" /> <input type="hidden"
		name="pageNum" value="${currentPage}" /> <input type="hidden"
		name="numPerPage" value="${numPerPage}" /> <input type="hidden"
		name="orderField" value="${param.orderField}" /><input type="hidden"
		name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/bannerList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>关键词：<input type="text" name="keywords" value="${keywords}"
						size="60" />[标题、关键词]</td>
					<td></td>
					<td>Banner类型： <select type="combox" name="ftype">
						<c:forEach items="${type}" var="t">
							<option value="${t.key}" <c:if test="${t.key eq ftype }">selected</c:if> >${t.value}</option>
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
						</div></li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<shiro:hasPermission name="buluo718admin/addBanner.html">
				<li><a class="add"
					href="/buluo718admin/goBannerJSP.html?url=ssadmin/banner/add"
					height="500" width="900" target="dialog" rel="addBanner"><span>新增</span>
				</a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="buluo718admin/deleteBanner.html">
				<li><a class="delete"
					href="/buluo718admin/deleteBanner.html?uid={sid_user}" target="ajaxTodo"
					title="确定要删除吗?"><span>删除</span> </a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="buluo718admin/updateBanner.html">
				<li><a class="edit"
					href="/buluo718admin/goBannerJSP.html?url=ssadmin/banner/update&uid={sid_user}"
					height="500" width="900" target="dialog" rel="updateBanner"><span>修改</span>
				</a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="buluo718admin/enableBanner.html">
				<li><a class="edit"
					href="/buluo718admin/enableBanner.html?uid={sid_user}&status=1" target="ajaxTodo"
					title="确定要启用吗?"><span>启用</span> </a></li>
				<li><a class="delete"
					href="/buluo718admin/enableBanner.html?uid={sid_user}&status=0" target="ajaxTodo"
					title="确定要禁用吗?"><span>禁用</span> </a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">序号</th>
				<th width="60" orderField="ftitle"
					<c:if test='${param.orderField == "ftitle" }'> class="${param.orderDirection}"  </c:if>>标题</th>
				<th width="60" orderField="ftype"
					<c:if test='${param.orderField == "ftype" }'> class="${param.orderDirection}"  </c:if>>类型</th>
				<th width="60" orderField="fstatus"
					<c:if test='${param.orderField == "fstatus" }'> class="${param.orderDirection}"  </c:if>>状态</th>
				<th width="60" orderField="fcreateDate"
					<c:if test='${param.orderField == "fcreateDate" }'> class="${param.orderDirection}"  </c:if>>创建时间</th>
				<th width="60" orderField="fadminByFcreateAdmin.fname"
					<c:if test='${param.orderField == "fadminByFcreateAdmin.fname" }'> class="${param.orderDirection}"  </c:if>>创建人</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${bannerList}" var="banner" varStatus="num">
				<tr target="sid_user" rel="${banner.fid}">
					<td>${num.index +1}</td>
					<td>${banner.ftitle}</td>
					<td>${banner.ftype}</td>
					<td>${banner.fstatus}</td>
					<td>${banner.fcreateDate}</td>
					<td>${banner.fadminByFcreateAdmin.fname}</td>
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
