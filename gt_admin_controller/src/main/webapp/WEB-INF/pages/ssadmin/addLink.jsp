<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">添加友情链接信息</h2>


<div class="pageContent">

	<form id="saveForm" name="saveForm" method="post" action="/buluo718admin/saveLink.html"
		class="pageForm required-validate" enctype="multipart/form-data"
		onsubmit="return iframeCallback(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>图标链接：</dt>
				<dd>
					<input type="file" class="inputStyle" value="" name="filedata"
						id="filedata" />
				</dd>
			</dl>
			<dl>
				<dt>名称：</dt>
				<dd>
					<input type="text" name="fname" maxlength="20" class="required"
						size="70" />
				</dd>
			</dl>
	
			<dl>
				<dt>类型：</dt>
				<dd>
					<select type="combox" name="ftype">
						<c:forEach items="${linkTypeMap}" var="link">
							<option value="${link.key}">${link.value}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>链接地址：</dt>
				<dd>
					<input type="text" name="furl" maxlength="100" class="required"
						size="70" />
				</dd>
			</dl>
			<dl>
				<dt>描述：</dt>
				<dd>
					<input type="text" name="fdescription" maxlength="120" size="70" />
				</dd>
			</dl>
			<dl>
				<dt>顺序：</dt>
				<dd>
					<input type="text" name="forder" maxlength="20"
						class="required digits" />
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
	function customvalidXxx(element) {
		if ($(element).val() == "xxx")
			return false;
		return true;
	}
	function fillgcode(){
		var gcode = $("#gcode").val();
		$("#saveForm").attr("action","/buluo718admin/saveLink.html?gcode="+gcode);
	}
</script>
