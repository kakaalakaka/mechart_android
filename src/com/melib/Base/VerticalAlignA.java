package com.melib.Base;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public enum VerticalAlignA {
	Bottom, Inherit, Middle, Top;

	private VerticalAlignA() {
	}

	public int GetValue() {
		return ordinal();
	}

	public static VerticalAlignA ForValue(int value) {
		return values()[value];
	}
}
