package com.melib.Grid;

import java.util.ArrayList;
import com.melib.Base.CControlMe;
import com.melib.Base.RECT;
import com.melib.ScrollBar.CHScrollBarMe;
import com.melib.ScrollBar.CScrollBarMe;
import com.melib.ScrollBar.CVScrollBarMe;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class CBandedGridMe extends CGridMe {
	protected ArrayList<CGridBandMe> m_bands = new ArrayList();

	protected int GetAllVisibleBandsWidth() {
		int width = 0;
		int size = this.m_bands.size();
		for (int i = 0; i < size; i++) {
			CGridBandMe gridBand = (CGridBandMe) this.m_bands.get(i);
			if (gridBand.IsVisible()) {
				width += gridBand.GetWidth();
			}
		}
		return width;
	}

	public void AddBand(CGridBandMe band) {
		band.SetGrid(this);
		this.m_bands.add(band);
		int size = this.m_bands.size();
		for (int i = 0; i < size; i++) {
			((CGridBandMe) this.m_bands.get(i)).SetIndex(i);
		}
		AddControl(band);
	}

	public void AddColumn(CGridColumnMe column) {
		CBandedGridColumnMe bandedGridColumn = (CBandedGridColumnMe) ((column instanceof CBandedGridColumnMe) ? column
				: null);
		if (bandedGridColumn != null) {
			column.SetGrid(this);
			this.m_columns.add(column);
			AddControl(column);
		}
	}

	public void ClearBands() {
		int size = this.m_bands.size();
		for (int i = 0; i < size; i++) {
			CGridBandMe gridBand = (CGridBandMe) this.m_bands.get(i);
			RemoveControl(gridBand);
			gridBand.Dispose();
		}
		this.m_bands.clear();
	}

	public void ClearColumns() {
	}

	public ArrayList<CGridBandMe> GetBands() {
		return this.m_bands;
	}

	public String GetControlType() {
		return "BandedGrid";
	}

	public int GetContentWidth() {
		CHScrollBarMe hScrollBar = GetHScrollBar();
		CVScrollBarMe vScrollBar = GetVScrollBar();
		int width = 0;
		ArrayList<CControlMe> controls = GetControls();
		int controlsSize = controls.size();
		for (int i = 0; i < controlsSize; i++) {
			CControlMe control = (CControlMe) controls.get(i);
			if ((control.IsVisible()) && (control != hScrollBar) && (control != vScrollBar)) {
				int right = control.GetRight();
				if (right > width) {
					width = right;
				}
			}
		}
		int bandsWidth = GetAllVisibleBandsWidth();
		return width > bandsWidth ? width : bandsWidth;
	}

	public void InsertBand(int index, CGridBandMe band) {
		band.SetGrid(this);
		this.m_bands.add(index, band);
		int size = this.m_bands.size();
		for (int i = 0; i < size; i++) {
			((CGridBandMe) this.m_bands.get(i)).SetIndex(i);
		}
		AddControl(band);
	}

	public void OnSetEmptyClipRegion() {
		ArrayList<CControlMe> controls = GetControls();
		int controlsSize = controls.size();
		RECT rect = new RECT();
		for (int i = 0; i < controlsSize; i++) {
			CControlMe control = (CControlMe) controls.get(i);
			CScrollBarMe scrollBar = (CScrollBarMe) ((control instanceof CScrollBarMe) ? control : null);
			CGridColumnMe gridColumn = (CGridColumnMe) ((control instanceof CGridColumnMe) ? control : null);
			CGridBandMe gridBand = (CGridBandMe) ((control instanceof CGridBandMe) ? control : null);
			if ((control != GetEditTextBox()) && (scrollBar == null) && (gridColumn == null) && (gridBand == null)) {
				control.SetRegion(rect);
			}
		}
	}

	public void RemoveBand(CGridBandMe band) {
		if (this.m_bands.contains(band)) {
			this.m_bands.remove(band);
			int bandsSize = this.m_bands.size();
			for (int i = 0; i < bandsSize; i++) {
				((CGridBandMe) this.m_bands.get(i)).SetIndex(i);
			}
			RemoveControl(band);
		}
	}

	public void RemoveColumn(CGridColumnMe column) {
		CBandedGridColumnMe bandedGridColumn = (CBandedGridColumnMe) ((column instanceof CBandedGridColumnMe) ? column
				: null);
		if (bandedGridColumn != null) {
			this.m_columns.remove(column);
			RemoveControl(column);
		}
	}

	public void ResetHeaderLayout() {
		int posX = 0;
		int posY = 0;
		int offset = 0;
		CHScrollBarMe hScrollBar = GetHScrollBar();
		if ((hScrollBar != null) && (hScrollBar.IsVisible())) {
			offset = -hScrollBar.GetPos();
		}
		int height = IsHeaderVisible() ? GetHeaderHeight() : 0;
		int hOffset = GetHorizontalOffset();
		int vOffset = GetVerticalOffset();
		int bandsSize = this.m_bands.size();
		for (int i = 0; i < bandsSize; i++) {
			CGridBandMe gridBand = (CGridBandMe) this.m_bands.get(i);
			if (gridBand.IsVisible()) {
				int newHeight = height < gridBand.GetHeight() ? height : gridBand.GetHeight();
				RECT newRect = new RECT(posX + hOffset, posY + vOffset, posX + hOffset + gridBand.GetWidth(),
						posY + newHeight + vOffset);
				boolean isFrozen = false;
				ArrayList<CBandedGridColumnMe> allChildColumns = gridBand.GetAllChildColumns();
				int allChildColumnsSize = allChildColumns.size();
				for (int idx = 0; idx < allChildColumnsSize; idx++) {
					if (((CBandedGridColumnMe) allChildColumns.get(idx)).IsFrozen()) {
						isFrozen = true;
						break;
					}
				}
				if (!isFrozen) {
					newRect.left += offset;
					newRect.right += offset;
				}
				gridBand.SetBounds(newRect);
				gridBand.ResetHeaderLayout();
				posX += gridBand.GetWidth();
			}
		}
	}

	public void Update() {
		if (!this.m_lockUpdate) {
			int size = this.m_bands.size();
			int index = 0;

			for (int i = 0; i < size; i++) {
				CGridBandMe gridBand = (CGridBandMe) this.m_bands.get(i);
				ArrayList<CBandedGridColumnMe> allChildColumns = gridBand.GetAllChildColumns();
				int allChildColumnsSize = allChildColumns.size();
				for (int idx = 0; idx < allChildColumnsSize; idx++) {
					CBandedGridColumnMe bandedGridColumn = (CBandedGridColumnMe) allChildColumns.get(idx);
					bandedGridColumn.SetIndex(index);
					index++;
				}
			}
		}
		super.Update();
	}
}
