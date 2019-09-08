package com.gt.controller.front;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gt.controller.BaseController;
import com.gt.service.front.FrontVirtualCoinService;

@Controller
public class FrontQuotationsController extends BaseController {
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService ;

	
	
	@RequestMapping("/json")
	public ModelAndView json(
			HttpServletRequest request
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("front/real/json") ;
		return modelAndView ;
	}
}
