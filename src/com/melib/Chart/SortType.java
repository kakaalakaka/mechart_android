package com.melib.Chart;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public enum SortType {
	ASC, DESC, NONE;

	private SortType() {
	}

	public int GetValue() {
		return ordinal();
	}

	public static SortType ForValue(int value) {
		return values()[value];
	}
}
