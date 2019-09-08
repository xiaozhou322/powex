var reg = {
	checkRegUserName : function() {
		var regType = document.getElementById("regType").value;
		var regUserName = "";
		var desc = '';
		if (regType == 0) {
			// 验证手机号
			regUserName = util.trim(document.getElementById("register-phone").value);
			if (regUserName.indexOf(" ") > -1) {
				desc = language["comm.error.tips.8"];
			} else {
				if (regUserName == '') {
					desc = language["comm.error.tips.9"];
				} else if (!util.checkMobile(regUserName)) {
					desc = language["comm.error.tips.10"];
				}
			}
		} else {
			// 验证邮箱
			regUserName = util.trim(document.getElementById("register-email").value);
			if (regUserName.indexOf(" ") > -1) {
				desc = language["comm.error.tips.11"];
			} else {
				if (regUserName == '') {
					desc = language["comm.error.tips.12"];
				} else if (!util.checkEmail(regUserName)) {
					desc = language["comm.error.tips.13"];
				} else if (new RegExp("[,]", "g").test(regUserName)) {
					desc = language["comm.error.tips.14"];
				} else if (regUserName.length > 100) {
					desc = language["comm.error.tips.15"];
				}
			}
		}
		if (desc != "") {
			util.showMsg(desc);
			return;
		} else {
			util.hideerrortips("register-errortips");
		}
		var url = "/user/reg/chcekregname.html?name=" + encodeURI(regUserName) + "&type=" + regType + "&random=" + Math.round(Math.random() * 100);
		$.get(url, null, function(data) {
			if (data.code == -1) {
				util.showMsg(data.msg);
				return;
			} else {
				util.hideerrortips("register-errortips");
			}
		}, "json");
	},
	checkPassword : function() {
		var pwd = util.trim(document.getElementById("register-password").value);
		var desc = util.isPassword(pwd);
		if (desc != "") {
			util.showMsg(desc);
			return false;
		} else {
			util.hideerrortips("register-errortips");
		}
		return true;
	},
	checkRePassword : function() {
		var pwd = util.trim(document.getElementById("register-password").value);
		var rePwd = util.trim(document.getElementById("register-confirmpassword").value);
		if(pwd != '' && pwd != rePwd)
		{
			util.showMsg(language["comm.error.tips.109"]);
			return false;
		}
		var desc = util.isPassword(pwd);
		if (desc != "") {
			util.showMsg(desc);
			return false;
		} else {
			util.hideerrortips("register-errortips");
		}
		return true;
	},

	checkRegUserNameNoJquery : function() {
		var regType = document.getElementById("regType").value;
		var regUserName = "";
		var desc = '';
		if (regType == 0) {
			// 验证手机号
			regUserName = util.trim(document.getElementById("register-phone").value);
			if (regUserName.indexOf(" ") > -1) {
				desc = language["comm.error.tips.8"];
			} else {
				if (regUserName == '') {
					desc = language["comm.error.tips.9"];
				} else if (!util.checkMobile(regUserName)) {
					desc = language["comm.error.tips.10"];
				}
			}
		} else {
			// 验证邮箱
			regUserName = util.trim(document.getElementById("register-email").value);
			if (regUserName.indexOf(" ") > -1) {
				desc = language["comm.error.tips.11"];
			} else {
				if (regUserName == '') {
					desc = language["comm.error.tips.12"];
				} else if (!util.checkEmail(regUserName)) {
					desc = language["comm.error.tips.13"];
				} else if (new RegExp("[,]", "g").test(regUserName)) {
					desc = language["comm.error.tips.14"];
				} else if (regUserName.length > 100) {
					desc = language["comm.error.tips.15"];
				}
			}
		}
		if (desc != "") {
			util.showMsg(desc);
			return false;
		} else {
			util.hideerrortips("register-errortips");
			return true;
		}
	},
	regSubmit : function() {
		if (!document.getElementById("agree").checked) {
			util.showMsg(language["comm.error.tips.23"]);
			return;
		}

		var regType = document.getElementById("regType").value;
		var areaCode = document.getElementById("register-phone-areacode").innerHTML;
		var flag = this.checkRegUserNameNoJquery() && this.checkPassword() && this.checkRePassword();
		if (flag == true) {
			var regUserName = "";
			if (regType == 0) {
				regUserName = util.trim(document.getElementById("register-phone").value);
			} else {
				regUserName = util.trim(document.getElementById("register-email").value);
			}
			/*validateCode = document.getElementById("register-imgcode").value;*/
			var pwd = util.trim(document.getElementById("register-password").value);
			var regPhoneCode = 0;
			if (regType == 0) {
				regPhoneCode = document.getElementById("register-msgcode").value;
				if (regPhoneCode == "") {
					util.showMsg(language["comm.error.tips.60"]);
					return;
				}
			}
			var regEmailCode = 0;
			if (regType == 1) {
				regEmailCode = document.getElementById("register-email-code").value;
				if (regEmailCode == "") {
					util.showMsg(language["comm.error.tips.138"]);
					return;
				}
			}
			var intro_user = document.getElementById("register-intro").value;
			
			//人机验证
			var sessionid = document.getElementById("sessionid").value;
			var sig = document.getElementById("sig").value;
			var token = document.getElementById("token").value;
			var scene = document.getElementById("scene").value;
			var appkey = document.getElementById("appkey").value;
			console.log(sessionid);
			console.log(sig);
			console.log(token);
			console.log(scene);
			
			var url = "/user/reg/index.html?random=" + Math.round(Math.random() * 100);
			var param = {
				regName : regUserName,
				password : pwd,
				regType : regType,
				/*vcode : validateCode,*/
				phoneCode : regPhoneCode,
				ecode : regEmailCode,
				areaCode : areaCode,
				intro_user : intro_user,
				sessionid : sessionid,
				sig : sig,
				token : token,
				scene : scene,
				appkey : appkey
			};
			jQuery.post(url, param, function(data) {
				if (data.code < 0) {
					// 注册失败
					util.showMsg(data.msg);
					if (data.code == -20) {
//						document.getElementById("register-imgcode").value = "";
						$(".btn-imgcode").click();
					}
				} else {
					util.showMsg(language["comm.succ.register"]);
					window.setTimeout("window.location='/user/realCertification.html'", 2000);
				}
			}, "json");
		}
	},
	areaCodeChange : function(ele, setEle) {
		var code = $(ele).val();
		$("#" + setEle).html("+" + code);
	},
};
$(function() {
	$(".btn-sendmsg").on("click", function() {
		var areacode = $("#register-phone-areacode").val();
		var phone = $("#register-phone").val();
		/*var vcode = $("#register-imgcode").val() ;*/
		msg.sendMsgCode($(this).data().msgtype, $(this).data().tipsid, this.id, areacode, phone/*,vcode*/);
	});
	$(".btn-imgcode").on("click", function() {
		this.src = "/servlet/ValidateImageServlet?r=" + Math.round(Math.random() * 100);
	});
	$("#register-phone").on("blur", function() {
		reg.checkRegUserName();
	});
	$("#register-email").on("blur", function() {
		reg.checkRegUserName();
	});
	$("#register-submit").on("click", function() {
		reg.regSubmit();
	});
	 $(".register-tab").on("click",
			    function() {
			        that = $(this);
			        var a = that.attr("class");
			        a.indexOf("active") >= 0 || ($(".register-tab").removeClass("active"), that.addClass("active"), $("." + that.data().show).show(), $("." + that.data().hide).hide(), $("#regType").val(that.data().type))
	});
	$("#register-areaCode").on("change", function() {
		reg.areaCodeChange(this, "register-phone-areacode");
	});
	$(".btn-sendemailcode").on("click", function() {
		var address = $("#register-email").val();
		if (address == "") {
			util.showMsg(language["comm.error.tips.7"]);
			return;
		}
		email.sendcode($(this).data().msgtype, $(this).data().tipsid, this.id, address/*,$("#register-imgcode").val()*/);
	});
});