package com.melib.Chart;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class CTableExMe extends CTableMe {
	protected void finalize() throws Throwable {
		Dispose();
	}

	protected int m_colsCapacity = 4;

	protected int m_colsStep = 4;

	protected CListMe<CColumnMe> m_columns = new CListMe();

	protected CListMe<Double> m_keys = new CListMe();

	protected CListMe<CRowMe> m_rows = new CListMe();

	public int GetColumnsCount() {
		return this.m_columns.size();
	}

	private boolean m_isDisposed = false;

	public boolean IsDisposed() {
		return this.m_isDisposed;
	}

	public int GetRowsCount() {
		if (this.m_keys.size() != 0) {
			return this.m_keys.size();
		}
		return 0;
	}

	public void AddColumn(int colName) {
		CColumnMe column = new CColumnMe(colName, this.m_columns.size());
		if (this.m_columns.size() == 0) {
			this.m_columns.push_back(column);
		} else {
			int begin = 0;
			int end = this.m_columns.size() - 1;
			int sub = end - begin;
			while (sub > 1) {
				int half = begin + sub / 2;
				int hf = ((CColumnMe) this.m_columns.get(half)).m_name;
				if (hf > colName) {
					end = half;
				} else if (hf < colName) {
					begin = half;
				}
				sub = end - begin;
			}
			if (colName < ((CColumnMe) this.m_columns.get(begin)).m_name) {
				this.m_columns.insert(begin, column);
				FillEmpty();
				return;
			}
			if (colName > ((CColumnMe) this.m_columns.get(end)).m_name) {
				this.m_columns.insert(end + 1, column);
				FillEmpty();
				return;
			}

			this.m_columns.insert(begin + 1, column);
			FillEmpty();
			return;
		}
	}

	private CRowMe AddKey(double num) {
		if ((this.m_keys.size() == 0) || (num > ((Double) this.m_keys.get(this.m_keys.size() - 1)).doubleValue())) {
			this.m_keys.push_back(Double.valueOf(num));
			CRowMe newRow = new CRowMe(this.m_colsCapacity, this.m_colsStep);
			this.m_rows.push_back(newRow);
			return newRow;
		}

		int begin = 0;
		int end = this.m_keys.size() - 1;
		int sub = end - begin;
		while (sub > 1) {
			int half = begin + sub / 2;
			double hf = ((Double) this.m_keys.get(half)).doubleValue();
			if (hf > num) {
				end = half;
			} else if (hf < num) {
				begin = half;
			}
			sub = end - begin;
		}
		if (num < ((Double) this.m_keys.get(begin)).doubleValue()) {
			this.m_keys.insert(begin, Double.valueOf(num));
			CRowMe newRow = new CRowMe(this.m_colsCapacity, this.m_colsStep);
			this.m_rows.insert(begin, newRow);
			return newRow;
		}
		if (num > ((Double) this.m_keys.get(end)).doubleValue()) {
			this.m_keys.insert(end + 1, Double.valueOf(num));
			CRowMe newRow = new CRowMe(this.m_colsCapacity, this.m_colsStep);
			this.m_rows.insert(end + 1, newRow);
			return newRow;
		}

		this.m_keys.insert(begin + 1, Double.valueOf(num));
		CRowMe newRow = new CRowMe(this.m_colsCapacity, this.m_colsStep);
		this.m_rows.insert(begin + 1, newRow);
		return newRow;
	}

	public void AddRow(double pk, double[] ary, int size) {
		this.m_keys.push_back(Double.valueOf(pk));
		CRowMe row = new CRowMe(ary, size);
		this.m_rows.push_back(row);
	}

	public void Clear() {
		if (this.m_keys != null) {
			this.m_keys.clear();
		}
		if (this.m_rows != null) {
			for (int i = 0; i < this.m_rows.size(); i++) {
				CRowMe row = (CRowMe) this.m_rows.get(i);
				if (row != null) {
					row.Dispose();
				}
			}
			this.m_rows.clear();
		}
	}

	public double[] DATA_ARRAY(int field, int index, int n) {
		if (index >= 0) {
			int arraylength = n;
			int start = 0;
			if (index < n - 1) {
				arraylength = index + 1;
			} else {
				start = index - n + 1;
			}
			if (arraylength == -1) {
				return new double[0];
			}
			double[] array = new double[arraylength];
			int columnIndex = GetColumnIndex(field);
			for (int i = start; i <= index; i++) {
				array[(i - start)] = Get3(i, columnIndex);
			}
			return array;
		}

		return new double[0];
	}

	public void Dispose() {
		if (!this.m_isDisposed) {
			Clear();
			if (this.m_columns != null) {
				this.m_columns.Dispose();
				this.m_columns = null;
			}
			if (this.m_keys != null) {
				this.m_keys.Dispose();
				this.m_keys = null;
			}
			if (this.m_rows != null) {
				this.m_rows.Dispose();
				this.m_rows = null;
			}
			this.m_isDisposed = true;
		}
	}

	private void FillEmpty() {
		int colSize = this.m_columns.size();
		for (int i = 0; i < this.m_rows.size(); i++) {
			((CRowMe) this.m_rows.get(i)).FillEmpty(colSize);
		}
	}

	public int GetColumnIndex(int colName) {
		if ((colName == CTableMe.NULLFIELD) || (this.m_columns == null)) {
			return -1;
		}
		int begin = 0;
		int end = this.m_columns.size() - 1;
		int sub = end - begin;
		int index = -1;
		while (sub >= 0) {
			int half = begin + sub / 2;
			CColumnMe hf = (CColumnMe) this.m_columns.get(half);
			if (hf.m_name == colName) {
				index = hf.m_index;
				break;
			}
			if ((half == begin) || (half == end)) {
				CColumnMe ce = (CColumnMe) this.m_columns.get(end);
				if (ce.m_name == colName) {
					index = ce.m_index;
					break;
				}
				CColumnMe be = (CColumnMe) this.m_columns.get(begin);
				if (be.m_name == colName) {
					index = be.m_index;
					break;
				}
				index = -1;
				break;
			}
			if (hf.m_name > colName) {
				end = half;
			} else if (hf.m_name < colName) {
				begin = half;
			}
			sub = end - begin;
		}
		return index;
	}

	public int GetRowIndex(double key) {
		if (this.m_keys == null) {
			return -1;
		}
		int begin = 0;
		int end = this.m_keys.size() - 1;
		int sub = end - begin;
		int index = -1;
		while (sub >= 0) {
			int half = begin + sub / 2;
			double hf = ((Double) this.m_keys.get(half)).doubleValue();
			if (hf == key) {
				index = half;
				break;
			}
			if ((half == begin) || (half == end)) {
				if (((Double) this.m_keys.get(end)).doubleValue() == key) {
					index = end;
					break;
				}
				if (((Double) this.m_keys.get(begin)).doubleValue() != key)
					break;
				index = begin;
				break;
			}

			if (hf > key) {
				end = half;
			} else if (hf < key) {
				begin = half;
			}
			sub = end - begin;
		}
		return index;
	}

	public double GetXValue(int index) {
		try {
			return ((Double) this.m_keys.get(index)).doubleValue();
		} catch (Exception e) {
		}

		return 0.0D;
	}

	public double Get(double pk, int colName) {
		try {
			int rowIndex = GetRowIndex(pk);
			int colIndex = GetColumnIndex(colName);
			return ((CRowMe) this.m_rows.get(rowIndex)).Get(colIndex);
		} catch (Exception e) {
		}

		return Double.NaN;
	}

	public double Get2(int rowIndex, int colName) {
		try {
			if ((rowIndex >= 0) && (rowIndex < this.m_rows.size())) {
				int colIndex = GetColumnIndex(colName);
				return ((CRowMe) this.m_rows.get(rowIndex)).Get(colIndex);
			}

			return Double.NaN;
		} catch (Exception e) {
		}

		return Double.NaN;
	}

	public double Get3(int rowIndex, int colIndex) {
		try {
			if ((rowIndex >= 0) && (rowIndex < this.m_rows.size())) {
				return ((CRowMe) this.m_rows.get(rowIndex)).Get(colIndex);
			}

			return Double.NaN;
		} catch (Exception e) {
		}

		return Double.NaN;
	}

	public void Remove(double pk) {
		try {
			int index = GetRowIndex(pk);
			this.m_keys.remove_at(index);
			CRowMe row = (CRowMe) this.m_rows.get(index);
			row.Clear();
			this.m_rows.remove_at(index);
		} catch (Exception localException) {
		}
	}

	public void RemoveAt(int rowIndex) {
		try {
			this.m_keys.remove_at(rowIndex);
			CRowMe row = (CRowMe) this.m_rows.get(rowIndex);
			row.Clear();
			this.m_rows.remove_at(rowIndex);
		} catch (Exception localException) {
		}
	}

	public void RemoveColumn(int colName) {
		int colIndex = GetColumnIndex(colName);
		if (colIndex != -1) {
			int colSize = this.m_columns.size();
			int removeIndex = -1;
			for (int i = 0; i < colSize; i++) {
				CColumnMe col = (CColumnMe) this.m_columns.get(i);
				int name = col.m_name;
				int index = col.m_index;
				if (name == colName) {
					removeIndex = i;

				} else if (index > colIndex) {
					this.m_columns.set(i, new CColumnMe(name, index - 1));
				}
			}

			this.m_columns.remove_at(removeIndex);
			for (int i = 0; i < this.m_rows.size(); i++) {
				((CRowMe) this.m_rows.get(i)).Remove(colIndex);
				((CRowMe) this.m_rows.get(i)).FillEmpty(this.m_columns.size());
			}
		}
	}

	public void Set(double pk, int colName, double value) {
		CRowMe row = null;
		int index = GetRowIndex(pk);
		if (index == -1) {
			row = AddKey(pk);
			row.FillEmpty(this.m_columns.size());
		} else {
			row = (CRowMe) this.m_rows.get(index);
		}
		int colIndex = GetColumnIndex(colName);
		row.Set(colIndex, value);
	}

	public void Set2(int rowIndex, int colName, double value) {
		int colIndex = GetColumnIndex(colName);
		((CRowMe) this.m_rows.get(rowIndex)).Set(colIndex, value);
	}

	public void Set3(int rowIndex, int colIndex, double value) {
		((CRowMe) this.m_rows.get(rowIndex)).Set(colIndex, value);
	}

	public void SetColsCapacity(int capacity) {
		this.m_colsCapacity = capacity;
	}

	public void SetColsGrowStep(int step) {
		this.m_colsStep = step;
	}

	public void SetRowsCapacity(int capacity) {
		this.m_keys.set_capacity(capacity);
		this.m_rows.set_capacity(capacity);
	}

	public void SetRowsGrowStep(int step) {
		this.m_keys.set_step(step);
		this.m_rows.set_step(step);
	}
}
