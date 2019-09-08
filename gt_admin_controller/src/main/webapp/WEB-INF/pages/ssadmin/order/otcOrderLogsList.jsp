<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<form id="pagerForm" method="post"
	action="/buluo718admin/otcOrderLogsList.html">
	 <input type="hidden" name="pageNum" value="${currentPage}" /> 
	 <input type="hidden" name="numPerPage" value="${numPerPage}" /> 
	 <input type="hidden" name="orderField" value="${param.orderField}" />
	 <input type="hidden" name="orderDirection" value="${param.orderDirection}" />
		
	 <input type="hidden" name="orderId" value="${orderId}" />
	 <input type="hidden" name="buyUserId" value="${buyUserId}" />
	 <input type="hidden" name="sellUserId" value="${sellUserId}" />
	 <input type="hidden" name="type" value="${type}" />
	 <input type="hidden" name="startDate" value="${startDate}" />
	 <input type="hidden" name="endDate" value="${endDate}" />
	 <input type="hidden" name="complainSucc" value="${complainSucc}" />
</form>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/otcOrderLogsList.html" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>订单ID：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="orderId" value="${orderId}"
						size="20" /></td>
					</td>
					
					<td>买家用户ID：&nbsp;
						<input type="text" name="buyUserId" value="${buyUserId}" size="20" />
					</td>
					<td>卖家用户ID：&nbsp;
						<input type="text" name="sellUserId" value="${sellUserId}" size="20" />
					</td>
					<td>&nbsp;&nbsp;操作类型： 
						<select type="combox" name="type" style="width:152px;">
                             <c:forEach items="${logsType}" var="logsType">
								<c:if test="${logsType.key == type}">
									<option value="${logsType.key}" selected="true">${logsType.value}</option>
								</c:if>
								<c:if test="${logsType.key != type}">
									<option value="${logsType.key}">${logsType.value}</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>是否被投诉成功： 
						<select type="combox" name="complainSucc" style="width:125px;">
							<option value="-10">全部</option>
							<option value="0" <c:if test="${complainSucc==0}">selected="true"</c:if> >否</option>
							<option value="1" <c:if test="${complainSucc==1}">selected="true"</c:if> >是</option>
						</select>
					</td>
                    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;开始时间： <input type="text" name="startDate" class="date"
						readonly="true" value="${startDate }"  dateFmt="yyyy-MM-dd"/>
					</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;结束时间： <input type="text" name="endDate" class="date"
						readonly="true" value="${endDate }"  dateFmt="yyyy-MM-dd"/>
					</td>
					<td></td>
					<td>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">查询</button>
							</div>
						</div>
					</td>
				</tr>
				
			</table>
		</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			  <shiro:hasPermission name="buluo718admin/otcOrderLogsList.html">
				<li><a class="edit"
					href="/buluo718admin/otcOrderLogsDetail.html?url=ssadmin/order/otcOrderLogsDetail&orderLogsId={sid_logsId}"
					height="500" width="700" target="dialog" rel="otcOrderLogsDetail"><span>订单日志详情</span>
				</a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th align="center" orderField="id"
					<c:if test='${param.orderField == "id" }'> class="${param.orderDirection}"  </c:if>>ID</th>
				<th align="center" orderField="orderId.id"
					<c:if test='${param.orderField == "orderId.id" }'> class="${param.orderDirection}"  </c:if>>订单ID</th>
				<th align="center" orderField="buyUser.fid"
					<c:if test='${param.orderField == "buyUser.fid" }'> class="${param.orderDirection}"  </c:if>>买家用户ID</th>
				<th align="center">买家登录名</th>
				<th align="center">买家姓名</th>
				<th align="center" orderField="sellUser.fid"
					<c:if test='${param.orderField == "sellUser.fid" }'> class="${param.orderDirection}"  </c:if>>卖家用户ID</th>
				<th align="center">卖家登录名</th>
				<th align="center">卖家姓名</th>
				<th align="center" orderField="type"
					<c:if test='${param.orderField == "type" }'> class="${param.orderDirection}"  </c:if>>操作类型</th>
				<th align="center">申诉人</th>
				<th align="center">申诉原因</th>
				<th align="center">是否被投诉成功</th>
				<th align="center" orderField="createTime"
					<c:if test='${param.orderField == "createTime" }'> class="${param.orderDirection}"  </c:if>>创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${orderLogsList}" var="v" varStatus="vs">
				<tr target="sid_logsId" rel="${v.id}">
				    <td>${v.id }</td>
				    <td>${v.orderId.id }</td>
				    <td>${v.buyUser.fid }</td>
				    <td>${v.buyUser.floginName }</td>
					<td>${v.buyUser.frealName }</td>
				    <td>${v.sellUser.fid }</td>
				    <td>${v.sellUser.floginName }</td>
					<td>${v.sellUser.frealName }</td>
					<td>
						<c:forEach items="${logsType}" var="logsType">
							<c:if test="${logsType.key == v.type}">
								${logsType.value}
							</c:if>
						</c:forEach>
	                 </td>
	                <td>${v.appealUser.frealName }</td>
					<td>
						<c:forEach items="${appealReasonMap}" var="appealReason">
						<c:if test="${appealReason.key == v.appealReason}">
							${appealReason.value}
						</c:if>
					</c:forEach>
	                </td>
					<td>
						${v.complainSucc==1 ? "是" : "否" }
	                </td>
					<td><fmt:formatDate value="${v.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
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
