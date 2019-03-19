package com.melib.Grid;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class CGridBoolCellMe extends CGridCellMe {
	protected boolean m_value;

	public CGridBoolCellMe() {
	}

	public CGridBoolCellMe(boolean value) {
		this.m_value = value;
	}

	public int CompareTo(CGridCellMe cell) {
		boolean value = GetBool();
		boolean target = cell.GetBool();
		if ((value) && (!target)) {
			return 1;
		}
		if ((!value) && (target)) {
			return -1;
		}
		return 0;
	}

	public boolean GetBool() {
		return this.m_value;
	}

	public double GetDouble() {
		return this.m_value ? 1.0D : 0.0D;
	}

	public float GetFloat() {
		return this.m_value ? 1.0F : 0.0F;
	}

	public int GetInt() {
		return this.m_value ? 1 : 0;
	}

	public long GetLong() {
		return this.m_value ? 1L : 0L;
	}

	public String GetString() {
		return this.m_value ? "true" : "false";
	}

	public void SetBool(boolean value) {
		this.m_value = value;
	}

	public void SetDouble(double value) {
		this.m_value = (value > 0.0D);
	}

	public void SetFloat(float value) {
		this.m_value = (value > 0.0F);
	}

	public void SetInt(int value) {
		this.m_value = (value > 0);
	}

	public void SetLong(long value) {
		this.m_value = (value > 0L);
	}

	public void SetString(String value) {
		this.m_value = (value.equals("true"));
	}
}
