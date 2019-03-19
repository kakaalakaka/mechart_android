package com.melib.Grid;

import java.util.ArrayList;
import java.util.Arrays;
import com.melib.Base.CPaintMe;
import com.melib.Base.CStrMe;
import com.melib.Base.HorizontalAlignA;
import com.melib.Base.INativeBaseMe;
import com.melib.Base.MouseButtonsA;
import com.melib.Base.POINT;
import com.melib.Base.RECT;
import com.melib.Base.RefObject;
import com.melib.Button.CButtonMe;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public class CGridColumnMe extends CButtonMe {
	public CGridColumnMe() {
		SetWidth(100);
	}

	public CGridColumnMe(String text) {
		SetText(text);
		SetWidth(100);
	}

	protected int m_beginWidth = 0;

	protected POINT m_mouseDownPoint = new POINT();

	protected int m_resizeState;

	protected boolean m_allowResize = false;

	public boolean AllowResize() {
		return this.m_allowResize;
	}

	public void SetAllowResize(boolean value) {
		this.m_allowResize = value;
	}

	protected boolean m_allowSort = true;

	public boolean AllowSort() {
		return this.m_allowSort;
	}

	public void SetAllowSort(boolean value) {
		this.m_allowSort = value;
	}

	protected HorizontalAlignA m_cellAlign = HorizontalAlignA.Left;

	public HorizontalAlignA GetCellAlign() {
		return this.m_cellAlign;
	}

	public void SetCellAlign(HorizontalAlignA value) {
		this.m_cellAlign = value;
	}

	protected String m_columnType = "";

	public String GetColumnType() {
		return this.m_columnType;
	}

	public void SetColumnType(String value) {
		this.m_columnType = value;
	}

	protected boolean m_frozen = false;

	public boolean IsFrozen() {
		return this.m_frozen;
	}

	public void SetFrozen(boolean value) {
		this.m_frozen = value;
	}

	protected CGridMe m_grid = null;

	public CGridMe GetGrid() {
		return this.m_grid;
	}

	public void SetGrid(CGridMe value) {
		this.m_grid = value;
	}

	protected RECT m_headerRect = new RECT();

	public RECT GetHeaderRect() {
		return this.m_headerRect.Clone();
	}

	public void SetHeaderRect(RECT value) {
		this.m_headerRect = value.Clone();
	}

	protected int m_index = -1;

	public int GetIndex() {
		return this.m_index;
	}

	public void SetIndex(int value) {
		this.m_index = value;
	}

	protected GridColumnSortMode m_sortMode = GridColumnSortMode.None;

	public GridColumnSortMode GetSortMode() {
		return this.m_sortMode;
	}

	public void SetSortMode(GridColumnSortMode value) {
		this.m_sortMode = value;
	}

	public String GetControlType() {
		return "GridColumn";
	}

	public void GetProperty(String name, RefObject<String> value, RefObject<String> type) {
		if (name.equals("allowresize")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(AllowResize());
		} else if (name.equals("allowsort")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(AllowSort());
		} else if (name.equals("columntype")) {
			type.argvalue = "text";
			value.argvalue = GetColumnType();
		} else if (name.equals("frozen")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(IsFrozen());
		} else if (name.equals("cellalign")) {
			type.argvalue = "enum:HorizontalAlignA";
			value.argvalue = CStrMe.ConvertHorizontalAlignToStr(GetCellAlign());
		} else {
			super.GetProperty(name, value, type);
		}
	}

	public ArrayList<String> GetPropertyNames() {
		ArrayList<String> propertyNames = super.GetPropertyNames();
		propertyNames.addAll(
				Arrays.asList(new String[] { "AllowResize", "AllowSort", "CellAlign", "ColumnType", "Frozen" }));
		return propertyNames;
	}

	public void OnClick(POINT mp, MouseButtonsA button, int clicks, int delta) {
		super.OnClick(mp.Clone(), button, clicks, delta);
		if ((this.m_resizeState == 0) && (this.m_allowSort)) {
			switch (this.m_sortMode) {
			case None:
			case Desc:
				this.m_grid.SortColumn(this.m_grid, this, GridColumnSortMode.Asc);
				break;
			case Asc:
				this.m_grid.SortColumn(this.m_grid, this, GridColumnSortMode.Desc);
			}

			if (GetGrid() != null) {
				GetGrid().Invalidate();
			}
		}
	}

	public boolean OnDragBegin() {
		return this.m_resizeState == 0;
	}

	public void OnDragging() {
		super.OnDragging();
		if (this.m_grid != null) {
			ArrayList<CGridColumnMe> columns = this.m_grid.GetColumns();
			int count = columns.size();
			for (int i = 0; i < count; i++) {
				CGridColumnMe column = (CGridColumnMe) columns.get(i);
				if (column == this) {
					CGridColumnMe lastColumn = null;
					CGridColumnMe nextColumn = null;
					if (i > 0) {
						lastColumn = (CGridColumnMe) columns.get(i - 1);
					}
					if (i < count - 1) {
						nextColumn = (CGridColumnMe) columns.get(i + 1);
					}

					INativeBaseMe nativeBase = GetNative();
					int clientX = nativeBase.ClientX(this);
					if (lastColumn != null) {
						int lastClientX = nativeBase.ClientX(lastColumn);
						if (clientX < lastClientX + lastColumn.GetWidth() / 2) {
							columns.set(i - 1, this);
							columns.set(i, lastColumn);
							this.m_grid.Update();
							break;
						}
					}
					if (nextColumn == null)
						break;
					int nextClientX = nativeBase.ClientX(nextColumn);
					if (clientX + column.GetWidth() > nextClientX + nextColumn.GetWidth() / 2) {
						columns.set(i + 1, this);
						columns.set(i, nextColumn);
						this.m_grid.Update();
						break;
					}
					break;
				}
			}
		}
	}

	public void OnMouseDown(POINT mp, MouseButtonsA button, int clicks, int delta) {
		super.OnMouseDown(mp.Clone(), button, clicks, delta);
		if ((button == MouseButtonsA.Left) && (clicks == 1)) {
			if (this.m_allowResize) {
				if ((this.m_index > 0) && (mp.x < 5)) {
					this.m_resizeState = 1;
					this.m_beginWidth = GetGrid().GetColumn(this.m_index - 1).GetWidth();
				} else if (mp.x > GetWidth() - 5) {
					this.m_resizeState = 2;
					this.m_beginWidth = GetWidth();
				}
				this.m_mouseDownPoint = GetNative().GetMousePoint();
			}
		}
	}

	public void OnMouseMove(POINT mp, MouseButtonsA button, int clicks, int delta) {
		super.OnMouseMove(mp.Clone(), button, clicks, delta);
		if (this.m_allowResize) {
			if (this.m_resizeState > 0) {
				POINT curPoint = GetNative().GetMousePoint();
				int newWidth = this.m_beginWidth + (curPoint.x - this.m_mouseDownPoint.x);
				if (newWidth > 0) {
					if (this.m_resizeState == 1) {
						GetGrid().GetColumn(this.m_index - 1).SetWidth(newWidth);
					} else if (this.m_resizeState == 2) {
						SetWidth(newWidth);
					}
				}
				if (this.m_grid != null) {
					this.m_grid.Invalidate();
				}
			}
		}
	}

	public void OnMouseUp(POINT mp, MouseButtonsA button, int clicks, int delta) {
		super.OnMouseUp(mp.Clone(), button, clicks, delta);
		this.m_resizeState = 0;
		if (this.m_grid != null) {
			this.m_grid.Invalidate();
		}
	}

	public void OnPaintForeground(CPaintMe paint, RECT clipRect) {
		super.OnPaintForeground(paint, clipRect);
		if ((GetNative() != null) && (this.m_grid != null)) {
			RECT rect = new RECT(0, 0, GetWidth(), GetHeight());
			int tLeft = rect.right - 15;
			int midTop = rect.top + (rect.bottom - rect.top) / 2;
			long paintForeColor = GetPaintingForeColor();

			if (this.m_sortMode == GridColumnSortMode.Asc) {
				POINT[] points = { new POINT(tLeft + 5, midTop - 5), new POINT(tLeft, midTop + 5),
						new POINT(tLeft + 10, midTop + 5) };
				paint.FillPolygon(paintForeColor, points);

			} else if (this.m_sortMode == GridColumnSortMode.Desc) {
				POINT[] pointArray = { new POINT(tLeft + 5, midTop + 5), new POINT(tLeft, midTop - 5),
						new POINT(tLeft + 10, midTop - 5) };
				paint.FillPolygon(paintForeColor, pointArray);
			}
		}
	}

	public void SetProperty(String name, String value) {
		if (name.equals("allowresize")) {
			SetAllowResize(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("allowsort")) {
			SetAllowSort(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("cellalign")) {
			SetCellAlign(CStrMe.ConvertStrToHorizontalAlign(value));
		} else if (name.equals("columntype")) {
			SetColumnType(value);
		} else if (name.equals("frozen")) {
			SetFrozen(CStrMe.ConvertStrToBool(value));
		} else {
			super.SetProperty(name, value);
		}
	}
}
