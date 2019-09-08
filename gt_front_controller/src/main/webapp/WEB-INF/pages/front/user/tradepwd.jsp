<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%

%>



<!doctype html>
<html>
<head>
<jsp:include page="../comm/link.inc.jsp"></jsp:include>
<link href="${oss_url}/static/front/css/user/common.css?v=20181126201750" rel="stylesheet" type="text/css" />
<link href="${oss_url}/static/front/css/user/main.css?v=20181126201750" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${oss_url}/static/front/css/user/kyc.css?v=20181126201750" type="text/css"></link>
<script type="text/javascript" src="${oss_url}/static/front/js/user/jquery-1.11.2.min.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/user/jquery.SuperSlide.2.1.js?v=20181126201750"></script>
</head>
<body>
	

<jsp:include page="../comm/header.jsp"></jsp:include>


<div class="lw-content">
    <div class="lw-finance">

        <jsp:include page="../comm/left_menu.jsp"></jsp:include>
              
            <div class="lw-financeRight security-right fr">
            <h1 class="lw-financeTit">CHANGE TRADING PASSWORD</h1>
            <p class="warn red" style="border-bottom: none;color: #ff6767!important;">
                <img src="${oss_url}/static/front/images/user/warn.png">
                Trading password is required when withdrawal
            </p>
            <div class="login_tixian_box">
                <form id="pwd-form" action="">
                    <div class="login_item login_item_show clear">
                        <div class="login_item_l">Current trading password:</div>
                        <div class="login_item_r">
                            <input id="unbindtradepass-oldpass"  type="password"  placeholder="Must be at least 6 characters" autocomplete="off"
autocomplete="off">
                        </div>
                    </div>
                    <div class="login_item login_item_show clear">
                        <div class="login_item_l">Trading Password:</div>
                        <div class="login_item_r">
                            <input id="unbindtradepass-newpass"  type="password"  placeholder="Must be at least 6 characters" autocomplete="off"
autocomplete="off">
                        </div>
                    </div>
                    <div class="login_item login_item_show clear">
                        <div class="login_item_l">Confirm:</div>
                        <div class="login_item_r">
                            <input type="password" id="unbindtradepass-confirmpass"  placeholder="Must be at least 6 characters" autocomplete="off"
autocomplete="off">
                        </div>

                    </div>

                    <c:if test="${isBindGoogle }">

                    <div class="login_item login_item_show clear">
                      <div class="login_item_l">Google Code:</div>
                      <div class="login_item_r">
                        <input type="text"  id="unbindtradepass-googlecode"  autocomplete="off">
                      </div>
                    </div>
                  </c:if> 

                  <div class="login_item login_item_show clear">
              <label for="unbindloginpass-errortips" class="col-xs-3 control-label"></label>
              <div class="col-xs-6">
                <span id="unbindtradepass-errortips" class="text-danger "></span>
              </div>
            </div>


                    <div class="login_item login_item_show clear">
                        <div class="login_item_l"></div>
                        <div class="login_item_r">
                            <input type="button"  class="loginpwd_btn" value="Save" id="unbindtradepass-Btn">
                        </div>

                    </div>
                </form>
            </div>
       </div>



    </div>
</div>


<jsp:include page="../comm/footer.jsp"></jsp:include>

    <script type="text/javascript" src="${oss_url}/static/front/js/plugin/ajaxfileupload.js?v=20181126201750"></script>
    <script type="text/javascript" src="${oss_url}/static/front/js/plugin/fileCheck.js?v=20181126201750"></script>
    <script type="text/javascript" src="${oss_url}/static/front/js/comm/msg.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.qrcode.min.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.autocomplete.min.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/user/kyc.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery-migrate-1.4.1.min.js?v=20181126201750"></script>
  <script type="text/javascript" src="${oss_url}/static/front/js/user/user.security.js?v=20181126201750"></script>
</body>
</html>
