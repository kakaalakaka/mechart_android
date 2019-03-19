package com.melib.Base;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class ANCHOR {
	public boolean bottom;
	public boolean left;
	public boolean right;
	public boolean top;

	public ANCHOR() {
	}

	public ANCHOR(boolean left, boolean top, boolean right, boolean bottom) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
	}

	public ANCHOR Clone() {
		ANCHOR varCopy = new ANCHOR();
		varCopy.bottom = this.bottom;
		varCopy.left = this.left;
		varCopy.right = this.right;
		varCopy.top = this.top;
		return varCopy;
	}
}
