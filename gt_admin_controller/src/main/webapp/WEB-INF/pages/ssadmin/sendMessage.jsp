<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">发送消息</h2>


<div class="pageContent">

	<form method="post" action="/buluo718admin/sendMessage.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
		    <dl>
				<dt>接收人：</dt>
				<dd>
					<input type="hidden" name="userLookup.id" value="${userLookup.id}"/>
				    <input type="text" class="" name="userLookup.floginName" value="" suggestFields="id,floginName"
				     suggestUrl="buluo718admin/userLookup.html" lookupGroup="userLookup" readonly="readonly"/>
				    <a class="btnLook" href="/buluo718admin/userLookup.html" lookupGroup="userLookup">查找带回</a>
				    <span>全部可不选</span>
				</dd>
			</dl>
			<dl>
				<dt>标题_中文：</dt>
				<dd>
					<input type="text" name="ftitle" maxlength="50"
						class="required" size="70" />
				</dd>
			</dl>
			<dl>
				<dt>内容_中文：</dt>
				<dd>
					<textarea name="fcontent" rows="5" cols="105" class="required"/>
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
