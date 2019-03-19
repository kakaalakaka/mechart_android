package com.melib.Grid;

import java.util.ArrayList;
import com.melib.Base.EVENTID;
import com.melib.Base.INativeBaseMe;
import com.melib.Base.MouseButtonsA;
import com.melib.Base.POINT;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class CBandedGridColumnMe extends CGridColumnMe {
	protected CGridBandMe m_band = null;

	public CGridBandMe GetBand() {
		return this.m_band;
	}

	public void SetBand(CGridBandMe value) {
		this.m_band = value;
	}

	public String GetControlType() {
		return "BandedGridColumn";
	}

	public boolean OnDragBegin() {
		return this.m_resizeState == 0;
	}

	public void OnMouseDown(POINT mp, MouseButtonsA button, int clicks, int delta) {
		CallMouseEvents(EVENTID.MOUSEDOWN, mp, button, clicks, delta);
		if (this.m_band != null) {
			if ((button == MouseButtonsA.Left) && (clicks == 1)) {
				if (AllowResize()) {
					ArrayList<CBandedGridColumnMe> bandColumns = this.m_band.GetColumns();
					int bandColumnsSize = bandColumns.size();
					int index = -1;
					for (int i = 0; i < bandColumnsSize; i++) {
						if (this == bandColumns.get(i)) {
							index = i;
							break;
						}
					}
					if ((index > 0) && (mp.x < 5)) {
						this.m_resizeState = 1;
						this.m_beginWidth = ((CBandedGridColumnMe) bandColumns.get(index - 1)).GetWidth();
					} else if ((index < bandColumnsSize - 1) && (mp.x > GetWidth() - 5)) {
						this.m_resizeState = 2;
						this.m_beginWidth = GetWidth();
					}
					this.m_mouseDownPoint = GetNative().GetMousePoint();
				}
			}
		}
		Invalidate();
	}

	public void OnMouseMove(POINT mp, MouseButtonsA button, int clicks, int delta) {
		CallMouseEvents(EVENTID.MOUSEMOVE, mp, button, clicks, delta);
		CGridMe grid = GetGrid();
		if ((this.m_band != null) && (grid != null)) {
			if (AllowResize()) {
				ArrayList<CBandedGridColumnMe> bandColumns = this.m_band.GetColumns();
				int bandColumnsSize = bandColumns.size();
				int index = -1;
				int width = GetWidth();
				for (int i = 0; i < bandColumnsSize; i++) {
					if (this == bandColumns.get(i)) {
						index = i;
						break;
					}
				}
				if (this.m_resizeState > 0) {
					POINT mousePoint = GetNative().GetMousePoint();
					int newWidth = this.m_beginWidth + (mousePoint.x - this.m_mouseDownPoint.x);
					if (newWidth > 0) {
						if (this.m_resizeState == 1) {
							CBandedGridColumnMe bandedGridColumn = (CBandedGridColumnMe) bandColumns.get(index - 1);
							int bandedGridColumnWidth = bandedGridColumn.GetWidth();
							bandedGridColumn.SetWidth(newWidth);
							width += bandedGridColumnWidth - newWidth;
							SetWidth(width);
						} else if (this.m_resizeState == 2) {
							SetWidth(newWidth);
							CBandedGridColumnMe bandedGridColumn = (CBandedGridColumnMe) bandColumns.get(index + 1);
							int bandedGridColumnWidth = bandedGridColumn.GetWidth();
							bandedGridColumnWidth += width - newWidth;
							bandedGridColumn.SetWidth(bandedGridColumnWidth);
						}
					}
					grid.Invalidate();
					return;
				}
			}
		}
	}
}
