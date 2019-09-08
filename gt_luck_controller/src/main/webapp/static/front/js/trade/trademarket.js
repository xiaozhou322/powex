
var trade2 = {
	check : 1,
	NumVerify : function(tradeType) {
		var coinshortName = $("#coinshortName").val();
		if (tradeType == 0) {
			var tradecnymoney = Number(document.getElementById("toptradecnymoney").innerHTML);
			var tradebuyprice2 = Number($("#tradebuyprice2").val()) ;
			if (tradebuyprice2 > tradecnymoney) {
				util.showerrortips("buy-errortips2", language["comm.error.tips.41"]);
				return;
			}
			util.hideerrortips("buy-errortips2");
		} else {
			var toptrademtccoin = Number(document.getElementById("toptrademtccoin").innerHTML);
			var tradesellprice2 = Number($("#tradesellprice2").val()) ;
			if (tradesellprice2 > toptrademtccoin) {
				util.showerrortips("sell-errortips2", language["comm.error.tips.42"].format(coinshortName));
				return;
			}
			util.hideerrortips("sell-errortips2");
			
		}
	},
	
	submitTradeBtcForm : function(tradeType, flag) {
		$("#limitedType").val(1) ;
		
		errorele = "";
		if (tradeType == 0) {
			errorele = "buy-errortips2";
		} else {
			errorele = "sell-errortips2";
		}
		var tradePassword = document.getElementById("tradePassword").value;
		var userid = document.getElementById("userid").value;
		if(userid ==0 || userid=="0"){
			util.showerrortips("", "请先登陆", {
				okbtn : function() {
					window.location.href = '/user/login.html';
				},
				noshow : true
			});
			return;
		}
		if (tradePassword == "false") {
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
		var symbol = document.getElementById("symbol").value;
		var coinName = document.getElementById("coinshortName").value;

		var reg = new RegExp("^[0-9]+\.{0,1}[0-9]{0,8}$");
		var limited = 1;

		var tradebuyprice2 = 0 ;
		var tradesellprice2 = 0 ;
		if (tradeType == 0) {
			if (!reg.test(document.getElementById("tradebuyprice2").value)) {
				util.showerrortips(errorele, language["comm.error.tips.43"]);
				return;
			} 
			
			var tradecnymoney = Number($("#toptradecnymoney").html()) ;
			tradebuyprice2 = Number($("#tradebuyprice2").val()) ;
			if (tradebuyprice2 > tradecnymoney) {
				util.showerrortips("buy-errortips2", language["comm.error.tips.41"]);
				return;
			}
			util.hideerrortips(errorele);
		} else {
			if (!reg.test(document.getElementById("tradesellprice2").value)) {
				util.showerrortips(errorele, language["comm.error.tips.45"]);
				return;
			}
			
			var toptrademtccoin = Number(document.getElementById("toptrademtccoin").innerHTML);
			tradesellprice2 = Number($("#tradesellprice2").val()) ;
			if (tradesellprice2 > toptrademtccoin) {
				util.showerrortips("sell-errortips2", language["comm.error.tips.42"].format(coinshortName));
				return;
			}
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
			tradeAmount : tradesellprice2,
			tradeCnyPrice : tradebuyprice2,
			tradePwd : tradePwd,
			symbol : symbol,
			limited : limited
		};
		var btntext="";
		var btn = "";
		if(tradeType==0){
			btn = document.getElementById("buybtn");
			btntext = btn.innerHTML;
			btn.innerHTML = "正在买入...";
		}else{
			btn = document.getElementById("sellbtn");
			btntext = btn.innerHTML;
			btn.innerHTML = "正在卖出...";
		}
		btn.disabled = true;		
		jQuery.post(url, param, function(data) {
			btn.disabled = false;
			btn.innerHTML = btntext;
			util.showerrortips(errorele, data.msg);
			if (data.resultCode == -2) {
				document.getElementById("isopen").value = "true";
			}else if(data.resultCode == 0) {
				util.showerrortips(errorele, "挂单成功");
				if (tradeType == 0) {
					document.getElementById("tradebuyamount").value="";
					$("#tradebuyTurnover").html("0");
				} else {
					var tradeAmount = document.getElementById("tradesellamount").value="";
					$("#tradesellTurnover").html("0");
				}
				window.setTimeout(function() {
					$("#"+errorele).html("");
				}, 5000);
			}
		}, "json");
	},
	onPortion2 : function(portion, tradeType) {
		portion = util.accDiv(portion, 100);
		if (tradeType == 0) {
			var tradecnymoney = Number(document.getElementById("toptradecnymoney").innerHTML);
			var portionMoney = util.accMul(tradecnymoney, portion);
			$("#tradebuyprice2").val(portionMoney) ;
			util.hideerrortips("buy-errortips2");
		} else {
			
			var tradecnymoney = Number(document.getElementById("toptrademtccoin").innerHTML);
			var portionMoney = util.accMul(tradecnymoney, portion);
			$("#tradesellprice2").val(portionMoney) ;
			util.hideerrortips("sell-errortips2");
		}
	}
};

$(function() {
	$("#tradebuyprice2").on("keyup", function() {
		trade2.NumVerify(0);
	}).on("keypress", function(event) {
		return util.VerifyKeypress(this, event, coinCount);
	}).on("click", function() {
		this.focus();
		//this.select();
	});
	$("#tradesellprice2").on("keyup", function() {
		trade2.NumVerify(1);
	}).on("keypress", function(event) {
		return util.VerifyKeypress(this, event, coinCount);
	}).on("click", function() {
		this.focus();
		//this.select();
	});

	$("#buybtn2").on("click", function() {
		trade2.submitTradeBtcForm(0, true);
	});
	$("#sellbtn2").on("click", function() {
		trade2.submitTradeBtcForm(1, true);
	});
	
	$("#buyslider2").on("change", function(e, val) {
		trade2.onPortion2(val, 0);
		$("#buyslidertext2").html(val + "%");
	});
	$("#sellslider2").on("change", function(e, val) {
		trade2.onPortion2(val, 1);
		$("#sellslidertext2").html(val + "%");
	});
	
	$("#tradebuyprice2").val('') ;
	$("#tradesellprice2").val('') ;
});