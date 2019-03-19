package com.melib.Chart;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public enum CandleStyle {
	American, CloseLine, Rect, Tower;

	private CandleStyle() {
	}

	public int GetValue() {
		return ordinal();
	}

	public static CandleStyle ForValue(int value) {
		return values()[value];
	}
}
