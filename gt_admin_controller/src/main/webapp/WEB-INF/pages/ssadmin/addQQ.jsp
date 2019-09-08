<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">添加QQ群信息</h2>


<div class="pageContent">
	
	<form method="post" action="/buluo718admin/saveQQ.html" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>名称：</dt>
				<dd>
					<input type="text" name="link.fname" maxlength="20" class="required" size="70"/>
				</dd>
			</dl>
			<dl>
				<dt>类型：</dt>
				<dd>
					<select type="combox" name="link.ftype">
					<c:forEach items="${linkTypeMap}" var="link">
						<option value="${link.key}">${link.value}</option>
					</c:forEach>
		            </select>
				</dd>
			</dl>
			<dl>
				<dt>链接地址：</dt>
				<dd>
					<input type="text" name="link.furl" maxlength="100" class="digits required" size="70"/>
				</dd>
			</dl>
			<dl>
				<dt>描述：</dt>
				<dd>
					<input type="text" name="link.fdescription" maxlength="120" size="70"/>
				</dd>
			</dl>

			<dl>
				<dt>顺序：</dt>
				<dd>
					<input type="text" name="link.forder" maxlength="20" class="required digits"/>
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
