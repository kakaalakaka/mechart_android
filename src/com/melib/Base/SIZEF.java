package com.melib.Base;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class SIZEF {
	public float cx;
	public float cy;

	public SIZEF() {
	}

	public SIZEF(float cx, float cy) {
		this.cx = cx;
		this.cy = cy;
	}

	public SIZEF Clone() {
		SIZEF varCopy = new SIZEF();
		varCopy.cx = this.cx;
		varCopy.cy = this.cy;
		return varCopy;
	}
}
