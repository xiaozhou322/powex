<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>

<!doctype html>
<html>
<head>
<%@ include file="../comm/basepath.jsp"%>
<jsp:include page="../comm/link.inc.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${oss_url}/static/front/css/index/main.css" />
<link href="${oss_url}/static/front2018/css/otc.css?v=12" rel="stylesheet" type="text/css" />

<style type="text/css">
.buyBt {
    width: 130px;
    height: 32px;
    background: #488bef;
    border-radius: 18px;
    text-align: center;
    line-height: 32px;
    margin-top: 20px;
    color: #fff;
    font-size: 12px;
    display: inline-block;
    cursor: pointer;
    margin-left: 30px;
}

.details {
			width: 700px;
			height: auto;
			background: #fff;
			border-radius: 10px;
			box-shadow: 0 0 6px #999;
			position: absolute;
			left: 50%;
			top: 50%;
			font-size: 14px;
			margin: -279px 0 0 -320px;
		}
		
		.head {
			font-family: "微软雅黑";
			text-align: center;
			font-size: 20px;
			width: 100%;
			margin-top: 40px;
			;
		}
		
		.details .orderD {
			margin:10px 0 20px 0;
			width:100%;
			height:100%;
		}
		
		.details input {
			box-shadow: 0 0 1px #999;
			border: 0px;
			width: 220px;
			height: 25px;
			border-radius: 5px;
		}
		
		.details tr {
			line-height: 30px;
		}
		
		
		.close {
			position: absolute;
			right: 30px;
			top: 10px;
			color: #5a5a5a;
			font-size: 20px;
			cursor: pointer;
		}
		
		
		.buyBt.disabled {
			background: #c6c6c8;
			color: #fff;
		}
		
		.buyBt:hover {
			opacity: 0.8;
		}
		
		tr td input{
			border:1px solid grey;
			
		}
		
		tr td{
			padding: 10px 0!important;
		    height: 40px;
		    line-height: 30px;
		    text-align: center;
		    width: 1000px;
		}
.orderliBox{    
	background: #fff;
    width: 1300px;
    margin: 50px auto;
    box-shadow: 0px 2px 9px 0px rgba(70, 90, 111, 0.14);}	
.orderliBox .ftype{    color: #666;
    border-color: #ccc;
    height: 30px;
    border-radius: 4px;}
 .orderliBox .orderNav{    
 	border-top: 1px solid #F6F6F6;
    padding: 12px 16px;
    margin-top: -1px;}
    .myRecord_list{padding: 10px 0px;}
    
   .orderliBox .buyMarket .buyMarTh li,.orderliBox .buyMarket .buyMarTd li{    width: 9.5%;
    float: left;
    height: 64px;
    display: block;}
	.Btnorder{    padding: 4px 8px;    display: initial;
    border-radius: 6px;
    color: #fff;
    margin-left: 4px;
    font-size: 14px;
    cursor: pointer;}	
    #t_con{clear: both;
    overflow: hidden;}
</style>
</head>
<body>	
<%@include file="../comm/newotc_header.jsp" %>

<section  class="orderliBox">
        <div class="buy_list">
		  <a href="/order/orderList.html?status=0" class="active">
            <spring:message code="order.unfinish" />                   
          </a>       
          <a href="/order/orderPageList.html?status=1">
            <spring:message code="order.history" />
          </a>
         </div>
        
        
        
        
        
    <form onsubmit="return navTabSearch(this);" action="/order/orderList.html" method="post" class="orderNav">
		<table  style="height:75px;margin-left:50px;">
				<tr>
					<td>
						&nbsp;&nbsp;&nbsp;&nbsp;订单ID：&nbsp;<input type="text" name="id" id="id" class="ftype" value="${id}"/>
					</td>
<!-- 					<td>推荐人UID：<input type="text" name="troUid" value="${troUid}" -->
<!-- 						size="10" /> -->
<!-- 					</td> -->
					<%-- <td>&nbsp;&nbsp;开始时间： <input type="date" name="startDate" class="date"
						 value="${startDate }"  dateFmt="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>&nbsp;&nbsp;结束时间： <input type="date" name="endDate" class="date"
						 value="${endDate }"  dateFmt="yyyy-MM-dd HH:mm:ss"/>
					</td> --%>
					 <td>&nbsp;&nbsp;&nbsp;&nbsp;备注号：&nbsp;<input type="text" name="remark" id="remark"  class="ftype" value="${remark}"/>
					</td>
				</tr>
				<tr>
					<td>&nbsp;订单状态： <select type="combox" name="orderStatus" id="orderStatus" class="ftype"  style="width:155px;">
							<c:forEach items="${orderStatusList}" var="sta">
								<c:if test="${sta.key == orderStatus}">
									<option value="${sta.key}" selected="true">${sta.value}</option>
								</c:if>
								<c:if test="${sta.key != orderStatus}">
									<option value="${sta.key}">${sta.value}</option>
								</c:if>
							</c:forEach>
						</select> 
					</td>
					
					<td>&nbsp;订单类型：&nbsp;<select type="combox" name="ad_type" id="ad_type" class="ftype"  style="width:155px;">
                             <c:forEach items="${orderType}" var="order">
								<c:if test="${order.key == ad_type}">
									<option value="${order.key}" selected="true">${order.value}</option>
								</c:if>
								<c:if test="${order.key != ad_type}">
									<option value="${order.key}">${order.value}</option>
								</c:if>
							</c:forEach>
						</select> 
					</td>
					
					<td>&nbsp; <spring:message code="m.security.selectcoin" />：  
                     <select onchange="go()" type="combox" style="width:155px;"  id="amount_type" class="ftype" type="combox" name="amount_type">
                     <option value=""><spring:message code="currency.all"/></option>
						<c:forEach items="${amountTypeMap}" var="t">
						<option value="${t.fid}" 
						<c:if test="${t.fid==amount_type}">
								selected="selected"
						</c:if>										
							>${t.fname}</option>
						</c:forEach>
					</select>
                  </td>
                  <td>
                  	<div class="subBar" >
						<button type="buttion" class="buyBt" onclick="go()" style="width:100px;margin:0;">查询</button>
					</div>
                  </td>
				</tr>
				
			</table>
	</form>
            <div class="buyMarket">
            <ul class="buyMarTh">
            	<li><spring:message code="order.orderid" /></li>
            	<li>下单人</li>
            	<li><spring:message code="order.type" /></li>
            	<li><spring:message code="market.currencytype" /></li>
            	<li><spring:message code="order.amount" /></li>
            	<li>单价/总价￥</li>
            	<li><spring:message code="order.status" /></li>
            	<li><spring:message code="order.newtime" /></li>
            	<li>备注号</li>
            	<li><spring:message code="操作" /></li>
            </ul>
            	<div id="t_con"></div>
              <div id="page" class="page">${pagin}</div>
            </div>
            <input type="hidden" value="${currentPage}" id="currentPage">
</section>




    
<input type="hidden" id="symbol" value="${ftrademapping.fid }">
<jsp:include page="../comm/footer.jsp"></jsp:include>
<script type="text/javascript" src="${oss_url}/static/front2018/js/trade/trade.js?v=2"></script>
<script type="text/javascript">
$(function(){
	go();
	
});
function go(){
	var status=${status};
	var id=$("#id").val();
	var orderStatus=$("#orderStatus").val();
	var ad_type=$("#ad_type").val();
	var remark=$("#remark").val();
	var amount_type=$("#amount_type").val();
		var url="/order/orderList1.html?random=" + Math.round(Math.random() * 1000);
	$.ajax({
       type: 'post',
       url: url,
       async: false,
        data:{
        	'id':id,
        	'orderStatus':orderStatus,
        	'ad_type':ad_type,
        	'remark':remark,
        	'status':status,
        	'amount_type':amount_type
        },
        dataType: "json",
        success: function(result) {
        	$("#t_con").empty();
        	var html ="";
        	pageIndex=result.pageIndex;//全局变量赋值
     		totalPage=result.totalPage;
        if(result.success){
              $.each(result.orderList,function(index,vul){
					html += '<ul class="buyMarTd">'; 
					html += '  <li> ' + vul.id + '</li>'; 
					html += '  <li> ' + vul.user_name + '</span>'; 
					html += '  <li>' + vul.order_type + '</span>';
					html += '  <li>' + vul.coin_type + '</span>';
					html += '  <li>' + vul.amount + '</span>';
					html += '   <li>' + vul.unit_price +' / '+ vul.total_price + '</span>';
					if(vul.order_status == 105 || vul.order_status== 106){
						 html += '  <li style="color:red;">' + vul.order_statusDesc+ '</span>';
					}else if(vul.order_status == 107){
						 html += '  <li style="color:green;">' + vul.order_statusDesc+ '</span>';
					}else{
						html += '  <li style="color:blue;">' + vul.order_statusDesc+ '</span>';
					}
					html += '  <li style="line-height: 17px;margin-top: 14px;height: 35px;">' + vul.update_time+ '</span>';
                    html += '  <li>' + vul.remark+ '</span>';
                   	if(vul.isAgent && vul.order_status == 101) {
                   		html += '<li><span class="Btnorder" style="background: #76C80E;" onclick="receiveOrder('+vul.id+')">接单</span><span class="Btnorder" style="background: red;" onclick="refuse('+vul.id+')">拒接</span></li>';                   		
					} else {
                   		html += '<li><a href="/order/orderDetail.html?orderId='+ vul.id +'" style="color:#2269D4;cursor: pointer;">处理订单</a></li>';
                   	}
                   	html += '</ul>'; 	
				});
        }
		$("#t_con").html(html);
			},
		});
//	setTimeout("go()", "5000");
	}
	
	
//接单
function receiveOrder(orderId){
	var param = {'orderId':orderId};
	 util.layerConfirm("你确定接单么？", function () {

	jQuery.post('/order/receiveOrder.html', param, function (data) {
	  
    	if (data.code == -1) {
			util.layerAlert("", data.msg, 2);
			
		} else {
			util.layerAlert("", data.msg, 1);
		}
    }, "json");
	 });
}
//拒接
function refuse(orderId)
{
	 util.layerConfirm("你确定拒绝接单？", function () {
	var param = {'orderId':orderId};
	jQuery.post('/order/failedOrder.html', param, function (data) {
	  
    	if (data.code == -1) {
			util.layerAlert("", data.msg, 2);
			
		} else {
			util.layerAlert("", data.msg, 1);
		}
    }, "json");
	 });
}	
</script>
</body>
</html>

