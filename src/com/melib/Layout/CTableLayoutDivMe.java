package com.melib.Layout;

import java.util.ArrayList;
import com.melib.Base.CStrMe;
import com.melib.Base.CControlMe;
import com.melib.Base.PADDING;
import com.melib.Base.RECT;
import com.melib.Base.RefObject;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public class CTableLayoutDivMe extends CDivMe {
	private ArrayList<Integer> m_columns = new ArrayList();

	private ArrayList<Integer> m_rows = new ArrayList();

	private ArrayList<CControlMe> m_tableControls = new ArrayList();

	protected int m_columnsCount;

	public final int GetColumnsCount() {
		return this.m_columnsCount;
	}

	public final void SetColumnsCount(int value) {
		this.m_columnsCount = value;
	}

	public ArrayList<CColumnStyleMe> m_columnStyles = new ArrayList();
	protected int m_rowsCount;

	public final ArrayList<CColumnStyleMe> GetColumnStyles() {
		return this.m_columnStyles;
	}

	public final void SetColumnStyles(ArrayList<CColumnStyleMe> value) {
		this.m_columnStyles = value;
	}

	public final int GetRowsCount() {
		return this.m_rowsCount;
	}

	public final void SetRowsCount(int value) {
		this.m_rowsCount = value;
	}

	public ArrayList<CRowStyleMe> m_rowStyles = new ArrayList();

	public final ArrayList<CRowStyleMe> GetRowStyles() {
		return this.m_rowStyles;
	}

	public final void SetRowStyles(ArrayList<CRowStyleMe> value) {
		this.m_rowStyles = value;
	}

	public void AddControl(CControlMe control) {
		ArrayList<CControlMe> controls = GetControls();
		int size = controls.size();
		super.AddControl(control);
		int column = size % this.m_columnsCount;
		int row = size / this.m_columnsCount;
		this.m_columns.add(Integer.valueOf(column));
		this.m_rows.add(Integer.valueOf(row));
		this.m_tableControls.add(control);
	}

	public void AddControl(CControlMe control, int column, int row) {
		super.AddControl(control);
		this.m_columns.add(Integer.valueOf(column));
		this.m_rows.add(Integer.valueOf(row));
		this.m_tableControls.add(control);
	}

	public void Dispose() {
		if (!IsDisposed()) {
			this.m_columns.clear();
			this.m_columnStyles.clear();
			this.m_rows.clear();
			this.m_rowStyles.clear();
			this.m_tableControls.clear();
		}
		super.Dispose();
	}

	public String GetControlType() {
		return "TableLayoutDiv";
	}

	public void GetProperty(String name, RefObject<String> value, RefObject<String> type) {
		if (name.equals("columnscount")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertIntToStr(GetColumnsCount());
		} else if (name.equals("rowscount")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertIntToStr(GetRowsCount());
		} else {
			super.GetProperty(name, value, type);
		}
	}

	public ArrayList<String> GetPropertyNames() {
		ArrayList<String> propertyNames = super.GetPropertyNames();
		propertyNames.add("ColumnsCount");
		propertyNames.add("RowsCount");
		return propertyNames;
	}

	public boolean OnResetLayout() {
		if (GetNative() != null) {
			if ((this.m_columnsCount > 0) && (this.m_rowsCount > 0) && (this.m_columnStyles.size() > 0)
					&& (this.m_rowStyles.size() > 0)) {
				int width = GetWidth();
				int height = GetHeight();
				int count = this.m_tableControls.size();
				int[] columnWidths = new int[this.m_columnsCount];
				int[] rowHeights = new int[this.m_rowsCount];
				int allWidth = 0;
				int allHeight = 0;
				for (int i = 0; i < this.m_columnsCount; i++) {
					CColumnStyleMe columnStyleA = (CColumnStyleMe) this.m_columnStyles.get(i);
					int cWidth = 0;
					SizeTypeA sizeTyle = columnStyleA.GetSizeType();
					float sWidth = columnStyleA.GetWidth();
					if (sizeTyle == SizeTypeA.AbsoluteSize) {
						cWidth = (int) sWidth;
					} else if (sizeTyle == SizeTypeA.AutoFill) {
						cWidth = width - allWidth;
					} else if (sizeTyle == SizeTypeA.PercentSize) {
						cWidth = (int) (width * sWidth);
					}
					columnWidths[i] = cWidth;
					allWidth += cWidth;
				}
				for (int j = 0; j < this.m_rowsCount; j++) {
					CRowStyleMe rowStyle = (CRowStyleMe) this.m_rowStyles.get(j);
					int rHeight = 0;
					SizeTypeA sizeType = rowStyle.GetSizeType();
					float sHeight = rowStyle.GetHeight();
					if (sizeType == SizeTypeA.AbsoluteSize) {
						rHeight = (int) sHeight;
					} else if (sizeType == SizeTypeA.AutoFill) {
						rHeight = height - allHeight;
					} else if (sizeType == SizeTypeA.PercentSize) {
						rHeight = (int) (height * sHeight);
					}
					rowHeights[j] = rHeight;
					allHeight += rHeight;
				}

				for (int k = 0; k < count; k++) {
					CControlMe control = (CControlMe) this.m_tableControls.get(k);
					int column = ((Integer) this.m_columns.get(k)).intValue();
					int row = ((Integer) this.m_rows.get(k)).intValue();
					PADDING margin = control.GetMargin();

					int cLeft = 0;
					int cTop = 0;

					for (int m = 0; m < column; m++) {
						cLeft += columnWidths[m];
					}

					for (int n = 0; n < row; n++) {
						cTop += rowHeights[n];
					}
					int cRight = cLeft + columnWidths[column] - margin.right;
					int cBottom = cTop + rowHeights[row] - margin.bottom;
					cLeft += margin.left;
					cTop += margin.top;
					if (cRight < cLeft) {
						cRight = cLeft;
					}
					if (cBottom < cTop) {
						cBottom = cTop;
					}
					control.SetBounds(new RECT(cLeft, cTop, cRight, cBottom));
				}
			}
		}
		return true;
	}

	public void RemoveControl(CControlMe control) {
		int count = this.m_tableControls.size();
		int index = -1;
		for (int i = 0; i < count; i++) {
			if (control == this.m_tableControls.get(i)) {
				index = i;
				break;
			}
		}
		if (index != -1) {
			this.m_columns.remove(index);
			this.m_rows.remove(index);
			this.m_tableControls.remove(index);
		}
		super.RemoveControl(control);
	}

	public void SetProperty(String name, String value) {
		if (name.equals("columnscount")) {
			SetColumnsCount(CStrMe.ConvertStrToInt(value));
		} else if (name.equals("rowscount")) {
			SetRowsCount(CStrMe.ConvertStrToInt(value));
		} else {
			super.SetProperty(name, value);
		}
	}

	public void Update() {
		OnResetLayout();
		int count = this.m_controls.size();
		for (int i = 0; i < count; i++) {
			((CControlMe) this.m_controls.get(i)).Update();
		}
		UpdateScrollBar();
	}
}
