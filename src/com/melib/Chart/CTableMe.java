package com.melib.Chart;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public abstract class CTableMe {
	public static int NULLFIELD = -1;

	private static int m_autoField = 10000;

	public static int GetAutoField() {
		return m_autoField++;
	}

	public abstract int GetColumnsCount();

	public abstract boolean IsDisposed();

	public abstract int GetRowsCount();

	public abstract void AddColumn(int paramInt);

	public abstract void AddRow(double paramDouble, double[] paramArrayOfDouble, int paramInt);

	public abstract void Clear();

	public abstract double[] DATA_ARRAY(int paramInt1, int paramInt2, int paramInt3);

	public abstract void Dispose();

	public abstract int GetColumnIndex(int paramInt);

	public abstract int GetRowIndex(double paramDouble);

	public abstract double GetXValue(int paramInt);

	public abstract double Get(double paramDouble, int paramInt);

	public abstract double Get2(int paramInt1, int paramInt2);

	public abstract double Get3(int paramInt1, int paramInt2);

	public abstract void Remove(double paramDouble);

	public abstract void RemoveAt(int paramInt);

	public abstract void RemoveColumn(int paramInt);

	public abstract void Set(double paramDouble1, int paramInt, double paramDouble2);

	public abstract void Set2(int paramInt1, int paramInt2, double paramDouble);

	public abstract void Set3(int paramInt1, int paramInt2, double paramDouble);

	public abstract void SetColsCapacity(int paramInt);

	public abstract void SetColsGrowStep(int paramInt);

	public abstract void SetRowsCapacity(int paramInt);

	public abstract void SetRowsGrowStep(int paramInt);
}
