package com.gt.comm;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

import com.gt.util.Utils;

public class DoubleCut extends TagSupport {
	private Double value;
	private String pattern;
	private Integer maxIntegerDigits ;
	private Integer maxFractionDigits ;
	   public void setValue(Double value) {
//	      this.value = value;
		   try {
			   this.value =Double.parseDouble((String)ExpressionEvaluatorManager.evaluate("value", value.toString(), Object.class, this, (PageContext) pageContext));
		} catch (JspException e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
		}
	   }
	   public void setMaxFractionDigits(Integer maxFractionDigits){
		   this.maxFractionDigits = maxFractionDigits ;
	   }
  public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	public void setMaxIntegerDigits(Integer maxIntegerDigits) {
		this.maxIntegerDigits = maxIntegerDigits;
	}
	
	
@Override
	public int doEndTag() throws JspException {

    try {
		JspWriter out = pageContext.getOut();
		String res = Utils.getDoubleS(value, maxFractionDigits) ;
		out.println( res );
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

		return super.doEndTag();
	}
public void doTag() throws JspException, IOException {
          JspWriter out = pageContext.getOut();
          out.println( Utils.getDoubleS(value, maxFractionDigits) );
  }
}