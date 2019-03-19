package com.melib.Chart;

import java.util.ArrayList;
import java.util.Arrays;
import com.melib.Base.COLOR;
import com.melib.Base.CStrMe;
import com.melib.Base.FONT;
import com.melib.Base.RefObject;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class CTextShapeMe extends CBaseShapeMe {
	public CTextShapeMe() {
		SetZOrder(4);
	}

	protected int m_colorField = CTableMe.NULLFIELD;

	public int GetColorField() {
		return this.m_colorField;
	}

	public void SetColorField(int value) {
		this.m_colorField = value;
	}

	protected int m_fieldName = CTableMe.NULLFIELD;

	public int GetFieldName() {
		return this.m_fieldName;
	}

	public void SetFieldName(int value) {
		this.m_fieldName = value;
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

	protected int m_styleField = CTableMe.NULLFIELD;
	protected String m_text;

	public int GetStyleField() {
		return this.m_styleField;
	}

	public void SetStyleField(int value) {
		this.m_styleField = value;
	}

	public String GetText() {
		return this.m_text;
	}

	public void SetText(String value) {
		this.m_text = value;
	}

	public void GetProperty(String name, RefObject<String> value, RefObject<String> type) {
		if (name.equals("colorfield")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertIntToStr(GetColorField());
		} else if (name.equals("fieldname")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertIntToStr(GetFieldName());
		} else if (name.equals("font")) {
			type.argvalue = "font";
			value.argvalue = CStrMe.ConvertFontToStr(GetFont());
		} else if (name.equals("forecolor")) {
			type.argvalue = "color";
			value.argvalue = CStrMe.ConvertColorToStr(GetForeColor());
		} else if (name.equals("stylefield")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertIntToStr(GetStyleField());
		} else if (name.equals("text")) {
			type.argvalue = "string";
			value.argvalue = GetText();
		} else {
			super.GetProperty(name, value, type);
		}
	}

	public ArrayList<String> GetPropertyNames() {
		ArrayList<String> propertyNames = new ArrayList();
		propertyNames.addAll(
				Arrays.asList(new String[] { "ColorField", "FieldName", "Font", "ForeColor", "StyleField", "Text" }));
		return propertyNames;
	}

	public void SetProperty(String name, String value) {
		if (name.equals("colorfield")) {
			SetColorField(CStrMe.ConvertStrToInt(value));
		} else if (name.equals("fieldname")) {
			SetFieldName(CStrMe.ConvertStrToInt(value));
		} else if (name.equals("font")) {
			SetFont(CStrMe.ConvertStrToFont(value));
		} else if (name.equals("forecolor")) {
			SetForeColor(CStrMe.ConvertStrToColor(value));
		} else if (name.equals("stylefield")) {
			SetStyleField(CStrMe.ConvertStrToInt(value));
		} else if (name.equals("text")) {
			SetText(value);
		} else {
			super.SetProperty(name, value);
		}
	}
}
