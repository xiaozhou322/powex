<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<h2 class="contentTitle">
	经纪人<font color="red">${virtualpresalelog.fid}</font>推荐奖励确认审核
</h2>


<div class="pageContent">

	<form method="post" action="/buluo718admin/auditEntrustIntro.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>经纪人ID：</dt>
				<dd>${fintroUser.fid}
				<input type="hidden" name="uid" value="${fintroUser.fid}" />
				<input type="hidden" name="reward" value="${reward}" />
				<input type="hidden" name="logDate" value="${logDate}" />
				<input type="hidden" name="flag" value="${flag}" />
			</dl>
			<dl>
				<dt>经纪人真实姓名：</dt>
				<dd style="color:#ff3300;font-weight:bold;font-size:18px;">${fintroUser.frealName}</dd>
			</dl>
			<dl>
				<dt>统计时间：</dt>
				<dd>${logDate}</dd>
			</dl>
			<dl>
				<dt>推荐人交易总量：</dt>
				<dd>${introTotal}</dd>
			</dl>
			<dl>
				<dt>当日交易总量：</dt>
				<dd>${totalAmount}</dd>
			</dl>
			<dl>
				<dt>预计可获得GT奖励数：</dt>
				<dd style="color:#ff3300;font-weight:bold;font-size:18px;">${reward}
				<c:if test="${flag eq true }">，本期奖励已经发放，不能重新发放</c:if>
				</dd>
			</dl>
			<dl>
				<dt>谷歌认证码：</dt>
				<dd>
					<input
						type="text" name="gcode" maxlength="6" class="required digits" />
				</dd>
			</dl>
			<c:if test="${flag eq false }">
			<dt>审核码：</dt>
				<dd>
					<input type="password" name="confirmcode" size="50" value="" class="required" />
				</dd>
			</c:if>
		<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">序号</th>
				<th width="60">经纪人UID</th>
				<th width="60">真实姓名</th>
				<th width="60">推荐会员UID</th>
				<th width="60">推荐交易量</th>
				<th width="60">推荐手续费</th>
				<th width="60">当日GT释放数</th>
				<th width="60">当日交易量</th>
				<th width="60">当日手续费</th>
				<th width="60">预计可得GT</th>
				<th width="60">处理状态</th>
				<th width="60">快照日期</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}"
				var="list" varStatus="num">
				<tr>
					<td>${num.index +1}</td>
					<td>${list.fintroUser.fid}</td>
					<td>${list.fintroUser.frealName}</td>
					<td>${list.fuser.fid}</td>
					<td>${list.famount}</td>
					<td>${list.ffee}</td>
					<td>${list.fgtqty}</td>
					<td>${list.ftotalamount}</td>
					<td>${list.ftotalfee}</td>
					<td>${list.freward}</td>
					<td>${list.status}</td>
					<td>${list.flastupdatetime}</td>
				</tr>
			</c:forEach>
		</tbody>
		</table>
		</div>
		
		<div class="formBar">
			<ul>
				<c:if test="${flag eq false }">
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">确认审核</button>
						</div>
					</div>
				</li>
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
