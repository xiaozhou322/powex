<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;if (request.getServerName().equals("www.gbcax.com")){basePath="https://www.gbcax.com";}
%>

<!doctype html>
<html>
<head> 
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp" %>
<link href="${oss_url}/static/front/css/index/main.css?v=2" rel="stylesheet" type="text/css" />
<link href="${oss_url}/static/front2018/css/lucky.css?v=20181126201750" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${oss_url}/static/front2018/js/index/main.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/layer/layer.js?v=20181126201750"></script>
<style type="text/css">
  .lw-helpCons{background:#ffffff;}
  .lw-heilpTabs{background:#f8f8f8;}
  .lw-helpRight{background:#f8f8f8; border:none;}
  .lw-heilpTabs ul li{border: 1px solid #fff;}
  .lw-helpRight h2{color:#333; font-weight:600;}
  .lw-helpNav{height: 80px; line-height:80px; background: #f8f8f8;border-bottom: 1px solid #e3e3e3;}
  .lw-helpNav a{color:#6f6f6f;}
    .lw-helpNav a:hover{background:none; color:#2269d4;}
  .lw-helpNav a.lw-active{background:#fff; color:#6f6f6f; border-bottom: 1px solid #2269d4;}
</style>
</head>
<body style="background: #F9FBFF;">
<%@include file="../comm/header.jsp" %>
<section class="l-content" >
<div class="projectTop">
	<h3>全新项目合作模式  享专属权益</h3>  
</div>
<br>
<div class="luckyCenter">
	<!-- 活动规则介绍 -->
	<div class="luckyTit">
		<h3>项目账户权益介绍</h3>
		<span></span>	<!-- 虚线 -->
	</div>
	<!-- 自助上币 -->
	<div class="proself">
		<ul class="proUlOdd">
			<li>
				<h3><img src="${oss_url}/static/front2018/images/exchange/shangbi2x.png" style="vertical-align: -4px;">自助上币</h3>
				<p class="bianpro">
					<img src="${oss_url}/static/front2018/images/exchange/biankuang.png">
					<img src="${oss_url}/static/front2018/images/exchange/biangkuang1.png">
					自助上币申请，币种信息管理、查看、修改、下线功能服务。
				</p>
				<p class="bianpro2">
					<span><img src="${oss_url}/static/front2018/images/exchange/duigouPro2x.png">自助上市</span>
					<span><img src="${oss_url}/static/front2018/images/exchange/duigouPro2x.png">币信息管理</span>

				</p>
				<c:if test="${sessionScope.login_user != null }">
				<p class="bianpro3">
					<!-- <a href="http://192.168.0.119:8089/project/">我要上币</a> -->
					 <button  onclick="return fisprojecter()" class="fisprojecter">我要上币</button>
				</p>
				</c:if>
			</li>
			<li>
				<img src="${oss_url}/static/front2018/images/exchange/shangbi12x.png">		
			</li>
		</ul>
		
		<ul class="proUleven">
			<li style="padding: 108px 248px 80px 353px;">
				<img src="${oss_url}/static/front2018/images/exchange/maskPro2x.png">		
			</li>
			<li>
				<h3>专业做市<img src="${oss_url}/static/front2018/images/exchange/zuoshi@2x.png"></h3>
				<p class="evenpro">
					<img src="${oss_url}/static/front2018/images/exchange/biankuang4.png">
					<img src="${oss_url}/static/front2018/images/exchange/biangkuang3.png">
					高性价专业做市服务，各等级专项服务按需求提供给项目方。
				</p>
				<p class="evenpro2">
					<span><img src="${oss_url}/static/front2018/images/exchange/duigouPro2x.png">开通市场</span>
					<span><img src="${oss_url}/static/front2018/images/exchange/duigouPro2x.png">市场配置</span>
				</p>
				<p class="evenpro3">
					<button>开通市场</button>
				</p>
			</li>
		</ul>
		<ul class="proUlOdd">
			<li>
				<h3><img src="${oss_url}/static/front2018/images/exchange/fuwuPro2x.png" style="width: 55px;height: 48px;vertical-align: -15px;">特色服务</h3>
				<p class="bianpro">
					<img src="${oss_url}/static/front2018/images/exchange/biankuang.png">
					<img src="${oss_url}/static/front2018/images/exchange/biangkuang1.png">
					平台只收取基础手续费，项目方可灵活加成共享收益；专属二级域名，单项目专区
				</p>
				<p class="bianpro2">
					<span><img src="${oss_url}/static/front2018/images/exchange/duigouPro2x.png">手续费灵活加成</span>
					<span><img src="${oss_url}/static/front2018/images/exchange/duigouPro2x.png">即时发布公告</span>
					<span><img src="${oss_url}/static/front2018/images/exchange/duigouPro2x.png">专属二级域名</span>
				</p>
			</li>
			<li>
				<img src="${oss_url}/static/front2018/images/exchange/serves2x.png" class="proserves2x">		
			</li>
		</ul>
		
		<ul class="proUleven">
			<li>
				<img src="${oss_url}/static/front2018/images/exchange/data1Prox.png" style="width: 186px;height: 190px;">		
			</li>
			<li>
				<h3>数据管理<img src="${oss_url}/static/front2018/images/exchange/dataPro.png" style="width: 52px;height: 54px;"></h3>
				<p class="evenpro">
					<img src="${oss_url}/static/front2018/images/exchange/biankuang4.png">
					<img src="${oss_url}/static/front2018/images/exchange/biangkuang3.png">
					资产收益统一管理，一目了然，交易数据可视化呈现。
				</p>
				<p class="evenpro2" style="width: 246px;float: right;">
					<span><img src="${oss_url}/static/front2018/images/exchange/duigouPro2x.png">资产统一管理</span>
					<span><img src="${oss_url}/static/front2018/images/exchange/duigouPro2x.png">收益记录，按月结算</span>
					<span><img src="${oss_url}/static/front2018/images/exchange/duigouPro2x.png">委托、交易、充值记录管理</span>
				</p>
			</li>
		</ul>
		<ul class="powexpro">
			<li>bd@powex.pro</li>
			<li><img src="${oss_url}/static/front2018/images/exchange/saoma2x.png"></li>
			<li>联系我们申请项目账户</li>
		</ul>
	</div>
</div>
 <div class="realfixed bindtrademodule" style="margin-left: -124px;top: 18em; height: 331px;">
    	<ul style="line-height: 30px;">
    		<h3><img src="${oss_url}/static/front2018/images/close@2x.png" class="bindtradeclose"></h3>
    		<li>
    			<p style="width: 218px; margin: 0 auto;line-height: 22px;">您好，联系我们成为项目方申请自助上币服务</p>
    			<img src="${oss_url}/static/front2018/images/exchange/saoma2x.png">
    			<span>扫一扫联系我们</span>
   			</li>
    	</ul>
    </div>
    <!--蒙层-->
    <div class="realModule bindtrademodule ngrealModule"></div>
<input type="hidden" id="fisprojecter" value="${sessionScope.login_user.fisprojecter }" />
</section> 

<%@include file="../comm/footer.jsp" %>	



<script type="text/javascript" src="${oss_url}/static/front2018/js/jquery.rotate.min.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/main/scroll.js?v=20181126201750"></script>
<script type="text/javascript">
	$(function () {
		
		$(".bindtradeclose").click(function(){
			$(".bindtrademodule").hide();
		})
	})
	function fisprojecter() {
			var fisprojecter = $("#fisprojecter").val();
			if(fisprojecter == "false"){
				$(".bindtrademodule").show();
				
			}else{
				window.location.href="http://192.168.0.119:8089/project/";
			}
		}
</script>

</body>
</html>
