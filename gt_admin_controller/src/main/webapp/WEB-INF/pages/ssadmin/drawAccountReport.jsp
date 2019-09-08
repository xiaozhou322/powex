<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
</head>
<body>
	<div class="pageFormContent" layoutH="20">

		<fieldset>
			<legend>划转统计数据（截止时间：${now }）</legend>
			<dl>
				<dt>钱包转入交易所BFSC：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit"><fmt:formatNumber
								value="${appbfsc}" pattern="#0.######" />（含测试：49576.959）</span>
				</dd>
			</dl>
			<dl>
				<dt>钱包转入交易所BFSC：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit"><fmt:formatNumber
								value="${appbfsc-49576.959}" pattern="#0.######" /></span>
				</dd>
			</dl>
			<dl>
				<dt>交易所转入钱包BFSC：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit"><fmt:formatNumber
								value="${jysbfs}" pattern="#0.######" />（含测试：49521.65）</span>
				</dd>
			</dl>
			<dl>
				<dt>交易所转入钱包BFSC：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit"><fmt:formatNumber
								value="${jysbfs-49521.65}" pattern="#0.######" /></span>
				</dd>
			</dl>
			<dl>
				<dt>钱包转入交易所USDT：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit"><fmt:formatNumber
								value="${appusdt}" pattern="#0.######" />（含测试：0）</span>
				</dd>
			</dl>
			<dl>
				<dt>钱包转入交易所USDT：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit"><fmt:formatNumber
								value="${appusdt-0}" pattern="#0.######" /></span>
				</dd>
			</dl>
		</fieldset>

		
	</div>
</body>
</html>

