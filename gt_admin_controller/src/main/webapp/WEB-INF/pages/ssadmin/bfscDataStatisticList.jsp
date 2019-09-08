<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post"
	action="/buluo718admin/bfscDataStatisticList.html">
	<input type="hidden" name="logDate1" value="${logDate1}" />
	<input type="hidden" name="logDate2" value="${logDate2}" />
	<input type="hidden" name="pageNum" value="${currentPage}" /> 
	<input type="hidden" name="numPerPage" value="${numPerPage}" /> 
	<input type="hidden" name="orderField" value="${param.orderField}" />
	<input type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/bfscDataStatisticList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>开始时间起： <input type="text" name="logDate1" class="date"
						readonly="true" value="${logDate1 }" />
					</td>
					<td>开始时间止： <input type="text" name="logDate2" class="date"
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
		<shiro:hasPermission name="buluo718admin/bfscDataStatisticList.html">
			<li>
				<a class="edit"
					href="/buluo718admin/bfscDataStatisticDetail.html?pid={sid_bfsc}"
					target="dialog" rel=auditUser height="800" width="1000"><span>查看</span>
				</a>
			</li>
			
		</shiro:hasPermission>	
		</ul>
	</div>
	<table class="table" width="200%" layoutH="138">
		<thead>
			<tr>
				<th align="center" width="100">用户ID</th>
				<th align="center" width="100">用户姓名</th>
				<th align="center" width="100">统计时间</th>
				<th align="center" width="100">充币数量</th>
				<th align="center" width="100">提币数量</th>
				<th align="center" width="100">充币笔数</th>
				<th align="center" width="100">提币笔数</th>
				<th align="center" width="100">OTC购买数量</th>
				<th align="center" width="100">OTC卖出数量</th>
				<th align="center" width="100">OTC购买金额</th>
				<th align="center" width="100">OTC出售金额</th>
				<th align="center" width="100">OTC购买笔数</th>
				<th align="center" width="100">OTC出售笔数</th>
				<th align="center" width="100">USDT转入数量</th>
				<th align="center" width="100">USDT转入笔数</th>
				<th align="center" width="100">BFSC转入数量</th>
				<th align="center" width="100">BFSC转出数量</th>
				<th align="center" width="100">BFSC存量数</th>
				<th align="center" width="100">市场买入数量</th>
				<th align="center" width="100">市场卖出数量</th>
				<th align="center" width="100">市场买入金额</th>
				<th align="center" width="100">市场卖出金额</th>
				<th align="center" width="100">市场买入手续费</th>
				<th align="center" width="100">市场卖出手续费</th>
				<th align="center" width="100">市场总买入数量</th>
				<th align="center" width="100">市场总卖出数量</th>
				<th align="center" width="100">市场总买入金额</th>
				<th align="center" width="100">市场总卖出金额</th>
				<th align="center" width="100">手续费BFSC数量</th>
				<th align="center" width="100">BFSC均价</th>
				<th align="center" width="100">待交割USDT数量</th>
				<th align="center" width="100">总注册人数</th>
				<th align="center" width="100">新增注册人数</th>
				<th align="center" width="100">创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${bfscDataStatisticsList}" var="bfscData" varStatus="num">
				<tr target="sid_bfsc" rel="${bfscData.id}">
					<td>${bfscData.fuser.fid}</td>
					<td>${bfscData.fuser.frealName}</td>
					<td>${bfscData.statistical_date}</td>
					<td><fmt:formatNumber value="${bfscData.charge_amount}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><fmt:formatNumber value="${bfscData.withdraw_amount}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td>${bfscData.charge_num}</td>
					<td>${bfscData.withdraw_num}</td>
					<td><fmt:formatNumber value="${bfscData.otc_buy_usdt_amount}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><fmt:formatNumber value="${bfscData.otc_sell_usdt_amount}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><fmt:formatNumber value="${bfscData.otc_buy_amount}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><fmt:formatNumber value="${bfscData.otc_sell_amount}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td>${bfscData.otc_buy_num}</td>
					<td>${bfscData.otc_sell_num}</td>
					<td><fmt:formatNumber value="${bfscData.transfer_usdt_in_amount}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td>${bfscData.transfer_usdt_in_num}</td>
					<td><fmt:formatNumber value="${bfscData.transfer_bfsc_in_amount}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><fmt:formatNumber value="${bfscData.transfer_bfsc_out_amount}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><fmt:formatNumber value="${bfscData.bfsc_stock_amount}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><fmt:formatNumber value="${bfscData.market_buy_qty}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><fmt:formatNumber value="${bfscData.market_sell_qty}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><fmt:formatNumber value="${bfscData.market_buy_amount}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><fmt:formatNumber value="${bfscData.market_sell_amount}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><fmt:formatNumber value="${bfscData.market_buy_fees}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><fmt:formatNumber value="${bfscData.market_sell_fees}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><fmt:formatNumber value="${bfscData.market_buy_total_qty}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><fmt:formatNumber value="${bfscData.market_sell_total_qty}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><fmt:formatNumber value="${bfscData.market_buy_total_amount}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><fmt:formatNumber value="${bfscData.market_sell_total_amount}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><fmt:formatNumber value="${bfscData.bfsc_fees_amount}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><fmt:formatNumber value="${bfscData.bfsc_avg_price}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><fmt:formatNumber value="${bfscData.usdt_fees_amount}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td>${bfscData.register_total_num}</td>
					<td>${bfscData.register_add_num}</td>
					<td>${bfscData.createTime}</td>
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
