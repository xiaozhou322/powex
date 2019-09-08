<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<h2 class="contentTitle">
	代笔预售单<font color="red">${virtualpresalelog.fid}</font>确认审核
</h2>


<div class="pageContent">

	<form method="post" action="/buluo718admin/auditVirtualPresaleLog.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>预售单ID：</dt>
				<dd>${virtualpresalelog.fid}
				<input type="hidden" name="uid" value="${virtualpresalelog.fid}" />
				<input type="hidden" name="type" value="0" />
			</dl>
			<dl>
				<dt>会员UID：</dt>
				<dd style="color:#ff3300;font-weight:bold;font-size:18px;">${fuser.fid}</dd>
			</dl>
			<dl>
				<dt>登陆名：</dt>
				<dd>${fuser.floginName}</dd>
			</dl>
			<dl>
				<dt>会员真实姓名：</dt>
				<dd style="color:#ff3300;font-weight:bold;font-size:18px;">${fuser.frealName}</dd>
			</dl>
			<dl>
				<dt>状态：</dt>
				<dd>${virtualpresalelog.fstatus_s} <c:if test="${virtualpresalelog.fstatus!=1 }"><em style="color:#ff3300;font-weight:bold;font-size:16px;">非处理状态，不能审核</em></c:if></dd>
			</dl>
			
			<dl>
				<dt>购买数量：</dt>
				<dd style="color:#ff3300;font-weight:bold;font-size:18px;"><fmt:formatNumber value="${virtualpresalelog.fqty}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits=""/> ( <span id="rmbstyle" style="color:#ff3300;font-weight:bold;font-size:18px;"></span> )</dd>
			</dl>
			<dl>
				<dt>释放期数：</dt>
				<dd><fmt:formatNumber value="${virtualpresalelog.fseason}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="0"/> 期</dd>
			</dl>
			
			<dl>
				<dt>谷歌认证码：</dt>
				<dd>
					<input
						type="text" name="gcode" maxlength="6" class="required digits" />
				</dd>
			</dl>
			<c:if test="${virtualpresalelog.fstatus==1 }">
			<dt>审核码：</dt>
				<dd>
					<input type="password" name="confirmcode" size="50" value="" class="required" />
				</dd>
			</c:if>
		</div>
		<div class="formBar">
			<ul>
			<c:if test="${virtualpresalelog.fstatus==1 }">
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">确认审核</button>
						</div>
					</div>
				</li>
			</c:if>
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

$(document).ready(function() {
	$("#rmbstyle").html(toBigMoney(${virtualpresalelog.fqty }));
});

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
                case 3:strDW = "个";break;  
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
                strBig = "个"+strBig;  
            }else if((i<7)&&(i>3)&&(strArr[intFen-1]=="0")&&(strNow[0]!="零")&&(strNow[0]!="个")){  
                strBig = "零"+strBig;  
            }else if((i<7)&&(i>3)&&(strArr[intFen-1]=="0")&&(strNow[0]=="零")){}  
            else if((i<7)&&(i>3)&&(strArr[intFen-1]=="0")&&(strNow[0]=="个")){}  
            else if((i==7)&&(strArr[intFen-1]=="0")){  
                strBig ="万"+strBig;  
            }else if((i<11)&&(i>7)&&(strArr[intFen-1]=="0")&&(strNow[0]!="零")&&(strNow[0]!="万")){  
                strBig = "零"+strBig;  
            }else if((i<11)&&(i>7)&&(strArr[intFen-1]=="0")&&(strNow[0]=="万")){}  
            else if((i<11)&&(i>7)&&(strArr[intFen-1]=="0")&&(strNow[0]=="零")){}  
            else if((i<11)&&(i>8)&&(strArr[intFen-1]!="0")&&(strNow[0]=="万")&&(strNow[2]=="千")){  
                strBig = strNum+strDW+"万零"+strBig.substring(1,strBig.length);  
            }else if(i==11){  
                if((strArr[intFen-1]=="0")&&(strNow[0]=="万")&&(strNow[2]=="千")){  
                    strBig ="亿"+"零"+strBig.substring(1,strBig.length);  
                }else if((strArr[intFen-1]=="0")&&(strNow[0]=="万")&&(strNow[2]!="千")){  
                    strBig ="亿"+strBig.substring(1,strBig.length);  
                }else if((strNow[0]=="万")&&(strNow[2]=="千")){  
                    strBig = strNum+strDW+"零"+strBig.substring(1,strBig.length);  
                }else if((strNow[0]=="万")&&(strNow[2]!="千")){  
                    strBig = strNum+strDW+strBig.substring(1,strBig.length);  
                }else {  
                    strBig = strNum+strDW+strBig;  
                }  
            }else if((i<15)&&(i>11)&&(strArr[intFen-1]=="0")&&(strNow[0]!="零")&&(strNow[0]!="亿")){  
                strBig = "零"+strBig;  
            }else if((i<15)&&(i>11)&&(strArr[intFen-1]=="0")&&(strNow[0]=="亿")){  
            }else if((i<15)&&(i>11)&&(strArr[intFen-1]=="0")&&(strNow[0]=="零")){  
            }else if((i<15)&&(i>11)&&(strArr[intFen-1]!="0")&&(strNow[0]=="零")&&(strNow[1]=="亿")&&(strNow[3]!="千")){  
                strBig = strNum+strDW+strBig.substring(1,strBig.length);  
            }else if((i<15)&&(i>11)&&(strArr[intFen-1]!="0")&&(strNow[0]=="零")&&(strNow[1]=="亿")&&(strNow[3]=="千")){  
                strBig = strNum+strDW+"亿零"+strBig.substring(2,strBig.length);  
            }else{  
                strBig = strNum+strDW+strBig;  
            }  
            strFen = strFen.substring(0,intFen-1);  
            intFen = strFen.length;  
            strArr = strFen.split("");  
        }   
        if(strBig.substring(0,1)=="个")strBig=strBig.substring(1)  
        if(strBig.substring(0,1)=="零")strBig=strBig.substring(1)   
        if(strBig=="整") {strBig="零个整";}   
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
