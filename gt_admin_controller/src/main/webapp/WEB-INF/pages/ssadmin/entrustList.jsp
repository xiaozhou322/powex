<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="/buluo718admin/entrustList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /><c:if test="${isNotHave=='True'}"><input
		type="hidden" name="isNotHave" value="True" /></c:if><input
		type="hidden" name="logDate" value="${logDate}" /><input
		type="hidden" name="logDate1" value="${logDate1}" /><input
		type="hidden" name="price" value="${price}" /><input
		type="hidden" name="price1" value="${price1}" /><input
		type="hidden" name="ftype" value="${ftype}" /><input
		type="hidden" name="ftype1" value="${ftype1}" /><input
		type="hidden" name="status" value="${status}" /><input
		type="hidden" name="entype" value="${entype}" /> <input type="hidden"
		name="pageNum" value="${currentPage}" /> <input type="hidden"
		name="numPerPage" value="${numPerPage}" /> <input type="hidden"
		name="orderField" value="${param.orderField}" /><input type="hidden"
		name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/entrustList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>会员信息：<input type="text" name="keywords" value="${keywords}"
						size="10" /><input type="checkbox" name="isNotHave" value="True" <c:if test="${isNotHave=='True'}">checked</c:if>/>不包含</td>
					<td>法币： <select type="combox" name="ftype1">
							<c:forEach items="${typeMap1}" var="type">
								<c:if test="${type.key == ftype1}">
									<option value="${type.key}" selected="true">${type.value}</option>
								</c:if>
								<c:if test="${type.key != ftype1}">
									<option value="${type.key}">${type.value}</option>
								</c:if>
							</c:forEach>
					</select>
					</td>	
					<td>交易虚拟币： <select type="combox" name="ftype">
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
					<td>开始时间： <input type="text" name="logDate" class="date"
						readonly="true" value="${logDate }"  dateFmt="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>结束时间： <input type="text" name="logDate1" class="date"
						readonly="true" value="${logDate1 }"  dateFmt="yyyy-MM-dd HH:mm:ss"/>
					</td>
				</tr>
				<tr>
					
					<td>开始价格：<input type="text" name="price" value="${price}"
						size="10" /></td>
					<td>结束价格：<input type="text" name="price1" value="${price1}"
						size="10" /></td>	
					<td>状态： <select type="combox" name="status">
							<c:forEach items="${statusMap}" var="s">
								<c:if test="${s.key == status}">
									<option value="${s.key}" selected="true">${s.value}</option>
								</c:if>
								<c:if test="${s.key != status}">
									<option value="${s.key}">${s.value}</option>
								</c:if>
							</c:forEach>
					</select>
					</td>
					<td>类型： <select type="combox" name="entype">
							<c:forEach items="${entypeMap}" var="t">
								<c:if test="${t.key == entype}">
									<option value="${t.key}" selected="true">${t.value}</option>
								</c:if>
								<c:if test="${t.key != entype}">
									<option value="${t.key}">${t.value}</option>
								</c:if>
							</c:forEach>
					</select>
					
					</td>	
					<td><button type="submit">查询</button></td>
				</tr>
			</table>
			
		</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
		<shiro:hasPermission name="buluo718admin/cancelEntrust.html">
			<li><a title="确定要取消吗?" target="selectedTodo" rel="ids"
				postType="delete" href="/buluo718admin/cancelEntrust.html"
				class="edit"><span>取消</span> </a>
			</li>
		</shiro:hasPermission>			
		</ul>
	</div>
	<table class="table" width="150%" layoutH="138">
		<thead>
			<tr>
			    <th width="22"><input type="checkbox" group="ids"
					class="checkboxCtrl">
				</th>
				<th width="20">订单ID</th>
				<th width="60">用户UID</th>
				<th width="60" orderField="fuser.floginName"
					<c:if test='${param.orderField == "fuser.floginName"}'> class="${param.orderDirection}"  </c:if>>登陆名</th>
				<th width="60" orderField="fuser.fnickName"
					<c:if test='${param.orderField == "fuser.fnickName"}'> class="${param.orderDirection}"  </c:if>>会员昵称</th>
				<th width="60" orderField="fuser.frealName"
					<c:if test='${param.orderField == "fuser.frealName"}'> class="${param.orderDirection}"  </c:if>>会员真实姓名</th>
				<th width="60">法币</th>
				<th width="60">交易虚拟币</th>
				<th width="60" orderField="fentrustType"
					<c:if test='${param.orderField == "fentrustType"}'> class="${param.orderDirection}"  </c:if>>交易类型</th>
				<th width="60" orderField="fstatus"
					<c:if test='${param.orderField == "fstatus"}'> class="${param.orderDirection}"  </c:if>>状态</th>
				<th width="60" orderField="fprize"
					<c:if test='${param.orderField == "fprize"}'> class="${param.orderDirection}"  </c:if>>单价</th>
				<th width="60" orderField="fcount"
					<c:if test='${param.orderField == "fcount"}'> class="${param.orderDirection}"  </c:if>>数量</th>
				<th width="60" orderField="fleftCount"
					<c:if test='${param.orderField == "fleftCount"}'> class="${param.orderDirection}"  </c:if>>未成交数量</th>
				<th width="60">已成交数量</th>	
				<th width="60" orderField="famount"
					<c:if test='${param.orderField == "famount"}'> class="${param.orderDirection}"  </c:if>>总金额</th>
				<th width="60" orderField="fsuccessAmount"
					<c:if test='${param.orderField == "fsuccessAmount"}'> class="${param.orderDirection}"  </c:if>>成交总金额</th>
				<th width="60">总手续费</th>
				<th width="60">剩余手续费</th>	
				<th width="100" orderField="flastUpdatTime"
					<c:if test='${param.orderField == "flastUpdatTime"}'> class="${param.orderDirection}"  </c:if>>修改时间</th>
				<th width="100" orderField="fcreateTime"
					<c:if test='${param.orderField == "fcreateTime"}'> class="${param.orderDirection}"  </c:if>>创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${entrustList}" var="entrust" varStatus="num">
				<tr target="sid_user" rel="${entrust.fid}">
				    <td><input name="ids" value="${entrust.fid}"
						type="checkbox">
					</td>
					<td>${entrust.fid}</td>
					<td>${entrust.fuser.fid}</td>
					<td>${entrust.fuser.floginName}</td>
					<td>${entrust.fuser.fnickName}</td>
					<td>${entrust.fuser.frealName}</td>
					<td>${entrust.ftrademapping.fvirtualcointypeByFvirtualcointype1.fname}</td>
					<td>${entrust.ftrademapping.fvirtualcointypeByFvirtualcointype2.fname}</td>
					<td>${entrust.fentrustType_s}</td>
					<td>${entrust.fstatus_s}</td>
					<td><fmt:formatNumber value="${entrust.fprize}" pattern="##.########" maxIntegerDigits="16" maxFractionDigits="10"/></td>
					<td><fmt:formatNumber value="${entrust.fcount}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="6"/></td>
					<td><fmt:formatNumber value="${entrust.fleftCount}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="6"/></td>
					<td><fmt:formatNumber value="${entrust.fcount - entrust.fleftCount}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="6"/></td>
					<td><fmt:formatNumber value="${entrust.famount}" pattern="##.######" maxIntegerDigits="16" maxFractionDigits="10"/></td>
					<td><fmt:formatNumber value="${entrust.fsuccessAmount}" pattern="##.######" maxIntegerDigits="16" maxFractionDigits="10"/></td>
					<td><fmt:formatNumber value="${entrust.ffees}" pattern="##.######" maxIntegerDigits="16" maxFractionDigits="10"/></td>
					<td><fmt:formatNumber value="${entrust.fleftfees}" pattern="##.######" maxIntegerDigits="16" maxFractionDigits="10"/></td>
					<td>${entrust.flastUpdatTime}</td>
					<td>${entrust.fcreateTime}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="panelBar">
		<div class="pages">
			<span>总共: ${totalCount}条；
			 查询区间总成交数量：<font color="red"><fmt:formatNumber value="${totalAmt }" pattern="##.######" maxIntegerDigits="16" maxFractionDigits="10"/></font>; 
			 查询区间总成交金额：<font color="red"><fmt:formatNumber value="${totalQty }" pattern="##.######" maxIntegerDigits="16" maxFractionDigits="10"/></font>;
			  查询区间总手续费：<font color="red"><fmt:formatNumber value="${fees }" pattern="##.######" maxIntegerDigits="16" maxFractionDigits="10"/></font>;</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${totalCount}"
			numPerPage="${numPerPage}" pageNumShown="5"
			currentPage="${currentPage}"></div>
	</div>
</div>
