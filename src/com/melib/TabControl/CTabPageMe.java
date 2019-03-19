package com.melib.TabControl;

import java.util.ArrayList;
import java.util.Arrays;
import com.melib.Base.CStrMe;
import com.melib.Base.CControlMe;
import com.melib.Base.CControlHostMe;
import com.melib.Base.ControlMouseEvent;
import com.melib.Base.EVENTID;
import com.melib.Base.INativeBaseMe;
import com.melib.Base.MouseButtonsA;
import com.melib.Base.POINT;
import com.melib.Base.RefObject;
import com.melib.Button.CButtonMe;
import com.melib.Layout.CDivMe;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public class CTabPageMe extends CDivMe implements ControlMouseEvent {
	private CButtonMe m_headerButton = null;

	public CButtonMe GetHeaderButton() {
		return this.m_headerButton;
	}

	public void SetHeaderButton(CButtonMe value) {
		this.m_headerButton = value;
	}

	private POINT m_headerLocation = new POINT();

	public POINT GetHeaderLocation() {
		return this.m_headerLocation.Clone();
	}

	public void SetHeaderLocation(POINT value) {
		this.m_headerLocation = value.Clone();
	}

	public boolean IsHeaderVisible() {
		if (this.m_headerButton != null) {
			return this.m_headerButton.IsVisible();
		}

		return false;
	}

	public void SetHeaderVisible(boolean value) {
		if (this.m_headerButton != null) {
			this.m_headerButton.SetVisible(value);
		}
	}

	private CTabControlMe m_tabControl = null;

	public CTabControlMe GetTabControl() {
		return this.m_tabControl;
	}

	public void SetTabControl(CTabControlMe value) {
		this.m_tabControl = value;
	}

	public void CallControlEvent(int eventID, Object sender) {
		super.CallControlEvent(eventID, sender);
		if (sender == this.m_headerButton) {
			if (eventID == EVENTID.DRAGBEGIN) {
				if (this.m_tabControl != null) {
					this.m_tabControl.OnDragTabHeaderBegin(this);
				}
			} else if (eventID == EVENTID.DRAGEND) {
				if (this.m_tabControl != null) {
					this.m_tabControl.OnDragTabHeaderEnd(this);
				}
			} else if (eventID == EVENTID.DRAGGING) {
				if (this.m_tabControl != null) {
					this.m_tabControl.OnDraggingTabHeader(this);
				}
			}
		}
	}

	public void CallControlMouseEvent(int eventID, Object sender, POINT mp, MouseButtonsA button, int clicks,
			int delta) {
		if (sender == this.m_headerButton) {
			if (eventID == EVENTID.MOUSEDOWN) {
				if (this.m_tabControl != null) {
					this.m_tabControl.SetSelectedTabPage(this);
					this.m_tabControl.Invalidate();
				}
			}
		}
	}

	public void Dispose() {
		if (!IsDisposed()) {
			if (this.m_headerButton != null) {
				this.m_headerButton.UnRegisterEvent(this, EVENTID.DRAGBEGIN);
				this.m_headerButton.UnRegisterEvent(this, EVENTID.DRAGEND);
				this.m_headerButton.UnRegisterEvent(this, EVENTID.DRAGGING);
				this.m_headerButton.UnRegisterEvent(this, EVENTID.MOUSEDOWN);
			}
			this.m_headerButton = null;
		}
		super.Dispose();
	}

	public String GetControlType() {
		return "TabPage";
	}

	public void GetProperty(String name, RefObject<String> value, RefObject<String> type) {
		if (name.equals("headersize")) {
			type.argvalue = "size";
			if (this.m_headerButton != null) {
				value.argvalue = CStrMe.ConvertSizeToStr(this.m_headerButton.GetSize());
			} else {
				value.argvalue = "0,0";
			}
		} else if (name.equals("headervisible")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(IsHeaderVisible());
		} else if (name.indexOf("header-") != -1) {
			if (this.m_headerButton != null) {
				this.m_headerButton.GetProperty(name.substring(7), value, type);
			}

		} else {
			super.GetProperty(name, value, type);
		}
	}

	public ArrayList<String> GetPropertyNames() {
		ArrayList<String> propertyNames = super.GetPropertyNames();
		propertyNames.addAll(Arrays.asList(new String[] { "Header", "HeaderSize", "HeaderVisible" }));
		return propertyNames;
	}

	public void OnLoad() {
		super.OnLoad();
		if (this.m_tabControl != null) {
			if (this.m_headerButton == null) {
				CControlHostMe host = GetNative().GetHost();
				CControlMe controlA = host.CreateInternalControl(this, "headerbutton");
				this.m_headerButton = ((CButtonMe) ((controlA instanceof CButtonMe) ? controlA : null));
				this.m_headerButton.RegisterEvent(this, EVENTID.DRAGBEGIN);
				this.m_headerButton.RegisterEvent(this, EVENTID.DRAGEND);
				this.m_headerButton.RegisterEvent(this, EVENTID.DRAGGING);
				this.m_headerButton.RegisterEvent(this, EVENTID.MOUSEDOWN);
			}
			if (!this.m_tabControl.ContainsControl(this.m_headerButton)) {
				this.m_tabControl.AddControl(this.m_headerButton);
			}
		}
	}

	public void OnTextChanged() {
		super.OnTextChanged();
		if (this.m_headerButton != null) {
			this.m_headerButton.SetText(GetText());
		}
	}

	public void SetProperty(String name, String value) {
		if (name.equals("headersize")) {
			if (this.m_headerButton != null) {
				this.m_headerButton.SetProperty("width", value);
			}
		} else if (name.equals("headervisible")) {
			SetHeaderVisible(CStrMe.ConvertStrToBool(value));
		} else if (name.indexOf("header-") != -1) {
			if (this.m_headerButton != null) {
				this.m_headerButton.SetProperty(name.substring(7), value);
			}

		} else {
			super.SetProperty(name, value);
		}
	}
}
