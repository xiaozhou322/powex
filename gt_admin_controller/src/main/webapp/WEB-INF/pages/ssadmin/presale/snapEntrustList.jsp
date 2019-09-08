<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<form id="pagerForm" method="post"
	action="/buluo718admin/snapEntrustList.html"><input
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
		action="/buluo718admin/snapEntrustList.html" method="post">
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
			<shiro:hasPermission name="buluo718admin/sendEntrustReward.html">
				<li><a class="edit"
					href="/buluo718admin/sendEntrustReward.html?uid={sid_user}&type=1"
					target="ajaxTodo" title="确定要发放GT奖励吗?"><span>发放GT奖励</span> </a></li>
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
				<th width="60" orderField="fuser.fid"
					<c:if test='${param.orderField == "fuser.fid" }'> class="${param.orderDirection}"  </c:if>>会员UID</th>
				<th width="60" orderField="fuser.frealName"
					<c:if test='${param.orderField == "fuser.frealName" }'> class="${param.orderDirection}"  </c:if>>会员真实姓名</th>
				<th width="60" orderField="famount"
					<c:if test='${param.orderField == "famount" }'> class="${param.orderDirection}"  </c:if>>会员交易量</th>
				<th width="60" orderField="ffee"
					<c:if test='${param.orderField == "ffee" }'> class="${param.orderDirection}"  </c:if>>会员手续费</th>
				<th width="60" orderField="fgtqty"
					<c:if test='${param.orderField == "fgtqty" }'> class="${param.orderDirection}"  </c:if>>平台当日GT释放数</th>
				<th width="60" orderField="ftotalamount"
					<c:if test='${param.orderField == "ftotalamount" }'> class="${param.orderDirection}"  </c:if>>平台当日交易量</th>
				<th width="60" orderField="ftotalfee"
					<c:if test='${param.orderField == "ftotalfee" }'> class="${param.orderDirection}"  </c:if>>平台当日手续费</th>
				<th width="60" orderField="freward"
					<c:if test='${param.orderField == "freward" }'> class="${param.orderDirection}"  </c:if>>会员预计可得GT</th>
				<th width="60" orderField="status"
					<c:if test='${param.orderField == "status" }'> class="${param.orderDirection}"  </c:if>>处理状态</th>
				<th width="60" orderField=flastupdatetime
					<c:if test='${param.orderField == "flastupdatetime" }'> class="${param.orderDirection}"  </c:if>>快照日期</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}"
				var="list" varStatus="num">
				<tr target="sid_user" rel="${list.fid}">
					<td>${num.index +1}</td>
					<td>${list.fuser.fid}</td>
					<td>${list.fuser.frealName}</td>
					<td><ex:DoubleCut value="${list.famount}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><ex:DoubleCut value="${list.ffee}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td>${list.fgtqty}</td>
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
			<span>总共: ${totalCount}条，总计发放交易奖励${totalreward }GT</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${totalCount}"
			numPerPage="${numPerPage}" pageNumShown="5"
			currentPage="${currentPage}"></div>
	</div>
</div>
