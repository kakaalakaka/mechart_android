package com.melib.Grid;

import com.melib.Base.CStrMe;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public class CGridFloatCellMe extends CGridCellMe {
	protected float m_value;

	public CGridFloatCellMe() {
	}

	public CGridFloatCellMe(float value) {
		this.m_value = value;
	}

	public int CompareTo(CGridCellMe cell) {
		float value = GetFloat();
		float target = cell.GetFloat();
		if (value > target) {
			return 1;
		}
		if (value < target) {
			return -1;
		}

		return 0;
	}

	public boolean GetBool() {
		return this.m_value != 0.0F;
	}

	public double GetDouble() {
		return this.m_value;
	}

	public float GetFloat() {
		return this.m_value;
	}

	public int GetInt() {
		return (int) this.m_value;
	}

	public long GetLong() {
		return (long) this.m_value;
	}

	public String GetString() {
		return new Float(this.m_value).toString();
	}

	public void SetBool(boolean value) {
		this.m_value = (value ? 1.0F : 0.0F);
	}

	public void SetDouble(double value) {
		this.m_value = ((float) value);
	}

	public void SetFloat(float value) {
		this.m_value = value;
	}

	public void SetInt(int value) {
		this.m_value = value;
	}

	public void SetLong(long value) {
		this.m_value = ((float) value);
	}

	public void SetString(String value) {
		this.m_value = ((float) CStrMe.ConvertStrToDouble(value));
	}
}
