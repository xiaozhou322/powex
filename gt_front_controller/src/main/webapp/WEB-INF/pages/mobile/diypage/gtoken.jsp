<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>

<!doctype html>
<html>
<head>
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
 <meta name = "viewport" content = "width = device-width, user-scalable = no,initial-scale=0.5,maximum-scale=0.5,minimum-scale=0.5">
<%@include file="../comm/link.inc.jsp"%>
</head>
<style type="text/css">
    .activity{margin-top:1.5rem;}
    .activity img{width:100%; height:auto; display:block; }
    .activityMain{width:100%; min-height:450px; padding:2px 2px 30px; overflow-x:hidden; background:url(/static/mobile2018/images/conBg.jpg) no-repeat center 0; background-size:100% 100%;}
    .activityMain ul{width:110%;}
    .activityMain ul li{width:30%; height:1.4rem; line-height: 1.4rem; text-align:center; color:#fff; background:url(/static/mobile2018/images/li_bg.png) no-repeat center; background-size:100% 100%; margin:0 0.5% 0.5% 0; font-size:15px; font-weight:bold;}
    .activityMain ul li.active{background:url(/static/mobile2018/images/activeBg.png) no-repeat center; background-size:100% 100%; color:#161511;}
    .W24{width:22.3%!important;}
    .cfff{color:#fff;}
    .cffbd50{color:#ffbd50;}
    .c72bbc7{color:#72bbc7;}
    .c00c1ff{color:#00c1ff;}
    .ceb603f{color:#eb603f;}
    .cf49c13{color:#f49c13;}
    .c88ba73{color:#88ba73;}
    .point{width:12px; height:12px; border-radius:50%; position: absolute; left:0; top:8px;}
    .pindent{text-indent:2em;}
    .activityTxtList{width:98%; height:auto; margin:0.8rem auto 0.5rem; background:url(/static/mobile2018/images/borderBg.png) no-repeat center; background-size:96% 100%; padding:40px 35px;font-size:0.5rem; line-height:1rem; display:none;}
    .activityTxtList.active{display:block;}
    
    .activity_tit02 h3{text-align:center; font-size:16px;}
    .activity_tit02 img{width:100px; margin:0.6rem auto; display:block;}
    .activity_tit02 .txt p{font-size:14px; line-height:22px; position: relative; padding-left:20px;}
    .txt .point{width:10px; height:10px; border-radius:50%; position: absolute; left:0; top:8px;}
</style>    

<body>

<%@include file="../comm/header.jsp"%>
<header class="header backHeader"> 
    <i class="backIcon" onclick="previousPage();"></i>
    <h2 class="tit">GB介绍详情</h2>
</header>
<section>
    <div class="activity">
        <img src="${oss_url}/static/mobile2018/images/activitymobile2018.jpg" alt="" />
    </div>
    <div class="activityMain">
        <ul>
            <li class="fl W24 active">收益共享</li>
            <li class="fl W24">交易奖励</li>
            <li class="fl W24">奖励机制</li>
            <li class="fl W24">抢购规则</li>
            <div class="clear"></div>
        </ul>
        <div class="activityTxtList cffbd50 active">            
            G Token（简称GT，G币）将作为GBCAX发行的一种基于以太坊ERC20标准的代币，每一枚平台上的GT均享有代币分红权。GBCAX平台将每天的交易手续费现金收入作为代币收益共享。GBCAX将发行总量1亿个GT，数量永不增加。
            GT是基于以太坊智能合约的代币。从创世发行开始，智能合约一共将进行200次释放，每次释放的时间间隔为24小时。所有的GT通过释放的形式产生，合约每天释放50万枚GT，总共释放200天。
        </div>
        <div class="activityTxtList activity_tit02 "> 
            <h3 class="cfff">GT分配图</h3>    
            <img src="${oss_url}/static/mobile2018/images/dataPic.png" alt="" />
            <div class="txt">
                <p class="p_01 c72bbc7"> <i class="point" style="background:#72bbc7;"></i> GBCAX平台方分得45%GT奖励（永久锁仓：用于平台技术研发    
                           引进人才、项目运营、回购GT并销毁等）。</p>
                <p class="p_02 c00c1ff"> <i class="point" style="background:#00c1ff;"></i> GBCAX项目方分得5%GT奖励（冻结一年：用于项目市场推广）。</p>
                <p class="p_03 ceb603f"> <i class="point" style="background:#eb603f;"></i> 预售分得20%GT奖励（逐步释放：用于发展平台初期种子用户）。</p>
                <p class="p_04 cf49c13"> <i class="point" style="background:#f49c13;"></i> 交易分得20%GT奖励（用户：交易奖励的70%；经纪人：交易奖励30%）。</p>                        
                <p class="p_05 c88ba73"> <i class="point" style="background:#88ba73;"></i> 抢购分得10%GT奖励（用于一级市场进行抢购）。</p>
            </div>
        </div>
        <div class="activityTxtList cfff">            
            所有的GT通过释放的形式产生，每天释放50万枚，总共释放200天。<br />
            GBCAX平台运行：每日释放 225,000 枚GT <br />
            GBCAX项目推广：每日释放 25,000 枚GT <br />
            配售：每日释放 100,000 枚GT <br />
            抢购：每日释放 50,000 枚GT <br />
            交易奖励：每日释放 100,000 枚GT <br />
        </div>
        <div class="activityTxtList cfff ">            
            （1）每日平台交易手续费现金收入的100%将按照GT有效持有比例，全部分配给GT有效持有者。<br />
            （2）平台将于每日23:59:59对GT有效持有的额度进行快照。当日奖励的USDT将于次日15:00到账，故用户当日可
                     分红GT=快照数据。分红将于次日发放，以USDT的形式发放至各持有者账户。<br />
            （3）分红公式：
             <p class="pindent">平台方待分红收入 = 交易手续费现金收入</p>
             <p class="pindent">持币用户分红收入 = 待分红收入*(个人有效GT持有数量 / 平台已发行GT总数量)</p>
             <p class="pindent">有效持有数量：可用非冻结状态和系统奖励锁仓增值的GT。挂单冻结的GT为非有效持有，不参与收益共享。</p>
        </div>
        <div class="activityTxtList cfff">            
            （1）奖励GT总数：20,000,000枚； <br />
            （2）奖励周期：200天； <br />
            （3）每日奖励数量：100,000枚； <br />
            （4）统计周期：当日0:00:00-23:59:59； <br />
            （5）发放时间：次日下午17:00 。<br />
        </div>        
        <div class="activityTxtList cfff">            
            （1）平台每天会对交易情况进行统计，统计周期为当日0:00:00-23:59:59；平台将按照统计数据，于次日下
            午17:00将奖励的GT发放到对应用户账户。<br />
            （2）用户通过交易行为获取GT，但获取的GT并非100%归用户所有
            <p class="pindent cffbd50">①用户分得70%的GT奖励</p> 
            <p class="pindent cffbd50">②经纪人分得30%的GT奖励</p> 
            （3）奖励公式：<br />
            <p class="pindent">当天交易奖励GT：(个人交易额 / 平台总交易额)*100000*70%</p>
            <p class="pindent">当天经纪人奖励GT：(好友总交易额 / 平台总交易额)*100000*30%</p>
            <p class="cffbd50">注：经纪人：客户A在注册时若使用了客户B的邀请链接，则B为A的经纪人，成为经纪人必须通过KYC1和KYC2认证</p>
        </div>
        <div class="activityTxtList cfff">            
            （1）每日可抢购GT数量：50,000枚；<br />
            （2）每日抢购时间：11:00--12:00；<br />
            （3）活动周期：200天；<br />
            （4）抢购期间GT抢购价格以页面价格为准；<br />
            （5）抢购份数：仅限一份；单次购买数量：100GT/次；<br />
            （6）抢购成功GT可进入二级市场进行交易，也可长期持有与平台收益共享；<br />
            （7）每名用户一天仅限一次抢购机会；<br />
            （8）抢购期间请保证账户资金足额，以便抢购；<br />
            （9）每天抢购结束后，系统会从抢购记录中随机抽取中签用户，并按照GT购买价格扣除相应价值的USDT；<br />
            如果未中签的用户，用户使用的USDT将原路返还至账户。
        </div>
</div>

</section>

<script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
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
