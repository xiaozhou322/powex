<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">
	更新会员<font color="red">${fuser.floginName}</font>等级
</h2>


<div class="pageContent">

	<form method="post" action="/buluo718admin/updateUserGrade.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>等级：</dt>
				<dd>
					<input type="hidden" name="uid" value="${fuser.fid}" />
					<select type="combox" name="fuserGrade">
						<c:forEach items="${typeMap}" var="type">
							<c:if test="${type.key == fuser.fscore.flevel}">
								<option value="${type.key}" selected="true">${type.value}</option>
							</c:if>
							<c:if test="${type.key != fuser.fscore.flevel}">
								<option value="${type.key}">${type.value}</option>
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
