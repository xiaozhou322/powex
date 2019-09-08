<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">修改法币类型匹配信息</h2>


<div class="pageContent">

		<form method="post" action="/buluo718admin/updateTradeMapping.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dt>法币类型：</dt>
				<dd>
					<input type="hidden" name="uid" value="${ftradeMapping.fid}" />
					<span>${ftradeMapping.fvirtualcointypeByFvirtualcointype1.fname }</span>
				</dd>
			</dl>
			<dl>
				<dt>交易币类型：</dt>
				<dd>
					<span>${ftradeMapping.fvirtualcointypeByFvirtualcointype2.fname }</span>
				</dd>
			</dl>
			<dl>
				<dt>单价小数位：</dt>
				<dd>
					<input type="text" name="fcount1"
						class="required number" size="30" value="<fmt:formatNumber value="${ftradeMapping.fcount1 }" pattern="##.######" maxIntegerDigits="20" maxFractionDigits="8"/>"/>
				</dd>
			</dl>
			<dl>
				<dt>数量小数位：</dt>
				<dd>
					<input type="text" name="fcount2"
						class="required number" size="30"  value="<fmt:formatNumber value="${ftradeMapping.fcount2 }" pattern="##.######" maxIntegerDigits="20" maxFractionDigits="8"/>"/>
				</dd>
			</dl>
			<dl>
				<dt>交易时间：</dt>
				<dd>
					<input type="text" name="ftradeTime"
						class="required" value="${ftradeMapping.ftradeTime }"/>
				</dd>
			</dl>
			<dl>
				<dt>开盘价：</dt>
				<dd>
					<input type="text" name="fprice"
						class="required number" size="30"  value="<fmt:formatNumber value="${ftradeMapping.fprice }" pattern="##.######" maxIntegerDigits="20" maxFractionDigits="8"/>"/>
				</dd>
			</dl>
            <dl>
				<dt>最小挂单数量：</dt>
				<dd>
					<input type="text" name="fminBuyCount"
						class="required number" size="30"  value="<fmt:formatNumber value="${ftradeMapping.fminBuyCount }" pattern="##.######" maxIntegerDigits="20" maxFractionDigits="8"/>"/>
				</dd>
			</dl>
			<dl>
				<dt>最小挂单单价：</dt>
				<dd>
					<input type="text" name="fminBuyPrice"
						class="required number" size="30"  value="<fmt:formatNumber value="${ftradeMapping.fminBuyPrice }" pattern="##.######" maxIntegerDigits="20" maxFractionDigits="8"/>"/>
				</dd>
			</dl>
			<dl>
				<dt>最小挂单金额：</dt>
				<dd>
					<input type="text" name="fminBuyAmount"
						class="required number" size="30"  value="<fmt:formatNumber value="${ftradeMapping.fminBuyAmount }" pattern="##.######" maxIntegerDigits="20" maxFractionDigits="8"/>"/>
				</dd>
			</dl>
			<dl>
				<dt>最大挂单数量：</dt>
				<dd>
					<input type="text" name="fmaxBuyCount"
						class="required number" size="30"  value="<fmt:formatNumber value="${ftradeMapping.fmaxBuyCount }" pattern="##.######" maxIntegerDigits="20" maxFractionDigits="8"/>"/>
				</dd>
			</dl>
			<dl>
				<dt>最大挂单单价：</dt>
				<dd>
					<input type="text" name="fmaxBuyPrice"
						class="required number" size="30"  value="<fmt:formatNumber value="${ftradeMapping.fmaxBuyPrice }" pattern="##.######" maxIntegerDigits="20" maxFractionDigits="8"/>"/>
				</dd>
			</dl>
			<dl>
				<dt>最大挂单金额：</dt>
				<dd>
					<input type="text" name="fmaxBuyAmount" value="<fmt:formatNumber value="${ftradeMapping.fmaxBuyAmount }" pattern="##.######" maxIntegerDigits="20" maxFractionDigits="8"/>"
						class="required number" size="30" />
				</dd>
			</dl>
			<dl>
				<dt>市场排序：</dt>
				<dd>
					<input type="text" name="forder"
						class="required number" size="30" value="${ftradeMapping.forder }" />
				</dd>
			</dl>
			
			<dl>
				<dt>谷歌认证码：</dt>
				<dd>
					<input
						type="text" name="gcode" maxlength="6" class="required digits" />
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">保存</button>
						</div>
					</div></li>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div></li>
			</ul>
		</div>
	</form>

</div>


<script type="text/javascript">
function customvalidXxx(element){
	if ($(element).val() == "xxx") return false;
	return true;
}
</script>
