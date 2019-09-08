<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.gt.entity.Fsecurity"%>
<%@ include file="comm/include.inc.jsp"%>
<%!public String tree(Fsecurity security) {
		StringBuilder builder = new StringBuilder();
		
		int pid = security.getFsecurity() == null ? 0:security.getFsecurity().getFid();
		builder.append("{id:" + security.getFid() +  ", pId:" + pid + 
				", name:\"" + security.getFname() + "\", url:\"/buluo718admin/goSecurityJSP.html?url=ssadmin//securityList1&treeId=" + security.getFid() + "\", target:\"ajax\", rel:\"jbsxBox2moduleList\"},");
		System.out.print(builder);
		for(Fsecurity o : security.getFsecurities()) {
			builder.append(tree(o));				
		}
		return builder.toString();
	}%>
<%
	Fsecurity security = (Fsecurity) request.getAttribute("security");
	String securityTree = tree(security);
	securityTree = securityTree.substring(0, securityTree.length() - 1);
%>
<script type="text/javascript">
<!--
	var setting = {
		view : {
		//showIcon: false
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pId",
				rootPId : ""
			}
		},
		callback : {
			onClick : function(event, treeId, treeNode) {
				var $rel = $("#jbsxBox2moduleList");
				$rel.loadUrl(treeNode.url, {}, function() {
					$rel.find("[layoutH]").layoutH();
				});

				event.preventDefault();
			}
		}
	};

	var zNodes = [
<%=securityTree%>
	];

	$(document).ready(function() {
		var t = $("#securityTree");
		t = $.fn.zTree.init(t, setting, zNodes);
		t.expandAll(true);
	});
//-->
</script>

<div class="pageContent">
	<div class="tabs">
		<div class="tabsContent">
			<div>
				<div layoutH="5" id="jbsxBox2moduleTree"
					style="float:left; display:block; overflow:auto; width:300px; border:solid 1px #CCC; line-height:21px; background:#fff">
					<ul id="securityTree" class="ztree"></ul>
				</div>

				<div layoutH="0" id="jbsxBox2moduleList" class="unitBox"
					style="margin-left:306px;">
					<c:import url="securityList1.jsp"></c:import>
				</div>
			</div>
		</div>
	</div>
</div>