<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<h2 class="contentTitle">
	项目方<font color="red"></font>上币信息审核
</h2>


<div class="pageContent">

	<form method="post" action="/buluo718admin/pcointypeAudit.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<input type="hidden" name="pcointypeId" value="${pcointype.id}" />
		<div class="pageFormContent nowrap" layoutH="97">
			<c:if test="${type == 1}">
				<dl>
					<dt>审核结果：</dt>
					<dd>
						<select type="combox" name="auditStatus" class="required">
							<option value="102">通过</option>
							<option value="103">不通过</option>
						</select>
					</dd>
				</dl>
			</c:if>
			<c:if test="${type == 2}">
				<dl>
					<dt>审核结果：</dt>
					<dd>
						<c:choose>
							<c:when test="${pcointype.auditStatus == 101}">待审核</c:when>
							<c:when test="${pcointype.auditStatus == 102}">审核通过</c:when>
							<c:when test="${pcointype.auditStatus == 103}">审核不通过</c:when>
						</c:choose>
					</dd>
				</dl>
			</c:if>
			<dl>
				<dt>项目方ID：</dt>
				<dd><input type="text" name="projectId" size="70" readonly="readonly"
						value="${pcointype.projectId.fid}" />
				</dd>
			</dl>
			<dl>
				<dt>币种名称：</dt>
				<dd><input
						type="text" name="name" readonly="true" size="70"
						value="${pcointype.name}" />
				</dd>
			</dl>
			<dl>
				<dt>币种简称：</dt>
				<dd>
					<input type="text" name="shortName" size="70" readonly="true"
						value="${pcointype.shortName}" />
				</dd>
			</dl>
			<dl>
				<dt>logo图片：</dt>
				<dd>
					<img src="${pcointype.logoUrl }" width="50" />
				</dd>
			</dl>
			<dl>
				<dt>发行数量：</dt>
				<dd>
					<ex:DoubleCut value="${pcointype.pushTotal}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>
				</dd>
			</dl>
			<dl>
				<dt>发行价格：</dt>
				<dd>
					<ex:DoubleCut value="${pcointype.pushPrice}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>
				</dd>
			</dl>
			<dl>
				<dt>当前流通量：</dt>
				<dd>
					<ex:DoubleCut value="${pcointype.currentCirculation}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>
				</dd>
			</dl>
			<dl>
				<dt>当前市值：</dt>
				<dd>
					<ex:DoubleCut value="${pcointype.currentMarketValue}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>
				</dd>
			</dl>
			<dl>
				<dt>当前持币用户：</dt>
				<dd>
					<ex:DoubleCut value="${pcointype.currentHoldNum}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>
				</dd>
			</dl>
			<dl>
				<dt>小数精度位：</dt>
				<dd>
					<input type="text" name="decimals" size="70" readonly="true"
						value="${pcointype.decimals}" />
				</dd>
			</dl>
			<dl>
				<dt>开放交易时间：</dt>
				<dd>
					<input type="text" name="openTradeTime" size="70" readonly="true"
						value="${pcointype.openTradeTime}" />
				</dd>
			</dl>
			<dl>
				<dt>开启提币时间：</dt>
				<dd>
					<input type="text" name="openWithdrawTime" size="70" readonly="true"
						value="${pcointype.openWithdrawTime}" />
				</dd>
			</dl>
			<dl>
				<dt>开启充币时间：</dt>
				<dd>
					<input type="text" name="openChargeTime" size="70" readonly="true"
						value="${pcointype.openChargeTime}" />
				</dd>
			</dl>
			<dl>
				<dt>官网地址：</dt>
				<dd>
					<input type="text" name="website" size="70" readonly="true"
						value="${pcointype.website}" />
				</dd>
			</dl>
			<dl>
				<dt>合约地址：</dt>
				<dd>
					<input type="text" name="contractAddr" size="70" readonly="true"
						value="${pcointype.contractAddr}" />
				</dd>
			</dl>
			<dl>
				<dt>区块链浏览器地址：</dt>
				<dd>
					<input type="text" name="blockAddr" size="70" readonly="true"
						value="${pcointype.blockAddr}" />
				</dd>
			</dl>
			<dl>
				<dt>已签合约代码链接：</dt>
				<dd>
					<input type="text" name="contractLink" size="70" readonly="true"
						value="${pcointype.contractLink}" />
				</dd>
			</dl>
			<dl>
				<dt>币实际用途：</dt>
				<dd>
					<input type="text" name="coinUsed" size="70" readonly="true"
						value="${pcointype.coinUsed}" />
				</dd>
			</dl>
			<c:if test="${type == 1}">
				<dl>
					<dt>谷歌认证码：</dt>
					<dd>
						<input
							type="text" name="gcode" maxlength="6" class="required digits" />
					</dd>
				</dl>
			</c:if>
		</div>
		<div class="formBar">
			<ul>
				<c:if test="${type == 1}">
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
				</c:if>
				<c:if test="${type == 2}">
					<li><div class="button">
							<div class="buttonContent">
								<button type="button" class="close">关闭</button>
							</div>
						</div>
					</li>
				</c:if>
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
