!
function(t) {
	function e(a) {
		if (r[a]) return r[a].exports;
		var i = r[a] = {
			exports: {},
			id: a,
			loaded: !1
		};
		return t[a].call(i.exports, i, i.exports, e), i.loaded = !0, i.exports
	}
	var r = {};
	return e.m = t, e.c = r, e.p = "", e(0)
}([function(t, e) {
	function r() {
		var t, e = arguments.length,
			r = function() {};
		if (e) {
			t = arguments[0];
			for (var a in t.prototype) r.prototype[a] = t.prototype[a]
		}
		for (var i = 1; i < e; i++) {
			var o = arguments[i],
				s = o.prototype.__construct;
			s && (r.prototype.__featureConstructors || (r.prototype.__featureConstructors = []), r.prototype.__featureConstructors.push(s), delete o.prototype.__construct);
			for (var a in o.prototype) r.prototype[a] = o.prototype[a];
			s && (o.prototype.__construct = s)
		}
		var n = function() {
				if (this.__construct && this.__construct.apply(this, arguments), this.__featureConstructors) {
					var t, e = this.__featureConstructors,
						r = e.length;
					for (t = 0; t < r; t++) e[t].apply(this, arguments)
				}
			};
		return r.prototype.__classId = S++, void 0 != t && (n.__super = t.prototype, r.prototype.__super = t), n.prototype = new r, n
	}
	function a(t, e) {
		var r = e.prototype.__classId;
		if (t.__classId == r) return !0;
		for (var a = t.__super; void 0 != a;) {
			if (a.prototype.__classId == r) return !0;
			a = a.prototype.__super
		}
		return !1
	}
	function i(t) {
		return parseInt(t.toString().charAt(8))
	}
	function o(t) {
		return t < 10 ? "0" + t.toString() : t.toString()
	}
	function s() {
		$(document).ready(function() {
			function t() {
				navigator.userAgent.indexOf("Firefox") >= 0 ? setTimeout(f, 200) : f()
			}
			t(), $(window).resize(t), $("#chart_overlayCanvas").bind("contextmenu", function(t) {
				return t.cancelBubble = !0, t.returnValue = !1, t.preventDefault(), t.stopPropagation(), !1
			}), $("#chart_input_interface").submit(function(t) {
				t.preventDefault();
				var e = $("#chart_input_interface_text").val(),
					r = JSON.parse(e),
					a = r.command,
					i = r.content;
				switch (a) {
				case "set future list":
					Zt.getInstance().getChart().setFutureList(i);
					break;
				case "set current depth":
					Zt.getInstance().getChart().updateDepth(i);
					break;
				case "set current future":
					break;
				case "set current language":
					g(i);
					break;
				case "set current theme":
				}
			}), $("#chart_dropdown_symbols li").click(function() {
				$("#chart_dropdown_symbols li a").removeClass("selected"), $(this).find("a").addClass("selected"), $(".chart_dropdown_data").removeClass("chart_dropdown-hover"), $(".chart_dropdown_t").removeClass("chart_dropdown-hover");
				var t = $(this).attr("name");
				Zt.getInstance().getChart().setCurrentFuture(t)
			}), $("#chart_container .chart_dropdown .chart_dropdown_t").mouseover(function() {
				var t = $("#chart_container"),
					e = $(this),
					r = e.next(),
					a = t.offset().left,
					i = e.offset().left,
					o = t.width(),
					s = e.width(),
					n = r.width(),
					h = (n - s) / 2 << 0;
				i - h < a + 4 ? h = i - a - 4 : h += i + s + h > a + o - 4 ? i + s + h - (a + o - 4) + 19 : 4, r.css({
					"margin-left": -h
				}), e.addClass("chart_dropdown-hover"), r.addClass("chart_dropdown-hover")
			}).mouseout(function() {
				$(this).next().removeClass("chart_dropdown-hover"), $(this).removeClass("chart_dropdown-hover")
			}), $(".chart_dropdown_data").mouseover(function() {
				$(this).addClass("chart_dropdown-hover"), $(this).prev().addClass("chart_dropdown-hover")
			}).mouseout(function() {
				$(this).prev().removeClass("chart_dropdown-hover"), $(this).removeClass("chart_dropdown-hover")
			}), $("#chart_btn_parameter_settings").click(function() {
				$("#chart_parameter_settings").addClass("clicked"), $(".chart_dropdown_data").removeClass("chart_dropdown-hover"), $("#chart_parameter_settings").find("th").each(function() {
					var t = $(this).html(),
						e = 0,
						r = Be.get(),
						a = r.indics[t];
					$(this.nextElementSibling).find("input").each(function() {
						null != a && e < a.length && $(this).val(a[e]), e++
					})
				})
			}), $("#close_settings").click(function() {
				$("#chart_parameter_settings").removeClass("clicked")
			}), $("#chart_container .chart_toolbar_tabgroup a").click(function() {
				$(this).hasClass("selected") || x($(this).parent().attr("name"))
			}), $("#chart_toolbar_periods_vert ul a").click(function() {
				$(this).hasClass("selected") || x($(this).parent().attr("name"))
			}), $("#chart_show_tools").click(function() {
				v($(this).hasClass("selected") ? "off" : "on")
			}), $("#chart_toolpanel .chart_toolpanel_button").click(function() {
				$(".chart_dropdown_data").removeClass("chart_dropdown-hover"), $("#chart_toolpanel .chart_toolpanel_button").removeClass("selected"), $(this).addClass("selected");
				var t = $(this).children().attr("name");
				b.chartMgr.setRunningMode(Zt.DrawingTool[t])
			}), $("#chart_show_indicator").click(function() {
				w($(this).hasClass("selected") ? "off" : "on")
			}), $("#chart_tabbar li a").click(function() {
				$("#chart_tabbar li a").removeClass("selected"), $(this).addClass("selected");
				var t = $(this).attr("name"),
					e = Be.get();
				e.charts.indics[1] = t, Be.save(), 0 == ye.displayVolume ? Zt.getInstance().getChart().setIndicator(1, t) : Zt.getInstance().getChart().setIndicator(2, t)
			}), $("#chart_select_chart_style a").click(function() {
				$("#chart_select_chart_style a").removeClass("selected"), $(this).addClass("selected");
				var t = Be.get();
				t.charts.chartStyle = $(this)[0].innerHTML, Be.save();
				var e = Zt.getInstance();
				e.setChartStyle("frame0.k0", $(this).html()), e.redraw()
			}), $("#chart_dropdown_themes li").click(function() {
				$("#chart_dropdown_themes li a").removeClass("selected");
				var t = $(this).attr("name");
				"chart_themes_dark" == t ? y("dark") : "chart_themes_light" == t && y("light")
			}), $("#chart_select_main_indicator a").click(function() {
				$("#chart_select_main_indicator a").removeClass("selected"), $(this).addClass("selected");
				var t = $(this).attr("name"),
					e = Be.get();
				e.charts.mIndic = t, Be.save();
				var r = Zt.getInstance();
				r.setMainIndicator("frame0.k0", t) || r.removeMainIndicator("frame0.k0"), r.redraw()
			}), $("#chart_toolbar_theme a").click(function() {
				$("#chart_toolbar_theme a").removeClass("selected"), "dark" == $(this).attr("name") ? y("dark") : "light" == $(this).attr("name") && y("light")
			}), $("#chart_select_theme li a").click(function() {
				$("#chart_select_theme a").removeClass("selected"), "dark" == $(this).attr("name") ? y("dark") : "light" == $(this).attr("name") && y("light")
			}), $("#chart_enable_tools li a").click(function() {
				$("#chart_enable_tools a").removeClass("selected"), "on" == $(this).attr("name") ? v("on") : "off" == $(this).attr("name") && v("off")
			}), $("#chart_enable_indicator li a").click(function() {
				$("#chart_enable_indicator a").removeClass("selected"), "on" == $(this).attr("name") ? w("on") : "off" == $(this).attr("name") && w("off")
			}), $(document).keyup(function(t) {
				46 == t.keyCode && (Zt.getInstance().deleteToolObject(), Zt.getInstance().redraw("OverlayCanvas", !1))
			}), $("#chart_overlayCanvas").mousemove(function(t) {
				var e = t.target.getBoundingClientRect(),
					r = t.clientX - e.left,
					a = t.clientY - e.top,
					i = Zt.getInstance();
				1 == b.button_down ? (i.onMouseMove("frame0", r, a, !0), i.redraw("All", !1)) : (i.onMouseMove("frame0", r, a, !1), i.redraw("OverlayCanvas"))
			}).mouseleave(function(t) {
				var e = t.target.getBoundingClientRect(),
					r = t.clientX - e.left,
					a = t.clientY - e.top,
					i = Zt.getInstance();
				i.onMouseLeave("frame0", r, a, !1), i.redraw("OverlayCanvas")
			}).mouseup(function(t) {
				if (1 == t.which) {
					b.button_down = !1;
					var e = t.target.getBoundingClientRect(),
						r = t.clientX - e.left,
						a = t.clientY - e.top,
						i = Zt.getInstance();
					i.onMouseUp("frame0", r, a), i.redraw("All")
				}
			}).mousedown(function(t) {
				if (1 != t.which) return Zt.getInstance().deleteToolObject(), void Zt.getInstance().redraw("OverlayCanvas", !1);
				b.button_down = !0;
				var e = t.target.getBoundingClientRect(),
					r = t.clientX - e.left,
					a = t.clientY - e.top;
				Zt.getInstance().onMouseDown("frame0", r, a)
			}), $("#chart_parameter_settings :input").change(function() {
				var t = $(this).attr("name"),
					e = 0,
					r = [],
					a = Zt.getInstance();
				if ($("#chart_parameter_settings :input").each(function() {
					if ($(this).attr("name") == t) {
						if ("" != $(this).val() && null != $(this).val() && void 0 != $(this).val()) {
							var a = parseInt($(this).val());
							r.push(a)
						}
						e++
					}
				}), 0 != r.length) {
					a.setIndicatorParameters(t, r);
					var i = a.getIndicatorParameters(t),
						o = [];
					e = 0, $("#chart_parameter_settings :input").each(function() {
						$(this).attr("name") == t && ("" != $(this).val() && null != $(this).val() && void 0 != $(this).val() && ($(this).val(i[e].getValue()), o.push(i[e].getValue())), e++)
					});
					var s = Be.get();
					s.indics[t] = o, Be.save(), a.redraw("All", !1)
				}
			}), $("#chart_parameter_settings button").click(function() {
				var t = $(this).parents("tr").children("th").html(),
					e = 0,
					r = Zt.getInstance().getIndicatorParameters(t),
					a = [];
				$(this).parent().prev().children("input").each(function() {
					null != r && e < r.length && ($(this).val(r[e].getDefaultValue()), a.push(r[e].getDefaultValue())), e++
				}), Zt.getInstance().setIndicatorParameters(t, a);
				var i = Be.get();
				i.indics[t] = a, Be.save(), Zt.getInstance().redraw("All", !1)
			})
		})
	}
	function n() {
		Mr++;
		var t = Zt.getInstance().getLanguage();
		if (Mr > 3600) {
			var e = new Number(Mr / 3600);
			"en-us" == t ? $("#chart_updated_time_text").html(e.toFixed(0) + "h") : $("#chart_updated_time_text").html(e.toFixed(0) + "小时")
		} else if (Mr > 60 && Mr <= 3600) {
			var e = new Number(Mr / 60);
			"en-us" == t ? $("#chart_updated_time_text").html(e.toFixed(0) + "m") : $("#chart_updated_time_text").html(e.toFixed(0) + "分钟")
		} else Mr <= 60 && ("en-us" == t ? $("#chart_updated_time_text").html(Mr + "s") : $("#chart_updated_time_text").html(Mr + "秒"))
	}
	function h() {
		window.clearInterval(Ar), Mr = 0;
		var t = Zt.getInstance().getLanguage();
		"en-us" == t ? $("#chart_updated_time_text").html(Mr + "s") : $("#chart_updated_time_text").html(Mr + "秒"), Ar = setInterval(n, 1e3)
	}
	function c() {
		b.G_HTTP_REQUEST && 4 != b.G_HTTP_REQUEST.readyState && b.G_HTTP_REQUEST.abort()
	}
	function l() {
		var t = b.chartMgr.getDataSource("frame0.k0").getLastDate();
		t == -1 ? b.requestParam = _(b.mark_from, b.time_type, "1000", null) : b.requestParam = _(b.mark_from, b.time_type, null, t.toString()), Ir()
	}
	function u() {
		Be.get(), Be.save();
		var t = Be.get();
		Zt.getInstance().setChartStyle("frame0.k0", t.charts.chartStyle);
		var e = t.charts.period;
		x(e), $("#chart_period_" + e + "_v a").addClass("selected"), $("#chart_period_" + e + "_h a").addClass("selected"), "close" == t.charts.indicsStatus ? w("off") : "open" == t.charts.indicsStatus && w("on");
		var r = $("#chart_select_main_indicator");
		r.find("a").each(function() {
			$(this).attr("name") == t.charts.mIndic && $(this).addClass("selected")
		});
		var a = $("#chart_select_chart_style");
		a.find("a").each(function() {
			$(this)[0].innerHTML == t.charts.chartStyle && $(this).addClass("selected")
		}), Zt.getInstance().getChart().setMainIndicator(t.charts.mIndic), Zt.getInstance().setThemeName("frame0", t.theme), v("off"), "Dark" == t.theme ? y("dark") : "Light" == t.theme && y("light")
	}
	function _(t, e, r, a) {
		var i = "symbol=" + t + "&step=" + e + "&rand=" + Math.round(100 * Math.random());
		return i
	}
	function p() {
		b.chartMgr = ve.loadTemplate("frame0.k0", "BCCoin" + b.mark_from + b.time_type, "frame0.order", "0.order", "frame0.trade", "0.trade"), Zt.getInstance().redraw("All", !0)
	}
	function d(t, e, r) {
		var a, i = {
			x: -1,
			y: -1
		},
			o = {
				x: -1,
				y: -1
			},
			s = r.x - e.x,
			n = r.y - e.y;
		if (Math.abs(s) < 2) return i = {
			x: e.x,
			y: t.top
		}, o = {
			x: r.x,
			y: t.bottom
		}, a = [i, o];
		var h = n / s;
		return o.x = t.right, o.y = e.y + (t.right - e.x) * h, i.x = t.left, i.y = e.y + (t.left - e.x) * h, a = [i, o]
	}
	function g(t) {
		var e = t.replace(/-/, "_");
		$("#chart_language_switch_tmp").find("span").each(function() {
			var t = $(this).attr("name"),
				r = $(this).attr(e);
			t = "." + t;
			var a = $(t)[0];
			a && $(t).each(function() {
				$(this)[0].innerHTML = r
			})
		}), Zt.getInstance().setLanguage(t), Zt.getInstance().getChart().setTitle()
	}
	function f() {
		var t = window.innerWidth,
			e = window.innerHeight,
			r = $("#chart_container");
		r.css({
			width: t + "px",
			height: e + "px"
		});
		var a = $("#chart_toolbar"),
			i = $("#chart_toolpanel"),
			o = $("#chart_canvasGroup"),
			s = $("#chart_tabbar"),
			n = "inline" == i[0].style.display,
			h = "block" == s[0].style.display,
			c = {};
		c.x = 0, c.y = 0, c.w = t, c.h =41;
		var l = {};
		l.x = 0, l.y = c.h + 1, l.w = n ? 32 : 0, l.h = e - l.y;
		var u = {};
		u.w = n ? t - (l.w + 1) : t, u.h = h ? 22 : -1, u.x = t - u.w, u.y = e - (u.h + 1);
		var _ = {};
		_.x = u.x, _.y = l.y, _.w = u.w, _.h = u.y - l.y, a.css({
			left: c.x + "px",
			top: c.y + "px",
			width: c.w + "px",
			height: c.h + "px"
		}), n && i.css({
			left: l.x + "px",
			top: l.y + "px",
			width: l.w + "px",
			height: l.h + "px"
		}), o.css({
			left: _.x + "px",
			top: _.y + "px",
			width: _.w + "px",
			height: _.h + "px"
		});
		var p = $("#chart_mainCanvas")[0],
			d = $("#chart_overlayCanvas")[0];
		p.width = _.w*1.33, p.height = _.h*1.33, d.width = _.w*1.33, d.height = _.h*1.33, h && s.css({
			left: u.x + "px",
			top: u.y + "px",
			width: u.w + "px",
			height: u.h + "px"
		});
		var g = $("#chart_parameter_settings");
		g.css({
			left: t - g.width() >> 1,
			top: e - g.height() >> 1
		});
		var f = $("#chart_loading");
		f.css({
			left: t - f.width() >> 1,
			top: e - f.height() >> 2
		});
		var m = $("#chart_dom_elem_cache"),
			y = $("#chart_select_theme")[0],
			v = $("#chart_enable_tools")[0],
			w = $("#chart_enable_indicator")[0],
			x = $("#chart_dropdown_symbols"),
			C = $("#chart_toolbar_periods_vert"),
			P = $("#chart_toolbar_periods_horz")[0],
			b = $("#chart_show_indicator")[0],
			S = $("#chart_show_tools")[0],
			M = $("#chart_toolbar_theme")[0],
			A = $("#chart_dropdown_settings"),
			I = 4 + x.find(".chart_dropdown_t")[0].offsetWidth,
			D = I + C[0].offsetWidth,
			T = D + P.offsetWidth,
			k = T + b.offsetWidth + 4,
			N = k + S.offsetWidth + 4,
			R = N + M.offsetWidth,
			O = A.find(".chart_dropdown_t")[0].offsetWidth + 128;
		I += O, D += O, T += O, k += O, N += O, R += O, t < T ? m.append(P) : C.after(P), t < k ? (m.append(b), w.style.display = "") : (A.before(b), w.style.display = "none"), t < N ? (m.append(S), v.style.display = "") : (A.before(S), v.style.display = "none"), t < R ? (m.append(M), y.style.display = "") : (A.before(M), y.style.display = "none"), Zt.getInstance().redraw("All", !0)
	}
	function m(t, e) {
		return Zt.getInstance().scale(e > 0 ? 1 : -1), Zt.getInstance().redraw("All", !0), !1
	}
	function y(t) {
		if ($("#chart_toolbar_theme a").removeClass("selected"), $("#chart_select_theme a").removeClass("selected"), "dark" == t) {
			$("#chart_toolbar_theme").find("a").each(function() {
				"dark" == $(this).attr("name") && $(this).addClass("selected")
			}), $("#chart_select_theme a").each(function() {
				"dark" == $(this).attr("name") && $(this).addClass("selected")
			}), $("#chart_container").attr("class", "dark"), $("#kline_logo").removeClass("kline_logo_weight"), $("#kline_logo").addClass("kline_logo_black"), Zt.getInstance().setThemeName("frame0", "Dark");
			var e = Be.get();
			e.theme = "Dark", Be.save()
		} else if ("light" == t) {
			$("#chart_toolbar_theme").find("a").each(function() {
				"light" == $(this).attr("name") && $(this).addClass("selected")
			}), $("#chart_select_theme a").each(function() {
				"light" == $(this).attr("name") && $(this).addClass("selected")
			}), $("#chart_container").attr("class", "light"), $("#kline_logo").removeClass("kline_logo_black"), $("#kline_logo").addClass("kline_logo_weight"), Zt.getInstance().setThemeName("frame0", "Light");
			var e = Be.get();
			e.theme = "Light", Be.save()
		}
		var r = {};
		r.command = "set current theme", r.content = t, $("#chart_output_interface_text").val(JSON.stringify(r)), $("#chart_output_interface_submit").submit(), window._current_theme_change.raise(t), Zt.getInstance().redraw()
	}
	function v(t) {
		$(".chart_dropdown_data").removeClass("chart_dropdown-hover"), $("#chart_toolpanel .chart_toolpanel_button").removeClass("selected"), $("#chart_enable_tools a").removeClass("selected"), "on" == t ? ($("#chart_show_tools").addClass("selected"), $("#chart_enable_tools a").each(function() {
			"on" == $(this).attr("name") && $(this).addClass("selected")
		}), $("#chart_toolpanel")[0].style.display = "inline", Zt.getInstance()._drawingTool == Zt.DrawingTool.Cursor ? $("#chart_Cursor").parent().addClass("selected") : Zt.getInstance()._drawingTool == Zt.DrawingTool.CrossCursor && $("#chart_CrossCursor").parent().addClass("selected")) : "off" == t && ($("#chart_show_tools").removeClass("selected"), $("#chart_enable_tools a").each(function() {
			"off" == $(this).attr("name") && $(this).addClass("selected")
		}), $("#chart_toolpanel")[0].style.display = "none", Zt.getInstance().setRunningMode(Zt.getInstance()._beforeDrawingTool), Zt.getInstance().redraw("All", !0)), f()
	}
	function w(t) {
		if ($("#chart_enable_indicator a").removeClass("selected"), "on" == t) {
			$("#chart_enable_indicator a").each(function() {
				"on" == $(this).attr("name") && $(this).addClass("selected")
			}), $("#chart_show_indicator").addClass("selected");
			var e = Be.get();
			e.charts.indicsStatus = "open", Be.save();
			var r = e.charts.indics[1];
			0 == ye.displayVolume ? Zt.getInstance().getChart().setIndicator(2, r) : Zt.getInstance().getChart().setIndicator(2, r), $("#chart_tabbar").find("a").each(function() {
				$(this).attr("name") == r && $(this).addClass("selected")
			}), $("#chart_tabbar")[0].style.display = "block"
		} else if ("off" == t) {
			$("#chart_enable_indicator a").each(function() {
				"off" == $(this).attr("name") && $(this).addClass("selected")
			}), $("#chart_show_indicator").removeClass("selected"), Zt.getInstance().getChart().setIndicator(2, "NONE");
			var e = Be.get();
			e.charts.indicsStatus = "close", Be.save(), $("#chart_tabbar")[0].style.display = "none", $("#chart_tabbar a").removeClass("selected")
		}
		f()
	}
	function x(t) {
		if ($("#chart_container .chart_toolbar_tabgroup a").removeClass("selected"), $("#chart_toolbar_periods_vert ul a").removeClass("selected"), $("#chart_container .chart_toolbar_tabgroup a").each(function() {
			$(this).parent().attr("name") == t && $(this).addClass("selected")
		}), $("#chart_toolbar_periods_vert ul a").each(function() {
			$(this).parent().attr("name") == t && $(this).addClass("selected")
		}), Zt.getInstance().showCursor(), C(t), "line" == t) {
			Zt.getInstance().getChart().strIsLine = !0, Zt.getInstance().setChartStyle("frame0.k0", "Line"), Zt.getInstance().getChart().setCurrentPeriod("01m");
			var e = Be.get();
			return e.charts.period = t, Be.save(), void b.chartMgr.cleanDatas("frame0.k0")
		}
		Zt.getInstance().getChart().strIsLine = !1;
		var r = b.tagMapPeriod[t];
		Zt.getInstance().setChartStyle("frame0.k0", Be.get().charts.chartStyle), Zt.getInstance().getChart().setCurrentPeriod(r);
		var e = Be.get();
		e.charts.period = t, Be.save(), b.chartMgr.cleanDatas("frame0.k0")
	}
	function C(t) {
		var e = t;
		"line" != t && (e = b.periodMap[b.tagMapPeriod[t]]);
		var r = Be.get().charts.period_weight;
		for (var a in r) r[a] > r[e] && (r[a] -= 1);
		r[e] = 8, Be.save(), $("#chart_toolbar_periods_horz").find("li").each(function() {
			var t = $(this).attr("name"),
				e = t;
			"line" != t && (e = b.periodMap[b.tagMapPeriod[t]]), 0 == r[e] ? $(this).css("display", "none") : $(this).css("display", "inline-block")
		})
	}
	function P() {
		clearTimeout(Sr);
		var t = "";
		util.globalurl.isremote ? (t = util.globalurl.depth, $.get(t, function(t) {
			window._set_current_depth(t)
		}, "json"), t = util.globalurl.kline.format(util.globalurl.klineType[b.time_type]), $.get(t, function(t) {
			var e = new Array;
			e.push(t[t.length - 1]), window.onPushingResponse(b.mark_from, b.time_type, b.mark_from, e)
		}, "json"), Sr = window.setTimeout(function() {
			P()
		}, 2e3)) : (t = util.globalurl.depth + "?" + b.requestParam, $.get(t, function(t) {
			window._set_current_depth(t.depth), window.onPushingResponse(t.period.marketFrom, t.period.type, t.period.coinVol, t.period.data)
		}, "json"), Sr = window.setTimeout(function() {
			P()
		}, 5e3))
	}
	var b = {
		KLineAllData: new Object,
		KLineData: new Object,
		time_type: "2",
		mark_from: "11",
		limit: "1000",
		requestParam: "",
		periodMap: null,
		chartMgr: null,
		G_HTTP_REQUEST: null,
		TimeOutId: null,
		button_down: !1,
		coinshortName: "BTC"
	};
	b.periodMap = {
		"01w": "604800",
		"03d": "259200",
		"01d": "86400",
		"12h": "43200",
		"06h": "21600",
		"04h": "14400",
		"02h": "7200",
		"01h": "3600",
		"30m": "1800",
		"15m": "900",
		"05m": "300",
		"03m": "180",
		"01m": "60"
	}, b.tagMapPeriod = {
		"1w": "01w",
		"3d": "03d",
		"1d": "01d",
		"12h": "12h",
		"6h": "06h",
		"4h": "04h",
		"2h": "02h",
		"1h": "01h",
		"30m": "30m",
		"15m": "15m",
		"5m": "05m",
		"3m": "03m",
		"1m": "01m"
	};
	var S = 0,
		M = r();
	M.prototype.__construct = function() {
		this._handlers = []
	}, M.prototype.addHandler = function(t, e) {
		this._indexOf(t, e) < 0 && this._handlers.push({
			obj: t,
			func: e
		})
	}, M.prototype.removeHandler = function(t, e) {
		var r = this._indexOf(t, e);
		r >= 0 && this._handlers.splice(r, 1)
	}, M.prototype.raise = function(t, e) {
		var r, a, i = this._handlers,
			o = i.length;
		for (a = 0; a < o; a++) r = i[a], r.func.call(r.obj, t, e)
	}, M.prototype._indexOf = function(t, e) {
		var r, a, i = this._handlers,
			o = i.length;
		for (a = 0; a < o; a++) if (r = i[a], t == r.obj && e == r.func) return a;
		return -1
	}, String.fromFloat = function(t, e) {
		for (var r = t.toFixed(6), a = r.length - 1; a >= 0; a--) {
			if ("." == r[a]) return r.substring(0, a);
			if ("0" != r[a]) return r.substring(0, a + 1)
		}
	};
	var A = r();
	A.get = function() {
		return A.inst
	}, A.set = function(t) {
		A.inst = t
	}, A.prototype.getDataSource = function() {
		return this._ds
	}, A.prototype.setDataSource = function(t) {
		return this._ds = t
	}, A.prototype.getFirstIndex = function() {
		return this._firstIndex
	}, A.prototype.setFirstIndex = function(t) {
		return this._firstIndex = t
	};
	var I = r();
	I.prototype.__construct = function() {
		this._rid = 0
	}, I.prototype.execute = function(t) {}, I.prototype.reserve = function(t, e) {}, I.prototype.clear = function() {};
	var D = r(I),
		T = r(I),
		k = r(I),
		N = r(I),
		R = r(I);
	D.prototype.execute = function(t) {
		return A.get()._ds.getDataAt(t).open
	}, T.prototype.execute = function(t) {
		return A.get()._ds.getDataAt(t).high
	}, k.prototype.execute = function(t) {
		return A.get()._ds.getDataAt(t).low
	}, N.prototype.execute = function(t) {
		return A.get()._ds.getDataAt(t).close
	}, R.prototype.execute = function(t) {
		return A.get()._ds.getDataAt(t).volume
	};
	var O = r(I);
	O.prototype.__construct = function(t) {
		O.__super.__construct.call(this), this._value = t
	}, O.prototype.execute = function(t) {
		return this._value
	};
	var L = r(I);
	L.prototype.__construct = function(t, e, r, a) {
		L.__super.__construct.call(this), this._name = t, this._minValue = e, this._maxValue = r, this._value = this._defaultValue = a
	}, L.prototype.execute = function(t) {
		return this._value
	}, L.prototype.getMinValue = function() {
		return this._minValue
	}, L.prototype.getMaxValue = function() {
		return this._maxValue
	}, L.prototype.getDefaultValue = function() {
		return this._defaultValue
	}, L.prototype.getValue = function() {
		return this._value
	}, L.prototype.setValue = function(t) {
		0 == t ? this._value = 0 : t < this._minValue ? this._value = this._minValue : t > this._maxValue ? this._value = this._maxValue : this._value = t
	};
	var j = r(I),
		F = r(I),
		V = r(I),
		Y = r(I);
	j.prototype.__construct = function(t) {
		j.__super.__construct.call(this), this._exprA = t
	}, j.prototype.reserve = function(t, e) {
		this._rid < t && (this._rid = t, this._exprA.reserve(t, e))
	}, j.prototype.clear = function() {
		this._exprA.clear()
	}, F.prototype.__construct = function(t, e) {
		F.__super.__construct.call(this), this._exprA = t, this._exprB = e
	}, F.prototype.reserve = function(t, e) {
		this._rid < t && (this._rid = t, this._exprA.reserve(t, e), this._exprB.reserve(t, e))
	}, F.prototype.clear = function() {
		this._exprA.clear(), this._exprB.clear()
	}, V.prototype.__construct = function(t, e, r) {
		V.__super.__construct.call(this), this._exprA = t, this._exprB = e, this._exprC = r
	}, V.prototype.reserve = function(t, e) {
		this._rid < t && (this._rid = t, this._exprA.reserve(t, e), this._exprB.reserve(t, e), this._exprC.reserve(t, e))
	}, V.prototype.clear = function() {
		this._exprA.clear(), this._exprB.clear(), this._exprC.clear()
	}, Y.prototype.__construct = function(t, e, r, a) {
		Y.__super.__construct.call(this), this._exprA = t, this._exprB = e, this._exprC = r, this._exprD = a
	}, Y.prototype.reserve = function(t, e) {
		this._rid < t && (this._rid = t, this._exprA.reserve(t, e), this._exprB.reserve(t, e), this._exprC.reserve(t, e), this._exprD.reserve(t, e))
	}, Y.prototype.clear = function() {
		this._exprA.clear(), this._exprB.clear(), this._exprC.clear(), this._exprD.clear()
	};
	var B = r(j);
	B.prototype.__construct = function(t) {
		B.__super.__construct.call(this, t)
	}, B.prototype.execute = function(t) {
		return -this._exprA.execute(t)
	};
	var E = r(F),
		X = r(F),
		H = r(F),
		U = r(F);
	E.prototype.__construct = function(t, e) {
		E.__super.__construct.call(this, t, e)
	}, X.prototype.__construct = function(t, e) {
		X.__super.__construct.call(this, t, e)
	}, H.prototype.__construct = function(t, e) {
		H.__super.__construct.call(this, t, e)
	}, U.prototype.__construct = function(t, e) {
		U.__super.__construct.call(this, t, e)
	}, E.prototype.execute = function(t) {
		return this._exprA.execute(t) + this._exprB.execute(t)
	}, X.prototype.execute = function(t) {
		return this._exprA.execute(t) - this._exprB.execute(t)
	}, H.prototype.execute = function(t) {
		return this._exprA.execute(t) * this._exprB.execute(t)
	}, U.prototype.execute = function(t) {
		var e = this._exprA.execute(t),
			r = this._exprB.execute(t);
		return 0 == e ? e : 0 == r ? e > 0 ? 1e6 : -1e6 : e / r
	};
	var W = r(F),
		G = r(F),
		z = r(F),
		q = r(F),
		K = r(F);
	W.prototype.__construct = function(t, e) {
		W.__super.__construct.call(this, t, e)
	}, G.prototype.__construct = function(t, e) {
		G.__super.__construct.call(this, t, e)
	}, z.prototype.__construct = function(t, e) {
		z.__super.__construct.call(this, t, e)
	}, q.prototype.__construct = function(t, e) {
		q.__super.__construct.call(this, t, e)
	}, K.prototype.__construct = function(t, e) {
		K.__super.__construct.call(this, t, e)
	}, W.prototype.execute = function(t) {
		return this._exprA.execute(t) > this._exprB.execute(t) ? 1 : 0
	}, G.prototype.execute = function(t) {
		return this._exprA.execute(t) >= this._exprB.execute(t) ? 1 : 0
	}, z.prototype.execute = function(t) {
		return this._exprA.execute(t) < this._exprB.execute(t) ? 1 : 0
	}, q.prototype.execute = function(t) {
		return this._exprA.execute(t) <= this._exprB.execute(t) ? 1 : 0
	}, K.prototype.execute = function(t) {
		return this._exprA.execute(t) == this._exprB.execute(t) ? 1 : 0
	};
	var J = r(F);
	J.prototype.__construct = function(t, e) {
		J.__super.__construct.call(this, t, e)
	}, J.prototype.execute = function(t) {
		return Math.max(this._exprA.execute(t), this._exprB.execute(t))
	};
	var Q = r(j);
	Q.prototype.__construct = function(t) {
		Q.__super.__construct.call(this, t)
	}, Q.prototype.execute = function(t) {
		return Math.abs(this._exprA.execute(t))
	};
	var Z = r(F);
	Z.prototype.__construct = function(t, e) {
		Z.__super.__construct.call(this, t, e), this._offset = -1
	}, Z.prototype.execute = function(t) {
		if (this._offset < 0 && (this._offset = this._exprB.execute(t), this._offset < 0)) throw "offset < 0";
		if (t -= this._offset, t < 0) throw "index < 0";
		var e = this._exprA.execute(t);
		if (isNaN(e)) throw "NaN";
		return e
	};
	var tt = r(F),
		et = r(F);
	tt.prototype.__construct = function(t, e) {
		tt.__super.__construct.call(this, t, e)
	}, et.prototype.__construct = function(t, e) {
		et.__super.__construct.call(this, t, e)
	}, tt.prototype.execute = function(t) {
		return 0 != this._exprA.execute(t) && 0 != this._exprB.execute(t) ? 1 : 0
	}, et.prototype.execute = function(t) {
		return 0 != this._exprA.execute(t) || 0 != this._exprB.execute(t) ? 1 : 0
	};
	var rt = r(V);
	rt.prototype.__construct = function(t, e, r) {
		rt.__super.__construct.call(this, t, e, r)
	}, rt.prototype.execute = function(t) {
		return 0 != this._exprA.execute(t) ? this._exprB.execute(t) : this._exprC.execute(t)
	};
	var at = r(j);
	at.prototype.__construct = function(t, e) {
		at.__super.__construct.call(this, e), this._name = t, this._buf = []
	}, at.prototype.getName = function() {
		return this._name
	}, at.prototype.execute = function(t) {
		return this._buf[t]
	}, at.prototype.assign = function(t) {
		if (this._buf[t] = this._exprA.execute(t), A.get()._firstIndex >= 0 && isNaN(this._buf[t]) && !isNaN(this._buf[t - 1])) throw this._name + ".assign(" + t + "): NaN"
	}, at.prototype.reserve = function(t, e) {
		if (this._rid < t) for (var r = e; r > 0; r--) this._buf.push(NaN);
		at.__super.reserve.call(this, t, e)
	}, at.prototype.clear = function() {
		at.__super.clear.call(this), this._buf = []
	};
	var it = {
		None: 0,
		Line: 1,
		VolumeStick: 2,
		MACDStick: 3,
		SARPoint: 4
	},
		ot = r(at);
	ot.prototype.__construct = function(t, e, r, a) {
		ot.__super.__construct.call(this, t, e), this._style = void 0 === r ? it.Line : r, this._color = a
	}, ot.prototype.getStyle = function() {
		return this._style
	}, ot.prototype.getColor = function() {
		return this._color
	};
	var st = r(ot);
	st.prototype.__construct = function(t, e, r, a) {
		st.__super.__construct.call(this, t, e, r, a)
	}, st.prototype.getName = function() {
		return this._name + this._exprA.getRange()
	};
	var nt = r(F);
	nt.prototype.__construct = function(t, e) {
		nt.__super.__construct.call(this, t, e), this._range = -1, this._buf = []
	}, nt.prototype.getRange = function() {
		return this._range
	}, nt.prototype.initRange = function() {
		this._range = this._exprB.execute(0)
	}, nt.prototype.execute = function(t) {
		this._range < 0 && this.initRange();
		var e = this._buf[t].resultA = this._exprA.execute(t),
			r = this._buf[t].result = this.calcResult(t, e);
		return r
	}, nt.prototype.reserve = function(t, e) {
		if (this._rid < t) for (var r = e; r > 0; r--) this._buf.push({
			resultA: NaN,
			result: NaN
		});
		nt.__super.reserve.call(this, t, e)
	}, nt.prototype.clear = function() {
		nt.__super.clear.call(this), this._range = -1, this._buf = []
	};
	var ht = r(nt),
		ct = r(nt);
	ht.prototype.__construct = function(t, e) {
		ht.__super.__construct.call(this, t, e)
	}, ct.prototype.__construct = function(t, e) {
		ct.__super.__construct.call(this, t, e)
	}, ht.prototype.calcResult = function(t, e) {
		if (0 == this._range) return NaN;
		var r = A.get()._firstIndex;
		if (r < 0) return e;
		if (t > r) {
			for (var a = this._range, i = e, o = t - a + 1, s = Math.max(r, o); s < t; s++) {
				var n = this._buf[s];
				i < n.resultA && (i = n.resultA)
			}
			return i
		}
		return e
	}, ct.prototype.calcResult = function(t, e) {
		if (0 == this._range) return NaN;
		var r = A.get()._firstIndex;
		if (r < 0) return e;
		if (t > r) {
			for (var a = this._range, i = e, o = t - a + 1, s = Math.max(r, o); s < t; s++) {
				var n = this._buf[s];
				i > n.resultA && (i = n.resultA)
			}
			return i
		}
		return e
	};
	var lt = r(nt);
	lt.prototype.__construct = function(t, e) {
		lt.__super.__construct.call(this, t, e)
	}, lt.prototype.calcResult = function(t, e) {
		if (0 == this._range) return NaN;
		var r = A.get()._firstIndex;
		if (r < 0) return 0;
		if (t >= r) {
			var a = this._range - 1;
			a > t - r && (a = t - r);
			for (var i = 0; a >= 0; a--) 0 != this._buf[t - a].resultA && i++;
			return i
		}
		return 0
	};
	var ut = r(nt);
	ut.prototype.__construct = function(t, e) {
		ut.__super.__construct.call(this, t, e)
	}, ut.prototype.calcResult = function(t, e) {
		var r = A.get()._firstIndex;
		if (r < 0) return e;
		if (t > r) {
			var a = this._range;
			return 0 == a || a >= t + 1 - r ? this._buf[t - 1].result + e : this._buf[t - 1].result + e - this._buf[t - a].resultA
		}
		return e
	};
	var _t = r(nt);
	_t.prototype.__construct = function(t, e) {
		_t.__super.__construct.call(this, t, e)
	}, _t.prototype.calcResult = function(t, e) {
		if (0 == this._range) return NaN;
		var r = this._stdBuf[t],
			a = A.get()._firstIndex;
		if (a < 0) return r.resultMA = e, 0;
		if (t > a) {
			var i = this._range;
			i >= t + 1 - a ? (i = t + 1 - a, r.resultMA = this._stdBuf[t - 1].resultMA * (1 - 1 / i) + e / i) : r.resultMA = this._stdBuf[t - 1].resultMA + (e - this._buf[t - i].resultA) / i;
			for (var o = 0, s = t - i + 1; s <= t; s++) o += Math.pow(this._buf[s].resultA - r.resultMA, 2);
			return Math.sqrt(o / i)
		}
		return r.resultMA = e, 0
	}, _t.prototype.reserve = function(t, e) {
		if (this._rid < t) for (var r = e; r > 0; r--) this._stdBuf.push({
			resultMA: NaN
		});
		_t.__super.reserve.call(this, t, e)
	}, _t.prototype.clear = function() {
		_t.__super.clear.call(this), this._stdBuf = []
	};
	var pt = r(nt);
	pt.prototype.__construct = function(t, e) {
		pt.__super.__construct.call(this, t, e)
	}, pt.prototype.calcResult = function(t, e) {
		if (0 == this._range) return NaN;
		var r = A.get()._firstIndex;
		if (r < 0) return e;
		if (t > r) {
			var a = this._range;
			return a >= t + 1 - r ? (a = t + 1 - r, this._buf[t - 1].result * (1 - 1 / a) + e / a) : this._buf[t - 1].result + (e - this._buf[t - a].resultA) / a
		}
		return e
	};
	var dt = r(nt);
	dt.prototype.__construct = function(t, e) {
		dt.__super.__construct.call(this, t, e)
	}, dt.prototype.initRange = function() {
		dt.__super.initRange.call(this), this._alpha = 2 / (this._range + 1)
	}, dt.prototype.calcResult = function(t, e) {
		if (0 == this._range) return NaN;
		var r = A.get()._firstIndex;
		if (r < 0) return e;
		if (t > r) {
			var a = this._buf[t - 1];
			return this._alpha * (e - a.result) + a.result
		}
		return e
	};
	var gt = r(dt);
	gt.prototype.__construct = function(t, e) {
		gt.__super.__construct.call(this, t, e)
	}, gt.prototype.calcResult = function(t, e) {
		var r = A.get()._firstIndex;
		if (r < 0) return e;
		if (t > r) {
			var a = this._range,
				i = this._buf[t - 1];
			return a >= t + 1 - r ? (a = t + 1 - r, i.result * (1 - 1 / a) + e / a) : this._alpha * (e - i.result) + i.result
		}
		return e
	};
	var ft = r(nt);
	ft.prototype.__construct = function(t, e, r) {
		ft.__super.__construct.call(this, t, e), this._exprC = r, this._mul
	}, ft.prototype.initRange = function() {
		ft.__super.initRange.call(this), this._mul = this._exprC.execute(0)
	}, ft.prototype.calcResult = function(t, e) {
		if (0 == this._range) return NaN;
		var r = A.get()._firstIndex;
		if (r < 0) return e;
		if (t > r) {
			var a = this._range;
			return a > t + 1 - r && (a = t + 1 - r), ((a - 1) * this._buf[t - 1].result + e * this._mul) / a
		}
		return e
	};
	var mt = r(Y);
	mt.prototype.__construct = function(t, e, r, a) {
		mt.__super.__construct.call(this, t, e, r, a), this._buf = [], this._range = -1, this._min, this._step, this._max
	}, mt.prototype.execute = function(t) {
		this._range < 0 && (this._range = this._exprA.execute(0), this._min = this._exprB.execute(0) / 100, this._step = this._exprC.execute(0) / 100, this._max = this._exprD.execute(0) / 100);
		var e = this._buf[t],
			r = A.get(),
			a = r._firstIndex;
		if (a < 0) e.longPos = !0, e.sar = r._ds.getDataAt(t).low, e.ep = r._ds.getDataAt(t).high, e.af = .02;
		else {
			var i = r._ds.getDataAt(t).high,
				o = r._ds.getDataAt(t).low,
				s = this._buf[t - 1];
			if (e.sar = s.sar + s.af * (s.ep - s.sar), s.longPos) {
				if (e.longPos = !0, i > s.ep ? (e.ep = i, e.af = Math.min(s.af + this._step, this._max)) : (e.ep = s.ep, e.af = s.af), e.sar > o) {
					e.longPos = !1;
					var n = t - this._range + 1;
					for (n = Math.max(n, a); n < t; n++) {
						var h = r._ds.getDataAt(n).high;
						i < h && (i = h)
					}
					e.sar = i, e.ep = o, e.af = .02
				}
			} else if (e.longPos = !1, o < s.ep ? (e.ep = o, e.af = Math.min(s.af + this._step, this._max)) : (e.ep = s.ep, e.af = s.af), e.sar < i) {
				e.longPos = !0;
				var n = t - this._range + 1;
				for (n = Math.max(n, a); n < t; n++) {
					var c = r._ds.getDataAt(n).low;
					o > c && (o = c)
				}
				e.sar = o, e.ep = i, e.af = .02
			}
		}
		return e.sar
	}, mt.prototype.reserve = function(t, e) {
		if (this._rid < t) for (var r = e; r > 0; r--) this._buf.push({
			longPos: !0,
			sar: NaN,
			ep: NaN,
			af: NaN
		});
		mt.__super.reserve.call(this, t, e)
	}, mt.prototype.clear = function() {
		mt.__super.clear.call(this), this._range = -1
	};
	var yt = r();
	yt.prototype.__construct = function() {
		this._exprEnv = new A, this._rid = 0, this._params = [], this._assigns = [], this._outputs = []
	}, yt.prototype.addParameter = function(t) {
		this._params.push(t)
	}, yt.prototype.addAssign = function(t) {
		this._assigns.push(t)
	}, yt.prototype.addOutput = function(t) {
		this._outputs.push(t)
	}, yt.prototype.getParameterCount = function() {
		return this._params.length
	}, yt.prototype.getParameterAt = function(t) {
		return this._params[t]
	}, yt.prototype.getOutputCount = function() {
		return this._outputs.length
	}, yt.prototype.getOutputAt = function(t) {
		return this._outputs[t]
	}, yt.prototype.clear = function() {
		this._exprEnv.setFirstIndex(-1);
		var t, e;
		for (e = this._assigns.length, t = 0; t < e; t++) this._assigns[t].clear();
		for (e = this._outputs.length, t = 0; t < e; t++) this._outputs[t].clear()
	}, yt.prototype.reserve = function(t) {
		this._rid++;
		var e, r;
		for (r = this._assigns.length, e = 0; e < r; e++) this._assigns[e].reserve(this._rid, t);
		for (r = this._outputs.length, e = 0; e < r; e++) this._outputs[e].reserve(this._rid, t)
	}, yt.prototype.execute = function(t, e) {
		if (!(e < 0)) {
			this._exprEnv.setDataSource(t), A.set(this._exprEnv);
			try {
				var r, a;
				for (a = this._assigns.length, r = 0; r < a; r++) this._assigns[r].assign(e);
				for (a = this._outputs.length, r = 0; r < a; r++) this._outputs[r].assign(e);
				this._exprEnv.getFirstIndex() < 0 && this._exprEnv.setFirstIndex(e)
			} catch (t) {
				if (this._exprEnv.getFirstIndex() >= 0) throw console.log(t), t
			}
		}
	}, yt.prototype.getParameters = function() {
		var t, e = [],
			r = this._params.length;
		for (t = 0; t < r; t++) e.push(this._params[t].getValue());
		return e
	}, yt.prototype.setParameters = function(t) {
		if (t instanceof Array && t.length == this._params.length) for (var e in this._params) this._params[e].setValue(t[e])
	};
	var vt = r(yt);
	vt.prototype.__construct = function() {
		vt.__super.__construct.call(this);
		var t = new L("M1", 2, 1e3, 60);
		this.addParameter(t), this.addOutput(new ot("HIGH", new T, it.None)), this.addOutput(new ot("LOW", new k, it.None)), this.addOutput(new ot("CLOSE", new N, it.Line, de.Color.Indicator0)), this.addOutput(new st("MA", new pt(new N, t), it.Line, de.Color.Indicator1))
	}, vt.prototype.getName = function() {
		return "CLOSE"
	};
	var wt = r(yt);
	wt.prototype.__construct = function() {
		wt.__super.__construct.call(this);
		var t = new L("M1", 2, 1e3, 7),
			e = new L("M2", 2, 1e3, 30),
			r = new L("M3", 2, 1e3, 0),
			a = new L("M4", 2, 1e3, 0);
		this.addParameter(t), this.addParameter(e), this.addParameter(r), this.addParameter(a), this.addOutput(new st("MA", new pt(new N, t))), this.addOutput(new st("MA", new pt(new N, e))), this.addOutput(new st("MA", new pt(new N, r))), this.addOutput(new st("MA", new pt(new N, a)))
	}, wt.prototype.getName = function() {
		return "MA"
	};
	var xt = r(yt);
	xt.prototype.__construct = function() {
		xt.__super.__construct.call(this);
		var t = new L("M1", 2, 1e3, 7),
			e = new L("M2", 2, 1e3, 30),
			r = new L("M3", 2, 1e3, 0),
			a = new L("M4", 2, 1e3, 0);
		this.addParameter(t), this.addParameter(e), this.addParameter(r), this.addParameter(a), this.addOutput(new st("EMA", new dt(new N, t))), this.addOutput(new st("EMA", new dt(new N, e))), this.addOutput(new st("EMA", new dt(new N, r))), this.addOutput(new st("EMA", new dt(new N, a)))
	}, xt.prototype.getName = function() {
		return "EMA"
	};
	var Ct = r(yt);
	Ct.prototype.__construct = function() {
		Ct.__super.__construct.call(this);
		var t = new L("M1", 2, 500, 5),
			e = new L("M2", 2, 500, 10);
		this.addParameter(t), this.addParameter(e);
		var r = new ot("VOLUME", new R, it.VolumeStick, de.Color.Text4);
		this.addOutput(r), this.addOutput(new st("MA", new pt(r, t), it.Line, de.Color.Indicator0)), this.addOutput(new st("MA", new pt(r, e), it.Line, de.Color.Indicator1))
	}, Ct.prototype.getName = function() {
		return "VOLUME"
	};
	var Pt = r(yt);
	Pt.prototype.__construct = function() {
		Pt.__super.__construct.call(this);
		var t = new L("SHORT", 2, 200, 12),
			e = new L("LONG", 2, 200, 26),
			r = new L("MID", 2, 200, 9);
		this.addParameter(t), this.addParameter(e), this.addParameter(r);
		var a = new ot("DIF", new X(new dt(new N, t), new dt(new N, e)));
		this.addOutput(a);
		var i = new ot("DEA", new dt(a, r));
		this.addOutput(i);
		var o = new ot("MACD", new H(new X(a, i), new O(2)), it.MACDStick);
		this.addOutput(o)
	}, Pt.prototype.getName = function() {
		return "MACD"
	};
	var bt = r(yt);
	bt.prototype.__construct = function() {
		bt.__super.__construct.call(this);
		var t = new L("N", 2, 90, 14),
			e = new L("MM", 2, 60, 6);
		this.addParameter(t), this.addParameter(e);
		var r = new at("MTR", new gt(new J(new J(new X(new T, new k), new Q(new X(new T, new Z(new N, new O(1))))), new Q(new X(new Z(new N, new O(1)), new k))), t));
		this.addAssign(r);
		var a = new at("HD", new X(new T, new Z(new T, new O(1))));
		this.addAssign(a);
		var i = new at("LD", new X(new Z(new k, new O(1)), new k));
		this.addAssign(i);
		var o = new at("DMP", new gt(new rt(new tt(new W(a, new O(0)), new W(a, i)), a, new O(0)), t));
		this.addAssign(o);
		var s = new at("DMM", new gt(new rt(new tt(new W(i, new O(0)), new W(i, a)), i, new O(0)), t));
		this.addAssign(s);
		var n = new ot("PDI", new H(new U(o, r), new O(100)));
		this.addOutput(n);
		var h = new ot("MDI", new H(new U(s, r), new O(100)));
		this.addOutput(h);
		var c = new ot("ADX", new gt(new H(new U(new Q(new X(h, n)), new E(h, n)), new O(100)), e));
		this.addOutput(c);
		var l = new ot("ADXR", new gt(c, e));
		this.addOutput(l)
	}, bt.prototype.getName = function() {
		return "DMI"
	};
	var St = r(yt);
	St.prototype.__construct = function() {
		St.__super.__construct.call(this);
		var t = new L("N1", 2, 60, 10),
			e = new L("N2", 2, 250, 50),
			r = new L("M", 2, 100, 10);
		this.addParameter(t), this.addParameter(e), this.addParameter(r);
		var a = new ot("DIF", new X(new pt(new N, t), new pt(new N, e)));
		this.addOutput(a);
		var i = new ot("DIFMA", new pt(a, r));
		this.addOutput(i)
	}, St.prototype.getName = function() {
		return "DMA"
	};
	var Mt = r(yt);
	Mt.prototype.__construct = function() {
		Mt.__super.__construct.call(this);
		var t = new L("N", 2, 100, 12),
			e = new L("M", 2, 100, 9);
		this.addParameter(t), this.addParameter(e);
		var r = new at("MTR", new dt(new dt(new dt(new N, t), t), t));
		this.addAssign(r);
		var a = new ot("TRIX", new H(new U(new X(r, new Z(r, new O(1))), new Z(r, new O(1))), new O(100)));
		this.addOutput(a);
		var i = new ot("MATRIX", new pt(a, e));
		this.addOutput(i)
	}, Mt.prototype.getName = function() {
		return "TRIX"
	};
	var At = r(yt);
	At.prototype.__construct = function() {
		At.__super.__construct.call(this);
		var t = new L("N", 2, 120, 26);
		this.addParameter(t);
		var e = new at("REF_CLOSE_1", new Z(new N, new O(1)));
		this.addAssign(e);
		var r = new ot("BR", new H(new U(new ut(new J(new O(0), new X(new T, e)), t), new ut(new J(new O(0), new X(e, new k)), t)), new O(100)));
		this.addOutput(r);
		var a = new ot("AR", new H(new U(new ut(new X(new T, new D), t), new ut(new X(new D, new k), t)), new O(100)));
		this.addOutput(a)
	}, At.prototype.getName = function() {
		return "BRAR"
	};
	var It = r(yt);
	It.prototype.__construct = function() {
		It.__super.__construct.call(this);
		var t = new L("N", 2, 100, 26),
			e = new L("M", 2, 100, 6);
		this.addParameter(t), this.addParameter(e);
		var r = new at("REF_CLOSE_1", new Z(new N, new O(1)));
		this.addAssign(r);
		var a = new at("TH", new ut(new rt(new W(new N, r), new R, new O(0)), t));
		this.addAssign(a);
		var i = new at("TL", new ut(new rt(new z(new N, r), new R, new O(0)), t));
		this.addAssign(i);
		var o = new at("TQ", new ut(new rt(new K(new N, r), new R, new O(0)), t));
		this.addAssign(o);
		var s = new ot("VR", new H(new U(new E(new H(a, new O(2)), o), new E(new H(i, new O(2)), o)), new O(100)));
		this.addOutput(s);
		var n = new ot("MAVR", new pt(s, e));
		this.addOutput(n)
	}, It.prototype.getName = function() {
		return "VR"
	};
	var Dt = r(yt);
	Dt.prototype.__construct = function() {
		Dt.__super.__construct.call(this);
		var t = new L("M", 2, 100, 30);
		this.addParameter(t);
		var e = new at("REF_CLOSE_1", new Z(new N, new O(1)));
		this.addAssign(e);
		var r = new at("VA", new rt(new W(new N, e), new R, new B(new R)));
		this.addAssign(r);
		var a = new ot("OBV", new ut(new rt(new K(new N, e), new O(0), r), new O(0)));
		this.addOutput(a);
		var i = new ot("MAOBV", new pt(a, t));
		this.addOutput(i)
	}, Dt.prototype.getName = function() {
		return "OBV"
	};
	var Tt = r(yt);
	Tt.prototype.__construct = function() {
		Tt.__super.__construct.call(this);
		var t = new L("N", 2, 90, 14),
			e = new L("M", 2, 60, 9);
		this.addParameter(t), this.addParameter(e);
		var r = new at("VOLUME", new U(new pt(new R, t), new R));
		this.addAssign(r);
		var a = new at("MID", new H(new U(new X(new E(new T, new k), new Z(new E(new T, new k), new O(1))), new E(new T, new k)), new O(100)));
		this.addAssign(a);
		var i = new ot("EMV", new pt(new U(new H(a, new H(r, new X(new T, new k))), new pt(new X(new T, new k), t)), t));
		this.addOutput(i);
		var o = new ot("MAEMV", new pt(i, e));
		this.addOutput(o)
	}, Tt.prototype.getName = function() {
		return "EMV"
	};
	var kt = r(yt);
	kt.prototype.__construct = function() {
		kt.__super.__construct.call(this);
		var t = new L("N1", 2, 120, 6),
			e = new L("N2", 2, 250, 12),
			r = new L("N3", 2, 500, 24);
		this.addParameter(t), this.addParameter(e), this.addParameter(r);
		var a = new at("LC", new Z(new N, new O(1)));
		this.addAssign(a);
		var i = new at("CLOSE_LC", new X(new N, a));
		this.addAssign(i), this.addOutput(new ot("RSI1", new H(new U(new ft(new J(i, new O(0)), t, new O(1)), new ft(new Q(i), t, new O(1))), new O(100)))), this.addOutput(new ot("RSI2", new H(new U(new ft(new J(i, new O(0)), e, new O(1)), new ft(new Q(i), e, new O(1))), new O(100)))), this.addOutput(new ot("RSI3", new H(new U(new ft(new J(i, new O(0)), r, new O(1)), new ft(new Q(i), r, new O(1))), new O(100))))
	}, kt.prototype.getName = function() {
		return "RSI"
	};
	var Nt = r(yt);
	Nt.prototype.__construct = function() {
		Nt.__super.__construct.call(this);
		var t = new L("N", 2, 100, 10),
			e = new L("N1", 2, 100, 6);
		this.addParameter(t), this.addParameter(e);
		var r = new at("HHV", new ht(new T, t));
		this.addAssign(r);
		var a = new at("HHV1", new ht(new T, e));
		this.addAssign(a);
		var i = new at("LLV", new ct(new k, t));
		this.addAssign(i);
		var o = new at("LLV1", new ct(new k, e));
		this.addAssign(o);
		var s = new ot("WR1", new H(new U(new X(r, new N), new X(r, i)), new O(100)));
		this.addOutput(s);
		var n = new ot("WR2", new H(new U(new X(a, new N), new X(a, o)), new O(100)));
		this.addOutput(n)
	}, Nt.prototype.getName = function() {
		return "WR"
	};
	var Rt = r(yt);
	Rt.prototype.__construct = function() {
		Rt.__super.__construct.call(this);
		var t = new O(4),
			e = new O(2),
			r = new O(2),
			a = new O(20);
		this.addOutput(new ot("SAR", new mt(t, e, r, a), it.SARPoint))
	}, Rt.prototype.getName = function() {
		return "SAR"
	};
	var Ot = r(yt);
	Ot.prototype.__construct = function() {
		Ot.__super.__construct.call(this);
		var t = new L("N", 2, 90, 9),
			e = new L("M1", 2, 30, 3),
			r = new L("M2", 2, 30, 3);
		this.addParameter(t), this.addParameter(e), this.addParameter(r);
		var a = new at("HHV", new ht(new T, t));
		this.addAssign(a);
		var i = new at("LLV", new ct(new k, t));
		this.addAssign(i);
		var o = new at("RSV", new H(new U(new X(new N, i), new X(a, i)), new O(100)));
		this.addAssign(o);
		var s = new ot("K", new ft(o, e, new O(1)));
		this.addOutput(s);
		var n = new ot("D", new ft(s, r, new O(1)));
		this.addOutput(n);
		var h = new ot("J", new X(new H(s, new O(3)), new H(n, new O(2))));
		this.addOutput(h)
	}, Ot.prototype.getName = function() {
		return "KDJ"
	};
	var Lt = r(yt);
	Lt.prototype.__construct = function() {
		Lt.__super.__construct.call(this);
		var t = new L("N", 2, 120, 12),
			e = new L("M", 2, 60, 6);
		this.addParameter(t), this.addParameter(e);
		var r = new at("REF_CLOSE_N", new Z(new N, t));
		this.addAssign(r);
		var a = new ot("ROC", new H(new U(new X(new N, r), r), new O(100)));
		this.addOutput(a);
		var i = new ot("MAROC", new pt(a, e));
		this.addOutput(i)
	}, Lt.prototype.getName = function() {
		return "ROC"
	};
	var $t = r(yt);
	$t.prototype.__construct = function() {
		$t.__super.__construct.call(this);
		var t = new L("N", 2, 120, 12),
			e = new L("M", 2, 60, 6);
		this.addParameter(t), this.addParameter(e);
		var r = new ot("MTM", new X(new N, new Z(new N, t)));
		this.addOutput(r);
		var a = new ot("MTMMA", new pt(r, e));
		this.addOutput(a)
	}, $t.prototype.getName = function() {
		return "MTM"
	};
	var jt = r(yt);
	jt.prototype.__construct = function() {
		jt.__super.__construct.call(this);
		var t = new L("N", 2, 120, 20);
		this.addParameter(t);
		var e = new at("STD_CLOSE_N", new _t(new N, t));
		this.addAssign(e);
		var r = new ot("BOLL", new pt(new N, t));
		this.addOutput(r);
		var a = new ot("UB", new E(r, new H(new O(2), e)));
		this.addOutput(a);
		var i = new ot("LB", new X(r, new H(new O(2), e)));
		this.addOutput(i)
	}, jt.prototype.getName = function() {
		return "BOLL"
	};
	var Ft = r(yt);
	Ft.prototype.__construct = function() {
		Ft.__super.__construct.call(this);
		var t = new L("N", 2, 100, 12),
			e = new L("M", 2, 100, 6);
		this.addParameter(t), this.addParameter(e);
		var r = new ot("PSY", new H(new U(new lt(new W(new N, new Z(new N, new O(1))), t), t), new O(100)));
		this.addOutput(r);
		var a = new ot("PSYMA", new pt(r, e));
		this.addOutput(a)
	}, Ft.prototype.getName = function() {
		return "PSY"
	};
	var Vt = r(yt);
	Vt.prototype.__construct = function() {
		Vt.__super.__construct.call(this);
		var t = new L("N", 3, 100, 14),
			e = new L("M", 3, 100, 14),
			r = new L("P1", 2, 50, 3),
			a = new L("P2", 2, 50, 3);
		this.addParameter(t), this.addParameter(e), this.addParameter(r), this.addParameter(a);
		var i = new at("LC", new Z(new N, new O(1)));
		this.addAssign(i);
		var o = new at("CLOSE_LC", new X(new N, i));
		this.addAssign(o);
		var s = new at("RSI", new H(new U(new ft(new J(o, new O(0)), t, new O(1)), new ft(new Q(o), t, new O(1))), new O(100)));
		this.addAssign(s);
		var n = new ot("STOCHRSI", new H(new U(new pt(new X(s, new ct(s, e)), r), new pt(new X(new ht(s, e), new ct(s, e)), r)), new O(100)));
		this.addOutput(n), this.addOutput(new st("MA", new pt(n, a)))
	}, Vt.prototype.getName = function() {
		return "StochRSI"
	};
	var Yt = {
		State: {
			Uninitial: 0,
			Disable: 1,
			Enable: 2
		},
		state: 0,
		marketFrom: "",
		type: "",
		moneyType: "",
		coinVol: "",
		time: "",
		Response: function(t, e, r, a) {
			if (Yt.state == Yt.State.Enable && Yt.marketFrom == t && Yt.type == e && Yt.coinVol == r) {
				if (b.KLineData = a, 1 == Zt.getInstance().getChart()._money_type) {
					Zt.getInstance().getChart()._usd_cny_rate;
					for (var i in b.KLineData) var o = b.KLineData[i]
				}
				try {
					if (!b.chartMgr.updateData("frame0.k0", b.KLineData)) return Yt.Switch(), void Zt.getInstance().redraw("All", !1)
				} catch (t) {
					return Yt.Switch(), void Zt.getInstance().redraw("All", !1)
				}
				h(), Zt.getInstance().redraw("All", !1)
			}
		},
		Start: function(t) {
			Yt.state = Yt.State.Enable, Yt.PushFrom = t, Zt.getInstance().getChart().updateDataAndDisplay2()
		},
		Stop: function() {
			Yt.state = Yt.State.Disable, Zt.getInstance().getChart().updateDataAndDisplay2()
		},
		Switch: function() {
			if (Yt.state != Yt.State.Uninitial) if (Yt.state == Yt.State.Enable) {
				var t = Zt.getInstance().getChart();
				Yt.marketFrom = t._market_from, Yt.type = t._time, Yt.moneyType = t._money_type, Yt.coinVol = t._contract_unit, b.mark_from = Yt.marketFrom, b.time_type = Yt.type, Zt.getInstance().getChart().updateDataAndDisplay2()
			} else Zt.getInstance().getChart().updateDataAndDisplay2()
		}
	},
		Bt = r();
	Bt._future_btc_market_num = new Array("0", "-1", "-1", "-1", "-1"), Bt._future_ltc_market_num = new Array("1", "-1", "-1", "-1", "-1"), Bt.PushNameVar = {}, Bt.PushNameCon = ["spot", "this_week", "next_week", "month", "quarter"], Bt.strMarket = {
		"zh-cn": {
			"01": "当周 BTC ",
			"02": "次周 BTC ",
			"03": "全月 BTC ",
			"04": "季度 BTC ",
			11: "当周 LTC ",
			12: "次周 LTC ",
			13: "全月 LTC ",
			14: "季度 LTC "
		},
		"en-us": {
			"01": "BTC This Week ",
			"02": "BTC Next Week ",
			"03": "BTC Month ",
			"04": "BTC Quarter ",
			11: "LTC This Week ",
			12: "LTC Next Week ",
			13: "LTC Month ",
			14: "LTC Quarter "
		},
		"zh-tw": {
			"01": "當周 BTC ",
			"02": "次周 BTC ",
			"03": "全月 BTC ",
			"04": "季度 BTC ",
			11: "當周 LTC ",
			12: "次周 LTC ",
			13: "全月 LTC ",
			14: "季度 LTC "
		}
	}, Bt.strPeriod = {
		"zh-cn": {
			line: "(分时)",
			0: "(1分钟)",
			1: "(5分钟)",
			2: "(15分钟)",
			9: "(30分钟)",
			10: "(1小时)",
			3: "(日线)",
			4: "(周线)",
			7: "(3分钟)",
			11: "(2小时)",
			12: "(4小时)",
			13: "(6小时)",
			14: "(12小时)",
			15: "(3天)"
		},
		"en-us": {
			line: "(Line)",
			0: "(1m)",
			1: "(5m)",
			2: "(15m)",
			9: "(30m)",
			10: "(1h)",
			3: "(1d)",
			4: "(1w)",
			7: "(3m)",
			11: "(2h)",
			12: "(4h)",
			13: "(6h)",
			14: "(12h)",
			15: "(3d)"
		},
		"zh-tw": {
			line: "(分時)",
			0: "(1分钟)",
			1: "(5分钟)",
			2: "(15分钟)",
			9: "(30分钟)",
			10: "(1小時)",
			3: "(日线)",
			4: "(周线)",
			7: "(3分钟)",
			11: "(2小時)",
			12: "(4小時)",
			13: "(6小時)",
			14: "(12小時)",
			15: "(3天)"
		}
	}, Bt.prototype.__construct = function() {
		this._data = null, this._charStyle = "CandleStick", this._depthData = {
			array: null,
			asks_count: 0,
			bids_count: 0,
			asks_si: 0,
			asks_ei: 0,
			bids_si: 0,
			bids_ei: 0,
			depth_count: 10
		}, this._time = "900", this._market_from = "4", this._usd_cny_rate = 1, this._money_type = 0, this._contract_unit = 1, this.strIsLine = !1, this.strCurrentMarket = 20141017001, this.strCurrentMarketType = 1
	}, Bt.prototype.setTitle = function() {
		var t = Zt.getInstance().getLanguage(),
			e = "BCCoin ";
		if ("0" == this._market_from) e += "BTC";
		else if ("3" == this._market_from) e += "LTC";
		else {
			var r = i(this.strCurrentMarket).toString() + this.strCurrentMarketType.toString();
			e += Bt.strMarket[t][r], e += this.strCurrentMarket.toString().slice(4, 8)
		}
		e += " ", e += this.strIsLine ? Bt.strPeriod[t].line : Bt.strPeriod[t][this._time], Zt.getInstance().setTitle("frame0.k0", e)
	}, Bt.prototype.setCurrentList = function() {}, Bt.prototype.setKlineIndex = function(t) {
		this._market_from = t, this.updateDataAndDisplay()
	}, Bt.prototype.setCurrentCoin = function(t) {
		this._market_from = t.toString(), this.updateDataAndDisplay()
	}, Bt.prototype.setFutureList = function(t) {
		var e = $("#chart_symbols_btc").find("span"),
			r = $("#chart_symbols_btc").find("li");
		this.btc_obj = {};
		var a = $("#chart_symbols_ltc").find("span"),
			o = $("#chart_symbols_ltc").find("li");
		this.ltc_obj = {};
		for (var s = 1; s <= 4; s++) this.btc_obj[s.toString()] = {}, this.btc_obj[s.toString()].id = 0, this.btc_obj[s.toString()].type = 0, this.btc_obj[s.toString()].text = $(e[2 * s - 1]), this.btc_obj[s.toString()].obj = $(r[s - 1]), this.btc_obj[s.toString()].obj.css("display", "none"), this.btc_obj[s.toString()].obj.attr("name", "0"), this.ltc_obj[s.toString()] = {}, this.ltc_obj[s.toString()].id = 0, this.ltc_obj[s.toString()].type = 0, this.ltc_obj[s.toString()].text = $(a[2 * s - 1]), this.ltc_obj[s.toString()].obj = $(o[s - 1]), this.ltc_obj[s.toString()].obj.css("display", "none"), this.ltc_obj[s.toString()].obj.attr("name", "0");
		for (var s = 0; s < t.length; s++) {
			var n = t[s].contractID,
				h = t[s].type;
			0 == i(n) ? (this.btc_obj[h].id = n, this.btc_obj[h].type = h, this.btc_obj[h].text.html(n.toString().slice(4, 8)), this.btc_obj[h].obj.attr("name", n.toString()), this.btc_obj[h].obj.css("display", "block"), Bt._future_btc_market_num[h] = n.toString().slice(n.toString().length - 2)) : 1 == i(n) && (this.ltc_obj[h].id = n, this.ltc_obj[h].type = h, this.ltc_obj[h].text.html(n.toString().slice(4, 8)), this.ltc_obj[h].obj.attr("name", n.toString()), this.ltc_obj[h].obj.css("display", "block"), Bt._future_ltc_market_num[h] = n.toString().slice(n.toString().length - 2))
		}
		for (var s = 0; s < Bt._future_btc_market_num.length; s++) {
			var c = Bt._future_btc_market_num[s].toString();
			"-1" != c && (Bt.PushNameVar[c] = "btc_" + Bt.PushNameCon[s])
		}
		for (var s = 0; s < Bt._future_ltc_market_num.length; s++) {
			var c = Bt._future_ltc_market_num[s].toString();
			"-1" != c && (Bt.PushNameVar[c] = "ltc_" + Bt.PushNameCon[s])
		}
	}, Bt.prototype.updateDataAndDisplay = function() {
		Yt.Switch()
	}, Bt.prototype.updateDataAndDisplay2 = function() {
		b.mark_from = this._market_from, b.time_type = this._time, b.limit = "1000", this.setTitle(), Zt.getInstance().setCurrentDataSource("frame0.k0", "BCCoin." + this._market_from + "." + this._time + "." + this._money_type + "." + this._contract_unit), Zt.getInstance().setNormalMode();
		b.chartMgr.getDataSource("frame0.k0").getLastDate();
		b.requestParam = _(b.mark_from, b.time_type, "1000", null), Ir(!0), Zt.getInstance().redraw("All", !1)
	}, Bt.prototype.setCurrentFutureNoRaise = function(t) {
		$("#chart_dropdown_symbols li a").removeClass("selected");
		var e = t,
			r = 0,
			a = $("#chart_symbols_btc").find("li"),
			i = $("#chart_symbols_ltc").find("li");
		a.each(function() {
			var t = $(this).attr("name");
			e.toString() == t && ($("#chart_dropdown_symbols .chart_dropdown_t").html($(this).html()), $(this).find("a").addClass("selected"))
		}), i.each(function() {
			var t = $(this).attr("name");
			e.toString() == t && ($("#chart_dropdown_symbols .chart_dropdown_t").html($(this).html()), $(this).find("a").addClass("selected"))
		});
		for (var o in this.btc_obj) if (this.btc_obj[o].id == e) {
			r = this.ltc_obj[o].type, this._market_from = Bt._future_btc_market_num[this.btc_obj[o].type], this.strCurrentMarket = e, this.strCurrentMarketType = this.btc_obj[o].type, this.setTitle();
			break
		}
		for (var o in this.ltc_obj) if (this.ltc_obj[o].id == e) {
			r = this.ltc_obj[o].type, this._market_from = Bt._future_ltc_market_num[this.ltc_obj[o].type], this.strCurrentMarket = e, this.strCurrentMarketType = this.ltc_obj[o].type, this.setTitle();
			break
		}
		this.updateDataAndDisplay()
	}, Bt.prototype.setCurrentFuture = function(t) {
		$("#chart_dropdown_symbols li a").removeClass("selected");
		var e = t,
			r = 0,
			a = $("#chart_symbols_btc").find("li"),
			i = $("#chart_symbols_ltc").find("li");
		a.each(function() {
			var t = $(this).attr("name");
			e.toString() == t && ($("#chart_dropdown_symbols .chart_dropdown_t").html($(this).html()), $(this).find("a").addClass("selected"))
		}), i.each(function() {
			var t = $(this).attr("name");
			e.toString() == t && ($("#chart_dropdown_symbols .chart_dropdown_t").html($(this).html()), $(this).find("a").addClass("selected"))
		});
		for (var o in this.btc_obj) if (this.btc_obj[o].id == e) {
			r = this.ltc_obj[o].type, this._market_from = Bt._future_btc_market_num[this.btc_obj[o].type], this.strCurrentMarket = e, this.strCurrentMarketType = this.btc_obj[o].type, this.setTitle();
			break
		}
		for (var o in this.ltc_obj) if (this.ltc_obj[o].id == e) {
			r = this.ltc_obj[o].type, this._market_from = Bt._future_ltc_market_num[this.ltc_obj[o].type], this.strCurrentMarket = e, this.strCurrentMarketType = this.ltc_obj[o].type, this.setTitle();
			break
		}
		var s = {};
		s.command = "set current future", s.content = e, $("#chart_output_interface_text").val(JSON.stringify(s)), $("#chart_output_interface_submit").submit(), window._current_future_change.raise(e, r), this.updateDataAndDisplay()
	}, Bt.prototype.setCurrentContractUnit = function(t) {
		this._contract_unit = t, this.updateDataAndDisplay()
	}, Bt.prototype.setCurrentMoneyType = function(t) {
		if ("usd" == t) {
			if (0 == this._money_type) return;
			this._money_type = 0
		} else if ("cny" == t) {
			if (1 == this._money_type) return;
			this._money_type = 1
		}
		this.updateDataAndDisplay()
	}, Bt.prototype.setCurrentPeriod = function(t) {
		this._time = b.periodMap[t], this.updateDataAndDisplay()
	}, Bt.prototype.updateDataSource = function(t) {
		this._data = t, Zt.getInstance().updateData("frame0.k0", this._data)
	}, Bt.prototype.updateDepth = function(t) {
		if (null != t && t.asks && t.bids && "" != t.asks && "" != t.bids) {
			var e = this._depthData;
			e.array = [];
			for (var r = 0; r < t.asks.length; r++) {
				var a = {};
				a.rate = t.asks[r][0], a.amount = t.asks[r][1], e.array.push(a)
			}
			for (var r = 0; r < t.bids.length; r++) {
				var a = {};
				a.rate = t.bids[r][0], a.amount = t.bids[r][1], e.array.push(a)
			}
			e.asks_count = t.asks.length, e.bids_count = t.bids.length, e.asks_si = e.asks_count - 1, e.asks_ei = 0, e.bids_si = e.asks_count, e.bids_ei = e.asks_count + e.bids_count - 1;
			for (var r = e.asks_si; r >= e.asks_ei; r--) r == e.asks_si ? e.array[r].amounts = e.array[r].amount : e.array[r].amounts = e.array[r + 1].amounts + e.array[r].amount;
			for (var r = e.bids_si; r <= e.bids_ei; r++) r == e.bids_si ? e.array[r].amounts = e.array[r].amount : e.array[r].amounts = e.array[r - 1].amounts + e.array[r].amount;
			Zt.getInstance().redraw("All", !1)
		}
	}, Bt.prototype.setMainIndicator = function(t) {
		this._mainIndicator = t, "NONE" == t ? Zt.getInstance().removeMainIndicator("frame0.k0") : Zt.getInstance().setMainIndicator("frame0.k0", t), Zt.getInstance().redraw("All", !0)
	}, Bt.prototype.setIndicator = function(t, e) {
		if ("NONE" == e) {
			var t = 2;
			0 == ye.displayVolume && (t = 1);
			var r = Zt.getInstance().getIndicatorAreaName("frame0.k0", t);
			"" != r && Zt.getInstance().removeIndicator(r)
		} else {
			var t = 2;
			0 == ye.displayVolume && (t = 1);
			var r = Zt.getInstance().getIndicatorAreaName("frame0.k0", t);
			"" == r ? ye.createIndicatorChartComps("frame0.k0", e) : Zt.getInstance().setIndicator(r, e)
		}
		Zt.getInstance().redraw("All", !0)
	}, Bt.prototype.addIndicator = function(t) {
		Zt.getInstance().addIndicator(t), Zt.getInstance().redraw("All", !0)
	}, Bt.prototype.removeIndicator = function(t) {
		var e = Zt.getInstance().getIndicatorAreaName(2);
		Zt.getInstance().removeIndicator(e), Zt.getInstance().redraw("All", !0)
	};
	var Et = r();
	Et.prototype.__construct = function(t) {
		if (this._names = [], this._comps = [], t instanceof Et) this._names = t._names, this._comps = t._comps;
		else {
			var e = t.split("."),
				r = e.length - 1;
			if (r > 0) {
				this._comps = e, this._names.push(e[0]);
				for (var a = 1; a <= r; a++) this._names.push(this._names[a - 1] + "." + e[a])
			} else this._comps.push(t), this._names.push(t)
		}
	}, Et.prototype.getCompAt = function(t) {
		return t >= 0 && t < this._comps.length ? this._comps[t] : ""
	}, Et.prototype.getName = function(t) {
		if (t < 0) {
			if (this._names.length > 0) return this._names[this._names.length - 1]
		} else if (t < this._names.length) return this._names[t];
		return ""
	};
	var Xt = r();
	Xt.prototype.__construct = function(t) {
		this._name = t, this._nameObj = new Et(t)
	}, Xt.prototype.getFrameName = function() {
		return this._nameObj.getName(0)
	}, Xt.prototype.getDataSourceName = function() {
		return this._nameObj.getName(1)
	}, Xt.prototype.getAreaName = function() {
		return this._nameObj.getName(2)
	}, Xt.prototype.getName = function() {
		return this._nameObj.getName(-1)
	}, Xt.prototype.getNameObject = function() {
		return this._nameObj
	};
	var Ht = r(Xt);
	Ht.prototype.__construct = function(t) {
		Ht.__super.__construct.call(this, t), this._left = 0, this._top = 0, this._right = 0, this._bottom = 0, this._changed = !1, this._highlighted = !1, this._pressed = !1, this._selected = !1, this.Measuring = new M
	}, Ht.DockStyle = {
		Left: 0,
		Top: 1,
		Right: 2,
		Bottom: 3,
		Fill: 4
	}, Ht.prototype.getDockStyle = function() {
		return this._dockStyle
	}, Ht.prototype.setDockStyle = function(t) {
		this._dockStyle = t
	}, Ht.prototype.getLeft = function() {
		return this._left
	}, Ht.prototype.getTop = function() {
		return this._top
	}, Ht.prototype.setTop = function(t) {
		this._top != t && (this._top = t, this._changed = !0)
	}, Ht.prototype.getRight = function() {
		return this._right
	}, Ht.prototype.getBottom = function() {
		return this._bottom
	}, Ht.prototype.setBottom = function(t) {
		this._bottom != t && (this._bottom = t, this._changed = !0)
	}, Ht.prototype.getCenter = function() {
		return this._left + this._right >> 1
	}, Ht.prototype.getMiddle = function() {
		return this._top + this._bottom >> 1
	}, Ht.prototype.getWidth = function() {
		return this._right - this._left
	}, Ht.prototype.getHeight = function() {
		return this._bottom - this._top
	}, Ht.prototype.getRect = function() {
		return {
			X: this._left,
			Y: this._top,
			Width: this._right - this._left,
			Height: this._bottom - this._top
		}
	}, Ht.prototype.contains = function(t, e) {
		return t >= this._left && t < this._right && e >= this._top && e < this._bottom ? [this] : null
	}, Ht.prototype.getMeasuredWidth = function() {
		return this._measuredWidth
	}, Ht.prototype.getMeasuredHeight = function() {
		return this._measuredHeight
	}, Ht.prototype.setMeasuredDimension = function(t, e) {
		this._measuredWidth = t, this._measuredHeight = e
	}, Ht.prototype.measure = function(t, e, r) {
		this._measuredWidth = 0, this._measuredHeight = 0, this.Measuring.raise(this, {
			Width: e,
			Height: r
		}), 0 == this._measuredWidth && 0 == this._measuredHeight && this.setMeasuredDimension(e, r)
	}, Ht.prototype.layout = function(t, e, r, a, i) {
		t <<= 0, this._left != t && (this._left = t, this._changed = !0), e <<= 0, this._top != e && (this._top = e, this._changed = !0), r <<= 0, this._right != r && (this._right = r, this._changed = !0), a <<= 0, this._bottom != a && (this._bottom = a, this._changed = !0), i && (this._changed = !0)
	}, Ht.prototype.isChanged = function() {
		return this._changed
	}, Ht.prototype.setChanged = function(t) {
		this._changed = t
	}, Ht.prototype.isHighlighted = function() {
		return this._highlighted
	}, Ht.prototype.getHighlightedArea = function() {
		return this._highlighted ? this : null
	}, Ht.prototype.highlight = function(t) {
		return this._highlighted = this == t, this._highlighted ? this : null
	}, Ht.prototype.isPressed = function() {
		return this._pressed
	}, Ht.prototype.setPressed = function(t) {
		this._pressed = t
	}, Ht.prototype.isSelected = function() {
		return this._selected
	}, Ht.prototype.getSelectedArea = function() {
		return this._selected ? this : null
	}, Ht.prototype.select = function(t) {
		return this._selected = this == t, this._selected ? this : null
	}, Ht.prototype.onMouseMove = function(t, e) {
		return null
	}, Ht.prototype.onMouseLeave = function(t, e) {}, Ht.prototype.onMouseDown = function(t, e) {
		return null
	}, Ht.prototype.onMouseUp = function(t, e) {
		return null
	};
	var Ut = r(Ht);
	Ut.prototype.__construct = function(t) {
		Ut.__super.__construct.call(this, t), this._dragStarted = !1, this._oldX = 0, this._oldY = 0, this._passMoveEventToToolManager = !0
	}, Ut.prototype.onMouseMove = function(t, e) {
		var r = Zt.getInstance();
		if (r._capturingMouseArea == this && 0 == this._dragStarted && (Math.abs(this._oldX - t) > 1 || Math.abs(this._oldY - e) > 1) && (this._dragStarted = !0), this._dragStarted) return r.hideCursor(), r.onToolMouseDrag(this.getFrameName(), t, e) ? this : (r.getTimeline(this.getDataSourceName()).move(t - this._oldX), this);
		if (this._passMoveEventToToolManager && r.onToolMouseMove(this.getFrameName(), t, e)) return r.hideCursor(), this;
		switch (r._drawingTool) {
		case Zt.DrawingTool.Cursor:
			r.showCursor();
			break;
		case Zt.DrawingTool.CrossCursor:
			r.showCrossCursor(this, t, e) ? r.hideCursor() : r.showCursor();
			break;
		default:
			r.hideCursor()
		}
		return this
	}, Ut.prototype.onMouseLeave = function(t, e) {
		this._dragStarted = !1, this._passMoveEventToToolManager = !0
	}, Ut.prototype.onMouseDown = function(t, e) {
		var r = Zt.getInstance();
		return r.getTimeline(this.getDataSourceName()).startMove(), this._oldX = t, this._oldY = e, this._dragStarted = !1, r.onToolMouseDown(this.getFrameName(), t, e) && (this._passMoveEventToToolManager = !1), this
	}, Ut.prototype.onMouseUp = function(t, e) {
		var r = Zt.getInstance(),
			a = null;
		return this._dragStarted && (this._dragStarted = !1, a = this), r.onToolMouseUp(this.getFrameName(), t, e) && (a = this), this._passMoveEventToToolManager = !0, a
	};
	var Wt = r(Ht);
	Wt.prototype.__construct = function(t) {
		Wt.__super.__construct.call(this, t), this._dragStarted = !1, this._oldX = 0, this._oldY = 0
	}, Wt.prototype.onMouseMove = function(t, e) {
		var r = Zt.getInstance();
		if (r._capturingMouseArea == this && 0 == this._dragStarted && (this._oldX == t && this._oldY == e || (this._dragStarted = !0)), this._dragStarted) return r.hideCursor(), r.getTimeline(this.getDataSourceName()).move(t - this._oldX), this;
		switch (r._drawingTool) {
		case Zt.DrawingTool.CrossCursor:
			r.showCrossCursor(this, t, e) ? r.hideCursor() : r.showCursor();
			break;
		default:
			r.showCursor()
		}
		return this
	}, Wt.prototype.onMouseLeave = function(t, e) {
		this._dragStarted = !1
	}, Wt.prototype.onMouseDown = function(t, e) {
		var r = Zt.getInstance();
		return r.getTimeline(this.getDataSourceName()).startMove(), this._oldX = t, this._oldY = e, this._dragStarted = !1, this
	}, Wt.prototype.onMouseUp = function(t, e) {
		return this._dragStarted ? (this._dragStarted = !1, this) : null
	};
	var Gt = r(Ht);
	Gt.prototype.__construct = function(t) {
		Gt.__super.__construct.call(this, t)
	}, Gt.prototype.onMouseMove = function(t, e) {
		return Zt.getInstance().showCursor(), this
	};
	var zt = r(Ht);
	zt.prototype.__construct = function(t) {
		zt.__super.__construct.call(this, t)
	}, zt.prototype.onMouseMove = function(t, e) {
		return Zt.getInstance().showCursor(), this
	};
	var qt = r(Ht);
	qt.prototype.__construct = function(t) {
		qt.__super.__construct.call(this, t)
	}, qt.prototype.onMouseMove = function(t, e) {
		return Zt.getInstance().showCursor(), this
	};
	var Kt = r(Ht);
	Kt.prototype.__construct = function(t) {
		Kt.__super.__construct.call(this, t), this._areas = [], this._highlightedArea = null, this._selectedArea = null
	}, Kt.prototype.contains = function(t, e) {
		var r, a, i, o = this._areas.length;
		for (i = 0; i < o; i++) if (a = this._areas[i], r = a.contains(t, e), null != r) return r.push(this), r;
		return Kt.__super.contains(t, e)
	}, Kt.prototype.getAreaCount = function() {
		return this._areas.length
	}, Kt.prototype.getAreaAt = function(t) {
		return t < 0 || t >= this._areas.length ? null : this._areas[t]
	}, Kt.prototype.addArea = function(t) {
		this._areas.push(t)
	}, Kt.prototype.removeArea = function(t) {
		var e, r = this._areas.length;
		for (e = 0; e < r; e++) if (t == this._areas[e]) {
			this._areas.splice(e), this.setChanged(!0);
			break
		}
	}, Kt.prototype.getGridColor = function() {
		return this._gridColor
	}, Kt.prototype.setGridColor = function(t) {
		this._gridColor = t
	}, Kt.prototype.getHighlightedArea = function() {
		return null != this._highlightedArea ? this._highlightedArea.getHighlightedArea() : null
	}, Kt.prototype.highlight = function(t) {
		this._highlightedArea = null;
		var e, r, a = this._areas.length;
		for (r = 0; r < a; r++) if (e = this._areas[r].highlight(t), null != e) return this._highlightedArea = e, this;
		return null
	}, Kt.prototype.getSelectedArea = function() {
		return null != this._selectedArea ? this._selectedArea.getSelectedArea() : null
	}, Kt.prototype.select = function(t) {
		this._selectedArea = null;
		var e, r, a = this._areas.length;
		for (r = 0; r < a; r++) if (e = this._areas[r].select(t), null != e) return this._selectedArea = e, this;
		return null
	}, Kt.prototype.onMouseLeave = function(t, e) {
		var r, a = this._areas.length;
		for (r = 0; r < a; r++) this._areas[r].onMouseLeave(t, e)
	}, Kt.prototype.onMouseUp = function(t, e) {
		var r, a, i = this._areas.length;
		for (a = 0; a < i; a++) if (r = this._areas[a].onMouseUp(t, e), null != r) return r;
		return null
	};
	var Jt = r(Kt);
	Jt.prototype.__construct = function(t) {
		Jt.__super.__construct.call(this, t), this._nextRowId = 0, this._focusedRowIndex = -1
	}, Jt.prototype.getNextRowId = function() {
		return this._nextRowId++
	}, Jt.prototype.measure = function(t, e, r) {
		this.setMeasuredDimension(e, r);
		var a, i, o, s, n = 0,
			h = 0,
			c = [],
			l = this._areas.length;
		for (s = 0; s < l; s += 2) {
			if (a = this._areas[s].getHeight(), 0 == a) {
				if (0 == s) {
					o = l + 1 >> 1;
					var u = 2 * o + 5,
						_ = r / u * 2 << 0;
					for (i = r, s = o - 1; s > 0; s--) c.unshift(_), i -= _;
					c.unshift(i);
					break
				}
				a = 2 == s ? n / 3 : n
			}
			h += a, n = a, c.push(a)
		}
		if (h > 0) {
			var p = r / h;
			for (o = l + 1 >> 1, i = r, s = o - 1; s > 0; s--) c[s] *= p, i -= c[s];
			c[0] = i
		}
		var d = 8,
			g = 64,
			f = Math.min(240, e >> 1),
			m = g,
			y = Zt.getInstance(),
			v = y.getTimeline(this.getDataSourceName());
		if (v.getFirstIndex() >= 0) {
			var w = [];
			for (m = g; m < f; m += d) w.push(v.calcFirstIndex(v.calcColumnCount(e - m)));
			var x, C, P = v.getLastIndex(),
				b = [".main", ".secondary"],
				S = new Array(w.length);
			for (x = 0, C = 0, m = g; x < this._areas.length && C < w.length; x += 2) {
				var M = this._areas[x],
					A = y.getPlotter(M.getName() + "Range.main");
				for (var I in b) {
					var D = y.getDataProvider(M.getName() + b[I]);
					if (void 0 != D) for (D.calcRange(w, P, S, null); C < w.length;) {
						var T = A.getRequiredWidth(t, S[C].min),
							k = A.getRequiredWidth(t, S[C].max);
						if (Math.max(T, k) < m) break;
						C++, m += d
					}
				}
			}
		}
		for (s = 1; s < this._areas.length; s += 2) this._areas[s].measure(t, m, c[s >> 1]);
		var N = e - m;
		for (s = 0; s < this._areas.length; s += 2) this._areas[s].measure(t, N, c[s >> 1])
	}, Jt.prototype.layout = function(t, e, r, a, i) {
		if (Jt.__super.layout.call(this, t, e, r, a, i), !(this._areas.length < 1)) {
			var o, s, n = t + this._areas[0].getMeasuredWidth(),
				h = e;
			i || (i = this.isChanged());
			var c, l = this._areas.length;
			for (c = 0; c < l; c++) o = this._areas[c], s = h + o.getMeasuredHeight(), o.layout(t, h, n, s, i), c++, o = this._areas[c], o.layout(n, h, this.getRight(), s, i), h = s;
			this.setChanged(!1)
		}
	}, Jt.prototype.drawGrid = function(t) {
		if (!(this._areas.length < 1)) {
			var e = Zt.getInstance(),
				r = e.getTheme(this.getFrameName());
			t.fillStyle = r.getColor(de.Color.Grid1), t.fillRect(this._areas[0].getRight(), this.getTop(), 1, this.getHeight());
			var a, i = this._areas.length - 2;
			for (a = 0; a < i; a += 2) t.fillRect(this.getLeft(), this._areas[a].getBottom(), this.getWidth(), 1);
			if (!e.getCaptureMouseWheelDirectly()) for (a = 0, i += 2; a < i; a += 2) if (this._areas[a].isSelected()) {
				t.strokeStyle = r.getColor(de.Color.Indicator1), t.strokeRect(this.getLeft() + .5, this.getTop() + .5, this.getWidth() - 1, this.getHeight() - 1);
				break
			}
		}
	}, Jt.prototype.highlight = function(t) {
		this._highlightedArea = null;
		var e, r, a = this._areas.length;
		for (r = 0; r < a; r++) e = this._areas[r], e == t ? (r &= -2, e = this._areas[r], e.highlight(e), this._highlightedArea = e, r++, e = this._areas[r], e.highlight(null), e.highlight(e)) : e.highlight(null);
		return null != this._highlightedArea ? this : null
	}, Jt.prototype.select = function(t) {
		this._selectedArea = null;
		var e, r, a = this._areas.length;
		for (r = 0; r < a; r++) e = this._areas[r], e == t ? (r &= -2, e = this._areas[r], e.select(e), this._selectedArea = e, r++, e = this._areas[r], e.select(e)) : e.select(null);
		return null != this._selectedArea ? this : null
	}, Jt.prototype.onMouseMove = function(t, e) {
		if (this._focusedRowIndex >= 0) {
			var r = this._areas[this._focusedRowIndex],
				a = this._areas[this._focusedRowIndex + 2],
				i = e - this._oldY;
			if (0 == i) return this;
			var o = this._oldUpperBottom + i,
				s = this._oldLowerTop + i;
			return o - r.getTop() >= 60 && a.getBottom() - s >= 60 && (r.setBottom(o), a.setTop(s)), this
		}
		var n, h = this._areas.length - 2;
		for (n = 0; n < h; n += 2) {
			var c = this._areas[n].getBottom();
			if (e >= c - 4 && e < c + 4) return Zt.getInstance().showCursor("n-resize"), this
		}
		return null
	}, Jt.prototype.onMouseLeave = function(t, e) {
		this._focusedRowIndex = -1
	}, Jt.prototype.onMouseDown = function(t, e) {
		var r, a = this._areas.length - 2;
		for (r = 0; r < a; r += 2) {
			var i = this._areas[r].getBottom();
			if (e >= i - 4 && e < i + 4) return this._focusedRowIndex = r, this._oldY = e, this._oldUpperBottom = i, this._oldLowerTop = this._areas[r + 2].getTop(), this
		}
		return null
	}, Jt.prototype.onMouseUp = function(t, e) {
		if (this._focusedRowIndex >= 0) {
			this._focusedRowIndex = -1;
			var r, a = this._areas.length,
				i = [];
			for (r = 0; r < a; r += 2) i.push(this._areas[r].getHeight());
			Be.get().charts.areaHeight = i, Be.save()
		}
		return this
	};
	var Qt = r(Kt);
	Qt.prototype.__construct = function(t) {
		Qt.__super.__construct.call(this, t)
	}, Qt.prototype.measure = function(t, e, r) {
		Qt.__super.measure.call(this, t, e, r), e = this.getMeasuredWidth(), r = this.getMeasuredHeight();
		for (var a in this._areas) {
			var i = this._areas[a];
			switch (i.measure(t, e, r), i.getDockStyle()) {
			case Ht.DockStyle.left:
			case Ht.DockStyle.Right:
				e -= i.getMeasuredWidth();
				break;
			case Ht.DockStyle.Top:
			case Ht.DockStyle.Bottom:
				r -= i.getMeasuredHeight();
				break;
			case Ht.DockStyle.Fill:
				e = 0, r = 0
			}
		}
	}, Qt.prototype.layout = function(t, e, r, a, i) {
		Qt.__super.layout.call(this, t, e, r, a, i), t = this.getLeft(), e = this.getTop(), r = this.getRight(), a = this.getBottom();
		var o, s;
		i || (i = this.isChanged());
		for (var n in this._areas) {
			var h = this._areas[n];
			switch (h.getDockStyle()) {
			case Ht.DockStyle.left:
				o = h.getMeasuredWidth(), h.layout(t, e, t + o, a, i), t += o;
				break;
			case Ht.DockStyle.Top:
				s = h.getMeasuredHeight(), h.layout(t, e, r, e + s, i), e += s;
				break;
			case Ht.DockStyle.Right:
				o = h.getMeasuredWidth(), h.layout(r - o, e, r, a, i), r -= o;
				break;
			case Ht.DockStyle.Bottom:
				s = h.getMeasuredHeight(), h.layout(t, a - s, r, a, i), a -= s;
				break;
			case Ht.DockStyle.Fill:
				h.layout(t, e, r, a, i), t = r, e = a
			}
		}
		this.setChanged(!1)
	}, Qt.prototype.drawGrid = function(t) {
		var e = Zt.getInstance(),
			r = e.getTheme(this.getFrameName()),
			a = this.getLeft(),
			i = this.getTop(),
			o = this.getRight(),
			s = this.getBottom();
		t.fillStyle = r.getColor(this._gridColor);
		for (var n in this._areas) {
			var h = this._areas[n];
			switch (h.getDockStyle()) {
			case Ht.DockStyle.Left:
				t.fillRect(h.getRight(), i, 1, s - i), a += h.getWidth();
				break;
			case Ht.DockStyle.Top:
				t.fillRect(a, h.getBottom(), o - a, 1), i += h.getHeight();
				break;
			case Ht.DockStyle.Right:
				t.fillRect(h.getLeft(), i, 1, s - i), o -= h.getWidth();
				break;
			case Ht.DockStyle.Bottom:
				t.fillRect(a, h.getTop(), o - a, 1), s -= h.getHeight()
			}
		}
	};
	var Zt = r();
	Zt.DrawingTool = {
		Cursor: 0,
		CrossCursor: 1,
		DrawLines: 2,
		DrawFibRetrace: 3,
		DrawFibFans: 4,
		SegLine: 5,
		StraightLine: 6,
		ArrowLine: 7,
		RayLine: 8,
		HoriStraightLine: 9,
		HoriRayLine: 10,
		HoriSegLine: 11,
		VertiStraightLine: 12,
		PriceLine: 13,
		BiParallelLine: 14,
		BiParallelRayLine: 15,
		TriParallelLine: 16,
		BandLine: 17
	}, Zt._instance = null, Zt.getInstance = function() {
		return null == Zt._instance && (Zt._instance = new Zt), Zt._instance
	}, Zt.prototype.__construct = function() {
		this._dataSources = {}, this._dataSourceCache = {}, this._dataProviders = {}, this._frames = {}, this._areas = {}, this._timelines = {}, this._ranges = {}, this._plotters = {}, this._themes = {}, this._titles = {}, this._frameMousePos = {}, this._dsChartStyle = {}, this._dragStarted = !1, this._oldX = 0, this._fakeIndicators = {}, this._captureMouseWheelDirectly = !0, this._chart = {}, this._chart.defaultFrame = new Bt, this._drawingTool = Zt.DrawingTool.CrossCursor, this._beforeDrawingTool = this._drawingTool, this._language = "zh-cn", this._mainCanvas = null, this._overlayCanvas = null, this._mainContext = null, this._overlayContext = null
	}, Zt.prototype.redraw = function(t, e) {
		(void 0 == t || e) && (t = "All"), "All" != t && "MainCanvas" != t || (e && this.getFrame("frame0").setChanged(!0), this.layout(this._mainContext, "frame0", 0, 0, this._mainCanvas.width, this._mainCanvas.height), this.drawMain("frame0", this._mainContext)), "All" != t && "OverlayCanvas" != t || (this._overlayContext.clearRect(0, 0, this._overlayCanvas.width, this._overlayCanvas.height), this.drawOverlay("frame0", this._overlayContext))
	}, Zt.prototype.bindCanvas = function(t, e) {
		"main" == t ? (this._mainCanvas = e, this._mainContext = e.getContext("2d")) : "overlay" == t && (this._overlayCanvas = e, this._overlayContext = e.getContext("2d"), this._captureMouseWheelDirectly && $(this._overlayCanvas).bind("mousewheel", m))
	}, Zt.prototype.getCaptureMouseWheelDirectly = function() {
		return this._captureMouseWheelDirectly
	}, Zt.prototype.setCaptureMouseWheelDirectly = function(t) {
		this._captureMouseWheelDirectly = t, t ? $(this._overlayCanvas).bind("mousewheel", m) : $(this._overlayCanvas).unbind("mousewheel")
	}, Zt.prototype.getChart = function(t) {
		return this._chart.defaultFrame
	}, Zt.prototype.init = function() {
		delete this._ranges["frame0.k0.indic1"], delete this._ranges["frame0.k0.indic1Range"], delete this._areas["frame0.k0.indic1"], delete this._areas["frame0.k0.indic1Range"], ve.loadTemplate("frame0.k0", "BCCoin", "frame0.order", "0.order", "frame0.trade", "0.trade"), this.redraw("All", !0)
	}, Zt.prototype.setCurrentDrawingTool = function(t) {
		this._drawingTool = Zt.DrawingTool[t], this.setRunningMode(this._drawingTool)
	}, Zt.prototype.getLanguage = function() {
		return this._language
	}, Zt.prototype.setLanguage = function(t) {
		this._language = t
	}, Zt.prototype.setThemeName = function(t, e) {
		void 0 == e && (e = "Dark");
		var r;
		switch (e) {
		case "Light":
			r = new fe;
			break;
		default:
			e = "Dark", r = new ge
		}
		this._themeName = e, this.setTheme(t, r), this.getFrame(t).setChanged(!0)
	}, Zt.prototype.getChartStyle = function(t) {
		var e = this._dsChartStyle[t];
		return void 0 == e ? "CandleStick" : e
	}, Zt.prototype.setChartStyle = function(t, e) {
		if (this._dsChartStyle[t] != e) {
			var r, i, o = t + ".main",
				s = o + ".main",
				n = o + ".main";
			switch (e) {
			case "CandleStick":
			case "CandleStickHLC":
			case "OHLC":
				switch (r = this.getDataProvider(s), void 0 != r && a(r, le) || (r = new le(s), this.setDataProvider(s, r), r.updateData()), this.setMainIndicator(t, Be.get().charts.mIndic), e) {
				case "CandleStick":
					i = new Me(n);
					break;
				case "CandleStickHLC":
					i = new Ae(n);
					break;
				case "OHLC":
					i = new Ie(n)
				}
				this.setPlotter(n, i), i = new Ne(o + ".decoration"), this.setPlotter(i.getName(), i);
				break;
			case "Line":
				r = new ue(s), this.setDataProvider(r.getName(), r), r.setIndicator(new vt), this.removeMainIndicator(t), i = new Te(n), this.setPlotter(n, i), this.removePlotter(o + ".decoration")
			}
			this.getArea(i.getAreaName()).setChanged(!0), this._dsChartStyle[t] = e
		}
	}, Zt.prototype.setNormalMode = function() {
		this._drawingTool = this._beforeDrawingTool, $(".chart_dropdown_data").removeClass("chart_dropdown-hover"), $("#chart_toolpanel .chart_toolpanel_button").removeClass("selected"), $("#chart_CrossCursor").parent().addClass("selected"), this._drawingTool == Zt.DrawingTool.Cursor ? (this.showCursor(), $("#mode a").removeClass("selected"), $("#chart_toolpanel .chart_toolpanel_button").removeClass("selected"), $("#chart_Cursor").parent().addClass("selected")) : this.hideCursor()
	}, Zt.prototype.setRunningMode = function(t) {
		var e = this.getDataSource("frame0.k0"),
			r = e.getCurrentToolObject();
		if (null != r && r.state != Xe.state.AfterDraw && e.delToolObject(), e.getToolObjectCount() > 10) return void this.setNormalMode();
		switch (this._drawingTool = t, t == Zt.DrawingTool.Cursor && this.showCursor(), t) {
		case Zt.DrawingTool.Cursor:
			this._beforeDrawingTool = t;
			break;
		case Zt.DrawingTool.ArrowLine:
			e.addToolObject(new ir("frame0.k0"));
			break;
		case Zt.DrawingTool.BandLine:
			e.addToolObject(new He("frame0.k0"));
			break;
		case Zt.DrawingTool.BiParallelLine:
			e.addToolObject(new Ue("frame0.k0"));
			break;
		case Zt.DrawingTool.BiParallelRayLine:
			e.addToolObject(new We("frame0.k0"));
			break;
		case Zt.DrawingTool.CrossCursor:
			this._beforeDrawingTool = t;
			break;
		case Zt.DrawingTool.DrawFibFans:
			e.addToolObject(new Ge("frame0.k0"));
			break;
		case Zt.DrawingTool.DrawFibRetrace:
			e.addToolObject(new ze("frame0.k0"));
			break;
		case Zt.DrawingTool.DrawLines:
			e.addToolObject(new tr("frame0.k0"));
			break;
		case Zt.DrawingTool.HoriRayLine:
			e.addToolObject(new qe("frame0.k0"));
			break;
		case Zt.DrawingTool.HoriSegLine:
			e.addToolObject(new Ke("frame0.k0"));
			break;
		case Zt.DrawingTool.HoriStraightLine:
			e.addToolObject(new Je("frame0.k0"));
			break;
		case Zt.DrawingTool.PriceLine:
			e.addToolObject(new ar("frame0.k0"));
			break;
		case Zt.DrawingTool.RayLine:
			e.addToolObject(new Qe("frame0.k0"));
			break;
		case Zt.DrawingTool.SegLine:
			e.addToolObject(new Ze("frame0.k0"));
			break;
		case Zt.DrawingTool.StraightLine:
			e.addToolObject(new tr("frame0.k0"));
			break;
		case Zt.DrawingTool.TriParallelLine:
			e.addToolObject(new er("frame0.k0"));
			break;
		case Zt.DrawingTool.VertiStraightLine:
			e.addToolObject(new rr("frame0.k0"))
		}
	}, Zt.prototype.getTitle = function(t) {
		return this._titles[t]
	}, Zt.prototype.setTitle = function(t, e) {
		this._titles[t] = e
	}, Zt.prototype.setCurrentDataSource = function(t, e) {
		var r = this.getCachedDataSource(e);
		if (null != r) this.setDataSource(t, r, !0);
		else {
			var i = this.getDataSource(t);
			null != i && (a(i, he) ? r = new he(e) : a(i, CLiveOrderDataSource) ? r = new CLiveOrderDataSource(e) : a(i, CLiveTradeDataSource) && (r = new CLiveTradeDataSource(e)), this.setDataSource(t, r, !0), this.setCachedDataSource(e, r))
		}
	}, Zt.prototype.getDataSource = function(t) {
		return this._dataSources[t]
	}, Zt.prototype.setDataSource = function(t, e, r) {
		this._dataSources[t] = e, r && this.updateData(t, null)
	}, Zt.prototype.getCachedDataSource = function(t) {
		return this._dataSourceCache[t]
	}, Zt.prototype.setCachedDataSource = function(t, e) {
		this._dataSourceCache[t] = e
	}, Zt.prototype.getDataProvider = function(t) {
		return this._dataProviders[t]
	}, Zt.prototype.setDataProvider = function(t, e) {
		this._dataProviders[t] = e
	}, Zt.prototype.removeDataProvider = function(t) {
		delete this._dataProviders[t]
	}, Zt.prototype.getFrame = function(t) {
		return this._frames[t]
	}, Zt.prototype.setFrame = function(t, e) {
		this._frames[t] = e
	}, Zt.prototype.removeFrame = function(t) {
		delete this._frames[t]
	}, Zt.prototype.getArea = function(t) {
		return this._areas[t]
	}, Zt.prototype.setArea = function(t, e) {
		this._areas[t] = e
	}, Zt.prototype.removeArea = function(t) {
		delete this._areas[t]
	}, Zt.prototype.getTimeline = function(t) {
		return this._timelines[t]
	}, Zt.prototype.cleanDatas = function(t) {
		var e = this.getDataSource(t);
		void 0 != e && e.cleanDatas()
	}, Zt.prototype.setTimeline = function(t, e) {
		this._timelines[t] = e
	}, Zt.prototype.removeTimeline = function(t) {
		delete this._timelines[t]
	}, Zt.prototype.getRange = function(t) {
		return this._ranges[t]
	}, Zt.prototype.setRange = function(t, e) {
		this._ranges[t] = e
	}, Zt.prototype.removeRange = function(t) {
		delete this._ranges[t]
	}, Zt.prototype.getPlotter = function(t) {
		return this._plotters[t]
	}, Zt.prototype.setPlotter = function(t, e) {
		this._plotters[t] = e
	}, Zt.prototype.removePlotter = function(t) {
		delete this._plotters[t]
	}, Zt.prototype.getTheme = function(t) {
		return this._themes[t]
	}, Zt.prototype.setTheme = function(t, e) {
		this._themes[t] = e
	}, Zt.prototype.getFrameMousePos = function(t, e) {
		void 0 != this._frameMousePos[t] ? (e.x = this._frameMousePos[t].x, e.y = this._frameMousePos[t].y) : (e.x = -1, e.y = -1)
	}, Zt.prototype.setFrameMousePos = function(t, e, r) {
		this._frameMousePos[t] = {
			x: e,
			y: r
		}
	}, Zt.prototype.drawArea = function(t, e, r) {
		var a = e.getNameObject().getCompAt(2);
		if ("timeline" == a) {
			if (e.getHeight() < 20) return
		} else if (e.getHeight() < 30) return;
		if (!(e.getWidth() < 30)) {
			a = e.getName();
			var i, o, s = r.length;
			for (o = 0; o < s; o++) i = this._plotters[a + r[o]], void 0 != i && i.Draw(t)
		}
	}, Zt.prototype.drawAreaMain = function(t, e) {
		var r, a = this._dataSources[e.getDataSourceName()];
		r = a.getDataCount() < 1 ? [".background"] : [".background", ".grid", ".main", ".secondary"], this.drawArea(t, e, r), e.setChanged(!1)
	}, Zt.prototype.drawAreaOverlay = function(t, e) {
		var r, a = this._dataSources[e.getDataSourceName()];
		r = a.getDataCount() < 1 ? [".selection"] : [".decoration", ".selection", ".info", ".tool"], this.drawArea(t, e, r)
	}, Zt.prototype.drawMain = function(t, e) {
		if (drawn = !1, !drawn) for (var r in this._areas) this._areas[r].getFrameName() != t || a(this._areas[r], Kt) || this.drawAreaMain(e, this._areas[r]);
		var i;
		for (var o in this._timelines) i = this._timelines[o], i.getFrameName() == t && i.setUpdated(!1);
		for (var o in this._ranges) i = this._ranges[o], i.getFrameName() == t && i.setUpdated(!1);
		for (var o in this._areas) i = this._areas[o], i.getFrameName() == t && i.setChanged(!1)
	}, Zt.prototype.drawOverlay = function(t, e) {
		for (var r in this._areas) {
			var i = this._areas[r];
			a(i, Kt) && i.getFrameName() == t && i.drawGrid(e)
		}
		for (var r in this._areas) {
			var i = this._areas[r];
			0 == a(i, Kt) && i.getFrameName() == t && this.drawAreaOverlay(e, i)
		}
	}, Zt.prototype.updateData = function(t, e) {
		var r = this.getDataSource(t);
		if (void 0 != r) {
			if (null != e) {
				if (!r.update(e)) return !1;
				if (r.getUpdateMode() == ne.UpdateMode.DoNothing) return !0
			} else r.setUpdateMode(ne.UpdateMode.Refresh);
			var i = this.getTimeline(t);
			if (void 0 != i && i.update(), r.getDataCount() < 1) return !0;
			var o, s, n = [".main", ".secondary"];
			for (var h in this._areas) if (o = this._areas[h], !a(o, Kt) && o.getDataSourceName() == t) {
				s = o.getName();
				for (var c = 0; c < n.length; c++) {
					var l = this.getDataProvider(s + n[c]);
					void 0 != l && l.updateData()
				}
			}
			return !0
		}
	}, Zt.prototype.updateRange = function(t) {
		var e = this.getDataSource(t);
		if (!(e.getDataCount() < 1)) {
			var r, i, o = [".main", ".secondary"];
			for (var s in this._areas) if (r = this._areas[s], !a(r, Kt) && r.getDataSourceName() == t) {
				i = r.getName();
				for (var n = 0; n < o.length; n++) {
					var h = this.getDataProvider(i + o[n]);
					void 0 != h && h.updateRange()
				}
				var c = this.getTimeline(t);
				if (void 0 != c && c.getMaxItemCount() > 0) {
					var l = this.getRange(i);
					void 0 != l && l.update()
				}
			}
		}
	}, Zt.prototype.layout = function(t, e, r, a, i, o) {
		var s = this.getFrame(e);
		s.measure(t, i - r, o - a), s.layout(r, a, i, o);
		for (var n in this._timelines) {
			var h = this._timelines[n];
			h.getFrameName() == e && h.onLayout()
		}
		for (var n in this._dataSources) n.substring(0, e.length) == e && this.updateRange(n)
	}, Zt.prototype.SelectRange = function(t, e) {
		for (var r in this._ranges) {
			var a = this._ranges[r].getAreaName(),
				i = t.getName();
			a == i ? this._ranges[r].selectAt(e) : this._ranges[r].unselect()
		}
	}, Zt.prototype.scale = function(t) {
		if (null != this._highlightedFrame) {
			var e = this._highlightedFrame.getHighlightedArea();
			if (void 0 != this.getRange(e.getName())) {
				var r = e.getDataSourceName(),
					a = this.getTimeline(r);
				null != a && (a.scale(t), this.updateRange(r))
			}
		}
	}, Zt.prototype.showCursor = function(t) {
		void 0 === t && (t = "default"), this._mainCanvas.style.cursor = t, this._overlayCanvas.style.cursor = t
	}, Zt.prototype.hideCursor = function() {
		this._mainCanvas.style.cursor = "none", this._overlayCanvas.style.cursor = "none"
	}, Zt.prototype.showCrossCursor = function(t, e, r) {
		var a = this.getRange(t.getName());
		return !(void 0 == a || (a.selectAt(r), a = this.getTimeline(t.getDataSourceName()), void 0 == a || !a.selectAt(e)))
	}, Zt.prototype.hideCrossCursor = function(t) {
		if (null != t) for (var e in this._timelines) {
			var r = this._timelines[e];
			r != t && r.unselect()
		} else for (var e in this._timelines) this._timelines[e].unselect();
		for (var e in this._ranges) this._ranges[e].unselect()
	}, Zt.prototype.clearHighlight = function() {
		null != this._highlightedFrame && (this._highlightedFrame.highlight(null), this._highlightedFrame = null)
	}, Zt.prototype.onToolMouseMove = function(t, e, r) {
		var i = !1;
		t += ".";
		for (var o in this._dataSources) if (0 == o.indexOf(t)) {
			var s = this._dataSources[o];
			a(s, he) && s.toolManager.acceptMouseMoveEvent(e, r) && (i = !0)
		}
		return i
	}, Zt.prototype.onToolMouseDown = function(t, e, r) {
		var i = !1;
		t += ".";
		for (var o in this._dataSources) if (0 == o.indexOf(t)) {
			var s = this._dataSources[o];
			a(s, he) && s.toolManager.acceptMouseDownEvent(e, r) && (i = !0)
		}
		return i
	}, Zt.prototype.onToolMouseUp = function(t, e, r) {
		var i = !1;
		t += ".";
		for (var o in this._dataSources) if (0 == o.indexOf(t)) {
			var s = this._dataSources[o];
			a(s, he) && s.toolManager.acceptMouseUpEvent(e, r) && (i = !0)
		}
		return i
	}, Zt.prototype.onToolMouseDrag = function(t, e, r) {
		var i = !1;
		t += ".";
		for (var o in this._dataSources) if (0 == o.indexOf(t)) {
			var s = this._dataSources[o];
			a(s, he) && s.toolManager.acceptMouseDownMoveEvent(e, r) && (i = !0)
		}
		return i
	}, Zt.prototype.onMouseMove = function(t, e, r, i) {
		var o = this.getFrame(t);
		if (void 0 !== o) {
			if (this.setFrameMousePos(t, e, r), this.hideCrossCursor(), this._highlightedFrame != o && this.clearHighlight(), null != this._capturingMouseArea) return void this._capturingMouseArea.onMouseMove(e, r);
			var s = o.contains(e, r);
			if (null != s) {
				var n, h, c = s.length;
				for (h = c - 1; h >= 0; h--) if (n = s[h], n = n.onMouseMove(e, r), null != n) return void(a(n, Kt) || (o.highlight(n), this._highlightedFrame = o))
			}
		}
	}, Zt.prototype.onMouseLeave = function(t, e, r, a) {
		var i = this.getFrame(t);
		void 0 != i && (this.setFrameMousePos(t, e, r), this.hideCrossCursor(), this.clearHighlight(), null != this._capturingMouseArea && (this._capturingMouseArea.onMouseLeave(e, r), this._capturingMouseArea = null), this._dragStarted = !1)
	}, Zt.prototype.onMouseDown = function(t, e, r) {
		var a = this.getFrame(t);
		if (void 0 != a) {
			var i = a.contains(e, r);
			if (null != i) {
				var o, s, n = i.length;
				for (s = n - 1; s >= 0; s--) if (o = i[s], o = o.onMouseDown(e, r), null != o) return void(this._capturingMouseArea = o)
			}
		}
	}, Zt.prototype.onMouseUp = function(t, e, r) {
		var a = this.getFrame(t);
		void 0 != a && this._capturingMouseArea && (null == this._capturingMouseArea.onMouseUp(e, r) && 0 == this._dragStarted && (null != this._selectedFrame && this._selectedFrame != a && this._selectedFrame.select(null), this._capturingMouseArea.isSelected() ? (this._captureMouseWheelDirectly || $(this._overlayCanvas).unbind("mousewheel"), a.select(null), this._selectedFrame = null) : (this._selectedFrame != a && (this._captureMouseWheelDirectly || $(this._overlayCanvas).bind("mousewheel", m)), a.select(this._capturingMouseArea), this._selectedFrame = a)), this._capturingMouseArea = null, this._dragStarted = !1)
	}, Zt.prototype.deleteToolObject = function() {
		var t = this.getDataSource("frame0.k0"),
			e = t.getSelectToolObjcet();
		null != e && t.delSelectToolObject();
		var r = t.getCurrentToolObject();
		null != r && r.getState() != Xe.state.AfterDraw && t.delToolObject(), this.setNormalMode()
	}, Zt.prototype.unloadTemplate = function(t) {
		var e = this.getFrame(t);
		if (void 0 != e) {
			for (var r in this._dataSources) r.match(t + ".") && delete this._dataSources[r];
			for (var r in this._dataProviders) this._dataProviders[r].getFrameName() == t && delete this._dataProviders[r];
			delete this._frames[t];
			for (var r in this._areas) this._areas[r].getFrameName() == t && delete this._areas[r];
			for (var r in this._timelines) this._timelines[r].getFrameName() == t && delete this._timelines[r];
			for (var r in this._ranges) this._ranges[r].getFrameName() == t && delete this._ranges[r];
			for (var r in this._plotters) this._plotters[r].getFrameName() == t && delete this._plotters[r];
			delete this._themes[t], delete this._frameMousePos[t]
		}
	}, Zt.prototype.createIndicatorAndRange = function(t, e, r) {
		var a, i;
		switch (e) {
		case "MA":
			a = new wt, i = new re(t);
			break;
		case "EMA":
			a = new xt, i = new re(t);
			break;
		case "VOLUME":
			a = new Ct, i = new ae(t);
			break;
		case "MACD":
			a = new Pt, i = new oe(t);
			break;
		case "DMI":
			a = new bt, i = new se(t);
			break;
		case "DMA":
			a = new St, i = new ee(t);
			break;
		case "TRIX":
			a = new Mt, i = new ee(t);
			break;
		case "BRAR":
			a = new At, i = new ee(t);
			break;
		case "VR":
			a = new It, i = new ee(t);
			break;
		case "OBV":
			a = new Dt, i = new ee(t);
			break;
		case "EMV":
			a = new Tt, i = new ee(t);
			break;
		case "RSI":
			a = new kt, i = new se(t);
			break;
		case "WR":
			a = new Nt, i = new se(t);
			break;
		case "SAR":
			a = new Rt, i = new re(t);
			break;
		case "KDJ":
			a = new Ot, i = new se(t);
			break;
		case "ROC":
			a = new Lt, i = new ee(t);
			break;
		case "MTM":
			a = new $t, i = new ee(t);
			break;
		case "BOLL":
			a = new jt, i = new ee(t);
			break;
		case "PSY":
			a = new Ft, i = new ee(t);
			break;
		case "StochRSI":
			a = new Vt, i = new se(t);
			break;
		default:
			return null
		}
		return r || a.setParameters(Be.get().indics[e]), {
			indic: a,
			range: i
		}
	}, Zt.prototype.setMainIndicator = function(t, e) {
		var r = t + ".main",
			i = this.getDataProvider(r + ".main");
		if (void 0 == i || !a(i, le)) return !1;
		var o;
		switch (e) {
		case "MA":
			o = new wt;
			break;
		case "EMA":
			o = new xt;
			break;
		case "BOLL":
			o = new jt;
			break;
		case "SAR":
			o = new Rt;
			break;
		default:
			return !1
		}
		o.setParameters(Be.get().indics[e]);
		var s = r + ".secondary",
			n = this.getDataProvider(s);
		void 0 == n && (n = new ue(s), this.setDataProvider(n.getName(), n)), n.setIndicator(o);
		var h = this.getPlotter(s);
		return void 0 == h && (h = new Te(s), this.setPlotter(h.getName(), h)), this.getArea(r).setChanged(!0), !0
	}, Zt.prototype.setIndicator = function(t, e) {
		var r = this.getArea(t);
		if (void 0 == r || "main" == r.getNameObject().getCompAt(2)) return !1;
		var i = this.getDataProvider(t + ".secondary");
		if (void 0 == i || !a(i, ue)) return !1;
		var o = this.createIndicatorAndRange(t, e);
		if (null == o) return !1;
		var s = o.indic,
			n = o.range;
		if (this.removeDataProvider(t + ".main"), this.removePlotter(t + ".main"), this.removeRange(t), this.removePlotter(t + "Range.decoration"), i.setIndicator(s), this.setRange(t, n), n.setPaddingTop(20), n.setPaddingBottom(4), n.setMinInterval(20), a(s, Ct)) {
			var h = new $e(t + "Range.decoration");
			this.setPlotter(h.getName(), h)
		} else if (a(s, jt) || a(s, Rt)) {
			var i = new le(t + ".main");
			this.setDataProvider(i.getName(), i), i.updateData();
			var h = new Ie(t + ".main");
			this.setPlotter(h.getName(), h)
		}
		return !0
	}, Zt.prototype.removeMainIndicator = function(t) {
		var e = t + ".main",
			r = e + ".secondary",
			i = this.getDataProvider(r);
		void 0 != i && a(i, ue) && (this.removeDataProvider(r), this.removePlotter(r), this.getArea(e).setChanged(!0))
	}, Zt.prototype.removeIndicator = function(t) {
		var e = this.getArea(t);
		if (void 0 != e && "main" != e.getNameObject().getCompAt(2)) {
			var r = this.getDataProvider(t + ".secondary");
			if (void 0 != r && a(r, ue)) {
				var i = t + "Range",
					o = this.getArea(i);
				if (void 0 != o) {
					var s = this.getArea(e.getDataSourceName() + ".charts");
					if (void 0 != s) {
						s.removeArea(e), this.removeArea(t), s.removeArea(o), this.removeArea(i);
						for (var n in this._dataProviders) this._dataProviders[n].getAreaName() == t && this.removeDataProvider(n);
						for (var n in this._ranges) this._ranges[n].getAreaName() == t && this.removeRange(n);
						for (var n in this._plotters) this._plotters[n].getAreaName() == t && this.removePlotter(n);
						for (var n in this._plotters) this._plotters[n].getAreaName() == i && this.removePlotter(n)
					}
				}
			}
		}
	}, Zt.prototype.getIndicatorParameters = function(t) {
		var e = this._fakeIndicators[t];
		if (void 0 == e) {
			var r = this.createIndicatorAndRange("", t);
			if (null == r) return null;
			this._fakeIndicators[t] = e = r.indic
		}
		var a, i = [],
			o = e.getParameterCount();
		for (a = 0; a < o; a++) i.push(e.getParameterAt(a));
		return i
	}, Zt.prototype.setIndicatorParameters = function(t, e) {
		var r, i;
		for (r in this._dataProviders) {
			var o = this._dataProviders[r];
			0 != a(o, ue) && (i = o.getIndicator(), i.getName() == t && (i.setParameters(e), o.refresh(), this.getArea(o.getAreaName()).setChanged(!0)))
		}
		if (i = this._fakeIndicators[t], void 0 == i) {
			var s = this.createIndicatorAndRange("", t, !0);
			if (null == s) return;
			this._fakeIndicators[t] = i = s.indic
		}
		i.setParameters(e)
	}, Zt.prototype.getIndicatorAreaName = function(t, e) {
		var r = this.getArea(t + ".charts"),
			a = r.getAreaCount() >> 1;
		return e < 0 || e >= a ? "" : r.getAreaAt(e << 1).getName()
	};
	var te = r(Xt);
	te._ItemWidth = [1, 3, 3, 5, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29], te._SpaceWidth = [1, 1, 2, 2, 3, 3, 3, 3, 3, 3, 5, 5, 5, 5, 7, 7, 7], te.PADDING_LEFT = 4, te.PADDING_RIGHT = 8, te.prototype.__construct = function(t) {
		te.__super.__construct.call(this, t), this._updated = !1, this._innerLeft = 0, this._innerWidth = 0, this._firstColumnLeft = 0, this._scale = 3, this._lastScale = -1, this._maxItemCount = 0, this._maxIndex = 0, this._firstIndex = -1, this._selectedIndex = -1, this._savedFirstIndex = -1
	}, te.prototype.isLatestShown = function() {
		return this.getLastIndex() == this._maxIndex
	}, te.prototype.isUpdated = function() {
		return this._updated
	}, te.prototype.setUpdated = function(t) {
		this._updated = t
	}, te.prototype.getItemWidth = function() {
		return te._ItemWidth[this._scale]
	}, te.prototype.getSpaceWidth = function() {
		return te._SpaceWidth[this._scale]
	}, te.prototype.getColumnWidth = function() {
		return this.getSpaceWidth() + this.getItemWidth()
	}, te.prototype.getInnerWidth = function() {
		return this._innerWidth
	}, te.prototype.getItemLeftOffset = function() {
		return this.getSpaceWidth()
	}, te.prototype.getItemCenterOffset = function() {
		return this.getSpaceWidth() + (this.getItemWidth() >> 1)
	}, te.prototype.getFirstColumnLeft = function() {
		return this._firstColumnLeft
	}, te.prototype.getMaxItemCount = function() {
		return this._maxItemCount
	}, te.prototype.getFirstIndex = function() {
		return this._firstIndex
	}, te.prototype.getLastIndex = function() {
		return Math.min(this._firstIndex + this._maxItemCount, this._maxIndex)
	}, te.prototype.getSelectedIndex = function() {
		return this._selectedIndex
	}, te.prototype.getMaxIndex = function() {
		return this._maxIndex
	}, te.prototype.calcColumnCount = function(t) {
		return Math.floor(t / this.getColumnWidth()) << 0
	}, te.prototype.calcFirstColumnLeft = function(t) {
		return this._innerLeft + this._innerWidth - this.getColumnWidth() * t
	}, te.prototype.calcFirstIndexAlignRight = function(t, e, r) {
		return Math.max(0, t + Math.max(e, 1) - Math.max(r, 1))
	}, te.prototype.calcFirstIndex = function(t) {
		return this.validateFirstIndex(this.calcFirstIndexAlignRight(this._firstIndex, this._maxItemCount, t), t)
	}, te.prototype.updateMaxItemCount = function() {
		var t, e = this.calcColumnCount(this._innerWidth);
		if (this._maxItemCount < 1) t = this.calcFirstIndex(e);
		else if (this._lastScale == this._scale) t = this.validateFirstIndex(this._firstIndex - (e - this._maxItemCount));
		else {
			var r = this._selectedIndex >= 0 ? this._selectedIndex : this.getLastIndex() - 1;
			t = this.validateFirstIndex(r - Math.round((r - this._firstIndex) * e / this._maxItemCount))
		}
		this._lastScale = this._scale, this._firstIndex != t && (this._selectedIndex == this._firstIndex && (this._selectedIndex = t), this._firstIndex = t, this._updated = !0), this._maxItemCount != e && (this._maxItemCount = e, this._updated = !0), this._firstColumnLeft = this.calcFirstColumnLeft(e)
	}, te.prototype.validateFirstIndex = function(t, e) {
		if (this._maxIndex < 1) return -1;
		if (t < 0) return 0;
		var r = Math.max(0, this._maxIndex - 1);
		return t > r ? r : t
	}, te.prototype.validateSelectedIndex = function() {
		this._selectedIndex < this._firstIndex ? this._selectedIndex = -1 : this._selectedIndex >= this.getLastIndex() && (this._selectedIndex = -1)
	}, te.prototype.onLayout = function() {
		var t = Zt.getInstance(),
			e = t.getArea(this.getDataSourceName() + ".main");
		if (null != e) {
			this._innerLeft = e.getLeft() + te.PADDING_LEFT;
			var r = Math.max(0, e.getWidth() - (te.PADDING_LEFT + te.PADDING_RIGHT));
			this._innerWidth != r && (this._innerWidth = r, this.updateMaxItemCount())
		}
	}, te.prototype.toIndex = function(t) {
		return this._firstIndex + this.calcColumnCount(t - this._firstColumnLeft)
	}, te.prototype.toColumnLeft = function(t) {
		return this._firstColumnLeft + this.getColumnWidth() * (t - this._firstIndex)
	}, te.prototype.toItemLeft = function(t) {
		return this.toColumnLeft(t) + this.getItemLeftOffset()
	}, te.prototype.toItemCenter = function(t) {
		return this.toColumnLeft(t) + this.getItemCenterOffset()
	}, te.prototype.selectAt = function(t) {
		return this._selectedIndex = this.toIndex(t), this.validateSelectedIndex(), this._selectedIndex >= 0
	}, te.prototype.unselect = function() {
		this._selectedIndex = -1
	}, te.prototype.update = function() {
		var t = Zt.getInstance(),
			e = t.getDataSource(this.getDataSourceName()),
			r = this._maxIndex;
		switch (this._maxIndex = e.getDataCount(), e.getUpdateMode()) {
		case ne.UpdateMode.Refresh:
			this._maxIndex < 1 ? this._firstIndex = -1 : this._firstIndex = Math.max(this._maxIndex - this._maxItemCount, 0), this._selectedIndex = -1, this._updated = !0;
			break;
		case ne.UpdateMode.Append:
			var a = this.getLastIndex(),
				i = e.getErasedCount();
			a < r ? i > 0 && (this._firstIndex = Math.max(this._firstIndex - i, 0), this._selectedIndex >= 0 && (this._selectedIndex -= i, this.validateSelectedIndex()), this._updated = !0) : a == r && (this._firstIndex += this._maxIndex - r, this._selectedIndex >= 0 && (this._selectedIndex -= i, this.validateSelectedIndex()), this._updated = !0)
		}
	}, te.prototype.move = function(t) {
		this.isLatestShown() && Zt.getInstance().getArea(this.getDataSourceName() + ".mainRange").setChanged(!0), this._firstIndex = this.validateFirstIndex(this._savedFirstIndex - this.calcColumnCount(t), this._maxItemCount), this._updated = !0, this._selectedIndex >= 0 && this.validateSelectedIndex()
	}, te.prototype.startMove = function() {
		this._savedFirstIndex = this._firstIndex
	}, te.prototype.scale = function(t) {
		this._scale += t, this._scale < 0 ? this._scale = 0 : this._scale >= te._ItemWidth.length && (this._scale = te._ItemWidth.length - 1), this.updateMaxItemCount(), this._selectedIndex >= 0 && this.validateSelectedIndex()
	};
	var ee = r(Xt);
	ee.prototype.__construct = function(t) {
		ee.__super.__construct.call(this, t), this._updated = !0, this._minValue = Number.MAX_VALUE, this._maxValue = -Number.MAX_VALUE, this._outerMinValue = Number.MAX_VALUE, this._outerMaxValue = -Number.MAX_VALUE, this._ratio = 0, this._top = 0, this._bottom = 0, this._paddingTop = 0, this._paddingBottom = 0, this._minInterval = 36, this._selectedPosition = -1, this._selectedValue = -Number.MAX_VALUE, this._gradations = []
	}, ee.prototype.isUpdated = function() {
		return this._updated
	}, ee.prototype.setUpdated = function(t) {
		this._updated = t
	}, ee.prototype.getMinValue = function() {
		return this._minValue
	}, ee.prototype.getMaxValue = function() {
		return this._maxValue
	}, ee.prototype.getRange = function() {
		return this._maxValue - this._minValue
	}, ee.prototype.getOuterMinValue = function() {
		return this._outerMinValue
	}, ee.prototype.getOuterMaxValue = function() {
		return this._outerMaxValue
	}, ee.prototype.getOuterRange = function() {
		return this._outerMaxValue - this._outerMinValue
	}, ee.prototype.getHeight = function() {
		return Math.max(0, this._bottom - this._top)
	}, ee.prototype.getGradations = function() {
		return this._gradations
	}, ee.prototype.getMinInterval = function() {
		return this._minInterval
	}, ee.prototype.setMinInterval = function(t) {
		this._minInterval = t
	}, ee.prototype.getSelectedPosition = function() {
		return this._selectedPosition >= 0 ? this._selectedPosition : this._selectedValue > -Number.MAX_VALUE ? this.toY(this._selectedValue) : -1
	}, ee.prototype.getSelectedValue = function() {
		if (this._selectedValue > -Number.MAX_VALUE) return this._selectedValue;
		var t = Zt.getInstance(),
			e = t.getArea(this.getAreaName());
		return null == e ? -Number.MAX_VALUE : this._selectedPosition < e.getTop() + 12 || this._selectedPosition >= e.getBottom() - 4 ? -Number.MAX_VALUE : this.toValue(this._selectedPosition)
	}, ee.prototype.setPaddingTop = function(t) {
		this._paddingTop = t
	}, ee.prototype.setPaddingBottom = function(t) {
		this._paddingBottom = t
	}, ee.prototype.toValue = function(t) {
		return this._maxValue - (t - this._top) / this._ratio
	}, ee.prototype.toY = function(t) {
		return this._ratio > 0 ? this._top + Math.floor((this._maxValue - t) * this._ratio + .5) : this._top
	}, ee.prototype.toHeight = function(t) {
		return Math.floor(t * this._ratio + 1.5)
	}, ee.prototype.update = function() {
		for (var t, e = Number.MAX_VALUE, r = -Number.MAX_VALUE, a = Zt.getInstance(), i = [".main", ".secondary"], o = 0; o < i.length; o++) t = a.getDataProvider(this.getName() + i[o]), null != t && (e = Math.min(e, t.getMinValue()), r = Math.max(r, t.getMaxValue()));
		var s = {
			min: e,
			max: r
		};
		this.preSetRange(s), this.setRange(s.min, s.max)
	}, ee.prototype.select = function(t) {
		this._selectedValue = t, this._selectedPosition = -1
	}, ee.prototype.selectAt = function(t) {
		this._selectedPosition = t, this._selectedValue = -Number.MAX_VALUE
	}, ee.prototype.unselect = function() {
		this._selectedPosition = -1, this._selectedValue = -Number.MAX_VALUE
	}, ee.prototype.preSetRange = function(t) {
		t.min == t.max && (t.min = -1, t.max = 1)
	}, ee.prototype.setRange = function(t, e) {
		var r = Zt.getInstance(),
			a = r.getArea(this.getAreaName());
		if (this._minValue != t || this._maxValue != e || a.isChanged()) {
			this._updated = !0, this._minValue = t, this._maxValue = e, this._gradations = [];
			var i = a.getTop() + this._paddingTop,
				o = a.getBottom() - (this._paddingBottom + 1);
			if (i >= o) return void(this._minValue = this._maxValue);
			this._top = i, this._bottom = o, this._maxValue > this._minValue ? this._ratio = (o - i) / (this._maxValue - this._minValue) : this._ratio = .001, this._outerMinValue = this.toValue(a.getBottom()), this._outerMaxValue = this.toValue(a.getTop()), this.updateGradations()
		}
	}, ee.prototype.calcInterval = function() {
		var t = this.getHeight(),
			e = this.getMinInterval();
		t / e <= 1 && (e = t >> 1);
		for (var r = this.getRange(), a = 0; a > -2 && Math.floor(r) < r;) r *= 10, a--;
		for (var i, o;
		(o = Math.pow(10, a), i = o, !(this.toHeight(i) > e)) && (i = 2 * o, !(this.toHeight(i) > e)) && (i = 5 * o, !(this.toHeight(i) > e)); a++);
		return i
	}, ee.prototype.updateGradations = function() {
		this._gradations = [];
		var t = this.calcInterval();
		if (!(t <= 0)) {
			var e = Math.floor(this.getMaxValue() / t) * t;
			do this._gradations.push(e), e -= t;
			while (e > this.getMinValue())
		}
	};
	var re = r(ee);
	re.prototype.__construct = function(t) {
		re.__super.__construct.call(this, t)
	}, re.prototype.preSetRange = function(t) {
		t.min < 0 && (t.min = 0), t.max < 0 && (t.max = 0)
	};
	var ae = r(ee);
	ae.prototype.__construct = function(t) {
		ae.__super.__construct.call(this, t)
	}, ae.prototype.preSetRange = function(t) {
		t.min = 0, t.max < 0 && (t.max = 0)
	};
	var ie = r(ee);
	ie.prototype.__construct = function(t) {
		ie.__super.__construct.call(this, t)
	}, ie.prototype.preSetRange = function(t) {
		var e = Zt.getInstance(),
			r = e.getTimeline(this.getDataSourceName()),
			a = r.getMaxIndex() - r.getLastIndex();
		if (a < 25) {
			var i = e.getDataSource(this.getDataSourceName()),
				o = i.getDataAt(i.getDataCount() - 1),
				s = (t.max - t.min) / 4 * (1 - a / 25);
			t.min = Math.min(t.min, Math.max(o.low - s, 0)), t.max = Math.max(t.max, o.high + s)
		}
		if (t.min > 0) {
			var n = t.max / t.min;
			if (n < 1.024) {
				var h = (t.max + t.min) / 2,
					c = n - 1;
				t.max = h * (1 + c), t.min = h * (1 - c)
			} else if (n < 1.048) {
				var h = (t.max + t.min) / 2;
				t.max = 1.024 * h, t.min = .976 * h
			}
		}
		t.min < 0 && (t.min = 0), t.max < 0 && (t.max = 0)
	};
	var oe = r(ee);
	oe.prototype.__construct = function(t) {
		oe.__super.__construct.call(this, t)
	}, oe.prototype.calcInterval = function(t) {
		var e = this.getMinInterval();
		if (t.getHeight() / e < 2) return 0;
		var r, a = this.getRange();
		for (r = 3; !(this.toHeight(a / r) <= e); r += 2);
		return r -= 2, a / r
	}, oe.prototype.updateGradations = function() {
		this._gradations = [];
		var t = Zt.getInstance(),
			e = t.getArea(this.getAreaName()),
			r = this.calcInterval(e);
		if (!(r <= 0)) {
			var a = r / 2;
			do this._gradations.push(a), this._gradations.push(-a), a += r;
			while (a <= this.getMaxValue())
		}
	}, oe.prototype.preSetRange = function(t) {
		var e = Math.max(Math.abs(t.min), Math.abs(t.max));
		t.min = -e, t.max = e
	};
	var se = r(ee);
	se.prototype.__construct = function(t) {
		se.__super.__construct.call(this, t)
	}, se.prototype.updateGradations = function() {
		this._gradations = [];
		var t = Zt.getInstance(),
			e = t.getArea(this.getAreaName()),
			r = 10,
			a = Math.floor(this.toHeight(r));
		if (!(a << 2 > e.getHeight())) {
			var i = Math.ceil(this.getMinValue() / r) * r;
			if (0 == i && (i = 0), a << 2 < 24) {
				if (a << 1 < 8) return;
				do 20 != i && 80 != i || this._gradations.push(i), i += r;
				while (i < this.getMaxValue())
			} else do a < 8 ? 20 != i && 50 != i && 80 != i || this._gradations.push(i) : 0 != i && 20 != i && 50 != i && 80 != i && 100 != i || this._gradations.push(i), i += r;
			while (i < this.getMaxValue())
		}
	};
	var ne = r(Xt);
	ne.prototype.__construct = function(t) {
		ne.__super.__construct.call(this, t)
	}, ne.UpdateMode = {
		DoNothing: 0,
		Refresh: 1,
		Update: 2,
		Append: 3
	}, ne.prototype.getUpdateMode = function() {
		return this._updateMode
	}, ne.prototype.setUpdateMode = function(t) {
		this._updateMode = t
	}, ne.prototype.getCacheSize = function() {
		return 0
	}, ne.prototype.getDataCount = function() {
		return 0
	};
	var he = r(ne);
	he.prototype.__construct = function(t) {
		he.__super.__construct.call(this, t), this._erasedCount = 0, this._dataItems = [], this._decimalDigits = 0, this.toolManager = new or(t)
	}, he.prototype.getCacheSize = function() {
		return this._dataItems.length
	}, he.prototype.getDataCount = function() {
		return this._dataItems.length
	}, he.prototype.getUpdatedCount = function() {
		return this._updatedCount
	}, he.prototype.getAppendedCount = function() {
		return this._appendedCount
	}, he.prototype.getErasedCount = function() {
		return this._erasedCount
	}, he.prototype.getDecimalDigits = function() {
		return this._decimalDigits
	}, he.prototype.calcDecimalDigits = function(t) {
		var e = "" + scientificToNumber(t),
			r = e.indexOf(".");
		return r < 0 ? 0 : e.length - 1 - r
	}, he.prototype.getLastDate = function() {
		var t = this.getDataCount();
		return t < 1 ? -1 : this.getDataAt(t - 1).date
	}, he.prototype.getDataAt = function(t) {
		return this._dataItems[t]
	}, he.prototype.getTime = function(t) {
		var e = t;
		if (util.globalurl.isremote) {
			var r = [4, 2, 2, 2, 2, 2, 3],
				a = [0, 0, 0, 0, 0, 0, 0],
				i = t,
				o = 0;
			for (ii = 0; ii < r.length; ii++) a[ii] = i.substring(o, o + r[ii]), o += r[ii];
			e = Math.round(new Date(a[0], parseInt(a[1]) - 1, a[2], a[3], a[4], a[5], a[6]).getTime())
		}
		return e
	}, he.prototype.cleanDatas = function() {
		this._dataItems = []
	}, he.prototype.update = function(t) {
		this._updatedCount = 0, this._appendedCount = 0, this._erasedCount = 0;
		var e = this._dataItems.length;
		if (e > 0) {
			var r, a, i = e - 1,
				o = this._dataItems[i],
				s = t.length;
			for (a = 0; a < s; a++) r = t[a], this.getTime(r[0]) == o.date ? o.open == r[1] && o.high == r[2] && o.low == r[3] && o.close == r[4] && o.volume == r[5] ? this.setUpdateMode(ne.UpdateMode.DoNothing) : (this.setUpdateMode(ne.UpdateMode.Update), this._dataItems[i] = {
				date: this.getTime(r[0]),
				open: r[1],
				high: r[2],
				low: r[3],
				close: r[4],
				volume: r[5]
			}, this._updatedCount++) : (this.setUpdateMode(ne.UpdateMode.Append), this._appendedCount++, this._dataItems.push({
				date: this.getTime(r[0]),
				open: r[1],
				high: r[2],
				low: r[3],
				close: r[4],
				volume: r[5]
			}));
			return !0
		}
		this.setUpdateMode(ne.UpdateMode.Refresh), this._dataItems = [];
		var n, h, r, a, s = t.length;
		for (a = 0; a < s; a++) {
			for (r = t[a], h = 1; h <= 4; h++) n = this.calcDecimalDigits(r[h]), this._decimalDigits < n && (this._decimalDigits = n);
			this._dataItems.push({
				date: this.getTime(r[0]),
				open: r[1],
				high: r[2],
				low: r[3],
				close: r[4],
				volume: r[5]
			})
		}
		return !0
	}, he.prototype.select = function(t) {
		this.toolManager.selecedObject = t
	}, he.prototype.unselect = function() {
		this.toolManager.selecedObject = -1
	}, he.prototype.addToolObject = function(t) {
		this.toolManager.addToolObject(t)
	}, he.prototype.delToolObject = function() {
		this.toolManager.delCurrentObject()
	}, he.prototype.getToolObject = function(t) {
		return this.toolManager.getToolObject(t)
	}, he.prototype.getToolObjectCount = function() {
		return this.toolManager.toolObjects.length
	}, he.prototype.getCurrentToolObject = function() {
		return this.toolManager.getCurrentObject()
	}, he.prototype.getSelectToolObjcet = function() {
		return this.toolManager.getSelectedObject()
	}, he.prototype.delSelectToolObject = function() {
		this.toolManager.delSelectedObject()
	};
	var ce = r(Xt);
	ce.prototype.__construct = function(t) {
		ce.__super.__construct.call(this, t), this._minValue = 0, this._maxValue = 0, this._minValueIndex = -1, this._maxValueIndex = -1
	}, ce.prototype.getMinValue = function() {
		return this._minValue
	}, ce.prototype.getMaxValue = function() {
		return this._maxValue
	}, ce.prototype.getMinValueIndex = function() {
		return this._minValueIndex
	}, ce.prototype.getMaxValueIndex = function() {
		return this._maxValueIndex
	}, ce.prototype.calcRange = function(t, e, r, a) {
		for (var i = Number.MAX_VALUE, o = -Number.MAX_VALUE, s = -1, n = -1, h = {}, c = e - 1, l = t.length - 1; l >= 0; l--) {
			var u = t[l];
			if (c < u) r[l] = {
				min: i,
				max: o
			};
			else {
				for (; c >= u; c--) 0 != this.getMinMaxAt(c, h) && (i > h.min && (i = h.min, s = c), o < h.max && (o = h.max, n = c));
				r[l] = {
					min: i,
					max: o
				}
			}
			null != a && (a[l] = {
				minIndex: s,
				maxIndex: n
			})
		}
	}, ce.prototype.updateRange = function() {
		var t = Zt.getInstance(),
			e = t.getTimeline(this.getDataSourceName()),
			r = [e.getFirstIndex()],
			a = [{}],
			i = [{}];
		this.calcRange(r, e.getLastIndex(), a, i), this._minValue = a[0].min, this._maxValue = a[0].max, this._minValueIndex = i[0].minIndex, this._maxValueIndex = i[0].maxIndex
	};
	var le = r(ce);
	le.prototype.__construct = function(t) {
		le.__super.__construct.call(this, t), this._candlestickDS = null
	}, le.prototype.updateData = function() {
		var t = Zt.getInstance(),
			e = t.getDataSource(this.getDataSourceName());
		a(e, he) && (this._candlestickDS = e)
	}, le.prototype.getMinMaxAt = function(t, e) {
		var r = this._candlestickDS.getDataAt(t);
		return e.min = r.low, e.max = r.high, !0
	};
	var ue = r(ce);
	ue.prototype.getIndicator = function() {
		return this._indicator
	}, ue.prototype.setIndicator = function(t) {
		this._indicator = t, this.refresh()
	}, ue.prototype.refresh = function() {
		var t = Zt.getInstance(),
			e = t.getDataSource(this.getDataSourceName());
		if (!(e.getDataCount() < 1)) {
			var r, a = this._indicator,
				i = e.getDataCount();
			for (a.clear(), a.reserve(i), r = 0; r < i; r++) a.execute(e, r)
		}
	}, ue.prototype.updateData = function() {
		var t = Zt.getInstance(),
			e = t.getDataSource(this.getDataSourceName());
		if (!(e.getDataCount() < 1)) {
			var r = this._indicator,
				a = e.getUpdateMode();
			switch (a) {
			case ne.UpdateMode.Refresh:
				this.refresh();
				break;
			case ne.UpdateMode.Append:
				r.reserve(e.getAppendedCount());
			case ne.UpdateMode.Update:
				var i, o = e.getDataCount(),
					s = e.getUpdatedCount() + e.getAppendedCount();
				for (i = o - s; i < o; i++) r.execute(e, i)
			}
		}
	}, ue.prototype.getMinMaxAt = function(t, e) {
		e.min = Number.MAX_VALUE, e.max = -Number.MAX_VALUE;
		var r, a, i = !1,
			o = this._indicator.getOutputCount();
		for (a = 0; a < o; a++) r = this._indicator.getOutputAt(a).execute(t), 0 == isNaN(r) && (i = !0, e.min > r && (e.min = r), e.max < r && (e.max = r));
		return i
	};
	var _e = 0,
		pe = 0,
		de = r();
	de.prototype.getColor = function(t) {
		return this._colors[t]
	}, de.prototype.getFont = function(t) {
		return this._fonts[t]
	}, de.Color = {
		Positive: _e++,
		Negative: _e++,
		PositiveDark: _e++,
		NegativeDark: _e++,
		Unchanged: _e++,
		Background: _e++,
		Cursor: _e++,
		RangeMark: _e++,
		Indicator0: _e++,
		Indicator1: _e++,
		Indicator2: _e++,
		Indicator3: _e++,
		Grid0: _e++,
		Grid1: _e++,
		Grid2: _e++,
		Grid3: _e++,
		Grid4: _e++,
		TextPositive: _e++,
		TextNegative: _e++,
		Text0: _e++,
		Text1: _e++,
		Text2: _e++,
		Text3: _e++,
		Text4: _e++,
		LineColorNormal: _e++,
		LineColorSelected: _e++,
		CircleColorFill: _e++,
		CircleColorStroke: _e++
	}, de.Font = {
		Default: pe++
	};
	var ge = r(de);
	ge.prototype.__construct = function() {
		this._colors = [], this._colors[de.Color.Positive] = "#b33120", this._colors[de.Color.Negative] = "#19b34c", this._colors[de.Color.PositiveDark] = "#3b0e08", this._colors[de.Color.NegativeDark] = "#004718", this._colors[de.Color.Unchanged] = "#fff", this._colors[de.Color.Background] = "#fff", this._colors[de.Color.Cursor] = "#aaa", this._colors[de.Color.RangeMark] = "#da2e22", this._colors[de.Color.Indicator0] = "#3b7fed", this._colors[de.Color.Indicator1] = "#f9ee30", this._colors[de.Color.Indicator2] = "#f600ff", this._colors[de.Color.Indicator3] = "#6bf", this._colors[de.Color.Grid0] = "#eee", this._colors[de.Color.Grid1] = "#eee", this._colors[de.Color.Grid2] = "#666", this._colors[de.Color.Grid3] = "#666", this._colors[de.Color.Grid4] = "#aaa", this._colors[de.Color.TextPositive] = "#1bd357", this._colors[de.Color.TextNegative] = "#ff6f5e", this._colors[de.Color.Text0] = "#999", this._colors[de.Color.Text1] = "#666", this._colors[de.Color.Text2] = "#666", this._colors[de.Color.Text3] = "#aaa", this._colors[de.Color.Text4] = "#666", this._colors[de.Color.LineColorNormal] = "#a6a6a6", this._colors[de.Color.LineColorSelected] = "#ffffff", this._colors[de.Color.CircleColorFill] = "#999", this._colors[de.Color.CircleColorStroke] = "#ffffff", this._fonts = [], this._fonts[de.Font.Default] = "16px PingFangMedium"
	};
	var fe = r(de);
	fe.prototype.__construct = function() {
		this._colors = [], this._colors[de.Color.Positive] = "#db5542", this._colors[de.Color.Negative] = "#53b37b", this._colors[de.Color.PositiveDark] = "#ffadaa", this._colors[de.Color.NegativeDark] = "#66d293", this._colors[de.Color.Unchanged] = "#fff", this._colors[de.Color.Background] = "#fff", this._colors[de.Color.Cursor] = "#aaa", this._colors[de.Color.RangeMark] = "#f27935", this._colors[de.Color.Indicator0] = "#2fd2b2", this._colors[de.Color.Indicator1] = "#ffb400", this._colors[de.Color.Indicator2] = "#e849b9", this._colors[de.Color.Indicator3] = "#1478c8", this._colors[de.Color.Grid0] = "#eee", this._colors[de.Color.Grid1] = "#afb1b3", this._colors[de.Color.Grid2] = "#ccc", this._colors[de.Color.Grid3] = "#bbb", this._colors[de.Color.Grid4] = "#aaa", this._colors[de.Color.TextPositive] = "#53b37b", this._colors[de.Color.TextNegative] = "#db5542", this._colors[de.Color.Text0] = "#ccc", this._colors[de.Color.Text1] = "#aaa", this._colors[de.Color.Text2] = "#666", this._colors[de.Color.Text3] = "#666", this._colors[de.Color.Text4] = "#999", this._colors[de.Color.LineColorNormal] = "#8c8c8c", this._colors[de.Color.LineColorSelected] = "#393c40", this._colors[de.Color.CircleColorFill] = "#ffffff", this._colors[de.Color.CircleColorStroke] = "#393c40", this._fonts = [], this._fonts[de.Font.Default] = "12px PingFangMedium"
	};
	var me = r();
	me.onMeasuring = function(t, e) {
		var r = e.Width,
			a = (e.Height, t.getNameObject().getCompAt(2));
		"timeline" == a && t.setMeasuredDimension(r, 22)
	};
	var ye = r();
	ye.displayVolume = !0, ye.createCandlestickDataSource = function(t) {
		return new he(t)
	}, ye.createLiveOrderDataSource = function(t) {
		return new CLiveOrderDataSource(t)
	}, ye.createLiveTradeDataSource = function(t) {
		return new CLiveTradeDataSource(t)
	}, ye.createDataSource = function(t, e, r) {
		var a = Zt.getInstance();
		null == a.getCachedDataSource(e) && a.setCachedDataSource(e, r(e)), a.setCurrentDataSource(t, e), a.updateData(t, null)
	}, ye.createTableComps = function(t) {
		ye.createMainChartComps(t), ye.displayVolume && ye.createIndicatorChartComps(t, "VOLUME"), ye.createTimelineComps(t)
	}, ye.createMainChartComps = function(t) {
		var e = Zt.getInstance(),
			r = e.getArea(t + ".charts"),
			a = t + ".main",
			i = a + "Range",
			o = new Ut(a);
		e.setArea(a, o), r.addArea(o);
		var s = new Gt(i);
		e.setArea(i, s), r.addArea(s);
		var n = new le(a + ".main");
		e.setDataProvider(n.getName(), n), e.setMainIndicator(t, "MA");
		var h = new ie(a);
		e.setRange(h.getName(), h), h.setPaddingTop(28), h.setPaddingBottom(12);
		var c = new Ce(a + ".background");
		e.setPlotter(c.getName(), c), c = new Se(a + ".grid"), e.setPlotter(c.getName(), c), c = new Me(a + ".main"), e.setPlotter(c.getName(), c), c = new Ne(a + ".decoration"), e.setPlotter(c.getName(), c), c = new De(a + ".info"), e.setPlotter(c.getName(), c), c = new Fe(a + ".selection"), e.setPlotter(c.getName(), c), c = new br(a + ".tool"), e.setPlotter(c.getName(), c), c = new Pe(a + "Range.background"), e.setPlotter(c.getName(), c), c = new Le(a + "Range.grid"), e.setPlotter(c.getName(), c), c = new Oe(a + "Range.main"), e.setPlotter(c.getName(), c), c = new Ye(a + "Range.selection"), e.setPlotter(c.getName(), c), c = new je(a + "Range.decoration"), e.setPlotter(c.getName(), c)
	}, ye.createIndicatorChartComps = function(t, e) {
		var r = Zt.getInstance(),
			a = r.getArea(t + ".charts"),
			i = t + ".indic" + a.getNextRowId(),
			o = i + "Range",
			s = new Wt(i);
		r.setArea(i, s), a.addArea(s);
		var n = a.getAreaCount() >> 1,
			h = Be.get().charts.areaHeight;
		if (h.length > n) {
			var c, l;
			for (l = 0; l < n; l++) c = a.getAreaAt(l << 1), c.setTop(0), c.setBottom(h[l]);
			s.setTop(0), s.setBottom(h[n])
		}
		var u = new zt(o);
		r.setArea(o, u), a.addArea(u);
		var _ = new ue(i + ".secondary");
		if (r.setDataProvider(_.getName(), _), 0 == r.setIndicator(i, e)) return void r.removeIndicator(i);
		var p = new Ce(i + ".background");
		r.setPlotter(p.getName(), p), p = new Se(i + ".grid"), r.setPlotter(p.getName(), p), p = new Te(i + ".secondary"), r.setPlotter(p.getName(), p), p = new ke(i + ".info"), r.setPlotter(p.getName(), p), p = new Fe(i + ".selection"), r.setPlotter(p.getName(), p), p = new Pe(i + "Range.background"), r.setPlotter(p.getName(), p), p = new Oe(i + "Range.main"), r.setPlotter(p.getName(), p), p = new Ye(i + "Range.selection"), r.setPlotter(p.getName(), p)
	}, ye.createTimelineComps = function(t) {
		var e, r = Zt.getInstance(),
			a = new te(t);
		r.setTimeline(a.getName(), a), e = new be(t + ".timeline.background"), r.setPlotter(e.getName(), e), e = new Re(t + ".timeline.main"), r.setPlotter(e.getName(), e), e = new Ve(t + ".timeline.selection"), r.setPlotter(e.getName(), e)
	}, ye.createLiveOrderComps = function(t) {
		var e, r = Zt.getInstance();
		e = new xe(t + ".main.background"), r.setPlotter(e.getName(), e), e = new CLiveOrderPlotter(t + ".main.main"), r.setPlotter(e.getName(), e)
	}, ye.createLiveTradeComps = function(t) {
		var e, r = Zt.getInstance();
		e = new xe(t + ".main.background"), r.setPlotter(e.getName(), e), e = new CLiveTradePlotter(t + ".main.main"), r.setPlotter(e.getName(), e)
	};
	var ve = r(ye);
	ve.loadTemplate = function(t, e, r, a, i, o) {
		var s = Zt.getInstance(),
			n = Be.get(),
			h = new Et(t).getCompAt(0);
		s.unloadTemplate(h), ye.createDataSource(t, e, ye.createCandlestickDataSource);
		var c = new Qt(h);
		s.setFrame(c.getName(), c), s.setArea(c.getName(), c), c.setGridColor(de.Color.Grid1);
		var l = new qt(t + ".timeline");
		s.setArea(l.getName(), l), c.addArea(l), l.setDockStyle(Ht.DockStyle.Bottom), l.Measuring.addHandler(l, me.onMeasuring);
		var u = new Jt(t + ".charts");
		return s.setArea(u.getName(), u), u.setDockStyle(Ht.DockStyle.Fill), c.addArea(u), ye.createTableComps(t), s.setThemeName(h, n.theme), s
	};
	var we = r(Xt);
	we.prototype.__construct = function(t) {
		we.__super.__construct.call(this, t)
	}, we.isChrome = null != navigator.userAgent.toLowerCase().match(/chrome/), we.drawLine = function(t, e, r, a, i) {
		t.beginPath(), t.moveTo((e << 0) + .5, (r << 0) + .5), t.lineTo((a << 0) + .5, (i << 0) + .5), t.stroke()
	}, we.drawLines = function(t, e) {
		var r, a = e.length;
		for (t.beginPath(), t.moveTo(e[0].x, e[0].y), r = 1; r < a; r++) t.lineTo(e[r].x, e[r].y);
		if (we.isChrome) for (t.moveTo(e[0].x, e[0].y), r = 1; r < a; r++) t.lineTo(e[r].x, e[r].y);
		t.stroke()
	}, we.drawDashedLine = function(t, e, r, a, i, o, s) {
		o < 2 && (o = 2);
		var n = a - e,
			h = i - r;
		if (t.beginPath(), 0 == h) {
			for (var c = n / o + .5 << 0, l = 0; l < c; l++) t.rect(e, r, s, 1), e += o;
			t.fill()
		} else {
			var c = Math.sqrt(n * n + h * h) / o + .5 << 0;
			n /= c, h /= c;
			for (var u = n * s / o, _ = h * s / o, l = 0; l < c; l++) t.moveTo(e + .5, r + .5), t.lineTo(e + .5 + u, r + .5 + _), e += n, r += h;
			t.stroke()
		}
	}, we.createHorzDashedLine = function(t, e, r, a, i, o) {
		i < 2 && (i = 2);
		for (var s = r - e, n = s / i + .5 << 0, h = 0; h < n; h++) t.rect(e, a, o, 1), e += i
	}, we.createRectangles = function(t, e) {
		t.beginPath();
		var r, a, i = e.length;
		for (a = 0; a < i; a++) r = e[a], t.rect(r.x, r.y, r.w, r.h)
	}, we.createPolygon = function(t, e) {
		t.beginPath(), t.moveTo(e[0].x + .5, e[0].y + .5);
		var r, a = e.length;
		for (r = 1; r < a; r++) t.lineTo(e[r].x + .5, e[r].y + .5);
		t.closePath()
	}, we.drawString = function(t, e, r) {
		var a = t.measureText(e).width;
		return !(r.w < a) && (t.fillText(e, r.x, r.y+12), r.x += a, r.w -= a, !0)
	};
	var xe = r(we);
	xe.prototype.__construct = function(t) {
		xe.__super.__construct.call(this, t), this._color = de.Color.Background
	}, xe.prototype.getColor = function() {
		return this._color
	}, xe.prototype.setColor = function(t) {
		this._color = t
	}, xe.prototype.Draw = function(t) {
		var e = Zt.getInstance(),
			r = e.getArea(this.getAreaName()),
			a = e.getTheme(this.getFrameName());
		t.fillStyle = a.getColor(this._color), t.fillRect(r.getLeft(), r.getTop(), r.getWidth(), r.getHeight())
	};
	var Ce = r(xe);
	Ce.prototype.__construct = function(t) {
		Ce.__super.__construct.call(this, t)
	}, Ce.prototype.Draw = function(t) {
		var e = Zt.getInstance(),
			r = e.getArea(this.getAreaName()),
			a = e.getTimeline(this.getDataSourceName()),
			i = e.getRange(this.getAreaName()),
			o = e.getTheme(this.getFrameName()),
			s = r.getRect();
		if (!r.isChanged() && !a.isUpdated() && !i.isUpdated()) {
			var n = a.getFirstIndex(),
				h = a.getLastIndex() - 2,
				c = Math.max(n, h);
			s.X = a.toColumnLeft(c), s.Width = r.getRight() - s.X
		}
		t.fillStyle = o.getColor(this._color), t.fillRect(s.X, s.Y, s.Width, s.Height)
	};
	var Pe = r(xe);
	Pe.prototype.__construct = function(t) {
		Pe.__super.__construct.call(this, t)
	}, Pe.prototype.Draw = function(t) {
		var e = Zt.getInstance(),
			r = this.getAreaName(),
			a = e.getArea(r),
			i = e.getRange(r.substring(0, r.lastIndexOf("Range"))),
			o = "main" == i.getNameObject().getCompAt(2);
		if (o);
		else if (!a.isChanged() && !i.isUpdated()) return;
		var s = e.getTheme(this.getFrameName());
		t.fillStyle = s.getColor(this._color), t.fillRect(a.getLeft(), a.getTop(), a.getWidth(), a.getHeight())
	};
	var be = r(xe);
	be.prototype.__construct = function(t) {
		be.__super.__construct.call(this, t)
	}, be.prototype.Draw = function(t) {
		var e = Zt.getInstance(),
			r = e.getArea(this.getAreaName()),
			a = e.getTimeline(this.getDataSourceName());
		if (r.isChanged() || a.isUpdated()) {
			var i = e.getTheme(this.getFrameName());
			t.fillStyle = i.getColor(this._color), t.fillRect(r.getLeft(), r.getTop(), r.getWidth(), r.getHeight())
		}
	};
	var Se = r(Xt);
	Se.prototype.__construct = function(t) {
		Se.__super.__construct.call(this, t)
	}, Se.prototype.Draw = function(t) {
		var e = Zt.getInstance(),
			r = e.getArea(this.getAreaName()),
			a = e.getTimeline(this.getDataSourceName()),
			i = e.getRange(this.getAreaName()),
			o = !1;
		if (!r.isChanged() && !a.isUpdated() && !i.isUpdated()) {
			var s = a.getFirstIndex(),
				n = a.getLastIndex(),
				h = Math.max(s, n - 2),
				c = a.toColumnLeft(h);
			t.save(), t.rect(c, r.getTop(), r.getRight() - c, r.getHeight()), t.clip(), o = !0
		}
		var l = e.getTheme(this.getFrameName());
		t.fillStyle = l.getColor(de.Color.Grid0), t.beginPath();
		var u = 12,
			_ = 3;
		we.isChrome && (u = 4, _ = 1);
		var p = i.getGradations();
		for (var d in p) we.createHorzDashedLine(t, r.getLeft(), r.getRight(), i.toY(p[d]), u, _);
		t.fill(), o && t.restore()
	};
	var Me = r(Xt);
	Me.prototype.__construct = function(t) {
		Me.__super.__construct.call(this, t)
	}, Me.prototype.Draw = function(t) {
		var e = Zt.getInstance(),
			r = e.getDataSource(this.getDataSourceName());
		if (!(r.getDataCount() < 1)) {
			var i = e.getArea(this.getAreaName()),
				o = e.getTimeline(this.getDataSourceName()),
				s = e.getRange(this.getAreaName());
			if (0 != s.getRange()) {
				var n, h = e.getTheme(this.getFrameName()),
					c = a(h, ge),
					l = o.getFirstIndex(),
					u = o.getLastIndex();
				n = i.isChanged() || o.isUpdated() || s.isUpdated() ? l : Math.max(l, u - 2);
				for (var _ = o.getColumnWidth(), p = o.getItemWidth(), d = o.toItemLeft(n), g = o.toItemCenter(n), f = [], m = [], y = [], v = [], w = n; w < u; w++) {
					var x = r.getDataAt(w),
						C = s.toY(x.high),
						P = s.toY(x.low),
						b = x.open,
						S = x.close;
					if (S > b) {
						var M = s.toY(S),
							A = s.toY(b),
							I = Math.max(A - M, 1);
						I > 1 && p > 1 && c ? f.push({
							x: d + .5,
							y: M + .5,
							w: p - 1,
							h: I - 1
						}) : m.push({
							x: d,
							y: M,
							w: Math.max(p, 1),
							h: Math.max(I, 1)
						}), x.high > S && (C = Math.min(C, M - 1), m.push({
							x: g,
							y: C,
							w: 1,
							h: M - C
						})), b > x.low && (P = Math.max(P, A + 1), m.push({
							x: g,
							y: A,
							w: 1,
							h: P - A
						}))
					} else if (S == b) {
						var M = s.toY(S);
						y.push({
							x: d,
							y: M,
							w: Math.max(p, 1),
							h: 1
						}), x.high > S && (C = Math.min(C, M - 1)), b > x.low && (P = Math.max(P, M + 1)), C < P && y.push({
							x: g,
							y: C,
							w: 1,
							h: P - C
						})
					} else {
						var M = s.toY(b),
							A = s.toY(S),
							I = Math.max(A - M, 1);
						v.push({
							x: d,
							y: M,
							w: Math.max(p, 1),
							h: Math.max(I, 1)
						}), x.high > b && (C = Math.min(C, M - 1)), S > x.low && (P = Math.max(P, A + 1)), C < P && v.push({
							x: g,
							y: C,
							w: 1,
							h: P - C
						})
					}
					d += _, g += _
				}
				f.length > 0 && (t.strokeStyle = h.getColor(de.Color.Positive), we.createRectangles(t, f), t.stroke()), m.length > 0 && (t.fillStyle = h.getColor(de.Color.Positive), we.createRectangles(t, m), t.fill()), y.length > 0 && (t.fillStyle = h.getColor(de.Color.Negative), we.createRectangles(t, y), t.fill()), v.length > 0 && (t.fillStyle = h.getColor(de.Color.Negative), we.createRectangles(t, v), t.fill())
			}
		}
	};
	var Ae = r(we);
	Ae.prototype.__construct = function(t) {
		Ae.__super.__construct.call(this, t)
	}, Ae.prototype.Draw = function(t) {
		var e = Zt.getInstance(),
			r = e.getDataSource(this.getDataSourceName());
		if (a(r, he) && !(r.getDataCount() < 1)) {
			var i = e.getArea(this.getAreaName()),
				o = e.getTimeline(this.getDataSourceName()),
				s = e.getRange(this.getAreaName());
			if (0 != s.getRange()) {
				var n, h = e.getTheme(this.getFrameName()),
					c = a(h, ge),
					l = o.getFirstIndex(),
					u = o.getLastIndex();
				n = i.isChanged() || o.isUpdated() || s.isUpdated() ? l : Math.max(l, u - 2);
				for (var _ = o.getColumnWidth(), p = o.getItemWidth(), d = o.toItemLeft(n), g = o.toItemCenter(n), f = [], m = [], y = [], v = [], w = n; w < u; w++) {
					var x = r.getDataAt(w),
						C = s.toY(x.high),
						P = s.toY(x.low),
						b = x.open;
					w > 0 && (b = r.getDataAt(w - 1).close);
					var S = x.close;
					if (S > b) {
						var M = s.toY(S),
							A = s.toY(b),
							I = Math.max(A - M, 1);
						I > 1 && p > 1 && c ? f.push({
							x: d + .5,
							y: M + .5,
							w: p - 1,
							h: I - 1
						}) : m.push({
							x: d,
							y: M,
							w: Math.max(p, 1),
							h: Math.max(I, 1)
						}), x.high > S && (C = Math.min(C, M - 1), m.push({
							x: g,
							y: C,
							w: 1,
							h: M - C
						})), b > x.low && (P = Math.max(P, A + 1), m.push({
							x: g,
							y: A,
							w: 1,
							h: P - A
						}))
					} else if (S == b) {
						var M = s.toY(S);
						y.push({
							x: d,
							y: M,
							w: Math.max(p, 1),
							h: 1
						}), x.high > S && (C = Math.min(C, M - 1)), b > x.low && (P = Math.max(P, M + 1)), C < P && y.push({
							x: g,
							y: C,
							w: 1,
							h: P - C
						})
					} else {
						var M = s.toY(b),
							A = s.toY(S),
							I = Math.max(A - M, 1);
						v.push({
							x: d,
							y: M,
							w: Math.max(p, 1),
							h: Math.max(I, 1)
						}), x.high > b && (C = Math.min(C, M - 1)), S > x.low && (P = Math.max(P, A + 1)), C < P && v.push({
							x: g,
							y: C,
							w: 1,
							h: P - C
						})
					}
					d += _, g += _
				}
				f.length > 0 && (t.strokeStyle = h.getColor(de.Color.Positive), we.createRectangles(t, f), t.stroke()), m.length > 0 && (t.fillStyle = h.getColor(de.Color.Positive), we.createRectangles(t, m), t.fill()), y.length > 0 && (t.fillStyle = h.getColor(de.Color.Negative), we.createRectangles(t, y), t.fill()), v.length > 0 && (t.fillStyle = h.getColor(de.Color.Negative), we.createRectangles(t, v), t.fill())
			}
		}
	};
	var Ie = r(we);
	Ie.prototype.__construct = function(t) {
		Ie.__super.__construct.call(this, t)
	}, Ie.prototype.Draw = function(t) {
		var e = Zt.getInstance(),
			r = e.getDataSource(this.getDataSourceName());
		if (a(r, he) && !(r.getDataCount() < 1)) {
			var i = e.getArea(this.getAreaName()),
				o = e.getTimeline(this.getDataSourceName()),
				s = e.getRange(this.getAreaName());
			if (0 != s.getRange()) {
				var n, h = e.getTheme(this.getFrameName()),
					c = o.getFirstIndex(),
					l = o.getLastIndex();
				n = i.isChanged() || o.isUpdated() || s.isUpdated() ? c : Math.max(c, l - 2);
				for (var u = o.getColumnWidth(), _ = (o.getItemWidth() >> 1) + 1, p = o.toItemLeft(n) - 1, d = o.toItemCenter(n), g = d + 1, f = [], m = [], y = [], v = n; v < l; v++) {
					var w = r.getDataAt(v),
						x = s.toY(w.high),
						C = s.toY(w.low),
						P = Math.max(C - x, 1);
					if (w.close > w.open) {
						var b = s.toY(w.close),
							S = s.toY(w.open);
						f.push({
							x: d,
							y: x,
							w: 1,
							h: P
						}), f.push({
							x: p,
							y: S,
							w: _,
							h: 1
						}), f.push({
							x: g,
							y: b,
							w: _,
							h: 1
						})
					} else if (w.close == w.open) {
						var M = s.toY(w.close);
						m.push({
							x: d,
							y: x,
							w: 1,
							h: P
						}), m.push({
							x: p,
							y: M,
							w: _,
							h: 1
						}), m.push({
							x: g,
							y: M,
							w: _,
							h: 1
						})
					} else {
						var b = s.toY(w.open),
							S = s.toY(w.close);
						y.push({
							x: d,
							y: x,
							w: 1,
							h: P
						}), y.push({
							x: p,
							y: b,
							w: _,
							h: 1
						}), y.push({
							x: g,
							y: S,
							w: _,
							h: 1
						})
					}
					p += u, d += u, g += u
				}
				f.length > 0 && (t.fillStyle = h.getColor(de.Color.Positive), we.createRectangles(t, f), t.fill()), m.length > 0 && (t.fillStyle = h.getColor(de.Color.Negative), we.createRectangles(t, m), t.fill()), y.length > 0 && (t.fillStyle = h.getColor(de.Color.Negative), we.createRectangles(t, y), t.fill())
			}
		}
	};
	var De = r(we);
	De._dsAliasToString = {
		"01w": "周线",
		"03d": "3天线",
		"01d": "日线",
		"12h": "12小时",
		"06h": "6小时",
		"04h": "4小时",
		"02h": "2小时",
		"01h": "1小时",
		"30m": "30分钟",
		"15m": "15分钟",
		"05m": "5分钟",
		"03m": "3分钟",
		"01m": "1分钟"
	}, De._dsAliasToString2 = {
		"01w": "1w",
		"03d": "3d",
		"01d": "1d",
		"12h": "12h",
		"06h": "6h",
		"04h": "4h",
		"02h": "2h",
		"01h": "1h",
		"30m": "30m",
		"15m": "15m",
		"05m": "5m",
		"03m": "3m",
		"01m": "1m"
	}, De._dsAliasToString_zh_tw = {
		"01w": "周線",
		"03d": "3天線",
		"01d": "日線",
		"12h": "12小時",
		"06h": "6小時",
		"04h": "4小時",
		"02h": "2小時",
		"01h": "1小時",
		"30m": "30分鐘",
		"15m": "15分鐘",
		"05m": "5分鐘",
		"03m": "3分鐘",
		"01m": "1分鐘"
	}, De.prototype.__construct = function(t) {
		De.__super.__construct.call(this, t)
	}, De.prototype.Draw = function(t) {
		var e = Zt.getInstance(),
			r = e.getArea(this.getAreaName()),
			a = e.getTimeline(this.getDataSourceName()),
			i = e.getDataSource(this.getDataSourceName()),
			s = e.getTheme(this.getFrameName());
		t.font = s.getFont(de.Font.Default), t.textAlign = "left", t.textBaseline = "top", t.fillStyle = s.getColor(de.Color.Text4);
		var n = {
			x: r.getLeft() + 4,
			y: r.getTop() + 2,
			w: r.getWidth() - 8,
			h: 20
		},
			h = a.getSelectedIndex();
		if (!(h < 0)) {
			var c = i.getDataAt(h),
				l = i.getDecimalDigits(),
				u = new Date(c.date),
				_ = u.getFullYear(),
				p = o(u.getMonth() + 1),
				d = o(u.getDate()),
				g = o(u.getHours()),
				f = o(u.getMinutes()),
				m = e.getLanguage();
			if ("zh-cn" == m) {
				if (!we.drawString(t, "时间: " + _ + "-" + p + "-" + d + "  " + g + ":" + f, n)) return;
				if (!we.drawString(t, "  开: " + c.open.toFixed(6), n)) return;
				if (!we.drawString(t, "  高: " + c.high.toFixed(6), n)) return;
				if (!we.drawString(t, "  低: " + c.low.toFixed(6), n)) return;
				if (!we.drawString(t, "  收: " + c.close.toFixed(6), n)) return
			} else if ("en-us" == m) {
				if (!we.drawString(t, "DATE: " + _ + "-" + p + "-" + d + "  " + g + ":" + f, n)) return;
				if (!we.drawString(t, "  O: " + c.open.toFixed(6), n)) return;
				if (!we.drawString(t, "  H: " + c.high.toFixed(6), n)) return;
				if (!we.drawString(t, "  L: " + c.low.toFixed(6), n)) return;
				if (!we.drawString(t, "  C: " + c.close.toFixed(6), n)) return
			} else if ("zh-tw" == m) {
				if (!we.drawString(t, "時間: " + _ + "-" + p + "-" + d + "  " + g + ":" + f, n)) return;
				if (!we.drawString(t, "  開: " + c.open.toFixed(6), n)) return;
				if (!we.drawString(t, "  高: " + c.high.toFixed(6), n)) return;
				if (!we.drawString(t, "  低: " + c.low.toFixed(6), n)) return;
				if (!we.drawString(t, "  收: " + c.close.toFixed(6), n)) return
			}
			if (h > 0) {
				if ("zh-cn" == m) {
					if (!we.drawString(t, "  涨幅: ", n)) return
				} else if ("en-us" == m) {
					if (!we.drawString(t, "  CHANGE: ", n)) return
				} else if ("zh-tw" == m && !we.drawString(t, "  漲幅: ", n)) return;
				var y = i.getDataAt(h - 1),
					v = (c.close - y.close) / y.close * 100;
				if (v >= 0 ? (v = " " + v.toFixed(2), t.fillStyle = s.getColor(de.Color.TextPositive)) : (v = v.toFixed(2), t.fillStyle = s.getColor(de.Color.TextNegative)), !we.drawString(t, v, n)) return;
				if (t.fillStyle = s.getColor(de.Color.Text4), !we.drawString(t, " %", n)) return
			}
			var w = (c.high - c.low) / c.low * 100;
			if ("zh-cn" == m) {
				if (!we.drawString(t, "  振幅: " + w.toFixed(2) + " %", n)) return;
				if (!we.drawString(t, "  量: " + c.volume.toFixed(4), n)) return
			} else if ("en-us" == m) {
				if (!we.drawString(t, "  AMPLITUDE: " + w.toFixed(2) + " %", n)) return;
				if (!we.drawString(t, "  V: " + c.volume.toFixed(4), n)) return
			} else if ("zh-tw" == m) {
				if (!we.drawString(t, "  振幅: " + w.toFixed(2) + " %", n)) return;
				if (!we.drawString(t, "  量: " + c.volume.toFixed(4), n)) return
			}
			var x = e.getDataProvider(this.getAreaName() + ".secondary");
			if (void 0 != x) {
				var C, P = x.getIndicator(),
					b = P.getOutputCount();
				for (C = 0; C < b; C++) {
					var S = P.getOutputAt(C),
						M = S.execute(h);
					if (!isNaN(M)) {
						var A = "  " + S.getName() + ": " + M.toFixed(6),
							I = S.getColor();
						if (void 0 === I && (I = de.Color.Indicator0 + C), t.fillStyle = s.getColor(I), !we.drawString(t, A, n)) return
					}
				}
			}
		}
	};
	var Te = r(Xt);
	Te.prototype.__construct = function(t) {
		Te.__super.__construct.call(this, t)
	}, Te.prototype.Draw = function(t) {
		var e = Zt.getInstance(),
			r = e.getArea(this.getAreaName()),
			i = e.getTimeline(this.getDataSourceName()),
			o = e.getRange(this.getAreaName());
		if (0 != o.getRange()) {
			var s = e.getDataProvider(this.getName());
			if (a(s, ue)) {
				var n, h = e.getTheme(this.getFrameName()),
					c = i.getColumnWidth(),
					l = i.getFirstIndex(),
					u = i.getLastIndex();
				n = r.isChanged() || i.isUpdated() || o.isUpdated() ? l : Math.max(l, u - 2);
				var _, p, d = s.getIndicator(),
					g = d.getOutputCount();
				for (p = 0; p < g; p++) {
					_ = d.getOutputAt(p);
					var f = _.getStyle();
					f == it.VolumeStick ? this.drawVolumeStick(t, h, e.getDataSource(this.getDataSourceName()), n, u, i.toItemLeft(n), c, i.getItemWidth(), o) : f == it.MACDStick ? this.drawMACDStick(t, h, _, n, u, i.toItemLeft(n), c, i.getItemWidth(), o) : f == it.SARPoint && this.drawSARPoint(t, h, _, n, u, i.toItemCenter(n), c, i.getItemWidth(), o)
				}
				var m = i.toColumnLeft(n),
					y = i.toItemCenter(n);
				for (t.save(), t.rect(m, r.getTop(), r.getRight() - m, r.getHeight()), t.clip(), t.translate(.5, .5), p = 0; p < g; p++) {
					var v = y;
					if (_ = d.getOutputAt(p), _.getStyle() == it.Line) {
						var w, x = [];
						n > l && (w = _.execute(n - 1), 0 == isNaN(w) && x.push({
							x: v - c,
							y: o.toY(w)
						}));
						for (var C = n; C < u; C++, v += c) w = _.execute(C), 0 == isNaN(w) && x.push({
							x: v,
							y: o.toY(w)
						});
						if (x.length > 0) {
							var P = _.getColor();
							void 0 == P && (P = de.Color.Indicator0 + p), t.strokeStyle = h.getColor(P), we.drawLines(t, x)
						}
					}
				}
				t.restore()
			}
		}
	}, Te.prototype.drawVolumeStick = function(t, e, r, i, o, s, n, h, c) {
		for (var l = a(e, ge), u = s, _ = (c.toY(0), []), p = [], d = [], g = i; g < o; g++) {
			var f = r.getDataAt(g),
				m = c.toY(f.volume),
				y = c.toHeight(f.volume);
			f.close > f.open ? y > 1 && h > 1 && l ? _.push({
				x: u + .5,
				y: m + .5,
				w: h - 1,
				h: y - 1
			}) : p.push({
				x: u,
				y: m,
				w: Math.max(h, 1),
				h: Math.max(y, 1)
			}) : f.close == f.open && g > 0 && f.close >= r.getDataAt(g - 1).close ? y > 1 && h > 1 && l ? _.push({
				x: u + .5,
				y: m + .5,
				w: h - 1,
				h: y - 1
			}) : p.push({
				x: u,
				y: m,
				w: Math.max(h, 1),
				h: Math.max(y, 1)
			}) : d.push({
				x: u,
				y: m,
				w: Math.max(h, 1),
				h: Math.max(y, 1)
			}), u += n
		}
		_.length > 0 && (t.strokeStyle = e.getColor(de.Color.Positive), we.createRectangles(t, _), t.stroke()), p.length > 0 && (t.fillStyle = e.getColor(de.Color.Positive), we.createRectangles(t, p), t.fill()), d.length > 0 && (t.fillStyle = e.getColor(de.Color.Negative), we.createRectangles(t, d), t.fill())
	}, Te.prototype.drawMACDStick = function(t, e, r, a, i, o, s, n, h) {
		for (var c = o, l = h.toY(0), u = [], _ = [], p = [], d = [], g = a > 0 ? r.execute(a - 1) : NaN, f = a; f < i; f++) {
			var m = r.execute(f);
			if (m >= 0) {
				var y = h.toHeight(m);
				(0 == f || m >= g) && y > 1 && n > 1 ? u.push({
					x: c + .5,
					y: l - y + .5,
					w: n - 1,
					h: y - 1
				}) : p.push({
					x: c,
					y: l - y,
					w: Math.max(n, 1),
					h: Math.max(y, 1)
				})
			} else {
				var y = h.toHeight(-m);
				(0 == f || m >= g) && y > 1 && n > 1 ? _.push({
					x: c + .5,
					y: l + .5,
					w: n - 1,
					h: y - 1
				}) : d.push({
					x: c,
					y: l,
					w: Math.max(n, 1),
					h: Math.max(y, 1)
				})
			}
			g = m, c += s
		}
		u.length > 0 && (t.strokeStyle = e.getColor(de.Color.Positive), we.createRectangles(t, u), t.stroke()), _.length > 0 && (t.strokeStyle = e.getColor(de.Color.Negative), we.createRectangles(t, _), t.stroke()), p.length > 0 && (t.fillStyle = e.getColor(de.Color.Positive), we.createRectangles(t, p), t.fill()), d.length > 0 && (t.fillStyle = e.getColor(de.Color.Negative), we.createRectangles(t, d), t.fill())
	}, Te.prototype.drawSARPoint = function(t, e, r, a, i, o, s, n, h) {
		var c = n >> 1;
		c < .5 && (c = .5), c > 4 && (c = 4);
		var l = o,
			u = l + c,
			_ = 2 * Math.PI;
		t.save(), t.translate(.5, .5), t.strokeStyle = e.getColor(de.Color.Indicator3), t.beginPath();
		for (var p = a; p < i; p++) {
			var d = h.toY(r.execute(p));
			t.moveTo(u, d), t.arc(l, d, c, 0, _), l += s, u += s
		}
		t.stroke(), t.restore()
	};
	var ke = r(we);
	ke.prototype.__construct = function(t) {
		ke.__super.__construct.call(this, t)
	}, ke.prototype.Draw = function(t) {
		var e = Zt.getInstance(),
			r = e.getArea(this.getAreaName()),
			a = e.getTimeline(this.getDataSourceName()),
			i = e.getDataProvider(this.getAreaName() + ".secondary"),
			o = e.getTheme(this.getFrameName());
		t.font = o.getFont(de.Font.Default), t.textAlign = "left", t.textBaseline = "top", t.fillStyle = o.getColor(de.Color.Text4);
		var s, n = {
			x: r.getLeft() + 4,
			y: r.getTop() + 2,
			w: r.getWidth() - 8,
			h: 20
		},
			h = i.getIndicator();
		switch (h.getParameterCount()) {
		case 0:
			s = h.getName();
			break;
		case 1:
			s = h.getName() + "(" + h.getParameterAt(0).getValue() + ")";
			break;
		case 2:
			s = h.getName() + "(" + h.getParameterAt(0).getValue() + "," + h.getParameterAt(1).getValue() + ")";
			break;
		case 3:
			s = h.getName() + "(" + h.getParameterAt(0).getValue() + "," + h.getParameterAt(1).getValue() + "," + h.getParameterAt(2).getValue() + ")";
			break;
		case 4:
			s = h.getName() + "(" + h.getParameterAt(0).getValue() + "," + h.getParameterAt(1).getValue() + "," + h.getParameterAt(2).getValue() + "," + h.getParameterAt(3).getValue() + ")";
			break;
		default:
			return
		}
		if (we.drawString(t, s, n)) {
			var c = a.getSelectedIndex();
			if (!(c < 0)) {
				var l, u, _, p, d, g = h.getOutputCount();
				for (d = 0; d < g; d++) if (l = h.getOutputAt(d), u = l.execute(c), !isNaN(u) && (_ = "  " + l.getName() + ": " + u.toFixed(2), p = l.getColor(), void 0 === p && (p = de.Color.Indicator0 + d), t.fillStyle = o.getColor(p), !we.drawString(t, _, n))) return
			}
		}
	};
	var Ne = r(Xt);
	Ne.prototype.__construct = function(t) {
		Ne.__super.__construct.call(this, t)
	}, Ne.prototype.Draw = function(t) {
		var e = Zt.getInstance(),
			r = e.getDataSource(this.getDataSourceName());
		if (!(r.getDataCount() < 1)) {
			var a = e.getTimeline(this.getDataSourceName());
			if (!(a.getInnerWidth() < a.getColumnWidth())) {
				var i = e.getRange(this.getAreaName());
				if (0 != i.getRange()) {
					var o = e.getDataProvider(this.getAreaName() + ".main"),
						s = a.getFirstIndex(),
						n = s + a.getLastIndex() >> 1,
						h = e.getTheme(this.getFrameName());
					t.font = h.getFont(de.Font.Default), t.textBaseline = "middle", t.fillStyle = h.getColor(de.Color.Text4), t.strokeStyle = h.getColor(de.Color.Text4);
					var c = r.getDecimalDigits();
					this.drawMark(t, o.getMinValue(), c, i.toY(o.getMinValue()), s, n, o.getMinValueIndex(), a), this.drawMark(t, o.getMaxValue(), c, i.toY(o.getMaxValue()), s, n, o.getMaxValueIndex(), a)
				}
			}
		}
	}, Ne.prototype.drawMark = function(t, e, r, a, i, o, s, n) {
		var h, c, l, u;
		s > o ? (t.textAlign = "right", h = n.toItemCenter(s) - 4, c = h - 7, l = h - 3, u = c - 4) : (t.textAlign = "left", h = n.toItemCenter(s) + 4, c = h + 7, l = h + 3, u = c + 4), we.drawLine(t, h, a, c, a), we.drawLine(t, h, a, l, a + 2), we.drawLine(t, h, a, l, a - 2), t.fillText(String.fromFloat(e, r), u, a)
	};
	var Re = r(we);
	Re.prototype.__construct = function(t) {
		Re.__super.__construct.call(this, t)
	}, Re.TP_MINUTE = 6e4, Re.TP_HOUR = 60 * Re.TP_MINUTE, Re.TP_DAY = 24 * Re.TP_HOUR, Re.TIME_INTERVAL = [5 * Re.TP_MINUTE, 10 * Re.TP_MINUTE, 15 * Re.TP_MINUTE, 30 * Re.TP_MINUTE, Re.TP_HOUR, 2 * Re.TP_HOUR, 3 * Re.TP_HOUR, 6 * Re.TP_HOUR, 12 * Re.TP_HOUR, Re.TP_DAY, 2 * Re.TP_DAY], Re.MonthConvert = {
		1: "Jan.",
		2: "Feb.",
		3: "Mar.",
		4: "Apr.",
		5: "May.",
		6: "Jun.",
		7: "Jul.",
		8: "Aug.",
		9: "Sep.",
		10: "Oct.",
		11: "Nov.",
		12: "Dec."
	}, Re.prototype.Draw = function(t) {
		var e = Zt.getInstance(),
			r = e.getArea(this.getAreaName()),
			a = e.getTimeline(this.getDataSourceName());
		if (r.isChanged() || a.isUpdated()) {
			var i = e.getDataSource(this.getDataSourceName());
			if (!(i.getDataCount() < 2)) {
				var o, s = i.getDataAt(1).date - i.getDataAt(0).date,
					n = Re.TIME_INTERVAL.length;
				for (o = 0; o < n && !(s < Re.TIME_INTERVAL[o]); o++);
				for (; o < n && !(Re.TIME_INTERVAL[o] % s == 0 && Re.TIME_INTERVAL[o] / s * a.getColumnWidth() > 60); o++);
				var h = a.getFirstIndex(),
					c = a.getLastIndex(),
					l = new Date,
					u = 60 * l.getTimezoneOffset() * 1e3,
					_ = e.getTheme(this.getFrameName());
				t.font = _.getFont(de.Font.Default), t.textAlign = "center", t.textBaseline = "middle";
				for (var p = e.getLanguage(), d = [], g = r.getTop(), f = r.getMiddle(), m = h; m < c; m++) {
					var y = i.getDataAt(m).date,
						v = y - u,
						w = new Date(y),
						x = w.getFullYear(),
						C = w.getMonth() + 1,
						P = w.getDate(),
						b = w.getHours(),
						S = w.getMinutes(),
						M = "";
					if (o < n) {
						var A = Math.max(Re.TP_DAY, Re.TIME_INTERVAL[o]);
						if (v % A == 0)"zh-cn" == p ? M = C.toString() + "月" + P.toString() + "日" : "zh-tw" == p ? M = C.toString() + "月" + P.toString() + "日" : "en-us" == p && (M = Re.MonthConvert[C] + " " + P.toString()), t.fillStyle = _.getColor(de.Color.Text4);
						else if (v % Re.TIME_INTERVAL[o] == 0) {
							var I = S.toString();
							S < 10 && (I = "0" + I), M = b.toString() + ":" + I, t.fillStyle = _.getColor(de.Color.Text2)
						}
					} else 1 == P && b < s / Re.TP_HOUR && (1 == C ? (M = x.toString(), "zh-cn" == p ? M += "年" : "zh-tw" == p && (M += "年")) : "zh-cn" == p ? M = C.toString() + "月" : "zh-tw" == p ? M = C.toString() + "月" : "en-us" == p && (M = Re.MonthConvert[C]), t.fillStyle = _.getColor(de.Color.Text4));
					if (M.length > 0) {
						var D = a.toItemCenter(m);
						d.push({
							x: D,
							y: g,
							w: 1,
							h: 4
						}), t.fillText(M, D, f)
					}
				}
				d.length > 0 && (t.fillStyle = _.getColor(de.Color.Grid1), we.createRectangles(t, d), t.fill())
			}
		}
	};
	var Oe = r(Xt);
	Oe.prototype.__construct = function(t) {
		Oe.__super.__construct.call(this, t)
	}, Oe.prototype.getRequiredWidth = function(t, e) {
		var r = Zt.getInstance(),
			a = r.getTheme(this.getFrameName());
		return t.font = a.getFont(de.Font.Default), t.measureText((Math.floor(e) + .88).toString()).width + 16
	}, Oe.prototype.Draw = function(t) {
		var e = Zt.getInstance(),
			r = this.getAreaName(),
			a = e.getArea(r),
			i = r.substring(0, r.lastIndexOf("Range")),
			o = e.getRange(i);
		if (0 != o.getRange()) {
			var s = "main" == o.getNameObject().getCompAt(2);
			if (s);
			else if (!a.isChanged() && !o.isUpdated()) return;
			var n = o.getGradations();
			if (0 != n.length) {
				var h = a.getLeft(),
					c = a.getRight(),
					l = a.getCenter(),
					u = e.getTheme(this.getFrameName());
				t.font = u.getFont(de.Font.Default), t.textAlign = "center", t.textBaseline = "middle", t.fillStyle = u.getColor(de.Color.Text2);
				var _ = [];
				for (var p in n) {
					var d = o.toY(n[p]);
					_.push({
						x: h,
						y: d,
						w: 6,
						h: 1
					}), _.push({
						x: c - 6,
						y: d,
						w: 6,
						h: 1
					}), t.fillText(String.fromFloat(n[p], 2), l, d)
				}
				_.length > 0 && (t.fillStyle = u.getColor(de.Color.Grid1), we.createRectangles(t, _), t.fill())
			}
		}
	};
	var Le = r(Xt);
	Le.prototype.__construct = function(t) {
		Le.__super.__construct.call(this, t)
	}, Le.prototype.Draw = function(t) {
		return this._Draw_(t)
	}, Le.prototype._Draw_ = function(t) {
		if (0 != this.Update() && 0 != this.updateData()) {
			this.m_top = this.m_pArea.getTop(), this.m_bottom = this.m_pArea.getBottom(), this.m_left = this.m_pArea.getLeft(), this.m_right = this.m_pArea.getRight(), t.save(), t.rect(this.m_left, this.m_top, this.m_right - this.m_left, this.m_bottom - this.m_top), t.clip();
			var e = Zt.getInstance().getChart()._depthData;
			this.x_offset = 0, this.y_offset = 0;
			var r = {},
				a = {};
			r.x = this.m_left + e.array[this.m_ask_si].amounts * this.m_Step, r.y = this.m_pRange.toY(e.array[this.m_ask_si].rate), a.x = this.m_left + e.array[this.m_bid_si].amounts * this.m_Step, a.y = this.m_pRange.toY(e.array[this.m_bid_si].rate), Math.abs(r.y - a.y) < 1 && (this.y_offset = 1), this.x_offset = 1, this.DrawBackground(t), this.UpdatePoints(), this.FillBlack(t), this.DrawGradations(t), this.DrawLine(t), t.restore()
		}
	}, Le.prototype.DrawBackground = function(t) {
		t.fillStyle = this.m_pTheme.getColor(de.Color.Background), t.fillRect(this.m_left, this.m_top, this.m_right - this.m_left, this.m_bottom - this.m_top);
		var e = Zt.getInstance().getChart()._depthData;
		if (0 == this.m_mode) {
			var r = this.m_pRange.toY(e.array[this.m_ask_si].rate) - this.y_offset,
				a = this.m_pRange.toY(e.array[this.m_bid_si].rate) + this.y_offset,
				i = t.createLinearGradient(this.m_left, 0, this.m_right, 0);
			i.addColorStop(0, this.m_pTheme.getColor(de.Color.Background)), i.addColorStop(1, this.m_pTheme.getColor(de.Color.PositiveDark)), t.fillStyle = i, t.fillRect(this.m_left, this.m_top, this.m_right - this.m_left, r - this.m_top);
			var o = t.createLinearGradient(this.m_left, 0, this.m_right, 0);
			o.addColorStop(0, this.m_pTheme.getColor(de.Color.Background)), o.addColorStop(1, this.m_pTheme.getColor(de.Color.NegativeDark)), t.fillStyle = o, t.fillRect(this.m_left, a, this.m_right - this.m_left, this.m_bottom - a)
		} else if (1 == this.m_mode) {
			var i = t.createLinearGradient(this.m_left, 0, this.m_right, 0);
			i.addColorStop(0, this.m_pTheme.getColor(de.Color.Background)), i.addColorStop(1, this.m_pTheme.getColor(de.Color.PositiveDark)), t.fillStyle = i, t.fillRect(this.m_left, this.m_top, this.m_right - this.m_left, this.m_bottom - this.m_top)
		} else if (2 == this.m_mode) {
			var o = t.createLinearGradient(this.m_left, 0, this.m_right, 0);
			o.addColorStop(0, this.m_pTheme.getColor(de.Color.Background)), o.addColorStop(1, this.m_pTheme.getColor(de.Color.NegativeDark)), t.fillStyle = o, t.fillRect(this.m_left, this.m_top, this.m_right - this.m_left, this.m_bottom - this.m_top)
		}
	}, Le.prototype.DrawLine = function(t) {
		if (0 == this.m_mode || 1 == this.m_mode) {
			t.strokeStyle = this.m_pTheme.getColor(de.Color.Positive), t.beginPath(), t.moveTo(Math.floor(this.m_ask_points[0].x) + .5, Math.floor(this.m_ask_points[0].y) + .5);
			for (var e = 1; e < this.m_ask_points.length; e++) t.lineTo(Math.floor(this.m_ask_points[e].x) + .5, Math.floor(this.m_ask_points[e].y) + .5);
			t.stroke()
		}
		if (0 == this.m_mode || 2 == this.m_mode) {
			t.strokeStyle = this.m_pTheme.getColor(de.Color.Negative), t.beginPath(), t.moveTo(this.m_bid_points[0].x + .5, this.m_bid_points[0].y + .5);
			for (var e = 1; e < this.m_bid_points.length; e++) t.lineTo(this.m_bid_points[e].x + .5, this.m_bid_points[e].y + .5);
			t.stroke()
		}
	}, Le.prototype.UpdatePoints = function() {
		var t = Zt.getInstance().getChart()._depthData;
		this.m_ask_points = [];
		var e = {};
		e.x = Math.floor(this.m_left), e.y = Math.floor(this.m_pRange.toY(t.array[this.m_ask_si].rate) - this.y_offset), this.m_ask_points.push(e);
		for (var r = 0, a = this.m_ask_si; a >= this.m_ask_ei; a--) {
			var i = {},
				o = {};
			a == this.m_ask_si ? (i.x = Math.floor(this.m_left + t.array[a].amounts * this.m_Step + this.x_offset), i.y = Math.floor(this.m_pRange.toY(t.array[a].rate) - this.y_offset), this.m_ask_points.push(i), r = 1) : (i.x = Math.floor(this.m_left + t.array[a].amounts * this.m_Step + this.x_offset), i.y = Math.floor(this.m_ask_points[r].y), o.x = Math.floor(i.x), o.y = Math.floor(this.m_pRange.toY(t.array[a].rate) - this.y_offset), this.m_ask_points.push(i), r++, this.m_ask_points.push(o), r++)
		}
		this.m_bid_points = [];
		var s = {};
		s.x = Math.floor(this.m_left), s.y = Math.ceil(this.m_pRange.toY(t.array[this.m_bid_si].rate) + this.y_offset), this.m_bid_points.push(s);
		for (var n = 0, a = this.m_bid_si; a <= this.m_bid_ei; a++) {
			var i = {},
				o = {};
			a == this.m_bid_si ? (i.x = Math.floor(this.m_left + t.array[a].amounts * this.m_Step + this.x_offset), i.y = Math.ceil(this.m_pRange.toY(t.array[a].rate) + this.y_offset), this.m_bid_points.push(i), n = 1) : (i.x = Math.floor(this.m_left + t.array[a].amounts * this.m_Step + this.x_offset), i.y = Math.ceil(this.m_bid_points[n].y), o.x = Math.floor(i.x), o.y = Math.ceil(this.m_pRange.toY(t.array[a].rate) + this.x_offset), this.m_bid_points.push(i), n++, this.m_bid_points.push(o), n++)
		}
	}, Le.prototype.updateData = function() {
		var t = Zt.getInstance().getChart()._depthData;
		if (null == t.array) return !1;
		if (t.array.length < t.depth_count) return !1;
		var e = this.m_pRange.getOuterMinValue(),
			r = this.m_pRange.getOuterMaxValue();
		this.m_ask_si = t.asks_si, this.m_ask_ei = t.asks_si;
		for (var a = t.asks_si; a >= t.asks_ei && t.array[a].rate < r; a--) this.m_ask_ei = a;
		this.m_bid_si = t.bids_si, this.m_bid_ei = t.bids_si;
		for (var a = t.bids_si; a <= t.bids_ei && t.array[a].rate > e; a++) this.m_bid_ei = a;
		return this.m_ask_ei == this.m_ask_si ? this.m_mode = 2 : this.m_bid_ei == this.m_bid_si ? this.m_mode = 1 : this.m_mode = 0, this.m_Step = this.m_pArea.getWidth(), 0 == this.m_mode ? this.m_ask_ei == t.asks_ei && this.m_bid_ei == t.bids_ei ? this.m_Step /= Math.min(t.array[this.m_ask_ei].amounts, t.array[this.m_bid_ei].amounts) : this.m_ask_ei != t.asks_ei && this.m_bid_ei == t.bids_ei ? this.m_Step /= t.array[this.m_bid_ei].amounts : this.m_ask_ei == t.asks_ei && this.m_bid_ei != t.bids_ei ? this.m_Step /= t.array[this.m_ask_ei].amounts : this.m_ask_ei != t.asks_ei && this.m_bid_ei != t.bids_ei && (this.m_Step /= Math.max(t.array[this.m_ask_ei].amounts, t.array[this.m_bid_ei].amounts)) : 1 == this.m_mode ? this.m_Step /= t.array[this.m_ask_ei].amounts : 2 == this.m_mode && (this.m_Step /= t.array[this.m_bid_ei].amounts), !0
	}, Le.prototype.Update = function() {
		this.m_pMgr = Zt.getInstance();
		var t = this.getAreaName();
		if (this.m_pArea = this.m_pMgr.getArea(t), null == this.m_pArea) return !1;
		var e = t.substring(0, t.lastIndexOf("Range"));
		return this.m_pRange = this.m_pMgr.getRange(e), null != this.m_pRange && 0 != this.m_pRange.getRange() && (this.m_pTheme = this.m_pMgr.getTheme(this.getFrameName()), null != this.m_pTheme)
	}, Le.prototype.DrawGradations = function(t) {
		var e = Zt.getInstance(),
			r = this.getAreaName(),
			a = e.getArea(r),
			i = r.substring(0, r.lastIndexOf("Range")),
			o = e.getRange(i);
		if (0 != o.getRange()) {
			var s = o.getGradations();
			if (0 != s.length) {
				var n = a.getLeft(),
					h = a.getRight(),
					c = [];
				for (var l in s) {
					var u = o.toY(s[l]);
					c.push({
						x: n,
						y: u,
						w: 6,
						h: 1
					}), c.push({
						x: h - 6,
						y: u,
						w: 6,
						h: 1
					})
				}
				if (c.length > 0) {
					var _ = e.getTheme(this.getFrameName());
					t.fillStyle = _.getColor(de.Color.Grid1), we.createRectangles(t, c), t.fill()
				}
			}
		}
	}, Le.prototype.FillBlack = function(t) {
		var e = this.m_ask_points,
			r = this.m_bid_points,
			a = {},
			i = {};
		a.x = this.m_right, a.y = e[0].y, i.x = this.m_right, i.y = e[e.length - 1].y;
		var o = {},
			s = {};
		o.x = this.m_right, o.y = r[0].y - 1, s.x = this.m_right, s.y = r[r.length - 1].y, e.unshift(a), e.push(i), r.unshift(o), r.push(s), t.fillStyle = this.m_pTheme.getColor(de.Color.Background), t.beginPath(), t.moveTo(Math.floor(e[0].x) + .5, Math.floor(e[0].y) + .5);
		for (var n = 1; n < e.length; n++) t.lineTo(Math.floor(e[n].x) + .5, Math.floor(e[n].y) + .5);
		t.fill(), t.beginPath(), t.moveTo(Math.floor(r[0].x) + .5, Math.floor(r[0].y) + .5);
		for (var n = 1; n < r.length; n++) t.lineTo(Math.floor(r[n].x) + .5, Math.floor(r[n].y) + .5);
		t.fill(), e.shift(), e.pop(), r.shift(), r.pop()
	}, Le.prototype.DrawTickerGraph = function(t) {
		return
	};
	var $e = r(we);
	$e.prototype.__construct = function(t) {
		$e.__super.__construct.call(this, t)
	}, $e.prototype.Draw = function(t) {
		var e = Zt.getInstance(),
			r = (e.getTimeline(this.getDataSourceName()), this.getAreaName()),
			a = e.getArea(r),
			i = r.substring(0, r.lastIndexOf("Range")),
			o = e.getRange(i);
		if (0 != o.getRange()) {
			var s = e.getDataSource(this.getDataSourceName());
			if (!(s.getDataCount() < 1)) {
				var n = e.getTheme(this.getFrameName());
				t.font = n.getFont(de.Font.Default), t.textAlign = "left", t.textBaseline = "middle", t.fillStyle = n.getColor(de.Color.RangeMark), t.strokeStyle = n.getColor(de.Color.RangeMark);
				var h = s.getDataAt(s.getDataCount() - 1).volume,
					c = o.toY(h),
					l = a.getLeft() + 1;
				we.drawLine(t, l, c, l + 7, c), we.drawLine(t, l, c, l + 3, c + 2), we.drawLine(t, l, c, l + 3, c - 2), t.fillText(String.fromFloat(h, 2), l + 10, c)
			}
		}
	};
	var je = r(we);
	je.prototype.__construct = function(t) {
		je.__super.__construct.call(this, t)
	}, je.prototype.Draw = function(t) {
		var e = Zt.getInstance(),
			r = (e.getTimeline(this.getDataSourceName()), this.getAreaName()),
			a = e.getArea(r),
			i = r.substring(0, r.lastIndexOf("Range")),
			o = e.getRange(i);
		if (0 != o.getRange()) {
			var s = e.getDataSource(this.getDataSourceName());
			if (!(s.getDataCount() < 1)) {
				var n = s._dataItems[s._dataItems.length - 1].close;
				if (!(n <= o.getMinValue() || n >= o.getMaxValue())) {
					var h = e.getTheme(this.getFrameName());
					t.font = h.getFont(de.Font.Default), t.textAlign = "left", t.textBaseline = "middle", t.fillStyle = h.getColor(de.Color.RangeMark), t.strokeStyle = h.getColor(de.Color.RangeMark);
					var c = o.toY(n),
						l = a.getLeft() + 1;
					we.drawLine(t, l, c, l + 7, c), we.drawLine(t, l, c, l + 3, c + 2), we.drawLine(t, l, c, l + 3, c - 2), t.fillText(String.fromFloat(n, s.getDecimalDigits()), l + 10, c)
				}
			}
		}
	};
	var Fe = r(we);
	Fe.prototype.__construct = function(t) {
		Fe.__super.__construct.call(this, t)
	}, Fe.prototype.Draw = function(t) {
		var e = Zt.getInstance();
		if (e._drawingTool == Zt.DrawingTool.CrossCursor) {
			var r = e.getArea(this.getAreaName()),
				a = e.getTimeline(this.getDataSourceName());
			if (!(a.getSelectedIndex() < 0)) {
				var i = e.getRange(this.getAreaName()),
					o = e.getTheme(this.getFrameName());
				t.strokeStyle = o.getColor(de.Color.Cursor);
				var s = a.toItemCenter(a.getSelectedIndex());
				we.drawLine(t, s, r.getTop() - 1, s, r.getBottom());
				var n = i.getSelectedPosition();
				n >= 0 && we.drawLine(t, r.getLeft(), n, r.getRight(), n)
			}
		}
	};
	var Ve = r(Xt);
	Ve.MonthConvert = {
		1: "Jan.",
		2: "Feb.",
		3: "Mar.",
		4: "Apr.",
		5: "May.",
		6: "Jun.",
		7: "Jul.",
		8: "Aug.",
		9: "Sep.",
		10: "Oct.",
		11: "Nov.",
		12: "Dec."
	}, Ve.prototype.__construct = function(t) {
		Ve.__super.__construct.call(this, t)
	}, Ve.prototype.Draw = function(t) {
		var e = Zt.getInstance(),
			r = e.getArea(this.getAreaName()),
			i = e.getTimeline(this.getDataSourceName());
		if (!(i.getSelectedIndex() < 0)) {
			var o = e.getDataSource(this.getDataSourceName());
			if (a(o, he)) {
				var s = e.getTheme(this.getFrameName()),
					n = e.getLanguage(),
					h = i.toItemCenter(i.getSelectedIndex());
				t.fillStyle = s.getColor(de.Color.Background), t.fillRect(h - 52.5, r.getTop() + 2.5, 106, 18), t.strokeStyle = s.getColor(de.Color.Grid3), t.strokeRect(h - 52.5, r.getTop() + 2.5, 106, 18), t.font = s.getFont(de.Font.Default), t.textAlign = "center", t.textBaseline = "middle", t.fillStyle = s.getColor(de.Color.Text4);
				var c = new Date(o.getDataAt(i.getSelectedIndex()).date),
					l = c.getMonth() + 1,
					u = c.getDate(),
					_ = c.getHours(),
					p = c.getMinutes(),
					d = l.toString(),
					g = u.toString(),
					f = _.toString(),
					m = p.toString();
				p < 10 && (m = "0" + m);
				var y = "";
				"zh-cn" == n ? y = d + "月" + g + "日  " + f + ":" + m : "zh-tw" == n ? y = d + "月" + g + "日  " + f + ":" + m : "en-us" == n && (y = Ve.MonthConvert[l] + " " + g + "  " + f + ":" + m), t.fillText(y, h, r.getMiddle())
			}
		}
	};
	var Ye = r(Xt);
	Ye.prototype.__construct = function(t) {
		Ye.__super.__construct.call(this, t)
	}, Ye.prototype.Draw = function(t) {
		var e = Zt.getInstance(),
			r = this.getAreaName(),
			a = e.getArea(r),
			i = e.getTimeline(this.getDataSourceName());
		if (!(i.getSelectedIndex() < 0)) {
			var o = r.substring(0, r.lastIndexOf("Range")),
				s = e.getRange(o);
			if (!(0 == s.getRange() || s.getSelectedPosition() < 0)) {
				var n = s.getSelectedValue();
				if (n != -Number.MAX_VALUE) {
					var h = s.getSelectedPosition();
					we.createPolygon(t, [{
						x: a.getLeft(),
						y: h
					}, {
						x: a.getLeft() + 5,
						y: h + 10
					}, {
						x: a.getRight() - 3,
						y: h + 10
					}, {
						x: a.getRight() - 3,
						y: h - 10
					}, {
						x: a.getLeft() + 5,
						y: h - 10
					}]);
					var c = e.getTheme(this.getFrameName());
					t.fillStyle = c.getColor(de.Color.Background), t.fill(), t.strokeStyle = c.getColor(de.Color.Grid4), t.stroke(), t.font = c.getFont(de.Font.Default), t.textAlign = "center", t.textBaseline = "middle", t.fillStyle = c.getColor(de.Color.Text3);
					var l = 2;
					"main" == s.getNameObject().getCompAt(2) && (l = e.getDataSource(this.getDataSourceName()).getDecimalDigits()), t.fillText(String.fromFloat(n, l), a.getCenter(), h)
				}
			}
		}
	};
	var Be = {};
	Be.checkVersion = function() {
		var t = 1;
		if (t = 2, Be._data.ver < t) {
			Be._data.ver = 2;
			var e = Be._data.charts;
			e.period_weight = {}, e.period_weight.line = 8, e.period_weight[0] = 7, e.period_weight[1] = 6, e.period_weight[2] = 5, e.period_weight[9] = 4, e.period_weight[10] = 3, e.period_weight[3] = 2, e.period_weight[4] = 1, e.period_weight[7] = 0, e.period_weight[11] = 0, e.period_weight[12] = 0, e.period_weight[13] = 0, e.period_weight[14] = 0, e.period_weight[15] = 0
		}
		if (t = 3, Be._data.ver < t) {
			Be._data.ver = 3;
			var e = Be._data.charts;
			e.areaHeight = []
		}
	}, Be.get = function() {
		return void 0 == Be._data && (Be.init(), Be.load(), Be.checkVersion()), Be._data
	}, Be.init = function() {
		for (var t = {}, e = new Array("MA", "EMA", "VOLUME", "MACD", "KDJ", "StochRSI", "RSI", "DMI", "OBV", "BOLL", "DMA", "TRIX", "BRAR", "VR", "EMV", "WR", "ROC", "MTM", "PSY"), r = 0; r < e.length; r++) {
			var a = Zt.getInstance().createIndicatorAndRange("", e[r], !0);
			if (null != a) {
				t[e[r]] = [];
				for (var i = a.indic.getParameters(), o = 0; o < i.length; o++) t[e[r]].push(i[o])
			}
		}
		var s = "CandleStick",
			n = "MA",
			h = new Array("VOLUME", "MACD"),
			c = "15m",
			l = {};
		l.chartStyle = s, l.mIndic = n, l.indics = h, l.indicsStatus = "close", l.period = c, Be._data = {
			ver: 1,
			charts: l,
			indics: t,
			theme: "Dark"
		}, Be.checkVersion()
	}, Be.load = function() {
		if (!(document.cookie.length <= 0)) {
			var t = document.cookie.indexOf("chartSettings=");
			if (t != -1) {
				t += "chartSettings=".length;
				var e = document.cookie.indexOf(";", t);
				e == -1 && (e = document.cookie.length);
				var r = unescape(document.cookie.substring(t, e));
				Be._data = JSON.parse(r)
			}
		}
	}, Be.save = function() {
		var t = new Date;
		t.setDate(t.getDate() + 365), document.cookie = "chartSettings=" + escape(JSON.stringify(Be._data)) + ";expires=" + t.toGMTString()
	};
	var Ee = r(Xt);
	Ee.state = {
		Hide: 0,
		Show: 1,
		Highlight: 2
	}, Ee.prototype.__construct = function(t) {
		Ee.__super.__construct.call(this, t), this.pos = {
			index: -1,
			value: -1
		}, this.state = Ee.state.Hide
	}, Ee.prototype.getChartObjects = function() {
		var t = Zt.getInstance(),
			e = t.getDataSource("frame0.k0");
		if (null == e || !a(e, he)) return null;
		var r = t.getTimeline("frame0.k0");
		if (null == r) return null;
		var i = t.getRange("frame0.k0.main");
		return null == i ? null : {
			pMgr: t,
			pCDS: e,
			pTimeline: r,
			pRange: i
		}
	}, Ee.prototype.setPosXY = function(t, e) {
		var r = this.getChartObjects(),
			a = r.pTimeline.toIndex(t),
			i = r.pRange.toValue(e),
			o = this.snapValue(a, i);
		null != o && (i = o), this.setPosIV(a, i)
	}, Ee.prototype.setPosXYNoSnap = function(t, e) {
		var r = this.getChartObjects(),
			a = r.pTimeline.toIndex(t),
			i = r.pRange.toValue(e);
		this.setPosIV(a, i)
	}, Ee.prototype.setPosIV = function(t, e) {
		this.pos = {
			index: t,
			value: e
		}
	}, Ee.prototype.getPosXY = function() {
		var t = this.getChartObjects(),
			e = t.pTimeline.toItemCenter(this.pos.index),
			r = t.pRange.toY(this.pos.value);
		return {
			x: e,
			y: r
		}
	}, Ee.prototype.getPosIV = function() {
		return {
			i: this.pos.index,
			v: this.pos.value
		}
	}, Ee.prototype.setState = function(t) {
		this.state = t
	}, Ee.prototype.getState = function() {
		return this.state
	}, Ee.prototype.isSelected = function(t, e) {
		var r = this.getPosXY();
		return !(t < r.x - 4 || t > r.x + 4 || e < r.y - 4 || e > r.y + 4) && (this.setState(Ee.state.Highlight), !0)
	}, Ee.prototype.snapValue = function(t, e) {
		var r = this.getChartObjects(),
			a = null,
			i = Math.floor(r.pTimeline.getFirstIndex()),
			o = Math.floor(r.pTimeline.getLastIndex());
		if (t < i || t > o) return a;
		var s = r.pRange.toY(e),
			n = r.pCDS.getDataAt(t);
		if (null == n || void 0 == n) return a;
		var h = null;
		h = t > 0 ? r.pCDS.getDataAt(t - 1) : r.pCDS.getDataAt(t);
		var c = r.pMgr.getChartStyle(r.pCDS.getFrameName()),
			l = r.pRange.toY(n.open),
			u = r.pRange.toY(n.high),
			_ = r.pRange.toY(n.low),
			p = r.pRange.toY(n.close);
		"CandleStickHLC" === c && (l = r.pRange.toY(h.close));
		var d = Math.abs(l - s),
			g = Math.abs(u - s),
			f = Math.abs(_ - s),
			m = Math.abs(p - s);
		return d <= g && d <= f && d <= m && d < 6 && (a = n.open), g <= d && g <= f && g <= m && g < 6 && (a = n.high), f <= d && f <= g && f <= m && f < 6 && (a = n.low), m <= d && m <= g && m <= f && m < 6 && (a = n.close), a
	};
	var Xe = r(Xt);
	Xe.state = {
		BeforeDraw: 0,
		Draw: 1,
		AfterDraw: 2
	}, Xe.prototype.__construct = function(t) {
		Xe.__super.__construct.call(this, t), this.drawer = null, this.state = Xe.state.BeforeDraw, this.points = [], this.step = 0
	}, Xe.prototype.getChartObjects = function() {
		var t = Zt.getInstance(),
			e = t.getDataSource("frame0.k0");
		if (null == e || !a(e, he)) return null;
		var r = t.getTimeline("frame0.k0");
		if (null == r) return null;
		var i = t.getArea("frame0.k0.main");
		if (null == i) return null;
		var o = t.getRange("frame0.k0.main");
		return null == o ? null : {
			pMgr: t,
			pCDS: e,
			pTimeline: r,
			pArea: i,
			pRange: o
		}
	}, Xe.prototype.isValidMouseXY = function(t, e) {
		var r = this.getChartObjects(),
			a = {
				left: r.pArea.getLeft(),
				top: r.pArea.getTop(),
				right: r.pArea.getRight(),
				bottom: r.pArea.getBottom()
			};
		return !(t < a.left || t > a.right || e < a.top || e > a.bottom)
	}, Xe.prototype.getPlotter = function() {
		return this.drawer
	}, Xe.prototype.setState = function(t) {
		this.state = t
	}, Xe.prototype.getState = function() {
		return this.state
	}, Xe.prototype.addPoint = function(t) {
		this.points.push(t)
	}, Xe.prototype.getPoint = function(t) {
		return this.points[t]
	}, Xe.prototype.acceptMouseMoveEvent = function(t, e) {
		return 0 != this.isValidMouseXY(t, e) && (this.state == Xe.state.BeforeDraw ? this.setBeforeDrawPos(t, e) : this.state == Xe.state.Draw ? this.setDrawPos(t, e) : this.state == Xe.state.AfterDraw && this.setAfterDrawPos(t, e), !0)
	}, Xe.prototype.acceptMouseDownEvent = function(t, e) {
		return 0 != this.isValidMouseXY(t, e) && (this.state == Xe.state.BeforeDraw ? (this.setDrawPos(t, e), this.setState(Xe.state.Draw)) : this.state == Xe.state.Draw ? (this.setAfterDrawPos(t, e), 0 == this.step && this.setState(Xe.state.AfterDraw)) : this.state == Xe.state.AfterDraw && (Xe.prototype.isSelected.call(this, t, e) ? (this.setDrawPos(t, e), this.setState(Xe.state.Draw)) : (this.oldx = t, this.oldy = e)), !0)
	}, Xe.prototype.acceptMouseDownMoveEvent = function(t, e) {
		if (0 == this.isValidMouseXY(t, e)) return !1;
		if (this.state == Xe.state.Draw) this.setDrawPos(t, e);
		else if (this.state == Xe.state.AfterDraw) {
			var r = this.getChartObjects(),
				a = r.pTimeline.getItemWidth();
			r.pRange;
			if (Math.abs(t - this.oldx) < a && 0 == Math.abs(e - this.oldy)) return !0;
			var i = r.pTimeline.toIndex(this.oldx),
				o = r.pRange.toValue(this.oldy),
				s = r.pTimeline.toIndex(t),
				n = r.pRange.toValue(e);
			this.oldx = t, this.oldy = e;
			var h = s - i,
				c = n - o;
			for (var l in this.points) this.points[l].pos.index += h, this.points[l].pos.value += c
		}
		return !0
	}, Xe.prototype.acceptMouseUpEvent = function(t, e) {
		return 0 != this.isValidMouseXY(t, e) && (this.state == Xe.state.Draw && (this.setAfterDrawPos(t, e), 0 == this.step && this.setState(Xe.state.AfterDraw), !0))
	}, Xe.prototype.setBeforeDrawPos = function(t, e) {
		for (var r in this.points) this.points[r].setPosXY(t, e), this.points[r].setState(Ee.state.Show)
	}, Xe.prototype.setDrawPos = function(t, e) {
		for (var r in this.points) this.points[r].getState() == Ee.state.Highlight && this.points[r].setPosXY(t, e)
	}, Xe.prototype.setAfterDrawPos = function(t, e) {
		0 != this.step && (this.step -= 1);
		for (var r in this.points) this.points[r].setState(Ee.state.Hide);
		if (0 == this.step) {
			var a = this.getChartObjects();
			a.pMgr.setNormalMode()
		}
	}, Xe.prototype.isSelected = function(t, e) {
		var r = !1;
		for (var a in this.points) if (this.points[a].isSelected(t, e)) {
			this.points[a].setState(Ee.state.Highlight), r = !0;
			break
		}
		return 1 == r && (this.select(), !0)
	}, Xe.prototype.select = function() {
		for (var t in this.points) this.points[t].getState() == Ee.state.Hide && this.points[t].setState(Ee.state.Show)
	}, Xe.prototype.unselect = function() {
		for (var t in this.points) this.points[t].getState() != Ee.state.Hide && this.points[t].setState(Ee.state.Hide)
	}, Xe.prototype.calcDistance = function(t, e, r) {
		var a = t.getPosXY().x,
			i = t.getPosXY().y,
			o = e.getPosXY().x,
			s = e.getPosXY().y,
			n = r.getPosXY().x,
			h = r.getPosXY().y,
			c = a - n,
			l = i - h,
			u = o - n,
			_ = s - h,
			p = Math.abs(c * _ - l * u),
			d = Math.sqrt(Math.pow(o - a, 2) + Math.pow(s - i, 2));
		return p / d
	}, Xe.prototype.calcGap = function(t, e, r) {
		var a = t.sx,
			i = t.sy,
			o = t.ex,
			s = t.ey,
			n = e,
			h = r,
			c = a - n,
			l = i - h,
			u = o - n,
			_ = s - h,
			p = Math.abs(c * _ - l * u),
			d = Math.sqrt(Math.pow(o - a, 2) + Math.pow(s - i, 2));
		return p / d
	}, Xe.prototype.isWithRect = function(t, e, r) {
		var a = t.getPosXY().x,
			i = t.getPosXY().y,
			o = e.getPosXY().x,
			s = e.getPosXY().y,
			n = r.getPosXY().x,
			h = r.getPosXY().y;
		return a > o ? (a += 4, o -= 4) : (a -= 4, o += 4), i > s ? (i += 4, s -= 4) : (i -= 4, s += 4), a <= n && o >= n && i <= h && s >= h || (a >= n && o <= n && i <= h && s >= h || (a <= n && o >= n && i >= h && s <= h || a >= n && o <= n && i >= h && s <= h))
	}, CBiToolObject = r(Xe), CBiToolObject.prototype.__construct = function(t) {
		CBiToolObject.__super.__construct.call(this, t), this.addPoint(new Ee(t)), this.addPoint(new Ee(t))
	}, CBiToolObject.prototype.setBeforeDrawPos = function(t, e) {
		this.step = 1, CBiToolObject.__super.setBeforeDrawPos.call(this, t, e), this.getPoint(0).setState(Ee.state.Show), this.getPoint(1).setState(Ee.state.Highlight)
	}, CTriToolObject = r(Xe), CTriToolObject.prototype.__construct = function(t) {
		CTriToolObject.__super.__construct.call(this, t), this.addPoint(new Ee(t)), this.addPoint(new Ee(t)), this.addPoint(new Ee(t))
	}, CTriToolObject.prototype.setBeforeDrawPos = function(t, e) {
		this.step = 2, CBiToolObject.__super.setBeforeDrawPos.call(this, t, e), this.getPoint(0).setState(Ee.state.Show), this.getPoint(1).setState(Ee.state.Show), this.getPoint(2).setState(Ee.state.Highlight)
	}, CTriToolObject.prototype.setAfterDrawPos = function(t, e) {
		if (0 != this.step && (this.step -= 1), 0 == this.step) for (var r in this.points) this.points[r].setState(Ee.state.Hide);
		else this.getPoint(0).setState(Ee.state.Show), this.getPoint(1).setState(Ee.state.Highlight), this.getPoint(2).setState(Ee.state.Show);
		if (0 == this.step) {
			var a = this.getChartObjects();
			a.pMgr.setNormalMode()
		}
	};
	var He = r(CBiToolObject);
	He.prototype.__construct = function(t) {
		He.__super.__construct.call(this, t), this.drawer = new Cr(t, this)
	}, He.prototype.isSelected = function(t, e) {
		if (1 == He.__super.isSelected.call(this, t, e)) return !0;
		var r = new Ee("frame0.k0");
		r.setPosXY(t, e);
		for (var a = (this.getPoint(0).getPosXY().x, this.getPoint(0).getPosXY().y), i = (this.getPoint(1).getPosXY().x, this.getPoint(1).getPosXY().y), o = [100, 87.5, 75, 62.5, 50, 37.5, 25, 12.5, 0], s = 0; s < o.length; s++) {
			var n = a + (100 - o[s]) / 100 * (i - a);
			if (n < e + 4 && n > e - 4) return this.select(), !0
		}
		return !1
	};
	var Ue = r(CTriToolObject);
	Ue.prototype.__construct = function(t) {
		Ue.__super.__construct.call(this, t), this.drawer = new mr(t, this)
	}, Ue.prototype.isSelected = function(t, e) {
		if (1 == er.__super.isSelected.call(this, t, e)) return !0;
		var r = this.getPoint(0).getPosXY().x,
			a = this.getPoint(0).getPosXY().y,
			i = this.getPoint(1).getPosXY().x,
			o = this.getPoint(1).getPosXY().y,
			s = this.getPoint(2).getPosXY().x,
			n = this.getPoint(2).getPosXY().y,
			h = {
				x: r - i,
				y: a - o
			},
			c = {
				x: r - s,
				y: a - n
			},
			l = {
				x: h.x + c.x,
				y: h.y + c.y
			},
			u = r - l.x,
			_ = a - l.y,
			p = {
				sx: r,
				sy: a,
				ex: s,
				ey: n
			},
			d = {
				sx: i,
				sy: o,
				ex: u,
				ey: _
			};
		return !(this.calcGap(p, t, e) > 4 && this.calcGap(d, t, e) > 4)
	};
	var We = r(CTriToolObject);
	We.prototype.__construct = function(t) {
		We.__super.__construct.call(this, t), this.drawer = new yr(t, this)
	}, We.prototype.isSelected = function(t, e) {
		if (1 == er.__super.isSelected.call(this, t, e)) return !0;
		var r = this.getPoint(0).getPosXY().x,
			a = this.getPoint(0).getPosXY().y,
			i = this.getPoint(1).getPosXY().x,
			o = this.getPoint(1).getPosXY().y,
			s = this.getPoint(2).getPosXY().x,
			n = this.getPoint(2).getPosXY().y,
			h = {
				x: r - i,
				y: a - o
			},
			c = {
				x: r - s,
				y: a - n
			},
			l = {
				x: h.x + c.x,
				y: h.y + c.y
			},
			u = r - l.x,
			_ = a - l.y,
			p = {
				sx: r,
				sy: a,
				ex: s,
				ey: n
			},
			d = {
				sx: i,
				sy: o,
				ex: u,
				ey: _
			};
		return (p.ex > p.sx && t > p.sx - 4 || p.ex < p.sx && t < p.sx + 4 || d.ex > d.sx && t > d.sx - 4 || d.ex < d.sx && t < d.sx + 4) && (!(this.calcGap(p, t, e) > 4 && this.calcGap(d, t, e) > 4) && (this.select(), !0))
	};
	var Ge = r(CBiToolObject);
	Ge.prototype.__construct = function(t) {
		Ge.__super.__construct.call(this, t), this.drawer = new Pr(t, this)
	}, Ge.prototype.isSelected = function(t, e) {
		if (1 == Ge.__super.isSelected.call(this, t, e)) return !0;
		var r = new Ee("frame0.k0");
		r.setPosXY(t, e);
		for (var a = this.getPoint(0).getPosXY().x, i = this.getPoint(0).getPosXY().y, o = this.getPoint(1).getPosXY().x, s = this.getPoint(1).getPosXY().y, n = this.getChartObjects(), h = {
			left: n.pArea.getLeft(),
			top: n.pArea.getTop(),
			right: n.pArea.getRight(),
			bottom: n.pArea.getBottom()
		}, c = [0, 38.2, 50, 61.8], l = 0; l < c.length; l++) {
			var u = i + (100 - c[l]) / 100 * (s - i),
				_ = {
					x: a,
					y: i
				},
				p = {
					x: o,
					y: u
				},
				g = d(h, _, p),
				f = Math.pow(g[0].x - a, 2) + Math.pow(g[0].y - i, 2),
				m = Math.pow(g[0].x - o, 2) + Math.pow(g[0].y - s, 2),
				y = f > m ? {
					x: g[0].x,
					y: g[0].y
				} : {
					x: g[1].x,
					y: g[1].y
				};
			if (!(y.x > a && t < a || y.x < a && t > a)) {
				var v = new Ee("frame0.k0");
				v.setPosXY(a, i);
				var w = new Ee("frame0.k0");
				if (w.setPosXY(y.x, y.y), !(this.calcDistance(v, w, r) > 4)) return this.select(), !0
			}
		}
		return !1
	};
	var ze = r(CBiToolObject);
	ze.prototype.__construct = function(t) {
		ze.__super.__construct.call(this, t), this.drawer = new xr(t, this)
	}, ze.prototype.isSelected = function(t, e) {
		if (1 == ze.__super.isSelected.call(this, t, e)) return !0;
		var r = new Ee("frame0.k0");
		r.setPosXY(t, e);
		for (var a = (this.getPoint(0).getPosXY().x, this.getPoint(0).getPosXY().y), i = (this.getPoint(1).getPosXY().x, this.getPoint(1).getPosXY().y), o = [100, 78.6, 61.8, 50, 38.2, 23.6, 0], s = 0; s < o.length; s++) {
			var n = a + (100 - o[s]) / 100 * (i - a);
			if (n < e + 4 && n > e - 4) return this.select(), !0
		}
		return !1
	};
	var qe = r(CBiToolObject);
	qe.prototype.__construct = function(t) {
		qe.__super.__construct.call(this, t), this.drawer = new _r(t, this)
	}, qe.prototype.setDrawPos = function(t, e) {
		return this.points[0].getState() == Ee.state.Highlight ? (this.points[0].setPosXY(t, e), void this.points[1].setPosXYNoSnap(this.points[1].getPosXY().x, this.points[0].getPosXY().y)) : void(this.points[1].getState() == Ee.state.Highlight && (this.points[1].setPosXY(t, e), this.points[0].setPosXYNoSnap(this.points[0].getPosXY().x, this.points[1].getPosXY().y)))
	}, qe.prototype.isSelected = function(t, e) {
		if (1 == qe.__super.isSelected.call(this, t, e)) return !0;
		var r = new Ee("frame0.k0");
		r.setPosXY(t, e);
		var a = this.getPoint(0).getPosXY().y,
			i = this.getPoint(0).getPosXY().x,
			o = this.getPoint(1).getPosXY().x;
		return !(e > a + 4 || e < a - 4) && (!(o > i && t < i - 4) && (!(o < i && t > i + 4) && (this.select(), !0)))
	};
	var Ke = r(CBiToolObject);
	Ke.prototype.__construct = function(t) {
		Ke.__super.__construct.call(this, t), this.drawer = new pr(t, this)
	}, Ke.prototype.setDrawPos = function(t, e) {
		return this.points[0].getState() == Ee.state.Highlight ? (this.points[0].setPosXY(t, e), void this.points[1].setPosXYNoSnap(this.points[1].getPosXY().x, this.points[0].getPosXY().y)) : void(this.points[1].getState() == Ee.state.Highlight && (this.points[1].setPosXY(t, e), this.points[0].setPosXYNoSnap(this.points[0].getPosXY().x, this.points[1].getPosXY().y)))
	}, Ke.prototype.isSelected = function(t, e) {
		if (1 == Ke.__super.isSelected.call(this, t, e)) return !0;
		var r = new Ee("frame0.k0");
		r.setPosXY(t, e);
		var a = this.getPoint(0).getPosXY().y,
			i = this.getPoint(0).getPosXY().x,
			o = this.getPoint(1).getPosXY().x;
		return !(e > a + 4 || e < a - 4) && (!(i > o && (t > i + 4 || t < o - 4)) && (!(i < o && (t < i - 4 || t > o + 4)) && (this.select(), !0)))
	};
	var Je = r(CBiToolObject);
	Je.prototype.__construct = function(t) {
		Je.__super.__construct.call(this, t), this.drawer = new ur(t, this)
	}, Je.prototype.setDrawPos = function(t, e) {
		for (var r in this.points) this.points[r].setPosXY(t, e)
	}, Je.prototype.isSelected = function(t, e) {
		if (1 == Je.__super.isSelected.call(this, t, e)) return !0;
		var r = new Ee("frame0.k0");
		r.setPosXY(t, e);
		var a = this.getPoint(0).getPosXY().y;
		return !(e > a + 4 || e < a - 4) && (this.select(), !0)
	};
	var Qe = r(CBiToolObject);
	Qe.prototype.__construct = function(t) {
		Qe.__super.__construct.call(this, t), this.drawer = new cr(t, this)
	}, Qe.prototype.isSelected = function(t, e) {
		if (1 == Qe.__super.isSelected.call(this, t, e)) return !0;
		var r = new Ee("frame0.k0");
		r.setPosXY(t, e);
		var a = this.getPoint(0).getPosXY().x,
			i = this.getPoint(1).getPosXY().x;
		return !(i > a && t < a - 4) && (!(i < a && t > a + 4) && (this.calcDistance(this.getPoint(0), this.getPoint(1), r) < 4 && (this.select(), !0)))
	};
	var Ze = r(CBiToolObject);
	Ze.prototype.__construct = function(t) {
		Ze.__super.__construct.call(this, t), this.drawer = new hr(t, this)
	}, Ze.prototype.isSelected = function(t, e) {
		if (1 == Ze.__super.isSelected.call(this, t, e)) return !0;
		var r = new Ee("frame0.k0");
		return r.setPosXY(t, e), 0 != this.isWithRect(this.getPoint(0), this.getPoint(1), r) && (this.calcDistance(this.getPoint(0), this.getPoint(1), r) < 4 && (this.select(), !0))
	};
	var tr = r(CBiToolObject);
	tr.prototype.__construct = function(t) {
		tr.__super.__construct.call(this, t), this.drawer = new nr(t, this)
	}, tr.prototype.isSelected = function(t, e) {
		if (1 == tr.__super.isSelected.call(this, t, e)) return !0;
		var r = new Ee("frame0.k0");
		return r.setPosXY(t, e), this.calcDistance(this.getPoint(0), this.getPoint(1), r) < 4 && (this.select(), !0)
	};
	var er = r(CTriToolObject);
	er.prototype.__construct = function(t) {
		er.__super.__construct.call(this, t), this.drawer = new vr(t, this)
	}, er.prototype.isSelected = function(t, e) {
		if (1 == er.__super.isSelected.call(this, t, e)) return !0;
		var r = (this.getChartObjects(), this.getPoint(0).getPosXY().x),
			a = this.getPoint(0).getPosXY().y,
			i = this.getPoint(1).getPosXY().x,
			o = this.getPoint(1).getPosXY().y,
			s = this.getPoint(2).getPosXY().x,
			n = this.getPoint(2).getPosXY().y,
			h = {
				x: r - i,
				y: a - o
			},
			c = {
				x: r - s,
				y: a - n
			},
			l = {
				x: h.x + c.x,
				y: h.y + c.y
			},
			u = r - l.x,
			_ = a - l.y,
			p = {
				sx: r,
				sy: a,
				ex: s,
				ey: n
			},
			d = {
				sx: i,
				sy: o,
				ex: u,
				ey: _
			},
			g = {
				x: i - r,
				y: o - a
			},
			f = {
				x: u - s,
				y: _ - n
			},
			m = Math.abs(g.x - r),
			y = Math.abs(g.y - a),
			v = Math.abs(f.x - s),
			w = Math.abs(f.y - n),
			x = {
				sx: m,
				sy: y,
				ex: v,
				ey: w
			};
		return !(this.calcGap(p, t, e) > 4 && this.calcGap(d, t, e) > 4 && this.calcGap(x, t, e) > 4) && (this.select(), !0)
	};
	var rr = r(CBiToolObject);
	rr.prototype.__construct = function(t) {
		rr.__super.__construct.call(this, t), this.drawer = new dr(t, this)
	}, rr.prototype.setDrawPos = function(t, e) {
		for (var r in this.points) this.points[r].setPosXY(t, e)
	}, rr.prototype.isSelected = function(t, e) {
		if (1 == rr.__super.isSelected.call(this, t, e)) return !0;
		var r = new Ee("frame0.k0");
		r.setPosXY(t, e);
		var a = this.getPoint(0).getPosXY().x;
		return !(t > a + 4 || t < a - 4) && (this.select(), !0)
	};
	var ar = r(Ze);
	ar.prototype.__construct = function(t) {
		ar.__super.__construct.call(this, t), this.drawer = new gr(t, this)
	}, ar.prototype.setDrawPos = function(t, e) {
		for (var r in this.points) this.points[r].setPosXY(t, e)
	}, ar.prototype.isSelected = function(t, e) {
		if (1 == ze.__super.isSelected.call(this, t, e)) return !0;
		var r = new Ee("frame0.k0");
		r.setPosXY(t, e);
		var a = this.getPoint(0).getPosXY().x,
			i = this.getPoint(0).getPosXY().y;
		this.getPoint(1).getPosXY().x, this.getPoint(1).getPosXY().y;
		return !(t < a - 4) && (!(e >= i + 4 || e <= i - 4) && (this.select(), !0))
	};
	var ir = r(Ze);
	ir.prototype.__construct = function(t) {
		ir.__super.__construct.call(this, t), this.drawer = new lr(t, this)
	};
	var or = r(Xt);
	or.prototype.__construct = function(t) {
		or.__super.__construct.call(this, t), this.selectedObject = -1, this.toolObjects = []
	}, or.prototype.getToolObjectCount = function() {
		return this.toolObjects.length
	}, or.prototype.addToolObject = function(t) {
		this.toolObjects.push(t)
	}, or.prototype.getToolObject = function(t) {
		return t < this.toolObjects.length && t >= 0 ? this.toolObjects[t] : null
	}, or.prototype.getCurrentObject = function() {
		return this.getToolObject(this.getToolObjectCount() - 1)
	}, or.prototype.getSelectedObject = function() {
		return this.getToolObject(this.selectedObject)
	}, or.prototype.delCurrentObject = function() {
		this.toolObjects.splice(this.getToolObjectCount() - 1, 1)
	}, or.prototype.delSelectedObject = function() {
		this.toolObjects.splice(this.selectedObject, 1), this.selectedObject = -1
	}, or.prototype.acceptMouseMoveEvent = function(t, e) {
		if (this.selectedObject == -1) {
			var r = this.toolObjects[this.getToolObjectCount() - 1];
			if (null != r && r.getState() != Xe.state.AfterDraw) return r.acceptMouseMoveEvent(t, e)
		} else {
			var a = this.toolObjects[this.selectedObject];
			if (a.getState() == Xe.state.Draw) return a.acceptMouseMoveEvent(t, e);
			a.unselect(), this.selectedObject = -1
		}
		for (var i in this.toolObjects) if (this.toolObjects[i].isSelected(t, e)) return this.selectedObject = i, !1;
		return !1
	}, or.prototype.acceptMouseDownEvent = function(t, e) {
		if (this.mouseDownMove = !1, this.selectedObject == -1) {
			var r = this.toolObjects[this.getToolObjectCount() - 1];
			if (null != r && r.getState() != Xe.state.AfterDraw) return r.acceptMouseDownEvent(t, e)
		} else {
			var a = this.toolObjects[this.selectedObject];
			if (a.getState() != Xe.state.BeforeDraw) return a.acceptMouseDownEvent(t, e)
		}
		return !1
	}, or.prototype.acceptMouseDownMoveEvent = function(t, e) {
		if (this.mouseDownMove = !0, this.selectedObject == -1) {
			var r = this.toolObjects[this.getToolObjectCount() - 1];
			return null != r && r.getState() == Xe.state.Draw && r.acceptMouseDownMoveEvent(t, e)
		}
		var a = this.toolObjects[this.selectedObject];
		if (a.getState() != Xe.state.BeforeDraw) {
			if (1 == a.acceptMouseDownMoveEvent(t, e)) for (var i = this.toolObjects[this.selectedObject].points, o = 0; o < i.length; o++) if (i[o].state == Ee.state.Highlight || i[o].state == Ee.state.Show) return !0;
			return !0
		}
	}, or.prototype.acceptMouseUpEvent = function(t, e) {
		if (1 == this.mouseDownMove) {
			if (this.selectedObject == -1) {
				var r = this.toolObjects[this.getToolObjectCount() - 1];
				return null == r || r.getState() != Xe.state.Draw || r.acceptMouseUpEvent(t, e)
			}
			var a = this.toolObjects[this.selectedObject];
			if (a.getState() != Xe.state.BeforeDraw) return a.acceptMouseUpEvent(t, e)
		}
		if (this.selectedObject != -1) return !0;
		var r = this.toolObjects[this.getToolObjectCount() - 1];
		if (null != r) {
			if (r.getState() == Xe.state.Draw) return !0;
			if (!r.isValidMouseXY(t, e)) return !1;
			if (r.isSelected(t, e)) return !0
		}
		return !1
	};
	var sr = r(Xt);
	sr.prototype.__construct = function(t, e) {
		sr.__super.__construct.call(this, t), this.toolObject = e;
		var r = Zt.getInstance(),
			a = r.getArea("frame0.k0.main");
		return null == a ? void(this.areaPos = {
			left: 0,
			top: 0,
			right: 0,
			bottom: 0
		}) : (this.areaPos = {
			left: a.getLeft(),
			top: a.getTop(),
			right: a.getRight(),
			bottom: a.getBottom()
		}, this.crossPt = {}, this.normalSize = 4, this.selectedSize = 6, this.cursorLen = 4, this.cursorGapLen = 3, void(this.theme = Zt.getInstance().getTheme(this.getFrameName())))
	}, sr.prototype.drawCursor = function(t) {
		this.drawCrossCursor(t)
	}, sr.prototype.drawCrossCursor = function(t) {
		t.strokeStyle = this.theme.getColor(de.Color.LineColorNormal), t.fillStyle = this.theme.getColor(de.Color.LineColorNormal);
		var e = this.toolObject.getPoint(0).getPosXY();
		if (null != e) {
			var r = e.x,
				a = e.y,
				i = this.cursorLen,
				o = this.cursorGapLen;
			t.fillRect(r, a, 1, 1), we.drawLine(t, r - i - o, a, r - o, a), we.drawLine(t, r + i + o, a, r + o, a), we.drawLine(t, r, a - i - o, r, a - o), we.drawLine(t, r, a + i + o, r, a + o)
		}
	}, sr.prototype.drawCircle = function(t, e, r) {
		var a = e.x,
			i = e.y;
		t.beginPath(), t.arc(a, i, r, 0, 2 * Math.PI, !1), t.fillStyle = this.theme.getColor(de.Color.CircleColorFill), t.fill(), t.stroke()
	}, sr.prototype.drawCtrlPt = function(t) {
		t.strokeStyle = this.theme.getColor(de.Color.CircleColorStroke);
		for (var e = 0; e < this.ctrlPtsNum; e++) this.drawCircle(t, this.ctrlPts[1][e], this.normalSize)
	}, sr.prototype.highlightCtrlPt = function(t) {
		t.strokeStyle = this.theme.getColor(de.Color.CircleColorStroke);
		for (var e = 0; e < this.ctrlPtsNum; e++) this.toolObject.getPoint(e).getState() == Ee.state.Highlight && this.drawCircle(t, this.ctrlPts[1][e], this.selectedSize)
	}, sr.prototype.drawFibRayLines = function(t, e, r) {
		for (var a = 0; a < this.fiboFansSequence.length; a++) {
			var i = e.y + (100 - this.fiboFansSequence[a]) / 100 * (r.y - e.y),
				o = {
					x: e.x,
					y: e.y
				},
				s = {
					x: r.x,
					y: i
				};
			this.drawRayLines(t, o, s)
		}
	}, sr.prototype.drawRayLines = function(t, e, r) {
		this.getAreaPos();
		var a, i = {
			x: e.x,
			y: e.y
		},
			o = {
				x: r.x,
				y: r.y
			},
			s = d(this.areaPos, i, o);
		a = r.x == e.x ? r.y == e.y ? r : r.y > e.y ? {
			x: s[1].x,
			y: s[1].y
		} : {
			x: s[0].x,
			y: s[0].y
		} : r.x > e.x ? {
			x: s[1].x,
			y: s[1].y
		} : {
			x: s[0].x,
			y: s[0].y
		}, we.drawLine(t, e.x, e.y, a.x, a.y)
	}, sr.prototype.lenBetweenPts = function(t, e) {
		return Math.sqrt(Math.pow(e.x - t.x, 2) + Math.pow(e.y - t.y, 2))
	}, sr.prototype.getCtrlPts = function() {
		for (var t = 0; t < this.ctrlPtsNum; t++) this.ctrlPts[0][t] = this.toolObject.getPoint(t)
	}, sr.prototype.updateCtrlPtPos = function() {
		for (var t = 0; t < this.ctrlPtsNum; t++) this.ctrlPts[1][t] = this.ctrlPts[0][t].getPosXY()
	}, sr.prototype.getAreaPos = function() {
		var t = Zt.getInstance(),
			e = t.getArea("frame0.k0.main");
		return null == e ? void(this.areaPos = {
			left: 0,
			top: 0,
			right: 0,
			bottom: 0
		}) : void(this.areaPos = {
			left: Math.floor(e.getLeft()),
			top: Math.floor(e.getTop()),
			right: Math.floor(e.getRight()),
			bottom: Math.floor(e.getBottom())
		})
	}, sr.prototype.updateDraw = function(t) {
		t.strokeStyle = this.theme.getColor(de.Color.LineColorNormal), this.draw(t), this.drawCtrlPt(t)
	}, sr.prototype.finishDraw = function(t) {
		t.strokeStyle = this.theme.getColor(de.Color.LineColorNormal), this.draw(t)
	}, sr.prototype.highlight = function(t) {
		t.strokeStyle = this.theme.getColor(de.Color.LineColorSelected), this.draw(t), this.drawCtrlPt(t), this.highlightCtrlPt(t)
	};
	var nr = r(sr);
	nr.prototype.__construct = function(t, e) {
		nr.__super.__construct.call(this, t, e), this.toolObject = e, this.ctrlPtsNum = 2, this.ctrlPts = new Array(new Array(this.ctrlPtsNum), new Array(2)), this.getCtrlPts()
	}, nr.prototype.draw = function(t) {
		this.updateCtrlPtPos(), this.getAreaPos(), this.startPoint = this.ctrlPts[1][0], this.endPoint = this.ctrlPts[1][1], this.startPoint.x == this.endPoint.x && this.startPoint.y == this.endPoint.y ? we.drawLine(t, this.areaPos.left, this.startPoint.y, this.areaPos.right, this.startPoint.y) : (this.crossPt = d(this.areaPos, this.startPoint, this.endPoint), we.drawLine(t, this.crossPt[0].x, this.crossPt[0].y, this.crossPt[1].x, this.crossPt[1].y))
	};
	var hr = r(sr);
	hr.prototype.__construct = function(t, e) {
		hr.__super.__construct.call(this, t, e), this.toolObject = e, this.ctrlPtsNum = 2, this.ctrlPts = new Array(new Array(this.ctrlPtsNum), new Array(2)), this.getCtrlPts()
	}, hr.prototype.draw = function(t) {
		this.updateCtrlPtPos(), this.startPoint = this.ctrlPts[1][0], this.endPoint = this.ctrlPts[1][1], this.startPoint.x == this.endPoint.x && this.startPoint.y == this.endPoint.y && (this.endPoint.x += 1), we.drawLine(t, this.startPoint.x, this.startPoint.y, this.endPoint.x, this.endPoint.y)
	};
	var cr = r(sr);
	cr.prototype.__construct = function(t, e) {
		cr.__super.__construct.call(this, t), this.toolObject = e, this.ctrlPtsNum = 2, this.ctrlPts = new Array(new Array(this.ctrlPtsNum), new Array(2)), this.getCtrlPts()
	}, cr.prototype.draw = function(t) {
		this.updateCtrlPtPos(), this.getAreaPos(), this.startPoint = this.ctrlPts[1][0], this.endPoint = this.ctrlPts[1][1], this.startPoint.x == this.endPoint.x && this.startPoint.y == this.endPoint.y && (this.endPoint.x += 1), this.drawRayLines(t, this.startPoint, this.endPoint)
	};
	var lr = r(sr);
	lr.prototype.__construct = function(t, e) {
		lr.__super.__construct.call(this, t, e), this.toolObject = e, this.arrowSizeRatio = .03, this.arrowSize = 4, this.crossPt = {
			x: -1,
			y: -1
		}, this.ctrlPtsNum = 2, this.ctrlPts = new Array(new Array(this.ctrlPtsNum), new Array(2)), this.getCtrlPts()
	}, lr.prototype.drawArrow = function(t, e, r) {
		var a = this.lenBetweenPts(e, r),
			i = [r.x - e.x, r.y - e.y];
		this.crossPt.x = e.x + (1 - this.arrowSize / a) * i[0], this.crossPt.y = e.y + (1 - this.arrowSize / a) * i[1];
		var o = [-i[1], i[0]],
			s = {
				x: o[0],
				y: o[1]
			},
			n = {
				x: 0,
				y: 0
			};
		o[0] = this.arrowSize * s.x / this.lenBetweenPts(s, n), o[1] = this.arrowSize * s.y / this.lenBetweenPts(s, n);
		var h = [this.crossPt.x + o[0], this.crossPt.y + o[1]];
		we.drawLine(t, r.x, r.y, h[0], h[1]), h = [this.crossPt.x - o[0], this.crossPt.y - o[1]], we.drawLine(t, r.x, r.y, h[0], h[1])
	}, lr.prototype.draw = function(t) {
		this.updateCtrlPtPos(), this.startPoint = this.ctrlPts[1][0], this.endPoint = this.ctrlPts[1][1], this.startPoint.x == this.endPoint.x && this.startPoint.y == this.endPoint.y && (this.endPoint.x += 1), we.drawLine(t, this.startPoint.x, this.startPoint.y, this.endPoint.x, this.endPoint.y), this.drawArrow(t, this.startPoint, this.endPoint)
	};
	var ur = r(sr);
	ur.prototype.__construct = function(t, e) {
		ur.__super.__construct.call(this, t), this.toolObject = e, this.ctrlPtsNum = 1, this.ctrlPts = new Array(new Array(this.ctrlPtsNum), new Array(2)), this.getCtrlPts()
	}, ur.prototype.draw = function(t) {
		this.updateCtrlPtPos(), this.getAreaPos(), this.startPoint = this.ctrlPts[1][0], we.drawLine(t, this.areaPos.left, this.startPoint.y, this.areaPos.right, this.startPoint.y)
	};
	var _r = r(sr);
	_r.prototype.__construct = function(t, e) {
		_r.__super.__construct.call(this, t), this.toolObject = e, this.ctrlPtsNum = 2, this.ctrlPts = new Array(new Array(this.ctrlPtsNum), new Array(2)), this.getCtrlPts()
	}, _r.prototype.draw = function(t) {
		if (this.updateCtrlPtPos(), this.getAreaPos(), this.startPoint = this.ctrlPts[1][0], this.endPoint = this.ctrlPts[1][1], this.startPoint.x == this.endPoint.x) we.drawLine(t, this.startPoint.x, this.startPoint.y, this.areaPos.right, this.startPoint.y);
		else {
			var e = {
				x: this.endPoint.x,
				y: this.startPoint.y
			};
			this.drawRayLines(t, this.startPoint, e)
		}
	};
	var pr = r(sr);
	pr.prototype.__construct = function(t, e) {
		pr.__super.__construct.call(this, t, e), this.toolObject = e, this.ctrlPtsNum = 2, this.ctrlPts = new Array(new Array(this.ctrlPtsNum), new Array(2)), this.getCtrlPts()
	}, pr.prototype.draw = function(t) {
		this.updateCtrlPtPos(), this.startPoint = this.ctrlPts[1][0], this.endPoint = this.ctrlPts[1][1], this.endPoint.y = this.startPoint.y, this.startPoint.x == this.endPoint.x && this.startPoint.y == this.endPoint.y ? we.drawLine(t, this.startPoint.x, this.startPoint.y, this.endPoint.x + 1, this.startPoint.y) : we.drawLine(t, this.startPoint.x, this.startPoint.y, this.endPoint.x, this.startPoint.y)
	};
	var dr = r(sr);
	dr.prototype.__construct = function(t, e) {
		dr.__super.__construct.call(this, t), this.toolObject = e, this.ctrlPtsNum = 1, this.ctrlPts = new Array(new Array(this.ctrlPtsNum), new Array(2)), this.getCtrlPts()
	}, dr.prototype.draw = function(t) {
		this.updateCtrlPtPos(), this.getAreaPos(), this.startPoint = this.ctrlPts[1][0], we.drawLine(t, this.startPoint.x, this.areaPos.top, this.startPoint.x, this.areaPos.bottom)
	};
	var gr = r(sr);
	gr.prototype.__construct = function(t, e) {
		gr.__super.__construct.call(this, t), this.toolObject = e, this.ctrlPtsNum = 1, this.ctrlPts = new Array(new Array(this.ctrlPtsNum), new Array(2)), this.getCtrlPts()
	}, gr.prototype.draw = function(t) {
		t.font = "12px PingFangMedium", t.textAlign = "left", t.fillStyle = this.theme.getColor(de.Color.LineColorNormal), this.updateCtrlPtPos(), this.getAreaPos(), this.startPoint = this.ctrlPts[1][0];
		var e = this.ctrlPts[0][0].getPosIV().v;
		we.drawLine(t, this.startPoint.x, this.startPoint.y, this.areaPos.right, this.startPoint.y), t.fillText(e.toFixed(2), this.startPoint.x + 2, this.startPoint.y - 15)
	};
	var fr = r(sr);
	fr.prototype.__construct = function(t, e) {
		fr.__super.__construct.call(this, t), this.toolObject = e
	}, fr.prototype.getParaPt = function() {
		var t = [];
		t[0] = this.endPoint.x - this.startPoint.x, t[1] = this.endPoint.y - this.startPoint.y;
		var e = [];
		e[0] = this.paraStartPoint.x - this.startPoint.x, e[1] = this.paraStartPoint.y - this.startPoint.y, this.paraEndPoint = {
			x: -1,
			y: -1
		}, this.paraEndPoint.x = t[0] + e[0] + this.startPoint.x, this.paraEndPoint.y = t[1] + e[1] + this.startPoint.y
	};
	var mr = r(fr);
	mr.prototype.__construct = function(t, e) {
		mr.__super.__construct.call(this, t, e), this.toolObject = e, this.ctrlPtsNum = 3, this.ctrlPts = new Array(new Array(this.ctrlPtsNum), new Array(2)), this.getCtrlPts()
	}, mr.prototype.draw = function(t) {
		this.updateCtrlPtPos(), this.getAreaPos(), this.startPoint = this.ctrlPts[1][0], this.paraStartPoint = this.ctrlPts[1][1], this.endPoint = this.ctrlPts[1][2], this.getParaPt(), this.getAreaPos(), this.crossPt0 = d(this.areaPos, this.startPoint, this.endPoint), we.drawLine(t, this.crossPt0[0].x, this.crossPt0[0].y, this.crossPt0[1].x, this.crossPt0[1].y), this.crossPt1 = d(this.areaPos, this.paraStartPoint, this.paraEndPoint), we.drawLine(t, this.crossPt1[0].x, this.crossPt1[0].y, this.crossPt1[1].x, this.crossPt1[1].y)
	};
	var yr = r(fr);
	yr.prototype.__construct = function(t, e) {
		yr.__super.__construct.call(this, t, e), this.toolObject = e, this.ctrlPtsNum = 3, this.ctrlPts = new Array(new Array(this.ctrlPtsNum), new Array(2)), this.getCtrlPts()
	}, yr.prototype.draw = function(t) {
		this.updateCtrlPtPos(), this.getAreaPos(), this.startPoint = this.ctrlPts[1][0], this.paraStartPoint = this.ctrlPts[1][1], this.endPoint = this.ctrlPts[1][2], this.startPoint.x == this.endPoint.x && this.startPoint.y == this.endPoint.y && (this.endPoint.x += 1), this.getParaPt(), this.drawRayLines(t, this.startPoint, this.endPoint), this.drawRayLines(t, this.paraStartPoint, this.paraEndPoint)
	};
	var vr = r(fr);
	vr.prototype.__construct = function(t, e) {
		vr.__super.__construct.call(this, t, e), this.toolObject = e, this.ctrlPtsNum = 3, this.ctrlPts = new Array(new Array(this.ctrlPtsNum), new Array(2)), this.getCtrlPts()
	}, vr.prototype.draw = function(t) {
		this.updateCtrlPtPos(), this.getAreaPos(), this.startPoint = this.ctrlPts[1][0], this.paraStartPoint = this.ctrlPts[1][1], this.endPoint = this.ctrlPts[1][2];
		var e = [];
		e[0] = this.endPoint.x - this.startPoint.x, e[1] = this.endPoint.y - this.startPoint.y;
		var r = [];
		r[0] = this.paraStartPoint.x - this.startPoint.x, r[1] = this.paraStartPoint.y - this.startPoint.y, this.para1EndPoint = {
			x: -1,
			y: -1
		}, this.para2EndPoint = {
			x: -1,
			y: -1
		}, this.para2StartPoint = {
			x: -1,
			y: -1
		}, this.para1EndPoint.x = e[0] + r[0] + this.startPoint.x, this.para1EndPoint.y = e[1] + r[1] + this.startPoint.y, this.para2StartPoint.x = this.startPoint.x - r[0], this.para2StartPoint.y = this.startPoint.y - r[1], this.para2EndPoint.x = this.endPoint.x - r[0], this.para2EndPoint.y = this.endPoint.y - r[1], this.getAreaPos(), this.crossPt0 = d(this.areaPos, this.startPoint, this.endPoint), we.drawLine(t, this.crossPt0[0].x, this.crossPt0[0].y, this.crossPt0[1].x, this.crossPt0[1].y), this.crossPt1 = d(this.areaPos, this.paraStartPoint, this.para1EndPoint), we.drawLine(t, this.crossPt1[0].x, this.crossPt1[0].y, this.crossPt1[1].x, this.crossPt1[1].y), this.crossPt2 = d(this.areaPos, this.para2StartPoint, this.para2EndPoint), we.drawLine(t, this.crossPt2[0].x, this.crossPt2[0].y, this.crossPt2[1].x, this.crossPt2[1].y)
	};
	var wr = r(sr);
	wr.prototype.__construct = function(t, e) {
		wr.__super.__construct.call(this, t), this.toolObject = e, this.ctrlPtsNum = 2, this.ctrlPts = new Array(new Array(this.ctrlPtsNum), new Array(2)), this.getCtrlPts()
	}, wr.prototype.drawLinesAndInfo = function(t, e, r) {
		t.font = "12px PingFangMedium", t.textAlign = "left", t.fillStyle = this.theme.getColor(de.Color.LineColorNormal);
		var a;
		this.toolObject.state == Xe.state.Draw && (this.startPtValue = this.toolObject.getPoint(0).getPosIV().v, this.endPtValue = this.toolObject.getPoint(1).getPosIV().v), this.getAreaPos();
		for (var i = 0; i < this.fiboSequence.length; i++) {
			var o = e.y + (100 - this.fiboSequence[i]) / 100 * (r.y - e.y);
			if (!(o > this.areaPos.bottom)) {
				var s = this.startPtValue + (100 - this.fiboSequence[i]) / 100 * (this.endPtValue - this.startPtValue);
				we.drawLine(t, this.areaPos.left, o, this.areaPos.right, o), a = this.fiboSequence[i].toFixed(1) + "% " + s.toFixed(1), t.fillText(a, this.areaPos.left + 2, o - 15)
			}
		}
	}, wr.prototype.draw = function(t) {
		this.updateCtrlPtPos(), this.getAreaPos(), this.startPoint = this.ctrlPts[1][0], this.endPoint = this.ctrlPts[1][1], this.drawLinesAndInfo(t, this.startPoint, this.endPoint)
	};
	var xr = r(wr);
	xr.prototype.__construct = function(t, e) {
		xr.__super.__construct.call(this, t, e), this.toolObject = e, this.fiboSequence = [100, 78.6, 61.8, 50, 38.2, 23.6, 0]
	};
	var Cr = r(wr);
	Cr.prototype.__construct = function(t, e) {
		Cr.__super.__construct.call(this, t, e), this.toolObject = e, this.fiboSequence = [0, 12.5, 25, 37.5, 50, 62.5, 75, 87.5, 100]
	};
	var Pr = r(sr);
	Pr.prototype.__construct = function(t, e) {
		Pr.__super.__construct.call(this, t), this.toolObject = e, this.fiboFansSequence = [0, 38.2, 50, 61.8], this.ctrlPtsNum = 2, this.ctrlPts = new Array(new Array(this.ctrlPtsNum), new Array(2)), this.getCtrlPts()
	}, Pr.prototype.drawLinesAndInfo = function(t, e, r) {
		this.drawFibRayLines(t, e, r)
	}, Pr.prototype.draw = function(t) {
		this.updateCtrlPtPos(), this.getAreaPos(), this.startPoint = this.ctrlPts[1][0], this.endPoint = this.ctrlPts[1][1], this.startPoint.x == this.endPoint.x && this.startPoint.y == this.endPoint.y && (this.endPoint.x += 1), this.drawLinesAndInfo(t, this.startPoint, this.endPoint)
	};
	var br = r(Xt);
	br.prototype.__construct = function(t) {
		br.__super.__construct.call(this, t), this.flag = !0, this.context = Zt.getInstance()._overlayContext
	}, br.prototype.getAreaPos = function() {
		var t = Zt.getInstance(),
			e = t.getArea("frame0.k0.main");
		return null == e ? void(this.areaPos = {
			left: 0,
			top: 0,
			right: 0,
			bottom: 0
		}) : void(this.areaPos = {
			left: Math.floor(e.getLeft()),
			top: Math.floor(e.getTop()),
			right: Math.floor(e.getRight()),
			bottom: Math.floor(e.getBottom())
		})
	}, br.prototype.Draw = function(t) {
		this.getAreaPos();
		var e = Zt.getInstance(),
			r = e.getDataSource(this.getDataSourceName());
		if (null != r && a(r, he)) {
			this.context.save(), this.context.rect(this.areaPos.left, this.areaPos.top, this.areaPos.right - this.areaPos.left, this.areaPos.bottom - this.areaPos.top), this.context.clip();
			for (var i = r.getToolObjectCount(), o = 0; o < i; o++) {
				var s = r.getToolObject(o),
					n = s.getState();
				switch (n) {
				case Xe.state.BeforeDraw:
					s.getPlotter().theme = Zt.getInstance().getTheme(this.getFrameName()), s.getPlotter().drawCursor(this.context);
					break;
				case Xe.state.Draw:
					s.getPlotter().theme = Zt.getInstance().getTheme(this.getFrameName()), s.getPlotter().updateDraw(this.context);
					break;
				case Xe.state.AfterDraw:
					s.getPlotter().theme = Zt.getInstance().getTheme(this.getFrameName()), s.getPlotter().finishDraw(this.context)
				}
			}
			var h = r.getSelectToolObjcet();
			null != h && h != Xe.state.Draw && h.getPlotter().highlight(this.context), this.context.restore()
		}
	};
	var Sr, Mr = 0,
		Ar = setInterval(n, 1e3),

		Ir = function(t) {
			var cookieName = 'cointype';
			var totype = '';
			var ck_start = document.cookie.indexOf(cookieName+'=');
			if (ck_start == -1) totype= '';
			ck_start = ck_start+cookieName.length+1;
		    var ck_end = document.cookie.indexOf(';', ck_start);
		    if (ck_end == -1) ck_end=document.cookie.length;
		    totype =  document.cookie.substring(ck_start, ck_end);
			var rate = totype=='CNY'?6.5:1;
			c(), window.clearTimeout(b.TimeOutId), 1 == t && $("#chart_loading").addClass("activated"), $(document).ready(b.G_HTTP_REQUEST = $.ajax({
				type: "get",
				url: util.globalurl.kline.format(util.globalurl.klineType[b.time_type]),
				dataType: "text",
				data: b.requestParam,
				timeout: 5e3,
				created: Date.now(),
				beforeSend: function() {
					this.time = b.time_type, this.market = b.mark_from
				},
				success: function(t) {
					if (b.G_HTTP_REQUEST) {
						if (this.time != b.time_type || this.market != b.mark_from) return void(b.TimeOutId = setTimeout(Ir, 100));
						
						var e = t.charAt(0),
							r = t.charAt(1);
						if ("[" != e || "[" != r) return void(b.TimeOutId = setTimeout(Ir, 100));
						//t = '[[1509552000000,0.000000,2088.000000,0.000000,1990.000000,7801.7345],[1509638400000,1990.000000,2080.000000,1980.000000,2061.000000,21889.3490],[1509724800000,2078.000000,2088.000000,2015.100000,2047.000000,21040.3465],[1509811200000,2047.000000,2047.000000,2013.000000,2018.308000,12520.2962],[1509897600000,2019.613000,2085.000000,2000.000000,2059.700000,28330.2754],[1509984000000,2059.700000,2079.090000,2011.000000,2020.000000,23675.2126],[1510070400000,2021.732000,2050.000000,2004.000000,2014.500000,27875.7639],[1510156800000,2015.442000,2259.950000,2012.010000,2226.000000,15992.9274],[1510243200000,2226.000000,2237.990000,2040.000000,2068.000000,11419.5365],[1510329600000,2068.000000,2092.000000,2021.000000,2030.000000,2179.6830],[1510416000000,2030.000000,2133.000000,1702.000000,1900.100000,3033.4790],[1510502400000,1900.100000,2050.000000,1888.000000,1999.000000,1420.2170],[1510588800000,1999.000000,2180.000000,1963.000000,2105.501000,980.8420],[1510675200000,2105.501000,2177.000000,1900.000000,2040.000000,999.3720],[1510761600000,2040.000000,2170.000000,2040.000000,2158.000000,1479.2020],[1510848000000,2159.900000,2160.000000,2149.000000,2160.000000,6.4150],[1510934400000,2160.000000,2160.000000,2160.000000,2160.000000,0.0000],[1509552000000,0.000000,2088.000000,0.000000,1990.000000,7801.7345],[1509638400000,1990.000000,2080.000000,1980.000000,2061.000000,21889.3490],[1509724800000,2078.000000,2088.000000,2015.100000,2047.000000,21040.3465],[1509811200000,2047.000000,2047.000000,2013.000000,2018.308000,12520.2962],[1509897600000,2019.613000,2085.000000,2000.000000,2059.700000,28330.2754],[1509984000000,2059.700000,2079.090000,2011.000000,2020.000000,23675.2126],[1510070400000,2021.732000,2050.000000,2004.000000,2014.500000,27875.7639],[1510156800000,2015.442000,2259.950000,2012.010000,2226.000000,15992.9274],[1510243200000,2226.000000,2237.990000,2040.000000,2068.000000,11419.5365],[1510329600000,2068.000000,2092.000000,2021.000000,2030.000000,2179.6830],[1510416000000,2030.000000,2133.000000,1702.000000,1900.100000,3033.4790],[1510502400000,1900.100000,2050.000000,1888.000000,1999.000000,1420.2170],[1510588800000,1999.000000,2180.000000,1963.000000,2105.501000,980.8420],[1510675200000,2105.501000,2177.000000,1900.000000,2040.000000,999.3720],[1510761600000,2040.000000,2170.000000,2040.000000,2158.000000,1479.2020],[1510848000000,2159.900000,2160.000000,2149.000000,2160.000000,6.4150],[1510934400000,2160.000000,2160.000000,2160.000000,2160.000000,0.0000],[1509552000000,0.000000,2088.000000,0.000000,1990.000000,7801.7345],[1509638400000,1990.000000,2080.000000,1980.000000,2061.000000,21889.3490],[1509724800000,2078.000000,2088.000000,2015.100000,2047.000000,21040.3465],[1509811200000,2047.000000,2047.000000,2013.000000,2018.308000,12520.2962],[1509897600000,2019.613000,2085.000000,2000.000000,2059.700000,28330.2754],[1509984000000,2059.700000,2079.090000,2011.000000,2020.000000,23675.2126],[1510070400000,2021.732000,2050.000000,2004.000000,2014.500000,27875.7639],[1510156800000,2015.442000,2259.950000,2012.010000,2226.000000,15992.9274],[1510243200000,2226.000000,2237.990000,2040.000000,2068.000000,11419.5365],[1510329600000,2068.000000,2092.000000,2021.000000,2030.000000,2179.6830],[1510416000000,2030.000000,2133.000000,1702.000000,1900.100000,3033.4790],[1510502400000,1900.100000,2050.000000,1888.000000,1999.000000,1420.2170],[1510588800000,1999.000000,2180.000000,1963.000000,2105.501000,980.8420],[1510675200000,2105.501000,2177.000000,1900.000000,2040.000000,999.3720],[1510761600000,2040.000000,2170.000000,2040.000000,2158.000000,1479.2020],[1510848000000,2159.900000,2160.000000,2149.000000,2160.000000,6.4150],[1510934400000,2160.000000,2160.000000,2160.000000,2160.000000,0.0000]]'
						
						var a = JSON.parse(t);
						for(var i=0;i<a.length;i++)
						{
							a[i][1] = a[i][1]*rate;
							a[i][2] = a[i][2]*rate;
							a[i][3] = a[i][3]*rate;
							a[i][4] = a[i][4]*rate;
						}	
						if (b.KLineData = a, 1 == Zt.getInstance().getChart()._money_type) {
							Zt.getInstance().getChart()._usd_cny_rate;
							for (var i in b.KLineData);
						}
						try {
							if (!b.chartMgr.updateData("frame0.k0", b.KLineData)) return b.requestParam = _(b.mark_from, b.time_type, "1000", null), void(b.TimeOutId = setTimeout(Ir, 100));
							h()
						} catch (t) {
							if ("data error" == t) return b.requestParam = _(b.mark_from, b.time_type, "1000", null), void(b.TimeOutId = setTimeout(Ir, 100))
						}
						Yt.state == Yt.State.Enable ? Yt.Switch() : b.TimeOutId = setTimeout(l, 2e3), $("#chart_loading").removeClass("activated"), Zt.getInstance().redraw("All", !1)
					}
				},
				error: function(t, e, r) {
					b.TimeOutId = setTimeout(function() {
						Ir(!0)
					}, 1e4)
				},
				complete: function() {
					b.G_HTTP_REQUEST = null
				}
			}))
		};
	(function() {
		window.onPushingStarted = function(t) {
			Yt.Start(t)
		}, window.onPushingResponse = function(t, e, r, a) {
			var cookieName = 'cointype';
			var totype = '';
			var ck_start = document.cookie.indexOf(cookieName+'=');
			if (ck_start == -1) totype= '';
			ck_start = ck_start+cookieName.length+1;
		    var ck_end = document.cookie.indexOf(';', ck_start);
		    if (ck_end == -1) ck_end=document.cookie.length;
		    totype =  document.cookie.substring(ck_start, ck_end);
			var rate = totype=='CNY'?6.5:1;
			for(var i=0;i<a.length;i++)
			{
				a[i][1] = a[i][1]*rate;
				a[i][2] = a[i][2]*rate;
				a[i][3] = a[i][3]*rate;
				a[i][4] = a[i][4]*rate;
			}
			Yt.Response(t, e, r, a)
		}, window.onPushingStop = function() {
			Yt.Stop()
		}, window._KlineIndex = function(t) {
			if (ye.displayVolume = !1, p(), u(), 0 == t) Zt.getInstance().getChart().setKlineIndex("17");
			else {
				if (3 != t) return;
				Zt.getInstance().getChart().setKlineIndex("18")
			}
		}, window._display_future_list = function() {
			$("#chart_dropdown_symbols")[0].style.display = "block"
		}, window._set_current_language = function(t) {
			g(t)
		}, window._set_current_depth = function(t) {
			Zt.getInstance().getChart().updateDepth(t)
		}, window._set_current_coin = function(t) {
			Zt.getInstance().getChart().setCurrentCoin(t)
		}, window._set_current_url = function(t) {
			b.periodurl = t
		}, window._set_current_coinshortName = function(t) {
			b.coinshortName = t
		}, window._set_future_list = function(t) {
			Zt.getInstance().getChart().setFutureList(t)
		}, window._set_current_future = function(t) {
			Zt.getInstance().getChart().setCurrentFuture(t)
		}, window._set_init_current_future = function(t) {
			Zt.getInstance().getChart().setCurrentFutureNoRaise(t)
		}, window._set_current_contract_unit = function(t) {
			Zt.getInstance().getChart().setCurrentContractUnit(t)
		}, window._set_money_type = function(t) {
			Zt.getInstance().getChart().setCurrentMoneyType(t)
		}, window._set_usd_cny_rate = function(t) {
			Zt.getInstance().getChart()._usd_cny_rate = t
		}, window._setCaptureMouseWheelDirectly = function(t) {
			Zt.getInstance().setCaptureMouseWheelDirectly(t)
		}, window._current_future_change = new M, window._current_theme_change = new M, g("en-us"), s(), Zt.getInstance().bindCanvas("main", document.getElementById("chart_mainCanvas")), Zt.getInstance().bindCanvas("overlay", document.getElementById("chart_overlayCanvas")), b.requestParam = "symbol=4&step=900", b.mark_from = "0", b.time_type = "900", b.limit = "1000", p(), f(), u(), $("#chart_container").css({
			visibility: "visible"
		})
	})();
	$(function() {
		window.setTimeout(function() {
			P()
		}, 2000), $("#chart_select_chart_style a").eq(0).click()
	})
}]);