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
                    <div class="tabs rate fl"><em>${v.fexchangerate} </em>CNY</div>
                    <div class="clear"></div>  
                </div>              
                <div class="group-btn"><span class="buyBt" onclick="buyIn(${v.fexchangerate},${v.fid})">买入</span></div>
            </div>
            </c:forEach>         

<script type="text/javascript" src="${oss_url}/static/mobile2018/js/account.usdtrecharge.js?v=14"></script>

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


     function buyIn(price,fid)
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
        var agent_id =  $('#agent_id').val();
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
        
        var param = {'usdt_num':usdt_num,'cny_num':cny_num,'price':price,'agent_id':agent_id}
        
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

    $("#cny_num").change(function(event) {
        if($('#cny_num').val()!='')
        {
          $('#usdt_num').val((parseInt($('#cny_num').val())/$('#price').text()).toFixed(4));
        }
    })
    
    $("#usdt_num").change(function(event) {
        if($('#usdt_num').val()!='')
        {
          $('#cny_num').val(($('#usdt_num').val()*$('#price').text()).toFixed(4));
        }
    })



    

    function setSecond(){
        second = 45;
       $(".countdown").html('('+second+'S)');
    }
</script>

