<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="/buluo718admin/lotteryLogList.html">
	<input type="hidden" name="activityId" value="${activityId}"> <input
		type="hidden" name="keywords" value="${keywords}" /><input
		type="hidden" name="userId" value="${userId}" /> <input type="hidden"
		name="pageNum" value="${currentPage}" /> <input type="hidden"
		name="numPerPage" value="${numPerPage}" /> <input type="hidden"
		name="orderField" value="${param.orderField}" /><input type="hidden"
		name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/lotteryLogList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>关键词：<input type="text" name="keywords" value="${keywords}"
						size="60" />[标题、关键词]</td>

				    <td>活动ID：<input type="text" name="activityId" value="${activityId}"
						 /></td>
						
					<td>用户ID：<input type="text" name="userId" value="${userId}"
						/></td>
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
			<%-- <shiro:hasPermission name="buluo718admin/addArticle.html">
				<li><a class="add"
					href="/buluo718admin/goArticleJSP.html?url=ssadmin//addArticle"
					height="500" width="900" target="dialog" rel="addArticle"><span>新增</span>
				</a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="buluo718admin/deleteArticle.html">
				<li><a class="delete"
					href="/buluo718admin/deleteArticle.html?uid={sid_user}" target="ajaxTodo"
					title="确定要删除吗?"><span>删除</span> </a></li>
			</shiro:hasPermission> --%>
	<%-- 		<shiro:hasPermission name="buluo718admin/updateActivity.html">
				<li><a class="edit"
					href="/buluo718admin/goActivityJSP.html?url=ssadmin//updateActivity&id={sid_user}"
					height="500" width="900" target="dialog" rel="updateActivity"><span>修改</span>
				</a></li>
			</shiro:hasPermission> --%>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">序号</th>
				<th width="60">中奖用户ID</th>
				<th width="60">中奖用户姓名</th>
				<th width="60" orderField="lotteryAwardsModel.factivityModel.name"
					<c:if test='${param.orderField == "name" }'> class="${param.orderDirection}"  </c:if>>活动名称</th>
				<th width="60" orderField="lotteryAwardsModel.factivityModel.content"
					<c:if test='${param.orderField == "content" }'> class="${param.orderDirection}"  </c:if>>活动内容</th>
				
				<th width="60">奖项</th>
				<th width="60">中奖金额</th>
				<th width="60">中奖时间</th> 

			</tr>
		</thead>
		<tbody>
			<c:forEach items="${lotteryLogList}" var="lotteryLogModel" varStatus="num">
				<tr target="sid_user" rel="${lotteryLogModel.id}">
					<td>${num.index +1}</td>
					<td>${lotteryLogModel.user.fid}</td>					
					<td>${lotteryLogModel.user.frealName}</td>
					<td>${lotteryLogModel.lotteryAwardsModel.factivityModel.name}</td>
					<td>${lotteryLogModel.lotteryAwardsModel.factivityModel.content}</td>
					
					
					<td>${lotteryLogModel.lotteryAwardsModel.awards_name}</td>
					<td>${lotteryLogModel.lotteryAwardsModel.fee_amount}${lotteryLogModel.lotteryAwardsModel.fvirtualcointype.fShortName}</td>
					<td>${lotteryLogModel.create_time}</td>
					
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
