<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">修改管理员：${login_admin.fname }密码</h2>


<div class="pageContent">
	
	<form method="post" action="/buluo718admin/updateAdminPassword.html" class="pageForm required-validate" 
	 onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
		    <dl>
				<dt>原密码：</dt>
				<dd>
				    <input type="hidden" name="fadmin.fid" value="${login_admin.fid}"/>
					<input type="password" name="oldPassword"  class="required" size="60"/>
				</dd>
			</dl>
			<dl>
				<dt>新密码：</dt>
				<dd>
					<input id="w_validation_pwd" type="password" name="fadmin.fpassword" maxlength="20" 
					class="required alphanumeric" minlength="6" maxlength="20" alt="字母、数字、下划线 6-20位" size="60"/>
				</dd>
			</dl>
			<dl>
				<dt>确认密码：</dt>
				<dd>
					<input type="password" maxlength="20" class="required"  equalto="#w_validation_pwd" size="60"/>
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
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">修改</button></div></div></li>
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
