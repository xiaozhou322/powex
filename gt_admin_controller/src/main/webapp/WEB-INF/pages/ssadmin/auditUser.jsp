<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">
	会员<font color="red">${fuser.fnickName}</font>证件信息审核
</h2>


<div class="pageContent">

	<form method="post" action="/buluo718admin/auditUser.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>审核结果：</dt>
				<dd>
					<select type="combox" name="status" class="required">
						<option value="1">通过</option>
						<option value="2">不通过</option>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>会员ID：</dt>
				<dd><input type="text" name="uid" size="70" readonly="readonly"
						value="${fuser.fid}" />
				</dd>
			</dl>
			<dl>
				<dt>会员真实姓名：</dt>
				<dd><input
						type="text" name="frealName" readonly="true" size="70"
						value="${fuser.frealName}" />
				</dd>
			</dl>
			<dl>
				<dt>证件类型：</dt>
				<dd>
					<select type="combox" name="fidentityType" readonly="true" disabled>
						<c:forEach items="${identityTypeMap}" var="identityType">
							<c:if test="${identityType.key == fuser.fidentityType}">
								<option value="${identityType.key}" selected="true">${identityType.value}</option>
							</c:if>
							<c:if test="${identityType.key != fuser.fidentityType}">
								<option value="${identityType.key}">${identityType.value}</option>
							</c:if>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>证件号码：</dt>
				<dd>
					<input type="text" name="fidentityNo" size="70" readonly="true"
						value="${fuser.fidentityNo}" />
				</dd>
			</dl>
			<dl>
				<dt>扫描件正面：</dt>
				<dd>
					<img src="${fuser.fIdentityPath }" width="500" />
				</dd>
			</dl>
			<dl>
				<dt>扫描件反面：</dt>
				<dd>
					<img src="${fuser.fIdentityPath2 }" width="500" />
				</dd>
			</dl>
			<dl>
				<dt>手持身份证：</dt>
				<dd>
					<img src="${fuser.fIdentityPath3 }" width="500" />
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
