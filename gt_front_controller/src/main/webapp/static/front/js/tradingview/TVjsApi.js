var TVjsApi = (function(){
	
    var TVjsApi = function(symbol,id,wsUrl) {
//      var urls = 'wss://api.fcoin.com/v2/ws';
		var urls = wsUrl;
        this.widgets = null;
        console.log(urls);
        this.socket = new socket(wsUrl);
        this.datafeeds = new datafeeds(this);
        this.symbol = symbol || 'ethusdt';
        this.coinId = id || '13';
        this.interval = localStorage.getItem('tradingview.resolution') || '5';
        this.cacheData = {};
        this.lastTime = null;
        this.getBarTimer = null;
        this.cmdunsub = null;
        this.isLoading = true;
        var that = this;
        this.socket.doOpen()
        this.socket.on('open', function() {
            //页面初始化的时候，为了快速加载，先请求150条记录
            if (that.interval < 60) {
                that.socket.send({
                    cmd: 'req',
                    args: ["candle.M"+that.interval+"."+symbol, 150, parseInt(Date.now() / 1000)],
                    id: coinId
                })
            } else if (that.interval >= 60) {
                that.socket.send({
                    cmd: 'req',
                    args: ["candle.H"+(that.interval/60)+"."+symbol, 150, parseInt(Date.now() / 1000)],
                    id: coinId
                })
            } else if (that.interval == '1D'){
                that.socket.send({
                    cmd: 'req',
                    args: ["candle.D1."+symbol, 150, parseInt(Date.now() / 1000)],
                    id: coinId
                })
            } else {
            	that.socket.send({
                    cmd: 'req',
                    args: ["candle.1W."+symbol, 150, parseInt(Date.now() / 1000)],
                    id: coinId
                })
            }
        })
        this.socket.on('message', that.onMessage.bind(this))
        this.socket.on('close', that.onClose.bind(this))
    }
    TVjsApi.prototype.init = function() {
        var resolution = this.interval;
        var chartType = (localStorage.getItem('tradingview.chartType') || '1')*1;

        var symbol = this.symbol;
        
        var locale = 'zh';

//        var skin = localStorage.getItem('tradingViewTheme') || 'white';
		var skin = localStorage.getItem('tradingViewTheme') || 'black';

        
		if(!this.cmdunsub) {
			var that = this;
			this.cmdunsub = function() {
				that.unSubscribe(that.interval)
			}
		}
        if (!this.widgets) {
            this.widgets = window.tvWidget = new TradingView.widget({
            	autosize: true,
            	fullscreen:true,
                symbol: symbol,
                interval: resolution,
                container_id: 'tv_chart_container',
                datafeed: this.datafeeds,
                library_path: './charting_library/',                    
                enabled_features: [],
                timezone: 'Asia/Shanghai',
                custom_css_url: './css/tradingview_'+skin+'.css',
                locale: locale,
                debug: false,
                disabled_features: [
                    "header_symbol_search",
                    "header_saveload",
                    "header_screenshot",
                    "header_chart_type",
                    "header_compare",
                    "header_undo_redo",
                    "timeframes_toolbar",
                    "volume_force_overlay",
                    "header_resolutions",
                    
                    "use_localstorage_for_settings",
                    "show_hide_button_in_legend",
                    
                    //"symbol_search_hot_key", "compare_symbol", "display_market_status", "go_to_date", "header_interval_dialog_button", "legend_context_menu", "show_hide_button_in_legend", "show_interval_dialog_on_key_press", "snapshot_trading_drawings", "symbol_info", "volume_force_overlay"
                ],
                enabled_features: [
                    "hide_last_na_study_output", // 隐藏成交量n/a
                    "keep_left_toolbar_visible_on_small_screens",
                    
                    //"dont_show_boolean_study_arguments", "same_data_requery", "side_toolbar_in_fullscreen_mode", "keep_left_toolbar_visible_on_small_screens", "disable_resolution_rebuild"
                ],
                //preset: "mobile",
                overrides: this.getOverrides(skin),
                studies_overrides: this.getStudiesOverrides(skin)
            })

            var thats = this.widgets;
            thats.onChartReady(function() {
                createStudy();
                createButton(buttons);
                thats.chart().setChartType(chartType);
                toggleStudy(chartType);
            })

            var buttons = [
                {title:'Time',resolution:'1',chartType:3},
                {title:'1min',resolution:'1',chartType:1},
                {title:'5min',resolution:'5',chartType:1},
                {title:'15min',resolution:'15',chartType:1},
                {title:'30min',resolution:'30',chartType:1},
                {title:'1hour',resolution:'60',chartType:1},
                {title:'1day',resolution:'1D',chartType:1},
                {title:'1week',resolution:'1W',chartType:1},
//                {title:'1month',resolution:'1M',chartType:1},
            ];
            var studies = [];
            
            function createButton(buttons){
                for(var i = 0; i < buttons.length; i++){
                    (function(button){
                        thats.createButton()
                        .attr('title', button.title).addClass("mydate")
                        .text(button.title)
                        .on('click', function(e) {
                            if(this.parentNode.className.search('active') > -1){
                                return false;
                            }
                            localStorage.setItem('tradingview.resolution',button.resolution);
                            localStorage.setItem('tradingview.chartType',button.chartType);
                            var $active = this.parentNode.parentNode.querySelector('.active');
                            $active.className = $active.className.replace(/(\sactive|active\s)/,'');
                            this.parentNode.className += ' active';
                            thats.chart().setResolution(button.resolution, function onReadyCallback() {});
                            if(button.chartType != thats.chart().chartType()){
                                thats.chart().setChartType(button.chartType);
                                toggleStudy(button.chartType);
                            }
                        }).parent().addClass('my-group'+(button.resolution==resolution && button.chartType == chartType ? ' active':''));
                    })(buttons[i]);
                }
            }
            function createStudy(){
                var id = thats.chart().createStudy('Moving Average', false, false, [5], null, {'Plot.color': 'rgb(150, 95, 196)'});
                studies.push(id);
                id = thats.chart().createStudy('Moving Average', false, false, [10], null, {'Plot.color': 'rgb(116,149,187)'});
                studies.push(id);
                id = thats.chart().createStudy('Moving Average', false, false, [20],null,{"plot.color": "rgb(58,113,74)"});
                studies.push(id);
                id = thats.chart().createStudy('Moving Average', false, false, [30],null,{"plot.color": "rgb(118,32,99)"});
                studies.push(id);
            }
            function toggleStudy(chartType){
                var state = chartType == 3 ? 0 : 1;
                for(var i = 0; i < studies.length; i++){
                    thats.chart().getStudyById(studies[i]).setVisible(state);
                }
            }
        }
    }
    TVjsApi.prototype.sendMessage = function(data) {
        var that = this;
        //console.log("这是要发送的数据："+JSON.stringify(data) )
        if (this.socket.checkOpen()) {
            this.socket.send(data)
        } else {
            this.socket.on('open', function() {
                that.socket.send(data)
            })
        }
    }
    TVjsApi.prototype.unSubscribe = function(interval) {
        var thats = this;
        var coinId = thats.coinId;
        var symbol = thats.symbol;
        //停止订阅，删除过期缓存、缓存时间、缓存状态
        var ticker = thats.symbol + "-" + interval;
        var tickertime = ticker + "load";
        var tickerstate = ticker + "state";
        var tickerCallback = ticker + "Callback";
        delete thats.cacheData[ticker];
        delete thats.cacheData[tickertime];
        delete thats.cacheData[tickerstate];
        delete thats.cacheData[tickerCallback];
        if (interval < 60) {
            this.sendMessage({
                cmd: 'unsub',
                args: ["candle.M" + interval + "." + symbol.toLowerCase()],
                id: coinId
            })
        } else if (interval >= 60) {
            this.sendMessage({
                cmd: 'unsub',
                args: ["candle.H" + (interval / 60) + "." + symbol.toLowerCase()],
                id: coinId
            })
        } else if (interval == '1D'){
            this.sendMessage({
                cmd: 'unsub',
                args: ["candle.D1." + symbol.toLowerCase()],
                id: coinId
            })
        } else {
        	this.sendMessage({
                cmd: 'unsub',
                args: ["candle.1W." + symbol.toLowerCase()],
                id: coinId
            })
        }
    }
    TVjsApi.prototype.subscribe = function() {
    	var thats = this;
    	var interval = thats.interval;
    	var coinId = thats.coinId;
    	var symbol = thats.symbol;
        if (this.interval < 60) {
            this.sendMessage({
                cmd: 'sub',
                args: ["candle.M" + interval + "." + symbol.toLowerCase()],
                id: coinId
            })
        } else if (interval >= 60) {
            this.sendMessage({
                cmd: 'sub',
                args: ["candle.H" + (interval / 60) + "." + symbol.toLowerCase()],
                id: coinId
            })
        } else if (this.interval == '1D'){
            this.sendMessage({
                cmd: 'sub',
                args: ["candle.D1." + symbol.toLowerCase()],
                id: coinId
            })
        } else {
        	this.sendMessage({
                cmd: 'sub',
                args: ["candle.1W." + symbol.toLowerCase()],
                id: coinId
            })
        }
    }
    TVjsApi.prototype.onMessage = function(data) {
        var thats = this;
        //通知app
        /*if(thats.trade.update){
            thats.trade.update(data);
        }*/
          //console.log("这是后台返回的数据："+JSON.stringify(data) )
          
        if (data.data && data.data.length) {
            //websocket返回的值，数组代表时间段历史数据，不是增量
            var list = []
            var ticker = thats.symbol + "-" + thats.interval;
            var tickerstate = ticker + "state";
            var tickerCallback = ticker + "Callback";
            var onLoadedCallback = thats.cacheData[tickerCallback];
            
            //var that = thats;
            //遍历数组，构造缓存数据
            data.data.forEach(function(element) {
                list.push({
                    time: element.time,
                	open: element.open,
                	high: element.high,
                	low: element.low,
                	close: element.close,
                	volume: element.volume
                })
            }, thats)
            //如果没有缓存数据，则直接填充，发起订阅
            if(!thats.cacheData[ticker]){
                /*thats.cacheData[ticker] = thats.cacheData[ticker].concat(list);
                thats.cacheData['onLoadedCallback'](list);
            }else{*/
                thats.cacheData[ticker] = list;
                thats.subscribe()
            }
            //新数据即当前时间段需要的数据，直接喂给图表插件
            if(onLoadedCallback){
                onLoadedCallback(list);
                delete thats.cacheData[tickerCallback];
            }
            //请求完成，设置状态为false
            thats.cacheData[tickerstate] = !1;
            //记录当前缓存时间，即数组最后一位的时间
            thats.lastTime = thats.cacheData[ticker][thats.cacheData[ticker].length - 1].time            
        }
        if (data.type && data.type.indexOf(thats.symbol.toLowerCase()) !== -1) {
            //console.log(' >> sub:', data.type)
            //console.log(' >> interval:', thats.interval)
            // data带有type，即返回的是订阅数据，
            //缓存的key
            var ticker = thats.symbol + "-" + thats.interval;
            //构造增量更新数据
            var barsData = {
                time: data.time,
                open: data.open,
                high: data.high,
                low: data.low,
                close: data.close,
                volume: data.volume
            }
            /*if (barsData.time >= thats.lastTime && thats.cacheData[ticker] && thats.cacheData[ticker].length) {
                thats.cacheData[ticker][thats.cacheData[ticker].length - 1] = barsData
            }*/
            //如果增量更新数据的时间大于缓存时间，而且缓存有数据，数据长度大于0
            if (barsData.time > thats.lastTime && thats.cacheData[ticker] && thats.cacheData[ticker].length) {
                //增量更新的数据直接加入缓存数组
                thats.cacheData[ticker].push(barsData)
                //修改缓存时间
                thats.lastTime = barsData.time
            }else if(barsData.time == thats.lastTime && thats.cacheData[ticker] && thats.cacheData[ticker].length){
                //如果增量更新的时间等于缓存时间，即在当前时间颗粒内产生了新数据，更新当前数据
                thats.cacheData[ticker][thats.cacheData[ticker].length - 1] = barsData
            }
            // 通知图表插件，可以开始增量更新的渲染了
            thats.datafeeds.barsUpdater.updateData()
        }else {
            var ticker = thats.symbol + "-" + thats.interval;
            var tickerCallback = ticker + "Callback";
            var onLoadedCallback = thats.cacheData[tickerCallback];
            if(onLoadedCallback){
                onLoadedCallback([]);
                delete thats.cacheData[tickerCallback];
            }
        }
    }
    TVjsApi.prototype.onClose = function(){
        var thats = this;
        //console.log(' >> : 连接已断开... 正在重连')
        thats.socket.doOpen()
        thats.socket.on('open', function() {
            console.log(' >> : 已重连')
            thats.subscribe()
        });
    }
    TVjsApi.prototype.initMessage = function(symbolInfo, resolution, rangeStartDate, rangeEndDate, onLoadedCallback){
        //console.log('发起请求，从websocket获取当前时间段的数据');
        var that = this;
        //保留当前回调
        var tickerCallback = this.symbol + "-" + resolution + "Callback";
        that.cacheData[tickerCallback] = onLoadedCallback;
        //获取需要请求的数据数目
        var limit = that.initLimit(resolution, rangeStartDate, rangeEndDate);
        //商品名
        var symbol = that.symbol;
        var coinId = that.coinId;
        //如果当前时间节点已经改变，停止上一个时间节点的订阅，修改时间节点值
        if(that.interval !== resolution){
            that.unSubscribe(that.interval)
            that.interval = resolution;
        }    
        //获取当前时间段的数据，在onMessage中执行回调onLoadedCallback
        if (that.interval < 60) {
            that.socket.send({
                cmd: 'req',
                args: ["candle.M"+resolution+"."+symbol, limit, rangeEndDate],
                id: coinId
            })
        } else if (that.interval >= 60) {
            that.socket.send({
                cmd: 'req',
                args: ["candle.H"+(resolution/60)+"."+symbol, limit, rangeEndDate],
                id: coinId
            })
        } else if (that.interval == '1D'){
            that.socket.send({
                cmd: 'req',
                args: ["candle.D1."+symbol, limit, rangeEndDate],
                id: coinId
            })
        } else {
        	that.socket.send({
                cmd: 'req',
                args: ["candle.1W."+symbol, limit, rangeEndDate],
                id: coinId
            })
        }
    }
    TVjsApi.prototype.initLimit = function(resolution, rangeStartDate, rangeEndDate){
        var limit = 0;
        switch(resolution){
            case '1D' : limit = Math.ceil((rangeEndDate - rangeStartDate) / 60 / 60 / 24); break;
            case '1W' : limit = Math.ceil((rangeEndDate - rangeStartDate) / 60 / 60 / 24 / 7); break;
            case '1M' : limit = Math.ceil((rangeEndDate - rangeStartDate) / 60 / 60 / 24 / 31); break;
            default : limit = Math.ceil((rangeEndDate - rangeStartDate) / 60 / resolution); break;
        }
        return limit;
    }
    TVjsApi.prototype.getBars = function(symbolInfo, resolution, rangeStartDate, rangeEndDate, onLoadedCallback) {
        //console.log(' >> :', rangeStartDate, rangeEndDate)
        var ticker = this.symbol + "-" + resolution;
        var tickerload = ticker + "load";
        var tickerstate = ticker + "state";

        if(!this.cacheData[ticker] && !this.cacheData[tickerstate]){
            //如果缓存没有数据，而且未发出请求，记录当前节点开始时间
            this.cacheData[tickerload] = rangeStartDate;
            //发起请求，从websocket获取当前时间段的数据
            this.initMessage(symbolInfo, resolution, rangeStartDate, rangeEndDate, onLoadedCallback);
            //设置状态为true
            this.cacheData[tickerstate] = !0;
            return false;
        }
        if(!this.cacheData[tickerload] || this.cacheData[tickerload] > rangeStartDate){
            //如果缓存有数据，但是没有当前时间段的数据，更新当前节点时间
            this.cacheData[tickerload] = rangeStartDate;
            //发起请求，从websocket获取当前时间段的数据
            this.initMessage(symbolInfo, resolution, rangeStartDate, rangeEndDate, onLoadedCallback);
            //设置状态为true
            this.cacheData[tickerstate] = !0;
            return false;
        }
        if(this.cacheData[tickerstate]){
            //正在从websocket获取数据，禁止一切操作
            return false;
        }
        ticker = this.symbol + "-" + this.interval;
        if (this.cacheData[ticker] && this.cacheData[ticker].length) {
            this.isLoading = false
            var newBars = []
            this.cacheData[ticker].forEach(item => {
                if (item.time >= rangeStartDate * 1000 && item.time <= rangeEndDate * 1000) {
                    newBars.push(item)
                }
            })
            onLoadedCallback(newBars)
        } else {
            var self = this
            this.getBarTimer = setTimeout(function() {
                self.getBars(symbolInfo, resolution, rangeStartDate, rangeEndDate, onLoadedCallback)
            }, 10)
        }
    }
    TVjsApi.prototype.getOverrides = function(theme){
        var themes = {
            "white": {
                up: "#0dcc6f",
				down: "#ed5550",
				bg: "#21232e",
				grid: "#2a2e3f",
				cross: "#9194A3",
				border: "#4e5b85",
				text: "#4e5b85",
				areatop: "rgba(122, 152, 247, .1)",
				areadown: "rgba(122, 152, 247, .02)",
				line: "#737375",
				showLegend: 0
            },
            "black": {
                up: "#589065",
                down: "#ae4e54",
                bg: "#181B2A",
                grid: "#1f2943",
                cross: "#9194A3",
                border: "#4e5b85",
                text: "#61688A",
                areatop: "rgba(122, 152, 247, .1)",
                areadown: "rgba(122, 152, 247, .02)",
                line: "#737375"
            },
            "mobile": {
                up: "#03C087",
                down: "#E76D42",
                bg: "#ffffff",
                grid: "#f7f8fa",
                cross: "#23283D",
                border: "#C5CFD5",
                text: "#8C9FAD",
                areatop: "rgba(71, 78, 112, 0.1)",
                areadown: "rgba(71, 78, 112, 0.02)",
                showLegend: !0
            }
        };
        var t = themes[theme];
        return {
        	// 成交量高度设置  large, medium, small, tiny
	        "volumePaneSize": "medium",
            "scalesProperties.lineColor": t.text,
            "scalesProperties.textColor": t.text,
            "paneProperties.background": t.bg,
            "paneProperties.vertGridProperties.color": t.grid,
            "paneProperties.horzGridProperties.color": t.grid,
            "paneProperties.crossHairProperties.color": t.cross,
            "paneProperties.legendProperties.showLegend": !t.showLegend,
            "paneProperties.legendProperties.showStudyArguments": !0,
            "paneProperties.legendProperties.showStudyTitles": !0,
            "paneProperties.legendProperties.showStudyValues": !0,
            "paneProperties.legendProperties.showSeriesTitle": !0,
            "paneProperties.legendProperties.showSeriesOHLC": !0,
            "mainSeriesProperties.candleStyle.upColor": t.up,
            "mainSeriesProperties.candleStyle.downColor": t.down,
            "mainSeriesProperties.candleStyle.drawWick": !0,
            "mainSeriesProperties.candleStyle.drawBorder": !0,
            "mainSeriesProperties.candleStyle.borderColor": t.border,
            "mainSeriesProperties.candleStyle.borderUpColor": t.up,
            "mainSeriesProperties.candleStyle.borderDownColor": t.down,
            "mainSeriesProperties.candleStyle.wickUpColor": t.up,
            "mainSeriesProperties.candleStyle.wickDownColor": t.down,
            "mainSeriesProperties.candleStyle.barColorsOnPrevClose": !1,
            "mainSeriesProperties.hollowCandleStyle.upColor": t.up,
            "mainSeriesProperties.hollowCandleStyle.downColor": t.down,
            "mainSeriesProperties.hollowCandleStyle.drawWick": !0,
            "mainSeriesProperties.hollowCandleStyle.drawBorder": !0,
            "mainSeriesProperties.hollowCandleStyle.borderColor": t.border,
            "mainSeriesProperties.hollowCandleStyle.borderUpColor": t.up,
            "mainSeriesProperties.hollowCandleStyle.borderDownColor": t.down,
            "mainSeriesProperties.hollowCandleStyle.wickColor": t.line,
            "mainSeriesProperties.haStyle.upColor": t.up,
            "mainSeriesProperties.haStyle.downColor": t.down,
            "mainSeriesProperties.haStyle.drawWick": !0,
            "mainSeriesProperties.haStyle.drawBorder": !0,
            "mainSeriesProperties.haStyle.borderColor": t.border,
            "mainSeriesProperties.haStyle.borderUpColor": t.up,
            "mainSeriesProperties.haStyle.borderDownColor": t.down,
            "mainSeriesProperties.haStyle.wickColor": t.border,
            "mainSeriesProperties.haStyle.barColorsOnPrevClose": !1,
            "mainSeriesProperties.barStyle.upColor": t.up,
            "mainSeriesProperties.barStyle.downColor": t.down,
            "mainSeriesProperties.barStyle.barColorsOnPrevClose": !1,
            "mainSeriesProperties.barStyle.dontDrawOpen": !1,
            "mainSeriesProperties.lineStyle.color": t.border,
            "mainSeriesProperties.lineStyle.linewidth": 1,
            "mainSeriesProperties.lineStyle.priceSource": "close",
            "mainSeriesProperties.areaStyle.color1": t.areatop,
            "mainSeriesProperties.areaStyle.color2": t.areadown,
            "mainSeriesProperties.areaStyle.linecolor": t.border,
            "mainSeriesProperties.areaStyle.linewidth": 1,
            "mainSeriesProperties.areaStyle.priceSource": "close",
            
            /*"paneProperties.vertGridProperties.style": 1,
            "paneProperties.horzGridProperties.style": 1,
            "scalesProperties.showRightScale": true,
            "scalesProperties.showLeftScale": false,
            "paneProperties.overlayPropreties.percentage": false,
			"paneProperties.overlayPropreties.autoScale": true,
			"paneProperties.overlayPropreties.log": false,
			"paneProperties.leftAxisProperties.percentage": false,
			"paneProperties.leftAxisProperties.autoScale": true,
			"paneProperties.leftAxisProperties.log": false,
			"paneProperties.rightAxisProperties.percentage": false,
			"paneProperties.rightAxisProperties.autoScale": true,
			"paneProperties.rightAxisProperties.log": false,
			"mainSourceProperties.style": 1,
			"mainSourceProperties.pnfStyle.inputs.reversalAmount": 3,
			"mainSourceProperties.pnfStyle.inputs.boxSize": 1.0,
			"mainSourceProperties.showInDataWindow": false,
			"mainSourceProperties.visible": true,
			"mainSourceProperties.areaStyle.transparency": 50,
			"mainSourceProperties.areaStyle.linestyle": 0,
			"mainSourceProperties.renkoStyle.inputs.source": "close",
			"mainSourceProperties.renkoStyle.inputs.boxSize": 3,
			"mainSourceProperties.showLastValue": true,
			"mainSourceProperties.lineStyle.linestyle": 0,
			"mainSourceProperties.kagiStyle.inputs.source": "close",
			"mainSourceProperties.kagiStyle.inputs.reversalAmount": 1.0,
			"mainSourceProperties.pbStyle.inputs.source": "close",
			"mainSourceProperties.pbStyle.inputs.lb": 3,*/
        	
        	
			"paneProperties.vertGridProperties.color": t.bg,
			"paneProperties.horzGridProperties.color": t.bg//"rgb(31,38,58)",
			
        }
    }
    TVjsApi.prototype.getStudiesOverrides = function(theme){
        var themes = {
            "white": {
                c0: "#eb4d5c",
                c1: "#53b987",
                t: 70,
                v: !1
            },
            "black": {
                c0: "#fd8b8b",
                c1: "#3cb595",
                t: 70,
                v: !1
            }
        };
        var t = themes[theme];
        return {
            "volume.volume.color.0": t.c0,
            "volume.volume.color.1": t.c1,
            "volume.volume.transparency": t.t,
            "volume.options.showStudyArguments": t.v
        }
    }
    TVjsApi.prototype.resetTheme = function(skin){
        this.widgets.addCustomCSSFile('./css/tradingview_'+skin+'.css');
        this.widgets.applyOverrides(this.getOverrides(skin));
        this.widgets.applyStudiesOverrides(this.getStudiesOverrides(skin));
    }
    TVjsApi.prototype.formatt = function(time){
        if(isNaN(time)){
            return time;
        }
        var date = new Date(time);
        var Y = date.getFullYear();
        var m = this._formatt(date.getMonth());
        var d = this._formatt(date.getDate());
        var H = this._formatt(date.getHours());
        var i = this._formatt(date.getMinutes());
        var s = this._formatt(date.getSeconds());
        return Y+'-'+m+'-'+d+' '+H+':'+i+':'+s;
    }
    TVjsApi.prototype._formatt = function(num){
        return num >= 10 ? num : '0'+num;
    }
    return TVjsApi;
})();
