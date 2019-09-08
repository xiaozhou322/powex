<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
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
<%@include file="comm/link.inc.jsp" %>
</head>
<body >
<%@include file="comm/header.jsp" %>
<link href="${oss_url}/static/mobile2018/css/base.css?v=2" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${oss_url}/static/mobile2018/css/lucky/lucky.css?v=20181126201750" type="text/css"/>
  <input id="activity_id" type="hidden"  value="${factivityModel.id }"/>
<header class="lucky_head">
			<div class="head_title">注册即赠POW<br />赢取10000ETH豪华大奖</div>
			<div class="head_content">
				<ul>
					<li>
						<img src="${oss_url}/static/mobile2018/images/exchange/chongzhi@3x.png" />
						<span>充值人数</span>
					</li>
					<li>
						<span>12300</span><em>人</em>
					</li>
				</ul>
				<ul>
					<li>|</li>
				</ul>
				<ul>
					<li>
						<img src="${oss_url}/static/mobile2018/images/exchange/pow@3x.png" />
						<span>当前POW</span>
					</li>
					<li>
						<span>12300</span><em>万</em>
					</li>
				</ul>
			</div>
		</header>
		<section class="s_con">
			<div class="act_title">
				<img src="${oss_url}/static/mobile2018/images/exchange/rules@3x.png" />&nbsp;
				<span>活动规则</span>
			</div>
			<div class="act_details">
				<div class="d_con">
					<span>奖励金额：</span><br />
					<span>参与平台抽奖可有机会获得平台超级大奖10000ETH获得者的相应推荐人可获得推荐奖励。</span>
				</div>
				<div class="details_con">
					<ul class="invite_cont2">
						<li><img src="${oss_url}/static/mobile2018/images/exchange/10000ETH@3x.png"></li>
						<li><img alt="" src="${oss_url}/static/mobile2018/images/exchange/A@3x.png"></li>
						<li>A</li>
						<li>超级大奖</li>
						<li>（最高奖金获得者）</li>
					</ul>

					<ul class="invite_cont2">
						<li><img src="${oss_url}/static/mobile2018/images/exchange/1000ETH@3x.png"></li>
						<li><img alt="" src="${oss_url}/static/mobile2018/images/exchange/B@3x.png"></li>
						<li>B</li>
						<li>引荐奖</li>
						<li>（A的推荐人B）</li>
					</ul>

					<ul class="invite_cont2">
						<li><img src="${oss_url}/static/mobile2018/images/exchange/100ETH@3x.png"></li>
						<li><img alt="" src="${oss_url}/static/mobile2018/images/exchange/C@3x.png" width="89px" height="63px"></li>
						<li>C</li>
						<li>溯源奖</li>
						<li>（B的推荐人C）</li>
					</ul>
				</div>
			</div>
			<div class="act_way">
				<div class="w_con">
					<span>参与方式：</span><br />
					<span>用户在POWEX.PRO平台进行注册、推荐、充值、交易(平台开放后可进行交易)均可获赠POW参与POW万币活动。</span>
				</div>
				<div class="w_icon click_recom">
					<ul id="click_reg">
						<li><img src="${oss_url}/static/mobile2018/images/exchange/zhuce@3x.png"></li>
						<li>注册</li>
					</ul>
					<ul id="click_recom">
						<li><img src="${oss_url}/static/mobile2018/images/exchange/yaoqing @3x.png"></li>
						<li>推荐</li>
					</ul>
					<ul id="click_pay">
						<li><img src="${oss_url}/static/mobile2018/images/exchange/chongzhi1@3x.png"></li>
						<li>充值</li>
					</ul>
					<ul id="click_deal">
						<li><img src="${oss_url}/static/mobile2018/images/exchange/jiaoyi @3x.png"></li>
						<li>交易</li>
					</ul>
				</div>
				<div class="w_frame w0">
					<img src="${oss_url}/static/mobile2018/images/exchange/close@2x.png" />
					<p>新用户注册并通过KYC1即赠送1POW，用户通过KYC2认证可使用POW参与抽奖活动。</p>
				</div>
				<div class="w_frame w1">
					<img src="${oss_url}/static/mobile2018/images/exchange/close@2x.png" />
					<p>每邀请一个用户并成功通过KYC2认证即赠送1POW，邀请人享有推荐奖励资格。</p>
				</div>
				<div class="w_frame w2">
					<img src="${oss_url}/static/mobile2018/images/exchange/close@2x.png" />
					<p>首次充币获赠1POW，充币数量满1ETH或等值其他币种。</p>
				</div>
				<div class="w_frame w3">
					<img src="${oss_url}/static/mobile2018/images/exchange/close@2x.png" />
					<p>当天交易手续费每满1USDT赠送1POW，平台根据用户当天交易手续费取整赠送POW，不做累计。</p>
				</div>
			</div>
			
			<div class="act_rule">
				<span>POW抽奖规则：</span><br />
				<div class="r_t">平台注册用户在平台开放后完成KYC2认证，使用获赠的POW参与抽奖活动。每消耗1POW参与一次刮奖，刮奖之后获得参与抽奖的奖票。</div>
				<div class="r_pic">
					<ul>
						<li><img src="${oss_url}/static/mobile2018/images/exchange/KYC2@3x.png"></li>
						<li>KYC2<br>认证</li>
					</ul>
					<ul>
						<li><img src="${oss_url}/static/mobile2018/images/exchange/jiantou@3x.png"></li>
					</ul>
					<ul>
						<li><img src="${oss_url}/static/mobile2018/images/exchange/choujiang@3x.png"></li>
						<li>参与<br>抽奖</li>
					</ul>
					<ul> 
						<li><img src="${oss_url}/static/mobile2018/images/exchange/jiantou@3x.png"/></li>
					</ul>
					<ul>
						<li><img src="${oss_url}/static/mobile2018/images/exchange/jiangpiao@3x.png"/></li>
						<li>获得<br>奖票</li>
					</ul>
					<ul> 
						<li><img src="${oss_url}/static/mobile2018/images/exchange/jiantou@3x.png"/></li>
					</ul>
					<ul>
						<li><img src="${oss_url}/static/mobile2018/images/exchange/jiangbei@2x.png"/></li>
						<li>开奖</li>
					</ul>
				</div><br />
				
			</div>
			<div class="act_notes">
				<span>*POW：</span><br />
				<div class="n_t">POW是POWEX.PRO的平台通用奖券，目前可用于平台抽奖活动，后续也可用于用户交易手续费折扣，可参与众筹，可用于流动性保证，可参与POW持有者专享活动，可币币交易，可发展生态链等。</div>
				<div class="n_pic">
					<ul>
						<li><img src="${oss_url}/static/mobile2018/images/exchange/huodong@2x.png"></li>
						<li>参与活动</li>
					</ul>
					<ul>
						<li><img src="${oss_url}/static/mobile2018/images/exchange/zekou@2x.png"></li>
						<li>手续费折扣</li>
					</ul>
					<ul>
						<li><img src="${oss_url}/static/mobile2018/images/exchange/jiaoyi@2x.png"/></li>
						<li>币币交易</li>
					</ul>
					<ul>
						<li><img src="${oss_url}/static/mobile2018/images/exchange/dongtai @2x.png"/></li>
						<li>发展生态</li>
					</ul>
				</div>
				<div class="act_attention">
						*奖票是参与抽奖活动的唯一凭证，所有奖票都要记录在区块链上公开。<br />*本抽奖活动公平、公开、公正，所有信息上链并对外公布
				</div>	
			</div>
		</section>
	<div class="main_foot">
			<div class="m_rule">
				<div class="f_line"></div>
				<div>活动原则</div>
				<div class="f_line"></div>
			</div>
			<div class="r_desc">本次抽奖活动公平、公正、公开，所有信息上链并对外公布</div>
			<div class="r_pictures">
				<ul class="r_picture1">
					<li>公平</li>
					<li>数据封存后上链</li>
				</ul>
				<ul class="r_picture2" style="margin: 0px 45px;">
					<li>公正</li>
					<li>抽奖信息上链并对外公布</li>
				</ul>
				<ul class="r_picture3">
					<li>公开</li>
					<li>摇奖算法公开真实随机数</li>
				</ul>
			</div>
		</div>
	
<%@include file="comm/tabbar.jsp"%>
<%@include file="comm/footer.jsp" %>
<script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
<script src="${oss_url}/static/mobile2018/js/fluckydraw/index.js?v=20181126201750"></script>
</body>
</html>
