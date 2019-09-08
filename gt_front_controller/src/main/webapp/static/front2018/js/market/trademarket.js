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
var market_socket = new socket(wsUrl);
var rose_socket = new socket(wsUrl);

var market = {
	cnyDigit : $("#cnyDigit").val(),
	coinDigit : $("#coinDigit").val(),
	resizeWidth : function() {
		var $width = $(window).width();
		var $height = $(window).height();
		var $login = $("#login").val();
		if ($width < 1170) {
			$width = 1170;
		}
		var leftWidth = $width - 505;
		var topLeftHeight = $height - 55;
		var topRightHight = $height - 55-288;
		if ($login == 1) {
			var topLeftHeight = $height - 55 - 159;
			var topRightHight = $height - 55 - 288;
			// $(".mardeTradeWarp").hide();
		}else{
			
			var spantxt=$('.trade_trade .tr_title span');
			spantxt.html('<p><a class="cblue2" href="/user/login.html">'+ util.getLan("mark.login") +'</a> '+ util.getLan("mark.or") +' <a class="cblue2" href="/user/register.html">'+ util.getLan("mark.register") +'</a> '+ util.getLan("mark.trade")+'</p>')
			$('.buy_btn').addClass('before_buy_btn');
			$('.buy_btn').attr('disabled', 'disabled');
			$('.Entruts_list').html('<p style="font-size:15px; text-align:center; padding-top:40px;">' + util.getLan("tradeMarket.tips.6") + '<a class="cblue2" href="/user/login.html"> '+ util.getLan("mark.login") +' </a> '+ util.getLan("mark.or") +' <a class="cblue2" href="/user/register.html"> '+ util.getLan("mark.register")+' </a> '+ util.getLan("mark.see") +'</p>')
		}
		$("#marketLeft").width(leftWidth);
		$("#marketStart").width(leftWidth).height(topLeftHeight);
		$("#marketData").height(topRightHight);
		$("#marketEntruts").width(leftWidth);
		$("#marketSuccessData").height(topRightHight - 30);
		$("#marketDepthData").height(topRightHight - 30);
		if ($width < 1600) {
			$("span", "#entrutsHead").removeClass("entruts-head-nav-full").addClass("entruts-head-nav-small");
			$("span:first-child", "#entrutsHead").css({
				color : "#da2e22"
			});
			$("#entrutsHis").hide();
			$(".entruts-head").css({
				"border-right" : "1px solid #000"
			});
			$(".entruts-data").width("99%");
			$(".entruts-head-nav-small").on("click", function() {
				$(".entruts-head-nav-small").css({
					color : "#a5afb3"
				});
				$(this).css({
					color : "#da2e22"
				});
				var showId = $(this).data().show;
				var hideId = $(this).data().hide;
				$("#" + showId).show();
				$("#" + hideId).hide();
			});
		} else {
			$("span", "#entrutsHead").removeClass("entruts-head-nav-small").addClass("entruts-head-nav-full");
			$("#entrutsCur").show();
			$("#entrutsHis").show();
			$(".entruts-data").width("49%");	
			$(".entruts-head-nav-full").css({
				color : "#a5afb3"
			});
			$(".entruts-head-nav-full").unbind();
			$(".entruts-head").css({
				"border-right" : "none"
			});
		}
	},
	loginUser : function(ele) {
		var login_acc = $("#login_acc").val();
		var login_pwd = $("#login_pwd").val();
		if (login_acc == "") {
			util.layerTips("login_acc", util.getLan("tradeMarket.tips.1"), false, 3);
			return;
		}
		if (login_pwd == "") {
			util.layerTips("login_pwd", util.getLan("tradeMarket.tips.2"), false, 3);
			return;
		}

		var url = "/user/login/index.html?random=" + Math.round(Math.random() * 100);
		var param = {
			loginName : login_acc,
			password : login_pwd
		};
		var callback = function(data) {
			if (data.code == 0) {
				window.location.reload(true);
			} else {
				util.layerTips("login_acc", data.msg, true, 3);
			}
		};
		util.network({
			btn : ele,
			url : url,
			param : param,
			success : callback,
		});
	},
	lastprice : 0,
	fristprice : true,
	autoRefresh : function() {
		var symbol = document.getElementById("symbol").value;
		var coinshortName = $("#coinshortName").val();		
		var buysellcount = 20;
		var successcount = 60;
		//var url = "/real/market2.html?symbol={0}";
		//url = util.strFormat(url,symbol, buysellcount, successcount);
		var priceOffset = function(value, type) {			
			return value;
		};
		var amountOffset = function(value, type) {
			return value;
		};
		// 实时成交
		var tradesCallback = function(result) {
			data = result;
			if('ping' == data.cmd){
				return;
			}
			
			getData(data);
			
			var loghtml = "";
			var rate =1;
			var fbprice = data.fbprice;
			var cursymbol = 'USDT';
			var curlang = document.getElementById("curlang").value;
			
			if(curlang=='cn'){
				cursymbol = "CNY";
				rate =data.usdtrate;
			}else{
				cursymbol = 'USDT';
				rate =1;
			}
			$.each(data.trades, function(key, value) {
				
				if (key >= successcount) {
					return;
				}
				loghtml += '<li>';
				loghtml += '<span class="success-time">' + value.time + '</span>';
				loghtml += '<span class="'+ (value.en_type == 'ask' ? "cgreen2" : "cred") +'">' + (value.en_type == 'ask' ? util.getLan("mark.sell") : util.getLan("mark.buy")) + '</span>';
				loghtml += '<span class="success-price coin_price">' + util.numFormat(Number(value.price), market.cnyDigit) + '</span>';
				loghtml += '<span class="success-amount">' + util.numFormat(Number(value.amount), market.coinDigit) + '</span>';
				loghtml += '</li>';
			});
			$("#marketSuccessData").html("").append(loghtml);
			
			if (market.fristprice) {
				if (data.buys.length <= 0) {
					$("#sell-price").val(util.numFormat(Number(data.p_new), market.cnyDigit));
					$("#buy-amount").val(util.numFormat(0, market.coinDigit));
				} else {
					$("#sell-price").val(util.numFormat(Number(data.buys[0].price), market.cnyDigit));
					$("#buy-amount").val(util.numFormat(0, market.coinDigit));
				}
				if (data.sells.length <= 0) {
					$("#buy-price").val(util.numFormat(Number(data.p_new), market.cnyDigit));
					$("#sell-amount").val(util.numFormat(0, market.coinDigit));
				} else {
					$("#buy-price").val(util.numFormat(Number(data.sells[0].price), market.cnyDigit));
					$("#sell-amount").val(util.numFormat(0, market.coinDigit));
				}
				
				 var money = document.getElementById("sell-price").value;
				 $('.sellCNY').text((money*rate*fbprice).toFixed(market.cnyDigit) + cursymbol);
				 var money1 = document.getElementById("buy-price").value;
				 $('.buyCNY').text((money1*rate*fbprice).toFixed(market.cnyDigit) + cursymbol);
				market.fristprice = false;
			}
			
			var asksdata = new Array();
			var asks = data.buys;
			var length = asks.length - 1;
			for (var i = length; i >= 0; i--) {
				asksdata.push(asks[i]);
			}
			var sumCoin = 0;
			var coinNum = 0;
			
			$.each(data.buys, function(key, value) {
				sumCoin = parseInt(sumCoin) + parseInt(util.numFormat(amountOffset(Number(value.amount), 1), market.coinDigit));
				coinNum++;
			});
			var buyAverage = sumCoin / coinNum / 2;
			buyAverage = parseInt(util.numFormat(amountOffset(Number(buyAverage), 1), market.coinDigit));
			$.each(data.buys, function(key, value) {
				if (key >= buysellcount) {
					return;
				}
				var buyele = $("#buy" + key, "#marketDepthBuy");
				if (buyele.length == 0) {
					var html = '<li id="buy' + key + '" data-type="1" data-money="'+util.numFormat(priceOffset(Number(value.price), 0), market.cnyDigit)+
					'" data-num="'+util.numFormat(amountOffset(Number(value.amount), 0), market.coinDigit)+'" id="sell'+
					key+'" class="marketNew-priceItem clear">';
					html += '<span class="depth-des s_tr1 cred">' + util.getLan("tradeMarket.tips.3", key + 1) + '</span>';
					html += '<span class="depth-price">' + util.numFormat(priceOffset(Number(value.price), 1), market.cnyDigit) + '</span>';
					html += '<span class="depth-amount">' + util.numFormat(amountOffset(Number(value.amount), 1), market.coinDigit) + '</span>';
					if(parseInt(util.numFormat(amountOffset(Number(value.amount), 1), market.coinDigit)) > buyAverage){
						var whshow = 100
					}else{
						var whshow = util.numFormat(amountOffset(Number(value.amount), 1), market.coinDigit);
						whshow = util.numFormat(amountOffset(Number(whshow), 1), market.coinDigit) * 100;
					}
					html += "<em class='down_price_bg' style='width: "+ whshow +"%'></em>";
					html += '</li>';
					 // $("#marketDepthBuy").prepend(html);
					$("#marketDepthBuy").append(html);
				} else {
					buyele.children()[1].innerHTML = util.numFormat(priceOffset(Number(value.price), 0), market.cnyDigit);
					buyele.children()[2].innerHTML = util.numFormat(amountOffset(Number(value.amount), 0), market.coinDigit);
					buyele.attr("data-money",util.numFormat(priceOffset(Number(value.price), 0), market.cnyDigit));
					buyele.attr("data-num",util.numFormat(amountOffset(Number(value.amount), 0), market.coinDigit));
				}
			});
			/*$("#marketDepthBuy").mCustomScrollbar("destroy"); 
			$("#marketDepthBuy").mCustomScrollbar({
				mouseWheel:true,
				scrollButtons:{
					enable:true
				},
				advanced:{ 
					updateOnContentResize:true
				}
			});*/
			for (var i = data.buys.length; i < buysellcount; i++) {
				$("#buy" + i, "#marketDepthBuy").remove();
			}
			var sellsumCoin = 0;
			var sellcoinNum = 0;
			$.each(data.sells, function(key, value) {
				sellsumCoin = parseInt(sellsumCoin) + parseInt(util.numFormat(amountOffset(Number(value.amount), 1), market.coinDigit));
				sellcoinNum++;
			});
			var sellAverage = sellsumCoin / sellcoinNum / 2;
			sellAverage = parseInt(util.numFormat(amountOffset(Number(sellAverage), 1), market.coinDigit));
			$.each(data.sells, function(key, value) {
				if (key >= buysellcount) {
					return;
				}
				var sellele = $("#sell" + key, "#marketDepthSell");
				if (sellele.length == 0) {
					var html = '<li id="sell' + key + '" data-type="0" data-money="'+util.numFormat(priceOffset(Number(value.price), 1), market.cnyDigit)+
					'" data-num="'+util.numFormat(amountOffset(Number(value.amount), 1), market.coinDigit)+'" id="sell'+
					key+'" class="marketNew-priceItem clear">';
					html += '<span class="depth-des s_tr1 cgreen2 ">' + util.getLan("tradeMarket.tips.4", key + 1) + '</span>';
					html += '<span class="depth-price  coin_price">' + util.numFormat(priceOffset(Number(value.price), 1), market.cnyDigit) + '</span>';
					html += '<span class="depth-amount">' + util.numFormat(amountOffset(Number(value.amount), 1), market.coinDigit) + '</span>';
					
					if(parseInt(util.numFormat(amountOffset(Number(value.amount), 1), market.coinDigit)) > sellAverage){
						var sellwhshow = 100
					}else{
						var sellwhshow = util.numFormat(amountOffset(Number(value.amount), 1), market.coinDigit) ;
						sellwhshow = util.numFormat(amountOffset(Number(sellwhshow), 1), market.coinDigit) * 100;
					}
					html += "<em class='up_price_bg' style='width: "+ sellwhshow +"%'></em>";
					html += '</li>';
					$("#marketDepthSell").prepend(html);
					
					
				} else {
					sellele.children()[1].innerHTML = util.numFormat(priceOffset(Number(value.price), 1), market.cnyDigit);
					sellele.children()[2].innerHTML = util.numFormat(amountOffset(Number(value.amount), 1), market.coinDigit);
					sellele.attr("data-money",util.numFormat(priceOffset(Number(value.price), 1), market.cnyDigit));
					sellele.attr("data-num",util.numFormat(amountOffset(Number(value.amount), 1), market.coinDigit));
				}
				
			});
			$(".curDate_con").mCustomScrollbar();		// 设置滚动条marketDepthSell
		//	$("#marketDepthSell").mCustomScrollbar();
		//	$("#marketDepthSell").mCustomScrollbar("destroy"); 
			/*$("#marketDepthSell").mCustomScrollbar({
				mouseWheel:true,
				scrollButtons:{
					enable:true
				},
				advanced:{ 
					updateOnContentResize:true
				}
			});*/
		

		// $("#marketDepthSell").mCustomScrollbar("scrollTo",'last');
		/*if($("#marketDepthSell").height()>=396){*/
			$("#marketDepthSell").css({
				'width':'100%',
				'overflow': 'hidden',
			    'position': 'absolute',
			    'bottom': '0',
			});
/*		if(data.sells.length >=11){
			$("#marketDepthSell").removeAttr("style");
				$("#marketDepthSell").css({
					'height':'396px',
					'width':'100%',
				});
				//$("#marketDepthSell").css('top',$("#mCSB_6").height()-$("#mCSB_6_container").height());
			}else{
				
				$("#marketDepthSell").removeAttr("style");
				$("#marketDepthSell").css({
					'height': $("#marketDepthSell").height(),
				    'position': 'absolute',
				    'bottom': '0',
				    'top':$(".sellCon").height()-$("#marketDepthSell").height(),
					'width':'100%'})
			}*/
			// mCSB_6_container
		
			
			for (var i = data.sells.length; i < buysellcount; i++) {
				$("#sell" + i, "#marketDepthSell").remove();
			}
			
			$(".marketNew-priceItem").on("click", function() {
				//var data1 = $(this).data();
				var type = $(this).attr("data-type");
				var money = scientificToNumber($(this).attr("data-money"));
				if (type == 0) {
					document.getElementById("buy-price").value = money;
					$('.buyCNY').text((money*rate*fbprice).toFixed(market.cnyDigit) + cursymbol);
				} else {
					
					document.getElementById("sell-price").value = money;
					$('.sellCNY').text((money*rate*fbprice).toFixed(market.cnyDigit) + cursymbol);
				}
			});
			
			
			var p_new = util.numFormat(Number(data.p_new), market.cnyDigit);
			
			if (p_new >= market.lastprice) {
				$("#marketPrice").html(p_new).removeClass("market-font-buy").addClass("cgreen2").addClass("coin_price");
				
			} else {
				$("#marketPrice").html(p_new).removeClass("market-font-sell").addClass("cred").addClass("coin_price");
				
			}
			var rose = data.rose;
			if (rose < 0) {
				$("#marketRose").removeClass("market-font-buy").addClass("cgreen").html(rose + "%");
				$("#marketPriceUD").html("↓").removeClass("market-font-sell").addClass("cgreen2").addClass("coin_price");
				$("#marketPriceLI").html(p_new).removeClass("market-font-buy").addClass("cgreen2").addClass("coin_price");
			} else {
				$("#marketRose").removeClass("market-font-sell").addClass("cred").html(rose + "%");
				$("#marketPriceUD").html("↑").removeClass("market-font-buy").addClass("cred").addClass("coin_price");
				$("#marketPriceLI").html(p_new).removeClass("market-font-sell").addClass("cred").addClass("coin_price");
			}
			market.lastprice = p_new;
		};
		
		/*util.network({
			url : url,
			method : "get",
			success : tradesCallback,
		});*/
		
		var param = {
		        cmd: 'sub',
		        args: ["market"],
		        id: symbol
		    };
		if (!market_socket.checkOpen()) {
			market_socket.doOpen();
		}
		market_socket.send(param)
		market_socket.on('message', tradesCallback);
		market_socket.on('close', function(){
			market_socket.doOpen();
			market_socket.on('open', function() {
		        console.log(' >> : 已重连')
		        if (market_socket.checkOpen()) {
		        	market_socket.send(param)
		        } else {
		        	market_socket.on('open', function() {
		        		market_socket.send(param)
		            })
		        }
		    });
		});;
		
		// 委单资产
		var $login = $("#login").val();
		if ($login == 0) {
			/*window.setTimeout(function() {
				market.autoRefresh();
			}, 30000);*/
			return;
		}
		
		url = "/real/userassets.html?random=" + Math.round(Math.random() * 100);
		$.post(url, {
			symbol : symbol
		}, function(data) {
			if (data != "") {
				$("#totalCny").html(util.numFormat(data.availableCny, market.cnyDigit));
				$("#totalCoin").html(util.numFormat(data.availableCoin, market.coinDigit));
			}
		}, "json");
		
		
		var entrutsUrl = "/kline/trade_history.html?random=" + Math.round(Math.random() * 100);
		var entrutsParam = {
			symbol : symbol
		};
		var entrutsCallbcack = function(result) {
			if (result.code == 0) {
//    					$("#totalCny").html(util.numFormat(result.data.totalCny, market.cnyDigit));
//    					$("#totalCoin").html(util.numFormat(result.data.totalCoin, market.coinDigit));
				var entrutsCur = "";
//    					遍历当前委托list
				$.each(result.fentrusts1, function(key, value) {
					entrutsCur += '<tr>';
					entrutsCur += '<td class="textL">' + value.flastUpdatTime_s + '</td>';
					entrutsCur += '<td ><sapn class=" ' + (value.fentrustType == '1' ? "cgreen2" : "cred") + '">' + value.fentrustType_s + '</span></td>';
					entrutsCur += '<td >' + util.numFormat(value.fprize, market.cnyDigit) + '</td>';
					entrutsCur += '<td >' + util.numFormat(value.fcount, market.coinDigit) + '</td>';
					entrutsCur += '<td >' + util.numFormat(value.fcount-value.fleftCount, market.coinDigit) + '</td>';
					entrutsCur += '<td >' + value.fstatus_s + '</span>';
					entrutsCur += '<td ><span class="entruts-cancel cblue2" data-fid="' + value.fid + '">' + util.getLan("tradeMarket.tips.5") + '</span></td>';
					entrutsCur += '</tr>';
				});
				if (entrutsCur == "") {
					$("#entrutsCurData").html('<p style="height: 100%; font-size:15px; width: 100%;text-align: center;padding-top: 40px;">' + util.getLan("tradeMarket.tips.6") + '</p>');
				} else {
					$("#entrutsCurData").html("").append(entrutsCur);
				}
//    					当前委托撤销
				$(".entruts-cancel").on("click", function() {
					var id = $(this).data().fid;
					market.cancelEntrustBtc(this, id);
				});
				
				var entrutsHis = "";
				$.each(result.fentrusts2, function(key, value) {
					entrutsHis += '<tr>';
					entrutsHis += '<td class="textL">' + value.flastUpdatTime_s + '</td>';
					entrutsHis += '<td><span class="col-2 ' + (value.fentrustType == '1' ? "cgreen2" : "cred") + '">' + value.fentrustType_s + '</span></td>';
					entrutsHis += '<td>' + util.numFormat(value.fprize, market.cnyDigit) + '</td>';
					entrutsHis += '<td>' + util.numFormat(value.fcount, market.coinDigit) + '</td>';
					entrutsHis += '<td>' + util.numFormat(value.fcount-value.fleftCount, market.coinDigit) + '</td>';
					entrutsHis += '<td>' + value.fstatus_s + '</span>';
					entrutsHis += '</tr>';
				});
				if (entrutsHis == "") {
					$("#entrutsHisData").html('<p style="height: 100%;font-size:15px; width: 100%;text-align: center;padding-top: 40px;">' + util.getLan("tradeMarket.tips.6") + '</p>');
				} else {
					$("#entrutsHisData").html("").append(entrutsHis);
				}
			}
		};
		util.network({
			url : entrutsUrl,
			param : entrutsParam,
			success : entrutsCallbcack,
		});
		
		/*window.setTimeout(function() {
			market.autoRefresh();
		}, 30000);*/
	},
	cancelEntrustBtc : function(ele, id) {
		var url = "/trade/cancelEntrust.html";
		var param = {
			id : id
		};
		var callbcack = function(data) {
			if (data.code == 0) {
				market.autoRefresh();	// 当前委托撤销成功回调
			//	$(ele).remove();	
			}
		};
		util.network({
			url : url,
			param : param,
			success : callbcack,
		});
	},
	onPortion : function(portion, tradeType) {
		portion = util.accDiv(portion, 100);
		if (tradeType == 0) {
			var money = Number($("#buy-price").val());
			var tradecnymoney = Number($("#totalCny").html());
			if(isNaN(tradecnymoney)){ 
				return; 
			}
			var mtcNum = util.accDiv(tradecnymoney, money);
			mtcNum = util.accMul(mtcNum, portion);
			var portionMoney = util.accMul(money, mtcNum);
			var money = util.numFormat(money, market.cnyDigit);
			var portionMoney = util.numFormat(portionMoney, market.cnyDigit);
			var mtcNum = util.numFormat(mtcNum, market.coinDigit);
			this.antoTurnover(money, portionMoney, mtcNum, tradeType);
		} else {
			var money = Number($("#sell-price").val());
			var trademtccoin = Number($("#totalCoin").html());
			if(isNaN(trademtccoin)){ 
				return; 
			}
			var mtcNum = util.accMul(trademtccoin, portion);
			var portionMoney = util.accMul(money, mtcNum);
			var money = util.numFormat(money, market.cnyDigit);
			var portionMoney = util.numFormat(portionMoney, market.cnyDigit);
			var mtcNum = util.numFormat(mtcNum, market.coinDigit);
			this.antoTurnover(money, portionMoney, mtcNum, tradeType);
		}
	},
	antoTurnover : function(price, money, mtcNum, tradeType) {
		if (tradeType == 0) {
			$("#buy-price").val(util.numFormat(price, market.cnyDigit));
			$("#buy-amount").val(util.numFormat(mtcNum, market.coinDigit));
			var tradeTurnover = util.numFormat(util.accMul(price, mtcNum), market.cnyDigit);
			var tradecnymoney = util.numFormat(Number($("#totalCny").html()), market.cnyDigit);
			if (parseFloat(tradeTurnover) > parseFloat(tradecnymoney)) {
				util.layerTips("buy_sub", util.getLan("comm.tips.9"), false, 1);
				return;
			}
			$("#buy-limit").html(util.numFormat(tradeTurnover, market.cnyDigit));
		} else {
			$("#sell-price").val(util.numFormat(price, market.cnyDigit));
			$("#sell-amount").val(util.numFormat(mtcNum, market.coinDigit));
			var tradeTurnover = util.accMul(price, mtcNum);
			var trademtccoin = util.numFormat(Number($("#totalCoin").html()), market.coinDigit);
			if (parseFloat(mtcNum) > parseFloat(trademtccoin)) {
				util.layerTips("sell_sub", util.getLan("comm.tips.9"), false, 1);
				return;
			}
			$("#sell-limit").html(util.numFormat(tradeTurnover, market.cnyDigit));
		}
	},
	numVerify : function(tradeType) {
		if (tradeType == 0) {
			var userCnyBalance = $("#buy-price").val();
			if (userCnyBalance == "") {
				userCnyBalance = 0;
			}
			var tradebuyAmount = $("#buy-amount").val();
			if (tradebuyAmount == "") {
				tradebuyAmount = 0;
			}
			var tradeTurnover = util.accMul(userCnyBalance, tradebuyAmount);
			var tradecnymoney = Number($("#totalCny").html());
			if (tradeTurnover > tradecnymoney) {
				util.layerTips("buy_sub", util.getLan("comm.tips.9"), false, 1);
				return;
			}
			$("#buy-limit").html(util.numFormat(tradeTurnover, market.cnyDigit));
		} else {
			var usersellCnyBalance = $("#sell-price").val();
			if (usersellCnyBalance == "") {
				usersellCnyBalance = 0;
			}
			var tradesellAmount = $("#sell-amount").val();
			if (tradesellAmount == "") {
				tradesellAmount = 0;
			}
			var tradeTurnover = util.accMul(usersellCnyBalance, tradesellAmount);
			var trademtccoin = Number($("#totalCoin").html());
			if (tradesellAmount > trademtccoin) {
				util.layerTips("sell_sub", util.getLan("comm.tips.9"), false, 1);
				return;
			}
			$("#sell-limit").html(util.numFormat(tradeTurnover, market.cnyDigit));
		}
	},
	saveCoinTrade : function(tradeType, flag) {
		if ($("#login").val() == 0) {
			window.location.href = "/user/login.html?forwardUrl=" + window.location.pathname;
			return;
		}
		errorele = "";
		if (tradeType == 0) {
			errorele = "buy_sub";
		} else {
			errorele = "sell_sub";
		}
		var tradePassword = $("#tradePassword").val();
		if (tradePassword == "false") {
			util.layerTips(errorele, util.getLan("comm.tips.17"), false, 1);
			return;
		}
		var isTelephoneBind = $("#isTelephoneBind").val();
		var isGoogleBind = $("#isGoogleBind").val();
		if (isTelephoneBind == "false" && isGoogleBind=="false") {
			util.layerTips(errorele, util.getLan("comm.tips.1"), false, 1);
			return;
		}
		var symbol = $("#symbol").val();
		var sellCoinName = $("#sellShortName").val();
		var buyCoinName = $("#buyShortName").val();
		var tradeAmount = 0;
		var tradePrice = 0;
		if (tradeType == 0) {
			tradeAmount = $("#buy-amount").val();
			tradePrice = $("#buy-price").val();
		} else {
			tradeAmount = $("#sell-amount").val();
			tradePrice = $("#sell-price").val();
		}
		var limited = 0;
		if (tradeType == 0) {
			var tradeTurnover = util.accMul(tradeAmount, tradePrice);
			if ($("#totalCny").length > 0 && Number($("#totalCny").html()) < Number(tradeTurnover)) {
				util.layerTips(errorele, util.getLan("comm.tips.9"), false, 1);
				return;
			}
		} else {
			if ($("#totalCoin").length > 0 && Number($("#totalCoin").html()) < Number(tradeAmount)) {
				util.layerTips(errorele, util.getLan("comm.tips.9"), false, 1);
				return;
			}
		}

		var reg = new RegExp("^[0-9]+\.{0,1}[0-9]{0,10}$");
		if (!reg.test(tradeAmount)) {
			util.layerTips(errorele, util.getLan("tradeMarket.tips.7"), false, 1);
			return;
		}

		if (!reg.test(tradePrice)) {
			util.layerTips(errorele, util.getLan("tradeMarket.tips.10"), false, 1);
			return;
		}

		var total = util.numFormat(util.accMul(tradeAmount, tradePrice), market.cnyDigit);
		if (parseFloat(total) < 0.00001) {
			util.layerTips(errorele, util.getLan("tradeMarket.tips.132", false, 1));
			return;
		}
		var isopen = $("#isopen").val();
		if (isopen == "true" && flag) {
			$("#tradeType").val(tradeType);
			$('#tradepass').modal({
				backdrop : 'static',
				keyboard : false,
				show : true
			});
			return;
		}
		var tradePwd = "";
		if ($("#tradePwd").length > 0) {
			tradePwd = util.trim($("#tradePwd").val());
		}
		if (tradePwd == "" && isopen == "true") {
			util.layerTips(errorele, util.getLan("comm.tips.8"), false, 1);
			$("#isopen").val("true");
			return;
		}
		var url = "";
		if (tradeType == 0) {
			url = "/trade/buyBtcSubmit.html?random=" + Math.round(Math.random() * 100);
		} else {
			url = "/trade/sellBtcSubmit.html?random=" + Math.round(Math.random() * 100);
		}
		tradePwd = isopen == "false" ? "" : tradePwd;
		var param = {
			tradeAmount : tradeAmount,
			tradeCnyPrice : tradePrice,
			tradePwd : tradePwd,
			symbol : symbol,
			limited : limited
		};
		var btntext = "";
		var btn = "";
		if (tradeType == 0) {
			btn = $("#buy_sub");
			btntext = btn.html();
			btn.html(util.getLan("tradeMarket.tips.14"));
		} else {
			btn = $("#sell_sub");
			btntext = btn.html();
			btn.html(util.getLan("tradeMarket.tips.15"));
		}
		var callbcack = function(data) {
			btn.html(btntext);
			util.layerTips(errorele, data.msg, false, 1);
			if (data.resultCode == 0) {
				$("#isopen").val("false");
				market.autoRefresh();	// 买入卖出成功回调
				/*window.setTimeout(function() {
					market.autoRefresh();// 买入卖出成功回调
				}, 1000);*/
				if (tradeType == 0) {
					document.getElementById("buy-amount").value="";
					$("#buy-limit").html("0");
				} else {
					var tradeAmount = document.getElementById("sell-amount").value="";
					$("#sell-limit").html("0");
				}
			}
		};
		util.network({
			btn : btn[0],
			url : url,
			param : param,
			success : callbcack,
			enter : true
		});
	},
	submitTradePwd : function() {
		var tradePwd = $("#tradePwd").val();
		if (tradePwd != "") {
			$("#tradePwd").val(tradePwd);
		}
		$('#tradepass').modal('hide');
		var tradeType = $("#tradeType").val();
		market.saveCoinTrade(tradeType, false);
	},
	hideLoading : function() {
		$('body').addClass('loaded');
		$('#loader-wrapper .loader-inner').remove();
	},
	getListData : function (map){
		 var rate = 1;
		 var coinclass =  '';
		 var param = {
		        cmd: 'sub',
		        args: ["marketrose"],
		        id: 1
		    };
		if (!rose_socket.checkOpen()) {
			rose_socket.doOpen();
		}
		rose_socket.send(param)
		rose_socket.on('message', function(data){
			if('ping' == data.cmd){
				return;
			}
			
			data.forEach(function(value,i){
				var pbits = Number(value.pbits);
				var vbits = Number(value.vbits);
		        var iconList = document.getElementsByClassName('lw-fwf');
		        var zuixin = document.getElementsByClassName('lw-zuixin');
		        var pnew = util.numFormat(Number(value.p_new)*rate, pbits);
		        zuixin[map.get(value.symbol)].innerHTML = pnew;
		        $(zuixin[map.get(value.symbol)]).addClass("coin_price").addClass(coinclass);
		        if(value.rose == 0){
		        	var rose = "+" + value.rose + ".00%";
		        	iconList[map.get(value.symbol)].className = "lw-fwf cred";
		        	iconList[map.get(value.symbol)].innerHTML = rose + "<i class='lw-up'></i>";
		        	return;
		        }
		        var rose = value.rose.toString();
		        if(rose.indexOf('-') == -1){

		        	iconList[map.get(value.symbol)].className = "lw-fwf cred";
		        	iconList[map.get(value.symbol)].innerHTML = "+" + rose + "%<i class='lw-up'></i>";
		        }else{
		        	
		        	iconList[map.get(value.symbol)].className = "lw-fwf cgreen2";
		        	iconList[map.get(value.symbol)].innerHTML = rose + "%" + "<i class='lw-down'></i>";
		        }
			});
		});
		rose_socket.on('close', function(){
			rose_socket.doOpen();
			rose_socket.on('open', function() {
		        console.log(' >> : 已重连')
		        if (rose_socket.checkOpen()) {
		        	rose_socket.send(param)
		        } else {
		        	rose_socket.on('open', function() {
		        		rose_socket.send(param)
		            })
		        }
		    });
		});
	}
}

$(function() {
	var iconList = document.getElementsByClassName('lw-coinName');
    var symbolMap = new Map();
    for(var i = 0; i < iconList.length;i++){
    	var symbol = iconList[i].getAttribute('data-setter');
    	symbolMap.set(parseInt(symbol),i);
    }
    market.getListData(symbolMap);
	market.resizeWidth();
	$(window).on("resize", function() {
		market.resizeWidth();
	});
	market.autoRefresh();
	$("#login_sub").on("click", function() {
		market.loginUser(this);
	});

	$("#buyBar li").on("click", function(e) {
		var val = $(this).data('points');
		market.onPortion(val, 0);
	});
	$("#sellBar li").on("click", function(e) {
		var val = $(this).data('points');
		market.onPortion(val, 1);
	});

	$("#buy-price").on("keyup", function() {
		market.numVerify(0);
	})
	$("#sell-price").on("keyup", function() {
		market.numVerify(1);
	})
	$("#buy-amount").on("keyup", function() {
		market.numVerify(0);
	})
	$("#sell-amount").on("keyup", function() {
		market.numVerify(1);
	})/*.on("keypress", function(event) {
		return util.goIngKeypress(this, event, market.coinDigit);
	});*/
	$('#tradepass').on('shown.bs.modal', function(e) {
		util.callbackEnter(market.submitTradePwd);
	});
	$('#tradepass').on('hidden.bs.modal', function(e) {
		document.onkeydown = function() {
		};
	});
	$("#buy_sub").on("click", function() {
		market.saveCoinTrade(0, true);
	});
	$("#sell_sub").on("click", function() {
		market.saveCoinTrade(1, true);
	});
	$("#modalbtn").on("click", function() {
		market.submitTradePwd();
	});
	
	market.hideLoading();
		
});

