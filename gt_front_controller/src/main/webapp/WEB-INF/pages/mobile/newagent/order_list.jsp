<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ include file="../comm/include.inc.jsp"%>

<!doctype html>
<html>
<head>
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport"
	content="width = device-width, initial-scale = 1.0, maximum-scale = 1.0, user-scalable = 0" />
<%@include file="../comm/link.inc.jsp"%>

<link href="${oss_url}/static/mobile2018/css/usdtTrade.css?v=20190301163300100" rel="stylesheet" type="text/css" />
<style type="text/css">
.tradeTop{padding-left:0;}
.tradeTop .tit{
    text-align: center;
    margin: 0 auto;
}
.orderType {
	height: 0.82rem;
	line-height: 0.82rem;
	vertical-align: middle;
	border-bottom: 1px solid #ddd;
	overflow: hidden;
	font-size: 0.24rem;
	padding: 0 0.5rem;
	background: #fff;
}

#t_con .itemForm {
	display: none;
	margin-bottom: 0.2rem;
	background: #fff;
}

.itemForm p {
	line-height: 0.72rem;
	color: #8b8b8b;
	vertical-align: middle;
}

.itemForm p:last-child {
	padding-bottom: 0.32rem;
}

.itemForm p .item_li {
	display: inline-block;
	width: 2rem;
	text-indent: 0.5rem;
}

.itemForm p .item_s {
	line-height: 0.72rem;
	color: #8b8b8b;
	vertical-align: middle;
}

.buyBtList {
	display: inline-block;
	padding: 0.1rem 0.2rem;
	background: #2269d4;
	color: #fff;
	line-height: 0.36rem;
	border-radius: 0.2rem;
}
.buyTit{
    color: #506FC8;
    font-size: 0.24rem;
    position: absolute;
    right: 0.3rem;
}
    .uTrade{
	box-shadow: 0px 2px 9px 0px rgba(70, 90, 111, 0.14);
    margin-bottom: 0.32rem;
    margin-top: 0.2rem;}
    .red_radius{
        position: relative;
    	float: left;
    }
    .red_radius span{ 
    border-radius: 50%;
    background: #FF1B1B;
    width: 0.16rem;
    height: 0.16rem;
    position: absolute;
    right: -0.15rem;
    top: 0.2rem;}
</style>
</head>
<body class="Ubody">
	<section class="uTrade mg clear" style="margin:0;">
	<%@include file="../comm/newotc_header.jsp"%>
			<%-- 		<div class="msgTit myRecord clear">
				<div class="clear">
					<div class="myRecord_tit fl">
						<a href="/order/orderList.html?status=0" class="fl active"> 
							<svg class="icon sfont18" aria-hidden="true">
	                      		<use xlink:href="#icon-yuyuejilu"></use>
	                      	</svg> 
	                      	<spring:message code="order.unfinish" />
						</a> 
						<a href="/order/orderPageList.html?status=1" class="fl "> 
							<svg class="icon sfont18" aria-hidden="true">
	                          <use xlink:href="#icon-jiaoyichenggong"></use>
	                      	</svg>
							<spring:message code="order.history" />
						</a>
					</div>
				</div>
			</div> --%>
			<!-- 订单下拉框 -->
			<div class="uTrade_l">
				<div class="securityMain newsCon helpCon">
					<span class="selStyle" onclick="showtab('coinWarp');">
					<spring:message code="order.unfinish" /></span>
					<%-- <span class="selStyle" onclick="showtab('coinWarp');"><spring:message code="order.history" /></span> --%>
				</div>
			</div>
			<!-- END订单下拉框 -->
			<div id="t_con"></div>
			<input type="hidden" value="${currentPage}" id="currentPage">
	</section>

	<div class="coinWarp" id="coinWarp" onclick="hidetab(this.id);"
		style="z-index: 10000">
		<div class="coinLitBox coinLitBox2">
			<ul>
				<li><a href="/order/orderList.html?status=0"> <spring:message
							code="order.unfinish" />
				</a></li>
				<li><a href="/order/orderPageList.html?status=1"> <spring:message
							code="order.history" />
				</a></li>
			</ul>
		</div>
	</div>



	<input type="hidden" id="symbol" value="${ftrademapping.fid }">
	<jsp:include page="../comm/footer.jsp"></jsp:include>
	<%@include file="../comm/otc_tabbar.jsp"%>
	<script type="text/javascript"	src="${oss_url}/static/front2018/js/trade/trade.js?v=2"></script>
	<script type="text/javascript"	src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
	<script type="text/javascript">
	var toggleId,
		slideboolean=false; 
		$(function() {
			go();
			/* $(".orderType").on("click",function(){
				$(".itemForm").hide();
				$(this).siblings('.itemForm').show();
				
			}) */
			
			//$(".orderType").click(function(event) {
				$(document).on('click','.orderType',function(){
				/* 	var typeslide = $(this).attr('nameble');
					if(typeslide=='false'){
						slideboolean =true;
						toggleId = $(this).attr('nameId');
						toggleId = $(this).attr('nameble','true');
						$(".itemForm").hide();
						$(this).siblings('.itemForm').show();
						console.log(1)
					}else{
						$(".itemForm").hide();
						slideboolean = false;
						toggleId = '';
						toggleId = $(this).attr('nameble','false');
					} */
					var typeslide = $(this).attr('nameble');
					if(typeslide=='false'){
						$(this).parent().siblings().find('.itemForm').hide();
						$(this).parent().siblings('.itemCon').find('.orderType').attr('nameble','false');
						$(this).siblings('.itemForm').show();
						$(this).attr('nameble','true');
						toggleId = $(this).attr('nameid');
						slideboolean = true;
					}else{
						$(this).siblings('.itemForm').hide();
						$(this).attr('nameble','false');
						toggleId = '';
					}
					
			
			});

		});
		function go() {
			var status = ${status};
			var id = $("#id").val();
			var orderStatus = $("#orderStatus").val();
			var ad_type = $("#ad_type").val();
			var remark = $("#remark").val();
			var amount_type = $("#amount_type").val();
			var url = "/order/orderList1.html?random="
					+ Math.round(Math.random() * 1000);
			$.ajax({
						type : 'post',
						url : url,
						async : false,
						data : {
							'id' : id,
							'orderStatus' : orderStatus,
							'ad_type' : ad_type,
							'remark' : remark,
							'status' : status,
							'amount_type' : amount_type
						},
						dataType : "json",
						success : function(result) {
							$("#t_con").empty();
							var html = "";
							pageIndex = result.pageIndex;//全局变量赋值
							totalPage = result.totalPage;
							if (result.success) {
								$.each(result.orderList,function(index, vul) {
													html += ' <div class="itemCon">';
													html += '<div class="orderType" nameId='+vul.id+' nameble='+slideboolean+'>';
													html += '<div class="orderTypeLi fl">';
													if (vul.isAgent && vul.order_status == 101 ||vul.order_status == 103) {
														html += '<em class="red_radius"><span></span>'+ vul.order_type + '</em>';
													}else{
														html += '<em>'+ vul.order_type + '</em>';
													}
													html += ' <em class="time" style=" margin-left: 0.3rem;">（'+ vul.coin_type + '）</em>';
													if (vul.order_type == "买入") { // 订单类型 ：买入     卖出
														html += ' <strong class="cgreen">￥'+ vul.unit_price+ '</strong>';
													} else {
														html += ' <strong class="cred">￥'+ vul.unit_price+ '</strong>';
													}
													html += '</div>';
													if (vul.isAgent && vul.order_status == 101) {
														html += '<p class="fr"><span class="buyBtList" onclick="receiveOrder('
																+ vul.id
																+ ')">接单</span><span class="buyBtList" style="background:#FF2E2E;margin-left:0.1rem;" onclick="refuse('
																+ vul.id
																+ ')">拒接</span></p>';
													} else {
														html += '<p class="fr"><span class="buyBtList" onclick="receiveBtn('+ vul.id+')" style="color:white;">处理订单</a> </span></p>';
													}
													html += '</div>';
													if (toggleId == vul.id.toString()) {
														html += '<div class="itemForm clear" style="display:block;">';
											        }else{
											        	html += '<div class="itemForm clear">';
											        }
													html += '<p><span class="item_li"><spring:message code="order.orderid" /></span><span class="item_s">'
															+ vul.id
															+ '</span></p>';
													html += '<p><span class="item_li"><spring:message code="order.type" /></span><span class="item_s">'
															+ vul.order_type
															+ '</span></p>';
													html += '<p><span class="item_li">下单人</span><span class="item_s">'
														+ vul.user_name
														+ '</span></p>';
													html += '<p><span class="item_li"><spring:message code="market.currencytype" /></span><span class="item_s">'
															+ vul.coin_type
															+ '</span></p>';
													html += '<p><span class="item_li"><spring:message code="order.amount" /></span><span class="item_s">'
															+ vul.amount
															+ '</span></p>';
													html += '<p><span class="item_li"><spring:message code="order.price" /></span><span class="item_s">'
															+ vul.unit_price
															+ '</span></p>';
													html += '<p><span class="item_li"><spring:message code="order.totalprice" /></span><span class="item_s">'
															+ vul.total_price
															+ '</span></p>';
													if (vul.order_status == 105
															|| vul.order_status == 106) {
														html += '<p><span class="item_li"><spring:message code="order.status" /></span><span class="item_s" style="width:8.8%;color:red;">'
																+ vul.order_statusDesc
																+ '</span></p>';
													} else if (vul.order_status == 107) {
														html += '<p><span class="item_li"><spring:message code="order.status" /></span><span class="item_s" style="width:8.8%;color:green;">'
																+ vul.order_statusDesc
																+ '</span></p>';
													} else {
														html += ' <p><span class="item_li"><spring:message code="order.status" /></span><span class="item_s" style="width:8.8%;color:blue;">'
																+ vul.order_statusDesc
																+ '</span></p>';
													}
													html += ' <p><span class="item_li"><spring:message code="order.newtime" /></span><span class="item_s" style="width:8.8%">'
															+ vul.update_time
															+ '</span></p>';
													html += ' <p><span class="item_li"><spring:message code="备注" /></span><span class="item_s" style="width:8.8%">'
															+ vul.remark
															+ '</span></p>';
													/* if (vul.isAgent && vul.order_status == 101) {
														html += '<p><span class="item_li"><spring:message code="操作" /></span><span class="buyBtList" onclick="receiveOrder('
																+ vul.id
																+ ')">接单</span><span style="width:7.5%;background:red;" onclick="refuse('
																+ vul.id
																+ ')">拒接</span></p>';
													} else {
														html += '<p><span class="item_li"><spring:message code="操作" /></span><span class="buyBtList"><a href="/order/orderDetail.html?orderId='
																+ vul.id
																+ '" style="color:white;">处理订单</a> </span></p>';
													} */
													html += '</div>';
													html += '</div>';
												
												});
							}

							$("#t_con").html(html);
						},

					});

				setTimeout("go()", "10000");

		}

		//接单
		function receiveOrder(orderId) {
			event.stopPropagation();		// 阻止冒泡事件
			toggleId = orderId
			var param = {
				'orderId' : orderId
			};
			util.layerConfirm("你确定接单么？", function() {
				jQuery.post('/order/receiveOrder.html', param, function(data) {
					if (data.code == -1) {
						util.layerAlert("", data.msg, 2);

					} else {
						util.layerAlert("", data.msg, 1);
					}
				}, "json");
			});
		}
		//拒接
		function refuse(orderId) {
			event.stopPropagation();	// 阻止冒泡事件
			util.layerConfirm("你确定拒绝接单？", function() {
				var param = {
					'orderId' : orderId
				};
				jQuery.post('/order/failedOrder.html', param, function(data) {

					if (data.code == -1) {
						util.layerAlert("", data.msg, 2);

					} else {
						util.layerAlert("", data.msg, 1);
					}
				}, "json");
			});
		}
		function receiveBtn(id){
			event.stopPropagation();	// 阻止冒泡事件
			window.location.href = "/order/orderDetail.html?orderId="+id;
		}
	</script>
</body>
</html>

