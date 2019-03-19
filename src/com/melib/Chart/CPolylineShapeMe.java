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
public class CPolylineShapeMe extends CBaseShapeMe {
	public CPolylineShapeMe() {
		SetZOrder(2);
	}

	protected long m_color = COLOR.ARGB(255, 255, 0);

	public long GetColor() {
		return this.m_color;
	}

	public void SetColor(long value) {
		this.m_color = value;
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

	protected String m_fieldText = "";

	public String GetFieldText() {
		return this.m_fieldText;
	}

	public void SetFieldText(String value) {
		this.m_fieldText = value;
	}

	protected long m_fillColor = COLOR.EMPTY;

	public long GetFillColor() {
		return this.m_fillColor;
	}

	public void SetFillColor(long value) {
		this.m_fillColor = value;
	}

	protected PolylineStyle m_style = PolylineStyle.SolidLine;

	public PolylineStyle GetStyle() {
		return this.m_style;
	}

	public void SetStyle(PolylineStyle value) {
		this.m_style = value;
	}

	protected float m_width = 1.0F;

	public float GetWidth() {
		return this.m_width;
	}

	public void SetWidth(float value) {
		this.m_width = value;
	}

	public int GetBaseField() {
		return this.m_fieldName;
	}

	public String GetFieldText(int fieldName) {
		if (fieldName == this.m_fieldName) {
			return GetFieldText();
		}

		return null;
	}

	public int[] GetFields() {
		return new int[] { this.m_fieldName };
	}

	public void GetProperty(String name, RefObject<String> value, RefObject<String> type) {
		if (name.equals("color")) {
			type.argvalue = "color";
			value.argvalue = CStrMe.ConvertColorToStr(GetColor());
		} else if (name.equals("colorfield")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertIntToStr(GetColorField());
		} else if (name.equals("fieldname")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertIntToStr(GetFieldName());
		} else if (name.equals("fieldtext")) {
			type.argvalue = "string";
			value.argvalue = GetFieldText();
		} else if (name.equals("fillcolor")) {
			type.argvalue = "color";
			value.argvalue = CStrMe.ConvertColorToStr(GetFillColor());
		} else if (name.equals("style")) {
			type.argvalue = "enum:PolylineStyle";
			PolylineStyle style = GetStyle();
			if (style == PolylineStyle.Cycle) {
				value.argvalue = "Cycle";
			} else if (style == PolylineStyle.DashLine) {
				value.argvalue = "DashLine";
			} else if (style == PolylineStyle.DotLine) {
				value.argvalue = "DotLine";
			} else {
				value.argvalue = "SolidLine";
			}
		} else if (name.equals("width")) {
			type.argvalue = "float";
			value.argvalue = CStrMe.ConvertFloatToStr(GetWidth());
		} else {
			super.GetProperty(name, value, type);
		}
	}

	public ArrayList<String> GetPropertyNames() {
		ArrayList<String> propertyNames = super.GetPropertyNames();
		propertyNames.addAll(Arrays.asList(
				new String[] { "Color", "ColorField", "FieldName", "FieldText", "FillColor", "Style", "Width" }));
		return propertyNames;
	}

	public long GetSelectedColor() {
		return this.m_color;
	}

	public void SetProperty(String name, String value) {
		if (name.equals("color")) {
			SetColor(CStrMe.ConvertStrToColor(value));
		} else if (name.equals("colorfield")) {
			SetColorField(CStrMe.ConvertStrToInt(value));
		} else if (name.equals("fieldname")) {
			SetFieldName(CStrMe.ConvertStrToInt(value));
		} else if (name.equals("fieldtext")) {
			SetFieldText(value);
		} else if (name.equals("fillcolor")) {
			SetFillColor(CStrMe.ConvertStrToColor(value));
		} else if (name.equals("style")) {
			value = value.toLowerCase();
			if (value.equals("cyle")) {
				SetStyle(PolylineStyle.Cycle);
			} else if (value.equals("dashline")) {
				SetStyle(PolylineStyle.DashLine);
			} else if (value.equals("dotline")) {
				SetStyle(PolylineStyle.DotLine);
			} else {
				SetStyle(PolylineStyle.SolidLine);
			}
		} else if (name.equals("width")) {
			SetWidth(CStrMe.ConvertStrToFloat(value));
		} else {
			super.SetProperty(name, value);
		}
	}
}
