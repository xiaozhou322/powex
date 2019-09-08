//切换语言
function langs(lang)
{
  var Url = window.location.href;
  if(Url.indexOf('?') == -1){
    Url = Url + "?"+ lang;
    window.location.href = Url;
    return ;
  }else{
    if(Url.indexOf('lang') == -1){
      Url = Url + "&" + lang;
      window.location.href = Url;
      return ;
    }else{
      if(Url.indexOf(lang) == -1){
        var nums = Url.indexOf("lang=");
        var danx = Url.substring(nums, nums+10);
        Url = Url.replace(danx,lang);
        window.location.href = Url;
        return ;
       
      }else{
        return;
      }
    }
  }

}

function refreshMarket()
{
var url="/real/indexmarket.html?random=" + Math.round(Math.random() * 100);
    $.post(url,{},function(data){
        var btcprice = 0;
        var ethprice = 0;
        var rate =1;
        var cursymbol = '$';
        var curlang = $("#curlang").val();
        
        if(curlang=='cn'){
            cursymbol = '¥';
            rate =6.5;
        }else{
            cursymbol = '$';
            rate =1;
        }
        
        $.each(data,function(key,value){
            var pbits = Number(value.pbits);
            var vbits = Number(value.vbits);
            $("#"+key+"_total").html(Number(value.total));
            $("#"+key+"_total_1").html(Number(value.total));
			var fbprice = Number(value.fbprice);
			var usdtrate = Number(value.usdtrate);
			if(curlang=='cn'){
				rate = usdtrate;
			}
			
            if(Number(value.rose)>=0){
                $("#"+key+"_rose").removeClass("text-danger").removeClass("text-success").addClass("text-danger").html('<span style="background: #ff5e3a;color: #fff;font-size: 0.16rem;font-family: SimHei;display: inline-block;min-width: 0.6rem;height: 0.24rem;line-height: 0.24rem;">+'+value.rose+'%</span>');
            	
                $("#"+key+"_rose_1").removeClass("text-danger").removeClass("text-success").addClass("text-danger").html('+ '+value.rose+'%');
                $("#"+key+"_price").removeClass("text-danger").removeClass("text-success").addClass("text-danger").html(""+util.numFormat(Number(value.price), pbits)+"");
                $("#"+key+"_cny").html(util.numFormat(Number(value.price)*fbprice*rate, pbits));
                
                $("#"+key+"_price_1").removeClass("text-danger").removeClass("text-danger").removeClass("text-success").addClass("text-danger").html(""+util.numFormat(Number(value.price), pbits)+"<em class='referprice'>" + cursymbol + util.numFormat(Number(value.price)*fbprice*rate, pbits) +"</em>");
                
            }else{

                $("#"+key+"_rose_1").removeClass("text-danger").removeClass("text-success").addClass("text-success").html(value.rose+'%');
                $("#"+key+"_rose").removeClass("text-danger").removeClass("text-success").addClass("text-success").html('<span style="background: #76c80e;color: #fff;font-size: 0.16rem;font-family: SimHei;display: inline-block;min-width: 0.6rem;height: 0.24rem;line-height: 0.24rem;">'+value.rose+'%</span>');
                $("#"+key+"_price").removeClass("text-danger").removeClass("text-success").addClass("text-success").html(""+util.numFormat(Number(value.price), pbits)+"");
                $("#"+key+"_cny").html(util.numFormat(Number(value.price)*fbprice*rate, pbits))
                $("#"+key+"_price_1").removeClass("text-danger").removeClass("text-success").addClass("text-success").html(""+util.numFormat(Number(value.price), pbits)+"<em class='referprice'>" + cursymbol + util.numFormat(Number(value.price)*fbprice*rate, pbits) +"</em>");
                
            }
            
        
        });
    });
}
$(document).ready(function(){
    refreshMarket()
    setInterval(refreshMarket,5000)
})

//banner
$(function(){
  /*  var swiper = new Swiper('.banner', {
        pagination: '.swiper-pagination',
        paginationClickable: true,
        spaceBetween: 30,
        centeredSlides: true,
        autoplay: 2500,
        autoplayDisableOnInteraction: false
    });*/
}); 

//switch
$(function(){
        $(".lw_tab").click(function(event) {
        var a = $(this);
        var key = a.data().key;

        if(key!=0){
            $(".market-con").css('display','none'); 
            $("."+key+"_market_list").css('display','block'); 
        }else{
            $(".market-con").css('display','block'); 
        }
        $(".lw_tab").removeClass("active");
        $("#"+key+"_market").addClass("active");
    });
})

// 公告
$(function(){
   /* var swiper = new Swiper('.news_all', {
        pagination: '.swiper-pagination',
        paginationClickable: true,
        direction: 'vertical',
        autoplay:2500
    });*/
})