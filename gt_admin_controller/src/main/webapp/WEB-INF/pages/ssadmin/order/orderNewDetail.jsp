<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<h2 class="contentTitle">订单详情信息</h2>
<style>
	.pageContent dl dd{
		width:200px;
		margin-left:40px;
	}
	
	.pageFormContent dt {
		    float: left;
		    width: 100px;
		    padding: 0 125px;
		    line-height: 21px;
</style>

<div class="pageContent">

		<div class="pageFormContent nowrap" layoutH="97">
		  <dl>
				<dt>订单ID</dt>
				<dd>
					<span>${order.id}</span>
				</dd>
			</dl>
			<dl>
				<dt>下单用户ID</dt>
				<dd>
					<span>${order.fuser.fid}</span>
				</dd>
			</dl>
			<dl>
				<dt>下单用户名称</dt>
				<dd>
					<span>${order.fuser.frealName}</span>
				</dd>
			</dl>
			<dl>
				<dt>下单用户登录名</dt>
				<dd>
					<span>${order.fuser.floginName}</span>
				</dd>
			</dl>
			<dl>
				<dt>广告类型</dt>
				<dd>
					<span>
					  <c:if test="${order.fotcAdvertisement.ad_type==1 }">出售</c:if>
					  <c:if test="${order.fotcAdvertisement.ad_type==2 }">求购</c:if>
					
					</span>
				</dd>
			</dl>
			
			<dl>
				<dt>广告发布者ID</dt>
				<dd>
					<span>${order.fotcAdvertisement.user.fid }</span>
				</dd>
			</dl>
			<dl>
				<dt>广告发布者名称</dt>
				<dd>
					<span>${order.fotcAdvertisement.user.frealName }</span>
				</dd>
			</dl>
			<dl>
				<dt>广告发布者登录名</dt>
				<dd>
					<span>${order.fotcAdvertisement.user.floginName }</span>
				</dd>
			</dl>
			<dl>
				<dt>币种类型</dt>
				<dd>
					<span>${order.fvirtualcointype.fShortName }</span>
				</dd>
			</dl>
			<dl>
				<dt>订单类型</dt>
				<dd>
					<span>
					 <c:if test="${order.orderType==1 }">买入</c:if>
					  <c:if test="${order.orderType==2 }">卖出</c:if>
					
					</span>
				</dd>
			</dl>
			<dl>
				<dt>单价</dt>
				<dd>
					<span>${order.unitPrice }</span>
				</dd>
			</dl>
			<dl>
				<dt>数量</dt>
				<dd>
					<span>${order.amount }</span>
				</dd>
			</dl>
			<dl>
				<dt>总价</dt>
				<dd>
					<span>${order.totalPrice }</span>
				</dd>
			</dl>
			<dl>
				<dt>订单状态</dt>
				<dd>
	              <span>
	              <c:if test="${order.orderStatus==101}">未接单</c:if>
	               <c:if test="${order.orderStatus==102}">待付款</c:if>
	               <c:if test="${order.orderStatus==103 }">已付款</c:if>
	               <c:if test="${order.orderStatus==104 }">已确认收款</c:if>
	               <c:if test="${order.orderStatus==105 }">异常订单</c:if>
	               <c:if test="${order.orderStatus==106 }">失败</c:if>
	               <c:if test="${order.orderStatus==107 }">成功</c:if>
	                            
	              </span>
				</dd>
			</dl>
			
			<dl>
				<dt>创建时间</dt>
				<dd>
                      <span>${order.createTime}</span>
				</dd>
			</dl>
			<dl>
				<dt>备注</dt>
				<dd>
                      <span>${order.remark}</span>
				</dd>
			</dl>
			<dl>
				<dt>修改时间：</dt>
				<dd>
					<span>${order.updateTime }</span>
				</dd>
			</dl>

		</div>

</div>

