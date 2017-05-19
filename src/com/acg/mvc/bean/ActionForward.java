package com.acg.mvc.bean;
/**
 * 视图跳转对象
 * @author Administrator
 *
 */
public class ActionForward {
	private String name;
	private String url;
	
	public ActionForward(){}
	public ActionForward(String name, String url) {
		super();
		this.name = name;
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
//	@Override
//	public boolean equals(Object forward) {
//		if(forward != null){
//			if(forward instanceof ActionForward){
//				if(this.name.equals(((ActionForward) forward).getName())){
//					return true;
//				}
//			}
//		}
//		return false;
//	}
	
	
}
