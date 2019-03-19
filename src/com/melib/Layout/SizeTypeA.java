package com.melib.Layout;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public enum SizeTypeA {
	AbsoluteSize, AutoFill, PercentSize;

	private SizeTypeA() {
	}

	public int GetValue() {
		return ordinal();
	}

	public static SizeTypeA ForValue(int value) {
		return values()[value];
	}
}
