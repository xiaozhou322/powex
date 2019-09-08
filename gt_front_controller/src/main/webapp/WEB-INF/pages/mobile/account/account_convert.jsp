<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>
<!doctype html>
<html>
<head> 
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name = "viewport" content = "width = device-width, user-scalable = no,initial-scale=0.5,maximum-scale=0.5,minimum-scale=0.5">
<%@include file="../comm/link.inc.jsp" %>
<style type="text/css">
.convertCen{
   margin: 0.3rem;
}
.convertNav{
	clear: both;
    overflow: hidden;
    background: url(/static/mobile2018/images/ABOTbg.png) no-repeat center;
    background-size: 100% 100%;
    text-align: center;
    height: 2.83rem;
}
.convertNav .convertNavUl{
    text-align: left;
    color: #fff;
    padding: 0.28rem 0 0 0.28rem;
    line-height: 0.5rem;
}
.convertNav .convertNavUl li{font-size: 0.24rem;}
.convertNav .convertNavUl li span{
	font-size: 14px;
    font-family: MicrosoftYaHei;
    font-weight: 400;
}
.convertNav .convertNavUl li>b{
    font-size: 0.32rem;
    font-family: MicrosoftYaHei;
    font-weight: 400;
}
.convertNav .convertNavUl li a{
    font-size: 0.26rem;
    font-family: MicrosoftYaHei;
    font-weight: 400;
    color: #668CFB;
    padding-left: 0.14rem;
}
/* 标题统计 */
.statsTit{
    box-shadow: 0 0.02rem 0.09rem 0 rgba(70, 90, 111, 0.14);
    margin: 0.22rem 0;
    height: 1.54rem;
    background: #fff;
}
.statsTit ul{
    line-height: 0.45rem;
    display: flex;
}
.statsTit ul li{
    display: inline-block;
    width: 33%;
    text-align: center;
}
.statsTit ul li p:nth-child(1){
    font-size: 0.2rem;
    font-family: MicrosoftYaHei;
    font-weight: 400;
    color: #3E3E3E;
}
.statsTit ul li p:nth-child(2){
	font-size: 0.32rem;
    font-family: MicrosoftYaHei;
    font-weight: 400;
    color: #3E3E3E;
}
.statsTit ul li a{
	font-size: 0.2rem;
    font-family: MicrosoftYaHei;
    text-decoration: underline;
    color: #4F6EC8;
    cursor: pointer;
}
/* TAB切换、ABO对换 */
.aboTab{
    box-shadow: 0 0.02rem 0.9rem 0 rgba(70, 90, 111, 0.14);
    padding-bottom: 0.5rem;
    clear: both;
    overflow: hidden;
    padding-top: 0.16rem;
    background: #fff;
}
.aboTab .aboTabLeft{margin: 0 0.2rem;}
.aboTab .aboTabLeft .aboTabBtn{
    border-bottom: 1px solid #D9D9D9;
    clear: both;
    overflow: hidden;
    font-size: 0.24rem;
    font-family: MicrosoftYaHei;
    line-height: 0.4rem;
}
.aboTab .aboTabLeft .aboTabBtn li{
    float: left;
    width: 1.5rem;
    text-align: center;
    cursor: pointer;
    line-height: 0.53rem;
    height: 0.6rem;
}
.aboTab .aboTabLeft .aboTabBtn .active{
	border-bottom: 2px solid #2269d4;
    color: #2269d4;
}
.convertABO{margin-top: 35px;}
.convertABO li{
    clear: both;
    overflow: hidden;
    zoom: 1;
    margin: 0 auto;
    margin-bottom: 40px;
    text-align: center;
}
.convertABO li>h3{
        height: 0.5rem;
    background: rgba(80,111,200,1);
    box-shadow: 0px 3px 21px 0px rgba(178,198,255,1);
    border-radius: 2px;
    color: #fff;
    margin-left: 30%;
    text-align: center;
    line-height: 0.5rem;
    font-size: 0.18rem;
    font-family: MicrosoftYaHei;
    width: 60%;
}
.convertABO li>p{    
	float: left;
    width: 60%;}
.convertABO li>p:nth-child(1){
    text-align: right;
    font-size: 0.24rem;
    font-family: MicrosoftYaHei;
    color: #3E3E3E;
    font-weight: 400;
    line-height: 0.4rem;
    padding-right: 0.1rem;
    width: 30%;
}
.convertABO li>p input{
    width: 100%;
    height: 0.5rem;
    border: 1px solid rgba(79,110,200,1);
    border-radius: 2px;
    text-indent: 0.1rem;
}
.aboTab .aboTabRight{margin: 0 0.3rem;}
.aboTabRight .TabRightH3{
    line-height: 0.54rem;
    font-size: 0.26rem;
    font-family: MicrosoftYaHei;
    color: #3E3E3E;
}
.TabRightUl{    
	background: #F6F6F6;
    line-height: 0.33rem;
    padding: 0.3rem 0.1rem;
    font-size: 0.2rem;
    color: #3E3E3E;
    word-break: break-all;
    }
    .TabRightUl li,.regulation-ul li{
        line-height: 0.46rem;
    }
/* 表格 */
.convertTab{
    padding: 0 0.2rem;
    box-shadow: 0px 2px 9px 0px rgba(70, 90, 111, 0.14);
    clear: both;
    overflow: hidden;
    margin: 0.22rem 0;
    padding-bottom: 0.2rem;
    background: #fff;
}
.convertTabNav{
    border-bottom: 1px solid #D9D9D9;
    clear: both;
    overflow: hidden;
    font-size: 0.24rem;
    font-family: MicrosoftYaHei;
    line-height: 0.7rem;
}    
.convertTabNav li{
    float: left;
    width: 1.5rem;
    text-align: center;
    cursor: pointer;
}    
.convertTabNav .active{
    border-bottom: 2px solid #2269d4;
    color: #2269d4;
}    
    /* 表头 */
.convertTH{
	clear: both;
    overflow: hidden;
    background: #EEF4FF;
    margin-top: 20px;
    color: #8b8b8b;
    font-size: 0.2rem;
}
.convertTH li{
    width: 20%;
    line-height: 0.5rem;
    height: 0.5rem;
    text-align: center;
    float: left;
    font-family: MicrosoftYaHei;
}
#convertCoinLogs .convertTH li{ width: 20%;}
#activeCoinLogs .convertTH li,#activeCoinLogs li span{ width: 25%;}
.pagination{margin-top: 0.2rem;    text-align: center;}
.page li {
    display: inline-block;
    border: 1px solid #ddd;
    border-radius: 3px;
    color: #6f6f6f;
    transition: all 0.3s;
    margin: 0 2px;
}
 .page li.active a {
    background: #2269d4;
    color: #fff;
    border-color: #2269d4;
}
.page a {
    color: #6f6f6f;
    display: block;
    padding: 3px 10px;
}

.convertTD li{
    clear: both;
    overflow: hidden;
    zoom: 1;
}
.convertTD li:nth-child(odd) {
    background-color: #F9FBFF;
}

.convertTD li span{
    width: 20%;
    color: #979797;
    line-height: 0.8rem;
    height: 0.8rem;
    text-align: center;
    float: left;
    font-size: 0.18rem;
    font-family: MicrosoftYaHei;
}
.regulation{
	padding: 0.3rem;
    box-shadow: 0px 2px 9px 0px rgba(70, 90, 111, 0.14);
    background: #fff;
}
.regulation .regulation-h3{
	line-height: 0.54rem;
    font-size: 0.26rem;
    font-family: MicrosoftYaHei;
    color: #3E3E3E;
}
.regulation .regulation-h3 img{
	vertical-align: -5px;
    margin-right: 10px;
    font-size: 16px;
}
.regulation .regulation-ul{
    margin-bottom: 0.4rem;
    padding: 0.3rem 0.1rem;
    background: #F6F6F6;
    line-height: 0.36rem;
    color: #3E3E3E;
    font-size: 0.2rem;
}
</style>

</head>
<body>
<header class="tradeTop">
    <span class="back toback2"></span>
    <span>兑换</span>
</header>
<div class="convertCen">
	<!-- 头部介绍 -->
	<div class="convertNav">
		<ul class="convertNavUl">
			<li>
				<b>ABOT产品</b>
				<a href="/service/ourService.html?id=1">更多详情>></a>
			</li>
			<li>
				上线时间：2019年03月01日00：00
			</li>
			<li>创新区开放USDT交易对，手续费按标准千分之二收取，</li>
			<li>在T+1天16:00自动进行100%双返</li>
		</ul>
	</div>
	<!-- 标题统计 -->
	<div class="statsTit">
		<ul>
			<li>
				<p>已激活${pproduct.coinType.fShortName}</p>
				<p>${hasActiveAmount }</p>
				<a href="/trademarket.html">立即交易</a>
			</li>
			<li>
				<p>未激活${pproduct.coinType.fShortName}</p>
				<p>${fvirtualwallet.flocked}</p>
				<a href="" class="scrollBtn">如何激活?</a>
			</li>
			<li>
				<p>待激活${pproduct.coinType.fShortName}</p>
				<p>${waitActiveAmount}</p>
				<a href="" class="scrollBtn">激活手续费：${activePOWFee}</a>
			</li>
		</ul>
	</div>
	<!-- 对换ABO -->
	<div class="aboTab">
		<input type="hidden" id="convertRatio" value="${pproduct.convertRatio }">
		<input type="hidden" id="convertRatioExpire" value="${pproduct.convertRatioExpire }">
		<input type="hidden" id="cointypeFrom" value="">
		<input type="hidden" id="cointypeTo" value="">
		<input type="hidden" id="minTimeAmount" value="${pproduct.minTimeAmount }">
		<input type="hidden" id="productId" value="${pproduct.id }">
		<input type="hidden" id="convertType" value="">
		<input type="hidden" id="tradePwd" value="">
		<div class="aboTabLeft">
			<ul class="aboTabBtn">
				<li class="active" name="ABOT">兑换${pproduct.coinType.fShortName}</li>
				<li name="ABO">兑换${pproduct.convertCointype.fShortName}</li>
			</ul>
			<div class="convertLi">
				<ul class="convertABO" id="ABOT">
					<li>
						<p>${pproduct.convertCointype.fShortName}数量：</p>
						<p>
							<input type="text" id="aboAmount" class="aboValue" placeholder="请输入${pproduct.convertCointype.fShortName}数">
						</p>						
					</li>
					<li>
						<p>${pproduct.coinType.fShortName}数量：</p>
						<p>
							<input type="text" id="abo_abotAmount" placeholder="0">
						</p>						
					</li>
					<label for="diyMoney" style="margin:0 0 0 0;">
        				<span id="convertABOTerrortips" class="text-danger"  style="margin:0 0 0 170px; display:block;">
      				</span>
					<li>
						<button class="btn tb_btn"  id="convertABOTButton"><spring:message code="兑换${pproduct.coinType.fShortName}" /></button>
						<!-- <h3>兑换ABOT</h3> -->
					</li>
				</ul>
				<ul class="convertABO" id="ABO" style="display:none;">
					<li>
						<p>${pproduct.coinType.fShortName}数量：</p>
						<p>
							<input type="text" id="abotAmount" placeholder="请输入${pproduct.coinType.fShortName}数">
						</p>						
					</li>
					<li>
						<p>${pproduct.convertCointype.fShortName}数量：</p>
						<p>
							<input type="text" id="abot_aboAmount" class="aboValue" placeholder="0">
						</p>						
					</li>
					<label for="diyMoney" style="margin:0 0 0 0;">
        				<span id="convertABOerrortips" class="text-danger"  style="margin:0 0 0 170px; display:block;">
      				</span>
					<li>
						<button class="btn tb_btn"  id="convertABOButton"><spring:message code="兑换${pproduct.convertCointype.fShortName}" /></button>
						<!-- <h3>兑换ABO</h3> -->
					</li>
				</ul>
			</div>
		</div>
		<div class="aboTabRight">
			<h3 class="TabRightH3">
				<img src="${oss_url}/static/front2018/images/notice.png">
				兑换须知：
			</h3>
			<ul class="TabRightUl">
				<li>1、ABOT不允许用户充提，只能使用ABO按照一定比例兑换的方式获得，兑换获得的ABOT处于非激活的锁仓状态；</li>                   
				<li>2、用户如果想将ABOT激活，只能先到ABOT的交易市场进行交易，通过交易挖矿的形式进行激活。</li>
				<li>3、根据用户ABOT买入量按解锁比例进行解锁；</li>
				<li>4、单次解锁手续费：解锁总量的10%等价值的平台币（POW）；</li>
			</ul>
		</div>
	</div>
	<!-- 对换记录、激活记录list -->
	<div class="convertTab">
		<ul class="convertTabNav">
			<li class="active" name="convertCoinLogs">兑换记录</li>
			<li name="activeCoinLogs">激活记录</li>
		</ul>
		<div id="convertCoinLogs">
			<ul class="convertTH">
				<li>基础币种名称</li>
				<li>兑换币种名称</li>
				<li>基础币种数</li>
				<li>兑换币种数</li>
				<li>兑换时间</li>
			</ul>
			<ul class="convertTD" id="convertLogsTD">
				<c:forEach items="${convertCoinLogsList }" var="v">
					<li>
						<span>${v.convertCointype1.fShortName }</span>
						<span>${v.convertCointype2.fShortName }</span>
						<span>${v.convertAmount1 }</span>
						<span>${v.convertAmount2 }</span>
						<span style="line-height: 0.25rem;padding-top: 0.2rem;"><fmt:formatDate value="${v.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></span>
					</li>
				</c:forEach>
			</ul>
			<div id="convertPage" class="page">
        		<ul class='pagination'>
        			<c:forEach var="current" begin="1" end="${convertTotalPage}" varStatus="">
        			<c:choose>
        				<c:when test="${1==current}">
        					<li class='active'>
		        				<a class='convertPagebutton'>${current}</a>
		        			</li>
        				</c:when>
        				<c:otherwise>
        					<li>
		        				<a class='convertPagebutton'>${current}</a>
		        			</li>
        				</c:otherwise>
        			</c:choose>
        			</c:forEach>
        		</ul>
        	</div>
		</div>
		
		<div id="activeCoinLogs" style="display:none;">
			<ul class="convertTH">
				<li>激活币种名称</li>
				<li>激活币种数量</li>
				<li>激活状态</li>
				<li>创建时间</li>
			</ul>
			<ul class="convertTD" id="activeLogsTD">
				<c:forEach items="${activeCoinLogsList }" var="v">
					<li>
						<span>${v.coinType.fShortName }</span>
						<span>${v.activeAmount }</span>
						<span>${v.statusDesc}</span>
						<span style="line-height: 0.25rem;padding-top: 0.2rem;"><fmt:formatDate value="${v.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></span>
					</li>
				</c:forEach>
			</ul>
			<div id="activePage" class="page">
        		<ul class='pagination'>
        			<c:forEach var="current" begin="1" end="${activeTotalPage}" varStatus="">
        			<c:choose>
        				<c:when test="${1==current}">
        					<li class='active'>
		        				<a class='activePagebutton'>${current}</a>
		        			</li>
        				</c:when>
        				<c:otherwise>
        					<li>
		        				<a class='activePagebutton'>${current}</a>
		        			</li>
        				</c:otherwise>
        			</c:choose>
        			</c:forEach>
        		</ul>
        	</div>
		</div>
	</div>
	<!-- 激活规则、激活手续费 -->
	<div class="regulation" id="miao">
		<!-- 激活规则 -->
		<h3 class="regulation-h3"><img src="${oss_url}/static/front2018/images/convert1.png">激活规则：</h3>
		<ul class="regulation-ul">
			<li>1、每位用户单日可激活数为个人锁仓币的0.1%，并根据每位用户当天的总买入量的万分之一进行可激活参考，
			若低于单日可激活锁仓数，则以总买入量的万分之一作为有效激活数；若高于单日可激活锁仓数，则以当天的总买入量的万分之一作为有效激活数；</li>
			<li>2、满足可激活条件的锁仓币都会在次日01：00进行自动激活。</li>
			<li>3、自动激活需要一定的激活手续费用（POW），当钱包有足够的手续费则自动激活，不足将不予自动激活；</li>
			<li>4、未自动激活的锁仓币需在24小时之内（下一个结算日之前）补足激活手续费用POW，若下一个结算日未能成功激活的则视为放弃激活，币返回锁仓账户。</li>
			<li style="color:#FF1B1B;">例如：参与2019年3月1日交易激活的用户，钱包POW不足的需在3月2日01:00之前补足POW，否则可激活锁仓币将不能成功自动激活成为待激活状态，如24小时之内（3月3日01:00之前）还未补足POW，
			待激活状态锁仓币将视为放弃激活并返回锁仓账户。</li>
		</ul>
		<!-- 激活手续费 -->
		<h3 class="regulation-h3"><img src="${oss_url}/static/front2018/images/convert2.png">激活手续费：</h3>
		<ul class="regulation-ul">
		 	 <li> 1、用户每日的锁仓币在激活之前需要钱包中有一定数额的POW，激活手续费比例为可激活的币值10%等值POW（激活价值10元的锁仓币需要手续费为1元的POW）；</li>
			 <li> 2、 所有参与者根据当天个人锁仓应激活量和总买入量应激活量来计算个人实际激活量；</li>
			 <li> 3、最终解释权归POWEX.PRO交易所运营团队所有。</li>
		</ul>
	</div>
</div>

<%@include file="../comm/footer.jsp" %>
<script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/msg.js?v=3"></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/account.convert.js?v=20181126201750"></script>
<script type="text/javascript">
//兑换记录切换
$(".convertTabNav li").click(function(){
	  $(this).addClass('active').siblings().removeClass('active');
	  var convertTabName = $(this).attr('name');
	  if(convertTabName == "convertCoinLogs") {
		  $("#convertCoinLogs").show();
		  $("#activeCoinLogs").hide();
	  } else if(convertTabName == "activeCoinLogs") {
		  $("#convertCoinLogs").hide();
		  $("#activeCoinLogs").show();
	  }
})

//兑换切换
 $(".aboTabBtn li").click(function(){
	  $(this).addClass('active').siblings().removeClass('active');
	  var aboName = $(this).attr('name');
	  if(aboName == 'ABOT'){
		  $('#cointypeFrom').val(${pproduct.convertCointype.fid});
		  $('#cointypeTo').val(${pproduct.coinType.fid});
		  $("#ABOT").show();
		  $("#ABO").hide();
	  }else if(aboName == 'ABO'){
		  $('#cointypeFrom').val(${pproduct.coinType.fid});
		  $('#cointypeTo').val(${pproduct.convertCointype.fid});
		  $("#ABOT").hide();
		  $("#ABO").show();
	  }
 })
 
 
//根据比例计算
 $("#aboAmount").on("input",function(){
       if($('#aboAmount').val()!='')
       {
          var convertRatio = $("#convertRatio").val();
          var beforeRatio = convertRatio.split(":")[0];
          var afterRatio = convertRatio.split(":")[1];
          $('#abo_abotAmount').val((parseFloat($('#aboAmount').val())*afterRatio/beforeRatio).toFixed(2));
       }else{
            $('#abo_abotAmount').val(parseFloat(0.00).toFixed(2));
       }
  });
 
 $("#abotAmount").on("input",function(){
       if($('#abotAmount').val()!='')
       {
       	var convertRatioExpire = $("#convertRatioExpire").val();
       	var beforeRatio = convertRatioExpire.split(":")[0];
           var afterRatio = convertRatioExpire.split(":")[1];
          $('#abot_aboAmount').val((parseFloat($('#abotAmount').val())*afterRatio/beforeRatio).toFixed(2));
       }else{
            $('#abot_aboAmount').val(parseFloat(0.00).toFixed(2));
       }
  });
 
//ABO——>ABOT 提交按钮
 $("#convertABOTButton").click(function(){
	  $('#cointypeFrom').val(${pproduct.convertCointype.fid});
	  $('#cointypeTo').val(${pproduct.coinType.fid});
	  $('#convertType').val(1);
	  convert.submitConvertCoinForm(true);
 })
 
 //ABOT——>ABO 提交按钮
 $("#convertABOButton").click(function(){
	  $('#cointypeFrom').val(${pproduct.coinType.fid});
	  $('#cointypeTo').val(${pproduct.convertCointype.fid});
	  $('#convertType').val(2);
	  convert.submitConvertCoinForm(true);
 })
 
 
 //兑换记录 ajax 刷新
  $(".convertPagebutton").click(function(){
	  $(this).parent().siblings().removeClass('active');
	  $(this).parent().addClass('active');
	  var currentPage = $(this).html();
	  console.log(currentPage);
	  $.ajax({
	       type: 'post',
	       url: '/user/convertCoinLogs.html?random=' + Math.round(Math.random() * 1000),
	       async: false,
	       data:{
	        	'currentPage':currentPage
	       },
	       dataType: "json",
	       success: function(result) {
	        	$("#convertLogsTD").empty();
	        	var html ="";
	        	var convertCurrentPage=result.convertCurrentPage;//全局变量赋值
	        	var convertTotalPage=result.convertTotalPage;
	       // 	console.log(result.convertCoinLogsList);
	        	if(null != result.convertCoinLogsList){
		        	 $.each(result.convertCoinLogsList,function(index,vul){ 
		        		 html += ' <li>';
		        		 html += '  <span>' + vul.convertCointype1.fShortName + '</span>'; 
		        		 html += '  <span>' + vul.convertCointype2.fShortName + '</span>'; 
		        		 html += '  <span>' + vul.convertAmount1 + '</span>'; 
		        		 html += '  <span>' + vul.convertAmount2 + '</span>'; 
		        		 html += '  <span style="line-height: 0.25rem;padding-top: 0.2rem;">' + datetimeFormat(vul.createTime.time) + '</span>'; 
		        	 })
	        	}
	        	$("#convertLogsTD").html(html);
	     	}
	  });
  })
  
  //激活记录 ajax 刷新
  $(".activePagebutton").click(function(){
	  $(this).parent().siblings().removeClass('active');
	  $(this).parent().addClass('active');
	  var currentPage = $(this).html();
	  console.log(currentPage);
	  $.ajax({
	       type: 'post',
	       url: '/user/activeCoinLogs.html?random=' + Math.round(Math.random() * 1000),
	       async: false,
	       data:{
	        	'currentPage':currentPage
	       },
	       dataType: "json",
	       success: function(result) {
	        	$("#activeLogsTD").empty();
	        	var html ="";
	        	var activeCurrentPage=result.activeCurrentPage;//全局变量赋值
	        	var activeTotalPage=result.activeTotalPage;
	        //	console.log(result.convertCoinLogsList);
	        	if(null != result.activeCoinLogsList){
		        	 $.each(result.activeCoinLogsList,function(index,vul){ 
		        		 html += ' <li>';
		        		 html += '  <span>' + vul.coinType.fShortName + '</span>'; 
		        		 html += '  <span>' + vul.activeAmount + '</span>'; 
		        		 html += '  <span>' + vul.statusDesc + '</span>'; 
		        		 html += '  <span style="line-height: 0.25rem;padding-top: 0.2rem;">' + datetimeFormat(vul.createTime.time) + '</span>'; 
		        	 })
	        	}
	        	$("#activeLogsTD").html(html);
	     	}
	  });
	        
  })
$(function(){
	  /* 设置body滚动位置 */
	  $(".scrollBtn").attr('href',window.location.href+'#miao');
  }) 
</script>
</body>
</html>
