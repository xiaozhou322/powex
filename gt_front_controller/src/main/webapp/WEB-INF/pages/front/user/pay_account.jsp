<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ include file="../comm/include.inc.jsp"%>

<!doctype html>
<html>
<head>
<%@ include file="../comm/basepath.jsp"%>
<style type="text/css">
html, body{background:#F9FBFF;}
.aqMain{    box-shadow: 0px 2px 9px 0px rgba(70, 90, 111, 0.14);}

</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp"%>
<link href="${oss_url}/static/front2018/css/usdtTrade.css?v=11"
	rel="stylesheet" type="text/css" />
</head>
<body class="">
	<%@include file="../comm/header.jsp"%>
	<section style="">
		<div class="mg">
			<div class="l_finance clear">
				<div class=" fl">
					<div class="firstNav">
						<%@include file="../comm/left_menu.jsp"%>
					</div>
				</div>
				
				<form id="agentinfoForm" onsubmit="return false;">
					<input name="paytype" id="paytype" type="hidden" value="0" />
					<div class="l_financeR fr tabBtn">
					<div class="financialCen"><spring:message code="agent.accountsetting" /><span class="attentionTit"><spring:message code="agent.hint" /></span></div>
						<!-- <h2 class="assetTitle">安全设置</h2> -->
						<%-- <h3 class="ts_title cred">
							<svg class="icon sfont18" aria-hidden="true">
	                            <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#icon-tishi2"></use>
	                        </svg>
							<spring:message code="agent.hint" />
						</h3> --%>
						<div class="aqMain">
							<ul>
								<%-- <c:if test="${v.payType == 1 }">修改</c:if> --%>
								<li>
									<div class="accoutList clear">
										<div class="aq_tit aq_tit02 fl">
											<svg class="icon i_mgr" aria-hidden="true">
                                               <use
													xmlns:xlink="http://www.w3.org/1999/xlink"
													xlink:href="#icon-yinhangqia"></use>
                                           </svg>
											<span><spring:message code="agent.bankname" /></span>
										</div>
										<p class="fl discription ">
											<spring:message code="agent.bindbank" />
										</p>
										<c:choose>
											<c:when test="${queryisBindBank != null }">
												<a class="fl cblue2 toggleBtn" href="javascript:;"> <spring:message
														code="security.tochange" />
												</a>
											</c:when>
											<c:otherwise>
												<a class="fl cred toggleBtn" href="javascript:;"> <spring:message
														code="security.bind" />
												</a>
											</c:otherwise>
										</c:choose>
										<!-- <span class="switch-on switch-on2" id="coinSwitch">
                                           <em class="slide1"></em>
                                           OFF
                                       </span> -->
									</div>
									<div class="accoutShow">
										<div class="area">
											<div class="tr_txt">
												<span class="tr_l"><spring:message
														code="financial.cnyrecharge.accname" /></span> <input
													type="text"
													<c:if test="${ismerchant != true }">readOnly="true" value="${realname }"</c:if>
													<c:if test="${ismerchant == true }">value="${queryisBindBank.realName }"</c:if>
													id="fbankperson" name="fbankperson" class="inpBox">
											</div>
											<div class="tr_txt">
												<span class="tr_l"><spring:message
														code="agent.accountnum" /></span> <input class="inpBox"
													type="text" value="${queryisBindBank.paymentAccount }"
													id="fbanknumber" name="fbanknumber" />
											</div>
											<div class="tr_txt">
												<span class="tr_l"><spring:message
														code="agent.Bankaccount" /></span> <input name="paytypebank"
													type="hidden" value="1" /> <input name="paytypebank_id"
													type="hidden" value="${queryisBindBank.id }" /> <input
													class="inpBox" type="text"
													value="${queryisBindBank.bank }" id="fbankname"
													name="fbankname" />
											</div>
											<div class="tr_txt">
												<span class="tr_l"><spring:message
														code="agent.Branchname" /></span> <input class="inpBox"
													type="text" value="${queryisBindBank.bankBranch }"
													id="fbankothers" name="fbankothers" /> <input
													class="inpBox" type="hidden"
													value="${queryisBindBank.remark }" id="fbankaddress"
													name="fbankaddress" />
											</div>

											<div class="">
												<label class="lab"> <spring:message
														code="agent.start" />&nbsp;<input class="checkbox"
													type="checkbox" id="openbank" name="openbank" value="1"
													<c:if test="${queryisBindBank.status==1 }">  checked  </c:if>>
													<span></span>
												</label>
											</div>
											<p id="save" style="color: red;text-align: center;"></p>
											<div class="tr_txt">
												<span class="tr_l"></span>
												<button class="btn sub mgt20" onclick="saveAgentInfo()">
													<spring:message code="security.submit" />
												</button>
											</div>

										</div>
										<div class="care">
											<div style="background: #fff; padding: 30px;"></div>
										</div>
									</div>
								</li>
								<li>
									<div class="accoutList clear">
										<div class="aq_tit aq_tit02 fl">
											<svg class="icon i_mgr" aria-hidden="true">
                                               <use
													xmlns:xlink="http://www.w3.org/1999/xlink"
													xlink:href="#icon-zhifubaoa"></use>
                                           	</svg>
											<span><spring:message code="agent.Alipayaccount" /></span>
										</div>
										<p class="fl discription ">
											<spring:message code="agent.bindalipay" />
										</p>
										<c:choose>
											<c:when test="${queryisBindAlipy != null }">
												<a class="fl cblue2 toggleBtn" href="javascript:;"> <spring:message
														code="security.tochange" />
												</a>
											</c:when>
											<c:otherwise>
												<a class="fl cred toggleBtn" href="javascript:;"> <spring:message
														code="security.bind" />
												</a>
											</c:otherwise>
										</c:choose>
									</div>
									<div class="accoutShow">
										<div class="area">
											<div class="tr_txt">
												<span class="tr_l"><spring:message
														code="agent.Alipayname" /></span> <input name="paytypealipay"
													type="hidden" value="3" /> <input name="paytypealipay_id"
													type="hidden" value="${queryisBindAlipy.id }" /> <input
													type="text"
													<c:if test="${ismerchant != true }">readOnly="true" value="${realname }"</c:if>
													<c:if test="${ismerchant == true }">value="${queryisBindAlipy.realName }"</c:if>
													id="aliname" name="aliname" class="inpBox"
													placeholder='<spring:message code="agent.Alipayname" />'>
											</div>
											<div class="tr_txt">
												<span class="tr_l"><spring:message
														code="agent.Alipayaccount" /></span> <input type="text"
													value="${queryisBindAlipy.paymentAccount }" id="aliid"
													name="aliid" class="inpBox"
													placeholder='<spring:message code="agent.Alipayaccount" />'>
											</div>
											<div class="upPic">
												<div class="verified">
													<div class="img_box">

														<div class="center inner">
															<span
																style="position: relative; top: 100px; left: 40px;"><spring:message
																	code="security.alipay" /></span>
															<c:if test="${queryisBindAlipy.qrCode!='' }">
																<p class="img">
																	<img src="${queryisBindAlipy.qrCode }" id="aliurl"
																		style="z-index: 1000; position: absolute; bottom: 50px; left: 25px;">
																</p>
															</c:if>
															<a class="radius upload_original" style="z-index: 1001">
																<label for="pic2" class="s1" id="alilabel"> <c:if
																		test="${queryisBindAlipy.qrCode =='' }">
																		<em class="cx"><spring:message
																				code="security.upload" /><span class="str"><spring:message
																					code="agent.Alipay" /></span> <spring:message
																				code="agent.receiptcode" /></em>
																	</c:if>
															</label>
															</a> <input id="pic2" type="file" onchange="uploadImg2()"
																style="display: none"> <input type="hidden"
																id="pic2Url" name="pic2Url"
																value="${queryisBindAlipy.qrCode }">
														</div>
													</div>
												</div>
											</div>
											<label class="lab"> <spring:message
													code="agent.start" />&nbsp;<input class="checkbox"
												type="checkbox" id="openalipay" name="openalipay" value="1"
												<c:if test="${queryisBindAlipy.status==1 }">  checked  </c:if>>
												<span></span>
											</label>
											<p id="saveone" style="color: red;text-align: center;"></p>
											<div class="tr_txt">
												<span class="tr_l"></span>
												<button class="btn sub mgt30" onclick="saveAgentInfo1()">
													<spring:message code="security.submit" />
												</button>
											</div>
										</div>

									</div>
								</li>
								<li>
									<div class="accoutList clear">
										<div class="aq_tit aq_tit02 fl">
											<svg class="icon i_mgr" aria-hidden="true">
                                               <use
													xmlns:xlink="http://www.w3.org/1999/xlink"
													xlink:href="#icon-weixin2"></use>
                                           </svg>
											<span><spring:message code="agent.wechat" /></span>
										</div>
										<p class="fl discription ">
											<spring:message code="agent.bindwechat" />
										</p>
										<c:choose>
											<c:when test="${queryisBindWeiXin != null }">
												<a class="fl cblue2 toggleBtn" href="javascript:;"> <spring:message
														code="security.tochange" />
												</a>
											</c:when>
											<c:otherwise>
												<a class="fl cred toggleBtn" href="javascript:;"> <spring:message
														code="security.bind" />
												</a>
											</c:otherwise>
										</c:choose>
									</div>
									<div class="accoutShow">
										<div class="area">
											<div class="tr_txt">
												<span class="tr_l"><spring:message
														code="agent.weChatname" /></span> <input name="paytypeweixin"
													type="hidden" value="2" /> <input name="paytypeweixin_id"
													type="hidden" value="${queryisBindWeiXin.id }" /> <input
													type="text" value="${queryisBindWeiXin.realName }"
													id="weixinname" name="weixinname" class="inpBox"
													placeholder='<spring:message code="agent.weChatname" />'>
											</div>
											<div class="tr_txt">
												<span class="tr_l"> <spring:message
														code="agent.wechat" />
												</span> <input type="text" class="inpBox"
													value="${queryisBindWeiXin.paymentAccount }" id="weixinid"
													name="weixinid"
													placeholder='<spring:message code="agent.wechat" />'>
											</div>
											<div class="upPic">
												<div class="verified">
													<div class="img_box">
														<div class="center inner">
															<span
																style="position: relative; top: 100px; left: 40px;">
																<spring:message code="security.wechat" />
															</span>
															<c:if test="${queryisBindWeiXin.qrCode != '' }">
																<p class="img">
																	<img src="${queryisBindWeiXin.qrCode }" id="wxurl"
																		style="z-index: 1000; position: absolute; bottom: 50px; left: 25px;">
																</p>
															</c:if>
															<a class="radius upload_original" style="z-index: 1001">
																<label for="pic1" class="s1" id="wxlabel"> <c:if
																		test="${queryisBindWeiXin.qrCode == '' }">
																		<em class="cx"><spring:message
																				code="security.upload" /> <span class="str"><spring:message
																					code="agent.wechattit" /></span> <spring:message
																				code="agent.receiptcode" /> </em>
																	</c:if>
															</label>
															</a> <input id="pic1" type="file" onchange="uploadImg1()"
																style="display: none"> <input type="hidden"
																id="pic1Url" name="pic1Url"
																value="${queryisBindWeiXin.qrCode }">
														</div>
													</div>
												</div>
											</div>
											<div>
												<label class="lab"> <spring:message
														code="agent.start" />&nbsp;<input class="checkbox"
													type="checkbox" id="openweixin" name="openweixin"
													value="1"
													<c:if test="${queryisBindWeiXin.status == 1 }">  checked  </c:if>>
													<span></span>
												</label>
											</div>
											<p id="savetwo" style="color: red;text-align: center;"></p>
											<div class="tr_txt">
												<span class="tr_l"></span>
												<button onclick="saveAgentInfo2()" class="btn sub mgt30">
													<spring:message code="security.submit" />
												</button>
											</div>
										</div>
									</div>
								</li>
							</ul>
						</div>
					</div>
				</form>
			</div>
		</div>
		<%@include file="../comm/footer.jsp"%>
	</section>




	<script type="text/javascript"
		src="${oss_url}/static/front2018/js/index/main.js"></script>
	<script type="text/javascript"
		src="${oss_url}/static/front2018/js/plugin/ajaxfileupload.js"></script>
	<script type="text/javascript"
		src="${oss_url}/static/front2018/js/plugin/fileCheck.js"></script>
	<script type="text/javascript"
		src="${oss_url}/static/front2018/js/comm/msg.js"></script>
	<script type="text/javascript"
		src="${oss_url}/static/front2018/js/plugin/jquery.qrcode.min.js"></script>
	<!-- <script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.autocomplete.min.js"></script> -->
	<script type="text/javascript"
		src="${oss_url}/static/front2018/js/plugin/jquery-migrate-1.4.1.min.js"></script>
	<script>
		$(".uTrade_l a").click(function(event) {
			$(this).addClass('active').siblings().removeClass('active');
		});

		$(".payStyle p")
				.click(
						function(event) {
							$(this).addClass('active').siblings().removeClass(
									'active');
							var num = $(this).index();
							$(".pay_con").eq(num).addClass('active').siblings()
									.removeClass('active');
						});

		var isClick = false;
		var isClick1 = false;
		var isClick2 = false;

		function saveAgentInfo1() {
			//防止重复点击
			if (isClick1) {
				util.layerAlert("", "请勿重复提交", 2);
				return;
			}
			isClick1 = true;

			$("#paytype").val("3");
			var param = $('#agentinfoForm').serialize();
			if ($("#aliname").val() == '') {
				$("#saveone").html(language["agent.alipay"]);
				//失败后允许再次点击按钮
				isClick1 = false;
				return false;
			}

			if ($("#aliid").val() == '') {
				$("#saveone").html(language["agent.alipay.account"]);
				//失败后允许再次点击按钮
				isClick1 = false;
				return false;
			}
			if ($("#pic2Url").val() == '') {
				$("#saveone").html(language["agent.alipay.qr"]);
				//失败后允许再次点击按钮
				isClick1 = false;
				return false;

			}

			jQuery.post('/user/savepaytype.html', param, function(data) {
				if (data.code == -1) {
					$("#saveone").html(data.msg);
					//失败后允许再次点击按钮
					isClick1 = false;
				} else {
					$("#saveone").html(data.msg);
					setTimeout("window.location.href='/user/paytypeList.html'",
							1500)

				}
			}, "json");

		}
		function saveAgentInfo() {
			//防止重复点击
			if (isClick) {
				util.layerAlert("", "请勿重复提交", 2);
				return;
			}
			isClick = true;

			$("#paytype").val("1");
			var param = $('#agentinfoForm').serialize();
			if ($("#fbankperson").val() == '') {

				$("#save").html(language["agent.person"]);
				//失败后允许再次点击按钮
				isClick = false;
				return false;
			}
			if ($("#fbankname").val() == '') {
				$("#save").html(language["agent.fbankname"]);
				//失败后允许再次点击按钮
				isClick = false;
				return false;
			}

			if ($("#fbanknumber").val() == '') {
				$("#save").html(language["agent.fbanknumber"]);
				//失败后允许再次点击按钮
				isClick = false;
				return false;
			}

			if ($("#fbankothers").val() == '') {
				$("#save").html(language["agent.fbankothers"]);
				//失败后允许再次点击按钮
				isClick = false;
				return false;
			}

			jQuery.post('/user/savepaytype.html', param, function(data) {
				if (data.code == -1) {
					$("#save").html(data.msg);
					//失败后允许再次点击按钮
					isClick = false;
				} else {

					$("#save").html(data.msg);
					setTimeout("window.location.href='/user/paytypeList.html'",
							1500)
				}
			}, "json");

		}
		function saveAgentInfo2() {
			//防止重复点击
			if (isClick2) {
				util.layerAlert("", "请勿重复提交", 2);
				return;
			}
			isClick2 = true;

			$("#paytype").val("2");
			var param = $('#agentinfoForm').serialize();
			if ($("#weixinname").val() == '') {
				$("#savetwo").html(language["agent.weixinname"]);
				//失败后允许再次点击按钮
				isClick2 = false;
				return false;
			}

			if ($("#weixinid").val() == '') {

				$("#savetwo").html(language["agent.weixinid"]);
				//失败后允许再次点击按钮
				isClick2 = false;
				return false;
			}
			if ($("#pic1Url").val() == '') {

				$("#savetwo").html(language["agent.wechat.qr"]);
				//失败后允许再次点击按钮
				isClick2 = false;
				return false;
			}
			jQuery.post('/user/savepaytype.html', param, function(data) {
				if (data.code == -1) {
					$("#savetwo").html(data.msg);
					//失败后允许再次点击按钮
					isClick2 = false;
				} else {
					$("#savetwo").html(data.msg);
					setTimeout("window.location.href='/user/paytypeList.html'",
							1500)
				}
			}, "json");

		}
		//上传图片
		function uploadImg1() {
			if (checkFileType('pic1', 'img')) {
				fileUpload("/user/uploadPayway.html", "4", "pic1", "pic1Url",
						null, null, imgbakc1, "resultUrl");
			}
		}

		function imgbakc1(resultUrl) {
			$("#wxurl").attr('src', resultUrl);
			$('label[for="pic1"]').text(language["kyc.tips.1"]);
			$('.pic1name').text($('#pic1').val().split('\\').pop()).siblings()
					.text(language["kyc.tips.2"]);
			$('#wxlabel').html('');
		}

		function uploadImg2() {
			if (checkFileType('pic2', 'img')) {
				fileUpload("/user/uploadPayway.html", "4", "pic2", "pic2Url",
						null, null, imgbakc2, "resultUrl");
			}
		}

		function imgbakc2(resultUrl) {
			$("#aliurl").attr('src', resultUrl);
			$('label[for="pic2"]').text(language["kyc.tips.1"]);
			$('.pic2name').text($('#pic2').val().split('\\').pop()).siblings()
					.text(language["kyc.tips.2"]);
			$('#alilabel').html('');
		}
	</script>

</body>
</html>
