package com.melib.Chart;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public enum VScaleType {
	Divide, EqualDiff, EqualRatio, GoldenRatio, Percent;

	private VScaleType() {
	}

	public int GetValue() {
		return ordinal();
	}

	public static VScaleType ForValue(int value) {
		return values()[value];
	}
}
