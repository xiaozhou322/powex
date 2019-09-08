<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%

%>








 



<!doctype html>
<html>
<head>
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name = "viewport" content = "width = device-width, user-scalable = no,initial-scale=0.5,maximum-scale=0.5,minimum-scale=0.5">
<%@include file="../comm/link.inc.jsp" %>

<link rel="stylesheet" href="${oss_url}/static/front/css/user/user.css?v=20181126201750" type="text/css"></link>
<link href="${oss_url}/static/front/css/index/main.css?v=20181126201750" rel="stylesheet" type="text/css" />
<script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
</head>
<body>
	




<%@include file="../comm/header.jsp" %>
	<div class="lw-content" style="background:#1d2c43;">
		<div class="lw-finance" style="padding:0;">		
	         <div class="lw-financeLeft fl">
	            <ul>
	                <li class="tabBtn "><a href="/user/security.html?tab=0"><spring:message code="security.security" /> <i></i></a></li>
	                <li class="tabBtn"><a href="/user/security.html?tab=1"><spring:message code="security.bindphone" /><i></i></a></li>
			        <li class="tabBtn"><a href="/user/security.html?tab=2"><spring:message code="security.bindemail" /><i></i></a></li>
			        <li class="tabBtn"><a href="/user/security.html?tab=3"><spring:message code="security.logpas" /><i></i></a></li>
	                <li class="tabBtn"><a href="/user/security.html?tab=4"><spring:message code="security.tradingpwd" /><i></i></a></li>
	                <li class="tabBtn"><a href="/user/realCertification.html"><spring:message code="security.ideaut" /><i></i></a></li>
	                <li class="tabBtn"><a href="/user/security.html?tab=6" ><spring:message code="security.gooaut" /><i></i></a></li>
	                <li class="tabBtn lw-cur"><a href="/question/question.html"><spring:message code="security.workcenter" /><i></i></a></li>
	            </ul>
      		 </div>

			<div class="lw-financeRight">
				<h1 class="lw-financeTit">积分等级</h1>
				<div class="lw-coinTitList  vip">			
<!-- 					<ul class="nav nav-tabs rightarea-tabs">
						<li class="active">
							<a href="javascript:void(0)">等级积分</a>
						</li>
						<li>
							<a href="/user/level.html?type=1">积分记录</a>
						</li>
					</ul> -->

					<ul>
                      <li class="fl lw-active"><a href="javascript:void(0)">等级积分</a></li>
                      <li class="fl "><a href="/user/level.html?type=1">积分记录</a></li>
                      <div class="clear"></div>
                    </ul>




					<div class="col-xs-12 padding-right-clear padding-left-clear rightarea-con">
						<div class="col-xs-12 ">
							<div class="score-top-icon col-xs-12">
								<div class="col-xs-2 top-icon">
									<i class="vip-icon viplevel${fuser.fscore.flevel }"></i>
								</div>
								<div class="col-xs-2 top-con" id="userlevel">
									<p>
										您目前的等级
										：
									</p>
									<p>
										VIP
										<strong>${fuser.fscore.flevel }</strong>
									</p>
								</div>
								<div class="col-xs-2 top-con">
									<i class="split"></i>
									<p>
										您目前的积分
										：
									</p>
									<p>${fuser.fscore.fscore }</p>
								</div>
								<div class="col-xs-6 top-icon text-center">
									<button id="buyVip6" class="now_buy">
										<sapn style="font-size:16px;">￥</sapn>
										<span style="font-size: 28px;">2888</span>
										<sapn style="font-size:18px;">/年</sapn>
										<span class="nbright">
											<span>
												立即购买VIP6
											</span>
										</span>
									</button>
								</div>
							</div>
							<div class="col-xs-12 score-top-v6">
								<div class="col-xs-12">
									<p class="top-v6-title">
										VIP6钻石等级用户特权
										：
									</p>
									<div class="top-v6-con">
										<p class="col-xs-3">
											专属交易风险提醒
										</p>
										<p class="col-xs-3">
											低提现费率
										</p>
										<p class="col-xs-3">
											快速充提
										</p>
										<p class="col-xs-3">
											大客户经理专职服务
										</p>
										<p class="col-xs-3">
											日提现专享定制额度
										</p>
										<p class="col-xs-3">
											客服24小时一对一服务
										</p>
									</div>
								</div>
							</div>
						</div>
						<div class="col-xs-12 padding-right-clear padding-left-clear userlevelinfo ">
							<p class="vipdjinfo text-center">-- 会员级别图示 --</p>
							<div class="vipdj-pic ">
								<span class="dj-line"></span>
							</div>
						</div>
						<div class="col-xs-12 padding-right-clear padding-left-clear ulinfo ">
							<p class="vipdjinfo text-center">-- 获得积分的好处 --</p>
							<div>
								<table class="text-center">
									<tr id="vipheader">
										<td>等级</td>
										<td>积分</td>
										<td>交易手续费</td>
										<td>人民币 <br /> 快速充值
										</td>
										<td>虚拟币 <br /> 快速提现
										</td>
										<td>人民币提现费率</td>
									</tr>
									<c:forEach items="${kvs }" var="v">
									<tr>
										<td>${v.name }</td>
										<td>${v.total }</td>
										<td>${v.status }</td>
										<c:if test="${v.name=='VIP1' }">
										<td rowspan="7">0%</td>
										<td rowspan="7">0%</td>
										</c:if>
										<td>${v.value }</td>
									</tr>
									</c:forEach>
								</table>
							</div>
							<div class="vipdjpromt">
								<p>*VIP等级永久有效。&nbsp;&nbsp;&nbsp;&nbsp;*当积分达到对应等级要求后系统自动为用户升级到对应VIP级别。&nbsp;&nbsp;&nbsp;&nbsp;*可能会根据运营需要对VIP政策做出调整。</p>
							</div>
						</div>
						<div class="col-xs-12 padding-right-clear padding-left-clear ulinfo  getjf ">
							<p class="vipdjinfo text-center">-- 如何获得积分 --</p>
							<table>
								<tr id="vipheader">
									<td>操作</td>
									<td>积分</td>
									<td>说明</td>
								</tr>
								<c:forEach items="${scores }" var="v" begin="0" end="3">
								<tr>
									<td>${v.type_s }</td>
									<c:if test="${v.type ==4 }">
									<td>折合人民币×${v.score }</td>
									</c:if>
									<c:if test="${v.type !=4 }">
									<td>+${v.score }</td>
									</c:if>
									<td class="text-left">${v.remark }</td>
								</tr>
								</c:forEach>
							</table>
							<table class="usergetjf">
								<tr id="vipheader">
									<td>操作</td>
									<td>积分</td>
									<td>说明</td>
								</tr>
								<c:forEach items="${scores }" var="v" begin="4">
								<tr>
									<td>${v.type_s }</td>
									<td>+${v.score }</td>
									<td>${v.remark }</td>
								</tr>
								</c:forEach>
							</table>
						</div>					
					</div>
				</div>
			</div>
		</div>
	</div>
	
	

<div class="modal modal-custom fade" id="tradepass" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
		<div class="modal-dialog modal-trading-dialog" role="document">
			<div class="modal-mark"></div>
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span class="modal-title" id="exampleModalLabel">
						交易密码
					</span>
				</div>
				<div class="modal-body form-horizontal">
					<div class="form-group">
						<div class="col-xs-8 col-xs-offset-2">
							<input type="password" class="form-control" id="tradePwd"
								placeholder="请输入交易密码">
						</div>
					</div>
					<div class="form-group">
						<div class="col-xs-8 col-xs-offset-2">
							<span id="errortips" class="error-msg text-danger"></span>
						</div>
					</div>
					<div class="form-group">
						<div class="col-xs-8 col-xs-offset-2">
							<button id="modalbtn" type="button" class="btn btn-danger btn-block">
								确定
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>


 


<%@include file="../comm/footer.jsp" %>	
<script type="text/javascript" src="${oss_url}/static/front/js/user/userlevel.js?v=20181126201750"></script>
</body>
</html>
