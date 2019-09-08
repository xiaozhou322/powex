<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">
	每日BFSC资金数据统计报表
</h2>

				

<div class="pageFormContent" layoutH="20">

		<fieldset>
		<legend>充提和OTC统计 <a target="_blank" href="/buluo718admin/exportfscDataStatistic.html?pid=${bfscDataStatistics.id}">导出数据</a></legend>
			<dl>
				<dt>统计日期：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit">${bfscDataStatistics.statistical_date}</span>
				</dd>
			</dl>
			<dl>
				<dt>创建时间：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit"><fmt:formatDate value="${bfscDataStatistics.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
				</dd>
			</dl>
			<dl>
				<dt>充币数量：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit"><fmt:formatNumber value="${bfscDataStatistics.charge_amount}" pattern="#0.######" /></span>
				</dd>
			</dl>
			<dl>
				<dt>提币数量：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit"><fmt:formatNumber
								value="${bfscDataStatistics.withdraw_amount}" pattern="#0.######" /></span>
				</dd>
			</dl>
			
			<dl>
				<dt>充币笔数：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit"><fmt:formatNumber
								value="${bfscDataStatistics.charge_num}" pattern="#0.######" /></span>
				</dd>
			</dl>
			<dl>
				<dt>提币笔数：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit"><fmt:formatNumber
								value="${bfscDataStatistics.withdraw_num}" pattern="#0.######" /></span>
				</dd>
			</dl>
			<dl>
				<dt>OTC购买数量：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit"><fmt:formatNumber
								value="${bfscDataStatistics.otc_buy_usdt_amount}" pattern="#0.######" /></span>
				</dd>
			</dl>
			<dl>
				<dt>OTC卖出数量：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit"><fmt:formatNumber
								value="${bfscDataStatistics.otc_sell_usdt_amount}" pattern="#0.######" /></span>
				</dd>
			</dl>
			<dl>
				<dt>OTC购买金额：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit"><fmt:formatNumber
								value="${bfscDataStatistics.otc_buy_amount}" pattern="#0.######" /></span>
				</dd>
			</dl>
			<dl>
				<dt>OTC出售金额：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit"><fmt:formatNumber
								value="${bfscDataStatistics.otc_sell_amount}" pattern="#0.######" /></span>
				</dd>
			</dl>
			<dl>
				<dt>OTC购买笔数：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit"><fmt:formatNumber
								value="${bfscDataStatistics.otc_buy_num}" pattern="#0.######" /></span>
				</dd>
			</dl>
			<dl>
				<dt>OTC出售笔数：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit"><fmt:formatNumber
								value="${bfscDataStatistics.otc_sell_num}" pattern="#0.######" /></span>
				</dd>
			</dl>
		</fieldset>
		<fieldset>
		<legend>划转统计</legend>
			<dl>
				<dt>USDT转入日数量：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit"><fmt:formatNumber
								value="${bfscDataStatistics.transfer_usdt_in_amount}" pattern="#0.######" /></span>
				</dd>
			</dl>
			<dl>
				<dt>USDT转入总数量：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit"><fmt:formatNumber
								value="${appusdt}" pattern="#0.######" /></span>
				</dd>
			</dl>
			<dl>
				<dt>BFSC转入日数量：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit"><fmt:formatNumber
								value="${bfscDataStatistics.transfer_bfsc_in_amount}" pattern="#0.######" /></span>
				</dd>
			</dl>
			<dl>
				<dt>BFSC转入总数量：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit"><fmt:formatNumber
								value="${appbfsc-49576.959}" pattern="#0.######" /></span>
				</dd>
			</dl>
			<dl>
				<dt>BFSC转出日数量：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit"><fmt:formatNumber
								value="${bfscDataStatistics.transfer_bfsc_out_amount}" pattern="#0.######" /></span>
				</dd>
			</dl>
			<dl>
				<dt>BFSC转出总数量：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit"><fmt:formatNumber
								value="${jysbfs-49521.65}" pattern="#0.######" /></span>
				</dd>
			</dl>
			<dl>
				<dt>BFSC存量数：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit"><fmt:formatNumber
								value="${bfscDataStatistics.bfsc_stock_amount}" pattern="#0.######" /></span>
				</dd>
			</dl>
		</fieldset>
		<fieldset>
		<legend>BFSC/USDT市场统计</legend>
			<dl>
				<dt>市场买入数量：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit"><fmt:formatNumber
								value="${bfscDataStatistics.market_buy_qty}" pattern="#0.######" /></span>
				</dd>
			</dl>
			<dl>
				<dt>市场卖出数量：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit"><fmt:formatNumber
								value="${bfscDataStatistics.market_sell_qty}" pattern="#0.######" /></span>
				</dd>
			</dl>
			<dl>
				<dt>市场买入金额：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit"><fmt:formatNumber
								value="${bfscDataStatistics.market_buy_amount}" pattern="#0.######" /></span>
				</dd>
			</dl>
			<dl>
				<dt>市场卖出金额：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit"><fmt:formatNumber
								value="${bfscDataStatistics.market_sell_amount}" pattern="#0.######" /></span>
				</dd>
			</dl>
			<dl>
				<dt>市场买入手续费：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit"><fmt:formatNumber
								value="${bfscDataStatistics.market_buy_fees}" pattern="#0.######" /></span>
				</dd>
			</dl>
			<dl>
				<dt>市场卖出手续费：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit"><fmt:formatNumber
								value="${bfscDataStatistics.market_sell_fees}" pattern="#0.######" /></span>
				</dd>
			</dl>
			<dl>
				<dt>市场总买入数量：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit"><fmt:formatNumber
								value="${bfscDataStatistics.market_buy_total_qty}" pattern="#0.######" /></span>
				</dd>
			</dl>
			<dl>
				<dt>市场总卖出数量：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit"><fmt:formatNumber
								value="${bfscDataStatistics.market_sell_total_qty}" pattern="#0.######" /></span>
				</dd>
			</dl>
			<dl>
				<dt>市场总买入金额：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit"><fmt:formatNumber
								value="${bfscDataStatistics.market_buy_total_amount}" pattern="#0.######" /></span>
				</dd>
			</dl>
			<dl>
				<dt>市场总卖出金额：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit"><fmt:formatNumber
								value="${bfscDataStatistics.market_sell_total_amount}" pattern="#0.######" /></span>
				</dd>
			</dl>
		</fieldset>
		<fieldset>
		<legend>手续费交割数据</legend>
			<dl>
				<dt>手续费BFSC数量：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit"><fmt:formatNumber
								value="${bfscDataStatistics.bfsc_fees_amount}" pattern="#0.######" /></span>
				</dd>
			</dl>
			<dl>
				<dt>BFSC均价：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit"><fmt:formatNumber
								value="${bfscDataStatistics.bfsc_avg_price}" pattern="#0.######" /></span>
				</dd>
			</dl>
			<dl>
				<dt>待交割USDT数量：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit"><fmt:formatNumber
								value="${bfscDataStatistics.usdt_fees_amount}" pattern="#0.######" /></span>
				</dd>
			</dl>
		</fieldset>
		<fieldset>
		<legend>注册统计</legend>
			<dl>
				<dt>总注册人数：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit"><fmt:formatNumber
								value="${bfscDataStatistics.register_total_num}" pattern="#0.######" /></span>
				</dd>
			</dl>
			<dl>
				<dt>新增注册人数：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit"><fmt:formatNumber
								value="${bfscDataStatistics.register_add_num}" pattern="#0.######" /></span>
				</dd>
			</dl>
		</fieldset>

		
	</div>

