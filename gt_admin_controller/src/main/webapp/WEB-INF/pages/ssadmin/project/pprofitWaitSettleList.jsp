<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="/buluo718admin/pprofitWaitSettleList.html">
	<input type="hidden" name="keywords" value="${keywords}" />
	<input type="hidden" name="startDate" value="${startDate}" />
	<input type="hidden" name="endDate" value="${endDate}" />
	<input type="hidden" name="pageNum" value="${currentPage}" /> 
	<input type="hidden" name="numPerPage" value="${numPerPage}" /> 
	<input type="hidden" name="orderField" value="${param.orderField}" /> 
	<input type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/pprofitWaitSettleList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>关键词：<input type="text" name="keywords" value="${keywords}"
						size="60" />[会员信息]</td>
					<td>统计开始日期： <input type="text" name="startDate" class="date"
						readonly="true" value="${startDate}" />
					</td>
					<td>统计结束日期： <input type="text" name="endDate" class="date"
						readonly="true" value="${endDate}" />
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
			<shiro:hasPermission name="buluo718admin/auditUser1.html">
				<li><a class="edit"
					href="/buluo718admin/pprofitSettle.html?keywords=${keywords}&startDate=${startDate}
					&endDate=${endDate}"
					target="ajaxTodo" title="确定要结算吗?"><span>结算</span>
				</a>
				</li>
			</shiro:hasPermission>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th align="center" width="60" orderField="projectId"
					<c:if test='${param.orderField == "projectId" }'> class="${param.orderDirection}"  </c:if>>项目方ID</th>
				<th align="center" width="60">项目方</th>
				<th align="center" width="60" orderField="statisticalDate"
					<c:if test='${param.orderField == "statisticalDate" }'> class="${param.orderDirection}"  </c:if>>统计时间</th>
				<th align="center" width="60" orderField="trademappingId"
					<c:if test='${param.orderField == "trademappingId" }'> class="${param.orderDirection}"  </c:if>>交易市场</th>
				<th align="center" width="60" orderField="cointypeId"
					<c:if test='${param.orderField == "cointypeId" }'> class="${param.orderDirection}"  </c:if>>收益币种</th>
				<th align="center" width="60">收益</th>
				<th align="center" width="60">收益类型</th>
				<th align="center" width="60">结算状态</th>
				<th align="center" width="60" orderField="createTime"
					<c:if test='${param.orderField == "createTime" }'> class="${param.orderDirection}"  </c:if>>创建时间</th>
				<th align="center" width="60" orderField="updateTime"
					<c:if test='${param.orderField == "updateTime" }'> class="${param.orderDirection}"  </c:if>>修改时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pprofitlogsList}" var="pprofitlogs" varStatus="num">
				<tr target="sid_profitlogs" rel="${pprofitlogs.id}">
					<td>${pprofitlogs.projectId.fid}</td>
					<td>${pprofitlogs.projectId.frealName}</td>
					<td>${pprofitlogs.statisticalDate}</td>
					<td>${pprofitlogs.trademappingStr}</td>
					<td>${pprofitlogs.cointypeId.fShortName}</td>
					<td><ex:DoubleCut value="${pprofitlogs.amount}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="6"/></td>
					<td>
						<c:choose>
							<c:when test="${pprofitlogs.profitType == 1}">买入返佣</c:when>
							<c:when test="${pprofitlogs.profitType == 2}">卖出返佣</c:when>
							<c:when test="${pprofitlogs.profitType == 3}">买入手续费</c:when>
							<c:when test="${pprofitlogs.profitType == 4}">卖出手续费</c:when>
						</c:choose>
					</td>
					<td>
						<c:choose>
							<c:when test="${pprofitlogs.status == 0}">待结算</c:when>
							<c:when test="${pprofitlogs.status == 1}">已结算</c:when>
						</c:choose>
					</td>
					<td>${pprofitlogs.createTime}</td>
					<td>${pprofitlogs.updateTime}</td>
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
