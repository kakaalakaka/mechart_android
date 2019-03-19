package com.melib.Chart;

import java.util.HashMap;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public enum DateType {
	Day(2), Hour(3), Millisecond(6), Minute(4), Month(1), Second(5), Year(0);

	private int intValue;
	private static HashMap<Integer, DateType> mappings;

	private static synchronized HashMap<Integer, DateType> GetMappings() {
		if (mappings == null) {
			mappings = new HashMap();
		}
		return mappings;
	}

	private DateType(int value) {
		this.intValue = value;
		GetMappings().put(Integer.valueOf(value), this);
	}

	public int GetValue() {
		return this.intValue;
	}

	public static DateType ForValue(int value) {
		return (DateType) GetMappings().get(Integer.valueOf(value));
	}
}
