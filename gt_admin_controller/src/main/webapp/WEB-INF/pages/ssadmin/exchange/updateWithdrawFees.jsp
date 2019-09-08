<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<h2 class="contentTitle">修改USDT提现手续费信息</h2>


<div class="pageContent">

	<form method="post" action="/buluo718admin/usdtUpdateWithdrawFees.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>会员等级：</dt>
				<dd>
					<input type="hidden" name="fid" value="${withdrawfees.fid}"/> 
					<input type="text" name="flevel" size="70" value="${withdrawfees.flevel}" readonly="true"/>
				</dd>
			</dl>
			<dl>
				<dt>USDT提现手续费：</dt>
				<dd>
					<input type="text" name="ffee" maxlength="100" size="40" class="required number" value="${withdrawfees.ffee}"/>
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
