var assets = {
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
			util.showMsg(language["comm.error.tips.129"]);
			return;
		}
		if (openBankTypeAddr == -1) {
			util.showMsg(language["comm.error.tips.70"]);
			return;
		}
		var reg = /^(\d{16}|\d{17}|\d{18}|\d{19})$/;
		if(!reg.test(withdrawAccount)){
			//银行卡号不合法
			util.showMsg(language["comm.error.tips.134"]);
			return;
		}
		if (withdrawAccount == "" || withdrawAccount.length > 200 || withdrawAccount == language["comm.error.tips.62"]) {
			util.showMsg(language["comm.error.tips.71"]);
			return;
		}
		var withdrawAccount2 = util.trim(document.getElementById("withdrawAccountAddr2").value);
		if (withdrawAccount != withdrawAccount2) {
			util.showMsg(language["comm.error.tips.72"]);
			return;
		}
		if ((prov == "" || prov == language["withdraw.error.tips.7"]) || (city == "" || city == language["withdraw.error.tips.7"]) || address == "") {
			util.showMsg(language["comm.error.tips.73"]);
			return;
		}
		if (address.length > 300) {
			util.showMsg(language["comm.error.tips.73"]);
			return;
		}

		if (document.getElementById("addressTotpCode") != null) {
			totpCode = util.trim(document.getElementById("addressTotpCode").value);
			if (!/^[0-9]{6}$/.test(totpCode)) {
				util.showMsg(language["comm.error.tips.65"]);
				document.getElementById("addressTotpCode").value = "";
				return;
			}
		}
		if (document.getElementById("addressPhoneCode") != null) {
			phoneCode = util.trim(document.getElementById("addressPhoneCode").value);
			if (!/^[0-9]{6}$/.test(phoneCode)) {
				util.showMsg(language["comm.error.tips.66"]);
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
					util.showMsg(result.msg);
				}
			}
		}, "json");
	},
	submitAlipayAddress : function() {
		var payeeAddr = document.getElementById("payeeAddr").value;
		var withdrawAccount = util.trim(document.getElementById("withdrawAccountAddr").value);
		var totpCode = 0;
		var phoneCode = 0;
		if (payeeAddr == "") {
			util.showMsg(language["withdraw.error.tips.8"]);
			return;
		}

		if (withdrawAccount == "" || withdrawAccount.length > 200 || withdrawAccount == language["comm.error.tips.62"]) {
			util.showMsg(language["withdraw.error.tips.9"]);
			return;
		}

		if (document.getElementById("addressTotpCode") != null) {
			totpCode = util.trim(document.getElementById("addressTotpCode").value);
			if (!/^[0-9]{6}$/.test(totpCode)) {
				util.showMsg(language["comm.error.tips.65"]);
				document.getElementById("addressTotpCode").value = "";
				return;
			}
		}
		if (document.getElementById("addressPhoneCode") != null) {
			phoneCode = util.trim(document.getElementById("addressPhoneCode").value);
			if (!/^[0-9]{6}$/.test(phoneCode)) {
				util.showMsg(language["comm.error.tips.66"]);
				document.getElementById("addressPhoneCode").value = "";
				return;
			}
		}
		util.hideerrortips("binderrortips");
		var url = "/user/updateOutAlipayAddress.html?random=" + Math.round(Math.random() * 100);
		jQuery.post(url, {
			account : withdrawAccount,
			totpCode : totpCode,
			phoneCode : phoneCode,
			payeeAddr : payeeAddr
		}, function(result) {
			if (result != null) {
				if (result.code == 0) {
					window.location.reload(true);
				} else {
					util.showMsg(result.msg);
				}
			}
		}, "json");
	},
	detelBankAddress : function(fid) {
		util.layerConfirm(language["withdraw.error.tips.10"],
		        function(result) {
			        $('#alertTips').modal('hide');
		            var url = "/user/deleteBankAddress.html?random=" + Math.round(100 * Math.random()),
		            param = {
		            	fid : fid
		    		};
		            $.post(url, param,
		            function(fid) {
		                null != fid && (location.reload(true), layer.close(result))
		            })
		        });
		
	},
	submitWithdrawBtcAddrForm : function() {
		var coinName = document.getElementById("coinName").value;
		var withdrawAddr = util.trim(document.getElementById("withdrawBtcAddr").value);
		var withdrawRemark = util.trim(document.getElementById("withdrawBtcRemark").value);
		var withdrawBtcPass = util.trim(document.getElementById("withdrawBtcPass").value);
		var withdrawBtcAddrTotpCode = 0;
		var withdrawBtcAddrPhoneCode = 0;
		var symbol = document.getElementById("symbol").value;
		if (withdrawAddr == "") {
			util.showMsg(language["comm.error.tips.63"]);
			return;
		} else {
			util.hideerrortips("binderrortips");
		}
		var start = withdrawAddr.substring(0, 1);
		if (withdrawAddr.length != 34 && withdrawAddr.length != 42) {
			util.showMsg(language["comm.error.tips.64"].format(coinName));
			return;
		}
		if (document.getElementById("withdrawBtcAddrTotpCode") != null) {
			withdrawBtcAddrTotpCode = util.trim(document.getElementById("withdrawBtcAddrTotpCode").value);
			if (!/^[0-9]{6}$/.test(withdrawBtcAddrTotpCode)) {
				util.showMsg(language["comm.error.tips.65"]);
				document.getElementById("withdrawBtcAddrTotpCode").value = "";
				return;
			} else {
				util.hideerrortips("binderrortips");
			}
		}
		if (document.getElementById("withdrawBtcAddrPhoneCode") != null) {
			withdrawBtcAddrPhoneCode = util.trim(document.getElementById("withdrawBtcAddrPhoneCode").value);
			if (!/^[0-9]{6}$/.test(withdrawBtcAddrPhoneCode)) {
				util.showMsg(language["comm.error.tips.66"]);
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
			withdrawRemark : withdrawRemark
		};
		$.post(url, param, function(result) {
			if (result != null) {
				if (result.code == -1) {
					util.layerAlert("", language["comm.error.tips.34"]+","+language["withdraw.error.tips.11"], 2);
					return;
				} else if (result.code == -4) {
					util.showMsg(result.msg);
				} else if (result.code == -2) {
					util.showMsg(result.msg);
					document.getElementById("withdrawBtcAddrTotpCode").value = "";
				} else if (result.code == -3) {
					util.showMsg(result.msg);
					document.getElementById("withdrawBtcAddrPhoneCode").value = "";
				} else if (result.code == 0) {
					window.location.reload(true);
				}
			}
		}, "json");
	},
	detelCoinAddress : function(fid) {
		util.layerConfirm(language["withdraw.error.tips.10"],
		        function(result) {
			        $('#alertTips').modal('hide');
		            var url = "/user/detelCoinAddress.html?random=" + Math.round(100 * Math.random()),
		            param = {
		            	fid : fid
		    		};
		            $.post(url, param,
		            function(fid) {
		                null != fid && (location.reload(true), layer.close(result))
		            })
		        });
	},
	showcode:function(){
		var code=$(".addresscode");
		$.each(code,function(key,value){
			var fid=$(value).data().fid;
			var text=$(value).data().text;
			if (navigator.userAgent.indexOf("MSIE") > 0) {
				$('#qrcode'+fid).qrcode({
					text : toUtf8(text),
					width : "200",
					height : "200",
					render : "table",
					correctLevel:0
				});
			} else {
				$('#qrcode'+fid).qrcode({
					text : toUtf8(text),
					width : "200",
					height : "200",
					correctLevel:0
				});
			}
			$(value).parent().css("z-index",1000-Number(fid));
		});		
	}
};
$(function() {
	$(".btn-sendmsg").on("click", function() {
		msg.sendMsgCode($(this).data().msgtype, $(this).data().tipsid, this.id);
	});
	$("#withdrawCnyAddrBtn").on("click", function() {
		assets.submitWithdrawCnyAddress();
	});
	$("#addAlipayAddrBtn").on("click", function() {
		assets.submitAlipayAddress();
	});
	$(".bank-item-del").on("click", function() {
		assets.detelBankAddress($(this).data().fid);
	});
	$("#withdrawBtcAddrBtn").on("click", function() {
		assets.submitWithdrawBtcAddrForm();
	});
	$(".coin-item-del").on("click", function() {
		assets.detelCoinAddress($(this).data().fid);
	});
	assets.showcode();
	$("#withdrawAccountAddr2").bind("copy cut paste",function(e){
        return false;
     });
});