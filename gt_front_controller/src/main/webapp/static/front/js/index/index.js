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
				var btcprice = 0;
				var ethprice = 0;
				var rate =1;
				var cursymbol = '$';
				var curlang = document.getElementById("curlang").value;
				
				if(curlang=='cn'){
					cursymbol = "￥";
					rate =6.5;
				}else{
					cursymbol = '$';
					rate =1;
				}
				
				$.each(data,function(key,value){
					if (key==13){
							btcprice = value.price;
					}else if(key==14){
							ethprice = value.price;
					}
				});
				$.each(data,function(key,value){
				    var pbits = Number(value.pbits);
				    var vbits = Number(value.vbits);
					$("#"+key+"_total").html(Number(value.total));
					$("#"+key+"_total_1").html(Number(value.total));
					if(Number(value.rose)>=0){
						$("#"+key+"_rose").removeClass("text-danger").removeClass("text-success").addClass("text-danger").html('+'+value.rose+'%');
						$("#"+key+"_price").removeClass("text-danger").removeClass("text-success").addClass("text-danger").html(value.symbol+"<em class='coin_price'>"+util.numFormat(Number(value.price), pbits)+"</em>");
						$("#"+key+"_rose_1").removeClass("text-danger").removeClass("text-success").addClass("text-danger").html('+'+value.rose+'%');
						if(value.symbol=="$"){
	                        $("#"+key+"_price_1").removeClass("text-danger").removeClass("text-danger").removeClass("text-success").addClass("text-danger").html(value.symbol+"<em class='coin_price'>"+util.numFormat(Number(value.price), pbits)+"</em>/<em class='referprice'>" + cursymbol + util.numFormat(Number(value.price)*rate, pbits) +"</em>");
						}else if(value.symbol=="฿"){
	                        $("#"+key+"_price_1").removeClass("text-danger").removeClass("text-danger").removeClass("text-success").addClass("text-danger").html(value.symbol+"<em class='coin_price'>"+util.numFormat(Number(value.price), pbits)+"</em>/<em class='referprice'>" + cursymbol + util.numFormat(Number(value.price)*btcprice*rate, pbits) +"</em>");
						}else if(value.symbol=="E"){
	                        $("#"+key+"_price_1").removeClass("text-danger").removeClass("text-danger").removeClass("text-success").addClass("text-danger").html(value.symbol+"<em class='coin_price'>"+util.numFormat(Number(value.price), pbits)+"</em>/<em class='referprice'>" + cursymbol + util.numFormat(Number(value.price)*ethprice*rate, pbits) +"</em>");
						}
					}else{
						$("#"+key+"_rose").removeClass("text-danger").removeClass("text-success").addClass("text-success").html(value.rose+'%');
						$("#"+key+"_price").removeClass("text-danger").removeClass("text-success").addClass("text-success").html(value.symbol+"<em class='coin_price'>"+util.numFormat(Number(value.price), pbits)+"</em>");

                        $("#"+key+"_rose_1").removeClass("text-danger").removeClass("text-success").addClass("text-success").html(value.rose+'%');
                        if(value.symbol=="$"){
                        	$("#"+key+"_price_1").removeClass("text-danger").removeClass("text-success").addClass("text-success").html(value.symbol+"<em class='coin_price'>"+util.numFormat(Number(value.price), pbits)+"</em>/<em class='referprice'>" + cursymbol + util.numFormat(Number(value.price)*rate, pbits) +"</em>");
                        }else if(value.symbol=="฿"){
                        	$("#"+key+"_price_1").removeClass("text-danger").removeClass("text-success").addClass("text-success").html(value.symbol+"<em class='coin_price'>"+util.numFormat(Number(value.price), pbits)+"</em>/<em class='referprice'>" + cursymbol + util.numFormat(Number(value.price)*btcprice*rate, pbits) +"</em>");
                        }else if(value.symbol=="E"){
                        	$("#"+key+"_price_1").removeClass("text-danger").removeClass("text-success").addClass("text-success").html(value.symbol+"<em class='coin_price'>"+util.numFormat(Number(value.price), pbits)+"</em>/<em class='referprice'>" + cursymbol + util.numFormat(Number(value.price)*ethprice*rate, pbits) +"</em>");
                        }
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
