<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<form id="pagerForm" method="post"
	action="/buluo718admin/snapGtList.html"><input
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
		action="/buluo718admin/snapGtList.html" method="post">
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
			<shiro:hasPermission name="buluo718admin/sendGtReward.html">
				<li><a class="edit"
					href="/buluo718admin/sendGtReward.html?uid={sid_user}"
					target="ajaxTodo" title="确定要发放GT分红吗?"><span>发放分红</span> </a></li>
				<li><a class="edit"
					href="/buluo718admin/sendGtRewardBatch.html"
					 title="确定要一键发放GT分红吗吗?" target="selectedTodo" rel="ids"
				postType="string" ><span>一键发放分红</span> </a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids"
					class="checkboxCtrl">
				</th>
				<th width="60" orderField="fuser.fid"
					<c:if test='${param.orderField == "fuser.fid" }'> class="${param.orderDirection}"  </c:if>>会员UID</th>
				<th width="60" orderField="fuser.frealName"
					<c:if test='${param.orderField == "fuser.frealName" }'> class="${param.orderDirection}"  </c:if>>会员真实姓名</th>
				<th width="60" orderField="ftotal"
					<c:if test='${param.orderField == "ftotal" }'> class="${param.orderDirection}"  </c:if>>可用GT数</th>
				<th width="60" orderField="ffrozen"
					<c:if test='${param.orderField == "ffrozen" }'> class="${param.orderDirection}"  </c:if>>冻结GT数</th>
				<th width="60" orderField="ftotal"
					<c:if test='${param.orderField == "fLocked" }'> class="${param.orderDirection}"  </c:if>>锁仓GT数</th>
				<th width="60">参与分红GT数</th>
				<th width="60" orderField="fgtqty"
					<c:if test='${param.orderField == "fgtqty" }'> class="${param.orderDirection}"  </c:if>>平台当日GT总数</th>
				<th width="60" orderField="ftotalamount"
					<c:if test='${param.orderField == "ftotalamount" }'> class="${param.orderDirection}"  </c:if>>平台当日交易量</th>
				<th width="60" orderField="ftotalfee"
					<c:if test='${param.orderField == "ftotalfee" }'> class="${param.orderDirection}"  </c:if>>平台当日手续费</th>
				<th width="60" orderField="freward"
					<c:if test='${param.orderField == "freward" }'> class="${param.orderDirection}"  </c:if>>用户可得分红</th>
				<th width="60" orderField="status"
					<c:if test='${param.orderField == "status" }'> class="${param.orderDirection}"  </c:if>>状态</th>
				<th width="60" orderField=flastupdatetime
					<c:if test='${param.orderField == "flastupdatetime" }'> class="${param.orderDirection}"  </c:if>>快照日期</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}"
				var="list" varStatus="num">
				<tr target="sid_user" rel="${list.fid}">
					<td><input name="ids" value="${list.fid}"
						type="checkbox"></td>
					<td>${list.fuser.fid}</td>
					<td>${list.fuser.frealName}</td>
					<td><ex:DoubleCut value="${list.ftotal}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><ex:DoubleCut value="${list.ffrozen}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><ex:DoubleCut value="${list.flocked}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><ex:DoubleCut value="${list.ftotal+list.flocked}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><ex:DoubleCut value="${list.fgtqty}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><ex:DoubleCut value="${list.ftotalamount}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><ex:DoubleCut value="${list.ftotalfee}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><ex:DoubleCut value="${list.freward}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td>${list.status}</td>
					<td>${list.flastupdatetime}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="panelBar">
		<div class="pages">
			<span>总共: ${totalCount}条，实际可分红GT数量：${usergts }，总计发放分红：${totalreward}USDT</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${totalCount}"
			numPerPage="${numPerPage}" pageNumShown="5"
			currentPage="${currentPage}"></div>
	</div>
</div>
