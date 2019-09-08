<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="/buluo718admin/userinfoList1.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /><input
		type="hidden" name="id" value="${id}" /><input type="hidden"
		name="pageNum" value="${currentPage}" /> <input type="hidden"
		name="numPerPage" value="${numPerPage}" /> <input type="hidden"
		name="orderField" value="${param.orderField}" /> <input type="hidden"
		name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/userinfoList1.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>登陆名：<input type="text" name="keywords"
						value="${keywords}" size="60" /><input type="hidden" name="id"
						value="${id}" />
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
	<table class="table" width="200%" layoutH="138">
		<thead>
			<tr>
				<th width="40" orderField="t.ID"
					<c:if test='${param.orderField == "t.ID" }'> class="${param.orderDirection}"  </c:if>>UID</th>
				<th width="40" orderField=t.name
					<c:if test='${param.orderField == "t.name" }'> class="${param.orderDirection}"  </c:if>>登陆名</th>
				<th width="40" orderField="t.realName"
					<c:if test='${param.orderField == "t.realName" }'> class="${param.orderDirection}"  </c:if>>真实姓名</th>
				<th width="40" orderField="t.grade"
					<c:if test='${param.orderField == "t.grade" }'> class="${param.orderDirection}"  </c:if>>会员自身级别</th>
				<th width="40" orderField="t.buyQty"
					<c:if test='${param.orderField == "t.buyQty" }'> class="${param.orderDirection}"  </c:if>>租地数量</th>
				<th width="40" orderField="t.zhongQty"
					<c:if test='${param.orderField == "t.zhongQty" }'> class="${param.orderDirection}"  </c:if>>种树数量</th>
				<th width="40" orderField="t.borrowCny"
					<c:if test='${param.orderField == "t.borrowCny" }'> class="${param.orderDirection}"  </c:if>>p2p中借钱金额</th>
				<th width="40" orderField="t.borrowDou"
					<c:if test='${param.orderField == "t.borrowDou" }'> class="${param.orderDirection}"  </c:if>>p2p中借豆数量</th>
				<th width="40" orderField="t.lendCny"
					<c:if test='${param.orderField == "t.lendCny" }'> class="${param.orderDirection}"  </c:if>>放贷金额</th>
				<th width="40" orderField="t.lendDou"
					<c:if test='${param.orderField == "t.lendDou" }'> class="${param.orderDirection}"  </c:if>>放豆数量</th>
				<th width="40" orderField="t.inAmt"
					<c:if test='${param.orderField == "t.inAmt" }'> class="${param.orderDirection}"  </c:if>>累计充值金额</th>
				<th width="40" orderField="t.outAmt"
					<c:if test='${param.orderField == "t.outAmt" }'> class="${param.orderDirection}"  </c:if>>累计提现金额</th>
				<th width="40" orderField="t.totalAmt"
					<c:if test='${param.orderField == "t.totalAmt" }'> class="${param.orderDirection}"  </c:if>>账户可用金额</th>
				<th width="40" orderField="t.frozenAmt"
					<c:if test='${param.orderField == "t.frozenAmt" }'> class="${param.orderDirection}"  </c:if>>账户冻结金额</th>
				<th width="40" orderField="t.introlAmt"
					<c:if test='${param.orderField == "t.introlAmt" }'> class="${param.orderDirection}"  </c:if>>其中推荐奖励金额</th>
				<th width="40" orderField="t.totalDou"
					<c:if test='${param.orderField == "t.totalDou" }'> class="${param.orderDirection}"  </c:if>>账户可用FC</th>
				<th width="40" orderField="t.frozenDou"
					<c:if test='${param.orderField == "t.frozenDou" }'> class="${param.orderDirection}"  </c:if>>账户冻结FC</th>
				<th width="40" orderField="t.introlDou"
					<c:if test='${param.orderField == "t.introlDou" }'> class="${param.orderDirection}"  </c:if>>其中推荐奖励FC</th>
				<th width="40" orderField="t.zhongDou"
					<c:if test='${param.orderField == "t.zhongDou" }'> class="${param.orderDirection}"  </c:if>>其中种树奖励FC</th>
				<th width="40" orderField="t.tradeBuyAmt"
					<c:if test='${param.orderField == "t.tradeBuyAmt" }'> class="${param.orderDirection}"  </c:if>>交易（买）总金额</th>
				<th width="40" orderField="t.tradeBuyDou"
					<c:if test='${param.orderField == "t.tradeBuyDou" }'> class="${param.orderDirection}"  </c:if>>交易（买）总FC</th>
				<th width="40" orderField="t.tradeSellAmt"
					<c:if test='${param.orderField == "t.tradeSellAmt" }'> class="${param.orderDirection}"  </c:if>>交易（卖）总金额</th>
				<th width="40" orderField="t.tradeSellDou"
					<c:if test='${param.orderField == "t.tradeSellDou" }'> class="${param.orderDirection}"  </c:if>>交易（卖）总FC</th>
				<th width="40" orderField="t.totalGet1"
					<c:if test='${param.orderField == "t.totalGet1" }'> class="${param.orderDirection}"  </c:if>>累计充值金额-累计提现金额-账户可用金额-账户可用冻结金额</th>
				<th width="40" orderField="t.totalGet2"
					<c:if test='${param.orderField == "t.totalGet2" }'> class="${param.orderDirection}"  </c:if>>（累计充值金额-累计提现金额-账户可用金额-账户可用冻结金额）/(账户可用FC+账户冻结FC)</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${userinfoList}" var="v">
				<tr target="sid_user">
				<td>${v.ID}</td>
				<td>${v.name}</td>
				<td>${v.realName}</td>
				<td>${v.grade}</td>
				<td>${v.buyQty}</td>
				<td>${v.zhongQty}</td>
				<td>${v.borrowCny}</td>
				<td>${v.borrowDou}</td>
				<td>${v.lendCny}</td>
				<td>${v.lendDou}</td>
				<td>${v.inAmt}</td>
				<td>${v.outAmt}</td>
				<td>${v.totalAmt}</td>
				<td>${v.frozenAmt}</td>
				<td>${v.introlAmt}</td>
				<td>${v.totalDou}</td>
				<td>${v.frozenDou}</td>
				<td>${v.introlDou}</td>
				<td>${v.zhongDou}</td>
				<td>${v.tradeBuyAmt}</td>
				<td>${v.tradeBuyDou}</td>
				<td>${v.tradeSellAmt}</td>
				<td>${v.tradeSellDou}</td>
				<td>${v.totalGet1}</td>
				<td>${v.totalGet2}</td>
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
