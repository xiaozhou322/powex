var clicktype=null;
var login = {
	loginNameOnblur : function() {
		var uName = document.getElementById("login-account").value;
		if (!util.checkEmail(uName) && !util.checkMobile(uName)) {
			// util.layerTips("login-account", language["comm.error.tips.1"]);
			layer.msg(language["comm.error.tips.1"]);
		}
	},
	checkLoginUserName : function() {
		var uName = document.getElementById("login-account").value;
		if (uName == "") {
			// util.layerTips("login-account", language["comm.error.tips.5"]);
			layer.msg(language["comm.error.tips.5"]);
			return false;
		} else if (!util.checkEmail(uName) && !util.checkMobile(uName)) {
			// util.layerTips("login-account", language["comm.error.tips.1"]);
			layer.msg(language["comm.error.tips.1"]);
			return false;
		}
		util.hideerrortips("login-errortips");
		return true;
	},
	checkLoginPassword : function() {
		var password = document.getElementById("login-password").value;
		if (password == "") {
			// util.layerTips("login-password", language["comm.error.tips.2"]);
			layer.msg(language["comm.error.tips.2"]);
			return false;
		} else if (password.length < 6) {
			// util.layerTips("login-password", language["comm.error.tips.3"]);
			layer.msg(language["comm.error.tips.3"]);
			return false;
		}
		util.hideerrortips("login-errortips");
		return true;
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
			util.layerTips("login-password", language["login.error.tips.1"]);
			return false;
		}
		util.hideerrortips("login-errortips");
		return true;
	},
	loginSubmit : function() {
		if (login.checkLoginUserName() && login.checkLoginPassword() && login.checkManMachine()) {
			var url = "/user/login/index.html?random=" + Math.round(Math.random() * 100);
			//var url = "/user/login/indexTest.html?random=" + Math.round(Math.random() * 100);
			var uName = document.getElementById("login-account").value;
			var pWord = document.getElementById("login-password").value;
			
			var sessionid = document.getElementById("sessionid").value;
			var sig = document.getElementById("sig").value;
			var token = document.getElementById("token").value;
			var scene = document.getElementById("scene").value;
			var appkey = document.getElementById("appkey").value;
			
			var longLogin = 0;
			if (util.checkEmail(uName)) {
				longLogin = 1;
			}
			var forwardUrl = "";
			if (document.getElementById("forwardUrl") != null) {
				forwardUrl = document.getElementById("forwardUrl").value;
			}
			var param = {
				loginName : uName,
				password : pWord,
				type : longLogin,
				sessionid : sessionid,
				sig : sig,
				token : token,
				scene : scene,
				appkey : appkey
			};
			$.post(url, param, function(data) {
				if (data.code < 0) {
					
					if(data.code == -2){
						$("#login-password").val("");
						// util.layerTips("login-password", data.msg);
						layer.msg(data.msg);
					}else{
						// util.layerTips("login-account", data.msg);
						layer.msg(data.msg);
					}
					nc.reset();
				} else if(data.code==101){
					window.location.href = "/index.html";
				}else {
					if(data.ValidateType==0){//手机验证
						clicktype=0;
						$("#h_title1,.text_1").show();
						$("#h_title2,.text_2").hide();
						$("#h_title3,.text_3").hide();
						$(".loginMain").hide();
						$(".abnormalLogin").show();
					}else if(data.ValidateType==1){//邮箱验证
						clicktype=1;
						$("#h_title1,.text_1").hide();
						$("#h_title2,.text_2").show();
						$("#h_title3,.text_3").hide();
						$(".loginMain").hide();
						$(".abnormalLogin").show();
					}else if(data.ValidateType==2){//谷歌验证
						clicktype=2;
						$("#h_title1,.text_1").hide();
						$("#h_title2,.text_2").hide();
						$("#h_title3,.text_3").show();
						$(".loginMain").hide();
						$(".abnormalLogin").show();
					}else{
						
					if (forwardUrl.trim() == "") {
						window.location.href = "/index.html";
					} else {
						window.location.href = forwardUrl;
					}
				}
				}
			}, "json");
		}
	},
	
};
$(function() {
	$("#login-password").on("focus", function() {
		login.loginNameOnblur();
		util.callbackEnter(login.loginSubmit);
	});
	$("#login-submit").on("click", function() {
		login.loginSubmit();
	});
	
});