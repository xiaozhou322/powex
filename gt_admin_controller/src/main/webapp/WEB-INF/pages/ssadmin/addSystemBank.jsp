<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">添加银行帐户信息</h2>


<div class="pageContent">
	
	<form method="post" action="/buluo718admin/saveSystemBank.html" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>银行名称：</dt>
				<dd>
					<input type="text" name="systemBank.fbankName" maxlength="40" class="required" size="70"/>
				</dd>
			</dl>
			<dl>
				<dt>开户姓名：</dt>
				<dd>
					<input type="text" name="systemBank.fownerName" maxlength="40" class="required" size="70"/>
				</dd>
			</dl>
			<dl>
				<dt>开户地址：</dt>
				<dd>
					<input type="text" name="systemBank.fbankAddress" maxlength="70" class="required" size="70"/>
				</dd>
			</dl>
			<dl>
				<dt>银行卡号：</dt>
				<dd>
					<input type="text" name="systemBank.fbankNumber" maxlength="40" class="required" size="70"/>
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
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
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
