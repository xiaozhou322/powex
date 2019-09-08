<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">增加帮助分类</h2>


<div class="pageContent">

	<form method="post" action="/buluo718admin/saveAbout.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>标题（通用）：</dt>
				<dd>
					<input
						type="text" name="ftitle" maxlength="50" class="required"
						size="70" />
				</dd>
			</dl>
			<dl>
				<dt>标题（中文）：</dt>
				<dd>
					<input
						type="text" name="ftitle_cn" maxlength="50" class="required"
						size="70" />
				</dd>
			</dl>
			<dl>
				<dt>类型（通用）：</dt>
				<dd>
					<select type="combox" name="ftype">
						<c:forEach items="${type}" var="t">
							<option value="${t.key}">${t.value}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			
			<dl>
				<dt>类型（中文）：</dt>
				<dd>
					<select type="combox" name="ftype_cn">
						<c:forEach items="${type_cn}" var="t">
							<option value="${t.key}">${t.value}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			
			<dl>
			
			
				<dt>内容（通用）：</dt>
				<dd>
					<textarea class="editor {width:'780px'}" name="fcontent" rows="20" cols="100"  upImgUrl="buluo718admin/upload.html"
						upImgExt="jpg,jpeg,gif,png">
				</textarea>
				</dd>
			</dl>
			
			<dl>
			
			
				<dt>内容（中文）：</dt>
				<dd>
					<textarea class="editor {width:'780px'}" name="fcontent_cn" rows="20" cols="100"  upImgUrl="buluo718admin/upload.html"
						upImgExt="jpg,jpeg,gif,png">
				</textarea>
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
