<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post"
	action="/buluo718admin/questionForAnswerList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="pageNum" value="${currentPage}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /><input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/questionForAnswerList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>提问人：<input type="text" name="keywords" value="${keywords}"
						size="60" /></td>
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
			<shiro:hasPermission name="buluo718admin/answerQuestion.html">
				<li><a class="edit"
					href="/buluo718admin/goQuestionJSP.html?url=ssadmin//answerQuestion&uid={sid_user}"
					height="300" width="800" target="dialog" rel="updateLink"><span>回复</span>
				</a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">序号</th>
				<th width="60">提问人UID</th>
				<th width="60">提问人</th>
				<th width="60">提问手机号码</th>
				<th width="60" orderField="fstatus"
					<c:if test='${param.orderField == "fstatus"}'> class="${param.orderDirection}"  </c:if>>状态</th>
				<th width="60" orderField="ftype"
					<c:if test='${param.orderField == "ftype"}'> class="${param.orderDirection}"  </c:if>>提问类型</th>
				<th width="100">提问内容</th>
				<th width="100">回复内容</th>
				<th width="60">回复管理员</th>
				<th width="60" orderField="fcreateTime"
					<c:if test='${param.orderField == "fcreateTime"}'> class="${param.orderDirection}"  </c:if>>创建时间
				</th>
				<th width="60" orderField="fSolveTime"
					<c:if test='${param.orderField == "fSolveTime"}'> class="${param.orderDirection}"  </c:if>>回复时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${questionList}" var="question" varStatus="num">
				<tr target="sid_user" rel="${question.fid}">
					<td>${num.index +1}</td>
					<td>${question.fuser.fid}</td>
					<td>${question.fuser.frealName}</td>
					<td>${question.fuser.ftelephone}</td>
					<td>${question.fstatus_s}</td>
					<td>${question.ftype_s}</td>
					<td>${question.fdesc}</td>
					<td>${question.fanswer}</td>
					<td>${question.fadmin.fname}</td>
					<td>${question.fcreateTime}</td>
					<td>${question.fSolveTime}</td>
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
