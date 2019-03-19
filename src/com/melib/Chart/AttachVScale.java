package com.melib.Chart;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public enum AttachVScale {
	Left, Right;

	private AttachVScale() {
	}

	public int GetValue() {
		return ordinal();
	}

	public static AttachVScale ForValue(int value) {
		return values()[value];
	}
}
