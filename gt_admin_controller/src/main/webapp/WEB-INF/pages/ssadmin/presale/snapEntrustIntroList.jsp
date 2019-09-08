<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<form id="pagerForm" method="post"
	action="/buluo718admin/snapEntrustIntroList.html"><input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="pageNum" value="${currentPage}" /><input
		type="hidden" name="logDate" value="${logDate}" /><input
		type="hidden" name="logDate2" value="${logDate2}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /><input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/snapEntrustIntroList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>关键词：<input type="text" name="keywords" value="${keywords}"
						size="60" />[会员信息]</td>
					<td>开始日期： <input type="text" name="logDate" class="date"
						readonly="true" value="${logDate }" />
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">查询</button>
							</div>
						</div></li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<shiro:hasPermission name="buluo718admin/entrustIntroReward.html">
				<li><a class="edit"
					href="/buluo718admin/entrustIntroReward.html?uid={sid_user}&logDate=${logDate }"
					height="580" width="800" target="dialog" title="确定要发放经纪人GT奖励吗?"><span>发放经纪人GT奖励</span> </a></li>
<!-- 				<li><a class="edit" -->
<!-- 					href="/buluo718admin/sendEntrustRewardBatch.html?uid={sid_user}" -->
<!-- 					target="ajaxTodo" title="确定要一键发放GT奖励吗?"><span>一键发放GT奖励</span> </a></li>	 -->
			</shiro:hasPermission>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">序号</th>
				<th width="60">经纪人UID</th>
				<th width="60">经纪人真实姓名</th>
				<th width="60">被推荐会员交易量</th>
				<th width="60">被推荐会员手续费</th>
				<th width="60">平台当日GT释放数</th>
				<th width="60">平台当日交易量</th>
				<th width="60">平台当日手续费</th>
				<th width="60">被推荐会员预计可得GT</th>
				<th width="60">快照日期</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}"
				var="list" varStatus="num">
				<tr target="sid_user" rel="${list.fintroUser.fid}">
					<td>${num.index +1}</td>
					<td>${list.fintroUser.fid}</td>
					<td>${list.fintroUser.frealName}</td>
					<td><ex:DoubleCut value="${list.famount}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><ex:DoubleCut value="${list.ffee}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td>${list.fgtqty}</td>
					<td><ex:DoubleCut value="${list.ftotalamount}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><ex:DoubleCut value="${list.ftotalfee}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><ex:DoubleCut value="${list.famount/list.ftotalamount*list.fgtqty*0.3}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td>${list.flastupdatetime}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="panelBar">
		<div class="pages">
			<span>总共: ${totalCount}条，总计发放推荐人交易奖励：${totalreward }GT</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${totalCount}"
			numPerPage="${numPerPage}" pageNumShown="5"
			currentPage="${currentPage}"></div>
	</div>
</div>
