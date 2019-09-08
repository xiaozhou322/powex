<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%
	
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="${oss_url}/static/front/css/kline_trade.css?v=20181126201750" rel=stylesheet type=text/css />
</head>
<body>
	<div class="main_box">
		<div class="k_img">
			<div style="width:96%;height:455px;margin:0px auto;" id="graphbox">
				<div style="height: 410px; min-width: 460px" id="container"
					data-highcharts-chart="0">
					
				</div>
				<div
					style="width: 98%;height: 30px;line-height: 30px; marign:0 auto;text-align:center"
					class="btn-group centered" data="ybc" id="chart-control">
					<button class="btn" data-time="5m">5分钟线</button>
					<button class="btn" data-time="15m">15分钟线</button>
					<button class="btn" data-time="30m">30分钟线</button>
					<button class="btn btn-success" data-time="1h">1小时线</button>
					<button class="btn" data-time="8h">6小时线</button>
					<button class="btn " data-time="1d">日线</button>
				</div>
			</div>
		</div>

	</div>

<input type="hidden" id="coinname" value="${fvirtualcointype.fShortName }2CNY">
<script type="text/javascript" src="${oss_url}/static/front2018/js/newKline/jquery-1.8.2.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/newKline/highstock.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/newKline/exporting.js?v=20181126201750"></script>
<script src="/kline/trade_json.html?id=${param.id }"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/newKline/kline_trade.js?v=20181126201750"></script>

</body>
</html>
