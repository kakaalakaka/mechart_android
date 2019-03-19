package com.melib.Chart;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public enum NumberStyle {
	Standard, UnderLine;

	private NumberStyle() {
	}

	public int GetValue() {
		return ordinal();
	}

	public static NumberStyle ForValue(int value) {
		return values()[value];
	}
}
