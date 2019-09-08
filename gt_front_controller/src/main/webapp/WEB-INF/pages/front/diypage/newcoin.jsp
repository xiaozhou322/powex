<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>

<!doctype html>
<html>
<head>
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp"%>
<link rel="stylesheet" href="${oss_url}/static/front/css/index/index.css?v=20171228101259.css?v=20181126201750" type="text/css"></link>
<link href="${oss_url}/static/front/css/index/common.css?v=20171228101259.css?v=20181126201750" rel="stylesheet" type="text/css" />
<link href="${oss_url}/static/front/css/index/main.css?v=20171228101259.css?v=20181126201750" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${oss_url}/static/front/js/index/main.js?v=20171228101259.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/index/jquery-1.11.2.min.js?v=20171228101259.js?v=20181126201750"></script>
</head>
<style type="text/css">
    .activity{margin-top:70px;}
    .activity img{width:100%; height:auto; display:block; }
    .activityMain{width:100%; padding-bottom:40px; background:url(/static/front/images/diypages/newcoin/activityBg.jpg) no-repeat center 0; background-size:100% 100%;}
    .auto{width:1154px; height:auto; margin:0 auto; padding-top:108px; font-family:"微软雅黑";}
    .auto dl{width:100%; height:auto; margin-bottom:124px; overflow:hidden; position: relative;}
    .activity_tit{width:249px; height:360px; background:url(/static/front/images/diypages/newcoin/sbg.png) no-repeat 0 0; background-size:100% 100%; position: relative;padding:160px 0 0 30px;}
    .activity_tit:before{content:""; width:74px; height:7px; background:#7ecef4; position:absolute; left:23px; top:50px; }
    .activity_tit h1 img{display:block;}
    .activity_txt{width:938px; height:auto; background:rgba(255,255,255,0.1); position: absolute; left:215px; top:26px; bottom:26px; z-index:999; font-size:18px; line-height:36px;padding:38px 20px 36px 87px; letter-spacing:-1px; color:#f3ab0d;}
    .activity_tit02{height:360px; padding-top:180px;}
    .activity_tit03{height:280px; padding-top:130px;}
    .activity_tit04{height:500px; padding-top:220px;}
    .activity_txt02{padding:30px 66px 0 22px;}
    .activity_txt03{line-height:34px; padding-top:35px;}
    .txt_r{width:500px; padding-top:40px;}
    .txt_r p{position:relative; padding-left:28px; margin-bottom:0;}
    .cfff{color:#fff;}
    .cgray{color:#e4edee;}
    .clightBlue{color:#6eb4bf;}
/*    .cffbd50{color:#ffbd50;}
    .c72bbc7{color:#72bbc7;}
    .c00c1ff{color:#00c1ff;}
    .ceb603f{color:#eb603f;}
    .cf49c13{color:#f49c13;}
    .c88ba73{color:#88ba73;}*/
    .cyellow{color:#f3ab0d;}
    .txt_r .point{width:12px; height:12px; border-radius:50%; position: absolute; left:0; top:8px;}
    .clineH{line-height:40px;}
    .pdt{padding-top:55px;}
    .pindent{text-indent:2em;}
    .txtmgb{margin-bottom:10px;}
    .txtTag{text-align:center; font-size:16px;}
    .code{position: absolute; right:110px; top:114px;}
    
    
</style>
<body>
<%@include file="../comm/header.jsp"%>
<c:choose>
<c:when test="${pageContext.response.locale eq 'zh_CN' }">
  <section>
    <div class="activity">       
        <img src="${oss_url}/static/front/images/diypages/newcoin/activity.jpg" alt="" />       
    </div>
    <div class="activityMain">
        <div class="auto">
            <dl>
                <dt class="activity_tit">
                    <h1><img src="${oss_url}/static/front/images/diypages/newcoin/tit01.png" height="36"  alt="申请规则" /></h1>
                </dt>
                <dd class="activity_txt cyellow">
                    <p class="cgray txtmgb">所有上线交易的项目需要满足如下条件，包含但不限于：</p>
                    <div class="txtmgb">
                        （1）强有力的团队或社区维护；<br />
                        （2）有实际技术支撑或有实际应用的项目；<br />
                        （3）项目无政策风险并且达到专业和合规要求；<br />
                        （4）能真实及时披露项目信息包含项目白皮书，定期发展及进度报告；<br />
                    </div>
                    <p class="clightBlue">上币申请请发送项目有关文件至邮箱：bd@gbcax.com 或官方微信号</p>
                </dd>
                <div class="code"><img src="${oss_url}/static/front/images/diypages/newcoin/code.png" width="120" alt="" /></div>
            </dl>            
            <dl>
                <dt class="activity_tit activity_tit02">
                    <h1><img src="${oss_url}/static/front/images/diypages/newcoin/tit02.png" height="36"  alt="扶持政策" /></h1>
                </dt>
                <dd class="activity_txt cyellow">
                    （1）新币上线G网将免上币费用；<br />
                    （2）G网将提供首页banner和官方公告进行宣传推广；<br />
                    （3）扶持期：自上线当日起，90天；<br />
                    （4）新币通过申请之后将于2个工作日内上线交易；<br />
                    （5）项目方可提供部分糖果进行空投给平台GT持有用户和活动推广，额度不限；<br />
                    （6）上线的项目，不少于50名新注册的用户。新注册用户指：新币上线之后注册的用户，以上币时间为准；                
                </dd>
            </dl>
            <dl>
                <dt class="activity_tit activity_tit03">
                    <h1><img src="${oss_url}/static/front/images/diypages/newcoin/tit03.png" height="36"  alt="交易门槛" /></h1>
                </dt>
                <dd class="activity_txt">
                    （1）为确保给用户及项目方一如既往提供稳定、有序以及优质的服务，新币扶持期结束后将统计扶持
                    期间总交易额，扶持期内日均交易额需达到5万美金；<br />
                    （2）新币上线将开放创新区BTC和ETH交易对；<br />
                    （3）自新币扶持期内若日交易额达100万美金以上，则开放USDT交易对；<br />
                </dd>
            </dl>            
            <dl>
                <dt class="activity_tit activity_tit04">
                    <h1><img src="${oss_url}/static/front/images/diypages/newcoin/tit04.png" alt="下线币种说明" /></h1>
                </dt>
                <dd class="activity_txt">
                    <p class="cgray txtmgb">为保护投资者利益，G网保留项目下线或继续支持项目在平台上交易的权利，<br />
                    项目方如果触发如下条件，我们会公告通知交易下线，包含但不限于： 
                    </p>
                    <p>
                    （1）项目团队解散；<br /> 
                    （2）项目方面临重大法律问题；<br />    
                    （3）由于战略调整和发展需要，项目运营团队主动要求下线；<br />   
                    （4）严重的技术或安全问题没有及时得到解决；<br /> 
                    （5）自新币扶持期过后连续5个交易日，每日交易额小于5万美金；<br />  
                    （6）信息披露出现重大偏差；<br /> 
                    （7）突然出现分叉；<br /> 
                    （8）不满足继续交易的其他事项；<br />   
                    </p>
                </dd>
            </dl>
            <div class="txtTag cgray">
            *G网会对决定下线的项目提前5天发出下线公告，用户有30天的期限从钱包中移出资产<br />
            *本活动解释权归G网所有，如有其它问题，请联系在线客服
            </div>        
        </div>
    </div>
  </section>
  </c:when>
  <c:otherwise>
  	<style>
  	.pdr{padding:0 220px 0 0;}
    .activity_tit{height:400px;}
    .activity_txt{line-height:30px;}
    .activity_tit04{height:530px;}
    .txtTag{width: 1154px;margin: 0 auto;padding: 0 220px;line-height: 24px; font-size:15px;}
	</style>
  	<section>
    <div class="activity">       
        <img src="${oss_url}/static/front/images/diypages/newcoinen/activity.jpg" alt="" />       
    </div>
    <div class="activityMain">
        <div class="auto">
            <dl>
                <dt class="activity_tit">
                    <h1><img src="${oss_url}/static/front/images/diypages/newcoinen/tit01.png" height=""  alt="Application Rules" /></h1>
                </dt>
                <dd class="activity_txt cyellow">
                    <p class="cgray txtmgb">All the on-line trading items need to meet the following conditions, including but not limited to：</p>
                    <div class="txtmgb pdr">
                        1.&nbsp; strong team or community maintenance;<br />
                        2.&nbsp; projects with practical technical support or practical application;<br />
                        3.&nbsp; the project has no policy risk and meets the requirements of professional and compliance.&nbsp;<br />
                        4.&nbsp; the real and timely disclosure of project information contains the project white paper, regular development and progress report;<br />
                    </div>
                    <p class="clightBlue">Please send the project documents to the mailbox: bd@gbcax.com;</p>
                </dd>
                <div class="code"><img src="${oss_url}/static/front/images/diypages/newcoinen/code.png" width="120" alt="" /></div>
            </dl>            
            <dl>
                <dt class="activity_tit activity_tit02">
                    <h1><img src="${oss_url}/static/front/images/diypages/newcoinen/tit02.png" height=""  alt="Support Policy" /></h1>
                </dt>
                <dd class="activity_txt cyellow">
                    1.&nbsp; the new currency GBCAX will be exempt from the cost of the money;<br />
                    2.&nbsp; GBCAX will provide the home page banner and official announcement for publicity and promotion;<br />
                    3.&nbsp; support period: from the day of the line, 90 days;<br />
                    4.&nbsp; the new currency will be traded within 2 working days after the application；<br />
                    5.&nbsp; the project can provide part of the candy for air drop to the platform GT to hold users and activities to promote, the amount is unlimited;<br />
                    6.&nbsp; on the line of the project, no less than 50 newly registered users. The new registered user refers to the registered users after the new currency is online, and the time of the above currency is <accurate class="br"></accurate>

                </dd>
            </dl>
            <dl>
                <dt class="activity_tit activity_tit03">
                    <h1><img src="${oss_url}/static/front/images/diypages/newcoinen/tit03.png" height=""  alt="Transaction Threshold" /></h1>
                </dt>
                <dd class="activity_txt">
                    1.&nbsp;  in order to ensure stable and orderly service to users and project providers, the total transaction volume during the support period and the average daily transaction volume during the support period will reach 50 thousand dollars after the support period of the new currency;<br />
                    2.&nbsp;  new currency line will open the Open Innovation Zone BTC and ETH trading pairs;<br />
                    3.&nbsp;  in the support period of the new coin, if the daily transaction amount to more than 1 million USDT, then open the USDT transaction;<br />

                </dd>
            </dl>            
            <dl>
                <dt class="activity_tit activity_tit04">
                    <h1><img src="${oss_url}/static/front/images/diypages/newcoinen/tit04.png" alt="Removal Policy" /></h1>
                </dt>
                <dd class="activity_txt">
                    <p class="cgray txtmgb">To protect the interests of investors, to retain or continue to support the project GBCAX project in trading platform on the right side of the project if the trigger conditions are as follows, we will notice the transaction offline, including but not limited to: 
                    </p>
                    <p>
                    1.&nbsp; project team disbanded;<br />
                    2.&nbsp; project faces major legal issues;<br />
                    3.&nbsp; as a result of the need of strategic adjustment and development, the project operation team actively demands the downline;<br />
                    4.&nbsp; the serious problems of technology or security have not been solved in time;<br />
                    5.&nbsp; for 5 consecutive trading days after the new currency is online, the daily transaction amount is less than 50 thousand dollars;<br />
                    6.&nbsp; there is a significant deviation in information disclosure;<br />
                    7.&nbsp; abrupt bifurcations;<br />
                    8.&nbsp; other matters that are not satisfied with the continued transaction;<br />

                    </p>
                </dd>
            </dl>
            <div class="txtTag cgray">
            *GBCAX will make a downline announcement 5 days ahead of the project to determine the downline, and the user has 30 days to remove the assets from the wallet.<br />
            *The right to explain this activity belongs to GBCAX. If there are other questions, please contact online customer service.
            </div>        
        </div>
    </div>
  </section>
  </c:otherwise>
  </c:choose>	  
</body>
</html>