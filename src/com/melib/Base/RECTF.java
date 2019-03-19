package com.melib.Base;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class RECTF {
	public float left;
	public float top;
	public float right;
	public float bottom;

	public RECTF() {
	}

	public RECTF(float left, float top, float right, float bottom) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
	}

	public RECTF Clone() {
		RECTF varCopy = new RECTF();

		varCopy.left = this.left;
		varCopy.top = this.top;
		varCopy.right = this.right;
		varCopy.bottom = this.bottom;

		return varCopy;
	}
}
