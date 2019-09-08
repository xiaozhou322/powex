<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>

<!doctype html>
<html>
<head>
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp"%>
<link rel="stylesheet" href="${oss_url}/static/front/css/index/index.css?v=20171026105823.css?v=20181126201750" type="text/css"></link>
<link href="${oss_url}/static/front/css/index/common.css?v=20171026105823.css?v=20181126201750" rel="stylesheet" type="text/css" />
<link href="${oss_url}/static/front/css/index/main.css?v=20171026105823.css?v=20181126201750" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${oss_url}/static/front/js/index/main.js?v=20171026105823.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/index/jquery-1.11.2.min.js?v=20171026105823.js?v=20181126201750"></script>
</head>
<style type="text/css">
    .activity{margin-top:70px;}
    .activity img{width:100%; height:auto; display:block; }
    .activityMain{width:100%; padding-bottom:300px; background:url(/static/front/images/index/activityBg.jpg) no-repeat center 0; background-size:100% 100%;}
    .auto{width:1154px; height:auto; margin:0 auto; padding-top:108px; font-family:"微软雅黑";}
    .auto dl{width:100%; height:auto; margin-bottom:124px; overflow:hidden; position: relative;}
    .activity_tit{width:249px; height:360px; background:url(/static/front/images/index/sbg.png) no-repeat 0 0; background-size:100% 100%; position: relative;padding:160px 0 0 30px;}
    .activity_tit:before{content:""; width:72px; height:4px; background:#ffbd50; position:absolute; left:23px; top:50px; }
    .activity_tit h1 img{display:block;}
    .activity_txt{width:938px; height:auto; background:rgba(255,255,255,0.1); position: absolute; left:215px; top:26px; bottom:26px; z-index:999; font-size:16px; line-height:28px; color:#ffbd50; padding:74px 62px 0 87px;}
    .activity_tit02{height:440px; padding-top:200px;}
    .activity_tit03{height:460px; padding-top:210px;}
    .activity_txt02{padding:30px 66px 0 22px;}
    .activity_txt03{line-height:34px; padding-top:35px;}
    .txt_r{width:500px; padding-top:20px;}
    .txt_r p{position:relative; padding-left:28px;}
    .cfff{color:#fff;}
    .cffbd50{color:#ffbd50;}
    .c72bbc7{color:#72bbc7;}
    .c00c1ff{color:#00c1ff;}
    .ceb603f{color:#eb603f;}
    .cf49c13{color:#f49c13;}
    .c88ba73{color:#88ba73;}
    .txt_r .point{width:12px; height:12px; border-radius:50%; position: absolute; left:0; top:8px;}
    .clineH{line-height:40px;}
    .pdt{padding-top:55px;}
    .pindent{text-indent:2em; margin-bottom:0;}
</style>
<body>
<%@include file="../comm/header.jsp"%>
  <section>
        <div class="activity">       
            <img src="${oss_url}/static/front/images/index/activity.jpg" alt="" />       
        </div>
        <div class="activityMain">
            <div class="auto">
                <dl>
                    <dt class="activity_tit">
                        <h1><img src="${oss_url}/static/front/images/index/tit01.png" height="36"  alt="G币介绍" /></h1>
                    </dt>
                    <dd class="activity_txt">
                        G Token（简称GT，GT）将作为GBCAX发行的一种基于以太坊ERC20标准的代币，每一枚平台上的GT均享有代币分红权。GBCAX平台将每天的交易手续费现金收入作为代币分红。GBCAX将发行总量1亿个GT，数量永不增加。<br />
                        GT是基于以太坊智能合约的代币。从创世发行开始，智能合约一共将进行200次释放，每次释放的时间间隔为24小时。所有的GT通过释放的形式产生，合约每天释放50万枚GT，总共释放200天。
                    </dd>
                </dl>            
                <dl>
                    <dt class="activity_tit activity_tit02">
                        <h1><img src="${oss_url}/static/front/images/index/tit02.png" height="36"  alt="分配机制" /></h1>
                    </dt>
                    <dd class="activity_txt activity_txt02">
                        <div class="txt_l fl"><img src="${oss_url}/static/front/images/index/dataPic.png" width="336" alt="" /></div>
                        <div class="txt_r fr">
                            <h3 class="cfff">G币分配图</h3>    
                            <p class="p_01 c72bbc7"> <i class="point" style="background:#72bbc7;"></i> GBCAX平台方分得45%GT奖励（永久锁仓：用于平台技术研发    
                                       引进人才、项目运营、回购G币并销毁等）。</p>
                            <p class="p_02 c00c1ff"> <i class="point" style="background:#00c1ff;"></i> GBCAX项目方分得5%GT奖励（冻结一年：用于项目市场推广）。</p>
                            <p class="p_03 ceb603f"> <i class="point" style="background:#eb603f;"></i> 预售分得20%GT奖励（逐步释放：用于发展平台初期种子用户）。</p>
                            <p class="p_04 cf49c13"> <i class="point" style="background:#f49c13;"></i> 交易分得20%GT奖励（用户：交易奖励的70%；经纪人：交易奖励30%）。</p>                        
                            <p class="p_05 c88ba73"> <i class="point" style="background:#88ba73;"></i> 抢购分得10%GT奖励（用于一级市场进行抢购）。</p>
                        </div>
                        <div class="clear"></div>
                    </dd>
                </dl>
                <dl>
                    <dt class="activity_tit">
                        <h1><img src="${oss_url}/static/front/images/index/tit03.png" height="36"  alt="释放规则" /></h1>
                    </dt>
                    <dd class="activity_txt" style="padding-top:70px;">
                        所有的G币通过释放的形式产生，每天释放50万枚，总共释放200天。<br />
                        GBCAX平台运行：每日释放 225000 枚GTa <br />
                        GBCAX项目推广：每日释放 25000 枚GT <br />  
                        配售：每日释放 50000 枚GT <br /> 
                        抢购：每日释放 50000 枚GT <br />
                        交易奖励：每日释放 100000 枚GT <br />
                    </dd>
                </dl>            
                <dl>
                    <dt class="activity_tit">
                        <h1><img src="${oss_url}/static/front/images/index/tit04.png" height="36"  alt="收益共享" /></h1>
                    </dt>
                    <dd class="activity_txt cfff"style="padding-top:60px;">
                        （1）每日平台交易手续费现金收入的100%将按照GT持有比例，全部分配给GT持有者。<br />
                        （2）平台将于每日23:59:59对GT持有的额度进行快照。当日奖励的GT将于次日15:00到账，故用户当日<br />  
                             <p class="pindent"> &nbsp; 可分红GT=快照数据。分红将于次日发放，以USDT的形式发放至各持有者账户。</p>
                        （3）分红公式：<br />
                            <p class="pindent">平台方待分红收入 = 交易手续费现金收入</p>
                            <p class="pindent">持币用户分红收入=待分红收入*(个人GT持有数量/平台GT已释放数量)</p>

                    </dd>
                </dl>
                <dl>
                    <dt class="activity_tit">
                        <h1><img src="${oss_url}/static/front/images/index/tit05.png" height="36"  alt="交易奖励" /></h1>
                    </dt>
                    <dd class="activity_txt cfff clineH pdt">
                        （1）奖励G币总数：20,000,000枚；<br />

                        （2）奖励周期：200天；<br />

                        （3）每日奖励数量：100,000枚；<br />

                        （4）统计周期：当日0:00:00-23:59:59；<br />

                        （5）发放时间：次日下午17:00 <br />    
                    </dd>
                </dl>
                <dl>
                    <dt class="activity_tit activity_tit03">
                        <h1><img src="${oss_url}/static/front/images/index/tit06.png" height="36"  alt="奖励机制" /></h1>
                    </dt>
                    <dd class="activity_txt cfff pdt">
                        （1）平台每天会对交易情况进行统计，统计周期为当日0:00:00-23:59:59；平台将按照统计数据，于次日下<br /> 
                         <p class="pindent">午17:00将奖励的G币发放到对应用户账户。</p>
                        （2）用户通过交易行为获取G币，但获取的G币并非100%归用户所有
                           <p class="pindent cffbd50"> ①用户分得70%的G币奖励</p>     
                           <p class="pindent cffbd50"> ②经纪人分得30%的G币奖励 </p>   
                        （3）奖励公式：
                          <p class="pindent">当天交易奖励G币：(个人交易额 / 平台总交易额)*100000*70%</p>       
                          <p class="pindent">当天经纪人奖励G币：(好友总交易额 / 平台总交易额)*100000*30%</p>      
                          <p class="cffbd50">注：经纪人：客户A在注册时若使用了客户B的邀请链接，则B为A的经纪人，成为经纪人必须通过KYC1和KYC2认证</p>
                    </dd>
                </dl> 
                <dl>
                    <dt class="activity_tit activity_tit03">
                        <h1><img src="${oss_url}/static/front/images/index/tit07.png" height="36"  alt="抢购规则" /></h1>
                    </dt>
                    <dd class="activity_txt activity_txt03 cfff">
                        （1）每日可抢购GT数量：50,000枚；<br />
                        （2）每日抢购时间：11:00--12:00；<br />
                        （3）活动周期：200天；<br />
                        （4）抢购期间GT抢购价格以页面价格为准；<br />
                        （5）抢购份数：仅限一份；单次购买数量：100GT/次；<br />
                        （6）抢购成功GT可进入二级市场进行交易，也可长期持有与平台收益共享；<br />
                        （7）每名用户一天仅限一次抢购机会；<br />
                        （8）抢购期间请保证账户资金足额，以便抢购；<br />
                        （9）每天抢购结束后，系统会从抢购记录中随机抽取中签用户，并按照GT购买价格扣除相应价值的USDT，<br />  
                         <p class="pindent">如果未中签的用户，用户使用的USDT将原路返还至账户。</p> 
                    </dd>
                </dl>         
            </div>
        </div>
  </section> 
</body>
</html>