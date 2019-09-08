<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">添加系统参数信息</h2>


<div class="pageContent">

	<form id="saveForm" name="saveForm" method="post" action="/buluo718admin/saveSystemArgs.html"
		class="pageForm required-validate" enctype="multipart/form-data"
		onsubmit="return iframeCallback(this, dialogAjaxDone);">

		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>KEY:</dt>
				<dd>
					<input type="text" name="key" maxlength="50" class="required"
						size="70" />
				</dd>
			</dl>
			<dl>
				<dt>URL:</dt>
				<dd>
					<input type="text" name="furl" size="70" />
				</dd>
			</dl>
			<dl>
				<dt>图片链接：</dt>
				<dd>
					<input type="file" class="inputStyle" value="" name="filedata"
						id="filedata" />
				</dd>
			</dl>
			<dl>
				<dt>参数值:</dt>
				<dd>
					<textarea name="value" rows="10" cols="70"/>
				</dd>
			</dl>
			<dl>
				<dt>备注:</dt>
				<dd>
					<textarea name="desc" cols="70" rows="1" />
				</dd>
			</dl>
			
			<dl>
				<dt>谷歌认证码：</dt>
				<dd>
					<input
						type="text" id="gcode" name="gcode" maxlength="6" class="required digits" />
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit" onclick="fillgcode();">保存</button>
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
	function customvalidXxx(element) {
		if ($(element).val() == "xxx")
			return false;
		return true;
	}
	function fillgcode(){
		var gcode = $("#gcode").val();
		$("#saveForm").attr("action","/buluo718admin/saveSystemArgs.html?gcode="+gcode);
	}
</script>
