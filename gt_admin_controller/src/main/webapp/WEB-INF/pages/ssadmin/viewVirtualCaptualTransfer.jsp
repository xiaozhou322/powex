<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">审核虚拟币转账</h2>


<div class="pageContent">

	<form method="post" action="/buluo718admin/virtualCapitalTransferAudit.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
<!-- 			<dl>
				<dt>钱包密码：</dt>
				<dd>
					<input type="password" name="fpassword" maxlengpahx="50"  size="40"/>
				</dd>
			</dl> -->
			<dl>
				<dt>登陆名：</dt>
				<dd>
				    <input type="hidden" name="uid" value="${virtualCapitaloperation.fid}"/>
					<input type="text" name="fname" maxlength="30" size="40" 
					  value="${virtualCapitaloperation.fuser.floginName}" disabled="true"/>
				</dd>
			</dl>
			<dl>
				<dt>虚拟币类型：</dt>
				<dd>
					<select type="combox" name="virtualcointype.fid" disabled="true">
					<option value="${virtualCapitaloperation.fvirtualcointype.fid}">${virtualCapitaloperation.fvirtualcointype.fname}</option>
		            </select>
				</dd>
			</dl>
			<dl>
				<dt>转账数量：</dt>
				<dd>
					<input type="text" name=famount maxlength="30"
						class="required number" size="40"  value="<fmt:formatNumber value="${virtualCapitaloperation.famount}" pattern="##.######" maxIntegerDigits="20" maxFractionDigits="6"/>" disabled="true"/>
				</dd>
			</dl>
			<dl>
				<dt>转账手续费：</dt>
				<dd>
					<input type="text" name="ffees" maxlength="30"
						class="required number" size="40"  value="<fmt:formatNumber value="${virtualCapitaloperation.ffees}" pattern="##.######" maxIntegerDigits="20" maxFractionDigits="6"/>" disabled="true"/>
				</dd>
			</dl>
			<dl>
				<dt>收款UID：</dt>
				<dd>
					<input type="text" name="withdraw_virtual_address" maxlength="30"
						class="required number" size="40" value="${virtualCapitaloperation.withdraw_virtual_address}" disabled="true"/>
				</dd>
			</dl>
			<dl>
				<dt>收款账号：</dt>
				<dd>
					<input type="text" name="withdraw_virtual_address" maxlength="30"
						class="required number" size="40" value="${virtualCapitaloperation.fischarge}" disabled="true"/>
				</dd>
			</dl>
			
			<dl>
				<dt>谷歌认证码：</dt>
				<dd>
					<input
						type="text" name="gcode" maxlength="6" class="required digits" />
				</dd>
			</dl>
			<dt>审核码：</dt>
					<dd>
						<input type="password" name="confirmcode" size="50" value="" class="required" />
					</dd>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">审核</button>
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
