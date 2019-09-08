<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<!doctype html>
<html lang="zh-cn">
<head>
    <meta charset="UTF-8">
    <title>专业版K线</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="/static/app/app_kline/script.js?v=2.72"></script>
    <script>
        var initRem=function(){var n=$(window).width(),r=$(window).height(),t=n>640?640:n,i=100/640*t;
            console.log($(document).width());$("html").css({fontSize:i})};
    </script>
    <style>
        *{margin:0;padding:0;}
        body{font-family: Roboto-Regular,-apple-system,Sans-Serif;-ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;}
        .btn-group{display: -webkit-flex;   display: flex;flex-direction:row;}
        .btn-group button{outline:0;font-size:11px;color:#9b9ea7;background-color: #1f222b;border:1px solid #2a2d36;width: 16.66666%; }
        .btn-group button.btn-success{
            background-color: #292c35;color:#fff;}
    </style>
</head>
<body style="background-color: #1f222b;">
<div id="chart-control" class="btn-group" data="${ftrademapping.fid}"  style="width:95%;margin:.21rem auto;height:.64rem;">
    <button data-time='5m' data-n="1" class="btn btn-success">5分钟</button>
    <button data-time="15m" data-n="2" class="btn">15分钟</button>
    <button data-time="30m" data-n="3" class="btn">30分钟</button>
    <button data-time="1h" data-n="4" class="btn">1小时</button>
    <button data-time="8h" data-n="5" class="btn">6小时</button>
    <button data-time="1d" data-n="5" class="btn">日线</button>
</div>
<div style="width: 100%;" id="graphbox"></div>
<h2 style="font-size:.36rem;margin-bottom:.24rem;font-weight:300; text-align: center;color:#eee;">市场深度</h2>
<div style="width: 100%;" id="depth"></div>
<script>
var fSymbol = "${ftrademapping.fvirtualcointypeByFvirtualcointype1.fSymbol}";
    document.write('<s'+'cript src="/real/appperiod.html?symbol='+$("#chart-control").attr('data')+'&t='+Math.random()+'"></scr'+'ipt>');

    document.write('<s'+'cript src="/static/app/app_kline/highcharts-custom.js?t=33ddddddddd33dddsdsddddd32d3d&fSymbol='+fSymbol+'"></scr'+'ipt>');
	initRem();
</script>
</body>
</html>