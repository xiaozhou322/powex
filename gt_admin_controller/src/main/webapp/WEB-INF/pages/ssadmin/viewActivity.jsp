<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">查看活动信息</h2>

<div class="pageContent">

	<form method="post" action="/buluo718admin/saveActivity.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>活动标题：</dt>
				<dd>
					<input type="text" name="ftitle" maxlength="20" size="70"
						readonly="readonly" value="${activity.ftitle}" />
				</dd>
			</dl>
			<dl>
				<dt>活动类型：</dt>
				<dd>
					<input type="hidden" name="activityTypeLookup.id"
						value="${activityTypeLookup.id}" /> <input type="text"
						name="activityTypeLookup.activityType"
						value="${activity.factivitytype.fvirtualCoinType.fname}${activity.factivitytype.fname}"
						suggestFields="id,activityType"
						suggestUrl="buluo718admin/activityTypeLookup.html"
						lookupGroup="orgLookup" readonly="readonly" size="70" />
				</dd>
			</dl>
			<dl>
				<dt>是否允许多次参与：</dt>
				<dd>
					<c:choose>
						<c:when test="${activity.fisMultiple}">
							<input type="checkbox" name="fisMultiple" checked='1' />
						</c:when>
						<c:otherwise>
							<input type="checkbox" name="fisMultiple"  readonly="readonly"/>
						</c:otherwise>
					</c:choose>
				</dd>
			</dl>
			<dl>
				<dt>两次间隔时间：</dt>
				<dd>
					<input type="text" name="ftimeInterval" readonly="readonly"
						value="${activity.ftimeInterval}" /> <span>如果可以多次完成，两次获奖的间隔</span>
				</dd>
			</dl>
			<dl>
				<dt>获得奖励：</dt>
				<dd>
					<input type="text" name="frewardPerCount" value="1"
						readonly="readonly" value="${activity.frewardPerCount}" /> <span>完成多少次可以获得奖励，一般是一次</span>
				</dd>
			</dl>
			<dl>
				<dt width="333">推广奖励：</dt>
				<dd>
					<input type="text" name="frewardPromotionPerCount"
						readonly="readonly" value="${activity.frewardPromotionPerCount}" />
					<span>完成多少次可以获得推广奖励</span>
				</dd>
			</dl>
			<dl>
				<dt>是否永久有效：</dt>
				<dd>
					<c:choose>
						<c:when test="${activity.fisActiveForever}">
							<input type="checkbox" name="fisActiveForever" checked='1' readonly="readonly"/>
						</c:when>
						<c:otherwise>
							<input type="checkbox" name="fisActiveForever" readonly="readonly"/>
						</c:otherwise>
					</c:choose>
				</dd>
			</dl>
			<dl>
				<dt>开始时间：</dt>
				<dd>
					<input type="text" name="fBeginTime" class="date" readonly="true"
						dateFmt="yyyy-MM-dd HH:mm:ss" size="40" readonly="readonly"
						value="${activity.fBeginTime}" /> <span>非永久有效情况，开始时间</span>
				</dd>
			</dl>
			<dl>
				<dt>结束时间：</dt>
				<dd>
					<input type="text" name="fEndTime" class="date" readonly="true"
						dateFmt="yyyy-MM-dd HH:mm:ss" size="40" readonly="readonly"
						value="${activity.fEndTime}" /> <span>非永久有效情况，开始时间</span>
				</dd>
			</dl>
			<dl>
				<dt>活动内容：</dt>
				<dd>
					<textarea name="fcontent" cols="70" rows="3" class="required"
						readonly="readonly">${activity.fcontent}</textarea>
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">关闭</button>
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
