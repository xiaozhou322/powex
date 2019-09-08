<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<!-- <meta name = "viewport" content = "width = device-width, initial-scale = 1.0, maximum-scale = 1.0, user-scalable = 0" /> -->
<meta name = "viewport" content = "width = device-width, user-scalable = no,initial-scale=0.5,maximum-scale=0.5,minimum-scale=0.5">
<%@include file="../comm/link.inc.jsp" %>
</head>
<body >
<%@include file="../comm/header.jsp" %>
<link href="${oss_url}/static/mobile2018/css/base.css?v=2" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${oss_url}/static/mobile2018/css/idangerous.swiper.css?v=20181126201750" type="text/css" />
<link rel="stylesheet" href="${oss_url}/static/mobile2018/css/lucky/projects.css?v=20181126201750" type="text/css" />
		
		<header>
			<div class="h_title">全新项目合作模式 享专属权益</div>
		</header>
		<section>
			<div class="sec_title">
				<p>项目账户权益介绍</p>
				<div class="thick_line"></div>
				<div class="fine_line"></div>
			</div>

			<div class="device">
				<div class="swiper-container">
					<div class="swiper-wrapper">
						<div class="swiper-slide">
							<div class="slide_title">
								<img src="${oss_url}/static/mobile2018/images/exchange/shangbi@2x.png" />&nbsp;
								<span>自助上币</span>
							</div>
							<div class="slide_introduce">
								<img src="${oss_url}/static/mobile2018/images/exchange/bainqian1@2x.png" />
								<p>自助上币申请，币种信息管理、查看、修改、下线功能服务。</p>
								<img src="${oss_url}/static/mobile2018/images//exchange/baioqian2@2x.png">
							</div>
							<div class="slide_details">
								<ul>
									<li><img src="${oss_url}/static/mobile2018/images/exchange/gou@2x.png"></li>
									<li><img src="${oss_url}/static/mobile2018/images/exchange/gou@2x.png"></li>
								</ul>
								<ul>
									<li>自助上币</li>
									<li>币种管理</li>
								</ul>
								<ul>
									<li><img src="${oss_url}/static/mobile2018/images/exchange/shangbi1@2x.png"></li>
								</ul>
							</div>
							<input class="btnmoney" type="button" value="我要上币" />
						</div>
						<div class="swiper-slide">
							<div class="slide_title">
								<img src="${oss_url}/static/mobile2018/images/exchange/shangcahng@2x.png" />&nbsp;
								<span>专业做市</span>
							</div>
							<div class="slide_introduce">
								<img src="${oss_url}/static/mobile2018/images/exchange/bainqian1@2x.png" />
								<p>高性价专业做市服务，各等级专项服务按需求提供项目账户。</p>
								<img src="${oss_url}/static/mobile2018/images/exchange/baioqian2@2x.png">
							</div>
							<div class="slide_details">
								<ul>
									<li><img src="${oss_url}/static/mobile2018/images/exchange/gou@2x.png"></li>
									<li><img src="${oss_url}/static/mobile2018/images/exchange/gou@2x.png"></li>
								</ul>
								<ul>
									<li>开通市场</li>
									<li>市场配置</li>
								</ul>
								<ul>
									<li><img src="${oss_url}/static/mobile2018/images/exchange/shichang2@2x.png"></li>
								</ul>
							</div>
							<input type="button" value="开通市场" style="opacity: 0.4;"/>
						</div>
						<div class="swiper-slide">
							<div class="slide_title">
								<img src="${oss_url}/static/mobile2018/images/exchange/fuwu@2x.png" />&nbsp;
								<span>特色服务</span>
							</div>
							<div class="slide_introduce">
								<img src="${oss_url}/static/mobile2018/images/exchange/bainqian1@2x.png" />
								<p>平台只收取基础手续费，项目账户可灵活加成并享收益；专属二<br>级域名，单项目专区</p>
								<img src="${oss_url}/static/mobile2018/images//exchange/baioqian2@2x.png">
							</div>
							<div class="slide_details2">
								<ul style="margin-left: 0.4rem;">
									<li><img src="${oss_url}/static/mobile2018/images/exchange/gou@2x.png"></li>
									<li><img src="${oss_url}/static/mobile2018/images/exchange/gou@2x.png"></li>
									<li><img src="${oss_url}/static/mobile2018/images/exchange/gou@2x.png"></li>
								</ul>
								<ul>
									<li>手续费灵活加成</li>
									<li>即时发布公告</li>
									<li>专属二级域名</li>
								</ul>
								<ul>
									<li><img src="${oss_url}/static/mobile2018/images/exchange/fuwu1@2x.png" style="width: 1.91rem"></li>
								</ul>
							</div>
						</div>
						<div class="swiper-slide">
							<div class="slide_title">
								<img src="${oss_url}/static/mobile2018/images/exchange/shuju@2x.png" />&nbsp;
								<span>数据管理</span>
							</div>
							<div class="slide_introduce">
								<img src="${oss_url}/static/mobile2018/images//exchange/bainqian1@2x.png" />
								<p>资产收益统一管理，一目了然，独特的交易数据可视化呈现。</p>
								<img src="${oss_url}/static/mobile2018/images//exchange/baioqian2@2x.png">
							</div>
							<div class="slide_details2">
								<ul>
									<li><img src="${oss_url}/static/mobile2018/images/exchange/gou@2x.png"></li>
									<li><img src="${oss_url}/static/mobile2018/images/exchange/gou@2x.png"></li>
									<li><img src="${oss_url}/static/mobile2018/images/exchange/gou@2x.png"></li>
								</ul>
								<ul>
									<li>资产统一管理</li>
									<li>收益记录，按月结算</li>
									<li>委托、交易、充值记录管理</li>
								</ul>
								<ul>
									<li style="margin-left: -0.75rem;"><img src="${oss_url}/static/mobile2018/images/exchange/shuju1@2x.png"></li>
								</ul>
							</div>
						</div>
					</div>
				</div>
				<div class="pagination"></div>
			</div>
			<div class="f_code">
				<div>bd@powex.pro</div>
				<img src="${oss_url}/static/mobile2018/images/exchange/lianxi@2x.png" />
				<p>联系我们成为专属项目账户</p>
			</div>
<!-- 我要上币弹窗 -->   
     <div class="realfixed bindtrademodule">
    	<ul>
    		<h3><img src="${oss_url}/static/front2018/images/close@2x.png" class="bindtradeclose"></h3>
    		<li>
    			<p style="width: 3.2rem;margin: 0.21rem auto;line-height: 0.3rem;">您好，联系我们成为项目方申请自助上币服务</p>
    			<img src="${oss_url}/static/front2018/images/exchange/saoma2x.png" style="width: 3.25rem;">
    			<span>扫一扫联系我们</span>
   			</li>
    	</ul>
    </div>
    <!--蒙层-->
    <div class="realModule bindtrademodule ngrealModule"></div>
		</section>
<%@include file="../comm/tabbar.jsp"%>
<%@include file="../comm/footer.jsp" %>
<script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/idangerous.swiper.min.js?v=20181126201750"></script>
<script src="${oss_url}/static/mobile2018/js/fluckydraw/index.js?v=20181126201750"></script>

<script type="text/javascript">
	$(function(){
		$(".btnmoney").on("click", function() {
			$(".bindtrademodule").show();
		});
		
		$(".bindtradeclose").on("click", function() {
			$(".bindtrademodule").hide();
		});
	})
</script>
</body>
</html>

