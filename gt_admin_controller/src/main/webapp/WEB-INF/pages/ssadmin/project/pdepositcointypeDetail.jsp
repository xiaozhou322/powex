<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<h2 class="contentTitle">
	项目方<font color="red"></font>域名信息审核
</h2>


<div class="pageContent">

	<form method="post" action="/buluo718admin/pdepositcointypeUpdate.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<input type="hidden" name="pid" value="${pdepositcointype.id}" />
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>保证金币种：</dt>
				<dd>
					<select type="combox" name="cointypeId">
						<c:forEach items="${allType}" var="type">
							<c:if test="${type.fid == pdepositcointype.cointypeId.fid}">
								<option value="${type.fid}" selected="true">${type.fShortName}</option>
							</c:if>
							<c:if test="${type.fid != pdepositcointype.cointypeId.fid}">
								<option value="${type.fid}">${type.fShortName}</option>
							</c:if>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>保证金额度：</dt>
				<dd>
					<input type="text" name="depositLimit" size="70" value="${pdepositcointype.depositLimit}" />
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
