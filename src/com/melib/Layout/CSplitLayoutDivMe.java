package com.melib.Layout;

import java.util.ArrayList;
import java.util.Arrays;
import com.melib.Base.CStrMe;
import com.melib.Base.CControlMe;
import com.melib.Base.ControlEvent;
import com.melib.Base.CControlHostMe;
import com.melib.Base.EVENTID;
import com.melib.Base.INativeBaseMe;
import com.melib.Base.LayoutStyleA;
import com.melib.Base.RECT;
import com.melib.Base.RefObject;
import com.melib.Base.SIZE;
import com.melib.Button.CButtonMe;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class CSplitLayoutDivMe extends CLayoutDivMe implements ControlEvent {
	protected SIZE m_oldSize;
	protected float m_splitPercent = -1.0F;

	protected CControlMe m_firstControl = null;

	public CControlMe GetFirstControl() {
		return this.m_firstControl;
	}

	public void SetFirstControl(CControlMe value) {
		this.m_firstControl = value;
	}

	protected CControlMe m_secondControl = null;

	public CControlMe GetSecondControl() {
		return this.m_secondControl;
	}

	public void SetSecondControl(CControlMe value) {
		this.m_secondControl = value;
	}

	protected SizeTypeA m_splitMode = SizeTypeA.AbsoluteSize;

	public SizeTypeA GetSplitMode() {
		return this.m_splitMode;
	}

	public void SetSplitMode(SizeTypeA value) {
		this.m_splitMode = value;
	}

	protected CButtonMe m_splitter = null;

	public CButtonMe GetSplitter() {
		return this.m_splitter;
	}

	public void CallControlEvent(int eventID, Object sender) {
		if ((eventID == EVENTID.DRAGGING) && (sender == this.m_splitter)) {
			this.m_splitPercent = -1.0F;
			Update();
			Invalidate();
		}
	}

	public void Dispose() {
		super.Dispose();
	}

	public String GetControlType() {
		return "SplitLayoutDiv";
	}

	public void GetProperty(String name, RefObject<String> value, RefObject<String> type) {
		if (name.equals("candragsplitter")) {
			type.argvalue = "bool";
			if (this.m_splitter != null) {
				value.argvalue = CStrMe.ConvertBoolToStr(this.m_splitter.AllowDrag());
			} else {
				value.argvalue = "False";
			}
		} else if (name.equals("splitmode")) {
			type.argvalue = "enum:SizeTypeA";
			if (GetSplitMode() == SizeTypeA.AbsoluteSize) {
				value.argvalue = "AbsoluteSize";
			} else {
				value.argvalue = "PercentSize";
			}
		} else if (name.indexOf("splitter-") != -1) {
			if (this.m_splitter != null) {
				this.m_splitter.GetProperty(name.substring(9), value, type);
			}
		} else if (name.equals("splitterposition")) {
			type.argvalue = "rect";
			if (this.m_splitter != null) {
				value.argvalue = CStrMe.ConvertRectToStr(this.m_splitter.GetBounds());
			}
			value.argvalue = "0,0,0,0";
		} else if (name.equals("splittervisible")) {
			type.argvalue = "bool";
			if (this.m_splitter != null) {
				value.argvalue = CStrMe.ConvertBoolToStr(this.m_splitter.IsVisible());
			} else {
				value.argvalue = "False";
			}
		} else {
			super.GetProperty(name, value, type);
		}
	}

	public ArrayList<String> GetPropertyNames() {
		ArrayList<String> propertyNames = super.GetPropertyNames();
		propertyNames.addAll(Arrays.asList(
				new String[] { "CanDragSplitter", "SplitMode", "Splitter", "SplitterPosition", "SplitterVisible" }));
		return propertyNames;
	}

	public void OnLoad() {
		super.OnLoad();
		if (this.m_splitter == null) {
			CControlHostMe host = GetNative().GetHost();
			CControlMe control = host.CreateInternalControl(this, "splitter");
			this.m_splitter = ((CButtonMe) ((control instanceof CButtonMe) ? control : null));
			this.m_splitter.RegisterEvent(this, EVENTID.DRAGGING);
			AddControl(this.m_splitter);
		}
		this.m_oldSize = GetSize();
	}

	public boolean OnResetLayout() {
		boolean flag = false;
		if ((GetNative() != null) && (this.m_splitter != null) && (this.m_firstControl != null)
				&& (this.m_secondControl != null)) {
			RECT rect = new RECT();
			int width = GetWidth();
			int height = GetHeight();
			RECT fRect = new RECT();
			RECT sRect = new RECT();
			SIZE size = new SIZE(0, 0);
			if (this.m_splitter.IsVisible()) {
				size.cx = this.m_splitter.GetWidth();
				size.cy = this.m_splitter.GetHeight();
			}
			LayoutStyleA layoutSytle = GetLayoutStyle();
			switch (layoutSytle) {
			case BottomToTop:
				if ((this.m_splitMode == SizeTypeA.AbsoluteSize) || (this.m_oldSize.cy == 0)) {
					rect.left = 0;
					rect.top = (height - (this.m_oldSize.cy - this.m_splitter.GetTop()));
					rect.right = width;
					rect.bottom = (rect.top + size.cy);
				} else if (this.m_splitMode == SizeTypeA.PercentSize) {
					rect.left = 0;
					if (this.m_splitPercent == -1.0F) {
						this.m_splitPercent = (this.m_splitter.GetTop() / this.m_oldSize.cy);
					}
					rect.top = ((int) (height * this.m_splitPercent));
					rect.right = width;
					rect.bottom = (rect.top + size.cy);
				}
				fRect.left = 0;
				fRect.top = rect.bottom;
				fRect.right = width;
				fRect.bottom = height;
				sRect.left = 0;
				sRect.top = 0;
				sRect.right = width;
				sRect.bottom = rect.top;
				break;
			case LeftToRight:
				if ((this.m_splitMode == SizeTypeA.AbsoluteSize) || (this.m_oldSize.cx == 0)) {
					rect.left = this.m_splitter.GetLeft();
					rect.top = 0;
					rect.right = (this.m_splitter.GetLeft() + size.cx);
					rect.bottom = height;
				} else if (this.m_splitMode == SizeTypeA.PercentSize) {
					if (this.m_splitPercent == -1.0F) {
						this.m_splitPercent = (this.m_splitter.GetLeft() / this.m_oldSize.cx);
					}
					rect.left = ((int) (width * this.m_splitPercent));
					rect.top = 0;
					rect.right = (rect.left + size.cx);
					rect.bottom = height;
				}
				fRect.left = 0;
				fRect.top = 0;
				fRect.right = rect.left;
				fRect.bottom = height;
				sRect.left = rect.right;
				sRect.top = 0;
				sRect.right = width;
				sRect.bottom = height;
				break;
			case RightToLeft:
				if ((this.m_splitMode == SizeTypeA.AbsoluteSize) || (this.m_oldSize.cx == 0)) {
					rect.left = (width - (this.m_oldSize.cx - this.m_splitter.GetLeft()));
					rect.top = 0;
					rect.right = (rect.left + size.cx);
					rect.bottom = height;
				} else if (this.m_splitMode == SizeTypeA.PercentSize) {
					if (this.m_splitPercent == -1.0F) {
						this.m_splitPercent = (this.m_splitter.GetLeft() / this.m_oldSize.cx);
					}
					rect.left = ((int) (width * this.m_splitPercent));
					rect.top = 0;
					rect.right = (rect.left + size.cx);
					rect.bottom = height;
				}
				fRect.left = rect.right;
				fRect.top = 0;
				fRect.right = width;
				fRect.bottom = height;
				sRect.left = 0;
				sRect.top = 0;
				sRect.right = rect.left;
				sRect.bottom = height;
				break;
			case TopToBottom:
				if ((this.m_splitMode == SizeTypeA.AbsoluteSize) || (this.m_oldSize.cy == 0)) {
					rect.left = 0;
					rect.top = this.m_splitter.GetTop();
					rect.right = width;
					rect.bottom = (rect.top + size.cy);
				} else if (this.m_splitMode == SizeTypeA.PercentSize) {
					rect.left = 0;
					if (this.m_splitPercent == -1.0F) {
						this.m_splitPercent = (this.m_splitter.GetTop() / this.m_oldSize.cy);
					}
					rect.top = ((int) (height * this.m_splitPercent));
					rect.right = width;
					rect.bottom = (rect.top + size.cy);
				}
				fRect.left = 0;
				fRect.top = 0;
				fRect.right = width;
				fRect.bottom = rect.top;
				sRect.left = 0;
				sRect.top = rect.bottom;
				sRect.right = width;
				sRect.bottom = height;
			}

			if (this.m_splitter.IsVisible()) {
				RECT bouds = this.m_splitter.GetBounds();
				if ((bouds.left != rect.left) || (bouds.top != rect.top) || (bouds.right != rect.right)
						|| (bouds.bottom != rect.bottom)) {
					this.m_splitter.SetBounds(rect);
					flag = true;
				}
				if (this.m_splitter.AllowDrag()) {
					this.m_splitter.BringToFront();
				}
			}
			RECT scRect = this.m_firstControl.GetBounds();
			if ((scRect.left != fRect.left) || (scRect.top != fRect.top) || (scRect.right != fRect.right)
					|| (scRect.bottom != fRect.bottom)) {
				flag = true;
				this.m_firstControl.SetBounds(fRect);
				this.m_firstControl.Update();
			}
			RECT scRect2 = this.m_secondControl.GetBounds();
			if ((scRect2.left != sRect.left) || (scRect2.top != sRect.top) || (scRect2.right != sRect.right)
					|| (scRect2.bottom != sRect.bottom)) {
				flag = true;
				this.m_secondControl.SetBounds(sRect);
				this.m_secondControl.Update();
			}
		}
		this.m_oldSize = GetSize();
		return flag;
	}

	public void SetProperty(String name, String value) {
		if (name.equals("candragsplitter")) {
			if (this.m_splitter != null) {
				this.m_splitter.SetAllowDrag(CStrMe.ConvertStrToBool(value));
			}
		} else if (name.equals("splitmode")) {
			value = value.toLowerCase();
			if (value.equals("absolutesize")) {
				SetSplitMode(SizeTypeA.AbsoluteSize);
			} else {
				SetSplitMode(SizeTypeA.PercentSize);
			}
		} else if (name.indexOf("splitter-") != -1) {
			if (this.m_splitter != null) {
				this.m_splitter.SetProperty(name.substring(9), value);
			}
		} else if (name.equals("splitterposition")) {
			if (this.m_splitter != null) {
				this.m_splitter.SetBounds(CStrMe.ConvertStrToRect(value));
			}
		} else if (name.equals("splittervisible")) {
			if (this.m_splitter != null) {
				this.m_splitter.SetVisible(CStrMe.ConvertStrToBool(value));
			}
		} else {
			super.SetProperty(name, value);
		}
	}

	public void Update() {
		OnResetLayout();
		int count = this.m_controls.size();
		for (int i = 0; i < count; i++) {
			((CControlMe) this.m_controls.get(i)).Update();
		}
	}
}
