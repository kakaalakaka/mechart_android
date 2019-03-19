package com.melib.Base;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public enum MouseButtonsA {
	Left, None, Right;

	private MouseButtonsA() {
	}

	public int GetValue() {
		return ordinal();
	}

	public static MouseButtonsA ForValue(int value) {
		return values()[value];
	}
}
