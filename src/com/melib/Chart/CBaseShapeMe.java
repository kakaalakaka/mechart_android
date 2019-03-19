package com.melib.Chart;

import java.util.ArrayList;
import java.util.Arrays;
import com.melib.Base.CPaintMe;
import com.melib.Base.CPropertyMe;
import com.melib.Base.CStrMe;
import com.melib.Base.RECT;
import com.melib.Base.RefObject;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class CBaseShapeMe implements CPropertyMe {
	protected void finalize() throws Throwable {
		Dispose();
	}

	protected boolean m_allowUserPaint = false;

	public boolean AllowUserPaint() {
		return this.m_allowUserPaint;
	}

	public void SetAllowUserPaint(boolean allowUserPaint) {
		this.m_allowUserPaint = allowUserPaint;
	}

	protected AttachVScale m_attachVScale = AttachVScale.Left;

	public AttachVScale GetAttachVScale() {
		return this.m_attachVScale;
	}

	public void SetAttachVScale(AttachVScale value) {
		this.m_attachVScale = value;
	}

	protected boolean isDisposed = false;

	public boolean IsDisposed() {
		return this.isDisposed;
	}

	protected boolean m_selected = false;

	public boolean IsSelected() {
		return this.m_selected;
	}

	public void SetSelected(boolean value) {
		this.m_selected = value;
	}

	protected boolean m_visible = true;
	protected int m_zOrder;

	public boolean IsVisible() {
		return this.m_visible;
	}

	public void SetVisible(boolean value) {
		this.m_visible = value;
	}

	public int GetZOrder() {
		return this.m_zOrder;
	}

	public void SetZOrder(int value) {
		this.m_zOrder = value;
	}

	public void Dispose() {
		if (!this.isDisposed) {
			this.isDisposed = true;
		}
	}

	public int GetBaseField() {
		return -1;
	}

	public String GetFieldText(int fieldName) {
		return "";
	}

	public int[] GetFields() {
		return null;
	}

	public void GetProperty(String name, RefObject<String> value, RefObject<String> type) {
		if (name.equals("allowuserpaint")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(AllowUserPaint());
		} else if (name.equals("attachvscale")) {
			type.argvalue = "enum:AttachVScale";
			if (GetAttachVScale() == AttachVScale.Left) {
				value.argvalue = "Left";
			} else {
				value.argvalue = "Right";
			}
		} else if (name.equals("selected")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(IsSelected());
		} else if (name.equals("visible")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(IsVisible());
		} else if (name.equals("zorder")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertIntToStr(GetZOrder());
		} else {
			type.argvalue = "undefined";
			value.argvalue = "";
		}
	}

	public ArrayList<String> GetPropertyNames() {
		ArrayList<String> propertyNames = new ArrayList();
		propertyNames.addAll(
				Arrays.asList(new String[] { "AllowUserPaint", "AttachVScale", "Selected", "Visible", "ZOrder" }));
		return propertyNames;
	}

	public void OnPaint(CPaintMe paint, CDivMe div, RECT rect) {
	}

	public void SetProperty(String name, String value) {
		if (name.equals("allowuserpaint")) {
			SetAllowUserPaint(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("attachvscale")) {
			value = value.toLowerCase();
			if (value.equals("left")) {
				SetAttachVScale(AttachVScale.Left);
			} else {
				SetAttachVScale(AttachVScale.Right);
			}
		} else if (name.equals("selected")) {
			SetSelected(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("visible")) {
			SetVisible(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("zorder")) {
			SetZOrder(CStrMe.ConvertStrToInt(value));
		}
	}

	public long GetSelectedColor() {
		return 0L;
	}
}
