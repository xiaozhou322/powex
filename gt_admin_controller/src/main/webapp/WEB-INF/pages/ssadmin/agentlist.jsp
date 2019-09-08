<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="/buluo718admin/agentlist.html">
 		<input type="hidden" name="fuid" value="${fuid}" />
		<input type="hidden" name="process" value="${fprocess}" />
		<input type="hidden" name="status" value="${fstatus}" />
		<input type="hidden" name="pageNum" value="${currentPage}" /> 
		<input type="hidden" name="numPerPage" value="${numPerPage}" /> 
</form>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/buluo718admin/agentlist.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>会员UID：<input type="text" name="fuid" value="${fuid}" size="10" /></td>
					
					<td>
					  账户状态： 
						<select type="combox" name="status">
								<c:forEach items="${statusMap}" var="status">
									<c:if test="${status.key == fstatus}">
										<option value="${status.key}" selected="true">${status.value}</option>
									</c:if>
									<c:if test="${status.key != fstatus}">
										<option value="${status.key}">${status.value}</option>
									</c:if>
								</c:forEach>
						</select>
					</td>
					<td>
					申请审核状态： 
						<select type="combox" name="process">
								<c:forEach items="${processMap}" var="process">
									<c:if test="${process.key == fprocess}">
										<option value="${process.key}" selected="true">${process.value}</option>
									</c:if>
									<c:if test="${process.key != fprocess}">
										<option value="${process.key}">${process.value}</option>
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
			<shiro:hasPermission name="buluo718admin/verifyagent.html">
			<li><a title="确定要审核通过?" target="selectedTodo" rel="ids"
				postType="select" href="/buluo718admin/verifyagent.html?ac=verify"
				class="edit"><span>审核通过</span> </a>
			</li>
			<li><a title="确定要拒绝通过?" target="selectedTodo" rel="ids"
				postType="select" href="/buluo718admin/verifyagent.html?ac=deny"
				class="edit"><span>拒绝通过审核</span> </a>
			</li>
			<li><a title="确定要禁用账户?" target="selectedTodo" rel="ids"
				postType="select" href="/buluo718admin/verifyagent.html?ac=disable"
				class="edit"><span>禁用账户</span> </a>
			</li>
			<li><a title="确定要解禁账户?" target="selectedTodo" rel="ids"
				postType="select" href="/buluo718admin/verifyagent.html?ac=enable"
				class="edit"><span>解禁账户</span> </a>
			</li>
			</shiro:hasPermission>	
		</ul>
	</div>
	
	
		<table class="table" width="100%" layoutH="138">
			<thead>
					<tr>
						<th width="22"><input type="checkbox" group="ids"
							class="checkboxCtrl">
						</th>
						<th width="20">ID</th>
						<th width="60">UID</th>	
						<th width="60">名称</th>					
						<th width="120">日期</th>
						<th width="60">账户状态</th>
						<th width="60">审核状态</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${agentlist}" var="v" varStatus="num">
						<tr>
							<td><input name="ids" value="${v.fid}"
							type="checkbox"></td>
							<td>${v.fid}</td>
							<td>${v.fuser.fid}</td>
							<td>${v.fname}</td>
							
							<td>${v.fcreatetime}</td>
							<td>
								<c:if test="${v.fstatus == 0}">禁用</c:if>
					              <c:if test="${v.fstatus == 1}">正常</c:if>
					        </td>
					        <td><c:if test="${v.process == 0}">待审核</c:if>
					              <c:if test="${v.process == 1}">审核通过</c:if>
					              <c:if test="${v.process == 2}">审核未通过</c:if>
					        </td>
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

<script>
	$('#process_btn').click(function(){
		var fids = [];
		$('.fids').each(function(){
			fids[fids.length] = $(this).val();
		})
		$('#fidstring').val(fids.join(","));
		$('#pagerForm')[0].submit();
	})
</script>
