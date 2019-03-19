package com.melib.TabControl;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public enum TabPageLayout {
	Bottom, Left, Right, Top;

	private TabPageLayout() {
	}

	public int GetValue() {
		return ordinal();
	}

	public static TabPageLayout ForValue(int value) {
		return values()[value];
	}
}
