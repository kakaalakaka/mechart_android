package com.melib.Grid;

import com.melib.Base.CStrMe;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public class CGridDoubleCellMe extends CGridCellMe {
	protected double m_value;

	public CGridDoubleCellMe() {
	}

	public CGridDoubleCellMe(double value) {
		this.m_value = value;
	}

	public int CompareTo(CGridCellMe cell) {
		double value = GetDouble();
		double target = cell.GetDouble();
		if (value > target) {
			return 1;
		}
		if (value < target) {
			return -1;
		}

		return 0;
	}

	public boolean GetBool() {
		return this.m_value != 0.0D;
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
		return (long) this.m_value;
	}

	public String GetString() {
		return new Double(this.m_value).toString();
	}

	public void SetBool(boolean value) {
		this.m_value = (value ? 1.0D : 0.0D);
	}

	public void SetDouble(double value) {
		this.m_value = value;
	}

	public void SetFloat(float value) {
		this.m_value = value;
	}

	public void SetInt(int value) {
		this.m_value = value;
	}

	public void SetLong(long value) {
		this.m_value = value;
	}

	public void SetString(String value) {
		this.m_value = CStrMe.ConvertStrToDouble(value);
	}
}
