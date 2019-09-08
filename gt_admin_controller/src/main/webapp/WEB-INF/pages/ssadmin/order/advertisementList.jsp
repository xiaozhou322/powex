<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<form id="pagerForm" method="post"
	action="/buluo718admin/advertisementList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${title}" /><input type="hidden"
		name="type" value="${status}" /> <input type="hidden" name="pageNum"
		value="${currentPage}" /> <input type="hidden" name="numPerPage"
		value="${numPerPage}" /> <input type="hidden" name="orderField"
		value="${param.orderField}" /><input type="hidden"
		name="orderDirection" value="${param.orderDirection}" />
		
		<input type="hidden"
		name="userid" value="${userid}" />
		<input type="hidden"
		name="startDate" value="${startDate}" />
		<input type="hidden"
		name="endDate" value="${endDate}" />
		<input type="hidden"
		name="cur_type" value="${cur_type}" />
		<input type="hidden"
		name="ad_type" value="${ad_type}" />
		<input type="hidden"
		name="ad_status" value="${ad_status}" />
</form>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/advertisementList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>&nbsp;会员ID：&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="userid" value="${userid}"
						size="20" />
					</td>
					
					<td>&nbsp;&nbsp;开始时间： <input type="text" name="startDate" class="date"
						readonly="true" value="${startDate }"  dateFmt="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>&nbsp;&nbsp;结束时间： <input type="text" name="endDate" class="date"
						readonly="true" value="${endDate }"  dateFmt="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td></td>
					</tr>
					<tr>
					<td>币种类型： <select type="combox" name="cur_type" style="width:152px;">
									<c:forEach items="${typeMap}" var="sta">
										<c:if test="${sta.key == cur_type}">
											<option value="${sta.key}" selected="true">${sta.value}</option>
										</c:if>
										<c:if test="${sta.key != cur_type}">
											<option value="${sta.key}">${sta.value}</option>
										</c:if>
									</c:forEach>
							</select>
					</td>
					
					<td>&nbsp;广告类型： <select type="combox" name="ad_type" style="width:152px;">
	                             <c:forEach items="${orderType}" var="order">
									<c:if test="${order.key == ad_type}">
										<option value="${order.key}" selected="true">${order.value}</option>
									</c:if>
									<c:if test="${order.key != ad_type}">
										<option value="${order.key}">${order.value}</option>
									</c:if>
								</c:forEach>
						</select>
					</td>
					
					<td>&nbsp;状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态： <select type="combox" name="ad_status" style="width:152px;">
                             <c:forEach items="${adStatusList}" var="order">
								<c:if test="${order.key == ad_status}">
									<option value="${order.key}" selected="true">${order.value}</option>
								</c:if>
								<c:if test="${order.key != ad_status}">
									<option value="${order.key}">${order.value}</option>
								</c:if>
							</c:forEach>
					</select></td>
					<td><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">查询</button>
							</div>
						</div></td>
				</tr>
			</table>

		</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
        <ul class="toolBar">
			  <shiro:hasPermission name="buluo718admin/userList.html">
				  <li> <a class="edit"
					href="/buluo718admin/updateadvertisementStatus0.html?id={sid_user}&status=1&rel=listUser"
					target="ajaxTodo" title="确定要上架该广告吗?"><span>上架</span> </a>
				</li>
			</shiro:hasPermission>
			  <shiro:hasPermission name="buluo718admin/userList.html">
				  <li> <a class="edit"
					href="/buluo718admin/updateadvertisementStatus.html?id={sid_user}&status=0&rel=listUser"
					target="ajaxTodo" title="确定要下架该广告吗?" ><span>下架</span> </a>
				</li>
			</shiro:hasPermission>
			
		</ul>
		
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th>广告ID</th>
				<th>会员ID</th>
				<th>登录名称</th>
				<th>商户对象</th>
				<th>币种类型</th>
				<th>广告类型</th>
				<th>状态</th>
				<th>数量</th>
				<th>限额</th>    
				<th>单价</th>    
				<th>创建时间</th>    
				<th>更新时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${advertisementList}" var="v" varStatus="vs">
				<tr  target="sid_user" rel="${v.id}" >
					<td>${v.id }</td>
					<td>${v.user.fid }</td>
					<td>${v.user.floginName }</td>
					<td>${v.user.fnickName }</td>
					<td>${v.fvirtualcointype.fname }</td>
					<td>${v.ad_type == 1 ? "出售" : "求购" }</td>
					<td>${v.status == 1 ? "下架" : "上架" }</td>
					<td>${v.repertory_count }</td>
					<td>${v.order_limit_min }~${v.order_limit_max }</td>
					<td>${v.price }</td>
					<td><fmt:formatDate value="${v.create_time }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td><fmt:formatDate value="${v.update_time }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
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
