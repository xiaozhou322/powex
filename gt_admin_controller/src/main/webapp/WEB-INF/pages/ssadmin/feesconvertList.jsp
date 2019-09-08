<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post"
	action="/buluo718admin/feesConvertList.html">
	<input type="hidden" name="startDate" value="${startDate}" />
	<input type="hidden" name="endDate" value="${endDate}" />
	<input type="hidden" name="status" value="${status}" />
	<input type="hidden" name="pageNum" value="${currentPage}" /> 
	<input type="hidden" name="numPerPage" value="${numPerPage}" /> 
	<input type="hidden" name="orderField" value="${param.orderField}" />
	<input type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/feesConvertList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>创建时间起： <input type="text" name="startDate" class="date"
						readonly="true" value="${startDate }" />
					</td>
					<td>创建时间止： <input type="text" name="endDate" class="date"
						readonly="true" value="${endDate }" />
					</td>
					<td>状态： <select type="combox" name="status">
							<option value="0">全部</option>
							<option value="1" <c:if test="${status==1}">selected="true"</c:if> >未处理</option>
							<option value="2" <c:if test="${status==2}">selected="true"</c:if> >已处理</option>
					</select> </td>
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
		<shiro:hasPermission name="buluo718admin/feesConvertList.html">
				<li>
					<a class="add"
						href="/buluo718admin/goFeesConvertJSP.html?url=ssadmin/addFeesConvert"
						height="400" width="750" target="dialog" rel="addFeesConvert"><span>新增</span>
					</a>
				</li>
				<li><a class="edit"
					href="/buluo718admin/goFeesConvertJSP.html?url=ssadmin/editFeesConvert&pid={sid_logs}"
					target="dialog" rel="editFeesConvert" height="400" width="750"><span>修改</span>
				</a>
				</li>
				<li><a class="delete"
					href="/buluo718admin/deleteFeesConvert.html?uid={sid_logs}"
					target="ajaxTodo" title="确定要删除记录吗?"><span>删除</span>
				</a>
				</li>
			</shiro:hasPermission>	
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th align="center" width="20">记录ID</th>
				<th align="center" width="60">项目方ID</th>
				<th align="center" width="60">项目方姓名</th>
				<th align="center" width="60">USDT数量</th>
				<th align="center" width="60">BFSC数量</th>
				<th align="center" width="60">BFSC价格</th>
				<th align="center" width="60">处理状态</th>
				<th align="center" width="60" orderField="createTime"
					<c:if test='${param.orderField == "createTime" }'> class="${param.orderDirection}"  </c:if>>创建时间</th>
					<th align="center" width="60" orderField="createTime"
					<c:if test='${param.orderField == "createTime" }'> class="${param.orderDirection}"  </c:if>>修改时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${feesConvertList}" var="feesConvert"
				varStatus="num">
				<tr target="sid_logs" rel="${feesConvert.id}">
					<td>${feesConvert.id}</td>
					<td>${feesConvert.projectId.fid}</td>
					<td>${feesConvert.projectId.frealName}</td>
					<td><fmt:formatNumber value="${feesConvert.usdtAmount}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><fmt:formatNumber value="${feesConvert.bfscAmount}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><fmt:formatNumber value="${feesConvert.bfscPrice}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td>
						<c:choose>
							<c:when test="${feesConvert.status == 1}">未处理</c:when>
							<c:when test="${feesConvert.status == 2}">已处理</c:when>
						</c:choose>
					</td>
					<td>${feesConvert.createTime}</td>
					<td>${feesConvert.updateTime}</td>
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
