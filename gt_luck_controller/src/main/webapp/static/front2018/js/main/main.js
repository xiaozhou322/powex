//首页

// $(document).scroll(function() {
//      var scrollTop = $(document).scrollTop();  //获取当前滑动位置
//      var sH1=$('.top').height();
//      var sH2=$('.l-header').height();
//      var allH=sH1+sH2
//      if(scrollTop > allH){                 //滑动到该位置时执行代码
//         $(".top").css('display', 'none');
//         $(".l-header").css('background', '#363842');
//      }else{
//         $(".top").css('display', 'block');
//         $(".l-header").css('background', 'rgba(0,0,0,.11)');
//      }
// });






$('.tabs li').click(function(event) {
    var _this=$(this).index();
    $(this).addClass('active').siblings().removeClass('active');
    $('.tableCon').eq(_this).addClass('cur').siblings().removeClass('cur');
});

// 找回密码弹窗
$('.repeatBtn').click(function(event) {
    $('.showWarp').fadeIn(100, function() {
        $('.close_btn').click(function(event) {
            $(this).parent().parent().fadeOut(100);
        });
    });
});

$(document).ready(function(){  
    $(function () {  
        //检测屏幕高度  
        var height=$(window).height(); 
        //scroll() 方法为滚动事件  
        $(window).scroll(function(){  
            if ($(window).scrollTop()>height){  
                $(".goTop").fadeIn(500);  
            }else{  
                $(".goTop").fadeOut(500);  
            }  
        });  
        $(".goTop").click(function(){  
            $('body,html').animate({scrollTop:0},300);  
            return false;  
        });  
    });  
});
//资产管理页


$('.switch-on2').click(function(event){
    
    if($(this).hasClass("switch-on2")){

        $(this).removeClass("switch-on2").addClass("switch-off2");
        $(this).html('<em class="slide1"></em>ON')

    }else{

        $(this).removeClass("switch-off2").addClass("switch-on2");
         $(this).html('<em class="slide1"></em>OFF')
    }
});

$('.nav_item_list').click(function(event) {
    $(this).addClass("active").removeClass("active");
});

$('.btn_01').click(function(event) {
    $(this).parent().parent().siblings('.charge_address_01').stop().slideToggle(10);
    $('.charge_address_02').hide();
});




$('.btn_02').click(function(event) {
    $(this).parent().parent().siblings('.charge_address_02').stop().slideToggle(10);
    $('.charge_address_01').hide();
}); 

$('.s_close').click(function(event) {
    $('.charge_address').fadeOut(100);
});  

//上传图片
function upload(obj){
    var file = obj.files[0];
    var photoExt=obj.value.substr(obj.value.lastIndexOf(".")).toLowerCase();//获得文件后缀名
    if(photoExt!='.jpg'&& photoExt!='.png'&& photoExt!='.jpeg'){
        alert("请上传后缀名为jpg jpeg png的照片!");
        sign=null;
        return;
    }
    var fileSize = 0;
    var isIE = /msie/i.test(navigator.userAgent) && !window.opera;
    if (isIE && !obj.files) {
        var filePath = obj.value;
        var fileSystem = new ActiveXObject("Scripting.FileSystemObject");
        var file = fileSystem.GetFile (filePath);
        fileSize = file.Size;
    }else {
        fileSize = obj.files[0].size;
    }
    fileSize=Math.round(fileSize/2048*100)/100; //单位为KB
    if(fileSize<2048){
        sign=1
    }else{
        alert("照片最大尺寸为2M，请重新上传!");
        sign=null;
        return;
    }
    var reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = function (e) {
        $('.upload_original_on').parent().find('img').attr('src',this.result);
        $('.upload_original_on').parent().find('.gh').html('更换上传').css("color","#488bef");
        $('.upload_original_on').parent().find('.cx').html('重新上传').css("color","#488bef");
    }    
}
function upload_original() {
    var input = $(".file");
    var original =$(".upload_original");
    if (typeof (FileReader) === 'undefined') {
        result.innerHTML = "抱歉，你的浏览器不支持 FileReader，请使用现代浏览器操作！";
        input.attr('disabled', 'disabled');
    } else {
        input.on('change',function () {
            upload(this);
        })
        original.on('click',function () {
            $('.upload_original').removeClass('upload_original_on');
            $(this).addClass('upload_original_on');
            $(this).parent().find('.file').click();
        })
    }
}
$(function () {
    upload_original();
})

//账户设置绑定账号显示
$('.toggleBtn').click(function(event) {
    $(this).parent().siblings().stop().slideToggle();
});

//站内消息，查看信息
$('.a_txt').click(function(event) {
    $(this).parent().siblings().stop().slideToggle(200);
});

//选择币种

$('.coinCur').click(function(event) {
    $('.allCoin').stop().slideToggle(100);
});
$('.allCoin li a').click(function(event) {
     var text = $(this).html();
      $(".coinCur").html(text);
      $('.allCoin').hide();
});
//我的订单
$('.trade_look').click(function(event) {
    $(this).parent().parent().siblings().stop().slideToggle(200);
});
//表格搜索

// $(".searchInput").on("input propertychange change",function(event){
//         var oBt=$('.searchInput').val();
//         var oTab=$('.tableCon')
//     for(var i=0;i<oTab.tBodies[0].rows.length;i++){
//       var str1=oTab.tBodies[0].rows[i].cells[1].innerHTML.toUpperCase();
//       var str2=oBt[0].value.toUpperCase();

//       if(str1==str2){
//        oTab.tBodies[0].rows[i].style.background='red';
//       }
//       else{
//         oTab.tBodies[0].rows[i].style.background='';
//       }

//      if(str1.search(str2)!=-1){oTab.tBodies[0].rows[i].style.background='red';}
//      else{oTab.tBodies[0].rows[i].style.background='';}

//      var arr=str2.split(' ');
//      for(var j=0;j<arr.length;j++)
//      {
//       if(str1.search(arr[j])!=-1){oTab.tBodies[0].rows[i].style.background='red';}
//      }
//      }
// });
// $(".searchInput").on('input',(function(){
//     var coin=$(this).val()
//     coin = coin.toUpperCase();
//      $('.tableCon table :not(:first-child) ').hide()
//      .filter(":contains('" + coin + "')")
//      .show();

// }));

