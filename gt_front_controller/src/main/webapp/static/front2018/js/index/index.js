var vm ;

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
				    var pbits = Number(value.pbits);
				    var vbits = Number(value.vbits);
				    var fbprice = Number(value.fbprice);
				    var usdtrate = Number(value.usdtrate);
				    if(curlang=='cn'){
				    	rate = usdtrate;
				    }
					$("#"+key+"_total").html(Number(value.total));
					$("#"+key+"_total_1").html(Number(value.total));
					$("#"+key+"_total_24rmb").html(cursymbol+util.numFormat(Number(value.total*value.price*fbprice*usdtrate),2));
					if(Number(value.rose)>=0){
						$("#"+key+"_rose").removeClass("text-danger").removeClass("text-success").addClass("text-danger").html('+'+value.rose+'%');
						$("#"+key+"_price").removeClass("text-danger").removeClass("text-success").addClass("text-danger").html(value.symbol+"<em class='coin_price'>"+util.numFormat(Number(value.price), pbits)+"</em>");
						$("#"+key+"_high_1").removeClass("text-danger").removeClass("text-success").html(value.symbol+"<em class='coin_price'>"+util.numFormat(Number(value.high), pbits)+"</em>");
						$("#"+key+"_low_1").removeClass("text-danger").removeClass("text-success").html(value.symbol+"<em class='coin_price'>"+util.numFormat(Number(value.low), pbits)+"</em>");
						$("#"+key+"_rose_1").removeClass("text-danger").removeClass("text-success").addClass("text-danger").html('+'+value.rose+'%');
						
	                    $("#"+key+"_price_1").removeClass("text-danger").removeClass("text-danger").removeClass("text-success").addClass("text-danger").html(value.symbol+"<em class='coin_price'>"+util.numFormat(Number(value.price), pbits)+"</em>/<em class='referprice'>" + cursymbol + util.numFormat(Number(value.price)*fbprice*rate, pbits) +"</em>");

					}else{
						$("#"+key+"_rose").removeClass("text-danger").removeClass("text-success").addClass("text-success").html(value.rose+'%');
						$("#"+key+"_price").removeClass("text-danger").removeClass("text-success").addClass("text-success").html(value.symbol+"<em class='coin_price'>"+util.numFormat(Number(value.price), pbits)+"</em>");
						$("#"+key+"_high_1").removeClass("text-danger").removeClass("text-success").html(value.symbol+"<em class='coin_price'>"+util.numFormat(Number(value.high), pbits)+"</em>");
						$("#"+key+"_low_1").removeClass("text-danger").removeClass("text-success").html(value.symbol+"<em class='coin_price'>"+util.numFormat(Number(value.low), pbits)+"</em>");
						
                        $("#"+key+"_rose_1").removeClass("text-danger").removeClass("text-success").addClass("text-success").html(value.rose+'%');
                        
                        $("#"+key+"_price_1").removeClass("text-danger").removeClass("text-success").addClass("text-success").html(value.symbol+"<em class='coin_price'>"+util.numFormat(Number(value.price), pbits)+"</em>/<em class='referprice'>" + cursymbol + util.numFormat(Number(value.price)*fbprice*rate, pbits) +"</em>");

                    }
					
				/*$.plot($("#"+key+"_plot"), [{shadowSize:0, data:value.data}],{ series: {lines: { show: true, lineWidth: 1 }}, grid: { borderWidth: 0}, xaxis: { mode: "time", ticks: false}, yaxis : {tickDecimals: 0, ticks: false},colors:['#da2e22']});*/
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
	    	        	alert(1);
	    	           
	    	            var key = a.data().key;
	    	            $(".market-con").css('display','none'); 
	    	            $("."+key+"_market_list").css('display','block'); 
	    	            $(".trade-tab").removeClass("active");
	    	            $("#"+key+"_market").addClass("active");
	    	        })
	    	    }
};

function initIndex(){
	var url="/index1.html?random=" + Math.round(Math.random() * 100);
	 jQuery.post(url, {}, function (data) {
		 
		 var locale=$("#locale").val();
		var html=""; 
		var html1="";
		var html2="";
	    if(data!=null&&data.code==1001) {
	    	
	    }else if(data!=null&&data.code==1002){	    	
	    	 $.each(data.fMap,function(index,map){
	    		 $.each(map.value,function(index1,map1){
	    			html+= '<li class="clear '+map.key.fid +'_market_list market-con market_show markent_s" data-mark="'+map.key.fid +'" data-key="'+map1.fvirtualcointypeByFvirtualcointype2.fShortName +'">';
	    			html+= '<em class="emWidth"><a href="/trademarket.html?symbol='+map1.fid+'"><i></i>'+map1.fvirtualcointypeByFvirtualcointype2.fShortName +'/'+map.key.fShortName +'</a></em>';
	    			html+= ' <em class="emWidth em_2"><span id="'+map1.fid +'_price_1">--</span></em>';
	    			html+= ' <em class="emWidth"><span id="'+map1.fid +'_total_1">--</span></em>';
	    			html+= ' <em class="emWidth" id="'+map1.fid +'_rose_1">--</em>';
	    			html+= ' <em class="emWidth c_upPrice" id="'+map1.fid +'_plot"></em>';
	    			html+= ' <em class="emWidth tradeBtn"><a href="/trademarket.html?symbol='+map1.fid +'">全球市场</a></em>';
	    			html+= ' </li>  ';
		    	 });
	    	 });
	    	 $.each(data.articles[0].value,function(index,v){
	    		 if(locale == 'zh_CN'){
	    			 
	    			html1+=' <li><a href="/service/article.html?id='+v.fid +'">['+v.fcreateDate+'] '+v.ftitle_cn +'</a></li>' ;
	    		 }else{
	    			html1+=' <li><a href="/service/article.html?id='+v.fid +'">['+v.fcreateDate+'] '+v.ftitle +'</a></li>' ;
	    		 }
	    		 
	    	 });
	    	 
	    	 
	    	 $.each(data.articles[1].value,function(index,v){
	    		 if(locale == 'zh_CN'){
	    			 
	    			html2+=' <li><a href="/service/article.html?id='+v.fid +'">['+v.fcreateDate+'] '+v.ftitle_cn +'</a></li>' ;
	    		 }else{
	    			html2+=' <li><a href="/service/article.html?id='+v.fid +'">['+v.fcreateDate+'] '+v.ftitle +'</a></li>' ;
	    		 }
	    		 
	    	 });
		    	 	    	 
	    	 
	    	// vm.get(); 
	    	
            $("#ul1").html(html);
	        $("#ul2").html(html1);
	    	$("#ul3").html(html2);
	    		login.refreshMarket();	
	    }   
	        
	    },"json");
}


$(function(){
	  
//	initIndex();
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
