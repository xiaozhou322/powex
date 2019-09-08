<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">添加手续费兑换信息</h2>


<div class="pageContent">

	<form id="saveForm" name="saveForm" method="post" action="/buluo718admin/saveFeesConvert.html"
		class="pageForm required-validate"
		onsubmit="return iframeCallback(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>项目方ID：</dt>
				<dd>
					<input type="text" name="projectId" class="required" readonly="true" size="50" value="366665" />
				</dd>
			</dl>
			<dl>
				<dt>USDT数量：</dt>
				<dd>
					<input type="text" name="usdtAmount" class="number required" size="50" />
				</dd>
			</dl>
			<dl>
				<dt>BFSC价格：</dt>
				<dd>
					<input type="text" name="bfscPrice" class="number required" size="50" />
				</dd>
			</dl>
			<dl>
				<dt>BFSC数量：</dt>
				<dd>
					<input type="text" name="bfscAmount" class="number required" size="50" />
				</dd>
			</dl>
			<dl>
				<dt>交割日期：</dt>
				<dd>
					<input type="text" name="feeday" class="date required"
						readonly="true" value="" />
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
	$("#saveForm").attr("action","/buluo718admin/saveFeesConvert.html?gcode="+gcode);
}

</script>
