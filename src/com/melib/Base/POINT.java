package com.melib.Base;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class POINT {
	public int x;
	public int y;

	public POINT() {
	}

	public POINT(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public POINT(float x, float y) {
		this.x = ((int) x);
		this.y = ((int) y);
	}

	public POINT Clone() {
		POINT varCopy = new POINT();

		varCopy.x = this.x;
		varCopy.y = this.y;

		return varCopy;
	}
}
