var convert = {
	submitConvertCoinForm : function(flag) {
		var cointypeFrom = util.trim(document.getElementById("cointypeFrom").value);
		var cointypeTo = util.trim(document.getElementById("cointypeTo").value);
		var aboAmount = util.trim(document.getElementById("aboAmount").value);
		var abo_abotAmount = util.trim(document.getElementById("abo_abotAmount").value);
		var abotAmount = util.trim(document.getElementById("abotAmount").value);
		var abot_aboAmount = util.trim(document.getElementById("abot_aboAmount").value);
		var tradePwd = util.trim(document.getElementById("tradePwd").value);
		
		var convertType = util.trim(document.getElementById("convertType").value);
		var productId = util.trim(document.getElementById("productId").value);
		
		var convertAmount1;
		var convertAmount2;
		var converterrortips;
		var convertButton;
		if(1 == convertType) {
			convertAmount1 = aboAmount;
			convertAmount2 = abo_abotAmount;
			converterrortips = "convertABOTerrortips";
			convertButton = document.getElementById("convertABOTButton");
		} else {
			convertAmount1 = abotAmount;
			convertAmount2 = abot_aboAmount;
			converterrortips = "convertABOerrortips";
			convertButton = document.getElementById("convertABOButton");
		}
		
		
		var reg = new RegExp("^[0-9]+\.{0,1}[0-9]{0,8}$");
		if (!reg.test(convertAmount1)) {
			util.showerrortips(converterrortips, language["comm.error.tips.201"]);
			return;
		} else {
			util.hideerrortips(converterrortips);
		}
		/*if (parseFloat(convertAmount2) > parseFloat(canConvertAmount)) {
			util.showerrortips("converterrortips", language["convert.error.tips.1"].format(canConvertAmount,coinName));
			return;
		} else {
			util.hideerrortips("converterrortips");
		}*/
		if(flag) {
			$('#tradepass').modal({
				backdrop : 'static',
				keyboard : false,
				show : true
		    });
			return;
		}
		if (tradePwd == "") {
			util.showerrortips(converterrortips, language["comm.error.tips.58"]);
			return;
		} else {
			util.hideerrortips(converterrortips);
		}
		convertButton.disabled = true;
		var url = "/account/convertCoinSubmit.html?random=" + Math.round(Math.random() * 100);
		var param = {
			cointypeFrom : cointypeFrom,
			cointypeTo : cointypeTo,
			convertAmount1 : convertAmount1,
			convertAmount2 : convertAmount2,
			tradePwd : tradePwd,
			productId : productId,
			convertType : convertType
		};
		$.post(url, param, function(result) {
			if (result != null) {
				convertButton.disabled = false;
				if (result.code < 0) {
					util.showerrortips(converterrortips, result.msg);
				} else if (result.code == 0) {
					util.showerrortips(converterrortips, result.msg);
					setTimeout(function(){  //使用  setTimeout（）方法设定定时3000毫秒
						window.location.reload();//页面刷新
					},3000);
				} else {
					util.hideerrortips(converterrortips);
				}
			}
		});
	},
	submitTradePwd : function() {
		var tradePwd = $("#tradePwd").val();
		if (tradePwd != "") {
			$("#tradePwd").val(tradePwd);
		}
		$('#tradepass').modal('hide');
		convert.submitConvertCoinForm(false);
	}
};
$(function() {
	$("#modalbtn").on("click", function() {
		convert.submitTradePwd();
	});
	
	$("#convertAmount1").on("keypress", function(event) {
		return util.VerifyKeypress(this, event, 4);
	});
});




/*
 * 时间格式化工具 
 * 把Long类型的1527672756454日期还原yyyy-MM-dd 00:00:00格式日期   
 */    
function datetimeFormat(longTypeDate){    
    var dateTypeDate = "";    
    var date = new Date();    
    date.setTime(longTypeDate);    
    dateTypeDate += date.getFullYear();   //年    
    dateTypeDate += "-" + getMonth(date); //月     
    dateTypeDate += "-" + getDay(date);   //日    
    dateTypeDate += " " + getHours(date);   //时    
    dateTypeDate += ":" + getMinutes(date);     //分  
    dateTypeDate += ":" + getSeconds(date);     //分  
    return dateTypeDate;  
}   
/*  
 * 时间格式化工具 
 * 把Long类型的1527672756454日期还原yyyy-MM-dd格式日期   
 */    
function dateFormat(longTypeDate){    
    var dateTypeDate = "";    
    var date = new Date();    
    date.setTime(longTypeDate);    
    dateTypeDate += date.getFullYear();   //年    
    dateTypeDate += "-" + getMonth(date); //月     
    dateTypeDate += "-" + getDay(date);   //日    
    return dateTypeDate;  
}   
//返回 01-12 的月份值     
function getMonth(date){    
    var month = "";    
    month = date.getMonth() + 1; //getMonth()得到的月份是0-11    
    if(month<10){    
        month = "0" + month;    
    }    
    return month;    
}    
//返回01-30的日期    
function getDay(date){    
    var day = "";    
    day = date.getDate();    
    if(day<10){    
        day = "0" + day;    
    }    
    return day;    
}  
//小时  
function getHours(date){  
    var hours = "";  
    hours = date.getHours();  
    if(hours<10){    
        hours = "0" + hours;    
    }    
    return hours;    
}  
//分  
function getMinutes(date){  
    var minute = "";  
    minute = date.getMinutes();  
    if(minute<10){    
        minute = "0" + minute;    
    }    
    return minute;    
}  
//秒  
function getSeconds(date){  
    var second = "";  
    second = date.getSeconds();  
    if(second<10){    
        second = "0" + second;    
    }    
    return second;    
}

