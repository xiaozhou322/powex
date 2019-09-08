<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;if (request.getServerName().equals("www.gbcax.com")){basePath="https://www.gbcax.com";}
%>
        <div class="realtimetop">
	      <div class="solid">
            <div class="box">
			  <div class="title">
			    <div>
					  <ul>
					  	<c:forEach items="${fvirtualcointypes }" var="v" varStatus="vs">
						<c:if test="${vs.index>0 }">
						<li style="padding:3px 9px 0 9px;">|</li>
						</c:if>
					    <li class="${v.fid==param.symbol?'cur':'' }">
							<a href="javascript:void(0);" onclick="javascript:indexDepthDiv(${v.fid});"  title="${v.fShortName }委托信息"><span class="">${v.fShortName }委托信息</span></a>
						</li>
					  	</c:forEach>
					 </ul>
				</div>
				<div  class="more">
					<ul>
					<li><a href="/market.html" title="更多">更多>></a></li>
					</ul>
			   </div>
			</div>
			  <div class="clear">
			    <div class="real-left">
				 <table class="transaction">
				   <thead>
				      <tr>
					    <th width="60">买入</th>
                        <th width="80">买入价</th>
                        <th width="80">委单量</th>
					  </tr>
					</thead>
					<tbody>
						<c:forEach items="${buy }" var="v" varStatus="vs">
							<tr>
								 <td class="lightgreen5">买(${vs.index+1 })</td>
		                         <td class="lightgreen5">￥${v.fprize }</td>
		                         <td>${fvirtualcointype.fSymbol }${v.fcount }</td>
						   </tr>
						</c:forEach>
						
						<c:if test="${fn:length(buy)<10 }">
						<c:forEach var="v" begin="${fn:length(buy)}" end="10" varStatus="vs">
							<tr>
								 <td class="lightgreen5"></td>
		                         <td class="lightgreen5"></td>
		                         <td></td>
						   </tr>
						</c:forEach>
						</c:if>
					</tbody>
				 </table>
				</div>
				<div class="real-right">
				    <table class="transaction">
				     <thead>
				      <tr>
					    <th width="60">买入</th>
                        <th width="80">买入价</th>
                        <th width="80">委单量</th>
					  </tr>
					</thead>
					<tbody>
				     <c:forEach items="${sell }" var="v" varStatus="vs">
							<tr>
								 <td class="lightred">(${vs.index+1 })</td>
		                         <td class="lightred">￥${v.fprize }</td>
		                         <td>${fvirtualcointype.fSymbol }${v.fcount }</td>
						   </tr>
						</c:forEach>
						
						<c:if test="${fn:length(sell)<10 }">
						<c:forEach var="v" begin="${fn:length(sell)}" end="10" varStatus="vs">
							<tr>
								 <td class="lightred"></td>
		                         <td class="lightred"></td>
		                         <td></td>
						   </tr>
						</c:forEach>
						</c:if>
						
					  </tbody>
					</table>
				</div>
			  </div>
			</div>
		  </div>
	    </div>
	    <div class="realtimebottom">
		  <div class="solid">
            <div class="box">
			  <div class="title">
				  <div>
				    <ul>
				    <c:forEach items="${fvirtualcointypes }" var="v" varStatus="vs">
						<c:if test="${vs.index>0 }">
						<li style="padding:3px 9px 0 9px;">|</li>
						</c:if>
					    <li class="${v.fid==param.symbol?'cur':'' }">
							<a href="javascript:void(0);" onclick="javascript:indexDepthDiv(${v.fid});"  title="${v.fShortName }委托信息"><span class="">${v.fShortName }委托信息</span></a>
						</li>
					  	</c:forEach>
					</ul>
				  </div>
				  <div  class="more">
					<ul>
					<li class=""><a href="/market.html" title="更多">更多>></a></li>
					</ul>
				  </div>
			  </div>
			  <div class="clear">
			    <div class="real">
				 <table class="transaction">
				   <thead>
				      <tr>
					    <th width="110">成交时间</th>
					    <th width="100">成交类型</th>
                        <th width="100">成交价</th>
                        <th width="110">成交量</th>
                        <th width="110">总金额</th>
					  </tr>
					</thead>
					<tbody>
						
						<c:forEach items="${success }" var="v" varStatus="vs">
							<tr>
								 <td> <fmt:formatDate value="${v.flastUpdatTime }" pattern="HH:mm:ss"/></td>
								 <td><font class="${v.fentrustType==0?'lightgreen':'red' }">${v.fentrustType==0?'买入':'卖出' }</font></td>
		                         <td class=""><font class="${v.fentrustType==0?'lightgreen':'red' }">￥${v.fprize }</font></td>
		                         <td>${fvirtualcointype.fSymbol }${v.fcount }</td>
		                         <td>￥${v.famount }</td>
						   </tr>
						</c:forEach>
						<c:if test="${fn:length(success)<10 }">
						<c:forEach var="v" varStatus="vs" begin="${fn:length(success)}" end="10">
							<tr>
								 <td></td>
								 <td></td>
		                         <td class=""></td>
		                         <td></td>
		                         <td></td>
						   </tr>
						</c:forEach>
						</c:if>
					 </tbody>
					</table>
					</div>
					</div>
			  </div>
			  </div>
	    </div>

