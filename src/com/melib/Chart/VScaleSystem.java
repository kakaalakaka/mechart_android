package com.melib.Chart;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public enum VScaleSystem {
	Logarithmic, Standard;

	private VScaleSystem() {
	}

	public int GetValue() {
		return ordinal();
	}

	public static VScaleSystem ForValue(int value) {
		return values()[value];
	}
}
