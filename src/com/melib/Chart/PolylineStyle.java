package com.melib.Chart;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public enum PolylineStyle {
	Cycle, DashLine, DotLine, SolidLine;

	private PolylineStyle() {
	}

	public int GetValue() {
		return ordinal();
	}

	public static PolylineStyle ForValue(int value) {
		return values()[value];
	}
}
