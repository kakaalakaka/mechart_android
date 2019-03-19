package com.melib.Grid;

import java.util.ArrayList;
import java.util.Arrays;
import com.melib.Base.CStrMe;
import com.melib.Base.INativeBaseMe;
import com.melib.Base.MouseButtonsA;
import com.melib.Base.POINT;
import com.melib.Base.RECT;
import com.melib.Base.RefObject;
import com.melib.Button.CButtonMe;
import com.melib.ScrollBar.CHScrollBarMe;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class CGridBandMe extends CButtonMe {
	public CGridBandMe() {
		SetWidth(100);
	}

	public ArrayList<CGridBandMe> m_bands = new ArrayList();

	public ArrayList<CBandedGridColumnMe> m_columns = new ArrayList();

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

	protected CBandedGridMe m_grid = null;

	public CBandedGridMe GetGrid() {
		return this.m_grid;
	}

	public void SetGrid(CBandedGridMe value) {
		this.m_grid = value;
	}

	protected int m_index = -1;

	public int GetIndex() {
		return this.m_index;
	}

	public void SetIndex(int value) {
		this.m_index = value;
	}

	protected CGridBandMe m_parentBand = null;

	public CGridBandMe GetParentBand() {
		return this.m_parentBand;
	}

	public void SetParentBand(CGridBandMe value) {
		this.m_parentBand = value;
	}

	public void AddBand(CGridBandMe band) {
		band.SetGrid(this.m_grid);
		band.SetParentBand(this);
		this.m_bands.add(band);
		int count = this.m_bands.size();
		for (int i = 0; i < count; i++) {
			((CGridBandMe) this.m_bands.get(i)).SetIndex(i);
		}
		this.m_grid.AddControl(band);
	}

	public void AddColumn(CBandedGridColumnMe column) {
		column.SetBand(this);
		this.m_columns.add(column);
		this.m_grid.AddColumn(column);
	}

	public void ClearBands() {
		int count = this.m_bands.size();
		for (int i = 0; i < count; i++) {
			CGridBandMe band = (CGridBandMe) this.m_bands.get(i);
			this.m_grid.RemoveControl(band);
			band.Dispose();
		}
		this.m_bands.clear();
	}

	public void ClearColumns() {
		int count = this.m_columns.size();
		for (int i = 0; i < count; i++) {
			CBandedGridColumnMe column = (CBandedGridColumnMe) this.m_columns.get(i);
			this.m_grid.RemoveColumn(column);
			column.Dispose();
		}
		this.m_columns.clear();
	}

	public ArrayList<CBandedGridColumnMe> GetAllChildColumns() {
		ArrayList<CBandedGridColumnMe> list = new ArrayList();
		int count = this.m_columns.size();
		for (int i = 0; i < count; i++) {
			CBandedGridColumnMe item = (CBandedGridColumnMe) this.m_columns.get(i);
			list.add(item);
		}
		int bandCount = this.m_bands.size();

		for (int j = 0; j < bandCount; j++) {

			CGridBandMe gridBand = (CGridBandMe) this.m_bands.get(j);
			ArrayList<CBandedGridColumnMe> allChildColumns = gridBand.GetAllChildColumns();
			int childColumnsCount = allChildColumns.size();
			for (int k = 0; k < childColumnsCount; k++) {
				CBandedGridColumnMe column2 = (CBandedGridColumnMe) allChildColumns.get(k);
				list.add(column2);
			}
		}
		return list;
	}

	public ArrayList<CGridBandMe> GetBands() {
		return this.m_bands;
	}

	public ArrayList<CBandedGridColumnMe> GetColumns() {
		return this.m_columns;
	}

	public String GetControlType() {
		return "GridBand";
	}

	public void GetProperty(String name, RefObject<String> value, RefObject<String> type) {
		if (name.equals("allowresize")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(AllowResize());
		}
	}

	public ArrayList<String> GetPropertyNames() {
		ArrayList<String> propertyNames = super.GetPropertyNames();
		propertyNames.addAll(Arrays.asList(new String[] { "AllowResize" }));
		return propertyNames;
	}

	public void Dispose() {
		ClearBands();
		ClearColumns();
		super.Dispose();
	}

	public void InsertBand(int index, CGridBandMe band) {
		band.SetGrid(this.m_grid);
		band.SetParentBand(this);
		this.m_bands.add(index, band);
		int count = this.m_bands.size();
		for (int i = 0; i < count; i++) {
			((CGridBandMe) this.m_bands.get(i)).SetIndex(i);
		}
		this.m_grid.AddControl(band);
	}

	public void InsertColumn(int index, CBandedGridColumnMe column) {
		column.SetBand(this);
		this.m_columns.add(index, column);
		this.m_grid.AddColumn(column);
	}

	public void OnMouseDown(POINT mp, MouseButtonsA button, int clicks, int delta) {
		super.OnMouseDown(mp.Clone(), button, clicks, delta);
		if ((button == MouseButtonsA.Left) && (clicks == 1)) {
			if (this.m_allowResize) {
				ArrayList<CGridBandMe> bands = null;
				if (this.m_parentBand != null) {
					bands = this.m_parentBand.GetBands();
				} else {
					bands = this.m_grid.GetBands();
				}
				int count = bands.size();
				if ((this.m_index > 0) && (mp.x < 5)) {
					this.m_resizeState = 1;
					this.m_beginWidth = ((CGridBandMe) bands.get(this.m_index - 1)).GetWidth();
				} else if (((this.m_parentBand == null) || (this.m_index < count - 1)) && (mp.x > GetWidth() - 5)) {
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
			ArrayList<CGridBandMe> bands = null;
			if (this.m_parentBand != null) {
				bands = this.m_parentBand.GetBands();
			} else {
				bands = this.m_grid.GetBands();
			}
			int count = bands.size();
			int width = GetWidth();
			if (this.m_resizeState > 0) {
				POINT curPosint = GetNative().GetMousePoint();
				int newWidth = this.m_beginWidth + (curPosint.x - this.m_mouseDownPoint.x);
				if (newWidth > 0) {
					if (this.m_resizeState == 1) {
						CGridBandMe leftBand = (CGridBandMe) bands.get(this.m_index - 1);
						int leftWidth = leftBand.GetWidth();
						leftBand.SetWidth(newWidth);
						width += leftWidth - newWidth;
						SetWidth(width);
					} else if (this.m_resizeState == 2) {
						SetWidth(newWidth);
						if (this.m_index < count - 1) {
							CGridBandMe rightBand = (CGridBandMe) bands.get(this.m_index + 1);
							int rightWidth = rightBand.GetWidth();
							rightWidth += width - newWidth;
							rightBand.SetWidth(rightWidth);

						} else if (this.m_grid != null) {
							this.m_grid.ResetHeaderLayout();
							this.m_grid.Update();
						}
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
		if (this.m_resizeState != 0) {
			this.m_resizeState = 0;
			if (this.m_grid != null) {
				this.m_grid.Invalidate();
			}
		}
	}

	public void RemoveBand(CGridBandMe band) {
		if (this.m_bands.contains(band)) {
			this.m_bands.remove(band);
			int size = this.m_bands.size();
			for (int i = 0; i < size; i++) {
				((CGridBandMe) this.m_bands.get(i)).SetIndex(i);
			}
			this.m_grid.RemoveControl(band);
		}
	}

	public void RemoveColumn(CBandedGridColumnMe column) {
		if (this.m_columns.contains(column)) {
			this.m_columns.remove(column);
			this.m_grid.RemoveColumn(column);
		}
	}

	public void ResetHeaderLayout() {
		int bandsSize = this.m_bands.size();
		RECT bands = GetBounds();
		int left = bands.left;
		int width = GetWidth();
		if (bandsSize == 0) {
			int scrollH = 0;
			CHScrollBarMe hScrollBar = GetGrid().GetHScrollBar();
			if ((hScrollBar != null) && (hScrollBar.IsVisible())) {
				scrollH = -hScrollBar.GetPos();
			}
			int columnsSize = this.m_columns.size();
			for (int i = 0; i < columnsSize; i++) {
				CBandedGridColumnMe column = (CBandedGridColumnMe) this.m_columns.get(i);
				if (column.IsVisible()) {
					int columnWidth = column.GetWidth();
					if ((i == columnsSize - 1) || (left + columnWidth > width + bands.left)) {
						columnWidth = width + bands.left - left;
					}
					RECT cellRect = new RECT(left, bands.bottom, left + columnWidth, bands.bottom + column.GetHeight());
					column.SetBounds(cellRect);
					cellRect.left -= scrollH;
					cellRect.right -= scrollH;
					column.SetHeaderRect(cellRect);
					left += columnWidth;
				}
			}
		} else {
			for (int i = 0; i < bandsSize; i++) {
				CGridBandMe band = (CGridBandMe) this.m_bands.get(i);
				if (band.IsVisible()) {
					int bandWidth = band.GetWidth();
					if ((i == bandsSize - 1) || (left + bandWidth > width + bands.left)) {
						bandWidth = width + bands.left - left;
					}
					RECT cellRect = new RECT(left, bands.bottom, left + bandWidth, bands.bottom + band.GetHeight());
					band.SetBounds(cellRect);
					band.ResetHeaderLayout();
					left += bandWidth;
				}
			}
		}
	}

	public void SetProperty(String name, String value) {
		if (name.equals("allowresize")) {
			SetAllowResize(CStrMe.ConvertStrToBool(value));
		} else {
			super.SetProperty(name, value);
		}
	}
}
