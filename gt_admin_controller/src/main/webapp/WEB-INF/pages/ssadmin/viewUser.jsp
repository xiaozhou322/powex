<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">
	查看会员:<font color="red">${fuser.fnickName}</font> 积分及等级
</h2>

<div class="pageContent">

	<form method="post" action="/buluo718admin/updateUserLevel.html" class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>原等级：</dt>
				<dd>
					<input type="text" name="flevel" readonly="true" size="50"
						value="${fuser.fscore.flevel}" /> <input type="hidden" name="fid"
						value="${fuser.fid}" />
				</dd>
			</dl>
			<dl>
				<dt>新等级：</dt>
				<dd>
					<select type="combox" name="newLevel">
						<c:forEach items="${allFees}" var="fee">
							<c:if test="${fee.flevel == fuser.fscore.flevel}">
								<option
									value="<fmt:formatNumber
													value="${fee.flevel}" pattern="#0.######" />"
									selected="true">
									<fmt:formatNumber value="${fee.flevel}" pattern="#0.######" />
								</option>
							</c:if>
							<c:if test="${fee.flevel != fuser.fscore.flevel}">
								<option
									value="<fmt:formatNumber
													value="${fee.flevel}" pattern="#0.######" />">
									<fmt:formatNumber value="${fee.flevel}" pattern="#0.######" />
								</option>
							</c:if>
						</c:forEach>
					</select>
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
