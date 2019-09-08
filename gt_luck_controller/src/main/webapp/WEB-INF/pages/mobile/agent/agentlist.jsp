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
<meta name = "viewport" content = "width = device-width, initial-scale = 1.0, maximum-scale = 1.0, user-scalable = 0" />
<%@include file="../comm/link.inc.jsp" %>

<link href="${oss_url}/static/mobile2018/css/phone.css?v=31" rel="stylesheet" type="text/css" />
<link href="${oss_url}/static/mobile2018/css/usdtTrade.css?v=21" rel="stylesheet" type="text/css" />

</head>
<body class="Ubody">
<%@include file="../comm/header.jsp" %>
<header class="tradeTop">  
    <i class="back toback2"></i>
    <h2 class="tit">购买USDT</h2>
</header>
<section class="usdtTrade">
    <div class="uTradeTabs fl">
        <ul>
            <li class="active1">
                <a href="/agent/agentlist.html" class="tab_nav">
                    <img class="fl" src="${oss_url}/static/mobile/css/agent/images/buyicon.png" alt="" />
                    <span class="text">
                        <h2>买入USDT</h2>
                        <p>可用：<span class="cblue">0.0475</span>USDT</p>
                    </span>
                </a>
            </li>            
            <li  class="active2">
                <a href="/agent/buyerlist.html" class="tab_nav">
                    <img class="fl" src="${oss_url}/static/mobile/css/agent/images/sellicon.png" alt="" />
                    <span class="text">
                        <h2>出售USDT</h2>
                        <p>可用：<span class="orange">0.0475</span>USDT</p>
                    </span>
                </a>
            </li>            
            <li  class="active3">
                <a href="/agent/orderlist.html" class="tab_nav">
                    <img class="fl" src="${oss_url}/static/mobile/css/agent/images/billcion.png" alt="" />
                    <span class="text">
                        <h2>订单记录</h2>
                        <p>您有<span>0</span>笔订单记录</p>
                    </span>
                </a>
            </li>
                      
            <li  class="active4">
                <a href="/agent/payaccount.html" class="tab_nav">
                    <img class="fl" src="${oss_url}/static/mobile/css/agent/images/fileicon.png" alt="" />
                    <span class="text">
                        <h2>信息资料</h2>
                        <p>请完善您的信息</p>
                    </span>
                </a>

            </li>
            <c:if test="${is_agent == 1 }">  
              <li   class="active1">
                <a href="/agent/agentinfo.html" class="tab_nav">
                    <img class="fl" src="${oss_url}/static/mobile/css/agent/images/fileicon.png" alt="" />
                    <span class="text">
                        <h2>交易设置</h2>
                        <p>设置交易规则</p>
                    </span>
                </a>
                 <li  class="active2">
                <a href="/agent/puborder.html" class="tab_nav">
                    <img class="fl" src="${oss_url}/static/mobile/css/agent/images/fileicon.png" alt="" />
                    <span class="text">
                        <h2>发布广告</h2>
                        <p>出售或购买USTD</p>
                    </span>
                </a>
                
            </li>
            <li   class="active3">
                <a href="/agent/adlist.html" class="tab_nav">
                    <img class="fl" src="${oss_url}/static/mobile/css/agent/images/fileicon.png" alt="" />
                    <span class="text">
                        <h2>我的广告</h2>
                        <p>广告列表</p>
                    </span>
                </a>
                
            </li>
            </c:if>
            <div class="clear"></div>
        </ul>
    </div>
    <div class="uTrade_r fr">
        <div class="tables-title clear">
            <span class="tit fl">商家</span>
            <span class="tit fl">支付方式</span>
            <span class="tit fl">数量</span>
            <span class="tit fl">价格</span>
        </div>
        <div class="agentCon" id="slidecontentbox">
        <c:forEach items="${list}" var="v">
            <div class="table-item">
                <div class="item_con">
                    <div class="tabs userFace fl">
                        <span class="fl iconTag">${v.fagentinfo['fname']}</span>
                        <div class="uName fl">
                            <a href="javascript:;">${v.fagentinfo['fname']}</a>
                             <c:if test="${v.fagentinfo['isaudit']==1}">
                            <img src="${oss_url}/static/mobile/css/agent/images/v.png" alt="" />
                            </c:if>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="tabs group-payway fl">
                     <c:if test="${v.fagentpayaccount['openalipay'] == 1 }">
                        <i><img src="${oss_url}/static/mobile/css/agent/images/jfb.png" alt="" /></i>
                    </c:if>
                    <c:if test="${v.fagentpayaccount['openweixin'] == 1 }">
                        <i><img src="${oss_url}/static/mobile/css/agent/images/wx.png" alt="" /></i>
                    </c:if>
                    <c:if test="${v.fagentpayaccount['openbank'] == 1 }">
                        <i><img src="${oss_url}/static/mobile/css/agent/images/card.png" alt="" /></i>
                    </c:if>
                    </div>
                    <div class="tabs rate fl"><em>${v.validnum} </em>USDT</div>
                    <div class="tabs rate fl"><em>${v.rate} </em>CNY</div>
                    
                    <div class="clear"></div>  
                </div>  

                <div class="group-btn clear">
                    <p data-v-ad45b12e="" class="gray fl"><em>交易限额</em>${v.minamount}-${v.maxamount}CNY</p>        
                    <span class="buyBt fr" onclick="buyIn(${v.rate},${v.fid})">买入</span>
                </div>
            </div>
            </c:forEach>            
        </div>
    </div>


    <div class="warpForm">
        <div class="showBox">
            <h2 class="title">
                买入USDT
                <span>请在45秒内确定，确保以当前价格下单</span>
            </h2>
            <input type="hidden" id='agentorder_id' >
            <div class="showCon">
                <p class="rateBox">交易价格（CNY/USDT）<span id="price">--</span></p>
                <p class="tr">数量USDT</p>
                <div class="tr inputBox">
                    <input type="text" class="inp" id="usdt_num" placeholder="请输入购买数量" />
<!--                     <span class="all">全部</span>
 -->                </div>
                <p class="tr">金额CNY</p>
                <div class="tr inputBox">
                    <input type="text" class="inp" id="cny_num" placeholder="0.0000" />
<!--                     <span class="all">全部</span>
 -->                </div>
                <span class="remind colorRed">交易金额不能为零</span>
                <div class="btns">
                    <input type="button" class="button active" value="确定"  onclick="doBuy()"/>
                    <span class="button cancle">
                        <em class="countdown" id="countdown">(45S)</em>
                        <span class="em">自动取消</span>
                    </span>
                </div>
             </div>
        </div> 
    </div>
</section>
<input type="hidden" id="pageCount" value="${totalPage}">
<input type="hidden" id="currentPage" value="${currentPage}">
<div id="slide_loading_btn"  onclick="slideLoadMoreInfo()" style=" <c:if test="${totalPage==1 }">display:none</c:if>"><spring:message code="m.security.moremsg" /></div>

<%@include file="../comm/footer.jsp" %>	
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/account.usdtrecharge.js?v=14"></script>
<script type="text/javascript">
$(".backIcon").click(function(){
    window.history.go(-1);
});


var pageCount = parseInt($('#pageCount').val());
var currentPage = parseInt($('#currentPage').val());
var slideBtn = $('#slide_loading_btn');
$(document).ready(function(){
    $(window).bind("scroll",slideLoad);
    if(currentPage==pageCount&&pageCount>1){  $(slideBtn).text(language["load.no.msg"]).show(); }
});
function slideLoad(){
    var scrollbar_top = document.documentElement.scrollTop  || document.body.scrollTop
    var bottomHeight = 10;
    var screenHeight = document.documentElement.clientHeight || document.body.clientHeight
    var page_bottom_pos = scrollbar_top+screenHeight+bottomHeight
    if (page_bottom_pos >= document.body.scrollHeight){
        slideLoadMoreInfo();
    }

}
function  slideLoadMoreInfo(){
    var selectId = $('#select option:selected'); 
    
    $(window).unbind("scroll",slideLoad);
    if(currentPage>0&&currentPage<pageCount)
    {
        var url = window.location.href;
        var index = layer.load(2);
        $(slideBtn).text(language["load.ing.msg"]).show();
    
        var object = {};
        object.currentPage = currentPage+1;
        $.get(url,object,function(html){
            if($.trim(html)!='')
            {
            
                $('#slidecontentbox').append(html);
                $('#currentPage').val(currentPage+1);
                $(slideBtn).text(language["load.more.msg"]).hide();
                $(window).bind("scroll",slideLoad);
                currentPage = parseInt($('#currentPage').val());
                if(currentPage==pageCount)
                {
                    $(slideBtn).text(language["load.no.msg"]).show();
                    $(window).unbind("scroll",slideLoad);
                }
            }
            else
            {
                $(slideBtn).text(language["load.no.msg"]).show();
                $(window).unbind("scroll",slideLoad);
            }
            layer.close(index);
        })
    }
    else
    {
        $(slideBtn).text(language["load.no.msg"]).fadeOut(3000);
        $(window).unbind("scroll",slideLoad);
    }
}
</script>
<script type="text/javascript">
    var time;   
    var second=45;
    function showTime(){
        if(second <= 0)
        {
        $(".warpForm").fadeOut(200);
        clearInterval(time);
        setTimeout(setSecond,500);
        }else{
            second--;
            $(".countdown").html('('+second+'S)');
        }
    }


    // $(".table-item .buyBt").click(function(event) {
    //     time=setInterval (showTime, 1000);
    //     $(".warpForm").fadeIn(200);
    // });

     function buyIn(price,fid)
    {
        $('.remind').text('').hide();
        $('#price').text(price);
        $('#agentorder_id').val(fid);
        time=setInterval (showTime, 1000);
        $(".warpForm").fadeIn(200, function() {
            $(".warpForm .close").click(function(event) { 
               $(this).parent().parent().fadeOut(100);
               setTimeout(setSecond,500);
               clearInterval(time);
            });
        });
    }


    $('#cny_num').focus(function(event) {
        $(".showBox").css('margin-top', '-80%');
         console.log($(window).height())
    });

    $('#cny_num').blur(function(event) {
        $(".showBox").css('margin-top', '-56%');
    });

    $(".warpForm .cancle").click(function(event) {
       $(".warpForm").fadeOut(100);
        setTimeout(setSecond,500);
        clearInterval(time);
 
    });
    
    function doBuy()
    {
        var usdt_num = $('#usdt_num').val();
        var cny_num = $('#cny_num').val();
        var price = $('#price').text();
        var agentorder_id =  $('#agentorder_id').val();
        if(usdt_num=='')
        {
            // $('.remind').text('请输入购买数量').show();
            util.showMsg('请输入购买数量');
            return;
        }
        if(cny_num=='')
        {
            // $('.remind').text('请输入购买金额').show();
            util.showMsg('请输入购买金额');
            return;
        }
        
        var param = {'usdt_num':usdt_num,'cny_num':cny_num,'price':price,'agentorder_id':agentorder_id}
        
        jQuery.post('/agent/dobuy.html', param, function (data) {
            $(".warpForm").fadeOut(100);
            setTimeout(setSecond,500);
            clearInterval(time);
            if (data.code == -1) {
                util.layerAlert("", data.msg, 2);
                
            } else {
                //util.layerAlert("", data.msg, 1);
                window.location.href='/agent/orderdetail.html?order_id='+data.operid;
                
            }
        }, "json");
    }

    $("#cny_num").on("input",function(){
       if($('#cny_num').val()!='')
        {
          $('#usdt_num').val((parseFloat($('#cny_num').val())/$('#price').text()).toFixed(4));
        }else{
            $('#usdt_num').val(parseFloat(0.0000).toFixed(4));
        }
    });


    $("#usdt_num").on("input",function(){
       if($('#usdt_num').val()!='')
        {
          $('#cny_num').val(($('#usdt_num').val()*$('#price').text()).toFixed(4));
        }else{
            $('#cny_num').val(parseFloat(0.0000).toFixed(4));
        }
    });
    

    function setSecond(){
        second = 45;
       $(".countdown").html('('+second+'S)');
    }


</script>
</body>
</html>
