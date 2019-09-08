<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<h2 class="contentTitle">
	项目方<font color="red"></font>公告信息审核
</h2>


<div class="pageContent">

	<form method="post" action="/buluo718admin/padAudit.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<input type="hidden" name="padId" value="${pad.id}" />
		<div class="pageFormContent nowrap" layoutH="97">
			<c:if test="${type == 1}">
				<dl>
					<dt style="width: 80px;">审核结果：</dt>
					<dd>
						<select type="combox" name="auditStatus" class="required">
							<option value="102">通过</option>
							<option value="103">不通过</option>
						</select>
					</dd>
				</dl>
			</c:if>
			<c:if test="${type == 2}">
				<dl>
					<dt style="width: 80px;">审核结果：</dt>
					<dd>
						<c:choose>
							<c:when test="${pad.auditStatus == 101}">待审核</c:when>
							<c:when test="${pad.auditStatus == 102}">审核通过</c:when>
							<c:when test="${pad.auditStatus == 103}">审核不通过</c:when>
						</c:choose>
					</dd>
				</dl>
			</c:if>
			<dl>
				<dt style="width: 80px;">项目方ID：</dt>
				<dd><input type="text" name="projectId" size="70" readonly="readonly"
						value="${pad.projectId.fid}" />
				</dd>
			</dl>
			<dl>
				<dt style="width: 80px;">公告标题：</dt>
				<dd><input
						type="text" name="adTittle" readonly="true" size="70"
						value="${pad.adTittle}" />
				</dd>
			</dl>
			<dl>
				<dt style="width: 80px;">公告内容：</dt>
				<dd>
					<%-- <input type="text" name="adContent" size="70" readonly="true"
						value="${pad.adContent}" /> --%>
					<textarea class="editor" name="adContent" rows="20" cols="105"
						upImgUrl="buluo718admin/upload.html"
						upImgExt="jpg,jpeg,gif,png"  readonly="true">
						${pad.adContent}
					</textarea>
				</dd>
			</dl>
			<c:if test="${type == 1}">
				<dl>
					<dt style="width: 80px;">谷歌认证码：</dt>
					<dd>
						<input
							type="text" name="gcode" maxlength="6" class="required digits" />
					</dd>
				</dl>
			</c:if>
		</div>
		<div class="formBar">
			<ul>
				<c:if test="${type == 1}">
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
				</c:if>
				<c:if test="${type == 2}">
					<li><div class="button">
							<div class="buttonContent">
								<button type="button" class="close">关闭</button>
							</div>
						</div>
					</li>
				</c:if>
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
