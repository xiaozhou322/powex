<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">修改资讯信息</h2>


<div class="pageContent">

     <form id="saveForm" name="saveForm" method="post" action="/buluo718admin/updateActivity.html"
		class="pageForm required-validate" enctype="multipart/form-data"
		onsubmit="return iframeCallback(this, dialogAjaxDone);">		
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>活动名称：</dt>
				<dd>
				    <input type="hidden" name="id" value="${factivityModel.id}"/>
					${factivityModel.name}
				</dd>
			</dl>
			<dl>
				<dt>活动内容）：</dt>
				<dd>
					${factivityModel.content}
				</dd>
			</dl>
			
			<dl>
				<dt>活动类型：</dt>
				<dd>
					${factivityModel.type_s}
				</dd>
			</dl>
			<dl>
				<dt>活动方式：</dt>
				<dd>
					${factivityModel.way_s}
				</dd>
			</dl>
			<dl>
				<dt>活动状态：</dt>
				<dd>
					${factivityModel.status_s}
				</dd>
			</dl>
			<dl>
				<dt>活动总期数：</dt>
				<dd>
					${factivityModel.total_round}
				</dd>
			</dl>
			
			<dl>
				<dt>抽奖花费币种类型：</dt>
				<dd>
					${factivityModel.fvirtualcointype.fShortName}
				</dd>
			</dl>
			<dl>
				<dt>抽奖花费币种数量：</dt>
				<dd>
					${factivityModel.coin_amount}
				</dd>
			</dl>
						<dl>
				<dt>创建时间：</dt>
				<dd>
					${factivityModel.create_time}
				</dd>
			</dl>
						<dl>
				<dt>活动开始时间：</dt>
				<dd>
					${factivityModel.start_time}
				</dd>
			</dl>
			<dl>
				<dt>活动结束时间：</dt>
				<dd>
					${factivityModel.end_time}
				</dd>
			</dl>
			<dl>
				<dt>审核结果：</dt>
				<dd>
					<select name="verify" id="verify">
					<option value="1">通过</option>
					<option value="0">不通过</option>
					</select>
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
							<button type="submit" onclick="fillgcode();">审核</button>
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
function fillgcode(){
	var gcode = $("#gcode").val();
	$("#saveForm").attr("action","/buluo718admin/updateActivity.html?gcode="+gcode);
}
</script>
