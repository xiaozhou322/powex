<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<h2 class="contentTitle">
	项目方<font color="red"></font>域名信息审核
</h2>


<div class="pageContent">

	<form method="post" action="/buluo718admin/pprojectUpdate.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<input type="hidden" name="pprojectId" value="${pproject.id}" />
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>项目方ID：</dt>
				<dd><input type="text" name="projectId" size="70" readonly="readonly"
						value="${pproject.projectId.fid}" />
				</dd>
			</dl>
			<dl>
				<dt>名称：</dt>
				<dd><input
						type="text" name="name" readonly="true" size="70"
						value="${pproject.name}" />
				</dd>
			</dl>
			<dl>
				<dt>项目方类型：</dt>
				<dd>
					<select type="combox" name="type" class="required">
						<option value="1">项目方</option>
						<option value="2">社群</option>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>logo图片：</dt>
				<dd>
					<img src="${pproject.logoUrl}" width="50" />
				</dd>
			</dl>
			<dl>
				<dt>项目方网站：</dt>
				<dd>
					<input type="text" name="website" size="70" readonly="true"
						value="${pproject.website}" />
				</dd>
			</dl>
			<dl>
				<dt>项目亮点：</dt>
				<dd>
					<input type="text" name="advantage" size="70" readonly="true"
						value="${pproject.advantage}" />
				</dd>
			</dl>
			<dl>
				<dt>项目介绍：</dt>
				<dd>
					<input type="text" name="introduce" size="70" readonly="true"
						value="${pproject.introduce}" />
				</dd>
			</dl>
			<dl>
				<dt>备注：</dt>
				<dd>
					<input type="text" name="remark" size="70" readonly="true"
						value="${pproject.remark}" />
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
