<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="/buluo718admin/nperList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="nper" value="${nper}" /> <input
		type="hidden" name="pageNum" value="${currentPage}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /><input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/nperList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>期数：<input type="text" name="nper"
						value="${nper}" size="60" /></td>
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
			<shiro:hasPermission name="buluo718admin/addNper.html">
				<li><a class="add"
					href="/buluo718admin/goNperJsp.html?url=ssadmin//addNper" height="350"
					width="800" target="dialog" rel="addNper"><span>新增</span> </a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="buluo718admin/startNper.html">
				<li><a class="edit"
					href="/buluo718admin/startNper.html?nper_id={sid_user}"
					target="ajaxTodo" title="确定要开启吗?"><span>开启</span> </a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="buluo718admin/drawLottery.html">
				<li><a class="edit"
					href="/buluo718admin/drawLottery.html?nper_id={sid_user}"
					target="ajaxTodo" title="确定要摇奖吗?"><span>摇奖</span> </a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="buluo718admin/drawLottery.html">
				<li><a id="sendPrize" href="/buluo718admin/goNperJsp.html?url=ssadmin//sendPrize&nper_id={sid_user}" height="420"
					width="800" target="dialog" rel="sendPrize"></a>
					<a class="edit" onclick="doSendPrize({sid_user})"><span>发放奖励</span> </a>
				</li>
			</shiro:hasPermission>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">序号</th>
				<th width="40">期数</th>
				<th width="40">奖票最小号码</th>
				<th width="40">奖票最大号码</th>
				<th width="40">中奖号码</th>
				<th width="40">中奖用户id</th>
				<th width="40">抽奖消耗/次</th>
				<th width="40">奖金</th>
				<th width="40" orderField="status"
					<c:if test='${param.orderField == "status" }'> class="${param.orderDirection}"  </c:if>>状态</th>
				<th width="40">是否发放奖励</th>
				<th width="40">创建日期</th>
				<th width="40">开启日期</th>
				<th width="40">摇奖日期</th>
				<th width="40">powBall</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}" var="nper" varStatus="num">
				<tr target="sid_user" rel="${nper.id}">
					<td>${num.index +1}</td>
					<td>${nper.nper}</td>
					<td>${nper.lottery_min}</td>
					<td>${nper.lottery_max}</td>
					<td><c:if test='${nper.win_no == ""}'>--</c:if><c:if test='${nper.win_no != ""}'>${nper.win_no}</c:if></td>
					<td><c:if test='${nper.win_uid == ""}'>--</c:if><c:if test='${nper.win_uid != ""}'>${nper.win_uid}</c:if></td>
					<td><c:if test='${nper.lottery_amount == ""}'>--</c:if><c:if test='${nper.lottery_amount != ""}'>${nper.lottery_amount} ${coinTypeMap.get(nper.lottery_coin_type)}</c:if></td>
					<td><c:if test='${nper.prize_amount == ""}'>--</c:if><c:if test='${nper.prize_amount != ""}'>${nper.prize_amount} ${coinTypeMap.get(nper.prize_coin_type)}</c:if></td>
					<td>${nper.status_v}</td>
					<td>${nper.is_send_prize}</td>
					<td>${nper.create_time}</td>
					<td>${nper.start_time}</td>
					<td>${nper.draw_time}</td>
					<td>${nper.ball}</td>
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
<script type="text/javascript">
function doSendPrize(sid_user){
	var tr = $("tbody tr.selected");
	var status = tr.children('td').eq(7).text();
	var is_send_prize = tr.children('td').eq(8).text();
	if("已结束" == status){
		if("false" == is_send_prize){
			$("#sendPrize").click();
		}else{
			alertMsg.info("本期活动奖励已发放");
		}
	}else{
		alertMsg.info("本期活动未结束");
	}
}
</script>
