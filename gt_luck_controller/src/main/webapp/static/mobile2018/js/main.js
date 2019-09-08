

$(function(){
	$('.sl1').each(function(){
		var id = $(this).attr("id")
		var html = '<div class="coinWarp" id="' + $(this).attr("id") + '_box" onclick="hidetabs(this.id);" style="display:none"><div class="coinLitBox coinLitBox2"><ul>';
		$(this).find('option').each(function(){
			html += '<li data-value="'+ $(this).val() +'" id="'+ id + $(this).val() + '" onclick="lsSelect(this.id)">' + $(this).html() + '</li>';
		})
		html += '</ul></div></div>';
		$(".slet").append(html);
	})
}); 

function lsSelect(id){
	var num = id.replace(/[0-9]/ig,"");
	var _val = $("#"+id).data("value");
	var _name = $("#"+id).html();
	$("#"+num + "s").html(_name);
	$("#" + num).val(_val).change();
}

function sll(num){
	console.log(num);
	alert($("#"+num).val());
}


function lwSelect(id,selectid,cho,coin){
        var text = $("#"+id).html();
        var _val = $("#"+id).data("value");
        $("."+ cho +" span").html(text);
        $("."+ coin).css('bottom', '-100%');
        $("#" + selectid + " option:selected").val(_val);
        $("#" + selectid + " option:selected").html(text); 

    }


function showtab(id){
	 $("#"+ id).css("display","block");
	 $("."+ id).animate({bottom:"0"}, 200)
}

function showtabs(id){
	 $("#"+ id).css("display","block");
	 $("#"+ id).animate({bottom:"0"}, 200)
}

function hidetabs(id){
	 $("#"+ id).css("display","none");
	$("#"+id).css('bottom', '-100%');
}

function hidetab(id){
	$("#"+ id).css("display","none");
	$("."+id).css('bottom', '-100%');
}

$(".footer a").click(function(event) {
    $(this).addClass('active').siblings().removeClass('active');
});

$(function(){
	var swiper = new Swiper('.banner', {
        pagination: '.swiper-pagination',
        paginationClickable: true,
        spaceBetween:0,
        centeredSlides: true,
        autoplay: 2500,
        autoplayDisableOnInteraction: true
    });
});

/*$(function(){
    // window.onload = function(){
    //     getRem(750,100)
    // };
    // window.onresize = function(){
    //     getRem(750,100)
    // };        function getRem(pwidth,prem){
    //     var html = document.getElementsByTagName("html")[0];
    //     var oWidth = document.body.clientWidth || document.documentElement.clientWidth;
    //     html.style.fontSize = oWidth/pwidth*prem + "px";
    // }
    
        !function(n){
            var  e=n.document,
                 t=e.documentElement,
                 i=720,
                 d=i/100,
                 o="orientationchange"in n?"orientationchange":"resize",
                 a=function(){
                     var n=t.clientWidth||320;n>720&&(n=720);
                     t.style.fontSize=n/d+"px"
                 };
                 e.addEventListener&&(n.addEventListener(o,a,!1),e.addEventListener("DOMContentLoaded",a,!1))
        }(window);
});
*/

// (function (doc, win) {
//     var docEl = doc.documentElement,
//         resizeEvt = 'onorientationchange' in window ? 'onorientationchange' : 'resize',
//         recalc = function () {
//             var clientWidth = docEl.clientWidth;
//             if (!clientWidth) return;
//             if(clientWidth>=750){
//                 docEl.style.fontSize = '100px';
//             }else{
//                 docEl.style.fontSize = 100 * (clientWidth / 750) + 'px';
//             }
//         };

//     if (!doc.addEventListener) return;
//     win.addEventListener(resizeEvt, recalc, false);
//     doc.addEventListener('DOMContentLoaded', recalc, false);
// })(document, window);
    

$(function(){
    $('.entrust_detail li').click(function(event) {
        var _this=$(this).children('a')
        $(_this).siblings('.entrustCon').slideToggle();
    });
});

function showTabar(){
    $("#topUp2").css('display','block');
    $("#topUp1").css('display','none');
}
function hideTabar(){
    $("#topUp1").css('display','block');
    $("#topUp2").css('display','none');
}

$(function(){
    $('.myset .top a').click(function(event) {
        $(this).toggleClass('en');
    });
});


$(function(){
    $('.trade_btn .tit').click(function(event) {
        var num=$(this).index();
        $(this).addClass('active').siblings().removeClass('active');
        $('.buyCon').eq(num).addClass('active').siblings().removeClass('active');
    });
});


$(function(){
    $('.numBar span').click(function(event) {
        $(this).addClass('active').siblings().removeClass('active');
    });
});

$(".back").click(function(){
    window.history.go(-1);
});


$(function(){
    $('.lookGZ').click(function(event) {
        $('.warpBg').fadeIn(10, function() {
            $('.lookShow').show(1);
        });
    });
});

$(function(){
    $('.yq_btn').click(function(event) {
        $('.warpBg').fadeIn(10, function() {
            $('.yqCode').fadeIn(100, function() {
               $('.lookShow').hide(1);
            });
        });
    });
});

$(function(){
 $('.warpBg').click(function(event) {
        $(this).hide();
        $('.yqCode').hide();
    });
});

