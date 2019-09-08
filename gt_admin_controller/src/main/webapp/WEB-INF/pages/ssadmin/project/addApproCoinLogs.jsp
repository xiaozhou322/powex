<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<h2 class="contentTitle">拨付虚拟币</h2>


<div class="pageContent">

	<form method="post" action="/buluo718admin/saveApproCoinLogs.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
		<input type="hidden" name="productId" value="${pproduct.id}" />
			<dl>
				<dt>产品名称：</dt>
				<dd><input type="text" name="name" readonly="true" size="50" value="${pproduct.name}" />
				</dd>
			</dl>
			<dl>
				<dt>产品简称：</dt>
				<dd>
					<input type="text" name="shortName" size="50" readonly="true"
						value="${pproduct.shortName}" />
				</dd>
			</dl>
			<dl>
				<dt>会员：</dt>
				<dd>
					<input type="hidden" name="userLookup.id" value="${userLookup.id}" />
					<input type="text" class="required" name="userLookup.floginName"
						value="" suggestFields="id,floginName"
						suggestUrl="buluo718admin/userLookup.html" lookupGroup="userLookup"
						readonly="readonly" /> <a class="btnLook"
						href="/buluo718admin/userLookup.html" lookupGroup="userLookup">查找带回</a>
				</dd>
			</dl>
			<dl>
				<dt>数量：</dt>
				<dd>
					<input type="text" name="amount" maxlength="50" size="40"
						class="number required" />
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
							<button type="submit">确定</button>
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
