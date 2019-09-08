
function getOrder(){
	 $("#buttonOper").html("");
	var orderId=$("#orderId").val();
	var url="/order/queryOtcOrder.html";
	$.ajax({
	   type: 'post',
	   url: url,
	   data:{'orderId':orderId},
	   dataType: "json",
	   success: function(result) {
    	
           if(result!=null&&result.code==0){
        		// 订单状态（101：未接单  102：待支付  103：已付款 104已确认收款 ;105:异常订单   106：失败 107：成功）',
        		// '是否是超时订单(1001:否，1002：是)',
        			var sitrue=$("#ismerchant").val();
        			console.log(sitrue);
        		   if(result.orderStatus==101){
        			   
        			   if(sitrue=="false"){
        				   $("#buttonOper").html('<span class="btn fl" style="margin-left:-30px;background:#fff;color: #14c034;border-radius: 0px;font-size: 0.3rem;">等待卖家接单</span>'); 
        			   }
        		   }else if(result.orderStatus==102){
                      if(result.overtime!=null&&result.overtime==1002){
                    	  $("#buttonOper").html('<span onclick="confirmOrder()" class="btn fl" >我已付款 </span><span  onclick="cancelOrder()" class="btn btn_2 fr"  >取消订单</span>');
                      }else if(result.overtime!=null&&result.overtime==1001){
                    	  $("#buttonOper").html('<span  onclick="appealOrder()" class="btn fl" >我要申诉 </span><span  onclick="cancelOrder()" class="btn btn_2 fr"  >取消订单</span>');
        			   }else{
        				   //提示超时状态异常
        			   }
        		   }else if(result.orderStatus==103){   
                       if(result.overtime!=null&&result.overtime==1002){
               			   $("#buttonOper").html('<span class="btn fl" style="margin-left:-30px;background:#fff;color: #14c034;border-radius: 0px;font-size: 0.3rem;">等待卖家确认收款</span>');    
               			   
        			   }else if(result.overtime!=null&&result.overtime==1001){
        				   $("#buttonOper").html('<span  onclick="appealOrder()" class="btn fl"  >我要申诉 </span>'); 
        			   }else{
        				   //提示超时状态异常
        			   }
        		   }else if(result.orderStatus==104){
         			  $("#buttonOper").html('<span class="btn fl" style="margin-left:-30px;background:#fff;color: #14c034;border-radius: 0px;font-size: 0.3rem;">等待卖家二次确认</span>'); 

                  	 
        		   }else if(result.orderStatus==105){
         			  $("#buttonOper").html('<span class="btn fl" style="margin-left:-30px;background:#fff;color: red;border-radius: 0px;font-size: 0.3rem;">订单异常申诉中</span>'); 

        		   }else if(result.orderStatus==106){
         			  $("#buttonOper").html('<span class="btn fl" style="margin-left:-30px;background:#fff;color: red;border-radius: 0px;font-size: 0.3rem;">交易失败</span>'); 

        		   }else if(result.orderStatus==107){
         			  $("#buttonOper").html('<span class="btn fl" style="margin-left:-30px;background:#fff;color: #14c034;border-radius: 0px;font-size: 0.3rem;">交易成功</span>'); 

        		   }else{
        			   
        			   
        			   //提示订单状态异常
        		   }
        		 //  window
        		   
        	   
           }else{
        	   
        	   //弹出系统异常
           }
	},

	});

//	setTimeout("getOrder()", "5000");



}