package com.melib.Chart.Plot;

import java.util.HashMap;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public enum ActionType {
	AT1(1), AT2(2), AT3(3), AT4(4), MOVE(0), NO(-1);

	private int intValue;
	private static HashMap<Integer, ActionType> mappings;

	private static synchronized HashMap<Integer, ActionType> GetMappings() {
		if (mappings == null) {
			mappings = new HashMap();
		}
		return mappings;
	}

	private ActionType(int value) {
		this.intValue = value;
		GetMappings().put(Integer.valueOf(value), this);
	}

	public int GetValue() {
		return this.intValue;
	}

	public static ActionType ForValue(int value) {
		return (ActionType) GetMappings().get(Integer.valueOf(value));
	}
}
