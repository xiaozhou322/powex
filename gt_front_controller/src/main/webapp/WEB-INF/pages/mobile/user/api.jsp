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

<link href="${oss_url}/static/front/css/index/main.css?v=20181126201750" rel="stylesheet" type="text/css" />

<%@include file="../comm/link.inc.jsp" %>

<style type="text/css">
.api-tips{
	padding: 30px 15px;
	font-size: 14px;
}
.api-info{
	padding: 0 15px 15px;
	font-size: 14px;
}
.api-btn{
	display: inline-block;
	width: 50%;
	margin-top: 30px;
	margin-bottom: 30px;
}
</style>
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
			<div class="lw-financeRight security-right">
			    <h1 class="lw-financeTit">API访问</h1>
				<p class="warn red" style="border-bottom: none;color: #ff6767!important;">【重要提示】每个用户最多只能创建1个密钥对。
				私有密钥允许您通过${requestScope.constant['webName']}开放协议提供API访问您的账户并执行交易指令，我们为您提供的密钥非常重要，请妥善保管。</p>
				<div class=" padding-right-clear padding-left-clear lw-financeMain">
					<div class=" rightarea-con login_tixian_box">
						<!-- <div class="panel panel-tips">
							<div class="panel-header text-center text-danger">
								<span class="panel-title">重要提示</span>
							</div>
							<div class="panel-body">
								<p>&lt 每个用户最多只能创建1个密钥对。</p>
								<p>&lt 私有密钥允许您通过${requestScope.constant['webName']}开放协议提供API访问您的账户并执行交易指令，我们为您提供的密钥非常重要，请妥善保管。</p>
							</div>
						</div> -->
						<div class="form-horizontal" >

								<div class="login_item login_item_show clear ">
									<label for="tradePwd" class="login_item_l control-label">交易密码</label>
									<div class="login_item_r">
										<input id="tradePwd" class="form-control" type="password" autocomplete="off">
									</div>
								</div>
								<div class="login_item login_item_show clear ">
									<label for="api" class="login_item_l control-label">API权限</label>
									<div class="login_item_r">
											<select class="form-control" name="type" id="type" style="width:400px;">
												<option value="0">交易</option>
												<option value="1">提现</option>
												<option value="2">交易与提现</option>
											</select>
									</div>
								</div>
							<div class="login_item login_item_show clear ">
									<label for="ip" class="login_item_l control-label">IP地址</label>
									<div class="login_item_r">
										<input id="ip" class="form-control" type="text" placeholder="不限制留空，多个IP用逗号隔开" autocomplete="off">
									</div>
								</div>
							<c:if test="${isBindTelephone == true }">		
									<div class="login_item login_item_show clear">
										<label for="apiPhoneCode" class="login_item_l control-label">短信验证码</label>
										<div class="login_item_r" style="position:relative;">
											<input id="apiPhoneCode" class="form-control" type="text" autocomplete="off">
											<button id="apisendmessage" data-msgtype="15" data-tipsid="apierrortips" class="btn btn-sendmsg" style="height:38px; right:0; top:1px;">发送验证码</button>
										</div>
									</div>
							</c:if>		
								
							<c:if test="${isBindGoogle ==true}">	
									<div class="login_item login_item_show clear"style="padding-bottom:0;">
										<label for="apiTotpCode" class="login_item_l control-label">谷歌验证码</label>
										<div class="login_item_r">
											<input id="apiTotpCode" class="form-control" type="text" autocomplete="off">
										</div>
									</div>
							</c:if>		
							
							<div class="login_item login_item_show clear" style="padding-bottom:0;">
								<label for="errorTips" class="login_item_l control-label"></label>
								<div class="login_item_r">
									<span id="errorTips" class="text-danger"> </span>
								</div>
							</div>
							<div class="login_item login_item_show clear">
								<label for="apiButton" class="login_item_l control-label"></label>
								<div class="login_item_r">
									<button id="apiButton" class="loginpwd_btn btn-find" style="width:400px; height:40px; line-height:40px; padding:0; border-radius:5px;">创建</button>
								</div>
							</div>
						</div>
						<div class="padding-clear padding-top-30">
							<div class="lw-Coinrecord">
								<div class="">
									<!-- <span class="text-danger">我的密匙</span> -->
									<h2 style="font-size:14px;">我的密匙</h2>
								</div>

								<div class="" id="recordbody0">
									<table class="table" style="border-top:none;">
										<tr>
											<th style="text-align:left;" class="col-xs-3">创建日期</th>
											<th style="text-align:left;" class="col-xs-4">API 访问密钥(Access Key)</th>
											<th style="text-align:left;" class="col-xs-2">权限</th>
											<th style="text-align:left;" class="col-xs-2">IP地址</th>
											<th style="text-align:left;" class="col-xs-1">操作</th>
										</tr>
										<c:forEach items="${list }" var="v">
										    <tr>
												<td>${v.fcreatetime }</td>
												<td>${v.fpartner }</td>
												<td>${v.fistrade?'交易':'' }&nbsp;&nbsp;${v.fiswithdraw?'提现':'' }</td>
												<td>${v.fip }</td>
												<td><a class="delete-api" style="cursor: pointer;" data-fid="${v.fid }">删除</a></td>
											</tr>
										</c:forEach>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="modal modal-custom fade" id="apiModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-mark"></div>
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span class="modal-title" id="exampleModalLabel">创建成功</span>
				</div>
				<div class="modal-body">
					<div class="api-tips text-danger">您已经申请了API密钥对，请不要把您的密钥透露给任何人。秘密密钥 （Secret Key）仅在申请时显示，遗失后不可找回，请务必注意保存。 如您忘记了秘密密钥，请回收该密钥对并申请新的密钥对。</div>
					<div class="api-info">
						<span>访问密钥(Access Key) : </span> <span id="accessKey"></span>
					</div>
					<div class="api-info">
						<span>访问密钥(Secret Key) : </span> <span id="secretKey"></span>
					</div>
					<div class="api-info text-center">
						<button id="modalBtn" class="btn btn-danger api-btn">确定</button>
					</div>
				</div>
			</div>
		</div>
	</div>

	




<%@include file="../comm/footer.jsp" %>	


	<input id="apiCount" type="hidden" value="${apiCount }"/>
	<input id="apinum" type="hidden" value="${apinum }"/>
	<script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/comm/msg.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/api/api.js?v=20181126201750"></script>
	<script type="text/javascript">
	  $(document).scroll(function() {
	          $(".lw-header").removeClass('lw-fixed')
	         var scrollTop = $(document).scrollTop();  //获取当前滑动位置
	         if(scrollTop > 60){                 //滑动到该位置时执行代码
	          $(".lw-header").addClass('lw-fixed')
	         }else{
	         }
	    });
	</script>
</body>
</html>