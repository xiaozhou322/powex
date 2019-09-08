<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<h2 class="contentTitle">
	<font color="red"></font>产品信息审核
</h2>


<div class="pageContent">

	<form method="post" action="/buluo718admin/pproductAudit.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<input type="hidden" name="productId" value="${pproduct.id}" />
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
							<c:when test="${pproduct.auditStatus == 101}">待审核</c:when>
							<c:when test="${pproduct.auditStatus == 102}">审核通过</c:when>
							<c:when test="${pproduct.auditStatus == 103}">审核不通过</c:when>
						</c:choose>
					</dd>
				</dl>
			</c:if>
			<dl>
				<dt>产品名称：</dt>
				<dd><input type="text" name="name" readonly="true" size="50" value="${pproduct.name}" />
				</dd>
			</dl>
			<dl>
				<dt>产品简称：</dt>
				<dd>
					<input type="text" name="shortName" size="50" readonly="true"
						value="${pproduct.shortName}" />
				</dd>
			</dl>
			<dl>
				<dt>兑换比例：</dt>
				<dd>
					<input type="text" name="convertRatio" readonly="true" size="50" value="${pproduct.convertRatio}" />
				</dd>
			</dl>
			<dl>
				<dt>到期兑换比例：</dt>
				<dd>
					<input type="text" name="convertRatioExpire" readonly="true" size="50" value="${pproduct.convertRatioExpire}"/>
				</dd>
			</dl>
			<dl>
				<dt>开始时间：</dt>
				<dd>
					<input type="text" name="startDate" readonly="true"  dateFmt="yyyy-MM-dd" value="${pproduct.startDate}" />
				</dd>
			</dl>
			<dl>
				<dt>周期：</dt>
				<dd>
				     <select type="combox" name="period">
						<c:forEach items="${periodType}" var="t">
							<c:if test="${t.key == pproduct.period}">
								<option value="${t.key}" selected="true">${t.value}</option>
							</c:if>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>兑换币种：</dt>
				<dd>
					<input type="text" name="convertCointype"  readonly="true" size="20"  value="${pproduct.convertCointype.fShortName}" />
				</dd>
			</dl>
			<dl>
				<dt>发行总量：</dt>
				<dd>
					<ex:DoubleCut value="${pproduct.pushTotal}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>
				</dd>
			</dl>
			<dl>
				<dt>最大总兑换数量：</dt>
				<dd>
					<ex:DoubleCut value="${pproduct.maxTotalAmount}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="2"/>
				</dd>
			</dl>
			<dl>
				<dt>单次最少兑换数量：</dt>
				<dd>
					<ex:DoubleCut value="${pproduct.minTimeAmount}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="2"/>
				</dd>
			</dl>
			<dl>
				<dt>产品可用额：</dt>
				<dd>
					<ex:DoubleCut value="${pproduct.proAvailableAmount}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>
				</dd>
			</dl>
			<dl>
				<dt>产品冻结额：</dt>
				<dd>
					<ex:DoubleCut value="${pproduct.proFrozenAmount}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>
				</dd>
			</dl>
			<dl>
				<dt>兑换可用额：</dt>
				<dd>
					<ex:DoubleCut value="${pproduct.convertAvailableAmount}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>
				</dd>
			</dl>
			<dl>
				<dt>兑换冻结额：</dt>
				<dd>
					<ex:DoubleCut value="${pproduct.convertFrozenAmount}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>
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
