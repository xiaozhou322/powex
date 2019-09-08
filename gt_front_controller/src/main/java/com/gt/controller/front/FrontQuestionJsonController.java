package com.gt.controller.front;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.HtmlUtils;

import com.gt.Enum.QuestionStatusEnum;
import com.gt.Enum.QuestionTypeEnum;
import com.gt.controller.BaseController;
import com.gt.entity.Fquestion;
import com.gt.entity.Fuser;
import com.gt.service.front.FrontQuestionService;
import com.gt.service.front.FrontUserService;
import com.gt.util.Utils;

import net.sf.json.JSONObject;

@Controller
public class FrontQuestionJsonController extends BaseController{
	
	@Autowired
	private FrontQuestionService frontQuestionService ;
	@Autowired
	private FrontUserService frontUserService;
	/*
	 * var param={questionType:questionType,desc:desc,name:name,phone:phone};
	 * */
	@ResponseBody
	@RequestMapping("/question/submitQuestion")
	public String submitQuestion(
			HttpServletRequest request,
			@RequestParam(required=true)int questiontype,
			@RequestParam(required=true)String questiondesc
			)throws Exception{
		JSONObject js = new JSONObject();
		String type = QuestionTypeEnum.getEnumString(questiontype,request);
		if(type == null || type.trim().length() ==0){
			js.accumulate("code", -1);
			js.accumulate("msg", getLocaleMessage(request,null,"json.user.illsubmit"));
			return js.toString();
		}
		
		questiondesc = HtmlUtils.htmlEscape(questiondesc) ;
        if(questiondesc.length() >500){
        	js.accumulate("code", -1);
			js.accumulate("msg", getLocaleMessage(request,null,"json.question.contentlong"));
			return js.toString();
        }
        
        Fuser fuser = this.frontUserService.findById(GetSession(request).getFid());
        if(!fuser.isFisTelephoneBind() && !fuser.getFgoogleBind() ){
        	js.accumulate("code", -1);
			js.accumulate("msg", getLocaleMessage(request,null,"json.account.bindinfo"));
			return js.toString();
        }
        if(fuser.getFtradePassword() == null ||fuser.getFtradePassword().trim().length() ==0){
        	js.accumulate("code", -1);
			js.accumulate("msg", getLocaleMessage(request,null,"json.user.settranfirst"));
			return js.toString();
        }
        if(!fuser.getFpostRealValidate()){
        	js.accumulate("code", -1);
			js.accumulate("msg", getLocaleMessage(request,null,"json.user.authfirst"));
			return js.toString();
        }
		
		Fquestion fquestion = new Fquestion() ;
		fquestion.setFuser(GetSession(request)) ;
		fquestion.setFcreateTime(Utils.getTimestamp()) ;
		fquestion.setFdesc(questiondesc) ;
		fquestion.setFstatus(QuestionStatusEnum.NOT_SOLVED) ;
		fquestion.setFtype(questiontype) ;

		this.frontQuestionService.save(fquestion) ;
		js.accumulate("code",0);
		js.accumulate("msg", getLocaleMessage(request,null,"json.question.waitreply"));
		return js.toString();
	}
	
	/* * /question/cancelQuestion.html?qid="+id+"&currentPage type=
	 * */
	@ResponseBody
	@RequestMapping("/question/delquestion")
	public String delquestion(
			HttpServletRequest request,
			@RequestParam(required=true)int fid
			) throws Exception{
		JSONObject js = new JSONObject();
		try {
			Fquestion fquestion = this.frontQuestionService.findById(fid) ;
			if(fquestion!=null && fquestion.getFuser().getFid()==GetSession(request).getFid()){
				this.frontQuestionService.delete(fquestion) ;
			}else{
				js.accumulate("code", -1);
				js.accumulate("msg", getLocaleMessage(request,null,"json.account.illoperation"));
				return js.toString();
			}
		} catch (Exception e) {
			js.accumulate("code", -1);
			js.accumulate("msg", getLocaleMessage(request,null,"json.account.networkanomaly"));
			return js.toString();
		}
		
		js.accumulate("code",0);
		js.accumulate("msg", getLocaleMessage(request,null,"json.question.delsuc"));
		return js.toString();
	}
	
	public static String getLocaleMessage(HttpServletRequest request,Object[] args,String code){
        WebApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
        return ac.getMessage(code,args, RequestContextUtils.getLocale(request));
    }
}
