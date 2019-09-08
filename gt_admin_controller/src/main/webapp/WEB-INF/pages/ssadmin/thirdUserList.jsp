<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="/buluo718admin/thirdUserList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /><input
		type="hidden" name="uid" value="${uid}" /><input
		type="hidden" name="startDate" value="${startDate}" />
		<input type="hidden" name="endDate" value="${endDate}" />
		<input type="hidden" name="kyc1" value="${kyc1}" />
		<input type="hidden" name="kyc2" value="${kyc2}" />
		<input type="hidden" name="hasIntro" value="${hasIntro}" />
		<input type="hidden" name="isactime" value="${isactime}" />
		 <input
		type="hidden" name="troUid" value="${troUid}" /> <input
		type="hidden" name="troNo" value="${troNo}" /> <input type="hidden"
		name="ftype" value="${ftype}" /> <input type="hidden" name="pageNum"
		value="${currentPage}" /> <input type="hidden" name="numPerPage"
		value="${numPerPage}" /> <input type="hidden" name="orderField"
		value="${param.orderField}" /> <input type="hidden"
		name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/thirdUserList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>会员信息：<input type="text" name="keywords"
						value="${keywords}" size="40" />
					</td>
					<td>推荐人UID：<input type="text" name="troUid" value="${troUid}"
						size="10" />
					</td>
					<td>会员状态： <select type="combox" name="ftype">
							<c:forEach items="${typeMap}" var="type">
								<c:if test="${type.key == ftype}">
									<option value="${type.key}" selected="true">${type.value}</option>
								</c:if>
								<c:if test="${type.key != ftype}">
									<option value="${type.key}">${type.value}</option>
								</c:if>
							</c:forEach>
					</select></td>
					
					<td>开始时间： <input type="text" name="startDate" class="date"
						readonly="true" value="${startDate }"  dateFmt="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>结束时间： <input type="text" name="endDate" class="date"
						readonly="true" value="${endDate }"  dateFmt="yyyy-MM-dd HH:mm:ss"/>
					</td>
				</tr>
				
				<tr>
					<td>KYC1认证： <select type="combox" name="kyc1">
							<option value="">全部</option>
							<option value="True" <c:if test="${kyc1=='True'}">selected="true"</c:if> >True</option>
							<option value="False" <c:if test="${kyc1=='False'}">selected="true"</c:if> >False</option>
					</select> </td>
					<td>KYC2认证： <select type="combox" name="kyc2">
							<option value="">全部</option>
							<option value="True" <c:if test="${kyc2=='True'}">selected="true"</c:if> >True</option>
							<option value="False" <c:if test="${kyc2=='False'}">selected="true"</c:if> >False</option>
					</select> </td>
					<td><input type="checkbox" name="hasIntro" value="True" <c:if test="${hasIntro=='True'}">checked</c:if>>推荐人不能为空</td>
					
					<td><input type="checkbox" name="isactime" value="True" <c:if test="${isactime=='True'}">checked</c:if>>是否在活动时间09:00-21:00</td>
					<td><button type="submit">查询</button></td>
				</tr>
			</table>
		</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<shiro:hasPermission name="buluo718admin/userForbbin1.html">
			 	<li><a class="edit"
					href="/buluo718admin/goUserJSP.html?uid={sid_user}&status=view&url=ssadmin//auditUser"
					target="dialog" rel=auditUser height="400" width="800"><span>身份证</span>
				</a></li>
				<li>
				<a class="edit"
					href="/buluo718admin/setOpenSecondVal.html?uid={sid_user}"
					target="ajaxTodo" title="确定要设置登录二次认证吗?"><span>二次认证</span>
				</a>
			</li>
				<li><a class="delete"
					href="/buluo718admin/userForbbin.html?uid={sid_user}&status=1&rel=listUser"
					target="ajaxTodo" title="确定要禁用吗?"><span>禁用</span> </a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="buluo718admin/userForbbin2.html">
				<li><a class="edit"
					href="/buluo718admin/userForbbin.html?uid={sid_user}&status=2&rel=listUser"
					target="ajaxTodo" title="确定要解除禁用吗?"><span>解禁</span> </a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="buluo718admin/userForbbin3.html">
				<li><a class="edit"
					href="/buluo718admin/userForbbin.html?uid={sid_user}&status=3&rel=listUser"
					target="ajaxTodo" title="确定要重设登陆密码吗?"><span>登陆密码</span> </a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="buluo718admin/userForbbin4.html">
				<li><a class="edit"
					href="/buluo718admin/userForbbin.html?uid={sid_user}&status=4&rel=listUser"
					target="ajaxTodo" title="确定要重设交易密码吗?"><span>交易密码</span> </a>
				</li>
		   </shiro:hasPermission>
		   <shiro:hasPermission name="buluo718admin/userForbbin5.html">
				<li><a class="edit"
					href="/buluo718admin/cancelGoogleCode.html?uid={sid_user}"
					target="ajaxTodo" title="确定要重设GOOGLE验证吗?"><span>谷歌</span>
				</a>
				</li>
		   </shiro:hasPermission>
		   <shiro:hasPermission name="buluo718admin/userForbbin6.html">
				<li><a class="edit"
					href="/buluo718admin/cancelTel.html?uid={sid_user}"
					target="ajaxTodo" title="确定要重置手机号码吗?"><span>手机号</span>
				</a>
				</li>
		</shiro:hasPermission>
		<shiro:hasPermission name="buluo718admin/userForbbin7.html">
		 	<li><a class="edit"
					href="/buluo718admin/goUserJSP.html?url=ssadmin//updateUserGrade&uid={sid_user}"
					height="250" width="700" target="dialog" rel="updateUserGrade"><span>等级</span>
				</a></li>
			<li><a class="edit"
				href="/buluo718admin/goUserJSP.html?uid={sid_user}&url=ssadmin//updateIntroPerson"
				height="240" width="800" target="dialog" rel="updateIntroPerson"><span>推荐人</span>
			</a></li>
			<li>
				<a class="edit"
					href="/buluo718admin/setTiger.html?uid={sid_user}"
					target="ajaxTodo" title="确定要设置操盘手吗?"><span>操盘手</span>
				</a>
			</li>
			<li>
				<a class="edit"
					href="/buluo718admin/setProject.html?uid={sid_user}"
					target="ajaxTodo" title="确定要设置项目方吗?"><span>项目方</span>
				</a>
			</li>
			<li>
				<a class="edit"
					href="/buluo718admin/setCommunity.html?uid={sid_user}"
					target="ajaxTodo" title="确定要设置项目方吗?"><span>社群号</span>
				</a>
			</li>
			<li>
				<a class="edit"
					href="/buluo718admin/setMerchant.html?uid={sid_user}"
					target="ajaxTodo" title="确定要设置承兑商吗?"><span>设置承兑商</span>
				</a>
			</li>
			<li>
				<a class="edit"
					href="/buluo718admin/setAppAuth.html?uid={sid_user}"
					target="ajaxTodo" title="确定要授权APP吗?"><span>授权APP</span>
				</a>
			</li>
			
	  </shiro:hasPermission>			
				<li class="line">line</li>
	<shiro:hasPermission name="buluo718admin/userExport.html">
				<li><a class="icon" href="/buluo718admin/thirdUserExport.html"
					target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出</span>
				</a></li>
	</shiro:hasPermission>		
		</ul>
		<ul></ul>
	</div>
	<table class="table" width="120%" layoutH="138">
		<thead>
			<tr>
				<th width="40" orderField="fid"
					<c:if test='${param.orderField == "fid" }'> class="${param.orderDirection}"  </c:if>>会员UID</th>
				<th width="60">注册类型</th>	
				<th width="40" orderField="fIntroUser_id.fid"
					<c:if test='${param.orderField == "fIntroUser_id.fid" }'> class="${param.orderDirection}"  </c:if>>推荐人UID</th>
				<th width="60" orderField="floginName"
					<c:if test='${param.orderField == "floginName" }'> class="${param.orderDirection}"  </c:if>>登陆名</th>				
				<th width="60">昵称</th>
				<th width="60">真实姓名</th>
				<th width="60">邮箱地址</th>
				<th width="60" orderField="fstatus"
					<c:if test='${param.orderField == "fstatus" }'> class="${param.orderDirection}"  </c:if>>会员状态</th>
				<th width="60" orderField="fscore.flevel"
					<c:if test='${param.orderField == "fscore.flevel" }'> class="${param.orderDirection}"  </c:if>>会员等级</th>
				<th width="60" orderField="fpostRealValidate"
					<c:if test='${param.orderField == "fpostRealValidate" }'> class="${param.orderDirection}"  </c:if>>证件是否提交</th>
				<th width="60" orderField="fhasRealValidate"
					<c:if test='${param.orderField == "fhasRealValidate" }'> class="${param.orderDirection}"  </c:if>>证件是否已审</th>	
				<th width="80">证件类型</th>
				<th width="80">国家地区</th>
				<th width="60">证件号码</th>
				<th width="60">复印件认证</th>
				<th width="60">手机号码</th>
				<th width="60" orderField="fInvalidateIntroCount"
					<c:if test='${param.orderField == "fInvalidateIntroCount" }'> class="${param.orderDirection}"  </c:if>>推广人数</th>
				<th width="60">是否操盘手</th>
				<th width="60">是否项目方</th>
				<th width="60">是否承兑商</th>
				<th width="60">是否二次认证</th>
				<th width="60">是否有APP权限</th>
				<th width="60" orderField="fregisterTime"
					<c:if test='${param.orderField == "fregisterTime" }'> class="${param.orderDirection}"  </c:if>>注册时间</th>
			    <th width="60">注册IP</th>
			    <th width="60" orderField="flastLoginTime"
					<c:if test='${param.orderField == "flastLoginTime" }'> class="${param.orderDirection}"  </c:if>>上次登陆时间</th>
			    <th width="60">上次登陆IP</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${userList}" var="user" varStatus="num">
				<tr target="sid_user" rel="${user.fid}">
					<td>${user.fid}</td>
					<td>${user.fregtype_s}</td>
					<td>${user.fIntroUser_id.fid}</td>
					<td>${user.floginName}</td>
					<td>${user.fnickName}</td>
					<td>${user.frealName}</td>
					<td>${user.femail}</td>
					<td>${user.fstatus_s}</td>
					<td>${user.fscore.flevel}</td>
					<td>${user.fpostRealValidate}</td>
					<td>${user.fhasRealValidate}</td>
					<td>${user.fidentityType_s}</td>
					<td>
						<c:choose>
							<c:when test="${user.fareaCode=='86'}">中国大陆</c:when>
							<c:when test="${user.fareaCode=='853'}">中国澳门</c:when>
							<c:when test="${user.fareaCode=='886'}">中国台湾</c:when>
							<c:when test="${user.fareaCode=='852'}">中国香港</c:when>
							<c:when test="${user.fareaCode=='61'}">澳大利亚(Australia)</c:when>
							<c:when test="${user.fareaCode=='49'}">德国(Germany)</c:when>
							<c:when test="${user.fareaCode=='7'}">俄罗斯(Russian Federation)</c:when>
							<c:when test="${user.fareaCode=='33'}">法国(France)</c:when>
							<c:when test="${user.fareaCode=='63'}">菲律宾(Philippines)</c:when>
							<c:when test="${user.fareaCode=='82'}">韩国(Korea)</c:when>
							<c:when test="${user.fareaCode=='1'}">加拿大(Canada)</c:when>
							<c:when test="${user.fareaCode=='52'}">墨西哥(Mexico)</c:when>
							<c:when test="${user.fareaCode=='81'}">日本(Japan)</c:when>
							<c:when test="${user.fareaCode=='66'}">泰国(Thailand)</c:when>
							<c:when test="${user.fareaCode=='65'}">新加坡(Singapore)</c:when>
							<c:when test="${user.fareaCode=='91'}">印度(India)</c:when>
							<c:when test="${user.fareaCode=='44'}">英国(United Kingdom)</c:when>
							<c:otherwise>${user.fareaCode}</c:otherwise>					
						</c:choose>
					</td>
					<td>${user.fidentityNo}</td>
					<td>${user.fhasImgValidate}</td>
					<td>${user.ftelephone}</td>
					<td>${user.fInvalidateIntroCount}</td>
					<td>${user.fistiger}</td>
					<td>${user.fisprojecter}</td>
					<td>${user.fismerchant}</td>
					<td>${user.fopenSecondValidate}</td>
					<td>${user.fgrade == 1 ? "有" : "无" }</td>
					<td>${user.fregisterTime}</td>
					<td>${user.fregIp}</td>
					<td>${user.flastLoginTime}</td>
					<td>${user.flastLoginIp}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="panelBar">
		<div class="pages">
			<span>总共: ${totalCount}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${totalCount}"
			numPerPage="${numPerPage}" pageNumShown="5"
			currentPage="${currentPage}"></div>
	</div>
</div>
