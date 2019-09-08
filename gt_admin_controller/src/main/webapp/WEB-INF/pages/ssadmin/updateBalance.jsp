<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">修改定存信息</h2>


<div class="pageContent">

	<form method="post" action="/buluo718admin/updateBalance.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>简介：</dt>
				<dd>
				    <input type="hidden" name="fid" value="${fbalance.fid }"/>
					<textarea class="required" name="ftitle" rows="4" cols="70">${fbalance.ftitle}</textarea>
				</dd>
			</dl>
			<dl>
				<dt>最小存入数量：</dt>
				<dd>
					<input type="text" name="fminqty" class="required number" size="70"
						value="<fmt:formatNumber
										value="${fbalance.fminqty }" pattern="##.##"
										maxIntegerDigits="10" maxFractionDigits="6" />" />
				</dd>
			</dl>
			<dl>
				<dt>最大存入数量：</dt>
				<dd>
					<input type="text" name="fmaxqty" class="required number" size="70"
						value="<fmt:formatNumber
										value="${fbalance.fmaxqty }" pattern="##.##"
										maxIntegerDigits="10" maxFractionDigits="6" />" />
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
