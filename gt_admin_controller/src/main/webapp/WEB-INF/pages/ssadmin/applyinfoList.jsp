<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="/buluo718admin/applyinfoList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" />
		<input type="hidden" name="pageNum"
		value="${currentPage}" /> <input type="hidden" name="numPerPage"
		value="${numPerPage}" /> <input type="hidden" name="orderField"
		value="${param.orderField}" /> <input type="hidden"
		name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/applyinfoList.html" method="post">
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
						</div></li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="edit"
					href="/buluo718admin/auditApplyinfo.html?uid={sid_user}" target="ajaxTodo"
					title="确定要审核通过吗?"><span>审核通过</span> </a>
				</li>
			<li><a class="delete"
					href="/buluo718admin/goApplyJSP.html?url=ssadmin//rejectApplyinfo&uid={sid_user}"
					height="250" width="800" target="dialog" rel="rejectApplyinfo"><span>审核不通过</span> </a>
				</li>
			<li><a class="edit"
					href="/buluo718admin/userGradeList1.html?parentId={sid_user}"
					height="500" width="800" target="dialog" rel="userGradeList1"><span>查看推荐记录</span>
				</a>
			</li>	
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
		    	<th width="40">序号</th>
				<th width="40">会员UID</th>
				<th width="60">姓名</th>
				<th width="60">联系电话</th>
				<th width="60">申请类型</th>
				<th width="60">申请描述</th>
				<th width="60">状态</th>
				<th width="60">拒绝原因</th>
				<th width="60">申请时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${applyinfoList}" var="applyinfo" varStatus="num">
				<tr target="sid_user" rel="${applyinfo.fid}">
					<td>${num.index +1}</td>
					<td>${applyinfo.fuser.fid}</td>
					<td>${applyinfo.frealname}</td>
					<td>${applyinfo.fphone}</td>
					<td>${applyinfo.fgrade_s}</td>
					<td>${applyinfo.freason}</td>
					<td>${applyinfo.fstatus_s}</td>
					<td>${applyinfo.frejectReason}</td>
					<td>${applyinfo.fcreatetime}</td>
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
