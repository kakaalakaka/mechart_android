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
public class CVScaleMe implements CPropertyMe {
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

	protected ArrayList<Double> m_scaleSteps = new ArrayList();

	protected boolean m_autoMaxMin = true;

	public boolean AutoMaxMin() {
		return this.m_autoMaxMin;
	}

	public void SetAutoMaxMin(boolean value) {
		this.m_autoMaxMin = value;
	}

	protected int m_baseField = CTableMe.NULLFIELD;

	public int GetBaseField() {
		return this.m_baseField;
	}

	public void SetBaseField(int value) {
		this.m_baseField = value;
	}

	protected CCrossLineTipMe m_crossLineTip = new CCrossLineTipMe();

	public CCrossLineTipMe GetCrossLineTip() {
		return this.m_crossLineTip;
	}

	public void SetCrossLineTip(CCrossLineTipMe value) {
		this.m_crossLineTip = value;
	}

	protected int m_digit = 2;

	public int GetDigit() {
		return this.m_digit;
	}

	public void SetDigit(int value) {
		this.m_digit = value;
	}

	protected FONT m_font = new FONT("Arial", 14.0F, true, false, false);

	public FONT GetFont() {
		return this.m_font;
	}

	public void SetFont(FONT value) {
		this.m_font = value;
	}

	protected long m_foreColor = COLOR.ARGB(255, 82, 82);

	public long GetForeColor() {
		return this.m_foreColor;
	}

	public void SetForeColor(long value) {
		this.m_foreColor = value;
	}

	protected long m_foreColor2 = COLOR.EMPTY;

	public long GetForeColor2() {
		return this.m_foreColor2;
	}

	public void SetForeColor2(long value) {
		this.m_foreColor2 = value;
	}

	protected boolean m_isDisposed = false;

	public boolean IsDisposed() {
		return this.m_isDisposed;
	}

	protected int m_magnitude = 1;
	protected double m_midValue;

	public int GetMagnitude() {
		return this.m_magnitude;
	}

	public void SetMagnitude(int value) {
		this.m_magnitude = value;
	}

	public double GetMidValue() {
		return this.m_midValue;
	}

	public void SetMidValue(double value) {
		this.m_midValue = value;
	}

	protected NumberStyle m_numberStyle = NumberStyle.Standard;
	protected int m_paddingBottom;
	protected int m_paddingTop;

	public NumberStyle GetNumberStyle() {
		return this.m_numberStyle;
	}

	public void SetNumberStyle(NumberStyle value) {
		this.m_numberStyle = value;
	}

	public int GetPaddingBottom() {
		return this.m_paddingBottom;
	}

	public void SetPaddingBottom(int value) {
		this.m_paddingBottom = value;
	}

	public int GetPaddingTop() {
		return this.m_paddingTop;
	}

	public void SetPaddingTop(int value) {
		this.m_paddingTop = value;
	}

	protected boolean m_reverse = false;

	public boolean IsReverse() {
		return this.m_reverse;
	}

	public void SetReverse(boolean value) {
		this.m_reverse = value;
	}

	protected long m_scaleColor = COLOR.ARGB(150, 0, 0);

	public long GetScaleColor() {
		return this.m_scaleColor;
	}

	public void SetScaleColor(long value) {
		this.m_scaleColor = value;
	}

	protected VScaleSystem m_system = VScaleSystem.Standard;

	public VScaleSystem GetSystem() {
		return this.m_system;
	}

	public void SetSystem(VScaleSystem value) {
		this.m_system = value;
	}

	protected VScaleType m_type = VScaleType.EqualDiff;
	protected double visibleMax;
	protected double m_visibleMin;

	public VScaleType GetType() {
		return this.m_type;
	}

	public void SetType(VScaleType value) {
		this.m_type = value;
	}

	public double GetVisibleMax() {
		return this.visibleMax;
	}

	public void SetVisibleMax(double value) {
		this.visibleMax = value;
	}

	public double GetVisibleMin() {
		return this.m_visibleMin;
	}

	public void SetVisibleMin(double value) {
		this.m_visibleMin = value;
	}

	public void Dispose() {
		if (!this.m_isDisposed) {
			if (this.m_crossLineTip != null) {
				this.m_crossLineTip.Dispose();
				this.m_crossLineTip = null;
			}
			this.m_scaleSteps.clear();
			this.m_isDisposed = true;
		}
	}

	public void GetProperty(String name, RefObject<String> value, RefObject<String> type) {
		if (name.equals("allowuserpaint")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(AllowUserPaint());
		} else if (name.equals("automaxmin")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(AutoMaxMin());
		} else if (name.equals("basefield")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertIntToStr(GetBaseField());
		} else if (name.equals("digit")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertIntToStr(GetDigit());
		} else if (name.equals("font")) {
			type.argvalue = "font";
			value.argvalue = CStrMe.ConvertFontToStr(GetFont());
		} else if (name.equals("forecolor")) {
			type.argvalue = "color";
			value.argvalue = CStrMe.ConvertColorToStr(GetForeColor());
		} else if (name.equals("forecolor2")) {
			type.argvalue = "color";
			value.argvalue = CStrMe.ConvertColorToStr(GetForeColor2());
		} else if (name.equals("magnitude")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertIntToStr(GetMagnitude());
		} else if (name.equals("midvalue")) {
			type.argvalue = "double";
			value.argvalue = CStrMe.ConvertDoubleToStr(GetMidValue());
		} else if (name.equals("numberstyle")) {
			type.argvalue = "enum:NumberStyle";
			NumberStyle style = GetNumberStyle();
			if (style == NumberStyle.Standard) {
				value.argvalue = "Standard";
			} else {
				value.argvalue = "UnderLine";
			}
		} else if (name.equals("paddingbottom")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertIntToStr(GetPaddingBottom());
		} else if (name.equals("paddingtop")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertIntToStr(GetPaddingTop());
		} else if (name.equals("reverse")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(IsReverse());
		} else if (name.equals("scalecolor")) {
			type.argvalue = "color";
			value.argvalue = CStrMe.ConvertColorToStr(GetScaleColor());
		} else if (name.equals("system")) {
			type.argvalue = "enum:VScaleSystem";
			VScaleSystem system = GetSystem();
			if (system == VScaleSystem.Logarithmic) {
				value.argvalue = "Log";
			} else {
				value.argvalue = "Standard";
			}
		} else if (name.equals("type")) {
			type.argvalue = "enum:VScaleType";
			VScaleType vScaleType = GetType();
			if (vScaleType == VScaleType.Divide) {
				value.argvalue = "Divide";
			} else if (vScaleType == VScaleType.EqualDiff) {
				value.argvalue = "EqualDiff";
			} else if (vScaleType == VScaleType.EqualRatio) {
				value.argvalue = "EqualRatio";
			} else if (vScaleType == VScaleType.GoldenRatio) {
				value.argvalue = "GoldenRatio";
			} else {
				value.argvalue = "Percent";
			}
		} else if (name.equals("visiblemax")) {
			type.argvalue = "double";
			value.argvalue = CStrMe.ConvertDoubleToStr(GetVisibleMax());
		} else if (name.equals("visiblemin")) {
			type.argvalue = "double";
			value.argvalue = CStrMe.ConvertDoubleToStr(GetVisibleMin());
		}
	}

	public ArrayList<String> GetPropertyNames() {
		ArrayList<String> propertyNames = new ArrayList();
		propertyNames.addAll(Arrays.asList(new String[] { "AllowUserPaint", "AutoMaxMin", "BaseField", "Digit", "Font",
				"ForeColor", "ForeColor2", "Magnitude", "MidValue", "NumberStyle", "PaddingBottom", "PaddingTop",
				"Reverse", "ScaleColor", "System", "Type", "VisibleMax", "VisibleMin" }));
		return propertyNames;
	}

	public void OnPaint(CPaintMe paint, CDivMe div, RECT rect) {
	}

	public void SetProperty(String name, String value) {
		if (name.equals("allowuserpaint")) {
			SetAllowUserPaint(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("automaxmin")) {
			SetAutoMaxMin(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("basefield")) {
			SetBaseField(CStrMe.ConvertStrToInt(value));
		} else if (name.equals("digit")) {
			SetDigit(CStrMe.ConvertStrToInt(value));
		} else if (name.equals("font")) {
			SetFont(CStrMe.ConvertStrToFont(value));
		} else if (name.equals("forecolor")) {
			SetForeColor(CStrMe.ConvertStrToColor(value));
		} else if (name.equals("forecolor2")) {
			SetForeColor2(CStrMe.ConvertStrToColor(value));
		} else if (name.equals("magnitude")) {
			SetMagnitude(CStrMe.ConvertStrToInt(value));
		} else if (name.equals("midvalue")) {
			SetMidValue(CStrMe.ConvertStrToDouble(value));
		} else if (name.equals("numberstyle")) {
			value = value.toLowerCase();
			if (value.equals("standard")) {
				SetNumberStyle(NumberStyle.Standard);
			} else {
				SetNumberStyle(NumberStyle.UnderLine);
			}
		} else if (name.equals("paddingbottom")) {
			SetPaddingBottom(CStrMe.ConvertStrToInt(value));
		} else if (name.equals("paddingtop")) {
			SetPaddingTop(CStrMe.ConvertStrToInt(value));
		} else if (name.equals("reverse")) {
			SetReverse(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("scalecolor")) {
			SetScaleColor(CStrMe.ConvertStrToColor(value));
		} else if (name.equals("system")) {
			if (value.equals("log")) {
				SetSystem(VScaleSystem.Logarithmic);
			} else {
				SetSystem(VScaleSystem.Standard);
			}
		} else if (name.equals("type")) {
			if (value.equals("Divide")) {
				SetType(VScaleType.Divide);
			} else if (value.equals("equaldiff")) {
				SetType(VScaleType.EqualDiff);
			} else if (value.equals("equalratio")) {
				SetType(VScaleType.EqualRatio);
			} else if (value.equals("goldenratio")) {
				SetType(VScaleType.GoldenRatio);
			} else {
				SetType(VScaleType.Percent);
			}
		} else if (name.equals("visiblemax")) {
			SetVisibleMax(CStrMe.ConvertStrToDouble(value));
		} else if (name.equals("visiblemin")) {
			SetVisibleMin(CStrMe.ConvertStrToDouble(value));
		}
	}

	public ArrayList<Double> GetScaleSteps() {
		return this.m_scaleSteps;
	}

	public void SetScaleSteps(ArrayList<Double> scaleSteps) {
		this.m_scaleSteps = scaleSteps;
	}
}
