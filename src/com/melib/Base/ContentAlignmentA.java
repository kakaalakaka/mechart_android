package com.melib.Base;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public enum ContentAlignmentA {
	BottomCenter, BottomLeft, BottomRight, MiddleCenter, MiddleLeft, MiddleRight, TopCenter, TopLeft, TopRight;

	private ContentAlignmentA() {
	}

	public int GetValue() {
		return ordinal();
	}

	public static ContentAlignmentA ForValue(int value) {
		return values()[value];
	}
}
