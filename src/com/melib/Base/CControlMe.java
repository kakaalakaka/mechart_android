package com.melib.Base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class CControlMe implements CPropertyMe {
	protected void finalize() throws Throwable {
		Dispose();
	}

	protected ArrayList<CControlMe> m_controls = new ArrayList();
	protected HashMap<Integer, ArrayList<Object>> m_events;
	protected POINT m_oldLocation = new POINT();
	protected SIZE m_oldSize = new SIZE();
	protected POINTF m_percentLocation = null;
	protected SIZEF m_percentSize = null;
	private static int m_timerID = 10000;
	protected HorizontalAlignA m_align = HorizontalAlignA.Left;

	public HorizontalAlignA GetAlign() {
		return this.m_align;
	}

	public void SetAlign(HorizontalAlignA align) {
		this.m_align = align;
	}

	protected boolean m_allowDrag = false;

	public boolean AllowDrag() {
		return this.m_allowDrag;
	}

	public void SetAllowDrag(boolean value) {
		this.m_allowDrag = value;
	}

	protected boolean m_allowPreviewsEvent = false;

	public boolean AllowPreviewsEvent() {
		return this.m_allowPreviewsEvent;
	}

	public void SetAllowPreviewsEvent(boolean value) {
		this.m_allowPreviewsEvent = value;
	}

	protected ANCHOR m_anchor = new ANCHOR(true, true, false, false);

	public ANCHOR GetAnchor() {
		return this.m_anchor;
	}

	public void SetAnchor(ANCHOR value) {
		this.m_anchor = value;
	}

	protected boolean m_autoEllipsis = false;

	public boolean AutoEllipsis() {
		return this.m_autoEllipsis;
	}

	public void SetAutoEllipsis(boolean value) {
		this.m_autoEllipsis = value;
	}

	protected boolean m_autoSize = false;

	public boolean AutoSize() {
		return this.m_autoSize;
	}

	public void SetAutoSize(boolean value) {
		if (this.m_autoSize != value) {
			this.m_autoSize = value;
			OnAutoSizeChanged();
		}
	}

	protected long m_backColor = COLOR.CONTROL;
	protected String m_backImage;

	public long GetBackColor() {
		return this.m_backColor;
	}

	public void SetBackColor(long value) {
		if (this.m_backColor != value) {
			this.m_backColor = value;
			OnBackColorChanged();
		}
	}

	public String GetBackImage() {
		return this.m_backImage;
	}

	public void SetBackImage(String value) {
		this.m_backImage = value;
		OnBackImageChanged();
	}

	protected long m_borderColor = COLOR.CONTROLBORDER;

	public long GetBorderColor() {
		return this.m_borderColor;
	}

	public void SetBorderColor(long value) {
		this.m_borderColor = value;
	}

	public int GetBottom() {
		return GetTop() + GetHeight();
	}

	public RECT GetBounds() {
		return new RECT(GetLeft(), GetTop(), GetRight(), GetBottom());
	}

	public void SetBounds(RECT value) {
		SetLocation(new POINT(value.left, value.top));
		int cx = value.right - value.left;
		int cy = value.bottom - value.top;
		SetSize(new SIZE(cx, cy));
	}

	protected boolean m_canFocus = true;

	public boolean CanFocus() {
		return this.m_canFocus;
	}

	public void SetCanFocus(boolean value) {
		this.m_canFocus = value;
	}

	protected boolean m_canRaiseEvents = true;
	protected int m_cornerRadius;

	public boolean CanRaiseEvents() {
		return this.m_canRaiseEvents;
	}

	public void SetCanRaiseEvents(boolean value) {
		this.m_canRaiseEvents = value;
	}

	public boolean IsCapture() {
		if (this.m_native != null) {
			if ((this.m_native.GetHoveredControl() == this) || (this.m_native.GetPushedControl() == this)) {
				return true;
			}
		}
		return false;
	}

	public int GetCornerRadius() {
		return this.m_cornerRadius;
	}

	public void SetCornerRadius(int value) {
		this.m_cornerRadius = value;
	}

	protected boolean m_displayOffset = true;

	public boolean DisplayOffset() {
		return this.m_displayOffset;
	}

	public void SetDisplayOffset(boolean value) {
		this.m_displayOffset = value;
	}

	public RECT GetDisplayRect() {
		if (this.m_useRegion) {
			return this.m_region.Clone();
		}

		return new RECT(0, 0, GetWidth(), GetHeight());
	}

	protected DockStyleA m_dock = DockStyleA.None;

	public DockStyleA GetDock() {
		return this.m_dock;
	}

	public void SetDock(DockStyleA value) {
		if (this.m_dock != value) {
			this.m_dock = value;
			OnDockChanged();
		}
	}

	protected boolean m_enabled = true;

	public boolean IsEnabled() {
		return this.m_enabled;
	}

	public void SetEnabled(boolean value) {
		if (this.m_enabled != value) {
			this.m_enabled = value;
			OnEnableChanged();
		}
	}

	public boolean IsFocused() {
		if (this.m_native != null) {
			if (this.m_native.GetFocusedControl() == this) {
				return true;
			}
		}
		return false;
	}

	public void SetFocused(boolean value) {
		if (this.m_native != null) {
			if (value) {
				this.m_native.SetFocusedControl(this);

			} else if (this.m_native.GetFocusedControl() == this) {
				this.m_native.SetFocusedControl(null);
			}
		}
	}

	protected FONT m_font = new FONT();

	public FONT GetFont() {
		return this.m_font;
	}

	public void SetFont(FONT value) {
		this.m_font = value;
		OnFontChanged();
	}

	protected long m_foreColor = COLOR.CONTROLTEXT;
	protected boolean m_hasPopupMenu;
	protected boolean m_isDisposed;

	public long GetForeColor() {
		return this.m_foreColor;
	}

	public void SetForeColor(long value) {
		if (this.m_foreColor != value) {
			this.m_foreColor = value;
			OnForeColorChanged();
		}
	}

	protected boolean m_isDragging;
	protected boolean m_isWindow;

	public boolean GetHasPopupMenu() {
		return this.m_hasPopupMenu;
	}

	public void SetHasPopupMenu(boolean hasPopupMenu) {
		this.m_hasPopupMenu = hasPopupMenu;
	}

	public int GetHeight() {
		if ((this.m_percentSize != null) && (this.m_percentSize.cy != -1.0F)) {
			SIZE parentSize = this.m_parent != null ? this.m_parent.GetSize() : this.m_native.GetDisplaySize();
			return (int) (parentSize.cy * this.m_percentSize.cy);
		}

		return this.m_size.cy;
	}

	public void SetHeight(int value) {
		if ((this.m_percentSize != null) && (this.m_percentSize.cy != -1.0F)) {
			return;
		}

		SetSize(new SIZE(this.m_size.cx, value));
	}

	public boolean IsDisposed() {
		return this.m_isDisposed;
	}

	public boolean IsDragging() {
		return this.m_isDragging;
	}

	public boolean IsWindow() {
		return this.m_isWindow;
	}

	public void SetIsWindow(boolean value) {
		this.m_isWindow = value;
	}

	public int GetLeft() {
		if ((this.m_percentLocation != null) && (this.m_percentLocation.x != -1.0F)) {
			SIZE parentSize = this.m_parent != null ? this.m_parent.GetSize() : this.m_native.GetDisplaySize();
			return (int) (parentSize.cx * this.m_percentLocation.x);
		}

		return this.m_location.x;
	}

	public void SetLeft(int value) {
		if ((this.m_percentLocation != null) && (this.m_percentLocation.x != -1.0F)) {
			return;
		}

		SetLocation(new POINT(value, this.m_location.y));
	}

	protected POINT m_location = new POINT(0, 0);

	public POINT GetLocation() {
		if (this.m_percentLocation != null) {
			POINT location = new POINT(GetLeft(), GetTop());
			return location;
		}

		return this.m_location.Clone();
	}

	public void SetLocation(POINT value) {
		if ((this.m_location.x != value.x) || (this.m_location.y != value.y)) {
			if (this.m_percentLocation != null) {
				this.m_oldLocation = GetLocation().Clone();
				if (this.m_percentLocation.x == -1.0F) {
					this.m_location.x = value.x;
				}
				if (this.m_percentLocation.y == -1.0F) {

					this.m_location.y = value.y;
				}
			} else {
				this.m_oldLocation = this.m_location.Clone();
				this.m_location = value.Clone();
			}
			OnLocationChanged();
		}
	}

	protected PADDING m_margin = new PADDING(0);

	public PADDING GetMargin() {
		return this.m_margin.Clone();
	}

	public void SetMargin(PADDING value) {
		this.m_margin = value.Clone();
		OnMarginChanged();
	}

	protected SIZE m_maximumSize = new SIZE(100000, 100000);

	public SIZE GetMaximumSize() {
		return this.m_maximumSize.Clone();
	}

	public void SetMaximumSize(SIZE value) {
		this.m_maximumSize = value.Clone();
	}

	protected SIZE m_minimumSize = new SIZE(0, 0);

	public SIZE GetMinimumSize() {
		return this.m_minimumSize.Clone();
	}

	public void SetMinimumSize(SIZE value) {
		this.m_minimumSize = value.Clone();
	}

	public POINT GetMousePoint() {
		POINT mp = GetNative().GetMousePoint();
		return PointToControl(mp);
	}

	protected String m_name = "";

	public String GetName() {
		return this.m_name;
	}

	public void SetName(String value) {
		this.m_name = value;
	}

	protected INativeBaseMe m_native = null;

	public INativeBaseMe GetNative() {
		return this.m_native;
	}

	public void SetNative(INativeBaseMe value) {
		this.m_native = value;
		int controlsSize = this.m_controls.size();
		for (int i = 0; i < controlsSize; i++) {
			((CControlMe) this.m_controls.get(i)).SetNative(value);
		}
		OnLoad();
	}

	protected float m_opacity = 1.0F;

	public float GetOpacity() {
		return this.m_opacity;
	}

	public void SetOpacity(float value) {
		this.m_opacity = value;
	}

	protected PADDING m_padding = new PADDING(0);

	public PADDING GetPadding() {
		return this.m_padding.Clone();
	}

	public void SetPadding(PADDING value) {
		this.m_padding = value.Clone();
		OnPaddingChanged();
	}

	protected CControlMe m_parent = null;

	public CControlMe GetParent() {
		return this.m_parent;
	}

	public void SetParent(CControlMe value) {
		if (this.m_parent != value) {
			this.m_parent = value;
			OnParentChanged();
		}
	}

	protected RECT m_region = new RECT();
	protected String m_resourcePath;

	public RECT GetRegion() {
		return this.m_region.Clone();
	}

	public void SetRegion(RECT value) {
		this.m_useRegion = true;
		this.m_region = value.Clone();
		OnRegionChanged();
	}

	public String GetResourcePath() {
		return this.m_resourcePath;
	}

	public void SetResourcePath(String value) {
		this.m_resourcePath = value;
	}

	public int GetRight() {
		return GetLeft() + GetWidth();
	}

	protected SIZE m_size = new SIZE();

	public SIZE GetSize() {
		if (this.m_percentSize != null) {
			SIZE size = new SIZE(GetWidth(), GetHeight());
			return size;
		}

		return this.m_size.Clone();
	}

	public void SetSize(SIZE value) {
		SIZE newSize = value.Clone();
		if (newSize.cx > this.m_maximumSize.cx) {
			newSize.cx = this.m_maximumSize.cx;
		}
		if (newSize.cy > this.m_maximumSize.cy) {
			newSize.cy = this.m_maximumSize.cy;
		}
		if (newSize.cx < this.m_minimumSize.cx) {
			newSize.cx = this.m_minimumSize.cx;
		}
		if (newSize.cy < this.m_minimumSize.cy) {
			newSize.cy = this.m_minimumSize.cy;
		}
		if ((this.m_size.cx != newSize.cx) || (this.m_size.cy != newSize.cy)) {
			if (this.m_percentSize != null) {
				this.m_oldSize = GetSize();
				if (this.m_percentSize.cx == -1.0F) {

					this.m_size.cx = newSize.cx;
				}
				if (this.m_percentSize.cy == -1.0F) {

					this.m_size.cy = newSize.cy;
				}
			} else {
				this.m_oldSize = this.m_size.Clone();
				this.m_size = newSize;
			}
			OnSizeChanged();
			Update();
		}
	}

	protected Object m_tag = null;

	public Object GetTag() {
		return this.m_tag;
	}

	public void SetTag(Object value) {
		this.m_tag = value;
	}

	protected String m_text = "";

	public String GetText() {
		return this.m_text;
	}

	public void SetText(String value) {
		if (!this.m_text.equals(value)) {
			this.m_text = value;
			OnTextChanged();
		}
	}

	public int GetTop() {
		if ((this.m_percentLocation != null) && (this.m_percentLocation.y != -1.0F)) {
			SIZE parentSize = this.m_parent != null ? this.m_parent.GetSize() : this.m_native.GetDisplaySize();
			return (int) (parentSize.cy * this.m_percentLocation.y);
		}

		return this.m_location.y;
	}

	public void SetTop(int value) {
		if ((this.m_percentLocation != null) && (this.m_percentLocation.y != -1.0F)) {
			return;
		}

		SetLocation(new POINT(this.m_location.x, value));
	}

	protected boolean m_topMost = false;

	public boolean IsTopMost() {
		return this.m_topMost;
	}

	public void SetTopMost(boolean value) {
		this.m_topMost = value;
	}

	protected boolean m_useRegion = false;

	public boolean UseRegion() {
		return this.m_useRegion;
	}

	protected VerticalAlignA m_verticalAlign = VerticalAlignA.Top;

	public VerticalAlignA GetVerticalAlign() {
		return this.m_verticalAlign;
	}

	public void SetVerticalAlign(VerticalAlignA value) {
		this.m_verticalAlign = value;
	}

	protected boolean m_visible = true;

	public boolean IsVisible() {
		return this.m_visible;
	}

	public void SetVisible(boolean value) {
		if (this.m_visible != value) {
			this.m_visible = value;
			OnVisibleChanged();
		}
	}

	public int GetWidth() {
		if ((this.m_percentSize != null) && (this.m_percentSize.cx != -1.0F)) {
			SIZE parentSize = this.m_parent != null ? this.m_parent.GetSize() : this.m_native.GetDisplaySize();
			return (int) (parentSize.cx * this.m_percentSize.cx);
		}

		return this.m_size.cx;
	}

	public void SetWidth(int value) {
		if ((this.m_percentSize != null) && (this.m_percentSize.cx != -1.0F)) {
			return;
		}

		SetSize(new SIZE(value, this.m_size.cy));
	}

	public void AddControl(CControlMe control) {
		control.SetParent(this);
		control.SetNative(this.m_native);
		this.m_controls.add(control);
		control.OnAdd();
	}

	public void BeginInvoke(Object args) {
		if (this.m_native != null) {
			CControlHostMe host = this.m_native.GetHost();
			host.BeginInvoke(this, args);
		}
	}

	public void BringChildToFront(CControlMe childControl) {
		if ((this.m_controls != null) && (this.m_controls.size() > 0)) {
			this.m_controls.remove(childControl);
			this.m_controls.add(childControl);
		}
	}

	public void BringToFront() {
		if (this.m_native != null) {
			this.m_native.BringToFront(this);
		}
	}

	protected void CallEvents(int eventID) {
		if (this.m_canRaiseEvents) {
			if ((this.m_events != null) && (this.m_events.containsKey(Integer.valueOf(eventID)))) {
				ArrayList<Object> events = (ArrayList) this.m_events.get(Integer.valueOf(eventID));
				int eventSize = events.size();
				for (int i = 0; i < eventSize; i++) {
					ControlEvent func = (ControlEvent) ((events.get(i) instanceof ControlEvent) ? events.get(i) : null);
					if (func != null) {
						func.CallControlEvent(eventID, this);
					}
				}
			}
		}
	}

	protected void CallInvokeEvents(int eventID, Object args) {
		if (this.m_canRaiseEvents) {
			if ((this.m_events != null) && (this.m_events.containsKey(Integer.valueOf(eventID)))) {
				ArrayList<Object> events = (ArrayList) this.m_events.get(Integer.valueOf(eventID));
				int eventSize = events.size();
				for (int i = 0; i < eventSize; i++) {
					ControlInvokeEvent func = (ControlInvokeEvent) ((events.get(i) instanceof ControlInvokeEvent)
							? events.get(i)
							: null);
					if (func != null) {
						func.CallControlInvokeEvent(eventID, this, args);
					}
				}
			}
		}
	}

	protected void CallMouseEvents(int eventID, POINT mp, MouseButtonsA button, int clicks, int delta) {
		if (this.m_canRaiseEvents) {
			if ((this.m_events != null) && (this.m_events.containsKey(Integer.valueOf(eventID)))) {
				ArrayList<Object> events = (ArrayList) this.m_events.get(Integer.valueOf(eventID));
				int eventSize = events.size();
				for (int i = 0; i < eventSize; i++) {
					ControlMouseEvent func = (ControlMouseEvent) ((events.get(i) instanceof ControlMouseEvent)
							? events.get(i)
							: null);
					if (func != null) {
						func.CallControlMouseEvent(eventID, this, mp.Clone(), button, clicks, delta);
					}
				}
			}
		}
	}

	protected void CallPaintEvents(int eventID, CPaintMe paint, RECT clipRect) {
		if (this.m_canRaiseEvents) {
			if ((this.m_events != null) && (this.m_events.containsKey(Integer.valueOf(eventID)))) {
				ArrayList<Object> events = (ArrayList) this.m_events.get(Integer.valueOf(eventID));
				int eventSize = events.size();
				for (int i = 0; i < eventSize; i++) {
					ControlPaintEvent func = (ControlPaintEvent) ((events.get(i) instanceof ControlPaintEvent)
							? events.get(i)
							: null);
					if (func != null) {
						func.CallControlPaintEvent(eventID, this, paint, clipRect);
					}
				}
			}
		}
	}

	protected boolean CallPreviewsMouseEvents(int eventID, int tEventID, POINT mp, MouseButtonsA button, int clicks,
			int delta) {
		if (this.m_canRaiseEvents) {
			if ((this.m_events != null) && (this.m_events.containsKey(Integer.valueOf(eventID)))) {
				ArrayList<Object> events = (ArrayList) this.m_events.get(Integer.valueOf(eventID));
				int eventSize = events.size();
				for (int i = 0; i < eventSize; i++) {
					ControlPreviewsMouseEvent func = (ControlPreviewsMouseEvent) ((events
							.get(i) instanceof ControlMouseEvent) ? events.get(i) : null);
					if (func != null) {
						if (func.CallPreviewsControlMouseEvent(eventID, tEventID, this, mp.Clone(), button, clicks,
								delta)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	protected void CallTimerEvents(int eventID, int timerID) {
		if (this.m_canRaiseEvents) {
			if ((this.m_events != null) && (this.m_events.containsKey(Integer.valueOf(eventID)))) {
				ArrayList<Object> events = (ArrayList) this.m_events.get(Integer.valueOf(eventID));
				int eventSize = events.size();
				for (int i = 0; i < eventSize; i++) {
					ControlTimerEvent func = (ControlTimerEvent) ((events.get(i) instanceof ControlTimerEvent)
							? events.get(i)
							: null);
					if (func != null) {
						func.CallControlTimerEvent(eventID, this, timerID);
					}
				}
			}
		}
	}

	protected void CallTouchEvents(int eventID, ArrayList<CTouch> touches) {
		if (this.m_canRaiseEvents) {
			if ((this.m_events != null) && (this.m_events.containsKey(Integer.valueOf(eventID)))) {
				ArrayList<Object> events = (ArrayList) this.m_events.get(Integer.valueOf(eventID));
				int eventSize = events.size();
				for (int i = 0; i < eventSize; i++) {
					ControlTouchEvent func = (ControlTouchEvent) ((events.get(i) instanceof ControlTouchEvent)
							? events.get(i)
							: null);
					if (func != null) {
						func.CallControlTouchEvent(eventID, this, touches);
					}
				}
			}
		}
	}

	public void ClearControls() {
		ArrayList<CControlMe> controls = new ArrayList();
		for (CControlMe control : this.m_controls) {
			controls.add(control);
		}
		for (CControlMe control : controls) {
			control.OnRemove();
			control.Dispose();
		}
		this.m_controls.clear();
	}

	public boolean ContainsControl(CControlMe control) {
		for (CControlMe subControl : this.m_controls) {
			if (subControl == control) {
				return true;
			}
		}
		return false;
	}

	public boolean ContainsPoint(POINT point) {
		POINT cPoint = PointToControl(point);
		SIZE size = GetSize();
		if ((cPoint.x >= 0) && (cPoint.x <= size.cx) && (cPoint.y >= 0) && (cPoint.y <= size.cy)) {
			if (this.m_useRegion) {
				if ((cPoint.x >= this.m_region.left) && (cPoint.x <= this.m_region.right)
						&& (cPoint.y >= this.m_region.top) && (cPoint.y <= this.m_region.bottom)) {
					return true;
				}

			} else {
				return true;
			}
		}
		return false;
	}

	public void Dispose() {
		if (!this.m_isDisposed) {
			if (this.m_events != null) {
				this.m_events.clear();
			}
			ClearControls();
			this.m_isDisposed = true;
		}
	}

	public void Focus() {
		SetFocused(true);
	}

	public ArrayList<CControlMe> GetControls() {
		return this.m_controls;
	}

	public String GetControlType() {
		return "BaseControl";
	}

	public POINT GetDisplayOffset() {
		return new POINT(0, 0);
	}

	public static int GetNewTimerID() {
		return m_timerID++;
	}

	protected long GetPaintingBackColor() {
		if ((this.m_backColor != COLOR.EMPTY) && (COLOR.DISABLEDCONTROL != COLOR.EMPTY)) {
			if (!IsPaintEnabled(this)) {
				return COLOR.DISABLEDCONTROL;
			}
		}
		return this.m_backColor;
	}

	protected String GetPaintingBackImage() {
		return this.m_backImage;
	}

	protected long GetPaintingBorderColor() {
		return this.m_borderColor;
	}

	protected long GetPaintingForeColor() {
		if ((this.m_foreColor != COLOR.CONTROLTEXT) && (COLOR.DISABLEDCONTROLTEXT != COLOR.EMPTY)) {
			if (!IsPaintEnabled(this)) {
				return COLOR.DISABLEDCONTROLTEXT;
			}
		}
		return this.m_foreColor;
	}

	public CControlMe GetPopupMenuContext(CControlMe control) {
		if (this.m_hasPopupMenu) {
			return this;
		}

		if (this.m_parent != null) {
			return GetPopupMenuContext(this.m_parent);
		}

		return null;
	}

	public void GetProperty(String name, RefObject<String> value, RefObject<String> type) {
		int len = name.length();
		switch (len) {

		case 3:
			if (name.equals("top")) {
				type.argvalue = "float";
				if ((this.m_percentLocation != null) && (this.m_percentLocation.y != -1.0F)) {
					value.argvalue = ("%" + CStrMe.ConvertFloatToStr(100.0F * this.m_percentLocation.y));
				} else {
					value.argvalue = CStrMe.ConvertIntToStr(GetTop());
				}
			}

			break;
		case 4:
			if (name.equals("dock")) {
				type.argvalue = "enum:DockStyleA";
				value.argvalue = CStrMe.ConvertDockToStr(GetDock());
			} else if (name.equals("font")) {
				type.argvalue = "font";
				value.argvalue = CStrMe.ConvertFontToStr(GetFont());
			} else if (name.equals("left")) {
				type.argvalue = "float";
				if ((this.m_percentLocation != null) && (this.m_percentLocation.x != -1.0F)) {
					value.argvalue = ("%" + CStrMe.ConvertFloatToStr(100.0F * this.m_percentLocation.x));
				} else {
					value.argvalue = CStrMe.ConvertIntToStr(GetLeft());
				}
			} else if (name.equals("name")) {
				type.argvalue = "text";
				value.argvalue = GetName();
			} else if (name.equals("size")) {
				type.argvalue = "size";
				if (this.m_percentSize != null) {
					String pWidth = "";
					String pHeight = "";
					String pType = "";
					RefObject<String> refPWidth = new RefObject(pWidth);
					RefObject<String> refPHeight = new RefObject(pHeight);
					RefObject<String> refPType = new RefObject(pType);
					GetProperty("width", refPWidth, refPType);
					GetProperty("height", refPHeight, refPType);
					value.argvalue = ((String) refPWidth.argvalue + "," + (String) refPHeight.argvalue);
				} else {
					value.argvalue = CStrMe.ConvertSizeToStr(GetSize());
				}
			} else if (name.equals("text")) {
				type.argvalue = "text";
				value.argvalue = GetText();
			}

			break;
		case 5:
			if (name.equals("align")) {
				type.argvalue = "enum:HorizontalAlignA";
				value.argvalue = CStrMe.ConvertHorizontalAlignToStr(GetAlign());
			} else if (name.equals("width")) {
				type.argvalue = "float";
				if ((this.m_percentSize != null) && (this.m_percentSize.cx != -1.0F)) {
					value.argvalue = ("%" + CStrMe.ConvertFloatToStr(100.0F * this.m_percentSize.cx));
				} else {
					value.argvalue = CStrMe.ConvertIntToStr(GetWidth());
				}
			}

			break;
		case 6:
			if (name.equals("anchor")) {
				type.argvalue = "anchor";
				value.argvalue = CStrMe.ConvertAnchorToStr(GetAnchor());
			} else if (name.equals("bounds")) {
				type.argvalue = "rect";
				value.argvalue = CStrMe.ConvertRectToStr(GetBounds());
			} else if (name.equals("height")) {
				type.argvalue = "float";
				if ((this.m_percentSize != null) && (this.m_percentSize.cy != -1.0F)) {
					value.argvalue = ("%" + CStrMe.ConvertFloatToStr(100.0F * this.m_percentSize.cy));
				} else {
					value.argvalue = CStrMe.ConvertIntToStr(GetHeight());
				}
			} else if (name.equals("margin")) {
				type.argvalue = "margin";
				value.argvalue = CStrMe.ConvertPaddingToStr(GetMargin());
			} else if (name.equals("region")) {
				type.argvalue = "rect";
				value.argvalue = CStrMe.ConvertRectToStr(GetRegion());
			}

			break;
		case 7:
			if (name.equals("enabled")) {
				type.argvalue = "bool";
				value.argvalue = CStrMe.ConvertBoolToStr(IsEnabled());
			} else if (name.equals("focused")) {
				type.argvalue = "bool";
				value.argvalue = CStrMe.ConvertBoolToStr(IsFocused());
			} else if (name.equals("opacity")) {
				type.argvalue = "float";
				value.argvalue = CStrMe.ConvertFloatToStr(GetOpacity());
			} else if (name.equals("padding")) {
				type.argvalue = "padding";
				value.argvalue = CStrMe.ConvertPaddingToStr(GetPadding());
			} else if (name.equals("topmost")) {
				type.argvalue = "bool";
				value.argvalue = CStrMe.ConvertBoolToStr(IsTopMost());
			} else if (name.equals("visible")) {
				type.argvalue = "bool";
				value.argvalue = CStrMe.ConvertBoolToStr(IsVisible());
			}

			break;
		case 8:
			if (name.equals("autosize")) {
				type.argvalue = "bool";
				value.argvalue = CStrMe.ConvertBoolToStr(AutoSize());
			} else if (name.equals("canfocus")) {
				type.argvalue = "bool";
				value.argvalue = CStrMe.ConvertBoolToStr(CanFocus());
			} else if (name.equals("iswindow")) {
				type.argvalue = "bool";
				value.argvalue = CStrMe.ConvertBoolToStr(IsWindow());
			} else if (name.equals("location")) {
				type.argvalue = "point";
				if (this.m_percentLocation != null) {
					String pLeft = "";
					String pTop = "";
					String pType = "";
					RefObject<String> refPLeft = new RefObject(pLeft);
					RefObject<String> refPTop = new RefObject(pTop);
					RefObject<String> refPType = new RefObject(pType);
					GetProperty("left", refPLeft, refPType);
					GetProperty("top", refPTop, refPType);
					value.argvalue = ((String) refPLeft.argvalue + "," + (String) refPTop.argvalue);
				} else {
					value.argvalue = CStrMe.ConvertPointToStr(GetLocation());
				}
			}

			break;
		case 9:
			if (name.equals("allowdrag")) {
				type.argvalue = "bool";
				value.argvalue = CStrMe.ConvertBoolToStr(AllowDrag());
			} else if (name.equals("backcolor")) {
				type.argvalue = "color";
				value.argvalue = CStrMe.ConvertColorToStr(GetBackColor());
			} else if (name.equals("backimage")) {
				type.argvalue = "text";
				value.argvalue = GetBackImage();
			} else if (name.equals("forecolor")) {
				type.argvalue = "color";
				value.argvalue = CStrMe.ConvertColorToStr(GetForeColor());
			}

			break;
		default:
			if (name.equals("allowpreviewsevent")) {
				type.argvalue = "bool";
				value.argvalue = CStrMe.ConvertBoolToStr(AllowPreviewsEvent());
			} else if (name.equals("autoellipsis")) {
				type.argvalue = "bool";
				value.argvalue = CStrMe.ConvertBoolToStr(AutoEllipsis());
			} else if (name.equals("bordercolor")) {
				type.argvalue = "color";
				value.argvalue = CStrMe.ConvertColorToStr(GetBorderColor());

			} else if (name.equals("canraiseevents")) {
				type.argvalue = "bool";
				value.argvalue = CStrMe.ConvertBoolToStr(CanRaiseEvents());
			} else if (name.equals("cornerradius")) {
				type.argvalue = "int";
				value.argvalue = CStrMe.ConvertIntToStr(GetCornerRadius());
			} else if (name.equals("displayoffset")) {
				type.argvalue = "bool";
				value.argvalue = CStrMe.ConvertBoolToStr(DisplayOffset());
			} else if (name.equals("haspopupmenu")) {
				type.argvalue = "bool";
				value.argvalue = CStrMe.ConvertBoolToStr(GetHasPopupMenu());
			} else if (name.equals("maximumsize")) {
				type.argvalue = "size";
				value.argvalue = CStrMe.ConvertSizeToStr(GetMaximumSize());
			} else if (name.equals("minimumsize")) {
				type.argvalue = "size";
				value.argvalue = CStrMe.ConvertSizeToStr(GetMinimumSize());
			} else if (name.equals("resourcepath")) {
				type.argvalue = "text";
				value.argvalue = GetResourcePath();
			} else if (name.equals("vertical-align")) {
				type.argvalue = "enum:VerticalAlignA";
				value.argvalue = CStrMe.ConvertVerticalAlignToStr(GetVerticalAlign());
			}
			break;
		}
	}

	public ArrayList<String> GetPropertyNames() {
		ArrayList<String> propertyNames = new ArrayList();
		propertyNames.addAll(Arrays.asList(new String[] { "Align", "AllowDrag", "AllowPreviewsEvent", "Anchor",
				"AutoEllipsis", "AutoSize", "BackColor", "BackImage", "BorderColor", "Bounds", "CanFocus",
				"CanRaiseEvents", "CornerRadius", "Cursor", "DisplayOffset", "Dock", "Enabled", "Focused", "Font",
				"ForeColor", "HasPopupMenu", "Height", "IsWindow", "Left", "Location", "Margin", "MaximumSize",
				"MinimumSize", "Name", "Opacity", "Padding", "Region", "ResourcePath", "Size", "TabIndex", "TabStop",
				"Text", "Top", "TopMost", "Vertical-Align", "Visible", "Width" }));

		return propertyNames;
	}

	public boolean HasChildren() {
		return this.m_controls.size() > 0;
	}

	public void Hide() {
		SetVisible(false);
	}

	public void InsertControl(int index, CControlMe control) {
		this.m_controls.add(index, control);
	}

	public void Invalidate() {
		if (this.m_native != null) {
			this.m_native.Invalidate(this);
		}
	}

	public void Invoke(Object args) {
		if (this.m_native != null) {
			CControlHostMe host = this.m_native.GetHost();
			host.Invoke(this, args);
		}
	}

	public boolean IsPaintEnabled(CControlMe control) {
		if (control.IsEnabled()) {
			CControlMe parent = control.GetParent();
			if (parent != null) {
				return IsPaintEnabled(parent);
			}

			return true;
		}

		return false;
	}

	public boolean IsPaintVisible(CControlMe control) {
		if (control.IsEnabled()) {
			CControlMe parent = control.GetParent();
			if (parent != null) {
				return IsPaintVisible(parent);
			}

			return true;
		}

		return false;
	}

	public void OnAdd() {
		CallEvents(EVENTID.ADD);
	}

	public void OnAutoSizeChanged() {
		CallEvents(EVENTID.AUTOSIZECHANGED);
	}

	public void OnBackColorChanged() {
		CallEvents(EVENTID.BACKCOLORCHANGED);
	}

	public void OnBackImageChanged() {
		CallEvents(EVENTID.BACKIMAGECHANGED);
	}

	public void OnClick(POINT mp, MouseButtonsA button, int clicks, int delta) {
		CallMouseEvents(EVENTID.CLICK, mp.Clone(), button, clicks, delta);
	}

	public void OnCopy() {
		CallEvents(EVENTID.COPY);
	}

	public void OnCut() {
		CallEvents(EVENTID.CUT);
	}

	public void OnDockChanged() {
		CallEvents(EVENTID.DOCKCHANGED);
	}

	public void OnDoubleClick(POINT mp, MouseButtonsA button, int clicks, int delta) {
		CallMouseEvents(EVENTID.DOUBLECLICK, mp.Clone(), button, clicks, delta);
	}

	public boolean OnDragBegin() {
		CallEvents(EVENTID.DRAGBEGIN);
		return true;
	}

	public void OnDragEnd() {
		this.m_isDragging = false;
		CallEvents(EVENTID.DRAGEND);
	}

	public void OnDragging() {
		this.m_isDragging = true;
		CallEvents(EVENTID.DRAGGING);
	}

	public void OnDragReady(RefObject<POINT> startOffset) {
		((POINT) startOffset.argvalue).x = 5;
		((POINT) startOffset.argvalue).y = 5;
	}

	public void OnEnableChanged() {
		CallEvents(EVENTID.ENABLECHANGED);
	}

	public void OnFontChanged() {
		CallEvents(EVENTID.FONTCHANGED);
	}

	public void OnForeColorChanged() {
		CallEvents(EVENTID.FORECOLORCHANGED);
	}

	public void OnGotFocus() {
		CallEvents(EVENTID.GOTFOCUS);
	}

	public void OnInvoke(Object args) {
		CallInvokeEvents(EVENTID.INVOKE, args);
	}

	public void OnLoad() {
		CallEvents(EVENTID.LOAD);
	}

	public void OnLocationChanged() {
		CallEvents(EVENTID.LOCATIONCHANGED);
	}

	public void OnLostFocus() {
		CallEvents(EVENTID.LOSTFOCUS);
	}

	public void OnMarginChanged() {
		CallEvents(EVENTID.MARGINCHANGED);
	}

	public void OnMouseDown(POINT mp, MouseButtonsA button, int clicks, int delta) {
		CallMouseEvents(EVENTID.MOUSEDOWN, mp.Clone(), button, clicks, delta);
	}

	public void OnMouseEnter(POINT mp, MouseButtonsA button, int clicks, int delta) {
		CallMouseEvents(EVENTID.MOUSEENTER, mp.Clone(), button, clicks, delta);
	}

	public void OnMouseLeave(POINT mp, MouseButtonsA button, int clicks, int delta) {
		CallMouseEvents(EVENTID.MOUSELEAVE, mp.Clone(), button, clicks, delta);
	}

	public void OnMouseMove(POINT mp, MouseButtonsA button, int clicks, int delta) {
		CallMouseEvents(EVENTID.MOUSEMOVE, mp.Clone(), button, clicks, delta);
	}

	public void OnMouseUp(POINT mp, MouseButtonsA button, int clicks, int delta) {
		CallMouseEvents(EVENTID.MOUSEUP, mp.Clone(), button, clicks, delta);
	}

	public void OnPaddingChanged() {
		CallEvents(EVENTID.PADDINGCHANGED);
	}

	public void OnPaint(CPaintMe paint, RECT clipRect) {
		OnPaintBackground(paint, clipRect);
		OnPaintForeground(paint, clipRect);
		CallPaintEvents(EVENTID.PAINT, paint, clipRect);
	}

	public void OnPaintBackground(CPaintMe paint, RECT clipRect) {
		RECT rect = new RECT(0, 0, GetWidth(), GetHeight());
		paint.FillRoundRect(GetPaintingBackColor(), rect, this.m_cornerRadius);
		String bkImage = GetPaintingBackImage();
		if ((bkImage != null) && (bkImage.length() > 0)) {
			paint.DrawImage(bkImage, rect);
		}
	}

	public void OnPaintBorder(CPaintMe paint, RECT clipRect) {
		RECT borderRect = new RECT(0, 0, GetWidth(), GetHeight());
		paint.DrawRoundRect(GetPaintingBorderColor(), 1.0F, 0, borderRect, this.m_cornerRadius);
		CallPaintEvents(EVENTID.PAINTBORDER, paint, clipRect);
	}

	public void OnPaintForeground(CPaintMe paint, RECT clipRect) {
	}

	public void OnParentChanged() {
		CallEvents(EVENTID.PARENTCHANGED);
	}

	public void OnPaste() {
		CallEvents(EVENTID.PASTE);
	}

	public void OnPrePaint(CPaintMe paint, RECT clipRect) {
	}

	public boolean OnPreviewsMouseEvent(int eventID, POINT mp, MouseButtonsA button, int clicks, int delta) {
		return CallPreviewsMouseEvents(EVENTID.PREVIEWSMOUSEEVENT, eventID, mp, button, clicks, delta);
	}

	public void OnRemove() {
		CallEvents(EVENTID.REMOVE);
	}

	public void OnRegionChanged() {
		CallEvents(EVENTID.REGIONCHANGED);
	}

	public void OnSizeChanged() {
		CallEvents(EVENTID.SIZECHANGED);
	}

	public void OnTabIndexChanged() {
		CallEvents(EVENTID.TABINDEXCHANGED);
	}

	public void OnTabStop() {
		CallEvents(EVENTID.TABSTOP);
	}

	public void OnTabStopChanged() {
		CallEvents(EVENTID.TABSTOPCHANGED);
	}

	public void OnTextChanged() {
		CallEvents(EVENTID.TEXTCHANGED);
	}

	public void OnTimer(int timerID) {
		CallTimerEvents(EVENTID.TIMER, timerID);
	}

	public void OnTouchBegin(ArrayList<CTouch> touches) {
		CallTouchEvents(EVENTID.TOUCHBEGIN, touches);
	}

	public void OnTouchCancel(ArrayList<CTouch> touches) {
		CallTouchEvents(EVENTID.TOUCHCANCEL, touches);
	}

	public void OnTouchEnd(ArrayList<CTouch> touches) {
		CallTouchEvents(EVENTID.TOUCHEND, touches);
	}

	public void OnTouchMove(ArrayList<CTouch> touches) {
		CallTouchEvents(EVENTID.TOUCHMOVE, touches);
	}

	public void OnVisibleChanged() {
		CallEvents(EVENTID.VISIBLECHANGED);
	}

	public POINT PointToControl(POINT point) {
		if (this.m_native != null) {
			int clientX = this.m_native.ClientX(this);
			int clientY = this.m_native.ClientY(this);
			return new POINT(point.x - clientX, point.y - clientY);
		}

		return point.Clone();
	}

	public POINT PointToNative(POINT point) {
		if (this.m_native != null) {
			int clientX = this.m_native.ClientX(this);
			int clientY = this.m_native.ClientY(this);
			return new POINT(point.x + clientX, point.y + clientY);
		}

		return point.Clone();
	}

	public void RegisterEvent(Object func, int eventID) {
		if (this.m_events == null) {
			this.m_events = new HashMap();
		}
		ArrayList<Object> eventList = null;
		if (this.m_events.containsKey(Integer.valueOf(eventID))) {
			eventList = (ArrayList) this.m_events.get(Integer.valueOf(eventID));
		} else {
			eventList = new ArrayList();
			this.m_events.put(Integer.valueOf(eventID), eventList);
		}
		eventList.add(func);
	}

	public void RemoveControl(CControlMe control) {
		if (this.m_native != null) {
			this.m_native.RemoveControl(control);
		}
		this.m_controls.remove(control);
		control.OnRemove();
		control.SetParent(null);
	}

	public void SendChildToBack(CControlMe childControl) {
		if ((this.m_controls != null) && (this.m_controls.size() > 0)) {
			this.m_controls.remove(childControl);
			this.m_controls.add(0, childControl);
		}
	}

	public void SendToBack() {
		if (this.m_native != null) {
			this.m_native.SendToBack(this);
		}
	}

	public void SetProperty(String name, String value) {
		int len = name.length();
		switch (len) {

		case 3:
			if (name.equals("top")) {
				if (value.indexOf("%") != -1) {
					float percentValue = CStrMe.ConvertStrToFloat(value.replace("%", "")) / 100.0F;
					if (this.m_percentLocation == null) {
						this.m_percentLocation = new POINTF();
						this.m_percentLocation.x = -1.0F;
					}
					this.m_percentLocation.y = percentValue;
				} else {
					if (this.m_percentLocation != null) {
						this.m_percentLocation.y = -1.0F;
					}
					SetTop(CStrMe.ConvertStrToInt(value));
				}
			}

			break;
		case 4:
			if (name.equals("dock")) {
				SetDock(CStrMe.ConvertStrToDock(value));
			} else if (name.equals("font")) {
				SetFont(CStrMe.ConvertStrToFont(value));
			} else if (name.equals("left")) {
				if (value.indexOf("%") != -1) {
					float percentValue = CStrMe.ConvertStrToFloat(value.replace("%", "")) / 100.0F;
					if (this.m_percentLocation == null) {
						this.m_percentLocation = new POINTF();
						this.m_percentLocation.y = -1.0F;
					}
					this.m_percentLocation.x = percentValue;
				} else {
					SetLeft(CStrMe.ConvertStrToInt(value));
					if (this.m_percentLocation != null) {
						this.m_percentLocation.x = -1.0F;
					}
				}
			} else if (name.equals("name")) {
				SetName(value);
			} else if (name.equals("size")) {
				SetSize(CStrMe.ConvertStrToSize(value));
			} else if (name.equals("text")) {
				SetText(value);
			}

			break;
		case 5:
			if (name.equals("align")) {
				SetAlign(CStrMe.ConvertStrToHorizontalAlign(value));
			} else if (name.equals("width")) {
				if (value.indexOf("%") != -1) {
					float percentValue = CStrMe.ConvertStrToFloat(value.replace("%", "")) / 100.0F;
					if (this.m_percentSize == null) {
						this.m_percentSize = new SIZEF();
						this.m_percentSize.cy = -1.0F;
					}
					this.m_percentSize.cx = percentValue;
				} else {
					SetWidth(CStrMe.ConvertStrToInt(value));
					if (this.m_percentSize != null) {
						this.m_percentSize.cx = -1.0F;
					}
				}
			}

			break;
		case 6:
			if (name.equals("anchor")) {
				SetAnchor(CStrMe.ConvertStrToAnchor(value));
			} else if (name.equals("bounds")) {
				SetBounds(CStrMe.ConvertStrToRect(value));
			} else if (name.equals("height")) {
				if (value.indexOf("%") != -1) {
					float percentValue = CStrMe.ConvertStrToFloat(value.replace("%", "")) / 100.0F;
					if (this.m_percentSize == null) {
						this.m_percentSize = new SIZEF();
						this.m_percentSize.cx = -1.0F;
					}
					this.m_percentSize.cy = percentValue;
				} else {
					if (this.m_percentSize != null) {
						this.m_percentSize.cy = -1.0F;
					}
					SetHeight(CStrMe.ConvertStrToInt(value));
				}
			} else if (name.equals("margin")) {
				SetMargin(CStrMe.ConvertStrToPadding(value));
			} else if (name.equals("region")) {
				SetRegion(CStrMe.ConvertStrToRect(value));
			}

			break;
		case 7:
			if (name.equals("enabled")) {
				SetEnabled(CStrMe.ConvertStrToBool(value));
			} else if (name.equals("focused")) {
				SetFocused(CStrMe.ConvertStrToBool(value));
			} else if (name.equals("opacity")) {
				SetOpacity(CStrMe.ConvertStrToFloat(value));
			} else if (name.equals("padding")) {
				SetPadding(CStrMe.ConvertStrToPadding(value));
			} else if (name.equals("topmost")) {
				SetTopMost(CStrMe.ConvertStrToBool(value));
			} else if (name.equals("visible")) {
				SetVisible(CStrMe.ConvertStrToBool(value));
			}

			break;
		case 8:
			if (name.equals("autosize")) {
				SetAutoSize(CStrMe.ConvertStrToBool(value));
			} else if (name.equals("canfocus")) {
				SetCanFocus(CStrMe.ConvertStrToBool(value));
			} else if (name.equals("iswindow")) {
				SetIsWindow(CStrMe.ConvertStrToBool(value));
			} else if (name.equals("location")) {
				SetLocation(CStrMe.ConvertStrToPoint(value));
			}

			break;
		case 9:
			if (name.equals("allowdrag")) {
				SetAllowDrag(CStrMe.ConvertStrToBool(value));
			} else if (name.equals("backcolor")) {
				SetBackColor(CStrMe.ConvertStrToColor(value));
			} else if (name.equals("backimage")) {
				SetBackImage(value);
			} else if (name.equals("forecolor")) {
				SetForeColor(CStrMe.ConvertStrToColor(value));
			}

			break;
		default:
			if (name.equals("allowpreviewsevent")) {
				SetAllowPreviewsEvent(CStrMe.ConvertStrToBool(value));
			} else if (name.equals("autoellipsis")) {
				SetAutoEllipsis(CStrMe.ConvertStrToBool(value));
			} else if (name.equals("bordercolor")) {
				SetBorderColor(CStrMe.ConvertStrToColor(value));
			} else if (name.equals("canraiseevents")) {
				SetCanRaiseEvents(CStrMe.ConvertStrToBool(value));
			} else if (name.equals("cornerradius")) {
				SetCornerRadius(CStrMe.ConvertStrToInt(value));
			} else if (name.equals("displayoffset")) {
				SetDisplayOffset(CStrMe.ConvertStrToBool(value));
			} else if (name.equals("haspopupmenu")) {
				SetHasPopupMenu(CStrMe.ConvertStrToBool(value));
			} else if (name.equals("maximumsize")) {
				SetMaximumSize(CStrMe.ConvertStrToSize(value));
			} else if (name.equals("minimumsize")) {
				SetMinimumSize(CStrMe.ConvertStrToSize(value));
			} else if (name.equals("resourcepath")) {
				SetResourcePath(value);
			} else if (name.equals("vertical-align")) {
				SetVerticalAlign(CStrMe.ConvertStrToVerticalAlign(value));
			}
			break;
		}
	}

	public void Show() {
		SetVisible(true);
	}

	public void StartTimer(int timerID, int interval) {
		if (this.m_native != null) {
			this.m_native.StartTimer(this, timerID, interval);
		}
	}

	public void StopTimer(int timerID) {
		if (this.m_native != null) {
			this.m_native.StopTimer(timerID);
		}
	}

	public void Update() {
		if (this.m_native != null) {
			this.m_native.SetAlign(this.m_controls);
			if ((this.m_oldSize.cx > 0) && (this.m_oldSize.cy > 0)) {
				this.m_native.SetAnchor(this.m_controls, this.m_oldSize);
			}
			this.m_native.SetDock(this.m_controls);
			this.m_oldLocation = GetLocation();
			this.m_oldSize = GetSize();
			int controlsSize = this.m_controls.size();
			for (int i = 0; i < controlsSize; i++) {
				((CControlMe) this.m_controls.get(i)).Update();
			}
		}
	}

	public void UnRegisterEvent(Object func, int eventID) {
		if ((this.m_events != null) && (this.m_events.containsKey(Integer.valueOf(eventID)))) {
			((ArrayList) this.m_events.get(Integer.valueOf(eventID))).remove(func);
		}
	}
}
