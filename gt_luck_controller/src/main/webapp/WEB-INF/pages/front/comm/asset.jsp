<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="include.inc.jsp"%>
<div class="assets">
															<span> 预估总资产： <span id="headertotalasset" class="text-danger">￥<ex:DoubleCut value="${totalCapitalTrade }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="2" /></span>
															</span> 
														</div>
														<div class="assets-detail">
															<ul class="first title clearfix">
																<li class="col-xs-3 padding-left-clear">币种</li>
																<li class="col-xs-3 text-center">可用</li>
																<li class="col-xs-3 text-center">冻结</li>
															</ul>
																
															<ul id="headercny0" class="clearfix">
																<li class="col-xs-3 padding-left-clear">人民币</li>
																<li class="col-xs-3 text-center text-danger">
																	<ex:DoubleCut value="${fwallet.ftotal }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>
																</li>
																<li class="col-xs-3 text-center">
																	<ex:DoubleCut value="${fwallet.ffrozen }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>
																</li>
															</ul>
															<c:forEach items="${fvirtualwallets }" var="v" varStatus="vs" begin="0">
																<ul id="headercoin${v.value.fvirtualcointype.fid }" class="clearfix first">
																	<li class="col-xs-3 padding-left-clear">${v.value.fvirtualcointype.fname }</li>
																	<li class="col-xs-3 text-center text-danger">
																		<ex:DoubleCut value="${v.value.ftotal }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>
																	</li>
																	<li class="col-xs-3 text-center">
																		<ex:DoubleCut value="${v.value.ffrozen }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>
																	</li>
																</ul>
															</c:forEach>

														</div>
														<div class="assets-btn">
															<a href="/account/rechargeCny.html" class="btn btn-danger btn-block">充值充币</a>
															 <a href="/account/withdrawCny.html" class="btn btn-success btn-block">提现提币</a>
														</div>