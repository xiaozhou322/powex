<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">
	修改管理员:<font color="red">${fadmin.fname}</font> 信息
</h2>


<div class="pageContent">

	<form method="post" action="/buluo718admin/updateAdminRole.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>角色：</dt>
				<dd>
					<input type="hidden" name="fadmin.fid" value="${fadmin.fid}" /> <select
						type="combox" name="frole.fid">
						<c:forEach items="${roleMap}" var="role">
							<c:if test="${role.key == fadmin.frole.fid}">
								<option value="${role.key}" selected="true">${role.value}</option>
							</c:if>
							<c:if test="${role.key != fadmin.frole.fid}">
								<option value="${role.key}">${role.value}</option>
							</c:if>
						</c:forEach>
					</select>
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
							<button type="submit">修改</button>
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
