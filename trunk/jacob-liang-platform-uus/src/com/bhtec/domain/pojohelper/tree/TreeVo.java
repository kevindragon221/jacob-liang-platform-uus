/**
 *功能说明：
 * @author jacobliang
 * @time @Nov 25, 2010 @2:01:21 PM
 */
package com.bhtec.domain.pojohelper.tree;

import java.io.Serializable;

public class TreeVo implements Serializable {
	private String id;
	private String text;
	private String iconCls;
	private boolean leaf;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	
}
