<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>

<!doctype html>
<html>
<head> 
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp" %>
<style type="text/css">
.detailsCon{
    overflow: hidden;
    line-height: 30px;
    margin: 20px;
    clear: both;
}
.detailsCon table{
    width:100%;
}
.recordType{    clear: both;    overflow: hidden;}
.recordType li{
	float: left;
    height: 46px;
    line-height: 46px;
    width: 90px;
    text-align: center;
    cursor: pointer;
}
.assetCon_list .infoTit{
margin-top: 10px;
}
.recordType{border-bottom: 1px solid #F6F6F6;}
.recordactive {
    color: #2269d4;
    background: #fff;
    border-bottom: 2px solid;
}
.ftradeCen{    overflow: hidden;
    clear: both;
    width: 97%;
    margin: 0 16px;
    background: #FAFAFA;
    padding: 20px;
    color: #666666;
    font-size: 14px;}
    .ftradeCen li span{color:#333;}
</style>
</head>
<body>
  <%@include file="../comm/header.jsp" %>
  <input type="hidden" id="datetype" value="${datetype }">
<section>
    <div class="mg">
        <div class="l_finance clear">
            <div class=" fl">
                <div class="firstNav">
                   <%@include file="../comm/left_menu.jsp" %>
                </div>
            </div>
            <div class="l_financeR fr">
            <div class="financialCen"><spring:message code="财务记录" /></div>   
                <%-- <div class="personal_title">
                    <h2 class="assetTitle"><spring:message code="new.recode" /></h2>
                    <div class="asset-status-box">
                        <div class="choiceTime">
                            <spring:message code="financial.record.starttime" />
                            <input class="databtn datainput" id="begindate" value="${begindate }" readonly="readonly">
                            <span class="databtn datatips">to</span>
                            <input class="databtn datainput" id="enddate" value="${enddate }" readonly="readonly">
                            <span class="databtn datatime  ${datetype==1?'active':'' }" data-type="1"><spring:message code="financial.record.today" /></span>
                            <span class="databtn datatime ${datetype==2?'active':'' }" data-type="2">7<spring:message code="financial.record.days" /></span>
                            <span class="databtn datatime ${datetype==3?'active':'' }" data-type="3">15<spring:message code="financial.record.days" /></span>
                            <span class="databtn datatime ${datetype==4?'active':'' }" data-type="4">30<spring:message code="financial.record.days" /></span>
                            
                        </div>
                    </div>
                </div>  --%>
                  
                    
                    
                    
                <div class="assetList" style="margin-top:30px;">
                    <div class="assetCon_list accountSettings">
                    <ul class="recordType">		
						<li onclick="recordType(0)" class="recordactive"><spring:message code="market.all" /></li>
						<li onclick="recordType(3)"><spring:message code="financial.recharge" /></li>
						<li onclick="recordType(4)"><spring:message code="financial.withdrawal" /></li>
					</ul>
                        <ol class="infoTit clear">
                            <li><spring:message code="financial.record.tradtime" /></li>
                            <li><spring:message code="financial.record.type" /></li>
                            <li>
                            	 <c:choose>
									<c:when test="${recordType==1 || recordType==2 }">
										<spring:message code="financial.record.amount" />
									</c:when>
								<c:when test="${recordType==3 || recordType==4 || recordType==5 || recordType==6  || recordType==0 }">
										<spring:message code="financial.record.qty" />
									</c:when>
								</c:choose>
                            </li>
                            <li><spring:message code="financial.cnyrewithdrawal.fee" /></li>
                            <li><spring:message code="financial.cnyrecharge.rechargestatus" /></li>
                        </ol>
                        <ul class="infoCon">
                        	
                            <c:forEach items="${list }" var="v">                            
                          	  <li>
                                <div class="coinInfo">
                                    <span class="s-1"><fmt:formatDate value="${v.fcreateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                                    <span class="s-1 cred s_type">
									<c:if test="${v.ftype==1 }">
										<spring:message code="nav.top.recharge" />
									</c:if>
									<c:if test="${v.ftype==2 }">
										<spring:message code="nav.top.withdraw" />
									</c:if>
									<c:if test="${v.ftype==3 }">
										<spring:message code="financial.transfer" />
									</c:if>
									</span>
                                    <span class="s-1"><ex:DoubleCut value="${v.famount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>${v.fvirtualcointype.fShortName }</span>
                                    <span class="s-1"><ex:DoubleCut value="${v.ffees }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>${v.fvirtualcointype.fShortName }</span>
                                    <span class="s-1 cred">${v.fstatus_s }
                                    	<c:if test="${v.fstatus==1 }">
		                                      <c:if test="${v.ftype==2 }">
		                                        <a class="cancelWithdrawBtc opa-link" href="javascript:void(0);" data-fid="${v.fid }"> | 取消</a>
		                                      </c:if>
		                                      <c:if test="${v.ftype==3 }">
		                                        <a class="cancelTransferBtc opa-link" href="javascript:void(0);" data-fid="${v.fid }"> | 取消</a>
		                                      </c:if>
		                                      <c:if test="${v.ftype==1 }">
		                                      (${v.fconfirmations }/${v.fvirtualcointype.fconfirm })
		                                      </c:if>
	                                    </c:if>
                                    </span>
                                    <div class="clear"></div>
                                </div>
                              	<div class="detailsCon" style="display: none">
			                     <%-- <table>
			                        	<tr>
			                                <td style="height:30px!important;">区块链ID：</td>
			                                <td style="height:30px!important;font-size:12px;"><c:if test="${v.ftype==1 or v.ftype==2}">${v.ftradeUniqueNumber }</c:if> &nbsp; &nbsp; &nbsp;</td>
											
											<td style="height:30px!important;">处理时间：</td>
			                                <td style="height:30px!important;font-size:12px;"><fmt:formatDate value="${v.flastUpdateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			                            </tr>
										<tr>
										<c:if test="${v.ftype==2 }">
			                                <td style="height:30px!important;">提币地址：</td>
			                                <td style="height:30px!important;font-size:12px;width:390px">${v.withdraw_virtual_address }</td>
			                            </c:if>
										<c:if test="${v.ftype==1 }">
											<td style="height:30px!important;">充币地址：</td>
			                                <td style="height:30px!important;font-size:12px;width:390px">${v.recharge_virtual_address }</td>
										</c:if>
										<c:if test="${v.ftype==3 && v.fuser.fid==cuid }">
											<td style="height:30px!important;">收款UID：</td>
			                                <td style="height:30px!important;font-size:12px;width:390px">${v.withdraw_virtual_address }</td>
										</c:if>
										<c:if test="${v.ftype==3 && v.fuser.fid!=cuid }">
											<td style="height:30px!important;">汇款UID：</td>
			                                <td style="height:30px!important;font-size:12px;width:390px">${v.fuser.fid }</td>
										</c:if>
											<td style="height:30px!important;">备注信息：</td>
			                                <td style="height:30px!important;font-size:12px;">${v.fischarge }</td>
			                            </tr>
			                        </table> --%>
			                        <ul class="ftradeCen">
			                        <c:if test="${v.ftype==1 or v.ftype==2}">
			                        	<li>区块链ID：
			                        		<span>${v.ftradeUniqueNumber }</span>
										</li>
									</c:if> 
									<c:if test="${v.ftype==2 }">
			                        	<li>提币地址：
			                        		<span>${v.withdraw_virtual_address }</span>
										</li>
									</c:if>
									<c:if test="${v.ftype==1 }">
			                        	<li>充币地址：
			                        		<span>${v.recharge_virtual_address }</span>
										</li>
									</c:if>
									<c:if test="${v.ftype==3 && v.fuser.fid==cuid }">
			                        	<li>收款UID：
			                        		<span>${v.withdraw_virtual_address }</span>
										</li>
									</c:if>
									<c:if test="${v.ftype==3 && v.fuser.fid!=cuid }">
			                        	<li>汇款UID：
			                        		<span>${v.fuser.fid }</span>
										</li>
									</c:if>
			                        	<li>备注信息：
			                        		<span>${v.fischarge }</span>
										</li>
			                        </ul>
			                    </div>
                            </li>
                            </c:forEach>
							
                          <c:if test="${fn:length(list)==0 }">		
									<span class='noMsg' >
										<spring:message code="financial.record.nobill" />
									</span>
							
						</c:if>
                                                      
                        </ul>
                        <c:if test="${!empty(pagin) }">
                        <div class="page">
                           ${pagin }
                        </div>
                        </c:if>	
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

	
	
 
<%@include file="../comm/footer.jsp" %>	


	<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/My97DatePicker/WdatePicker.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/front2018/js/finance/account.record.js?v=20181126201750"></script>
	 <script type="text/javascript" src="${oss_url}/static/front2018/js/finance/account.withdraw.js?v=20181126201752"></script>
	   <script type="text/javascript" src="${oss_url}/static/front2018/js/finance/account.recharge.js?v=20181126201750"></script>
	   <script type="text/javascript" src="${oss_url}/static/front2018/js/finance/account.usdtrecharge.js?v=14"></script>
	<script type="text/javascript" src="${oss_url}/static/front2018/js/index/main.js?v=20181126201750"></script>
<script type="text/javascript">
$(".coinInfo").click(function(event){
    $(this).siblings(".detailsCon").stop().slideToggle(10);
    $(this).parent("li").css('height', 'auto');
});
if(window.location.search == '?recordType=3'){
	$(".recordType li").eq(1).addClass('recordactive').siblings().removeClass('recordactive');
}else if(window.location.search == '?recordType=4'){
	$(".recordType li").eq(2).addClass('recordactive').siblings().removeClass('recordactive');
}else{
	$(".recordType li").eq(0).addClass('recordactive').siblings().removeClass('recordactive');
}

</script>
</body>
</html>
