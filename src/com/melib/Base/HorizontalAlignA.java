package com.melib.Base;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public enum HorizontalAlignA {
	Center, Right, Inherit, Left;

	private HorizontalAlignA() {
	}

	public int GetValue() {
		return ordinal();
	}

	public static HorizontalAlignA ForValue(int value) {
		return values()[value];
	}
}
