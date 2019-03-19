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
public class CTitleBarMe implements CPropertyMe {
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

	protected int m_height = 22;

	public int GetHeight() {
		return this.m_height;
	}

	public void SetHeight(int value) {
		this.m_height = value;
	}

	protected boolean m_isDisposed = false;

	public boolean IsDisposed() {
		return this.m_isDisposed;
	}

	protected int m_maxLine = 3;

	public int GetMaxLine() {
		return this.m_maxLine;
	}

	public void SetMaxLine(int value) {
		this.m_maxLine = value;
	}

	protected boolean m_showUnderLine = true;

	public boolean ShowUnderLine() {
		return this.m_showUnderLine;
	}

	public void SetShowUnderLine(boolean value) {
		this.m_showUnderLine = value;
	}

	protected String m_text = "";

	public String GetText() {
		return this.m_text;
	}

	public void SetText(String value) {
		this.m_text = value;
	}

	protected ArrayList<CTitleMe> m_titles = new ArrayList();

	public ArrayList<CTitleMe> GetTitles() {
		return this.m_titles;
	}

	public void SetTitles(ArrayList<CTitleMe> value) {
		this.m_titles = value;
	}

	protected long m_underLineColor = COLOR.ARGB(80, 0, 0);

	public long GetUnderLineColor() {
		return this.m_underLineColor;
	}

	public void SetUnderLineColor(long value) {
		this.m_underLineColor = value;
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
		} else if (name.equals("font")) {
			type.argvalue = "font";
			value.argvalue = CStrMe.ConvertFontToStr(GetFont());
		} else if (name.equals("forecolor")) {
			type.argvalue = "color";
			value.argvalue = CStrMe.ConvertColorToStr(GetForeColor());
		} else if (name.equals("height")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertIntToStr(GetHeight());
		} else if (name.equals("maxline")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertIntToStr(GetMaxLine());
		} else if (name.equals("showunderline")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(ShowUnderLine());
		} else if (name.equals("text")) {
			type.argvalue = "string";
			value.argvalue = GetText();
		} else if (name.equals("underlinecolor")) {
			type.argvalue = "color";
			value.argvalue = CStrMe.ConvertColorToStr(GetUnderLineColor());
		} else if (name.equals("visible")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(IsVisible());
		}
	}

	public ArrayList<String> GetPropertyNames() {
		ArrayList<String> propertyNames = new ArrayList();
		propertyNames.addAll(Arrays.asList(new String[] { "AllowUserPaint", "Font", "ForeColor", "Height", "MaxLine",
				"ShowUnderLine", "Text", "UnderLineColor", "Visible" }));
		return propertyNames;
	}

	public void OnPaint(CPaintMe paint, CDivMe div, RECT rect) {
	}

	public void SetProperty(String name, String value) {
		if (name.equals("allowuserpaint")) {
			SetAllowUserPaint(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("font")) {
			SetFont(CStrMe.ConvertStrToFont(value));
		} else if (name.equals("forecolor")) {
			SetForeColor(CStrMe.ConvertStrToColor(value));
		} else if (name.equals("height")) {
			SetHeight(CStrMe.ConvertStrToInt(value));
		} else if (name.equals("maxline")) {
			SetMaxLine(CStrMe.ConvertStrToInt(value));
		} else if (name.equals("showunderline")) {
			SetShowUnderLine(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("text")) {
			SetText(value);
		} else if (name.equals("underlinecolor")) {
			SetUnderLineColor(CStrMe.ConvertStrToColor(value));
		} else if (name.equals("visible")) {
			SetVisible(CStrMe.ConvertStrToBool(value));
		}
	}
}
