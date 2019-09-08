<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">修改积分等级:<font color="red">VIP${flevelSetting.level }</font></h2>


<div class="pageContent">

	<form method="post" action="/buluo718admin/updateLevelSetting.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>最小积分：</dt>
				<dd>
					<input type="hidden" name="fid" value="${flevelSetting.fid }" /> <input
						type="text" name="score" maxlength="50" class="required"
						size="70" value="${flevelSetting.score }" />
				</dd>
			</dl>
			<c:if test="${flevelSetting.level !=6 }">
			<dl>
				<dt>最大积分：</dt>
				<dd>
					<input
						type="text" name="score2" maxlength="50" class="required"
						size="70" value="${flevelSetting.score2 }" />
				</dd>
			</dl>
			</c:if>
			
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
