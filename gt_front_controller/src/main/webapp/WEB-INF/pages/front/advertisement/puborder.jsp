<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>

<!doctype html>
<html>
<head> 
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp" %>

<link href="${oss_url}/static/front2018/css/usdtTrade.css?v=12" rel="stylesheet" type="text/css" />
<style type="text/css">
.text{
border: 1px solid #ccc;
height: 100px;
width: 670px;
margin-bottom: 0;
padding: 5px;
color: #999;
}

</style>
</head>
<body class="">
<%@include file="../comm/newotc_header.jsp" %>
<section class="uTrade mg infoMain clear">
        <div class="uTrade_l">
            <div>
                <%@include file="_leftmenuUser.jsp"%>
            </div>
        </div>
        <form id="agentinfoForm">
            <div class="uTrade_r">
                <div class="baseForm">
                    <div class="mg">
                    	<div style="color:red;padding-top:50px;padding-left:100px;"><spring:message code="agent.pub.tips" /></div>
                        <!-- <h1 class="title">发布广告</h1> -->
                        <div class="baseInfo baseInfo_2">
                        	<p class="commH"><spring:message code="agent.cointype" /></p>
                            <label for=""><select class="commH txt ftype" style="height:46px;" onchange="showOption()" autocomplete="off"  id="amount_type" name="amount_type">
						<option value=""   selected=true><spring:message code="agent.cointype" /></option>
						<c:forEach items="${amountTypeMap}" var="t">										
							<option value="${t.fid}">${t.fShortName}</option>
						</c:forEach>
					</select></label>
                        
                            <!-- <div class="commH jtype"> -->
                            <p class="commH"><spring:message code="agent.tradetype" /></p >
                            <label class="" for="">
                                <select class="commH txt ftype" style="height:46px;" id="ftype" name="ad_type" autocomplete="off">
                                    <option value=""><spring:message code="agent.choose" /></option>
                                    <option value="2"><spring:message code="agent.purchase" /></option>
                                    <option value="1"><spring:message code="market.sell" /></option>
                                </select>
                            </label>
                         <!--    </div> -->
                             <div id="showPrice">
                            <p class="commH"><spring:message code="market.price.cny" /> <span class="colorBlue"></span></p>
                            <label for=""><input class="commH txt" id="rate" name="price" type="text"  autocomplete="off"/></label>
                            </div>
                            <p class="commH"><spring:message code="agent.tradenum" /></p>
                            <label for="">
                            	<input class="commH txt" id="validnum" name="total_count" type="text" autocomplete="off"/>
                            </label>
                            <p class="commH"><spring:message code="agent.tradequotas" /> <span class="colorBlue">CNY</span></p>

                            <label for="" class="spectial">
                                <input class="commH txt" id="minamount" name="order_limit_min" autocomplete="off" type="text" placeholder='<spring:message code="agent.minPrice" /> ' />
                                &nbsp;&nbsp;to&nbsp;&nbsp;
                                <input class="commH txt"  id="maxamount" name="order_limit_max" autocomplete="off" type="text" placeholder='<spring:message code="agent.maxPrice" /> ' />
                            </label>
<!--                              <p class="commH">广告描述<span class="colorBlue"></span></p> -->
<!--                               <label for="" class="spectial"> -->
<!--                                 <textarea class="commH text" name="ad_desc" rows="20" cols="105"></textarea> -->
<!--                             </label> -->
                          
<!--                              <p class="commH">广告备注<span class="colorBlue"></span></p> -->
<!--                               <label for="" class="spectial"> -->
<!--                                 <textarea class="commH text" name="remark" rows="20" cols="105"></textarea> -->
<!--                             </label> -->
							<p class="commH">广告订单默认发送消息</p>
                            <label for="" class="spectial">
                            	<textarea class="text" id="remark" name="remark" rows="20" cols="105"></textarea>
                            </label>
                            <div class="tj_box">
                                <button class="tj" onclick="saveOrderInfo()" type="button"><spring:message code="security.submit" /></button> 
                           		<em></em>
                            </div>
                            
                        </div> 
                        
                    </div>                  
                </div>
                
            </div>
        </form>
    </section>

 

<%@include file="../comm/footer.jsp" %>	
<script type="text/javascript" src="${oss_url}/static/front2018/js/index/main.js"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/ajaxfileupload.js"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/fileCheck.js"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/comm/msg.js"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/jquery.qrcode.min.js"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/jquery-migrate-1.4.1.min.js"></script>
<script>
var isClick = false;
	function saveOrderInfo()
	{
	
		
		var ftype = $('#ftype').val();
		if(ftype=='')
		{
			 util.layerAlert("", language["agent.type"], 0);
             return;
		}
		var shortName=$("#amount_type option:selected").text();
		if(!shortName=="WTO"){
			var rate = $('#rate').val();
			if(rate=='')
			{
				 util.layerAlert("", language["agent.tpirce"], 0);
	             return;
			}	
		}
		var amount_type=$("#amount_type").val();
		if(amount_type==null||amount_type==""||amount_type.length==0){
			 util.layerAlert("", language["agent.cointype"], 0);
             return;
		}
		
		var validnum = $('#validnum').val();
		if(validnum=='')
		{
			 util.layerAlert("", language["agent.tamount"], 0);
             return;
		}
		
		var minamount = $('#minamount').val();
		var maxamount = $('#maxamount').val();
		if(parseInt(maxamount)<parseInt(minamount)){
			 util.layerAlert("", "最小交易额不得大于最大交易额", 0);
			 $('#minamount').val("");
			 $('#maxamount').val("");
             return;
		}
		if(minamount==''||maxamount=='')
		{
			 util.layerAlert("", language["agent.topirce"], 0);
             return;
		}
		var remark = $('#remark').val();
		if(remark.length>80)
		{
			 util.layerAlert("", "消息内容最多不能大于80字符", 0);
             return;
		}
		var param = $('#agentinfoForm').serialize();
		 <c:if test="${sessionScope.online==false}">		
	  	    util.layerConfirm("您当前状态为处于离线状态，该广告不会展示给给其他用户", function () {
	  			if(isClick) {
	  				return ;
	  			}
	  			isClick= true;

	  			jQuery.post('/advertisement/saveAdvertisement.html', param, function (data) {
	  	        	if (data.success) {
	  	        		util.layerAlert("", data.massage, 1);
	  	        		
	  	        		
	  				} else {
	  					util.layerAlert("", data.massage, 2);
	  					return;
	  				}
	  	        }, "json");
	  	    });
	  		
	  </c:if>
	  <c:if test="${sessionScope.online==true}">	 
		jQuery.post('/advertisement/saveAdvertisement.html', param, function (data) {
	        	if (data.success) {
	        		util.layerAlert("", data.massage, 1);
				} else {
					util.layerAlert("", data.massage, 2);
					return;
				}
	        }, "json");
	   
	  </c:if>
	
	}
	function showOption(){
		var shortName=$("#amount_type option:selected").text();
		if(shortName=="WTO"){
			$("#showPrice").hide();
		}else{
			$("#showPrice").show();
		}
	}
</script>    

</body>
</html>
