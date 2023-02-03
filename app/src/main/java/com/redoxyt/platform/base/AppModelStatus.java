package com.redoxyt.platform.base;

import java.io.Serializable;

/** 
 * @author PutinMa
 * @date 2016年4月27日 
 * @Description: 状态基类  
 **/
public class AppModelStatus implements Serializable {
	private static final long serialVersionUID = 1L;
	public boolean isSelected=false;
	public boolean isChecked=false;
	public boolean isFocused=false;
	public boolean isAll=false;
}
