var login={
		indexLoginOnblur:function () {
		    var uName = document.getElementById("indexLoginName").value;
		    if (!util.checkEmail(uName) && !util.checkMobile(uName)) {
		        util.showerrortips("indexLoginTips",language["comm.error.tips.1"]);
		    } else {
		    	util.hideerrortips("indexLoginTips");
		    }
		},
		loginIndexSubmit:function () {
		    util.hideerrortips("indexLoginTips");
		    var url = "/user/login/index.html?random=" + Math.round(Math.random() * 100);
		    var uName = document.getElementById("indexLoginName").value;
		    var pWord = document.getElementById("indexLoginPwd").value;
		    var longLogin = 0;
		    if (util.checkEmail(uName)) {
		        longLogin = 1;
		    }
		    if (!util.checkEmail(uName) && !util.checkMobile(uName)) {
		    	util.showerrortips("indexLoginTips", language["comm.error.tips.1"]);
		        return
		    }
		    if (pWord == "") {
		    	util.showerrortips("indexLoginTips", language["comm.error.tips.2"]);
		        return;
		    } else if (pWord.length < 6) {
		    	util.showerrortips("indexLoginTips", language["comm.error.tips.3"]);
		        return;
		    }
		    var param = {
		        loginName: uName,
		        password: pWord,
		        type: longLogin
		    };
		    jQuery.post(url, param, function (data) {
		        if (data.code == 0) {
		            if (document.getElementById("forwardUrl") != null && document.getElementById("forwardUrl").value != "") {
		                var forward = document.getElementById("forwardUrl").value;
		                forward = decodeURI(forward);
		                window.location.href = forward;
		            } else {
		                var whref = document.location.href;
		                if (whref.indexOf("#") != -1) {
		                    whref = whref.substring(0, whref.indexOf("#"));
		                }
		                window.location.href = whref;
		            }
		        } else if (data.code <0) {
		        	util.showerrortips("indexLoginTips", data.msg);
		            document.getElementById("indexLoginPwd").value = "";
		        }
		    },"json");
		},
		refreshMarket:function(){
			var url="/real/indexmarket.html?random=" + Math.round(Math.random() * 100);
			$.post(url,{},function(data){
				$.each(data,function(key,value){
					$("#"+key+"_total").html(Number(value.total));
					$("#"+key+"_total_1").html(Number(value.total));
					if(Number(value.rose)>=0){
						$("#"+key+"_rose").removeClass("text-danger").removeClass("text-success").addClass("text-danger").html('+'+value.rose+'%');
						$("#"+key+"_price").removeClass("text-danger").removeClass("text-success").addClass("text-danger").html(value.symbol+scientificToNumber(value.price));

                        $("#"+key+"_rose_1").removeClass("text-danger").removeClass("text-success").addClass("text-danger").html('+'+value.rose+'%');
                        $("#"+key+"_price_1").removeClass("text-danger").removeClass("text-success").addClass("text-danger").html(value.symbol+scientificToNumber(value.price));
					}else{
						$("#"+key+"_rose").removeClass("text-danger").removeClass("text-success").addClass("text-success").html(value.rose+'%');
						$("#"+key+"_price").removeClass("text-danger").removeClass("text-success").addClass("text-success").html(value.symbol+scientificToNumber(value.price));

                        $("#"+key+"_rose_1").removeClass("text-danger").removeClass("text-success").addClass("text-success").html(value.rose+'%');
                        $("#"+key+"_price_1").removeClass("text-danger").removeClass("text-success").addClass("text-success").html(value.symbol+scientificToNumber(value.price));
					}
					
				$.plot($("#"+key+"_plot"), [{shadowSize:0, data:value.data}],{ grid: { borderWidth: 0}, xaxis: { mode: "time", ticks: false}, yaxis : {tickDecimals: 0, ticks: false},colors:['#da2e22']});
				});
			});
			window.setTimeout(function() {
				login.refreshMarket();
			}, 5000);
		},
		loginerror:function(){
			var errormsg =$("#errormsg").val();
			if(errormsg!="" && errormsg!="/"){
				util.showerrortips("", errormsg);
			}
		},
	    newsHover: function(a) {
	        $(".news-items").removeClass("active");
	        $(".news-items").stop().animate({
	            width: "345px"
	        },
	        50);
	        $(a).stop().animate({
	            width: "450px"
	        },
	        50);
	        $(a).addClass("active");
	    },
		switchCoin: function() {
	        $(".trade-tab").on("click",
	    	        function() {
	    	            var a = $(this);
	    	            var key = a.data().key;
	    	            $(".market-con").css('display','none'); 
	    	            $("."+key+"_market_list").css('display','block'); 
	    	            $(".trade-tab").removeClass("active");
	    	            $("#"+key+"_market").addClass("active");
	    	        })
	    	    }
};
$(function(){
	login.loginerror();
	$("#indexLoginPwd").on("focus",function(){
		login.indexLoginOnblur();
		util.callbackEnter(login.loginIndexSubmit);
	});
	$("#loginbtn").on("click",function(){
		login.loginIndexSubmit();
	});
	login.refreshMarket();	
	$(".news-items").mouseover(function() {
        login.newsHover(this)
    });
    var v = $('#alert').val();
    if(v == 1){
    	var modal = $("#msgdetail");
    	modal.modal('show');
    };
    login.switchCoin();
});
