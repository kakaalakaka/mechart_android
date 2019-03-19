package com.melib.Chart;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public enum BarStyle {
	Line, Rect;

	private BarStyle() {
	}

	public int GetValue() {
		return ordinal();
	}

	public static BarStyle ForValue(int value) {
		return values()[value];
	}
}
