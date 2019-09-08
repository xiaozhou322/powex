<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>

<table class="table">
	<tr>
		<td>订单号</td>
		<td>充值时间</td>
		<td>充值方式</td>
		<td>充值金额(￥)</td>
		<td>状态</td>
		<td>操作</td>
	</tr>
	<c:forEach items="${list}" var="v">
		<tr>
			<td class="gray">${v.fid }</td>
			<td><fmt:formatDate value="${v.fcreateTime }"
					pattern="yyyy-MM-dd HH:mm:ss" />
			</td>
			<td>${v.fremittanceType }</td>
			<td>${v.famount }</td>
			<td>${v.fstatus_s }</td>
			<td class="opa-link"><c:if
					test="${(v.fstatus==1 || v.fstatus==2)}">
					<a class="rechargecancel opa-link" href="javascript:void(0);"
						data-fid="${v.fid }">取消</a>
					<c:if test="${v.fstatus==1}">
						&nbsp;|&nbsp;&nbsp;<a class="rechargesub opa-link"
							href="javascript:void(0);" data-fid="${v.fid }">提交充值</a>
					</c:if>
				</c:if> <c:if test="${(v.fstatus==3 || v.fstatus==4)}">
				--
			</c:if>
			</td>
		</tr>
	</c:forEach>
	<c:if test="${fn:length(list)==0 }">
		<tr>
			<td colspan="6" class="no-data-tips" align="center"><span>
					您暂时没有充值数据 </span></td>
		</tr>
	</c:if>
</table>

<input type="hidden" value="${cur_page }" name="currentPage"
	id="currentPage"></input>
<div class="text-right">${pagin }</div>