
	$(function() {
        var turl = $("#isftrademapping").val();
        var starturl = "real/market2.html?symbol="+ turl +"&_t=" + parseInt(Math.random()*90+10);
        getData(starturl);

      var list = document.getElementsByClassName('lw-coinPrice');

	 for(var i = 0; i < list.length;i++){	

        var num = new Number(list[i].innerHTML)
        var length = list[i].innerHTML.substr(list[i].innerHTML.length-1,1);
        if(length>3)
        {
            num = num.toFixed(length);
            list[i].innerHTML = num;
        }
    	}


    });

    var iconList = document.getElementsByClassName('lw-coinName');
    for(var i = 0; i < iconList.length;i++){
        var iconUrl = "real/market2.html?symbol=" + iconList[i].getAttribute('data-setter') + "&_t=" + parseInt(Math.random()*90+10);
        getListData(iconUrl,i);
    }



     $("#search").bind('input',(function(){

        var coin = $("#search").val();
        coin = coin.toUpperCase();

        if(coin == ''){
            $(".market_show").each(function(){
                $(this).show().removeClass('markent_h').addClass('markent_s');
            });
        }else{

            $(".market_show").each(function(){
            if($(this).data('key') == ''){
                return true;
            }else{
              
                if($(this).data('key').indexOf(coin) != -1){
                    $(this).show().addClass('markent_s').removeClass('markent_h');
                }else{
                    $(this).hide().removeClass('markent_s').addClass('markent_h');
                }
            }
            
        });
      }
    }));



    $('.coin_filter ol .txtCenterLI').click(function(event) {
        var _this=$(this).index();
        $(this).addClass('active').siblings('').removeClass('active');
        $('.coin_list .ul_list').eq(_this).addClass('active').siblings('').removeClass('active');
    });

//    五角星自选
    $('.Btn_optional').click(function(event) {
       debugger
    });

     $("#sell-price").change(function(){
	  	 var money = $("#sell-price").val();
	  	 var curlang = $("#curlang").val();
	  	 var fbprice = $("#fbprice").val();
	  	 var pbits  = $("#cnyDigit").val();
	  	 var rate = 1;
	  	 var cursymbol = 'USDT';
		
		 if(curlang=='cn'){
			cursymbol = 'CNY';
			rate =6.5;
		 }else{
			cursymbol = 'USDT';
			rate =1;
		 }
		console.log(fbprice);
	     $('.sellCNY').text((money*rate*fbprice).toFixed(pbits)+cursymbol);
	  });

	  $("#buy-price").change(function(){
	  	 var money1 = $("#buy-price").val();
	  	var money = $("#sell-price").val();
	  	 var curlang = $("#curlang").val();
	  	 var fbprice = $("#fbprice").val();
	  	 var pbits  = $("#cnyDigit").val();
	  	 var rate = 1;
	  	 var cursymbol = 'USDT';
		
		 if(curlang=='cn'){
			cursymbol = 'CNY';
			rate =6.5;
		 }else{
			cursymbol = 'USDT';
			rate =1;
		 }
	     $('.buyCNY').text((money1*rate*fbprice).toFixed(pbits)+cursymbol);
	  });


       function getData(url){
        $.get(url,function(data){ 
        	var rate =1;
			var fbprice = data.fbprice;
			var cursymbol = '$';
			var curlang = document.getElementById("curlang").value;
			document.getElementById("fbprice").value=fbprice;
			if(curlang=='cn'){
				cursymbol = '￥';
				rate =6.5;
			}else{
				cursymbol = '$';
				rate =1;
			}
			var fbsymbol = '$';
        	var pbits = Number(data.pbits);
    		var vbits = Number(data.vbits);
        	document.getElementById('vold').innerHTML = data.vol;
        	var kaipan  = util.numFormat(Number(data.p_new), pbits);
        	var hoursdi = util.numFormat(Number(data.low), pbits);
            var hoursgao = util.numFormat(Number(data.high), pbits);
        	document.getElementById('kaipan').innerHTML =fbsymbol+ kaipan ;
        	document.getElementById('hoursdi').innerHTML = hoursdi;
            document.getElementById('hoursgao').innerHTML = hoursgao;
        	document.getElementById('kaipancny').innerHTML ="/" + cursymbol +util.numFormat(Number(data.p_new)*rate*fbprice, pbits);
        	document.getElementById('kaipancnyLI').innerHTML ="/" + cursymbol +util.numFormat(Number(data.p_new)*rate*fbprice, pbits);
        	$('#kaipan').addClass("coin_price");
        	if(data.rose == 0){
        		var rose = "+" + data.rose + ".00%";
        		document.getElementById('rosed').innerHTML = rose;
        		document.getElementById('rosed').className = "lw-coin cred";
        		return;
        	}
        	var rose = data.rose.toString();

        	if(rose.indexOf('-') == -1){
        		document.getElementById('rosed').innerHTML = "+" + rose + "%";
        		document.getElementById('rosed').className = "lw-coin cred";
        	}else{
        		document.getElementById('rosed').innerHTML = rose + "%";
        		document.getElementById('rosed').className = "lw-coin cgreen2";
        	}
		
		});

    }


    function getListData(url,id){
	 var rate = 1;
	 var coinclass =  '';
     $.get(url,function(data){
    	var pbits = Number(data.pbits);
		var vbits = Number(data.vbits);
        var iconList = document.getElementsByClassName('lw-fwf');
        var zuixin = document.getElementsByClassName('lw-zuixin');
        var pnew = util.numFormat(Number(data.p_new)*rate, pbits);
        zuixin[id].innerHTML = pnew;
        $(zuixin[id]).addClass("coin_price").addClass(coinclass);
        if(data.rose == 0){
        	var rose = "+" + data.rose + ".00%";
        	iconList[id].className = "lw-fwf cred";
        	iconList[id].innerHTML = rose + "<i class='lw-up'></i>";
        	return;
        }
        var rose = data.rose.toString();
        if(rose.indexOf('-') == -1){

        	iconList[id].className = "lw-fwf cred";
        	iconList[id].innerHTML = "+" + rose + "%<i class='lw-up'></i>";
        }else{
        	
        	iconList[id].className = "lw-fwf cgreen2";
        	iconList[id].innerHTML = rose + "%" + "<i class='lw-down'></i>";
        }
		});

    }


    $("#search").bind('input',(function(){
        var coin = $("#search").val();
        coin = coin.toUpperCase();
        if(coin == ''){
            $(".market_show").each(function(){
                $(this).show()
            });
        }else{

            $(".market_show").each(function(){
            if($(this).data('key') == ''){
                return true;
            }else{
              
                if($(this).data('key').indexOf(coin) != -1){
                    $(this).show();
                }else{
                    $(this).hide();
                }
            }
            
        });
      }
    }));



var seconds = 60;
//定时调用函数
var timer = setInterval(timeRun, 60000);
//倒计时函数
function timeRun(){

    if (seconds <= 0) {
        var tourl = $("#isftrademapping").val();
        clearInterval(timer);
        var dscurrent = document.getElementById('symbol');
        var dsurl = "real/market2.html?symbol="+ tourl +"&_t=" + parseInt(Math.random()*90+10);
        getData(dsurl);
        seconds = 2;
        timer = setInterval(timeRun, 2000);
        //获取其他币种的涨跌幅
        var iconList = document.getElementsByClassName('lw-coinName');
        for(var i = 0; i < iconList.length;i++){
            var iconUrl = "real/market2.html?symbol=" + iconList[i].getAttribute('data-setter') + "&_t=" + parseInt(Math.random()*90+10);
            getListData(iconUrl,i);
        }


        return;
    }
    seconds --;
}