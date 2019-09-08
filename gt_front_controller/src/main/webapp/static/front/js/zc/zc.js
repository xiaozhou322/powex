function crowdnumChange(o) {
	if (isNaN(o.value))
		o.value = '';
	var value = formatfloat(o.value, 0, 0);
	if (badFloat(o.value, 0))
		o.value = value;
};
// 距离开始的秒数
var start  = document.getElementById("start").value;
// 距离结束的秒数
var end  = document.getElementById("end").value;
function Countdown() {
	var time;
	if (start > 0) {
		$("#cdstatus").html(language["zc.error.tips.1"]);
		time = start;
	} else {
		$("#cdstatus").html(language["zc.error.tips.2"]);
		time = end;
	}
	if (start <= 0 && start >= -2) {
		location.reload(true);
	}
	if (end <= 0 && end >= -2) {
		location.reload(true);
	}
	if (time < 0) {
		time = 0;
		if (typeof countDownInterVal != 'undefined') {
			clearInterval(countDownInterVal);
		}
	}
	var day = (time - time % 86400) / 86400;
	var hour = (time % 86400 - time % 3600) / 3600;
	var min = (time % 3600 - time % 60) / 60;
	var second = time % 60;
	$("#t_d").html(addSpan(day) + language["zc.error.tips.3"]);
	$("#t_h").html(addSpan(hour) + language["zc.error.tips.4"]);
	$("#t_m").html(addSpan(min) + language["zc.error.tips.5"]);
	$("#t_s").html(addSpan(second) + language["zc.error.tips.6"]);
	start--;
	end--;
};
function addSpan(number) {
	number = number > 9 ? '' + number : '0' + number;
	var html = '';
	for (i = 0; i < number.length; i++) {
		html += '<span>' + number.substr(i, 1) + '</span>';
	}
	return html;
};

$(function() {
	Countdown();
	countDownInterVal = setInterval("Countdown()", 1000);
	$("#subCrowdNum").click(function() {
		var num = $("#crowd_num").val();
		num = parseInt(num);
		if (isNaN(num))
			num = 0;
		num--;
		num = num < 1 ? 1 : num;
		$("#crowd_num").val(num);
	});
	$("#addCrowdNum").click(function() {
		var num = $("#crowd_num").val();
		num = parseInt(num);
		if (isNaN(num))
			num = 0;
		num++;
		$("#crowd_num").val(num);
	});
});

function joincrowd() {
	var fid = document.getElementById("subid").value;
	var buyAmount = document.getElementById("crowd_num").value;
	var pwd = document.getElementById("pwtrade").value;
	var type = document.getElementById("type").value; 
	if (buyAmount <= 0) {
		util.layerAlert("", language["zc.error.tips.7"].format(type), 2);
		return;
	}
	if (pwd == "" || pwd == null) {
		util.layerAlert("", language["zc.error.tips.8"], 2);
		return;
	}
	var url = "/json/crowd/submit.html?random="
			+ Math.round(Math.random() * 100);
	var param = {
		fid : fid,
		buyAmount : buyAmount,
		pwd : pwd
	};
	jQuery.post(url, param, function(result) {
		if (result != null) {
			if (result.code < 0) {
				util.layerAlert("", result.msg, 2);
			} else {
				util.layerAlert("", language["zc.error.tips.9"], 1);
			}
		}
	});
};