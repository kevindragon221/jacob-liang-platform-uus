/**
 *功能说明：
 * @author jacobliang
 * @time @Nov 25, 2010 @2:01:21 PM
 */
package com.bhtec.domain.pojohelper.tree;

import java.io.Serializable;

public class TreeVoExtend extends TreeVo implements Serializable {
	private static final long serialVersionUID = 10000L;
	private boolean checked;
	
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
