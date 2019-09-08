<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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

<%@include file="../comm/link.inc.jsp" %>

</head>
<body>
<%@include file="../comm/header.jsp" %>
<section>   
    <div class="myMsg mg clear">
        <div class="msgTit clear">
            <div class="">
                <a href="/user/message.html?type=1" class="fl  ${type ==1?'active':'' }">
                    <span class='tag'>
                        <svg class="icon sfont18" aria-hidden="true">
                        <use xlink:href="#icon-wodexiaoxi"></use>
                        </svg>
                       
                    </span>
                    <spring:message code="security.message.unread" />                    
                </a>
                <a href="/user/message.html?type=2" class="fl ${type ==2?'active':'' }">
                    <svg class="icon sfont18" aria-hidden="true">
                        <use xlink:href="#icon-yiduxinxi"></use>
                    </svg>
                    <spring:message code="security.message.read" />
                </a>
            </div>
        </div>
        <div class="mg">
            <div class="msgMain">
                <ol class="clear">
                    <li class="fl">  <spring:message code="msg.title" />  </li>
                    <li class="fl">  <spring:message code="msg.time" />  </li>
                    <li class="fl caozuo"><spring:message code="msg.oper" />  </li>
                </ol>
                <ul>
                	<c:forEach items="${messages }" var="v" varStatus="st">
                    <li id="li_${v.fid}">
                        <div class="list clear">
                            <span class="nums fl">${st.count}</span>
                            <p class="txt fl"><span >${v.ftitle }</span></p>
                            <span class="time fl">${v.fcreateTime }</span>
                            <span class='fl  msglook caozuo' style="cursor: pointer;" data-value="${v.fid}" data-msg="${v.fcontent}">
                                                                                                                                     查看
                            </span> 
                        </div> 
                        <div class="msgShow" id="itme${v.fid}">
                  				
                        </div>                    
                    </li>                    
                   </c:forEach>
                </ul>
                <c:if test="${fn:length(messages)==0 }">
					<span class="noMsg"> <spring:message code="security.loginlog.nolog" /> </span>
				</c:if>	
                <div class="page">
                    ${pagin }
                </div>
            </div>
        </div>
    </div>
</section>

	<div class="modal modal-custom fade" id="msgdetail" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-mark"></div>
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span class="modal-title" id="exampleModalLabel"></span>
				</div>
				<div class="modal-body">
					</div>
				<!-- <div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal" style="background-color: #c83935;color: #fff;border-color: #c83935;">我已了解以上风险</button>
				</div> -->
			</div>
		</div>
	</div>
	

<%@include file="../comm/footer.jsp" %>	
<script type="text/javascript" src="${oss_url}/static/front2018/js/msg/message.js?v=20181126201750"></script>

</body>
</html>