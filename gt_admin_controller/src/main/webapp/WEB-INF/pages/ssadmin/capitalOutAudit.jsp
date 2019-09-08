<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">
	人民币提现订单<font color="red">${capitaloperation.fid}</font>确认审核
</h2>


<div class="pageContent">

	<form method="post" action="/buluo718admin/capitalOutAudit.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>订单ID：</dt>
				<dd>${capitaloperation.fid}
				<input type="hidden" name="uid" value="${capitaloperation.fid}" />
				<input type="hidden" name="type" value="1" /></dd>
			</dl>
			<dl>
				<dt>会员UID：</dt>
				<dd>${fuser.fid}</dd>
			</dl>
			<dl>
				<dt>登陆名：</dt>
				<dd>${fuser.floginName}</dd>
			</dl>
			<dl>
				<dt>会员真实姓名：</dt>
				<dd style="color:#ff3300;font-weight:bold;font-size:18px;">${fuser.frealName}</dd>
			</dl>
			<dl>
				<dt>状态：</dt>
				<dd>${capitaloperation.fstatus_s} <c:if test="${capitaloperation.fstatus!=2 }"><em style="color:#ff3300;font-weight:bold;font-size:16px;">非锁定处理状态，不能审核</em></c:if></dd>
			</dl>
			
			<dl>
				<dt>提现金额：</dt>
				<dd style="color:#ff3300;font-weight:bold;font-size:18px;">￥<fmt:formatNumber value="${capitaloperation.famount}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/> 元</dd>
			</dl>
			<dl>
				<dt>手续费：</dt>
				<dd>￥<fmt:formatNumber value="${capitaloperation.ffees}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/> 元</dd>
			</dl>
			
			<dl>
				<dt>收款银行：</dt>
				<dd style="color:#ff3300;font-weight:bold;font-size:18px;">${capitaloperation.fBank}</dd>
			</dl>
			<dl>
				<dt>开户行地址：</dt>
				<dd>${capitaloperation.faddress}</dd>
			</dl>
			<dl>
				<dt>收款帐号：</dt>
				<dd style="color:#ff3300;font-weight:bold;font-size:18px;">${capitaloperation.faccount_s.replaceAll("\\d{4}(?!$)", "$0 ")}</dd>
			</dl>
			<dl>
				<dt>创建时间：</dt>
				<dd>${capitaloperation.fcreateTime}</dd>
			</dl>
			<dl>
				<dt>最后修改时间：</dt>
				<dd>${capitaloperation.fLastUpdateTime}</dd>
			</dl>
			
			<dl>
				<dt>谷歌认证码：</dt>
				<dd>
					<input
						type="text" name="gcode" maxlength="6" class="required digits" />
				</dd>
			</dl>
			<c:if test="${capitaloperation.fstatus==2 }">
			<dt>审核码：</dt>
				<dd>
					<input type="password" name="confirmcode" size="50" value="" class="required" />
				</dd>
			</c:if>
		</div>
		<div class="formBar">
			<ul>
			<c:if test="${capitaloperation.fstatus==2 }">
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">确认审核</button>
						</div>
					</div>
				</li>
			</c:if>
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
