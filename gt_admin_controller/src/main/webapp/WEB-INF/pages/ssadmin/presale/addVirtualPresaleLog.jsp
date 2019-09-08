<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<h2 class="contentTitle">添加预售记录信息</h2>


<div class="pageContent">

	<form method="post" action="/buluo718admin/saveVirtualPresaleLog.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>会员：</dt>
				<dd>
					<input type="hidden" name="userLookup.id" value="${userLookup.id}" />
					<input type="text" class="required" name="userLookup.floginName"
						value="" suggestFields="id,floginName"
						suggestUrl="buluo718admin/userLookup.html" lookupGroup="userLookup"
						readonly="readonly" /> <a class="btnLook"
						href="/buluo718admin/userLookup.html" lookupGroup="userLookup">查找带回</a>
				</dd>
			</dl>
			<dl>
				<dt>虚拟币名称：</dt>
				<dd>
					<select type="combox" name="vid">
						<c:forEach items="${allType}" var="type">
							<c:if test="${type.fid == vid}">
								<option value="${type.fid}" selected="true">${type.fname}</option>
							</c:if>
							<c:if test="${type.fid != vid}">
								<option value="${type.fid}">${type.fname}</option>
							</c:if>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>数量：</dt>
				<dd>
					<input type="text" name="fqty" maxlength="50" size="40"
						class="number required" onkeyup="convertrmb(this)" /> &nbsp; &nbsp;  <span id="rmbstyle" style="color:#ff3300;font-weight:bold;font-size:18px;"></span>
				</dd>
			</dl>
			<dl>
				<dt>释放期数：</dt>
				<dd>
					<input type="text" name="season" maxlength="50" size="40"
						class="number required" />
				</dd>
			</dl>
			<dl>
				<dt>谷歌认证码：</dt>
				<dd>
					<input
						type="text" name="gcode" maxlength="6" class="required digits" />
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">保存</button>
						</div>
					</div>
				</li>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>

</div>


<script type="text/javascript">
function customvalidXxx(element){
	if ($(element).val() == "xxx") return false;
	return true;
}

function convertrmb(element){
	var num = $(element).val();
	$("#rmbstyle").html(toBigMoney(num));
}

function rendererZhMoney(v) {  
    if(isNaN(v)){  
        return v;  
    }  
    v = (Math.round((v - 0) * 100)) / 100;  
    v = (v == Math.floor(v)) ? v + ".00" : ((v * 10 == Math.floor(v * 10)) ? v  
            + "0" : v);  
    v = String(v);  
    var ps = v.split('.');  
    var whole = ps[0];  
    var sub = ps[1] ? '.' + ps[1] : '.00';  
    var r = /(\d+)(\d{3})/;  
    while (r.test(whole)) {  
        whole = whole.replace(r, '$1' + ',' + '$2');  
    }  
    v = whole + sub;  
      
    return v;  
}  
  
/*转为以万为单位*/  
function rendererZhMoneyWan(v) {  
    if(isNaN(v)){  
        return v;  
    }  
    v = v*0.0001;//10000;  
    v = formatFloat(v,0);//parseInt(v);  
    rendererZhMoney(v);  
    return v;  
}  
  
/*转换为中文大写金额*/  
function toBigMoney(value){  
    var intFen,i;  
    var strArr,strCheck,strFen,strDW,strNum,strBig,strNow;  
    var isFu =  false; //是否为负数  
  
    if(value.trim==""){  
        return "零";  
    }  
    strCheck = value+".";  
    strArr = strCheck.split(".");  
    strCheck = strArr[0];  
    var len = strCheck.length ;  
    var valueFunc = value+""; //  
    if(len > 12){  
        Ext.MessageBox.alert("提示","数据"+value+"过大，无法处理！");  
        return "";  
    }  
    try{  
        i = 0;  
        strBig = "";  
        if(valueFunc.indexOf("-") != -1){  //如果为负数  
            isFu = true;  
            valueFunc = valueFunc.substring(1,valueFunc.length);  
            value = valueFunc;  
        }  
        var s00="00";  
        var svalue = value+"";  
        var ipos = svalue.indexOf(".") ;  
        var iiLen = svalue.length;  
        if(ipos<0){  
            strFen = svalue+"00";  
        }else if(ipos==iiLen-2){  
            strFen = svalue.substring(0,iiLen-2)+svalue.substring(iiLen-1,iiLen)+"0";  
        }else if(ipos==iiLen-3){  
            strFen = svalue.substring(0,iiLen-3)+svalue.substring(iiLen-2,iiLen);  
        }else{  
            strFen = svalue.substring(0,ipos)+svalue.substring(ipos+1,ipos+3);  
        }  
        intFen = strFen.length;  
        strArr = strFen.split("");  
        while(intFen!=0){  
            i = i+1;  
            switch(i){  
                case 1:strDW = "分";break;  
                case 2:strDW = "角";break;  
                case 3:strDW = "元";break;  
                case 4:strDW = "十";break;  
                case 5:strDW = "百";break;  
                case 6:strDW = "千";break;  
                case 7:strDW = "万";break;  
                case 8:strDW = "十";break;  
                case 9:strDW = "百";break;  
                case 10:strDW = "千";break;  
                case 11:strDW = "亿";break;  
                case 12:strDW = "十";break;  
                case 13:strDW = "百";break;  
                case 14:strDW = "千";break;  
            }  
            switch (strArr[intFen-1]){  
                case "1":strNum = "一";break;  
                case "2":strNum = "二";break;  
                case "3":strNum = "三";break;  
                case "4":strNum = "四";break;  
                case "5":strNum = "五";break;  
                case "6":strNum = "六";break;  
                case "7":strNum = "七";break;  
                case "8":strNum = "八";break;  
                case "9":strNum = "九";break;  
                case "0":strNum = "零";break;  
            }  
  
            strNow = strBig.split("");  
            if((i==1)&&(strArr[intFen-1]=="0")){  
                strBig = strBig+"整" ;  
            } else if((i==2)&&(strArr[intFen-1]=="0")){  
                if(strBig!="整")  
                    strBig = "零"+strBig;  
            }else if((i==3)&&(strArr[intFen-1]=="0")){  
                strBig = "元"+strBig;  
            }else if((i<7)&&(i>3)&&(strArr[intFen-1]=="0")&&(strNow[0]!="零")&&(strNow[0]!="元")){  
                strBig = "零"+strBig;  
            }else if((i<7)&&(i>3)&&(strArr[intFen-1]=="0")&&(strNow[0]=="零")){}  
            else if((i<7)&&(i>3)&&(strArr[intFen-1]=="0")&&(strNow[0]=="元")){}  
            else if((i==7)&&(strArr[intFen-1]=="0")){  
                strBig ="万"+strBig;  
            }else if((i<11)&&(i>7)&&(strArr[intFen-1]=="0")&&(strNow[0]!="零")&&(strNow[0]!="万")){  
                strBig = "零"+strBig;  
            }else if((i<11)&&(i>7)&&(strArr[intFen-1]=="0")&&(strNow[0]=="万")){}  
            else if((i<11)&&(i>7)&&(strArr[intFen-1]=="0")&&(strNow[0]=="零")){}  
            else if((i<11)&&(i>8)&&(strArr[intFen-1]!="0")&&(strNow[0]=="万")&&(strNow[2]=="仟")){  
                strBig = strNum+strDW+"万零"+strBig.substring(1,strBig.length);  
            }else if(i==11){  
                if((strArr[intFen-1]=="0")&&(strNow[0]=="万")&&(strNow[2]=="仟")){  
                    strBig ="亿"+"零"+strBig.substring(1,strBig.length);  
                }else if((strArr[intFen-1]=="0")&&(strNow[0]=="万")&&(strNow[2]!="仟")){  
                    strBig ="亿"+strBig.substring(1,strBig.length);  
                }else if((strNow[0]=="万")&&(strNow[2]=="仟")){  
                    strBig = strNum+strDW+"零"+strBig.substring(1,strBig.length);  
                }else if((strNow[0]=="万")&&(strNow[2]!="仟")){  
                    strBig = strNum+strDW+strBig.substring(1,strBig.length);  
                }else {  
                    strBig = strNum+strDW+strBig;  
                }  
            }else if((i<15)&&(i>11)&&(strArr[intFen-1]=="0")&&(strNow[0]!="零")&&(strNow[0]!="亿")){  
                strBig = "零"+strBig;  
            }else if((i<15)&&(i>11)&&(strArr[intFen-1]=="0")&&(strNow[0]=="亿")){  
            }else if((i<15)&&(i>11)&&(strArr[intFen-1]=="0")&&(strNow[0]=="零")){  
            }else if((i<15)&&(i>11)&&(strArr[intFen-1]!="0")&&(strNow[0]=="零")&&(strNow[1]=="亿")&&(strNow[3]!="仟")){  
                strBig = strNum+strDW+strBig.substring(1,strBig.length);  
            }else if((i<15)&&(i>11)&&(strArr[intFen-1]!="0")&&(strNow[0]=="零")&&(strNow[1]=="亿")&&(strNow[3]=="仟")){  
                strBig = strNum+strDW+"亿零"+strBig.substring(2,strBig.length);  
            }else{  
                strBig = strNum+strDW+strBig;  
            }  
            strFen = strFen.substring(0,intFen-1);  
            intFen = strFen.length;  
            strArr = strFen.split("");  
        }   
        if(strBig.substring(0,1)=="元")strBig=strBig.substring(1)  
        if(strBig.substring(0,1)=="零")strBig=strBig.substring(1)   
        if(strBig=="整") {strBig="零元整";}   
        if(true == isFu){ //如果为负数  
            strBig = "负"+strBig;  
        }  
        return strBig;  
    }catch(err){  
        alert(err);  
        return "";  
    }  
}

</script>
