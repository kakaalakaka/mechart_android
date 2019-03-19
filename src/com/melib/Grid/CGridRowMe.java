package com.melib.Grid;

import java.util.ArrayList;
import java.util.Arrays;
import com.melib.Base.CPaintMe;
import com.melib.Base.CPropertyMe;
import com.melib.Base.CStrMe;
import com.melib.Base.CControlMe;
import com.melib.Base.RECT;
import com.melib.Base.RefObject;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public class CGridRowMe implements CPropertyMe {
	protected void finalize() throws Throwable {
		Dispose();
	}

	public ArrayList<CGridCellMe> m_cells = new ArrayList();

	public int m_editState;

	protected boolean m_allowEdit;

	public boolean AllowEdit() {
		return this.m_allowEdit;
	}

	public void SetAllowEdit(boolean allowEdit) {
		this.m_allowEdit = allowEdit;
	}

	protected RECT m_bounds = new RECT();
	protected CControlMe m_editButton;

	public RECT GetBounds() {
		return this.m_bounds.Clone();
	}

	public void SetBounds(RECT value) {
		this.m_bounds = value.Clone();
	}

	public CControlMe GetEditButton() {
		return this.m_editButton;
	}

	public void SetEditButton(CControlMe editButton) {
		this.m_editButton = editButton;
	}

	protected CGridMe m_grid = null;

	public CGridMe GetGrid() {
		return this.m_grid;
	}

	public void SetGrid(CGridMe value) {
		this.m_grid = value;
	}

	protected int height = 20;

	public int GetHeight() {
		return this.height;
	}

	public void SetHeight(int value) {
		this.height = value;
	}

	protected int m_horizontalOffset = 0;

	public int GetHorizontalOffset() {
		return this.m_horizontalOffset;
	}

	public void SetHorizontalOffset(int value) {
		this.m_horizontalOffset = value;
	}

	protected int m_index = -1;

	public int GetIndex() {
		return this.m_index;
	}

	public void SetIndex(int value) {
		this.m_index = value;
	}

	protected boolean m_isDisposed = false;

	public boolean IsDisposed() {
		return this.m_isDisposed;
	}

	protected Object m_tag = null;

	public Object GetTag() {
		return this.m_tag;
	}

	public void SetTag(Object value) {
		this.m_tag = value;
	}

	protected boolean m_visible = true;

	public boolean IsVisible() {
		return this.m_visible;
	}

	public void SetVisible(boolean value) {
		this.m_visible = value;
	}

	protected int m_visibleIndex = -1;

	public int GetVisibleIndex() {
		return this.m_visibleIndex;
	}

	public void SetVisibleIndex(int value) {
		this.m_visibleIndex = value;
	}

	public void AddCell(int columnIndex, CGridCellMe cell) {
		cell.SetGrid(this.m_grid);
		cell.SetColumn(this.m_grid.GetColumn(columnIndex));
		cell.SetRow(this);
		this.m_cells.add(cell);
		cell.OnAdd();
	}

	public void AddCell(String columnName, CGridCellMe cell) {
		cell.SetGrid(this.m_grid);
		cell.SetColumn(this.m_grid.GetColumn(columnName));
		cell.SetRow(this);
		this.m_cells.add(cell);
		cell.OnAdd();
	}

	public void ClearCells() {
		int count = this.m_cells.size();
		for (int i = 0; i < count; i++) {
			((CGridCellMe) this.m_cells.get(i)).OnRemove();
			((CGridCellMe) this.m_cells.get(i)).Dispose();
		}
		this.m_cells.clear();
	}

	public void Dispose() {
		if (!this.m_isDisposed) {
			this.m_isDisposed = (!this.m_isDisposed);
			this.m_cells.clear();
		}
	}

	public CGridCellMe GetCell(int columnIndex) {
		int count = this.m_cells.size();
		if (count > 0) {
			if ((columnIndex >= 0) && (columnIndex < count)) {
				if (((CGridCellMe) this.m_cells.get(columnIndex)).GetColumn().GetIndex() == columnIndex) {
					return (CGridCellMe) this.m_cells.get(columnIndex);
				}
			}
			for (int i = 0; i < count; i++) {
				if (((CGridCellMe) this.m_cells.get(i)).GetColumn().GetIndex() == columnIndex) {
					return (CGridCellMe) this.m_cells.get(i);
				}
			}
		}
		return null;
	}

	public CGridCellMe GetCell(String columnName) {
		int count = this.m_cells.size();
		for (int i = 0; i < count; i++) {
			if (((CGridCellMe) this.m_cells.get(i)).GetColumn().GetName().equals(columnName)) {
				return (CGridCellMe) this.m_cells.get(i);
			}
		}
		return null;
	}

	public ArrayList<CGridCellMe> GetCells() {
		return this.m_cells;
	}

	public void GetProperty(String name, RefObject<String> value, RefObject<String> type) {
		if (name.equals("allowedit")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(AllowEdit());
		}
		if (name.equals("height")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertIntToStr(GetHeight());
		} else if (name.equals("visible")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertBoolToStr(IsVisible());
		} else {
			type.argvalue = "undefined";
			value.argvalue = "";
		}
	}

	public ArrayList<String> GetPropertyNames() {
		ArrayList<String> propertyNames = new ArrayList();
		propertyNames.addAll(Arrays.asList(new String[] { "AllowEdit", "Height", "Visible" }));
		return propertyNames;
	}

	public void OnAdd() {
	}

	public void OnPaint(CPaintMe paint, RECT clipRect, boolean isAlternate) {
	}

	public void OnPaintBorder(CPaintMe paint, RECT clipRect, boolean isAlternate) {
	}

	public void OnRemove() {
	}

	public void RemoveCell(int columnIndex) {
		int count = this.m_cells.size();
		if ((columnIndex >= 0) && (columnIndex < count)) {
			CGridCellMe item = (CGridCellMe) this.m_cells.get(columnIndex);
			if (item.GetColumn().GetIndex() == columnIndex) {
				this.m_cells.remove(item);
				item.OnRemove();
				return;
			}
			for (int i = 0; i < count; i++) {
				item = (CGridCellMe) this.m_cells.get(i);
				if (item.GetColumn().GetIndex() == columnIndex) {
					this.m_cells.remove(item);
					item.OnRemove();
					break;
				}
			}
		}
	}

	public void SetProperty(String name, String value) {
		if (name.equals("allowedit")) {
			SetAllowEdit(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("height")) {
			SetHeight(CStrMe.ConvertStrToInt(value));
		} else if (name.equals("visible")) {
			SetVisible(CStrMe.ConvertStrToBool(value));
		}
	}
}
