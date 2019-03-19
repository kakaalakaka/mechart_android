package com.melib.Base;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public enum LayoutStyleA {
	BottomToTop, LeftToRight, None, RightToLeft, TopToBottom;

	private LayoutStyleA() {
	}

	public int GetValue() {
		return ordinal();
	}

	public static LayoutStyleA ForValue(int value) {
		return values()[value];
	}
}
