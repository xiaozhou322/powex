<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;if (request.getServerName().equals("www.gbcax.com")){basePath="https://www.gbcax.com";}
%>


 <input type="hidden" id="tradetype" value="0"/>
 <input type="hidden" id="symbol" value="0"/>
		<div class="coinBox buyonesellone">
			<div class="coinBoxBody buybtcbody2">
				<ul class="orderlist">
					<li style="height:31px;">
						<span style="float:left;padding-left:15px;">
							 <span class="black bold fontsize">最新成交价:</span>
							 <span class="lightorange1 fontsize"><ex:DoubleCut value="${LatestDealPrize }" pattern="##.##" maxFractionDigits="4" maxIntegerDigits="15"/></span>
						</span>
						<span style="float:right;padding-right:35px;"><a href="/market.html">更多>></a></span>
					 </li>
						<li class="borderbtm black"><span class="height-40 c1 fontsize">买卖</span><span class="height-40 c2 fontsize">价格</span><span class="height-40 c3 fontsize">委单量</span></li>
						
						
						<c:forEach begin="0" end="7" varStatus="vs">
							<c:forEach items="${SellMap }" var="v" varStatus="s" begin="${7-vs.index }" end="${7-vs.index }">
								<li class="red" onmouseover="this.style.cursor='pointer';this.style.backgroundColor=' #FFFFAA';" onmouseout="this.style.backgroundColor=' #fff';" onclick="javascript:autoTrade(${vs.index +1-(fn:length(SellMap)>=5?0:(5-fn:length(SellMap)))},1,'${fvirtualcointype.fShortName}');"><span class="c1 height-35">卖 (${5-vs.index})</span><span class="c2 height-35" ><input id="sellPrice${vs.index +1-(fn:length(SellMap)>=5?0:(5-fn:length(SellMap)))}" type="hidden" value="<ex:DoubleCut value="${v.fprize }" pattern="##.##" maxFractionDigits="4" maxIntegerDigits="15"/>"/><ex:DoubleCut value="${v.fprize }" pattern="##.##" maxFractionDigits="4" maxIntegerDigits="15"/></span><span class="c3 height-35" >${fvirtualcointype.fSymbol}<input id="sellAmount${vs.index +1-(fn:length(SellMap)>=5?0:(5-fn:length(SellMap)))}" type="hidden" value="<ex:DoubleCut value="${v.fleftCount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>"/><ex:DoubleCut value="${v.fleftCount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></span></li>
							</c:forEach>
						</c:forEach>


						<c:forEach items="${BuyMap }" var="v" varStatus="vs" begin="0" end="7">
							<li class="lightgreen5" onmouseover="this.style.cursor='pointer';this.style.backgroundColor=' #FFFFAA';" onmouseout="this.style.backgroundColor=' #fff';" onclick="javascript:autoTrade(${vs.index +1},0,'${fvirtualcointype.fShortName}');" ><span class="c1 height-35">买 (${vs.index +1})</span><span class="c2 height-35" ><input id="buyPrice${vs.index +1}" type="hidden" value="<ex:DoubleCut value="${v.fprize }" pattern="##.##" maxFractionDigits="4" maxIntegerDigits="15"/>"/><ex:DoubleCut value="${v.fprize }" pattern="##.##" maxFractionDigits="4" maxIntegerDigits="15"/></span><span class="c3 height-35">${fvirtualcointype.fSymbol}<input id="buyAmount${vs.index +1}" type="hidden" value="<ex:DoubleCut value="${v.fleftCount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>"/><ex:DoubleCut value="${v.fleftCount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></span></li>
						</c:forEach>
									
									
									
					</ul>
				</div>
				
</div>

