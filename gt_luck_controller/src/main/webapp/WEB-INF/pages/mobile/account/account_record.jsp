<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;if (request.getServerName().equals("www.gbcax.com")){basePath="https://www.gbcax.com";}
%>
<!doctype html>
<html>
<head>
    <base href="<%=basePath%>"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name = "viewport" content = "width = device-width, user-scalable = no,initial-scale=0.5,maximum-scale=0.5,minimum-scale=0.5">
    <%@include file="../comm/link.inc.jsp" %>
    <link rel="stylesheet" type="text/css" href="${oss_url}/static/mobile2018/css/LCalendar.css?v=20181126201750" />
    <title><spring:message code="financial.accrecord" /></title>
    <style type="text/css">
        @font-face {
           font-family: 'PingFangMedium';
           src: url('/static/mobile/fonts/PingFangMedium.ttf'); 
        } 
        
    </style>
</head>
<body>

    <%@include file="../comm/header.jsp" %>
    <div class="trade assets">
    <header class="tradeTop">
        <span class="back"></span>
        <span><spring:message code="newuser.financial.asset" /></span>
    </header>
    <div class="assets_header">
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
                    <em class="sec textCenter choiceBtn">${v.value }</em>
                    </span>
                 </c:if>
            </c:forEach>       
        </div>
    </div>
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
                            <c:when test="${recordType==5 || recordType==6 }">
                                <spring:message code="financial.record.qty" />
                            </c:when>
                            <c:when test="${recordType==3 || recordType==4 }">
                            <spring:message code="financial.record.qty" />
                            </c:when>
                            </c:choose>
                    </span>
                    <span><spring:message code="financial.cnyrewithdrawal.fee" /></span>
                    <span class="textR"><spring:message code="financial.cnyrecharge.rechargestatus" /></span>
                </div>
                <ul class="ul_con" id="slidecontentbox">
                 <c:choose>
                     <c:when test="${recordType==1 || recordType==2 }">
                         <c:forEach items="${list }" var="v">
                             <li class="clear"> <em class="textL"><fmt:formatDate value="${v.fcreateTime }" pattern="yyyy/MM/dd"/></em> <em class="textC">￥<ex:DoubleCut value="${v.famount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></em>
                                 <em>￥<ex:DoubleCut value="${v.ffees }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></em>
                                 <em class="cblue2 textR">${v.fstatus_s }</em>
                             </li>
                         </c:forEach>
                     </c:when>
                      <c:when test="${recordType==5 || recordType==6 }">
                      <%--人民币充值、提现--%>
                      <c:forEach items="${list }" var="v">
                         <li class="clear">
                        <em class="textL"><fmt:formatDate value="${v.fcreateTime }" pattern="yyyy/MM/dd"/></em>
                        <em class="textC">$<ex:DoubleCut value="${v.famount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>(￥<ex:DoubleCut value="${v.famount*6.5 }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>)</em>
                        <em>￥<ex:DoubleCut value="${v.ffees }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></em>
                        <em class="cblue2 textR">${v.fstatus_s }</em>
                    </li>           
                      </c:forEach>
                      </c:when>
                      <c:when test="${recordType==3 || recordType==4 }">
                       <%--充值、提现--%>
                        <c:forEach items="${list }" var="v">
                             <li class="clear">
                                <em class="textL"><fmt:formatDate value="${v.fcreateTime }" pattern="yyyy/MM/dd"/></em>
                                <em class="textC">${v.fvirtualcointype.fSymbol }<ex:DoubleCut value="${v.famount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></em>
                                <em>${v.fvirtualcointype.fSymbol }<ex:DoubleCut value="${v.ffees }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></em>
                                <em class="cblue2 textR">${v.fstatus_s }</em>
                            </li>         
                        </c:forEach>
                    </c:when>
                 </c:choose>

                      <c:if test="${fn:length(list)==0 }">        
                         <div class="noTxt textCenter mtop">
                            <img src="${oss_url}/static/mobile/images/noMsg.png" alt="" />
                            <p> <spring:message code="financial.record.nobill" /></p>
                        </div>
                    </c:if>
                </ul>

            </div>        
        </div>
    </div>
</div>
    <!-- 上滑加载更多  -->

 
<div class="coinWarp">
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
<div id="slide_loading_btn" class="textC"> onclick="slideLoadMoreInfo()" style=" <c:if test="${totalPage==1 }">display:none</c:if>"><spring:message code="m.security.moremsg" /></div>
<script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js"></script>
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
        $(".coinWarp").animate({bottom:"0"}, 200)
    });
       $(".coinWarp").click(function(event) {
        $(this).css('bottom', '-100%');
    });

</script>
  <%@include file="../comm/footer.jsp" %>
<script src="${oss_url}/static/mobile2018/js/LCalendar.min.js" type="text/javascript" charset="utf-8"></script>
<!-- <script type="text/javascript" src="${oss_url}/static/mobile2018/js/plugin/My97DatePicker/WdatePicker.js?v=20181126201750"></script> -->
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/finance/account.record.js?v=2"></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/finance/LCalendar.js?v=1"></script>
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