var clicktype=null;
var login = {
	loginNameOnblur : function() {
		var uName = document.getElementById("login-account").value;
		if (!util.checkEmail(uName) && !util.checkMobile(uName)) {
			util.layerTips("login-account", language["comm.error.tips.1"]);
		}
	},
	checkLoginUserName : function() {
		var uName = document.getElementById("login-account").value;
		if (uName == "") {
			util.layerTips("login-account", language["comm.error.tips.5"]);
			return false;
		} else if (!util.checkEmail(uName) && !util.checkMobile(uName)) {
			util.layerTips("login-account", language["comm.error.tips.1"]);
			return false;
		}
		util.hideerrortips("login-errortips");
		return true;
	},
	checkLoginPassword : function() {
		var password = document.getElementById("login-password").value;
		if (password == "") {
			util.layerTips("login-password", language["comm.error.tips.2"]);
			return false;
		} else if (password.length < 6) {
			util.layerTips("login-password", language["comm.error.tips.3"]);
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
			var param={
					'loginName':uName
			};
			var last=JSON.stringify(param); 
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
				sessionid : sessionid,
				sig : sig,
				token : token,
				scene : scene,
				appkey : appkey,
				type : longLogin
			};
			$.post(url, param, function(data) {
				if (data.code < 0) {
					
					if(data.code == -2){
						$("#login-password").val("");
						util.layerTips("login-password", data.msg);
						
					}else{
						util.layerTips("login-account", data.msg);
					}
					nc.reset();
				} else if(data.code==101){
					window.location.href = "/index.html";
				}else if(data.code ==102){
					if(data.ValidateType==0){//手机验证
						clicktype=0;
						$("#h_title1,.text_1").show();
						$("#h_title2,.text_2").hide();
						$("#h_title3,.text_3").hide();
						$(".loginL,.loginR").hide();
						$(".abnormalLogin").show();
					}else if(data.ValidateType==1){//邮箱验证
						clicktype=1;
						$("#h_title1,.text_1").hide();
						$("#h_title2,.text_2").show();
						$("#h_title3,.text_3").hide();
						$(".loginL,.loginR").hide();
						$(".abnormalLogin").show();
					}else if(data.ValidateType==2){//谷歌验证
						clicktype=2;
						$("#h_title1,.text_1").hide();
						$("#h_title2,.text_2").hide();
						$("#h_title3,.text_3").show();
						$(".loginL,.loginR").hide();
						$(".abnormalLogin").show();
					}else{
						//提示异常

					if (forwardUrl.trim() == "") {
						//window.location.href = "/redirectPage.html?pageUrl=front/index";
						window.location.href = "/index.html";
					} else {
						window.location.href = forwardUrl;

					}
					
					}
				}
			}, "json");
		}
	},
	 ValidateCode:function(){
		var code = null;
		if(clicktype==null){
			return;
		}else{
			if(clicktype==0){
				code=$("#phoneCode").val();
			}else if(clicktype==1){
				code=$("#emailCode").val();
			}else{
				code=$("#googleCode").val();
			}
		}
		if(code==null || code.length==0){
			if(clicktype==0){
				util.layerTips("phoneCode", "验证码不能为空");
			}else if(clicktype==1){
				util.layerTips("emailCode", "验证码不能为空");
			}else{
				util.layerTips("googleCode", "验证码不能为空");
			}
			
			return;
		}
		var param={	
				codeType:clicktype,
				code:code
		}
		$.post("/user/codelogin.html?random=" + Math.round(Math.random() * 100), param, function(data) {
		console.log(data);
			if (data.code == 0) {
				//提示登录名不存在
				window.location.href = "/index.html";
			} else if(data.code == -1){
				if(clicktype==0){
					util.layerTips("phoneCode", data.msg);
				}else if(clicktype==1){
					util.layerTips("emailCode", data.msg);
				}else{
					util.layerTips("googleCode", data.msg);
				}
				
			}else {
				//提示异常
				
			}
		}, "json");
		
	},
	
	
	
};

function way1(){
	email.sendcodeTwo(3, "layui-layer", "login_sendemail");
}	

function way2(){
	msg.sendMsgCodeTwo(18, "layui-layer", "login_sendphone");
}
$(function() {
	$("#login-password").on("focus", function() {
		login.loginNameOnblur();
		util.callbackEnter(login.loginSubmit);
	});
	$("#login-submit").on("click", function() {
		login.loginSubmit();
	});
	$("#Secondlogin-submit").on("click", function() {
		login.ValidateCode();
	});
//	谷歌回车认证
	$("#googleCode").on("focus", function() {
	//	login.ValidateCode();
		
		util.callbackEnter(login.ValidateCode);
	});
//	短信回车认证
	$("#phoneCode").on("focus", function() {
	//	login.ValidateCode();
		util.callbackEnter(login.ValidateCode);
	});
//	邮箱回车认证
	$("#emailCode").on("focus", function() {
		//	login.ValidateCode();
		util.callbackEnter(login.ValidateCode);
	});

});