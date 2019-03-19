package com.melib.Grid;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public enum GridSelectionMode {
	SelectCell, SelectFullColumn, SelectFullRow, SelectNone;

	private GridSelectionMode() {
	}

	public int GetValue() {
		return ordinal();
	}

	public static GridSelectionMode ForValue(int value) {
		return values()[value];
	}
}
