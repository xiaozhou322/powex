<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;if (request.getServerName().equals("www.gbcax.com")){basePath="https://www.gbcax.com";}
%>
  <c:forEach items="${list}" var="v">
            <div class="table-item">
                <div class="item_con">
                    <div class="tabs userFace fl">
                        <span class="fl iconTag">${v.fname}</span>
                        <div class="uName fl">
                            <a href="javascript:;">${v.fname }</a>
                            <img src="${oss_url}/static/mobile/css/agent/images/v.png" alt="" />
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="tabs group-payway fl">
                    <c:if test="${v.openalipay == 1 }">
                        <i><img src="${oss_url}/static/mobile/css/agent/images/jfb.png" alt="" /></i>
                    </c:if>
                    <c:if test="${v.openweixin == 1 }">
                        <i><img src="${oss_url}/static/mobile/css/agent/images/wx.png" alt="" /></i>
                    </c:if>
                    <c:if test="${v.openbank == 1 }">
                        <i><img src="${oss_url}/static/mobile/css/agent/images/card.png" alt="" /></i>
                    </c:if>
                    </div>
                    <div class="tabs rate fl"><em>${v.fwithdrawrate} </em>CNY</div>
                    <div class="clear"></div>  
                </div>              
                <div class="group-btn"><span class="buyBt sellBt" onclick="sellOut(${v.fwithdrawrate},${v.fid})">卖出</span></div>
            </div>            
    </c:forEach>

<script type="text/javascript" src="${oss_url}/static/mobile/js/account.usdtrecharge.js?v=14"></script>
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

    function doSell()
    {
        var usdt_num = $('#usdt_num').val();
        var cny_num = $('#cny_num').val();
        var price = $('#price').text();
        var agent_id =  $('#agent_id').val();
        var pay_pwd =  $('#pay_pwd').val();
        var free = $('#free').text();
        var withdrawBlank = $('#openBankTypeAddr').val();
        if(usdt_num=='')
        {
            // $('.remind').text('请输入卖出数量').show();
            util.showMsg('请输入卖出数量');
            return;
        }
        if(pay_pwd=='')
        {
            // $('.remind').text('请输入交易密码').show();
            util.showMsg('请输入交易密码');
            return;
        }
        
        var param = {'usdt_num':usdt_num,'cny_num':cny_num,'price':price,'agent_id':agent_id,'pay_pwd':pay_pwd,'free':free,'withdrawBlank':withdrawBlank}
        jQuery.post('/agent/dosell.html', param, function (data) {
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
    function sellOut(price,fid)
    {
        $('.remind').text('').hide();
        $('#price').text(price);
        $('#agent_id').val(fid);
        time=setInterval (showTime, 1000);
        $(".warpForm").fadeIn(200, function() {
            $(".warpForm .close").click(function(event) { 
               $(this).parent().parent().fadeOut(100);
               setTimeout(setSecond,500);
               clearInterval(time);
            });
        });
    }
    
    function  calculateFees() {
            var amount = $("#usdt_num").val();
            var feesRate = $("#feesRate").val();
            if (amount == "") {
                amount = 0;
            }
            var feeamt = util.moneyformat(util.accMul(amount, feesRate), 2);
            if(parseFloat(feeamt)<0.5){
                feeamt = "0.5";
            }
            $("#free").html(feeamt);
            var realamount = util.moneyformat(parseFloat(amount) - parseFloat(feeamt), 2);
             $('#cny_num').val((realamount*$('#price').text()).toFixed(4));
    }

    $(".warpForm .cancle").click(function(event) {
       $(".warpForm").fadeOut(100);
        setTimeout(setSecond,500);
        clearInterval(time);
    });

    function setSecond(){
        second = 45;
       $(".countdown").html('('+second+'S)');
    }

      $("#usdt_num").on("keypress", function(event) {
        return util.VerifyKeypress(this, event, 2);
        }).on("keyup", function() {
        calculateFees();
    });
</script>

