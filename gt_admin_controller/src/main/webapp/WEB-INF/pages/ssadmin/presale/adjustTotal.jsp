<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<h2 class="contentTitle">
	交易量加权调整
</h2>


<div class="pageContent">

	<form method="post" action="/buluo718admin/adjustTotal.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>交易统计日期：</dt>
				<dd>${fassetgt.flastupdatetime}</dd>
			</dl>
			<dl>
				<dt>总交易量：</dt>
				<dd style="color:#ff3300;font-weight:bold;font-size:18px;">${fassetgt.ftotalamount}</dd>
			</dl>
			<dl>
				<dt>总交易手续费：</dt>
				<dd>${fassetgt.ftotalfee}</dd>
			</dl>
			<dt>调整后交易量：</dt>
				<dd>
					<input type="text" name="newamount" size="50" value="" class="required" />
				</dd>
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
							<button type="submit">确认调整</button>
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
