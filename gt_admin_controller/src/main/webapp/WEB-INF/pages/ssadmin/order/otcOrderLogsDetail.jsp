<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<h2 class="contentTitle">订单日志详情信息</h2>
<style>
	.pageContent dl dd{
		width:250px;
		margin-left:20px;
	}
	
	.pageFormContent dt {
		    float: left;
		    width: 100px;
		    padding: 0 100px;
		    line-height: 21px;
</style>

<div class="pageContent">

		<div class="pageFormContent nowrap" layoutH="97">
		  <dl>
				<dt>日志ID</dt>
				<dd>
					<span>${otcOrderLogs.id}</span>
				</dd>
			</dl>
		  <dl>
				<dt>订单ID</dt>
				<dd>
					<span>${otcOrderLogs.orderId.id}</span>
				</dd>
			</dl>
			<dl>
				<dt>买家用户ID</dt>
				<dd>
					<span>${otcOrderLogs.buyUser.fid}</span>
				</dd>
			</dl>
			<dl>
				<dt>买家名称</dt>
				<dd>
					<span>${otcOrderLogs.buyUser.frealName}</span>
				</dd>
			</dl>
			<dl>
				<dt>买家登录名</dt>
				<dd>
					<span>${otcOrderLogs.buyUser.floginName}</span>
				</dd>
			</dl>
			<dl>
				<dt>卖家用户ID</dt>
				<dd>
					<span>${otcOrderLogs.sellUser.fid}</span>
				</dd>
			</dl>
			<dl>
				<dt>卖家名称</dt>
				<dd>
					<span>${otcOrderLogs.sellUser.frealName}</span>
				</dd>
			</dl>
			<dl>
				<dt>卖家登录名</dt>
				<dd>
					<span>${otcOrderLogs.sellUser.floginName}</span>
				</dd>
			</dl>
			<dl>
				<dt>订单类型</dt>
				<dd>
					<span>
					 	<c:if test="${otcOrderLogs.orderType==1 }">买入</c:if>
					  	<c:if test="${otcOrderLogs.orderType==2 }">卖出</c:if>
					</span>
				</dd>
			</dl>
			<dl>
				<dt>申诉原因</dt>
				<dd>
					<span>
	              	<c:forEach items="${appealReasonMap}" var="appealReason">
						<c:if test="${appealReason.key == otcOrderLogs.appealReason}">
							${appealReason.value}
						</c:if>
					</c:forEach>
					</span>
				</dd>
			</dl>
			<dl>
				<dt>申诉人</dt>
				<dd>
					<span>${otcOrderLogs.appealUser.frealName }</span>
				</dd>
			</dl>
			<dl>
				<dt>操作类型</dt>
				<dd>
					<span>
	              	<c:forEach items="${logsType}" var="logsType">
						<c:if test="${logsType.key == otcOrderLogs.type}">
							${logsType.value}
						</c:if>
					</c:forEach>
					</span>
				</dd>
			</dl>
			
			<dl>
				<dt>是否被投诉成功</dt>
				<dd>
                      <span>${otcOrderLogs.complainSucc==1 ? "是" : "否" }</span>
				</dd>
			</dl>
			
			<dl>
				<dt>创建时间</dt>
				<dd>
                      <span>${otcOrderLogs.createTime}</span>
				</dd>
			</dl>
		</div>

</div>

