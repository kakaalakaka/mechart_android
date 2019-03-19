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
public class CCandleShapeMe extends CBaseShapeMe {
	public CCandleShapeMe() {
		SetZOrder(1);
	}

	protected int m_closeField = CTableMe.NULLFIELD;

	public int GetCloseField() {
		return this.m_closeField;
	}

	public void SetCloseField(int value) {
		this.m_closeField = value;
	}

	protected int m_colorField = CTableMe.NULLFIELD;
	protected String m_closeFieldText;

	public int GetColorField() {
		return this.m_colorField;
	}

	public void SetColorField(int value) {
		this.m_colorField = value;
	}

	public String GetCloseFieldText() {
		return this.m_closeFieldText;
	}

	public void SetCloseFieldText(String value) {
		this.m_closeFieldText = value;
	}

	protected long m_downColor = COLOR.ARGB(82, 255, 255);

	public long GetDownColor() {
		return this.m_downColor;
	}

	public void SetDownColor(long value) {
		this.m_downColor = value;
	}

	protected int m_highField = CTableMe.NULLFIELD;
	protected String m_highFieldText;

	public int GetHighField() {
		return this.m_highField;
	}

	public void SetHighField(int value) {
		this.m_highField = value;
	}

	public String GetHighFieldText() {
		return this.m_highFieldText;
	}

	public void SetHighFieldText(String value) {
		this.m_highFieldText = value;
	}

	protected int m_lowField = CTableMe.NULLFIELD;
	protected String m_lowFieldText;

	public int GetLowField() {
		return this.m_lowField;
	}

	public void SetLowField(int value) {
		this.m_lowField = value;
	}

	public String GetLowFieldText() {
		return this.m_lowFieldText;
	}

	public void SetLowFieldText(String value) {
		this.m_lowFieldText = value;
	}

	protected int m_openField = CTableMe.NULLFIELD;
	protected String m_openFieldText;

	public int GetOpenField() {
		return this.m_openField;
	}

	public void SetOpenField(int value) {
		this.m_openField = value;
	}

	public String GetOpenFieldText() {
		return this.m_openFieldText;
	}

	public void SetOpenFieldText(String value) {
		this.m_openFieldText = value;
	}

	protected boolean m_showMaxMin = true;

	public boolean ShowMaxMin() {
		return this.m_showMaxMin;
	}

	public void SetShowMaxMin(boolean value) {
		this.m_showMaxMin = value;
	}

	protected CandleStyle m_style = CandleStyle.Rect;

	public CandleStyle GetStyle() {
		return this.m_style;
	}

	public void SetStyle(CandleStyle value) {
		this.m_style = value;
	}

	protected int m_styleField = CTableMe.NULLFIELD;

	public int GetStyleField() {
		return this.m_styleField;
	}

	public void SetStyleField(int value) {
		this.m_styleField = value;
	}

	protected long m_tagColor = COLOR.ARGB(255, 255, 255);

	public long GetTagColor() {
		return this.m_tagColor;
	}

	public void SetTagColor(long value) {
		this.m_tagColor = value;
	}

	protected long m_upColor = COLOR.ARGB(255, 82, 82);

	public long GetUpColor() {
		return this.m_upColor;
	}

	public void SetUpColor(long value) {
		this.m_upColor = value;
	}

	public int GetBaseField() {
		return this.m_closeField;
	}

	public String GetFieldText(int fieldName) {
		if (fieldName == this.m_closeField) {
			return GetCloseFieldText();
		}
		if (fieldName == this.m_highField) {
			return GetHighFieldText();
		}
		if (fieldName == this.m_lowField) {
			return GetLowFieldText();
		}
		if (fieldName == this.m_openField) {
			return GetOpenFieldText();
		}

		return null;
	}

	public int[] GetFields() {
		return new int[] { this.m_closeField, this.m_highField, this.m_lowField, this.m_openField };
	}

	public void GetProperty(String name, RefObject<String> value, RefObject<String> type) {
		if (name.equals("closefield")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertIntToStr(GetCloseField());
		} else if (name.equals("colorfield")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertIntToStr(GetColorField());
		} else if (name.equals("closefieldtext")) {
			type.argvalue = "string";
			value.argvalue = GetCloseFieldText();
		} else if (name.equals("downcolor")) {
			type.argvalue = "color";
			value.argvalue = CStrMe.ConvertColorToStr(GetDownColor());
		} else if (name.equals("highfield")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertIntToStr(GetHighField());
		} else if (name.equals("highfieldtext")) {
			type.argvalue = "string";
			value.argvalue = GetHighFieldText();
		} else if (name.equals("lowfield")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertIntToStr(GetLowField());
		} else if (name.equals("lowfieldtext")) {
			type.argvalue = "string";
			value.argvalue = GetLowFieldText();
		} else if (name.equals("openfield")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertIntToStr(GetOpenField());
		} else if (name.equals("openfieldtext")) {
			type.argvalue = "string";
			value.argvalue = GetOpenFieldText();
		} else if (name.equals("showmaxmin")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(ShowMaxMin());
		} else if (name.equals("style")) {
			type.argvalue = "enum:CandleStyle";
			CandleStyle style = GetStyle();
			if (style == CandleStyle.American) {
				value.argvalue = "American";
			} else if (style == CandleStyle.CloseLine) {
				value.argvalue = "CloseLine";
			} else if (style == CandleStyle.Tower) {
				value.argvalue = "Tower";
			} else {
				value.argvalue = "Rect";
			}
		} else if (name.equals("stylefield")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertIntToStr(GetStyleField());
		} else if (name.equals("tagcolor")) {
			type.argvalue = "double";
			value.argvalue = CStrMe.ConvertDoubleToStr(GetTagColor());
		} else if (name.equals("upcolor")) {
			type.argvalue = "color";
			value.argvalue = CStrMe.ConvertDoubleToStr(GetUpColor());
		} else {
			super.GetProperty(name, value, type);
		}
	}

	public ArrayList<String> GetPropertyNames() {
		ArrayList<String> propertyNames = super.GetPropertyNames();
		propertyNames.addAll(Arrays.asList(new String[] { "CloseField", "ColorField", "CloseFieldText", "DownColor",
				"DownColor", "HighFieldText", "LowField", "LowFieldText", "OpenField", "OpenFieldText", "ShowMaxMin",
				"Style", "StyleField", "TagColor", "UpColor" }));
		return propertyNames;
	}

	public long GetSelectedColor() {
		return this.m_downColor;
	}

	public void SetProperty(String name, String value) {
		if (name.equals("closefield")) {
			SetCloseField(CStrMe.ConvertStrToInt(value));
		} else if (name.equals("colorfield")) {
			SetColorField(CStrMe.ConvertStrToInt(value));
		} else if (name.equals("closefieldtext")) {
			SetCloseFieldText(value);
		} else if (name.equals("downcolor")) {
			SetDownColor(CStrMe.ConvertStrToColor(value));
		} else if (name.equals("highfield")) {
			SetHighField(CStrMe.ConvertStrToInt(value));
		} else if (name.equals("highfieldtext")) {
			SetHighFieldText(value);
		} else if (name.equals("lowfield")) {
			SetLowField(CStrMe.ConvertStrToInt(value));
		} else if (name.equals("lowfieldtext")) {
			SetLowFieldText(value);
		} else if (name.equals("openfield")) {
			SetOpenField(CStrMe.ConvertStrToInt(value));
		} else if (name.equals("openfieldtext")) {
			SetOpenFieldText(value);
		} else if (name.equals("showmaxmin")) {
			SetShowMaxMin(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("style")) {
			value = value.toLowerCase();
			if (value.equals("american")) {
				SetStyle(CandleStyle.American);
			} else if (value.equals("closeline")) {
				SetStyle(CandleStyle.CloseLine);
			} else if (value.equals("tower")) {
				SetStyle(CandleStyle.Tower);
			} else {
				SetStyle(CandleStyle.Rect);
			}
		} else if (name.equals("stylefield")) {
			SetStyleField(CStrMe.ConvertStrToInt(value));
		} else if (name.equals("tagcolor")) {
			SetTagColor(CStrMe.ConvertStrToColor(value));
		} else if (name.equals("upcolor")) {
			SetUpColor(CStrMe.ConvertStrToColor(value));
		} else {
			super.SetProperty(name, value);
		}
	}
}
