var userlevel = {
	state : true,
	pass : true,
	buyvip : function(that) {
		var level = $("#userlevel strong").text();
		if (level == 6) {
			util.layerAlert("", util.getLan("user.tips.33"));
			return;
		}
		var tradePwd = $("#tradePwd").val();
		if (tradePwd == "") {
			if (userlevel.pass) {
				$('#tradepass').modal({
					backdrop : 'static',
					keyboard : false,
					show : true
				});
				userlevel.pass = false;
				return;
			} else {
				util.showerrortips("errortips", util.getLan("comm.tips.8"));
				return;
			}
		}
		// 提交信息
		if (!userlevel.state) {
			return;
		} else {
			userlevel.state = false;
		}
		var url = "/user/pay_vip.html";
		var param = {
			tradePwd : tradePwd
		}
		var callback = function(data) {
			userlevel.state = true;
			if (data.code == 200) {
				util.layerAlert("", util.getLan("user.tips.34"), 1);
			} else {
				util.showerrortips("errortips", data.msg);
			}
		};
		util.network({
			btn : that,
			url : url,
			param : param,
			success : callback,
		});
	},
};
$(function() {
	$("#buyVip6").click(function() {
		userlevel.buyvip(this);
	});
	$("#modalbtn").on("click", function() {
		userlevel.buyvip(this);
	});
	$('#tradepass').on('shown.bs.modal', function(e) {
		util.callbackEnter(userlevel.buyvip);
	});
	$('#tradepass').on('hidden.bs.modal', function(e) {
		document.onkeydown = function() {
		};
		$("#tradePwd").val("");
		$("#errortips").html("");
		userlevel.pass = true;
	});
});