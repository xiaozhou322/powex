<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>

<!doctype html>
<html>
<head>
    <%@ include file="../comm/basepath.jsp"%>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name = "viewport" content = "width = device-width, user-scalable = no,initial-scale=0.5,maximum-scale=0.5,minimum-scale=0.5">
  
    <%@include file="../comm/link.inc.jsp" %>
    <link rel="stylesheet" type="text/css" href="${oss_url}/static/mobile2018/css/LCalendar.css?v=20181126201750" />
    <title><spring:message code="financial.accrecord" /></title>
    <style type="text/css">
    .tradeTopas{color:#fff;}
        @font-face {
           font-family: 'PingFangMedium';
           src: url('/static/mobile2018/fonts/PingFangMedium.ttf'); 
        } 
        .choiceBtn {
        	width: 235%;
        }
        .assets #slidecontentbox li{
        	padding: 0.2rem 0.1rem;
        }

        .tradeTop{
            background: #fff;
    		margin-bottom: 0.2rem;
        }
        .tradeList_tit .textC, .ul_con li .textC{
            width: 20%
        }
        .tradeList_tit span, .ul_con li em{
            width: 26%;
        }
        
        /* 点击下拉内容 */
        .showmsg{
       		 clear: both;
		    overflow: hidden;
        }
        .entrustCon h3{
                color: #999999;
    font-size: 0.24rem;
    padding-top: 0.2rem;
    padding-left: 0.18rem;
    border-top: 1px solid #f5f7ff;
		    	}
        .entUl{background: #F9F9F9;color: #999999;padding-bottom: 0.3rem;border-radius: 0.08rem;}
        .assets .ul_con li .entUl li{
            border-bottom: 1px solid #C0C0C0;
    		padding-bottom: 0.33rem;
  		    margin: 0 0.3rem;
        }
        .entUl li .entUlem{    
        float: right;
    color: #333333;
    font-size: 0.26rem;}
    .recordType{    clear: both;
    text-align: center;
    height: 0.8rem;
    line-height: 0.8rem;
    font-size: 0.3rem;
    background: #fff;
    color: #8b8b8b;
    border-bottom: 0.02rem solid #f5f7ff;}
.recordType li{
	    float: left;
    width: 1.6rem;
    text-align: center;
    cursor: pointer;
}
.recordactive {
        color: #506FC8;
    border-bottom: 4px solid #506FC8;
}
	</style>
</head>
<body>

    <%@include file="../comm/header.jsp" %>
    <header class="tradeTop">
    	<a class="tradeTopas" href="/user/security.html">
	        <span class="back toback2"></span>
	        <span class="tit"><spring:message code="newuser.financial.records" /></span>
        </a>
    </header>
    <div class="trade assets">
<%--    	  <div class="assets_header accheader">
        <div class="startTime">
            <span><spring:message code="financial.record.starttime" /></span>
            <input type="text" class="input_tr" id="begindate" readonly="readonly" value="${begindate }" />
        </div>        
        <div class="startTime endTime">
            <span><spring:message code="m.security.endtime" /></span>
            <input type="text" class="input_tr" name="start_date" id="enddate" readonly="readonly"  value="${enddate }" />
        </div>
        <div style="display: none">
                <span class="databtn datatime ${datetype==1?'datatime-sel':'' }" data-type="1"><spring:message code="financial.record.today" /></span>
                <span class="databtn datatime ${datetype==2?'datatime-sel':'' }" data-type="2">7<spring:message code="financial.record.days" /></span>
                <span class="databtn datatime ${datetype==3?'datatime-sel':'' }" data-type="3">15<spring:message code="financial.record.days" /></span>
                <span class="databtn datatime ${datetype==4?'datatime-sel':'' }" data-type="4">30<spring:message code="financial.record.days" /></span>
            </div>
            <input type="hidden" id="datetype" value="${datetype }">

        <div class="choiceType startTime">
            <span><spring:message code="financial.record.opertype" /></span>
            <c:forEach items="${filters }" var="v">
                <c:if test="${select==v.value}">
                	<span>
                    	<em class="sec textCenter choiceBtn" >${v.value }</em>
                    </span>
                 </c:if>
                 
            </c:forEach>       
        </div>
    </div> --%>
    <ul class="recordType">		
		<li onclick="recordType(0)" class="recordactive"><spring:message code="market.all" /></li>
		<li onclick="recordType(3)"><spring:message code="financial.recharge" /></li>
		<li onclick="recordType(4)"><spring:message code="financial.withdrawal" /></li>
	</ul>
   	  <div class="assetsMain">
        <div class="tradeCon">
            <div class="tradeList active">
                <div class="tradeList_tit">
                    <span class="textL"><spring:message code="financial.record.tradtime" /></span>
                    <span class="textC">
                        <c:choose>
                            <c:when test="${recordType==1 || recordType==2 }">
                                <spring:message code="financial.record.amount" />
                            </c:when>
                            <c:when test="${recordType==3 || recordType==4 || recordType==5 || recordType==6 || recordType==0 }">
                                <spring:message code="financial.record.qty" />
                            </c:when>
                            </c:choose>
                    </span>
                    <span><spring:message code="financial.cnyrewithdrawal.fee" /></span>
                    <span class="textR"><spring:message code="financial.cnyrecharge.rechargestatus" /></span>
                </div>
                <ul class="ul_con" id="slidecontentbox">
                       <%--充值、提现--%>
                 <c:forEach items="${list }" var="v">
                      <li class="clear">
                      	<div class="showmsg">
                         <em class="textL"><fmt:formatDate value="${v.fcreateTime }" pattern="yyyy/MM/dd"/></em>
                         <em class="textC"><ex:DoubleCut value="${v.famount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>${v.fvirtualcointype.fShortName }</em>
                         <em><ex:DoubleCut value="${v.ffees }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>${v.fvirtualcointype.fShortName }</em>
                         <em>
                         <lable class="cblue2 textR">${v.fstatus_s }</lable>
                         <c:if test="${v.fstatus==1 }">
                               <c:if test="${v.ftype==2 }">
                                 <a class="cancelWithdrawBtc opa-link" data-fid="${v.fid }">取消</a>
                               </c:if>
                               <c:if test="${v.ftype==3 }">
                                 <a class="cancelTransferBtc opa-link" data-fid="${v.fid }">取消</a>
                               </c:if>
                         </c:if>
                         </em>
                         </div>
                         <div class="entrustCon" style="display: none;margin-bottom:0px;padding:0;border-top:0px solid #f7f7fb;">
                         <c:if test="${v.ftype==2 }"><h3>提币详情</h3></c:if>
                         <c:if test="${v.ftype==1 }"><h3>充币详情</h3></c:if>
                         <c:if test="${v.ftype==3 }"><h3>站内转账详情</h3></c:if>
                    <ul class="entUl">
                    	<c:if test="${v.ftype==2 }">
                     	<li>
                     		<span>地址</span>
                     		<span class="entUlem">${v.withdraw_virtual_address }</span>
                     	</li>
                    	</c:if>
                    	<c:if test="${v.ftype==1 }">
                     	<li>
                     		<span>地址</span>
                     		<span class="entUlem">${v.recharge_virtual_address}</span>
                     	</li>
                    	</c:if>
                    	<c:if test="${v.ftype==3 && v.fuser.fid==cuid }">
                     	<li>
                     		<span>收款UID</span>
                     		<span class="entUlem">${v.withdraw_virtual_address}</span>
                     	</li>
                    	</c:if>
                    	<c:if test="${v.ftype==3 && v.fuser.fid!=cuid }">
                     	<li>
                     		<span>汇款UID</span>
                     		<span class="entUlem">${v.fuser.fid}</span>
                     	</li>
                    	</c:if>
                    	<li>
                    		<span>数量</span>
                    		<span class="entUlem"><ex:DoubleCut value="${v.famount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>${v.fvirtualcointype.fShortName }</span>
                    	</li>
                    	<li>
                    		<span>手续费</span>
                    		<span class="entUlem"><ex:DoubleCut value="${v.ffees }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>${v.fvirtualcointype.fShortName }</span>
                    	</li>
                    	<li>
                    		<span>状态</span>
                    		<span class="entUlem">${v.fstatus_s }</span>
                    	</li>
                    	<c:if test="${v.ftype==1 && v.fstatus==1}">
                    	<li>
                    		<span>确认数</span>
                    		<span class="entUlem">${v.fconfirmations }/${v.fvirtualcointype.fconfirm }</span>
                    	</li>
                    	</c:if>
                    	<li>
                    		<span>时间</span>
                    		<span class="entUlem"><fmt:formatDate value="${v.flastUpdateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                    	</li>	                        	
                    	<li>
                    		<span>备注</span>
                    		<span class="entUlem">${v.fischarge}</span>
                    	</li>
                    </ul>
                    
                </div>
                     </li>
                 </c:forEach>

                      <c:if test="${fn:length(list)==0 }">        
                         <div class="noTxt textCenter mtop">
                            <%-- <img src="${oss_url}/static/mobile/images/noMsg.png" alt="" /> --%>
                            <p> <spring:message code="financial.record.nobill" /></p>
                        </div>
                    </c:if>
                    <div id="slide_loading_btn"  onclick="slideLoadMoreInfo()" style=" <c:if test="${totalPage==1 }">display:none</c:if>"></div>
                </ul>

            </div>        
        </div>
   	  </div>
</div>
    <!-- 上滑加载更多  -->

 
<div id="coinWarp" class="coinWarp" onclick="hidetab(this.id);" style="z-index: 10000">
    <div class="coinLitBox coinLitBox2">
          <ul>
             <c:forEach items="${filters }" var="v"> 
                      <c:if test="${select==v.value}">
                      <input type="hidden" id="recordType" value="${v.key }">
                      </c:if>
                 <li> <a href="${v.key }">${v.value }</a></li>
               </c:forEach>
          </ul>
    </div>
    
</div>

<input type="hidden" id="pageCount" value="${totalPage}">
<input type="hidden" id="currentPage" value="${currentPage}">

<script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
<script type="text/javascript">
    // $(".choiceBtn").click(function(event) {
    //     $(".coinWarp").animate({bottom:"0"}, 200)
    // });
    //    $(".coinWarp").click(function(event) {
    //     $(this).css('bottom', '-100%');
    // });


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


  $(".choiceBtn").click(function(event) {
	  $(".coinWarp").show();
        $(".coinWarp").animate({bottom:"0"}, 200)
    });
 /*  $(".coinWarp").click(function(event) {
        $(this).css('bottom', '-100%');
    }); */

</script>
  <%@include file="../comm/footer.jsp" %>
<script src="${oss_url}/static/mobile2018/js/LCalendar.min.js" type="text/javascript" charset="utf-8"></script>
<%-- <script type="text/javascript" src="${oss_url}/static/mobile2018/js/plugin/My97DatePicker/WdatePicker.js?v=20181126201750"></script> --%>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/finance/account.record.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/finance/account.withdraw.js?v=20181126201752"></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/finance/account.recharge.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/finance/account.usdtrecharge.js?v=14"></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/finance/LCalendar.js?v=1"></script>

<script type="text/javascript">
$(".showmsg").click(function(event){
    $(this).siblings(".entrustCon").stop().slideToggle(10);
    $(this).parent("li").css('height', 'auto');
});
function recordType(item){
	window.location.href ='/account/record.html?recordType='+item;
}
if(window.location.search == '?recordType=3'){
	$(".recordType li").eq(1).addClass('recordactive').siblings().removeClass('recordactive');
}else if(window.location.search == '?recordType=4'){
	$(".recordType li").eq(2).addClass('recordactive').siblings().removeClass('recordactive');
}else{
	$(".recordType li").eq(0).addClass('recordactive').siblings().removeClass('recordactive');
}
</script>

<script type="text/javascript">
  // var calendar = new LCalendar();
  // calendar.init({
  //   'trigger': '#begindate', //标签id
  //   'type': 'date', //date 调出日期选择 datetime 调出日期时间选择 time 调出时间选择 ym 调出年月选择,
  //   'minDate': (new Date().getFullYear()-3) + '-' + 1 + '-' + 1, //最小日期
  //   'maxDate': (new Date().getFullYear()+3) + '-' + 12 + '-' + 31 //最大日期
  // });
  // var calendar = new LCalendar();
  // calendar.init({
  //   'trigger': '#enddate', //标签id
  //   'type': 'date', //date 调出日期选择 datetime 调出日期时间选择 time 调出时间选择 ym 调出年月选择,
  //   'minDate': (new Date().getFullYear()-3) + '-' + 1 + '-' + 1, //最小日期
  //   'maxDate': (new Date().getFullYear()+3) + '-' + 12 + '-' + 31 //最大日期
  // });

</script>
</body>
</html>