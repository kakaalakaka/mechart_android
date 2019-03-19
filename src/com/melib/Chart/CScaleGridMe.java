package com.melib.Chart;

import java.util.ArrayList;
import java.util.Arrays;
import com.melib.Base.COLOR;
import com.melib.Base.CPaintMe;
import com.melib.Base.CPropertyMe;
import com.melib.Base.CStrMe;
import com.melib.Base.RECT;
import com.melib.Base.RefObject;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class CScaleGridMe implements CPropertyMe {
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

	protected int m_distance = 30;

	public int GetDistance() {
		return this.m_distance;
	}

	public void SetDistance(int value) {
		this.m_distance = value;
	}

	protected long m_gridColor = COLOR.ARGB(80, 0, 0);

	public long GetGridColor() {
		return this.m_gridColor;
	}

	public void SetGridColor(long value) {
		this.m_gridColor = value;
	}

	protected int m_lineStyle = 2;

	public int GetLineStyle() {
		return this.m_lineStyle;
	}

	public void SetLineStyle(int value) {
		this.m_lineStyle = value;
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
		} else if (name.equals("distance")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertIntToStr(GetDistance());
		} else if (name.equals("gridcolor")) {
			type.argvalue = "color";
			value.argvalue = CStrMe.ConvertColorToStr(GetGridColor());
		} else if (name.equals("linestyle")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertIntToStr(GetLineStyle());
		} else if (name.equals("visible")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(IsVisible());
		}
	}

	public ArrayList<String> GetPropertyNames() {
		ArrayList<String> propertyNames = new ArrayList();
		propertyNames.addAll(
				Arrays.asList(new String[] { "AllowUserPaint", "Distance", "GridColor", "LineStyle", "Visible" }));
		return propertyNames;
	}

	public void OnPaint(CPaintMe paint, CDivMe div, RECT rect) {
	}

	public void SetProperty(String name, String value) {
		if (name.equals("allowuserpaint")) {
			SetAllowUserPaint(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("distance")) {
			SetDistance(CStrMe.ConvertStrToInt(value));
		} else if (name.equals("gridcolor")) {
			SetGridColor(CStrMe.ConvertStrToColor(value));
		} else if (name.equals("linestyle")) {
			SetLineStyle(CStrMe.ConvertStrToInt(value));
		} else if (name.equals("visible")) {
			SetVisible(CStrMe.ConvertStrToBool(value));
		}
	}
}
