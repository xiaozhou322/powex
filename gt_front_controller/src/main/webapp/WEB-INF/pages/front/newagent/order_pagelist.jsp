<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>

<!doctype html>
<html>
<head>
<%@ include file="../comm/basepath.jsp"%>
<jsp:include page="../comm/link.inc.jsp"></jsp:include>
<link href="${oss_url}/static/front2018/css/usdtTrade.css?v=12" rel="stylesheet" type="text/css" />
<link href="${oss_url}/static/front2018/css/otc.css?v=12" rel="stylesheet" type="text/css" />

<style type="text/css">
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
	margin: -279px 0 0 -320px;}
.head {
	font-family: "微软雅黑";
	text-align: center;
	font-size: 20px;
	width: 100%;
	margin-top: 40px;}
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
    
   .orderliBox .buyMarket .buyMarTh li,.orderliBox .buyMarket .buyMarTd li{width: 11.2%;line-height: 14px;}
</style>
</head>
<body>	
<%@include file="../comm/newotc_header.jsp" %>

<section class="orderliBox">   
        <div class="buy_list">
		  <a href="/order/orderList.html?status=0" >
            <spring:message code="order.unfinish" />                   
          </a>       
          <a href="/order/orderPageList.html?status=1" class="active">
            <spring:message code="order.history" />
          </a>
        </div>
    <form onsubmit="return navTabSearch(this);" action="/order/orderPageList.html" method="post" class="orderNav">
		<table>
				<tr>
					<td>
						&nbsp;&nbsp;&nbsp;&nbsp;订单ID：<input type="text" class="ftype" id="id" name="id" value="${id}"/>
					</td>
					 <td>&nbsp;&nbsp;&nbsp;&nbsp;备注号：&nbsp;<input type="text" class="ftype" id="remark" name="remark" value="${remark}"/>
					</td>
					<td>
						开始时间：<input class="Wdate ftype" type="text" id="startDate" name="startDate" value="${startDate }" onclick="WdatePicker({isShowClear:false,readOnly:true})"/>
					</td>
					<td>
                    	结束时间：<input class="Wdate ftype" type="text" id="endDate" name="endDate" value="${endDate }" onclick="WdatePicker({isShowClear:false,readOnly:true})"/>
					</td>
				</tr>
				<tr>
				<td>&nbsp;订单状态：<select type="combox" id="orderStatus" name="orderStatus" style="width:155px;" class="ftype">
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
					<td>&nbsp;广告类型：&nbsp;<select type="combox" id="ad_type" name="ad_type" style="width:155px;" class="ftype">

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
                     <select  type="combox" style="width:155px;"  id="amount_type" class="ftype" type="combox" name="amount_type">
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
						<button type="submit" class="buyBt" style="width:80px;margin-top:0;">查询</button>
						<button type="button" class="buyBt" style="width:80px;margin-left: 20px;margin-top:0;" onclick="exportOtcOrder()">导出</button>
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
                <li style="width: 90px;"><spring:message code="order.status" /></li>
                <li><spring:message code="order.newtime" /></li>
                <li>操作</li>
            </ul>     
        		<c:forEach items="${FotcorderList}" var="vul">
        		<ul class="buyMarTd">
            		<li>${vul.id}</li>
            		<li>${vul.fuser.frealName}</li>
            		<li>
	            		<c:choose>
							<c:when test="${isMerchant}">
							   ${vul.orderType==1?"出售":"求购 "}
							</c:when>
							<c:otherwise>
							 ${vul.orderType==1?"买入":"卖出"}
							</c:otherwise>
						 </c:choose>
					 </li>
            		<li>${ vul.fvirtualcointype.fShortName}</li>
            		<li>${vul.amount }</li>
            		<li>${ vul.unitPrice } / ${ vul.totalPrice}</li>
            		<li style="width: 90px;">
           			 <c:choose>
				    	<c:when test="${vul.orderStatus==105 || vul.orderStatus==106}">
				    		<span style="color:red;">${ vul.orderStatusDesc}</span>
				    	</c:when>
				    	<c:when test="${vul.orderStatus==107}">
				    		<span style="color:green;">${ vul.orderStatusDesc}</span>
				    	</c:when>
				    	<c:otherwise>
				    		<span style="color:blue;">${ vul.orderStatusDesc}</span>
				    	</c:otherwise>
					  </c:choose> 
            		</li>
            		<li><fmt:formatDate value="${ vul.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></li>
            		<li><span onclick="orderDetails(${  vul.id})" style="color:#2269D4;cursor: pointer;">订单详情</span></li>
           		</ul>
				</c:forEach>
				<div id="page" class="page">${pagin}</div>
            <input type="hidden" value="${currentPage}" id="currentPage">
            <br><br>
        </div>
    <div class="warpForm">
	    <div class="details" id="details" style="display: none;">
			<p class="head">订单详情</p>
			<table class="orderD">
				<tbody>
					<tr>
						<td>
							<label>订单号:&nbsp;</label>
							<label id="orderId1" class="dt">订单号:&nbsp;</label>
						</td>
						<td>
							<label>备注:&nbsp;</label>
							<label id="remark1" class="dt">订单号:&nbsp;</label>
						</td>
					</tr>
	                   <tr>
						<td>
							<label >订单类型:&nbsp;</label>
							<label id="adType1" class="dt">订单号:&nbsp;</label>
						</td>
						<td>
							<label>广告商ID:&nbsp;</label>
							<label id="adUser" class="dt">订单号:&nbsp;</label>
						</td>
					</tr>
					<tr>
						<td>
							<label>币种类型:&nbsp;</label>
							<label id="coinType1" class="dt">订单号:&nbsp;</label>
						</td>
						<td>
							<label>订单状态:&nbsp;</label>
							<label id="orderStatus1" class="dt">订单号:&nbsp;</label>
						</td>
					</tr>
					<tr>
						<td>
							<label>交易数量:&nbsp;</label>
							<label id="mount1" class="dt">订单号:&nbsp;</label>
						</td>
						<td>
							<label>交易单价:&nbsp;</label>
							<label id="price1" class="dt">订单号:&nbsp;</label>
						</td>
					</tr>
					<tr>
						<td>
							<label>交易总额:&nbsp;</label>
							<label id="count1" class="dt">订单号:&nbsp;</label>
						</td>
						<td>
							<label>更新时间:&nbsp;</label>
							<label id="updateTime1" class="dt">订单号:&nbsp;</label>
						</td>
					</tr>
				</tbody>
				
			</table>
			<span class="buyBt" style="margin-bottom: 20px;margin-left: 450px;color: white;">关闭</span>
			<em class="close">&Chi;</em>
		</div>
	 </div>
</section>

<input type="hidden" id="symbol" value="${ftrademapping.fid }">

<form id="exportForm" action="/order/orderExport.html" method="post">
	<input type="hidden" id="f_orderId" name="f_orderId" value="">
	<input type="hidden" id="f_remark" name="f_remark" value=""/>
	<input type="hidden" id="f_startDate" name="f_startDate" value=""/>
	<input type="hidden" id="f_endDate" name="f_endDate" value=""/>
	<input type="hidden" id="f_orderStatus" name="f_orderStatus" value=""/>
	<input type="hidden" id="f_adType" name="f_adType" value=""/>
	<input type="hidden" id="f_coinType" name="f_coinType" value=""/>
	<input type="hidden" id="f_isMerchant" name="f_isMerchant" value="${isMerchant}"/>

</form>
<jsp:include page="../comm/footer.jsp"></jsp:include>
<script type="text/javascript" src="${oss_url}/static/front2018/js/trade/trade.js?v=2"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/bootstrap.js?v=20181126100022.js"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/My97DatePicker/WdatePicker.js?v=20181126201750"></script>
<script type="text/javascript">

	var isClick = false;
	//买家申诉
	function appealOrder(orderId)
	{
		util.layerConfirm("您确定要申诉吗？", function () {
			if(isClick) {
				return ;
			}
			isClick= true;
			var param = {'orderId':orderId, 'exceptionOrder':"exception"};
			jQuery.post('/order/appeal.html', param, function (data) {
			  
		    	if (data.code == -1) {
					util.layerAlert("", data.msg, 2);
				} else {
					window.location.reload();
				}
		    }, "json");
	    });
	}
	
	
	//订单导出
	function exportOtcOrder()
	{
		util.layerConfirm("您确定要导出吗？", function (index) {
			if(isClick) {
				return ;
			}
			isClick= true;
			var orderId = $("#id").val();
			var remark = $("#remark").val();
			var startDate = $("#startDate").val();
			var endDate = $("#endDate").val();
			var orderStatus = $("#orderStatus").val();
			var adType = $("#ad_type").val();
			var coinType = $("#amount_type").val();
			$("#f_orderId").val(orderId);
			$("#f_remark").val(remark);
			$("#f_startDate").val(startDate);
			$("#f_endDate").val(endDate);
			$("#f_orderStatus").val(orderStatus);
			$("#f_adType").val(adType);
			$("#f_coinType").val(coinType);
			
			$("#exportForm").submit();
			
			layer.close(index);
	    });
	}
	

	function orderDetails(orderId) {
		var lab = $("#details").find(".dt");

		$.each(lab, function(index, v) {
			$(this).text("");
		});

		var url = "/order/selectcOrder.html";
		$.ajax({
			type : 'post',
			url : url,
			data : {
				'orderId' : orderId
			},
			dataType : "json",
			success : function(result) {
				console.log(result)
				if (result != null && result.code == 0) {

					$("#orderId1").text(result.id);
					$("#remark1").text(result.remark);

					$("#adType1").text(result.orderType);

					$("#adUser").text(result.adUser);
					$("#coinType1").text(result.cointype);
					$("#orderStatus1").text(result.orderStatus);

					$("#mount1").text(result.amount);
					$("#count1").text(result.totalPrice);
					$("#price1").text(result.unitPrice);
					$("#updateTime1").text(result.updateTime);

				}

			}
		});
		$(".warpForm").fadeIn(200, function() {

			$(this).children().fadeIn(200);
			$(".warpForm .close").click(function(event) {
				$(this).parent().parent().fadeOut(100);
			});
		});

	}

	$(function() {
		$(".warpForm .buyBt").click(function(event) {
			$(this).parent().parent().fadeOut(100);
		});
	})
</script>
</body>
</html>




