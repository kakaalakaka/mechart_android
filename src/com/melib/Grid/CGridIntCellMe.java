package com.melib.Grid;

import com.melib.Base.CStrMe;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public class CGridIntCellMe extends CGridCellMe {
	protected int m_value;

	public CGridIntCellMe() {
	}

	public CGridIntCellMe(int value) {
		this.m_value = value;
	}

	public int CompareTo(CGridCellMe cell) {
		int value = GetInt();
		int target = cell.GetInt();
		if (value > target) {
			return 1;
		}
		if (value < target) {
			return -1;
		}

		return 0;
	}

	public boolean GetBool() {
		return this.m_value != 0;
	}

	public double GetDouble() {
		return this.m_value;
	}

	public float GetFloat() {
		return this.m_value;
	}

	public int GetInt() {
		return this.m_value;
	}

	public long GetLong() {
		return this.m_value;
	}

	public String GetString() {
		return new Integer(this.m_value).toString();
	}

	public void SetBool(boolean value) {
		this.m_value = (value ? 1 : 0);
	}

	public void SetDouble(double value) {
		this.m_value = ((int) value);
	}

	public void SetFloat(float value) {
		this.m_value = ((int) value);
	}

	public void SetInt(int value) {
		this.m_value = value;
	}

	public void SetLong(long value) {
		this.m_value = ((int) value);
	}

	public void SetString(String value) {
		this.m_value = CStrMe.ConvertStrToInt(value);
	}
}
