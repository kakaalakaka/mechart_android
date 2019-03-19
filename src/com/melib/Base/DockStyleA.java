package com.melib.Base;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public enum DockStyleA {
	Bottom, Fill, Left, None, Right, Top;

	private DockStyleA() {
	}

	public int GetValue() {
		return ordinal();
	}

	public static DockStyleA ForValue(int value) {
		return values()[value];
	}
}
