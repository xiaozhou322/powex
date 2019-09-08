<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="include.inc.jsp"%>



            <div class="l_financeL fl">
                <div class="firstNav">
                    <ul>
                        <li class="nav_item">
                            <h2>
                                <!-- <i class="nav_item_icon">
                                    <svg class="icon sfont24" aria-hidden="true">
                                       <use xlink:href="#icon-zichanxinxi"></use>
                                     </svg>
                                </i> --><spring:message code="newuser.financial.Account" />
                            </h2>
                            <ul>
                                <li class="nav_item_list ">
                                    <a href="/financial/index.html">
                                        <!-- <svg class="icon icon_s sfont16" aria-hidden="true">
                                           <use xlink:href="#icon-qian"></use>
                                         </svg> -->
                                         <spring:message code="newuser.financial.asset" />
                                    </a>
                                </li>
                                <li class="nav_item_list">
                                    <a href="/user/paytypeList.html">
                                        <!-- <svg class="icon icon_s sfont16" aria-hidden="true">
                                           <use xlink:href="#icon-dingdan"></use>
                                         </svg> -->
                                         <spring:message code="agent.accountsetting" />
                                    </a>
                                </li>
                                <li class="nav_item_list">
                                    
                                    <a href="/account/record.html">
                                        <!-- <svg class="icon icon_s sfont16" aria-hidden="true">
                                           <use xlink:href="#icon-dingdan"></use>
                                         </svg> -->
                                         <spring:message code="newuser.financial.records" />
                                    </a>
                                </li>
                            </ul>
                        </li>
                        <li class="nav_item">
                            <h2>
                                <!-- <i class="nav_item_icon">
                                    <svg class="icon" aria-hidden="true">
                                       <use xlink:href="#icon-anquan"></use>
                                     </svg>
                                </i> --><spring:message code="nav.top.security" />
                            </h2>
                            <ul>
                                <li class="nav_item_list">
                                    <a href="/user/security.html">
                                       <!--  <svg class="icon icon_s sfont16" aria-hidden="true">
                                           <use xlink:href="#icon-anquan"></use>
                                         </svg> -->
                                         <spring:message code="newuser.security.settings" />
                                    </a>
                                </li>
                                  
                                 <li class="nav_item_list"  <c:if test="${!sessionScope.login_user.fistiger }"> style="display:none;"</c:if>>
                                    <a href="/user/api.html">
                                        <!-- <svg class="icon icon_s sfont16" aria-hidden="true">
                                           <use xlink:href="#icon-anquan"></use>
                                         </svg> -->
                                         <spring:message code="API访问" />
                                    </a>
                                </li>
                              
                                <li class="nav_item_list">
                                    <a href="/user/userloginlog.html">
                                        <!-- <svg class="icon icon_s sfont16" aria-hidden="true">
                                           <use xlink:href="#icon-yuyuejilu"></use>
                                         </svg> -->
                                         <spring:message code="security.loginlog" />
                                    </a>
                                </li>
                            </ul>
                        </li>
                        <li class="nav_item">
                            <h2>
                                <!-- <i class="nav_item_icon">
                                    <svg class="icon" aria-hidden="true">
                                       <use xlink:href="#icon-jiangli-"></use>
                                     </svg>
                                </i> --><spring:message code="newuser.financial.benefits" />
                            </h2>
                            <ul>
                                <li class="nav_item_list">
                                    <a href="/introl/mydivide.html?type=1">
                                        <!-- <svg class="icon icon_s sfont16" aria-hidden="true">
                                           <use xlink:href="#icon-leijishouyi"></use>
                                         </svg> -->
                                         <spring:message code="introl.title" />
                                    </a>
                                </li>
                                <li class="nav_item_list">
                                    <a href="/introl/mydivide.html?type=2">
                                        <!-- <svg class="icon icon_s sfont16" aria-hidden="true" >
                                           <use xlink:href="#icon-leijishouyi2"></use>
                                         </svg> -->
                                         <spring:message code="introl.income.detail" />
                                    </a>
                                </li>
                                <li class="nav_item_list">
                                    <a href="/introl/redPocket.html">
                                        <!-- <svg class="icon icon_s sfont16" aria-hidden="true">
                                           <use xlink:href="#icon-hongbaoguanli"></use>
                                         </svg> -->
                                         <spring:message code="newuser.financial.Candy" />
                                    </a>
                                </li>
                                <li class="nav_item_list">
                                    <a href="/introl/tradingRewards.html">
                                        <!-- <svg class="icon icon_s sfont16" aria-hidden="true">
                                           <use xlink:href="#icon-yuangongjiangliwodejianglijiangpinwodelipin"></use>
                                         </svg> -->
                                         <spring:message code="newuser.financial.rewards" />
                                    </a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
<script >

    // console.log(leftMeun);
    var leftpath = window.location.pathname;
    var leftactive = $(".nav_item_list");
    if(leftpath.startWith("/financial/index.html") || leftpath.startWith("/financial/accountcoin.html")|| leftpath.startWith("/financial/accountbank.html")){
        leftactive.eq(0).addClass("active") ;
    }else if(leftpath.startWith("/user/paytypeList.html")){
        leftactive.eq(1).addClass("active");
    }else if(leftpath.startWith("/account/record.html")){
        leftactive.eq(2).addClass("active");
    }else if(leftpath.startWith("/user/security.html") || leftpath.startWith("/user/realCertification.html")){
        leftactive.eq(3).addClass("active");
    }else if(leftpath.startWith("/user/api.html")){
        leftactive.eq(4).addClass("active");
    }else if(leftpath.startWith("/user/userloginlog.html")){
        leftactive.eq(5).addClass("active");
    }else if(leftpath.startWith("/introl/mydivide.html")){
    	var type=$("#type").val();
    	if(type==1||type=="1"){
    		leftactive.eq(6).addClass("active");
    	}else if(type==2||type=="2"){
    		leftactive.eq(7).addClass("active");
    	}else{
    		leftactive.eq(6).addClass("active");
    	} 
    }else if(leftpath.startWith("/introl/redPocket.html")){
        leftactive.eq(8).addClass("active");
    }else if(leftpath.startWith("/introl/tradingRewards.html")){
        leftactive.eq(9).addClass("active");
    }
</script>