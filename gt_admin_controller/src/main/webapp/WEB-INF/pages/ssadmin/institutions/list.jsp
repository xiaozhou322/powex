<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<form id="pagerForm" method="post"
	action="/buluo718admin/institutionsList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${title}" /><input type="hidden"
		name="type" value="${status}" /> <input type="hidden" name="pageNum"
		value="${currentPage}" /> <input type="hidden" name="numPerPage"
		value="${numPerPage}" /> <input type="hidden" name="orderField"
		value="${param.orderField}" /><input type="hidden"
		name="orderDirection" value="${param.orderDirection}" />
</form>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/institutionsList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>标题：<input type="text" name="keywords" value="${keywords}"
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
			<shiro:hasPermission name="buluo718admin/addAbout">
				<li><a class="add"
					href="/buluo718admin/goAddInstitutions.html?url=ssadmin/institutions/save" height="500"
					width="900" target="dialog" rel="addAbout"><span>新增</span> </a>
				</li>
				<li><a class="edit"
					href="/buluo718admin/goAddInstitutions.html?url=ssadmin/institutions/update&uid={sid_user}"
					height="500" width="900" target="dialog" rel="updateArticle"><span>修改</span>
				</a></li>
				 <li> <a class="edit"
					href="/buluo718admin/forbidden.html?uid={sid_user}&status=1&rel=listUser"
					target="ajaxTodo" title="确定要禁用该机构商吗?"><span>禁用</span> </a>
				</li>
				
				 <li> <a class="edit"
					href="/buluo718admin/startuse.html?uid={sid_user}&status=2&rel=listUser"
					target="ajaxTodo" title="确定要启用该机构商吗?"><span>启用</span> </a>
				</li>		
				
				
				<li> <a class="edit"
					href="/buluo718admin/forbiddenconfirm.html?uid={sid_user}&status=3&rel=listUser"
					target="ajaxTodo" title="确定要禁用该机构商吗?"><span>禁用买单自动确认</span> </a>
				</li>
				
				 <li> <a class="edit"
					href="/buluo718admin/startuseconfirm.html?uid={sid_user}&status=4&rel=listUser"
					target="ajaxTodo" title="确定要启用该机构商吗?"><span>启用买单自动确认</span> </a>
				</li>							
			</shiro:hasPermission>
			
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th>机构id</th>
				<th>机构名称</th>
				<th>机构简称</th>
				<th>机构负责人</th>
				<th>机构负责人联系方式</th>
				<th>业务人员</th>
				<th>业务人员联系方式</th>
				<th>机构状态</th>
				<th>是否支持卖单自动确认</th>
				<th>页面回调地址</th>
				<th>服务器回调地址</th>
				<th>首页返回地址</th>
				<th>创建时间</th>
				<th>更新时间</th>
				<th>录入人</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${institutionsinfoList}" var="v" varStatus="vs">
				<tr target="sid_user" rel="${v.id}">
					<td>${v.id }</td>
					<td>${v.institutions_name }</td>
					<td>${v.institutions_short_name }</td>
					<td>${v.institutions_username }</td>
					<td>${v.institutions_user_contact }</td>
					<td>${v.business_people }</td>
					<td>${v.business_people_contact }</td>
					<td>${v.institutions_status == 1 ? "启用" : "禁用" }</td>
					<td>${v.auto_confirm == 1 ? "支持" : "不支持" }</td>
					<td>${v.page_callback_url }</td>
					<td>${v.server_callback_url }</td>
					<td>${v.index_url }</td>
					<td><fmt:formatDate value="${v.create_time }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td><fmt:formatDate value="${v.update_time }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>${v.fadmin.fname }</td>
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
			currentPage="${institutionsMap.pageIndex}"></div>
	</div>
</div>
