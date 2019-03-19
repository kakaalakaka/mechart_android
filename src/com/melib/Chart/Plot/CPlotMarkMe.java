package com.melib.Chart.Plot;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class CPlotMarkMe {
	private int m_index;
	private double m_key;
	private double m_value;

	public CPlotMarkMe(int index, double key, double value) {
		this.m_index = index;
		this.m_key = key;
		this.m_value = value;
	}

	public int GetIndex() {
		return this.m_index;
	}

	public void SetIndex(int index) {
		this.m_index = index;
	}

	public double GetKey() {
		return this.m_key;
	}

	public void SetKey(double key) {
		this.m_key = key;
	}

	public double GetValue() {
		return this.m_value;
	}

	public void SetValue(double value) {
		this.m_value = value;
	}
}
