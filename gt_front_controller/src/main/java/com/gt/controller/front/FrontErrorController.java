package com.gt.controller.front;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gt.controller.BaseController;



@Controller
public class FrontErrorController extends BaseController {
	
	@RequestMapping("/error/error")
	public ModelAndView error404(
			HttpServletRequest request
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("front/error/error") ;
		
		if(this.isMobile(request))
		{
			modelAndView.setViewName("mobile/error/error") ;
		}
		
		return modelAndView ;
	}
}
