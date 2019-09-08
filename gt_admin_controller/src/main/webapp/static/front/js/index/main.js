(function($){

	var z = {
		init : function(){
			// this.headerFixedFun();
            this.agreeFun();
            this.tixianWarpFun();
			
		}
	};
	$.extend(z,{
		// headerFixedFun : function(){		
  //           $(document).scroll(function() {
  //                var scrollTop = $(document).scrollTop();  //获取当前滑动位置
  //                if(scrollTop > 60){                 //滑动到该位置时执行代码
  //                	$(".lw-header").addClass('lw-fixed')
  //                }else{
  //                	$(".lw-header").removeClass('lw-fixed')
  //                }
  //           });
		// },
        agreeFun : function(){      
            $(".lw-indexReg .lw-remeber span").click(function(event) {
                $(this).toggleClass('hasRight');
            });
        },      
        tixianWarpFun : function(){      
            $(".lw-addAddress a").click(function(event) {
                $(".lw-capitalWarp").fadeIn();
                
            });       
             $(".lw-capitalWarp .lw-close").click(function(event) {
                 $(".lw-capitalWarp").fadeOut();
             });
        },
	});
	z.init();

})(jQuery);