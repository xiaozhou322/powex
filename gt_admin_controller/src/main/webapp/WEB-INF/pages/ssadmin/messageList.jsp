<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="/buluo718admin/messageList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="type" value="${ftype}" /><input type="hidden"
		name="pageNum" value="${currentPage}" /> <input type="hidden"
		name="numPerPage" value="${numPerPage}" /> <input type="hidden"
		name="orderField" value="${param.orderField}" /><input type="hidden"
		name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/messageList.html" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>会员信息：<input type="text" name="keywords"
						value="${keywords}" size="60" /></td>
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
                <li><a class="add"
					href="/buluo718admin/goMessageJSP.html?url=ssadmin//sendMessage"
					height="500" width="900" target="dialog" rel="sendMessage"><span>发送站内消息</span>
				</a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">序号</th>
				<th width="20">会员登陆名</th>
				<th width="20">会员昵称</th>
				<th width="20">会员真实姓名</th>
				<th width="20">状态</th>
				<th width="60">标题</th>
				<th width="60">内容</th>
				<th width="40">创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${messageList}" var="message" varStatus="num">
				<tr target="sid_user" rel="${message.fid}">
					<td>${num.index +1}</td>
					<td>${message.freceiver.floginName}</td>
					<td>${message.freceiver.fnickName}</td>
					<td>${message.freceiver.frealName}</td>
					<td>${message.fstatus_s}</td>
					<td>${message.ftitle}</td>
					<td>${message.fcontent}</td>
					<td>${message.fcreateTime}</td>
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
