var util = {
	ltrim : function(s) {
		return s.replace(/^\s*/, "");
	},

	rtrim : function(s) {
		return s.replace(/\s*$/, "");
	},

	trim : function(s) {
		return this.rtrim(this.ltrim(s));
	},
	checkPassWord : function(pass) {
		filter = /^[a-zA-Z0-9\u0391-\uFFE5]{2,20}/;
		if (!filter.test(this.trim(pass))) {
			return false;
		} else {
			return true;
		}
	},
	checkNumber : function(num) {
		filter = /^-?([1-9][0-9]*|0)(\.[0-9]+)?$/;
		if (!filter.test(this.trim(num))) {
			return false;
		} else {
			return true;
		}
	},
	checkNumberInt : function(num) {
		filter = /^-?([1-9][0-9]*|0)$/;
		if (!filter.test(this.trim(num))) {
			return false;
		} else {
			return true;
		}
	},

	checkEmail : function(email) {
		filter = /^([a-zA-Z0-9_\-\.\+]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
		if (!filter.test(this.trim(email))) {
			return false;
		} else {
			return true;
		}
	},
	checkMobile : function(mobile) {
		filter = /^1[3|4|5|8|7][0-9]\d{8}$/;
		if (!filter.test(this.trim(mobile))) {
			return false;
		} else {
			return true;
		}
	},
	callbackEnter : function(callfun) {
		document.onkeydown = function(event) { // 回车
			var e = event || window.event || arguments.callee.caller.arguments[0];
			if (e && e.keyCode == 13) {
				return callfun();
			}
		};
	},
	accAdd : function(arg1, arg2) {
		var r1, r2, m;
		try {
			r1 = arg1.toString().split(".")[1].length;
		} catch (e) {
			r1 = 0;
		}
		try {
			r2 = arg2.toString().split(".")[1].length;
		} catch (e) {
			r2 = 0;
		}
		m = Math.pow(10, Math.max(r1, r2));
		return (arg1 * m + arg2 * m) / m;
	},
	accMul : function(arg1, arg2) {
		var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
		try {
			m += s1.split(".")[1].length;
		} catch (e) {
		}
		try {
			m += s2.split(".")[1].length;
		} catch (e) {
		}
		return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
	},
	accDiv : function(arg1, arg2) {
		var t1 = 0, t2 = 0, r1, r2;
		try {
			t1 = arg1.toString().split(".")[1].length;
		} catch (e) {
		}
		try {
			t2 = arg2.toString().split(".")[1].length;
		} catch (e) {
		}
		with (Math) {
			r1 = Number(arg1.toString().replace(".", ""));
			r2 = Number(arg2.toString().replace(".", ""));
			return (r1 / r2) * pow(10, t2 - t1);
		}
	},
	numFormat : function(money, digit) {
		if (typeof (digit) == 'undefined') {
			digit = 4;
		}
		money = scientificToNumber(money);
		if (money != null && money.toString().split(".") != null && money.toString().split(".")[1] != null) {
			var end = money.toString().split(".")[1];
			if (end.length > digit) {
				end = end.substring(0, digit);
			} else if (end.length < digit) {
				var endCount = digit - end.length;
				for (var i = 0; i < endCount; i++) {
					end += "0";
				}
			}
			money = money.toString().split(".")[0] + "." + end;
		} else {
			money = money.toString() + ".";
			for (var i = 0; i < digit; i++) {
				money += "0";
			}
		}
		if (digit == 0) {
			money = money.substring(0, money.length - 1);
		}
		return money;
	},
	goIngKeypress : function(ele, event, decimal) {
		if (typeof (decimal) == 'undefined') {
			decimal = 4;
		}
		var keyCode = event.keyCode ? event.keyCode : event.which;
		if (decimal == 0 && keyCode == 46) {
			event.returnValue = false;
		} else if (((keyCode < 48 && keyCode != 46) || keyCode > 57)) {
			event.returnValue = false;
		} else if (ele.value.indexOf(".") > 0 && keyCode == 46) {
			event.returnValue = false;
		} else if (this.getCursortPosition(ele) > 0 && ele.value[0] == 0 && keyCode != 46 && ele.value.length < 2) {
			event.returnValue = false;
		} else if (ele.value.length <= 0 && keyCode == 46) {
			event.returnValue = false;
		} else if (this.getCursortPosition(ele) <= 0 && keyCode == 46) {
			event.returnValue = false;
		} else if (this.getCursortPosition(ele) > ele.value.indexOf(".") && ele.value.indexOf(".") >= 0
				&& (ele.value.length - ele.value.indexOf(".")) > decimal) {
			event.returnValue = false;
		} else if (ele.value.length >= 10) {
			event.returnValue = false;
		} else {
			event.returnValue = true;
		}
		return event.returnValue;
	},
	showconfirm : function(value, options) {
		var defaults = {
			title : language["comm.error.tips.37"],
			oktxt : language["comm.error.tips.40"],
			notxt : language["comm.error.tips.39"],
			html : false,
			noshow : false,
			okbtn : function() {
				$('#confirmTips').modal('hide');
				return;
			},
			nobtn : function() {
				$('#confirmTips').modal('hide');
				return;
			}
		};
		var confirmsettings;
		if (typeof (options) == "undefined") {
			confirmsettings = $.extend({}, defaults, defaults);
		} else {
			confirmsettings = $.extend({}, defaults, options);
		}

		var alertHTML = '<div id="confirmTips" class="modal fade" tabindex="-1">';
		alertHTML += '<div class="modal-dialog modal-sm">';
		alertHTML += '<div class="modal-content">';
		alertHTML += '<div class="modal-header">';
		alertHTML += '<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>';
		alertHTML += '<h4 class="modal-title text-danger">' + confirmsettings.title + '</h4>';
		alertHTML += '</div>';
		alertHTML += '<div class="modal-body text-danger">';
		if (confirmsettings.html) {
			alertHTML += value;
		} else {
			alertHTML += '<h6>' + value + '</h6>';
		}

		alertHTML += '</div>';
		alertHTML += '<div class="modal-footer">';
		alertHTML += '<button id="okbtn" type="button" class="btn btn-primary">' + confirmsettings.oktxt + '</button>';
		if (confirmsettings.noshow) {
			alertHTML += '<button id="nobtn" type="button" class="btn btn-default" onclick="settings.nobtn">' + confirmsettings.notxt + '</button>';
		}
		alertHTML += '</div>';
		alertHTML += '</div>';
		alertHTML += '</div>';
		alertHTML += '</div>';
		$('body').append(alertHTML);
		$("#okbtn", "#confirmTips").click(function() {
			confirmsettings.okbtn();
		});
		$("#nobtn", "#confirmTips").click(function() {
			try {
				confirmsettings.nobtn();
			} catch (e) {
			}
		});
		$('#confirmTips').on('hidden.bs.modal', function() {
			$('.modal-backdrop').remove();
			$('#confirmTips').remove();
			confirmsettings = {};
		});
		centerModals();
		$('#confirmTips').modal({
			backdrop : 'static',
			keyboard : false,
			show : true
		});
		return;
	},
	showerrortips : function(id, value, options) {
		if (id == "" && value != "") {
			var defaults = {
				title : language["comm.error.tips.37"],
				oktxt : language["comm.error.tips.40"],
				notxt : language["comm.error.tips.39"],
				html : false,
				noshow : false,
				okbtn : function() {
					$('#alertTips').modal('hide');
					return;
				},
				nobtn : function() {
					$('#alertTips').modal('hide');
					return;
				}
			};
			var settings;
			if (typeof (options) == "undefined") {
				settings = $.extend({}, defaults, defaults);
			} else {
				settings = $.extend({}, defaults, options);
			}

			var alertHTML = '<div id="alertTips" class="modal fade" tabindex="-1">';
			alertHTML += '<div class="modal-dialog modal-sm">';
			alertHTML += '<div class="modal-content">';
			alertHTML += '<div class="modal-header">';
			alertHTML += '<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>';
			alertHTML += '<h4 class="modal-title text-danger">' + settings.title + '</h4>';
			alertHTML += '</div>';
			alertHTML += '<div class="modal-body text-danger">';
			if (settings.html) {
				alertHTML += value;
			} else {
				alertHTML += '<h6>' + value + '</h6>';
			}

			alertHTML += '</div>';
			alertHTML += '<div class="modal-footer">';
			alertHTML += '<button id="okbtn" type="button" class="btn btn-primary">' + settings.oktxt + '</button>';
			if (settings.noshow) {
				alertHTML += '<button id="nobtn" type="button" class="btn btn-default" onclick="settings.nobtn">' + settings.notxt + '</button>';
			}
			alertHTML += '</div>';
			alertHTML += '</div>';
			alertHTML += '</div>';
			alertHTML += '</div>';
			$('body').append(alertHTML);
			$("#okbtn", "#alertTips").click(function() {
				settings.okbtn();
			});
			$("#nobtn", "#alertTips").click(function() {
				try {
					settings.nobtn();
				} catch (e) {
				}
			});
			$('#alertTips').on('hidden.bs.modal', function() {
				$('.modal-backdrop').remove();
				$('#alertTips').remove();
				settings = {};
			});
			centerModals();
			$('#alertTips').modal({
				backdrop : 'static',
				keyboard : false,
				show : true
			});
			return;
		}
		if (value != "") {
			$("#" + id).html(value);
		} else {
			$("#" + id).html("");
		}
		centerModals();
	},
	hideerrortips : function(id, isall) {
		if (isall) {
			$("span.errortips").html("");
		} else {
			$("#" + id).html("");
		}
	},
	moneyformat : function(money, digit) {
		if (typeof (digit) == 'undefined') {
			digit = 4;
		}
		if (money != null && money.toString().split(".") != null && money.toString().split(".")[1] != null) {
			var end = money.toString().split(".")[1];
			if (end.length > digit) {
				end = end.substring(0, digit);
			} else if (end.length < digit) {
				for ( var i = 0; i <= digit - end.length; i++) {
					end += "0";
				}
			}
			money = money.toString().split(".")[0] + "." + end;
		} else {
			money = money.toString() + ".";
			for ( var i = 0; i < digit; i++) {
				money += "0";
			}
		}
		if (digit == 0) {
			money = money.substring(0, money.length - 1);
		}
		return money;
	},
	getCursortPosition : function(ctrl) {
		var CaretPos = 0; // IE Support
		if (document.selection) {
			ctrl.focus();
			var Sel = document.selection.createRange();
			Sel.moveStart('character', -ctrl.value.length);
			CaretPos = Sel.text.length;
		}
		// Firefox support
		else if (ctrl.selectionStart || ctrl.selectionStart == '0')
			CaretPos = ctrl.selectionStart;
		return (CaretPos);
	},
	VerifyKeypress : function(ele, event, decimal) {
		if (typeof (decimal) == 'undefined') {
			decimal = 4;
		}
		var keyCode = event.keyCode ? event.keyCode : event.which;
		if (decimal == 0 && keyCode == 46) {
			event.returnValue = false;
		} else if (((keyCode < 48 && keyCode != 46 && keyCode != 8) || keyCode > 57)) {
			event.returnValue = false;
		} else if (ele.value.indexOf(".") > 0 && keyCode == 46) {
			event.returnValue = false;
		} else if (this.getCursortPosition(ele) > 0 && ele.value[0] == 0 && keyCode != 46 && ele.value.length < 2) {
			event.returnValue = false;
		} else if (ele.value.length <= 0 && keyCode == 46) {
			event.returnValue = false;
		} else if (this.getCursortPosition(ele) <= 0 && keyCode == 46) {
			event.returnValue = false;
		} else if (this.getCursortPosition(ele) > ele.value.indexOf(".") && ele.value.indexOf(".") >= 0 && (ele.value.length - ele.value.indexOf(".")) > decimal && keyCode != 8) {
			event.returnValue = false;
		} else if (ele.value.length >= 14 && keyCode != 8) {
			event.returnValue = false;
		} else {
			event.returnValue = true;
		}
		return event.returnValue;
	},
	recordTab : function(ele) {
		var type = ele.data().type;
		var title = "";
		var value = ele.data().value;;
		if (value == 0) {
			value = 1;
			title = language["comm.error.tips.47"] + "&nbsp;+";
			$("#recordbody" + type).hide();
		} else {
			value = 0;
			title = language["comm.error.tips.48"] + "&nbsp;-";
			$("#recordbody" + type).show();
		}
		ele.data().value = value;
		ele.html(title);
	},
	isPassword : function(pwd) {
		var desc = "";
		var c = new RegExp("^(?![0-9]+$)(?![a-zA-Z]+$)[\\S]{6,}$");
		if (pwd == "") {
			desc = language["comm.error.tips.17"];
		} else if (pwd.length < 6) {
			desc = language["comm.error.tips.3"];
		} else if (pwd.length > 16) {
			desc = language["comm.error.tips.18"];
		} else if (!c.test(pwd)) {
			desc = language["comm.error.tips.19"];
		}
		return desc;
	},
	isOriginPassword : function(pwd) {
		var desc = "";
		var c = new RegExp("^(?![0-9]+$)(?![a-zA-Z]+$)[\\S]{6,}$");
		if (pwd == "") {
			desc = language["comm.error.tips.17"];
		} else if (pwd.length < 6) {
			desc = language["comm.error.tips.3"];
		}
		return desc;
	},
	lrFixFooter : function(obj) {
		var footer = $(obj), doc = $(document);
		function fixFooter() {
			if (doc.height() - 4 <= $(window).height()) {
				footer.css({
					width : "100%",
					position : "absolute",
					left : 0,
					bottom : 0
				});
			} else {
				footer.css({
					position : "static"
				});
			}
		}
		fixFooter();
		$(window).on('resize.footer', function() {
			fixFooter();
		});
		$(window).on('scroll.footer', function() {
			fixFooter();
		});
	},
	globalurl : {
			market : "/real/market.html?symbol={0}&buysellcount={1}&successcount={2}",
			kline : "/kline/fullperiod.html",
			depth : "/kline/fulldepth.html",
			klineType : {
				60 : "001",
				300 : "005",
				900 : "015",
				1800 : "030",
				3600 : "060",
				86400 : "100",
				604800 : "200"
			},
			isremote : false,
	},
	//1成功，2错误，0警告
    layerAlert: function(id, txt, icon, options) {
        if ("" == id && null != txt) {
        	icon = icon || 0,
            options = {
                icon: icon,
                maxWidth: 500,
                title: language["comm.error.tips.37"],
                anim: 'up'
            },
            "function" == typeof options && (options.cancel = options);
            var e;
            e = 1 == icon ?
            function(id) {
                layer.close(id),
                "function" == typeof options ? options.call(this) : window.location.reload(true)
            }: function(id) {
                layer.close(id)
            },
            layer.alert(txt, options, e)
      
        }
    },
    showMsg : function(desc) {
		layer.msg(desc,{anim: 'up'});
	},
    layerConfirm: function(a, b) {
        options = {
            icon: 3,
            maxWidth: 200,
            title: language["comm.error.tips.37"],
            anim: 'up'
        },
        b = b ||
        function(a) {},
        layer.confirm(a, options, b)
    },
    layerTips: function(obj, txt, isRed) {
        layer.tips(txt, "#" + obj, {
            tips: [2, "#f5dddb", "#da2e22"],
            time: 3000
        }),
        isRed = isRed || false,
        isRed || ($("#" + obj).addClass("layer-error-tips"), setTimeout(function() {
            $("#" + obj).removeClass("layer-error-tips")
        },
        3000))
    },
	/**
	 * 网络请求 options
	 * {btn:当前操作按钮,url:请求地址,param:请求参数,success:成功回调函数,method:请求方法(post,get),type:服务器响应的数据类型,enter:是否是回车执行}
	 */
	network : function(options) {
		var defaults = {
			btn : "",
			url : "",
			param : {},
			success : function() {
			},
			method : "post",
			dataType : "json",
			enter : false,
		};
		var $settings = $.extend({}, defaults, options);
		var $success = function(result) {
			if ($settings.btn != "") {
				$settings.btn.disabled = false;
			}
			if (result.code == 401) {
				util.layerAlert("", result.msg, 2, function(index) {
					layer.close(index);
					window.location.href = "/user/login.html?forwardUrl=" + window.location.pathname
							+ window.location.search;
				});
				return;
			}
			$settings.success.call(this, result);
		};
		if ($settings.btn != "") {
			$settings.btn.disabled = true;
		}
		if ($settings.enter) {
			util.callbackEnter(function() {
			});
		}
		if ($settings.url != "") {
			if ($settings.url.indexOf("?") >= 0) {
				$settings.url = $settings.url + "&_t=" + Math.round(Math.random() * 100);
			} else {
				$settings.url = $settings.url + "?_t=" + Math.round(Math.random() * 100);
			}
		}
		if ($settings.method == "post") {
			$.post($settings.url, $settings.param, $success, $settings.dataType);
		} else if ($settings.method == "get") {
			$.post($settings.url, $success, $settings.dataType);
		}
	},
	/**
	 * 多语言(key,args...)
	 */
	getLan : function() {
		if (arguments.length == 0) {
			throw "key is undefined";
		}
		var key = arguments[0];
		var result = "";
		if ("undefined" == typeof (language[key])) {
			result = key;
		} else {
			result = language[key];
		}
		if (arguments.length == 1) {
			return result;
		}
		arguments[0] = result;
		return util.strFormat.apply(this, arguments);
	},
	/**
	 * 字符串格式化
	 */
	strFormat : function() {
		if (arguments.length == 0) {
			throw "args is undefined";
		}
		result = arguments[0];
		if (arguments.length == 1) {
			return result;
		}
		for (var i = 1; i < arguments.length; i++) {
			if (undefined == arguments[i]) {
				continue;
			}
			var reg = new RegExp("({[" + (i - 1) + "]})", "g");
			result = result.replace(reg, arguments[i]);
		}
		return result;
	}
};