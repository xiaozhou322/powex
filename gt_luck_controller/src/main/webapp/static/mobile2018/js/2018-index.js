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
        var curlang = document.getElementById("curlang").value;
        
        if(curlang=='cn'){
            cursymbol = '￥';
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
                $("#"+key+"_rose_1").removeClass("text-danger").removeClass("text-success").addClass("text-danger").html('+'+value.rose+'%');
                $("#"+key+"_price").removeClass("text-danger").removeClass("text-success").addClass("text-danger").html("<em class='coin_price'>"+util.numFormat(Number(value.price), pbits)+"</em>");
                $("#"+key+"_cny").html(util.numFormat(Number(value.price)*6.5, pbits));
                if(value.symbol=="$"){
                    $("#"+key+"_price_1").removeClass("text-danger").removeClass("text-danger").removeClass("text-success").addClass("text-danger").html("<em class='coin_price'>"+util.numFormat(Number(value.price), pbits)+"</em><em class='referprice'>" + cursymbol + util.numFormat(Number(value.price)*rate, pbits) +"</em>");
                }else if(value.symbol=="฿"){
                    $("#"+key+"_price_1").removeClass("text-danger").removeClass("text-danger").removeClass("text-success").addClass("text-danger").html("<em class='coin_price'>"+util.numFormat(Number(value.price), pbits)+"</em><em class='referprice'>" + cursymbol + util.numFormat(Number(value.price)*btcprice*rate, pbits) +"</em>");
                }else if(value.symbol=="E"){
                    $("#"+key+"_price_1").removeClass("text-danger").removeClass("text-danger").removeClass("text-success").addClass("text-danger").html("<em class='coin_price'>"+util.numFormat(Number(value.price), pbits)+"</em><em class='referprice'>" + cursymbol + util.numFormat(Number(value.price)*ethprice*rate, pbits) +"</em>");
                }
            }else{

                $("#"+key+"_rose_1").removeClass("text-danger").removeClass("text-success").addClass("text-success").html(value.rose+'%');
                $("#"+key+"_rose").removeClass("text-danger").removeClass("text-success").addClass("text-success").html(value.rose+'%');
                $("#"+key+"_price").removeClass("text-danger").removeClass("text-success").addClass("text-success").html("<em class='coin_price'>"+util.numFormat(Number(value.price), pbits)+"</em>");
                $("#"+key+"_cny").html(util.numFormat(Number(value.price)*6.5, pbits))
                if(value.symbol=="$"){
                    $("#"+key+"_price_1").removeClass("text-danger").removeClass("text-success").addClass("text-success").html("<em class='coin_price'>"+util.numFormat(Number(value.price), pbits)+"</em><em class='referprice'>" + cursymbol + util.numFormat(Number(value.price)*rate, pbits) +"</em>");
                }else if(value.symbol=="฿"){
                    $("#"+key+"_price_1").removeClass("text-danger").removeClass("text-success").addClass("text-success").html("<em class='coin_price'>"+util.numFormat(Number(value.price), pbits)+"</em><em class='referprice'>" + cursymbol + util.numFormat(Number(value.price)*btcprice*rate, pbits) +"</em>");
                }else if(value.symbol=="E"){
                    $("#"+key+"_price_1").removeClass("text-danger").removeClass("text-success").addClass("text-success").html("<em class='coin_price'>"+util.numFormat(Number(value.price), pbits)+"</em><em class='referprice'>" + cursymbol + util.numFormat(Number(value.price)*ethprice*rate, pbits) +"</em>");
                }
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
    var swiper = new Swiper('.banner', {
        pagination: '.swiper-pagination',
        paginationClickable: true,
        spaceBetween: 30,
        centeredSlides: true,
        autoplay: 2500,
        autoplayDisableOnInteraction: false
    });
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
    var swiper = new Swiper('.news_all', {
        pagination: '.swiper-pagination',
        paginationClickable: true,
        direction: 'vertical',
        autoplay:2500
    });
})