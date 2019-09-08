var msg = {
	secs : 121,
	msgtype : 1,
	sendMsgCode : function(type, tipElement_id, button_id, areaCode, phone,vcode) {
		var that = this;
		var tipElement = document.getElementById(tipElement_id);
		var button = document.getElementById(button_id);
		if (typeof (areaCode) == 'undefined') {
			areaCode = 0;
		}
		if (typeof (phone) == 'undefined') {
			phone = 0;
		} else {
			if (!util.checkMobile(phone)) {
				util.showerrortips(tipElement_id, language["comm.error.tips.10"]);
				return;
			}
		}
		var url = "/user/sendMsg.html?random=" + Math.round(Math.random() * 100);
		$.post(url, {
			type : type,
			msgtype : this.msgtype,
			areaCode : areaCode,
			phone : phone,
			vcode:vcode
		}, function(data) {
			if (data.code < 0) {
				util.showerrortips(tipElement_id, data.msg);
				$(".btn-imgcode").click();
			} else if (data.code == 0) {
				util.showerrortips(tipElement_id, "");
				button.disabled = true;
				for ( var num = 1; num <= that.secs; num++) {
					window.setTimeout("msg.updateNumber(" + num + ",'" + button_id + "',2)", num * 1000);
				}
			}
		}, "json");
	},
	sendMsgCodeTwo : function(type, tipElement_id, button_id, phone) {
		var that = this;
		var tipElement = document.getElementById(tipElement_id);
		var button = document.getElementById(button_id);
		if (typeof (areaCode) == 'undefined') {
			areaCode = 0;
		}
		if (typeof (phone) == 'undefined') {
			phone = 0;
		} else {
			if (!util.checkMobile(phone)) {
				util.showerrortips(tipElement_id, language["comm.error.tips.10"]);
				return;
			}
		}
		var url = "/user/sendMsgTwo.html?random=" + Math.round(Math.random() * 100);
		$.post(url, {
			type : type,
			msgtype : this.msgtype,
			loginName : phone,
			}, function(data) {
				
			if (data.code < 0) {
				util.showerrortips(tipElement_id, data.msg);
				$(".btn-imgcode").click();
			} else if (data.code == 0) {
				
				util.showerrortips(tipElement_id, "");
				button.disabled = true;
				for ( var num = 1; num <= that.secs; num++) {
					window.setTimeout("msg.updateNumber(" + num + ",'" + button_id + "',2)", num * 1000);
				}
			}
		}, "json");
	},
	updateNumber : function(num, button_id, isVoice) {
		var button = document.getElementById(button_id);
		if (num == this.secs) {
			button.innerHTML = language["comm.error.tips.33"];
			button.disabled = false;
		} else {
			var printnr = this.secs - num;
			button.innerHTML = language["comm.error.tips.32"].format(printnr);
		}
	}
};
var email = {
	secs : 121,
	msgtype : 1,
	sendcode : function(type, tipElement_id, button_id, address,vcode) {
		var that = this;
		var tipElement = document.getElementById(tipElement_id);
		var button = document.getElementById(button_id);
		if (typeof (address) == 'undefined') {
			address = 0;
		}else {
			if (!util.checkEmail(address)) {
				util.showerrortips(tipElement_id, language["comm.error.tips.13"]);
				return;
			}
		}
		var url = "/user/sendMailCode.html?random=" + Math.round(Math.random() * 100);
		$.post(url, {
			type : type,
			msgtype : this.msgtype,
			email : address,
			vcode:vcode
		}, function(data) {
			if (data.code < 0) {
				util.showerrortips(tipElement_id, data.msg);
			} else if (data.code == 0) {
				button.disabled = true;
				util.showerrortips(tipElement_id, "");
				for ( var num = 1; num <= that.secs; num++) {
					window.setTimeout("email.updateNumber(" + num + ",'" + button_id + "',2)", num * 1000);
				}
			}
		}, "json");
	},
	sendcodeTwo : function(type, tipElement_id, button_id, address) {
		var that = this;
		var tipElement = document.getElementById(tipElement_id);
		var button = document.getElementById(button_id);
		if (typeof (address) == 'undefined') {
			address = 0;
		}
		var url = "/user/sendMailCodeTwo.html?random=" + Math.round(Math.random() * 100);
		$.post(url, {
			type : type,
			msgtype : this.msgtype,
			loginName : address,
		}, function(data) {
			if (data.code < 0) {
				/*util.showerrortips(tipElement_id, data.msg);*/
			} else if (data.code == 0) {
				button.disabled = true;
				util.showerrortips(tipElement_id, "");
				for ( var num = 1; num <= that.secs; num++) {
					window.setTimeout("email.updateNumber(" + num + ",'" + button_id + "',2)", num * 1000);
				}
			}
		}, "json");
	},
	updateNumber : function(num, button_id, isVoice) {
		var button = document.getElementById(button_id);
		if (num == this.secs) {
			button.innerHTML = language["comm.error.tips.33"];
			button.disabled = false;
		} else {
			var printnr = this.secs - num;
			button.innerHTML = language["comm.error.tips.32"].format(printnr);
		}
	}
};