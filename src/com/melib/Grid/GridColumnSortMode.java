package com.melib.Grid;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public enum GridColumnSortMode {
	Asc, Desc, None;

	private GridColumnSortMode() {
	}

	public int GetValue() {
		return ordinal();
	}

	public static GridColumnSortMode ForValue(int value) {
		return values()[value];
	}
}
