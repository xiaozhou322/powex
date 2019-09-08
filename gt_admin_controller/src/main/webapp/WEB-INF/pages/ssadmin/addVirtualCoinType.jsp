<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">添加虚拟币类型信息</h2>


<div class="pageContent">

	<form id="saveForm" name="saveForm" method="post" action="/buluo718admin/saveVirtualCoinType.html"
		class="pageForm required-validate" enctype="multipart/form-data"
		onsubmit="return iframeCallback(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>图标链接：</dt>
				<dd>
					<input type="file" class="inputStyle" value="" name="filedata"
						id="filedata" />
				</dd>
			</dl>
			<dl>
				<dt>币种名称：</dt>
				<dd>
					<input type="text" name="fname" class="required"
						size="70" />
				</dd>
			</dl>
			<dl>
				<dt>币种简称：</dt>
				<dd>
					<input type="text" name="fShortName"
						class="required" size="70" />
				</dd>
			</dl>
			<dl>
				<dt>符号：</dt>
				<dd>
					<input type="text" name="fSymbol" maxlength="1" class="required"
						size="40" />
				</dd>
			</dl>
			<dl>
				<dt>ACCESS_KEY：</dt>
				<dd>
					<input type="password" name="faccess_key"
						class="required" size="70" />
				</dd>
			</dl>
			<dl>
				<dt>SECRT_KEY：</dt>
				<dd>
					<input type="password" name="fsecrt_key"
						class="required" size="70" />
				</dd>
			</dl>
			<dl>
				<dt>IP地址：</dt>
				<dd>
					<input type="text" name="fip" class="required"
						size="70" />
				</dd>
			</dl>
			<dl>
				<dt>端口号：</dt>
				<dd>
					<input type="text" name="fport"
						class="required number" size="30" />
				</dd>
			</dl>
			<dl>
				<dt>最小提现数量：</dt>
				<dd>
					<input type="text" name="fminqty" class="required number"
						size="70" />
				</dd>
			</dl>
			<dl>
				<dt>最大提现数量：</dt>
				<dd>
					<input type="text" name="fmaxqty" class="required number"
						size="70" />
				</dd>
			</dl>
			<dl>
				<dt>最小充值数量：</dt>
				<dd>
					<input type="text" name="fpushMinQty" class="required number"
						size="70" />
					<input type="hidden" name="fpushMaxPrice" value="0" />
					<input type="hidden" name="fpushMaxQty" value="0" />
					<input type="hidden" name="fpushRate" value="0" />
				</dd>
			</dl>
			<dl>
				<dt>提币手续费：</dt>
				<dd>
					<input type="text" name="fpushMinPrice"  class="required number"
						size="70" />
				</dd>
			</dl>
			<dl>
				<dt>上级币种：</dt>
				<dd><select type="combox" name="parentCid">
					<option value="0">顶级币种</option>
					<c:forEach items="${typeMap1}" var="type">
						<option value="${type.key}">${type.value}</option>
					</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>是否可以充值：</dt>
				<dd>
					<input type="checkbox" name="fisrecharge" checked='1' />
				</dd>
			</dl>
			<dl>
				<dt>是否可以提现：</dt>
				<dd>
					<input type="checkbox" name="FIsWithDraw" checked='1' />
				</dd>
			</dl>
			<dl>
				<dt>是否可以转账：</dt>
				<dd>
					<input type="checkbox" name="fistransfer" />
				</dd>
			</dl>
		    <dl>
				<dt>确认数：</dt>
				<dd>
					<input type="text" name="fconfirm" class="required digits"
						size="70"/>
				</dd>
			</dl>
			<dl>
				<dt>生成地址数量：</dt>
				<dd>
					<input type="text" name="faddressCount" class="required number"
						size="70"/>
				</dd>
			</dl>
			<dl>
				<dt>钱包类地址：</dt>
				<dd>
					<input type="text" name="fclasspath" class=""
						size="70"/>
				</dd>
			</dl>
	 		<dl>
				<dt>是否ETP：</dt>
				<dd>
					<input type="checkbox" name="fisEtp" />
				</dd>
			</dl>
			<dl>
				<dt>是否BTS：</dt>
				<dd>
				<input type="checkbox" name="fisBts" />
				</dd>
			</dl>
			<dl>
				<dt>是否ETH：</dt>
				<dd>
					<input type="checkbox" name="fisEth" />
				</dd>
			</dl>
			<dl>
				<dt>汇总地址：</dt>
				<dd>
					<input type="text" name="mainAddr"
						class="" size="70" />
				</dd>
			</dl>
			
			
			<dl>
				<dt>是否ERC20代币：</dt>
				<dd>
					<input type="checkbox" name="fiserc20" />
				</dd>
			</dl>
			<dl>
				<dt>合约地址：</dt>
				<dd>
					<input type="text" name="fcontract"
						class="" size="70" />
				</dd>
			</dl>
			<dl>
				<dt>转账方法ID：</dt>
				<dd>
					<input type="text" name="ftransfer"
						class="" size="70" value="0xa9059cbb" />
				</dd>
			</dl>
			<dl>
				<dt>查询余额方法ID：</dt>
				<dd>
					<input type="text" name="fbalance"
						class="" size="70" value="0xf8b2cb4f" />
				</dd>
			</dl>
			<dl>
				<dt>代币精度：</dt>
				<dd>
					<input type="text" name="fdecimals"
						class="" size="70" value="18" />
				</dd>
			</dl>
			
			<dl>
				<dt>是否自动提币：</dt>
				<dd>
				<input type="checkbox" name="fisautosend" />
				</dd>
			</dl>
			<dl>
				<dt>钱包密码：</dt>
				<dd>
					<input type="text" name="fpassword"
						class="" size="70"/>
				</dd>
			</dl>
			<dl>
				<dt>充值是否自动到账：</dt>
				<dd>
					<input type="checkbox" name="fisauto" checked='1' />
				</dd>
			</dl>
			<dl>
				<dt>手续费说明：</dt>
				<dd>
					<textarea rows="3" cols="80" name="fdescription"></textarea>
				</dd>
			</dl>
			<dl>
				<dt>是否锁仓增值：</dt>
				<dd>
				<input type="checkbox" name="fislocked" />
				</dd>
			</dl>
			<dl>
				<dt>币种排序：</dt>
				<dd>
					<input type="text" name="forder"
						class="" size="70" value="0"/>
				</dd>
			</dl>
			
			<dl>
				<dt>谷歌认证码：</dt>
				<dd>
					<input
						type="text" id="gcode" name="gcode" maxlength="6" class="required digits" />
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit" onclick="fillgcode();">保存</button>
						</div>
					</div></li>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div></li>
			</ul>
		</div>
	</form>

</div>


<script type="text/javascript">
function customvalidXxx(element){
	if ($(element).val() == "xxx") return false;
	return true;
}
function fillgcode(){
	var gcode = $("#gcode").val();
	$("#saveForm").attr("action","/buluo718admin/saveVirtualCoinType.html?gcode="+gcode);
}
</script>
