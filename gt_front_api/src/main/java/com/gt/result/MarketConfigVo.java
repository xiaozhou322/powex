package com.gt.result;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 市场配置VO
 * @author zhouyong
 *
 */
public class MarketConfigVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8563150578122863370L;
	
	/** 法币名称  **/
	private String fbType;
	
	/** 法币id **/
	private String fbId;
	
	/** 子属性 **/
	private List<Map<String,Object>> children;

	public String getFbType() {
		return fbType;
	}

	public void setFbType(String fbType) {
		this.fbType = fbType;
	}

	public String getFbId() {
		return fbId;
	}

	public void setFbId(String fbId) {
		this.fbId = fbId;
	}

	public List<Map<String,Object>> getChildren() {
		return children;
	}

	public void setChildren(List<Map<String,Object>> children) {
		this.children = children;
	}
	

}
