<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post"
	action="/buluo718admin/entrustMember.html">
	<input
		type="hidden" name="day" value="${rday}" /><input
		type="hidden" name="ftype" value="${ftype}" /><input type="hidden"
		name="pageNum" value="${currentPage}" /> <input type="hidden"
		name="numPerPage" value="${numPerPage}" />
</form>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/entrustMember.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>统计周期：
					<select type="combox" name="ftype">
						<option value="d" <c:if test="${'d' == ftype}">selected="true"</c:if>>按日统计</option>
						<option value="m" <c:if test="${'m' == ftype}">selected="true"</c:if>>按月统计</option>
						<option value="y" <c:if test="${'y' == ftype}">selected="true"</c:if>>按年统计</option>	
					</select></td>
					<td>统计时间： <select type="combox" name="day">
							<c:forEach items="${queryday}" var="type">
								<c:if test="${type.key == rday}">
									<option value="${type.key}" selected="true">${type.value}</option>
								</c:if>
								<c:if test="${type.key != rday}">
									<option value="${type.key}">${type.value}</option>
								</c:if>
							</c:forEach>
					</select>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">查询</button>
							</div>
						</div></li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			
			<shiro:hasPermission name="buluo718admin/enMemberExport.html">
				<c:if test="${allmember.size()>0}">
				<li><a class="icon" href="/buluo718admin/enMemberExport.html?ftype=${ftype}&day=${day}"
					target="dwzExport" targetType="navTab" title="实要导出这些记录吗?" ><span>导出EXCEL</span>
				</a></li>
				</c:if>
			</shiro:hasPermission>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">会员UID</th>
				<th width="60">会员真实姓名</th>
				<th width="60">是否认证</th>
				<th width="60">推荐人ID</th>
				<th width="60">完全成交额</th>
				<th width="60">平台总成交量</th>
				<th width="60">预计可得GT数量</th>
			</tr>
		</thead>
		<tbody>
			<c:set value="0" var="sum" />
			<c:forEach items="${allmember}"
				var="member">
				<c:set value="${sum + member.total}" var="sum" />
				<tr target="sid_user" rel="${member.uid}">
					<td>${member.uid}</td>
					<td>${member.realname}</td>
					<td><c:if test="${member.hasRealValidate=='1'}">
							<c:if test="${member.hasImgValidate=='1'}">
								已完成KYC1、KYC2认证
							</c:if>
							<c:if test="${member.hasImgValidate!='1'}">
								已完成KYC1认证
							</c:if>
						</c:if>
						<c:if test="${member.hasRealValidate!='1'}">
							未认证
						</c:if>	
					</td>
					<td>${member.iuid}</td>
					<td><ex:DoubleCut value="${member.total}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><ex:DoubleCut value="${entrustTotal}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					<td><ex:DoubleCut value="${member.total/entrustTotal*70000}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="panelBar">
		<div class="pages">
			<span>总共: ${total}条，用户成交总额：${sum }，总成交额：${entrustTotal }，预计发放交易量奖励：${sum/entrustTotal*70000 }</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${totalCount}"
			numPerPage="${numPerPage}" pageNumShown="5"
			currentPage="${currentPage}"></div>
	</div>
</div>
