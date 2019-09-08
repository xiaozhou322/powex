<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">修改定存类型信息</h2>


<div class="pageContent">

	<form method="post" action="/buluo718admin/updateBalancetype.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>类型名称：</dt>
				<dd>
				    <input type="hidden" name="fid" value="${fbalancetype.fid }"/>
					<input type="text" name="fname" class="required" size="70"
						value="${fbalancetype.fname }" />
				</dd>
			</dl>
			<dl>
				<dt>类型名称-英文：</dt>
				<dd>
					<input type="text" name="fname_en" class="required" size="70"
						value="${fbalancetype.fname_en }" />
				</dd>
			</dl>
			<dl>
				<dt>定存周期：</dt>
				<dd>
					<input type="text" name="fday" class="required digits" size="70"
						value="${fbalancetype.fday }" /><span>天</span>
				</dd>
			</dl>
			<dl>
				<dt>定存利率：</dt>
				<dd>
					<input type="text" name="frate" class="required number" size="70"
						value="<fmt:formatNumber
										value="${fbalancetype.frate }" pattern="##.##"
										maxIntegerDigits="10" maxFractionDigits="6" />" />
				</dd>
			</dl>
			<dl>
				<dt>虚拟币名称：</dt>
				<dd>
					 <select
						type="combox" name="vid" class="required">
						<c:forEach items="${allType}" var="type">
							<c:if test="${type.fid == fbalancetype.fvirtualcointype.fid}">
								<option value="${type.fid}" selected="true">${type.fname}</option>
							</c:if>
							<c:if test="${type.fid != fbalancetype.fvirtualcointype.fid}">
								<option value="${type.fid}">${type.fname}</option>
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
