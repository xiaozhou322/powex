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
<link href="${oss_url}/static/front2018/css/usdtTrade.css?v=11" rel="stylesheet" type="text/css" />
</head>
<body class="">
<%@include file="../comm/white_header.jsp" %>
 <section class="uTrade mg clear">
        <div class="agentApply">
            <div class="applyFor">
                <ol class="clear">                                                   
                    <li class="fl active">
                        <em class="fl"><spring:message code="agent.Datapreparation" /></em>

                    </li>                    
                    <li class="fl active">
                        <i class="fl"></i>
                        <span class="s_line fl"></span>
                        <em class="fl"><spring:message code="agent.sendemail" /></em>
                    </li>                    
                    <li class="fl active">
                        <i class="fl"></i>
                        <span class="s_line fl"></span>
                        <em class="fl"><spring:message code="agent.Submitreview" /></em>
                    </li>                    
                    <li class="fl">
                        <i class="fl"></i>
                        <span class="s_line fl"></span>                        
                        <em class="fl"><spring:message code="agent.Auditresults" /></em>
                    </li>
                </ol>
                <div class="applyType clear">
                    <dl class="fl">
                        <dt><img src="${oss_url}/static/front2018/images/1.png"  /></dt>
                        <dd><spring:message code="agent.Exclusivebooth" /></dd>
                    </dl>                    
                    <dl class="fl">
                        <dt><img src="${oss_url}/static/front2018/images/2.png" /></dt>
                        <dd><spring:message code="agent.service" /></dd>
                    </dl>                    
                    <dl class="fl">
                        <dt><img src="${oss_url}/static/front2018/images/3.png"  /></dt>
                        <dd><spring:message code="agent.Auditresults" /></dd>
                    </dl>
                </div>
                <div class="careText">
                    <p class="p_01">如果您想成为有资质的商人，我们将冻结您5000HT的定金。<a href="#" class="colorBlue">什么是HT？</a></p>
                    <p>请事先通过高级认证。</p> 
                    <p>请将如下材料用邮件发送至123@GBCAX吗，我们将尽快对您的申请进行审核。</p>
                </div>
                <div class="applyForCon">
                    <ul class="clear">
                        <li><span class="colorBlue">*</span>联系电话</li>
                        <li><span class="colorBlue">*</span>个人QQ</li>
                        <li><span class="colorBlue">*</span>个人微信</li>
                        <li><span class="colorBlue">*</span>是否有相应的风控策略</li>
                        <li><span class="colorBlue">*</span>是否从事过数字资产的场外交易</li>
                        <li><span class="colorBlue">*</span>个人资产(近一个月银行流水情况)</li>
                    </ul>
                </div>
                <div class="tjApply">
                    <div class="check">
                        <label class="lab fl">
                            <input class="checkbox" type="checkbox">
                            <span></span>
                        </label> 
                         <em>我已阅读<a href="javascript:;" class="colorBlue">《申请代理商协议》</a></em>
                    </div>
                    <button class="buyBt applyBtn">确认申请</button>
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
		util.layerAlert("", language["agent.sorry"], 2);
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
