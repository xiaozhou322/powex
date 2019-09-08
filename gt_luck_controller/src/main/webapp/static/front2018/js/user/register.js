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
			util.showerrortips("register-errortips", desc);
			return;
		} else {
			util.hideerrortips("register-errortips");
		}
		var url = "/user/reg/chcekregname.html?name=" + encodeURI(regUserName) + "&type=" + regType + "&random=" + Math.round(Math.random() * 100);
		$.get(url, null, function(data) {
			if (data.code == -1) {
				util.showerrortips("register-errortips", data.msg);
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
			util.showerrortips("register-errortips", desc);
			return false;
		} else {
			util.hideerrortips("register-errortips");
		}
		return true;
	},
	checkRePassword : function() {
		var pwd = util.trim(document.getElementById("register-password").value);
		var rePwd = util.trim(document.getElementById("register-confirmpassword").value);
		var desc = util.isPassword(pwd);
		if (desc != "") {
			util.showerrortips("register-errortips", desc);
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
			util.showerrortips("register-errortips", desc);
			return false;
		} else {
			util.hideerrortips("register-errortips");
			return true;
		}
	},
	checkManMachine : function() {
		var sessionid = document.getElementById("sessionid").value;
		var sig = document.getElementById("sig").value;
		var token = document.getElementById("token").value;
		var scene = document.getElementById("scene").value;
		if (sessionid == "" || sessionid == null
				|| sig == "" || sig == null
				|| token == "" || token == null
				|| scene == "" || scene == null) {
			return false;
		}
		return true;
	},
	regSubmit : function() {
		if (!document.getElementById("agree").checked) {
			util.showerrortips("register-errortips", language["comm.error.tips.23"]);
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
			var invite_code = util.trim(document.getElementById("invite_code").value);
			var regPhoneCode = 0;
			if (regType == 0) {
				regPhoneCode = document.getElementById("register-msgcode").value;
				if (regPhoneCode == "") {
					util.showerrortips("register-errortips", language["comm.error.tips.60"]);
					return;
				}
			}
			var regEmailCode = 0;
			if (regType == 1) {
				regEmailCode = document.getElementById("register-email-code").value;
				if (regEmailCode == "") {
					util.showerrortips("register-errortips", language["comm.error.tips.138"]);
					return;
				}
			}
			
			if(!this.checkManMachine()) {
				util.showerrortips("register-errortips", language["login.error.tips.1"]);
				return;
			}
			var intro_user ="";
			if(invite_code!=null&&invite_code.length>0){
				intro_user=invite_code;
			}else{
				intro_user= document.getElementById("register-intro").value;
			}
			
			
			//人机验证
			var sessionid = document.getElementById("sessionid").value;
			var sig = document.getElementById("sig").value;
			var token = document.getElementById("token").value;
			var scene = document.getElementById("scene").value;
			var appkey = document.getElementById("appkey").value;
			
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
					util.showerrortips("register-errortips", data.msg);
					if (data.code == -20) {
//						document.getElementById("register-imgcode").value = "";
						$(".btn-imgcode").click();
					}
				} else {
					console.log(1);
					$(".pop_frame,.pop_body").show();
					$(".btn_approve").click(function(){
						window.location='/user/realCertification.html';
					})

					$(".frame_close").click(function(){
						window.location='/index.html';
					})
					/*注册成功提示和跳转到实名认证 util.showerrortips("register-errortips", language["comm.succ.register"]);
					window.setTimeout("window.location='/user/realCertification.html'", 2000);*/
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
		var areacode = $("#register-phone-areacode").html();
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
			util.showerrortips("register-errortips", language["comm.error.tips.7"]);
			return;
		}
		email.sendcode($(this).data().msgtype, $(this).data().tipsid, this.id, address/*$("#register-imgcode").val()*/);
	});
});