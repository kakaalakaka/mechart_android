package com.melib.Base;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class RECT {
	public int left;
	public int top;
	public int right;
	public int bottom;

	public RECT() {
	}

	public RECT(int left, int top, int right, int bottom) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
	}

	public RECT(float left, float top, float right, float bottom) {
		this.left = ((int) left);
		this.top = ((int) top);
		this.right = ((int) right);
		this.bottom = ((int) bottom);
	}

	public RECT Clone() {
		RECT varCopy = new RECT();

		varCopy.left = this.left;
		varCopy.top = this.top;
		varCopy.right = this.right;
		varCopy.bottom = this.bottom;

		return varCopy;
	}
}
