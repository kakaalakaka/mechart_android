package com.melib.Menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import com.melib.Base.CStrMe;
import com.melib.Base.CControlMe;
import com.melib.Base.EVENTID;
import com.melib.Base.INativeBaseMe;
import com.melib.Base.LayoutStyleA;
import com.melib.Base.MouseButtonsA;
import com.melib.Base.POINT;
import com.melib.Base.RefObject;
import com.melib.Base.SIZE;
import com.melib.Layout.CLayoutDivMe;
import com.melib.ScrollBar.CHScrollBarMe;
import com.melib.ScrollBar.CVScrollBarMe;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public class CMenuMe extends CLayoutDivMe {
	public CMenuMe() {
		SetAutoSize(true);
		SetLayoutStyle(LayoutStyleA.TopToBottom);
		SetMaximumSize(new SIZE(2000, 500));
		SetShowHScrollBar(true);
		SetShowVScrollBar(true);
		SetTopMost(true);
		SIZE size = new SIZE(200, 200);
		SetSize(size);
	}

	public ArrayList<CMenuItemMe> m_items = new ArrayList();
	private int m_timerID = GetNewTimerID();

	protected boolean m_autoHide = true;
	protected CMenuItemMe m_parentItem;

	public boolean AutoHide() {
		return this.m_autoHide;
	}

	public void SetAutoHide(boolean value) {
		this.m_autoHide = value;
	}

	public CMenuItemMe GetParentItem() {
		return this.m_parentItem;
	}

	public void SetParentItem(CMenuItemMe value) {
		this.m_parentItem = value;
	}

	protected boolean m_popup = false;

	public boolean CanPopup() {
		return this.m_popup;
	}

	public void SetPopup(boolean value) {
		this.m_popup = value;
		if (this.m_popup) {
			SetVisible(false);
		}
	}

	public void AddItem(CMenuItemMe item) {
		item.SetParentMenu(this);
		item.OnAddingItem(-1);
		this.m_items.add(item);
	}

	protected void Adjust(CMenuMe menu) {
		INativeBaseMe nativeBase = GetNative();
		if (AutoSize()) {
			int contentHeight = menu.GetContentHeight();
			int cy = GetMaximumSize().cy;
			menu.SetHeight(Math.min(contentHeight, cy));
		}
		POINT location = menu.GetLocation();
		SIZE size = menu.GetSize();
		SIZE display = nativeBase.GetDisplaySize();
		if (location.x < 0) {
			location.x = 0;
		}
		if (location.y < 0) {
			location.y = 0;
		}
		if (location.x + size.cx > display.cx) {
			location.x = (display.cx - size.cx);
		}
		if (location.y + size.cy > display.cy) {
			location.y = (display.cy - size.cy);
		}
		menu.SetLocation(location);
		menu.Update();
	}

	protected void CallMenuItemMouseEvent(int eventID, CMenuItemMe item, POINT mp, MouseButtonsA button, int clicks,
			int delta) {
		if ((this.m_events != null) && (this.m_events.containsKey(Integer.valueOf(eventID)))) {
			ArrayList<Object> events = (ArrayList) this.m_events.get(Integer.valueOf(eventID));
			int eventsSize = events.size();
			for (int i = 0; i < eventsSize; i++) {
				MenuItemMouseEvent menuItemMouseEvent = (MenuItemMouseEvent) ((events
						.get(i) instanceof MenuItemMouseEvent) ? events.get(i) : null);
				if (menuItemMouseEvent != null) {
					menuItemMouseEvent.CallMenuItemMouseEvent(eventID, this, item, mp.Clone(), button, clicks, delta);
				}
			}
		}
	}

	protected boolean CheckDivFocused(ArrayList<CMenuItemMe> items) {
		int itemsSize = items.size();
		for (int i = 0; i < itemsSize; i++) {
			CMenuItemMe menuItem = (CMenuItemMe) items.get(i);
			CMenuMe dropDownMenu = menuItem.GetDropDownMenu();
			if (dropDownMenu != null) {
				if (CheckFocused(dropDownMenu)) {
					return true;
				}
			}
			ArrayList<CMenuItemMe> list = menuItem.GetItems();
			boolean focused = CheckDivFocused(list);
			if (focused) {
				return true;
			}
		}
		return false;
	}

	protected boolean CheckFocused(CControlMe control) {
		if (control.IsFocused()) {
			return true;
		}

		ArrayList<CControlMe> controls = control.GetControls();
		if ((controls != null) && (controls.size() > 0)) {
			int count = controls.size();
			for (int i = 0; i < count; i++) {
				boolean focused = CheckFocused((CControlMe) controls.get(i));
				if (focused) {
					return true;
				}
			}
		}
		return false;
	}

	public void ClearItems() {
		ArrayList<CMenuItemMe> list = new ArrayList();
		int itemsSize = this.m_items.size();
		for (int i = 0; i < itemsSize; i++) {
			list.add(this.m_items.get(i));
		}
		int listSize = list.size();
		for (int i = 0; i < listSize; i++) {
			((CMenuItemMe) list.get(i)).OnRemovingItem();
		}
		this.m_items.clear();
	}

	protected boolean CloseMenus(ArrayList<CMenuItemMe> items) {
		int count = items.size();
		boolean close = false;
		for (int i = 0; i < count; i++) {
			CMenuItemMe item = (CMenuItemMe) items.get(i);
			ArrayList<CMenuItemMe> list = item.GetItems();
			if (CloseMenus(list)) {
				close = true;
			}
			CMenuMe dropDownMenu = item.GetDropDownMenu();
			if ((dropDownMenu != null) && (dropDownMenu.IsVisible())) {
				dropDownMenu.Hide();
				close = true;
			}
		}
		return close;
	}

	public CMenuMe CreateDropDownMenu() {
		CMenuMe menu = new CMenuMe();
		menu.SetPopup(true);
		menu.SetShowHScrollBar(true);
		menu.SetShowVScrollBar(true);
		return menu;
	}

	public void Dispose() {
		if (!IsDisposed()) {
			StopTimer(this.m_timerID);
			ClearItems();
		}
		super.Dispose();
	}

	public String GetControlType() {
		return "Menu";
	}

	public ArrayList<CMenuItemMe> GetItems() {
		return this.m_items;
	}

	public void GetProperty(String name, RefObject<String> value, RefObject<String> type) {
		if (name.equals("popup")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(CanPopup());
		} else {
			super.GetProperty(name, value, type);
		}
	}

	public ArrayList<String> GetPropertyNames() {
		ArrayList<String> propertyNames = super.GetPropertyNames();
		propertyNames.addAll(Arrays.asList(new String[] { "Popup" }));
		return propertyNames;
	}

	public void InsertItem(int index, CMenuItemMe item) {
		item.SetParentMenu(this);
		item.OnAddingItem(index);
		this.m_items.add(index, item);
	}

	public boolean OnAutoHide() {
		return true;
	}

	public void OnLoad() {
		super.OnLoad();
	}

	public void OnMenuItemClick(CMenuItemMe item, POINT mp, MouseButtonsA button, int clicks, int delta) {
		if (item.GetItems().isEmpty()) {
			CallMenuItemMouseEvent(EVENTID.MENUITEMCLICK, item, mp.Clone(), button, clicks, delta);
			boolean ret = CloseMenus(this.m_items);
			if (this.m_popup) {
				Hide();
			} else {
				GetNative().Invalidate();
			}
		} else {
			OnMenuItemMouseMove(item, mp, button, clicks, delta);
		}
	}

	public void OnMenuItemMouseMove(CMenuItemMe item, POINT mp, MouseButtonsA button, int clicks, int delta) {
		INativeBaseMe nativeBase = GetNative();
		ArrayList<CMenuItemMe> items = null;
		CMenuItemMe parentItem = item.GetParentItem();
		if (parentItem != null) {
			items = parentItem.GetItems();
		} else {
			items = this.m_items;
		}

		boolean close = CloseMenus(items);
		if (item.GetItems().size() > 0) {
			CMenuMe dropDownMenu = item.GetDropDownMenu();
			if (dropDownMenu != null) {
				dropDownMenu.SetNative(nativeBase);
				LayoutStyleA layoutStyle = GetLayoutStyle();
				POINT point = new POINT(nativeBase.ClientX(item) + item.GetWidth(), nativeBase.ClientY(item));
				if ((layoutStyle == LayoutStyleA.LeftToRight) || (layoutStyle == LayoutStyleA.RightToLeft)) {
					point.x = nativeBase.ClientX(item);
					point.y = (nativeBase.ClientY(item) + item.GetHeight());
				}

				dropDownMenu.SetOpacity(GetOpacity());
				dropDownMenu.SetLocation(point);
				dropDownMenu.BringToFront();
				dropDownMenu.Focus();
				dropDownMenu.Show();
				Adjust(dropDownMenu);
			}
		}
		nativeBase.Invalidate();
	}

	public void OnMouseDown(POINT mp, MouseButtonsA button, int clicks, int delta) {
		super.OnMouseDown(mp.Clone(), button, clicks, delta);
		boolean close = CloseMenus(this.m_items);
		GetNative().Invalidate();
	}

	public void OnTimer(int timerID) {
		super.OnTimer(timerID);
		if (this.m_timerID == timerID) {
			if ((this.m_parentItem == null) && (IsVisible())) {
				if ((!CheckFocused(this)) && (!CheckDivFocused(this.m_items)) && (OnAutoHide())) {
					boolean close = CloseMenus(this.m_items);
					if (this.m_popup) {
						Hide();
					} else {
						GetNative().Invalidate();
					}
				}
			}
		}
	}

	public void OnVisibleChanged() {
		super.OnVisibleChanged();
		if (IsVisible()) {
			if (this.m_popup) {
				CHScrollBarMe hScrollBar = GetHScrollBar();
				CVScrollBarMe vScrollBar = GetVScrollBar();
				if (hScrollBar != null) {
					hScrollBar.SetPos(0);
				}
				if (vScrollBar != null) {
					vScrollBar.SetPos(0);
				}
				Focus();
				Adjust(this);
			}
			StartTimer(this.m_timerID, 10);
		} else {
			StopTimer(this.m_timerID);
			boolean close = CloseMenus(this.m_items);
			INativeBaseMe nativeBase = GetNative();
			if (nativeBase != null) {
				nativeBase.Invalidate();
			}
		}
	}

	public void RemoveItem(CMenuItemMe item) {
		item.OnRemovingItem();
		this.m_items.remove(item);
	}

	public void SetProperty(String name, String value) {
		if (name.equals("popup")) {
			SetPopup(CStrMe.ConvertStrToBool(value));
		} else {
			super.SetProperty(name, value);
		}
	}
}
