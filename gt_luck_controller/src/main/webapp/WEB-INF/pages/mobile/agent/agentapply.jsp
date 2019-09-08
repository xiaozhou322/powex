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
<!-- <link href="${oss_url}/static/front/css/index/common.css?v=20181126201750" rel="stylesheet" type="text/css" />
<link href="${oss_url}/static/front/css/index/main.css?v=20181126201750" rel="stylesheet" type="text/css" /> -->
<!-- <link href="${oss_url}/static/front/css/agent/css/common.css?v=20181126201750" rel="stylesheet" type="text/css" /> -->
<link href="${oss_url}/static/mobile2018/css/usdtTrade.css?v=20181126201750" rel="stylesheet" type="text/css" />

</head>
<body class="Ubody">
<%@include file="../comm/header.jsp" %>
<header class="tradeTop">  
    <i class="back toback2"></i>
    <h2 class="tit">代理商申请</h2>
</header>
    <section class="usdtTrade2 mg clear">
        <div class="agentApply">
            <h1>代理商申请</h1>
            <div class="applyFor">
                <div class="steps">
                    <ol>
                        <li id="rechargeprocess1" class="active textLeft fl">
                            <span></span>
                            <em>资料准备</em>
                        </li>
                        <li id="rechargeprocess2" class="fl active textLeft">
                            <span></span>
                            <em>发送邮件</em>
                        </li>
                        <li id="rechargeprocess3" class="fl active textCenter">
                            <span></span>
                            <em>提交审核</em>
                        </li>                        
                        <li id="rechargeprocess4" class="fl textRight">
                            <span></span>
                            <em>审核结果</em>
                        </li>
                    </ol>
                </div>
               <div class="applyType clear">
                    <dl class="fl">
                        <dt><img src="${oss_url}/static/mobile/css/agent/images/1.png"  /></dt>
                        <dd>专属展位</dd>
                    </dl>                    
                    <dl class="fl">
                        <dt><img src="${oss_url}/static/mobile/css/agent/images/2.png"  /></dt>
                        <dd>1&1服务</dd>
                    </dl>                    
                    <dl class="fl">
                        <dt><img src="${oss_url}/static/mobile/css/agent/images/3.png"  /></dt>
                        <dd>更低手续费</dd>
                    </dl>
                </div>
                <div class="careText">
                    <div class="careTextBox">
                        <p class="p_01">如果您想成为有资质的商人，我们将冻结您5000HT的定金。<a href="#" class="cblue">什么是HT？</a></p>
                        <p>请事先通过高级认证。 请将如下材料用邮件发送至123@GBCAX吗，我们将尽快对您的申请进行审核。</p>
                    </div>
                </div>
                <div class="applyForCon">
                    <ul class="clear">
                        <li class="liw"><span class="cblue">* </span>联系电话</li>
                        <li class="liw"><span class="cblue">* </span>个人QQ</li>
                        <li class="liw"><span class="cblue">* </span>个人微信</li>
                        <li><span class="cblue">* </span>是否有相应的风控策略</li>
                        <li><span class="cblue">* </span>是否从事过数字资产的场外交易</li>
                        <li><span class="cblue">* </span>个人资产(近一个月银行流水情况)</li>
                    </ul>
                </div>
                <div class="tjApply">
                    <div class="checkCon">
                        <label class="lab fl">
                            <input class="checkbox" type="checkbox">
                            <span></span>
                        </label> 
                         <em>我已阅读<a href="#" class="cblue">《申请代理商协议》</a></em>
                    </div>
                    <button class="buyBt apply_Btn applyBtn">确认申请</button>
                </div>
            </div>
        </div>
    </section>


<%@include file="../comm/footer.jsp" %>	

<script>
var agent_min_usdt = ${agent_min_usdt};
var totalUsdt = ${totalUsdt};


$('.applyBtn').click(function(){
    if(parseInt(totalUsdt)<parseInt(agent_min_usdt))
    {
        util.layerAlert("", '非常遗憾，您还不具备申请代理商资格', 2);
        return;
    }
    else
    {
        var param = {}
        jQuery.post('/agent/doapply.html', param, function (data) {
            if (data.code == -1) {
                util.layerAlert("", data.msg, 2);
            } else {
                window.location.href='/agent/agentapply.html?type=1';
            }
        }, "json");
    
    }

})
</script>
</body>
</html>
