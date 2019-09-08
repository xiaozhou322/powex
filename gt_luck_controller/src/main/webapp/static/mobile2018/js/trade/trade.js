var coinCount1 = 4;
var coinCount2 = 4;
if(document.getElementById("coinCount1") != null){
	coinCount1 = document.getElementById("coinCount1").value;
}
if(document.getElementById("coinCount2") != null){
	coinCount2 = document.getElementById("coinCount2").value;
}
var trade = {
	check : 1,
	NumVerify : function(tradeType) {
		var coinshortName = $("#coinshortName").val();
		if (tradeType == 0) {
			var userCnyBalance = document.getElementById("tradebuyprice").value;
			if (userCnyBalance == "") {
				userCnyBalance = 0;
			}
			var tradebuyAmount = document.getElementById("tradebuyamount").value;
			if (tradebuyAmount == "") {
				tradebuyAmount = 0;
			}
			var tradeTurnover = util.accMul(userCnyBalance, tradebuyAmount);
			var tradecnymoney = parseFloat(document.getElementById("toptradecnymoney").innerHTML);
			document.getElementById("tradebuyTurnover").innerHTML = util.moneyformat(tradeTurnover, 4);
			if (tradeTurnover > tradecnymoney) {
				util.showerrortips("buy-errortips", language["comm.error.tips.41"]);
				return;
			}
			util.hideerrortips("buy-errortips");
		} else {
			var usersellCnyBalance = document.getElementById("tradesellprice").value;
			if (usersellCnyBalance == "") {
				usersellCnyBalance = 0;
			}
			var tradesellAmount = document.getElementById("tradesellamount").value;
			if (tradesellAmount == "") {
				tradesellAmount = 0;
			}
			var tradeTurnover = util.accMul(usersellCnyBalance, tradesellAmount);
			var trademtccoin = parseFloat(document.getElementById("toptrademtccoin").innerHTML);
			document.getElementById("tradesellTurnover").innerHTML = util.moneyformat(tradeTurnover, 4);
			if (tradesellAmount > trademtccoin) {
				util.showerrortips("sell-errortips", language["comm.error.tips.42"].format(coinshortName));
				return;
			}
			util.hideerrortips("sell-errortips");
		}
	},
	submitTradePwd : function() {
		var tradePwd = document.getElementById("tradePwd").value;
		if (tradePwd != "") {
			document.getElementById("tradePwd").value = tradePwd;
			document.getElementById("isopen").value = "false";
		}
		$('#tradepass').modal('hide');
		var tradeType = document.getElementById("tradeType").value;
		
		if($("#limitedType").val()=="0"){
			trade.submitTradeBtcForm(tradeType, false);
		}else{
			trade2.submitTradeBtcForm(tradeType, false);
		}
	},
	submitTradeBtcForm : function(tradeType, flag) {
		
		$("#limitedType").val(0) ;
		
		errorele = "";
		if (tradeType == 0) {
			errorele = "buy-errortips";
		} else {
			errorele = "sell-errortips";
		}
		var tradePassword = document.getElementById("tradePassword").value;
		var userid = document.getElementById("userid").value;
		var minBuyCount = new Number(util.trim(document.getElementById("minBuyCount").value));
		if(userid ==0 || userid=="0"){
			util.layerAlert("", language["trade.error.tips.1"], 2);
			return;
		}
		if (tradePassword == "false") {
			util.layerAlert("", language["trade.error.tips.2"], 4);
			return;
		}
		var isTelephoneBind = document.getElementById("isTelephoneBind").value;
		if (isTelephoneBind == "false") {
			util.layerAlert("", language["trade.error.tips.3"], 2);
			return;
		}
		var symbol = document.getElementById("symbol").value;
		var coinName = document.getElementById("coinshortName").value;

		if (tradeType == 0) {
			var tradeAmount = document.getElementById("tradebuyamount").value;
			var tradeCnyPrice = document.getElementById("tradebuyprice").value;
		} else {
			var tradeAmount = document.getElementById("tradesellamount").value;
			var tradeCnyPrice = document.getElementById("tradesellprice").value;
		}
		var isAMT = util.checkNumber(tradeAmount);
		var isPrice = util.checkNumber(tradeCnyPrice);
		if(!isAMT || !isPrice){
			util.showerrortips(errorele, "单价或者数量有误");
			return;
		}
		var limited = 0;
		if (tradeType == 0) {
			var tradeTurnover = tradeAmount * tradeCnyPrice;
			if (document.getElementById("toptradecnymoney") != null && Number($("#toptradecnymoney").text()) < Number(tradeTurnover)) {
				util.showerrortips(errorele, language["comm.error.tips.41"]);
				return;
			} else {
				util.hideerrortips(errorele);
			}
		} else {
			if (document.getElementById("toptrademtccoin") != null && Number($("#toptrademtccoin").text()) < Number(tradeAmount)) {
				util.showerrortips(errorele, language["comm.error.tips.42"].format(coinName));
				return;
			} else {
				util.hideerrortips(errorele);
			}
		}
		var reg = new RegExp("^[0-9]+\.{0,1}[0-9]{0,8}$");
		if (!reg.test(tradeAmount)) {
			util.showerrortips(errorele, language["comm.error.tips.43"]);
			return;
		} else {
			util.hideerrortips(errorele);
		}
		console.log("tradeAmount=" + tradeAmount + " minBuyCount=" + minBuyCount);
		console.log("tradeAmount < minBuyCount = " + tradeAmount < minBuyCount);
		tradeAmount = new Number(tradeAmount);
		if (tradeAmount < minBuyCount) {
			util.showerrortips(errorele, language["comm.error.tips.44"].format(minBuyCount, coinName));
			return;
		} else {
			util.hideerrortips(errorele);
		}
		if (!reg.test(tradeCnyPrice)) {
			util.showerrortips(errorele, language["comm.error.tips.45"]);
			return;
		} else {
			util.hideerrortips(errorele);
		}
		if (tradeCnyPrice <= 0) {
			util.showerrortips(errorele, language["trade.error.tips.4"]);
			return;
		} else {
			util.hideerrortips(errorele);
		}
		var total = util.moneyformat(util.accMul(tradeAmount, tradeCnyPrice), coinCount1);
		if (parseFloat(total) <= 0) {
			util.showerrortips(errorele, language["trade.error.tips.5"]);
			return;
		} else {
			util.hideerrortips(errorele);
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
			util.showerrortips(errorele, language["comm.error.tips.46"]);
			document.getElementById("isopen").value = true;
			return;
		} else {
			util.hideerrortips(errorele);
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
		var btntext="";
		var btn = "";
		if(tradeType==0){
			btn = document.getElementById("buybtn");
			btntext = btn.innerHTML;
			btn.innerHTML = language["trade.error.tips.6"];
		}else{
			btn = document.getElementById("sellbtn");
			btntext = btn.innerHTML;
			btn.innerHTML = language["trade.error.tips.7"];
		}
		btn.disabled = true;		
		jQuery.post(url, param, function(data) {
			btn.disabled = false;
			btn.innerHTML = btntext;
			util.showerrortips(errorele, data.msg);
			if (data.resultCode == -2) {
				document.getElementById("isopen").value = "true";
			}else if(data.resultCode == 0) {
				util.showerrortips(errorele, language["trade.error.tips.8"]);
				if (tradeType == 0) {
					document.getElementById("tradebuyamount").value="";
					$("#tradebuyTurnover").html("0");
				} else {
					var tradeAmount = document.getElementById("tradesellamount").value="";
					$("#tradesellTurnover").html("0");
				}
				window.setTimeout(function() {
					$("#"+errorele).html("");
				}, 2000);
			}
		}, "json");
	},
	cancelEntrustBtc: function(id) {
        util.layerConfirm(language["trade.error.tips.9"],
        function(result) {
            var url = "/trade/cancelEntrust.html?random=" + Math.round(100 * Math.random()),
            param = {
    			id : id
    		};
            $.post(url, param,
            function(id) {
                null != id && (location.reload(true), layer.close(result))
            })
        });
    },
    cancelAllEntrustBtc: function(id) {
        util.layerConfirm(language["trade.error.tips.10"],
                function(result) {
                    var url = "/trade/cancelAllEntrust.html?random=" + Math.round(100 * Math.random()),
                    param = {
            			id : id
            		};
                    $.post(url, param,
                    function(id) {
                        null != id && (location.reload(true), layer.close(result))
                    })
                });
    },
	antoTurnover : function(price, money, mtcNum, tradeType) {
		if (tradeType == 0) {
			document.getElementById("tradebuyprice").value = util.moneyformat(price, coinCount1);
			document.getElementById("tradebuyamount").value = util.moneyformat(mtcNum, coinCount2);
			var tradeTurnover = util.moneyformat(util.accMul(price, mtcNum), 4);
//			console.log(tradeTurnover);
			var tradecnymoney = util.moneyformat(Number(document.getElementById("toptradecnymoney").innerHTML), 4);
			if (parseFloat(tradeTurnover) > parseFloat(tradecnymoney)) {
				util.showerrortips("buy-errortips", language["comm.error.tips.41"]);
				return;
			}
			document.getElementById("tradebuyTurnover").innerHTML = tradeTurnover;
			util.hideerrortips("buy-errortips");
		} else {
			var coinName = document.getElementById("coinshortName").value;
			document.getElementById("tradesellprice").value = util.moneyformat(price, coinCount1);
			document.getElementById("tradesellamount").value = util.moneyformat(mtcNum, coinCount2);
			var tradeTurnover = util.accMul(price, mtcNum);
			var trademtccoin = util.moneyformat(Number(document.getElementById("toptrademtccoin").innerHTML), 4);
			if (parseFloat(mtcNum) > parseFloat(trademtccoin)) {
				util.showerrortips("sell-errortips", language["comm.error.tips.42"].format(coinName));
				return;
			}
			document.getElementById("tradesellTurnover").innerHTML = util.moneyformat(tradeTurnover, 4);
			util.hideerrortips("sell-errortips");
		}
	},
	entrustInfoTab : function(ele) {
		var type = ele.data().type;
		var title = "";
		var value = ele.data().value;;
		if (value == 0) {
			value = 1;
			title = language["comm.error.tips.47"] + "&nbsp;+";
			$("#fentrustsbody" + type).hide();
		} else {
			value = 0;
			title = language["comm.error.tips.48"] + "&nbsp;-";
			$("#fentrustsbody" + type).show();
		}
		ele.data().value = value;
		ele.html(title);
	},
	entrustLog : function(id) {
		var url = "/trade/entrustLog.html?random=" + Math.round(Math.random() * 100);
		var param = {
			id : id
		};
		jQuery.post(url, param, function(data) {
			if (data != null && data.result == true) {
				var modal = $("#entrustsdetail");
				modal.find('.modal-title').html(data.title);
				modal.find('.modal-body').html(data.content);
				modal.modal('show');
			}
		}, "json");
	},
	onPortion : function(portion, tradeType) {
		portion = util.accDiv(portion, 100);
		if (tradeType == 0) {
			var money = Number(document.getElementById("tradebuyprice").value);
			var tradecnymoney = Number(document.getElementById("toptradecnymoney").innerHTML);
			var mtcNum = util.accDiv(tradecnymoney, money);
			mtcNum = util.accMul(mtcNum, portion);
			var portionMoney = util.accMul(money, mtcNum);
			this.antoTurnover(money, portionMoney, mtcNum, tradeType);
		} else {
			var money = Number(document.getElementById("tradesellprice").value);
			var trademtccoin = Number(document.getElementById("toptrademtccoin").innerHTML);
			mtcNum = util.accMul(trademtccoin, portion);
			var portionMoney = util.accMul(money, mtcNum);
			this.antoTurnover(money, portionMoney, mtcNum, tradeType);
		}
	},
	lastprice : 0,
	fristprice : true,
	autoRefresh : function() {
		var symbol = document.getElementById("symbol").value;
		var coinshortName = $("#coinshortName").val();
		var url = util.globalurl.market;
		var buysellcount = 3;
		var successcount = 3;
		var url = url.format(symbol, buysellcount, successcount) + "&random=" + Math.round(Math.random() * 100);
		$.get(url, function(data) {
			$.each(data.buys, function(key, value) {
				if (key >= buysellcount) {
					return;
				}
				var buyele = $("#buy" + key);
				if (buyele.length == 0) {
					$("#buybox").append('<li id="buy' + key + '"  class="list-group-item clearfix buysellmap" data-type="1" data-money="' + util.moneyformat(Number(value.price), coinCount1) + '" data-num="' + util.moneyformat(Number(value.amount), coinCount2) + '">' + '<span class="col-xs-2 redtips padding-clear" style="width:20%;">' + '买' + (key + 1) + '</span>' + '<span class="col-xs-5 text-right padding-clear" style="width:40%;">' + util.moneyformat(Number(value.price), coinCount1) + '</span>' + '<span class="col-xs-5 redtips text-right padding-clear" style="width:40%;">' + util.moneyformat(Number(value.amount), coinCount2) + '</span></li>');
				} else {
					buyele.data().money = util.moneyformat(Number(value.price), coinCount1);
					buyele.data().num = util.moneyformat(Number(value.amount), coinCount2);
					buyele.children()[1].innerHTML = util.moneyformat(Number(value.price), coinCount1);
					buyele.children()[2].innerHTML = util.moneyformat(Number(value.amount), coinCount2);
				//	buyele.children()[3].innerHTML = util.moneyformat(Number(value.amount)*Number(value.price), 4);
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
					$("#sellbox").prepend('<li id="sell' + key + '"  class="list-group-item clearfix buysellmap" data-type="0" data-money="' + util.moneyformat(Number(value.price), coinCount1) + '" data-num="' + util.moneyformat(Number(value.amount), coinCount2) + '">' + '<span class="col-xs-2 greentips padding-clear" style="width:20%;">' + '卖' + (key + 1) + '</span>' + '<span class="col-xs-5 text-right padding-clear" style="width:40%;">' + util.moneyformat(Number(value.price), coinCount1) + '</span>' + '<span class="col-xs-5 greentips text-right padding-clear" style="width:40%;">' + util.moneyformat(Number(value.amount), coinCount2) + '</span></li>');
				} else {
					sellele.data().money = util.moneyformat(Number(value.price), coinCount1);
					sellele.data().num = util.moneyformat(Number(value.amount), coinCount2);
					sellele.children()[1].innerHTML = util.moneyformat(Number(value.price), coinCount1);
					sellele.children()[2].innerHTML = util.moneyformat(Number(value.amount), coinCount2);
				//	sellele.children()[3].innerHTML = util.moneyformat(Number(value.amount)*Number(value.price), 4);
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
				loghtml += '<li class="list-group-item clearfix">' + '<span class="col-xs-2 padding-clear">' + value.time + '</span>' + '<span class="col-xs-5 text-right padding-clear">' + util.moneyformat(Number(value.price), coinCount1) + '</span>' + '<span class="col-xs-5 text-right padding-clear ' + (value.en_type == 'ask' ? "greentips" : "redtips") + '">' + util.moneyformat(Number(value.amount), coinCount2)+ '</li>';
			});
			$("#logbox").html("").append(loghtml);
			if (trade.fristprice) {
				if (data.buys.length <= 0) {
					$("#tradesellprice").val(util.moneyformat(Number(data.p_new), coinCount1));
				} else {
					$("#tradesellprice").val(data.buys[0].price);
				}
				if (data.sells.length <= 0) {
					$("#tradebuyprice").val(util.moneyformat(Number(data.p_new), coinCount1));
				} else {
					$("#tradebuyprice").val(data.sells[0].price);
				}
				trade.fristprice = false;
			}
			$(".buysellmap").on("click", function() {
				var data = $(this).data();
				var type = data.type;
				var money = data.money;
				var num = data.num;
				var tradeAmount = "";
				if (type == 0) {
					tradeAmount = document.getElementById("tradebuyamount").value;
				} else {
					tradeAmount = document.getElementById("tradesellamount").value;
				}
				if (tradeAmount == "") {
					tradeAmount = 0;
				}
				trade.antoTurnover(money, 0, num, type);
			});

			var p_new = util.moneyformat(Number(data.p_new), coinCount1);
			
			$("#lastprice").html(p_new);
//			$("#marketLast").html(p_new);
//			$("#marketHigh").html(Number(data.high));
//			$("#marketLow").html(Number(data.low));
//			$("#marketBuy").html(Number(data.buy1));
//			$("#marketSell").html(Number(data.sell1));
//			$("#marketVol").html(Number(data.vol));

			
			if (p_new > trade.lastprice) {
				$("#lastprice").parent().removeClass("greentips").addClass("redtips");
				$("#lastpriceicon").removeClass("green").addClass("red");
			} else if (p_new < trade.lastprice) {
				$("#lastprice").parent().removeClass("redtips").addClass("greentips");
				$("#lastpriceicon").removeClass("red").addClass("green");
			}
			trade.lastprice = p_new;
		}, "json");
		url = "/real/userassets.html?random=" + Math.round(Math.random() * 100);
		$.post(url, {
			symbol : symbol
		}, function(data) {
			if (data != "") {
				$("#toptradecnymoney").html(util.moneyformat(Number(data.availableCny), 4));
				$("#toptrademtccoin").html(util.moneyformat(Number(data.availableCoin), 4));
				$("#toptradelevercny").html(util.moneyformat(Number(data.frozenCny), 4));
				$("#toptradelevercoin").html(util.moneyformat(Number(data.frozenCoin), 4));
				if ($("#headertotalasset").length > 0) {
					$("#headertotalasset").html('￥' + util.moneyformat(Number(data.leveritem.total), 4));
				}
//				if ($("#headerasset").length > 0) {
//					$("#headerasset").html('￥' + util.moneyformat(Number(data.leveritem.asset), 4));
//				}
//				if ($("#headerscore").length > 0) {
//					$("#headerscore").html(util.moneyformat(Number(data.leveritem.score), 0));
//				}
				if ($("#headercny0").length > 0) {
					var cnychild = $("#headercny0").children();
					cnychild[1].innerHTML = util.moneyformat(Number(data.cnyitem.total), 4);
					cnychild[2].innerHTML = util.moneyformat(Number(data.cnyitem.frozen), 4);
					/*cnychild[3].innerHTML = util.moneyformat(Number(data.cnyitem.borrow), 2);*/
				}
				if ($("#headercoin" + data.coinitem.id).length > 0) {
					var coinchild = $("#headercoin" + data.coinitem.id).children();
					coinchild[1].innerHTML = util.moneyformat(Number(data.coinitem.total, 4));
					coinchild[2].innerHTML = util.moneyformat(Number(data.coinitem.frozen, 4));
					/*coinchild[3].innerHTML = util.moneyformat(Number(data.coinitem.borrow, 4));*/
				}
			}
		}, "json");

		var fentruststitle = $(".fentruststitle");
		var displaytype0, displaytype1, displayvalue0, displayvalue1;
		$.each(fentruststitle, function(index, key) {
			key = $(key);
			if (index == 0) {
				displaytype0 = key.data('type');
				displayvalue0 = key.data('value');
			}
			if (index == 1) {
				displaytype1 = key.data('type');
				displayvalue1 = key.data('value');
			}
		});
		url = "/trade/entrustInfo.html?displaytype0=" + displaytype0 + "&displaytype1=" + displaytype1 + "&displayvalue0=" + displayvalue0 + "&displayvalue1=" + displayvalue1 + "&symbol=" + symbol + "&type=0&tradeType=0&random=" + Math.round(Math.random() * 100);
		var hidden0 = $("#fentrustsbody0").is(":hidden") ;
		var hidden1 = $("#fentrustsbody1").is(":hidden") ;
		$.post(url,null,function(data){
			if(data.indexOf('委托记录')==-1 && data.indexOf('Entrust Record')==-1){
				window.location.href = '/' ;
				return ;
			}
			$("#entrustInfo").html(data);
			$(".fentruststitle").on("click", function() {
				trade.entrustInfoTab($(this));
			});
			$(".tradecancel").on("click", function() {
				var id = $(this).data().value;
				trade.cancelEntrustBtc(id);
			});
			$(".allcancel").on("click", function() {
				var id = $(this).data().value;
				trade.cancelAllEntrustBtc(id);
			});
			$(".tradelook").on("click", function() {
				var id = $(this).data().value;
				trade.entrustLog(id);
			});
			
			if(hidden0 == true ){
				$(".fentruststitle").eq(0).click();
			}
			if(hidden1 == true ){
				$(".fentruststitle").eq(1).click();
			}
			
		}) ;
		window.setTimeout(function() {
			trade.autoRefresh();
		}, 5000);
	}
};

$(function() {
	if(document.getElementById("tradesellTurnover") != null){
		document.getElementById("tradesellTurnover").value=0;
	}
	if(document.getElementById("tradebuyTurnover") != null){
		document.getElementById("tradebuyTurnover").value=0;
	}
	if(document.getElementById("tradebuyamount") != null){
		document.getElementById("tradebuyamount").value=0;
	}
	if(document.getElementById("tradesellamount") != null){
		document.getElementById("tradesellamount").value=0;
	}
	
	$("#tradebuyprice").on("keyup", function() {
		trade.NumVerify(0);
	}).on("keypress", function(event) {
		return util.VerifyKeypress(this, event, coinCount1);
	}).on("click", function() {
		this.focus();
		this.select();
	});
	$("#tradesellprice").on("keyup", function() {
		trade.NumVerify(1);
	}).on("keypress", function(event) {
		return util.VerifyKeypress(this, event, coinCount1);
	}).on("click", function() {
		this.focus();
		this.select();
	});
	$("#tradebuyamount").on("keyup", function() {
		trade.NumVerify(0);
	}).on("keypress", function(event) {
		return util.VerifyKeypress(this, event, coinCount2);
	}).on("click", function() {
		this.focus();
		this.select();
	});
	$("#tradesellamount").on("keyup", function() {
		trade.NumVerify(1);
	}).on("keypress", function(event) {
		return util.VerifyKeypress(this, event, coinCount2);
	}).on("click", function() {
		this.focus();
		this.select();
	});
	$("#buybtn").on("click", function() {
		trade.submitTradeBtcForm(0, true);
	});
	$("#sellbtn").on("click", function() {
		trade.submitTradeBtcForm(1, true);
	});
	$("#modalbtn").on("click", function() {
		trade.submitTradePwd();
	});
	$(".tradecancel").on("click", function() {
		var id = $(this).data().value;
		trade.cancelEntrustBtc(id);
	});
	$(".allcancel").on("click", function() {
		var id = $(this).data().value;
		trade.cancelAllEntrustBtc(id);
	});
	$(".tradelook").on("click", function() {
		var id = $(this).data().value;
		trade.entrustLog(id);
	});
	$(".buysellmap").on("click", function() {
		var data = $(this).data();
		var type = data.type;
		var money = data.money;
		var num = data.num;
		var tradeAmount = "";
		if (type == 0) {
			tradeAmount = document.getElementById("tradebuyamount").value;
		} else {
			tradeAmount = document.getElementById("tradesellamount").value;
		}
		if (tradeAmount == "") {
			tradeAmount = 0;
		}
		trade.antoTurnover(money, 0, num, type);
	});
	$(".fentruststitle").on("click", function() {
		trade.entrustInfoTab($(this));
	});
	$("#buyslider").on("change", function(e, val) {
		trade.onPortion(val, 0);
	//	$("#buyslidertext").html(val + "%");
	});
	$("#sellslider").on("change", function(e, val) {
		trade.onPortion(val, 1);
	//	$("#sellslidertext").html(val + "%");
	});
	
	if(document.getElementById("tradesellTurnover") != null){
		trade.autoRefresh();
	}
	
	$('#tradepass').on('shown.bs.modal', function (e) {
		util.callbackEnter(trade.submitTradePwd);
	});
	$('#tradepass').on('hidden.bs.modal', function (e) {
		document.onkeydown=function(){};
	});
	
//	$("#openfullscreen").on("click", function() {
//		trade.klineFullScreenOpen();
//	});
//	$("#closefullscreen").on("click", function() {
//		trade.klineFullScreenClose();
//	});
//	$("#openZYKline").on("click", function() {
//		trade.ZYkline();
//	});
//	$("#openPTKline").on("click", function() {
//		trade.openPTKline();
//	});
});

$(".databtn").click(function(){
	if($(this).data("type")==1){
		$("#buypricediv").show() ;
		$("#buymarketdiv").hide() ;
		$(this).addClass("datatime-sel") ;
		$(this).siblings().removeClass("datatime-sel") ;
	}else if($(this).data("type")==2){
		$("#buypricediv").hide() ;
		$("#buymarketdiv").show() ;
		$(this).addClass("datatime-sel") ;
		$(this).siblings().removeClass("datatime-sel") ;
	}else if($(this).data("type")==3){
		$("#sellpricediv").show() ;
		$("#sellmarketdiv").hide() ;
		$(this).addClass("datatime-sel") ;
		$(this).siblings().removeClass("datatime-sel") ;
	}else if($(this).data("type")==4){
		$("#sellpricediv").hide() ;
		$("#sellmarketdiv").show() ;
		$(this).addClass("datatime-sel") ;
		$(this).siblings().removeClass("datatime-sel") ;
	}
}) ;