<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<form id="pagerForm" method="post"
	action="/buluo718admin/userProfitLogsList.html"><input
		type="hidden" name="ftype" value="${ftype}" /> <input
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
		action="/buluo718admin/userProfitLogsList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>关键词：<input type="text" name="keywords" value="${keywords}"
						size="60" />[会员信息]</td>
					<td>开始日期： <input type="text" name="logDate" class="date"
						readonly="true" value="${logDate }" />
					</td>
					<td>交易市场： <select type="combox" name="ftype">
							<c:forEach items="${trades}" var="type">
								<c:if test="${type.fid == ftype}">
									<option value="${type.fid}" selected="true">${type.fname}</option>
								</c:if>
								<c:if test="${type.fid != ftype}">
									<option value="${type.fid}">${type.fname}</option>
								</c:if>
							</c:forEach>
					</select>
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
					href="/buluo718admin/sendUserProfitReward.html?uid={sid_user}&type=1"
					target="ajaxTodo" title="确定要交易奖励吗?"><span>发放交易奖励</span> </a></li>
				<li><a class="edit"
					href="/buluo718admin/sendUserProfitRewardBatch.html"
					 target="selectedTodo" rel="ids" title="确定要一键发放挖矿奖励吗?" postType="string" ><span>一键发放交易奖励</span> </a></li>	
			</shiro:hasPermission>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th align="center" width="22"><input type="checkbox" group="ids"
					class="checkboxCtrl">
				</th>
				<th align="center" width="60" orderField="fuser.fid"
					<c:if test='${param.orderField == "fuser.fid" }'> class="${param.orderDirection}"  </c:if>>会员UID</th>
				<th align="center" width="60" orderField="fuser.frealName"
					<c:if test='${param.orderField == "fuser.frealName" }'> class="${param.orderDirection}"  </c:if>>会员真实姓名</th>
				<th align="center" width="60" orderField="statisticalDate"
					<c:if test='${param.orderField == "statisticalDate" }'> class="${param.orderDirection}"  </c:if>>统计时间</th>
				<th align="center" width="60" orderField="trademappingStr"
					<c:if test='${param.orderField == "trademappingStr" }'> class="${param.orderDirection}"  </c:if>>市场</th>
				<th align="center" width="60" orderField="cointype.fShortName"
					<c:if test='${param.orderField == "cointype.fShortName" }'> class="${param.orderDirection}"  </c:if>>收益币种</th>
				<th align="center" width="60">收益总额</th>
				<th align="center" width="60">美元估值</th>
				<th align="center" width="60">奖励币种</th>
				<th align="center" width="60">奖励数量</th>
				<th align="center" width="60" orderField="status"
					<c:if test='${param.orderField == "status" }'> class="${param.orderDirection}"  </c:if>>处理状态</th>
				<th align="center" width="60" orderField=flastupdatetime
					<c:if test='${param.orderField == "updateTime" }'> class="${param.orderDirection}"  </c:if>>快照日期</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}"
				var="list" varStatus="num">
				<tr target="sid_user" rel="${list.id}">
				<td><input name="ids" value="${list.id}"
						type="checkbox"></td>
					<td>${list.user.fid}</td>
					<td>${list.user.frealName}</td>
					<td>${list.statisticalDate}</td>
					<td>${list.trademappingStr}</td>
					<td>${list.cointype.fShortName}</td>
					<td><ex:DoubleCut value="${list.amount}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><ex:DoubleCut value="${list.usdtValuation}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td>${list.rewardCointype.fShortName}</td>
					<td><ex:DoubleCut value="${list.rewardAmount}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td>
						<c:choose>
							<c:when test="${list.status == 0}">未结算</c:when>
							<c:when test="${list.status == 1}">已结算</c:when>
						</c:choose>
					</td>
					<td>${list.updateTime}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="panelBar">
		<div class="pages">
			<span>总共: ${totalCount}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${totalCount}"
			numPerPage="${numPerPage}" pageNumShown="5"
			currentPage="${currentPage}"></div>
	</div>
</div>
