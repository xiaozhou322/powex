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
 <meta name = "viewport" content = "width = device-width, user-scalable = no,initial-scale=0.5,maximum-scale=0.5,minimum-scale=0.5">
<%@include file="../comm/link.inc.jsp" %>

<link href="${oss_url}/static/mobile2018/css/usdtTrade.css?v=1" rel="stylesheet" type="text/css" />

</head>
<body class="Ubody">
<%@include file="../comm/header.jsp" %>
<header class="tradeTop">  
    <i class="back toback2"></i>
    <h2 class="tit">交易设置</h2>
</header>
<section class="uTrade infoMain mg clear usdtTrade">
        <form id="agentinfoForm">
        <div class="uTrade_r fr">
            <div class="baseForm">
                <h1 class="title">发布广告</h1>
                <div class="baseInfo">
                	<p class="commH">交易账户可用USDT</span></p>
                    <label for=""><input class="commH txt" readonly type="text" value="${fagent.usdtwallet}" /></label>
                
                    <p class="commH">交易类型</p>
                    <label for="">
                    	<select id="ftype" name="ftype">
                    		<option value="">请选择</option>
                    		<option value="1">购买</option>
                    		<option value="2">出售</option>
                    	</select>
                    </label>
                    <p class="commH">价格 <span class="colorBlue">CNY/USDT</span></p>
                    <label for=""><input class="commH txt" id="rate" name="rate" type="text" value="6.5" /></label>
                    <p class="commH">交易数量(USDT)</p>
                    <label for="">
                    	<input class="commH txt" id="validnum" name="validnum" type="text" />
                    </label>
                    <p class="commH">交易限额 <span class="colorBlue">CNY</span></p>
                    <label for="" class="spectial">
                        <input class="commH txt" id="minamount" name="minamount" type="text" placeholder="5000"  />
                        &nbsp;&nbsp;至&nbsp;&nbsp;
                        <input class="commH txt"  id="maxamount" name="maxamount" type="text" placeholder="10000" />
                    </label>
                </div>
                                  
                   
                    <div class="clear"></div>
                </div>
                <button class="tj" onclick="saveOrderInfo()" type="button">提交</button>
            </div>
            </form>
        </div>
    </section>

 
<!-- <div class="coinWarp">
    <div class="coinLitBox coinLitBox2">
        
          <ul>
             <c:forEach items="${filters }" var="v"> 
                      <c:if test="${select==v.value}">
                      <input type="hidden" id="recordType" value="${v.key }">
                      </c:if>
                 <li> <a href="${v.key }">${v.value }</a></li>
               </c:forEach>
          </ul>

        
    </div>
</div> -->
<%@include file="../comm/footer.jsp" %>	
<script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
<!-- <script type="text/javascript" src="${oss_url}/static/front/js/index/main.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/plugin/ajaxfileupload.js?v=20181126201750"></script>
   <script type="text/javascript" src="${oss_url}/static/front/js/plugin/fileCheck.js?v=20181126201750"></script>
   <script type="text/javascript" src="${oss_url}/static/front/js/comm/msg.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.qrcode.min.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery-migrate-1.4.1.min.js?v=20181126201750"></script> -->
<script>
  
	function saveOrderInfo()
	{
	
		var ftype = $('#ftype').val();
		if(ftype=='')
		{
			 util.layerAlert("", "请选择交易类型", 0);
		}
		
		var rate = $('#rate').val();
		if(rate=='')
		{
			 util.layerAlert("", "请输入交易价格", 0);
		}
		
		var validnum = $('#validnum').val();
		if(validnum=='')
		{
			 util.layerAlert("", "请输入交易数量", 0);
		}
		
		var minamount = $('#minamount').val();
		var maxamount = $('#maxamount').val();
		if(minamount==''||maxamount=='')
		{
			 util.layerAlert("", "请设置交易限额", 0);
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
