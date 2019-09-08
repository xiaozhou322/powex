$(function() {
    var turl = $("#isftrademapping").val();
    var starturl = "real/market2.html?symbol=" + turl + "&_t=" + parseInt(Math.random() * 90 + 10);
    getData(starturl);

    var list = document.getElementsByClassName('lw-coinPrice');

    for (var i = 0; i < list.length; i++) {

        var num = new Number(list[i].innerHTML)
        var length = list[i].innerHTML.substr(list[i].innerHTML.length - 1, 1);
        if (length > 3) {
            num = num.toFixed(length);
            list[i].innerHTML = num;
        }
    }


});

var iconList = document.getElementsByClassName('lw-coinName');
for (var i = 0; i < iconList.length; i++) {
    var iconUrl = "real/market2.html?symbol=" + iconList[i].getAttribute('data-setter') + "&_t=" + parseInt(Math.random() * 90 + 10);
    getListData(iconUrl, i);
}



function getData(url) {

    $.get(url, function(data) {
        var rate = 1;
        var fbprice = data.fbprice;
        var cursymbol = '$';
        var curlang = document.getElementById("curlang").value;
        document.getElementById("fbprice").value = fbprice;

        if (curlang == 'cn') {
            cursymbol = '￥';
            rate = 6.5;
        } else {
            cursymbol = '$';
            rate = 1;
        }

        var fbsymbol = $("#ftrademappingtype").val();
        document.getElementById('vold').innerHTML = data.vol;
        var pbits = Number(data.pbits);
        var vbits = Number(data.vbits);
        var hoursdi = util.numFormat(Number(data.low), pbits);
        var hoursgao = util.numFormat(Number(data.high), pbits);
        var kaipan = util.numFormat(Number(data.p_new), pbits);
        document.getElementById('hoursdi').innerHTML = hoursdi;
        document.getElementById('hoursgao').innerHTML = hoursgao;
        document.getElementById('kaipan').innerHTML = fbsymbol + kaipan;
        document.getElementById('kaipancny').innerHTML = "≈" + cursymbol + util.numFormat(Number(data.p_new) * rate * fbprice, pbits) + "";
        document.getElementById("fbprice").value = fbprice;

        if (data.rose == 0) {
            var rose = "+" + data.rose + ".00%";
            document.getElementById('rosed').innerHTML = rose;
            document.getElementById('rosed').className = "lw-coin upPrice";
            return;
        }
        var rose = data.rose.toString();

        if (rose.indexOf('-') == -1) {
            document.getElementById('rosed').innerHTML = "+" + rose + "%";
            document.getElementById('rosed').className = "lw-coin upPrice";
        } else {

            document.getElementById('rosed').innerHTML = rose + "%";
            document.getElementById('rosed').className = "lw-coin downPrice";
        }

    });

}




function getListData(url, id) {
    var rate = 1;
    var coinclass = '';
    $.get(url, function(data) {
        var pbits = Number(data.pbits);
        var vbits = Number(data.vbits);
        var iconList = document.getElementsByClassName('lw-fwf');
        var zuixin = document.getElementsByClassName('lw-zuixin');
        var pnew = util.numFormat(Number(data.p_new) * rate, pbits);
        zuixin[id].innerHTML = pnew;
        $(zuixin[id]).addClass("coin_price").addClass(coinclass);
        if (data.rose == 0) {
            var rose = "+" + data.rose + ".00%";
            iconList[id].className = "lw-fwf cred";
            iconList[id].innerHTML = rose + "<i class='lw-up'></i>";
            return;
        }
        var rose = data.rose.toString();
        if (rose.indexOf('-') == -1) {

            iconList[id].className = "lw-fwf cred";
            iconList[id].innerHTML = "+" + rose + "%<i class='lw-up'></i>";
        } else {

            iconList[id].className = "lw-fwf cgreen";
            iconList[id].innerHTML = rose + "%" + "<i class='lw-down'></i>";
        }
    });

}


$("#sell-price").change(function() {
    var money = $("#sell-price").val();
    var curlang = $("#curlang").val();
    var fbprice = $("#fbprice").val();
    var pbits = $("#cnyDigit").val();
    var rate = 1;
    var cursymbol = 'USDT';

    if (curlang == 'cn') {
        cursymbol = 'CNY';
        rate = 6.5;
    } else {
        cursymbol = 'USDT';
        rate = 1;
    }

    $('.sellCNY').text((money * rate * fbprice).toFixed(pbits) + cursymbol);
});

$("#buy-price").change(function() {
    var money1 = $("#buy-price").val();
    var money = $("#sell-price").val();
    var curlang = $("#curlang").val();
    var fbprice = $("#fbprice").val();
    var pbits = $("#cnyDigit").val();
    var rate = 1;
    var cursymbol = 'USDT';

    if (curlang == 'cn') {
        cursymbol = 'CNY';
        rate = 6.5;
    } else {
        cursymbol = 'USDT';
        rate = 1;
    }
    $('.buyCNY').text((money1 * rate * fbprice).toFixed(pbits) + cursymbol);
});


$(".trade_btns a").click(function() {
    var key = $(this).data("key");
    if (key == 1) {
        $(".buy_tit,.buyCon1").removeClass("active").addClass("active");
        $(".sell_tit, .sellCon").removeClass("active");
    } else {
        $(".sell_tit , .sellCon").removeClass("active").addClass("active");
        $(".buy_tit,.buyCon1").removeClass("active");
    }
    $("#tabOne").css("display", "none");
    $("#tabTwo").css("display", "block");
})

$(".tradeRight li").click(function() {
    var key = $(this).data("key");
    if (key == 3) {
    	$(".textA").addClass("active");
        $(".textB").removeClass("active");
        $(".textS").removeClass("active");
    } else if(key==4){
    	$(".textA").removeClass("active");
    	$(".textB").addClass("active");
    	$(".textS").removeClass("active");
    } else if(key==5){
    	$(".textS").addClass("active");
    	$(".textA").removeClass("active");
    	$(".textB").removeClass("active");
    }else{
    	 $(".textA,.textB,.textS").addClass("active").removeClass("active").removeClass("active");
    }
})  

$(".toback").click(function() {
    $("#tabTwo").css("display", "none");
    $("#tabOne").css("display", "block");
});


$('.moreCoin_btn').click(function(event) {
    $('.moreWarp').animate({
        right: 0
    }, 200)
});
$('.moreWarp').click(function(event) {
    event.stopPropagation();
    $(this).animate({
        right: -100 + '%'
    }, 200)
});
$(".moreCoin ol li").click(function(event) {
    event.stopPropagation();
    var num = $(this).index()
    $(this).addClass('active').siblings().removeClass("active");
    $('.coinList_item').eq(num).addClass('active').siblings().removeClass("active");
});

$(".s_tab").click(function(event) {
    event.stopPropagation();
    var num = $(this).index()
    $(".s_tab").removeClass("active");
    $(this).addClass('active');
    $('.lw-tabCon').eq(num).addClass('active').siblings().removeClass("active");
});

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