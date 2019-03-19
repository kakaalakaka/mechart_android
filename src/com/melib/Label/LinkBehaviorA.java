package com.melib.Label;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public enum LinkBehaviorA {
	AlwaysUnderLine, HoverUnderLine, NeverUnderLine;

	private LinkBehaviorA() {
	}

	public int GetValue() {
		return ordinal();
	}

	public static LinkBehaviorA ForValue(int value) {
		return values()[value];
	}
}
