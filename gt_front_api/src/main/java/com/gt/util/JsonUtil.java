package com.gt.util;
import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gt.entity.BTCInfo;
import com.gt.entity.BTCMessage;
import com.gt.entity.Emailvalidate;
import com.gt.entity.Fabout;
import com.gt.entity.Fadmin;
import com.gt.entity.Fapi;
import com.gt.entity.Farticle;
import com.gt.entity.Farticletype;
import com.gt.entity.Fasset;
import com.gt.entity.Fautotrade;
import com.gt.entity.Fbankin;
import com.gt.entity.Fbankinfo;
import com.gt.entity.FbankinfoWithdraw;
import com.gt.entity.Fcapitaloperation;
import com.gt.entity.Fcountlimit;
import com.gt.entity.Fentrust;
import com.gt.entity.Fentrustlog;
import com.gt.entity.Fentrustplan;
import com.gt.entity.Ffees;
import com.gt.entity.Ffriendlink;
import com.gt.entity.Fintrolinfo;
import com.gt.entity.FlevelSetting;
import com.gt.entity.Flimittrade;
import com.gt.entity.Flog;
import com.gt.entity.Fmessage;
import com.gt.entity.Foperationlog;
import com.gt.entity.Fperiod;
import com.gt.entity.Fpool;
import com.gt.entity.Fquestion;
import com.gt.entity.Frole;
import com.gt.entity.FroleSecurity;
import com.gt.entity.Fscore;
import com.gt.entity.FscoreRecord;
import com.gt.entity.FscoreSetting;
import com.gt.entity.Fsecurity;
import com.gt.entity.Fsubscription;
import com.gt.entity.Fsubscriptionlog;
import com.gt.entity.Fsystemargs;
import com.gt.entity.Ftradehistory;
import com.gt.entity.Ftrademapping;
import com.gt.entity.Fuser;
import com.gt.entity.Fusersetting;
import com.gt.entity.Fvalidateemail;
import com.gt.entity.Fvalidatemessage;
import com.gt.entity.Fvirtualaddress;
import com.gt.entity.FvirtualaddressWithdraw;
import com.gt.entity.Fvirtualcaptualoperation;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Fvirtualoperationlog;
import com.gt.entity.Fvirtualwallet;
import com.gt.entity.Fwebbaseinfo;
import com.gt.entity.Fwithdrawfees;
import com.gt.entity.Systembankinfo;

import net.sf.json.JSONArray;

public class JsonUtil {

	private static List getFiledsInfo(Object o){  
	    Field[] fields=o.getClass().getDeclaredFields();  
	        String[] fieldNames=new String[fields.length];  
	        List list = new ArrayList();  
	        Map infoMap=null;  
	    for(int i=0;i<fields.length;i++){  
	        infoMap = new HashMap();  
	        infoMap.put(fields[i].getName(), fields[i].getType().toString()); 
	        list.add(infoMap);  
	    }  
	    return list;  
	   }
	
	public static String getObjJson(Object o){
		
		List objP =  getFiledsInfo(o);
		JSONArray json= JSONArray.fromObject(objP);
		return json.toString();
	}
	
	public static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive){  
        //获取此包的目录 建立一个File  
        File dir = new File(packagePath);  
        //如果不存在或者 也不是目录就直接返回  
        if (!dir.exists() || !dir.isDirectory()) {  
            return;  
        }  
        //如果存在 就获取包下的所有文件 包括目录  
        File[] dirfiles = dir.listFiles(new FileFilter() {  
        //自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)  
              public boolean accept(File file) {  
                return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));  
              }  
            });  
        //循环所有文件  
        for (File file : dirfiles) {  
            //如果是目录 则继续扫描  
            if (file.isDirectory()) {  
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(),  
                                      file.getAbsolutePath(),  
                                      recursive);  
            }  
            else {  
                //如果是java类文件 去掉后面的.class 只留下类名  
                String className = file.getName().substring(0, file.getName().length() - 6);  
                try {  
                    //添加到集合中去 
                	System.out.println(className+" " + className.toLowerCase() + " = new " + className + "();");
                	System.out.println("System.out.println(\"" + className + "\");");
                	System.out.println("System.out.println(getObjJson(" + className.toLowerCase() + "));");
            		Class.forName(packageName + '.' + className);
                } catch (ClassNotFoundException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
    }
	
	public static void main(String args[]) throws Exception{
		//findAndAddClassesInPackageByFile("com.ruizton.main.model","D:/项目/btc718/apache-tomcat-8.5.20x86/webapps/ROOT/WEB-INF/classes/com/ruizton/main/model",false);
		
		BTCInfo btcinfo = new BTCInfo();
		System.out.println("BTCInfo");
		System.out.println(getObjJson(btcinfo));
		BTCMessage btcmessage = new BTCMessage();
		System.out.println("BTCMessage");
		System.out.println(getObjJson(btcmessage));
		Emailvalidate emailvalidate = new Emailvalidate();
		System.out.println("Emailvalidate");
		System.out.println(getObjJson(emailvalidate));
		Fabout fabout = new Fabout();
		System.out.println("Fabout");
		System.out.println(getObjJson(fabout));
		Fadmin fadmin = new Fadmin();
		System.out.println("Fadmin");
		System.out.println(getObjJson(fadmin));
		Fapi fapi = new Fapi();
		System.out.println("Fapi");
		System.out.println(getObjJson(fapi));
		Farticle farticle = new Farticle();
		System.out.println("Farticle");
		System.out.println(getObjJson(farticle));
		Farticletype farticletype = new Farticletype();
		System.out.println("Farticletype");
		System.out.println(getObjJson(farticletype));
		Fasset fasset = new Fasset();
		System.out.println("Fasset");
		System.out.println(getObjJson(fasset));
		Fautotrade fautotrade = new Fautotrade();
		System.out.println("Fautotrade");
		System.out.println(getObjJson(fautotrade));
		Fbankin fbankin = new Fbankin();
		System.out.println("Fbankin");
		System.out.println(getObjJson(fbankin));
		Fbankinfo fbankinfo = new Fbankinfo();
		System.out.println("Fbankinfo");
		System.out.println(getObjJson(fbankinfo));
		FbankinfoWithdraw fbankinfowithdraw = new FbankinfoWithdraw();
		System.out.println("FbankinfoWithdraw");
		System.out.println(getObjJson(fbankinfowithdraw));
		Fcapitaloperation fcapitaloperation = new Fcapitaloperation();
		System.out.println("Fcapitaloperation");
		System.out.println(getObjJson(fcapitaloperation));
		Fcountlimit fcountlimit = new Fcountlimit();
		System.out.println("Fcountlimit");
		System.out.println(getObjJson(fcountlimit));
		Fentrust fentrust = new Fentrust();
		System.out.println("Fentrust");
		System.out.println(getObjJson(fentrust));
		Fentrustlog fentrustlog = new Fentrustlog();
		System.out.println("Fentrustlog");
		System.out.println(getObjJson(fentrustlog));
		Fentrustplan fentrustplan = new Fentrustplan();
		System.out.println("Fentrustplan");
		System.out.println(getObjJson(fentrustplan));
		Ffees ffees = new Ffees();
		System.out.println("Ffees");
		System.out.println(getObjJson(ffees));
		Ffriendlink ffriendlink = new Ffriendlink();
		System.out.println("Ffriendlink");
		System.out.println(getObjJson(ffriendlink));
		Fintrolinfo fintrolinfo = new Fintrolinfo();
		System.out.println("Fintrolinfo");
		System.out.println(getObjJson(fintrolinfo));
		FlevelSetting flevelsetting = new FlevelSetting();
		System.out.println("FlevelSetting");
		System.out.println(getObjJson(flevelsetting));
		Flimittrade flimittrade = new Flimittrade();
		System.out.println("Flimittrade");
		System.out.println(getObjJson(flimittrade));
		Flog flog = new Flog();
		System.out.println("Flog");
		System.out.println(getObjJson(flog));
		Fmessage fmessage = new Fmessage();
		System.out.println("Fmessage");
		System.out.println(getObjJson(fmessage));
		Foperationlog foperationlog = new Foperationlog();
		System.out.println("Foperationlog");
		System.out.println(getObjJson(foperationlog));
		Fperiod fperiod = new Fperiod();
		System.out.println("Fperiod");
		System.out.println(getObjJson(fperiod));
		Fpool fpool = new Fpool();
		System.out.println("Fpool");
		System.out.println(getObjJson(fpool));
		Fquestion fquestion = new Fquestion();
		System.out.println("Fquestion");
		System.out.println(getObjJson(fquestion));
		Frole frole = new Frole();
		System.out.println("Frole");
		System.out.println(getObjJson(frole));
		FroleSecurity frolesecurity = new FroleSecurity();
		System.out.println("FroleSecurity");
		System.out.println(getObjJson(frolesecurity));
		Fscore fscore = new Fscore();
		System.out.println("Fscore");
		System.out.println(getObjJson(fscore));
		FscoreRecord fscorerecord = new FscoreRecord();
		System.out.println("FscoreRecord");
		System.out.println(getObjJson(fscorerecord));
		FscoreSetting fscoresetting = new FscoreSetting();
		System.out.println("FscoreSetting");
		System.out.println(getObjJson(fscoresetting));
		Fsecurity fsecurity = new Fsecurity();
		System.out.println("Fsecurity");
		System.out.println(getObjJson(fsecurity));
		Fsubscription fsubscription = new Fsubscription();
		System.out.println("Fsubscription");
		System.out.println(getObjJson(fsubscription));
		Fsubscriptionlog fsubscriptionlog = new Fsubscriptionlog();
		System.out.println("Fsubscriptionlog");
		System.out.println(getObjJson(fsubscriptionlog));
		Fsystemargs fsystemargs = new Fsystemargs();
		System.out.println("Fsystemargs");
		System.out.println(getObjJson(fsystemargs));
		Ftradehistory ftradehistory = new Ftradehistory();
		System.out.println("Ftradehistory");
		System.out.println(getObjJson(ftradehistory));
		Ftrademapping ftrademapping = new Ftrademapping();
		System.out.println("Ftrademapping");
		System.out.println(getObjJson(ftrademapping));
		Fuser fuser = new Fuser();
		System.out.println("Fuser");
		System.out.println(getObjJson(fuser));
		Fusersetting fusersetting = new Fusersetting();
		System.out.println("Fusersetting");
		System.out.println(getObjJson(fusersetting));
		Fvalidateemail fvalidateemail = new Fvalidateemail();
		System.out.println("Fvalidateemail");
		System.out.println(getObjJson(fvalidateemail));
		Fvalidatemessage fvalidatemessage = new Fvalidatemessage();
		System.out.println("Fvalidatemessage");
		System.out.println(getObjJson(fvalidatemessage));
		Fvirtualaddress fvirtualaddress = new Fvirtualaddress();
		System.out.println("Fvirtualaddress");
		System.out.println(getObjJson(fvirtualaddress));
		FvirtualaddressWithdraw fvirtualaddresswithdraw = new FvirtualaddressWithdraw();
		System.out.println("FvirtualaddressWithdraw");
		System.out.println(getObjJson(fvirtualaddresswithdraw));
		Fvirtualcaptualoperation fvirtualcaptualoperation = new Fvirtualcaptualoperation();
		System.out.println("Fvirtualcaptualoperation");
		System.out.println(getObjJson(fvirtualcaptualoperation));
		Fvirtualcointype fvirtualcointype = new Fvirtualcointype();
		System.out.println("Fvirtualcointype");
		System.out.println(getObjJson(fvirtualcointype));
		Fvirtualoperationlog fvirtualoperationlog = new Fvirtualoperationlog();
		System.out.println("Fvirtualoperationlog");
		System.out.println(getObjJson(fvirtualoperationlog));
		Fvirtualwallet fvirtualwallet = new Fvirtualwallet();
		System.out.println("Fvirtualwallet");
		System.out.println(getObjJson(fvirtualwallet));
		Fwebbaseinfo fwebbaseinfo = new Fwebbaseinfo();
		System.out.println("Fwebbaseinfo");
		System.out.println(getObjJson(fwebbaseinfo));
		Fwithdrawfees fwithdrawfees = new Fwithdrawfees();
		System.out.println("Fwithdrawfees");
		System.out.println(getObjJson(fwithdrawfees));
		Systembankinfo systembankinfo = new Systembankinfo();
		System.out.println("Systembankinfo");
		System.out.println(getObjJson(systembankinfo));


	}
}
