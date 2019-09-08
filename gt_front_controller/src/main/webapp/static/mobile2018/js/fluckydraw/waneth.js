
			var easingU = 100;
			var easingN = 1;
			var timer; //定义滚动的定时器
			var result = 189999999; //指定中奖结果,可以抽取指定数组中的某一个
			var isBegin = false; //标识能否开始抽奖

			var isclick= true;	// 防止重复提交

			$(".wanEthnum").css('backgroundPositionY', 0); //默认显示摇奖号码
			

			//执行数字滚动
			function run() {
				easingN++;
				$(".wanEthnum").each(function(index) {
					var _num = $(this);
					_num.animate({
						backgroundPositionY: ((easingU + 1) * easingN * (index + 1))
					}, 100);
				});
				timer = window.setTimeout(run, 100);
				isBegin = true;
			}
			//	时间格式化
			function pubdate(pub) {
				var timestamp3 = pub;
				var newDate = new Date();
				newDate.setTime(timestamp3);
				Date.prototype.format = function(format) {
					var date = {
						"M+": this.getMonth() + 1,
						"d+": this.getDate(),
						"h+": this.getHours(),
						"m+": this.getMinutes(),
						"s+": this.getSeconds(),
						"q+": Math.floor((this.getMonth() + 3) / 3),
						"S+": this.getMilliseconds()
					};
					if(/(y+)/i.test(format)) {
						format = format.replace(RegExp.$1, (this.getFullYear() + '')
							.substr(4 - RegExp.$1.length));
					}
					for(var k in date) {
						if(new RegExp("(" + k + ")").test(format)) {
							format = format.replace(RegExp.$1,
								RegExp.$1.length == 1 ? date[k] : ("00" + date[k])
								.substr(("" + date[k]).length));
						}
					}
					return format;
				};
				return newDate.format('yyyy-MM-dd hh:mm:ss');
			}

			//开始抽奖
			function startInit() {
				run();
				setTimeout(function() {
					var num_arr = (result + '').split('');
					$(".wanEthnum").each(function(index) {
						var _num = $(this);
						_num.animate({
							backgroundPositionY: (easingU * 60) - (easingU * num_arr[index])
						}, {
							duration: 500,
							easing: "easeInOutCirc",
							complete: function() {
								if(index == 10) {
									isBegin = false;
								}
							}
						});
					});
					window.clearTimeout(timer);
					isBegin = false;
				}, 2000);
				setTimeout(function() {
					$(".wanEthModule").show();
					$("body").css('overflow','hidden');
				}, 2500)
			}
			
			//	摇奖接口
			function wanEthInit(){
				var url = "/luckDraw/getNewLottery.html";
				var id = document.getElementById("numETH").value;
				var param = {
					num : id,
				};
				jQuery.post(url,param,function(item) {
					if (item.code == 200) {		
						result = parseInt(item.data[0]);		// 奖票码
						var html=""
							$.each(item.data,function(v,value){
								html+= '<li>'+ value+'</li>';
							});
						$(".wanscroll").html(html);
						$(".wanEthModule .wanscroll").mCustomScrollbar();	// 设置滚动条			
						startInit();		// 开奖方法
						getRemainPowNum();	// 初始化方法
						wanEthListInit();	// 初始化列表
						
					} else if(item.code == -3){
						// 没有币
						$(".ngrealModule").show();
						$(".powModules").html('前往<label class="lotterKYC">POW</label>充值')
						$(".lotterKYCBtn").attr('href',"/financial/index.html");
						$(".powModulesBtn").html('前往充值');
					}else if (item.code == 101) {							
						// kyc1认证
						$(".ngrealModule").show();
						$(".powModules").html('前往<label class="lotterKYC">KYC1</label>认证获得1POW奖励');
						$(".powModulesBtn").html('点击KYC1认证');
						$(".lotterKYCBtn").attr('href',"/user/realCertification.html");
					} else if (item.code == 102) {
						// kyc2认证
						$(".ngrealModule").show();
						$(".powModules").html('前往<label class="lotterKYC">KYC2</label>认证获得1POW奖励');
						$(".powModulesBtn").html('点击KYC2认证');
						$(".lotterKYCBtn").attr('href',"/user/realCertification.html");					
					} else {
						util.showMsg(item.msg);
					}
					setTimeout(function(){ 
						isclick= true;
					}, 3000);
					 
				})
			}
			//	奖票列表
			function wanEthListInit(){
				var url = "/luckDraw/getLotteryList.html";
				var id = document.getElementById("nper_id").value;
				var param = {
					nper: id,
				};
				jQuery.post(url,param,function(item) {
//					var status = item.nper.status;
					 if(item.lotteryList.length <= 0){
						$("#LotteryList").html('<div style="font-size: 32px;text-align: center;line-height: 190px;color: #fff;">暂无数据</div>')

					}else {
						var html=""
							$.each(item.lotteryList,function(v,value){
								html+= '<li>';
								html+= '<p>'+value.uid+'</p>'; 
								html+= '<p>'+value.lottery_no+'</p>'; 
								html+= '<p>'+pubdate(value.createtime.time)+'</p>'; 
								html+'</li>';
							});
						 $("#LotteryList").html(html);
						// 奖票信息list奇偶数 
							$(".wanEthUL3Prize .wanEthUL3Td li:even").css('background','rgba(77, 27, 37, 0.2)');
							// 列表无缝滚动
							$('.wanEthUL3Box').myScroll({
								speed: 40, //数值越大，速度越慢
								rowHeight: 70 //li的高度
							});
					}
					
				})
			}
			//	初始化摇奖数据
			function getRemainPowNum(){
				var url = "/luckDraw/getRemainPowNum.html";
				jQuery.post(url,function(item) {
					$("#nper_id").val(item.nper);
					$(".wanEthTithour").html(item.remainNum);
					$(".totalPowNum").html(item.totalPowNum);
				})
				setTimeout(function(){ 
					getRemainPowNum();	//	摇奖列表
				}, 10000);
			}
			
			$(function() {
				getRemainPowNum();		//	初始化摇奖数据
				setTimeout(function(){ 
					wanEthListInit();	//	摇奖列表
				}, 500);
				//	  关闭弹窗
				  $(".realclose").on("click",function(){
					  $(".ngrealModule").hide();
				  });
			//	countDown(182040);			// 日期倒计时
		//		$(".wanEthModule .wanscroll").mCustomScrollbar();	// 设置滚动条
				// 按下
				  
				  var div = document.querySelector('.wanETHbtn')
			     	// 触摸开始
			     	div.addEventListener('touchstart', function(){
			     		$(".wanETHbtn").attr('src','/static/mobile2018/images/exchange/button1@3x.png');
						$(this).css('padding-top', '10px');
			     	},false)
			     	//触摸结束
			     	div.addEventListener('touchend', function(){
			     		$(".wanETHbtn").attr('src','/static/mobile2018/images/exchange/button@3x.png')
						$(this).css('padding-top', '0');
			     		//定时器
						if(isclick){
					        isclick= false;
					         setTimeout(function(){ 
								wanEthInit();
					        }, 500);
					    }else{
						util.showMsg('操作频繁！3秒后再试！！！');
						}   
			     	},false)
				
					    
					
			
				// 关闭弹出窗
				$(".wanEthModuleClose").click(function(){
					$(".wanEthModule").hide();
					$("body").css('overflow','auto');
					$(".wanEthModule .wanscroll").mCustomScrollbar("destroy"); 
				})

		

			})
 