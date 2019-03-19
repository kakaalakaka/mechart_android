package com.melib.Chart;

import java.util.ArrayList;
import java.util.Arrays;
import com.melib.Base.COLOR;
import com.melib.Base.CPaintMe;
import com.melib.Base.CPropertyMe;
import com.melib.Base.CStrMe;
import com.melib.Base.FONT;
import com.melib.Base.RECT;
import com.melib.Base.RefObject;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class CCrossLineTipMe implements CPropertyMe {
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

	protected long m_backColor = COLOR.ARGB(255, 0, 0);

	public long GetBackColor() {
		return this.m_backColor;
	}

	public void SetBackColor(long value) {
		this.m_backColor = value;
	}

	protected FONT m_font = new FONT();

	public FONT GetFont() {
		return this.m_font;
	}

	public void SetFont(FONT value) {
		this.m_font = value;
	}

	protected long m_foreColor = COLOR.ARGB(255, 255, 255);

	public long GetForeColor() {
		return this.m_foreColor;
	}

	public void SetForeColor(long value) {
		this.m_foreColor = value;
	}

	protected boolean m_isDisposed = false;

	public boolean IsDisposed() {
		return this.m_isDisposed;
	}

	protected boolean m_visible = true;

	public boolean IsVisible() {
		return this.m_visible;
	}

	public void SetVisible(boolean value) {
		this.m_visible = value;
	}

	public void Dispose() {
		this.m_isDisposed = true;
	}

	public void GetProperty(String name, RefObject<String> value, RefObject<String> type) {
		if (name.equals("allowuserpaint")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(AllowUserPaint());
		} else if (name.equals("backcolor")) {
			type.argvalue = "color";
			value.argvalue = CStrMe.ConvertColorToStr(GetBackColor());
		} else if (name.equals("font")) {
			type.argvalue = "font";
			value.argvalue = CStrMe.ConvertFontToStr(GetFont());
		} else if (name.equals("forecolor")) {
			type.argvalue = "color";
			value.argvalue = CStrMe.ConvertColorToStr(GetForeColor());
		} else if (name.equals("visible")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(IsVisible());
		}
	}

	public ArrayList<String> GetPropertyNames() {
		ArrayList<String> propertyNames = new ArrayList();
		propertyNames
				.addAll(Arrays.asList(new String[] { "AllowUserPaint", "BackColor", "Font", "ForeColor", "Visible" }));
		return propertyNames;
	}

	public void OnPaint(CPaintMe paint, CDivMe div, RECT rect) {
	}

	public void SetProperty(String name, String value) {
		if (name.equals("allowuserpaint")) {
			SetAllowUserPaint(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("backcolor")) {
			SetBackColor(CStrMe.ConvertStrToColor(value));
		} else if (name.equals("font")) {
			SetFont(CStrMe.ConvertStrToFont(value));
		} else if (name.equals("forecolor")) {
			SetForeColor(CStrMe.ConvertStrToColor(value));
		} else if (name.equals("visible")) {
			SetVisible(CStrMe.ConvertStrToBool(value));
		}
	}
}
