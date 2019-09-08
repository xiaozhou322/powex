<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<h2 class="contentTitle">
	项目方<font color="red"></font>域名设置分成比例
</h2>


<div class="pageContent">

	<form method="post" action="/buluo718admin/pdomainSetProportions.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<input type="hidden" name="pdomainId" value="${pdomain.id}" />
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>项目方ID：</dt>
				<dd><input type="text" name="projectId" size="70" readonly="readonly"
						value="${pdomain.projectId.fid}" />
				</dd>
			</dl>
			<dl>
				<dt>专属域名：</dt>
				<dd><input
						type="text" name="exclusiveDomain" readonly="true" size="70"
						value="${pdomain.exclusiveDomain}" />
				</dd>
			</dl>
			<dl>
				<dt>完整域名：</dt>
				<dd>
					<input type="text" name="completeDomain" size="70" readonly="true"
						value="${pdomain.completeDomain}" />
				</dd>
			</dl>
			<dl>
				<dt>分成比例：</dt>
				<dd>
					<input type="text" name="proportions" size="70" value="${pdomain.proportions}" />
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
