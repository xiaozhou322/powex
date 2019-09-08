<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<script src="../../static/ssadmin/js/js/highcharts.js?v=20181126201750"></script>
<script src="../../static/ssadmin/js/js/modules/exporting.js?v=20181126201750"></script>
<script type="text/javascript">
	$(function() {
		$('#vcOperationOutReport').highcharts(
				{
					chart : {},
					title : {
						text : '${coinType}提现统计表,区间总数：${total}'
					},
					xAxis : {
						categories : ${key}
					},
					tooltip : {
						formatter : function() {
							var s;
							if (this.point.name) { // the pie chart
								s = '' + this.point.name + ': ' + this.y
										+ ' fruits';
							} else {
								s = '日期：' + this.x + '| 数量：' + this.y;
							}
							return s;
						}
					},
					labels : {
						items : [ {
							html : '',
							style : {
								left : '40px',
								top : '8px',
								color : 'black'
							}
						} ]
					},
					series : [ {
						name : '日期',
						data : ${value}
					} ]
				});
	});
</script>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/buluo718admin/vcOperationReport.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>开始日期： <input type="text" name="startDate" class="date"
						readonly="true" value="${startDate }" /><font color="red">*</font>
						<input type="hidden" name="isSearch" value="1" />
						<input type="hidden" name="type" value="2" />
						<input type="hidden" name="status" value="3" />
						<input type="hidden" name="url" value="ssadmin/vcOperationOutReport" />
					</td>
					<td>结束日期： <input type="text" name="endDate" class="date"
						readonly="true" value="${endDate }" /><font color="red">*</font>
					</td>
					<td>虚拟币类型： <select type="combox" name="vid">
							<c:forEach items="${allType}" var="type">
								<c:if test="${type.fid == vid}">
									<option value="${type.fid}" selected="true">${type.fname}</option>
								</c:if>
								<c:if test="${type.fid != vid}">
									<option value="${type.fid}">${type.fname}</option>
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
</head>
<body>

	<div id="vcOperationOutReport"
		style="min-width: 310px; height: 400px; margin: 0 auto"></div>

</body>
</html>

