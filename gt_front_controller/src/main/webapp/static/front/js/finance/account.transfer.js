var withdraw = {
	transfer : {
		submitTransferForm : function() {
			var coinName = document.getElementById("coinName").value;
			var skruid = util.trim(document.getElementById("skruid").value);
			var skruname = util.trim(document.getElementById("skruname").value);
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
			if (skruid == "") {
				util.showerrortips("withdrawerrortips", language["comm.error.tips.55.1"]);
				return;
			} else {
				util.hideerrortips("withdrawerrortips");
			}
			if (skruname == "") {
				util.showerrortips("withdrawerrortips", language["comm.error.tips.55.1"]);
				return;
			} else {
				util.hideerrortips("withdrawerrortips");
			}
			
			if (parseFloat(withdrawAmount) < parseFloat(min_double)) {
				util.showerrortips("withdrawerrortips", language["withdraw.error.tips.1.1"].format(min_double,coinName));
				return;
			} else {
				util.hideerrortips("withdrawerrortips");
			}
			if (parseFloat(withdrawAmount) > parseFloat(max_double)) {
				util.showerrortips("withdrawerrortips", language["withdraw.error.tips.2.1"].format(max_double,coinName));
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
			var url = "/account/transferSubmit.html?random=" + Math.round(Math.random() * 100);
			var param = {
				skruid : skruid,
				skruname : skruname,
				withdrawAmount : withdrawAmount,
				tradePwd : tradePwd,
				totpCode : totpCode,
				phoneCode : phoneCode,
				symbol : symbol,
				level:btcfee
			};
			$.post(url, param, function(result) {
				if (result != null) {
					if (result.code < 0) {
						util.showerrortips("withdrawerrortips", result.msg);
					} else if (result.code == 0) {
						document.getElementById("withdrawBtcButton").disabled = "true";
						$('#alertTips').modal('hide');
						util.layerAlert("", language["withdraw.error.tips.4"], 1);
					} else {
						util.hideerrortips("withdrawerrortips");
					}
				}
			});
		},
		calculatefeesRate : function() {
			var amount = $("#withdrawAmount").val();
			var feesRate = $("#feesRate").val();
			if (amount == "") {
				amount = 0;
			}
			var feeamt = util.moneyformat(util.accMul(amount, feesRate), 6);
			$("#free").html(feeamt);
			$("#amount").html(util.moneyformat(parseFloat(amount) - parseFloat(feeamt), 6));
		},
		cancelTransfer: function(id) {
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
	}
};
$(function() {
	$(".btn-sendmsg").on("click", function() {
		msg.sendMsgCode($(this).data().msgtype, $(this).data().tipsid, this.id);
	});
	$("#withdrawBtcButton").on("click", function() {
		withdraw.transfer.submitTransferForm();
	});
	$("#withdrawAmount").on("keypress", function(event) {
		return util.VerifyKeypress(this, event, 4);
	}).on("keyup", function() {
		withdraw.transfer.calculatefeesRate();
	});
	$(".cancelWithdrawBtc").on("click", function(event) {
		withdraw.transfer.cancelTransfer($(this).data().fid);
	});
	$(".recordtitle").on("click", function() {
		util.recordTab($(this));
	});
});