String.prototype.format = function(args) {
	var result = this;
	if (arguments.length > 0) {
		for ( var i = 0; i < arguments.length; i++) {
			if (arguments[i] != undefined) {
				var reg = new RegExp("({[" + i + "]})", "g");
				result = result.replace(reg, arguments[i]);
			}
		}
	}
	return result;
};

/*
 * jQuery placeholder, fix for IE6,7,8,9
 * @author JENA
 * @since 20131115.1504
 * @website ishere.cn
 */
var JPlaceHolder = {
    //检测
    _check : function(){
        return 'placeholder' in document.createElement('input');
    },
    //初始化
    init : function(){
        if(!this._check()){
            this.fix();
        }
    },
    //修复
    fix : function(){
        jQuery(':input[placeholder]').each(function(index, element) {
            var self = $(this), txt = self.attr('placeholder');
            self.wrap($('<div></div>').css({position:'relative',display:'inline-block', zoom:'1', border:'none', background:'none', padding:'none', margin:'none'}));
            var pos = self.position(), h = self.outerHeight(true), paddingleft = self.css('padding-left');
            var holder = $('<span></span>').text(txt).css({position:'absolute', left:pos.left, top:'8px', height:h, lienHeight:h, paddingLeft:paddingleft, color:'#aaa'}).appendTo(self.parent());
            self.focusin(function(e) {
                holder.hide();
            }).focusout(function(e) {
                if(!self.val()){
                    holder.show();
                }
            });
            holder.click(function(e) {
                holder.hide();
                self.focus();
            });
        });
    }
};
//执行
$(function(){
    JPlaceHolder.init();    
});

function centerModals() {
	$('.modal').each(function(i) {
		var $clone = $(this).clone().css('display', 'block').appendTo('body');
		var modalHeight = $clone.find('.modal-content').height();
		var width = $clone.find('.modal-content').width();
		var top = Math.round(($clone.height() - modalHeight) / 2);
		top = top > 0 ? top : 0;
		$clone.remove();
		$(this).find('.modal-content').css("margin-top", top);
		$(this).find('.modal-mark').css("height", modalHeight + 20).css("width", width + 20);
	});
}

$('.modal').on('show.bs.modal', centerModals);
$(window).on('resize', centerModals);

util.lrFixFooter("#allFooter");

$(function() {
	var speed = 8000;
	var count = 0;
	var newstoplist = jQuery("#newsList p");
	var sumCount = jQuery("#newsList p").length;
	function Marquee() {
		jQuery("#newsList p").hide();
		if(count>sumCount){
			count=0;
		}
		jQuery("#newsList p:eq("+count+")").fadeToggle(2000);
		++count;
	}
	Marquee();
	var MyMar = setInterval(Marquee, speed);
	newstoplist.onmouseover = function() {
		clearInterval(MyMar);
	};
	newstoplist.onmouseout = function() {
		MyMar = setInterval(Marquee, speed);
	};
});

$(function(){
	$(".leftmenu-folding").on("click",function(){
		var that=$(this);
		$("."+that.data().folding).slideToggle("fast"); 
	});
	$(".help_list li").mousemove(function() {
        var long = 104;
        $(this).data("long") && (long = 134),
        $(this).find("span").stop().animate({
            width: long + "px"
        },
        200);
    }).mouseout(function() {
        $(this).find("span").stop().animate({
            width: "0px"
        },
        200);
    });
    $(".lan-tab-hover").on("click",
    function() {
    	var lang = $(this).data().lan;
    	lanTab(lang);
    });
});

function lanTab(lang){
	var url = "/real/switchlan.html?random=" + Math.round(Math.random() * 100);
	var param = {
		lang : lang
	};
	$.post(url, param, function(data) {
		if(data.code ==0){
			window.location.reload(false) ;
		}
	}, "json");
}

/*$(function(){
if(navigator.userAgent.toLowerCase().indexOf("chrome") != -1){
    var inputers = document.getElementsByTagName("input");  
    for(var i=0;i<inputers.length;i++){  
        if((inputers[i].type !== "submit") && (inputers[i].type !== "password")){  
            inputers[i].disabled= true;  
        }  
    }  
    setTimeout(function(){  
        for(var i=0;i<inputers.length;i++){  
            if(inputers[i].type !== "submit"){  
                inputers[i].disabled= false;  
            }  
        }  
    },100);
}
});*/

/** *******QQ登录****************************** */	
function openqq(url){
	if(url==null||url==""){
		url=window.location.href;
	}
	window.open('/qqLogin?url='+url,'new','height='+550+',,innerHeight='+550+',width='+800+',innerWidth='+800+',top='+200+',left='+200+',toolbar=no,menubar=no,scrollbars=auto,resizeable=no,location=no,status=no');
}

function showTips(text){
	util.showerrortips("", text, {
		okbtn : function() {
			$('#alertTips').modal('hide');
		}
	});
}


function toUtf8(str) {
	var out, i, len, c;
	out = "";
	len = str.length;
	for(i = 0; i < len; i++) {
		c = str.charCodeAt(i);
		if ((c >= 0x0001) && (c <= 0x007F)) {
			out += str.charAt(i);
		} else if (c > 0x07FF) {
			out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));
			out += String.fromCharCode(0x80 | ((c >>  6) & 0x3F));
			out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));
		} else {
			out += String.fromCharCode(0xC0 | ((c >>  6) & 0x1F));
			out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));
		}
	}
	return out;
}

function scientificToNumber(num) {
	var str = num.toString();
	var reg = /^(\d+.?\d+|\d+)(e)([\-]?\d+)$/;
	var arr, len,
		zero = '';
	if (!reg.test(str)) {
		return num;
	} else {
		arr = reg.exec(str);
		var tnum = arr[1];
		tnum = tnum.replace(/\./,"");
		len = Math.abs(arr[3]) - 1;
		for (var i = 0; i < len; i++) {
			zero += '0';
		}

		return '0.' + zero + tnum;
	}
}