package com.melib.Chart;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class CRowMe {
	private CListMe<Double> m_values;

	public CRowMe() {
		this.m_values = new CListMe();
	}

	public CRowMe(int capacity, int step) {
		this.m_values = new CListMe();
		this.m_values.set_capacity(capacity);
		this.m_values.set_step(capacity);
	}

	public CRowMe(double[] ary, int size) {
		this.m_values = new CListMe();
		for (int i = 0; i < size; i++) {
			this.m_values.push_back(Double.valueOf(ary[i]));
		}
	}

	protected void finalize() throws Throwable {
		Dispose();
	}

	public void Clear() {
		if (this.m_values != null) {
			this.m_values.clear();
		}
	}

	public void Dispose() {
		if (this.m_values != null) {
			this.m_values.Dispose();
			this.m_values = null;
		}
	}

	public void FillEmpty(int columns) {
		if (this.m_values != null) {
			int size = this.m_values.size();
			if (size >= 0) {
				for (int i = size; i < columns; i++) {
					this.m_values.insert(i, Double.valueOf(Double.NaN));
				}
			}
		}
	}

	public double Get(int index) {
		if (index != -1) {
			return ((Double) this.m_values.get(index)).doubleValue();
		}
		return Double.NaN;
	}

	public void Remove(int index) {
		if ((index != -1) && (this.m_values != null)) {
			this.m_values.remove_at(index);
		}
	}

	public void Set(int index, double value) {
		this.m_values.set(index, Double.valueOf(value));
	}
}
