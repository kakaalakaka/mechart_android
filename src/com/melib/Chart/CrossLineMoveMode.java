package com.melib.Chart;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public enum CrossLineMoveMode {
	AfterClick, FollowMouse;

	private CrossLineMoveMode() {
	}

	public int GetValue() {
		return ordinal();
	}

	public static CrossLineMoveMode ForValue(int value) {
		return values()[value];
	}
}
