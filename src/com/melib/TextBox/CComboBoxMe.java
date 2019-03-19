package com.melib.TextBox;

import java.util.ArrayList;
import java.util.Arrays;
import com.melib.Base.CStrMe;
import com.melib.Base.CControlMe;
import com.melib.Base.CControlHostMe;
import com.melib.Base.ControlMouseEvent;
import com.melib.Base.EVENTID;
import com.melib.Base.INativeBaseMe;
import com.melib.Base.MouseButtonsA;
import com.melib.Base.PADDING;
import com.melib.Base.POINT;
import com.melib.Base.RefObject;
import com.melib.Base.SIZE;
import com.melib.Button.CButtonMe;
import com.melib.Menu.CMenuItemMe;
import com.melib.Menu.MenuItemMouseEvent;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public class CComboBoxMe extends CTextBoxMe implements MenuItemMouseEvent, ControlMouseEvent {
	private CButtonMe m_dropDownButton = null;

	public CButtonMe GetDropDownButton() {
		return this.m_dropDownButton;
	}

	private CComboBoxMenuMe m_dropDownMenu = null;

	public CComboBoxMenuMe GetDropDownMenu() {
		return this.m_dropDownMenu;
	}

	public int GetSelectedIndex() {
		if (this.m_dropDownMenu != null) {
			ArrayList<CMenuItemMe> items = this.m_dropDownMenu.GetItems();
			int count = items.size();
			for (int i = 0; i < count; i++) {
				CMenuItemMe menuItemA = (CMenuItemMe) items.get(i);
				if (menuItemA.IsChecked()) {
					return i;
				}
			}
		}
		return -1;
	}

	public void SetSelectedIndex(int value) {
		if (this.m_dropDownMenu != null) {
			ArrayList<CMenuItemMe> items = this.m_dropDownMenu.GetItems();
			int count = items.size();
			boolean changed = false;
			for (int i = 0; i < count; i++) {
				CMenuItemMe menuItemA = (CMenuItemMe) items.get(i);
				if (i == value) {
					if (!menuItemA.IsChecked()) {
						menuItemA.SetChecked(true);
						changed = true;
					}
					SetText(menuItemA.GetText());
				} else {
					menuItemA.SetChecked(false);
				}
			}
			if (changed) {
				OnSelectedIndexChanged();
			}
		}
	}

	public String GetSelectedText() {
		if (this.m_dropDownMenu != null) {
			ArrayList<CMenuItemMe> items = this.m_dropDownMenu.GetItems();
			int count = items.size();
			for (int i = 0; i < count; i++) {
				CMenuItemMe menuItemA = (CMenuItemMe) items.get(i);
				if (menuItemA.IsChecked()) {
					return menuItemA.GetText();
				}
			}
		}
		return null;
	}

	public void SetSelectedText(String value) {
		if (this.m_dropDownMenu != null) {
			ArrayList<CMenuItemMe> items = this.m_dropDownMenu.GetItems();
			int count = items.size();
			boolean changed = false;
			for (int i = 0; i < count; i++) {
				CMenuItemMe menuItemA = (CMenuItemMe) items.get(i);
				if (menuItemA.GetText().equals(value)) {
					if (!menuItemA.IsChecked()) {
						menuItemA.SetChecked(true);
						changed = true;
					}
					SetText(menuItemA.GetText());
				} else {
					menuItemA.SetChecked(false);
				}
			}
			if (changed) {
				OnSelectedIndexChanged();
			}
		}
	}

	public String GetSelectedValue() {
		if (this.m_dropDownMenu != null) {
			ArrayList<CMenuItemMe> items = this.m_dropDownMenu.GetItems();
			int count = items.size();
			for (int i = 0; i < count; i++) {
				CMenuItemMe menuItemA = (CMenuItemMe) items.get(i);
				if (menuItemA.IsChecked()) {
					return menuItemA.GetValue();
				}
			}
		}
		return null;
	}

	public void SetSelectedValue(String value) {
		if (this.m_dropDownMenu != null) {
			ArrayList<CMenuItemMe> items = this.m_dropDownMenu.GetItems();
			int count = items.size();
			boolean changed = false;
			for (int i = 0; i < count; i++) {
				CMenuItemMe menuItemA = (CMenuItemMe) items.get(i);
				if (menuItemA.GetValue().equals(value)) {
					if (!menuItemA.IsChecked()) {
						menuItemA.SetChecked(true);
						changed = true;
					}
					SetText(menuItemA.GetText());
				} else {
					menuItemA.SetChecked(false);
				}
			}
			if (changed) {
				OnSelectedIndexChanged();
			}
		}
	}

	public void AddItem(CMenuItemMe item) {
		if (this.m_dropDownMenu != null) {
			this.m_dropDownMenu.AddItem(item);
		}
	}

	public void CallControlMouseEvent(int eventID, Object sender, POINT mp, MouseButtonsA button, int clicks,
			int delta) {
		if (sender == this.m_dropDownButton) {
			if (eventID == EVENTID.MOUSEDOWN) {
				OnDropDownOpening();
			}
		}
	}

	public void CallMenuItemMouseEvent(int eventID, Object sender, CMenuItemMe item, POINT mp, MouseButtonsA button,
			int clicks, int delta) {
		if (sender == this.m_dropDownMenu) {
			if (eventID == EVENTID.MENUITEMCLICK) {
				SetText(item.GetText());
				ArrayList<CMenuItemMe> items = GetItems();
				int count = items.size();
				for (int i = 0; i < count; i++) {
					if (items.get(i) == item) {
						SetSelectedIndex(i);
						break;
					}
				}
				SetSelectionStart(GetText().length());
				Invalidate();
			}
		}
	}

	public void Dispose() {
		if (!IsDisposed()) {
			if (this.m_dropDownButton != null) {
				this.m_dropDownButton.UnRegisterEvent(this, EVENTID.MOUSEDOWN);
			}
			if (this.m_dropDownMenu != null) {
				this.m_dropDownMenu.UnRegisterEvent(this, EVENTID.MENUITEMCLICK);
				GetNative().RemoveControl(this.m_dropDownMenu);
				this.m_dropDownMenu.Dispose();
				this.m_dropDownMenu = null;
			}
		}
		super.Dispose();
	}

	public void ClearItems() {
		if (this.m_dropDownMenu != null) {
			this.m_dropDownMenu.ClearItems();
		}
	}

	public String GetControlType() {
		return "ComboBox";
	}

	public ArrayList<CMenuItemMe> GetItems() {
		if (this.m_dropDownMenu != null) {
			return this.m_dropDownMenu.GetItems();
		}
		return null;
	}

	public void GetProperty(String name, RefObject<String> value, RefObject<String> type) {
		if (name.equals("selectedindex")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertIntToStr(GetSelectedIndex());
		} else if (name.equals("selectedtext")) {
			type.argvalue = "text";
			value.argvalue = GetSelectedText();
		} else if (name.equals("selectedvalue")) {
			type.argvalue = "text";
			value.argvalue = GetSelectedValue();
		} else {
			super.GetProperty(name, value, type);
		}
	}

	public ArrayList<String> GetPropertyNames() {
		ArrayList<String> propertyNames = super.GetPropertyNames();
		propertyNames.addAll(Arrays.asList(new String[] { "SelectedIndex", "SelectedText", "SelectedValue" }));
		return propertyNames;
	}

	public void InsertItem(int index, CMenuItemMe item) {
		if (this.m_dropDownMenu != null) {
			this.m_dropDownMenu.InsertItem(index, item);
		}
	}

	public void OnDropDownOpening() {
		this.m_dropDownMenu.SetNative(GetNative());
		POINT pp = PointToNative(new POINT(0, GetHeight()));
		this.m_dropDownMenu.SetLocation(pp);
		this.m_dropDownMenu.SetSize(new SIZE(GetWidth(), this.m_dropDownMenu.GetContentHeight()));
		this.m_dropDownMenu.SetVisible(true);
		this.m_dropDownMenu.BringToFront();
		this.m_dropDownMenu.Invalidate();
	}

	public void OnLoad() {
		super.OnLoad();
		CControlHostMe host = GetNative().GetHost();
		if (this.m_dropDownButton == null) {
			CControlMe controlA = host.CreateInternalControl(this, "dropdownbutton");
			this.m_dropDownButton = ((CButtonMe) ((controlA instanceof CButtonMe) ? controlA : null));
			AddControl(this.m_dropDownButton);
			this.m_dropDownButton.RegisterEvent(this, EVENTID.MOUSEDOWN);
		}
		if (this.m_dropDownMenu == null) {
			CControlMe controlA = host.CreateInternalControl(this, "dropdownmenu");
			this.m_dropDownMenu = ((CComboBoxMenuMe) ((controlA instanceof CComboBoxMenuMe) ? controlA : null));
			GetNative().AddControl(this.m_dropDownMenu);
			this.m_dropDownMenu.SetVisible(false);
			this.m_dropDownMenu.RegisterEvent(this, EVENTID.MENUITEMCLICK);
		} else {
			this.m_dropDownMenu.SetNative(GetNative());
		}
	}

	public void OnSelectedIndexChanged() {
		CallEvents(EVENTID.SELECTEDINDEXCHANGED);
	}

	public void RemoveItem(CMenuItemMe item) {
		if (this.m_dropDownMenu != null) {
			this.m_dropDownMenu.RemoveItem(item);
		}
	}

	public void SetProperty(String name, String value) {
		if (name.equals("selectedindex")) {
			SetSelectedIndex(CStrMe.ConvertStrToInt(value));
		} else if (name.equals("selectedtext")) {
			SetSelectedText(value);
		} else if (name.equals("selectedvalue")) {
			SetSelectedValue(value);
		} else {
			super.SetProperty(name, value);
		}
	}

	public void Update() {
		super.Update();
		int width = GetWidth();
		int height = GetHeight();
		if (this.m_dropDownButton != null) {
			int dWidth = this.m_dropDownButton.GetWidth();
			this.m_dropDownButton.SetLocation(new POINT(width - dWidth, 0));
			this.m_dropDownButton.SetSize(new SIZE(dWidth, height));
			SetPadding(new PADDING(0, 0, dWidth, 0));
		}
	}
}
