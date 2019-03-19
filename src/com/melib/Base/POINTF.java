package com.melib.Base;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class POINTF {
	public float x;
	public float y;

	public POINTF() {
	}

	public POINTF(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public POINTF Clone() {
		POINTF varCopy = new POINTF();

		varCopy.x = this.x;
		varCopy.y = this.y;

		return varCopy;
	}
}
