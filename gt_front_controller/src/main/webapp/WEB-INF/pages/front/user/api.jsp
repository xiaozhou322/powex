<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%

%>

<!doctype html>
<html>
<head> 
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="${oss_url}/static/front/css/index/main.css?v=20181126201750" rel="stylesheet" type="text/css" />

<%@include file="../comm/link.inc.jsp" %>

<style type="text/css">
.api-tips{
	padding: 30px 15px;
	font-size: 14px;
}
.api-info{
	padding: 0 15px 15px;
	font-size: 14px;
}
.api-btn{
	display: inline-block;
	width: 50%;
	margin-top: 30px;
	margin-bottom: 30px;
}
</style>
</head>
<body>

 


<%@include file="../comm/header.jsp" %>

			


	
<section>
    <div class="mg">
        <div class="clear" style="padding: 35px 0 0 0;">
            <div class="fl">
                <div class="firstNav">
                 <%@include file="../comm/left_menu.jsp" %> 
                </div>
            </div>
            <div class="l_financeR fr">

			 <h2 class="assetTitle"><a href="/user/api.html"><spring:message code="user.api.access" /></a> </h2>
			   
				<p class="warn red" style="border-bottom: none;color: #ff6767!important;"><spring:message code="user.api.topcontent" /></p>
				
				 <div class="aqMain accountBdMain">
                        <div class="accoutType">
                            <div class="area accoutForm modifyPwd">
                                <div class="tr_txt">
                                     <span class="tr_l"><spring:message code="user.api.tradepassword" /></span>
                                     <input id="tradePwd"  class="inpBox"  autocomplete="off" type="password" >
                                </div>  
  
                                <div class="tr_txt">
                       				
                                      <span class="tr_l">API<spring:message code="user.api.permissions" /></span>
                                     <select class="inpBox" name="type" id="type">
                                                <option value="0"><spring:message code="user.api.trade" /></option>
												<option value="1"><spring:message code="user.api.withdrawal" /></option>
												<option value="2"><spring:message code="user.api.tradeandwithdrawal" /></option>        
                                     </select>
                                </div>                                                                               
                                <div class="tr_txt">
                                     <span class="tr_l"><spring:message code="user.api.ipadress" /></span>
                                     <input id="tradePwd"  class="inpBox" placeholder="<spring:message code="user.api.ipadress.pl" />" autocomplete="off"  type="text" >
                                </div>  
                              <c:if test="${isBindTelephone == true }">	  
                                <div class="tr_txt">
                                     <span class="tr_l"><spring:message code="security.smscode" /></span>
                                     <input type="text" class="inpBox" id="apiPhoneCode" autocomplete="off" placeholder="<spring:message code="security.smscode" />">
                                     <button class="sendCode btn-sendmsg" id="apisendmessage" data-msgtype="15" data-tipsid="apierrortips" ><spring:message code="financial.send" /></button>
                                </div>
                                </c:if>	
                             <c:if test="${isBindGoogle ==true}">	
								<div class="tr_txt">
                                     <span class="tr_l"><spring:message code="security.goocod" /></span>
                                     <input id="apiTotpCode"  class="inpBox"  autocomplete="off"   type="text">
                                </div>
							</c:if>	
							
							   <div class="tr_txt">
							        <label for="bindphone-errortips" class="tr_l tr_ts"></label>
                                     <span id="errorTips" class="cred"></span>
                                   
                                </div>
                                
                                <div class="tr_txt">
                                     <span class="tr_l"></span>
                                    <button id="apiButton" class="loginpwd_btn btn-find" style="width:400px; height:40px; line-height:40px; padding:0; border-radius:5px;"><spring:message code="user.api.button" /></button>
                                </div>
                                
                            </div>                           
                        </div>
                    </div>
                  <div class="tb_addressList">
                  <h2  class="assetTitle" style="    text-align: left;"><spring:message code="user.api.mykey" /></h2>
						<table>
							<tr>
								<th><spring:message code="user.api.createtime" /></th>
								<th><spring:message code="user.api.accesskey" />(Access Key)</th>
								<th><spring:message code="user.api.permissions" /></th>
								<th><spring:message code="user.api.ipadress" /></th>
								<th><spring:message code="user.api.operation" /></th>
							</tr>

							<c:forEach items="${list }" var="v">
								<tr>
									<td>${v.fcreatetime }</td>
									<td>${v.fpartner }</td>
									<td>${v.fistrade?'交易':'' }&nbsp;&nbsp;${v.fiswithdraw?'提现':'' }</td>
									<td>${v.fip }</td>
									<td><a class="delete-api" style="cursor: pointer;" data-fid="${v.fid }"><spring:message code="user.api.delete" /></a></td>
								</tr>
							</c:forEach>

						</table>
					</div>

				</div>
            </div>
        </div>


</section>
    <!-- 弹窗认证 -->

	<div class="modal modal-custom fade" id="apiModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-mark"></div>
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span class="modal-title" id="exampleModalLabel">创建成功</span>
				</div>
				<div class="modal-body">
					<div class="api-tips text-danger">您已经申请了API密钥对，请不要把您的密钥透露给任何人。秘密密钥 （Secret Key）仅在申请时显示，遗失后不可找回，请务必注意保存。 如您忘记了秘密密钥，请回收该密钥对并申请新的密钥对。</div>
					<div class="api-info">
						<span>访问密钥(Access Key) : </span> <span id="accessKey"></span>
					</div>
					<div class="api-info">
						<span>访问密钥(Secret Key) : </span> <span id="secretKey"></span>
					</div>
					<div class="api-info text-center">
						<button id="modalBtn" class="btn btn-danger api-btn">确定</button>
					</div>
				</div>
			</div>
		</div>
	</div>

 <%@include file="../comm/footer.jsp" %>	


	<input id="apiCount" type="hidden" value="${apiCount }"/>
	<input id="apinum" type="hidden" value="${apinum }"/>
	<script type="text/javascript" src="${oss_url}/static/front/js/comm/msg.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/api/api.js?v=20181126201750"></script>
	<script type="text/javascript">
	  $(document).scroll(function() {
	          $(".lw-header").removeClass('lw-fixed')
	         var scrollTop = $(document).scrollTop();  //获取当前滑动位置
	         if(scrollTop > 60){                 //滑动到该位置时执行代码
	          $(".lw-header").addClass('lw-fixed')
	         }else{
	         }
	    });
	</script>
</body>
</html>