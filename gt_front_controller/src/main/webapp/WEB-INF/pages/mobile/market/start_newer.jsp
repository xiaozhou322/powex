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
	 <meta name = "viewport" content = "width = device-width, user-scalable = no,initial-scale=0.5,maximum-scale=0.5,minimum-scale=0.5">
 
	<title></title>
	<link rel="stylesheet" href="${oss_url}/static/mobile2018/css/fullkline/fullScreenKline.css?v=20181126201750" type="text/css"></link>
	 <script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
	 <script src="${oss_url}/static/mobile2018/js/h5kline/loading.js" type="text/javascript" charset="GBK"></script>
    <script src="${oss_url}/static/mobile2018/js/h5kline/util.js?v=13" type="text/javascript" charset="GBK"></script>
    <script src="${oss_url}/static/mobile2018/js/h5kline/absPainter.js" type="text/javascript" charset="GBK"></script>
    <script src="${oss_url}/static/mobile2018/js/h5kline/ajax.js" type="text/javascript" charset="GBK"></script>
    <script src="${oss_url}/static/mobile2018/js/h5kline/crossLines.js" type="text/javascript" charset="GBK"></script>
    <script src="${oss_url}/static/mobile2018/js/h5kline/axis-x.js" type="text/javascript" charset="GBK"></script>
    <script src="${oss_url}/static/mobile2018/js/h5kline/tip.js" type="text/javascript" charset="GBK"></script>
    <script src="${oss_url}/static/mobile2018/js/h5kline/linepainter.js" type="text/javascript" charset="GBK"></script>
    <script src="${oss_url}/static/mobile2018/js/h5kline/volumePainter.js" type="text/javascript" charset="GBK"></script>
    <script src="${oss_url}/static/mobile2018/js/h5kline/axis-y.js?v=1" type="text/javascript" charset="GBK"></script>
    <script src="${oss_url}/static/mobile2018/js/h5kline/chartEventHelper.kl.js?a2" type="text/javascript" charset="GBK"></script>
    <script type="text/javascript" src="${oss_url}/static/mobile2018/js/h5kline/controller.js" charset="GBK"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/comm/util.js?v=20171025221650.js"></script>

	
	</head>
    <style type="text/css">
    .showTime{background:#262a42; overflow:hidden; color:#c7ccda;}
    .showTime>div{width:20%; text-align:center; float:left;}
    .showTime>div span{padding:20px 5px 20px;display:inline-block; font-size:36px;font-family: arial, sans;}
    .showTime>div.active span{border-bottom:2px solid #fff; color:#fff; font-weight:bold;}
    #canvasKL{width:100%!important; height:125%!important; margin-top:12px;}
    @media screen and (min-width:360px) and (max-width:374px){
       .showTime>div span{font-size:28px;}
       #canvasKL{height:120%!important;}
    }   
    @media screen and (min-width:375px){
       .showTime>div span{font-size:30px;}
       #canvasKL{height:128%!important;}
    }


    </style>
	<body >
	<div class="showTime">
        <div  <c:if test="${step ==300 }"> class="active" </c:if> onclick="redirect('/kline/h5.html?symbol=${ftrademapping.fid}&step=300&t=<%=new java.util.Date().getTime() %>');" ><span><spring:message code="mbtrder.market.minFive" /></span></div>
    	<div <c:if test="${step ==1800 }"> class="active" </c:if> onclick="redirect('/kline/h5.html?symbol=${ftrademapping.fid}&step=1800&t=<%=new java.util.Date().getTime() %>');" ><span><spring:message code="mbtrder.market.minThere" /></span></div>
    	<div   <c:if test="${step ==3600 }"> class="active" </c:if> onclick="redirect('/kline/h5.html?symbol=${ftrademapping.fid}&step=3600&t=<%=new java.util.Date().getTime() %>');" ><span><spring:message code="mbtrder.market.Hour" /></span></div>
    	<div  <c:if test="${step == 86400 }"> class="active" </c:if>  onclick="redirect('/kline/h5.html?symbol=${ftrademapping.fid}&step=86400&t=<%=new java.util.Date().getTime() %>');" ><span><spring:message code="mbtrder.market.daily" /></span></div>
    	<div  <c:if test="${step == 604800 }"> class="active" </c:if>  onclick="redirect('/kline/h5.html?symbol=${ftrademapping.fid}&step=604800&t=<%=new java.util.Date().getTime() %>');" ><span><spring:message code="mbtrder.market.weekly" /></span></div>
    </div>

	<input type="hidden" id="rqurl" value="/kline/fullperiod.html?symbol=${ftrademapping.fid}&step=${step}&t=<%=new java.util.Date().getTime() %>">
	<canvas  id="canvasKL"  width="100%" height="420" style="z-index: 2;">
	<%--  <script type="text/javascript" src="${oss_url}/static/mobile2018/js/h5kline/k-data.js?v=20181126201750"></script> --%>
	</canvas >
	<div id="loadbox" style="margin:0 auto;height:100%;line-height:25px;width:100%"><img id="loadingDiv" src="${oss_url}/static/mobile2018/images/load123.gif?v=1" style="width: 100px;height: 100px;position:absolute;z-index:999;top:50%;margin-top: -37px;left:50%;margin-left: -37px;"></div>
	
	</body>
	<input type="hidden" id="coinId" value="${ftrademapping.fid}"/>
	<input type="hidden" id="coinName" value="${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName}"/>
	<script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.min.js?v=20171026105823.js?v=20181126201750"></script>
	
	<script type="text/javascript" src="${oss_url}/static/front/js/language/language_<spring:message code="language.title" />.js?v=20171026105823.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/mobile2018/js/jquery-1.11.2.min.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/index/jquery.cookie.js?v=6"></script>
    
    <script type="text/javascript">
    $(".showTime div").click(function(event) {
        $(this).addClass('active').siblings().removeClass();
    });
    </script>

	<script type="text/javascript">
		 // function convertDate(val, withWeek) {
   //          var year = Math.ceil(val / 10000) - 1;
   //          var day = val % 100;
   //          var month = (Math.ceil(val / 100) - 1) % 100;
   //          var d = new Date();
   //          d.setYear(year);
   //          d.setMonth(month - 1);
   //          d.setDate(day);
   //          if (month < 10) month = '0' + month;
   //          if (day < 10) day = '0' + day;
   //          if (withWeek) {
   //              var weekNames = ['日', '一', '二', '三', '四', '五', '六'];
   //              return year + '-' + month + '-' + day + '(星期' + weekNames[d.getDay()] + ')';
   //          }
   //          else {
   //              return year + '-' + month + '-' + day;
   //          }
   //      }
        function convertDate(val, withWeek) {
            var now = new   Date(val);
            return   formatDate(now);
          
        }
        
        function formatDate(now) {
            var year = now.getFullYear();
            var month = now.getMonth() + 1;
            var date = now.getDate();
            var hour = now.getHours();
            var minute = now.getMinutes();
            var second = now.getSeconds();
            return year + "-" + month + "-" + date + "   " + getzf(hour) + ":" + getzf(minute);
        }


        function getzf(num) {
            if (parseInt(num) < 10) {
                num = '0' + num;
            }
            return num;
        }

        function calcMAPrices(ks, startIndex, count, daysCn) {
            var result = new Array();
            for (var i = startIndex; i < startIndex + count; i++) {
                var startCalcIndex = i - daysCn + 1;
                if (startCalcIndex < 0) {
                    result.push(false);
                    continue;
                }
                var sum = 0;
                for (var k = startCalcIndex; k <= i; k++) {
                    sum += ks[k].close;
                }
                var val = sum / daysCn;
                result.push(val);
            }
            return result;
        }

        var timer = {
            start:function(step){this.startTime = new Date();this.stepName = step;},
            stop:function(){
                var timeSpan = new Date().getTime() - this.startTime.getTime();
                setDebugMsg(this.stepName + '耗时' + timeSpan+'ms');
            }
        };

        function kLine(options) {
            this.options = options;
            this.dataRanges = null;
        }

        kLine.prototype = {
            initialize: function (painter) {
                painter.klOptions = this.options;
                painter.implement = this;
            },
            start: function () {
                //timer.start('start');
                var canvas = this.canvas;
                var ctx = this.ctx;
                this.painting = true;
                var options = this.klOptions;
                var clearPart = { width: canvas.width, height: options.priceLine.region.y - 3 };
                ctx.clearRect(0, 0, clearPart.width, clearPart.height);

                ctx.save();
                window.riseColor = options.riseColor;
                window.fallColor = options.fallColor;
                window.normalColor = options.normalColor;
                if (options.backgroundColor && !this.drawnBackground) {
                    ctx.beginPath();
                    ctx.fillStyle = options.backgroundColor;
                    ctx.rect(0, 0, clearPart.width, clearPart.height);
                    ctx.fill();
                    //ctx.closePath();
                    this.drawnBackground = true;
                }
                ctx.translate(options.region.x, options.region.y);
                ctx.strokeStyle = options.borderColor;
                ctx.beginPath();
                ctx.rect(0, 0, options.region.width, options.region.height);
                ctx.stroke();
                //画水平底纹线
                var spaceHeight = options.region.height / (options.horizontalLineCount + 1);
                for (var i = 1; i <= options.horizontalLineCount; i++) {
                    var y = spaceHeight * i;
                    if (y * 10 % 10 == 0) y += .5;
                    this.drawHLine(options.splitLineColor, 0, y, options.region.width, 1, options.lineStyle);
                }
                //画垂直底纹线
                var spaceWidth = options.region.width / (options.verticalLineCount + 1);
                for (var i = 1; i <= options.verticalLineCount; i++) {
                    var w = spaceWidth * i;
                    if (w * 10 % 10 == 0) w += .5;
                    this.drawVLine(options.splitLineColor, w, 0, options.region.height, 1, options.lineStyle);
                }
                //timer.stop();
            },
            end: function () {
                this.ctx.restore();
                var me = this;
                var options = me.klOptions;
                var region = options.region;
                var volumeRegion = options.volume.region;

                function getIndex(x) {
                    x -= region.x;
                    var index = Math.ceil(x / (me.klOptions.spaceWidth + me.klOptions.barWidth)) - 1;
                    var count = me.toIndex - me.startIndex + 1;
                    if (index >= count) index = count - 1;
                    return index;
                }
                function getX(x) {
                    var index = getIndex(x);
                    return region.x + me.klOptions.spaceWidth * (index + 1) + me.klOptions.barWidth * index + me.klOptions.barWidth * .5;
                }
                function getPriceColor(ki, price) {
                    if (price > ki.preClose) {
                        return riseColor;
                    } else if (price < ki.preClose) {
                        return fallColor;
                    } else {
                        return normalColor;
                    }
                }

                function getTipHtml(x) {
                    var index = me.startIndex + getIndex(x);
                    if (index >= me.data.ks.length) index = me.data.ks.length - 1;
                    if (index < 0) index = 0;
                    var ki = me.data.ks[index];
                    var tipHtml = '<div style="line-height:22px; fotn-size:30px;"><div><div style="font-size:22px;"><b>' + convertDate(ki.quoteTime) + '</b></div>' +  
                    language["m.kline.prec"] + '：<font  style="font-size:20px;" color="red"' + getPriceColor(ki, ki.preClose) + '">' + toMoney(ki.preClose) + '</font><br/>' +
                    language["m.kline.open"] + '：<font  style="font-size:20px;" color="red"' + getPriceColor(ki, ki.open) + '">' + toMoney(ki.open) + '</font><br/>' +
                    language["m.kline.close"] +'：<font  style="font-size:20px;" color="red"' + getPriceColor(ki, ki.high) + '">' + toMoney(ki.high) + '</font><br/>' +
                    language["m.kline.lowest"] +'：<font  style="font-size:20px;" color="red"' + getPriceColor(ki, ki.low) + '">' + toMoney(ki.low) + '</font><br/>' +
                    language["m.kline.highest"] +'：<font  style="font-size:20px;" color="red"' + getPriceColor(ki, ki.close) + '">' + toMoney(ki.close) + '</font><br/>' +
                    language["m.kline.volume"] +'：' + ki.volume+'</div>' ;
                    return tipHtml;
                }

                function getEventOffsetPosition(ev){                    
                    var offset = isTouchDevice()
                        ? setTouchEventOffsetPosition(ev, getPageCoord(me.canvas))
                        : getOffset(ev);
                    return offset;
                }
                
                var controllerEvents = {
                    onStart:function(ev){
                        ev = ev || event;
                        var offset = getEventOffsetPosition(ev);
                        me.controllerStartOffset = offset;
                        me.controllerStartRange = me.dataRanges;
                    },
                    onEnd:function(ev){
                        me.controllerStartOffset = null;
                        me.controllerStartRange = null;
                    },
                    onMove:function(ev){
                        if(painter.data.ks.length<30)return;
                        if(!me.controllerStartOffset) return;
                        ev = ev || event;
                        var offset = getEventOffsetPosition(ev);
                        var moveX = offset.offsetX - me.controllerStartOffset.offsetX;
                        var currentRange = me.controllerStartRange;/*0-100*/
                        var regionWidth = region.width;
                        var moveValue =0- (moveX/regionWidth)*(currentRange.to-currentRange.start);
                        var start = currentRange.start+moveValue;
                        var to = currentRange.to + moveValue;
                        if(start<0) {
                            start = 0;
                            to = start + (currentRange.to-currentRange.start);
                        }
                        else{
                            if(to > 100){
                                to = 100;
                                start = to-(currentRange.to-currentRange.start);
                            }
                        }                        
                        var changeToValue = {left:start,right:to};
                        if(!me.painting) drawKL({ start: changeToValue.left, to: changeToValue.right });
                    }
                };

                /*
                	当touchstart时的位置在K线柱附近时表示要显示柱的描述信息框；否则则要控制K线的范围
                */
                var fingerSize = {width:0.5,height:0.5};
                function shouldDoControllerEvent(ev,evtType){
                    if(evtType == undefined) return true;
                    if(typeof me.shouldController == 'undefined') me.shouldController = true;
                    if(evtType == 'touchstart'){
                        var offset = getEventOffsetPosition(ev);
                        var showTip=true;

                        var x = offset.offsetX;
                        x -= region.x;
                        var index = Math.ceil(x / (me.klOptions.spaceWidth + me.klOptions.barWidth)) - 1;

                        var indexRange = Math.ceil(fingerSize.width / (me.klOptions.spaceWidth + me.klOptions.barWidth))+1;

                        var indexStart = Math.max(0,index - indexRange/2);
                        var indexTo = Math.min(me.filteredData.length-1,index+indexRange/2);
                        var yMin=999999;
                        var yMax=-1;
                        for(index = indexStart;index<=indexTo;index++){
                            var dataAtIndex = me.filteredData[index];
                            var yTop = region.y+ (me.high - dataAtIndex.high) * region.height / (me.high - me.low) - fingerSize.height;
                            var yBottom = region.y+(me.high - dataAtIndex.low) * region.height / (me.high - me.low) + fingerSize.height;
                            yMin = Math.min(yTop,yMin);
                            yMax = Math.max(yBottom,yMax);
                        }
                        //showTip = offset.offsetY >= yMin && offset.offsetY <= yMax;
                        setDebugMsg('yMin='+yMin + ',yMax=' + yMax + ',offsetY='+offset.offsetY+',showTip=' + showTip);
                        me.shouldController = !showTip;
                        
                    }
                    //setDebugMsg('shouldController=' + me.shouldController);
                    return me.shouldController;
                }
				
                if(!me.crossLineAndTipMgrInstance){
                    me.crossLineAndTipMgrInstance = new crossLinesAndTipMgr(me.canvas, {
                        getCrossPoint: function (ev) { return { x: getX(ev.offsetX), y: ev.offsetY }; },
                        triggerEventRanges: { x: region.x, y: region.y + 1, width: region.width, height: volumeRegion.y + volumeRegion.height - region.y },
                        tipOptions: {
                            getTipHtml: function (ev) { return getTipHtml(ev.offsetX); },
                            size:{width:180,height:170},
                            position:{x:false,y:region.y+(region.height-150)/3}, //position中的值是相对于canvas的左上角的
                            opacity:80,
                            cssClass:'',
                            offsetToPoint:30
                        },
                        crossLineOptions: {
                            color: '#444'
                        },
                        shouldDoControllerEvent:shouldDoControllerEvent,
                        controllerEvents:controllerEvents
                    });
                    me.crossLineAndTipMgrInstance.addCrossLinesAndTipEvents();
                }
                else {
                    me.crossLineAndTipMgrInstance.updateOptions({
                            getCrossPoint: function (ev) { return { x: getX(ev.offsetX), y: ev.offsetY }; },
                            triggerEventRanges: { x: region.x, y: region.y + 1, width: region.width, height: volumeRegion.y + volumeRegion.height - region.y },
                            tipOptions: {
                                getTipHtml: function (ev) { return getTipHtml(ev.offsetX); },
                                size:{width:180,height:170},
                                position:{x:false,y:region.y+(region.height-150)/3}, //position中的值是相对于canvas的左上角的
                                opacity:80,
                                cssClass:'',
                                offsetToPoint:30
                            },
                            crossLineOptions: {
                                color: '#444'
                            },
                            shouldDoControllerEvent:shouldDoControllerEvent,
                            controllerEvents:controllerEvents
                        });
                }
                
                if (!me.addOrentationChangedEvent) {
                    me.addOrentationChangedEvent = true;

                    addEvent(window, 'orientationchange', function (ev) {
                        me.requestController = true;
                        me.implement.onOrentationChanged.call(me);
                    });
                }

                me.painting = false;
            },
            paintItems: function () {
                var options = this.klOptions;
                var region = options.region;
                var maxDataLength = this.data.ks.length;
                var needCalcSpaceAndBarWidth = true;
                if (this.dataRanges == null) {
                    //计算dataRanges
                    var dataCount = Math.ceil(region.width / (options.spaceWidth + options.barWidth))-1;
                    if (dataCount > maxDataLength) dataCount = maxDataLength;

                    this.dataRanges = {
                        start: 100 * (this.data.ks.length - dataCount) / this.data.ks.length,
                        to: 100
                    };

                    needCalcSpaceAndBarWidth = false;
                }
                var dataRanges = this.dataRanges;
                var startIndex = Math.ceil(dataRanges.start / 100 * maxDataLength);
                var toIndex = Math.ceil(dataRanges.to / 100 * maxDataLength) + 1;
                if (toIndex == maxDataLength) toIndex = maxDataLength - 1;
                this.startIndex = startIndex;
                this.toIndex = toIndex;
                var itemsCount = toIndex - startIndex + 1;
                if (needCalcSpaceAndBarWidth) {
                    //重新计算spaceWidth和barWidth属性
                    function isOptionsOK() { return (options.spaceWidth + options.barWidth) * itemsCount <= region.width; }
                    var spaceWidth, barWidth;
                    if (isOptionsOK()) {
                        //柱足够细了
                        spaceWidth = 1;
                        barWidth = (region.width - spaceWidth * itemsCount) / itemsCount;
                        if (barWidth > 4) {
                            spaceWidth = 2;
                            barWidth = ((region.width - spaceWidth * itemsCount) / itemsCount);
                        }
                    } else {
                        spaceWidth = 1;
                        barWidth = (region.width - spaceWidth * itemsCount) / itemsCount;
                        if (barWidth <= 2) {
                            spaceWidth = 0;
                            barWidth = (region.width - spaceWidth * itemsCount) / itemsCount;
                        } else if (barWidth > 4) {
                            spaceWidth = 2;
                            barWidth = ((region.width - spaceWidth * itemsCount) / itemsCount);
                        }
                    }

                    options.barWidth = barWidth;
                    options.spaceWidth = spaceWidth;
                }

                var filteredData = [];
                for (var i = startIndex; i <= toIndex && i < maxDataLength; i++) {
                    filteredData.push(this.data.ks[i]);
                }
                var high, low;
                filteredData.each(function (val, a, i) {
                    if (i == 0) { high = val.high; low = val.low; }
                    else { high = Math.max(val.high, high); low = Math.min(low, val.low); }
                });
				
                this.high = high;
                this.low = low;
                var ctx = this.ctx;
                var me = this;
                //timer.start('paintItems:移动均线');
                //画移动平均线
                this.implement.paintMAs.call(this, filteredData, getY);
                //timer.stop();
                //timer.start('paintItems:画柱');
                function getY(price) { return (me.high - price) * region.height / (me.high - me.low); }
                function getCandleLineX(i) { var result = i * (options.spaceWidth + options.barWidth) + (options.spaceWidth + options.barWidth) * .75; if (result * 10 % 10 == 0) result += .5; return result; }

                var currentX = 0;
                var needCandleRect = options.barWidth > 1.5;
                var drawCandle = function (ki, a, i) {
                    var isRise = ki.close > ki.open;
                    var color = isRise ? riseColor : fallColor;

                    var lineX = getCandleLineX(i);
                    if (currentX == 0) currentX = lineX;
                    else {
                        if (lineX - currentX < 1) return;
                    }
                    currentX = lineX;
                    var topY = getY(ki.high);
                    var bottomY = getY(ki.low);
                    if (needCandleRect) {
                        ctx.fillStyle = color;
                        ctx.strokeStyle = color;
                        var candleY, candleHeight;
                        if (isRise) {
                            candleY = getY(ki.close);
                            candleHeight = getY(ki.open) - candleY;
                        } else {
                            candleY = getY(ki.open);
                            candleHeight = getY(ki.close) - candleY;
                        }
						
                        //画线
                        ctx.beginPath();
                        ctx.moveTo(lineX, topY);
                        ctx.lineTo(lineX, bottomY);
                        ctx.stroke();

                        var candleX = lineX - options.barWidth / 2;
                        ctx.beginPath();
                        ctx.fillRect(candleX, candleY, options.barWidth, candleHeight);
                    } else {
                        ctx.strokeStyle = color;
                        //画线
                        ctx.beginPath();
                        ctx.moveTo(lineX, topY);
                        ctx.lineTo(lineX, bottomY);
                        ctx.stroke();
                    }

                };
                //画蜡烛
                filteredData.each(drawCandle);
                this.filteredData = filteredData;
                //timer.stop();
                //timer.start('paintItems:纵轴');
                var yAxisOptions = options.yAxis;
                yAxisOptions.region = yAxisOptions.region || { x: 0 - region.x, y: 0 - 3, height: region.height, width: region.x - 3 };
                //画y轴

                var yAxisImp = new yAxis(yAxisOptions);
                var yPainter = new Painter(
                    this.canvas.id,
                    yAxisImp,
                    calcAxisValues(high, low, (options.horizontalLineCount + 2))
                );
                yPainter.paint();
                //timer.stop();
                //timer.start('paintItems:横轴');
                //画X轴
                var xAxisOptions = options.xAxis;
                xAxisOptions.region = { x: 0, y: region.height + 2, width: region.width, height: 20 };
                var xAxisImp = new xAxis(xAxisOptions);
                var xScalers = [];
                var stepLength = filteredData.length / (options.xAxis.scalerCount - 1);
                if (stepLength < 1) {
                    options.xAxis.scalerCount = filteredData.length;
                    stepLength = 1;
                }
                xScalers.push(convertDate(filteredData[0].quoteTime, false).substr(2));
                for (var i = 1; i < options.xAxis.scalerCount; i++) {
                    var index = Math.ceil(i * stepLength);
                    if (index >= filteredData.length) index = filteredData.length - 1;
                    var quoteTime = convertDate(filteredData[index].quoteTime, false);
                    quoteTime = quoteTime.substr(2);
                    xScalers.push(quoteTime);
                }
               // var xPainter = new Painter(this.canvas.id, xAxisImp, xScalers);
               // xPainter.paint();
               // timer.stop();

                //timer.start('volume');
                //画量
                this.implement.paintVolume.call(this, filteredData);
                //timer.stop();
                //画价格线                
                //this.implement.paintPriceLine.call(this);
            },
            paintPriceLine: function () {
                if (this.hasPaintPriceLine) return;
                this.hasPaintPriceLine = true;
                var ctx = this.ctx;
                var options = this.klOptions.priceLine;
                var region = options.region;
                ctx.save();
                ctx.translate(region.x, region.y);

                ctx.clearRect(0 - region.x, 0, this.canvas.width, region.height);
                //画水平底纹线
                var spaceHeight = region.height / (options.horizontalLineCount + 1);
                for (var i = 1; i <= options.horizontalLineCount; i++) {
                    var y = spaceHeight * i;
                    if (y * 10 % 10 == 0) y += .5;
                    this.drawHLine(options.splitLineColor, 0, y, region.width, 1, options.lineStyle);
                }
                //画垂直底纹线
                var spaceWidth = region.width / (options.verticalLineCount + 1);
                for (var i = 1; i <= options.verticalLineCount; i++) {
                    var w = spaceWidth * i;
                    if (w * 10 % 10 == 0) w += .5;
                    this.drawVLine(options.splitLineColor, w, 0, region.height, 1, options.lineStyle);
                }
                var ks = this.data.ks;
                var ksLength = ks.length;
                var priceRange;
                ks.each(function (val, arr, i) {
                    if (i == 0) { priceRange = { high: val.high, low: val.low }; }
                    else {
                        priceRange.high = Math.max(priceRange.high, val.close);
                        priceRange.low = Math.min(priceRange.low, val.close);
                    }
                });
                if (priceRange.low > 1) priceRange.low -= 1;
                function getRangeX(i) { return i * region.width / ksLength; }
                function getRangeY(val) { return (priceRange.high - val) * region.height / (priceRange.high - priceRange.low); }
                var currentX = 0;
                ks.each(function (val, arr, i) {
                    var x = getRangeX(i);
                    if (currentX == 0) currentX = x;
                    else {
                        if (x - currentX < 1) return;
                    }
                    currentX = x;
                    var y = getRangeY(val.close);
                    if (i == 0) {
                        ctx.beginPath();
                        ctx.moveTo(x, y);
                    } else {
                        ctx.lineTo(x, y);
                    }
                });
                ctx.strokeStype = options.borderColor;
                ctx.stroke();
                ctx.lineTo(region.width, region.height);
                ctx.lineTo(0, region.height);
                ctx.closePath();
                ctx.fillStyle = options.fillColor;
                ctx.globalAlpha = options.alpha;
                ctx.fill();
                ctx.globalAlpha = 1;
                var yAxisOptions = options.yAxis;
                yAxisOptions.region = yAxisOptions.region || { x: 0 - region.x, y: 0 - 3, height: region.height, width: region.x - 3 };
                //画y轴
                var yAxisImp = new yAxis(yAxisOptions);
                var yPainter = new Painter(
                    this.canvas.id,
                    yAxisImp,
                    calcAxisValues(priceRange.high, priceRange.low, (options.horizontalLineCount + 2))
                );

                yPainter.paint();
                ctx.restore();
            },
            paintMAs: function (filteredData, funcGetY) {
                var ctx = this.ctx;
                var options = this.klOptions;
                var MAs = options.MAs;
                var me = this;
                MAs.each(function (val, arr, index) {
                    var MA = calcMAPrices(me.data.ks, me.startIndex, filteredData.length, val.daysCount);
                    val.values = MA;
                    MA.each(function (val, arr, i) {
                        if (val) {
                            me.high = Math.max(me.high, val);
                            me.low = Math.min(me.low, val);
                        }
                    });
                });

                MAs.each(function (val, arr, index) {
                    var MA = val.values;
                    ctx.strokeStyle = val.color;
                    ctx.beginPath();
                    var currentX = 0;
                    MA.each(function (val, arr, i) {
                        var x = i * (options.spaceWidth + options.barWidth) + (options.spaceWidth + options.barWidth) * .5;
                        
                        if (!val) return;
                        var y = funcGetY(val);
                        if (y && i==0) {
                            ctx.moveTo(x, y);
                        } else {
                            ctx.lineTo(x, y);
                        }
                    });
                    ctx.stroke();
                });
            },
            paintVolume: function (filteredData) {
				
				
			
				
                var ctx = this.ctx;
                var options = this.klOptions;
                //画量线
                var volumeOptions = options.volume;
                var volumeRegion = volumeOptions.region;
                ctx.restore();
                ctx.save();
                ctx.translate(volumeRegion.x, volumeRegion.y);
                ctx.globalAlpha = 1;
                //画水平底纹线
                var spaceHeight = volumeRegion.height / (volumeOptions.horizontalLineCount + 1);
                for (var i = 1; i <= volumeOptions.horizontalLineCount; i++) {
                    var y = spaceHeight * i;
                    if (y * 10 % 10 == 0) y += .5;
                    this.drawHLine(options.splitLineColor, 0, y, options.region.width, 1, options.lineStyle);
                }
                //画垂直底纹线
                var spaceWidth = options.region.width / (options.verticalLineCount + 1);
                for (var i = 1; i <= options.verticalLineCount; i++) {
                    var w = spaceWidth * i;
                    if (w * 10 % 10 == 0) w += .5;
                    this.drawVLine(options.splitLineColor, w, 0, volumeRegion.height, 1, options.lineStyle);
                }

                ctx.strokeStyle = options.borderColor;
                ctx.beginPath();
                ctx.rect(0, 0, volumeRegion.width, volumeRegion.height);
                ctx.stroke();
                //drawLines(ctx, [{ direction: 'H', position: .50, color: 'lightgray'}]);
                var maxVolume = 0;

                filteredData.each(function (val, arr, i) {
                    maxVolume = Math.max(maxVolume, val.volume);
                    
                });
                maxVolume *= 1.05;
                function getVolumeY(v) { return volumeRegion.height - volumeRegion.height / maxVolume * v; }
                function getVolumeX(i) { return i * (options.spaceWidth + options.barWidth) + (options.spaceWidth) ; }
                ctx.globalAlpha = 1;
                filteredData.each(function (val, arr, i) {
                    var x = getVolumeX(i);
                    var y = getVolumeY(val.volume);
                    
                    ctx.beginPath();
                    ctx.rect(x, y, options.barWidth, volumeRegion.height / maxVolume * val.volume);
                    ctx.fillStyle = val.close > val.open ? riseColor : fallColor;
                    ctx.fill();
                });

                //画y轴
                var volumeLevel;
                var volumeUnit;
                if (maxVolume > 10000) {
                    volumeLevel = 10000;
                    volumeUnit = language["l.kline.wans"];
                }
                else {
                    volumeLevel = 1;
                    volumeUnit = '';
                }

                var volumeScalers = [];
                volumeScalers.push((maxVolume / volumeLevel).toFixed(2));
                
                volumeScalers.push((maxVolume / 2 / volumeLevel).toFixed(2));
                volumeScalers.push(volumeUnit);
				//console.log(volumeScalers)
                var volumeScalerOptions = volumeOptions.yAxis;
                volumeScalerOptions.region = volumeScalerOptions.region || { x: 0 - volumeRegion.x, y: -3, width: volumeRegion.x - 3, height: volumeRegion.height };
                var volumeScalerImp = new yAxis(volumeScalerOptions);
                var volumeScalerPainter = new Painter(this.canvas.id, volumeScalerImp, volumeScalers);
                volumeScalerPainter.paint();
                ctx.restore();
                ctx.save();
            },
            onOrentationChanged: function (e) {
                var orientation = window.orientation;
                function getWidth() { return screen.width-40;/*((orientation == 90 || orientation == -90) ? screen.width : screen.height) - 40; */}
                if (typeof orientation != 'undefined') {
                    setDebugMsg('orientation=' + orientation + ',getWidth=' + getWidth());
                    //if(orientation == 90 || orientation == -90 || orientation == 0){
                    var me = this;
                    var width = getWidth();
                    //var rate = width/me.canvas.width;
                    me.canvas.width = width;
                    var options = me.klOptions;
                    var chartWidth = width - options.chartMargin.left - options.chartMargin.right;
                    me.klOptions.volume.region.width =
                            me.klOptions.priceLine.region.width =
                            me.klOptions.region.width = chartWidth;
                    //方向改变了，要重画controller
                    me.controller = null;
                    me.hasPaintPriceLine = false;
                    drawKL({ start: me.dataRanges.start, to: me.dataRanges.to });
                   
                    // }
                }
            }
        };

        
        var painter;// = new Painter('canvasKL', kl, data);
        var initialWidth = Math.min(screen.width,1024)-20;
        function drawKL(ranges) {            
            var kOptions = {
                backgroundColor:'#1e2136',
                riseColor: '#e43c59',
                fallColor: '#46A656',
                normalColor: '#444',
                //主图区域的边距
                chartMargin:{left:55.5,top:20.5,right:20.5},
                region: { x: 55.5, y: 20.5, width: initialWidth  - 55.5 , height: 150 },
                barWidth: 5, spaceWidth: 5, horizontalLineCount: 7, verticalLineCount: 7, lineStyle: 'solid', borderColor: '#cccccc', splitLineColor: '#eeeeee', lineWidth: 1,

                MAs: [
                   	{color: 'rgb(255,70,251)', daysCount: 7 },
                    //{ color: 'rgb(227,150,34)', daysCount: 10 },
                    { color: 'rgb(53,71,107)', daysCount: 30 }/*,//表格里右边兰线
                    { color: 'rgb(0,0,0)', daysCount: 60 }*/

                    ],
                yAxis: {
                    font: '12px Arial', // region: { },
                    color: '#85aae1',
                    align: 'right',
                    fontHeight: 8,
                    textBaseline: 'top'
                },
                xAxis: {
                    font: '12px Arial', // region: { },
                    color: '#85aae1',
                    align: 'right',
                    fontHeight: 8,
                    textBaseline: 'top',
                    scalerCount: 9
                },
                volume: {
                    region: { x: 55.5, y: 180.5, height: 80, width: initialWidth - 55.5 },
                    horizontalLineCount: 1,
                    yAxis: {
                        font: '12px Arial', // region: { },
                        color: '#85aae1',
                        align: 'right',
                        fontHeight: 8,
                        textBaseline: 'top'
                    }
                },
                priceLine: {
                    region:{ x: 55.5, y: 380.5, height: 60, width: initialWidth - 55.5 - 20.5 },
                    verticalLineCount: 7,
                    horizontalLineCount: 1, lineStyle: 'solid', borderColor: 'gray', splitLineColor: '#eeeeee',fillColor:'lightgray',alpha:.5,
                    yAxis: {
                        font: '12px Arial', // region: { },
                        color: '#85aae1',
                        align: 'right',
                        fontHeight: 8,
                        textBaseline: 'top'
                    }
                },
                controller:{
                    bar: { width: 20, height: 35, borderColor: '#444', fillColor: 'lightgray' },
                    minBarDistance: 20
                }
               
            };

           
            //if(!painter){            
                var canvas = $id('canvasKL');
                if(canvas.width != initialWidth) canvas.width = initialWidth;
                var kl = new kLine(kOptions);
                 $.get($('#rqurl').val(),function(data){
                 	var data = eval('('+data+')');
                 	var data = getKLData(data);
                	painter = new Painter('canvasKL', kl, data);
                	painter.dataRanges = ranges;
            		painter.paint();
                 })
                
               
            //}
          
        }
        
        function getKLData(tempresult) {

			var cointype = $.cookie('cointype');
			var rate = 1;
			var coinclass = '';
			if(typeof cointype!='undefined'&&cointype!='')
			{
				
				 rate = cointype=='CNY'?6.5:1;
				 coinclass = 'coin_price_'+cointype;
			}
			
        	var preClose = 0;
        	var temp = [];
			
        	for (var i = 0; i < tempresult.length; i++) {
        		var temprow = tempresult[i];
        		temp[temp.length] = [temprow[0],preClose,temprow[1],temprow[2],temprow[3],temprow[4],temprow[5],temprow[5]*temprow[1]]
        		preClose = temprow[4]
        	}
        	//console.log(temp);
			//console.log(preClose);
         	var result = {};
		    var ks = [];
		    var data = temp;
		  		    
		    for (var i = 0; i < data.length; i++) {
		        var rawData = data[i];
		        var item = {
		            quoteTime: rawData[0],
		            preClose: rawData[1]*rate,
		            open: rawData[2]*rate,
		            high: rawData[3]*rate,
		            low: rawData[4]*rate,
		            close: rawData[5]*rate,
		            volume: rawData[6],
		            amount: rawData[7]
		        };
		        if (ks.length == 0) {
		            result.low = item.low;
		            result.high = item.high;
		        } else {
		            result.high = Math.max(result.high, item.high);
		            result.low = Math.min(result.low, item.low);
		        }
		        ks.push(item);
		    }

		    result.ks = ks;
		    return result;
        
		    
		}
        window.onload = function(){
        	drawKL();
        	$('#loadbox').remove();
        }
		function fmtDate(obj){
			var date = new Date();
			date.setTime(obj);
		    var y = 1900+date.getYear();
		    var m = "0"+(date.getMonth()+1);
		    var d = "0"+date.getDate();
		    return parseInt(y+""+m.substring(m.length-2,m.length)+""+d.substring(d.length-2,d.length));
		}
		function redirect(url){
			location.href = url;
		}
		
    </script>
	

	
	</html>
