package com.melib.Chart;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public enum SelectedPoint {
	Ellipse, Rectangle;

	private SelectedPoint() {
	}

	public int GetValue() {
		return ordinal();
	}

	public static SelectedPoint ForValue(int value) {
		return values()[value];
	}
}
