<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">修改手续费兑换信息</h2>


<div class="pageContent">

	<form id="saveForm" name="saveForm" method="post" action="/buluo718admin/editFeesConvert.html"
		class="pageForm required-validate"
		onsubmit="return iframeCallback(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="97">
		<input type="hidden" name="pid" value="${feesConvert.id}"/>
			<dl>
				<dt>项目方ID：</dt>
				<dd>
					<!-- <input type="text" name="projectId" class="required" readonly="true" size="50" value="366665" /> -->
					<input type="text" name="projectId" class="required" readonly="true" size="50" value="${feesConvert.projectId.fid}" />
				</dd>
			</dl>
			<dl>
				<dt>USDT数量：</dt>
				<dd>
					<input type="text" name="usdtAmount" class="number required" size="50" value="${feesConvert.usdtAmount}"/>
				</dd>
			</dl>
			<dl>
				<dt>BFSC价格：</dt>
				<dd>
					<input type="text" name="bfscPrice" class="number required" size="50" value="${feesConvert.bfscPrice}" />
				</dd>
			</dl>
			<dl>
				<dt>BFSC数量：</dt>
				<dd>
					<input type="text" name="bfscAmount" class="number required" size="50" value="${feesConvert.bfscAmount}" />
				</dd>
			</dl>
			<dl>
				<dt>交割日期：</dt>
				<dd>
					<input type="text" name="feeday" class="date required"
						readonly="true" value="${feesConvert.createTime}" />
				</dd>
			</dl>
			<dl>
				<dt>谷歌认证码：</dt>
				<dd>
					<input type="text" id="gcode" name="gcode" maxlength="6" class="required digits" />
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit" onclick="fillgcode();">保存</button>
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
function fillgcode(){
	var gcode = $("#gcode").val();
	$("#saveForm").attr("action","/buluo718admin/editFeesConvert.html?gcode="+gcode);
}

</script>
