<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">查看提现状态</h2>


<div class="pageContent">

	<form method="post" class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>交易ID：</dt>
				<dd>
					<input type="text"
						value="${virtualCapitaloperation.ftradeUniqueNumber }"
						readonly="true" size="70" />
				</dd>
			</dl>
			<dl>
				<dt>提现地址：</dt>
				<dd>
					<input type="text"
						value="${virtualCapitaloperation.withdraw_virtual_address }"
						readonly="true" size="70" />
				</dd>
			</dl>
			<dl>
				<dt>确认数：</dt>
				<dd>
					<input type="text" value="${confirmations }" readonly="true"
						size="70" />
				</dd>
			</dl>
			
		</div>
		<div class="formBar">
			<ul>
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
