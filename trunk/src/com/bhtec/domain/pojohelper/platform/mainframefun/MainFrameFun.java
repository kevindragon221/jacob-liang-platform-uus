/**
 * 功能说明：主頁面功能區vo
 * @author jacobliang
 * @time @Jul 24, 2010 @4:36:39 PM
 */
package com.bhtec.domain.pojohelper.platform.mainframefun;

public class MainFrameFun{
	private String funId;
	private String funName;
	private String funURI;
	private String funURIDesc;
	private String funMemo;
	public String getFunName() {
		return funName;
	}
	public void setFunName(String funName) {
		this.funName = funName;
	}
	public String getFunURI() {
		return funURI;
	}
	public void setFunURI(String funURI) {
		this.funURI = funURI;
	}
	public String getFunMemo() {
		return funMemo;
	}
	public void setFunMemo(String funMemo) {
		this.funMemo = funMemo;
	}
	public String getFunId() {
		return funId;
	}
	public void setFunId(String funId) {
		this.funId = funId;
	}

	public boolean equals(Object obj){
		if(this.getClass() != obj.getClass())return false;
		MainFrameFun mainFrameFun = (MainFrameFun)obj;
		if(this.getFunURI().equals(mainFrameFun.getFunURI()))
			return true;
		return false;
	}
	public String getFunURIDesc() {
		return funURIDesc;
	}
	public void setFunURIDesc(String funURIDesc) {
		this.funURIDesc = funURIDesc;
	}
}
