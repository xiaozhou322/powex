var withdraw = {
	btc : {
		submitWithdrawBtcAddrForm : function(nvcData, callbackName) {
			var coinName = document.getElementById("coinName").value;
			var withdrawAddr = util.trim(document.getElementById("withdrawBtcAddr").value);
			var withdrawRemark = util.trim(document.getElementById("withdrawBtcRemark").value);
			var withdrawBtcPass = util.trim(document.getElementById("withdrawBtcPass").value);
			var coincode = util.trim(document.getElementById("coinName").value);
			var withdrawBtcAddrTotpCode = 0;
			var withdrawBtcAddrPhoneCode = 0;
			var symbol = document.getElementById("symbol").value;
			if (withdrawAddr == "") {
				util.showerrortips("binderrortips", language["comm.error.tips.63"]);
				return;
			} else {
				util.hideerrortips("binderrortips");
			}
			var start = withdrawAddr.substring(0, 1);
			if (withdrawAddr.length == 34) {
				util.showerrortips("binderrortips", language["comm.error.tips.64"].format(coinName));
				return;
			}
			if (document.getElementById("withdrawBtcAddrTotpCode") != null) {
				withdrawBtcAddrTotpCode = util.trim(document.getElementById("withdrawBtcAddrTotpCode").value);
				if (!/^[0-9]{6}$/.test(withdrawBtcAddrTotpCode)) {
					util.showerrortips("binderrortips", language["comm.error.tips.65"]);
					document.getElementById("withdrawBtcAddrTotpCode").value = "";
					return;
				} else {
					util.hideerrortips("binderrortips");
				}
			}
			if (document.getElementById("withdrawBtcAddrPhoneCode") != null) {
				withdrawBtcAddrPhoneCode = util.trim(document.getElementById("withdrawBtcAddrPhoneCode").value);
				if (!/^[0-9]{6}$/.test(withdrawBtcAddrPhoneCode)) {
					util.showerrortips("binderrortips", language["comm.error.tips.66"]);
					document.getElementById("withdrawBtcAddrPhoneCode").value = "";
					return;
				} else {
					util.hideerrortips("binderrortips");
				}
			}
			var url = "/user/modifyWithdrawBtcAddr.html?random=" + Math.round(Math.random() * 100);
			var param = {
				withdrawAddr : withdrawAddr,
				totpCode : withdrawBtcAddrTotpCode,
				phoneCode : withdrawBtcAddrPhoneCode,
				symbol : symbol,
				withdrawBtcPass : withdrawBtcPass,
				withdrawRemark : withdrawRemark,
				nvcData : nvcData
			};
			$.post(url, param, function(result) {
				if (result != null) {
					//人机验证--无痕验证
					nvcCheckCallback(result);
					
					if (result.code <0) {
						util.showerrortips("binderrortips", result.msg);
					} else if (result.code == 0) {
						window.location.reload(true);
					} else {
						util.hideerrortips("withdrawerrortips");
					}
				}
			}, "json");
		},
		submitWithdrawBtcForm : function(nvcData, callbackName) {
			var coinName = document.getElementById("coinName").value;
			var withdrawAddr = util.trim(document.getElementById("withdrawAddr").value);
			var withdrawAmount = util.trim(document.getElementById("withdrawAmount").value);
			var tradePwd = util.trim(document.getElementById("tradePwd").value);
			var max_double = util.trim(document.getElementById("max_double").value);
			var min_double = util.trim(document.getElementById("min_double").value);
			var btcfee = document.getElementById("btcfee").value;
			var totpCode = 0;
			var phoneCode = 0;
			var symbol = document.getElementById("symbol").value;
			if (document.getElementById("btcbalance") != null && document.getElementById("btcbalance").value == 0) {
				util.showerrortips("withdrawerrortips", language["comm.error.tips.54"]);
				return;
			} else {
				util.hideerrortips("withdrawerrortips");
			}
			if (withdrawAddr == "") {
				util.showerrortips("withdrawerrortips", language["comm.error.tips.55"]);
				return;
			} else {
				util.hideerrortips("withdrawerrortips");
			}
			var reg = new RegExp("^[0-9]+\.{0,1}[0-9]{0,8}$");
			if (!reg.test(withdrawAmount)) {
				util.showerrortips("withdrawerrortips", language["comm.error.tips.56"]);
				return;
			} else {
				util.hideerrortips("withdrawerrortips");
			}
			if (parseFloat(withdrawAmount) < parseFloat(min_double)) {
				util.showerrortips("withdrawerrortips", language["withdraw.error.tips.1"].format(min_double,coinName));
				return;
			} else {
				util.hideerrortips("withdrawerrortips");
			}
			if (parseFloat(withdrawAmount) > parseFloat(max_double)) {
				util.showerrortips("withdrawerrortips", language["withdraw.error.tips.2"].format(max_double,coinName));
				return;
			} else {
				util.hideerrortips("withdrawerrortips");
			}
			if (tradePwd == "") {
				util.showerrortips("withdrawerrortips", language["comm.error.tips.58"]);
				return;
			} else {
				util.hideerrortips("withdrawerrortips");
			}
			if (document.getElementById("withdrawTotpCode") != null) {
				totpCode = util.trim(document.getElementById("withdrawTotpCode").value);
				if (!/^[0-9]{6}$/.test(totpCode)) {
					util.showerrortips("withdrawerrortips", language["comm.error.tips.59"]);
					return;
				} else {
					util.hideerrortips("withdrawerrortips");
				}
			}
			if (document.getElementById("withdrawPhoneCode") != null) {
				phoneCode = util.trim(document.getElementById("withdrawPhoneCode").value);
				if (!/^[0-9]{6}$/.test(phoneCode)) {
					util.showerrortips("withdrawerrortips", language["comm.error.tips.60"]);
					return;
				} else {
					util.hideerrortips("withdrawerrortips");
				}
			}
			if (document.getElementById("withdrawPhoneCode") == null && document.getElementById("withdrawTotpCode") == null) {
				util.showerrortips("withdrawerrortips", language["withdraw.error.tips.3"]);
				return;
			}
			var url = "/account/withdrawBtcSubmit.html?random=" + Math.round(Math.random() * 100);
			var param = {
				withdrawAddr : withdrawAddr,
				withdrawAmount : withdrawAmount,
				tradePwd : tradePwd,
				totpCode : totpCode,
				phoneCode : phoneCode,
				symbol : symbol,
				level:btcfee,
				nvcData : nvcData
			};
			$.post(url, param, function(result) {
				if (result != null) {
					//人机验证--无痕验证
					nvcCheckCallback(result);
					
					if (result.code < 0) {
						util.showerrortips("withdrawerrortips", result.msg);
					} else if (result.code == 0) {
						document.getElementById("withdrawBtcButton").disabled = "true";
						util.showerrortips("withdrawerrortips", result.msg);
					} else {
						util.hideerrortips("withdrawerrortips");
					}
				}
			});
		},
		calculatefeesRate : function() {
			var amount = $("#withdrawAmount").val();
			var feesRate = $("#feesRate").val();
			var feeMin = $("#withdrawFeeMini").val();
			if (amount == "") {
				amount = 0;
			}
			var feeamt = util.moneyformat(util.accMul(amount, feesRate), 2);
			if(parseFloat(feeamt)<parseFloat(feeMin)){
				feeamt = feeMin;
			}
			$("#free").html(feeamt);
			var actArr = util.moneyformat(parseFloat(amount) - parseFloat(feeamt), 2);
			if (actArr<=0){actArr=0;}
			$("#amount").html(actArr);
		},
		cancelWithdrawBtc: function(id) {
	        util.layerConfirm(language["comm.error.tips.67"],
	        function(result) {
	        	$('#confirmTips').modal('hide');
	            var url = "/account/cancelWithdrawBtc.html?random=" + Math.round(100 * Math.random()),
	            param = {
	    			id : id
	    		};
	            $.post(url, param,
	            function(id) {
	                null != id && (location.reload(true), layer.close(result))
	            })
	        })
	    },
		cancelTransferBtc: function(id) {
	        util.layerConfirm(language["comm.error.tips.67"],
	        function(result) {
	        	$('#confirmTips').modal('hide');
	            var url = "/account/cancelTransfer.html?random=" + Math.round(100 * Math.random()),
	            param = {
	    			id : id
	    		};
	            $.post(url, param,
	            function(id) {
	                null != id && (location.reload(true), layer.close(result))
	            })
	        })
	    }
	},
	cny : {
		submitWithdrawCnyAddress : function(type) {
			var payeeAddr = document.getElementById("payeeAddr").value;
			var openBankTypeAddr = $("#openBankTypeAddr").val();
			var withdrawAccount = util.trim(document.getElementById("withdrawAccountAddr").value);
			var address = util.trim(document.getElementById("address").value);
			var prov = util.trim(document.getElementById("prov").value);
			var city = util.trim(document.getElementById("city").value);
			var dist = util.trim(document.getElementById("dist").value);
			var totpCode = 0;
			var phoneCode = 0;
			if (payeeAddr == "" || payeeAddr == language["withdraw.error.tips.5"] || payeeAddr == language["withdraw.error.tips.6"]) {
				util.showerrortips("binderrortips", language["comm.error.tips.129"]);
				return;
			}
			if (openBankTypeAddr == -1) {
				util.showerrortips("binderrortips", language["comm.error.tips.70"]);
				return;
			}
			var reg = /^(\d{16}|\d{17}|\d{18}|\d{19})$/;
			if(!reg.test(withdrawAccount)){
				//银行卡号不合法
				util.showerrortips("binderrortips", language["comm.error.tips.134"]);
				return;
			}
			if (withdrawAccount == "" || withdrawAccount.length > 200 || withdrawAccount == language["comm.error.tips.62"]) {
				util.showerrortips("binderrortips", language["comm.error.tips.71"]);
				return;
			}
			var withdrawAccount2 = util.trim(document.getElementById("withdrawAccountAddr2").value);
			if (withdrawAccount != withdrawAccount2) {
				util.showerrortips("binderrortips", language["comm.error.tips.72"]);
				return;
			}
			if ((prov == "" || prov == language["withdraw.error.tips.7"]) || (city == "" || city == language["withdraw.error.tips.7"]) || address == "") {
				util.showerrortips("binderrortips", language["comm.error.tips.73"]);
				return;
			}
			if (address.length > 300) {
				util.showerrortips("binderrortips", language["comm.error.tips.73"]);
				return;
			}

			if (document.getElementById("addressTotpCode") != null) {
				totpCode = util.trim(document.getElementById("addressTotpCode").value);
				if (!/^[0-9]{6}$/.test(totpCode)) {
					util.showerrortips("binderrortips", language["comm.error.tips.65"]);
					document.getElementById("addressTotpCode").value = "";
					return;
				}
			}
			if (document.getElementById("addressPhoneCode") != null) {
				phoneCode = util.trim(document.getElementById("addressPhoneCode").value);
				if (!/^[0-9]{6}$/.test(phoneCode)) {
					util.showerrortips("binderrortips", language["comm.error.tips.66"]);
					document.getElementById("addressPhoneCode").value = "";
					return;
				}
			}
			util.hideerrortips("binderrortips");
			var url = "/user/updateOutAddress.html?random=" + Math.round(Math.random() * 100);
			jQuery.post(url, {
				account : withdrawAccount,
				openBankType : openBankTypeAddr,
				totpCode : totpCode,
				phoneCode : phoneCode,
				address : address,
				prov : prov,
				city : city,
				dist : dist,
				payeeAddr : payeeAddr
			}, function(result) {
				if (result != null) {
					if (result.code == 0) {
						window.location.reload(true);
					} else {
						util.showerrortips("binderrortips", result.msg);
					}
				}
			}, "json");
		},
		submitWithdrawCnyForm : function(ele) {
			var withdrawBlank = $("#withdrawBlank").val();
			var withdrawBalance = util.trim(document.getElementById('withdrawBalance').value);
			var tradePwd = util.trim(document.getElementById("tradePwd").value);
			var totpCode = 0;
			var phoneCode = 0;
			var min = document.getElementById("min_double").value;
			var max = document.getElementById("max_double").value;
			var reg = new RegExp("^[0-9]+\.{0,1}[0-9]{0,8}$");
			if (withdrawBlank == "" || withdrawBlank ==0 || withdrawBlank == null) {
				util.showerrortips("withdrawerrortips", language["comm.error.tips.71"]);
				return;
			}
			if (!reg.test(withdrawBalance)) {
				util.showerrortips("withdrawerrortips", language["comm.error.tips.74"]);
				return;
			}
			if (parseFloat(withdrawBalance) < parseFloat(min)) {
				util.showerrortips("withdrawerrortips", language["comm.error.tips.77"].format(min));
				return;
			}
			if (parseFloat(withdrawBalance) > parseFloat(max)) {
				util.showerrortips("withdrawerrortips", language["comm.error.tips.78"].format(max));
				return;
			}
			if (tradePwd == "" || tradePwd.length > 200) {
				util.showerrortips("withdrawerrortips", language["comm.error.tips.79"]);
				return;
			}
			if (document.getElementById("withdrawTotpCode") != null) {
				totpCode = util.trim(document.getElementById("withdrawTotpCode").value);
				if (!/^[0-9]{6}$/.test(totpCode)) {
					util.showerrortips("withdrawerrortips", language["comm.error.tips.80"]);
					return;
				}

			}
			if (document.getElementById("withdrawPhoneCode") != null) {
				phoneCode = util.trim(document.getElementById("withdrawPhoneCode").value);
				if (!/^[0-9]{6}$/.test(phoneCode)) {
					util.showerrortips("withdrawerrortips", language["comm.error.tips.81"]);
					return;
				}
			}
			if (document.getElementById("withdrawPhoneCode") == null) {
				util.showerrortips("withdrawerrortips", language["withdraw.error.tips.3"]);
				return;
			}
			ele.disabled = true;
			var url = "/account/withdrawCnySubmit.html?random=" + Math.round(Math.random() * 100);
			var param = {
				tradePwd : tradePwd,
				withdrawBalance : withdrawBalance,
				phoneCode : phoneCode,
				totpCode : totpCode,
				withdrawBlank : withdrawBlank
			};
			jQuery.post(url, param, function(result) {
				ele.disabled = false;
				if (result != null) {
					if (result.code == 0 || result.code == "0") {
						util.layerAlert("", language["withdraw.error.tips.4"], 1);
					} else {
						util.showerrortips("withdrawerrortips", result.msg);
					}
				}
			}, "json");
		},
		cancelWithdrawCny: function(outId) {
	        util.layerConfirm(language["comm.error.tips.67"],
	        function(result) {
	        	$('#confirmTips').modal('hide');
	            var url = "/account/cancelWithdrawcny.html?random=" + Math.round(100 * Math.random()),
	            param = {
	    			id : outId
	    		};
	            $.post(url, param,
	            function(id) {
	                null != id && (location.reload(true), layer.close(result))
	            })
	        })
	    },
		calculatefeesRate : function() {
			var amount = $("#withdrawBalance").val();
			var feesRate = $("#feesRate").val();
			if (amount == "") {
				amount = 0;
			}
			var feeamt = util.moneyformat(util.accMul(amount, feesRate), 2);
			if(parseFloat(feeamt)<2){
				feeamt = "2.00";
			}
			$("#free").html(feeamt);
			var actArr = util.moneyformat(parseFloat(amount) - parseFloat(feeamt), 2);
			if (actArr<=0){actArr=0;}
			$("#amount").html(actArr);
		}
	},usdt : {
		submitWithdrawUsdtForm : function(ele, nvcData, callbackName) {
			var withdrawBlank = $("#withdrawBlank").val();
			var withdrawBalance = util.trim(document.getElementById('withdrawBalanceUsdt').value);
			var tradePwd = util.trim(document.getElementById("tradePwd").value);
			var totpCode = 0;
			var phoneCode = 0;
			var min = document.getElementById("min_double").value;
			var max = document.getElementById("max_double").value;
			var reg = new RegExp("^[0-9]+\.{0,1}[0-9]{0,8}$");
			if (withdrawBlank == "" || withdrawBlank ==0 || withdrawBlank == null) {
				util.showerrortips("withdrawerrortips", language["comm.error.tips.71"]);
				return;
			}
			if (!reg.test(withdrawBalance)) {
				util.showerrortips("withdrawerrortips", language["comm.error.tips.74"]);
				return;
			}
			if (parseFloat(withdrawBalance) < parseFloat(min)) {
				util.showerrortips("withdrawerrortips", language["comm.error.tips.77"].format(min));
				return;
			}
			if (parseFloat(withdrawBalance) > parseFloat(max)) {
				util.showerrortips("withdrawerrortips", language["comm.error.tips.78"].format(max));
				return;
			}
			if (tradePwd == "" || tradePwd.length > 200) {
				util.showerrortips("withdrawerrortips", language["comm.error.tips.79"]);
				return;
			}
			if (document.getElementById("withdrawTotpCode") != null) {
				totpCode = util.trim(document.getElementById("withdrawTotpCode").value);
				if (!/^[0-9]{6}$/.test(totpCode)) {
					util.showerrortips("withdrawerrortips", language["comm.error.tips.80"]);
					return;
				}

			}
			if (document.getElementById("withdrawPhoneCode") != null) {
				phoneCode = util.trim(document.getElementById("withdrawPhoneCode").value);
				if (!/^[0-9]{6}$/.test(phoneCode)) {
					util.showerrortips("withdrawerrortips", language["comm.error.tips.81"]);
					return;
				}
			}
			if (document.getElementById("withdrawPhoneCode") == null) {
				util.showerrortips("withdrawerrortips", language["withdraw.error.tips.3"]);
				return;
			}
			ele.disabled = true;
			var url = "/exchange/withdrawUsdtSubmit.html?random=" + Math.round(Math.random() * 100);
			var param = {
				tradePwd : tradePwd,
				withdrawBalance : withdrawBalance,
				phoneCode : phoneCode,
				totpCode : totpCode,
				withdrawBlank : withdrawBlank,
				nvcData : nvcData
			};
			jQuery.post(url, param, function(result) {
				ele.disabled = false;
				if (result != null) {
					//人机验证--无痕验证
					nvcCheckCallback(result);
					
					if (result.code == 0 || result.code == "0") {
						$("#coincode").val(result.coincode);
						$("#subformdiv").hide();
						$("#showcoincode").show();
					} else {
						util.showerrortips("withdrawerrortips", result.msg);
					}
				}
			}, "json");
		},
		cancelWithdrawUsdt: function(outId) {
	        util.layerConfirm(language["comm.error.tips.67"],
	        function(result) {
	        	$('#confirmTips').modal('hide');
	            var url = "/exchange/cancelWithdrawusdt.html?random=" + Math.round(100 * Math.random()),
	            param = {
	    			id : outId
	    		};
	            $.post(url, param,
	            function(id) {
	                null != id && (location.reload(true), layer.close(result))
	            })
	        })
	    },
		calculatefeesRate : function() {
			var amount = $("#withdrawBalanceUsdt").val();
			var feesRate = $("#feesRate").val();
			if (amount == "") {
				amount = 0;
			}
			var feeamt = util.moneyformat(util.accMul(amount, feesRate), 2);
			if(parseFloat(feeamt)<0.5){
				feeamt = "0.5";
			}
			$("#free").html(feeamt);
			var actArr = util.moneyformat(parseFloat(amount) - parseFloat(feeamt), 2);
			if (actArr<=0){actArr=0;}
			$("#amount").html(actArr);
		},
		submitexchangeUsdtForm : function(ele, nvcData, callbackName) {
			
			var exchangecode = util.trim(document.getElementById('exchangecode').value);
			
			ele.disabled = true;
			var url = "/exchange/exchangeUsdtSubmit.html?random=" + Math.round(Math.random() * 100);
			var param = {
					exchangecode : exchangecode,
					nvcData : nvcData
			};
			jQuery.post(url, param, function(result) {
				ele.disabled = false;
				if (result != null) {
					//人机验证--无痕验证
					nvcCheckCallback(result);
					
					if (result.code == 0 || result.code == "0") {
//						$("#coincode").html(result.coincode);
//						$("#subformdiv").hide();
//						$("#showcoincode").show();
						// util.layerAlert("", language["withdraw.error.tips.4"], 1);
						window.location.href = "/exchange/recordUsdt.html?operId=" + result.operId;
					} else {
						util.showerrortips("withdrawerrortips", result.msg);
					}
				}
			}, "json");
		}
	}
};
$(function() {
	$(".btn-sendmsg").on("click", function() {
		msg.sendMsgCode($(this).data().msgtype, $(this).data().tipsid, this.id);
	});
	$(".sendCode").on("click", function() {
		msg.sendMsgCode($(this).data().msgtype, $(this).data().tipsid, this.id);
	});
	
	$("#withdrawBtcAddrBtn").on("click", function() {
		var nvcData = getNVCVal();
		var callbackName = ('jsonp_' + Math.random()).replace('.', '');
		withdraw.btc.submitWithdrawBtcAddrForm(nvcData, callbackName);
	});
	$("#withdrawBtcButton").on("click", function() {
		var nvcData = getNVCVal();
		var callbackName = ('jsonp_' + Math.random()).replace('.', '');
		withdraw.btc.submitWithdrawBtcForm(nvcData, callbackName);
	});
	$("#withdrawAmount").on("keypress", function(event) {
		return util.VerifyKeypress(this, event, 4);
	}).on("keyup", function() {
		withdraw.btc.calculatefeesRate();
	});
	$(".cancelWithdrawBtc").on("click", function(event) {
		withdraw.btc.cancelWithdrawBtc($(this).data().fid);
	});
	$(".cancelTransferBtc").on("click", function(event) {
		withdraw.btc.cancelTransferBtc($(this).data().fid);
	});
	
	$(".recordtitle").on("click", function() {
		util.recordTab($(this));
	});
	$("#withdrawCnyAddrBtn").on("click", function() {
		withdraw.cny.submitWithdrawCnyAddress();
	});
	$("#withdrawBalance").on("keypress", function(event) {
		return util.VerifyKeypress(this, event, 2);
	}).on("keyup", function() {
		withdraw.cny.calculatefeesRate();
	});
	$("#withdrawCnyButton").on("click", function(event) {
		withdraw.cny.submitWithdrawCnyForm(this);
	});
	$(".cancelWithdrawcny").on("click", function(event) {
		withdraw.cny.cancelWithdrawCny($(this).data().fid);
	});
	
	$("#withdrawUsdtAddrBtn").on("click", function() {
		withdraw.cny.submitWithdrawCnyAddress();
	});
	$("#withdrawBalanceUsdt").on("keypress", function(event) {
		return util.VerifyKeypress(this, event, 2);
	}).on("keyup", function() {
		withdraw.usdt.calculatefeesRate();
	});
	$("#withdrawUsdtButton").on("click", function(event) {
		var nvcData = getNVCVal();
		var callbackName = ('jsonp_' + Math.random()).replace('.', '');
		withdraw.usdt.submitWithdrawUsdtForm(this, nvcData, callbackName);
	});
	$(".cancelWithdrawusdt").on("click", function(event) {
		withdraw.usdt.cancelWithdrawUsdt($(this).data().fid);
	});
	
	$("#withdrawAccountAddr2").bind("copy cut paste", function(e) {
		return false;
	});
	$("#exchangeUsdtButton").on("click", function(event) {
		var nvcData = getNVCVal();
		var callbackName = ('jsonp_' + Math.random()).replace('.', '');
		withdraw.usdt.submitexchangeUsdtForm(this, nvcData, callbackName);
	});
});


function nvcCheckCallback(json){
	//人机验证--无痕验证
    if(json.code == 400) {
        //唤醒滑动验证
        getNC().then(function(){
            _nvc_nc.upLang('cn', {
            	_startTEXT: language["aliyun.nc.startTEXT"],
                _yesTEXT: language["aliyun.nc.yesTEXT"],
                _error300: language["aliyun.nc.error300"],
                _errorNetwork: language["aliyun.nc.errorNetwork"],
            })
            _nvc_nc.reset()
        })
    } else if (json.code == 600) {
        //唤醒刮刮卡
        getSC().then(function(){})
    } else if (json.code == 800 || json.code == 900) {
        //直接拦截
    	util.showerrortips(errorEle, json.msg);
    }
}