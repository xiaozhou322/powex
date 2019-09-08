<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">锁定虚拟币提现</h2>


<div class="pageContent">

	<form method="post" action="/buluo718admin/goVirtualCapitaloperationChangeStatus.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
<!-- 			<dl>
				<dt>钱包密码：</dt>
				<dd>
					<input type="password" name="fpassword" maxlengpahx="50"  size="40"/>
				</dd>
			</dl> -->
			<dl>
				<dt>登陆名：</dt>
				<dd>
				    <input type="hidden" name="uid" value="${virtualCapitaloperation.fid}"/>
				    <input type="hidden" name="type" value="1"/>
					<input type="text" name="fname" maxlength="30" size="40" 
					  value="${virtualCapitaloperation.fuser.floginName}" disabled="true"/>
					  <c:if test="${teshuflag }">
					  <em style="color:#ff0000">该账户是特殊账户，请谨慎处理</em>
					  </c:if>
				</dd>
			</dl>
			<dl>
				<dt>虚拟币类型：</dt>
				<dd>
					<select type="combox" name="virtualcointype.fid" disabled="true">
					<option value="${virtualCapitaloperation.fvirtualcointype.fid}">${virtualCapitaloperation.fvirtualcointype.fname}</option>
		            </select>
				</dd>
			</dl>
			<dl>
				<dt>提现数量：</dt>
				<dd>
					<input type="text" name=famount maxlength="30"
						class="required number" size="40"  value="<fmt:formatNumber value="${virtualCapitaloperation.famount}" pattern="##.######" maxIntegerDigits="20" maxFractionDigits="10"/>" disabled="true"/>
				</dd>
			</dl>
			<dl>
				<dt>提现手续费：</dt>
				<dd>
					<input type="text" name="ffees" maxlength="30"
						class="required number" size="40"  value="<fmt:formatNumber value="${virtualCapitaloperation.ffees}" pattern="##.######" maxIntegerDigits="20" maxFractionDigits="10"/>" disabled="true"/>
				</dd>
			</dl>
			<dl>
				<dt>提现地址：</dt>
				<dd>
					<input type="text" name="withdraw_virtual_address" maxlength="30"
						class="required number" size="40" value="${virtualCapitaloperation.withdraw_virtual_address}" disabled="true"/>
				</dd>
			</dl>
			<dl>
				<dt>进账累计总数：</dt>
				<dd>${rewardcount+buycount+rechargecount+fabigetcount+drawcount+tranincount}
				（购买：${buycount}，
				充值：${rechargecount}，
				奖励：${rewardcount}，
				法币获得数量：${fabigetcount}，
				OTC购买数量：${otcbuycount}，
				手工入账数量：${operationcount}，
				站内转入数量：${tranincount}，
				APP划转数量：${drawcount}）</dd>
			</dl>
			<dl>
				<dt>出账累计总数：</dt>
				<dd>${sellcount+withdrawcount+fabiusecount+tranoutcount}
				（售出：${sellcount}，
				提现：${withdrawcount}，
				法币消耗数量：${fabiusecount}，
				OTC售出数量：${otcsellcount}，
				站内转出数量：${tranoutcount}）</dd>
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
			<c:if test="${withdrawfrozen>=tixianzhi && youxiaoshu>=0}">
				
				<c:if test="${needadmin==false }">
				<dl>
				<dt>审核码：</dt>
					<dd>
						<input type="password" name="confirmcode" size="50" value="" class="required" />
					</dd>
				</dl>
				</c:if>
			</c:if>
		</div>
		<div class="formBar">
			<ul>
			<c:if test="${withdrawfrozen>=tixianzhi && youxiaoshu>=0}">
				<c:if test="${needadmin==false }">
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">审核</button>
						</div>
					</div>
				</li>
				</c:if>
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
