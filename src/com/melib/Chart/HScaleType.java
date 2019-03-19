package com.melib.Chart;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public enum HScaleType {
	Date, Number;

	private HScaleType() {
	}

	public int GetValue() {
		return ordinal();
	}

	public static HScaleType ForValue(int value) {
		return values()[value];
	}
}
