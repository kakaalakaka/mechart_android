package com.melib.Chart;

import java.util.ArrayList;
import com.melib.Base.CStrMe;
import com.melib.Base.RefObject;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class CTitleMe implements com.melib.Base.CPropertyMe {
	public CTitleMe(int fieldName, String fieldText, long textColor, int digit, boolean visible) {
		this.m_fieldName = fieldName;
		this.m_fieldText = fieldText;
		this.m_textColor = textColor;
		this.m_digit = digit;
		this.m_visible = visible;
	}

	protected int m_digit = 2;
	protected int m_fieldName;
	protected String m_fieldText;

	public int GetDigit() {
		return this.m_digit;
	}

	public void SetDigit(int value) {
		this.m_digit = value;
	}

	public int GetFieldName() {
		return this.m_fieldName;
	}

	public void SetFieldName(int value) {
		this.m_fieldName = value;
	}

	public String GetFieldText() {
		return this.m_fieldText;
	}

	public void SetFieldText(String value) {
		this.m_fieldText = value;
	}

	protected TextMode m_fieldTextMode = TextMode.Full;

	public TextMode GetFieldTextMode() {
		return this.m_fieldTextMode;
	}

	public void SetFieldTextMode(TextMode value) {
		this.m_fieldTextMode = value;
	}

	protected String m_fieldTextSeparator = " ";
	protected long m_textColor;

	public String GetFieldTextSeparator() {
		return this.m_fieldTextSeparator;
	}

	public void SetFieldTextSeparator(String value) {
		this.m_fieldTextSeparator = value;
	}

	public long GetTextColor() {
		return this.m_textColor;
	}

	public void SetTextColor(long value) {
		this.m_textColor = value;
	}

	protected boolean m_visible = true;

	public boolean IsVisible() {
		return this.m_visible;
	}

	public void SetVisible(boolean value) {
		this.m_visible = value;
	}

	public void GetProperty(String name, RefObject<String> value, RefObject<String> type) {
		if (name.equals("digit")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertIntToStr(GetDigit());
		} else if (name.equals("fieldname")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertIntToStr(GetFieldName());
		} else if (name.equals("fieldtext")) {
			type.argvalue = "text";
			value.argvalue = GetFieldText();
		} else if (name.equals("fieldtextmode")) {
			type.argvalue = "enum:TextMode";
			TextMode fieldTextMode = GetFieldTextMode();
			if (fieldTextMode == TextMode.Field) {
				value.argvalue = "field";
			} else if (fieldTextMode == TextMode.Full) {
				value.argvalue = "full";
			} else if (fieldTextMode == TextMode.None) {
				value.argvalue = "none";
			} else {
				value.argvalue = "value";
			}
		} else if (name.equals("fieldtextseparator")) {
			value.argvalue = GetFieldTextSeparator();
		} else if (name.equals("textcolor")) {
			type.argvalue = "color";
			value.argvalue = CStrMe.ConvertColorToStr(GetTextColor());
		} else if (name.equals("visible")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(IsVisible());
		}
	}

	public ArrayList<String> GetPropertyNames() {
		ArrayList<String> propertyNames = new ArrayList();
		propertyNames.addAll(java.util.Arrays.asList(new String[] { "Digit", "FieldName", "FieldText", "FieldTextMode",
				"FieldTextSeparator", "TextColor", "Visible" }));
		return propertyNames;
	}

	public void SetProperty(String name, String value) {
		if (name.equals("digit")) {
			SetDigit(CStrMe.ConvertStrToInt(value));
		} else if (name.equals("fieldname")) {
			SetFieldName(CStrMe.ConvertStrToInt(value));
		} else if (name.equals("fieldtext")) {
			SetFieldText(value);
		} else if (name.equals("fieldtextmode")) {
			value = value.toLowerCase();
			if (value.equals("field")) {
				SetFieldTextMode(TextMode.Field);
			} else if (value.equals("full")) {
				SetFieldTextMode(TextMode.Full);
			} else if (value.equals("none")) {
				SetFieldTextMode(TextMode.None);
			} else {
				SetFieldTextMode(TextMode.None);
			}
		} else if (name.equals("fieldtextseparator")) {
			SetFieldTextSeparator(value);
		} else if (name.equals("textcolor")) {
			SetTextColor(CStrMe.ConvertStrToColor(value));
		} else if (name.equals("visible")) {
			SetVisible(CStrMe.ConvertStrToBool(value));
		}
	}
}
