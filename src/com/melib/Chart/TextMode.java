package com.melib.Chart;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public enum TextMode {
	Field, Full, None, Value;

	private TextMode() {
	}

	public int GetValue() {
		return ordinal();
	}

	public static TextMode ForValue(int value) {
		return values()[value];
	}
}
