<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>
<%

%>
<!doctype html>
<html>
<head> 
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp" %>

<link href="${oss_url}/static/front2018/css/usdtTrade.css?v=12" rel="stylesheet" type="text/css" />
</head>
<body class="">
<%@include file="../comm/white_header.jsp" %>
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
                        <!-- <h1 class="title">发布广告</h1> -->
                        <div class="baseInfo baseInfo_2">
                        	<p class="commH"><spring:message code="agent.usernum" />USDT</span></p>
                            <label for=""><input class="commH txt" readonly type="text" value="${fagent.usdtwallet}" /></label>
                        
                            <!-- <div class="commH jtype"> -->
                            <p class="commH"><spring:message code="agent.tradetype" /></p >
                            <label class="" for="">
                                <select class="commH txt ftype" id="ftype" name="ftype">
                                    <option value=""><spring:message code="agent.choose" /></option>
                                    <option value="1"><spring:message code="agent.purchase" /></option>
                                    <option value="2"><spring:message code="market.sell" /></option>
                                </select>
                            </label>
                         <!--    </div> -->

                            <p class="commH"><spring:message code="market.price" /> <span class="colorBlue">CNY/USDT</span></p>
                            <label for=""><input class="commH txt" id="rate" name="rate" type="text" value="6.5" /></label>
                            <p class="commH"><spring:message code="agent.tradenum" />(USDT)</p>
                            <label for="">
                            	<input class="commH txt" id="validnum" name="validnum" type="text" />
                            </label>
                            <p class="commH"><spring:message code="agent.tradequotas" /> <span class="colorBlue">CNY</span></p>

                            <label for="" class="spectial">
                                <input class="commH txt" id="minamount" name="minamount" type="text" placeholder='<spring:message code="agent.minPrice" /> ' />
                                &nbsp;&nbsp;to&nbsp;&nbsp;
                                <input class="commH txt"  id="maxamount" name="maxamount" type="text" placeholder='<spring:message code="agent.maxPrice" /> ' />
                            </label>
                            <div class="tj_box">
                                <button class="tj" onclick="saveOrderInfo()" type="button"><spring:message code="security.submit" /></button> 
                            </div>
                        </div> 
                        
                    </div>                  
                </div>
                
            </div>
        </form>
    </section>

 

<%@include file="../comm/footer.jsp" %>	
<script type="text/javascript" src="${oss_url}/static/front2018/js/index/main.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/ajaxfileupload.js?v=20181126201750"></script>
   <script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/fileCheck.js?v=20181126201750"></script>
   <script type="text/javascript" src="${oss_url}/static/front2018/js/comm/msg.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/jquery.qrcode.min.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/jquery-migrate-1.4.1.min.js?v=20181126201750"></script>
<script>
  
	function saveOrderInfo()
	{
	
		var ftype = $('#ftype').val();
		if(ftype=='')
		{
			 util.layerAlert("", language["agent.type"], 0);
             return;
		}
		
		var rate = $('#rate').val();
		if(rate=='')
		{
			 util.layerAlert("", language["agent.tpirce"], 0);
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
		if(minamount==''||maxamount=='')
		{
			 util.layerAlert("", language["agent.topirce"], 0);
             return;
		}
		
		var param = $('#agentinfoForm').serialize();
		jQuery.post('/agent/savepuborder.html', param, function (data) {
        	if (data.code == -1) {
				util.layerAlert("", data.msg, 2);
				
			} else {
				util.layerAlert("", data.msg, 1);
			}
        }, "json");
	
	}
    
</script>    

</body>
</html>
