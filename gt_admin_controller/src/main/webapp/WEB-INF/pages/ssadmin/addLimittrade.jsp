<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">添加限价信息</h2>


<div class="pageContent">

	<form method="post" action="/buluo718admin/saveLimittrade.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>交易类型：</dt>
				<dd>
					<select type="combox" name="vid">
						<c:forEach items="${trademappings}" var="v">
							<option value="${v.fid}">${v.fvirtualcointypeByFvirtualcointype1.fname}-${v.fvirtualcointypeByFvirtualcointype2.fname }</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>最低价格：</dt>
				<dd>
					<input type="text" name="fdownprice" maxlength="20"
						class="required number" />
				</dd>
			</dl>
			<dl>
				<dt>最高价格：</dt>
				<dd>
					<input type="text" name="fupprice" maxlength="20"
						class="required number" />
				</dd>
			</dl>
			<dl>
				<dt>涨跌比例：</dt>
				<dd>
					<input type="text" name="fpercent" maxlength="20"
						class="required number" />
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
					</div>
				</li>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div>
				</li>
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
