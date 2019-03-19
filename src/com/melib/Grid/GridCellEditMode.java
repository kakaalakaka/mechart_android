package com.melib.Grid;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public enum GridCellEditMode {
	DoubleClick, None, SingleClick;

	private GridCellEditMode() {
	}

	public int GetValue() {
		return ordinal();
	}

	public static GridCellEditMode ForValue(int value) {
		return values()[value];
	}
}
