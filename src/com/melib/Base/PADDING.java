package com.melib.Base;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class PADDING {
	public int bottom;
	public int left;
	public int right;
	public int top;

	public PADDING() {
	}

	public PADDING(int all) {
		this.bottom = all;
		this.left = all;
		this.right = all;
		this.top = all;
	}

	public PADDING(int left, int top, int right, int bottom) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
	}

	public PADDING Clone() {
		PADDING varCopy = new PADDING();

		varCopy.bottom = this.bottom;
		varCopy.left = this.left;
		varCopy.right = this.right;
		varCopy.top = this.top;

		return varCopy;
	}
}
