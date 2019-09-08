<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post"
	action="/buluo718admin/entrustMemberMarket.html">
	<input
		type="hidden" name="logdate" value="${logdate}" /><input
		type="hidden" name="logdate1" value="${logdate1}" /><input
		type="hidden" name="ftype" value="${ftype}" /><input type="hidden"
		name="pageNum" value="${currentPage}" /> <input type="hidden"
		name="numPerPage" value="${numPerPage}" />
</form>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/entrustMemberMarket.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>统计时间： <input type="text" name="logdate" class="date"
						readonly="true" value="${logdate }"  dateFmt="yyyy-MM-dd"/>
						到
						 <input type="text" name="logdate1" class="date"
						readonly="true" value="${logdate1 }"  dateFmt="yyyy-MM-dd"/>
					</td>
					<td>统计时间： <select type="combox" name="ftype">
							<c:forEach items="${tmap}" var="type">
								<c:if test="${type.fid == ftype}">
									<option value="${type.fid}" selected="true">${type.fvirtualcointypeByFvirtualcointype2.fShortName}/${type.fvirtualcointypeByFvirtualcointype1.fShortName}</option>
								</c:if>
								<c:if test="${type.fid != ftype}">
									<option value="${type.fid}">${type.fvirtualcointypeByFvirtualcointype2.fShortName}/${type.fvirtualcointypeByFvirtualcointype1.fShortName}</option>
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
				<th width="60">该市场总成交量</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${allmember}"
				var="member">
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
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="panelBar">
		<div class="pages">
			<span>总共: ${total}条，用户成交总额：${sum }，该市场总成交额：${entrustTotal }</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${totalCount}"
			numPerPage="${numPerPage}" pageNumShown="5"
			currentPage="${currentPage}"></div>
	</div>
</div>
