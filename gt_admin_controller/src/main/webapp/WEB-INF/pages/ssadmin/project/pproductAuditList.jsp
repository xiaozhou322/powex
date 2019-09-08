<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="/buluo718admin/pproductAuditList.html">
	<input type="hidden" name="shortName" value="${shortName}" /> 
	<input type="hidden" name="pageNum" value="${currentPage}" /> 
	<input type="hidden" name="numPerPage" value="${numPerPage}" /> 
	<input type="hidden" name="orderField" value="${param.orderField}" /> 
	<input type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/pproductAuditList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>币种简称：<input type="text" name="shortName" value="${shortName}"
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
			<shiro:hasPermission name="buluo718admin/pproductAuditList.html">
				<li><a class="edit"
					href="/buluo718admin/goPproductJSP.html?url=ssadmin//project//pproductAuditDetail&pid={sid_product}&type=1"
					target="dialog" rel="auditProduct" height="700" width="800"><span>审核</span>
				</a>
				</li>
			</shiro:hasPermission>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th align="center" width="60" orderField="name"
					<c:if test='${param.orderField == "name" }'> class="${param.orderDirection}"  </c:if>>名称</th>
				<th align="center" width="60" orderField="shortName"
					<c:if test='${param.orderField == "shortName" }'> class="${param.orderDirection}"  </c:if>>简称</th>
				<%-- <th align="center" width="60" orderField="projectId"
					<c:if test='${param.orderField == "projectId" }'> class="${param.orderDirection}"  </c:if>>项目方</th> --%>
				<th align="center" width="60">兑换比例</th>
				<th align="center" width="60">到期兑换比例</th>
				<th align="center" width="60">开始时间</th>
				<th align="center" width="60">周期</th>
				<th align="center" width="60">发行总量</th>
				<th align="center" width="60">产品可用额</th>
				<th align="center" width="60">产品冻结额</th>
				<th align="center" width="60">兑换币种</th>
				<th align="center" width="60">兑换可用额</th>
				<th align="center" width="60">兑换冻结额</th>
				<th align="center" width="60">最大总兑换数量</th>
				<th align="center" width="60">单次最少兑换数量</th>
				<th align="center" width="60">审核状态</th>
				<th align="center" width="80">备注</th>
				<th align="center" width="60" orderField="createTime"
					<c:if test='${param.orderField == "createTime" }'> class="${param.orderDirection}"  </c:if>>创建时间</th>
				<th align="center" width="60" orderField="updateTime"
					<c:if test='${param.orderField == "updateTime" }'> class="${param.orderDirection}"  </c:if>>修改时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pproductList}" var="pproduct" varStatus="num">
				<tr target="sid_product" rel="${pproduct.id}">
					<td>${pproduct.name}</td>
					<td>${pproduct.shortName}</td>
					<%-- <td>${pproduct.projectId.fid}</td> --%>
					<td>${pproduct.convertRatio}</td>
					<td>${pproduct.convertRatioExpire}</td>
					<td>${pproduct.startDate}</td>
					<td>
						<c:forEach items="${periodType}" var="t">
							<c:if test="${t.key == pproduct.period}">${t.value}</c:if>
						</c:forEach>
					</td>
					<td><ex:DoubleCut value="${pproduct.pushTotal}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><ex:DoubleCut value="${pproduct.proAvailableAmount}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><ex:DoubleCut value="${pproduct.proFrozenAmount}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td>${pproduct.convertCointype.fShortName}</td>
					<td><ex:DoubleCut value="${pproduct.convertAvailableAmount}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><ex:DoubleCut value="${pproduct.convertFrozenAmount}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><ex:DoubleCut value="${pproduct.maxTotalAmount}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="2"/></td>
					<td><ex:DoubleCut value="${pproduct.minTimeAmount}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="2"/></td>
					<td>
						<c:choose>
							<c:when test="${pproduct.auditStatus == 101}">待审核</c:when>
							<c:when test="${pproduct.auditStatus == 102}">审核通过</c:when>
							<c:when test="${pproduct.auditStatus == 103}">审核不通过</c:when>
						</c:choose>
					</td>
					<td>${pproduct.remark}</td>
					<td>${pproduct.createTime}</td>
					<td>${pproduct.updateTime}</td>
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
