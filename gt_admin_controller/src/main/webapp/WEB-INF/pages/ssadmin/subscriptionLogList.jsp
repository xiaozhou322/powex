<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post"
	action="/buluo718admin/subscriptionLogList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /><input
		type="hidden" name="ftype" value="${ftype}" />  <input
		type="hidden" name="parentId" value="${parentId}" /><input
		type="hidden" name="pageNum" value="1" /> <input type="hidden"
		name="numPerPage" value="${numPerPage}" /> <input type="hidden"
		name="orderField" value="${param.orderField}" /><input type="hidden"
		name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/subscriptionLogList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<input type="hidden" name="parentId" value="${parentId}" />
					<td>标题：<input type="text" name="keywords" value="${keywords}"
						size="60" />[会员信息]</td>
					<td>状态： <select type="combox" name="ftype">
							<c:forEach items="${typeMap}" var="type">
								<c:if test="${type.key == ftype}">
									<option value="${type.key}" selected="true">${type.value}</option>
								</c:if>
								<c:if test="${type.key != ftype}">
									<option value="${type.key}">${type.value}</option>
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
		    <%-- <li><a title="确实要指定这些记录的中签比例吗?" target="selectedTodo" rel="ids"
				postType="string" href="/buluo718admin/subFail.html"
				class="edit"><span>全部指定预设<font color="red">${requestScope.constant['subSuccessRate'] }份</font></span> </a>
			</li>
		    <li><a title="确实要操作这些记录吗?" target="selectedTodo" rel="ids"
				postType="string" href="/buluo718admin/subSuccess.html" 
				class="edit"><span>全部审核</span> </a>
			</li>
			<li><a title="确实要操作这些记录吗?" target="selectedTodo" rel="ids"
				postType="string" href="/buluo718admin/subSend.html"
				class="edit"><span>全部解冻</span> </a>
			</li> --%>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
			    <th width="22"><input type="checkbox" group="ids"
					class="checkboxCtrl">
				</th>
				<th width="60">解冻类型</th>
				<th width="60">解冻比例</th>
				<th width="60">状态</th>
				<th width="60">付款类型</th>
				<th width="60">会员登陆名</th>
				<th width="60">会员昵称</th>
				<th width="60">会员真实姓名</th>
				<th width="60">众筹数量</th>
				<th width="60">未解冻数量</th>
				<th width="60">众筹价格</th>
				<th width="60">总金额</th>
				<th width="60">创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${subscriptionLogList}" var="subscriptionLog"
				varStatus="num">
				<tr target="sid_user" rel="${subscriptionLog.fid}">
				   <td><input name="ids" value="${subscriptionLog.fid}"
						type="checkbox">
					</td>
					<td>${subscriptionLog.fsubscription.ffrozenType_s}</td>
					<td>${subscriptionLog.fsubscription.frate}</td>
					<td>${subscriptionLog.fstatus_s}</td>
					<td>${subscriptionLog.fsubscription.symbol}</td>
					<td>${subscriptionLog.fuser.floginName}</td>
					<td>${subscriptionLog.fuser.fnickName}</td>
					<td>${subscriptionLog.fuser.frealName}</td>
					<td>${subscriptionLog.fcount}</td>
					<td>${subscriptionLog.flastcount}</td>
					<td>${subscriptionLog.fprice}</td>
					<td>${subscriptionLog.ftotalCost}</td>
					<td>${subscriptionLog.fcreatetime}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="panelBar">
		<div class="pages">
			<span>总共: ${totalCount}条;预设中签份数：<font color="red">${a }</font>;中签总金额：<font color="red">${b }</font>;中签币总数量:<font color="red">${c }</font></span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${totalCount}"
			numPerPage="${numPerPage}" pageNumShown="5"
			currentPage="${currentPage}"></div>
	</div>
</div>
