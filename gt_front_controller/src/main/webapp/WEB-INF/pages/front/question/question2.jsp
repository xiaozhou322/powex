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
<!-- <script type="text/javascript" src="${oss_url}/static/front/js/user/jquery.SuperSlide.2.1.js?v=20181126201750"></script>
 --></head>
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
                      <li class="fl lw-active"><a href="/question/question.html"><spring:message code="security.qa" /></a></li>
                      <li class="fl "><a href="/question/questionlist.html"><spring:message code="security.list" /></a></li>
                      <div class="clear"></div>
                    </ul>
                    <div class="lw-askCon">
                      <div class="lw-onlineAsk  lw-onlineAskCur">
                          <form action="" class="lw-askForm">
                              <label class="lw-label clear" for="">
                                  <span class="fl"><spring:message code="security.quetype" /></span>
                                  <select name="" id="question-type" class="lw-askSelect fl">
                                            <c:forEach items="${question_list }" var="v">
                                                <option value="${v.key }">${v.value }</option>
                                            </c:forEach>
                                  </select>
                              </label>

                              <label class="lw-label clear" for="" style="margin-bottom:0px;">
                                  <span class="fl"><spring:message code="security.prodes" /></span>
                                  <textarea class="fl lw-txtarea" name="" id="question-desc" placeholder="<spring:message code="security.pleenteradescr" />"></textarea>
                              </label>

                             
                                <label for="" class="lw-label clear"> 
                                    <span class="fl"></span>
                                    <span id="errortips" class="text-danger fl"  style="width:279px;"></span>
                                </label>
  <!--                                 <div class="">
                                      <span></span>
                                      <span id="errortips" class="text-danger"></span>
                                  </div> -->
                            


                              <label class="lw-label clear" for="">
                                  <span class="fl"></span>
                                  <input class="fl lw-askSub" type="button" value="<spring:message code="security.subques" />" id="submitQuestion" />
                              </label>

                          </form>
                      </div>          

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
