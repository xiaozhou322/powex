<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">添加权限项，上级菜单：<font color="red">${security.fname}</font></h2>


<div class="pageContent">
	
	<form method="post" action="/buluo718admin/saveSecurity.html" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>权限名称：</dt>
				<dd>
				    <input type="hidden" name="fparentid" value="${security.fid}"/>
				    <input type="hidden" name="fpriority" value="${security.fpriority}"/>
					<input type="text" name="fname" maxlength="20" class="required" size="70"/>
				</dd>
			</dl>
			<dl>
				<dt>访问地址：</dt>
				<dd>
					<input type="text" name="furl" maxlength="120" size="70"/>
				</dd>
			</dl>
			<dl>
				<dt>描述：</dt>
				<dd>
					<input type="text" name="fdescription" maxlength="120" size="70"/>
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
