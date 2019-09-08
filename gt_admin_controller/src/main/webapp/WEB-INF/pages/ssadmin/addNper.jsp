<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">添加</h2>


<div class="pageContent">

	<form method="post" action="/buluo718admin/saveNper.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<input type="hidden" name="creater" value="${admin_id}" />
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>奖票最小号码：</dt>
				<dd>
					<input type="text" name="lottery_min" maxlength="20"
						class="required" size="50" value="100000001" />
				</dd>
			</dl>
			<dl>
				<dt>奖票最大号码：</dt>
				<dd>
					<input type="text" name="lottery_max" maxlength="20"
						class="required" size="50" value="999999999" />
				</dd>
			</dl>
			<dl>
				<dt>奖励：</dt>
				<dd>
					<dl>
						<dt>虚拟币数量：</dt>
						<dl>
							<input type="text" name="prize_amount" maxlength="20"
								class="required" size="50" onKeyPress="return inputNum(event);" />
						</dl>
					</dl>
					<dl>
						<dt>虚拟币类型：</dt>
						<dl>
							<select type="combox" name="prize_coin_type">
								<c:forEach items="${coinTypeMap}" var="coinType">
									<option value="${coinType.key}">${coinType.value}</option>
								</c:forEach>
							</select>
						</dl>
					</dl>
				</dd>
			</dl>
			<dl>
				<dt>抽奖消耗/次：</dt>
				<dd>
					<dl>
						<dt>虚拟币数量：</dt>
						<dl>
							<input type="text" name="lottery_amount" maxlength="20"
								class="required" size="50" onKeyPress="return inputNum(event);" />
						</dl>
					</dl>
					<dl>
						<dt>虚拟币类型：</dt>
						<dl>
							<select type="combox" name="lottery_coin_type">
								<c:forEach items="${coinTypeMap}" var="coinType">
									<option value="${coinType.key}">${coinType.value}</option>
								</c:forEach>
							</select>
						</dl>
					</dl>
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
							<button type="submit">保存</button>
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
function inputNum(evt){ 
	//火狐使用evt.which获取键盘按键值，IE使用window.event.keyCode获取键盘按键值
	var ev = evt.which?evt.which:window.event.keyCode;
	if(ev>=48&&ev<=57){
	return true;  
	}else{
	return false;
	} 
}
</script>
