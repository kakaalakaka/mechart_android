package com.melib.Grid;

import java.util.ArrayList;
import java.util.Arrays;
import com.melib.Base.COLOR;
import com.melib.Base.CPaintMe;
import com.melib.Base.CPropertyMe;
import com.melib.Base.CStrMe;
import com.melib.Base.FONT;
import com.melib.Base.HorizontalAlignA;
import com.melib.Base.POINT;
import com.melib.Base.RECT;
import com.melib.Base.RefObject;
import com.melib.Base.SIZE;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class CGridCellMe implements CPropertyMe {
	protected void finalize() throws Throwable {
		Dispose();
	}

	protected boolean m_allowEdit = false;

	public boolean AllowEdit() {
		return this.m_allowEdit;
	}

	public void SetAllowEdit(boolean value) {
		this.m_allowEdit = value;
	}

	protected int colSpan = 1;

	public int GetColSpan() {
		return this.colSpan;
	}

	public void SetColSpan(int value) {
		this.colSpan = value;
	}

	protected CGridColumnMe m_column = null;

	public CGridColumnMe GetColumn() {
		return this.m_column;
	}

	public void SetColumn(CGridColumnMe value) {
		this.m_column = value;
	}

	protected CGridMe m_grid = null;
	protected boolean m_isDisposed;
	protected String m_name;

	public CGridMe GetGrid() {
		return this.m_grid;
	}

	public void SetGrid(CGridMe value) {
		this.m_grid = value;
	}

	public boolean IsDisposed() {
		return this.m_isDisposed;
	}

	public String GetName() {
		return this.m_name;
	}

	public void SetName(String value) {
		this.m_name = value;
	}

	protected CGridRowMe m_row = null;

	public CGridRowMe GetRow() {
		return this.m_row;
	}

	public void SetRow(CGridRowMe value) {
		this.m_row = value;
	}

	protected int rowSpan = 1;

	public int GetRowSpan() {
		return this.rowSpan;
	}

	public void SetRowSpan(int value) {
		this.rowSpan = value;
	}

	protected GridCellStyle m_style = null;

	public GridCellStyle GetStyle() {
		return this.m_style;
	}

	public void SetStyle(GridCellStyle value) {
		this.m_style = value;
	}

	protected Object m_tag = null;

	public Object GetTag() {
		return this.m_tag;
	}

	public void SetTag(Object value) {
		this.m_tag = value;
	}

	public String GetText() {
		return GetString();
	}

	public void SetText(String value) {
		SetString(value);
	}

	public int CompareTo(CGridCellMe cell) {
		return 0;
	}

	public void Dispose() {
		this.m_isDisposed = true;
	}

	public boolean GetBool() {
		return false;
	}

	public double GetDouble() {
		return 0.0D;
	}

	public float GetFloat() {
		return 0.0F;
	}

	public int GetInt() {
		return 0;
	}

	public long GetLong() {
		return 0L;
	}

	public String GetPaintText() {
		return GetText();
	}

	public void GetProperty(String name, RefObject<String> value, RefObject<String> type) {
		if (name.equals("align")) {
			type.argvalue = "enum:HorizontalAlignA";
			GridCellStyle gridCellStyle = GetStyle();
			if (gridCellStyle != null) {
				value.argvalue = CStrMe.ConvertHorizontalAlignToStr(gridCellStyle.GetAlign());
			}
		} else if (name.equals("allowedit")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(AllowEdit());
		} else if (name.equals("autoellipsis")) {
			type.argvalue = "bool";
			GridCellStyle gridCellStyle = GetStyle();
			if (gridCellStyle != null) {
				value.argvalue = CStrMe.ConvertBoolToStr(gridCellStyle.AutoEllipsis());
			}
		} else if (name.equals("backcolor")) {
			type.argvalue = "color";
			GridCellStyle gridCellStyle = GetStyle();
			if (gridCellStyle != null) {
				value.argvalue = CStrMe.ConvertColorToStr(gridCellStyle.GetBackColor());
			}
		} else if (name.equals("colspan")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertIntToStr(GetColSpan());
		} else if (name.equals("font")) {
			type.argvalue = "font";
			GridCellStyle gridCellStyle = GetStyle();
			if ((gridCellStyle != null) && (gridCellStyle.GetFont() != null)) {
				value.argvalue = CStrMe.ConvertFontToStr(gridCellStyle.GetFont());
			}
		} else if (name.equals("forecolor")) {
			type.argvalue = "color";
			GridCellStyle style = GetStyle();
			if (style != null) {
				value.argvalue = CStrMe.ConvertColorToStr(style.GetForeColor());
			}
		} else if (name.equals("name")) {
			type.argvalue = "string";
			value.argvalue = GetName();
		} else if (name.equals("rowspan")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertIntToStr(GetRowSpan());
		} else if (name.equals("text")) {
			type.argvalue = "string";
			value.argvalue = GetText();
		} else {
			type.argvalue = "undefined";
			value.argvalue = "";
		}
	}

	public ArrayList<String> GetPropertyNames() {
		ArrayList<String> propertyNames = new ArrayList();
		propertyNames.addAll(Arrays.asList(new String[] { "Align", "AllowEdit", "AutoEllipsis", "BackColor", "ColSpan",
				"Font", "ForeColor", "Name", "RowSpan", "Text" }));
		return propertyNames;
	}

	public String GetString() {
		return "";
	}

	public void OnAdd() {
	}

	public void OnPaint(CPaintMe paint, RECT rect, RECT clipRect, boolean isAlternate) {
		int clipW = clipRect.right - clipRect.left;
		int clipH = clipRect.bottom - clipRect.top;
		if ((clipW > 0) && (clipH > 0)) {
			if ((this.m_grid != null) && (this.m_row != null) && (this.m_column != null)) {
				String paintText = GetPaintText();
				boolean selected = false;
				if (this.m_grid.GetSelectionMode() == GridSelectionMode.SelectCell) {
					ArrayList<CGridCellMe> selectedCells = this.m_grid.GetSelectedCells();
					int selectedCellsSize = selectedCells.size();
					for (int i = 0; i < selectedCellsSize; i++) {
						if (selectedCells.get(i) == this) {
							selected = true;
							break;
						}
					}
				} else if (this.m_grid.GetSelectionMode() == GridSelectionMode.SelectFullColumn) {
					ArrayList<CGridColumnMe> selectedColumns = this.m_grid.GetSelectedColumns();
					int selectedColumnsSize = selectedColumns.size();
					for (int i = 0; i < selectedColumnsSize; i++) {
						if (selectedColumns.get(i) == this.m_column) {
							selected = true;
							break;
						}
					}
				} else if (this.m_grid.GetSelectionMode() == GridSelectionMode.SelectFullRow) {
					ArrayList<CGridRowMe> selectedRows = this.m_grid.GetSelectedRows();
					int selectedRowsSize = selectedRows.size();
					for (int i = 0; i < selectedRowsSize; i++) {
						if (selectedRows.get(i) == this.m_row) {
							selected = true;
							break;
						}
					}
				}

				FONT font = null;
				long backColor = COLOR.EMPTY;
				long foreColor = COLOR.EMPTY;
				boolean autoEllipsis = this.m_grid.AutoEllipsis();
				HorizontalAlignA cellAlign = this.m_column.GetCellAlign();
				if (this.m_style != null) {
					if (this.m_style.AutoEllipsis()) {
						autoEllipsis = this.m_style.AutoEllipsis();
					}
					backColor = this.m_style.GetBackColor();
					if (this.m_style.GetFont() != null) {
						font = this.m_style.GetFont();
					}
					foreColor = this.m_style.GetForeColor();
					if (this.m_style.GetAlign() != HorizontalAlignA.Inherit) {
						cellAlign = this.m_style.GetAlign();
					}
				}
				GridRowStyle rowStyle = this.m_grid.GetRowStyle();
				if (isAlternate) {
					GridRowStyle alternateRowStyle = this.m_grid.GetAlternateRowStyle();
					if (alternateRowStyle != null) {
						rowStyle = alternateRowStyle;
					}
				}
				if (rowStyle != null) {
					if (backColor == COLOR.EMPTY) {
						if (selected) {
							backColor = rowStyle.GetSelectedBackColor();
						} else if (this.m_row == this.m_grid.GetHoveredRow()) {
							backColor = rowStyle.GetHoveredBackColor();
						} else {
							backColor = rowStyle.GetBackColor();
						}
					}
					if (font == null) {
						font = rowStyle.GetFont();
					}
					if (foreColor == COLOR.EMPTY) {
						if (selected) {
							foreColor = rowStyle.GetSelectedForeColor();
						} else if (this.m_row == this.m_grid.GetHoveredRow()) {
							foreColor = rowStyle.GetHoveredForeColor();
						} else {
							foreColor = rowStyle.GetForeColor();
						}
					}
				}
				paint.FillRect(backColor, rect);
				SIZE tSize = paint.TextSize(paintText, font);
				POINT tPoint = new POINT(rect.left + 1, rect.top + clipH / 2 - tSize.cy / 2);
				if (cellAlign == HorizontalAlignA.Center) {
					tPoint.x = (rect.left + (rect.right - rect.left - tSize.cx) / 2);
				} else if (cellAlign == HorizontalAlignA.Right) {
					tPoint.x = (rect.right - tSize.cx - 2);
				}
				RECT tRect = new RECT(tPoint.x, tPoint.y, tPoint.x + tSize.cx, tPoint.y + tSize.cy);
				if ((autoEllipsis) && ((tRect.right > clipRect.right) || (tRect.bottom > clipRect.bottom))) {
					if (tRect.right > clipRect.right) {
						tRect.right = clipRect.right;
					}
					if (tRect.bottom > clipRect.bottom) {
						tRect.bottom = clipRect.bottom;
					}
					paint.DrawTextAutoEllipsis(paintText, foreColor, font, tRect);
				} else {
					paint.DrawText(paintText, foreColor, font, tRect);
				}
			}
		}
	}

	public void OnRemove() {
	}

	public void SetBool(boolean value) {
	}

	public void SetDouble(double value) {
	}

	public void SetFloat(float value) {
	}

	public void SetInt(int value) {
	}

	public void SetLong(long value) {
	}

	public void SetProperty(String name, String value) {
		if (name.equals("align")) {
			if (this.m_style == null) {
				this.m_style = new GridCellStyle();
			}
			this.m_style.SetAlign(CStrMe.ConvertStrToHorizontalAlign(value));
		} else if (name.equals("allowedit")) {
			SetAllowEdit(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("autoellipsis")) {
			if (this.m_style == null) {
				this.m_style = new GridCellStyle();
			}
			this.m_style.SetAutoEllipsis(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("backcolor")) {
			if (this.m_style == null) {
				this.m_style = new GridCellStyle();
			}
			this.m_style.SetBackColor(CStrMe.ConvertStrToColor(value));
		} else if (name.equals("colspan")) {
			SetColSpan(CStrMe.ConvertStrToInt(value));
		} else if (name.equals("font")) {
			if (this.m_style == null) {
				this.m_style = new GridCellStyle();
			}
			this.m_style.SetFont(CStrMe.ConvertStrToFont(value));
		} else if (name.equals("forecolor")) {
			if (this.m_style == null) {
				this.m_style = new GridCellStyle();
			}
			this.m_style.SetForeColor(CStrMe.ConvertStrToColor(value));
		} else if (name.equals("name")) {
			SetName(value);
		} else if (name.equals("rowspan")) {
			SetRowSpan(CStrMe.ConvertStrToInt(value));
		} else if (name.equals("text")) {
			SetText(value);
		}
	}

	public void SetString(String value) {
	}
}
