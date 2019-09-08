<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">发放奖励</h2>


<div class="pageContent">

	<form method="post" action="/buluo718admin/doSendPrize.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>登陆名：</dt>
				<dd>
				    <input type="hidden" name="nper_id" value="${nper.id}"/>
				    <input type="hidden" name="uid" value="${user.fid}"/>
					<input type="text" name="fname" maxlength="30" size="40" 
					  value="${user.floginName}" disabled="true"/>
				</dd>
			</dl>
			<dl>
				<dt>虚拟币类型：</dt>
				<dd>
					<select type="combox" name="coin_type" disabled="true">
					<option value="${nper.prize_coin_type}">${coin_name}</option>
		            </select>
				</dd>
			</dl>
			<dl>
				<dt>发放数量：</dt>
				<dd>
					<input type="text" name=amount maxlength="30"
						class="required number" size="40"  value="<fmt:formatNumber value="${nper.prize_amount}" pattern="##.######" maxIntegerDigits="20" maxFractionDigits="10"/>" disabled="true"/>
				</dd>
			</dl>
			<dl>
				<dt>进账累计总数：</dt>
				<dd>${rewardcount+buycount+rechargecount+fabigetcount}
				（购买：${buycount}，
				充值：${rechargecount}，
				奖励：${rewardcount}，
				法币获得数量：${fabigetcount}）</dd>
			</dl>
			<dl>
				<dt>出账累计总数：</dt>
				<dd>${sellcount+withdrawcount+fabiusecount}
				（售出：${sellcount}，
				提现：${withdrawcount}，
				法币消耗数量：${fabiusecount}）</dd>
			</dl>
			<dl>
				<dt>冻结累计数量：</dt>
				<dd>${sellfrozen+withdrawfrozen+fabifrozen}
				（交易冻结：${sellfrozen}，
				提现冻结：${withdrawfrozen}，
				法币冻结数量：${fabifrozen}）</dd>
			</dl>
			<dl>
				<dt>有效数量：</dt>
				<dd>${youxiaoshu}</dd>
			</dl>
			<dl>
				<dt>当前钱包数量：</dt>
				<dd>可用：<fmt:formatNumber value="${wallettotal}" pattern="##.######" maxIntegerDigits="20" maxFractionDigits="10"/>，
				冻结：<fmt:formatNumber value="${walletfrozen}" pattern="##.######" maxIntegerDigits="20" maxFractionDigits="10"/></dd>
			</dl>
			<dl>
				<dt>谷歌认证码：</dt>
				<dd>
					<input
						type="text" name="gcode" maxlength="6" class="required digits" />
				</dd>
			</dl>	
			<dl>
			<dt>审核码：</dt>
				<dd>
					<input type="password" name="confirmcode" size="50" value="" class="required" />
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">发放</button>
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
