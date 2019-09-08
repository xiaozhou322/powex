<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<h2 class="contentTitle">添加USDT手工充值信息</h2>


<div class="pageContent">

	<form method="post" action="/buluo718admin/usdtSaveOperationLog.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>会员：</dt>
				<dd>
					<input type="hidden" name="userLookup.id" value="${userLookup.id}"/>
				    <input type="text" class="required" name="userLookup.floginName" value="" suggestFields="id,floginName"
				     suggestUrl="buluo718admin/userLookup.html" lookupGroup="userLookup" readonly="readonly"/>
				    <a class="btnLook" href="/buluo718admin/userLookup.html" lookupGroup="userLookup">查找带回</a>	
				</dd>
			</dl>
			<dl>
				<dt>金额：</dt>
				<dd>
					<input type="hidden" name="ftype" value="4">
					<input type="text" name="famount" maxlength="50"  size="40" class="number required"/>
				</dd>
			</dl>
            <dl>
				<dt>备注：</dt>
				<dd>
					<input type="text" name="fdescription" maxlength="50" size="70" />
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
