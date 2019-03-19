package com.melib.Window;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import com.melib.Base.COLOR;
import com.melib.Base.CPaintMe;
import com.melib.Base.CStrMe;
import com.melib.Base.CControlMe;
import com.melib.Base.EVENTID;
import com.melib.Base.FONT;
import com.melib.Base.INativeBaseMe;
import com.melib.Base.MouseButtonsA;
import com.melib.Base.POINT;
import com.melib.Base.RECT;
import com.melib.Base.RefObject;
import com.melib.Base.SIZE;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public class CWindowMe extends CControlMe {
	public CWindowMe() {
		SetAllowDrag(true);
		SetIsWindow(true);
		SetVisible(false);
	}

	private int m_resizePoint = -1;

	private POINT m_startMousePoint = new POINT();

	private RECT m_startRect = new RECT();

	protected int m_borderWidth = 2;

	public int GetBorderWidth() {
		return this.m_borderWidth;
	}

	public void SetBorderWidth(int value) {
		this.m_borderWidth = value;
	}

	protected boolean m_canResize = false;

	public boolean CanResize() {
		return this.m_canResize;
	}

	public void SetCanResize(boolean value) {
		this.m_canResize = value;
	}

	protected int m_captionHeight = 20;

	public int GetCaptionHeight() {
		return this.m_captionHeight;
	}

	public void SetCaptionHeight(int value) {
		this.m_captionHeight = value;
	}

	public RECT GetClientSize() {
		int width = GetWidth();
		int twidth = GetHeight() - this.m_captionHeight;
		if (twidth < 0) {
			twidth = 0;
		}
		return new RECT(0, this.m_captionHeight, width, twidth);
	}

	protected CWindowFrameMe m_frame = null;
	protected boolean m_isDialog;

	public CWindowFrameMe GetFrame() {
		return this.m_frame;
	}

	public void SetFrame(CWindowFrameMe value) {
		this.m_frame = value;
	}

	public boolean IsDialog() {
		return this.m_isDialog;
	}

	protected long m_shadowColor = COLOR.ARGB(25, 255, 255, 255);

	public long GetShadowColor() {
		return this.m_shadowColor;
	}

	public void SetShadowColor(long value) {
		this.m_shadowColor = value;
	}

	protected int m_shadowSize = 10;

	public int GetShadowSize() {
		return this.m_shadowSize;
	}

	public void SetShadowSize(int value) {
		this.m_shadowSize = value;
	}

	public void BringToFront() {
		super.BringToFront();
		if (this.m_frame != null) {
			this.m_frame.BringToFront();
		}
	}

	protected void CallWindowClosingEvents(int eventID, RefObject<Boolean> cancel) {
		if ((this.m_events != null) && (this.m_events.containsKey(Integer.valueOf(eventID)))) {
			ArrayList<Object> events = (ArrayList) this.m_events.get(Integer.valueOf(eventID));
			int count = events.size();
			for (int i = 0; i < count; i++) {
				WindowClosingEvent func = (WindowClosingEvent) ((events.get(i) instanceof WindowClosingEvent)
						? events.get(i)
						: null);
				if (func != null) {
					func.CallWindowClosingEvent(eventID, this, cancel);
				}
			}
		}
	}

	public void Close() {
		boolean cancel = false;
		RefObject<Boolean> cancel_ref = new RefObject(Boolean.valueOf(cancel));
		OnWindowClosing(cancel_ref);
		cancel = ((Boolean) cancel_ref.argvalue).booleanValue();
		if (!cancel) {
			if (this.m_frame != null) {
				this.m_frame.RemoveControl(this);
				GetNative().RemoveControl(this.m_frame);
				this.m_frame.Dispose();
				this.m_frame = null;
				SetParent(null);
			} else {
				GetNative().RemoveControl(this);
			}
			OnWindowClosed();
		}
	}

	public void Dispose() {
		if (!IsDisposed()) {
			if (this.m_frame != null) {
				this.m_frame.RemoveControl(this);
				GetNative().RemoveControl(this.m_frame);
				this.m_frame.Dispose();
				this.m_frame = null;
			}
		}
		super.Dispose();
	}

	public String GetControlType() {
		return "Window";
	}

	public RECT GetDynamicPaintRect() {
		SIZE oldSize = this.m_oldSize;
		if ((oldSize.cx == 0) && (oldSize.cy == 0)) {
			oldSize = GetSize();
		}
		RECT rect = new RECT(this.m_oldLocation.x, this.m_oldLocation.y, this.m_oldLocation.x + oldSize.cx,
				this.m_oldLocation.y + oldSize.cy);
		RECT rect2 = new RECT(this.m_location.x, this.m_location.y, this.m_location.x + GetWidth(),
				this.m_location.y + GetHeight());
		RECT rc = new RECT(Math.min(rect.left, rect2.left) - this.m_shadowSize - 10,
				Math.min(rect.top, rect2.top) - this.m_shadowSize - 10,
				Math.max(rect.right, rect2.right) + this.m_shadowSize + 10,
				Math.max(rect.bottom, rect2.bottom) + this.m_shadowSize + 10);

		return rc;
	}

	public void GetProperty(String name, RefObject<String> value, RefObject<String> type) {
		if (name.equals("borderwidth")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertIntToStr(GetBorderWidth());
		} else if (name.equals("canresize")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(CanResize());
		} else if (name.equals("captionheight")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertIntToStr(GetCaptionHeight());
		} else if (name.equals("shadowcolor")) {
			type.argvalue = "color";
			value.argvalue = CStrMe.ConvertColorToStr(GetShadowColor());
		} else if (name.equals("shadowsize")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertIntToStr(GetShadowSize());
		} else {
			super.GetProperty(name, value, type);
		}
	}

	public ArrayList<String> GetPropertyNames() {
		ArrayList<String> propertyNames = super.GetPropertyNames();
		propertyNames.addAll(Arrays
				.asList(new String[] { "BorderWidth", "CanResize", "CaptionHeight", "ShadowColor", "ShadowSize" }));
		return propertyNames;
	}

	protected RECT[] GetResizePoints() {
		int width = GetWidth();
		int height = GetHeight();
		RECT[] vec = new RECT[8];
		vec[0] = new RECT(0, 0, this.m_borderWidth * 2, this.m_borderWidth * 2);
		vec[1] = new RECT(0, height - this.m_borderWidth * 2, this.m_borderWidth * 2, height);
		vec[2] = new RECT(width - this.m_borderWidth * 2, 0, width, this.m_borderWidth * 2);
		vec[3] = new RECT(width - this.m_borderWidth * 2, height - this.m_borderWidth * 2, width, height);
		vec[4] = new RECT(0, 0, this.m_borderWidth, height);
		vec[5] = new RECT(0, 0, width, this.m_borderWidth);
		vec[6] = new RECT(width - this.m_borderWidth, 0, width, height);
		vec[7] = new RECT(0, height - this.m_borderWidth, width, height);
		return vec;
	}

	protected int GetResizeState() {
		POINT mousePoint = GetMousePoint();
		RECT[] resizePoints = GetResizePoints();
		int length = resizePoints.length;
		for (int i = 0; i < length; i++) {
			RECT rect = resizePoints[i];
			if ((mousePoint.x >= rect.left) && (mousePoint.x <= rect.right) && (mousePoint.y >= rect.top)
					&& (mousePoint.y <= rect.bottom)) {
				return i;
			}
		}
		return -1;
	}

	public boolean OnDragBegin() {
		POINT mousePoint = GetMousePoint();
		int width = GetWidth();
		int height = GetHeight();
		if (mousePoint.y > this.m_captionHeight) {
			return false;
		}
		if (this.m_resizePoint != -1) {
			return false;
		}
		return super.OnDragBegin();
	}

	public void OnDragReady(RefObject<POINT> startOffset) {
		((POINT) startOffset.argvalue).x = 0;
		((POINT) startOffset.argvalue).y = 0;
	}

	public void OnMouseDown(POINT mp, MouseButtonsA button, int clicks, int delta) {
		super.OnMouseDown(mp.Clone(), button, clicks, delta);

		if ((button == MouseButtonsA.Left) && (clicks == 1)) {
			if (this.m_canResize) {
				this.m_resizePoint = GetResizeState();
				this.m_startMousePoint = GetNative().GetMousePoint();
				this.m_startRect = GetBounds();
			}
		}
		Invalidate();
	}

	public void OnMouseMove(POINT mp, MouseButtonsA button, int clicks, int delta) {
		super.OnMouseMove(mp.Clone(), button, clicks, delta);
		if (this.m_canResize) {
			POINT mousePoint = GetNative().GetMousePoint();
			if (this.m_resizePoint != -1) {
				int left = this.m_startRect.left;
				int top = this.m_startRect.top;
				int right = this.m_startRect.right;
				int bottom = this.m_startRect.bottom;
				switch (this.m_resizePoint) {
				case 0:
					left = left + mousePoint.x - this.m_startMousePoint.x;
					top = top + mousePoint.y - this.m_startMousePoint.y;
					break;
				case 1:
					left = left + mousePoint.x - this.m_startMousePoint.x;
					bottom = bottom + mousePoint.y - this.m_startMousePoint.y;
					break;
				case 2:
					right = right + mousePoint.x - this.m_startMousePoint.x;
					top = top + mousePoint.y - this.m_startMousePoint.y;
					break;
				case 3:
					right = right + mousePoint.x - this.m_startMousePoint.x;
					bottom = bottom + mousePoint.y - this.m_startMousePoint.y;
					break;
				case 4:
					left = left + mousePoint.x - this.m_startMousePoint.x;
					break;
				case 5:
					top = top + mousePoint.y - this.m_startMousePoint.y;
					break;
				case 6:
					right = right + mousePoint.x - this.m_startMousePoint.x;
					break;
				case 7:
					bottom = bottom + mousePoint.y - this.m_startMousePoint.y;
				}

				RECT rect = new RECT(left, top, right, bottom);
				SetBounds(rect);
				GetNative().Invalidate();
			}
		}
	}

	public void OnMouseUp(POINT mp, MouseButtonsA button, int clicks, int delta) {
		super.OnMouseUp(mp.Clone(), button, clicks, delta);
		this.m_resizePoint = -1;
		Invalidate();
	}

	public void OnPaintForeground(CPaintMe paint, RECT clipRect) {
		String text = GetText();
		if ((text != null) && (text.length() > 0)) {
			int width = GetWidth();
			FONT font = GetFont();
			SIZE size = paint.TextSize(text, font);
			POINT point = new POINT();
			point.x = 5;
			point.y = ((this.m_captionHeight - size.cy) / 2);
			RECT rect = new RECT(point.x, point.y, point.x + size.cx, point.y + size.cy);
			paint.DrawText(text, GetPaintingForeColor(), font, rect);
		}
	}

	public void OnVisibleChanged() {
		super.OnVisibleChanged();
		INativeBaseMe nativeBase = GetNative();
		if (nativeBase != null) {
			if (IsVisible()) {
				if (this.m_frame == null) {
					this.m_frame = new CWindowFrameMe();
				}
				nativeBase.RemoveControl(this);
				nativeBase.AddControl(this.m_frame);
				this.m_frame.SetSize(nativeBase.GetDisplaySize());
				if (!this.m_frame.ContainsControl(this)) {
					this.m_frame.AddControl(this);
				}

			} else if (this.m_frame != null) {
				this.m_frame.RemoveControl(this);
				nativeBase.RemoveControl(this.m_frame);
			}
		}
	}

	public void OnWindowClosing(RefObject<Boolean> cancel) {
		CallWindowClosingEvents(EVENTID.WINDOWCLOSING, cancel);
	}

	public void OnWindowClosed() {
		CallEvents(EVENTID.WINDOWCLOSED);
	}

	public void SendToBack() {
		super.SendToBack();
		if (this.m_frame != null) {
			this.m_frame.SendToBack();
		}
	}

	public void SetProperty(String name, String value) {
		if (name.equals("borderwidth")) {
			SetBorderWidth(CStrMe.ConvertStrToInt(value));
		} else if (name.equals("canresize")) {
			SetCanResize(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("captionheight")) {
			SetCaptionHeight(CStrMe.ConvertStrToInt(value));
		} else if (name.equals("shadowcolor")) {
			SetShadowColor(CStrMe.ConvertStrToColor(value));
		} else if (name.equals("shadowsize")) {
			SetShadowSize(CStrMe.ConvertStrToInt(value));
		} else {
			super.SetProperty(name, value);
		}
	}

	public void ShowDialog() {
		this.m_isDialog = true;
		Show();
	}
}
