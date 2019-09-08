<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">修改自动交易信息</h2>


<div class="pageContent">

	<form method="post" action="/buluo718admin/updateAutotrade.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>虚拟币名称：</dt>
				<dd>
					<select type="combox" name="vid" class="required">
						<c:forEach items="${allType}" var="type">
							<c:if test="${type.fid == fautotrade.ftrademapping.fid}">
								<option value="${type.fid}" selected="true">${type.fvirtualcointypeByFvirtualcointype2.fname}</option>
							</c:if>
							<c:if test="${type.fid != fautotrade.ftrademapping.fid}">
								<option value="${type.fid}">${type.fvirtualcointypeByFvirtualcointype2.fname}</option>
							</c:if>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>类型：</dt>
				<dd>
					<input type="hidden" name="uid" value="${fautotrade.fid }" /> <select
						type="combox" name="ftype">
						<c:forEach items="${map}" var="m">
							<c:if test="${m.key == fautotrade.ftype}">
								<option value="${m.key}" selected="true">${m.value}</option>
							</c:if>
							<c:if test="${m.key != fautotrade.ftype}">
								<option value="${m.key}">${m.value}</option>
							</c:if>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>用户UID：</dt>
				<dd>
					<input type="text" name="fuserid" maxlength="20"
						class="required digits" value="${fautotrade.fuser.fid }" />
				</dd>
			</dl>
			<dl>
				<dt>最小交易数量：</dt>
				<dd>
					<input type="text" name="fminqty" maxlength="20"
						class="required number"  value="<fmt:formatNumber value="${fautotrade.fminqty }" pattern="##.######" maxIntegerDigits="10" maxFractionDigits="6"/>"/>
				</dd>
			</dl>
			<dl>
				<dt>最大交易数量：</dt>
				<dd>
					<input type="text" name="fmaxqty" maxlength="20"
						class="required number"  value="<fmt:formatNumber value="${fautotrade.fmaxqty }" pattern="##.######" maxIntegerDigits="10" maxFractionDigits="6"/>"/>
				</dd>
			</dl>
			<dl>
				<dt>取价类型：</dt>
				<dd>
					<select
						type="combox" name="fsynType">
						<c:forEach items="${typemap}" var="m">
							<c:if test="${m.key == fautotrade.fsynType}">
								<option value="${m.key}" selected="true">${m.value}</option>
							</c:if>
							<c:if test="${m.key != fautotrade.fsynType}">
								<option value="${m.key}">${m.value}</option>
							</c:if>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>最小浮动价格：</dt>
				<dd>
					<input type="text" name="fminprice" maxlength="20"
						class="required number"  value="<fmt:formatNumber value="${fautotrade.fminprice }" pattern="##.######" maxIntegerDigits="10" maxFractionDigits="6"/>"/>
				</dd>
			</dl>
			<dl>
				<dt>最大浮动价格：</dt>
				<dd>
					<input type="text" name="fmaxprice" maxlength="20"
						class="required number"  value="<fmt:formatNumber value="${fautotrade.fmaxprice }" pattern="##.######" maxIntegerDigits="10" maxFractionDigits="6"/>"/>
				</dd>
			</dl>
			<dl>
				<dt>多少分钟一次：</dt>
				<dd>
					<input type="text" name="ftimes" maxlength="20"
						class="required digits"  value="${fautotrade.ftimes }"/>
				</dd>
			</dl>
			<dl>
				<dt>开始前随机停几秒：</dt>
				<dd>
					<input type="text" name="fstoptimes" maxlength="20"
						class="required digits"  value="${fautotrade.fstoptimes }"/>
				</dd>
			</dl>
			
			<dl>
				<dt>谷歌认证码：</dt>
				<dd>
					<input
						type="text" name="gcode" maxlength="6" class="required digits" />
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">保存</button>
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
</script>
