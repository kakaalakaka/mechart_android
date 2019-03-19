package com.melib.Base;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public enum MirrorMode {
	BugHole, None, Shadow;

	private MirrorMode() {
	}

	public int GetValue() {
		return ordinal();
	}

	public static MirrorMode ForValue(int value) {
		return values()[value];
	}
}
