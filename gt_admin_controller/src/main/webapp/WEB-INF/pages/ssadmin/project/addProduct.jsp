<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<h2 class="contentTitle">添加产品信息</h2>


<div class="pageContent">

	<form id="saveForm" name="saveForm" method="post" action="/buluo718admin/saveProduct.html"
		class="pageForm required-validate"
		onsubmit="return iframeCallback(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>产品名称：</dt>
				<dd>
					<input type="text" name="name" class="required" size="50" />
				</dd>
			</dl>
			<dl>
				<dt>产品简称：</dt>
				<dd>
					<input type="text" name="shortName" class="required" size="50" />
				</dd>
			</dl>
			<dl>
				<dt>兑换比例：</dt>
				<dd>
					<input type="text" name="convertRatio" class="required" size="50" />
				</dd>
			</dl>
			<dl>
				<dt>到期兑换比例：</dt>
				<dd>
					<input type="text" name="convertRatioExpire" class="required" size="50" />
				</dd>
			</dl>
			<dl>
				<dt>开始时间：</dt>
				<dd>
					<input type="text" name="startDate" class="required date" readonly="true" />
				</dd>
			</dl>
			<dl>
				<dt>周期：</dt>
				<dd>
				     <select type="combox" name="period">
						<c:forEach items="${periodType}" var="t">
							<option value="${t.key}">${t.value}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>兑换币种：</dt>
				<dd>
					<select type="combox" name="convertCointype" class="required">
						<c:forEach items="${fvirtualcointypes}" var="type">
							<option value="${type.fid}">${type.fShortName}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>发行总量：</dt>
				<dd>
					<input type="text" name="pushTotal" class="required number" size="50" />
				</dd>
			</dl>
			<dl>
				<dt>最大总兑换数量：</dt>
				<dd>
					<input type="text" name="maxTotalAmount" class="required number" size="50"/>
				</dd>
			</dl>
			<dl>
				<dt>单次最少兑换数量：</dt>
				<dd>
					<input type="text" name="minTimeAmount" class="required number" size="50"/>
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
	$("#saveForm").attr("action","/buluo718admin/saveProduct.html?gcode="+gcode);
}
</script>
