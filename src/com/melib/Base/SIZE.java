package com.melib.Base;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class SIZE {
	public int cx;
	public int cy;

	public SIZE() {
	}

	public SIZE(int cx, int cy) {
		this.cx = cx;
		this.cy = cy;
	}

	public SIZE Clone() {
		SIZE varCopy = new SIZE();

		varCopy.cx = this.cx;
		varCopy.cy = this.cy;

		return varCopy;
	}
}
