var vm ;
var time=0;
var currentPage=1;

$(function() {


	/* 大转盘 */
	tplaybtn();
	 $(".asdbtn").on("click",function(){
		   $(".lucky").show();
		   $(".comModule").hide();
		   $(".fixedModule").hide();
	  });
	$(".btn_desc").click(function() {
		$(".a_rule,.body_back,.rule_close").show();
	});

	$(".rule_close").click(function() {
		$(".a_rule,.body_back,.rule_close").hide();
	})

	$(".close2x").on("click", function() {
		$(".lucky").show();
		$(".ngrealModule").hide();
		$(".comModule").hide();
		$(".realfixed").hide();
		
	});
	$(".asdbtn").on("click", function() {
		$(".ngrealModule").hide();
	});
	lottery.drawLotteryUser();
//	  抽奖活动
	  $(".realclose").on("click",function(){
		$(".realfixed").hide();
		$(".POWRechargeBox").hide();
		$(".POWModuleS").hide();
		$(".lucky").show();
		$(".fixedModule").show();
		
	})
	/*项目账号内容滑动*/
	var mySwiper = new Swiper('.swiper-container', {
		pagination: '.pagination',
		loop: true,
		grabCursor: true,
		paginationClickable: true
	})
});

	// 获取更多POW	0、kyc1；1、kyc2； 2、google认证；3、交易密码；
	function ftradeBtn(){
	//	$(".POWRecharge").show();
		$(".POWModuleS").show();

	}

	/* 走马灯 */
	function trottingUl(item){
		var num=0;
		setInterval(function(){
			num++;
			if(num==item){
				$('.hezi').css({'top':'0px'});
				num=0;
			}
			$('.hezi').stop().animate({'top':-60*num+'px'},200)
		},3000)
	}
	/* 大转盘 */

	function tplaybtn(){
		var $btn = $('.playbtn');
		var isture = 0;
		var clickfunc = function(data) {
			switch(data) {
				case 1:
					rotateFunc(1, 0, '恭喜您，获得一等奖！<a style="color: #ff6600;display: block;" href="/introl/redPocket.html">前往糖果红包查看</a>');
					break;
				case 2:
					rotateFunc(2, 40, '恭喜您，获得三等奖！<a style="color: #ff6600;display: block;" href="/introl/redPocket.html">前往糖果红包查看</a>');

					break;
				case 3:
					rotateFunc(3, 80, '恭喜您，获得四等奖！<a style="color: #ff6600;display: block;" href="/introl/redPocket.html">前往糖果红包查看</a>');

					break;
				case 4:
					rotateFunc(4, 120, '谢谢参与~再来一次吧~');
					break;
				case 5:
					rotateFunc(5, 160, '恭喜您，获得二等奖！<a style="color: #ff6600;display: block;" href="/introl/redPocket.html">前往糖果红包查看</a>');
					break;

				case 6:
					rotateFunc(6, 200, '恭喜您，获得三等奖！<a style="color: #ff6600;display: block;" href="/introl/redPocket.html">前往糖果红包查看</a>');
					break;
				case 7:
					rotateFunc(7, 240, '恭喜您，获得四等奖！<a style="color: #ff6600;display: block;" href="/introl/redPocket.html">前往糖果红包查看</a>');
					break;
				case 8:
					rotateFunc(8, 280, '谢谢参与~再来一次吧~');
					break;
				case 9:
					rotateFunc(9, 320, '恭喜您，获得四等奖！<a style="color: #ff6600;display: block;" href="/introl/redPocket.html">糖果红包查看</a>');
					break;
			}
		}
		$btn.click(function() {
			/*$(".comModule").hide();
			$(".fixedModule").hide();
			$(".ngrealModule").hide();*/
				lotterySubmit();				
		});
		
		
		var no=[4,8];
		var three=[2,6];
		var four=[3,7,9];
		var lotterySubmit = function() {
		util.hideerrortips("indexLoginTips");
		var url = "/activitity/lottery.html?random="
				+ Math.round(Math.random() * 100);
		var id = document.getElementById("activity_id").value;
		var param = {
			id : id,
		};
		jQuery.post(url,param,function(data) {
			var playnum = $('.playnum').html();

			if (data.code == -1) {
				isture = false;
				util.showMsg(data.msg);
			}
			if (data.code == 1) {
				playnum = playnum - 1; // 执行转盘了则次数减1
				var nn = Math.round(Math.random() * 1);						
				clickfunc(no[nn]);
			} else if (data.code == 0) {
				playnum = playnum - 1; // 执行转盘了则次数减1
				// 中一等奖
				if (data.type == 1) {
					clickfunc(1);
				} else if (data.type == 2) {
					clickfunc(5);
				} else if (data.type == 3) {
					var tt = Math.round(Math.random() * 1);
					clickfunc(three[tt]);
				} else if (data.type == 4) {
					var ff = Math.round(Math.random() * 2);
					clickfunc(four[ff]);
				} else {
					clickfunc(8);
				}
			} else if (data.code == -3) {
				// 没有币
				$(".lucky").hide();
				$(".ngrealModule").show();
				$(".powModules").html('前往<label class="lotterKYC">POW</label>充值')
				$(".lotterKYCBtn").attr('href',"/financial/index.html");
				$(".powModulesBtn").html('前往充值');
			} else if (data.code == 101) {
				// kyc1认证
				$(".lucky").hide();
				$(".ngrealModule").show();
				$(".powModules").html('前往<label class="lotterKYC">KYC1</label>认证获得1POW奖励');
				$(".powModulesBtn").html('点击KYC1认证');
				$(".lotterKYCBtn").attr('href',"/user/realCertification.html");
					
			} else if (data.code == 102) {
				// kyc2认证
				$(".lucky").hide();
				$(".ngrealModule").show();
				$(".powModules").html('前往<label class="lotterKYC">KYC2</label>认证获得1POW奖励');
				$(".powModulesBtn").html('点击KYC2认证');
				$(".lotterKYCBtn").attr('href',"/user/realCertification.html");
				
			} else if (data.code == -4) {
				window.location.href = '/user/login.html';
			}
			if (playnum <= 0) {
				playnum = 0;
			}
			$('.playnum').html(playnum);
		}, "json").error(function(){
			window.location.href = '/user/login.html';
		})
	};
		
		var rotateFunc = function(awards, angle, text) {
			isture = true;
			$btn.stopRotate();
			$btn.rotate({
				angle: 0,
				duration: 4000, // 旋转时间
				animateTo: angle + 1440, //让它根据得出来的结果加上1440度旋转
				callback: function() {
					isture = false; // 标志为 执行完毕
					//显示弹出窗
					$(".WinningTit").html(text)
			//		$(".asdbtn").css("transform","inherit");
					if(awards==4||awards==8){
						$(".Winning").attr("src","/static/front2018/images/exchange/noWinning.png");
					}else{
						$(".Winning").attr("src","/static/front2018/images/exchange/Winning.png");
					}
				//	lottery.drawLotteryUser();
					/* 弹出窗 */
					$(".comModule").show();
					$(".lucky").hide();
					$(".fixedModule").show();
				}
			});
		};
		

	};
var lottery={

		drawLotteryUser:function(){
			var url="/activitity/getLotteryLog.html?random=" + Math.round(Math.random() * 100);
			  var id = document.getElementById("activity_id").value;

			 var param = {
					 id: id,
			    };
			$.post(url,param,function(data){
				
				var html=""
				var html1=""
				  if(data.list.length <= 0){
	                	$(".lotteryNolist").show();
	                	$(".trottingUl").css('visibility',"hidden");
	                }
				/* 走马灯 */
				trottingUl(data.list.length);
				$.each(data.list,function(v,value){
					
					html1+= '<li><a  href="/luckydraw/luckydrawIndex.html?lang=zh_CN#miao">恭喜用户'+value.userId +'获得'+value.awards_name+'，奖金'+value.num+value.coin_name+'</a></li>';
					html+= '<li>';
					html+= '<p>'+value.userId +'</p>';
					html+= '<p>'+value.time+'</p>';
					html+= '<p>'+value.awards_name+'</p>';
					
					html+= ' </li>';
				});
                $("#lottery_log").html(html);
                $("#hezi_top").html(html1);
              
      		  $(".lotteryBox").myScroll({
					speed: 80, //数值越大，速度越慢
					rowHeight: 40 //li的高度
				});
			});

		},
		
		/*getPrizePool:function(){
			var url="/activitity/getPrizePool.html?random=" + Math.round(Math.random() * 100);
			  var id = document.getElementById("activity_id").value;

			 var param = {
			    		id: id,
				    };
			$.post(url,param,function(data){
				console.log(data)
				var html=""
				$.each(data.list,function(v,value){
					html+= '<li>';
					html+= '<div class="coinInfo">';
					html+= ' <span class="s-1">'+value.awards_name +'</span>';
					html+= '   <span class="s-1">'+value.surplus_num+' </span>';
					html+= ' <span class="s-1">'+ value.fee_amount+value.coin_name+'</span>';
					html+= '  </div>';
					html+= ' </li>';
				});
				var coin_num=data.fvirtualwallet.ftotal;
                $("#lottery_pool").html(html);
               $("#coin_num").html(coin_num+data.fvirtualwallet.coin_name);
			});
			 window.setTimeout(function() {
						 lottery.getPrizePool();
				}, 5000); 
		},*/
 
};


/*首页显示隐藏*/
$(".click_recom ul").click(function() {
	var indexs = $(this).index();
	$('.w_frame').hide();
	$('.w_frame').eq(indexs).slideDown(1000).siblings('.w_frame').hide();
	// $(".w1").fadeIn(200);
})

$(".w_frame img").click(function() {
	$(".w_frame").slideUp(1000);
})


$(".luckyClose").click(function() {
	$(".lucky").hide();
	$(".comModule").hide();
	$(".fixedModule").hide();
	$(".ngrealModule").hide();
})
/*关于平台tab切换内容*/
var tabsSwiper = new Swiper('.swiper-containers', {
	onlyExternal: true,
	speed: 500
})
$(".tabs a").on('touchstart mousedown', function(e) {
	
	e.preventDefault()
	$(".tabs .active").removeClass('active')
	$(this).addClass('active')
	tabsSwiper.swipeTo($(this).index())
})

