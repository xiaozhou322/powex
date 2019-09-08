<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>

		<c:forEach items="${list }" var="v">
                      <li class="clear">
                      	<div class="showmsg">
                         <em class="textL"><fmt:formatDate value="${v.fcreateTime }" pattern="yyyy/MM/dd"/></em>
                         <em class="textC"><ex:DoubleCut value="${v.famount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>${v.fvirtualcointype.fShortName }</em>
                         <em><ex:DoubleCut value="${v.ffees }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>${v.fvirtualcointype.fShortName }</em>
                         <em>
                         <lable class="cblue2 textR">${v.fstatus_s }</lable>
                         <c:if test="${v.fstatus==1 }">
                               <c:if test="${v.ftype==2 }">
                                 <a class="cancelWithdrawBtc opa-link" data-fid="${v.fid }">取消</a>
                               </c:if>
                               <c:if test="${v.ftype==3 }">
                                 <a class="cancelTransferBtc opa-link" data-fid="${v.fid }">取消</a>
                               </c:if>
                         </c:if>
                         </em>
                         </div>
                         <div class="entrustCon" style="display: none;margin-bottom:0px;padding:0;border-top:0px solid #f7f7fb;">
                         <c:if test="${v.ftype==2 }"><h3>提币详情</h3></c:if>
                         <c:if test="${v.ftype==1 }"><h3>充币详情</h3></c:if>
                         <c:if test="${v.ftype==3 }"><h3>站内转账详情</h3></c:if>
                    <ul class="entUl">
                    	<c:if test="${v.ftype==2 }">
                     	<li>
                     		<span>地址</span>
                     		<span class="entUlem">${v.withdraw_virtual_address }</span>
                     	</li>
                    	</c:if>
                    	<c:if test="${v.ftype==1 }">
                     	<li>
                     		<span>地址</span>
                     		<span class="entUlem">${v.recharge_virtual_address}</span>
                     	</li>
                    	</c:if>
                    	<c:if test="${v.ftype==3 && v.fuser.fid==cuid }">
                     	<li>
                     		<span>收款UID</span>
                     		<span class="entUlem">${v.withdraw_virtual_address}</span>
                     	</li>
                    	</c:if>
                    	<c:if test="${v.ftype==3 && v.fuser.fid!=cuid }">
                     	<li>
                     		<span>汇款UID</span>
                     		<span class="entUlem">${v.fuser.fid}</span>
                     	</li>
                    	</c:if>
                    	<li>
                    		<span>数量</span>
                    		<span class="entUlem"><ex:DoubleCut value="${v.famount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>${v.fvirtualcointype.fShortName }</span>
                    	</li>
                    	<li>
                    		<span>手续费</span>
                    		<span class="entUlem"><ex:DoubleCut value="${v.ffees }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>${v.fvirtualcointype.fShortName }</span>
                    	</li>
                    	<li>
                    		<span>状态</span>
                    		<span class="entUlem">${v.fstatus_s }</span>
                    	</li>
                    	<c:if test="${v.ftype==1 && v.fstatus==1}">
                    	<li>
                    		<span>确认数</span>
                    		<span class="entUlem">${v.fconfirmations }/${v.fvirtualcointype.fconfirm }</span>
                    	</li>
                    	</c:if>
                    	<li>
                    		<span>时间</span>
                    		<span class="entUlem"><fmt:formatDate value="${v.flastUpdateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                    	</li>	                        	
                    	<li>
                    		<span>备注</span>
                    		<span class="entUlem">${v.fischarge}</span>
                    	</li>
                    </ul>
                    
                </div>
                     </li>
                 </c:forEach>
