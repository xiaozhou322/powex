<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>
<%
	String path = request.getContextPath();
	
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
	if (request.getServerName().equals("www.gbcax.com")){basePath="https://www.gbcax.com";}
%>

<!doctype html>
<html>
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<%@include file="../comm/link.inc.jsp"%>
</head>
<style type="text/css">
    .activity{margin-top:1.5rem;}
    .activity img{width:100%; height:auto; display:block; }
    .activityMain{width:100%; min-height:450px; padding:2px 2px 30px; overflow-x:hidden; background:url(/static/mobile/images/conBg.jpg) no-repeat center 0; background-size:100% 100%;}
    .activityMain ul{width:110%;}
    .activityMain ul li{width:30%; height:1.4rem; line-height: 1.4rem; text-align:center; color:#fff; background:url(/static/mobile/images/li_bg.png) no-repeat center; background-size:100% 100%; margin:0 0.5% 0.5% 0; font-size:15px; font-weight:bold;}
    .activityMain ul li.active{background:url(/static/mobile/images/activeBg.png) no-repeat center; background-size:100% 100%; color:#161511;}
    .W24{width:22.3%!important;}
    .W48{width:44.6%!important;}
    .cfff{color:#fff;}
    .cffbd50{color:#ffbd50;}
    .c72bbc7{color:#72bbc7;}
    .c00c1ff{color:#00c1ff;}
    .ceb603f{color:#eb603f;}
    .cf49c13{color:#f49c13;}
    .c88ba73{color:#88ba73;}
    .point{width:12px; height:12px; border-radius:50%; position: absolute; left:0; top:8px;}
    .pindent{text-indent:2em;}
    .activityTxtList{width:98%; height:auto; margin:0.8rem auto 0.5rem; background:url(/static/mobile/images/borderBg.png) no-repeat center; background-size:96% 100%; padding:40px 35px;font-size:0.5rem; line-height:1rem; display:none;}
    .activityTxtList.active{display:block;}
    
    .activity_tit02 h3{text-align:center; font-size:16px;}
    .activity_tit02 img{width:100px; margin:0.6rem auto; display:block;}
    .activity_tit02 .txt p{font-size:14px; line-height:22px; position: relative; padding-left:20px;}
    .txt .point{width:10px; height:10px; border-radius:50%; position: absolute; left:0; top:8px;}
</style>    

<body>

<%@include file="../comm/header.jsp"%>
<c:choose>
<c:when test="${pageContext.response.locale eq 'zh_CN' }">
<header class="header backHeader"> 
    <i class="backIcon" onclick="previousPage();"></i>
    <h2 class="tit">区块链项目扶持政策</h2>
</header>
<section>
    <div class="activity">
        <img src="${oss_url}/static/front/images/diypages/newcoin/activitymobile.jpg" alt="" />
    </div>
    <div class="activityMain">
        <ul>
            <li class="fl W24 active">申请规则</li>
            <li class="fl W24">扶持政策</li>
            <li class="fl W24">交易门槛</li>
            <li class="fl W24">下线币种说明</li>
            <div class="clear"></div>
        </ul>
        <div class="activityTxtList cffbd50 active">    
                       （1）强有力的团队或社区维护；<br />
                        （2）有实际技术支撑或有实际应用的项目；<br />
                        （3）项目无政策风险并且达到专业和合规要求；<br />
                        （4）能真实及时披露项目信息包含项目白皮书，定期发展及进度报告；<br />
                        上币申请请发送项目有关文件至邮箱：bd@gbcax.com 或官方微信号
        </div>
        <div class="activityTxtList cffbd50 cfff">            
            （1）新币上线G网将免上币费用；<br />
                    （2）G网将提供首页banner和官方公告进行宣传推广；<br />
                    （3）扶持期：自上线当日起，90天；<br />
                    （4）新币通过申请之后将于2个工作日内上线交易；<br />
                    （5）项目方可提供部分糖果进行空投给平台GT持有用户和活动推广，额度不限；<br />
                    （6）上线的项目，不少于50名新注册的用户。新注册用户指：新币上线之后注册的用户，以上币时间为准；
        </div>        
        <div class="activityTxtList cffbd50 cfff">            
            （1）为确保给用户及项目方一如既往提供稳定、有序以及优质的服务，新币扶持期结束后将统计扶持
                    期间总交易额，扶持期内日均交易额需达到5万美金；<br />
                    （2）新币上线将开放创新区BTC和ETH交易对；<br />
                    （3）自新币扶持期内若日交易额达100万美金以上，则开放USDT交易对；<br />
        </div>
        <div class="activityTxtList cfff">            
            为保护投资者利益，G网保留项目下线或继续支持项目在平台上交易的权利， 项目方如果触发如下条件，我们会公告通知交易下线，包含但不限于： </br>
                    （1）项目团队解散；<br /> 
                    （2）项目方面临重大法律问题；<br />    
                    （3）由于战略调整和发展需要，项目运营团队主动要求下线；<br />   
                    （4）严重的技术或安全问题没有及时得到解决；<br /> 
                    （5）自新币扶持期过后连续5个交易日，每日交易额小于5万美金；<br />  
                    （6）信息披露出现重大偏差；<br /> 
                    （7）突然出现分叉；<br /> 
                    （8）不满足继续交易的其他事项；<br />   
        </div>
</div>

</section>
</c:when>
<c:otherwise>
<header class="header backHeader"> 
    <i class="backIcon" onclick="previousPage();"></i>
    <h2 class="tit">Global Blockchain Support Plan</h2>
</header>
<section>
    <div class="activity">
        <img src="${oss_url}/static/front/images/diypages/newcoinen/activitymobile.jpg" alt="" />
    </div>
    <div class="activityMain">
        <ul>
            <li class="fl W48 active">Application Rules</li>
            <li class="fl W48">Support Policy</li>
            <li class="fl W48">Transaction Threshold</li>
            <li class="fl W48">Removal Policy</li>
            <div class="clear"></div>
        </ul>
        <div class="activityTxtList cffbd50 active">            
                       1.&nbsp; strong team or community maintenance;<br />
                        2.&nbsp; projects with practical technical support or practical application;<br />
                        3.&nbsp; the project has no policy risk and meets the requirements of professional and compliance.&nbsp;<br />
                        4.&nbsp; the real and timely disclosure of project information contains the project white paper, regular development and progress report;<br />
                        Please send the project documents to the mailbox: bd@gbcax.com;
        </div>
        <div class="activityTxtList cffbd50 cfff">            
            1.&nbsp; the new currency GBCAX will be exempt from the cost of the money;<br />
                    2.&nbsp; GBCAX will provide the home page banner and official announcement for publicity and promotion;<br />
                    3.&nbsp; support period: from the day of the line, 90 days;<br />
                    4.&nbsp; the new currency will be traded within 2 working days after the application；<br />
                    5.&nbsp; the project can provide part of the candy for air drop to the platform GT to hold users and activities to promote, the amount is unlimited;<br />
                    6.&nbsp; on the line of the project, no less than 50 newly registered users. The new registered user refers to the registered users after the new currency is online, and the time of the above currency is <accurate class="br"></accurate>

        </div>        
        <div class="activityTxtList cffbd50 cfff">            
            1.&nbsp;  in order to ensure stable and orderly service to users and project providers, the total transaction volume during the support period and the average daily transaction volume during the support period will reach 50 thousand dollars after the support period of the new currency;<br />
                    2.&nbsp;  new currency line will open the Open Innovation Zone BTC and ETH trading pairs;<br />
                    3.&nbsp;  in the support period of the new coin, if the daily transaction amount to more than 1 million USDT, then open the USDT transaction;<br />

        </div>
        <div class="activityTxtList cfff">            
            To protect the interests of investors, to retain or continue to support the project GBCAX project in trading platform on the right side of the project if the trigger conditions are as follows, we will notice the transaction offline, including but not limited to: 
                     </br>
                    1.&nbsp; project team disbanded;<br />
                    2.&nbsp; project faces major legal issues;<br />
                    3.&nbsp; as a result of the need of strategic adjustment and development, the project operation team actively demands the downline;<br />
                    4.&nbsp; the serious problems of technology or security have not been solved in time;<br />
                    5.&nbsp; for 5 consecutive trading days after the new currency is online, the daily transaction amount is less than 50 thousand dollars;<br />
                    6.&nbsp; there is a significant deviation in information disclosure;<br />
                    7.&nbsp; abrupt bifurcations;<br />
                    8.&nbsp; other matters that are not satisfied with the continued transaction;<br />
        </div>
</div>

</c:otherwise>
  </c:choose>
<script type="text/javascript">
    function previousPage(){
      window.history.go(-1);
    }
    $(".activityMain ul li").click(function(event) {
        $(this).addClass('active').siblings().removeClass('active');
        var num=$(this).index();
        $(".activityTxtList").eq(num).addClass('active').siblings().removeClass('active');
    });
</script>
</body>
</html>
</body>
</html>
