package com.melib.Chart;

import java.util.ArrayList;
import java.util.Arrays;
import com.melib.Base.COLOR;
import com.melib.Base.CStrMe;
import com.melib.Base.RefObject;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class CBarShapeMe extends CBaseShapeMe {
	public CBarShapeMe() {
		SetZOrder(0);
	}

	protected int m_colorField = CTableMe.NULLFIELD;

	public int GetColorField() {
		return this.m_colorField;
	}

	public void SetColorField(int value) {
		this.m_colorField = value;
	}

	protected long m_downColor = COLOR.ARGB(82, 255, 255);

	public long GetDownColor() {
		return this.m_downColor;
	}

	public void SetDownColor(long value) {
		this.m_downColor = value;
	}

	protected int m_fieldName = CTableMe.NULLFIELD;

	public int GetFieldName() {
		return this.m_fieldName;
	}

	public void SetFieldName(int value) {
		this.m_fieldName = value;
	}

	protected int m_fieldName2 = CTableMe.NULLFIELD;

	public int GetFieldName2() {
		return this.m_fieldName2;
	}

	public void SetFieldName2(int value) {
		this.m_fieldName2 = value;
	}

	protected String m_fieldText = "";

	public String GetFieldText() {
		return this.m_fieldText;
	}

	public void SetFieldText(String value) {
		this.m_fieldText = value;
	}

	protected String m_fieldText2 = "";

	public String GetFieldText2() {
		return this.m_fieldText2;
	}

	public void SetFieldText2(String value) {
		this.m_fieldText2 = value;
	}

	protected float m_lineWidth = 1.0F;

	public float GetLineWidth() {
		return this.m_lineWidth;
	}

	public void SetLineWidth(float value) {
		this.m_lineWidth = value;
	}

	protected BarStyle m_style = BarStyle.Rect;

	public BarStyle GetStyle() {
		return this.m_style;
	}

	public void SetStyle(BarStyle value) {
		this.m_style = value;
	}

	protected int m_styleField = CTableMe.NULLFIELD;

	public int GetStyleField() {
		return this.m_styleField;
	}

	public void SetStyleField(int value) {
		this.m_styleField = value;
	}

	protected long m_upColor = COLOR.ARGB(255, 82, 82);

	public long GetUpColor() {
		return this.m_upColor;
	}

	public void SetUpColor(long value) {
		this.m_upColor = value;
	}

	public int GetBaseField() {
		return this.m_fieldName;
	}

	public String GetFieldText(int fieldName) {
		if (fieldName == this.m_fieldName) {
			return GetFieldText();
		}
		if (fieldName == this.m_fieldName2) {
			return GetFieldText2();
		}

		return null;
	}

	public int[] GetFields() {
		if (this.m_fieldName2 == CTableMe.NULLFIELD) {
			return new int[] { this.m_fieldName };
		}

		return new int[] { this.m_fieldName, this.m_fieldName2 };
	}

	public void GetProperty(String name, RefObject<String> value, RefObject<String> type) {
		if (name.equals("colorfield")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertIntToStr(GetColorField());
		} else if (name.equals("downcolor")) {
			type.argvalue = "color";
			value.argvalue = CStrMe.ConvertColorToStr(GetDownColor());
		} else if (name.equals("fieldname")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertIntToStr(GetFieldName());
		} else if (name.equals("fieldname2")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertIntToStr(GetFieldName2());
		} else if (name.equals("fieldtext")) {
			type.argvalue = "string";
			value.argvalue = GetFieldText();
		} else if (name.equals("fieldtext2")) {
			type.argvalue = "string";
			value.argvalue = GetFieldText2();
		} else if (name.equals("linewidth")) {
			type.argvalue = "float";
			value.argvalue = CStrMe.ConvertFloatToStr(GetLineWidth());
		} else if (name.equals("style")) {
			type.argvalue = "enum:BarStyle";
			BarStyle style = GetStyle();
			if (style == BarStyle.Line) {
				value.argvalue = "Line";
			} else {
				value.argvalue = "Rect";
			}
		} else if (name.equals("stylefield")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertIntToStr(GetStyleField());
		} else if (name.equals("upcolor")) {
			type.argvalue = "double";
			value.argvalue = CStrMe.ConvertColorToStr(GetUpColor());
		} else {
			super.GetProperty(name, value, type);
		}
	}

	public ArrayList<String> GetPropertyNames() {
		ArrayList<String> propertyNames = super.GetPropertyNames();
		propertyNames.addAll(Arrays.asList(new String[] { "ColorField", "DownColor", "FieldName", "FieldName2",
				"FieldText", "FieldText2", "LineWidth", "Style", "StyleField", "UpColor" }));
		return propertyNames;
	}

	public long GetSelectedColor() {
		return this.m_downColor;
	}

	public void SetProperty(String name, String value) {
		if (name.equals("colorfield")) {
			SetColorField(CStrMe.ConvertStrToInt(value));
		} else if (name.equals("downcolor")) {
			SetDownColor(CStrMe.ConvertStrToColor(value));
		} else if (name.equals("fieldname")) {
			SetFieldName(CStrMe.ConvertStrToInt(value));
		} else if (name.equals("fieldname2")) {
			SetFieldName2(CStrMe.ConvertStrToInt(value));
		} else if (name.equals("fieldtext")) {
			SetFieldText(value);
		} else if (name.equals("fieldtext2")) {
			SetFieldText2(value);
		} else if (name.equals("linewidth")) {
			SetLineWidth(CStrMe.ConvertStrToFloat(value));
		} else if (name.equals("style")) {
			value = value.toLowerCase();
			if (value.equals("line")) {
				SetStyle(BarStyle.Line);
			} else {
				SetStyle(BarStyle.Rect);
			}
		} else if (name.equals("stylefield")) {
			SetStyleField(CStrMe.ConvertStrToInt(value));
		} else if (name.equals("upcolor")) {
			SetUpColor(CStrMe.ConvertStrToColor(value));
		} else {
			super.SetProperty(name, value);
		}
	}
}
