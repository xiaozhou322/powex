<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@include file="include.inc.jsp"%>
<style>
.footer .active{
	border:none;
}
</style>
<br><br><br>
<footer class="footer">
    <ul>
        <a href="/" class="tabbar">
            <img class="img01" src="${oss_url}/static/mobile2018/images/icon_home1.png" alt="" />
            <img class="img02" src="${oss_url}/static/mobile2018/images/icon_home1-1.png" alt="" />
            <span><spring:message code="nav.top.home" /></span>
        </a>
        <a href="/trademarket.html" class="tabbar">
            <img class="img01" src="${oss_url}/static/mobile2018/images/icon_trade2.png" alt="" />
            <img class="img02" src="${oss_url}/static/mobile2018/images/icon_trade2-2.png" alt="" />
            <span><spring:message code="nav.top.trade" /></span>
        </a>  
		<a href="/advertisement/buyList.html" class="tabbar">
            <img class="img01" src="${oss_url}/static/mobile2018/images/icon_OTC3.png" alt="" />
            <img class="img02" src="${oss_url}/static/mobile2018/images/icon_OTC3-3.png" alt="" />
            <span><spring:message code="m.security.otccentre" /></span>
        </a>    
        <a href="/service/ourService.html?id=1" class="tabbar">
            <img class="img01" src="${oss_url}/static/mobile2018/images/icon_new4.png" alt="" />
            <img class="img02" src="${oss_url}/static/mobile2018/images/icon_new4-4.png" alt="" />
            <span><spring:message code="m.security.advisory" /></span>
        </a>   
        <a href="/user/security.html" class="tabbar">
            <img class="img01" src="${oss_url}/static/mobile2018/images/icon_user5.png" alt="" />
            <img class="img02" src="${oss_url}/static/mobile2018/images/icon_user5-5.png" alt="" />
            <span><spring:message code="m.security.user" /></span>
        </a>    
        <div class="clear"></div>
    </ul>
</footer>

<script type="text/javascript">
$(document).ready(function(){
	var url = window.location.href;
	var hasTab = 0;
	$('.tabbar').each(function(){
		$(this).removeClass('active');
		if($(this).index()>0)
		{
    		if(url.indexOf("/trademarket.html")!==-1)
    		{
    			hasTab = 1;
    		}else if(url.indexOf("/advertisement/")!==-1 || url.indexOf("/order/orderList.html")!==-1|| url.indexOf("/order/orderPageList.html")!==-1){
    			hasTab = 2;
    		}else if(url.indexOf("/about/")!==-1 || url.indexOf("/service/")!==-1){
    			hasTab = 3;
    		}else if(url.indexOf("/user/")!==-1 || url.indexOf("/financial/")!==-1 || url.indexOf("/question/")!==-1 || url.indexOf("/trade/")!==-1 || url.indexOf("/account/")!==-1 || url.indexOf("/introl/")!==-1){
    			hasTab = 4;
    		}
		}
		
	})
	$('.tabbar').eq(hasTab).addClass('active');
}) 
</script>