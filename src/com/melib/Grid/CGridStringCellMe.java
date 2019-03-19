package com.melib.Grid;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public class CGridStringCellMe extends CGridCellMe {
	protected String m_value;

	public CGridStringCellMe() {
	}

	public CGridStringCellMe(String value) {
		this.m_value = value;
	}

	public int CompareTo(CGridCellMe cell) {
		String value = cell.GetString();
		String target = GetString();
		if (target != null) {
			return target.compareTo(value);
		}
		return -1;
	}

	public String GetString() {
		return this.m_value;
	}

	public void SetString(String value) {
		this.m_value = value;
	}
}
