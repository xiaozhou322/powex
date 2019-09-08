


function getOrder(){
	$("#buttonOper").html('<span class="btn" style="margin-right: 20px;background:#fff;color: #14c034;border-radius: 0px;width: 200px;height: 40px;font-size: 20px;"></span>');
	var orderId=$("#orderId").val();
	var url="/order/queryOtcOrder.html";
$.ajax({
   type: 'post',
   url: url,
    data:{
    	'orderId':orderId
     },
    dataType: "json",
    success: function(result) {
           if(result!=null&&result.code==0){
        	  
        		// 订单状态（101：未接单  102：待支付  103：已付款 104已确认收款 ;105:异常订单   106：失败 107：成功）',
        		// '是否是超时订单(1001:否，1002：是)',
        		   var sitrue=$("#ismerchant").val();
        		   console.log($("#ismerchant").val());
        		   if(result.orderStatus==101){
        			   if(sitrue=="false"){
        				   $("#buttonOper").html('<span class="btn" style="margin-right: 20px;background:#fff;color: #14c034;border-radius: 0px;width: 200px;height: 40px;font-size: 20px;text-align: left;">等待买家接单</span>'); 
        			   }
        		   }else if(result.orderStatus==102){
          			  if(result.overtime!=null&&result.overtime==1002){
          				  $("#buttonOper").html('<span class="btn" style="margin-right: 20px;background:#fff;color: #14c034;border-radius: 0px;width: 200px;height: 40px;font-size: 20px;text-align: left;">等待买家付款</span>'); 
            			
	     			   }else if(result.overtime!=null&&result.overtime==1001){
	     				  $("#buttonOper").html('<span class="btn" style="margin-right: 20px;background:#fff;color: #cc7a08;border-radius: 0px;width: 200px;height: 40px;font-size: 20px;text-align: left;">等待买家申诉</span>'); 
	       
	     			   }else{
	     				   //提示超时状态异常
	     			   }
        		   }else if(result.orderStatus==103){
                       if(result.overtime!=null&&result.overtime==1002){
                    	   $("#buttonOper").html('<span onclick="confirmOrder()" class="btn"  style="margin-right: 20px;">确认收款</span>');   
               			   
        			   }else if(result.overtime!=null&&result.overtime==1001){
        				   $("#buttonOper").html('<span onclick="appealOrder()" class="btn"  style="margin-right: 20px;">我要申诉 </span>'); 
        			   }else{
        				   //提示超时状态异常
        			   }
        		   }else if(result.orderStatus==104){
        			   $("#buttonOper").html('<span onclick="confirmOrderSecond()" class="btn"  style="margin-left: 20px;">二次确认</span><span  onclick="appealOrder()" class="btn fl"  >我要申诉</span>');
        		   }else if(result.orderStatus==105){
         			   $("#buttonOper").html('<span class="btn" style="margin-right: 20px;background:#fff;color:red;border-radius: 0px;width: 200px;height: 40px;font-size: 20px;">订单异常申诉中</span>'); 
        		   }else if(result.orderStatus==106){
         			   $("#buttonOper").html('<span class="btn" style="margin-right: 20px;background:#fff;color: red;border-radius: 0px;width: 200px;height: 40px;font-size: 20px;">交易失败</span>'); 
        		   }else if(result.orderStatus==107){
         			   $("#buttonOper").html('<span class="btn" style="margin-right: 20px;background:#fff;color: #14c034;border-radius: 0px;width: 200px;height: 40px;font-size: 20px;">交易成功</span>'); 

        		   }else{
        			   
        			   
        			   //提示订单状态异常
        		   }
        		  // window
        		   
        	  
           }else{
        	   
        	   //弹出系统异常
           }
	},

	});

//	setTimeout("getOrder()", "5000");



}