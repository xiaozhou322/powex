<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<form id="pagerForm" method="post"
	action="/buluo718admin/orderNewList.html">
	 <input
		type="hidden" name="keywords" value="${title}" /> <input type="hidden" name="pageNum"
		value="${currentPage}" /> <input type="hidden" name="regfrom" value="${regfrom}" />
		<input type="hidden" name="numPerPage"
		value="${numPerPage}" /> <input type="hidden" name="orderField"
		value="${param.orderField}" /><input type="hidden"
		name="orderDirection" value="${param.orderDirection}" />
		
		<input type="hidden"
		name="id" value="${id}" />
		<input type="hidden"
		name="startDate" value="${startDate}" />
		<input type="hidden"
		name="endDate" value="${endDate}" />
		<input type="hidden"
		name="orderUserId" value="${orderUserId}" />
		<input type="hidden"
		name="adUserId" value="${adUserId}" />
		<input type="hidden"
		name="status" value="${status}" />
		<input type="hidden"
		name="ad_type" value="${ad_type}" />
</form>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/orderNewList.html" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>订单ID：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="id" value="${id}"
						size="20" /></td>
					</td>
					<td>&nbsp;&nbsp;开始时间： <input type="text" name="startDate" class="date"
						readonly="true" value="${startDate }"  dateFmt="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>&nbsp;&nbsp;结束时间： <input type="text" name="endDate" class="date"
						readonly="true" value="${endDate }"  dateFmt="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>下单用户ID：&nbsp;<input type="text" name="orderUserId" value="${orderUserId}"
						size="20" />
					</td>
					<td></td>
				</tr>
				<tr>
                    <td>广告发布者ID：<input type="text" name="adUserId" value="${adUserId}"
						size="20" />
					</td>
									
					<td>&nbsp;&nbsp;订单状态： <select type="combox" name="status" style="width:152px;">
							<c:forEach items="${orderStatus}" var="sta">
								<c:if test="${sta.key == status}">
									<option value="${sta.key}" selected="true">${sta.value}</option>
								</c:if>
								<c:if test="${sta.key != status}">
									<option value="${sta.key}">${sta.value}</option>
								</c:if>
							</c:forEach>
					</select></td>
					
					<td>&nbsp;&nbsp;广告类型： <select type="combox" name="ad_type" style="width:152px;">
                             <c:forEach items="${orderType}" var="order">
								<c:if test="${order.key == ad_type}">
									<option value="${order.key}" selected="true">${order.value}</option>
								</c:if>
								<c:if test="${order.key != ad_type}">
									<option value="${order.key}">${order.value}</option>
								</c:if>
							</c:forEach>
					</select></td>
					<td>所属项目方ID：<input type="text" name="regfrom" value="${regfrom}"
						size="20" /></td>
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
			  <shiro:hasPermission name="buluo718admin/entrustList.html">
				 <li> <a class="edit"
					href="/buluo718admin/updateNewOrderStatus.html?orderId={sid_user}"
					target="ajaxTodo" title="确定要取消订单吗?"><span>取消订单</span> </a>
				</li>
				<li><a class="edit"
					href="/buluo718admin/orderNewDetail.html?url=ssadmin/order/orderNewDetail&orderId={sid_user}"
					height="500" width="700" target="dialog" rel="orderDetail"><span>订单详情</span>
				</a></li>
			</shiro:hasPermission>
		</ul>
		<ul></ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th orderField="id"
					<c:if test='${param.orderField == "id" }'> class="${param.orderDirection}"  </c:if>>订单ID</th>
				<th orderField="fuser.fid"
					<c:if test='${param.orderField == "fuser.fid" }'> class="${param.orderDirection}"  </c:if>>下单用户ID</th>
				<th>下单用户登录名</th>
				<th>下单用户姓名</th>
				<th orderField="fotcAdvertisement.ad_type"
					<c:if test='${param.orderField == "fotcAdvertisement.ad_type" }'> class="${param.orderDirection}"  </c:if>>广告类型</th>
				<th orderField="fotcAdvertisement.id"
					<c:if test='${param.orderField == "fotcAdvertisement.id" }'> class="${param.orderDirection}"  </c:if>>广告id</th>
				<th>发布者id</th>
				<th orderField="fvirtualcointype.fid"
					<c:if test='${param.orderField == "fvirtualcointype.fid" }'> class="${param.orderDirection}"  </c:if>>币种类型</th>
				<th orderField="orderType"
					<c:if test='${param.orderField == "orderType" }'> class="${param.orderDirection}"  </c:if>>订单类型</th>
				<th orderField="unitPrice"
					<c:if test='${param.orderField == "unitPrice" }'> class="${param.orderDirection}"  </c:if>>单价</th>
				<th orderField="amount"
					<c:if test='${param.orderField == "amount" }'> class="${param.orderDirection}"  </c:if>>数量</th>
				<th>总价</th>
				<th orderField="orderStatus"
					<c:if test='${param.orderField == "orderStatus" }'> class="${param.orderDirection}"  </c:if>>订单状态</th>
				<th orderField="createTime"
					<c:if test='${param.orderField == "createTime" }'> class="${param.orderDirection}"  </c:if>>创建时间</th>
				<th orderField="updateTime"
					<c:if test='${param.orderField == "updateTime" }'> class="${param.orderDirection}"  </c:if>>更新时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${orderList}" var="v" varStatus="vs">
				<tr target="sid_user" rel="${v.id}">
				    <td>${v.id }</td>
				    <td>${v.fuser.fid }</td>
				    <td>${v.fuser.floginName }(${v.fuser.fisorganization==false ? "普通用户" : "机构商" })</td>
					<td>${v.fuser.frealName }</td>
					<td>${v.fotcAdvertisement.ad_type == 1 ? "出售" : "求购" }</td>
					<td>${v.fotcAdvertisement.id }</td>
					<td>${v.fotcAdvertisement.user.fid}</td>
					<td>${v.fvirtualcointype.fname }</td>
					<td>${v.orderType==1 ? "买入" : "卖出" }</td>
					<td>${v.unitPrice }</td>
					<td>${v.amount }</td>
					<td>${v.totalPrice }</td>
					<td>
						
                     <c:if test="${v.orderStatus==101}">未接单</c:if>
	               <c:if test="${v.orderStatus==102}">待付款</c:if>
	               <c:if test="${v.orderStatus==103 }">已付款</c:if>
	               <c:if test="${v.orderStatus==104 }">已确认收款</c:if>
	               <c:if test="${v.orderStatus==105 }">异常订单</c:if>
	               <c:if test="${v.orderStatus==106 }">失败</c:if>
	               <c:if test="${v.orderStatus==107 }">成功</c:if>
	                 </td>
					<td><fmt:formatDate value="${v.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td><fmt:formatDate value="${v.updateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="panelBar">
		<div class="pages">
			<span>总共: ${totalCount}条，总金额：${totalAmount}USDT（${totalPrice}CNY）</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${totalCount}"
			numPerPage="${numPerPage}" pageNumShown="5"
			currentPage="${currentPage}"></div>
	</div>
</div>
