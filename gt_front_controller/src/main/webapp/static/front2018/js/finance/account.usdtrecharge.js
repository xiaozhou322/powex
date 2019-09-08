var recharge = {
	submitFinForm : function(that) {
		var sbank = document.getElementById("sbank").value;
		var random = document.getElementById("random").value;
		var minMoney = document.getElementById("minRecharge").value;
		var maxMoney = document.getElementById("maxRecharge").value;
		var money = 0;
		if (sbank == "") {
			util.showerrortips("", language["comm.error.tips.83"]);
			return;
		}
		money = document.getElementById("diyMoney").value;
		if (money.toString().indexOf(".") != -1) {
			money = money.toString().split(".")[0];
			money = money + (random.substring(1));
		} else {
			money = money + (random.substring(1));
		}
		if (Number(money) < Number(minMoney) || isNaN(money)) {
			util.showerrortips("", language["comm.error.tips.84"].format(minMoney));
			return;
		}
		if (Number(money) > Number(maxMoney)) {
			util.showerrortips("", language["comm.error.tips.84x"].format(maxMoney));
			return;
		}
		
		var url = "/exchange/usdtManual.html?random=" + Math.round(Math.random() * 100);
		var param = {
			money : money,
			sbank : sbank
		};
		that.disabled = true;
		$.post(url, param, function(result) {
			that.disabled = false;
			if (result != null) {
				if (result.code < 0) {
					util.showerrortips("", result.msg);
				} else {
					document.getElementById("fownerName").innerHTML = result.fownerName;
					document.getElementById("fbankNumber").innerHTML = result.fbankNumber;
					document.getElementById("fbankAddress").innerHTML = result.fbankAddress;
					document.getElementById("bankMoney").innerHTML = result.money;
					document.getElementById("bankInfo").innerHTML = result.remark;
					document.getElementById("desc").value = result.tradeId;
					document.getElementById("bankInfotips").innerHTML = result.remark;
					document.getElementById("recharge1").style.display = "none";
					document.getElementById("recharge2").style.display = "block";
					recharge.refTenbody();
					
					$(".recharge-process li").removeClass("active");
					$("#rechargeprocess2").addClass("active");
				}
			}
		});
	},
	submitPaymentInformation : function() {		
		var desc = document.getElementById("desc").value;
		var url = "/exchange/rechargeUsdtSubmit.html?random=" + Math.round(Math.random() * 100);
		var param = {
			bank : '无',
			account : '无',
			payee : '无',
			phone : '无',
			desc : desc

		};
		$.post(url, param, function(result) {
			if (result != null) {
				if (result.code < 0) {
					util.showerrortips("", result.msg);
				} else if (result.code == 0) {
					var url='/exchange/recordUsdt.html?type=0&operid='+desc;
					window.location.href=url;
					/*document.getElementById("recharge2").style.display = "none";
					document.getElementById("recharge3").style.display = "block";
					$(".recharge-process li").removeClass("active");
					$("#rechargeprocess3").addClass("active");
					recharge.refTenbody();*/
				}
			}
		});
	},
	
	
	submitTransferAccounts : function() {

		var bank = $("#fromBank").val();
		var account = document.getElementById("fromAccount").value;
		var payee = document.getElementById("fromPayee").value;
		var phone = document.getElementById("fromPhone").value;
		var desc = document.getElementById("desc").value;
		if (bank == "" || bank == "-1" || account == "" || payee == "" || phone == "") {
			util.showerrortips("", language["comm.error.tips.95"]);
			return;
		}
		var reg = /^(\d{16}|\d{17}|\d{18}|\d{19})$/;
		if(!reg.test(account)){
			//银行卡号不合法
			util.showerrortips("",language["comm.error.tips.134"]);
			return;
		}
		var url = "/exchange/rechargeUsdtSubmit.html?random=" + Math.round(Math.random() * 100);
		var param = {
			bank : bank,
			account : account,
			payee : payee,
			phone : phone,
			desc : desc

		};
		$.post(url, param, function(result) {
			if (result != null) {
				if (result.code < 0) {
					util.showerrortips("", result.msg);
				} else if (result.code == 0) {
					document.getElementById("rechage3").style.display = "none";
					document.getElementById("rechage4").style.display = "block";
					$(".recharge-process li").removeClass("active");
					$("#rechargeprocess4").addClass("active");
					recharge.refTenbody();
				}
			}
		});
	},
	refTenbody : function() {
		var currentPage = $('#currentPage').val();
		var type = $('#type').val();
		var url = "/exchange/refTenUsdtbody.html?currentPage="+currentPage+"&type="+type+"&random=" + Math.round(Math.random() * 100);
		$("#recordbody0").load(url, null, function(data) {
			$(".completioninfo").on("click", function() {
				var fid = $(this).data().fid;
				recharge.updateCnyRecharge(fid);
			}),
//			$(".recordtitle").on("click", function() {
//				util.recordTab($(this));
//			}),
			$(".rechargecancel").on("click", function() {
				var fid = $(this).data().fid;
				recharge.cancelRechargeCNY(fid);
			}),
			$(".rechargesub").on("click", function() {
				var fid = $(this).data().fid;
				recharge.subRechargeCNY(fid);
			})
		})
	},
    updateCnyRecharge: function(id) {
        $("#desc").val(id),
        $("#rechage1").hide(),
        $("#rechage2").hide(),
        $("#rechage3").show(),
        $("#rechage4").hide(),
        $("html,body").stop(true),
        $("html,body").animate({
            scrollTop: $(".rightarea").offset().top
        },
        10),
        $(".recharge-process li").removeClass("active"),
        $("#rechargeprocess3").addClass("active")
    },
	cancelRechargeCNY: function(id) {
        util.layerConfirm(language["recharge.error.tips.1"],
        function(result) {
            var url = "/exchange/cancelRechargeUsdtSubmit.html?random=" + Math.round(100 * Math.random()),
            param = {
    			id : id
    		};
            $.post(url, param,
            function(id) {
                null != id && (recharge.refTenbody(), layer.close(result))
            })
        })
    },
    cancelRechargeUSDT: function(id) {
        util.layerConfirm(language["recharge.error.tips.1"],
        function(result) {
            var url = "/exchange/cancelRechargeUsdtSubmit.html?random=" + Math.round(100 * Math.random()),
            param = {
    			id : id
    		};
            $.post(url, param,
            function(id) {
            	var url='/exchange/recordUsdt.html?type=0&operid='+id;
				window.location.href=url;
            })
        })
    },
    subRechargeCNY: function(id) {
        util.layerConfirm(language["recharge.error.tips.2"],
        function(result) {
            var url = "/exchange/subRechargeUsdtSubmit.html?random=" + Math.round(100 * Math.random()),
            param = {
    			id : id
    		};
            $.post(url, param,
            function(id) {
                null != id && (recharge.refTenbody(), layer.close(result))
            })
        })
    },
	commission : function() {
		var amount = $("#diyMoney").val();
		var fee = 0;
		var fee = util.accMul(amount, fee);
		$("#ffee").html(fee);
		$("#realamount").html(amount - fee);
	},
	bankitemcheck : function(ele, value) {
		$(".bank-item-checked", ".bank-item").hide(0, function() {
			ele.find(".bank-item-checked").show();
		});
		$("#bankid").val(value);
	},
	fcapitaloperationStatus:function(){
		$.post('/exchange/fcapitaloperationStatus.html',{id:$('#refreshid').val()},function(data){
			if(data.code == 0 ){
				window.location.reload() ;
			}else{
				setTimeout(recharge.fcapitaloperationStatus, 1000) ;
			}
			
		},'json') ;
	},
	viewOrderStatus : function() {
		
		var desc = document.getElementById("desc").value;
		var url = "/exchange/recordUsdt.html?operId=" + desc;
		window.location.href = url;
	}
};
$(function() {
	$(".btn-imgcode").on("click", function() {
		this.src = "/servlet/ValidateImageServlet?r=" + Math.round(Math.random() * 100);
	});
	$(".recordtitle").on("click", function() {
		util.recordTab($(this));
	});
	$("#rechargebtn").on("click", function() {
		recharge.submitFinForm(this);
	});
	$("#rechargenextbtn").on("click", function() {
		recharge.submitPaymentInformation();
	});
	$("#rechargesuccessbtn").on("click", function() {
		recharge.submitTransferAccounts();
	});
	$(".completioninfo").on("click", function() {
		var fid = $(this).data().fid;
		recharge.updateFinTransactionReceive(fid);
	});
	
	$(".cancelRechargeUSDT").on("click", function() {
		var fid = $(this).data().fid;
		recharge.cancelRechargeUSDT(fid);
	});
	$(".rechargecancel").on("click", function() {
		var fid = $(this).data().fid;
		recharge.cancelRechargeCNY(fid);
	});
	$(".rechargesub").on("click", function() {
		var fid = $(this).data().fid;
		recharge.subRechargeCNY(fid);
	});
	$("#diyMoney").on("keypress", function(event) {
		return util.VerifyKeypress(this, event, 0);
	});
	$("#diyMoney", ".online").on("keyup", function() {
		recharge.commission();
	});
	$(".bank-item").on("click", function() {
		var that = $(this);
		recharge.bankitemcheck(that, that.data().fid);
	});
	$("#rechargeDialog-Btn").on("click", function() {
		window.location.reload(true) ;
	});
	$("#viewstate").on("click", function() {
		recharge.viewOrderStatus();
	});
});