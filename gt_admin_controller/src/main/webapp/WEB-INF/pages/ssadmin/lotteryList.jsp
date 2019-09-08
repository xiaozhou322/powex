<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="/buluo718admin/lotteryList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="uid" value="${uid}" /> <input
		type="hidden" name="pageNum" value="${currentPage}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /><input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/lotteryList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>期数：
						<select type="combox" name="nper">
							<c:forEach items="${nperList}" var="nper">
								<option value="${nper}">${nper}</option>
							</c:forEach>
						</select>
					</td>
					<td>用户id：
						<input type="text" name="uid" value="${uid}" />
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">查询</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">序号</th>
				<th width="40">期数</th>
				<th width="40">奖票号码</th>
				<th width="40">用户id</th>
				<th width="40">抽奖日期</th>
				<th width="60">序列号</th>
				<th width="60">区块链接</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}" var="lottery" varStatus="num">
				<tr target="sid_user" rel="${lottery.id}">
					<td>${num.index +1}</td>
					<td>${lottery.nper}</td>
					<td>${lottery.lottery_no}</td>
					<td>${lottery.uid}</td>
					<td>${lottery.createtime}</td>
					<td>${lottery.serial_no}</td>
					<td>${lottery.block_link}</td>
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
