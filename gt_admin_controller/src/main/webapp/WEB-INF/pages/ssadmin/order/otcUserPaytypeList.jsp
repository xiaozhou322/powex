<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="/buluo718admin/otcUserPaytypeList.html">
 	<input type="hidden" name="pageNum" value="${currentPage}" /> 
 	<input type="hidden" name="numPerPage" value="${numPerPage}" /> 
 	<input type="hidden" name="orderField" value="${param.orderField}" />
 	<input type="hidden" name="orderDirection" value="${param.orderDirection}" />
	<input type="hidden" name="orderUserId" value="${userId}" />
	<input type="hidden" name="payType" value="${payType}" />
</form>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/otcUserPaytypeList.html" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>用户ID：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="userId" value="${userId}"
						size="20" /></td>
					</td>
					<td>&nbsp;&nbsp;支付方式： <select type="combox" name="payType" style="width:152px;">
                             <option value="-10">所有</option>
                             <c:forEach items="${payTypeMap}" var="paytype">
								<c:if test="${paytype.key == payType}">
									<option value="${paytype.key}" selected="true">${paytype.value}</option>
								</c:if>
								<c:if test="${paytype.key != payType}">
									<option value="${paytype.key}">${paytype.value}</option>
								</c:if>
							</c:forEach>
					</select></td>
					<td><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">查询</button>
							</div>
						</div></td>
				</tr>
				
			</table>
		</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			  <shiro:hasPermission name="buluo718admin/otcUserPaytypeList.html">
				<li><a class="edit"
					href="/buluo718admin/otcUserPaytypeDetail.html?id={sid_paytype}"
					height="600" width="800" target="dialog" rel="paytypeList"><span>查看详情</span>
				</a></li>
			</shiro:hasPermission>
		</ul>
		
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th orderField="id"
					<c:if test='${param.orderField == "id" }'> class="${param.orderDirection}"  </c:if>>ID</th>
				<th orderField="fuser.fid"
					<c:if test='${param.orderField == "fuser.fid" }'> class="${param.orderDirection}"  </c:if>>用户ID</th>
				<th>用户登录名</th>
				<th>用户姓名</th>
				<th orderField="payType"
					<c:if test='${param.orderField == "payType" }'> class="${param.orderDirection}"  </c:if>>支付方式</th>
				<th>真实姓名/昵称</th>
				<th>支付账号</th>
				<th>二维码</th>
				<th>所属银行</th>
				<th>银行支行</th>
				<th>开户地址</th>
				<th>是否启用</th>
				<th orderField="createTime"
					<c:if test='${param.orderField == "createTime" }'> class="${param.orderDirection}"  </c:if>>创建时间</th>
				<th orderField="updateTime"
					<c:if test='${param.orderField == "updateTime" }'> class="${param.orderDirection}"  </c:if>>更新时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${paytypeList}" var="v" varStatus="vs">
				<tr target="sid_paytype" rel="${v.id}">
				    <td>${v.id }</td>
				    <td>${v.fuser.fid }</td>
				    <td>${v.fuser.floginName }</td>
					<td>${v.fuser.frealName }</td>
					<td>${v.paytypename }</td>
					<td>${v.realName }</td>
					<td>${v.paymentAccount}</td>
					<td>${v.qrCode }</td>
					<td>${v.bank }</td>
					<td>${v.bankBranch }</td>
					<td>${v.remark }</td>
					<td>${v.status == 1 ? "启用" : "禁用" }</td>
					<td><fmt:formatDate value="${v.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td><fmt:formatDate value="${v.updateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
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
