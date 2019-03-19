package com.melib.Grid;

import java.util.ArrayList;
import java.util.HashMap;
import com.melib.Base.COLOR;
import com.melib.Base.CPaintMe;
import com.melib.Base.CStrMe;
import com.melib.Base.CControlMe;
import com.melib.Base.CControlHostMe;
import com.melib.Base.EVENTID;
import com.melib.Base.INativeBaseMe;
import com.melib.Base.MouseButtonsA;
import com.melib.Base.POINT;
import com.melib.Base.RECT;
import com.melib.Base.RefObject;
import com.melib.Layout.CDivMe;
import com.melib.ScrollBar.CHScrollBarMe;
import com.melib.ScrollBar.CScrollBarMe;
import com.melib.ScrollBar.CVScrollBarMe;
import com.melib.TextBox.CTextBoxMe;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class CGridMe extends CDivMe implements com.melib.Base.ControlEvent {
	public CGridMe() {
		SetShowHScrollBar(true);
		SetShowVScrollBar(true);
	}

	public ArrayList<CGridRowMe> m_animateAddRows = new ArrayList();

	public ArrayList<CGridRowMe> m_animateRemoveRows = new ArrayList();

	public ArrayList<CGridColumnMe> m_columns = new ArrayList();

	protected CGridCellMe m_editingCell = null;

	public CGridRowMe m_editingRow;

	protected boolean m_hasUnVisibleRow = false;

	protected boolean m_lockUpdate = false;

	private POINT m_point;

	public ArrayList<CGridRowMe> m_rows = new ArrayList();

	private int m_timerID = GetNewTimerID();

	protected boolean m_allowDragRow = false;

	public boolean AllowDragRow() {
		return this.m_allowDragRow;
	}

	public void SetAllowDragRow(boolean value) {
		this.m_allowDragRow = value;
	}

	protected boolean m_allowHoveredRow = true;

	public boolean AllowHoveredRow() {
		return this.m_allowHoveredRow;
	}

	public void SetAllowHoveredRow(boolean value) {
		this.m_allowHoveredRow = value;
	}

	protected int GetAllVisibleColumnsWidth() {
		int width = 0;
		int columnsSize = this.m_columns.size();
		for (int i = 0; i < columnsSize; i++) {
			CGridColumnMe gridColumn = (CGridColumnMe) this.m_columns.get(i);
			if (gridColumn.IsVisible()) {
				width += gridColumn.GetWidth();
			}
		}
		return width;
	}

	protected int GetAllVisibleRowsHeight() {
		int height = 0;
		int rowsSize = this.m_rows.size();
		for (int i = 0; i < rowsSize; i++) {
			if (((CGridRowMe) this.m_rows.get(i)).IsVisible()) {
				height += ((CGridRowMe) this.m_rows.get(i)).GetHeight();
			}
		}
		return height;
	}

	protected GridRowStyle m_alternateRowStyle = null;

	public GridRowStyle GetAlternateRowStyle() {
		return this.m_alternateRowStyle;
	}

	public void SetAlternateRowStyle(GridRowStyle value) {
		this.m_alternateRowStyle = value;
	}

	protected GridCellEditMode m_cellEditMode = GridCellEditMode.SingleClick;

	public GridCellEditMode GetCellEditMode() {
		return this.m_cellEditMode;
	}

	public void SetCellEditMode(GridCellEditMode value) {
		this.m_cellEditMode = value;
	}

	protected CTextBoxMe m_editTextBox = null;

	public CTextBoxMe GetEditTextBox() {
		return this.m_editTextBox;
	}

	protected long m_gridLineColor = COLOR.ARGB(100, 100, 100);

	public long GetGridLineColor() {
		return this.m_gridLineColor;
	}

	public void SetGridLineColor(long value) {
		this.m_gridLineColor = value;
	}

	protected boolean m_headerVisible = true;

	public boolean IsHeaderVisible() {
		return this.m_headerVisible;
	}

	public void SetHeaderVisible(boolean value) {
		this.m_headerVisible = value;
	}

	protected int m_headerHeight = 20;

	public int GetHeaderHeight() {
		return this.m_headerHeight;
	}

	public void SetHeaderHeight(int value) {
		this.m_headerHeight = value;
	}

	protected int m_horizontalOffset = 0;

	public int GetHorizontalOffset() {
		return this.m_horizontalOffset;
	}

	public void SetHorizontalOffset(int value) {
		this.m_horizontalOffset = value;
	}

	protected CGridCellMe m_hoveredCell = null;

	public CGridCellMe GetHoveredCell() {
		return this.m_hoveredCell;
	}

	protected CGridRowMe m_hoveredRow = null;

	public CGridRowMe GetHoveredRow() {
		return this.m_hoveredRow;
	}

	protected boolean m_multiSelect = false;

	public boolean MultiSelect() {
		return this.m_multiSelect;
	}

	public void SetMultiSelect(boolean value) {
		this.m_multiSelect = value;
	}

	protected GridRowStyle m_rowStyle = new GridRowStyle();

	public GridRowStyle GetRowStyle() {
		return this.m_rowStyle;
	}

	public void SetRowStyle(GridRowStyle value) {
		this.m_rowStyle = value;
	}

	protected ArrayList<CGridCellMe> m_selectedCells = new ArrayList();

	public ArrayList<CGridCellMe> GetSelectedCells() {
		return this.m_selectedCells;
	}

	public void SetSelectedCells(ArrayList<CGridCellMe> value) {
		this.m_selectedCells.clear();
		int cellsSize = value.size();
		for (int i = 0; i < cellsSize; i++) {
			this.m_selectedCells.add(value.get(i));
		}
		OnSelectedCellsChanged();
	}

	protected ArrayList<CGridColumnMe> m_selectedColumns = new ArrayList();

	public ArrayList<CGridColumnMe> GetSelectedColumns() {
		return this.m_selectedColumns;
	}

	public void SetSelectedColumns(ArrayList<CGridColumnMe> value) {
		this.m_selectedColumns.clear();
		int columnsSize = value.size();
		for (int i = 0; i < columnsSize; i++) {
			this.m_selectedColumns.add(value.get(i));
		}
		OnSelectedColumnsChanged();
	}

	protected ArrayList<CGridRowMe> m_selectedRows = new ArrayList();

	public ArrayList<CGridRowMe> GetSelectedRows() {
		return this.m_selectedRows;
	}

	public void SetSelectedRows(ArrayList<CGridRowMe> value) {
		this.m_selectedRows.clear();
		int rowsSize = value.size();
		for (int i = 0; i < rowsSize; i++) {
			this.m_selectedRows.add(value.get(i));
		}
		OnSelectedRowsChanged();
	}

	protected GridSelectionMode m_selectionMode = GridSelectionMode.SelectFullRow;

	public GridSelectionMode GetSelectionMode() {
		return this.m_selectionMode;
	}

	public void SetSelectionMode(GridSelectionMode value) {
		this.m_selectionMode = value;
	}

	protected CGridSortMe m_sort = new CGridSortMe();

	public CGridSortMe GetSort() {
		return this.m_sort;
	}

	public void SetSort(CGridSortMe sort) {
		this.m_sort = sort;
	}

	protected boolean m_useAnimation = false;

	public boolean UseAnimation() {
		return this.m_useAnimation;
	}

	public void SetUseAnimation(boolean value) {
		this.m_useAnimation = value;
		if (this.m_useAnimation) {
			StartTimer(this.m_timerID, 20);
		} else {
			StopTimer(this.m_timerID);
		}
	}

	protected int m_verticalOffset = 0;

	public int GetVerticalOffset() {
		return this.m_verticalOffset;
	}

	public void SetVerticalOffset(int value) {
		this.m_verticalOffset = value;
	}

	public void AddColumn(CGridColumnMe column) {
		column.SetGrid(this);
		this.m_columns.add(column);
		int columnsSize = this.m_columns.size();
		for (int i = 0; i < columnsSize; i++) {
			((CGridColumnMe) this.m_columns.get(i)).SetIndex(i);
		}
		AddControl(column);
	}

	public void AddRow(CGridRowMe row) {
		row.SetGrid(this);
		this.m_rows.add(row);
		row.OnAdd();
		if (this.m_selectionMode == GridSelectionMode.SelectFullRow) {
			int selelctedRowsSize = this.m_selectedRows.size();
			if (selelctedRowsSize == 0) {
				this.m_selectedRows.add(row);
				OnSelectedRowsChanged();
			}
		}
	}

	public void AnimateAddRow(CGridRowMe row) {
		row.SetGrid(this);
		this.m_rows.add(row);
		row.OnAdd();
		if (this.m_selectionMode == GridSelectionMode.SelectFullRow) {
			int selectRowsSzie = this.m_selectedRows.size();
			if (selectRowsSzie == 0) {
				this.m_selectedRows.add(row);
				OnSelectedRowsChanged();
			}
		}
		if (this.m_useAnimation) {
			this.m_animateAddRows.add(row);
		}
	}

	public void AnimateRemoveRow(CGridRowMe row) {
		if (this.m_useAnimation) {
			this.m_animateRemoveRows.add(row);
		} else {
			RemoveRow(row);
		}
	}

	public void BeginUpdate() {
		this.m_lockUpdate = true;
	}

	protected void CallCellEvents(int eventID, CGridCellMe cell) {
		if ((this.m_events != null) && (this.m_events.containsKey(Integer.valueOf(eventID)))) {
			ArrayList<Object> events = (ArrayList) this.m_events.get(Integer.valueOf(eventID));
			int eventsSize = events.size();
			for (int i = 0; i < eventsSize; i++) {
				GridCellEvent gridCellEvent = (GridCellEvent) ((events.get(i) instanceof GridCellEvent) ? events.get(i)
						: null);
				if (gridCellEvent != null) {
					gridCellEvent.CallGridCellEvent(eventID, this, cell);
				}
			}
		}
	}

	protected void CallCellMouseEvents(int eventID, CGridCellMe cell, POINT mp, MouseButtonsA button, int clicks,
			int delta) {
		if ((this.m_events != null) && (this.m_events.containsKey(Integer.valueOf(eventID)))) {
			ArrayList<Object> events = (ArrayList) this.m_events.get(Integer.valueOf(eventID));
			int eventsSize = events.size();
			for (int i = 0; i < eventsSize; i++) {
				GridCellMouseEvent gridCellMouseEvent = (GridCellMouseEvent) ((events
						.get(i) instanceof GridCellMouseEvent) ? events.get(i) : null);
				if (gridCellMouseEvent != null) {
					gridCellMouseEvent.CallGridCellMouseEvent(eventID, this, cell, mp.Clone(), button, clicks, delta);
				}
			}
		}
	}

	public void CallControlEvent(int eventID, Object sender) {
		if ((eventID == EVENTID.LOSTFOCUS) && (sender == this.m_editTextBox)) {
			if ((this.m_editTextBox != null) && (this.m_editTextBox.GetTag() != null)) {
				Object tag = this.m_editTextBox.GetTag();
				OnCellEditEnd((CGridCellMe) ((tag instanceof CGridCellMe) ? tag : null));
			}
		}
	}

	public void Clear() {
		ClearRows();
		ClearColumns();
	}

	public void ClearColumns() {
		this.m_selectedColumns.clear();
		int columnsSize = this.m_columns.size();
		for (int i = 0; i < columnsSize; i++) {
			RemoveControl((CControlMe) this.m_columns.get(i));
			((CGridColumnMe) this.m_columns.get(i)).Dispose();
		}
		this.m_columns.clear();
	}

	public void ClearRows() {
		this.m_hasUnVisibleRow = false;
		this.m_hoveredCell = null;
		this.m_hoveredRow = null;
		this.m_selectedRows.clear();
		int rowsSize = this.m_rows.size();
		for (int i = 0; i < rowsSize; i++) {
			((CGridRowMe) this.m_rows.get(i)).OnRemove();
			((CGridRowMe) this.m_rows.get(i)).Dispose();
		}
		this.m_rows.clear();
	}

	public void Dispose() {
		if (!IsDisposed()) {
			this.m_editingCell = null;
			if (this.m_editTextBox != null) {
				this.m_editTextBox.UnRegisterEvent(this, EVENTID.LOSTFOCUS);
				this.m_editTextBox = null;
			}
			StopTimer(this.m_timerID);
			Clear();
		}
		super.Dispose();
	}

	public void EndUpdate() {
		if (this.m_lockUpdate) {
			this.m_lockUpdate = false;
			Update();
		}
	}

	public CGridColumnMe GetColumn(int columnIndex) {
		if ((columnIndex >= 0) && (columnIndex < this.m_columns.size())) {
			return (CGridColumnMe) this.m_columns.get(columnIndex);
		}
		return null;
	}

	public CGridColumnMe GetColumn(String columnName) {
		int columnsSize = this.m_columns.size();
		for (int i = 0; i < columnsSize; i++) {
			if (((CGridColumnMe) this.m_columns.get(i)).GetName().equals(columnName)) {
				return (CGridColumnMe) this.m_columns.get(i);
			}
		}
		return null;
	}

	public ArrayList<CGridColumnMe> GetColumns() {
		return this.m_columns;
	}

	public int GetContentHeight() {
		int height = GetAllVisibleRowsHeight();
		if (height > 0) {
			if (height <= GetHeight()) {
				height += (this.m_headerVisible ? this.m_headerHeight : 0);
			}
			return height;
		}

		return 0;
	}

	public int GetContentWidth() {
		return GetAllVisibleColumnsWidth();
	}

	public String GetControlType() {
		return "Grid";
	}

	public POINT GetDisplayOffset() {
		return new POINT(0, 0);
	}

	public void GetProperty(String name, RefObject<String> value, RefObject<String> type) {
		if (name.equals("allowdragrow")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(AllowDragRow());
		} else if (name.equals("allowhoveredrow")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(AllowHoveredRow());
		} else if (name.equals("celleditmode")) {
			type.argvalue = "enum:GridCellEditMode";
			GridCellEditMode cellEditMode = GetCellEditMode();
			if (cellEditMode == GridCellEditMode.DoubleClick) {
				value.argvalue = "DoubleClick";
			} else if (cellEditMode == GridCellEditMode.None) {
				value.argvalue = "None";
			} else {
				value.argvalue = "SingleClick";
			}
		} else if (name.equals("gridlinecolor")) {
			type.argvalue = "color";
			value.argvalue = CStrMe.ConvertColorToStr(GetGridLineColor());
		} else if (name.equals("headerheight")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertIntToStr(GetHeaderHeight());
		} else if (name.equals("headervisible")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(IsHeaderVisible());
		} else if (name.equals("horizontaloffset")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertIntToStr(GetHorizontalOffset());
		} else if (name.equals("multiselect")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(MultiSelect());
		} else if (name.equals("selectionmode")) {
			type.argvalue = "enum:GridSelectionMode";
			GridSelectionMode selectionMode = GetSelectionMode();
			if (selectionMode == GridSelectionMode.SelectCell) {
				value.argvalue = "SelectCell";
			} else if (selectionMode == GridSelectionMode.SelectFullColumn) {
				value.argvalue = "SelectFullColumn";
			} else if (selectionMode == GridSelectionMode.SelectFullRow) {
				value.argvalue = "SelectFullRow";
			} else {
				value.argvalue = "None";
			}
		} else if (name.equals("useanimation")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(UseAnimation());
		} else if (name.equals("verticaloffset")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertIntToStr(GetVerticalOffset());
		} else {
			super.GetProperty(name, value, type);
		}
	}

	public ArrayList<String> GetPropertyNames() {
		ArrayList<String> propertyNames = super.GetPropertyNames();
		propertyNames.addAll(java.util.Arrays.asList(new String[] { "AllowDragRow", "AllowHoveredRow", "CellEditMode",
				"GridLineColor", "HeaderHeight", "HeaderVisible", "HorizontalOffset", "MultiSelect", "SelectionMode",
				"UseAnimation", "VerticalOffset" }));
		return propertyNames;
	}

	public CGridRowMe GetRow(POINT mp) {
		if (this.m_hasUnVisibleRow) {
			int rowsSize = this.m_rows.size();
			for (int i = 0; i < rowsSize; i++) {
				CGridRowMe gridRow = (CGridRowMe) this.m_rows.get(i);
				if (gridRow.IsVisible()) {
					RECT rect = gridRow.GetBounds();
					if ((mp.y >= rect.top) && (mp.y <= rect.bottom)) {
						return gridRow;
					}
				}
			}
		} else {
			int begin = 0;
			int end = this.m_rows.size() - 1;
			int sub = end - begin;
			while (sub >= 0) {
				int half = begin + sub / 2;
				CGridRowMe row = (CGridRowMe) this.m_rows.get(half);
				RECT bands = row.GetBounds();
				if ((half == begin) || (half == end)) {
					if ((mp.y >= ((CGridRowMe) this.m_rows.get(begin)).GetBounds().top)
							&& (mp.y <= ((CGridRowMe) this.m_rows.get(begin)).GetBounds().bottom)) {
						return (CGridRowMe) this.m_rows.get(begin);
					}
					if ((mp.y < ((CGridRowMe) this.m_rows.get(end)).GetBounds().top)
							|| (mp.y > ((CGridRowMe) this.m_rows.get(end)).GetBounds().bottom))
						break;
					return (CGridRowMe) this.m_rows.get(end);
				}

				if ((mp.y >= bands.top) && (mp.y <= bands.bottom)) {
					return row;
				}
				if (bands.top > mp.y) {
					end = half;
				} else if (bands.bottom < mp.y) {
					begin = half;
				}
				sub = end - begin;
			}
		}
		return null;
	}

	public CGridRowMe GetRow(int rowIndex) {
		if ((rowIndex >= 0) && (rowIndex < this.m_rows.size())) {
			return (CGridRowMe) this.m_rows.get(rowIndex);
		}
		return null;
	}

	public ArrayList<CGridRowMe> GetRows() {
		return this.m_rows;
	}

	public void GetVisibleRowsIndex(double visiblePercent, RefObject<Integer> firstVisibleRowIndex,
			RefObject<Integer> lastVisibleRowIndex) {
		firstVisibleRowIndex.argvalue = Integer.valueOf(-1);
		lastVisibleRowIndex.argvalue = Integer.valueOf(-1);
		int rowsSize = this.m_rows.size();
		if (rowsSize > 0) {
			for (int i = 0; i < rowsSize; i++) {
				CGridRowMe row = (CGridRowMe) this.m_rows.get(i);
				if (IsRowVisible(row, visiblePercent)) {
					if (((Integer) firstVisibleRowIndex.argvalue).intValue() == -1) {
						firstVisibleRowIndex.argvalue = Integer.valueOf(i);
					}

				} else if (((Integer) firstVisibleRowIndex.argvalue).intValue() != -1) {
					lastVisibleRowIndex.argvalue = Integer.valueOf(i);
					break;
				}
			}

			if ((((Integer) firstVisibleRowIndex.argvalue).intValue() != -1)
					&& (((Integer) lastVisibleRowIndex.argvalue).intValue() == -1)) {
				lastVisibleRowIndex.argvalue = firstVisibleRowIndex.argvalue;
			}
		}
	}

	public void InsertRow(int index, CGridRowMe row) {
		row.SetGrid(this);
		this.m_rows.add(index, row);
		row.OnAdd();
	}

	public boolean IsRowVisible(CGridRowMe row, double visiblePercent) {
		int scrollV = 0;
		CVScrollBarMe vScrollBar = GetVScrollBar();
		if ((vScrollBar != null) && (vScrollBar.IsVisible())) {
			scrollV = -vScrollBar.GetPos();
		}
		int cell = this.m_headerVisible ? this.m_headerHeight : 0;
		int floor = GetHeight() - cell;
		RECT bounds = row.GetBounds();
		int top = bounds.top + scrollV;
		int bottom = bounds.bottom + scrollV;
		if (top < cell) {
			top = cell;
		} else if (top > floor) {
			top = floor;
		}
		if (bottom < cell) {
			bottom = cell;
		} else if (bottom > floor) {
			bottom = floor;
		}
		if (bottom - top > row.GetHeight() * visiblePercent) {
			return true;
		}
		return false;
	}

	protected void MouseEvent(POINT mp, MouseButtonsA button, int clicks, int delta, int state) {
		int height = GetHeight();
		int hHeight = this.m_headerVisible ? this.m_headerHeight : 0;
		int scrollH = 0;
		int scrollV = 0;
		CControlHostMe host = GetNative().GetHost();
		CHScrollBarMe hScrollBar = GetHScrollBar();
		CVScrollBarMe vScrollBar = GetVScrollBar();
		if ((hScrollBar != null) && (hScrollBar.IsVisible())) {
			scrollH = -hScrollBar.GetPos();
		}
		if ((vScrollBar != null) && (vScrollBar.IsVisible())) {
			scrollV = -vScrollBar.GetPos();
		}
		POINT fPoint = new POINT(0, hHeight + 1 - scrollV);
		POINT ePoint = new POINT(0, height - 10 - scrollV);
		CGridRowMe fRow = GetRow(fPoint);
		CGridRowMe eRow = GetRow(ePoint);
		while ((eRow == null) && (ePoint.y > 0)) {
			ePoint.y -= 10;
			eRow = GetRow(ePoint);
		}
		if ((fRow != null) && (eRow != null)) {
			int fIndex = fRow.GetIndex();
			int eIndex = eRow.GetIndex();
			for (int i = fIndex; i <= eIndex; i++) {
				CGridRowMe row = (CGridRowMe) this.m_rows.get(i);
				if (row.IsVisible()) {
					RECT rowRect = row.GetBounds();
					rowRect.top += scrollV;
					rowRect.bottom += scrollV;
					ArrayList<CGridCellMe> cells = null;
					ArrayList<CGridCellMe> unFrozenCells = new ArrayList();
					for (int j = 0; j < 2; j++) {
						if (j == 0) {
							cells = row.GetCells();
						} else {
							cells = unFrozenCells;
						}
						int cellSize = cells.size();
						for (int c = 0; c < cellSize; c++) {
							CGridCellMe cell = (CGridCellMe) cells.get(c);
							CGridColumnMe column = cell.GetColumn();
							if (column.IsVisible()) {
								if ((j == 0) && (!column.IsFrozen())) {
									unFrozenCells.add(cell);
								} else {
									RECT headerRect = column.GetHeaderRect();
									if (!column.IsFrozen()) {
										headerRect.left += scrollH;
										headerRect.right += scrollH;
									}
									int cellWidth = column.GetWidth();
									int colSpan = cell.GetColSpan();
									if (colSpan > 1) {
										for (int n = 1; n < colSpan; n++) {
											CGridColumnMe spanColumn = GetColumn(column.GetIndex() + n);
											if ((spanColumn != null) && (spanColumn.IsVisible())) {
												cellWidth += spanColumn.GetWidth();
											}
										}
									}
									int cellHeight = row.GetHeight();
									int rowSpan = cell.GetRowSpan();
									if (rowSpan > 1) {
										for (int n = 1; n < rowSpan; n++) {
											CGridRowMe spanRow = GetRow(i + n);
											if ((spanRow != null) && (spanRow.IsVisible())) {
												cellHeight += spanRow.GetHeight();
											}
										}
									}
									RECT cellRect = new RECT(headerRect.left, rowRect.top + this.m_verticalOffset,
											headerRect.left + cellWidth,
											rowRect.top + this.m_verticalOffset + cellHeight);
									if ((mp.x >= cellRect.left) && (mp.x <= cellRect.right) && (mp.y >= cellRect.top)
											&& (mp.y <= cellRect.bottom)) {
										if (state == 0) {
											boolean hoverChanged = false;
											if ((this.m_allowHoveredRow) && (this.m_hoveredRow != row)) {
												this.m_hoveredRow = row;
												hoverChanged = true;
											}
											if (GetNative().GetPushedControl() == this) {
												if (this.m_allowDragRow) {
													if (this.m_selectionMode == GridSelectionMode.SelectFullRow) {
														int selectedRowSize = this.m_selectedRows.size();
														if (selectedRowSize == 1) {
															if (this.m_selectedRows.get(0) != row) {
																MoveRow(((CGridRowMe) this.m_selectedRows.get(0))
																		.GetIndex(), row.GetIndex());
																hoverChanged = true;
															}
														}
													}
												}
											}
											if (this.m_hoveredCell != cell) {
												if (this.m_hoveredCell != null) {
													OnCellMouseLeave(this.m_hoveredCell, mp.Clone(), button, clicks,
															delta);
												}
												this.m_hoveredCell = cell;
												OnCellMouseEnter(this.m_hoveredCell, mp.Clone(), button, clicks, delta);
											}
											if (this.m_editingRow == null) {
												if (row.AllowEdit()) {
													if (GetNative().GetPushedControl() == this) {
														int selectedRowSize = this.m_selectedRows.size();
														if (selectedRowSize == 1) {
															if (this.m_selectedRows.get(0) == row) {
																OnRowEditBegin(row);
																hoverChanged = true;
															}
														}
													}
												}
											}
											OnCellMouseMove(cell, mp, button, clicks, delta);
											if (hoverChanged) {
												Invalidate();
											}
										} else {
											if (state == 1) {
												OnCellMouseDown(cell, mp, button, clicks, delta);
												this.m_point = mp;
												if ((button == MouseButtonsA.Left) && (clicks == 1)) {
													int multiSelectMode = 0;
													if (this.m_selectionMode == GridSelectionMode.SelectCell) {
														boolean contains = false;
														boolean selectedChanged = false;
														int selectedCellSize = this.m_selectedCells.size();
														if ((multiSelectMode == 0) || (multiSelectMode == 2)) {
															for (int idx = 0; idx < selectedCellSize; idx++) {
																if (this.m_selectedCells.get(idx) == cell) {
																	contains = true;
																	if (multiSelectMode != 2)
																		break;
																	this.m_selectedCells.remove(cell);
																	selectedChanged = true;
																	break;
																}
															}
														}

														if (multiSelectMode == 0) {
															selectedCellSize = this.m_selectedCells.size();
															if ((!contains) || (selectedCellSize > 1)) {
																this.m_selectedCells.clear();
																this.m_selectedCells.add(cell);
																selectedChanged = true;
															}
														} else if (multiSelectMode == 2) {
															if (!contains) {
																this.m_selectedCells.add(cell);
																selectedChanged = true;
															}
														}
														if (selectedChanged) {
															OnSelectedCellsChanged();
														}
													} else if (this.m_selectionMode == GridSelectionMode.SelectFullColumn) {
														boolean contains = false;
														boolean selectedChanged = false;
														int selectedColumnsSize = this.m_selectedColumns.size();
														if ((multiSelectMode == 0) || (multiSelectMode == 2)) {
															for (int idx = 0; idx < selectedColumnsSize; idx++) {
																if (this.m_selectedColumns.get(idx) == column) {
																	contains = true;
																	if (multiSelectMode != 2)
																		break;
																	this.m_selectedColumns.remove(column);
																	selectedChanged = true;
																	break;
																}
															}
														}

														if (multiSelectMode == 0) {
															if ((!contains) || (selectedColumnsSize > 1)) {
																this.m_selectedColumns.clear();
																this.m_selectedColumns.add(column);
																selectedChanged = true;
															}
														} else if (multiSelectMode == 2) {
															if (!contains) {
																this.m_selectedColumns.add(column);
																selectedChanged = true;
															}
														}
														this.m_selectedCells.clear();
														this.m_selectedCells.add(cell);
														if (selectedChanged) {
															OnSelectedColumnsChanged();
														}
													} else if (this.m_selectionMode == GridSelectionMode.SelectFullRow) {
														boolean contains = false;
														boolean selectedChanged = false;
														int selectedRowsSize = this.m_selectedRows.size();
														if ((multiSelectMode == 0) || (multiSelectMode == 2)) {
															for (int idx = 0; idx < selectedRowsSize; idx++) {
																if (this.m_selectedRows.get(idx) == row) {
																	contains = true;
																	if (multiSelectMode != 2)
																		break;
																	this.m_selectedRows.remove(row);
																	selectedChanged = true;
																	break;
																}
															}
														}

														if (multiSelectMode == 0) {
															selectedRowsSize = this.m_selectedRows.size();
															if ((!contains) || (selectedRowsSize > 1)) {
																this.m_selectedRows.clear();
																this.m_selectedRows.add(row);
																selectedChanged = true;
															}
														} else if (multiSelectMode == 1) {
															selectedRowsSize = this.m_selectedRows.size();
															if (selectedRowsSize > 0) {
																int firstIndex = ((CGridRowMe) this.m_selectedRows
																		.get(0)).GetIndex();
																int newIndex = row.GetIndex();
																int minIndex = Math.min(firstIndex, newIndex);
																int maxIndex = Math.max(firstIndex, newIndex);
																this.m_selectedRows.clear();
																for (int s = minIndex; s <= maxIndex; s++) {
																	this.m_selectedRows.add(GetRow(s));
																}
															} else {
																this.m_selectedRows.add(row);
															}
														} else if (multiSelectMode == 2) {
															if (!contains) {
																this.m_selectedRows.add(row);
																selectedChanged = true;
															}
														}

														this.m_selectedCells.clear();
														this.m_selectedCells.add(cell);
														if (selectedChanged) {
															OnSelectedRowsChanged();
														}
													}
												}
											} else if (state == 2) {
												OnCellMouseUp(cell, mp, button, clicks, delta);
											}
											if ((state == 2) || ((clicks == 2) && (state == 1))) {
												if ((this.m_selectedCells.size() > 0)
														&& (this.m_selectedCells.get(0) == cell)) {
													OnCellClick(cell, mp, button, clicks, delta);
													if ((button == MouseButtonsA.Left) && (cell.AllowEdit())) {
														if (((this.m_cellEditMode == GridCellEditMode.DoubleClick)
																&& (clicks == 2) && (state == 1))
																|| ((this.m_cellEditMode == GridCellEditMode.SingleClick)
																		&& (state == 2))) {
															OnCellEditBegin(cell);
														}
													}
												}
											}
											Invalidate();
										}
										unFrozenCells.clear();
										if ((state == 1) && (this.m_editingRow != null)) {
											OnRowEditEnd();
										}
										return;
									}
								}
							}
						}
					}
					unFrozenCells.clear();
				}
			}
		}
		if ((state == 1) && (this.m_editingRow != null)) {
			OnRowEditEnd();
		}
	}

	public void MoveRow(int oldIndex, int newIndex) {
		int rowsSize = this.m_rows.size();
		if (rowsSize > 0) {
			if ((oldIndex >= 0) && (oldIndex < rowsSize) && (newIndex >= 0) && (newIndex < rowsSize)) {
				CGridRowMe movingRow = (CGridRowMe) this.m_rows.get(oldIndex);
				CGridRowMe targetRow = (CGridRowMe) this.m_rows.get(newIndex);
				if (movingRow != targetRow) {
					this.m_rows.set(newIndex, movingRow);
					this.m_rows.set(oldIndex, targetRow);
					movingRow.SetIndex(newIndex);
					targetRow.SetIndex(oldIndex);
					CVScrollBarMe vScrollBar = GetVScrollBar();
					if ((vScrollBar != null) && (vScrollBar.IsVisible())) {
						int firstVisibleRowIndex = -1;
						int lastVisibleRowIndex = -1;
						RefObject<Integer> firstVisibleRowIndexRef = new RefObject(
								Integer.valueOf(firstVisibleRowIndex));
						RefObject<Integer> lastVisibleRowIndexRef = new RefObject(Integer.valueOf(lastVisibleRowIndex));
						GetVisibleRowsIndex(0.6D, firstVisibleRowIndexRef, lastVisibleRowIndexRef);
						firstVisibleRowIndex = ((Integer) firstVisibleRowIndexRef.argvalue).intValue();
						lastVisibleRowIndex = ((Integer) lastVisibleRowIndexRef.argvalue).intValue();
						int th = targetRow.GetHeight();
						if (newIndex <= firstVisibleRowIndex) {
							if (newIndex == firstVisibleRowIndex) {
								vScrollBar.SetPos(vScrollBar.GetPos() - th);
							}
							int count = 0;
							while (!IsRowVisible(targetRow, 0.6D)) {
								int newPos = vScrollBar.GetPos() - th;
								vScrollBar.SetPos(newPos);
								count++;
								if ((count > rowsSize) || (newPos <= vScrollBar.GetPos())) {
									break;
								}

							}
						} else if (newIndex >= lastVisibleRowIndex) {
							if (newIndex == lastVisibleRowIndex) {
								vScrollBar.SetPos(vScrollBar.GetPos() + th);
							}
							int count = 0;
							while (!IsRowVisible(targetRow, 0.6D)) {
								int newPos = vScrollBar.GetPos() + th;
								vScrollBar.SetPos(newPos);
								count++;
								if ((count > rowsSize) || (newPos >= vScrollBar.GetPos())) {
									break;
								}
							}
						}

						vScrollBar.Update();
					}
					Update();
				}
			}
		}
	}

	public void OnCellClick(CGridCellMe cell, POINT mp, MouseButtonsA button, int clicks, int delta) {
		CallCellMouseEvents(EVENTID.GRIDCELLCLICK, cell, mp.Clone(), button, clicks, delta);
	}

	public void OnCellEditBegin(CGridCellMe cell) {
		this.m_editingCell = cell;

		if (this.m_editTextBox == null) {
			CControlHostMe host = GetNative().GetHost();
			CControlMe control = host.CreateInternalControl(this, "edittextbox");
			this.m_editTextBox = ((CTextBoxMe) ((control instanceof CTextBoxMe) ? control : null));
			this.m_editTextBox.RegisterEvent(this, EVENTID.LOSTFOCUS);
			AddControl(this.m_editTextBox);
		}
		this.m_editTextBox.SetFocused(true);
		String text = this.m_editingCell.GetText();
		this.m_editTextBox.SetTag(this.m_editingCell);
		this.m_editTextBox.SetText(text);
		this.m_editTextBox.ClearRedoUndo();
		this.m_editTextBox.SetVisible(true);
		if ((text != null) && (text.length() > 0)) {
			this.m_editTextBox.SetSelectionStart(text.length());
		}
		CallCellEvents(EVENTID.GRIDCELLEDITBEGIN, cell);
	}

	public void OnCellEditEnd(CGridCellMe cell) {
		if (cell != null) {
			cell.SetText(this.m_editTextBox.GetText());
		}
		this.m_editTextBox.SetTag(null);
		this.m_editTextBox.SetVisible(false);
		this.m_editingCell = null;
		CallCellEvents(EVENTID.GRIDCELLEDITEND, cell);
		Invalidate();
	}

	public void OnCellMouseDown(CGridCellMe cell, POINT mp, MouseButtonsA button, int clicks, int delta) {
		CallCellMouseEvents(EVENTID.GRIDCELLMOUSEDOWN, cell, mp.Clone(), button, clicks, delta);
	}

	public void OnCellMouseEnter(CGridCellMe cell, POINT mp, MouseButtonsA button, int clicks, int delta) {
		CallCellMouseEvents(EVENTID.GRIDCELLMOUSEENTER, cell, mp.Clone(), button, clicks, delta);
		if ((AutoEllipsis()) || ((cell.GetStyle() != null) && (cell.GetStyle().AutoEllipsis()))) {
			this.m_native.GetHost().ShowToolTip(cell.GetPaintText(), this.m_native.GetMousePoint());
		}
	}

	public void OnCellMouseLeave(CGridCellMe cell, POINT mp, MouseButtonsA button, int clicks, int delta) {
		CallCellMouseEvents(EVENTID.GRIDCELLMOUSELEAVE, cell, mp.Clone(), button, clicks, delta);
	}

	public void OnCellMouseMove(CGridCellMe cell, POINT mp, MouseButtonsA button, int clicks, int delta) {
		CallCellMouseEvents(EVENTID.GRIDCELLMOUSEMOVE, cell, mp.Clone(), button, clicks, delta);
	}

	public void OnCellMouseUp(CGridCellMe cell, POINT mp, MouseButtonsA button, int clicks, int delta) {
		CallCellMouseEvents(EVENTID.GRIDCELLMOUSEUP, cell, mp.Clone(), button, clicks, delta);
	}

	public void OnLoad() {
		super.OnAdd();
		if (this.m_useAnimation) {
			StartTimer(this.m_timerID, 20);
		} else {
			StopTimer(this.m_timerID);
		}
	}

	public void OnLostFocus() {
		super.OnLostFocus();
		this.m_hoveredCell = null;
		this.m_hoveredRow = null;
	}

	public void OnMouseDown(POINT mp, MouseButtonsA button, int clicks, int delta) {
		super.OnMouseDown(mp.Clone(), button, clicks, delta);
		MouseEvent(mp.Clone(), button, clicks, delta, 1);
	}

	public void OnMouseLeave(POINT mp, MouseButtonsA button, int clicks, int delta) {
		super.OnMouseLeave(mp.Clone(), button, clicks, delta);
		if (this.m_hoveredCell != null) {
			OnCellMouseLeave(this.m_hoveredCell, mp.Clone(), button, clicks, delta);
			this.m_hoveredCell = null;
		}
		this.m_hoveredRow = null;
		Invalidate();
	}

	public void OnMouseMove(POINT mp, MouseButtonsA button, int clicks, int delta) {
		super.OnMouseMove(mp.Clone(), button, clicks, delta);
		MouseEvent(mp.Clone(), button, clicks, delta, 0);
	}

	public void OnMouseUp(POINT mp, MouseButtonsA button, int clicks, int delta) {
		super.OnMouseUp(mp.Clone(), button, clicks, delta);
		MouseEvent(mp.Clone(), button, clicks, delta, 2);
	}

	public void OnPaintForeground(CPaintMe paint, RECT clipRect) {
		ResetHeaderLayout();
		int width = GetWidth();
		int height = GetHeight();
		if ((width > 0) && (height > 0)) {
			INativeBaseMe nativeBase = GetNative();
			RECT drawRect = new RECT(0, 0, width, height);
			int allVisibleColumnsWidth = GetAllVisibleColumnsWidth();
			int v64 = 0;
			if (allVisibleColumnsWidth > 0) {
				v64 = allVisibleColumnsWidth > width ? width : allVisibleColumnsWidth;
			}

			int hHeight = this.m_headerVisible ? this.m_headerHeight : 0;
			int scrollH = 0;
			int scrollV = 0;
			CHScrollBarMe hScrollBar = GetHScrollBar();
			CVScrollBarMe vScrollBar = GetVScrollBar();
			if ((hScrollBar != null) && (hScrollBar.IsVisible())) {
				scrollH = -hScrollBar.GetPos();
			}
			if ((vScrollBar != null) && (vScrollBar.IsVisible())) {
				scrollV = -vScrollBar.GetPos();
			}
			OnSetEmptyClipRegion();
			POINT fPoint = new POINT(0, hHeight + 1 - scrollV);
			POINT ePoint = new POINT(0, height - 10 - scrollV);
			CGridRowMe fRow = GetRow(fPoint);
			CGridRowMe eRow = GetRow(ePoint);
			while ((eRow == null) && (ePoint.y > 0)) {
				ePoint.y -= 10;
				eRow = GetRow(ePoint);
			}
			if ((fRow != null) && (eRow != null)) {
				int fIndex = fRow.GetIndex();
				int eIndex = eRow.GetIndex();
				for (int i = fIndex; i <= eIndex; i++) {
					CGridRowMe row3 = (CGridRowMe) this.m_rows.get(i);
					if (row3.IsVisible()) {
						RECT rowRect = row3.GetBounds();
						rowRect.top += scrollV;
						rowRect.bottom += scrollV;
						row3.OnPaint(paint, rowRect.Clone(), row3.GetVisibleIndex() % 2 == 1);
						RECT lpDestRect = new RECT();
						ArrayList<CGridCellMe> cells = null;
						ArrayList<CGridCellMe> frozenCells = new ArrayList();
						for (int j = 0; j < 2; j++) {
							if (j == 0) {
								cells = row3.GetCells();
							} else {
								cells = frozenCells;
							}
							int right = 0;
							int count = cells.size();
							for (int k = 0; k < count; k++) {
								CGridCellMe item = (CGridCellMe) cells.get(k);
								CGridColumnMe column = item.GetColumn();
								if (column.IsVisible()) {
									RECT headerRect = column.GetHeaderRect();
									if ((j == 0) && (column.IsFrozen())) {
										right = headerRect.right;
										frozenCells.add(item);
									} else {
										if (!column.IsFrozen()) {
											headerRect.left += scrollH;
											headerRect.right += scrollH;
										}
										int cellWidth = column.GetWidth();
										int colSpan = item.GetColSpan();
										if (colSpan > 1) {
											for (int z = 1; z < colSpan; z++) {
												CGridColumnMe spanColumn = GetColumn(column.GetIndex() + z);
												if ((spanColumn != null) && (spanColumn.IsVisible())) {
													cellWidth += spanColumn.GetWidth();
												}
											}
										}
										int cellHeight = row3.GetHeight();
										int rowSpan = item.GetRowSpan();
										if (rowSpan > 1) {
											for (int n = 1; n < rowSpan; n++) {
												CGridRowMe spanRow = GetRow(i + n);
												if ((spanRow != null) && (spanRow.IsVisible())) {
													cellHeight += spanRow.GetHeight();
												}
											}
										}
										RECT cellRect = new RECT(headerRect.left, rowRect.top + this.m_verticalOffset,
												headerRect.left + cellWidth,
												rowRect.top + this.m_verticalOffset + cellHeight);
										cellRect.left += row3.GetHorizontalOffset();
										cellRect.right += row3.GetHorizontalOffset();
										RefObject<RECT> lpDestRectRef = new RefObject(lpDestRect);
										RefObject<RECT> cellRectRef = new RefObject(cellRect);
										RefObject<RECT> drawRectRef = new RefObject(drawRect);
										boolean tempVar = nativeBase.GetHost().GetIntersectRect(lpDestRectRef,
												cellRectRef, drawRectRef) > 0;
										if (tempVar) {
											if (item != null) {
												RECT cellClipRectr = cellRect.Clone();
												if (!column.IsFrozen()) {
													if (cellClipRectr.left < right) {
														cellClipRectr.left = right;
													}
													if (cellClipRectr.right < right) {
														cellClipRectr.right = right;
													}
												}
												item.OnPaint(paint, cellRect, cellClipRectr,
														row3.GetVisibleIndex() % 2 == 1);
												if ((this.m_editingCell != null) && (this.m_editingCell == item)
														&& (this.m_editTextBox != null)) {
													RECT editClipRect = new RECT(cellClipRectr.left - cellRect.left,
															cellClipRectr.top - cellRect.top,
															cellClipRectr.right - cellRect.left,
															cellClipRectr.bottom - cellRect.top);
													OnPaintEditTextBox(item, paint, cellRect, editClipRect);
												}
												if (this.m_gridLineColor != COLOR.EMPTY) {
													if (i == fIndex) {
														paint.DrawLine(this.m_gridLineColor, 1.0F, 0,
																cellClipRectr.left, cellClipRectr.top,
																cellClipRectr.right - 1, cellClipRectr.top);
													}
													if (k == 0) {
														paint.DrawLine(this.m_gridLineColor, 1.0F, 0,
																cellClipRectr.left, cellClipRectr.top,
																cellClipRectr.left, cellClipRectr.bottom - 1);
													}
													paint.DrawLine(this.m_gridLineColor, 1.0F, 0, cellClipRectr.left,
															cellClipRectr.bottom - 1, cellClipRectr.right - 1,
															cellClipRectr.bottom - 1);
													paint.DrawLine(this.m_gridLineColor, 1.0F, 0,
															cellClipRectr.right - 1, cellClipRectr.top,
															cellClipRectr.right - 1, cellClipRectr.bottom - 1);
												}
											}
										}
									}
								}
							}
						}
						frozenCells.clear();
						row3.OnPaintBorder(paint, rowRect.Clone(), row3.GetVisibleIndex() % 2 == 1);
					}
				}
			}
		}
	}

	public void OnPaintEditTextBox(CGridCellMe cell, CPaintMe paint, RECT rect, RECT clipRect) {
		this.m_editTextBox.SetRegion(clipRect);
		this.m_editTextBox.SetBounds(rect);
		this.m_editTextBox.SetDisplayOffset(false);
		this.m_editTextBox.BringToFront();
	}

	public void OnRowEditBegin(CGridRowMe row) {
		CControlMe editButton = row.GetEditButton();
		if ((editButton != null) && (!ContainsControl(editButton))) {
			POINT mp = GetMousePoint();
			if (mp.x - this.m_point.x < -10) {
				this.m_editingRow = row;
				AddControl(editButton);
				if (this.m_useAnimation) {
					editButton.SetLocation(new POINT(55536, 55536));
					this.m_editingRow.m_editState = 1;
				} else {
					this.m_editingRow.SetHorizontalOffset(
							-editButton.GetWidth() - ((this.m_vScrollBar != null) && (this.m_vScrollBar.IsVisible())
									? this.m_vScrollBar.GetWidth()
									: 0));
				}
			}
		}
	}

	public void OnRowEditEnd() {
		if (this.m_useAnimation) {
			this.m_editingRow.m_editState = 2;
		} else {
			this.m_editingRow.SetHorizontalOffset(0);
			RemoveControl(this.m_editingRow.GetEditButton());
			this.m_editingRow = null;
		}
	}

	public void OnSelectedCellsChanged() {
		CallEvents(EVENTID.GRIDSELECTEDCELLSCHANGED);
	}

	public void OnSelectedColumnsChanged() {
		CallEvents(EVENTID.GRIDSELECTEDCOLUMNSSCHANGED);
	}

	public void OnSelectedRowsChanged() {
		CallEvents(EVENTID.GRIDSELECTEDROWSCHANGED);
	}

	public void OnSetEmptyClipRegion() {
		ArrayList<CControlMe> controls = GetControls();
		int controlsSize = controls.size();
		RECT emptyRect = new RECT();
		for (int i = 0; i < controlsSize; i++) {
			CControlMe control = (CControlMe) controls.get(i);
			if ((this.m_editingRow == null) || (control != this.m_editingRow.GetEditButton())) {

				CScrollBarMe scrollBar = (CScrollBarMe) ((control instanceof CScrollBarMe) ? control : null);
				CGridColumnMe gridColumn = (CGridColumnMe) ((control instanceof CGridColumnMe) ? control : null);
				if ((control != this.m_editTextBox) && (scrollBar == null) && (gridColumn == null)) {
					control.SetRegion(emptyRect);
				}
			}
		}
	}

	public void OnTimer(int timerID) {
		super.OnTimer(timerID);
		if (this.m_timerID == timerID) {
			if (this.m_useAnimation) {
				boolean paint = false;
				if ((this.m_horizontalOffset != 0) || (this.m_verticalOffset != 0)) {
					if (this.m_horizontalOffset != 0) {
						this.m_horizontalOffset = (this.m_horizontalOffset * 2 / 3);
						if ((this.m_horizontalOffset >= -1) && (this.m_horizontalOffset <= 1)) {
							this.m_horizontalOffset = 0;
						}
					}
					if (this.m_verticalOffset != 0) {
						this.m_verticalOffset = (this.m_verticalOffset * 2 / 3);
						if ((this.m_verticalOffset >= -1) && (this.m_verticalOffset <= 1)) {
							this.m_verticalOffset = 0;
						}
					}
					paint = true;
				}
				int animateAddRowsSize = this.m_animateAddRows.size();
				if (animateAddRowsSize > 0) {
					int width = GetAllVisibleColumnsWidth();
					int step = width / 10;
					if (step < 10) {
						step = 10;
					}
					for (int i = 0; i < animateAddRowsSize; i++) {
						CGridRowMe row = (CGridRowMe) this.m_animateAddRows.get(i);
						int horizontalOffset = row.GetHorizontalOffset();
						if (horizontalOffset > step) {
							horizontalOffset -= step;
						} else {
							horizontalOffset = 0;
						}
						row.SetHorizontalOffset(horizontalOffset);
						if (horizontalOffset == 0) {
							this.m_animateAddRows.remove(i);
							animateAddRowsSize--;
							i--;
						}
					}
					paint = true;
				}
				int animateRemoveRowsSize = this.m_animateRemoveRows.size();
				if (animateRemoveRowsSize > 0) {
					int width = GetAllVisibleColumnsWidth();
					int step = width / 10;
					if (step < 10) {
						step = 10;
					}
					for (int i = 0; i < animateRemoveRowsSize; i++) {
						CGridRowMe row = (CGridRowMe) this.m_animateRemoveRows.get(i);
						int horizontalOffset = row.GetHorizontalOffset();
						if (horizontalOffset <= width) {
							horizontalOffset += step;
						}
						row.SetHorizontalOffset(horizontalOffset);
						if (horizontalOffset > width) {
							this.m_animateRemoveRows.remove(i);
							RemoveRow(row);
							Update();
							animateRemoveRowsSize--;
							i--;
						}
					}
					paint = true;
				}
				if (this.m_editingRow != null) {
					int scrollH = 0;
					int scrollV = 0;
					CHScrollBarMe hScrollBar = GetHScrollBar();
					CVScrollBarMe vScrollBar = GetVScrollBar();
					int vScrollBarW = 0;
					if ((hScrollBar != null) && (hScrollBar.IsVisible())) {
						scrollH = -hScrollBar.GetPos();
					}
					if ((vScrollBar != null) && (vScrollBar.IsVisible())) {
						scrollV = -vScrollBar.GetPos();
						vScrollBarW = vScrollBar.GetWidth();
					}
					if (this.m_editingRow.m_editState == 1) {
						CControlMe editButton = this.m_editingRow.GetEditButton();
						boolean isOver = false;
						int sub = editButton.GetWidth() + this.m_editingRow.GetHorizontalOffset() + vScrollBarW;
						if (sub < 2) {
							isOver = true;
							this.m_editingRow.SetHorizontalOffset(-editButton.GetWidth() - vScrollBarW);
						} else {
							this.m_editingRow.SetHorizontalOffset(this.m_editingRow.GetHorizontalOffset() - 10);
						}
						editButton.SetLocation(new POINT(
								GetAllVisibleColumnsWidth() + scrollH + this.m_editingRow.GetHorizontalOffset(),
								this.m_editingRow.GetBounds().top + scrollV));

						if (isOver) {
							this.m_editingRow.m_editState = 0;
						}
					}
					if (this.m_editingRow.m_editState == 2) {
						CControlMe editButton = this.m_editingRow.GetEditButton();
						boolean isOver = false;
						if (this.m_editingRow.GetHorizontalOffset() < 0) {
							this.m_editingRow.SetHorizontalOffset(this.m_editingRow.GetHorizontalOffset() + 10);
							if (this.m_editingRow.GetHorizontalOffset() >= 0) {
								this.m_editingRow.SetHorizontalOffset(0);
								isOver = true;
							}
						}
						editButton.SetLocation(new POINT(
								GetAllVisibleColumnsWidth() + scrollH + this.m_editingRow.GetHorizontalOffset(),
								this.m_editingRow.GetBounds().top + scrollV));

						if (isOver) {
							RemoveControl(editButton);
							this.m_editingRow.m_editState = 0;
							this.m_editingRow = null;
						}
					}
					paint = true;
				}
				if (paint) {
					Invalidate();
				}
			}
		}
	}

	public void OnVisibleChanged() {
		super.OnVisibleChanged();
		this.m_hoveredCell = null;
		this.m_hoveredRow = null;
	}

	public void RemoveColumn(CGridColumnMe column) {
		boolean selectedChanged = false;
		int selectedColumnsSize = this.m_selectedColumns.size();
		for (int i = 0; i < selectedColumnsSize; i++) {
			if (this.m_selectedColumns.get(i) == column) {
				this.m_selectedColumns.remove(column);
				selectedChanged = true;
				break;
			}
		}
		this.m_columns.remove(column);
		int columnsSize = this.m_columns.size();
		for (int i = 0; i < columnsSize; i++) {
			((CGridColumnMe) this.m_columns.get(i)).SetIndex(i);
		}
		RemoveControl(column);
		int rowsSize = this.m_rows.size();
		for (int i = 0; i < rowsSize; i++) {
			CGridRowMe row = (CGridRowMe) this.m_rows.get(i);
			row.RemoveCell(column.GetIndex());
		}
		if (selectedChanged) {
			OnSelectedColumnsChanged();
		}
	}

	public void RemoveRow(CGridRowMe row) {
		if (this.m_editingRow != null) {
			if (ContainsControl(this.m_editingRow.GetEditButton())) {
				RemoveControl(this.m_editingRow.GetEditButton());
			}
			this.m_editingRow.m_editState = 0;
			this.m_editingRow = null;
		}
		boolean selectedChanged = false;
		boolean selected = false;
		int selectedRowsSize = this.m_selectedRows.size();
		for (int i = 0; i < selectedRowsSize; i++) {
			CGridRowMe selectedRow = (CGridRowMe) this.m_selectedRows.get(i);
			if (selectedRow == row) {
				selected = true;
				break;
			}
		}
		if (selected) {
			CGridRowMe otherRow = SelectFrontRow();
			if (otherRow != null) {
				selectedChanged = true;

			} else if (otherRow != null) {
				selectedChanged = true;
			}
		}

		if (this.m_hoveredRow == row) {
			this.m_hoveredCell = null;
			this.m_hoveredRow = null;
		}
		row.OnRemove();
		this.m_rows.remove(row);
		int rowsSize = this.m_rows.size();
		if (rowsSize == 0) {
			this.m_selectedCells.clear();
			this.m_selectedRows.clear();
		}
		int visibleIndex = 0;
		for (int i = 0; i < rowsSize; i++) {
			CGridRowMe gridRow = (CGridRowMe) this.m_rows.get(i);
			gridRow.SetIndex(i);
			if (gridRow.IsVisible()) {
				gridRow.SetVisibleIndex(visibleIndex);
				visibleIndex++;
			}
		}
		if (selected) {
			if (selectedChanged) {
				OnSelectedRowsChanged();
			} else {
				this.m_selectedCells.clear();
				this.m_selectedRows.clear();
			}
		}
	}

	public void ResetHeaderLayout() {
		if (!this.m_lockUpdate) {
			int left = 0;
			int top = 0;
			int scrollH = 0;
			int scrollV = 0;
			CHScrollBarMe hScrollBar = GetHScrollBar();
			CVScrollBarMe vScrollBar = GetVScrollBar();
			int vScrollBarW = 0;
			if ((hScrollBar != null) && (hScrollBar.IsVisible())) {
				scrollH = -hScrollBar.GetPos();
			}
			if ((vScrollBar != null) && (vScrollBar.IsVisible())) {
				scrollV = -vScrollBar.GetPos();
				vScrollBarW = vScrollBar.GetWidth();
			}
			int headerHeight = this.m_headerVisible ? this.m_headerHeight : 0;
			CGridColumnMe draggingColumn = null;
			int colSize = this.m_columns.size();
			for (int i = 0; i < colSize; i++) {
				CGridColumnMe column = (CGridColumnMe) this.m_columns.get(i);
				if (column.IsVisible()) {
					RECT cellRect = new RECT(left + this.m_horizontalOffset, top + this.m_verticalOffset,
							left + this.m_horizontalOffset + column.GetWidth(),
							top + headerHeight + this.m_verticalOffset);
					column.SetHeaderRect(cellRect);
					if (column.IsDragging()) {
						draggingColumn = column;
						column.SetBounds(new RECT(column.GetLeft(), cellRect.top, column.GetRight(), cellRect.bottom));
					} else {
						if (!column.IsFrozen()) {
							cellRect.left += scrollH;
							cellRect.right += scrollH;
						}
						column.SetBounds(cellRect);
					}
					left += column.GetWidth();
				}
			}

			for (int i = colSize - 1; i >= 0; i--) {
				((CGridColumnMe) this.m_columns.get(i)).BringToFront();
			}
			if (draggingColumn != null) {
				draggingColumn.BringToFront();
			}
			if ((this.m_editingRow != null) && (this.m_editingRow.m_editState == 0)
					&& (this.m_editingRow.GetEditButton() != null)) {
				CControlMe control = this.m_editingRow.GetEditButton();
				control.SetLocation(new POINT(GetAllVisibleColumnsWidth() - control.GetWidth() + scrollH - vScrollBarW,
						this.m_editingRow.GetBounds().top + scrollV));
			}
		}
	}

	public CGridRowMe SelectFrontRow() {
		int rowsSize = this.m_rows.size();
		if (rowsSize == 0) {
			this.m_selectedRows.clear();
			this.m_selectedCells.clear();
			return null;
		}
		CGridRowMe fontRow = null;
		ArrayList<CGridRowMe> selectedRows = GetSelectedRows();
		if (selectedRows.size() == 1) {
			CGridRowMe selectedRow = (CGridRowMe) selectedRows.get(0);
			int selectedIndex = selectedRow.GetIndex();
			for (int i = selectedIndex - 1; i >= 0; i--) {
				if ((i < rowsSize) && (((CGridRowMe) this.m_rows.get(i)).IsVisible())) {
					fontRow = (CGridRowMe) this.m_rows.get(i);
					break;
				}
			}

			if (this.m_selectionMode == GridSelectionMode.SelectFullRow) {
				if (fontRow != null) {
					this.m_selectedRows.clear();
					this.m_selectedRows.add(fontRow);
				} else {
					this.m_selectedRows.clear();
					fontRow = (CGridRowMe) this.m_rows.get(this.m_rows.size() - 1);
					this.m_selectedRows.add(fontRow);
					CVScrollBarMe vScrollBar = GetVScrollBar();
					if ((vScrollBar != null) && (vScrollBar.IsVisible())) {
						vScrollBar.ScrollToEnd();
					}
				}
			}
		}
		return fontRow;
	}

	public CGridRowMe SelectNextRow() {
		int rowsSize = this.m_rows.size();
		if (rowsSize == 0) {
			this.m_selectedRows.clear();
			this.m_selectedCells.clear();
			return null;
		}
		CGridRowMe nextRow = null;
		ArrayList<CGridRowMe> selectedRows = GetSelectedRows();
		if (selectedRows.size() == 1) {
			CGridRowMe selectedRow = (CGridRowMe) selectedRows.get(0);
			int selectedIndex = selectedRow.GetIndex();
			for (int i = selectedIndex + 1; i < rowsSize; i++) {
				if ((i >= 0) && (((CGridRowMe) this.m_rows.get(i)).IsVisible())) {
					nextRow = (CGridRowMe) this.m_rows.get(i);
					break;
				}
			}
			if (this.m_selectionMode == GridSelectionMode.SelectFullRow) {
				if (nextRow != null) {
					this.m_selectedRows.clear();
					this.m_selectedRows.add(nextRow);
				} else {
					this.m_selectedRows.clear();
					nextRow = (CGridRowMe) this.m_rows.get(0);
					this.m_selectedRows.add(nextRow);
					CVScrollBarMe vScrollBar = GetVScrollBar();
					if ((vScrollBar != null) && (vScrollBar.IsVisible())) {
						vScrollBar.ScrollToBegin();
					}
				}
			}
		}
		return nextRow;
	}

	public void SetProperty(String name, String value) {
		if (name.equals("allowdragrow")) {
			SetAllowDragRow(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("allowhoveredrow")) {
			SetAllowHoveredRow(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("celleditmode")) {
			value = value.toLowerCase();
			if (value.equals("doubleclick")) {
				SetCellEditMode(GridCellEditMode.DoubleClick);
			} else if (value.equals("none")) {
				SetCellEditMode(GridCellEditMode.None);
			} else if (value.equals("singleclick")) {
				SetCellEditMode(GridCellEditMode.SingleClick);
			}
		} else if (name.equals("gridlinecolor")) {
			SetGridLineColor(CStrMe.ConvertStrToColor(value));
		} else if (name.equals("headerheight")) {
			SetHeaderHeight(CStrMe.ConvertStrToInt(value));
		} else if (name.equals("headervisible")) {
			SetHeaderVisible(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("horizontaloffset")) {
			SetHorizontalOffset(CStrMe.ConvertStrToInt(value));
		} else if (name.equals("multiselect")) {
			SetMultiSelect(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("selectionmode")) {
			value = value.toLowerCase();
			if (value.equals("selectcell")) {
				SetSelectionMode(GridSelectionMode.SelectCell);
			} else if (value.equals("selectfullcolumn")) {
				SetSelectionMode(GridSelectionMode.SelectFullColumn);
			} else if (value.equals("selectfullrow")) {
				SetSelectionMode(GridSelectionMode.SelectFullRow);
			} else {
				SetSelectionMode(GridSelectionMode.SelectNone);
			}
		} else if (name.equals("useanimation")) {
			SetUseAnimation(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("verticaloffset")) {
			SetVerticalOffset(CStrMe.ConvertStrToInt(value));
		} else {
			super.SetProperty(name, value);
		}
	}

	public void SortColumn(CGridMe grid, CGridColumnMe column, GridColumnSortMode sortMode) {
		if (column.AllowSort()) {

			int colSize = grid.m_columns.size();
			for (int i = 0; i < colSize; i++) {
				if (grid.m_columns.get(i) != column) {
					((CGridColumnMe) grid.m_columns.get(i)).SetSortMode(GridColumnSortMode.None);
				} else {
					((CGridColumnMe) grid.m_columns.get(i)).SetSortMode(sortMode);
				}
			}

			if (this.m_sort != null) {
				this.m_sort.SortColumn(grid, column, sortMode);
			}
			grid.Update();
			grid.Invalidate();
		}
	}

	public void Update() {
		if (GetNative() != null) {
			if (!this.m_lockUpdate) {
				super.Update();
				if (IsVisible()) {
					int colSize = this.m_columns.size();
					for (int i = 0; i < colSize; i++) {
						((CGridColumnMe) this.m_columns.get(i)).SetIndex(i);
					}
					int rowSize = this.m_rows.size();
					int visibleIndex = 0;
					int rowTop = this.m_headerVisible ? this.m_headerHeight : 0;
					int allVisibleColumnsWidth = GetAllVisibleColumnsWidth();
					this.m_hasUnVisibleRow = false;
					for (int i = 0; i < rowSize; i++) {
						CGridRowMe gridRow = (CGridRowMe) this.m_rows.get(i);
						gridRow.SetIndex(i);
						if (gridRow.IsVisible()) {
							gridRow.SetVisibleIndex(i);
							int rowHeight = gridRow.GetHeight();
							RECT rowRect = new RECT(0, rowTop, allVisibleColumnsWidth, rowTop + rowHeight);
							gridRow.SetBounds(rowRect);
							rowTop += rowHeight;
							visibleIndex++;
						} else {
							gridRow.SetVisibleIndex(-1);
							RECT rowRect = new RECT(0, rowTop, allVisibleColumnsWidth, rowTop);
							gridRow.SetBounds(rowRect);
						}
					}
					CHScrollBarMe hScrollBar = GetHScrollBar();
					CVScrollBarMe vScrollBar = GetVScrollBar();
					if ((vScrollBar != null) && (vScrollBar.IsVisible())) {
						int top = this.m_headerVisible ? this.m_headerHeight : 0;
						vScrollBar.SetTop(top);
						int height = GetHeight() - top
								- ((hScrollBar != null) && (hScrollBar.IsVisible()) ? hScrollBar.GetHeight() : 0);
						vScrollBar.SetHeight(height);
						vScrollBar.SetPageSize(height);
						if (rowSize > 0) {
							vScrollBar.SetLineSize(GetAllVisibleRowsHeight() / rowSize);
						}
					}
				}
			}
		}
	}

	public void UpdateSortColumn() {
		int colSize = this.m_columns.size();
		for (int i = 0; i < colSize; i++) {
			if (((CGridColumnMe) this.m_columns.get(i)).GetSortMode() != GridColumnSortMode.None) {
				SortColumn(this, (CGridColumnMe) this.m_columns.get(i),
						((CGridColumnMe) this.m_columns.get(i)).GetSortMode());
				break;
			}
		}
	}
}
