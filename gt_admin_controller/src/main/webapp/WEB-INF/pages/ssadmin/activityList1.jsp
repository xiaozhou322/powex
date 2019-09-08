<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="/buluo718admin/activityList.html?ty=1">
	<input type="hidden" name="status" value="${status}"> <input
		type="hidden" name="keywords" value="${keywords}" /><input
		type="hidden" name="way" value="${way}" /> <input type="hidden"
		name="pageNum" value="${currentPage}" /> <input type="hidden"
		name="numPerPage" value="${numPerPage}" /> <input type="hidden"
		name="orderField" value="${param.orderField}" /><input type="hidden"
		name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/activityList.html?ty=1" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>关键词：<input type="text" name="keywords" value="${keywords}"
						size="60" />[标题、关键词]</td>
					<td>活动状态：<select type="combox" name="status">
							<c:forEach items="${statusMap}" var="t">
								<c:if test="${t.key == status}">
									<option value="${t.key}" selected="true">${t.value}</option>
								</c:if>
								<c:if test="${t.key != status}">
									<option value="${t.key}">${t.value}</option>
								</c:if>
							</c:forEach>
					</select></td>
					<td>活动方式： <select type="combox" name="way">
							<c:forEach items="${wayMap}" var="w">
								<c:if test="${w.key == way}">
									<option value="${w.key}" selected="true">${w.value}</option>
								</c:if>
								<c:if test="${w.key != way}">
									<option value="${w.key}">${w.value}</option>
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
						</div></li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<%-- <shiro:hasPermission name="buluo718admin/addArticle.html">
				<li><a class="add"
					href="/buluo718admin/goArticleJSP.html?url=ssadmin//addArticle"
					height="500" width="900" target="dialog" rel="addArticle"><span>新增</span>
				</a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="buluo718admin/deleteArticle.html">
				<li><a class="delete"
					href="/buluo718admin/deleteArticle.html?uid={sid_user}" target="ajaxTodo"
					title="确定要删除吗?"><span>删除</span> </a></li>
			</shiro:hasPermission> --%>
			<%-- <shiro:hasPermission name="buluo718admin/updateActivity.html">
				<li><a class="edit"
					href="/buluo718admin/goActivityJSP.html?url=ssadmin//updateActivity&id={sid_user}"
					height="500" width="900" target="dialog" rel="updateActivity"><span>修改</span>
				</a></li>
			</shiro:hasPermission> --%>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">序号</th>
				<th width="60" orderField="name"
					<c:if test='${param.orderField == "name" }'> class="${param.orderDirection}"  </c:if>>活动名称</th>
				<th width="60" orderField="content"
					<c:if test='${param.orderField == "content" }'> class="${param.orderDirection}"  </c:if>>活动内容</th>
				<th width="60">总期数</th>
				<th width="60">消耗虚拟币类型</th>
				<th width="60">消耗虚拟币数量</th>
				<th width="60">活动状态</th>
				<th width="60">活动方式</th>
				<th width="60">创建时间</th>
				<th width="60">开始时间</th>
				<th width="60">结束时间</th>

			</tr>
		</thead>
		<tbody>
			<c:forEach items="${factivityModelList}" var="factivityModel" varStatus="num">
				<tr target="sid_user" rel="${factivityModel.id}">
					<td>${num.index +1}</td>
					<td>${factivityModel.name}</td>
					<td>${factivityModel.content}</td>
					<td>${factivityModel.total_round}</td>
					<td>${factivityModel.fvirtualcointype.fShortName}</td>
					<td>${factivityModel.coin_amount}</td>
					<td>${statusMap[factivityModel.status]}</td>
					<td>${wayMap[factivityModel.way]}</td>
					<td>${factivityModel.create_time}</td>
					<td>${factivityModel.start_time}</td>
					<td>${factivityModel.end_time}</td>
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
