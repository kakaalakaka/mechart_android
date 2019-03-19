package com.melib.Grid;

import com.melib.Base.CPaintMe;
import com.melib.Base.CControlMe;
import com.melib.Base.ControlMouseEvent;
import com.melib.Base.EVENTID;
import com.melib.Base.MouseButtonsA;
import com.melib.Base.POINT;
import com.melib.Base.RECT;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public class CGridControlCellMe extends CGridCellMe implements ControlMouseEvent {
	protected CControlMe m_control = null;

	public CControlMe GetControl() {
		return this.m_control;
	}

	public void SetControl(CControlMe value) {
		this.m_control = value;
	}

	public String GetPaintText() {
		return "";
	}

	public String GetString() {
		if (this.m_control != null) {
			return this.m_control.GetText();
		}

		return "";
	}

	public void CallControlMouseEvent(int eventID, Object sender, POINT mp, MouseButtonsA button, int clicks,
			int delta) {
		if (sender == this.m_control) {
			if (eventID == EVENTID.MOUSEDOWN) {
				OnControlMouseDown(mp.Clone(), button, clicks, delta);
			} else if (eventID == EVENTID.MOUSEMOVE) {
				OnControlMouseMove(mp.Clone(), button, clicks, delta);
			} else if (eventID == EVENTID.MOUSEUP) {
				OnControlMouseUp(mp.Clone(), button, clicks, delta);
			}
		}
	}

	public void Dispose() {
		if (!this.m_isDisposed) {
			if (this.m_control != null) {
				this.m_control.Dispose();
				this.m_control = null;
			}
			this.m_isDisposed = true;
		}
	}

	public void OnAdd() {
		CGridMe grid = GetGrid();
		if ((this.m_control != null) && (grid != null)) {
			grid.AddControl(this.m_control);
			this.m_control.RegisterEvent(this, EVENTID.MOUSEDOWN);
			this.m_control.RegisterEvent(this, EVENTID.MOUSEMOVE);
			this.m_control.RegisterEvent(this, EVENTID.MOUSEUP);
		}
	}

	public void OnControlMouseDown(POINT mp, MouseButtonsA button, int clicks, int delta) {
		CGridMe grid = GetGrid();
		if ((this.m_control != null) && (grid != null)) {
			grid.OnMouseDown(grid.PointToControl(this.m_control.PointToNative(mp)), button, clicks, delta);
		}
	}

	public void OnControlMouseMove(POINT mp, MouseButtonsA button, int clicks, int delta) {
		CGridMe grid = GetGrid();
		if ((this.m_control != null) && (grid != null)) {
			grid.OnMouseMove(grid.PointToControl(this.m_control.PointToNative(mp)), button, clicks, delta);
		}
	}

	public void OnControlMouseUp(POINT mp, MouseButtonsA button, int clicks, int delta) {
		CGridMe grid = GetGrid();
		if ((this.m_control != null) && (grid != null)) {
			grid.OnMouseUp(grid.PointToControl(this.m_control.PointToNative(mp)), button, clicks, delta);
		}
	}

	public void OnPaint(CPaintMe paint, RECT rect, RECT clipRect, boolean isAlternate) {
		super.OnPaint(paint, rect, clipRect, isAlternate);
		OnPaintControl(paint, rect, clipRect);
	}

	public void OnPaintControl(CPaintMe paint, RECT rect, RECT clipRect) {
		if (this.m_control != null) {
			RECT rect2 = new RECT(rect.left + 1, rect.top + 1, rect.right - 1, rect.bottom - 1);
			this.m_control.SetBounds(rect2);
			RECT newRect = clipRect.Clone();
			newRect.left -= rect.left;
			newRect.top -= rect.top;
			newRect.right -= rect.left;
			newRect.bottom -= rect.top;
			this.m_control.SetRegion(newRect);
		}
	}

	public void OnRemove() {
		CGridMe grid = GetGrid();
		if ((this.m_control != null) && (grid != null)) {
			this.m_control.UnRegisterEvent(this, EVENTID.MOUSEDOWN);
			this.m_control.UnRegisterEvent(this, EVENTID.MOUSEMOVE);
			this.m_control.UnRegisterEvent(this, EVENTID.MOUSEUP);
			grid.RemoveControl(this.m_control);
		}
	}

	public void SetString(String value) {
		if (this.m_control != null) {
			this.m_control.SetText(value);
		}
	}
}
