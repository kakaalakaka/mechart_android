package com.melib.Chart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

public class CHScaleMe implements CPropertyMe {
	public CHScaleMe() {
		this.m_dateColors.put(DateType.Year, Long.valueOf(COLOR.ARGB(255, 255, 255)));
		this.m_dateColors.put(DateType.Month, Long.valueOf(COLOR.ARGB(150, 0, 0)));
		this.m_dateColors.put(DateType.Day, Long.valueOf(COLOR.ARGB(100, 100, 100)));
		this.m_dateColors.put(DateType.Hour, Long.valueOf(COLOR.ARGB(82, 82, 255)));
		this.m_dateColors.put(DateType.Minute, Long.valueOf(COLOR.ARGB(255, 255, 0)));
		this.m_dateColors.put(DateType.Second, Long.valueOf(COLOR.ARGB(255, 0, 255)));
		this.m_dateColors.put(DateType.Millisecond, Long.valueOf(COLOR.ARGB(255, 0, 255)));
	}

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

	protected HashMap<DateType, Long> m_dateColors = new HashMap();

	protected ArrayList<Double> m_scaleSteps = new ArrayList();

	protected CCrossLineTipMe m_crossLineTip = new CCrossLineTipMe();

	public CCrossLineTipMe GetCrossLineTip() {
		return this.m_crossLineTip;
	}

	public void SetCrossLineTip(CCrossLineTipMe value) {
		this.m_crossLineTip = value;
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

	protected int m_height = 25;

	public int GetHeight() {
		return this.m_height;
	}

	public void SetHeight(int value) {
		this.m_height = value;
	}

	protected HScaleType m_hScaleType = HScaleType.Date;

	public HScaleType GetHScaleType() {
		return this.m_hScaleType;
	}

	public void SetHScaleType(HScaleType value) {
		this.m_hScaleType = value;
	}

	protected int m_interval = 60;

	public int GetInterval() {
		return this.m_interval;
	}

	public void SetInterval(int value) {
		this.m_interval = value;
	}

	protected boolean m_isDisposed = false;

	public boolean IsDisposed() {
		return this.m_isDisposed;
	}

	protected long m_scaleColor = COLOR.ARGB(150, 0, 0);

	public long GetScaleColor() {
		return this.m_scaleColor;
	}

	public void SetScaleColor(long value) {
		this.m_scaleColor = value;
	}

	protected boolean m_visible = true;

	public boolean IsVisible() {
		return this.m_visible;
	}

	public void SetVisible(boolean value) {
		this.m_visible = value;
	}

	public void Dispose() {
		if (!this.m_isDisposed) {
			if (this.m_crossLineTip != null) {
				this.m_crossLineTip.Dispose();
				this.m_crossLineTip = null;
			}
			if (this.m_dateColors != null) {
				this.m_dateColors.clear();
				this.m_dateColors = null;
			}
			this.m_isDisposed = true;
		}
	}

	public long GetDateColor(DateType dateType) {
		return ((Long) this.m_dateColors.get(dateType)).longValue();
	}

	public void SetDateColor(DateType dateType, long color) {
		this.m_dateColors.put(dateType, Long.valueOf(color));
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
		} else if (name.equals("type")) {
			type.argvalue = "enum:HScaleType";
			HScaleType hScaleType = GetHScaleType();
			if (hScaleType == HScaleType.Date) {
				value.argvalue = "Date";
			} else {
				value.argvalue = "Number";
			}
		} else if (name.equals("interval")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertIntToStr(GetInterval());
		} else if (name.equals("scalecolor")) {
			type.argvalue = "color";
			value.argvalue = CStrMe.ConvertColorToStr(GetScaleColor());
		} else if (name.equals("visible")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(IsVisible());
		}
	}

	public ArrayList<String> GetPropertyNames() {
		ArrayList<String> propertyNames = new ArrayList();
		propertyNames.addAll(Arrays.asList(new String[] { "AllowUserPaint", "Font", "ForeColor", "Height", "Type",
				"Interval", "ScaleColor", "Visible" }));
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
		} else if (name.equals("type")) {
			value = value.toLowerCase();
			if (value.equals("date")) {
				SetHScaleType(HScaleType.Date);
			} else {
				SetHScaleType(HScaleType.Number);
			}
		} else if (name.equals("interval")) {
			SetInterval(CStrMe.ConvertStrToInt(value));
		} else if (name.equals("scalecolor")) {
			SetScaleColor(CStrMe.ConvertStrToColor(value));
		} else if (name.equals("visible")) {
			SetVisible(CStrMe.ConvertStrToBool(value));
		}
	}

	public ArrayList<Double> GetScaleSteps() {
		return this.m_scaleSteps;
	}

	public void SetScaleSteps(ArrayList<Double> scaleSteps) {
		this.m_scaleSteps = scaleSteps;
	}
}
