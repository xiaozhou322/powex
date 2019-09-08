var security = {
	loadgoogleAuth : function() {
		var url = "/user/googleAuth.html?random=" + Math.round(Math.random() * 100);
		var param = null;
		$.post(url, param, function(data) {
			if (data.code == 0) {
				if (navigator.userAgent.indexOf("MSIE") > 0) {
					$('#qrcode').html("").qrcode({
						text : toUtf8(data.qecode),
						width : "140",
						height : "140",
						render : "table",
						correctLevel:0
					});
				} else {
					$('#qrcode').html("").qrcode({
						text : toUtf8(data.qecode),
						width : "140",
						height : "140",
						correctLevel:0
					});
				}
				document.getElementById("bindgoogle-key").innerHTML = data.totpKey;
				document.getElementById("bindgoogle-key-hide").value = data.totpKey;
			}
		}, "json");
	},
	submitValidateEmailForm : function(email, flag) {
		var desc = '';
		if (email.indexOf(" ") > -1) {
			desc = language["comm.error.tips.11"];
		}
		if (email == '') {
			desc = language["comm.error.tips.7"];
		}
		if (!util.checkEmail(email)) {
			desc = language["comm.error.tips.16"];
		}
		if (desc != "") {
			util.showMsg(desc);
			return;
		} else {
			util.hideerrortips('bindemail-errortips');
		}
		var url = "/validate/postValidateMail.html?random=" + Math.round(Math.random() * 100);
		$.post(url, {
			email : email
		}, function(data) {
			if (data.code == 0) {
				console.log(flag);
				if (flag) {
					util.showMsg(data.msg, {
						okbtn : function() {
							$('#alertTips').modal('hide');
							location.reload(true);
						}
					});
				} else {
					util.showMsg(data.msg);
					window.setTimeout(function() {
						window.location.href = '/user/security.html';
					}, 1000);
				}
			} else {
				if (flag) {
					util.showMsg(data.msg);
				} else {
					util.showMsg(data.msg);
				}
			}
		}, "json");

		return;
	},
	areaCodeChange : function(ele, setEle) {
		var code = $(ele).val();
		$("#" + setEle).html("+" + code);
	},
	submitValidatePhoneForm : function(isUpdate) {
		var phone = document.getElementById("unbindphone-newphone").value;
		var oldcode = document.getElementById("unbindphone-msgcode").value;
		var newcode = document.getElementById("unbindphone-newmsgcode").value;
		var areaCode = document.getElementById("unbindphone-newphone-areacode").innerHTML;
		var imgcode = document.getElementById("unbindphone-imgcode").value;
		var totpCode = 0;
		var desc = '';
		if (phone.indexOf(" ") > -1) {
			desc = language["comm.error.tips.8"];
			util.showMsg(desc);
			return false;
		} else {
			if (phone == '') {
				desc = language["comm.error.tips.6"];
				util.showMsg(desc);
				return false;
			}
		}
		if (oldcode.indexOf(" ") > -1 || oldcode.length != 6 || !/^[0-9]{6}$/.test(oldcode)) {
			desc = language["comm.error.tips.124"];
			util.showMsg(desc);
			return false;
		}
		if (newcode.indexOf(" ") > -1 || newcode.length != 6 || !/^[0-9]{6}$/.test(newcode)) {
			desc = language["comm.error.tips.124"];
			util.showMsg(desc);
			return false;
		}
		if (document.getElementById("unbindphone-googlecode") != null) {
			totpCode = document.getElementById("unbindphone-googlecode").value;
			if (totpCode.indexOf(" ") > -1 || totpCode.length != 6 || !/^[0-9]{6}$/.test(totpCode)) {
				desc = language["comm.error.tips.98"];
				util.showMsg(desc);
				return false;
			}
		}
		if (!util.checkMobile(phone)) {
			util.showMsg(language["comm.error.tips.10"]);
			return;
		}
		util.hideerrortips('unbindphone-errortips');
		var url = "/user/validatePhone.html?random=" + Math.round(Math.random() * 100);
		var param = {
			isUpdate:isUpdate,
			phone : phone,
			oldcode : oldcode,
			newcode : newcode,
			totpCode : totpCode,
			areaCode : areaCode,
			imgcode : imgcode
		};
		jQuery.post(url, param, function(result) {
			util.showMsg(result.msg);
			if (result.code == 0) {
				window.setTimeout(function() {
					window.location.href = '/user/security.html';
				}, 1000);
			}
		}, "json");
	},
	submitBindPhone : function(isUpdate) {
		var phone = document.getElementById("bindphone-newphone").value;
		var newcode = document.getElementById("bindphone-msgcode").value;
		var areaCode = document.getElementById("bindphone-newphone-areacode").innerHTML;
		var imgcode = document.getElementById("bindphone-imgcode").value;
		var totpCode = 0;
		var oldcode = 0;
		var desc = '';
		if (phone.indexOf(" ") > -1) {
			desc = language["comm.error.tips.8"];
			util.showMsg(desc);
			return false;
		} else {
			if (phone == '') {
				desc = language["comm.error.tips.6"];
				util.showMsg(desc);
				return false;
			}
		}
		if (newcode.indexOf(" ") > -1 || newcode.length != 6 || !/^[0-9]{6}$/.test(newcode)) {
			desc = language["comm.error.tips.124"];
			util.showMsg(desc);
			return false;
		}
		if (document.getElementById("bindphone-googlecode") != null) {
			totpCode = document.getElementById("bindphone-googlecode").value;
			if (totpCode.indexOf(" ") > -1 || totpCode.length != 6 || !/^[0-9]{6}$/.test(totpCode)) {
				desc = language["comm.error.tips.98"];
				util.showMsg(desc);
				return false;
			}
		}
		if (!util.checkMobile(phone)) {
			util.showMsg(language["comm.error.tips.10"]);
			return;
		}
		util.hideerrortips('bindphone-errortips');
		var url = "/user/validatePhone.html?random=" + Math.round(Math.random() * 100);
		var param = {
			isUpdate:isUpdate,
			phone : phone,
			oldcode : oldcode,
			newcode : newcode,
			totpCode : totpCode,
			areaCode : areaCode,
			imgcode : imgcode
		};
		jQuery.post(url, param, function(result) {
			util.showMsg(result.msg);
			if (result.code == 0) {
				window.setTimeout(function() {
					window.location.href = '/user/security.html';
				}, 1000);
			}
		}, "json");
	},
	submitbindGoogleForm : function() {
		var code = document.getElementById("bindgoogle-topcode").value;
		var totpKey = document.getElementById("bindgoogle-key-hide").value;
		var phoneCode = 0;
		var desc = '';
		if (!/^[0-9]{6}$/.test(code)) {
			desc = language["comm.error.tips.98"];
		}
		if (desc != "") {
			util.showMsg(desc);
			return;
		} else {
			util.hideerrortips('bindgoogle-errortips', false);
		}
		var url = "/user/validateAuthenticator.html?random=" + Math.round(Math.random() * 100);
		var param = {
			code : code,
			totpKey : totpKey
		};
		jQuery.post(url, param, function(data) {
			if (data.code == 0) {
				util.showMsg(data.msg);
				window.setTimeout(function() {
					window.location.href = '/user/security.html';
				}, 1000);
			} else {
				util.showMsg(data.msg);
				document.getElementById("bindgoogle-topcode").value = "";
			}
		}, "json");
	},
	submitlookbindGoogleForm : function() {
		var totpCode = 0;
		var desc = '';
		totpCode = document.getElementById("unbindgoogle-topcode").value;
		if (!/^[0-9]{6}$/.test(totpCode)) {
			desc = language["comm.error.tips.98"];
		}
		if (desc != "") {
			util.showMsg(desc);
			return;
		} else {
			util.hideerrortips('unbindgoogle-errortips');
		}
		var url = "/user/getGoogleTotpKey.html?random=" + Math.round(Math.random() * 100);
		var param = {
			totpCode : totpCode
		};
		jQuery.post(url, param, function(data) {
			if (data.code == -1) {
				util.showMsg(data.msg);
				document.getElementById("unbindgoogle-topcode").value = "";
			} else if (data.code == 0) {

				if (data.code == 0) {
					if (navigator.userAgent.indexOf("MSIE") > 0) {
						jQuery('#unqrcode').qrcode({
							text : toUtf8(data.qecode),
							width : "140",
							height : "140",
							render : "table",
							correctLevel:0
						});
					} else {
						jQuery('#unqrcode').qrcode({
							text : toUtf8(data.qecode),
							width : "140",
							height : "140",
							correctLevel:0
						});
					}
					$("#unbindgoogle-key").html(data.totpKey);
					$(".unbindgoogle-hide-box").show();
					$(".unbindgoogle-show-box").hide();
					centerModals();
				}
			}
		}, "json");
	},
	submitPwdForm : function(pwdType, istrade) {

		var originPwdEle = "";
		var newPwdEle = "";
		var reNewPwdEle = "";
		var phoneCodeEle = "";
		var totpCodeEle = "";
		var errorEle = "";
		if (pwdType == 0) {
			originPwdEle = "unbindloginpass-oldpass";
			newPwdEle = "unbindloginpass-newpass";
			reNewPwdEle = "unbindloginpass-confirmpass";
			phoneCodeEle = "unbindloginpass-msgcode";
			totpCodeEle = "unbindloginpass-googlecode";
			errorEle = "unbindloginpass-errortips";
		} else {
			if (istrade) {
				originPwdEle = "unbindtradepass-oldpass";
				newPwdEle = "unbindtradepass-newpass";
				reNewPwdEle = "unbindtradepass-confirmpass";
				phoneCodeEle = "unbindtradepass-msgcode";
				totpCodeEle = "unbindtradepass-googlecode";
				errorEle = "unbindtradepass-errortips";

			} else {
				originPwdEle = "bindtradepass-oldpass";
				newPwdEle = "bindtradepass-newpass";
				reNewPwdEle = "bindtradepass-confirmpass";
				phoneCodeEle = "bindtradepass-msgcode";
				totpCodeEle = "bindtradepass-googlecode";
				errorEle = "bindtradepass-errortips";
			}
		}
		if (istrade) {
			var originPwd = util.trim(document.getElementById(originPwdEle).value);
		}
		var newPwd = util.trim(document.getElementById(newPwdEle).value);
		var reNewPwd = util.trim(document.getElementById(reNewPwdEle).value);
		if (istrade) {
			var originPwdTips = util.isOriginPassword(originPwd);
		}
		var newPwdTips = util.isPassword(newPwd);
		var reNewPwdTips = util.isPassword(reNewPwd);
		var originTip = true;


        if (istrade && originPwdTips != "") {
			util.showMsg(originPwdTips);
			return;
		} else {

			util.hideerrortips(errorEle);
		}
		if (newPwdTips != "") {
			util.showMsg(newPwdTips);
			return;
		} else {
			util.hideerrortips(errorEle);
		}
		if (reNewPwdTips != "") {
			util.showMsg(reNewPwdTips);
			return;
		} else if (newPwd != reNewPwd) {
			util.showMsg(language["comm.error.tips.109"]);
			document.getElementById(reNewPwdEle).value = "";
			return;
		} else {
			util.hideerrortips(errorEle);
		}

		var phoneCode = "";
		var totpCode = "";
		if (document.getElementById(phoneCodeEle) != null) {
			phoneCode = util.trim(document.getElementById(phoneCodeEle).value);
			if (phoneCode == "") {
				util.showMsg(language["comm.error.tips.60"]);
				return;
			}
			if (!/^[0-9]{6}$/.test(phoneCode)) {
				util.showMsg(language["comm.error.tips.124"]);
				return;
			} else {
				util.hideerrortips(errorEle);
			}
		}
		if (document.getElementById(totpCodeEle) != null) {
			totpCode = util.trim(document.getElementById(totpCodeEle).value);
			if (!/^[0-9]{6}$/.test(totpCode)) {
				util.showMsg(language["comm.error.tips.98"]);
				return;
			} else {
				util.hideerrortips(errorEle);
			}
		}
		if (document.getElementById(phoneCodeEle) == null && document.getElementById(totpCodeEle) == null) {
			util.showMsg(language["comm.error.tips.110"]);
			return;
		}

		var url = "/user/modifyPwd.html?random=" + Math.round(Math.random() * 100);
		var param = {
			pwdType : pwdType,
			originPwd : originPwd,
			newPwd : newPwd,
			reNewPwd : reNewPwd,
			phoneCode : phoneCode,
			totpCode : totpCode
		};
		jQuery.post(url, param, function(data) {
			if (data.code == 0) {
				if (istrade) {
					//"修改成功，请牢记您的" + (pwdType == 0 ? '登录' : '交易') + "密码！"
					var type = (pwdType == 0 ? language["comm.error.tips.142"] :language["comm.error.tips.143"]);
					util.showMsg(language["comm.error.tips.141"].format(type));
				} else {					
					util.showMsg(language["comm.error.tips.140"]);
				}
				window.setTimeout(function() {
					window.location.href = '/user/security.html';
				}, 1000);
			} else if (data.code == -3) {
				util.showMsg(data.msg);
				document.getElementById(reNewPwdEle).value = "";
			} else if (data.code == -5) {
				util.showMsg(data.msg);
				document.getElementById(originPwdEle).value = "";
			} else if (data.code == -6) {
				util.showMsg(data.msg);
				document.getElementById(totpCodeEle).value = "";
			} else if (data.code == -7) {
				util.showMsg(data.msg);
				document.getElementById(phoneCodeEle).value = "";
			} else if (data.code == -10) {
				util.showMsg(data.msg);
			} else if (data.code == -13) {
				util.showMsg(data.msg);
			} else if (data.code == -20) {
				util.showMsg(data.msg);
			}
		}, "json");
	}
};
$(function() {
	$(".btn-imgcode").on("click", function() {
		this.src = "/servlet/ValidateImageServlet?r=" + Math.round(Math.random() * 100);
	});
	security.loadgoogleAuth();
	$("#bindemail-Btn").on("click", function() {
		var email = $("#bindemail-email").val();
		security.submitValidateEmailForm(email, false);
	});
	$("#bindemail-send").on("click", function() {
		var email = $("#bindemail-send-email").val();
		security.submitValidateEmailForm(email, true);
	});
	$("#unbindphone-areaCode").on("change", function() {
		security.areaCodeChange(this, "unbindphone-newphone-areacode");
	});
	$(".btn-sendmsg").on("click", function() {
		if (this.id == "unbindphone-newsendmessage") {
			var areacode = $("#unbindphone-newphone-areacode").html();
			var phone = $("#unbindphone-newphone").val();
			if (phone == "") {
				util.showerrortips("unbindphone-errortips", language["comm.error.tips.6"]);
				return;
			}
			msg.sendMsgCode($(this).data().msgtype, $(this).data().tipsid, this.id, areacode, phone);
		}else if (this.id == "bindphone-sendmessage") {
			var areacode = $("#bindphone-newphone-areacode").html();
			var phone = $("#bindphone-newphone").val();
			msg.sendMsgCode($(this).data().msgtype, $(this).data().tipsid, this.id, areacode, phone);
		} else {
			msg.sendMsgCode($(this).data().msgtype, $(this).data().tipsid, this.id);
		}
	});
	$("#unbindphone-Btn").on("click", function() {
		security.submitValidatePhoneForm(1);
	});
	$("#bindphone-areaCode").on("change", function() {
		security.areaCodeChange(this, "bindphone-newphone-areacode");
	});
	$("#bindphone-Btn").on("click", function() {
		security.submitBindPhone(0);
	});
	$("#bindgoogle-Btn").on("click", function() {
		security.submitbindGoogleForm();
	});
	$("#unbindgoogle-Btn").on("click", function() {
		security.submitlookbindGoogleForm();
	});
	$("#unbindloginpass-Btn").on("click", function() {
		security.submitPwdForm(0, true);
	});
	$("#unbindtradepass-Btn").on("click", function() {
		security.submitPwdForm(1, true);
	});
	$("#bindtradepass-Btn").on("click", function() {
		security.submitPwdForm(1, false);
	});
});