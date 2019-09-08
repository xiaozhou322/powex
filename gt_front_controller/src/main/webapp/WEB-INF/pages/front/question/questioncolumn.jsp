<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>
<%

%>

<!doctype html>
<html>
<head> 
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp" %>

<link href="${oss_url}/static/front/css/index/common.css?v=20181126201750" rel="stylesheet" type="text/css" />
<link href="${oss_url}/static/front/css/index/main.css?v=20181126201750" rel="stylesheet" type="text/css" />
<link href="${oss_url}/static/front/css/question/question.css?v=20181126201750" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${oss_url}/static/front/js/user/jquery-1.11.2.min.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/user/jquery.SuperSlide.2.1.js?v=20181126201750"></script>
</head>
<body>
<%@include file="../comm/header.jsp" %>

  <div class=" lw-content">
    <div class="lw-finance">
                    <div class="lw-financeLeft fl">
                        <ul>
                            <li class="tabBtn "><a href="/user/security.html?tab=0"><spring:message code="security.security" /> <i></i></a></li>
			                <li class="tabBtn"><a href="/user/security.html?tab=1"><spring:message code="security.bindphone" /><i></i></a></li>
					       <li class="tabBtn"><a href="/user/security.html?tab=2"><spring:message code="security.bindemail" /><i></i></a></li>
					       <li class="tabBtn"><a href="/user/security.html?tab=3"><spring:message code="security.logpas" /><i></i></a></li>
			                <li class="tabBtn"><a href="/user/security.html?tab=4"><spring:message code="security.tradingpwd" /><i></i></a></li>
			                <li class="tabBtn"><a href="/user/realCertification.html"><spring:message code="security.ideaut" /><i></i></a></li>
			                <li class="tabBtn"><a href="/user/security.html?tab=6" ><spring:message code="security.gooaut" /><i></i></a></li>
			                <li class="tabBtn lw-cur"><a href="/question/question.html"><spring:message code="security.workcenter" /><i></i></a></li>
			                <li class="tabBtn"><a href="/user/userloginlog.html"><spring:message code="security.loginlog" /><i></i></a></li>
        					<li class="tabBtn"><a href="/user/message.html"><spring:message code="security.message" /><i></i></a></li>
			            </ul>
                    </div>

        <div class="lw-financeRight fr">
            <h1 class="lw-financeTit"><spring:message code="security.qa" /></h1>
            <div class="lw-financeMain lw-depositMain">
                <div class="lw-coinTitList lw-askTitList">
                    <ul>
                      <li class="fl "><a href="/question/question.html"><spring:message code="security.qa" /></a></li>
                      <li class="fl lw-active"><a href="/question/questionlist.html"><spring:message code="security.list" /></a></li>
                      <div class="clear"></div>
                    </ul>             
                </div>
                     <div class="lw-askCon">
                          <c:forEach items="${list }" var="v">
                          <c:if test="${v.fstatus==2 }">
                            <div class="modal modal-custom fade" id="questiondetail${v.fid }" tabindex="-1" role="dialog">
                          <div class="modal-dialog" role="document">
                          <div class="modal-mark"></div>
                          <div class="modal-content" style="border-radius:8px;">
                            <div class="modal-header">
                              <button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                              </button>
                              <span class="modal-title"><spring:message code="security.prodetail" /></span>
                            </div>
                            <div class="modal-body form-horizontal">
                              <div class="panel panel-default">
                                  <div class="panel-heading">
                                     <h3 class="panel-title"><spring:message code="security.quecontent" /></h3>
                                  </div>
                                 <div class="panel-body">
                                    ${v.fdesc }
                                  </div>
                              </div>
                              <div class="panel panel-default">
                                  <div class="panel-heading">
                                     <h3 class="panel-title"><spring:message code="security.repcontent" /></h3>
                                  </div>
                                 <div class="panel-body">
                                    ${v.fanswer }
                                  </div>
                              </div>                        
                            </div>
                          </div>
                        </div>
                      </div>
                      </c:if>       
                      </c:forEach>
                      
                            <div class="lw-onlineAsk" style="display: block;">
                                <table>
                                    <tr>
                                      <th width="11%"><spring:message code="security.quesnum" /></th>
                                      <th width="15%"><spring:message code="security.subtime" /></th>
                                      <th width="11%"><spring:message code="security.quetype" /></th>
                                      <th width="42%" class="lw-askDescription"><spring:message code="security.prodes" /></th>
                                      <th width="12%"><spring:message code="security.prostate" /></th>
                                      <th width="9%"><spring:message code="security.operating" /></th>
                                    </tr>                      
                                </table>
                              <c:forEach items="${list }" var="v">
                                <ul class="lw-askListAll">
                                  <li class="fl" style="width:11%;">${v.fid }</li>
                                  <li class="fl" style="width:15%;">${v.fcreateTime }</li>
                                  <li class="fl" style="width:11%;"><spring:message code="enum.question.${v.ftype }" /></li>
                                  <li class="fl" style="width:42%;">${v.fdesc }</li>
                                  <li class="fl" style="width:12%;"><spring:message code="enum.question.status.${v.fstatus }" /></li>
                                  <li class="fl" style="width:9%;"><a class="delete opa-link" href="javascript:void(0)" data-question="{&quot;questionid&quot;:${v.fid }}">
                                   <spring:message code="security.delete" />
                                  </a>
                            <c:if test="${v.fstatus==2 }">
                            | <a class="view opa-link" href="javascript:void(0)" data-question="{&quot;questionid&quot;:${v.fid }}"><spring:message code="security.view" /></a>
                            </c:if> </li>
                                  <div class="clear"></div>
                                </ul>
                                </c:forEach>

                                  <c:if test="${fn:length(list)==0 }">
                                  <p style="text-align:center; font-size:18px; display:none;"><spring:message code="financial.norec" /></p>
                               </c:if>

                               <tr>
                                      <td class="text-right" colspan="12">
                                          <div class="text-right">
                                              ${pagin }
                                          </div>
                                      </td>
                                  </tr>

                            </div>  


                    </div>
            </div>
        </div>



    </div>
  </div>
  

<%@include file="../comm/footer.jsp" %> 
<script type="text/javascript" src="${oss_url}/static/front/js/question/question.js?v=20181126201750"></script>
<script type="text/javascript">
  $(document).scroll(function() {
         var scrollTop = $(document).scrollTop();  //获取当前滑动位置
         if(scrollTop > 60){                 //滑动到该位置时执行代码
          $(".lw-header").addClass('lw-fixed')
         }else{
          $(".lw-header").removeClass('lw-fixed')
         }
    });
</script>
</body>
</html>
