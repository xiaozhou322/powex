	<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
	<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
	<%@ include file="../comm/include.inc.jsp"%>
	<%
	
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
	String basePath2 = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
	%>

	<!doctype html>
	<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>
	<link rel="stylesheet" href="${oss_url}/static/front/css/fullkline/fullScreenKline2018.css?v=20181126201750" type="text/css"></link>
	<style type="text/css">
			.study_span,.time_span  {color: #a6b2c8;margin:0 10px;display: inline-block;text-align: center;font-size: 12px;text-decoration: none}
			.study_span.active,.time_span.active {border: 1px solid #a6b2c8;border-radius: 2px;}
			.study_span:hover,.time_span:hover {color:#fff}
	</style>
	</head>
	<!-- Chart Container -->
	<body>
		<div id="tv_chart_container" style="height: 100%;width: 100%;">
        </div>
		<input type="hidden" id="coinId" value="${ftrademapping.fid}"/>
		<input type="hidden" id="coinName2" value="${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName}"/>
		<input type="hidden" id="coinName1" value="${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName}"/>
		<script src="${oss_url}/static/front/js/tradingview/charting_library/charting_library.min.js?v=6" type="text/javascript" charset="utf-8"></script>
        <script src="${oss_url}/static/front/js/tradingview/dataUpdater.js" type="text/javascript" charset="utf-8"></script>
        <script src="${oss_url}/static/front/js/tradingview/datafees.js" type="text/javascript" charset="utf-8"></script>
        <script src="${oss_url}/static/front/js/tradingview/socket.js" type="text/javascript" charset="utf-8"></script>
        <script src="${oss_url}/static/front/js/tradingview/TVjsApi.js?v=34" type="text/javascript" charset="utf-8"></script>
        <script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.min.js?v=20171026105823.js?v=20181126201750"></script>
        <script>
        
           var path = "${oss_url}/static/front/js/tradingview/";
           
           //读取json配置文件
           var configJSON = '${oss_url}/static/front/config/config.json?t='+new Date().getTime();
           
           var wsUrl;
           $.ajax({
        	   url: configJSON,//json文件位置
        	   type: "GET",//请求方式为get
        	   async: false, //请求是否异步，默认为异步，这也是ajax重要特性   
        	   dataType: "json", //返回数据格式为json
        	   success: function(data) {//请求成功完成后要执行的方法 
        		   wsUrl = data.quotation_ws_url;
        	   }
        	})
        	
            var coinId = $("#coinId").val();
            var coinName1 = $("#coinName1").val();
            var coinName2 = $("#coinName2").val();
            var _TVjsApi = new TVjsApi(coinName2+"/"+coinName1,coinId, wsUrl);
            _TVjsApi.init();
        </script>
	</body>

	</html>
