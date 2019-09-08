<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post"
	action="/buluo718admin/drawAccountsList.html">
	<input type="hidden" name="status" value="${param.status}">
	<input type="hidden" name="drawAccountsFrom" value="${drawAccountsFrom}" />
	<input type="hidden" name="drawAccountsTo" value="${drawAccountsTo}" />
	<input type="hidden" name="coinType" value="${coinType}" /> 
	<input type="hidden" name="ftype" value="${ftype}" /> 
	<input type="hidden" name="logDate1" value="${logDate1}" />
	<input type="hidden" name="logDate2" value="${logDate2}" />
	<input type="hidden" name="pageNum" value="${currentPage}" /> 
	<input type="hidden" name="numPerPage" value="${numPerPage}" /> 
	<input type="hidden" name="orderField" value="${param.orderField}" />
	<input type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/drawAccountsList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>划账人信息：<input type="text" name="drawAccountsFrom" value="${drawAccountsFrom}"
						size="10" /></td>
					<td>被划账人信息：<input type="text" name="drawAccountsTo" value="${drawAccountsTo}"
						size="10" /></td>
					<td>划转类型： 
						<select type="combox" name="ftype">
								<option value="-10">全部</option>
								<option value="1" <c:if test="${ftype==1}">selected="true"</c:if> >钱包——>交易所</option>
								<option value="2" <c:if test="${ftype==2}">selected="true"</c:if> >交易所——>钱包</option>
						</select> 
					</td>
					<td>划账币种： <select type="combox" name="coinType"">
									<c:forEach items="${coinTypeMap}" var="sta">
										<c:if test="${sta.key == coinType}">
											<option value="${sta.key}" selected="true">${sta.value}</option>
										</c:if>
										<c:if test="${sta.key != coinType}">
											<option value="${sta.key}">${sta.value}</option>
										</c:if>
									</c:forEach>
							</select>
					</td>
					<td>创建时间起： <input type="text" name="logDate1" class="date"
						readonly="true" value="${logDate1 }" />
					</td>
					<td>创建时间止： <input type="text" name="logDate2" class="date"
						readonly="true" value="${logDate2 }" />
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
		<shiro:hasPermission name="buluo718admin/drawAccountsList.html">
			<li><a class="icon" href="/buluo718admin/drawAccountsExport.html"
				target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span>
			</a></li>
			
			<li><a class="icon" href="/buluo718admin/drawAccountReport.html"
				target="dialog" rel=auditUser height="300" width="800"><span>统计数据</span>
			</a></li>
		</shiro:hasPermission>	
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th align="center" width="20">订单ID</th>
				<th align="center" width="60" orderField="fuserFrom.fid"
					<c:if test='${param.orderField == "fuserFrom.fid" }'> class="${param.orderDirection}"  </c:if>>划账人ID</th>
				<th align="center" width="60" orderField="fuserFrom.frealName"
					<c:if test='${param.orderField == "fuserFrom.frealName" }'> class="${param.orderDirection}"  </c:if>>划账人姓名</th>
				<th align="center" width="60" orderField="fuserTo.fid"
					<c:if test='${param.orderField == "fuserTo.fid" }'> class="${param.orderDirection}"  </c:if>>被划账人ID</th>
				<th align="center" width="60" orderField="fuserTo.frealName"
					<c:if test='${param.orderField == "fuserTo.frealName" }'> class="${param.orderDirection}"  </c:if>>被划账人姓名</th>
				<th align="center" width="60" orderField="fcointype.fid"
					<c:if test='${param.orderField == "fcointype.fid" }'> class="${param.orderDirection}"  </c:if>>划账币种</th>
				<th align="center" width="60">划账类型</th>
				<th align="center" width="60">划账数量</th>
				<th align="center" width="60" orderField="fcreateTime"
					<c:if test='${param.orderField == "fcreateTime" }'> class="${param.orderDirection}"  </c:if>>创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${drawAccountsList}" var="drawAccounts"
				varStatus="num">
				<tr target="sid_user" rel="${drawAccounts.fid}">
					<td>${drawAccounts.fid}</td>
					<td>${drawAccounts.fuserFrom.fid}</td>
					<td>${drawAccounts.fuserFrom.frealName}</td>
					<td>${drawAccounts.fuserTo.fid}</td>
					<td>${drawAccounts.fuserTo.frealName}</td>
					<td>${drawAccounts.fcointype.fShortName}</td>
					<td>
						<c:choose>
							<c:when test="${drawAccounts.ftype == 1}">钱包——>交易所</c:when>
							<c:when test="${drawAccounts.ftype == 2}">交易所——>钱包</c:when>
						</c:choose>
					</td>
					<td><fmt:formatNumber value="${drawAccounts.famount}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td>${drawAccounts.fcreateTime}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="panelBar">
		<div class="pages">
			<span>总共: ${totalCount}条，总数量：${totalAmount }</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${totalCount}"
			numPerPage="${numPerPage}" pageNumShown="5"
			currentPage="${currentPage}"></div>
	</div>
</div>
