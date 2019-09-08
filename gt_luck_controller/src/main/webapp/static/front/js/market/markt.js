var markt = {
	check : 1,
	NumVerify : function(tradeType) {
		var coinshortName = $("#coinshortName").value;
		var userCnyBalance = document.getElementById("tradebuyprice").value;
		if (userCnyBalance == "") {
			userCnyBalance = 0;
		}
		var tradebuyAmount = document.getElementById("tradebuyamount").value;
		if (tradebuyAmount == "") {
			tradebuyAmount = 0;
		}
		var tradeTurnover = util.accMul(userCnyBalance, tradebuyAmount);
		document.getElementById("tradebuyTurnover").innerHTML = util.moneyformat(tradeTurnover, 2);
		util.hideerrortips("");
	},
	coinTypeChange : function() {
		var coinType = document.getElementById("coinType").value;
		window.location.href="/market.html?type="+coinType;
	},
	submitTradePwd : function() {
		var tradePwd = document.getElementById("tradePwd").value;
		if (tradePwd != "") {
			document.getElementById("tradePwd").value = tradePwd;
			document.getElementById("isopen").value = "false";
		}
		$('#tradepass').modal('hide');
		var tradeType = document.getElementById("tradeType").value;
		this.submitTradeBtcForm(tradeType, false);
	},
	submitTradeBtcForm : function(tradeType, flag) {
		if (this.check == 2) {
			return;
		}
		var login = document.getElementById("login").value;
		var symbol = document.getElementById("symbol").value;
		if(login=="false"){
			window.location.href="/user/login.html?forwardUrl=/market.html?symbol="+symbol;
		}
		var tradePassword = document.getElementById("tradePassword").value;
		if (tradePassword == "true") {
			util.showerrortips("", "请先设置交易密码", {
				okbtn : function() {
					window.location.href = '/user/security.html#traingtr';
				},
				noshow : true
			});
			return;
		}
		var isTelephoneBind = document.getElementById("isTelephoneBind").value;
		if (isTelephoneBind == "false") {
			util.showerrortips("", "请先绑定您的手机", {
				okbtn : function() {
					window.location.href = '/user/security.html';
				},
				noshow : true
			});
			return;
		}
		var coinName = document.getElementById("coinshortName").value;
		
		var tradeAmount = document.getElementById("tradebuyamount").value;
		var tradeCnyPrice = document.getElementById("tradebuyprice").value;

		var limited = 0;
		if (tradeType == 0) {
			var tradeTurnover = tradeAmount * tradeCnyPrice;
			if (document.getElementById("toptradecnymoney") != null && Number(document.getElementById("toptradecnymoney").value) < Number(tradeTurnover)) {
				util.showerrortips("errortips", language["comm.error.tips.41"]);
				return;
			} else {
				util.hideerrortips("errortips");
			}
		} else {
			if (document.getElementById("toptrademtccoin") != null && Number(document.getElementById("toptrademtccoin").value) < Number(tradeAmount)) {
				util.showerrortips("errortips", language["comm.error.tips.42"].format(coinName));
				return;
			} else {
				util.hideerrortips("errortips");
			}
		}
		var reg = new RegExp("^[0-9]+\.{0,1}[0-9]{0,8}$");
		if (!reg.test(tradeAmount)) {
			util.showerrortips("errortips", language["comm.error.tips.43"]);
			return;
		} else {
			util.hideerrortips("errortips");
		}
		if (tradeAmount < 0.0001) {
			util.showerrortips("errortips", language["comm.error.tips.44"].format(0.0001, coinName));
			return;
		} else {
			util.hideerrortips("errortips");
		}
		if (!reg.test(tradeCnyPrice)) {
			util.showerrortips("errortips", language["comm.error.tips.45"]);
			return;
		} else {
			util.hideerrortips("errortips");
		}
		if (tradeCnyPrice < 0.01) {
			util.showerrortips("errortips", "出价不能小于0.01！");
			return;
		} else {
			util.hideerrortips("errortips");
		}
		var total=util.moneyformat(util.accMul(tradeAmount,tradeCnyPrice),2);
		if(parseFloat(total)<0.01){
			util.showerrortips("errortips", "交易额不能小于0.01");
			return;
		}else{
			util.hideerrortips("errortips");
		}
		var isopen = document.getElementById("isopen").value;
		if (isopen == "true" && flag) {
			document.getElementById("tradeType").value = tradeType;
			$('#tradepass').modal({
				backdrop : 'static',
				keyboard : false,
				show : true
			});
			return;
		}
		var tradePwd = "";
		if (document.getElementById("tradePwd") != null) {
			tradePwd = util.trim(document.getElementById("tradePwd").value);
		}
		if (tradePwd == "" && isopen == "true") {
			util.showerrortips("errortips", language["comm.error.tips.46"]);
			document.getElementById("isopen").value = true;
			return;
		} else {
			util.hideerrortips("errortips");
		}
		var url = "";
		if (tradeType == 0) {
			url = "/trade/buyBtcSubmit.html?random=" + Math.round(Math.random() * 100);
		} else {
			url = "/trade/sellBtcSubmit.html?random=" + Math.round(Math.random() * 100);
		}
		tradePwd = isopen == "true" ? "" : tradePwd;
		var param = {
			tradeAmount : tradeAmount,
			tradeCnyPrice : tradeCnyPrice,
			tradePwd : tradePwd,
			symbol : symbol,
			limited : limited
		};
		check = 2;
		$.post(url, param, function(data) {
			check = 1;
			util.showerrortips("errortips", data.msg);
			if (data.code == -2) {
				document.getElementById("isopen").value = "true";
				document.getElementById("tradePwd").value = "";
			}
		}, "json");
	},
	marktunit:function(buys,sells){
		var tmpBuy=buys.concat(sells);
		if(tmpBuy.length<=0){
			return;
		}
		tmpBuy.sort(function (a, b) {
            return a.amount - b.amount;
        });
		var i=Math.floor((tmpBuy.length/3)*2,0);
        return tmpBuy[i].amount<1?1:tmpBuy[i].amount;
	},
	autoRefresh : function() {
		var symbol = document.getElementById("symbol").value;
		var coinCount1 = $("#coinCount1").val();
		var coinCount2 = $("#coinCount2").val();
		var coinshortName = $("#coinshortName").val();		
		var buysellcount = 21;
		var successcount = 21;
		/*
		 * var url = "/real/userassets.html?random=" + Math.round(Math.random() *
		 * 100); $.post(url, { symbol : symbol }, function(data) { if (data !=
		 * "") {
		 * $("#toptradecnymoney").val(util.moneyformat(Number(data.availableCny),
		 * 2));
		 * $("#toptrademtccoin").val(util.moneyformat(Number(data.availableCoin),
		 * 4));
		 * $("#tradecnymoney").html(util.moneyformat(Number(data.availableCny),
		 * 2));
		 * $("#trademtccoin").html(util.moneyformat(Number(data.availableCoin),
		 * 4)); } }, "json");
		 */
		url = "/real/market2.html?symbol={0}";
		url = url.format(symbol) + "&random=" + Math.round(Math.random() * 100);
		$.get(url, function(data) {
			if (data != "") {
				var cny = $("#toptradecnymoney").val();
				var coin = $("#toptrademtccoin").val();
				var lastprice = Number(data.p_new);
				var marketBuy = Number(data.buy1);
				var marketSell = Number(data.sell1);
				var marketHigh = Number(data.high);
				var marketLow = Number(data.low);
				var marketVol = Number(data.vol);
//				$("#marketLast").html("￥"+util.moneyformat(lastprice));
//				$("#marketBuy").html("￥"+util.moneyformat(marketBuy));
//				$("#marketSell").html("￥"+util.moneyformat(marketSell));
//				$("#marketHigh").html("￥"+util.moneyformat(marketHigh));
//				$("#marketLow").html("￥"+util.moneyformat(marketLow));
//				$("#marketVol").html(util.moneyformat(marketVol));
				
				$("#getcoin").html(lastprice==0?0:util.moneyformat(util.accDiv(cny, lastprice), 4));
				$("#getcny").html(lastprice==0?0:util.moneyformat(util.accMul(coin, lastprice), 2));
				var unit=markt.marktunit(data.buys,data.sells);
				$.each(data.buys, function(key, value) {
					if (key >= buysellcount) {
						return;
					}
					var buyele = $("#buy" + key);
					if (buyele.length == 0) {
						$("#buybox").append("<li id='buy" + key + "' class='list-group-item clearfix'>" +
								"<span class='proportioninfo'>"+
								"<span class='col-xs-2 buycolor padding-clear'>买 " + (key + 1) + "</span>" + 
								"<span class='col-xs-4 padding-clear text-right'>" + util.moneyformat(Number(value.price), coinCount1) + "</span>" + 
								"<span class='col-xs-6 padding-left-clear text-right'>" + util.moneyformat(Number(value.amount), coinCount2) + "</span>" + 
								"</span>"+
								"<span class='proportion'><span class='proportion-item-buy' style='width:" + (Number(value.amount) > unit ? 100 : Math.round(Number(value.amount)/unit*100)) + "%'></span></span>" + 
								"</li>");
					} else {
						buyele.children()[0].children[1].innerHTML = util.moneyformat(Number(value.price), coinCount1);
						buyele.children()[0].children[2].innerHTML = util.moneyformat(Number(value.amount), coinCount2);
						buyele.children()[1].children[0].style.width = (Number(value.amount) > unit ? 100 : Math.round(Number(value.amount)/unit*100)) + '%';
					}
				});
				for ( var i = data.buys.length; i < buysellcount; i++) {
					$("#buy" + i).remove();
				}
				$.each(data.sells, function(key, value) {
					if (key >= buysellcount) {
						return;
					}
					var sellele = $("#sell" + key);
					if (sellele.length == 0) {
						$("#sellbox").append("<li id='sell" + key + "' class='list-group-item clearfix'>" + 
								"<span class='proportioninfo'>"+
								"<span class='col-xs-2 sellcolor padding-clear'>卖 " + (key + 1) + "</span>" + 
								"<span class='col-xs-4 padding-clear  text-right'>" + util.moneyformat(Number(value.price), coinCount1) + "</span>" + 
								"<span class='col-xs-6 padding-left-clear  text-right'>" + util.moneyformat(Number(value.amount), coinCount2) + "</span>" + 
								"</span>"+
								"<span class='col-xs-4 padding-clear proportion'><span class='proportion-item-sell' style='width:" + (Number(value.amount) > unit ? 100 : Math.round(Number(value.amount)/unit*100)) + "%'></span></span>" + "</li>");
					} else {
						sellele.children()[0].children[1].innerHTML = util.moneyformat(Number(value.price), coinCount1);
						sellele.children()[0].children[2].innerHTML = util.moneyformat(Number(value.amount), coinCount2);
						sellele.children()[1].children[0].style.width = (Number(value.amount) > unit ? 100 : Math.round(Number(value.amount)/unit*100)) + '%';
					}
				});
				for ( var i = data.sells.length; i < buysellcount; i++) {
					$("#sell" + i).remove();
				}
				var loghtml = "";
				$.each(data.trades, function(key, value) {
					if (key >= successcount) {
						return;
					}
					loghtml += '<li class="list-group-item clearfix">' + 
					'<span class="col-xs-4 padding-right-clear">' + value.time + '</span>' + 
					'<span class="col-xs-3 padding-clear text-right ">' + util.moneyformat(Number(value.price), coinCount1) + '</span>' + 
					'<span class="col-xs-5 text-right ' + (value.en_type == 'ask' ? "sellcolor" : "buycolor") + '">' + util.moneyformat(Number(value.amount), coinCount2) + '</span>' + '</li>';
				});
				$("#logbox").html("").append(loghtml);
			};
		}, "json");
		window.setTimeout(function() {
			markt.autoRefresh();
		}, 5000);
	},
	autotradingbox : function() {
		var offsetop = $(".info-box").offset().top;
		$(window).scroll(function() {
			var scoretop = $('body').scrollTop();
			if (scoretop > offsetop) {
				$(".info-box").css("top", (scoretop - offsetop + 30) + "px");
			} else {
				$(".info-box").css("top", "0px");
			}
		});
	},
	klineFullScreenOpen:function (){
		document.getElementById('closefullscreen').style.display="block";
		document.getElementById('openfullscreen').style.display="none";
		document.getElementById('delegateinfo').style.display="none";
		document.getElementById('allFooter').style.display="none";
		document.getElementById('allheader').style.display="none";
		document.getElementById('marketheader').style.display="none";	
		document.getElementById('klineFullScreen').className="fullscreen";
	},
	klineFullScreenClose:function (isRemote){
		document.getElementById('closefullscreen').style.display="none";
		document.getElementById('openfullscreen').style.display="block";
		document.getElementById('delegateinfo').style.display="block";
		document.getElementById('allFooter').style.display="block";
		document.getElementById('allheader').style.display="block";
		document.getElementById('marketheader').style.display="block";	
		document.getElementById('klineFullScreen').className="";
	}
};

$(function() {
	$("#tradebuyprice").on("keyup", function() {
		markt.NumVerify(0);
	}).on("keypress", function(event) {
		return util.VerifyKeypress(this, event, 2);
	});
	$("#tradebuyamount").on("keyup", function() {
		markt.NumVerify(0);
	}).on("keypress", function(event) {
		return util.VerifyKeypress(this, event, 4);
	});
	$("#buybtn").on("click", function() {
		markt.submitTradeBtcForm(0, true);
	});
	$("#sellbtn").on("click", function() {
		markt.submitTradeBtcForm(1, true);
	});
	$("#modalbtn").on("click", function() {
		markt.submitTradePwd();
	});
	$("#openfullscreen").on("click", function() {
		markt.klineFullScreenOpen();
	});
	$("#closefullscreen").on("click", function() {
		markt.klineFullScreenClose();
	});
	$("#coinType").on("change", function() {
		markt.coinTypeChange();
	});
	markt.autoRefresh();
	markt.autotradingbox();
});