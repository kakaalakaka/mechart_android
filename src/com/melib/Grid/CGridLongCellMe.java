package com.melib.Grid;

import com.melib.Base.CStrMe;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public class CGridLongCellMe extends CGridCellMe {
	protected long m_value;

	public CGridLongCellMe() {
	}

	public CGridLongCellMe(long value) {
		this.m_value = value;
	}

	public int CompareTo(CGridCellMe cell) {
		long value = GetLong();
		long target = cell.GetLong();
		if (value > target) {
			return 1;
		}
		if (value < target) {
			return -1;
		}

		return 0;
	}

	public boolean GetBool() {
		return this.m_value != 0L;
	}

	public double GetDouble() {
		return this.m_value;
	}

	public float GetFloat() {
		return (float) this.m_value;
	}

	public int GetInt() {
		return (int) this.m_value;
	}

	public long GetLong() {
		return this.m_value;
	}

	public String GetString() {
		return new Long(this.m_value).toString();
	}

	public void SetBool(boolean value) {
		this.m_value = (value ? 1L : 0L);
	}

	public void SetDouble(double value) {
		this.m_value = (long) (value);
	}

	public void SetFloat(float value) {
		this.m_value = (long) (value);
	}

	public void SetInt(int value) {
		this.m_value = value;
	}

	public void SetLong(long value) {
		this.m_value = value;
	}

	public void SetString(String value) {
		this.m_value = CStrMe.ConvertStrToInt(value);
	}
}
