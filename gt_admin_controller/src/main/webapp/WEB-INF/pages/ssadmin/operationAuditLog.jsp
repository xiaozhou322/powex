<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">
	人民币手工充值订单<font color="red">${capitaloperation.fid}</font>确认审核
</h2>


<div class="pageContent">

	<form method="post" action="/buluo718admin/usdtAuditOperationLog.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>订单ID：</dt>
				<dd>${operationlog.fid}
				<input type="hidden" name="uid" value="${operationlog.fid}" />
				<input type="hidden" name="type" value="${operationlog.ftype}" /></dd>
			</dl>
			<dl>
				<dt>状态：</dt>
				<dd style="color:#ff3300;font-weight:bold;font-size:18px;">${operationlog.fstatus_s} <c:if test="${operationlog.fstatus!=1 }">，不能重复审核</c:if></dd>
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
				<dt>金额：</dt>
				<dd style="color:#ff3300;font-weight:bold;font-size:18px;">￥<fmt:formatNumber value="${operationlog.famount}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/> 元</dd>
			</dl>
			<dl>
				<dt>创建时间：</dt>
				<dd>${operationlog.fcreateTime} </dd>
			</dl>
			<dl>
				<dt>更新时间：</dt>
				<dd>${operationlog.flastUpdateTime} </dd>
			</dl>
			<c:if test="${operationlog.fstatus==1 }">
			<dt>审核码：</dt>
				<dd>
					<input type="password" name="confirmcode" size="50" value="" class="required" />
			</dd>
			</c:if>
		</div>
		<div class="formBar">
			<ul>
				<c:if test="${operationlog.fstatus==1 }">
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
