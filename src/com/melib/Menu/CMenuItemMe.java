package com.melib.Menu;

import java.util.ArrayList;
import java.util.Arrays;
import com.melib.Base.CPaintMe;
import com.melib.Base.CStrMe;
import com.melib.Base.FONT;
import com.melib.Base.INativeBaseMe;
import com.melib.Base.LayoutStyleA;
import com.melib.Base.MouseButtonsA;
import com.melib.Base.POINT;
import com.melib.Base.RECT;
import com.melib.Base.RefObject;
import com.melib.Base.SIZE;
import com.melib.Button.CButtonMe;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public class CMenuItemMe extends CButtonMe {
	public CMenuItemMe() {
		SetFont(new FONT("SimSun", 12.0F, false, false, false));
		SetSize(new SIZE(200, 25));
	}

	public CMenuItemMe(String text) {
		SetFont(new FONT("SimSun", 12.0F, false, false, false));
		SetSize(new SIZE(200, 25));
		SetText(text);
	}

	protected ArrayList<CMenuItemMe> m_items = new ArrayList();

	protected boolean m_checked = false;

	public boolean IsChecked() {
		return this.m_checked;
	}

	public void SetChecked(boolean value) {
		this.m_checked = value;
	}

	protected CMenuMe m_dropDownMenu = null;

	public CMenuMe GetDropDownMenu() {
		return this.m_dropDownMenu;
	}

	public void SetDropDownMenu(CMenuMe value) {
		this.m_dropDownMenu = value;
	}

	private CMenuItemMe m_parentItem = null;

	public CMenuItemMe GetParentItem() {
		return this.m_parentItem;
	}

	public void SetParentItem(CMenuItemMe value) {
		this.m_parentItem = value;
	}

	private CMenuMe m_parentMenu = null;
	protected String m_value;

	public CMenuMe GetParentMenu() {
		return this.m_parentMenu;
	}

	public void SetParentMenu(CMenuMe value) {
		this.m_parentMenu = value;
	}

	public String GetValue() {
		return this.m_value;
	}

	public void SetValue(String value) {
		this.m_value = value;
	}

	public void AddItem(CMenuItemMe item) {
		item.SetParentItem(this);
		item.SetParentMenu(GetParentMenu());
		item.OnAddingItem(-1);
		this.m_items.add(item);
	}

	public void ClearItems() {
		ArrayList<CMenuItemMe> list = new ArrayList();
		int size = this.m_items.size();
		for (int i = 0; i < size; i++) {
			list.add(this.m_items.get(i));
		}
		int listSize = list.size();
		for (int i = 0; i < listSize; i++) {
			((CMenuItemMe) list.get(i)).OnRemovingItem();
		}
		this.m_items.clear();
	}

	public String GetControlType() {
		return "MenuItem";
	}

	public ArrayList<CMenuItemMe> GetItems() {
		return this.m_items;
	}

	public void GetProperty(String name, RefObject<String> value, RefObject<String> type) {
		if (name.equals("checked")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(IsChecked());
		} else if (name.equals("value")) {
			type.argvalue = "string";
			value.argvalue = GetValue();
		} else {
			super.GetProperty(name, value, type);
		}
	}

	public ArrayList<String> GetPropertyNames() {
		ArrayList<String> propertyNames = super.GetPropertyNames();
		propertyNames.addAll(Arrays.asList(new String[] { "Checked", "Value" }));
		return propertyNames;
	}

	public void InsertItem(int index, CMenuItemMe item) {
		item.SetParentItem(this);
		item.SetParentMenu(GetParentMenu());
		item.OnAddingItem(index);
		this.m_items.add(index, item);
	}

	public void OnAddingItem(int index) {
		CMenuMe menu = null;
		if (this.m_parentItem != null) {
			menu = this.m_parentItem.GetDropDownMenu();
			if (menu == null) {
				menu = this.m_parentMenu.CreateDropDownMenu();
				menu.SetVisible(false);
				this.m_parentItem.SetDropDownMenu(menu);
				menu.SetParentItem(this.m_parentItem);
				this.m_parentMenu.GetNative().AddControl(menu);
			}
		} else {
			menu = this.m_parentMenu;
		}
		if (menu != null) {
			if (index != -1) {
				menu.InsertControl(index, this);
			} else {
				menu.AddControl(this);
			}

			int itemsSize = this.m_items.size();
			for (int i = 0; i < itemsSize; i++) {
				((CMenuItemMe) this.m_items.get(i)).OnAddingItem(-1);
			}
		}
	}

	public void OnClick(POINT mp, MouseButtonsA button, int clicks, int delta) {
		super.OnClick(mp.Clone(), button, clicks, delta);
		if (this.m_parentMenu != null) {
			this.m_parentMenu.OnMenuItemClick(this, mp.Clone(), button, clicks, delta);
		}
	}

	public void OnMouseMove(POINT mp, MouseButtonsA button, int clicks, int delta) {
		super.OnMouseMove(mp.Clone(), button, clicks, delta);
		if (this.m_parentMenu != null) {
			this.m_parentMenu.OnMenuItemMouseMove(this, mp.Clone(), button, clicks, delta);
		}
	}

	public void OnPaintForeground(CPaintMe paint, RECT clipRect) {
		int width = GetWidth();
		int height = GetHeight();
		if ((width > 0) && (height > 0)) {
			int right = width;
			int midY = height / 2;
			String text = GetText();
			int tRight = 0;
			long foreColor = GetPaintingForeColor();
			if ((text != null) && (text.length() > 0)) {
				FONT font = GetFont();
				SIZE tSize = paint.TextSize(text, font);
				RECT rect = new RECT();
				rect.left = 10;
				rect.top = (midY - tSize.cy / 2 + 2);
				rect.right = (rect.left + tSize.cx);
				rect.bottom = (rect.top + tSize.cy);
				paint.DrawText(text, foreColor, font, rect);
				tRight = rect.right + 4;
			}

			if (this.m_checked) {
				RECT rect = new RECT(tRight, height / 2 - 4, tRight + 8, height / 2 + 4);
				paint.FillEllipse(foreColor, rect);
			}

			if (this.m_items.size() > 0) {
				POINT point = new POINT();
				POINT point2 = new POINT();
				POINT point3 = new POINT();
				CMenuMe parentMenu = this.m_parentMenu;
				if (this.m_parentItem != null) {
					parentMenu = this.m_parentItem.GetDropDownMenu();
				}
				LayoutStyleA layoutStyle = parentMenu.GetLayoutStyle();

				if ((layoutStyle == LayoutStyleA.LeftToRight) || (layoutStyle == LayoutStyleA.RightToLeft)) {
					point.x = (right - 25);
					point.y = (midY - 2);
					point2.x = (right - 14);
					point2.y = (midY - 2);
					point3.x = (right - 20);
					point3.y = (midY + 4);
				} else {
					point.x = (right - 15);
					point.y = midY;
					point2.x = (right - 25);
					point2.y = (midY - 5);
					point3.x = (right - 25);
					point3.y = (midY + 5);
				}
				POINT[] points = { point, point2, point3 };
				paint.FillPolygon(foreColor, points);
			}
		}
	}

	public void OnRemovingItem() {
		CMenuMe dropDownMenu = null;
		if (this.m_parentItem != null) {
			dropDownMenu = this.m_parentItem.GetDropDownMenu();
		} else {
			dropDownMenu = this.m_parentMenu;
		}
		if (dropDownMenu != null) {
			if ((this.m_items != null) && (this.m_items.size() > 0)) {
				int itemSize = this.m_items.size();
				for (int i = 0; i < itemSize; i++) {
					((CMenuItemMe) this.m_items.get(i)).OnRemovingItem();
				}
			}
			dropDownMenu.RemoveControl(this);
		}
		if (this.m_dropDownMenu != null) {
			this.m_parentMenu.GetNative().RemoveControl(this.m_dropDownMenu);
			this.m_dropDownMenu.Dispose();
			this.m_dropDownMenu = null;
		}
	}

	public void RemoveItem(CMenuItemMe item) {
		item.OnRemovingItem();
		this.m_items.remove(item);
	}

	public void SetProperty(String name, String value) {
		if (name.equals("checked")) {
			SetChecked(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("value")) {
			SetValue(value);
		} else {
			super.SetProperty(name, value);
		}
	}
}
