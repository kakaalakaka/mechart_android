package com.melib.Layout;

import java.util.ArrayList;
import java.util.Arrays;
import com.melib.Base.CStrMe;
import com.melib.Base.CControlMe;
import com.melib.Base.ControlEvent;
import com.melib.Base.CControlHostMe;
import com.melib.Base.EVENTID;
import com.melib.Base.INativeBaseMe;
import com.melib.Base.MouseButtonsA;
import com.melib.Base.POINT;
import com.melib.Base.RefObject;
import com.melib.Base.SIZE;
import com.melib.Grid.CGridColumnMe;
import com.melib.ScrollBar.CHScrollBarMe;
import com.melib.ScrollBar.CScrollBarMe;
import com.melib.ScrollBar.CVScrollBarMe;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public class CDivMe extends CControlMe implements ControlEvent {
	private boolean m_isDragScrolling;
	private POINT m_startMovePoint;
	private int m_startMovePosX;
	private int m_startMovePosY;
	private long m_startMoveTime;
	private boolean m_isDragScrolling2;
	protected boolean m_allowDragScroll;

	public CDivMe() {
		SIZE size = new SIZE(200, 200);
		SetSize(size);
	}

	public boolean AllowDragScroll() {
		return this.m_allowDragScroll;
	}

	public void SetAllowDragScroll(boolean value) {
		this.m_allowDragScroll = value;
	}

	protected CHScrollBarMe m_hScrollBar = null;

	public CHScrollBarMe GetHScrollBar() {
		if ((GetNative() != null) && (this.m_showHScrollBar)) {
			if (this.m_hScrollBar == null) {
				CControlHostMe host = GetNative().GetHost();
				CControlMe control = host.CreateInternalControl(this, "hscrollbar");
				this.m_hScrollBar = ((CHScrollBarMe) ((control instanceof CHScrollBarMe) ? control : null));
				AddControl(this.m_hScrollBar);
			}
			return this.m_hScrollBar;
		}
		return null;
	}

	protected boolean m_showHScrollBar = false;

	public boolean ShowHScrollBar() {
		return this.m_showHScrollBar;
	}

	public void SetShowHScrollBar(boolean value) {
		this.m_showHScrollBar = value;
	}

	public boolean IsDragScrolling() {
		return this.m_isDragScrolling;
	}

	protected boolean m_showVScrollBar = false;

	public boolean ShowVScrollBar() {
		return this.m_showVScrollBar;
	}

	public void SetShowVScrollBar(boolean value) {
		this.m_showVScrollBar = value;
	}

	protected CVScrollBarMe m_vScrollBar = null;

	public CVScrollBarMe GetVScrollBar() {
		if ((GetNative() != null) && (this.m_showVScrollBar)) {
			if (this.m_vScrollBar == null) {
				CControlHostMe host = GetNative().GetHost();
				CControlMe m_hScrollBar = host.CreateInternalControl(this, "vscrollbar");
				this.m_vScrollBar = ((CVScrollBarMe) ((m_hScrollBar instanceof CVScrollBarMe) ? m_hScrollBar : null));
				AddControl(this.m_vScrollBar);
			}
			return this.m_vScrollBar;
		}
		return null;
	}

	public void CallControlEvent(int eventID, Object sender) {
	}

	public void Dispose() {
		super.Dispose();
	}

	public int GetContentHeight() {
		CHScrollBarMe hScrollBar = GetHScrollBar();
		CVScrollBarMe vScrollBar = GetVScrollBar();
		int height = 0;
		ArrayList<CControlMe> controls = GetControls();
		int size = controls.size();
		for (int i = 0; i < size; i++) {
			CControlMe control = (CControlMe) controls.get(i);
			if ((control.IsVisible()) && (control != hScrollBar) && (control != vScrollBar)) {
				int bottom = control.GetBottom();
				if (bottom > height) {
					height = bottom;
				}
			}
		}
		return height;
	}

	public int GetContentWidth() {
		CHScrollBarMe hScrollBar = GetHScrollBar();
		CVScrollBarMe vScrollBar = GetVScrollBar();
		int width = 0;
		ArrayList<CControlMe> controls = GetControls();
		int count = controls.size();
		for (int i = 0; i < count; i++) {
			CControlMe control = (CControlMe) controls.get(i);
			if ((control.IsVisible()) && (control != hScrollBar) && (control != vScrollBar)) {
				int m_vScrollBar = control.GetRight();
				if (m_vScrollBar > width) {
					width = m_vScrollBar;
				}
			}
		}
		return width;
	}

	public String GetControlType() {
		return "Div";
	}

	public POINT GetDisplayOffset() {
		POINT point = new POINT();
		if (IsVisible()) {
			point.x = ((this.m_hScrollBar != null) && (this.m_hScrollBar.IsVisible()) ? this.m_hScrollBar.GetPos() : 0);
			point.y = ((this.m_vScrollBar != null) && (this.m_vScrollBar.IsVisible()) ? this.m_vScrollBar.GetPos() : 0);
		}
		return point;
	}

	public void GetProperty(String name, RefObject<String> value, RefObject<String> type) {
		if (name.equals("allowdragscroll")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(AllowDragScroll());
		}
		if (name.equals("showhscrollbar")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(ShowHScrollBar());
		} else if (name.equals("showvscrollbar")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(ShowVScrollBar());
		} else {
			super.GetProperty(name, value, type);
		}
	}

	public ArrayList<String> GetPropertyNames() {
		ArrayList<String> propertyNames = super.GetPropertyNames();
		propertyNames.addAll(Arrays.asList(new String[] { "AllowDragScroll", "ShowHScrollBar", "ShowVScrollBar" }));
		return propertyNames;
	}

	public void LineDown() {
		if ((this.m_vScrollBar != null) && (this.m_vScrollBar.IsVisible())) {
			this.m_vScrollBar.LineAdd();
		}
	}

	public void LineLeft() {
		if ((this.m_hScrollBar != null) && (this.m_hScrollBar.IsVisible())) {
			this.m_hScrollBar.LineReduce();
		}
	}

	public void LineRight() {
		if ((this.m_hScrollBar != null) && (this.m_hScrollBar.IsVisible())) {
			this.m_hScrollBar.LineAdd();
		}
	}

	public void LineUp() {
		if ((this.m_vScrollBar != null) && (this.m_vScrollBar.IsVisible())) {
			this.m_vScrollBar.LineReduce();
		}
	}

	public void OnDragReady(RefObject<POINT> startOffset) {
		((POINT) startOffset.argvalue).x = 0;
		((POINT) startOffset.argvalue).y = 0;
	}

	public void OnDragScrollEnd() {
		this.m_isDragScrolling = false;
		if (this.m_isDragScrolling) {
			long time = System.nanoTime();
			POINT point = GetNative().GetMousePoint();
			if ((this.m_hScrollBar != null) && (this.m_hScrollBar.IsVisible())) {
				this.m_hScrollBar.OnAddSpeedScrollStart(this.m_startMoveTime, time, this.m_startMovePoint.x, point.x);
			}
			if ((this.m_vScrollBar != null) && (this.m_vScrollBar.IsVisible())) {
				this.m_vScrollBar.OnAddSpeedScrollStart(this.m_startMoveTime, time, this.m_startMovePoint.y, point.y);
			}
			this.m_isDragScrolling = false;
			Invalidate();
		}
	}

	public void OnDragScrolling() {
		int width = GetWidth();
		int height = GetHeight();
		if ((this.m_allowDragScroll) && (this.m_isDragScrolling)) {
			if (!OnDragScrollPermit()) {
				this.m_isDragScrolling = false;
				return;
			}
			boolean paint = false;
			POINT mousePoint = GetNative().GetMousePoint();
			if ((this.m_hScrollBar != null) && (this.m_hScrollBar.IsVisible())) {
				if (Math.abs(this.m_startMovePoint.x - mousePoint.x) > width / 10) {
					this.m_isDragScrolling2 = true;
				}
				int newPos = this.m_startMovePosX + this.m_startMovePoint.x - mousePoint.x;
				if (newPos != this.m_hScrollBar.GetPos()) {
					this.m_hScrollBar.SetPos(this.m_startMovePosX + this.m_startMovePoint.x - mousePoint.x);
					this.m_hScrollBar.Update();
					paint = true;
				}
			}
			if ((this.m_vScrollBar != null) && (this.m_vScrollBar.IsVisible())) {
				if (Math.abs(this.m_startMovePoint.y - mousePoint.y) > height / 10) {
					this.m_isDragScrolling2 = true;
				}
				int newPos = this.m_startMovePosY + this.m_startMovePoint.y - mousePoint.y;
				if (newPos != this.m_vScrollBar.GetPos()) {
					this.m_vScrollBar.SetPos(this.m_startMovePosY + this.m_startMovePoint.y - mousePoint.y);
					this.m_vScrollBar.Update();
					paint = true;
				}
			}
			if (paint) {
				this.m_isDragScrolling = true;
				Invalidate();
			}
		}
	}

	public boolean OnDragScrollPermit() {
		CControlMe focusedControl = GetNative().GetFocusedControl();
		if (focusedControl != null) {
			if (focusedControl.IsDragging()) {
				return false;
			}
			CGridColumnMe gridColumn = (CGridColumnMe) ((focusedControl instanceof CGridColumnMe) ? focusedControl
					: null);
			if (gridColumn != null) {
				return false;
			}
			if (focusedControl.GetParent() != null) {
				CScrollBarMe scrollBar = (CScrollBarMe) ((focusedControl.GetParent() instanceof CScrollBarMe)
						? focusedControl.GetParent()
						: null);
				if (scrollBar != null) {
					return false;
				}
			}
		}
		return true;
	}

	public void OnDragScrollStart() {
		this.m_isDragScrolling = false;
		this.m_isDragScrolling2 = false;
		CControlMe focusedControl = GetNative().GetFocusedControl();
		if ((this.m_hScrollBar != null) && (this.m_hScrollBar.IsVisible())) {
			if ((focusedControl == this.m_hScrollBar.GetAddButton())
					|| (focusedControl == this.m_hScrollBar.GetReduceButton())
					|| (focusedControl == this.m_hScrollBar.GetBackButton())
					|| (focusedControl == this.m_hScrollBar.GetScrollButton())) {

				this.m_hScrollBar.SetAddSpeed(0);
				return;
			}
		}
		if ((this.m_vScrollBar != null) && (this.m_vScrollBar.IsVisible())) {
			if ((focusedControl == this.m_vScrollBar.GetAddButton())
					|| (focusedControl == this.m_vScrollBar.GetReduceButton())
					|| (focusedControl == this.m_vScrollBar.GetBackButton())
					|| (focusedControl == this.m_vScrollBar.GetScrollButton())) {

				this.m_vScrollBar.SetAddSpeed(0);
				return;
			}
		}
		if (this.m_allowDragScroll) {
			if ((this.m_hScrollBar != null) && (this.m_hScrollBar.IsVisible())) {
				this.m_startMovePosX = this.m_hScrollBar.GetPos();
				this.m_hScrollBar.SetAddSpeed(0);
				this.m_isDragScrolling = true;
			}
			if ((this.m_vScrollBar != null) && (this.m_vScrollBar.IsVisible())) {
				this.m_startMovePosY = this.m_vScrollBar.GetPos();
				this.m_vScrollBar.SetAddSpeed(0);
				this.m_isDragScrolling = true;
			}
			if (this.m_isDragScrolling) {
				this.m_startMovePoint = GetNative().GetMousePoint();
				this.m_startMoveTime = System.nanoTime();
			}
		}
	}

	public void OnMouseDown(POINT mp, MouseButtonsA button, int clicks, int delta) {
		super.OnMouseDown(mp, button, clicks, delta);
		if (!this.m_allowPreviewsEvent) {
			OnDragScrollStart();
		}
	}

	public void OnMouseMove(POINT mp, MouseButtonsA button, int clicks, int delta) {
		super.OnMouseMove(mp, button, clicks, delta);
		if (!this.m_allowPreviewsEvent) {
			OnDragScrolling();
		}
	}

	public void OnMouseUp(POINT mp, MouseButtonsA button, int clicks, int delta) {
		super.OnMouseUp(mp, button, clicks, delta);
		if (!this.m_allowPreviewsEvent) {
			OnDragScrollEnd();
		}
	}

	public boolean OnPreviewsMouseEvent(int eventID, POINT mp, MouseButtonsA button, int clicks, int delta) {
		if (CallPreviewsMouseEvents(EVENTID.PREVIEWSMOUSEEVENT, eventID, mp, button, clicks, delta)) {
			return true;
		}
		if (this.m_allowPreviewsEvent) {
			if (eventID == EVENTID.MOUSEDOWN) {
				OnDragScrollStart();
			} else if (eventID == EVENTID.MOUSEMOVE) {
				OnDragScrolling();
			} else if (eventID == EVENTID.MOUSEUP) {
				boolean state = this.m_isDragScrolling;
				OnDragScrollEnd();
				if ((state) && (!this.m_isDragScrolling2)) {
					return false;
				}
				return state;
			}
		}
		return false;
	}

	public void PageDown() {
		if ((this.m_vScrollBar != null) && (this.m_vScrollBar.IsVisible())) {
			this.m_vScrollBar.PageAdd();
		}
	}

	public void PageLeft() {
		if ((this.m_hScrollBar != null) && (this.m_hScrollBar.IsVisible())) {
			this.m_hScrollBar.PageReduce();
		}
	}

	public void PageRight() {
		if ((this.m_hScrollBar != null) && (this.m_hScrollBar.IsVisible())) {
			this.m_hScrollBar.PageAdd();
		}
	}

	public void PageUp() {
		if ((this.m_vScrollBar != null) && (this.m_vScrollBar.IsVisible())) {
			this.m_vScrollBar.PageReduce();
		}
	}

	public void SetProperty(String name, String value) {
		if (name.equals("allowdragscroll")) {
			SetAllowDragScroll(CStrMe.ConvertStrToBool(value));
		}
		if (name.equals("showhscrollbar")) {
			SetShowHScrollBar(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("showvscrollbar")) {
			SetShowVScrollBar(CStrMe.ConvertStrToBool(value));
		} else {
			super.SetProperty(name, value);
		}
	}

	public void Update() {
		super.Update();
		UpdateScrollBar();
	}

	public void UpdateScrollBar() {
		if (GetNative() != null) {
			CHScrollBarMe hScrollBar = GetHScrollBar();
			CVScrollBarMe vScrollBar = GetVScrollBar();
			if (IsVisible()) {
				int width = GetWidth();
				int height = GetHeight();
				int cy = hScrollBar != null ? hScrollBar.GetHeight() : 0;
				int cx = vScrollBar != null ? vScrollBar.GetWidth() : 0;
				int contentWidth = GetContentWidth();
				int contentHeight = GetContentHeight();
				if (hScrollBar != null) {
					hScrollBar.SetContentSize(contentWidth);
					hScrollBar.SetSize(new SIZE(width - cx, cy));
					hScrollBar.SetPageSize(width - cx);
					hScrollBar.SetLocation(new POINT(0, height - cy));
					if (contentWidth <= width) {
						hScrollBar.SetVisible(false);
					} else {
						hScrollBar.SetVisible(true);
					}
				}
				if (vScrollBar != null) {
					vScrollBar.SetContentSize(contentHeight);
					vScrollBar.SetSize(new SIZE(cx, height - cy));
					vScrollBar.SetPageSize(height - cy);
					vScrollBar.SetLocation(new POINT(width - cx, 0));
					int height2 = (hScrollBar != null) && (hScrollBar.IsVisible()) ? height - cy : height;
					if (contentHeight <= height2) {
						vScrollBar.SetVisible(false);
					} else {
						vScrollBar.SetVisible(true);
					}
				}
				if ((hScrollBar != null) && (vScrollBar != null)) {
					if ((hScrollBar.IsVisible()) && (!vScrollBar.IsVisible())) {
						hScrollBar.SetWidth(width);
						hScrollBar.SetPageSize(width);
					} else if ((!hScrollBar.IsVisible()) && (vScrollBar.IsVisible())) {
						vScrollBar.SetHeight(height);
						vScrollBar.SetPageSize(height);
					}
				}
				if ((hScrollBar != null) && (hScrollBar.IsVisible())) {
					hScrollBar.Update();
				}
				if ((vScrollBar != null) && (vScrollBar.IsVisible())) {
					vScrollBar.Update();
				}
			}
		}
	}
}
