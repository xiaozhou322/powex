<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ include file="../comm/include.inc.jsp"%>

<!doctype html>
<html>
<head>
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp"%>
</head>
<style type="text/css">
html, body {
	width: 100%;
	min-width: 100%;
	height: auto;
}

.area {
	background: #FAFAFA;
	padding: 0 32px 30px 32px;
}

.care {
	background: #fff;
	padding: 20px;
}

canvas {
	width: 100px;
	height: 100px;
}

.areah2 {
	clear: both;
	overflow: hidden;
	height: 60px;
	line-height: 60px;
}

.qrcodedl {
	display: block;
	text-align: center;
}

.unie62b {
	color: #333333;
}

.unie62b img {
	width: 14px;
	margin-right: 8px;
	vertical-align:-1px;
}
</style>
<body style="background: #fff;">

	<div class="charge_address_01" style="display: block;">
		<%--     <h2 class="assetTitle clear">
        <spring:message code="financial.waladd" />  
        <a href="javascript:;" class="fr s_btn cgreen charge_btns">
          <spring:message code="new.charging" />  
          <svg class="icon sfont16" aria-hidden="true">
            <use xlink:href="#icon-youjiantouda"></use>
          </svg>
      	</a>
    </h2> 
    <div></div> --%>
		<div class="area">
			<h2 class="areah2">
				<span
					style="font-size: 18px; color: #333333; float: left; padding-right: 10px;"><spring:message
						code="financial.waladd" /></span> <a href="javascript:;"
					class="fl cgreen charge_btns"> <spring:message
						code="new.charging" /> <svg class="icon sfont16"
						aria-hidden="true">
	            <use xlink:href="#icon-youjiantouda"></use>
	          </svg>
				</a>
			</h2>

			<div class="c_addressCon">
				<c:if test="${fvirtualaddress.fadderess == null}">
					<div class="get_dzBtn getCoinAddress opa-link"
						data-fid="${fvirtualcointype.fid }">
						<spring:message code="financial.getadd" />
						&gt;&gt;
					</div>
				</c:if>
				<c:if test="${fvirtualaddress.fadderess != null}">
					<dl class="qrcodedl">
						<dt id="qrcode"></dt>
						<dd>
							<p class="text" id="link">
								<c:if test="${fvirtualcointype.fisBts==true }">${fvirtualcointype.fname }<spring:message
										code="newmobile.zhaccount" />：${fvirtualcointype.mainAddr }<br />MEMO：</c:if>${fvirtualaddress.fadderess}
							</p>
							<span class="linkCopy cblue userInvite-linkCopy  btn-danger"
								data-clipboard-target="#link"><spring:message
									code="introl.affi.copylink" /></span> <span
								class="userInvite lightBlue userInvite-linktips"><spring:message
									code="introl.affi.copysuc" />√</span>
						</dd>
					</dl>
				</c:if>

			</div>
			<div class="care" style="line-height: 30px; color: #99999A;">
				<h3 class="unie62b">
					<img src="${oss_url}/static/front2018/images/unie62b.png">
					<spring:message code="new.tips" />
				</h3>
				<c:if test="${fvirtualcointype.fisBts==true }">
					<p>${fvirtualcointype.fname }<spring:message
							code="newmobile.please" />
					</p>
				</c:if>
				<p class="txt">
					<spring:message code="financial.aftyoufill"
						arguments="${fvirtualcointype.fname }" />
					<br />
					<spring:message code="financial.thsminiamo"
						arguments="${fvirtualcointype.fconfirm},${fvirtualcointype.fpushMinQty},${fvirtualcointype.fShortName }" />
				</p>
			</div>
		</div>
		<span class="s_close" id='s_close' onclick='del()'>×</span>
	</div>

	<input type="hidden" id="address" value="${fvirtualaddress.fadderess}">
	<input type="hidden" id="symbol" value="${fvirtualcointype.fid }">

	<script type="text/javascript"
		src="${oss_url}/static/front2018/js/plugin/bootstrap.js?v=20171025221650.js"></script>

	<script type="text/javascript"
		src="${oss_url}/static/front2018/js/plugin/layer/layer.js?v=20171025221650.js"></script>
	<script type="text/javascript"
		src="${oss_url}/static/front2018/js/comm/util.js?v=20171025221650.js"></script>
	<script type="text/javascript"
		src="${oss_url}/static/front2018/js/comm/comm.js?v=20171025221650.js"></script>

	<script type="text/javascript"
		src="${oss_url}/static/front2018/js/language/language_<spring:message code="language.title" />.js?v=20171025221650.js"></script>
	<script type="text/javascript"
		src="${oss_url}/static/front2018/js/comm/msg.js?v=20181126201750"></script>

	<script type="text/javascript"
		src="${oss_url}/static/front2018/js/finance/account.recharge.js?v=20181126201750"></script>
	<script type="text/javascript"
		src="${oss_url}/static/front2018/js/plugin/jquery.qrcode.min.js?v=20181126201750"></script>


	<script type="text/javascript">
		jQuery(document).ready(function() {
			if (navigator.userAgent.indexOf("MSIE") > 0) {
				jQuery('#qrcode').qrcode({
					text : '${fvirtualaddress.fadderess}',
					width : "149",
					height : "143",
					render : "table"
				});
			} else {
				jQuery('#qrcode').qrcode({
					text : '${fvirtualaddress.fadderess}',
					width : "149",
					height : "143"
				});
			}

		});
	</script>

	<script type="text/javascript"
		src="${oss_url}/static/front2018/js/plugin/clipboard.min.js?v=20181126201750"></script>
	<script type="text/javascript">
		function del() {
			window.parent.document.getElementById("r_btc").remove();
		}
		/* 复制 */
		// 将【复制】按钮充当复制数据的元素载体
		var clip = new Clipboard('.userInvite-linkCopy');
		clip.on('success', function(e) {
			// 	$('.userInvite-linktips').show();
			util.showMsg("复制成功√");
			e.clearSelection();
		});
		$(".charge_btns")
				.click(
						function() {
							var symbol = $("#symbol").val();
							parent.location.href = "/account/record.html?recordType=3&symbol="
									+ symbol + "&datetype=2";
						})
	</script>
</body>
</html>
